package cn.gaily.crm.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;

import org.apache.commons.lang.StringUtils;

import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.domain.SysMenuPrivilege;
import cn.gaily.crm.domain.SysUser;
import cn.gaily.crm.service.SysMenuPrivilegeService;
import cn.gaily.crm.util.SessionUtils;

public class CheckMenuTag implements SimpleTag {

	
	private PageContext pageContext;
	
	//标签体
	private JspFragment jspFragment;
	
	//<gaily:checkMenu module="" privilege="">
	private String module;
	private String privilege;
	
	@Override
	public void doTag() throws JspException, IOException {
		
		//获取request对象
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		//获取当前登录用户
		SysUser sysUser = SessionUtils.getSysUserFromSession(request);
		if(sysUser==null){
			return ;
		}
		if(sysUser.getSysRole()==null){
			return ;
		}
		//获取当前登录用户对应的权限组id
		String roleId = sysUser.getSysRole().getId();
		//获取菜单操作权限的业务层 sys_menu_privilege
		SysMenuPrivilegeService sysMenuPrivilegeService = (SysMenuPrivilegeService) ServiceProvinder
				.getService("sysMenuPrivilegeService");
		//获取菜单操作权限表中的所有数据  sys_menu_privilege 返回值为list集合
		List<SysMenuPrivilege> list = sysMenuPrivilegeService.findAllSysMenuPrivileges();

		//遍历集合
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				SysMenuPrivilege s = list.get(i);
				if(StringUtils.isNotBlank(roleId)&&StringUtils.isNotBlank(module)&&StringUtils.isNotBlank(privilege)){
					//比对权限组id+模块名称+操作名称
					if(roleId.equals(s.getId().getRoleId())&&module.equals(s.getId().getMenuModule())
							&&privilege.equals(s.getId().getMenuPrivilege())){
						//如果集合中存在,输出标签体
						//this.jspFragment.invoke(null);
						this.jspFragment.invoke(pageContext.getOut());
						break;
					}
				}
			
				
				
			}
		}
	}

	@Override
	public JspTag getParent() {
		return null;
	}

	@Override
	public void setJspBody(JspFragment jspFragment) {
		this.jspFragment = jspFragment;
	}

	@Override
	public void setJspContext(JspContext jspContext) {
		this.pageContext = (PageContext) jspContext;
	}

	@Override
	public void setParent(JspTag jspTag) {
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

}
