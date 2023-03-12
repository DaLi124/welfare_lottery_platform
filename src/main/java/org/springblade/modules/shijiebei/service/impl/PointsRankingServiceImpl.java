package org.springblade.modules.shijiebei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.core.tool.api.R;
import org.springblade.modules.shijiebei.entity.PointsRanking;
import org.springblade.modules.shijiebei.mapper.PointsRankingMapper;
import org.springblade.modules.shijiebei.service.PointsRankingService;
import org.springframework.stereotype.Service;

/**
 * @description 针对表【points_ranking(球队积分排名)】的数据库操作Service实现
 */
@Service
public class PointsRankingServiceImpl extends ServiceImpl<PointsRankingMapper, PointsRanking>
	implements PointsRankingService {

	@Override
	public R<Page<PointsRanking>> getAllPointsRanking(PointsRanking pointsRanking,
		Page<PointsRanking> pointsRankingPage) {
		return R.data(page(pointsRankingPage,
			new LambdaQueryWrapper<PointsRanking>().setEntity(pointsRanking)));
	}

	@Override
	public R<PointsRanking> getPointsRankingById(Long id) {
		return R.data(getById(id));
	}

	@Override
	public R<PointsRanking> createPointsRanking(PointsRanking pointsRanking) {
		save(pointsRanking);
		return R.data(pointsRanking);
	}

	@Override
	public R<PointsRanking> updatePointsRanking(PointsRanking pointsRanking) {
		updateById(pointsRanking);
		return R.data(pointsRanking);
	}

	@Override
	public R<String> deletePointsRanking(Long id) {
		boolean b = removeById(id);
		return R.data(Boolean.toString(b), "删除成功！");
	}
}




