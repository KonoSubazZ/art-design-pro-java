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

package com.iboot.studio.web.vo;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserMenu {

  /** 菜单 id */
  private String id;

  /** 路由 */
  private String path;

  /** 组件名 */
  private String name;

  /** 字符串类型，表示组件路径 */
  private String component;

  /** 菜单元信息 */
  private Meta meta;

  /** 子菜单 */
  private List<UserMenu> children;

  @Data
  @Accessors(chain = true)
  public static class Meta {

    /** 菜单名称 */
    private String title;

    /** 菜单图标（Unicode） */
    private String icon;

    /** 是否显示徽标 */
    private Boolean showBadge;

    /** 是否显示新徽标 */
    private String showTextBadge;

    /** 是否在菜单中隐藏 */
    private Boolean isHide;

    /** 是否在标签页中隐藏 */
    private Boolean isHideTab;

    /** 链接 */
    private String link;

    /** 是否是 iframe */
    private Boolean isIframe;

    /** 是否缓存 */
    private Boolean keepAlive;

    /** 是否固定标签页 */
    private Boolean fixedTab;

    /** 可操作权限 */
    private List<Auth> authList;

    /** 是否在主容器中 */
    private Boolean isInMainContainer;
  }

  @Data
  public static class Auth {
    /** 权限 ID */
    private String id;

    /** 权限名称 */
    private String title;

    /** 权限标识 */
    private String authMark;
  }
}
