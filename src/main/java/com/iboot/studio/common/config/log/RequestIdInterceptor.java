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

import cn.hutool.core.text.CharSequenceUtil;
import com.iboot.studio.common.util.IdUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestIdInterceptor implements HandlerInterceptor {

  private static final String REQUEST_ID = "requestId";
  private static final String REQUEST_ID_HEADER = "X-Request-ID";

  @Override
  public boolean preHandle(
      @NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response,
      @NotNull Object handler) {
    // 优先从请求头中获取请求ID
    String requestId = request.getHeader(REQUEST_ID_HEADER);
    // 如果请求头中没有，则生成新的单调递增ULID
    if (CharSequenceUtil.isEmpty(requestId)) {
      requestId = IdUtil.getMonotonicUlid();
    }
    // 将请求ID放入MDC
    MDC.put(REQUEST_ID, requestId);
    // 将请求ID添加到响应头中
    response.addHeader(REQUEST_ID_HEADER, requestId);
    return true;
  }

  @Override
  public void afterCompletion(
      @NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response,
      @NotNull Object handler,
      Exception ex) {
    // 请求完成后清除MDC中的请求ID
    MDC.remove(REQUEST_ID);
  }
}
