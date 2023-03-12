package org.springblade.modules.shijiebei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.tool.api.R;
import org.springblade.modules.shijiebei.entity.PlatformRules;
import org.springblade.modules.shijiebei.mapper.PlatformRulesMapper;
import org.springblade.modules.shijiebei.service.PlatformRulesService;
import org.springframework.stereotype.Service;

/**
 * @author crush
 * @description 针对表【platform_rules(平台规则)】的数据库操作Service实现
 * @createDate 2023-02-21 09:51:16
 */
@Service
public class PlatformRulesServiceImpl extends ServiceImpl<PlatformRulesMapper, PlatformRules>
	implements PlatformRulesService {

	@Override
	public R<PlatformRules> select(PlatformRules platformRules) {
		return R.data(baseMapper.selectOne(
			new LambdaQueryWrapper<PlatformRules>().setEntity(platformRules)));
	}
}




