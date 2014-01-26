package cn.gaily.crm.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gaily.crm.dao.SysMenuPrivilegeDao;
import cn.gaily.crm.domain.SysMenuPrivilege;
import cn.gaily.crm.domain.SysMenuPrivilegeId;
import cn.gaily.crm.service.SysMenuPrivilegeService;
import edu.emory.mathcs.backport.java.util.LinkedList;

@Transactional(readOnly=true)
@Service(value="sysMenuPrivilegeService")
public class SysMenuPrivilegeServiceImpl implements SysMenuPrivilegeService {

	@Resource(name="sysMenuPrivilegeDao")
	private SysMenuPrivilegeDao sysMenuPrivilegeDao;
	
	@Override
	@Transactional(readOnly=false)
	public void updateMenu(String roleId, String[] menuModules) {
		
		if(StringUtils.isNotBlank(roleId)&&menuModules!=null&&menuModules.length>0){
			//删除权限组对应的权限
			String whereHql = " and o.id.roleId=?";
			Object[] params = {roleId};
			List<SysMenuPrivilege> list = sysMenuPrivilegeDao.findObjectsByConditionWithNoPage(whereHql, params);
			
			sysMenuPrivilegeDao.deleteObjects(list);
		}
		//增加权限到权限组中
		if(StringUtils.isNotBlank(roleId)&&menuModules!=null&&menuModules.length>0){
			for(int i=0;i<menuModules.length;i++){
				if(StringUtils.isNotBlank(menuModules[i])&&!menuModules[i].trim().equals(",")){
					String[] str = menuModules[i].split(",");
					SysMenuPrivilege s = new SysMenuPrivilege();
					SysMenuPrivilegeId id = new SysMenuPrivilegeId();
					id.setRoleId(roleId);
					id.setMenuModule(str[0]);
					id.setMenuPrivilege(str[1]);
					s.setId(id);
					sysMenuPrivilegeDao.save(s);
				}
			}
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<SysMenuPrivilege> findSysMenuPrivilegesByRoleId(String roleId) {
		if(StringUtils.isNotBlank(roleId)){
			String whereHql = " and o.id.roleId=?";
			Object[] params = {roleId};
			List<SysMenuPrivilege> sysMenuPrivileges = sysMenuPrivilegeDao.findObjectsByConditionWithNoPage(whereHql, params);
			return sysMenuPrivileges;
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<SysMenuPrivilege> findAllSysMenuPrivileges() {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.id.roleId", "asc");
		
		return sysMenuPrivilegeDao.findObjectsByConditionWithNoPage(orderby);
	}

	@Override
	@Transactional(readOnly=true)
	public List<SysMenuPrivilege> findAllSysMenuPrivilegesCache() {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.id.roleId", "asc");
		return sysMenuPrivilegeDao.findObjectsByConditionWithNoPageCache(null,null,orderby);
	}
	
}
