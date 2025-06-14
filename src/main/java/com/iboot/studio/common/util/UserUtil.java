package com.iboot.studio.common.util;

import cn.hutool.crypto.digest.BCrypt;

public class UserUtil {
  public static String getHashPassword(String userName, String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt(userName.length()));
  }

  public static boolean checkPassword(String password, String hashPassword) {
    return BCrypt.checkpw(password, hashPassword);
  }
}
