package cn.gaily.crm.web.action;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import com.opensymphony.xwork2.ModelDriven;

import cn.gaily.crm.annotation.Limit;
import cn.gaily.crm.bean.SysUserSearch;
import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.domain.SysRole;
import cn.gaily.crm.domain.SysUser;
import cn.gaily.crm.domain.SysUserGroup;
import cn.gaily.crm.service.SysRoleService;
import cn.gaily.crm.service.SysUserGroupService;
import cn.gaily.crm.service.SysUserService;
import cn.gaily.crm.util.DataType;
import cn.gaily.crm.util.MD5keyBean;
import cn.gaily.crm.util.SQLDateConverter;
import cn.gaily.crm.util.SessionUtils;
import cn.gaily.crm.web.form.SysUserForm;

public class SysUserAction extends BaseAction implements
		ModelDriven<SysUserForm> {
	private static final long serialVersionUID = 1L;

	private SysUserForm sysUserForm = new SysUserForm();

	private SysUserService sysUserService = (SysUserService) ServiceProvinder.getService("sysUserService");
	private SysUserGroupService sysUserGroupService = (SysUserGroupService) ServiceProvinder
			.getService("sysUserGroupService");
	private SysRoleService sysRoleService = (SysRoleService) ServiceProvinder
			.getService("sysRoleService");

	/**
	 * 更新密码
	 * @return
	 */
	@Limit(module="user",privilege="updatePassword")
	public String updatePassword(){
		String sid = request.getParameter("id");
		String newPassword = request.getParameter("password");
		SysUser curSysuser = SessionUtils.getSysUserFromSession(request);
		if(StringUtils.isNotBlank(sid)&&StringUtils.isNotBlank(newPassword)&&curSysuser!=null){
			Integer id = Integer.parseInt(sid);
			SysUser sysUser = sysUserService.findSysUserById(id);
			if(sysUser!=null){
				MD5keyBean md5 = new MD5keyBean();
				String password = md5.getkeyBeanofStr(newPassword);
				sysUser.setPassword(password);
				sysUserService.updateSysUsersPassword(curSysuser,sysUser);
				return "updatePassword";
			}
		}
		return null;
	}
	
	/**
	 * 显示修改密码界面
	 * @return
	 */
	@Limit(module="user",privilege="loadPassword")
	public String loadPassword(){
		String sid = request.getParameter("id");
		if(StringUtils.isNotBlank(sid)){
			Integer id = Integer.parseInt(sid);
			SysUser sysUser = sysUserService.findSysUserById(id);
			if(sysUser!=null){
				request.setAttribute("sysUser", sysUser);
				return "loadPassword";
			}
		}
		return null;
	}
	
	/**
	 * 更新
	 * 
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Limit(module="user",privilege="update")
	public String update() throws IllegalAccessException, InvocationTargetException {
		// 实例化po
		SysUser sysUser = new SysUser();

		// 注册转换器
		ConvertUtils.register(new SQLDateConverter(), java.sql.Date.class);

		// 把vo值移到po，
		BeanUtils.copyProperties(sysUser, sysUserForm);

		// 再处理特殊情况

		// 1.权限组
		SysRole sysRole = new SysRole();
		sysRole.setId(sysUserForm.getRoleId());
		sysUser.setSysRole(sysRole);

		// 2.部门
		SysUserGroup sysUserGroup = new SysUserGroup();
		if (StringUtils.isNotBlank(sysUserForm.getGroupId())) {
			sysUserGroup.setId(Integer.parseInt(sysUserForm.getGroupId()));
		}
		sysUser.setSysUserGroup(sysUserGroup);
		
		SysUser curSysUser = SessionUtils.getSysUserFromSession(request);
		if(curSysUser==null){
			return "login";
		}
		//设置修改者和修改时间
		sysUser.setUpdater(curSysUser.getCnname());
		sysUser.setUpdateTime(DateFormatUtils.format(new java.util.Date(), "yyyy-MM-dd HH:mm:ss"));

		// 调用业务层保存
		sysUserService.updateSysUser(sysUser);
		
		return "listAction";
	}

	/**
	 * 编辑用户信息
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module="user",privilege="edit")
	public String edit() throws IllegalAccessException,
			InvocationTargetException {

		// 处理特殊
		// 处理权限组的下拉框
		List<SysRole> sysRoles = sysRoleService.findAllSysRoles();
		request.setAttribute("sysRoleSelect", sysRoles);

		// 处理角色组的下拉框
		List<SysUserGroup> sysUserGroups = sysUserGroupService
				.findAllSysGroups();
		request.setAttribute("sysUserGroupSelect", sysUserGroups);

		String sid = request.getParameter("id");
		if (StringUtils.isNotBlank(sid)) {
			Integer id = Integer.parseInt(sid);

			// 查出对象
			SysUser sysUser = sysUserService.findSysUserById(id);

			// po-->vo
			BeanUtils.copyProperties(sysUserForm, sysUser);

			// 处理下拉框的回显
			if (sysUser.getSysRole() != null) {
				sysUserForm.setRoleId(sysUser.getSysRole().getId());
			}

			if (sysUser.getSysUserGroup() != null) {
				sysUserForm.setGroupId(sysUser.getSysUserGroup().getId()
						.toString());
			}
			request.setAttribute("sysUser", sysUser);
			return "edit";
		}
		return null;
	}

	/**
	 * 启用
	 * 
	 * @return
	 */
	@Limit(module="user",privilege="enable")
	public String enable() {
		String[] sids = request.getParameterValues("ids");

		Integer[] ids = DataType.converterStringArray2IntegerArray(sids);
		if (ids != null) {
			sysUserService.enableSysUsersByIds(ids);
		}

		return "listAction";
	}

	/**
	 * 停用
	 * 
	 * @return
	 */
	@Limit(module="user",privilege="disable")
	public String disable() {
		String[] sids = request.getParameterValues("ids");

		Integer[] ids = DataType.converterStringArray2IntegerArray(sids);
		if (ids != null) {
			sysUserService.disableSysUsersByIds(ids);
		}

		return "listAction";
	}

	/**
	 * 删除用户
	 * 
	 * @return
	 */
	@Limit(module="user",privilege="delete")
	public String delete() {

		String[] sids = request.getParameterValues("ids");

		Integer[] ids = DataType.converterStringArray2IntegerArray(sids);
		if (ids != null) {
			sysUserService.deleteSysUsersByIds(ids);
		}

		return "listAction";
	}

	/**
	 * 显示用户
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module="user",privilege="list")
	public String list() throws IllegalAccessException,
			InvocationTargetException {
		List<SysUserGroup> sysUserGroups = sysUserGroupService
				.findAllSysGroups();
		request.setAttribute("sysUserGroupSelect", sysUserGroups);

		SysUserSearch sysUserSearch = new SysUserSearch();
		BeanUtils.copyProperties(sysUserSearch, sysUserForm);

		List<SysUser> sysUserList = sysUserService
				.findSysUsersByCondition(sysUserSearch);
		request.setAttribute("sysUserList", sysUserList);
		return "list";
	}

	/**
	 * 显示添加用户界面
	 * 
	 * @return
	 */
	@Limit(module="user",privilege="add")
	public String add() {

		// 处理权限组的下拉框
		List<SysRole> sysRoles = sysRoleService.findAllSysRoles();
		request.setAttribute("sysRoleSelect", sysRoles);

		// 处理角色组的下拉框
		List<SysUserGroup> sysUserGroups = sysUserGroupService
				.findAllSysGroups();
		request.setAttribute("sysUserGroupSelect", sysUserGroups);

		// 处理创建人 修改人 创建时间 修改时间

		// 1.获取当前的登录用户
		SysUser sysUser = SessionUtils.getSysUserFromSession(request);
		if (sysUser == null) {
			return "login";
		}

		// 2.设置创建人和修改人(添加信息 此时创建人和修改人一致)
		sysUserForm.setCreator(sysUser.getCnname());
		sysUserForm.setUpdater(sysUser.getCnname());

		String curDate = DateFormatUtils.format(new java.util.Date(),
				"yyyy-MM-dd HH-mm-ss");
		sysUserForm.setCreateTime(curDate);
		sysUserForm.setUpdateTime(curDate);
		return "add";
	}

	/**
	 * 保存
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module="user",privilege="save")
	public String save() throws IllegalAccessException,
			InvocationTargetException {
		// 实例化po
		SysUser sysUser = new SysUser();

		// 注册转换器
		ConvertUtils.register(new SQLDateConverter(), java.sql.Date.class);

		// 把vo值移到po，
		BeanUtils.copyProperties(sysUser, sysUserForm);

		// 再处理特殊情况

		// 0.处理密码加密
		MD5keyBean m = new MD5keyBean();
		String password = m.getkeyBeanofStr(sysUserForm.getPassword());
		sysUser.setPassword(password);

		// 1.权限组
		SysRole sysRole = new SysRole();
		sysRole.setId(sysUserForm.getRoleId());
		sysUser.setSysRole(sysRole);

		// 2.部门
		SysUserGroup sysUserGroup = new SysUserGroup();
		if (StringUtils.isNotBlank(sysUserForm.getGroupId())) {
			sysUserGroup.setId(Integer.parseInt(sysUserForm.getGroupId()));
		}
		sysUser.setSysUserGroup(sysUserGroup);

		// 调用业务层保存
		sysUserService.saveSysUser(sysUser);

		return "listAction";
	}

	/**
	 * 处理用户登录请求
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String isLogin() throws UnsupportedEncodingException {
		//System.out.println("isLogin");
		// 1. 处理验证码
		/*
		 * boolean flag = SessionUtils.isCheckNum(request); if(!flag){
		 * this.addFieldError("checkNum", "验证码错误，请重新输入！"); return "login"; }
		 */

		// 2. 处理用户名密码
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		MD5keyBean m = new MD5keyBean();
		password = m.getkeyBeanofStr(password);

		SysUser sysUser = sysUserService.findSysUserByNameAndPassword(name,
				password);
		// 2.1验证失败
		if (sysUser == null) {
			this.addFieldError("name", "用户名或者密码输入错误！");
			return "login";
		}
		if(sysUser.getStatus().equals("N")){
			this.addFieldError("name", "该用户暂未启用，请联系管理员。");
			return "login";
		}
		// 2.2登录成功，放置当前的对象到session中
		SessionUtils.setSysUserToSession(request, sysUser);

		// 3. 处理session
		addSession(name, request.getParameter("password"), request, response);

		return "main";
	}

	/**
	 * 添加session
	 * 
	 * @param name
	 * @param parameter
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@Limit(module="user",privilege="addSession")
	private void addSession(String name, String password,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		// 设置cookie
		Cookie nameCookie = new Cookie("name", java.net.URLEncoder.encode(name,
				"utf-8"));
		Cookie pswCookie = new Cookie("psw", password);

		// 设置cookie的父路径
		nameCookie.setPath(request.getContextPath() + "/");
		pswCookie.setPath(request.getContextPath() + "/");

		// 获取是否保存cookie
		String rememberMe = request.getParameter("rememberMe");
		if (rememberMe == null) {// 不保存cookie
			nameCookie.setMaxAge(0);
			pswCookie.setMaxAge(0);
		} else {// 保存cookie
			nameCookie.setMaxAge(7 * 24 * 60 * 60);
			pswCookie.setMaxAge(7 * 24 * 60 * 60);
		}

		// 加入cookie到响应头
		response.addCookie(nameCookie);
		response.addCookie(pswCookie);

	}

	@Override
	public SysUserForm getModel() {
		return sysUserForm;
	}

}
