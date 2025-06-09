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

package com.iboot.studio.common.constant;

import lombok.Getter;

/** 响应码 */
@Getter
public enum ResponseCode {
  // 200 成功
  SUCCESS(200, "操作成功"),

  // 4xx 客户端错误
  BAD_REQUEST(400, "请求参数错误"),
  UNAUTHORIZED(401, "未授权"),
  FORBIDDEN(403, "禁止访问"),
  NOT_FOUND(404, "资源不存在"),
  METHOD_NOT_ALLOWED(405, "请求方法不允许"),
  CONFLICT(409, "资源冲突"),
  TOO_MANY_REQUESTS(429, "请求过于频繁"),

  // 5xx 服务器错误
  INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
  SERVICE_UNAVAILABLE(503, "服务不可用"),
  GATEWAY_TIMEOUT(504, "网关超时"),

  // 6xx 业务错误
  BUSINESS_ERROR(600, "业务处理失败"),
  DATA_VALIDATION_ERROR(601, "数据验证失败"),
  FILE_UPLOAD_ERROR(602, "文件上传失败"),
  FILE_DOWNLOAD_ERROR(603, "文件下载失败"),
  PRINT_ERROR(604, "打印处理失败"),
  TEMPLATE_ERROR(605, "模板处理失败");

  private final Integer code;
  private final String msg;

  ResponseCode(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}
