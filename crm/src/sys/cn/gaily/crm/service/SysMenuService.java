package cn.gaily.crm.service;

import java.util.List;

import cn.gaily.crm.domain.SysMenu;

public interface SysMenuService {

	/**
	 * 查询所有的菜单
	 * @return
	 */
	List<SysMenu> findAllSysMenus();

	/**
	 * 查询所有的菜单，带缓存
	 * @return
	 */
	List<SysMenu> findAllSysMenusCache();

}
