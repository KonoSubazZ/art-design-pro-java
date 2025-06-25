package com.novo.report.infrastructure.integration.satoken;

import cn.dev33.satoken.strategy.SaStrategy;
import com.novo.report.common.util.IdUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

/** Sa-Token 的 token 生成策略,如需重写请在此类中配置打开注解 */
@Configuration
public class SaTokenConfigure {
  @PostConstruct
  public void rewriteSaStrategy() {
    SaStrategy.instance.createToken = (loginId, loginType) -> IdUtil.getMonotonicUlid();
  }
}
