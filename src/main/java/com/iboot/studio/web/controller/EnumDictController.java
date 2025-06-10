package com.iboot.studio.web.controller;

import cn.hutool.core.util.StrUtil;
import com.iboot.studio.common.enumdict.EnumDictCollector;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.iboot.studio.common.constant.C.SERVER_API_PATH;

@RestController
@RequestMapping(SERVER_API_PATH + "/enums")
public class EnumDictController {
  private final EnumDictCollector enumDictCollector;

  public EnumDictController(EnumDictCollector enumDictCollector) {
    this.enumDictCollector = enumDictCollector;
  }

  @GetMapping("/options")
  public List<EnumDictCollector.EnumDictInfo> getEnumDicts(
      @RequestParam(value = "name", required = false) String name) {
    List<EnumDictCollector.EnumDictInfo> allEnumDicts = enumDictCollector.getAllEnumDicts();
    if (StrUtil.isEmpty(name)) {
      return allEnumDicts;
    }
    return allEnumDicts.stream().filter(e -> Objects.equals(e.getName(), name)).toList();
  }
}
