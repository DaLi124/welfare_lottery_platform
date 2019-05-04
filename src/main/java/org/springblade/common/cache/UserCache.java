/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.common.cache;

import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.service.IUserService;

import static org.springblade.core.launch.constant.FlowConstant.TASK_USR_PREFIX;

/**
 * 系统缓存
 *
 * @author Chill
 */
public class UserCache {
	private static final String USER_CACHE = "blade:user";
	private static final String USER_CACHE_ID_ = "user:id";


	private static IUserService userService;

	static {
		userService = SpringUtil.getBean(IUserService.class);
	}

	/**
	 * 根据任务用户id获取用户信息
	 *
	 * @param taskUserId 任务用户id
	 * @return
	 */
	public static User getUserByTaskUser(String taskUserId) {
		int userId = Func.toInt(StringUtil.removePrefix(taskUserId, TASK_USR_PREFIX));
		return getUser(userId);
	}

	/**
	 * 获取用户名
	 *
	 * @param userId 用户id
	 * @return
	 */
	public static User getUser(Integer userId) {
		User user = CacheUtil.get(USER_CACHE, USER_CACHE_ID_ + userId, User.class);
		if (Func.isEmpty(user)) {
			User u = userService.getById(userId);
			if (Func.isNotEmpty(u)) {
				user = u;
				if (Func.isNotEmpty(user)) {
					CacheUtil.put(USER_CACHE, USER_CACHE_ID_ + userId, user);
				}
			}
		}
		return user;
	}

}