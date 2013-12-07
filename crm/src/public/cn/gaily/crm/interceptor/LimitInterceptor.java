package cn.gaily.crm.interceptor;

import java.lang.reflect.Method;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import cn.gaily.crm.annotation.Limit;
import cn.gaily.crm.container.ServiceProvinder;
import cn.gaily.crm.domain.SysPopedomPrivilege;
import cn.gaily.crm.domain.SysUser;
import cn.gaily.crm.service.SysPopedomPrivilegeService;
import cn.gaily.crm.util.SessionUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;


public class LimitInterceptor extends MethodFilterInterceptor {
	private static final long serialVersionUID = 1L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {

		//获取请求的action对象
		Object action = invocation.getAction();
		
		//获取请求的方法的名称
		String methodName = invocation.getProxy().getMethod();
		
		//获取action中的方法的封装类 
		Method method = action.getClass().getMethod(methodName, null);
		//System.out.println(method);
		//获取request对象
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//检查注解
		boolean flag = isCheckLimit(request,method);
		if(!flag){
			//没有权限
			return "popmsg_popedom";
		}
		//有权限
		String returnvalue = invocation.invoke();
		
		return returnvalue;
	}

	/**
	 * 检测权限
	 * @param request
	 * @param method
	 * @return
	 */
	private boolean isCheckLimit(HttpServletRequest request, Method method) {
		if(method ==null){
			return false;
		}
		//获取当前的登录 用户
		SysUser sysUser = SessionUtils.getSysUserFromSession(request);
		if(sysUser==null){
			return false;
		}
		//获取当前登录用户的权限组id
		if(sysUser.getSysRole()==null){
			return false;
		}
		String roleId = sysUser.getSysRole().getId();
		
		//处理注解
		boolean isAnnotationPresent = method.isAnnotationPresent(Limit.class);
		//不存在注解
		if(!isAnnotationPresent){
			return false;
		}
		//存在注解
		Limit limit = method.getAnnotation(Limit.class);
		
		//获取注解上的值
		String module = limit.module();
		String privilege = limit.privilege();
		
		//如果
		boolean flag = false;
		
		//查询
		SysPopedomPrivilegeService sysPopedomPrivilegeService = (SysPopedomPrivilegeService) ServiceProvinder.getService("sysPopedomPrivilegeService");
		List<SysPopedomPrivilege> list  = sysPopedomPrivilegeService.findSysPopedomPrivileges();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				SysPopedomPrivilege s = list.get(i);
				if(s!=null){
					if(roleId.equals(s.getId().getRoleId())&&module.equals(s.getId().getPopedomModule())
							&&privilege.equals(s.getId().getPopedomPrivilege())){
						flag = true;
						break;
					}
				}
			}
		}
		
		return flag;
	}
}









