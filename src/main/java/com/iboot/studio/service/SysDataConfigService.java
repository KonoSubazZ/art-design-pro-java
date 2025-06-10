package com.iboot.studio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iboot.studio.infrastructure.persistence.entity.SysDataConfig;

public interface SysDataConfigService extends IService<SysDataConfig> {
	Object getPublicData(String dataKey);
}
