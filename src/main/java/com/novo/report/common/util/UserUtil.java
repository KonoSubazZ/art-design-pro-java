package com.novo.report.common.util;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.extra.spring.SpringUtil;
import com.novo.report.infrastructure.persistence.entity.system.User;
import com.novo.report.service.system.UserService;
import java.util.Objects;

public class UserUtil {
  /**
   * 校验密码是否相同
   * @param sha256Hex SHA256 加密后的16进制字符串密码
   * @param pwdBCrypt BCrypt 加密后的密码
   * @return true or false
   */
  public static boolean checkIsSamePwd(String sha256Hex, String pwdBCrypt) {
    return BCrypt.checkpw(sha256Hex, pwdBCrypt);
  }

  public static User getCurrentUser() {
    String loginId = StpUtil.getLoginIdAsString();
    if (Objects.isNull(loginId)) {
      // 返回系统管理员
      User user = new User();
      user.setUserId(1);
      user.setUserName("admin");
      user.setRealName("系统管理员");
      return user;
    }
    UserService userService = SpringUtil.getBean(UserService.class);
    return userService.getById(loginId);
  }
}
