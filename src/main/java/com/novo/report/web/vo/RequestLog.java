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

package com.novo.report.web.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

/** 请求日志封装类 */
@Data
@Builder
public class RequestLog {
  /** 请求ID */
  private String xRequestId;

  /** 请求路径 */
  private String path;

  /** 请求方法 */
  private String method;

  /** 请求头 */
  private Map<String, String> headers;

  /** 请求参数 */
  private Map<String, String[]> parameters;

  /** 请求体 */
  private Object body;

  /** 客户端IP */
  private String clientIp;

  /** 用户代理 */
  private String userAgent;

  /** 请求时间 */
  @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
  private LocalDateTime requestTime;

  /** 完整 cURL */
  private String cURL;
}
