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

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionInterceptor extends SaInterceptor {

  public static final List<String> OPEN_RESOURCES =
      List.of(
          "/",
          "/api/iboot/auth/login",
          "/api/iboot/auth/logout",
          // 排除静态资源目录
          "/static/**",
          "/assets/**",
          // 排除根目录下的静态文件
          "/*.js",
          "/*.css",
          "/*.html",
          "/*.ico",
          "/*.png",
          "/*.jpg",
          "/*.jpeg",
          "/*.gif",
          "/*.svg",
          "/*.woff",
          "/*.woff2",
          "/*.ttf",
          "/*.json");

  public PermissionInterceptor() {
    super(
        handler -> {
          // 指定一条 match 规则
          SaRouter
              // 拦截的 path 列表，可以写多个 */
              .match("/**")
              // 不拦截以下接口（sa-token 不用加 serverContextPath 前缀但需要 / 开头）
              .notMatch(OPEN_RESOURCES)
              // 要执行的校验动作，可以写完整的 lambda 表达式
              .check(r -> StpUtil.checkLogin());

          // 配置权限认证规则
          SaRouter.match("/user/**", r -> StpUtil.checkPermission("user"));
        });
  }
}
