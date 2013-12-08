package cn.gaily.crm.service;

import java.util.List;

import cn.gaily.crm.domain.SysMenuPrivilege;

public interface SysMenuPrivilegeService {

	/**
	 * 更新菜单权限
	 * @param roleId
	 * @param menuModules
	 */
	void updateMenu(String roleId, String[] menuModules);

	/**
	 * 通过角色id查询菜单的权限
	 * @param roleId
	 * @return
	 */
	List<SysMenuPrivilege> findSysMenuPrivilegesByRoleId(String roleId);

	/**
	 * 获得所有的菜单权限
	 * @return
	 */
	List<SysMenuPrivilege> findAllSysMenuPrivileges();

	/**
	 * 获得所有的菜单权限,带二级缓存
	 * @return
	 */
	List<SysMenuPrivilege> findAllSysMenuPrivilegesCache();

}
