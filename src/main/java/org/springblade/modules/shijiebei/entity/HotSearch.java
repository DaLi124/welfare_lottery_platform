package org.springblade.modules.shijiebei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 热搜信息
 * @TableName hot_search
 */
@Data
@TableName(value ="t_hot_search")
public class HotSearch implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 热搜标题
     */
    private String header;

    /**
     * 热搜地址
     */
    private String url;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 时间
     */
    private Date date;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
