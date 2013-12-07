package cn.gaily.crm.test;

import org.junit.Test;

import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.dao.SysUserDao;
import cn.gaily.crm.domain.SysRole;
import cn.gaily.crm.domain.SysUser;
import cn.gaily.crm.domain.SysUserGroup;
import cn.gaily.crm.util.MD5keyBean;

public class testSysUser {

	@Test
	public void saveSysUser(){
		
		SysUserDao sysUserDao = (SysUserDao) ServiceProvinder.getService("sysUserDao");
		SysUser sysUser = new SysUser();
		
		sysUser.setName("xiaohuan");
		sysUser.setCnname("普通管理员");
		
		SysUserGroup sysUserGroup = new SysUserGroup();
		sysUserGroup.setId(10);
		sysUser.setSysUserGroup(sysUserGroup);
		
		SysRole sysRole = new SysRole();
		sysRole.setId("402882e74237b685014237b70ee50001");
		sysUser.setSysRole(sysRole);
		
		//密码
		MD5keyBean m = new MD5keyBean();
		String md5 = m.getkeyBeanofStr("123456");
		sysUser.setPassword(md5);
		
		sysUserDao.save(sysUser);
	}
}
