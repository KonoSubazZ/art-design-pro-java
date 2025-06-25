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

package com.novo.report.common.config;

import com.novo.report.common.util.JacksonUtil;
import com.novo.report.common.util.WebUtil;
import com.novo.report.web.vo.RequestLog;
import com.novo.report.web.vo.ResponseLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
public class LogAdviceHandler implements MethodInterceptor {

  ThreadLocal<StopWatch> stopWatchLocal = new ThreadLocal<>();

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    // 执行前
    StopWatch clock = new StopWatch(String.valueOf(System.currentTimeMillis()));
    clock.start();
    stopWatchLocal.set(clock);

    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    HttpServletResponse response = attributes.getResponse();

    // 包装请求和响应对象
    ContentCachingRequestWrapper requestWrapper = WebUtil.wrapRequest(request);
    ContentCachingResponseWrapper responseWrapper = WebUtil.wrapResponse(response);

    // 获取请求ID
    String requestId = request.getHeader("X-Request-ID");

    // 记录请求日志
    RequestLog requestLog =
        RequestLog.builder()
            .path(request.getRequestURI())
            .method(request.getMethod())
            .headers(WebUtil.getRequestHeaders(request))
            .parameters(request.getParameterMap())
            .body(WebUtil.getRequestBody(requestWrapper))
            .clientIp(WebUtil.getClientIp(request))
            .userAgent(request.getHeader("User-Agent"))
            .build();
    log.info("请求信息: {}", JacksonUtil.toJsonPrettyStr(requestLog));

    // 执行方法
    Object result = invocation.proceed();

    // 执行后
    clock = stopWatchLocal.get();
    clock.stop();

    // 记录响应日志
    ResponseLog responseLog =
        ResponseLog.builder()
            .xRequestId(requestId)
            .status(response.getStatus())
            .headers(WebUtil.getResponseHeaders(response))
            .body(WebUtil.getResponseBody(responseWrapper))
            .duration(clock.getTotalTimeMillis() + "ms")
            .build();
    log.info("响应信息: {}", JacksonUtil.toJsonPrettyStr(responseLog));

    stopWatchLocal.remove();
    return result;
  }
}
