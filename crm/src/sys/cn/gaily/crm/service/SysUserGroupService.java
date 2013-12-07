package cn.gaily.crm.service;

import java.util.List;

import cn.gaily.crm.bean.SysUserGroupSearch;
import cn.gaily.crm.domain.SysUserGroup;

public interface SysUserGroupService {
 
	void saveUserGroup(SysUserGroup sysUserGroup);

	/**
	 * 按条件进行查询
	 * @param sysUserGroupSearch 封装的是查询条件
	 * @return
	 */
	List<SysUserGroup> findSysUserGroups(SysUserGroupSearch sysUserGroupSearch);

	/**
	 * 根据id进行查询
	 * @param id
	 * @return
	 */
	SysUserGroup findSysUserGroupById(Integer id);

	/**
	 * 修改部门信息
	 * @param sysUserGroup
	 */
	void updateUserGroup(SysUserGroup sysUserGroup);

	/**
	 * 通过id批量删除部门信息
	 * @param ids
	 */
	void deleteSysUserGroupsByIds(Integer... ids);

	/**
	 * 查询所有的部门信息
	 * @return
	 */
	List<SysUserGroup> findAllSysGroups();
}
