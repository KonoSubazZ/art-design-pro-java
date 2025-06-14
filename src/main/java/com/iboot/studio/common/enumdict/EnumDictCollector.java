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
    // 创建类扫描器
    ClassPathScanningCandidateComponentProvider scanner = createScanner();
    
    // 扫描每个包
    for (String packageName : scanPackages.split(",")) {
      scanPackage(scanner, packageName.trim());
    }
  }

  private ClassPathScanningCandidateComponentProvider createScanner() {
    ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
    scanner.addIncludeFilter(new AnnotationTypeFilter(EnumDict.class));
    return scanner;
  }

  private void scanPackage(ClassPathScanningCandidateComponentProvider scanner, String packageName) {
    try {
      // 获取包下的所有候选组件
      Set<Class<?>> enumClasses = findEnumClasses(scanner, packageName);
      
      // 处理每个枚举类
      for (Class<?> enumClass : enumClasses) {
        processEnumClass(enumClass);
      }
    } catch (Exception e) {
      log.warn("扫描包 {} 时出错", packageName, e);
    }
  }

  private Set<Class<?>> findEnumClasses(ClassPathScanningCandidateComponentProvider scanner, String packageName) {
    return scanner.findCandidateComponents(packageName).stream()
        .map(this::loadClass)
        .filter(this::isValidEnumDict)
        .collect(Collectors.toSet());
  }

  private Class<?> loadClass(org.springframework.beans.factory.config.BeanDefinition beanDefinition) {
    try {
      return Class.forName(beanDefinition.getBeanClassName());
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  private boolean isValidEnumDict(Class<?> clazz) {
    return clazz != null && clazz.isEnum() && IEnumDict.class.isAssignableFrom(clazz);
  }

  private void processEnumClass(Class<?> enumClass) {
    // 获取注解信息
    EnumDict annotation = enumClass.getAnnotation(EnumDict.class);
    String key = getEnumDictKey(annotation, enumClass);

    // 检查重复
    checkDuplicateKey(key, enumClass);

    // 创建字典信息
    EnumDictInfo info = createEnumDictInfo(annotation, enumClass);
    enumDictMap.put(key, info);
  }

  private String getEnumDictKey(EnumDict annotation, Class<?> enumClass) {
    return StringUtils.hasText(annotation.value()) ? annotation.value() : enumClass.getName();
  }

  private void checkDuplicateKey(String key, Class<?> enumClass) {
    if (enumDictMap.containsKey(key)) {
      throw new IllegalStateException(
          String.format("发现重复的EnumDict value='%s'，存在于以下类中：%s", key, enumClass.getName()));
    }
  }

  private EnumDictInfo createEnumDictInfo(EnumDict annotation, Class<?> enumClass) {
    EnumDictInfo info = new EnumDictInfo();
    info.setName(getEnumDictKey(annotation, enumClass));
    info.setDisplayName(annotation.displayName());
    info.setOptions(createEnumOptions(enumClass));
    return info;
  }

  private List<EnumOption> createEnumOptions(Class<?> enumClass) {
    return Arrays.stream(enumClass.getEnumConstants())
        .map(this::createEnumOption)
        .collect(Collectors.toList());
  }

  private EnumOption createEnumOption(Object enumConstant) {
    IEnumDict enumDict = (IEnumDict) enumConstant;
    return new EnumOption(enumDict.getValue(), enumDict.getLabel());
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
