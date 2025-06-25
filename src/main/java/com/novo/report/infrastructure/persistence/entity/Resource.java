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

package com.novo.report.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_resource")
public class Resource extends BaseEntity implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  @TableId(value = "resource_id", type = IdType.ASSIGN_ID)
  private String resourceId;

  @TableField(value = "parent_id")
  private String parentId;

  @TableField(value = "resource_name")
  private String resourceName;

  @TableField(value = "resource_code")
  private String resourceCode;

  @TableField(value = "component_path")
  private String componentPath;

  @TableField("resource_type")
  private Integer resourceType;

  @TableField("sort_order")
  private String sortOrder;

  @TableField("title")
  private String title;

  @TableField("icon")
  private String icon;

  @TableField("show_badge")
  private Boolean showBadge;

  @TableField("show_text_badge")
  private String showTextBadge;

  @TableField("is_hide")
  private Boolean isHide;

  @TableField("is_hide_tab")
  private Boolean isHideTab;

  @TableField("link")
  private String link;

  @TableField("is_iframe")
  private Boolean isIframe;

  @TableField("keep_alive")
  private Boolean keepAlive;

  @TableField("fixed_tab")
  private Boolean fixedTab;

  @TableField("is_in_main_container")
  private Boolean isInMainContainer;
}
