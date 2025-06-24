package com.iboot.studio.infrastructure.integration.mybatisplus;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** MyBatis-Plus SQL日志配置属性 */
@Data
@Component
@ConfigurationProperties(prefix = "mybatis-plus.extension")
public class MybatisPlusSqLogProperties {
  
  /** 是否打印SQL执行时间 */
  private boolean showExecuteTime = true;
  
  /** 是否格式化SQL语句 */
  private boolean formatSql = true;
  
  /** 是否启用SQL日志 */
  private boolean enabled = true;
} 