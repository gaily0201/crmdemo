<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>联系人列表</title>
<link href="${pageContext.request.contextPath}/ui/css/style_cn.css"
	rel="stylesheet" type="text/css">

<script src="${pageContext.request.contextPath}/ui/js/jquery-1.4.2.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/ui/js/date_input/jquery.datepick.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/ui/js/date_input/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/ui/js/date_input/calendar.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/ui/js/date_input/jquery.datepick.css"
	type="text/css">

<script type="text/javascript">
	function forward(strURL) {
		window.location = strURL;
	}
</script>
</head>

<body>
	<table width="100%" border="0" cellspacing="0" class="tabForm">
		<tr>
			<td colspan="4" class="th_head">
				<div id="menuArrow1"
					style="background:url(${pageContext.request.contextPath}/ui/images/down.gif) no-repeat center;float:left;">&nbsp;</div>
				<div id="menuTitle1" style="font-weight:bold">联系人搜索</div></td>
		</tr>
		<tr>
			<td>
				<div id="menu1">
					<s:form name="form1" namespace="/crm" action="linkmanAction_list.do">
						<input type="hidden" name="method" value="search"> <input
							type="hidden" name="cid" value="8"> <input type="hidden"
							name="back" value="list">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="12%" nowrap>姓名：</td>
								<td width="12%" nowrap><input name="name" type="text"
									id="name" value="" style="width:110px">
								</td>
								<td width="12%" nowrap>拼音码：</td>
								<td width="12%" nowrap><input name="pycode" type="text"
									id="pycode" value="" style="width:110px">
								</td>
								<td width="12%" nowrap>所属公司：</td>
								<td width="16%" nowrap><s:if
										test="#request.companySelect!=null">
										<s:select list="#request.companySelect" id='companyId'
											name="companyId" cssStyle='width:90%' headerKey=""
											headerValue="--------" listKey="id" listValue="name">
										</s:select>
									</s:if></td>
								<td width="19%" align="center">
									<div class="control">
										<button type='button' class='button'
											onMouseOver="this.className='button_over';"
											onMouseOut="this.className='button';"
											onClick="document.forms[0].submit()">
											<img
												src="${pageContext.request.contextPath}/ui/images/button/sousuo.png"
												border='0' align='absmiddle'>&nbsp;搜索
										</button>
										<button type='button' class='button'
											onMouseOver="this.className='button_over';"
											onMouseOut="this.className='button';" onClick="goRefresh()">
											<img
												src="${pageContext.request.contextPath}/ui/images/button/qingkong.png"
												border='0' align='absmiddle'>&nbsp;清空
										</button>
									</div></td>
							</tr>
							<tr>
								<td>部门：</td>
								<td><input name="department" type="text" id="department"
									value="" style="width:110px">
								</td>
								<td>职务：</td>
								<td><input name="duty" type="text" id="duty" value=""
									style="width:110px">
								</td>
								<td>性别：</td>
								<td><select name='sex' id='sex' style='width:110px'>
										<option value='' selected>------</option>
										<option value='男'>男</option>
										<option value='女'>女</option>
								</select></td>
							</tr>
						</table>
					</s:form>
				</div></td>
		</tr>
	</table>
	<br>
	<h3>
		<img src="${pageContext.request.contextPath}/ui/images/menu/khlb.png"
			border="0">&nbsp;联系人列表
	</h3>
	<div class="control">
		<button type='button' class='button'
			onMouseOver="this.className='button_over';"
			onMouseOut="this.className='button';"
			onClick="forward('${pageContext.request.contextPath}/crm/linkmanAction_add.do')">
			<img
				src="${pageContext.request.contextPath}/ui/images/button/xinjian.png"
				border='0' align='absmiddle'>&nbsp;新建
		</button>
		<button type='button' class='button'
			onMouseOver="this.className='button_over';"
			onMouseOut="this.className='button';"
			onClick="document.forms[1].submit()">
			<img
				src="${pageContext.request.contextPath}/ui/images/button/shanchu.png"
				border='0' align='absmiddle'>&nbsp;删除
		</button>
	</div>
	<!-- list -->
	<div class="border">
		<s:form method="post" action="linkmanAction_delete.do" namespace="/crm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="PowerTable" class="PowerTable">
				<!-- title -->
				<tr>
					<td width="4%" class="listViewThS1">
						<s:checkbox name="checkall" id="checkall" value="" cssClass="checkbox" onclick="checkAll()"/>
					<br></td>
					<td width="10%" class="listViewThS1">姓名</td>
					<td width="10%" class="listViewThS1">性别</td>
					<td width="15%" class="listViewThS1">手机</td>
					<td width="18%" class="listViewThS1">所属公司</td>
					<td width="10%" class="listViewThS1">部门</td>
					<td width="10%" class="listViewThS1">职务</td>
					<td width="10%" class="listViewThS1">办公电话</td>
				</tr>
				<!-- data -->
				<s:if test="#request.linkmans!=null">
				<s:iterator value="#request.linkmans" var="linkman">
				<tr>
					<td width="4%">
						<s:checkbox name="ids" fieldValue="%{#linkman.id}" cssClass="checkbox" onclick="changeCheckCount()"/>
					</td>
					<td width="10%">
						<a href="${pageContext.request.contextPath}/crm/linkmanAction_edit.do?id=<s:property value="#linkman.id"/>">
						<s:property value="%{#linkman.name}"/></a>
					</td>
					<td width="10%"><s:property value="%{#linkman.sex}"/></td>
					<td width="15%"><s:property value="%{#linkman.mobile}"/></td>
					<td width="18%"><s:property value="%{#linkman.company.name}"/></td>
					<td width="10%"><s:property value="%{#linkman.department}"/></td>
					<td width="10%"><s:property value="%{#linkman.duty}"/></td>
					<td width="10%"><s:property value="%{#linkman.officeTel}"/></td>
				</tr>
				</s:iterator>
		</s:if>
			</table>
		</s:form>
	</div>
</body>
<script type="text/javascript">
   function changeCheckCount(){
       var count=0;
	   $("input[type='checkbox'][name='ids']").each(function(index,data){
            if(this.checked){
            	count++;  
            }
	   });
	   $("#slt_ids_count2").empty();
 	   $("#slt_ids_count2").append(count);

       if(count== $("input[type='checkbox'][name='ids']").length){
    	   $("#checkall").attr("checked","checked");
       }else{
    	   $("#checkall").attr("checked",null);
       }
   }
   
   function  checkAll(){
      if($("#checkall")[0].checked){
    	  $("input[type='checkbox'][name='ids']").attr("checked","checked");
    	  $("#slt_ids_count2").empty();
    	  $("#slt_ids_count2").append($("input[type='checkbox'][name='ids']").length);
      }else{
    	  $("input[type='checkbox'][name='ids']").attr("checked",null);
    	  $("#slt_ids_count2").empty();
    	  $("#slt_ids_count2").append(0);
      }
   }
 </script>

</html>
