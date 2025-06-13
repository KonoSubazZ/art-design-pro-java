package com.iboot.studio.common.enumdict;

import com.iboot.studio.common.util.JacksonUtil;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnumDictCollector {
  private final ApplicationContext applicationContext;

  @Value("${iboot-studio.enum-dict.enabled:false}")
  private boolean enabled;

  @Value("${iboot-studio.enum-dict.scan-packages:}")
  private String scanPackages;

  private final Map<String, EnumDictInfo> enumDictMap = new HashMap<>();

  @PostConstruct
  public void init() {
    if (!enabled) {
      return;
    }

    if (!StringUtils.hasText(scanPackages)) {
      throw new IllegalStateException("必须配置 iboot-studio.enum-dict.scan-packages 属性，用于指定枚举类扫描包路径");
    }
    log.info("启用枚举类转字典功能，扫描包路径：{}", scanPackages);
    scanEnums();
    log.info("启用枚举类转字典功能，扫描结果：{}", JacksonUtil.toJsonStr(enumDictMap));
  }

  private void scanEnums() {
    Set<Class<?>> enumClasses = new HashSet<>();
    String[] packages = scanPackages.split(",");

    for (String packageName : packages) {
      enumClasses.addAll(findEnumClasses(packageName.trim()));
    }

    Map<String, List<Class<?>>> valueToClasses = new HashMap<>();

    for (Class<?> enumClass : enumClasses) {
      EnumDict annotation = enumClass.getAnnotation(EnumDict.class);
      if (annotation != null) {
        // 检查是否实现了IEnumDict接口
        if (!IEnumDict.class.isAssignableFrom(enumClass)) {
          throw new IllegalStateException(
              String.format("枚举类 %s 使用了@EnumDict注解，但未实现IEnumDict接口", enumClass.getName()));
        }

        String value = annotation.value();
        String key = StringUtils.hasText(value) ? value : enumClass.getName();

        valueToClasses.computeIfAbsent(key, k -> new ArrayList<>()).add(enumClass);
      }
    }

    // 检查重复的value
    valueToClasses.forEach(
        (key, classes) -> {
          if (classes.size() > 1) {
            String duplicateClasses =
                classes.stream().map(Class::getName).collect(Collectors.joining(", "));
            throw new IllegalStateException(
                String.format("发现重复的EnumDict value='%s'，存在于以下类中：%s", key, duplicateClasses));
          }
        });

    // 构建最终结果
    valueToClasses.forEach(
        (key, classes) -> {
          Class<?> enumClass = classes.get(0);
          EnumDict annotation = enumClass.getAnnotation(EnumDict.class);

          EnumDictInfo info = new EnumDictInfo();
          info.setName(key);
          info.setDisplayName(annotation.displayName());
          info.setOptions(
              Arrays.stream(enumClass.getEnumConstants())
                  .map(
                      enumConstant -> {
                        IEnumDict enumDict = (IEnumDict) enumConstant;
                        return new EnumOption(enumDict.getValue(), enumDict.getLabel());
                      })
                  .collect(Collectors.toList()));

          enumDictMap.put(key, info);
        });
  }

  private Set<Class<?>> findEnumClasses(String packageName) {
    Set<Class<?>> result = new HashSet<>();
    try {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      String path = packageName.replace('.', '/');
      java.net.URL resource = classLoader.getResource(path);
      if (resource != null) {
        java.io.File directory = new java.io.File(resource.getFile());
        if (directory.exists()) {
          findEnumClassesInDirectory(directory, packageName, result);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("扫描枚举类失败", e);
    }
    return result;
  }

  private void findEnumClassesInDirectory(
      java.io.File directory, String packageName, Set<Class<?>> result) {
    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          findEnumClassesInDirectory(file, packageName + "." + file.getName(), result);
        } else if (file.getName().endsWith(".class")) {
          String className =
              packageName + "." + file.getName().substring(0, file.getName().length() - 6);
          try {
            Class<?> clazz = Class.forName(className);
            if (clazz.isEnum() && clazz.isAnnotationPresent(EnumDict.class)) {
              result.add(clazz);
            }
          } catch (ClassNotFoundException e) {
            // 忽略找不到的类
          }
        }
      }
    }
  }

  public List<EnumDictInfo> getAllEnumDicts() {
    return new ArrayList<>(enumDictMap.values());
  }

  @Data
  public static class EnumDictInfo {
    private String name;
    private String displayName;
    private List<EnumOption> options;
  }

  @Data
  public static class EnumOption {
    private Object value;
    private Object label;

    public EnumOption(Object value, Object label) {
      this.value = value;
      this.label = label;
    }
  }
}
