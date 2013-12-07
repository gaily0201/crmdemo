package cn.gaily.crm.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import cn.gaily.crm.domain.SysUser;

public class SessionUtils {

	/**
	 * 处理验证码
	 * @param request
	 * @return
	 */
	public static boolean isCheckNum(HttpServletRequest request) {

		//得到已经存在的session
		HttpSession session = request.getSession(false);
		
		if(session==null){
			return false;
		}
		
		//获得存在session中的验证码
		String check_number_key = (String) session.getAttribute("CHECK_NUMBER_KEY");
		if(StringUtils.isBlank(check_number_key)){
			return false;
		}
		
		//得到用户输入的验证码
		String saved  = (String) request.getParameter("checkNum");
		if(StringUtils.isBlank(saved)){
			return false;
		}
		
		return check_number_key.equalsIgnoreCase(saved);
	}

	/**
	 * 保存当前登录用户的信息到session中
	 * @param request
	 * @param sysUser
	 */
	public static void setSysUserToSession(HttpServletRequest request,
			SysUser sysUser) {

		if(sysUser==null){
			return;
		}
		request.getSession().setAttribute("sysUserKey", sysUser);
		
	}

	/**
	 * 从session中获取当前登录 用户的信息
	 * @param request
	 * @return
	 */
	public static SysUser getSysUserFromSession(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		if(session==null){
			return null;
		}
		return (SysUser) session.getAttribute("sysUserKey");
		
	}

	
}
