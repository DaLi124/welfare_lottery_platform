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
package org.springblade.modules.system.controller;


import static org.springblade.core.cache.constant.CacheConstant.USER_CACHE;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springblade.common.utils.ValidateCodeUtils;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.excel.util.ExcelUtil;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.excel.UserExcel;
import org.springblade.modules.system.excel.UserImporter;
import org.springblade.modules.system.service.IUserService;
import org.springblade.modules.system.vo.UserVO;
import org.springblade.modules.system.wrapper.UserWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 控制器
 *
 * @author Chill
 */
@Slf4j
@NonDS
@ApiIgnore
@RestController
@RequestMapping(AppConstant.APPLICATION_USER_NAME)
@AllArgsConstructor
public class UserController {

	private final IUserService userService;

	/**
	 * 查询单条
	 */
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查看详情", notes = "传入id")
	@GetMapping("/detail")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<UserVO> detail(User user) {
		User detail = userService.getOne(Condition.getQueryWrapper(user));
		return R.data(UserWrapper.build().entityVO(detail));
	}

	/**
	 * 查询单条
	 */
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查看详情", notes = "传入id")
	@GetMapping("/info")
	public R<UserVO> info(BladeUser user) {
		User detail = userService.getById(user.getUserId());
		return R.data(UserWrapper.build().entityVO(detail));
	}

