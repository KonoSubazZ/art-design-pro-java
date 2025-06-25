package com.novo.report.web.vo;

import com.novo.report.common.constant.GenderEnum;
import com.novo.report.common.constant.StatusEnum;
import com.novo.report.infrastructure.persistence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends BaseEntity {
  private Long userId;

  private String userName;

  private String realName;

  private String mobile;

  private String email;

  private String avatar;

  private GenderEnum gender;

  private StatusEnum userStatus;

  private String intro;

  private Boolean isSuperAdmin;

  /** 角色ID列表 */
  private Set<String> roleIds;
}
