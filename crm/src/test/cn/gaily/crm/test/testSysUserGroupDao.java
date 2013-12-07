package cn.gaily.crm.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.dao.SysUserGroupDao;
import cn.gaily.crm.domain.SysUserGroup;
import cn.gaily.crm.service.SysUserGroupService;

public class testSysUserGroupDao {

	@Test
	public void testSave(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		SysUserGroupDao sysUserGroupDao = (SysUserGroupDao) context.getBean("sysUserGroupDao");
		SysUserGroup sysUserGroup = new SysUserGroup();
		sysUserGroup.setName("销售部");
		sysUserGroup.setPrincipal("xxx");
		sysUserGroup.setIncumbent("ttt");
		sysUserGroupDao.save(sysUserGroup);
		//hibernate.cfg.xml  <property name="hibernate.connection.autocommit">true</property>  
	}
	
	@Test
	public void testUpdate(){
		
		SysUserGroupDao sysUserGroupDao = (SysUserGroupDao) ServiceProvinder.getService("sysUserGroupDao");
		SysUserGroup sysUserGroup = new SysUserGroup();
		sysUserGroup.setId(10);
		sysUserGroup.setName("销售部01");
		
		sysUserGroupDao.update(sysUserGroup);
	}
	
	@Test
	public void testFindSysUserGroupById(){
		SysUserGroupDao sysUserGroupDao = (SysUserGroupDao) ServiceProvinder.getService("sysUserGroupDao");
		Serializable id = 11;
		SysUserGroup sysUserGroup = sysUserGroupDao.findObjectById(id);
	}
	
	@Test
	public void testDeleteByIds(){
		SysUserGroupDao sysUserGroupDao = (SysUserGroupDao) ServiceProvinder.getService("sysUserGroupDao");
		Serializable[] ids = {};
		sysUserGroupDao.deleteByIds(ids);
	}
	
	@Test
	public void testDeleteCollections(){
		SysUserGroupDao sysUserGroupDao = (SysUserGroupDao) ServiceProvinder.getService("sysUserGroupDao");
		SysUserGroup s1 = new SysUserGroup();
		s1.setId(12);
		s1.setName("销售部01");
		SysUserGroup s2 = new SysUserGroup();
		s2.setId(13);
		s2.setName("销售部01");
		
		List<SysUserGroup> list = new ArrayList<SysUserGroup>();
		list.add(s1);
		list.add(s2);
		
		sysUserGroupDao.deleteObjects(list);
	}
	
}
