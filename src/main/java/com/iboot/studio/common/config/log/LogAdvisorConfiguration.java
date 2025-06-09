/*
 * Proprietary Software License
 *
 * Copyright (c) 2025 iboot
 *
 * This software and its associated documentation ("Software") are proprietary property of iboot.
 * Without explicit written permission from iboot, no individual or entity may:
 *
 * 1. Copy, modify, merge, publish, distribute, sublicense, or sell copies of the Software;
 * 2. Reverse engineer, decompile, or disassemble the Software;
 * 3. Remove or alter any copyright notices or other proprietary markings in the Software;
 * 4. Use the Software for any commercial purposes.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * IBOOT BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * The Software may not be used without explicit written permission from iboot.
 * Author: tangsc.
 */

package com.iboot.studio.common.config.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "log.aop.pointcut.value")
@EnableConfigurationProperties(AopProperties.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LogAdvisorConfiguration {

  @Bean
  public AspectJExpressionPointcutAdvisor configurableAdvisor(AopProperties aopProperties) {
    log.info("初始化 Log AOP 配置，切入点：{}", aopProperties.getValue());
    AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
    advisor.setExpression(aopProperties.getValue());
    advisor.setAdvice(new LogAdviceHandler());
    log.info("Log AOP 初始化完毕");
    return advisor;
  }
}
