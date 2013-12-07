<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>联系人列表</title>
<link href="${pageContext.request.contextPath}/ui/css/style_cn.css" rel="stylesheet" type="text/css">

<script src="${pageContext.request.contextPath}/ui/js/jquery-1.4.2.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/date_input/jquery.datepick.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/date_input/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/date_input/calendar.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/js/date_input/jquery.datepick.css" type="text/css">

<script type="text/javascript">
 function forward(strURL){
     window.location=strURL;
 }
</script>
</head>
 
<body>
<table width="100%" border="0" cellspacing="0" class="tabForm">
<tr>
	<td colspan="4" class="th_head">
		<div id="menuArrow1" style="background:url(${pageContext.request.contextPath}/ui/images/down.gif) no-repeat center;float:left;">&nbsp;</div>
		<div id="menuTitle1" style="font-weight:bold">联系人搜索</div>
	</td>
</tr>
<tr>
<td>
<div id="menu1"> 
<form name="form2" method="post" action="linkman.do">
<input type="hidden" name="method" value="search">
<input type="hidden" name="cid" value="8">
<input type="hidden" name="back" value="list">
<table  width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
  	    <td width="15%" nowrap>编码：</td>
   	    <td width="12%" nowrap><input name="code" type="text" id="code" value="" style="width:110px"></td>
  	    <td width="15%" nowrap>姓名：</td>
		<td width="12%" nowrap><input name="name" type="text" id="name" value="" style="width:110px"></td>
		<td width="15%" nowrap>拼音码：</td>
		<td width="12%" nowrap><input name="pycode" type="text" id="pycode" value="" style="width:110px"></td>
  	    <td width="19%" align="center">
			<div class="control">
				<button type='button' class='button' onMouseOver="this.className='button_over';" onMouseOut="this.className='button';"  onClick="document.form2.submit();"><img src="${pageContext.request.contextPath}/ui/images/button/sousuo.png" border='0' align='absmiddle'>&nbsp;搜索</button>
      			<button type='button' class='button' onMouseOver="this.className='button_over';" onMouseOut="this.className='button';"  onClick="goRefresh()"><img src="${pageContext.request.contextPath}/ui/images/button/qingkong.png" border='0' align='absmiddle'>&nbsp;清空</button>
			</div>
        </td>
  	</tr>
  	<tr>
		<td>部门：</td>
		<td><input name="department" type="text" id="department" value="" style="width:110px"></td>
		<td>职务：</td>
		<td><input name="duty" type="text" id="duty" value="" style="width:110px"></td>
		<td>性别：</td>
		<td><select name='sex' id='sex' style='width:110px'>
<option value='' selected>------</option>
<option value='男'>男</option>
<option value='女'>女</option>
</select></td>
	</tr>
</table>
</form>
</div>
</td>
</tr>	
</table>	
<br>
<h3><img src="${pageContext.request.contextPath}/ui/images/menu/khlb.png" border="0">&nbsp;联系人列表</h3>
<div class="control">
	  <button type='button' class='button' onMouseOver="this.className='button_over';" onMouseOut="this.className='button';"  
	          onClick="forward('${pageContext.request.contextPath}/crm/linkmanAction_add.do')">
	          <img src="${pageContext.request.contextPath}/ui/images/button/xinjian.png" border='0' align='absmiddle'>&nbsp;新建</button>
	  <button type='button' class='button' onMouseOver="this.className='button_over';" onMouseOut="this.className='button';"  
	          onClick="forward('${pageContext.request.contextPath}/crm/linkmanAction_delete.do')">
	          <img src="${pageContext.request.contextPath}/ui/images/button/shanchu.png" border='0' align='absmiddle'>&nbsp;删除</button>
</div>
<!-- list -->
<div class="border">
<form name="ActionForm" method="post" action="">
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="PowerTable" class="PowerTable">
 
	<!-- title -->
	<tr>
    	<td width="4%" class="listViewThS1">
    		<input type="checkbox" name="checkall" value="" class="checkbox" onClick="CheckAll(this.checked);changeCheckCount();"></td>
  	    <td width="14%" class="listViewThS1">姓名</td>
  	    <td width="11%" class="listViewThS1">性别</td>
  	    <td width="20%" class="listViewThS1">手机</td>
        <td width="11%" class="listViewThS1">部门</td>
        <td width="12%" class="listViewThS1">职务</td>
        <td width="13%" class="listViewThS1">电子邮件</td>
        <td width="15%" class="listViewThS1">办公电话</td>
        </tr>
	<!-- data -->
 
 
	
	
	<tr>
    	<td width="4%">
    	  <input type="checkbox" name="ids" value="2" class="checkbox" onClick="changeCheckCount();"></td>
  	    <td width="14%"><a href="linkman.do?method=load&id=2">
  	    	王刚</a></td>
  	    <td width="11%">
  	    	男</td>
  	    <td width="20%">
        	&nbsp;</td>
        <td width="11%">
        	&nbsp;</td>
        <td width="12%">
        	&nbsp;</td>
        <td width="13%">
        	&nbsp;</td>
        <td width="15%">
        	&nbsp;</td>
        </tr>
	
	
	<tr>
    	<td width="4%">
    	  <input type="checkbox" name="ids" value="1" class="checkbox" onClick="changeCheckCount();"></td>
  	    <td width="14%"><a href="linkman.do?method=load&id=1">
  	    	张丹枫</a></td>
  	    <td width="11%">
  	    	男</td>
  	    <td width="20%">
        	&nbsp;</td>
        <td width="11%">
        	&nbsp;</td>
        <td width="12%">
        	&nbsp;</td>
        <td width="13%">
        	&nbsp;</td>
        <td width="15%">
        	&nbsp;</td>
        </tr>
	
	
</table>
</form>
</div>
</body>
</html>
