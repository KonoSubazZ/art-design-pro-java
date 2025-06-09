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

package com.iboot.studio.common.util;

import cn.hutool.core.util.StrUtil;
import com.iboot.studio.web.vo.RequestLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * Web 工具类
 *
 * <p>提供常用的 Web 相关工具方法，包括： 1. 请求信息获取：IP、请求头、请求体等 2. 响应信息获取：响应头、响应体等 3. 请求类型判断：是否为 JSON 请求等
 *
 * <p>使用方式： 1. 所有方法都是静态方法，直接通过类名调用 2. 部分方法需要包装后的请求/响应对象，可以使用 wrapRequest/Response 方法获取
 */
@Slf4j
public final class WebUtil {

  /** 请求/响应体最大记录长度：1MB */
  public static final int MAX_CONTENT_LENGTH = 1024 * 1024;

  /** 私有构造函数，防止实例化 */
  private WebUtil() {}

  /**
   * 包装请求对象
   *
   * <p>使用 ContentCachingRequestWrapper 包装原始请求对象，以便能够多次读取请求体。 如果请求对象已经是包装类型，则直接返回。
   *
   * @param request 原始请求对象
   * @return 包装后的请求对象
   */
  public static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
    return request instanceof ContentCachingRequestWrapper
        ? (ContentCachingRequestWrapper) request
        : new ContentCachingRequestWrapper(request);
  }

  /**
   * 包装响应对象
   *
   * <p>使用 ContentCachingResponseWrapper 包装原始响应对象，以便能够多次读取响应体。 如果响应对象已经是包装类型，则直接返回。
   *
   * @param response 原始响应对象
   * @return 包装后的响应对象
   */
  public static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
    return response instanceof ContentCachingResponseWrapper
        ? (ContentCachingResponseWrapper) response
        : new ContentCachingResponseWrapper(response);
  }

  /**
   * 获取完整的请求路径（包含查询参数）
   *
   * @param request 请求对象
   * @return 完整的请求路径
   */
  public static String getFullPath(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    String queryString = request.getQueryString();
    return StrUtil.isNotEmpty(queryString) ? requestURI + "?" + queryString : requestURI;
  }

  /**
   * 获取所有请求头
   *
   * @param request 请求对象
   * @return 请求头映射表
   */
  public static Map<String, String> getRequestHeaders(HttpServletRequest request) {
    Map<String, String> headers = new HashMap<>();
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      headers.put(headerName, request.getHeader(headerName));
    }
    return headers;
  }

  /**
   * 获取请求体内容
   *
   * <p>注意： 1. 只有 Content-Type 为 application/json 的请求才会返回请求体 2. 如果请求体为空或读取失败，则返回 null 3.
   * 需要包装后的请求对象才能读取请求体
   *
   * @param request 包装后的请求对象
   * @return 请求体内容，如果不是 JSON 请求或读取失败则返回 null
   */
  public static Object getRequestBody(ContentCachingRequestWrapper request) {
    log.debug("开始读取请求体，Content-Type: {}", request.getContentType());
    if (!isJsonRequest(request)) {
      log.debug("不是JSON请求，跳过请求体读取");
      return null;
    }
    byte[] content = request.getContentAsByteArray();
    if (content.length == 0) {
      return null;
    }
    try {
      String reqBodyStr = new String(content, StandardCharsets.UTF_8);
      return JacksonUtil.parseObject(reqBodyStr, Map.class);
    } catch (Exception e) {
      log.error("读取请求体失败", e);
      return null;
    }
  }

  /**
   * 获取所有响应头
   *
   * @param response 响应对象
   * @return 响应头映射表
   */
  public static Map<String, String> getResponseHeaders(HttpServletResponse response) {
    Map<String, String> headers = new HashMap<>();
    for (String headerName : response.getHeaderNames()) {
      headers.put(headerName, response.getHeader(headerName));
    }
    return headers;
  }

  /**
   * 获取响应体内容
   *
   * <p>注意： 1. 如果响应体为空或超过最大长度限制，则返回空字符串 2. 最大长度限制为 1MB 3. 需要包装后的响应对象才能读取响应体
   *
   * @param response 包装后的响应对象
   * @return 响应体内容
   */
  public static String getResponseBody(ContentCachingResponseWrapper response) {
    if (response == null) {
      return "";
    }
    // 只记录 application/json 类型的响应体
    String contentType = response.getContentType();
    if (contentType == null || !contentType.contains("application/json")) {
      return "";
    }
    byte[] content = response.getContentAsByteArray();
    if (content == null || content.length == 0 || content.length >= MAX_CONTENT_LENGTH) {
      return "";
    }
    try {
      return new String(content, response.getCharacterEncoding());
    } catch (Exception e) {
      return new String(content);
    }
  }

  /**
   * 获取客户端真实 IP 地址
   *
   * <p>按以下顺序尝试获取： 1. X-Forwarded-For 2. Proxy-Client-IP 3. WL-Proxy-Client-IP 4. 直接连接的客户端 IP
   *
   * @param request 请求对象
   * @return 客户端 IP 地址
   */
  public static String getClientIp(HttpServletRequest request) {
    String[] headers = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP"};
    for (String header : headers) {
      String ip = request.getHeader(header);
      if (StrUtil.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
        return ip;
      }
    }
    return request.getRemoteAddr();
  }

  /**
   * 判断是否为 JSON 请求
   *
   * @param request 请求对象
   * @return 如果 Content-Type 包含 application/json 则返回 true
   */
  public static boolean isJsonRequest(HttpServletRequest request) {
    String contentType = request.getContentType();
    return contentType != null && contentType.contains(MediaType.APPLICATION_JSON_VALUE);
  }

  /**
   * 根据RequestLog构建cURL命令
   *
   * @param requestLog 请求日志对象
   * @return cURL命令字符串
   */
  public static String buildCURL(RequestLog requestLog) {
    StringBuilder curl = new StringBuilder("curl -X ").append(requestLog.getMethod());

    // 只添加Content-Type请求头
    Map<String, String> headers = requestLog.getHeaders();
    if (headers != null && headers.containsKey("content-type")) {
      curl.append(" -H \"Content-Type:").append(headers.get("content-type")).append("\"");
    }

    // 添加请求体
    Object body = requestLog.getBody();
    if (body != null) {
      String bodyStr = body.toString();
      if (!bodyStr.isEmpty()) {
        // 使用JacksonUtil格式化JSON
        String formattedBody = JacksonUtil.toJsonPrettyStr(body);
        curl.append(" -d '").append(formattedBody).append("'");
      }
    }

    // 构建完整URL（包含host和port）
    String fullUrl = buildFullUrl(requestLog);
    curl.append(" --url \"").append(fullUrl).append("\"");

    String cURL = curl.toString();

    cURL = cURL.replaceAll("\r\n", "");

    return cURL;
  }

  /**
   * 构建完整的URL（包含host和port）
   *
   * @param requestLog 请求日志对象
   * @return 完整的URL
   */
  private static String buildFullUrl(RequestLog requestLog) {
    Map<String, String> headers = requestLog.getHeaders();
    StringBuilder url = new StringBuilder("http://");
    if (headers != null && headers.containsKey("host")) {
      url.append(headers.get("host")).append(requestLog.getPath());
    }
    // 添加URL参数
    Map<String, String[]> params = requestLog.getParameters();
    if (params != null && !params.isEmpty()) {
      String queryString =
          params.entrySet().stream()
              .flatMap(
                  entry -> {
                    String key = entry.getKey();
                    return java.util.Arrays.stream(entry.getValue())
                        .map(value -> key + "=" + value);
                  })
              .collect(Collectors.joining("&"));
      url.append("?").append(queryString);
    }

    return url.toString();
  }
}
