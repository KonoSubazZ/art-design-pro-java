package com.iboot.studio.infrastructure.integration.satoken;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Sa-Token 扩展配置
 */
@ConfigurationProperties(prefix = "sa-token.extension")
@Data
public class SaExtensionProperties {
	/**
	 * 是否启用 Sa-Token
	 */
	private Boolean  enabled;
	/**
	 * 开放的资源
	 */
	private List<String> openResources;
	/**
	 * 是否演示模式（登录登出允许操作，其他非 GET 请求全部禁止操作）
	 */
	private Boolean demoMode;
}
