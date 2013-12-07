package cn.gaily.crm.service;

import java.util.List;

import cn.gaily.crm.bean.SysUserSearch;
import cn.gaily.crm.domain.SysUser;

public interface SysUserService {

	/**
	 * 根据用户名和密码查找用户
	 * @param name
	 * @param password
	 * @return
	 */
	SysUser findSysUserByNameAndPassword(String name, String password);

	
	/**
	 * 保存用户
	 * @param sysUser
	 */
	void saveSysUser(SysUser sysUser);

	
	/**
	 * 根据条件查询用户
	 * @param sysUserSearch
	 * @return
	 */
	List<SysUser> findSysUsersByCondition(SysUserSearch sysUserSearch);


	/**
	 * 通过id批量删除用户
	 * @param ids
	 */
	void deleteSysUsersByIds(Integer... ids);


	/**
	 * 编辑用户
	 * @param id
	 * @return
	 */
	SysUser findSysUserById(Integer id);


	/**
	 * 启用
	 * @param ids
	 */
	void enableSysUsersByIds(Integer[] ids);


	/**
	 * 停用
	 * @param ids
	 */
	void disableSysUsersByIds(Integer[] ids);


	/**
	 * 修改
	 * @param sysUser
	 */
	void updateSysUser(SysUser sysUser);


	/**
	 * 查找所有的用户
	 * @return
	 */
	List<SysUser> findAllSysUsers();

}
