package com.iboot.studio.common.util;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.extra.spring.SpringUtil;
import com.iboot.studio.infrastructure.persistence.entity.User;
import com.iboot.studio.service.UserService;

import java.util.Objects;

public class UserUtil {
  public static String getHashPassword(String userName, String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt(userName.length()));
  }

  public static boolean checkPassword(String password, String hashPassword) {
    return BCrypt.checkpw(password, hashPassword);
  }

  public static User getCurrentUser() {
    Object loginId = StpUtil.getLoginId();
    if (Objects.isNull(loginId)) {
      // 返回系统管理员
      User user = new User();
      user.setUserId("1");
      user.setUserName("admin");
      user.setRealName("系统管理员");
      return user;
    }
    UserService userService = SpringUtil.getBean(UserService.class);
    return userService.getById(loginId.toString());
  }
}
