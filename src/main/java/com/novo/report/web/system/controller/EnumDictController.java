package com.novo.report.web.system.controller;

import static com.novo.report.common.constant.Const.SERVER_API_PATH;

import cn.hutool.core.util.StrUtil;
import com.novo.report.common.constant.R;
import com.novo.report.common.enumdict.EnumDictCollector;
import java.util.List;
import java.util.Objects;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SERVER_API_PATH + "/enums")
public class EnumDictController {
  private final EnumDictCollector enumDictCollector;

  public EnumDictController(EnumDictCollector enumDictCollector) {
    this.enumDictCollector = enumDictCollector;
  }

  @GetMapping("/options")
  public R<List<EnumDictCollector.EnumDictInfo>> getEnumDicts(
      @RequestParam(value = "name", required = false) String name) {
    List<EnumDictCollector.EnumDictInfo> allEnumDicts = enumDictCollector.getAllEnumDicts();
    if (StrUtil.isEmpty(name)) {
      return R.success(allEnumDicts);
    }
    return R.success(allEnumDicts.stream().filter(e -> Objects.equals(e.getName(), name)).toList());
  }
}
