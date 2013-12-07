<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.gaily.cn/jsp/jstl/core" prefix="gaily"  %>
<html>
<head>
<title>菜单</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/menu/css/jquery.treeview.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/menu/css/screen.css" />
<script src="${pageContext.request.contextPath}/menu/js/jquery-1.4.2.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/menu/js/jquery.treeview.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#tree").treeview({
			collapsed : true,
			animated : "medium",
			control : "#sidetreecontrol",
			persist : "location"
		});
	})
</script>

</head>
<body ondblclick="ToggleNav();">
<c:if test="${! empty applicationScope.sysMenus}">
<div id="main">
<div id="sidetree">
	<div class="treeheader">操作菜单</div>
		<div id="sidetreecontrol"><a href="?#">关闭所有</a> | <a href="?#">打开所有</a></div>
			<ul id="tree">
			    <c:forEach items="${applicationScope.sysMenus}" var="sysMenu">
			        <c:if test="${sysMenu.id.menuModule==sysMenu.id.menuPrivilege}">
			        	<gaily:checkMemu module="${sysMenu.id.menuModule}" privilege="${sysMenu.id.menuPrivilege}">
						    <li><img src="${pageContext.request.contextPath}/ui/images${sysMenu.icon}" width="17" height="17">
						    <strong>${sysMenu.menuName}</strong>
							
						    <c:forEach items="${applicationScope.sysMenus}" var="sysMenuSub">
						      <c:if test="${sysMenuSub.id.menuModule!=sysMenuSub.id.menuPrivilege
						                    &&sysMenu.id.menuModule==sysMenuSub.id.menuModule}">
						          <gaily:checkMemu module="${sysMenuSub.id.menuModule}" privilege="${sysMenuSub.id.menuPrivilege}" >
									<ul>
										  <li><img src="${pageContext.request.contextPath}/ui/images${sysMenuSub.icon}" width="17" height="17">
										  <a href="${pageContext.request.contextPath}${sysMenuSub.url}"  target="${sysMenuSub.target}">${sysMenuSub.menuName}</a></li>
									</ul>
									</gaily:checkMemu>
							  </c:if>	
							</c:forEach>
						    </li>
						</gaily:checkMemu>
				    </c:if>
			    </c:forEach>
			</ul>
		</div>
	</div>
</c:if>	

	<div id="divCollapsedNav" class="navTocColor"
		style="display:none;width:100%;height:100%;">
		<a href="javascript:ToggleNav();" title="展开导航框架" id="linkNavClosed">
			<img src="${pageContext.request.contextPath}/menu/images/toc2.gif"
			alt="展开导航框架" border="3"></img> </a>
	</div>
	<script src="${pageContext.request.contextPath}/menu/js/left.js"></script>

</body>
</html>


