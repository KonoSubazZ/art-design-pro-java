package com.novo.report.service.system.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.novo.report.infrastructure.persistence.entity.system.SysDataConfig;
import com.novo.report.infrastructure.persistence.repository.system.SysDataConfigRepository;
import com.novo.report.service.system.SysDataConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class SysDataConfigServiceImpl extends ServiceImpl<SysDataConfigRepository, SysDataConfig>
    implements SysDataConfigService {
  private final SysDataConfigRepository sysDataConfigRepository;

  @Override
  public Object getPublicData(String dataKey) {
    SysDataConfig one =
        new LambdaQueryChainWrapper<>(sysDataConfigRepository)
            .eq(SysDataConfig::getDataKey, dataKey)
            .one();
	  Assert.notNull(one, "数据不存在");
		return one.getDataValue();
  }
}
