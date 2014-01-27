package cn.gaily.crm.web.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import cn.gaily.crm.annotation.Limit;
import cn.gaily.crm.bean.SysRoleSearch;
import cn.gaily.crm.bean.SysUserGroupSearch;
import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.domain.SysMenu;
import cn.gaily.crm.domain.SysMenuPrivilege;
import cn.gaily.crm.domain.SysPopedom;
import cn.gaily.crm.domain.SysPopedomId;
import cn.gaily.crm.domain.SysPopedomPrivilege;
import cn.gaily.crm.domain.SysRole;
import cn.gaily.crm.domain.SysUserGroup;
import cn.gaily.crm.service.SysMenuPrivilegeService;
import cn.gaily.crm.service.SysMenuService;
import cn.gaily.crm.service.SysPopedomPrivilegeService;
import cn.gaily.crm.service.SysPopedomService;
import cn.gaily.crm.service.SysRoleService;
import cn.gaily.crm.web.form.CompanyForm;
import cn.gaily.crm.web.form.SysRoleForm;

import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class SysRoleAction extends BaseAction implements
		ModelDriven<SysRoleForm> {

	private SysRoleForm sysRoleForm = new SysRoleForm();

	// 获取权限组的业务层对象
	private SysRoleService sysRoleService = (SysRoleService) ServiceProvinder
			.getService("sysRoleService");

	// 获取操作功能的业务层对象
	private SysPopedomService sysPopedomService = (SysPopedomService) ServiceProvinder
			.getService("sysPopedomService");

	// 获取操作权限组的业务层对象
	private SysPopedomPrivilegeService sysPopedomPrivilegeService = (SysPopedomPrivilegeService) ServiceProvinder
			.getService("sysPopedomPrivilegeService");

	// 获取菜单功能的业务层对象
	private SysMenuService sysMenuService = (SysMenuService) ServiceProvinder
			.getService("sysMenuService");

	// 获取菜单权限的业务层对象
	private SysMenuPrivilegeService sysMenuPrivilegeService = (SysMenuPrivilegeService) ServiceProvinder
			.getService("sysMenuPrivilegeService");

	/**
	 * 设置菜单操作权限
	 * 
	 * @return
	 */
	@Limit(module="role",privilege="updateMenu")
	public String updateMenu() {

		
		// 获得角色id
		String roleId = request.getParameter("roleId");
		SysRole sysRole = sysRoleService.findSysRoleById(roleId);
		request.setAttribute("sysRole", sysRole);

		// 获取复选框的值
		String[] menuModules = request.getParameterValues("menuModule");
		sysMenuPrivilegeService.updateMenu(roleId, menuModules);

		return "updateMenu";
	}

	/**
	 * 显示菜单操作权限设置页面
	 * 
	 * @return
	 */
	@Limit(module="role",privilege="listMenu")
	public String listMenu() {

		// 获取权限组id
		String roleId = request.getParameter("roleId");
		if (StringUtils.isNotBlank(roleId)) {
			// 通过权限组id查询权限组信息
			SysRole sysRole = sysRoleService.findSysRoleById(roleId);
			// 放置到request中
			request.setAttribute("sysRole", sysRole);
			// 查询sys_menu表中所有数据
			List<SysMenu> sysMenus = sysMenuService.findAllSysMenus();
			request.setAttribute("sysMenus", sysMenus);

			//通过权限组id查询sys_menu_privilege
			List<SysMenuPrivilege> sysMenuPrivileges = 
					sysMenuPrivilegeService.findSysMenuPrivilegesByRoleId(roleId);
			request.setAttribute("sysMenuPrivileges", sysMenuPrivileges);
			
			return "listMenu";
		}
		return null;
	}

	/**
	 * 更新权限操作
	 * 
	 * @return
	 */
	@Limit(module = "role", privilege = "updatePopedom")
	public String updatePopedom() {
		// 获得角色id
		String roleId = request.getParameter("roleId");
		SysRole sysRole = sysRoleService.findSysRoleById(roleId);
		request.setAttribute("sysRole", sysRole);

		// 获取复选框的值
		String[] popedomModules = request.getParameterValues("popedomModule");

		sysPopedomPrivilegeService.updatePopedom(roleId, popedomModules);

		return "updatePopedom";
	}

	/**
	 * 显示权限操作设置页面
	 * 
	 * @return
	 */
	@Limit(module = "role", privilege = "listPopedom")
	public String listPopedom() {

		// 获得角色id
		String roleId = request.getParameter("roleId");
		SysRole sysRole = sysRoleService.findSysRoleById(roleId);
		request.setAttribute("sysRole", sysRole);

		// 获取系统的所有功能（操作功能表）
		List<SysPopedom> sysPopedoms = sysPopedomService.findAllPopedoms();
		request.setAttribute("sysPopedoms", sysPopedoms);

		// 查询该权限组包含的权限
		List<SysPopedomPrivilege> sysPopedomPrivileges = sysPopedomPrivilegeService
				.findSysPopedomPrivilegesByRoleId(roleId);
		request.setAttribute("sysPopedomPrivileges", sysPopedomPrivileges);

		return "listPopedom";
	}

	/**
	 * 更新权限组
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module = "role", privilege = "update")
	public String update() throws IllegalAccessException,
			InvocationTargetException {

		// 实例化po对象
		SysRole sysRole = new SysRole();
		// 将vo对象copy为po对象
		BeanUtils.copyProperties(sysRole, sysRoleForm);

		sysRoleService.updateSysRole(sysRole);

		return "listAction";
	}

	/**
	 * 修改操作组权限
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module = "role", privilege = "edit")
	public String edit() throws IllegalAccessException,
			InvocationTargetException {

		// 获取权限组id
		String sid = sysRoleForm.getId();
		if (StringUtils.isNotBlank(sid)) {

			SysRole sysRole = sysRoleService.findSysRoleById(sid);

			BeanUtils.copyProperties(sysRoleForm, sysRole);

			return "edit";
		}

		return "listAction";
	}

	/**
	 * 删除操作权限组
	 * 
	 * @return
	 */
	@Limit(module = "role", privilege = "delete")
	public String delete() {

		String[] ids = request.getParameterValues("ids");
		
		//企业管理部不能删除
		if (ids != null && ids.length > 0) {
			List<String> iids = new ArrayList<String>();
			for(int i=0;i<ids.length;i++){
				if("402882e74237b685014237b70ee50003".equals(ids[i])){
					continue;
				}else{
					iids.add(ids[i]);
				}
			}
			String[] id = new String[iids.size()];
			for(int j=0;j<iids.size();j++){
				id[j] = iids.get(j);
			}
			
			sysRoleService.deleteSysRolesByIds(id);
			return "listAction";
		}

		return null;
	}

	/**
	 * 保存操作权限组的信息
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Limit(module = "role", privilege = "save")
	public String save() throws IllegalAccessException,
			InvocationTargetException {

		SysRole sysRole = new SysRole();

		BeanUtils.copyProperties(sysRole, sysRoleForm);

		sysRoleService.saveSysRole(sysRole);

		return "listAction";
	}

	/**
	 * 查询操作权限组的信息
	 * 
	 * @return
	 */
	@Limit(module = "role", privilege = "list")
	public String list() {

		SysRoleSearch sysRoleSearch = new SysRoleSearch();

		sysRoleSearch.setName(sysRoleForm.getName());

		List<SysRole> sysRoles = sysRoleService.findSysRoles(sysRoleSearch);

		request.setAttribute("sysRoles", sysRoles);

		return "list";
	}

	/**
	 * 显示权限组的添加页面
	 * 
	 * @return
	 */
	@Limit(module = "role", privilege = "add")
	public String add() {
		return "add";
	}

	@Override
	public SysRoleForm getModel() {
		return sysRoleForm;
	}

}
