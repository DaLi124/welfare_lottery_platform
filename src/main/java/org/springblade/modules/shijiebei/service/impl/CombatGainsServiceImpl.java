package org.springblade.modules.shijiebei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.tool.api.R;
import org.springblade.modules.shijiebei.entity.CombatGains;
import org.springblade.modules.shijiebei.mapper.CombatGainsMapper;
import org.springblade.modules.shijiebei.service.CombatGainsService;
import org.springframework.stereotype.Service;

/**
 * @author crush
 * @description 针对表【combat_gains(历史战绩)】的数据库操作Service实现
 * @createDate 2023-02-21 10:03:53
 */
@Service
public class CombatGainsServiceImpl extends ServiceImpl<CombatGainsMapper, CombatGains>
	implements CombatGainsService {

	@Override
	public R<CombatGains> select(CombatGains combatGains) {
		return R.data(baseMapper.selectOne(
			new LambdaQueryWrapper<CombatGains>().setEntity(combatGains)));
	}
}




