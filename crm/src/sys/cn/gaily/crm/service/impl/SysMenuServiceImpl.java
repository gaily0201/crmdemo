package cn.gaily.crm.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gaily.crm.dao.SysMenuDao;
import cn.gaily.crm.domain.SysMenu;
import cn.gaily.crm.service.SysMenuService;

@Transactional(readOnly=true)
@Service(value="sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

	@Resource(name="sysMenuDao")
	private SysMenuDao sysMenuDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<SysMenu> findAllSysMenus() {
	
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.sort", "asc");
		
		return sysMenuDao.findObjectsByConditionWithNoPage(orderby);
	}

	@Override
	@Transactional(readOnly=true)
	public List<SysMenu> findAllSysMenusCache() {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.sort", "asc");
		return sysMenuDao.findObjectsByConditionWithNoPageCache(null,null,orderby);
	}

	
	
}
