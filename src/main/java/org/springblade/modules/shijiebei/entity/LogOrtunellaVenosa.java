package org.springblade.modules.shijiebei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 金豆操作日志
 * @TableName log_ortunella_venosa
 */
@Data
@TableName(value ="t_log_ortunella_venosa")
public class LogOrtunellaVenosa implements Serializable {
    /**
     * 金豆记录id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 操作数量
     */
    private Double number;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 操作人id
     */
    private Long userOperation;

    /**
     * 创建时间
     */
    @JsonFilter("yyyy-MM-dd")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
