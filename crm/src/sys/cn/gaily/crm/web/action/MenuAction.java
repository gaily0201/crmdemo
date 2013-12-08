package cn.gaily.crm.web.action;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.domain.SysMenu;
import cn.gaily.crm.service.SysMenuPrivilegeService;
import cn.gaily.crm.service.SysMenuService;

@SuppressWarnings("serial")
public class MenuAction extends BaseAction {

	// 获取菜单功能的业务层对象
	private SysMenuService sysMenuService = (SysMenuService) ServiceProvinder
			.getService("sysMenuService");
	// 获取菜单权限的业务层对象
	private SysMenuPrivilegeService sysMenuPrivilegeService = (SysMenuPrivilegeService) ServiceProvinder
			.getService("sysMenuPrivilegeService");

	public String top() {
		// System.out.println("main");
		return "top";
	}

	public String left() {
		List<SysMenu> sysMenus = sysMenuService.findAllSysMenusCache();
		ServletContext sc = ServletActionContext.getServletContext();
		sc.setAttribute("sysMenus", sysMenus);
		return "left";
	}

	public String right(){
		return "right";
	}
}
