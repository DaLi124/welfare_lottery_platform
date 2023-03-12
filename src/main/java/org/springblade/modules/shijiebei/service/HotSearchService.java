package org.springblade.modules.shijiebei.service;

import org.springblade.core.tool.api.R;
import org.springblade.modules.shijiebei.entity.HotSearch;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.shijiebei.entity.PlatformRules;

/**
 * @description 针对表【hot_search(热搜信息)】的数据库操作Service
 */
public interface HotSearchService extends IService<HotSearch> {

    R<PlatformRules> select();

    boolean saveOrUpdateByTable(HotSearch hotSearch);

}
