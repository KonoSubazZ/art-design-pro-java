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

package com.iboot.studio.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.iboot.studio.common.constant.R;
import com.iboot.studio.common.constant.ResponseCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** 全局异常处理器 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
  /** 处理业务异常 */
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.OK)
  public R<Void> handleNotFoundException(NotFoundException e) {
    log.error("资源未找到异常", e);
    return R.failed(ResponseCode.NOT_FOUND, "资源未找到", ExceptionUtils.getStackTrace(e), null);
  }

  /** 未登录 */
  @ExceptionHandler(NotLoginException.class)
  @ResponseStatus(HttpStatus.OK)
  public R<Void> handleNotLoginException(NotLoginException e) {
    log.error("token失效", e);
    return R.failed(ResponseCode.UNAUTHORIZED, "未登录或登录失效", ExceptionUtils.getStackTrace(e), null);
  }

  /** 未登录 */
  @ExceptionHandler(DemoModeException.class)
  @ResponseStatus(HttpStatus.OK)
  public R<Void> handleDemoModeException(DemoModeException e) {
    log.error("演示模式异常", e);
    return R.failed(ResponseCode.FORBIDDEN, "演示模式禁止操作", ExceptionUtils.getStackTrace(e), null);
  }

  /** 处理空指针异常 */
  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(HttpStatus.OK)
  public R<Void> handleNullPointerException(NullPointerException e) {
    log.error("空指针异常", e);
    String message = "空指针异常，请检查相关对象是否已正确初始化";
    return R.failed(
        ResponseCode.INTERNAL_SERVER_ERROR, message, ExceptionUtils.getStackTrace(e), null);
  }

  /** 处理参数异常 */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.OK)
  public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    String msg =
        fieldErrors.stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
    log.error("参数异常", e);
    return R.failed(ResponseCode.BAD_REQUEST, msg, ExceptionUtils.getStackTrace(e), null);
  }

  /** 处理参数校验异常 */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.OK)
  public R<Void> handleIllegalArgumentException(IllegalArgumentException e) {
    log.error("内部参数异常", e);
    return R.failed(
        ResponseCode.BAD_REQUEST,
        ExceptionUtils.getMessage(e),
        ExceptionUtils.getStackTrace(e),
        null);
  }

  /** 处理绑定异常 */
  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.OK)
  public R<Void> handleBindException(BindException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    String errorMessage =
        fieldErrors.stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
    log.error("参数绑定异常", e);
    return R.failed(
        ResponseCode.DATA_VALIDATION_ERROR,
        "参数错误：" + errorMessage,
        ExceptionUtils.getStackTrace(e),
        null);
  }

  /** 处理其他所有异常 */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.OK)
  public R<Void> handleException(Exception e) {
    log.error("系统异常", e);
    return R.failed(
        ResponseCode.INTERNAL_SERVER_ERROR,
        "系统未知错误：" + ExceptionUtils.getMessage(e),
        ExceptionUtils.getStackTrace(e),
        null);
  }
}
