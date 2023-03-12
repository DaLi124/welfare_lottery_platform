package org.springblade.modules.shijiebei.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.modules.shijiebei.entity.LogOrtunellaVenosa;
import org.springblade.modules.shijiebei.service.LogOrtunellaVenosaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 金豆日志相关接口
 */
@Slf4j
@RestController
@RequestMapping("/log_ortunella_venosa")
@AllArgsConstructor
public class LogOrtunellaVenosaController {

    private final LogOrtunellaVenosaService logOrtunellaVenosaService;

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
        Page<LogOrtunellaVenosa> pageInfo = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<LogOrtunellaVenosa> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LogOrtunellaVenosa::getState, 0);
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByDesc(LogOrtunellaVenosa::getId);

        //分页查询
        logOrtunellaVenosaService.page(pageInfo, queryWrapper);
        return R.data(pageInfo);
    }

    /**
     * 查询金豆日志
     *
     * @param logOrtunellaVenosa 条件
     * @return 返回结果
     */
    @GetMapping("select")
    public R<LogOrtunellaVenosa> select(LogOrtunellaVenosa logOrtunellaVenosa) {
        logOrtunellaVenosa.setState(0);
        return logOrtunellaVenosaService.select(logOrtunellaVenosa);
    }

    /**
     * 根据id修改
     *
     * @return 返回信息
     */
    @PostMapping("/update")
    public R<LogOrtunellaVenosa> update(@RequestBody LogOrtunellaVenosa logOrtunellaVenosa) {
        logOrtunellaVenosaService.updateById(logOrtunellaVenosa);
        return R.data(logOrtunellaVenosa);
    }

    /**
     * 根据id删除
     *
     * @return 返回信息
     */
    @GetMapping("/delete")
    public R<String> delete(String id) {
        logOrtunellaVenosaService.removeById(id);
        return R.success("删除成功");
    }

    /**
     * 新增
     *
     * @return 返回信息
     */
    @PostMapping("/save")
    public R<LogOrtunellaVenosa> save(@RequestBody LogOrtunellaVenosa logOrtunellaVenosa) {
        logOrtunellaVenosa.setState(0);
        logOrtunellaVenosaService.save(logOrtunellaVenosa);
        return R.data(logOrtunellaVenosa);
    }

	/**
	 * 充值
	 *
	 * @return 返回信息
	 */
	@PostMapping("/recharge")
	public R<Object> recharge(@RequestBody LogOrtunellaVenosa logOrtunellaVenosa) {
		return logOrtunellaVenosaService.recharge(logOrtunellaVenosa);
	}

}
