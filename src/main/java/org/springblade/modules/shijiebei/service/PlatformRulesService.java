package org.springblade.modules.shijiebei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.tool.api.R;
import org.springblade.modules.shijiebei.entity.PlatformRules;

/**
 * @author crush
 * @description 针对表【platform_rules(平台规则)】的数据库操作Service
 * @createDate 2023-02-21 09:51:16
 */
public interface PlatformRulesService extends IService<PlatformRules> {

    R<PlatformRules> select(PlatformRules platformRules);
}
