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

package com.iboot.studio.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iboot.studio.common.constant.R;
import com.iboot.studio.common.constant.ResponseCode;
import com.iboot.studio.infrastructure.persistence.entity.User;
import com.iboot.studio.service.AuthService;
import com.iboot.studio.service.UserService;
import com.iboot.studio.web.dto.UserDTO;
import com.iboot.studio.web.vo.UserTokenInfo;
import com.iboot.studio.web.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.iboot.studio.common.constant.Const.SERVER_API_PATH;

@RestController
@RequestMapping(SERVER_API_PATH + "/user")
@RequiredArgsConstructor
public class UserController {
  @Value("${iboot-studio.default-password:123456}")
  private String defaultPassword;

  private final AuthService authService;
  private final UserService userService;

  @GetMapping("/info")
  public R<UserTokenInfo> getUserInfo() {
    UserTokenInfo userTokenInfo = authService.getUserInfo();
    return R.success(userTokenInfo);
  }

  @GetMapping("/page")
  public R<Page<UserVO>> getUserPage() {
    Page<User> page = userService.page(new Page<>());
    Page<UserVO> userVOPage = BeanUtil.toBean(page, Page.class);
    userVOPage.setRecords(BeanUtil.copyToList(page.getRecords(), UserVO.class));
    return R.success(userVOPage);
  }

  @PostMapping("/saveOrUpdate")
  public R<String> saveOrUpdate(@RequestBody @Validated UserDTO userDTO) {
    userService.saveOrUpdate(userDTO);
    if (StringUtils.hasText(userDTO.getUserId())) {
      return R.success();
    }
    return R.success(ResponseCode.SUCCESS, "操作成功，初始密码为：" + defaultPassword, null, null);
  }
}
