package org.springblade.modules.shijiebei.service;

import org.springblade.core.tool.api.R;
import org.springblade.modules.shijiebei.entity.LogOrtunellaVenosa;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.shijiebei.entity.PlatformRules;

/**
* @author crush
* @description 针对表【log_ortunella_venosa(金豆操作日志)】的数据库操作Service
* @createDate 2023-03-02 14:59:17
*/
public interface LogOrtunellaVenosaService extends IService<LogOrtunellaVenosa> {

    R<LogOrtunellaVenosa> select(LogOrtunellaVenosa logOrtunellaVenosa);

	R<Object> recharge(LogOrtunellaVenosa logOrtunellaVenosa);
}
