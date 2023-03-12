package org.springblade.modules.shijiebei.service;

import org.springblade.core.tool.api.R;
import org.springblade.modules.shijiebei.entity.CombatGains;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.shijiebei.entity.PlatformRules;

/**
* @author crush
* @description 针对表【combat_gains(历史战绩)】的数据库操作Service
* @createDate 2023-02-21 10:03:53
*/
public interface CombatGainsService extends IService<CombatGains> {

    R<CombatGains> select(CombatGains combatGains);

}
