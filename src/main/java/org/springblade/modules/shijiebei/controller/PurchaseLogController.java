/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.modules.shijiebei.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.shijiebei.entity.PurchaseLog;
import org.springblade.modules.shijiebei.vo.PurchaseLogVO;
import org.springblade.modules.shijiebei.service.IPurchaseLogService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 购买记录 控制器
 *
 * @author BladeX
 * @since 2023-03-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("blade-sjb/purchaselog")
@Api(value = "购买记录", tags = "购买记录接口")
public class PurchaseLogController extends BladeController {

	private final IPurchaseLogService purchaseLogService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入purchaseLog")
	public R<PurchaseLog> detail(PurchaseLog purchaseLog) {
		PurchaseLog detail = purchaseLogService.getOne(Condition.getQueryWrapper(purchaseLog));
		return R.data(detail);
	}

	/**
	 * 分页 购买记录
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入purchaseLog")
	public R<IPage<PurchaseLog>> list(PurchaseLog purchaseLog, Query query) {
		IPage<PurchaseLog> pages = purchaseLogService.page(Condition.getPage(query), Condition.getQueryWrapper(purchaseLog));
		return R.data(pages);
	}

	/**
	 * 新增 购买记录
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入purchaseLog")
	public R save(@Valid @RequestBody PurchaseLog purchaseLog) {
		return R.status(purchaseLogService.save(purchaseLog));
	}

	/**
	 * 修改 购买记录
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入purchaseLog")
	public R update(@Valid @RequestBody PurchaseLog purchaseLog) {
		return R.status(purchaseLogService.updateById(purchaseLog));
	}

	/**
	 * 新增或修改 购买记录
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入purchaseLog")
	public R submit(@Valid @RequestBody PurchaseLog purchaseLog) {
		return R.status(purchaseLogService.saveOrUpdate(purchaseLog));
	}


	/**
	 * 删除 购买记录
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(purchaseLogService.removeByIds(Func.toLongList(ids)));
	}


}
