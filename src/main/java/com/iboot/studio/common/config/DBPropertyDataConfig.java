package com.iboot.studio.common.config;

import com.iboot.studio.common.util.JacksonUtil;
import com.iboot.studio.infrastructure.persistence.entity.SysDataConfig;
import com.iboot.studio.infrastructure.persistence.repository.SysDataConfigRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

@Slf4j
@Configuration("DBPropertyDataConfig")
@RequiredArgsConstructor
public class DBPropertyDataConfig {

  private final ConfigurableEnvironment configurableEnvironment;
  private final SysDataConfigRepository sysDataConfigRepository;

  @PostConstruct
  public void init() {
    // 获取Spring环境中的所有配置源（PropertySources）
    MutablePropertySources sources = configurableEnvironment.getPropertySources();
    // 从数据库查询所有配置项（键值对）
    List<SysDataConfig> configs = sysDataConfigRepository.selectList(null);
    // 如果没有查到配置，直接返回
    if (configs == null || configs.isEmpty()) return;
    // 将数据库配置项转换为Map，key为dataKey，value为dataValue
    Map<String, Object> propertyMap =
        configs.stream()
            .collect(Collectors.toMap(SysDataConfig::getDataKey, SysDataConfig::getDataValue));
    // 用MapPropertySource包装数据库配置，命名为db_data_config
    MapPropertySource mapPropertySource = new MapPropertySource("db_data_config", propertyMap);
    // 将数据库配置源添加到配置源列表首位，优先级最高
    sources.addFirst(mapPropertySource);
    log.info("加载数据库配置项完成, 配置数据：{}", JacksonUtil.toJsonStr(mapPropertySource));
  }
}
