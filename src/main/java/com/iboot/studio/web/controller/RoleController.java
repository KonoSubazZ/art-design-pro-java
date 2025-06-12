package com.iboot.studio.web.controller;

import static com.iboot.studio.common.constant.Const.SERVER_API_PATH;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iboot.studio.common.constant.R;
import com.iboot.studio.infrastructure.persistence.entity.Role;
import com.iboot.studio.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(SERVER_API_PATH + "/role")
public class RoleController {
	private final RoleService roleService;
	@GetMapping("/page")
	public R<Page<Role>> getRolePageList() {
		Page<Role> page = roleService.page(new Page<>());
		return R.success(page);
	}
}
