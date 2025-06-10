package com.iboot.studio.common.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.iboot.studio.common.enumdict.EnumDict;
import com.iboot.studio.common.enumdict.IEnumDict;
import lombok.Getter;

@Getter
@EnumDict(value = "gender", displayName = "性别")
public enum GenderEnum implements IEnumDict {
  MALE(0, "女"),
  FEMALE(1, "男"),
  UNKNOWN(2, "未知");

  @JsonValue @EnumValue private final Integer value;

  private final String label;

  GenderEnum(Integer value, String label) {
    this.value = value;
    this.label = label;
  }
}
