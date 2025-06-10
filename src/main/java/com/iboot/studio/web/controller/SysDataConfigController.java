package com.iboot.studio.web.controller;

import static com.iboot.studio.common.constant.C.SERVER_API_PATH;

import com.iboot.studio.common.constant.R;
import com.iboot.studio.service.SysDataConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(SERVER_API_PATH + "/sys")
public class SysDataConfigController {
	private final SysDataConfigService sysDataConfigService;
  @GetMapping("/public/data-config")
  public R<Object> getPublicData(@RequestParam String dataKey) {
	  Assert.notNull(dataKey, "dataKey不能为空");
	  Object data = sysDataConfigService.getPublicData(dataKey);
    return R.success(data);
  }
}
