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

package com.iboot.studio.common.config;

import cn.dev33.satoken.fun.strategy.SaCorsHandleFunction;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import com.iboot.studio.common.config.log.RequestIdInterceptor;
import com.iboot.studio.infrastructure.integration.satoken.SaPermissionInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

  private final RequestIdInterceptor requestIdInterceptor;
  private final SaPermissionInterceptor saPermissionInterceptor;

  public WebMvcConfigure(
      RequestIdInterceptor requestIdInterceptor, SaPermissionInterceptor saPermissionInterceptor) {
    this.requestIdInterceptor = requestIdInterceptor;
    this.saPermissionInterceptor = saPermissionInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 注册请求ID拦截器
    registry.addInterceptor(requestIdInterceptor);
    registry.addInterceptor(saPermissionInterceptor);
  }

  @Bean
  public FilterRegistrationBean<RequestWrapperFilter> requestWrapperFilter() {
    FilterRegistrationBean<RequestWrapperFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(new RequestWrapperFilter());
    registration.addUrlPatterns("/*");
    registration.setOrder(Ordered.HIGHEST_PRECEDENCE); // 确保过滤器最先执行
    return registration;
  }

  /** CORS 跨域处理 */
  @Bean
  public SaCorsHandleFunction corsHandle() {
    return (req, res, sto) -> {
      // 从请求头获取 Origin（前端域名）
      String origin = req.getHeader("Origin");

      // 1. 允许指定域访问（根据前端域名动态设置）
      if (origin != null) {
        res.setHeader("Access-Control-Allow-Origin", origin);
      }

      // 2. 允许携带凭证（cookies等）
      res.setHeader("Access-Control-Allow-Credentials", "false");

      // 3. 其他必要配置保持不变
      res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
          .setHeader("Access-Control-Max-Age", "3600")
          // 显式列出允许的请求头，包括 content-type 和其他可能用到的头
          .setHeader(
              "Access-Control-Allow-Headers",
              // 先允许所有的请求头 不然对接各种前端的时候会报跨域错误
              // "content-type, authorization, x-requested-with, content-type, platform");
              "*");
      // 预检请求直接返回
      SaRouter.match(SaHttpMethod.OPTIONS).free(r -> {}).back();
    };
  }
}
