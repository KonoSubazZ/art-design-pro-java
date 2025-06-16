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

package com.iboot.studio.infrastructure.integration.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import com.google.common.collect.Lists;
import com.iboot.studio.infrastructure.persistence.entity.Resource;
import com.iboot.studio.infrastructure.persistence.entity.Role;
import com.iboot.studio.infrastructure.persistence.entity.User;
import com.iboot.studio.service.ResourceService;
import com.iboot.studio.service.RoleService;
import com.iboot.studio.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/** 权限加载接口实现类，保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展 */
@Component
@RequiredArgsConstructor
public class SaPermissionImpl implements StpInterface {
  private final UserService userService;
  private final ResourceService resourceService;
  private final RoleService roleService;

  @Override
  public List<String> getPermissionList(Object loginId, String loginType) {
    // 该账户所具有的权限码
    List<String> permissionList = Lists.newArrayList();

    String userId = Convert.toStr(loginId);
    User user = userService.getById(userId);
    Assert.notNull(user, "用户不存在:" + userId);

    if (user.getIsSuperAdmin()) {
      // 超级管理员返回所有的权限
      List<Resource> resources = resourceService.list();
      permissionList =
          resources.stream().map(Resource::getResourceCode).collect(Collectors.toList());
      return CollUtil.isNotEmpty(permissionList) ? permissionList : Lists.newArrayList();
    }

    // 获取角色列表
    List<String> roleList = this.getRoleList(userId, loginType);
    if (CollUtil.isEmpty(roleList)) {
      // 用户没有角色返回空集合权限码
      return permissionList;
    }

    // 获取角色所具有的权限码
    List<Resource> resources = resourceService.listByRoleList(roleList);
    permissionList = resources.stream().map(Resource::getResourceCode).collect(Collectors.toList());
    return CollUtil.isNotEmpty(permissionList) ? permissionList : Lists.newArrayList();
  }

  @Override
  public List<String> getRoleList(Object loginId, String loginType) {
    List<Role> roles;
    User user = userService.getById(Convert.toStr(loginId));
    Assert.notNull(user, "用户不存在:" + loginId);

    if (user.getIsSuperAdmin()) {
      roles = roleService.list();
      return roles.stream().map(Role::getRoleCode).toList();
    }
    roles = roleService.listByUserId(loginId);
    return roles.stream().map(Role::getRoleCode).toList();
  }
}
