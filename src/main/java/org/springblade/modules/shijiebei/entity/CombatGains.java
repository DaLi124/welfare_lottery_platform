package org.springblade.modules.shijiebei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 历史战绩
 *
 * @TableName combat_gains
 */
@Data
@TableName(value = "t_combat_gains")
public class CombatGains implements Serializable {

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 状态
	 */
	private Integer state;

	/**
	 * 球队_主
	 */
	private String teamMain;

	/**
	 * 球队旗帜地址_主
	 */
	private String urlMain;

	/**
	 * 球队_客
	 */
	private String teamGuest;

	/**
	 * 球队旗帜地址_客
	 */
	private String urlGuest;

	/**
	 * 结果主
	 */
	private String resultMain;

	/**
	 * 结果客
	 */
	private String resultGuest;

	/**
	 * 比赛类别
	 */
	private String type;

	/**
	 * 比赛时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date gameDate;

	/**
	 * 主胜倍率
	 */
	private BigDecimal multiplyingPowerHost;

	/**
	 * 客胜倍率
	 */
	private BigDecimal multiplyingPowerVisitor;

	/**
	 * 平倍率
	 */
	private BigDecimal multiplyingPowerFlat;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}