	/**
	 * 用户列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "account", value = "账号名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "realName", value = "姓名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "列表", notes = "传入account和realName")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<IPage<UserVO>> list(@ApiIgnore @RequestParam Map<String, Object> user, Query query,
		BladeUser bladeUser) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
		IPage<User> pages = userService.page(Condition.getPage(query),
			(!bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda()
				.eq(User::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return R.data(UserWrapper.build().pageVO(pages));
	}

	/**
	 * 自定义用户列表
	 */
	@GetMapping("/page")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "account", value = "账号名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "realName", value = "姓名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "列表", notes = "传入account和realName")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<IPage<UserVO>> page(@ApiIgnore User user, Query query, Long deptId,
		BladeUser bladeUser) {
		IPage<User> pages = userService.selectUserPage(Condition.getPage(query), user, deptId,
			(bladeUser.getTenantId().equals(BladeConstant.ADMIN_TENANT_ID) ? StringPool.EMPTY
				: bladeUser.getTenantId()));
		return R.data(UserWrapper.build().pageVO(pages));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入User")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R submit(@Valid @RequestBody User user) {
		CacheUtil.clear(USER_CACHE);
		return R.status(userService.submit(user));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入User")
	public R update(@Valid @RequestBody User user) {
		CacheUtil.clear(USER_CACHE);
		return R.status(userService.updateUser(user));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "删除", notes = "传入id集合")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R remove(@RequestParam String ids) {
		CacheUtil.clear(USER_CACHE);
		return R.status(userService.removeUser(ids));
	}

	/**
	 * 设置菜单权限
	 */
	@PostMapping("/grant")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R grant(@ApiParam(value = "userId集合", required = true) @RequestParam String userIds,
		@ApiParam(value = "roleId集合", required = true) @RequestParam String roleIds) {
		boolean temp = userService.grant(userIds, roleIds);
		return R.status(temp);
	}

	/**
	 * 重置密码
	 */
	@PostMapping("/reset-password")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "初始化密码", notes = "传入userId集合")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R resetPassword(
		@ApiParam(value = "userId集合", required = true) @RequestParam String userIds) {
		boolean temp = userService.resetPassword(userIds);
		return R.status(temp);
	}

	/**
	 * 修改密码
	 */
	@PostMapping("/update-password")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "修改密码", notes = "传入密码")
	public R updatePassword(BladeUser user,
		@ApiParam(value = "旧密码", required = true) @RequestParam String oldPassword,
		@ApiParam(value = "新密码", required = true) @RequestParam String newPassword,
		@ApiParam(value = "新密码", required = true) @RequestParam String newPassword1) {
		boolean temp = userService.updatePassword(user.getUserId(), oldPassword, newPassword,
			newPassword1);
		return R.status(temp);
	}

	/**
	 * 修改基本信息
	 */
	@PostMapping("/update-info")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "修改基本信息", notes = "传入User")
	public R updateInfo(@Valid @RequestBody User user) {
		CacheUtil.clear(USER_CACHE);
		return R.status(userService.updateUserInfo(user));
	}

	/**
	 * 用户列表
	 */
	@GetMapping("/user-list")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "用户列表", notes = "传入user")
	public R<List<User>> userList(User user, BladeUser bladeUser) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user);
		List<User> list = userService.list((!AuthUtil.isAdministrator()) ? queryWrapper.lambda()
			.eq(User::getTenantId, bladeUser.getTenantId()) : queryWrapper);
		return R.data(list);
	}

	/**
	 * 导入用户
	 */
	@PostMapping("import-user")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "导入用户", notes = "传入excel")
	public R importUser(MultipartFile file, Integer isCovered) {
		UserImporter userImporter = new UserImporter(userService, isCovered == 1);
		ExcelUtil.save(file, userImporter, UserExcel.class);
		return R.success("操作成功");
	}

	/**
	 * 导出用户
	 */
	@GetMapping("export-user")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "导出用户", notes = "传入user")
	public void exportUser(@ApiIgnore @RequestParam Map<String, Object> user, BladeUser bladeUser,
		HttpServletResponse response) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
		if (!AuthUtil.isAdministrator()) {
			queryWrapper.lambda().eq(User::getTenantId, bladeUser.getTenantId());
		}
		queryWrapper.lambda().eq(User::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<UserExcel> list = userService.exportUser(queryWrapper);
		ExcelUtil.export(response, "用户数据" + DateUtil.time(), "用户数据表", list,
			UserExcel.class);
	}

	/**
	 * 导出模板
	 */
	@GetMapping("export-template")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "导出模板")
	public void exportUser(HttpServletResponse response) {
		List<UserExcel> list = new ArrayList<>();
		ExcelUtil.export(response, "用户数据模板", "用户数据表", list, UserExcel.class);
	}


	/**
	 * 第三方注册用户
	 */
	@PostMapping("/register-guest")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "第三方注册用户", notes = "传入user")
	public R registerGuest(User user, Long oauthId) {
		return R.status(userService.registerGuest(user, oauthId));
	}

	/**
	 * 配置用户平台信息
	 */
	@PostMapping("/update-platform")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "配置用户平台信息", notes = "传入user")
	public R updatePlatform(Long userId, Integer userType, String userExt) {
		return R.status(userService.updatePlatform(userId, userType, userExt));
	}

	/**
	 * 查看平台详情
	 */
	@ApiOperationSupport(order = 17)
	@ApiOperation(value = "查看平台详情", notes = "传入id")
	@GetMapping("/platform-detail")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<UserVO> platformDetail(User user) {
		return R.data(userService.platformDetail(user));
	}


	/**
	 * 用户列表查询
	 */
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "人员姓名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "postName", value = "职位名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "current", value = "当前页数", paramType = "query", dataType = "int"),
		@ApiImplicitParam(name = "size", value = "每页数量", paramType = "query", dataType = "int")
	})
	@ApiOperationSupport(order = 18)
	@ApiOperation(value = "用户列表查询", notes = "用户列表查询")
	@GetMapping("/search/user")
	public R<IPage<UserVO>> userSearch(@ApiIgnore UserVO user, @ApiIgnore Query query) {
		return R.data(userService.selectUserSearch(user, query));
	}

	/**
	 * 发送手机短信验证码
	 */
	@PostMapping("/sendMsg")
	public R<String> sendMsg(@RequestBody User user, HttpSession session) {
		//获取手机号
		String phone = user.getPhone();

		if (StringUtils.isNotEmpty(phone)) {
			//生成随机的4位验证码
			String code = ValidateCodeUtils.generateValidateCode(4).toString();
			log.info("code={}", code);

			//需要将生成的验证码保存到Session
			session.setAttribute(phone, code);

			return R.success("手机验证码短信发送成功");
		}

		return R.fail("短信发送失败");
	}

	/**
	 * 移动端用户登录
	 */
	@PostMapping("/login")
	public R<User> login(@RequestBody Map map, HttpSession session) {
		log.info(map.toString());

		//获取手机号
		String phone = map.get("phone").toString();

		//获取验证码
		String code = map.get("code").toString();

		//从Session中获取保存的验证码
		Object codeInSession = session.getAttribute(phone);

		//进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
		if (codeInSession != null && codeInSession.equals(code)) {
			//如果能够比对成功，说明登录成功

			LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(User::getPhone, phone);

			User user = userService.getOne(queryWrapper);
			session.setAttribute("user", user.getId());
			return R.data(user);
		}
		return R.fail("登录失败");
	}

	/**
	 * 移动端用户注册
	 */
	@PostMapping("/register")
	public R<User> register(@RequestBody Map<String, String> map, HttpSession session) {
		log.info(map.toString());

		//获取手机号
		String phone = map.get("phone").toString();
		//获取验证码
		String code = map.get("code").toString();

		//从Session中获取保存的验证码
		Object codeInSession = session.getAttribute(phone);

		//进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
		if (codeInSession != null && codeInSession.equals(code)) {
			//如果能够比对成功，说明手机号正确

			LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(User::getPhone, phone);

			User user = userService.getOne(queryWrapper);
			if (user == null) {
				//判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
				user = new User();
				user.setSex(Integer.getInteger(map.get("sex")));
				user.setName(map.get("name"));
				user.setAvatar(map.get("avatar"));
				user.setUserFortunellaVenosa(Double.valueOf(map.get("userFortunellaVenosa")));
				user.setPhone(phone);
				user.setStatus(1);
				userService.save(user);
				session.setAttribute("user", user.getId());
				return R.data(user);
			}

			return R.fail("用户已经存在");
		}
		return R.fail("验证码错误");
	}

	/**
	 * 移动端用户信息修改
	 */
	@PostMapping("/update_yi")
	public R<User> update(@RequestBody Map<String, String> map, HttpSession session) {
		log.info(map.toString());

		//获取用户id
		String id = map.get("id");

		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getId, id);

		User user = userService.getOne(queryWrapper);

		if (user == null) {
			return R.fail("用户不存在");
		}

		//获取手机号
		String phone = map.get("phone");
		if (!StringUtils.equals(user.getPhone(), phone)) {
			//获取验证码
			String code = map.get("code");

			//从Session中获取保存的验证码
			Object codeInSession = session.getAttribute(phone);

			//进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
			if (codeInSession != null && codeInSession.equals(code)) {
				boolean a = true;
			} else {
				return R.fail("验证码错误");
			}
		}
		user.setSex(Integer.getInteger(map.get("sex")));
		user.setName(map.get("name"));
		user.setAvatar(map.get("avatar"));
		user.setUserFortunellaVenosa(Double.valueOf(map.get("userFortunellaVenosa")));
		user.setPhone(phone);
		user.setStatus(Integer.getInteger(map.get("status")));
		userService.updateById(user);
		session.setAttribute("user", user.getId());
		return R.data(user);
	}

}
