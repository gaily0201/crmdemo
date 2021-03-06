package cn.gaily.crm.service;

import java.util.List;

import cn.gaily.crm.domain.SysPopedomPrivilege;

public interface SysPopedomPrivilegeService {

	/**
	 * 更新操作权限组
	 * @param roleId
	 * @param popedomModules
	 */
	void updatePopedom(String roleId, String[] popedomModules);

	/**
	 * 通过权限组id查询该权限组包含的权限
	 * @param roleId
	 * @return
	 */
	List<SysPopedomPrivilege> findSysPopedomPrivilegesByRoleId(String roleId);

	/**
	 * 查询所有的操作权限组的数据
	 * @return
	 */
	List<SysPopedomPrivilege> findSysPopedomPrivileges();

	/**
	 * 查询所有的操作权限组的数据,启用二级缓存
	 * @return
	 */
	List<SysPopedomPrivilege> findSysPopedomPrivilegesCache();

	/**
	 * 查询指定的操作组权限是否存在
	 * @param roleId
	 * @param module
	 * @param privilege
	 */
	List<SysPopedomPrivilege> findSysPopedomPrivilege(String roleId, String module, String privilege);

}
