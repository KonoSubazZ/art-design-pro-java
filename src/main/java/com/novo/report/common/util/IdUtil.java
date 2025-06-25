package com.novo.report.common.util;

import com.github.f4b6a3.ulid.UlidCreator;

public class IdUtil extends cn.hutool.core.util.IdUtil {
  /**
   * 获取相对单调递增的 ULID
   *
   * @return
   */
  public static String getMonotonicUlid() {
    return UlidCreator.getMonotonicUlid().toLowerCase();
  }
}
