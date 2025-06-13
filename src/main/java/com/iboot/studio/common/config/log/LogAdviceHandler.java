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

package com.iboot.studio.common.config.log;

import com.iboot.studio.common.util.JacksonUtil;
import com.iboot.studio.common.util.ServletUtil;
import com.iboot.studio.common.util.WebUtil;
import com.iboot.studio.web.vo.RequestLog;
import com.iboot.studio.web.vo.ResponseLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
public class LogAdviceHandler implements MethodInterceptor {
  ThreadLocal<StopWatch> stopWatchLocal = new ThreadLocal<>();

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    log.debug("开始执行方法：{}#{}", invocation.getThis().getClass().getName(),invocation.getMethod().getName());
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

    // 记录请求日志
    assert response != null;
    String requestId = response.getHeader("X-Request-ID");
    RequestLog requestLog =
        RequestLog.builder()
            .xRequestId(requestId)
            .path(request.getRequestURI())
            .method(request.getMethod())
            .headers(WebUtil.getRequestHeaders(request))
            .parameters(request.getParameterMap())
            .body(WebUtil.getRequestBody(requestWrapper))
            .clientIp(ServletUtil.getClientIP(request))
            .userAgent(request.getHeader("User-Agent"))
            .build();

    // 使用WebUtil构建cURL命令
    requestLog.setCURL(WebUtil.buildCURL(requestLog));

    log.info("请求信息: {}", JacksonUtil.toJsonStr(requestLog));
    log.debug("请求详情: {}", requestLog);

    // 执行方法
    Object result = invocation.proceed();
    log.debug("方法执行完成，结果：{}", result);

    // 执行后
    clock = stopWatchLocal.get();
    clock.stop();

    // 记录响应日志
    ResponseLog responseLog =
        ResponseLog.builder()
            .xRequestId(requestId)
            .status(response.getStatus())
            .headers(WebUtil.getResponseHeaders(response))
            .body(result)
            .duration(clock.getTotalTimeMillis() + "ms")
            .build();
    log.info("响应信息: {}", JacksonUtil.toJsonStr(responseLog));

    stopWatchLocal.remove();
    return result;
  }
}
