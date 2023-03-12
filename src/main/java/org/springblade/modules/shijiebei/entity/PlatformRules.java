package org.springblade.modules.shijiebei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 平台规则
 * @TableName platform_rules
 */
@Data
@TableName(value ="t_platform_rules")
public class PlatformRules implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 平台规则内容
     */
    private String content;

    /**
     * 状态
     */
    private Integer state;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
