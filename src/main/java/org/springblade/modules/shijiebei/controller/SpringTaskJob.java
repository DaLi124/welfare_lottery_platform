package org.springblade.modules.shijiebei.controller;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springblade.common.utils.HttpClient;
import org.springblade.core.http.HttpRequest;
import org.springblade.modules.shijiebei.entity.HotSearch;
import org.springblade.modules.shijiebei.service.HotSearchService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Component
@AllArgsConstructor
public class SpringTaskJob {

    private final HotSearchService hotSearchService;

    /**
     * 定时任务 每30分钟执行一次 把热搜信息同步到库中
     */
    @Scheduled(cron = "* 0/30 * * * ? ")
    public void getHotSearch() {
        System.out.println("进入");
        // 获取信息地址
        String url = "https://tiyu.baidu.com/api/realtime";
        // 过去信息参数
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("pn", "0");
        stringStringHashMap.put("word", "世界杯");
        // 通过使用工具类调用需要过去信息的地址
        JSONObject jsonObject = HttpClient.doGet(url, stringStringHashMap);
        // 处理返回回来的数据
        List list = JSONObject.parseObject(
                JSONObject.parseObject(jsonObject.get("data").toString()).get("data").toString(),
                List.class);
        // 如果有返回数据
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String o = list.get(i).toString();
                JSONObject jsonObject1 = JSONObject.parseObject(o);
                HotSearch hotSearch = new HotSearch();
                hotSearch.setState(0);
                hotSearch.setHeader(jsonObject1.get("title").toString());
                hotSearch.setUrl(jsonObject1.get("link").toString());
                hotSearchService.saveOrUpdateByTable(hotSearch);
            }
        }
        System.out.println("执行一次");
    }

}
