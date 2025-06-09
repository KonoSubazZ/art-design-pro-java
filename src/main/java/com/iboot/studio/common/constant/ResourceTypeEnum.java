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

/** 资源类型枚举 */
@Getter
public enum ResourceTypeEnum {
  /** 菜单 */
  MENU(0, "目录"),

  /** 按钮/接口 */
  BUTTON(1, "按钮"),

  /** 链接 */
  LINK(2, "链接"),

  /** 页面 */
  PAGE(3, "菜单");

  private final Integer code;
  private final String desc;

  ResourceTypeEnum(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  /**
   * 根据code获取枚举
   *
   * @param code 类型码
   * @return 对应的枚举，如果不存在返回null
   */
  public static ResourceTypeEnum getByCode(Integer code) {
    if (code == null) {
      return null;
    }

    for (ResourceTypeEnum type : values()) {
      if (type.getCode().equals(code)) {
        return type;
      }
    }

    return null;
  }
}
