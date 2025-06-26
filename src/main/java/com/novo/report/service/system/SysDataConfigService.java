package com.novo.report.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.novo.report.infrastructure.persistence.entity.system.SysDataConfig;

public interface SysDataConfigService extends IService<SysDataConfig> {
	Object getPublicData(String dataKey);
}
