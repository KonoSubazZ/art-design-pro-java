package com.iboot.studio.infrastructure.integration.satoken;

import cn.dev33.satoken.strategy.SaStrategy;
import com.github.f4b6a3.ulid.Ulid;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaTokenConfigure {
  /** 重写 Sa-Token 框架内部算法策略 */
  @PostConstruct
  public void rewriteSaStrategy() {
    SaStrategy.instance.createToken = (loginId, loginType) -> Ulid.fast().toLowerCase();
  }
}
