package com.novo.report.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** 统一属性配置，以数据库作为配置中心 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "sys_data_config")
public class SysDataConfig extends BaseEntity {
  /** 配置数据ID */
  @TableId(value = "data_id", type = IdType.ASSIGN_ID)
  private String dataId;

  /** namespace, 用于隔离不同业务（比如 public的无需登录即可访问） */
  @TableField(value = "namespace")
  private String namespace;

  @TableField(value = "data_key")
  private String dataKey;

  @TableField(value = "data_value")
  private String dataValue;

  @TableField(value = "description")
  private String description;

  @TableField(value = "data_group")
  private String dataGroup;

  @TableField(value = "deleted")
  private Integer deleted;
}
