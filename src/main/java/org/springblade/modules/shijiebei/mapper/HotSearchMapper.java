package org.springblade.modules.shijiebei.mapper;

import org.springblade.modules.shijiebei.entity.HotSearch;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 针对表【hot_search(热搜信息)】的数据库操作Mapper
 */
@Mapper
public interface HotSearchMapper extends BaseMapper<HotSearch> {

    boolean saveOrUpdateByTable(HotSearch hotSearch);
}




