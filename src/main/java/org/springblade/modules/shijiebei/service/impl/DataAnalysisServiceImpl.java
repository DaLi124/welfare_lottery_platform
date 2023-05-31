package org.springblade.modules.shijiebei.service.impl;

import com.alibaba.excel.util.CollectionUtils;
import java.util.Map;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.shijiebei.mapper.DataAnalysisMapper;
import org.springblade.modules.shijiebei.service.DataAnalysisService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DataAnalysisServiceImpl implements DataAnalysisService {

    private final DataAnalysisMapper dataAnalysisMapper;

    @Override
    public R<List<Map<String, String>>> reachRank() {
        List<Map<String, String>> maps = dataAnalysisMapper.reachRank();
        if (CollectionUtils.isEmpty(maps)) {
            return R.fail("数据为空");
        }
        return R.data(maps);
    }

    @Override
    public R<List<Map<String, String>>> purchaseRanking() {
        return R.data(dataAnalysisMapper.purchaseRanking());
    }

    @Override
    public R<List<Map<String, String>>> goldenBeanRanking() {
        return R.data(dataAnalysisMapper.goldenBeanRanking());
    }

    @Override
    public R<List<Map<String, String>>> incomingAndOutgoingAccounts() {
        return R.data(dataAnalysisMapper.incomingAndOutgoingAccounts());
    }
}
