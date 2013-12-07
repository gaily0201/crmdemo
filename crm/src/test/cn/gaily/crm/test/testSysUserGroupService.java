 package cn.gaily.crm.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.container.ServiceProvinderCore;
import cn.gaily.crm.domain.SysUserGroup;
import cn.gaily.crm.service.SysUserGroupService;

public class testSysUserGroupService {

	@Test
	public void testSaveSysUserGroupService(){
		SysUserGroupService sysUserGroupService = (SysUserGroupService) ServiceProvinder.getService("sysUserGroupService");
		SysUserGroup sysUserGroup = new SysUserGroup();
		sysUserGroup.setName("销售部");
		sysUserGroup.setPrincipal("xxx");
		sysUserGroup.setIncumbent("ccc");
		sysUserGroupService.saveUserGroup(sysUserGroup);
		
	}
	
	
/*	@Test
	public void testSaveSysUserGroupService(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		ISysUserGroupService iSysUserGroupService = (ISysUserGroupService) context.getBean("sysUserGroupService");
		SysUserGroup sysUserGroup = new SysUserGroup();
		sysUserGroup.setName("销售部");
		sysUserGroup.setPrincipal("xxx");
		sysUserGroup.setIncumbent("bbb");
		iSysUserGroupService.saveUserGroup(sysUserGroup);
		
	}*/
	
	/**
	 * 模拟Action中的查询测试
	 */
	@Test
	public void testFindObjectByConditionsWithNoPage(){
		SysUserGroupService sysUserGroupService = (SysUserGroupService) ServiceProvinder.getService("sysUserGroupService");
		
		String name ="销售部";
		String principal = "xxx";
		//List<SysUserGroup> list = sysUserGroupService.findSysUserGroups(name, principal);
		
	}
	
}
