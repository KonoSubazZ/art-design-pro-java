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

package com.novo.report.web.system.controller;

import static com.novo.report.common.constant.Const.SERVER_API_PATH;

import cn.dev33.satoken.stp.StpUtil;
import com.novo.report.common.constant.R;
import com.novo.report.service.system.AuthService;
import com.novo.report.web.system.dto.LoginDTO;
import com.novo.report.web.system.vo.UserMenu;
import com.novo.report.web.system.vo.UserTokenInfo;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SERVER_API_PATH +"/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public R<UserTokenInfo> login(@Validated @RequestBody LoginDTO loginDTO) {
    UserTokenInfo userTokenInfo = authService.login(loginDTO);
    return R.success(userTokenInfo);
  }

  @PostMapping("/logout")
  public R<Void> logout() {
    authService.logout();
    return R.success();
  }

  @GetMapping("/getUserMenu")
  public R<List<UserMenu>> getUserMenu() {
    List<UserMenu> userMenus = authService.getUserMenus(StpUtil.getLoginId().toString());
    return R.success(userMenus);
  }
}
