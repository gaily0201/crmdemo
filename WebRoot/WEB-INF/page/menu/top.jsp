<%@page import="org.apache.commons.lang.time.DateFormatUtils,java.util.Date"%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>客户关系管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/menu/css/top.css" rel="stylesheet" type="text/css">
</head>
<%
	String curDate = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
 %>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="top">
  <tr> 
    <td class="logo">
        <div class="subNav"><s:property value="%{#session.sysUserKey.cnname}"/>，欢迎您！今天是：<%=curDate %> | <a href="${pageContext.request.contextPath}/index.jsp">退出
           </a> | <a href="#">帮助</a>
        </div>
    </td>
  </tr>
</table>
</body>
</html>
