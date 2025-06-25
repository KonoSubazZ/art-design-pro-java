package com.novo.report.infrastructure.integration.satoken;

import com.google.common.collect.Sets;
import java.util.Set;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/** Sa-Token 扩展配置 */
@ConfigurationProperties(prefix = "sa-token.extension")
@Data
public class SaExtensionProperties {
  /** 是否启用 Sa-Token */
  private Boolean enabled = true;

  /** 登录或者无需校验权限的资源 */
  private Set<String> excludes = Sets.newHashSet();

  /**
   * 登录后用户拥有的最基础的权限（比如用户查看自己的用户信息、修改用户信息等）
   */
  private Set<String> basicPermissions = Sets.newHashSet();

  /** 是否演示模式（登录登出允许操作，其他非 GET 请求全部禁止操作） */
  private Boolean demoMode = false;
  /**
   * 演示模式下可操作的资源
   */
  private Set<String> demoModeIncludes = Sets.newHashSet();
}
