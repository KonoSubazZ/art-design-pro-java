package com.iboot.studio.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.iboot.studio.common.enumdict.EnumDict;
import com.iboot.studio.common.enumdict.IEnumDict;
import lombok.Getter;

/** 通用状态枚举 */
@Getter
@EnumDict(value = "status", displayName = "状态")
public enum StatusEnum implements IEnumDict {
  ENABLED(1, "启用"),
  DISABLED(0, "禁用");

  StatusEnum(Integer value, String label) {
    this.value = value;
    this.label = label;
  }

  @JsonValue @EnumValue private final Integer value;
  private final String label;
}
