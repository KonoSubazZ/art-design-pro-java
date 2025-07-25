package com.novo.report.infrastructure.integration.satoken;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.router.SaRouterStaff;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.novo.report.common.exception.DemoModeException;
import com.novo.report.common.exception.UnauthorizedException;
import com.novo.report.common.util.MenuUtil;
import com.novo.report.infrastructure.persistence.entity.system.User;
import com.novo.report.service.system.UserService;
import java.util.Objects;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/** 权限处理器 */
@Slf4j
@Component
@EnableConfigurationProperties(SaExtensionProperties.class)
@RequiredArgsConstructor
public class SaPermissionProcessor {
  private final SaExtensionProperties properties;
  private final UserService userService;

  public void process() {
    if (!properties.getEnabled()) {
      log.warn("权限校验未启用");
      return;
    }
    // 先拦截所以路径
    SaRouterStaff saRouterStaff = SaRouter.match("/**");
    Set<String> excludes = properties.getExcludes();
    if (CollUtil.isNotEmpty(excludes)) {
      // 排除不需要权限校验的资源
      saRouterStaff.notMatch(Lists.newArrayList(excludes));
    }
    // 进行权限检查
    saRouterStaff.check(this::checkPermission);
  }
  private void checkPermission() {
    // 登录校验
    StpUtil.checkLogin();

    // 权限检查
    SaRequest request = SaHolder.getRequest();
    if (Objects.isNull(request)) {
      log.debug("非HTTP请求，跳过权限校验");
      return;
    }

    String requestPath = request.getRequestPath();
    String requestMethod = request.getMethod();

    boolean demoModeRequest = isDemoModeRequest(requestMethod, requestPath);
    if (demoModeRequest) {
      throw new DemoModeException("演示模式禁止操作");
    }

    // 权限校验
    String loginId = StpUtil.getLoginIdAsString();
    User user = userService.getById(loginId);
    if (user.getIsSuperAdmin()) {
      // 超级管理员不需要权限校验
      return;
    }

    String resourceCode = MenuUtil.requestPath2ResourceCode(requestPath);
    boolean hasPermission = StpUtil.hasPermission(resourceCode);
    if (!hasPermission) {
      throw new UnauthorizedException("权限不足");
    }
  }

  private boolean isDemoModeRequest(String requestMethod, String requestPath) {
    return properties.getDemoMode()
        && !"GET".equals(requestMethod)
        && !properties.getDemoModeIncludes().contains(requestPath);
  }
}
