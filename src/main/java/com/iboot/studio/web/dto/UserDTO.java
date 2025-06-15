package com.iboot.studio.web.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iboot.studio.common.constant.GenderEnum;
import com.iboot.studio.web.vo.UserVO;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends Page <UserVO> {
  private String userId;

  @NotNull(message = "用户名不能为空")
  private String userName;

  @NotNull(message = "姓名不能为空")
  private String realName;

  private String mobile;

  private String email;

  private String avatar;

  private GenderEnum gender;

  private String intro;

  /** 角色ID列表 */
  private Set<String> roleIds;
}
