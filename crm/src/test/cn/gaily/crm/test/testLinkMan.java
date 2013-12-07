package cn.gaily.crm.test;

import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.dao.CompanyDao;
import cn.gaily.crm.dao.LinkmanDao;
import cn.gaily.crm.domain.Company;
import cn.gaily.crm.domain.Linkman;
import cn.gaily.crm.domain.SysUser;

public class testLinkMan {

	public static void main(String[] args) {
		LinkmanDao linkmanDao = (LinkmanDao) ServiceProvinder.getService("linkmanDao");
		CompanyDao companyDao = (CompanyDao) ServiceProvinder.getService("companyDao");

		Linkman linkman = new Linkman();
		Company c = new Company();
		c.setCode("xxx");
		c.setName("用友政务");
		
		SysUser sysUser = new SysUser();
		sysUser.setId(1);
		c.setSysUser(sysUser);
		c.setShareIds("N");
		
		companyDao.save(c);
		
		linkman.setCompany(c);
		linkman.setSysUser(sysUser);
		
		linkmanDao.save(linkman);
		
	}
}
