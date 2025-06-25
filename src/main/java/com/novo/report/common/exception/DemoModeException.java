package com.novo.report.common.exception;

public class DemoModeException extends RuntimeException {
  public DemoModeException() {}

  public DemoModeException(String message) {
    super(message);
  }

  public DemoModeException(String message, Throwable cause) {
    super(message, cause);
  }

  public DemoModeException(Throwable cause) {
    super(cause);
  }

  public DemoModeException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
