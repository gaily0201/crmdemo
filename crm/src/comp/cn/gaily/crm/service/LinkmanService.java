package cn.gaily.crm.service;

import java.util.List;

import cn.gaily.crm.bean.LinkmanSearch;
import cn.gaily.crm.domain.Company;
import cn.gaily.crm.domain.Linkman;
import cn.gaily.crm.domain.SysUser;

public interface LinkmanService {

	/**
	 * 生成联系人编码
	 * @param tabName
	 * @return
	 */
	String getLinkmanCodeByTabName(String tabName);

	/**
	 * 保存联系人
	 * @param curSysuser
	 * @param linkman
	 */
	void saveLinkman(SysUser curSysuser, Linkman linkman);

	/**
	 * 根据 查询条件 查询联系人
	 * @param curSysuser
	 * @param linkmanSearch
	 * @return
	 */
	List<Linkman> findLinkmansByCondition(SysUser curSysuser,
			LinkmanSearch linkmanSearch);

	/**
	 * 根据Id查询联系人
	 * @param id
	 * @return
	 */
	Linkman findLinkmanById(Integer id);

	/**
	 * 修改联系人信息
	 * @param curSysuser
	 * @param linkman
	 */
	void updateLinkman(SysUser curSysuser, Linkman linkman);

	/**
	 * 批量删除联系人
	 * @param cursysUser
	 * @param ids
	 */
	void deleteLinkmansByIds(SysUser cursysUser, Integer[] ids);

	/**
	 * 按照客户id查询联系人
	 * @param cid
	 * @return
	 */
	List<Linkman> findLinkmanByComp(SysUser curSysuser,Company company);

}
