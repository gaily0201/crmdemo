package cn.gaily.crm.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import sun.security.rsa.RSASignature.MD5withRSA;

import cn.gaily.crm.domain.SysUser;
import cn.gaily.crm.util.SessionUtils;

public class SessionCheckUserFilter implements Filter {

	private List<String> list;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		list = new ArrayList<String>();
		list.add("/index.jsp");
		list.add("/image.jsp");
		list.add("/WEB-INF/page/login.jsp");
		list.add("/sys/sysUserAction_isLogin.do");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse) res;
		String path = request.getServletPath();
		//System.out.println("path  "+path);
		
		//放行list中的路径
		if(list!=null&&list.contains(path)){
			chain.doFilter(request, response);
			return;
		}
		
		//检查用户是否登录
		SysUser sysUser = SessionUtils.getSysUserFromSession(request);
		//用户登录放行，没有登录，转发到登陆界面
		if(sysUser!=null){
			chain.doFilter(request, response);
		}else {// 没有登录，转发到登录界面
			response.sendRedirect(request.getContextPath());
		}
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}


}
