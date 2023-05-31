package org.springblade.modules.shijiebei.service;

import java.util.Map;
import java.util.List;
import org.springblade.core.tool.api.R;

public interface DataAnalysisService {

    R<List<Map<String, String>>> reachRank();

    R<List<Map<String, String>>> purchaseRanking();

    R<List<Map<String, String>>> goldenBeanRanking();

    R<List<Map<String, String>>> incomingAndOutgoingAccounts();
}
