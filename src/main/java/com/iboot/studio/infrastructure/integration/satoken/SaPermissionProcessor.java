package com.iboot.studio.infrastructure.integration.satoken;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.router.SaRouterStaff;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.iboot.studio.common.exception.DemoModeException;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/** 权限处理器 */
@Component
@EnableConfigurationProperties(SaExtensionProperties.class)
@RequiredArgsConstructor
public class SaPermissionProcessor {
  private final SaExtensionProperties properties;

  public void process() {
    if (!properties.getEnabled()) {
      // 未启用权限校验
      return;
    }

    SaRouterStaff saRouterStaff = SaRouter.match("/**");
	  Set<String> excludes = properties.getExcludes();
	  if (CollUtil.isNotEmpty(excludes)) {
      // 排除不需要权限校验的资源
      saRouterStaff.notMatch(Lists.newArrayList(excludes));
    }
    saRouterStaff.check(
        r -> {
          // 登录校验
          StpUtil.checkLogin();
          // 权限校验
          checkPermission();
        });
  }

  private void checkPermission() {
    SaRequest request = SaHolder.getRequest();
    if (Objects.isNull(request)) {
      // 非 http 请求放行避免空指针异常
      return;
    }
    String requestPath = request.getRequestPath();
		System.out.println("requestPath = " + requestPath);
    String requestMethod = request.getMethod();
    System.out.println("requestMethod = " + requestMethod);

		if (properties.getDemoMode() && !"GET".equals(requestMethod) && !properties.getDemoModeIncludes().contains(requestPath)) {
			throw new DemoModeException();
		}
  }
}
