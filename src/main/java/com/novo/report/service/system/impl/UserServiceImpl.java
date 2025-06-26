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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.novo.report.infrastructure.persistence.entity.system.User;
import com.novo.report.infrastructure.persistence.repository.system.RoleRepository;
import com.novo.report.infrastructure.persistence.repository.system.UserRepository;
import com.novo.report.service.system.UserService;
import com.novo.report.web.system.dto.UserDTO;
import com.novo.report.web.system.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserRepository, User> implements UserService {
  @Value("${iboot-studio.default-password:123456}")
  private String defaultPassword;

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void saveOrUpdate(UserDTO userDTO) {
    // 根据用户名查询用户
    User one =
        new LambdaQueryChainWrapper<>(userRepository)
            .eq(User::getUserName, userDTO.getUserName())
            .one();
    User bean = BeanUtil.toBean(userDTO, User.class);

    // 判断是否是新增用户
    boolean isAddUser = !StringUtils.hasText(userDTO.getUserId());
    if (isAddUser) {
      //  判断用户名是否已存在
      Assert.isNull(one, "用户名已存在");
      // 密码加密
      String hashedPassword =
          BCrypt.hashpw(DigestUtil.sha256Hex(defaultPassword), BCrypt.gensalt(userDTO.getUserName().length()));
      bean.setPassword(hashedPassword);
//      bean.setUserId(IdUtil.getMonotonicUlid());
    } else {
      // 判断是否是修改用户名是不是已经存在
      Assert.isTrue(Objects.equals(bean.getUserId(), userDTO.getUserId()), "用户名已存在");
    }
    this.saveOrUpdate(bean);

    // 处理用户角色
    roleRepository.deleteRoleUserByUserId(bean.getUserId());
    Set<String> roleIds = userDTO.getRoleIds();
    if (CollUtil.isEmpty(roleIds)) {
      return;
    }
    roleRepository.insertRoleUser(bean.getUserId(), roleIds);
  }

  @Override
  public Page<UserVO> getUserPage(UserDTO userDTO) {
    return userRepository.getUserPage(userDTO);
  }
}
