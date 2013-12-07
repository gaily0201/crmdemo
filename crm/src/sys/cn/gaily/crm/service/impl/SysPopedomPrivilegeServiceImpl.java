package cn.gaily.crm.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.gaily.crm.dao.SysPopedomPrivilegeDao;
import cn.gaily.crm.dao.impl.CommonDaoImpl;
import cn.gaily.crm.domain.SysPopedomPrivilege;
import cn.gaily.crm.domain.SysPopedomPrivilegeId;
import cn.gaily.crm.service.SysPopedomPrivilegeService;

@Service(value = "sysPopedomPrivilegeService")
@Transactional(readOnly = true)
public class SysPopedomPrivilegeServiceImpl extends
		CommonDaoImpl<SysPopedomPrivilege> implements
		SysPopedomPrivilegeService {

	@Resource(name = "sysPopedomPrivilegeDao")
	private SysPopedomPrivilegeDao sysPopedomPrivilegeDao;

	@Override
	@Transactional(readOnly = false)
	public void updatePopedom(String roleId, String[] popedomModules) {

		// 删除该权限组对应的权限
		String whereHql = " and o.id.roleId=?";
		Object[] params = { roleId };

		List<SysPopedomPrivilege> list = sysPopedomPrivilegeDao
				.findObjectsByConditionWithNoPage(whereHql, params);
		sysPopedomPrivilegeDao.deleteObjects(list);

		// 保存该权限组对应的权限
		if (StringUtils.isNotBlank(roleId) && popedomModules != null
				&& popedomModules.length > 0) {
			for (int i = 0; i < popedomModules.length; i++) {
				if (StringUtils.isNotBlank(popedomModules[i])) {
					String[] str = popedomModules[i].split(",");
					SysPopedomPrivilege sysPopedomPrivilege = new SysPopedomPrivilege();

					SysPopedomPrivilegeId id = new SysPopedomPrivilegeId();
					id.setRoleId(roleId);
					id.setPopedomModule(str[0]);
					id.setPopedomPrivilege(str[1]);

					sysPopedomPrivilege.setId(id);
					sysPopedomPrivilegeDao.save(sysPopedomPrivilege);
				}
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<SysPopedomPrivilege> findSysPopedomPrivilegesByRoleId(String roleId) {
		// 删除该权限组对应的权限
		if (StringUtils.isNotBlank(roleId)) {
			String whereHql = " and o.id.roleId=?";
			Object[] params = { roleId };

			List<SysPopedomPrivilege> list = sysPopedomPrivilegeDao.findObjectsByConditionWithNoPage(whereHql, params);
			return list;
		}
		return null;
	}

	@Override
	public List<SysPopedomPrivilege> findSysPopedomPrivileges() {
		LinkedHashMap<String, String> orderby  = new LinkedHashMap<String, String>();
		orderby.put("o.id.roleId", "asc");
		return sysPopedomPrivilegeDao.findObjectsByConditionWithNoPage(orderby);
	}
}
