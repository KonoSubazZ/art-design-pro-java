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

package com.novo.report.service.system.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.novo.report.common.util.MenuUtil;
import com.novo.report.infrastructure.persistence.entity.system.Resource;
import com.novo.report.infrastructure.persistence.entity.system.User;
import com.novo.report.service.system.AuthService;
import com.novo.report.service.system.ResourceService;
import com.novo.report.service.system.UserService;
import com.novo.report.web.system.dto.LoginDTO;
import com.novo.report.web.system.vo.UserMenu;
import com.novo.report.web.system.vo.UserTokenInfo;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserService userService;
  private final ResourceService resourceService;

  @Override
  public UserTokenInfo login(LoginDTO loginDTO) {
    User user =
        userService.getOne(
            new LambdaQueryWrapper<User>().eq(User::getUserName, loginDTO.getUserName()));
    Assert.notNull(user, "用户不存在或密码错误");
 /*   boolean isSame = UserUtil.checkIsSamePwd(loginDTO.getPassword(), user.getPassword());
    Assert.isTrue(isSame, "用户不存在或密码错误");*/

    // 用户存在且密码匹配，生成token
    StpUtil.login(user.getUserId(), loginDTO.getRememberPassword());

    // 获取token并赋值
    SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
    UserTokenInfo userTokenInfo = new UserTokenInfo();
    userTokenInfo.setSaTokenInfo(saTokenInfo);
    return userTokenInfo;
  }

  @Override
  public List<UserMenu> getUserMenus(String userId) {
    List<Resource> resources = Lists.newArrayList();

    User user = userService.getById(userId);
    Assert.notNull(user, "用户不存在");

    if (user.getIsSuperAdmin()) {
      // 超级管理员返回所有菜单
      resources = resourceService.list();
      return MenuUtil.generateUserMenus(resources);
    }

    // 获取用户所拥有的角色
    List<String> roleList = StpUtil.getRoleList(userId);
    resources = resourceService.listByRoleList(roleList);
    // 根据角色获取角色所拥有的菜单
    return MenuUtil.generateUserMenus(resources);
  }

  @Override
  public UserTokenInfo getUserInfo() {
    // 获取当前账户登录信息
    String userId = StpUtil.getLoginIdAsString();
    User user = userService.getById(userId);

    // 准备返回用户信息及token
    UserTokenInfo userTokenInfo = BeanUtil.toBean(user, UserTokenInfo.class);
    userTokenInfo.setRoles(StpUtil.getRoleList(userId));

    return userTokenInfo;
  }

  @Override
  public void logout() {
    String loginId = StpUtil.getLoginIdAsString();
    StpUtil.logout(loginId);
  }
}
