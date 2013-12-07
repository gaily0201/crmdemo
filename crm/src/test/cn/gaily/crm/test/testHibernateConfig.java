package cn.gaily.crm.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.gaily.crm.domain.SysUserGroup;

public class testHibernateConfig {

	@Test
	public void testHibernateConfig(){
		Configuration config = new Configuration();
		config.configure("hibernate/hibernate.cfg.xml");
		SessionFactory sf = config.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		SysUserGroup sysUserGroup = new SysUserGroup();
		sysUserGroup.setName(" 销售部");
		sysUserGroup.setIncumbent("xxx");
		sysUserGroup.setPrincipal("aaa");
		sysUserGroup.setRemark("beizhu");
		s.save(sysUserGroup);
		tx.commit();
		s.close();
	}
}
