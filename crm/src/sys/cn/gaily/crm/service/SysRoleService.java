package cn.gaily.crm.service;

import java.util.LinkedHashMap;
import java.util.List;

import cn.gaily.crm.bean.SysRoleSearch;
import cn.gaily.crm.domain.SysRole;

public interface SysRoleService {

	/**
	 * 保存操作权限组信息
	 * @param sysRole
	 */
	void saveSysRole(SysRole sysRole);

	/**
	 * 查找操作权限组信息
	 * @param sysRoleSearch
	 * @return
	 */
	List<SysRole> findSysRoles(SysRoleSearch sysRoleSearch);

	/**
	 * 根据id批量删除操作权限组
	 * @param ids
	 */
	void deleteSysRolesByIds(String... ids);

	
	/**
	 * 根据id查询操作组
	 * @param id
	 * @return
	 */
	SysRole findSysRoleById(String id);

	/**
	 * 更新权限组
	 * @param sysRole
	 */
	void updateSysRole(SysRole sysRole);

	/**
	 * 查询所有的操作组 
	 * @return
	 */
	List<SysRole> findAllSysRoles();


}
