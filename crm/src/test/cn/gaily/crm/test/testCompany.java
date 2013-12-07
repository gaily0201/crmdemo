package cn.gaily.crm.test;

import org.junit.Test;

import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.dao.CompanyDao;
import cn.gaily.crm.domain.Company;
import cn.gaily.crm.domain.SysUser;

public class testCompany {

	@Test
	public void test(){
		CompanyDao companyDao = (CompanyDao) ServiceProvinder.getService("companyDao");
		Company c = new Company();
		c.setCode("xxx");
		c.setName("用友政务");
		SysUser sysUser = new SysUser();
		sysUser.setId(1);
		c.setSysUser(sysUser);
		c.setShareIds("N");
		
		companyDao.save(c);
	}
}
