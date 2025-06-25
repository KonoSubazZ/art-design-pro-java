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
import com.novo.report.common.constant.GenderEnum;
import com.novo.report.common.constant.StatusEnum;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "sys_user",  autoResultMap = true)
public class User extends BaseEntity implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  @TableId(value = "user_id", type = IdType.ASSIGN_ID)
  private String userId;

  @TableField(value = "user_name")
  private String userName;

  @TableField(value = "password")
  private String password;

  @TableField(value = "real_name")
  private String realName;

  @TableField(value = "mobile")
  private String mobile;

  @TableField(value = "email")
  private String email;

  @TableField(value = "avatar")
  private String avatar;

  @TableField(value = "gender")
  private GenderEnum gender;

  @TableField(value = "user_status")
  private StatusEnum userStatus;

  @TableField(value = "intro")
  private String intro;

  /**
   * 是否是超级管理员即拥有 "*" 上帝权限
   */
  @TableField(value = "is_super_admin")
  private Boolean isSuperAdmin;
}
