package cn.gaily.crm.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hamcrest.core.Is;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.hql.ast.tree.FromClause;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import cn.gaily.crm.dao.CommonDao;
import cn.gaily.crm.domain.SysRole;
import cn.gaily.crm.domain.SysUser;
import cn.gaily.crm.util.GenericClass;

public class CommonDaoImpl<T> implements CommonDao<T> {

	@Resource(name="hibernateTemplete")
	private HibernateTemplate hibernateTemplate;
	
	//获取父类的泛型类型
	private Class entityClass = GenericClass.getGenericClass(this.getClass());
	
	/**
	 * 保存
	 */
	@Override
	public void save(T entity) {
		this.hibernateTemplate.save(entity);
	}

	/**
	 * 更新 
	 */
	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.update(entity);
	}

	/**
	 * 根据id查找实体
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findObjectById(Serializable id) {
		// TODO Auto-generated method stub
		if(id==null){
			throw new RuntimeException("您要查找的["+id+"]不存在！");
		}
		
		return (T) this.hibernateTemplate.get(entityClass, id);
	}

	/**
	 * 根据id进行批量删除
	 */
	@Override
	public void deleteByIds(Serializable... ids) {
		if(ids!=null&&ids.length>0){
			for(Serializable id : ids){
				Object entity = this.hibernateTemplate.get(entityClass, id);
				if(entity==null){
					throw new RuntimeException("您要删除的["+id+"]不存在！");
				}
				this.hibernateTemplate.delete(entity);
			}
		}
	}

	/**
	 * 删除集合
	 */
	@Override
	public void deleteObjects(Collection<T> entities) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.deleteAll(entities);
	}

	/**
	 * 根据条件进行查询
	 */
	@Override
	public List<T> findObjectsByConditionWithNoPage(String whereHql,
			final Object[] params, LinkedHashMap<String, String> orderby) {
		
		//产生select语句 SELECT o FROM SysUserGroup o WHERE 
		String hql = "select o from "+ entityClass.getSimpleName()+" o where 1=1 ";
		//System.out.println(hql);
		
		//加入查询条件
		if(StringUtils.isNotBlank(whereHql)){
			hql = hql + whereHql;
		}
		//System.out.println(hql);
		
		//加入排序规则	
		String orderbystr = buildOrderBy(orderby);
		hql = hql + orderbystr;
		
		final String fhql = hql;
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) this.hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(fhql);
				setParams(query, params);				
				return query.list();
			}
		});	
		return list;
	}

	@Override
	public List<T> findObjectsByConditionWithNoPage(String whereHql, Object[] params) {
		return this.findObjectsByConditionWithNoPage(whereHql, params, null);
	}

	/**
	 * 设置hql语句参数
	 * @param query
	 * @param params
	 */
	public void setParams(Query query, Object[] params) {
		// TODO Auto-generated method stub
		if(params!=null && params.length>0){
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
			}
		}
	}
	
	
	/**
	 * 处理排序条件
	 * @param orderby
	 * @return
	 */
	public String buildOrderBy(LinkedHashMap<String, String> orderby) {
		StringBuffer sb = new StringBuffer("");
		if(orderby!=null&&!orderby.isEmpty()){
			sb.append(" order by ");
			for(Map.Entry<String, String> order : orderby.entrySet()){
				sb.append(order.getKey()+" " +order.getValue()+",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}

	@Override
	public List<T> findObjectsByConditionWithNoPage() {
		// TODO Auto-generated method stub
		return this.findObjectsByConditionWithNoPage(null, null, null);
	}

	@Override
	public List<T> findObjectsByConditionWithNoPage(
			LinkedHashMap<String, String> orderby) {
		// TODO Auto-generated method stub
		return this.findObjectsByConditionWithNoPage(null, null, orderby);
	}

	
	


}
