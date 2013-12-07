package cn.gaily.crm.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gaily.crm.bean.SysRoleSearch;
import cn.gaily.crm.dao.SysRoleDao;
import cn.gaily.crm.domain.SysRole;
import cn.gaily.crm.service.SysRoleService;

@Service(value="sysRoleService")
@Transactional(readOnly=true)
public class SysRoleServiceImpl implements SysRoleService {

	@Resource(name="sysRoleDao")
	private SysRoleDao sysRoleDao;
	
	@Override
	@Transactional(readOnly=false)
	public void saveSysRole(SysRole sysRole) {
		// TODO Auto-generated method stub
		sysRoleDao.save(sysRole);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(readOnly=true)
	public List<SysRole> findSysRoles(SysRoleSearch sysRoleSearch) {
		
		if(sysRoleSearch==null){
			throw new RuntimeException("传递给业务层部门查询条件的对象为空");
		}
		
		//组织查询条件
		String whereHql = "";
		List paramList = new ArrayList();
		
		if(StringUtils.isNotBlank(sysRoleSearch.getName())){
			whereHql +=" and name like ?";
			paramList.add("%"+sysRoleSearch.getName()+"%");
		}
		
		Object[] params = paramList.toArray();
		
		//组织排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("name", "asc");
		
		//调用dao
		return sysRoleDao.findObjectsByConditionWithNoPage(whereHql, params, orderby);

	}

	@Override
	@Transactional(readOnly=false)
	public void deleteSysRolesByIds(String... ids) {
		// TODO Auto-generated method stub
		sysRoleDao.deleteByIds((java.io.Serializable[])ids);
	}

	@Override
	@Transactional(readOnly=true)
	public SysRole findSysRoleById(String id) {
		// TODO Auto-generated method stub
		return sysRoleDao.findObjectById(id);
		
	}

	@Override
	@Transactional(readOnly=false)
	public void updateSysRole(SysRole sysRole) {
		// TODO Auto-generated method stub
		sysRoleDao.update(sysRole);
	}

	@Override
	@Transactional(readOnly=true)
	public List<SysRole> findAllSysRoles() {
		// TODO Auto-generated method stub
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("name", "asc");
		return sysRoleDao.findObjectsByConditionWithNoPage(orderby);
	}
}
