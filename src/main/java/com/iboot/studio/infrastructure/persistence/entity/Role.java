package com.iboot.studio.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role")
public class Role extends BaseEntity implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  @TableId(value = "role_id", type = IdType.ASSIGN_ID)
  private String roleId;
}
