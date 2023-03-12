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
package org.springblade.modules.shijiebei.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.function.BiConsumer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 购买记录实体类
 *
 * @author BladeX
 * @since 2023-03-12
 */
@Data
@TableName("t_purchase_log")
@ApiModel(value = "PurchaseLog对象", description = "购买记录")
public class PurchaseLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id")
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Long userId;
	/**
	 * 球队_主
	 */
	@ApiModelProperty(value = "球队_主")
	private String teamMain;
	/**
	 * 球队旗帜地址_主
	 */
	@ApiModelProperty(value = "球队旗帜地址_主")
	private String urlMain;
	/**
	 * 球队_客
	 */
	@ApiModelProperty(value = "球队_客")
	private String teamGuest;
	/**
	 * 球队旗帜地址_客
	 */
	@ApiModelProperty(value = "球队旗帜地址_客")
	private String urlGuest;
	/**
	 * 结果主
	 */
	@ApiModelProperty(value = "结果主")
	private String resultMain;
	/**
	 * 结果客
	 */
	@ApiModelProperty(value = "结果客")
	private String resultGuest;
	/**
	 * 比赛类别
	 */
	@ApiModelProperty(value = "比赛类别")
	private String type;
	/**
	 * 比赛时间
	 */
	@ApiModelProperty(value = "比赛时间")
	private LocalDateTime gameDate;
	/**
	 * 购买赔率
	 */
	@ApiModelProperty(value = "购买赔率")
	private BigDecimal multiplyingPowerHost;
	/**
	 * 购买类别
	 */
	@ApiModelProperty(value = "购买类别")
	private Integer purchaseType;
	/**
	 * 比赛结果类别
	 */
	@ApiModelProperty(value = "比赛结果类别")
	private Integer resultType;
	/**
	 * 购买金豆数量
	 */
	@ApiModelProperty(value = "购买金豆数量")
	private Integer userFortunellaVenosa;
	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private Boolean state;

	@TableField(exist = false)
	private Integer combatGainsId;


}
