package cn.gaily.crm.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.gaily.crm.bean.ReportBean;
import cn.gaily.crm.dao.ReportDao;

@Repository(value="reportDao")
public class ReportDaoImpl extends CommonDaoImpl<ReportBean> implements ReportDao{
	
	@Resource(name="hibernateTemplete")
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public List<ReportBean> findReportBeans() {
		@SuppressWarnings("unchecked")
		List<ReportBean> list = (List<ReportBean>) this.hibernateTemplate.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				//select o.grade,count(o.grade) from Company o group by o.grade;
				String hql = "select new cn.gaily.crm.bean.ReportBean(o.grade,count(o.grade)) from Company o group by o.grade";
				Query query = session.createQuery(hql);
				
				return query.list();
			}
		});
		return list;
	}

}
