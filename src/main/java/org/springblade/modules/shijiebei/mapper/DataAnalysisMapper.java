package org.springblade.modules.shijiebei.mapper;

import java.util.Map;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataAnalysisMapper {

    List<Map<String, String>> reachRank();

    List<Map<String, String>> purchaseRanking();

    List<Map<String, String>> goldenBeanRanking();

    List<Map<String, String>> incomingAndOutgoingAccounts();
}
