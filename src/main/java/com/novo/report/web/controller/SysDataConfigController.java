package com.novo.report.web.controller;

import static com.novo.report.common.constant.Const.SERVER_API_PATH;

import com.novo.report.common.constant.R;
import com.novo.report.service.SysDataConfigService;
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
