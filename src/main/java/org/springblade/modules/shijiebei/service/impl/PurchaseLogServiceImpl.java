package org.springblade.modules.shijiebei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Calendar;
import lombok.AllArgsConstructor;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.shijiebei.entity.CombatGains;
import org.springblade.modules.shijiebei.entity.LogOrtunellaVenosa;
import org.springblade.modules.shijiebei.entity.PurchaseLog;
import org.springblade.modules.shijiebei.mapper.PurchaseLogMapper;
import org.springblade.modules.shijiebei.service.CombatGainsService;
import org.springblade.modules.shijiebei.service.IPurchaseLogService;
import org.springblade.modules.shijiebei.service.LogOrtunellaVenosaService;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 购买记录 服务实现类
 *
 * @author BladeX
 * @since 2023-03-12
 */
@Service
@AllArgsConstructor
public class PurchaseLogServiceImpl extends ServiceImpl<PurchaseLogMapper, PurchaseLog> implements
        IPurchaseLogService {

    private final IUserService userService;

    private final CombatGainsService combatGainsService;

    private final LogOrtunellaVenosaService logOrtunellaVenosaService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Object> purchase(PurchaseLog purchaseLog) {
        // 判断用户是否有相应数量金币
        purchaseLog.setUserId(AuthUtil.getUserId());
        User byId = userService.getById(AuthUtil.getUserId());
        if (purchaseLog.getUserFortunellaVenosa() <= 0) {
            return R.fail("购买金额需要大于0个金币。");
        }
        if (purchaseLog.getUserFortunellaVenosa() > byId.getUserFortunellaVenosa()) {
            return R.fail("金币不足，请充值！");
        }
        // 添加购买记录
        purchaseLog.setPurchaseType(0);
        CombatGains byId1 = combatGainsService.getById(purchaseLog.getCombatGainsId());
        if (null == byId1) {
            return R.fail("购买项不存在。");
        }
        BeanUtil.copy(byId1, purchaseLog);
        switch (purchaseLog.getPurchaseType()) {
            case 1:
                purchaseLog.setMultiplyingPowerHost(byId1.getMultiplyingPowerHost());
                break;
            case 2:
                purchaseLog.setMultiplyingPowerHost(byId1.getMultiplyingPowerVisitor());
                break;
            case 3:
                purchaseLog.setMultiplyingPowerHost(byId1.getMultiplyingPowerFlat());
                break;
        }
        purchaseLog.setCreateTime(Calendar.getInstance().getTime());
        baseMapper.insert(purchaseLog);
        // 扣除相应金币
        byId.setUserFortunellaVenosa(
                byId.getUserFortunellaVenosa() - purchaseLog.getUserFortunellaVenosa());
        userService.updateById(byId);
        // 添加金币日志
        LogOrtunellaVenosa logOrtunellaVenosa = new LogOrtunellaVenosa();
        logOrtunellaVenosa.setState(0);
        logOrtunellaVenosa.setUserId(AuthUtil.getUserId());
        logOrtunellaVenosa.setUserOperation(AuthUtil.getUserId());
        logOrtunellaVenosa.setNumber(-purchaseLog.getUserFortunellaVenosa());
        logOrtunellaVenosaService.save(logOrtunellaVenosa);
        return R.success("购买成功！");
    }
}
