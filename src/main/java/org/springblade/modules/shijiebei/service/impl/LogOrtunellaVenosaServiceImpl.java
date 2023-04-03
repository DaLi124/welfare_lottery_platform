package org.springblade.modules.shijiebei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.modules.shijiebei.entity.LogOrtunellaVenosa;
import org.springblade.modules.shijiebei.mapper.LogOrtunellaVenosaMapper;
import org.springblade.modules.shijiebei.service.LogOrtunellaVenosaService;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author crush
 * @description 针对表【log_ortunella_venosa(金豆操作日志)】的数据库操作Service实现
 * @createDate 2023-03-02 14:59:17
 */
@Service
@AllArgsConstructor
public class LogOrtunellaVenosaServiceImpl extends
        ServiceImpl<LogOrtunellaVenosaMapper, LogOrtunellaVenosa>
        implements LogOrtunellaVenosaService {

    private final IUserService userService;

    @Override
    public R<LogOrtunellaVenosa> select(LogOrtunellaVenosa logOrtunellaVenosa) {
        return R.data(baseMapper.selectOne(
                new LambdaQueryWrapper<LogOrtunellaVenosa>().setEntity(logOrtunellaVenosa)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Object> recharge(LogOrtunellaVenosa logOrtunellaVenosa) {
        // 检查充值数量是否正确
        if (logOrtunellaVenosa.getNumber() <= 0) {
            return R.fail("充值数量要大于0个。");
        }
        // 充值到用户
        User byId = userService.getById(logOrtunellaVenosa.getUserId());
        byId.setUserFortunellaVenosa(
                byId.getUserFortunellaVenosa() + logOrtunellaVenosa.getNumber());
        userService.updateById(byId);
        // 记录日志
        logOrtunellaVenosa.setState(0);
        logOrtunellaVenosa.setUserId(byId.getId());
        logOrtunellaVenosa.setUserOperation(byId.getId());
        logOrtunellaVenosa.setCreateTime(DateUtil.now());
        baseMapper.insert(logOrtunellaVenosa);
        return R.success("充值成功。");
    }

    @Override
    public R<Object> withdrawal(LogOrtunellaVenosa logOrtunellaVenosa) {
        // 检查充值数量是否正确
        if (logOrtunellaVenosa.getNumber() <= 0) {
            return R.fail("提现数量要大于0个。");
        }
        // 提现
        User byId = userService.getById(logOrtunellaVenosa.getUserId());

        if (byId.getUserFortunellaVenosa() < logOrtunellaVenosa.getNumber()) {
            return R.fail("你没有相关数量的金豆。");
        }
        byId.setUserFortunellaVenosa(
                byId.getUserFortunellaVenosa() - logOrtunellaVenosa.getNumber());
        userService.updateById(byId);
        // 记录日志
        logOrtunellaVenosa.setState(0);
        logOrtunellaVenosa.setUserId(byId.getId());
        logOrtunellaVenosa.setNumber(-logOrtunellaVenosa.getNumber());
        logOrtunellaVenosa.setUserOperation(byId.getId());
        logOrtunellaVenosa.setCreateTime(DateUtil.now());
        baseMapper.insert(logOrtunellaVenosa);
        return R.success("提现成功。");

    }
}




