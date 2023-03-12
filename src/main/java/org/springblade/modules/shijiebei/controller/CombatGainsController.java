package org.springblade.modules.shijiebei.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.api.R;
import org.springblade.modules.shijiebei.entity.CombatGains;
import org.springblade.modules.shijiebei.entity.PurchaseLog;
import org.springblade.modules.shijiebei.service.CombatGainsService;
import org.springblade.modules.shijiebei.service.IPurchaseLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 历史战绩接口
 */
@Slf4j
@RestController
@RequestMapping("/combatGains")
@AllArgsConstructor
public class CombatGainsController {

	private final CombatGainsService combatGainsService;

	private final IPurchaseLogService purchaseLogService;

	/**
	 * 分页查询
	 *
	 * @param page     页数
	 * @param pageSize 每页数量
	 * @return 返回信息
	 */
	@GetMapping("/page")
	public R<Page> page(int page, int pageSize) {
		//分页构造器
		Page<CombatGains> pageInfo = new Page<>(page, pageSize);
		//条件构造器
		LambdaQueryWrapper<CombatGains> queryWrapper = new LambdaQueryWrapper<>();
		//添加排序条件，根据sort进行排序
		queryWrapper.orderByDesc(CombatGains::getId);

		//分页查询
		combatGainsService.page(pageInfo, queryWrapper);
		return R.data(pageInfo);
	}

	/**
	 * 查询历史战绩
	 *
	 * @param combatGains 条件
	 * @return 返回结果
	 */
	@GetMapping("select")
	public R<CombatGains> select(CombatGains combatGains) {
		combatGains.setState(0);
		return combatGainsService.select(combatGains);
	}

	/**
	 * 根据id修改
	 *
	 * @return 返回信息
	 */
	@PostMapping("/update")
	public R<CombatGains> update(@RequestBody CombatGains combatGains) {
		combatGainsService.updateById(combatGains);
		return R.data(combatGains);
	}

	/**
	 * 根据id删除
	 *
	 * @return 返回信息
	 */
	@GetMapping("/delete")
	public R<String> delete(String id) {
		combatGainsService.removeById(id);
		return R.success("删除成功");
	}

	/**
	 * 新增
	 *
	 * @return 返回信息
	 */
	@PostMapping("/save")
	public R<CombatGains> save(@RequestBody CombatGains combatGains) {
		combatGains.setState(0);
		combatGainsService.save(combatGains);
		return R.data(combatGains);
	}

	/**
	 * 购买
	 *
	 * @return 返回信息
	 */
	@PostMapping("/purchase")
	public R<Object> purchase(@RequestBody PurchaseLog purchaseLog) {
		return purchaseLogService.purchase(purchaseLog);
	}


}
