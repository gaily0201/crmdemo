<%@ page language="java" pageEncoding="GBK" contentType="text/html; charset=utf-8"%>
<html>
<head>
<title>客户关系管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>

<frameset rows="80,*" cols="*" frameborder=0 border="0" framespacing="0">
  <frame src="${pageContext.request.contextPath}/sys/menuAction_top.do" name="topFrame"  id="topFrame" scrolling="NO" noresize >
  <frameset cols="180,*" frameborder="0" border="0" framespacing="0" id="frmstOuter">
    <frame src="${pageContext.request.contextPath}/sys/menuAction_left.do"  name="leftFrame"  id="leftFrame" 
                  scrolling="auto" noresize frameborder="0" framespacing="1px" bordercolor="#4faad8">
    <frame src="${pageContext.request.contextPath}/sys/menuAction_right.do" name="rightFrame" id="rightFrame">
  </frameset>
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>
