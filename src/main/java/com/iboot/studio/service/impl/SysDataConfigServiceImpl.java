package com.iboot.studio.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iboot.studio.infrastructure.persistence.entity.SysDataConfig;
import com.iboot.studio.infrastructure.persistence.repository.SysDataConfigRepository;
import com.iboot.studio.service.SysDataConfigService;
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
