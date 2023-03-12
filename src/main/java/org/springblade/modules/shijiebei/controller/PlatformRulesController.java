package org.springblade.modules.shijiebei.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.modules.shijiebei.entity.PlatformRules;
import org.springblade.modules.shijiebei.service.PlatformRulesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台规则相关接口
 */
@Slf4j
@RestController
@RequestMapping("/platformRules")
@AllArgsConstructor
public class PlatformRulesController {

    private final PlatformRulesService platformRulesService;

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
        Page<PlatformRules> pageInfo = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<PlatformRules> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.eq(PlatformRules::getState, 0);

        //分页查询
        platformRulesService.page(pageInfo, queryWrapper);
        return R.data(pageInfo);
    }

    /**
     * 查询平台规则
     *
     * @param platformRules 条件
     * @return 返回结果
     */
    @GetMapping("select")
    public R<PlatformRules> select(PlatformRules platformRules) {
        platformRules.setState(0);
        return platformRulesService.select(platformRules);
    }

    /**
     * 根据id修改
     *
     * @return 返回信息
     */
    @PostMapping("/update")
    public R<PlatformRules> update(@RequestBody PlatformRules platformRules) {
        platformRulesService.updateById(platformRules);
        return R.data(platformRules);
    }

    /**
     * 根据id删除
     *
     * @return 返回信息
     */
    @GetMapping("/delete")
    public R<String> delete(String id) {
        platformRulesService.removeById(id);
        return R.success("删除成功");
    }

    /**
     * 新增
     *
     * @return 返回信息
     */
    @PostMapping("/save")
    public R<PlatformRules> save(@RequestBody PlatformRules platformRules) {
        platformRulesService.update(
                new LambdaUpdateWrapper<PlatformRules>().isNotNull(PlatformRules::getId)
                        .set(PlatformRules::getState, 1));
        platformRulesService.save(platformRules);
        return R.data(platformRules);
    }

}
