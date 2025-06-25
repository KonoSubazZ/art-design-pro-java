package com.novo.report.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.novo.report.common.constant.StatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role")
public class Role extends BaseEntity implements Serializable {
  @TableField(exist = false)
  @Serial private static final long serialVersionUID = 1L;

  @TableId(value = "role_id", type = IdType.ASSIGN_ID)
  private String roleId;

  @TableField(value = "role_code")
  private String roleCode;

  @TableField(value = "role_name")
  private String roleName;

  @TableField(value = "description")
  private String description;

  @TableField(value = "enabled")
  private StatusEnum enabled;
}
