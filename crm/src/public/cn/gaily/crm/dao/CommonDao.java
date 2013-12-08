package cn.gaily.crm.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import cn.gaily.crm.domain.SysPopedomPrivilege;
import cn.gaily.crm.domain.SysRole;
import cn.gaily.crm.domain.SysUser;
import cn.gaily.crm.domain.SysUserGroup;


public interface CommonDao<T> {

	void save(T entity);
	void update(T entity);
	T findObjectById(Serializable id);
	void deleteByIds(Serializable... ids);
	void deleteObjects(Collection<T> entities);
	
	List<T> findObjectsByConditionWithNoPage(String whereHql,
			Object[] params);
	List<T> findObjectsByConditionWithNoPage(String whereHql,
			Object[] params, LinkedHashMap<String, String> orderby);
	List<T> findObjectsByConditionWithNoPage();
	List<T> findObjectsByConditionWithNoPage(
			LinkedHashMap<String, String> orderby);
	
	List<T> findObjectsByConditionWithNoPageCache(
			String whereHql, Object[] params,LinkedHashMap<String, String> orderby);
}
