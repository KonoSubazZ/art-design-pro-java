package com.iboot.studio.web.vo;

import com.iboot.studio.common.constant.GenderEnum;
import com.iboot.studio.common.constant.StatusEnum;
import com.iboot.studio.infrastructure.persistence.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends BaseEntity {
  private String userId;

  private String userName;

  private String realName;

  private String mobile;

  private String email;

  private String avatar;

  private GenderEnum gender;

  private StatusEnum userStatus;

  private String intro;

  private Boolean isSuperAdmin;
}
