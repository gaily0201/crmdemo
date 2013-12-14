package cn.gaily.crm.service;

import java.io.Serializable;
import java.util.List;

import cn.gaily.crm.bean.CompanySearch;
import cn.gaily.crm.domain.Company;
import cn.gaily.crm.domain.SysUser;

public interface CompanyService {

	/**
	 * 生成客户编码
	 * @param tabName
	 * @return
	 */
	String getCompanyCodeByTabName(String tabName);

	/**
	 * 保存客户信息
	 * @param curSysuser 处理日志用
	 * @param company
	 */
	void saveCompany(SysUser curSysuser, Company company);

	/**
	 * 根据查询条件查询
	 * @param sysUser 当前登录用户
	 * @param companySearch 查询条件
	 * @return
	 */
	List<Company> findCompanyByCondition(SysUser curSysuser, CompanySearch companySearch);

	/**
	 * 通过客户Id获取客户信息
	 * @param id
	 * @return
	 */
	Company findCompanyById(Integer id);

	/**
	 * 修改客户信息
	 * @param curSysuser
	 * @param company
	 */
	void updateCompany(SysUser curSysuser, Company company);

	/**
	 * 根据ids删除客户
	 * @param ids
	 */
	void deleteCompanyByIds(SysUser curSysuser,Integer[] ids);

	/**
	 * 增加共享
	 * @param s_module 模块名称
	 * @param id 客户id
	 * @param uids 用户id
	 */
	void addUpdateShareSetOne(SysUser curSysuser,String s_module, Integer id, Integer[] uids);
	
	/**
	 * 减少共享
	 * @param s_module 模块名称
	 * @param id 客户id
	 * @param uids 用户id
	 */
	void minusUpdateShareSetOne(SysUser curSysuser,String s_module, Integer id, Integer[] uids);

	/**
	 * 取消共享设置
	 * @param id 客户id
	 * @param s_module 模块名称
	 */
	void updateShareCancelOne(SysUser curSysuser,Integer id, String s_module);

	/**
	 * 根据共享的Id查找用户信息
	 * @param id
	 * @return
	 */
	List<SysUser> findSysUserBySharedIds(Integer id);

	/**
	 * 查询出共享的客户
	 * @param curSysuser
	 * @return
	 */
	List<Company> findSharedCompany(SysUser curSysuser);

	/**
	 * 设置下次联系时间
	 * @param ids 客户id
	 * @param next_touch_date 下次联系时间
	 */
	void updateNextTouchTime(SysUser curSysuser, Integer[] ids, java.sql.Date next_touch_date);

	/**
	 * 变更客户的所有人
	 * @param id
	 * @param new_owner
	 */
	void changeHandler(SysUser curSysuser,Integer[] id, Integer new_owner);

	/**
	 * 查询属于当前用户的所有客户
	 * @param curSysuser
	 * @return
	 */
	List<Company> findMyOwnCompanys(SysUser curSysuser);



}
