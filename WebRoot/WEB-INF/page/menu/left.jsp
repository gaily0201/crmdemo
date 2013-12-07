<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.gaily.cn/jsp/jstl/core" prefix="gaily"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>管理页面</title>

<script src="${pageContext.request.contextPath}/ui/js/prototype.lite.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/ui/js/moo.fx.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/ui/js/moo.fx.pack.js"
	type="text/javascript"></script>
<style>
body {
	font: 12px Arial, Helvetica, sans-serif;
	color: #000;
	background-color: #EEF2FB;
	margin: 0px;
}

#container {
	width: 182px;
}

H1 {
	font-size: 12px;
	margin: 0px;
	width: 182px;
	cursor: pointer;
	height: 30px;
	line-height: 20px;
}

H1 a {
	display: block;
	width: 182px;
	color: #000;
	height: 30px;
	text-decoration: none;
	moz-outline-style: none;
	background-image:
		url(${pageContext.request.contextPath}/ui/images/left/menu_bgs.gif);
	background-repeat: no-repeat;
	line-height: 30px;
	text-align: center;
	margin: 0px;
	padding: 0px;
}

.content {
	width: 182px;
	height: 26px;
}

.MM ul {
	list-style-type: none;
	margin: 0px;
	padding: 0px;
	display: block;
}

.MM li {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	list-style-type: none;
	display: block;
	text-decoration: none;
	height: 26px;
	width: 182px;
	padding-left: 0px;
}

.MM {
	width: 182px;
	margin: 0px;
	padding: 0px;
	left: 0px;
	top: 0px;
	right: 0px;
	bottom: 0px;
	clip: rect(0px, 0px, 0px, 0px);
}

.MM a:link {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image:
		url(${pageContext.request.contextPath}/ui/images/left/menu_bg1.gif);
	background-repeat: no-repeat;
	height: 26px;
	width: 182px;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	overflow: hidden;
	text-decoration: none;
}

.MM a:visited {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image:
		url(${pageContext.request.contextPath}/ui/images/left/menu_bg1.gif);
	background-repeat: no-repeat;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	height: 26px;
	width: 182px;
	text-decoration: none;
}

.MM a:active {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image:
		url(${pageContext.request.contextPath}/ui/images/left/menu_bg1.gif);
	background-repeat: no-repeat;
	height: 26px;
	width: 182px;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	overflow: hidden;
	text-decoration: none;
}

.MM a:hover {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	font-weight: bold;
	color: #006600;
	background-image:
		url(${pageContext.request.contextPath}/ui/images/left/menu_bg2.gif);
	background-repeat: no-repeat;
	text-align: center;
	display: block;
	margin: 0px;
	padding: 0px;
	height: 26px;
	width: 182px;
	text-decoration: none;
}
</style>
</head>

<body>

	<table width="100%" height="280" border="0" cellpadding="0"
		cellspacing="0" bgcolor="#EEF2FB">
		<tr>
			<td width="182" valign="top"><c:if
					test="${! empty applicationScope.sysMenus}">
					<div id="container">
						<c:forEach items="${applicationScope.sysMenus}" var="sysMenu">
							<c:if test="${sysMenu.id.menuModule==sysMenu.id.menuPrivilege}">
								<gaily:checkMemu module="${sysMenu.id.menuModule}"
									privilege="${sysMenu.id.menuPrivilege}">
									<h1 class="type">
										<strong><a  href="javascript:void(0)">${sysMenu.menuName}</a></strong>
									</h1>
									<div class="content">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td><img
													src="${pageContext.request.contextPath}/ui/images/menu_topline.gif"
													width="182" height="5" />
												</td>
											</tr>
										</table>
										<c:forEach items="${applicationScope.sysMenus}"
											var="sysMenuSub">
											<c:if
												test="${sysMenuSub.id.menuModule!=sysMenuSub.id.menuPrivilege
						                    &&sysMenu.id.menuModule==sysMenuSub.id.menuModule}">
												<gaily:checkMemu module="${sysMenuSub.id.menuModule}"
													privilege="${sysMenuSub.id.menuPrivilege}">
													<ul class="MM">
														<li><a
															href="${pageContext.request.contextPath}${sysMenuSub.url}"
															target="${sysMenuSub.target}">${sysMenuSub.menuName}</a>
														</li>
													</ul>
												</gaily:checkMemu>
											</c:if>
										</c:forEach>
									</div>
								</gaily:checkMemu>
							</c:if>
						</c:forEach>
					</div>
				</c:if> <script type="text/javascript">
					var contents = document.getElementsByClassName('content');
					var toggles = document.getElementsByClassName('type');

					var myAccordion = new fx.Accordion(toggles, contents, {
						opacity : true,
						duration : 400
					});
					myAccordion.showThisHideOpen(contents[0]);
				</script></td>
		</tr>
	</table>
</body>
</html>
