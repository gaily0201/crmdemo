package cn.gaily.crm.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gaily.crm.bean.SysUserGroupSearch;
import cn.gaily.crm.dao.SysUserGroupDao;
import cn.gaily.crm.domain.SysUserGroup;
import cn.gaily.crm.service.SysUserGroupService;

@Transactional(readOnly = true)
@Service(value = "sysUserGroupService")
public class SysUserGroupServiceImpl implements SysUserGroupService {

	@Resource(name = "sysUserGroupDao")
	private SysUserGroupDao sysUserGroupDao;

	@Override
	@Transactional(readOnly = false)
	public void saveUserGroup(SysUserGroup sysUserGroup) {
		// TODO Auto-generated method stub
		this.sysUserGroupDao.save(sysUserGroup);
	}

	/**
	 * 通过查询条件查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUserGroup> findSysUserGroups(SysUserGroupSearch sysUserGroupSearch) {

		if(sysUserGroupSearch==null){
			throw new RuntimeException("传递给业务层部门查询条件的对象为空");
		}
		
		//组织查询条件
		String whereHql = "";
		
		//定义封装查询条件的list
		@SuppressWarnings("rawtypes")
		List paramList = new ArrayList();
		if(StringUtils.isNotBlank(sysUserGroupSearch.getName())){
			whereHql = " and o.name like ?";
			paramList.add("%"+sysUserGroupSearch.getName().trim()+"%");
		}
		
		Object[] params = paramList.toArray();
		
		//排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.id", "asc");
		
		return sysUserGroupDao.findObjectsByConditionWithNoPage(whereHql, params, orderby);
	}

	/**
	 * 通过id查询部门信息
	 */
	@Override
	public SysUserGroup findSysUserGroupById(Integer id) {
		return sysUserGroupDao.findObjectById(id);
	}

	/**
	 * 修改部门信息
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateUserGroup(SysUserGroup sysUserGroup) {
		// TODO Auto-generated method stub
		sysUserGroupDao.update(sysUserGroup);
	}

	/**
	 * 根据id批量删除部门信息
	 */
	@Override
	@Transactional(readOnly=false)
	public void deleteSysUserGroupsByIds(Integer... ids) {
		sysUserGroupDao.deleteByIds(ids);
		
	}

	@Override
	@Transactional(readOnly=true)
	public List<SysUserGroup> findAllSysGroups() {
		// TODO Auto-generated method stub
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("name", "asc");
		return sysUserGroupDao.findObjectsByConditionWithNoPage(orderby);
	}
	
	
	
	
	
/*
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(readOnly=true)
	public List<SysUserGroup> findSysUserGroups(String name, String principal) {

		// 组织查询条件
		String whereHql = "";

		// 定义封装查询条件的List
		List paramList = new ArrayList();

		if(StringUtils.isNotBlank(name)){
			whereHql = " and o.name like ?";
			paramList.add("%"+name+"%");
		}
		
		if(StringUtils.isNotBlank(principal)){
			whereHql = whereHql+" and o.principal like ?";
			paramList.add("%"+principal+"%");
		}
		
		Object[] params = paramList.toArray();
		
		//组织排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.name", "asc");
		orderby.put("o.principal", "desc");
		
		return sysUserGroupDao.findObjectsByConditionWithNoPage(whereHql, params, orderby);
		
	}*/

}
