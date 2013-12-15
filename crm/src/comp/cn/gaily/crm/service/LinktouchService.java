package cn.gaily.crm.service;

import java.util.Collection;
import java.util.List;

import cn.gaily.crm.bean.LinkmanSearch;
import cn.gaily.crm.bean.LinktouchSearch;
import cn.gaily.crm.domain.Company;
import cn.gaily.crm.domain.Linkman;
import cn.gaily.crm.domain.Linktouch;
import cn.gaily.crm.domain.SysUser;

public interface LinktouchService {

	/**
	 * 保存联系记录
	 * @param curSysuser
	 * @param linktouch
	 */
	void saveLinktouch(SysUser curSysuser, Linktouch linktouch);

	/**
	 * 根据当前用户查出属于自己的所有联系记录
	 * @param curSysuser
	 * @return
	 */
	List<Linktouch> findMyOwnLinktouchsByCondition(SysUser curSysuser,LinktouchSearch linktouchSearch);

	/**
	 * 根据id查询联系记录
	 * @param id
	 * @return
	 */
	Linktouch findLinktouchById(Integer id);

	/**
	 * 更新联系记录
	 * @param curSysuser
	 * @param linktouch
	 */
	void updateLinktouch(SysUser curSysuser, Linktouch linktouch);

	/**
	 * 删除联系记录
	 * @param curSysuser
	 * @param ids
	 */
	void delteLinktouchsByIds(SysUser curSysuser, Integer[] ids);

	/**
	 * 通过
	 * @param curSysuser
	 * @param linkman
	 * @return
	 */
	List<Linktouch> findlinktouchsByLinkmans(SysUser curSysuser, Linkman linkman);


}
