package org.springblade.modules.shijiebei.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.modules.shijiebei.entity.PointsRanking;
import org.springblade.modules.shijiebei.service.PointsRankingService;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 球队积分排名
 */
@RestController
@AllArgsConstructor
@RequestMapping("/points-ranking")
public class PointsRankingController {

	private final PointsRankingService pointsRankingService;

	/**
	 * 分页查询球队积分排名
	 *
	 * @param pointsRanking 条件
	 * @param page          第几页
	 * @param pageSize      每页条数
	 * @return R
	 */
	@PostMapping("/page")
	public R<Page<PointsRanking>> getAllPointsRanking(
		@RequestBody(required = false) PointsRanking pointsRanking,
		int page, int pageSize) {
		Page<PointsRanking> pointsRankingPage = new Page<>(page, pageSize);
		return pointsRankingService.getAllPointsRanking(pointsRanking, pointsRankingPage);
	}

	/**
	 * 根据数据id查询球队积分排名信息
	 *
	 * @param id 数据id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R<PointsRanking> getPointsRankingById(@PathVariable Long id) {
		return pointsRankingService.getPointsRankingById(id);
	}

	/**
	 * 新建球队积分排名信息
	 *
	 * @param pointsRanking 需要新增的信息
	 * @return R
	 */
	@PostMapping("/create")
	public R<PointsRanking> createPointsRanking(@RequestBody PointsRanking pointsRanking) {
		return pointsRankingService.createPointsRanking(pointsRanking);
	}

	/**
	 * 根据id修改球队积分排名信息
	 *
	 * @param pointsRanking 需要修改的信息
	 * @return R
	 */
	@PostMapping("/update")
	public R<PointsRanking> updatePointsRanking(
		@RequestBody PointsRanking pointsRanking) {
		return pointsRankingService.updatePointsRanking(pointsRanking);
	}

	/**
	 * 根据id删除球队积分排名信息
	 *
	 * @param id 需要删除的数据id
	 * @return R
	 */
	@GetMapping("/delete/{id}")
	public R<String> deletePointsRanking(@PathVariable Long id) {
		return pointsRankingService.deletePointsRanking(id);
	}

}
