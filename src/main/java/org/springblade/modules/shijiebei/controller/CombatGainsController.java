package org.springblade.modules.shijiebei.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.shijiebei.entity.CombatGains;
import org.springblade.modules.shijiebei.entity.LogOrtunellaVenosa;
import org.springblade.modules.shijiebei.entity.PointsRanking;
import org.springblade.modules.shijiebei.entity.PurchaseLog;
import org.springblade.modules.shijiebei.service.CombatGainsService;
import org.springblade.modules.shijiebei.service.IPurchaseLogService;
import org.springblade.modules.shijiebei.service.LogOrtunellaVenosaService;
import org.springblade.modules.shijiebei.service.PointsRankingService;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.service.IUserService;
import org.springframework.transaction.annotation.Transactional;
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

    private final LogOrtunellaVenosaService logOrtunellaVenosaService;

    private final PointsRankingService pointsRankingService;

    private final IUserService userService;

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
        queryWrapper.orderByAsc(CombatGains::getId);

        //分页查询
        combatGainsService.page(pageInfo, queryWrapper);
        return R.data(pageInfo);
    }

    /**
     * 分页查询
     *
     * @param page     页数
     * @param pageSize 每页数量
     * @return 返回信息
     */
    @GetMapping("/page-yhd")
    public R<Page> pageYhd(int page, int pageSize) {
        //分页构造器
        Page<CombatGains> pageInfo = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<CombatGains> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByAsc(CombatGains::getId);
        // 添加显示今天数据条件
        //调用的代码

        Date start = DateUtil.parse(
                DateUtil.format(DateUtil.now(), DateUtil.PATTERN_DATE) + " 00:00:00",
                DateUtil.PATTERN_DATETIME);
        Date end = DateUtil.parse(
                DateUtil.format(DateUtil.now(), DateUtil.PATTERN_DATE) + " 23:59:59",
                DateUtil.PATTERN_DATETIME);
        queryWrapper.ge(CombatGains::getGameDate, start);
        queryWrapper.lt(CombatGains::getGameDate, end);

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
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/update")
    public R<CombatGains> update(@RequestBody CombatGains combatGains) {
        // 判断比赛结果是否出来了
        if (StringUtil.isNoneBlank(combatGains.getResultGuest())
                && StringUtil.isNoneBlank(combatGains.getResultMain())) {
            List<PurchaseLog> list = purchaseLogService.list(
                    new LambdaQueryWrapper<PurchaseLog>()
                            .eq(PurchaseLog::getTeamMain, combatGains.getTeamMain())
                            .eq(PurchaseLog::getTeamGuest, combatGains.getTeamGuest())
                            .eq(PurchaseLog::getResultType, "-1"));

            // 如果主队赢
            if (Integer.valueOf(combatGains.getResultMain()) > Integer.valueOf(
                    combatGains.getResultGuest())) {
                if (CollectionUtils.isNotEmpty(list)) {
                    list.forEach(purchaseLog -> {
                        if (Integer.valueOf("1").equals(purchaseLog.getPurchaseType())) {
                            // 修改购买记录，给用户添加金豆
                            upPurchaseLog(purchaseLog, combatGains, 1);
                        } else {
                            purchaseLog.setResultType(2);
                            purchaseLog.setResultMain(combatGains.getResultMain());
                            purchaseLog.setResultGuest(combatGains.getResultGuest());
                            purchaseLogService.updateById(purchaseLog);
                        }
                    });
                }
                if (StringUtil.equals(combatGains.getType().substring(0, 3), "小组赛")) {
                    // 修改球队的积分
                    PointsRanking one = pointsRankingService.getOne(
                            new LambdaQueryWrapper<PointsRanking>()
                                    .eq(PointsRanking::getGroupsRedundance,
                                            combatGains.getType().substring(0, 5))
                                    .eq(PointsRanking::getTeam,
                                            combatGains.getTeamMain()));
                    one.setIntegral(String.valueOf(Integer.valueOf(
                            StringUtils.isBlank(one.getIntegral()) ? "0" : one.getIntegral()) + 3));

                            /*one.setNearLoss();
                            one.setWinDrawLose();*/
                    pointsRankingService.updateById(one);

                    PointsRanking tow = pointsRankingService.getOne(
                            new LambdaQueryWrapper<PointsRanking>()
                                    .eq(PointsRanking::getGroupsRedundance,
                                            combatGains.getType().substring(0, 5))
                                    .eq(PointsRanking::getTeam,
                                            combatGains.getTeamGuest()));

                    jinShi(one, combatGains.getResultMain(), combatGains.getResultGuest(), "1",
                            "1");
                    jinShi(tow, combatGains.getResultMain(), combatGains.getResultGuest(), "3",
                            "1");
                }
            }
            // 如果客队赢
            if (Integer.valueOf(combatGains.getResultMain()) < Integer.valueOf(
                    combatGains.getResultGuest())) {
                if (CollectionUtils.isNotEmpty(list)) {
                    list.forEach(purchaseLog -> {
                        if (Integer.valueOf("2").equals(purchaseLog.getPurchaseType())) {
                            // 修改购买记录，给用户添加金豆
                            upPurchaseLog(purchaseLog, combatGains, 1);
                        } else {
                            purchaseLog.setResultType(2);
                            purchaseLog.setResultMain(combatGains.getResultMain());
                            purchaseLog.setResultGuest(combatGains.getResultGuest());
                            purchaseLogService.updateById(purchaseLog);
                        }
                    });
                }

                if (StringUtil.equals(combatGains.getType().substring(0, 3), "小组赛")) {
                    // 修改球队的积分
                    PointsRanking one = pointsRankingService.getOne(
                            new LambdaQueryWrapper<PointsRanking>()
                                    .eq(PointsRanking::getGroupsRedundance,
                                            combatGains.getType().substring(0, 5))
                                    .eq(PointsRanking::getTeam,
                                            combatGains.getTeamGuest()));
                    one.setIntegral(String.valueOf(Integer.valueOf(
                            StringUtils.isBlank(one.getIntegral()) ? "0" : one.getIntegral()) + 3));

                            /*one.setNearLoss();
                            one.setWinDrawLose();*/
                    pointsRankingService.updateById(one);

                    PointsRanking tow = pointsRankingService.getOne(
                            new LambdaQueryWrapper<PointsRanking>()
                                    .eq(PointsRanking::getGroupsRedundance,
                                            combatGains.getType().substring(0, 5))
                                    .eq(PointsRanking::getTeam,
                                            combatGains.getTeamMain()));
                    jinShi(one, combatGains.getResultMain(), combatGains.getResultGuest(), "1",
                            "2");
                    jinShi(tow, combatGains.getResultMain(), combatGains.getResultGuest(), "3",
                            "2");
                }
            }
            // 如果平
            if (StringUtil.equals(combatGains.getResultMain(), combatGains.getResultGuest())) {
                if (CollectionUtils.isNotEmpty(list)) {
                    list.forEach(purchaseLog -> {
                        if (Integer.valueOf("3").equals(purchaseLog.getPurchaseType())) {
                            // 修改购买记录，给用户添加金豆
                            upPurchaseLog(purchaseLog, combatGains, 1);
                        } else {
                            purchaseLog.setResultType(2);
                            purchaseLog.setResultMain(combatGains.getResultMain());
                            purchaseLog.setResultGuest(combatGains.getResultGuest());
                            purchaseLogService.updateById(purchaseLog);
                        }
                    });
                }
                if (StringUtil.equals(combatGains.getType().substring(0, 3), "小组赛")) {
                    // 修改球队的积分
                    PointsRanking one = pointsRankingService.getOne(
                            new LambdaQueryWrapper<PointsRanking>()
                                    .eq(PointsRanking::getGroupsRedundance,
                                            combatGains.getType().substring(0, 5))
                                    .eq(PointsRanking::getTeam,
                                            combatGains.getTeamGuest()));
                    one.setIntegral(String.valueOf(Integer.valueOf(
                            StringUtils.isBlank(one.getIntegral()) ? "0" : one.getIntegral()) + 1));

                    PointsRanking tow = pointsRankingService.getOne(
                            new LambdaQueryWrapper<PointsRanking>()
                                    .eq(PointsRanking::getGroupsRedundance,
                                            combatGains.getType().substring(0, 5))
                                    .eq(PointsRanking::getTeam,
                                            combatGains.getTeamMain()));
                    tow.setIntegral(String.valueOf(Integer.valueOf(
                            StringUtils.isBlank(tow.getIntegral()) ? "0" : tow.getIntegral()) + 1));
                    pointsRankingService.updateById(one);
                    pointsRankingService.updateById(tow);

                    jinShi(one, combatGains.getResultMain(), combatGains.getResultGuest(), "2",
                            "3");
                    jinShi(tow, combatGains.getResultMain(), combatGains.getResultGuest(), "2",
                            "3");
                }
            }
        }
        combatGainsService.updateById(combatGains);
        return R.data(combatGains);
    }

    public void jinShi(PointsRanking pointsRanking, String resultMain, String resultGuest,
            String type, String type2) {
        String s1 = StringUtils.isBlank(pointsRanking.getNearLoss()) ? "0/0"
                : pointsRanking.getNearLoss();

        String[] split1 = s1.split("/");

        String s2 = StringUtils.isBlank(pointsRanking.getWinDrawLose()) ? "0/0/0"
                : pointsRanking.getWinDrawLose();

        String[] split2 = s2.split("/");
        switch (type) {
            // 胜
            case "1":
                switch (type2) {
                    case "1":
                        split1[0] = Integer.valueOf(split1[0]) + Integer.valueOf(resultMain) + "";
                        split1[1] = Integer.valueOf(split1[1]) + Integer.valueOf(resultGuest) + "";

                        split2[0] = Integer.valueOf(split2[0]) + 1 + "";
                        break;
                    case "2":
                        split1[0] = Integer.valueOf(split1[0]) + Integer.valueOf(resultGuest) + "";
                        split1[1] = Integer.valueOf(split1[1]) + Integer.valueOf(resultMain) + "";

                        split2[0] = Integer.valueOf(split2[0]) + 1 + "";
                        break;
                }
                break;
            // 平
            case "2":
                split1[0] = Integer.valueOf(split1[0]) + Integer.valueOf(resultMain) + "";
                split1[1] = Integer.valueOf(split1[1]) + Integer.valueOf(resultGuest) + "";

                split2[1] = Integer.valueOf(split2[1]) + 1 + "";
                break;
            // 负
            case "3":
                switch (type2) {
                    case "1":
                        split1[0] = Integer.valueOf(split1[0]) + Integer.valueOf(resultGuest) + "";
                        split1[1] = Integer.valueOf(split1[1]) + Integer.valueOf(resultMain) + "";

                        split2[2] = Integer.valueOf(split2[2]) + 1 + "";
                        break;
                    case "2":
                        split1[0] = Integer.valueOf(split1[0]) + Integer.valueOf(resultMain) + "";
                        split1[1] = Integer.valueOf(split1[1]) + Integer.valueOf(resultGuest) + "";

                        split2[2] = Integer.valueOf(split2[2]) + 1 + "";
                        break;
                }
                break;
        }
        pointsRanking.setNearLoss(String.join("/", split1));
        pointsRanking.setWinDrawLose(String.join("/", split2));
        pointsRankingService.updateById(pointsRanking);
    }

    public void upPurchaseLog(PurchaseLog purchaseLog, CombatGains combatGains, Integer type) {
        // 比赛结果类型改为1
        purchaseLog.setResultType(type);
        purchaseLog.setResultMain(combatGains.getResultMain());
        purchaseLog.setResultGuest(combatGains.getResultGuest());
        purchaseLogService.updateById(purchaseLog);

        BigDecimal subtract = BigDecimal.valueOf(
                        purchaseLog.getUserFortunellaVenosa())
                .multiply(purchaseLog.getMultiplyingPowerHost());
        LogOrtunellaVenosa logOrtunellaVenosa = new LogOrtunellaVenosa();
        logOrtunellaVenosa.setState(0);
        logOrtunellaVenosa.setNumber(subtract.setScale(2, RoundingMode.HALF_UP).doubleValue());
        logOrtunellaVenosa.setUserId(purchaseLog.getUserId());
        logOrtunellaVenosa.setUserOperation(AuthUtil.getUserId());
        logOrtunellaVenosa.setCreateTime(DateUtil.now());
        logOrtunellaVenosaService.save(logOrtunellaVenosa);

        User byId = userService.getById(AuthUtil.getUserId());
        byId.setUserFortunellaVenosa(
                BigDecimal.valueOf(
                                Optional.ofNullable(byId.getUserFortunellaVenosa()).orElse((double) 0))
                        .add(subtract).doubleValue()
        );
        userService.updateById(byId);
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
