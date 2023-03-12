package org.springblade.modules.shijiebei.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.modules.shijiebei.entity.HotSearch;
import org.springblade.modules.shijiebei.service.HotSearchService;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 热搜相关接口
 */
@Slf4j
@RestController
@RequestMapping("/hotSearch")
@AllArgsConstructor
public class HotSearchController {

    private final HotSearchService hotSearchService;

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
        Page<HotSearch> pageInfo = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<HotSearch> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByDesc(HotSearch::getDate);

        //分页查询
        hotSearchService.page(pageInfo, queryWrapper);
        return R.data(pageInfo);
    }

    /**
     * 根据id修改
     *
     * @return 返回信息
     */
    @PostMapping("/update")
    public R<HotSearch> update(@RequestBody HotSearch hotSearch) {
        hotSearchService.updateById(hotSearch);
        return R.data(hotSearch);
    }

    /**
     * 根据id删除
     *
     * @return 返回信息
     */
    @GetMapping("/delete")
    public R<String> delete(String id) {
        hotSearchService.removeById(id);
        return R.success("删除成功");
    }

    /**
     * 新增
     *
     * @return 返回信息
     */
    @PostMapping("/save")
    public R<HotSearch> save(@RequestBody HotSearch hotSearch) {
        hotSearch.setState(0);
        hotSearch.setDate(new Date());
        hotSearchService.save(hotSearch);
        return R.data(hotSearch);
    }


}
