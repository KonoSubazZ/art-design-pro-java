package com.iboot.studio.common.enumdict;

import com.iboot.studio.common.util.JacksonUtil;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnumDictCollector {
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
    ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
    scanner.addIncludeFilter(new AnnotationTypeFilter(EnumDict.class));

    String[] packages = scanPackages.split(",");
    for (String packageName : packages) {
      try {
        Set<Class<?>> enumClasses = scanner.findCandidateComponents(packageName.trim()).stream()
            .map(beanDefinition -> {
              try {
                return Class.forName(beanDefinition.getBeanClassName());
              } catch (ClassNotFoundException e) {
                return null;
              }
            })
            .filter(clazz -> clazz != null && clazz.isEnum() && IEnumDict.class.isAssignableFrom(clazz))
            .collect(Collectors.toSet());

        for (Class<?> enumClass : enumClasses) {
          EnumDict annotation = enumClass.getAnnotation(EnumDict.class);
          String value = annotation.value();
          String key = StringUtils.hasText(value) ? value : enumClass.getName();

          if (enumDictMap.containsKey(key)) {
            throw new IllegalStateException(
                String.format("发现重复的EnumDict value='%s'，存在于以下类中：%s", key, enumClass.getName()));
          }

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
        }
      } catch (Exception e) {
        log.warn("扫描包 {} 时出错", packageName, e);
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
