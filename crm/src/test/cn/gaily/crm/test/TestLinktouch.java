package cn.gaily.crm.test;

import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.dao.LinktouchDao;
import cn.gaily.crm.dao.impl.LinktouchDaoImpl;
import cn.gaily.crm.domain.Linktouch;

public class TestLinktouch {
	public static void main(String[] args) {
		LinktouchDao linktouchDao = (LinktouchDao) ServiceProvinder.getService("linktouchDao");
		Linktouch linktouch = new Linktouch();
		linktouch.setContent("context");
		linktouchDao.save(linktouch);
	}
}
