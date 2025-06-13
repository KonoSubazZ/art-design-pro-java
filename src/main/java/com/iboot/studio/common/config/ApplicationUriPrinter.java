package com.iboot.studio.common.config;

import com.iboot.studio.common.util.PathUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** 输出服务访问地址 */
@Component
@Order
@DependsOn("DBPropertyDataConfig")
public class ApplicationUriPrinter implements CommandLineRunner {
  @Value("${iboot-studio.show-server-url:true}")
  private Boolean showServerUrl;

  @Value("${server.port:18080}")
  private int port;

  @Value("${server.servlet.context-path:}")
  private String contextPath;

  @Override
  public void run(String... args) {
    if (!showServerUrl) {
      return;
    }
    System.out.println(
        "********************************************当前服务相关地址********************************************");
    String ip = "IP";
    try {
      ip = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      System.out.println("当前服务地址获取失败");
    }
    String schema = "http://";
    String localUrl =
        schema + PathUtils.replaceSlash(String.format("localhost:%s/%s/", port, contextPath));
    String externUrl =
        schema + PathUtils.replaceSlash(String.format("%s:%s/%s/", ip, port, contextPath));
    System.out.printf(
        """
								服务启动成功! Access URLs:\

								\t接口本地地址: \t\t%s\

								\t接口外部地址: \t\t%s
								""",
        localUrl, externUrl);

    System.out.println(
        "********************************************当前服务相关地址********************************************");
  }
}
