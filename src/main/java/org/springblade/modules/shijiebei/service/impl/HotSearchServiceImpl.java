package org.springblade.modules.shijiebei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.shijiebei.entity.HotSearch;
import org.springblade.modules.shijiebei.entity.PlatformRules;
import org.springblade.modules.shijiebei.mapper.HotSearchMapper;
import org.springblade.modules.shijiebei.service.HotSearchService;
import org.springblade.core.tool.api.R;
import org.springframework.stereotype.Service;

/**
 * @description 针对表【hot_search(热搜信息)】的数据库操作Service实现
 */
@Service
public class HotSearchServiceImpl extends ServiceImpl<HotSearchMapper, HotSearch>
	implements HotSearchService {

	@Override
	public R<PlatformRules> select() {
		return null;
	}

	@Override
	public boolean saveOrUpdateByTable(HotSearch hotSearch) {
		return baseMapper.saveOrUpdateByTable(hotSearch);
	}
}




