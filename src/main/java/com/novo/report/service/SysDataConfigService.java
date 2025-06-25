package com.novo.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.novo.report.infrastructure.persistence.entity.SysDataConfig;

public interface SysDataConfigService extends IService<SysDataConfig> {
	Object getPublicData(String dataKey);
}
