package org.springblade.modules.shijiebei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 球队积分排名
 *
 * @TableName points_ranking
 */
@Data
@TableName(value = "t_points_ranking")
public class PointsRanking implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 积分类别
     */
    private String category;

    /**
     * 积分
     */
    private String integral;

    /**
     * 进/失
     */
    private String nearLoss;

    /**
     * 胜/平/负
     */
    private String winDrawLose;

    /**
     * 球队
     */
    private String team;

    /**
     * 球队旗帜地址
     */
    private String url;

    /**
     * 顺序
     */
    private String sequence;

    /**
     * 组别
     */
    @TableField(value = "groups_m")
    private String groups;

    /**
     *  组别冗余
     */
    private String groupsRedundance;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
