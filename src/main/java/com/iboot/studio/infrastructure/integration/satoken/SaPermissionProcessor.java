package com.iboot.studio.infrastructure.integration.satoken;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 权限处理器
 */
@Component
@EnableConfigurationProperties(SaExtensionProperties.class)
public class SaPermissionProcessor {
	public void process() {
		// 登录校验
		StpUtil.checkLogin();
		// 权限校验
		SaRequest request = SaHolder.getRequest();
		if (Objects.isNull(request)) {
			// 非 http 请求放行避免空指针异常
			return;
		}
		// 请求路径
		String requestPath = request.getRequestPath();
		// 请求方式
		String requestMethod = request.getMethod();
    System.out.println("requestPath = " + requestPath);
    System.out.println("requestMethod = " + requestMethod);
	}
}
