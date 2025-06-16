package com.iboot.studio.infrastructure.integration.satoken;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.router.SaRouterStaff;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.iboot.studio.common.exception.DemoModeException;
import com.iboot.studio.common.exception.UnauthorizedException;
import com.iboot.studio.common.util.MenuUtil;
import com.iboot.studio.infrastructure.persistence.entity.User;
import com.iboot.studio.service.UserService;
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
    saRouterStaff.check(this::performPermissionCheck);
  }

  private void performPermissionCheck() {
    // 登录校验
    StpUtil.checkLogin();
    checkPermission();
  }

  private void checkPermission() {
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
    Object loginId = StpUtil.getLoginId();
    User user = userService.getById(loginId.toString());
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
