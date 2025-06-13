package com.iboot.studio.common.config.log;

import com.iboot.studio.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "iboot-studio.log.aop.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(LogAopProperties.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LogAdvisorConfiguration {
  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  public AspectJExpressionPointcutAdvisor configurableAdvisor(LogAopProperties logAopProperties) {
    String value = logAopProperties.getPointcut().getValue();
    Assert.isTrue(StringUtils.hasText(value), "已启用日志切面，但 logAopProperties.pointcut.value 为空");
    log.info("Log AOP 初始化开始，logAopProperties.pointcut.value: {}", JacksonUtil.toJsonStr(value));
    AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
    advisor.setExpression(value);
    advisor.setAdvice(new LogAdviceHandler());
    log.info("Log AOP 初始化完毕，advisor: {}", advisor);
    return advisor;
  }
}
