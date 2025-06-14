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

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** 统一返回 */
@Data
@Component
public class R<T> {
  @JsonIgnore private ResponseCode responseCode;
  private Integer code;
  private Boolean success;
  private String msg;
  private String detailMsg;
  private T data;

  private static R<?> instance;

  @Value("${iboot-studio.response.detail-msg.enabled:true}")
  private boolean detailMsgEnabled;

  public R() {
    instance = this;
  }

  private R(Integer code, Boolean success, String msg, String detailMsg, T data) {
    this.code = code;
    this.success = success;
    this.msg = msg;
    this.detailMsg = detailMsg;
    this.data = data;
  }

  private static boolean isDetailMsgEnabled() {
    return instance != null && instance.detailMsgEnabled;
  }

  public static <T> R<T> success() {
    return new R<>(ResponseCode.SUCCESS.getCode(), true, ResponseCode.SUCCESS.getMsg(), null, null);
  }

  public static <T> R<T> success(T data) {
    return new R<>(ResponseCode.SUCCESS.getCode(), true, ResponseCode.SUCCESS.getMsg(), null, data);
  }

  public static <T> R<T> success(ResponseCode responseCode, String msg, String detailMsg, T data) {
    msg = StrUtil.isEmpty(msg) ? responseCode.getMsg() : msg;
    return new R<>(
        responseCode.getCode(), true, msg, isDetailMsgEnabled() ? detailMsg : null, data);
  }

  public static <T> R<T> failed() {
    return new R<>(
        ResponseCode.INTERNAL_SERVER_ERROR.getCode(),
        false,
        ResponseCode.INTERNAL_SERVER_ERROR.getMsg(),
        null,
        null);
  }

  public static <T> R<T> failed(ResponseCode responseCode, String msg, String detailMsg, T data) {
    msg = StrUtil.isEmpty(msg) ? responseCode.getMsg() : msg;
    return new R<>(
        responseCode.getCode(), false, msg, isDetailMsgEnabled() ? detailMsg : null, data);
  }
}
