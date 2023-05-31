package org.springblade.modules.shijiebei.controller;

import java.util.Map;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.api.R;
import org.springblade.modules.shijiebei.service.DataAnalysisService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据分析接口
 */
@Slf4j
@RestController
@RequestMapping("/DataAnalysis")
@AllArgsConstructor
public class DataAnalysisController {

    private final DataAnalysisService dataAnalysisService;

    /**
     * 购买人次排名
     */
    @PostMapping("/ReachRank")
    public R<List<Map<String, String>>> reachRank() {
        return dataAnalysisService.reachRank();
    }

    /**
     * 当天球队购买次排名
     */
    @PostMapping("/PurchaseRanking")
    public R<List<Map<String, String>>> purchaseRanking() {
        return dataAnalysisService.purchaseRanking();
    }

    /**
     * 当天球队购买金豆排名
     */
    @PostMapping("/GoldenBeanRanking")
    public R<List<Map<String, String>>> goldenBeanRanking() {
        return dataAnalysisService.goldenBeanRanking();
    }

    /**
     * 当天进出账户总金豆
     */
    @PostMapping("/IncomingAndOutgoingAccounts")
    public R<List<Map<String, String>>> incomingAndOutgoingAccounts() {
        return dataAnalysisService.incomingAndOutgoingAccounts();
    }

}
