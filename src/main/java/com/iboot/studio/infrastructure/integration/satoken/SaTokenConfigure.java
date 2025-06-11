package com.iboot.studio.infrastructure.integration.satoken;

import cn.dev33.satoken.strategy.SaStrategy;
import com.github.f4b6a3.ulid.Ulid;
import jakarta.annotation.PostConstruct;

/**
 * Sa-Token 的 token 生成策略,如需重写请在此类中配置打开注解
 */
//@Configuration
public class SaTokenConfigure {
  /** 重写 Sa-Token 框架内部算法策略 */
  @PostConstruct
  public void rewriteSaStrategy() {
    SaStrategy.instance.createToken = (loginId, loginType) -> Ulid.fast().toLowerCase();
  }
}
