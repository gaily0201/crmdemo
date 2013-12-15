<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>联系记录</title>
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
				<div id="menuTitle1" style="font-weight:bold">联系记录搜索</div>
			</td>
		</tr>
		<tr>
			<td>
				<div id="menu1">
					<s:form action="linktouchAction_list.do" namespace="/crm">
						<s:hidden name="id" />
						<s:hidden name="ownerUserId" />
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%" nowrap>联系人：</td>
								<td width="12%" nowrap><s:if
										test="%{#request.linkmansSelect!=null}">
										<s:select list="%{#request.linkmansSelect}" id='linkmanId'
											name='linkmanId' cssStyle="width:120px" listKey="name"
											listValue="name" headerKey="" headerValue="-------">
										</s:select>
									</s:if>
								<td width="10%" nowrap>联系方式：</td>
								<td width="12%" nowrap><s:if
										test="%{#request.linkFashionsSelect!=null}">
										<s:select list="%{#request.linkFashionsSelect}"
											id='linkFashion' name='linkFashion' cssStyle="width:120px"
											listKey="value" listValue="value" headerKey=""
											headerValue="-------">
										</s:select>
									</s:if></td>
								<td width="10%" nowrap>联系类别：</td>
								<td width="12%" nowrap><s:if
										test="%{#request.linkTypesSelect!=null}">
										<s:select list="%{#request.linkTypesSelect}" id='linkType'
											name='linkType' cssStyle="width:120px" listKey="value"
											listValue="value" headerKey="" headerValue="-------">
										</s:select>
									</s:if></td>
								<td width="18%" align="center" nowrap>
									<div class="control">
										<button type='button' class='button'
											onMouseOver="this.className='button_over';"
											onMouseOut="this.className='button';"
											onClick="document.forms[0].submit();">
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
									</div>
								</td>
							</tr>
							<tr>
								<td width="10%" nowrap="nowrap">业务员：</td>
								<td><s:if test="%{#request.userNamesSelect!=null}">
										<s:select list="%{#request.userNamesSelect}" id='userNameId'
											name='userNameId' cssStyle="width:85%" listKey="cnname"
											listValue="cnname" headerKey="" headerValue="-------">
										</s:select>
									</s:if>
								</td>
								<td width="1%" nowrap="nowrap">联系时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;从</td>
								<td colspan="1"><s:textfield id="beginDate"
										name="beginDate" cssStyle="width:85px"
										cssClass="dateClassStyle" />
								</td>
								<td>至&nbsp; <s:textfield id="endDate" name="endDate"
										cssStyle="width:85px" cssClass="dateClassStyle" />
								</td>
							</tr>
						</table>
					</s:form>
				</div>
			</td>
		</tr>
	</table>
	<br>
	<h3>
		<img src="${pageContext.request.contextPath}/ui/images/menu/khlb.png"
			border="0">&nbsp;联系记录列表
	</h3>
	<div class="control">
		<button type='button' class='button'
			onMouseOver="this.className='button_over';"
			onMouseOut="this.className='button';"
			onClick="forward('${pageContext.request.contextPath}/crm/linktouchAction_add.do')">
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
		<s:form name="ActionForm" action="linktouchAction_delete.do" namespace="/crm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				id="PowerTable" class="PowerTable">
				<tr>
					<td width="3%" class="listViewThS1"><s:checkbox
							name="checkall" id="checkall" value="" cssClass="checkbox"
							onclick="checkAll()" /></td>
					<td width="35%" class="listViewThS1">联系记录</td>
					<td width="10%" class="listViewThS1">联系时间</td>
					<td width="12%" class="listViewThS1">联系人</td>
					<td width="16%" class="listViewThS1">联系方式</td>
					<td width="12%" class="listViewThS1">业务员</td>
					<td width="12%" class="listViewThS1">下次联系时间</td>
				</tr>
				
				<s:if test="#request.linktouchs!=null">
				<s:iterator value="#request.linktouchs" var="linktouch">
					<tr>
						<td><s:checkbox name="ids" fieldValue="%{#linktouch.id}"
								cssClass="checkbox" onclick="changeCheckCount()" /></td>
						<td>
							<a href="linktouchAction_edit.do?id=<s:property value="#linktouch.id"/>">
								<s:property value="%{#linktouch.content}"/>
							</a>
						</td>
						<td><s:property value="%{#linktouch.linkTime}"/></td>
						<td><s:property value="%{#linktouch.linkman.name}"/></td>
						<td><s:property value="%{#linktouch.linkFashion}"/></td>
						<td><s:property value="%{#linktouch.userName}"/></td>
						<td><s:property value="%{#linktouch.nextTouchDate}"/></td>
					</tr>
					</s:iterator>
				</s:if>
			</table>
		</s:form>
	</div>
</body>

<script type="text/javascript">
	function changeCheckCount() {
		var count = 0;
		$("input[type='checkbox'][name='ids']").each(function(index, data) {
			if (this.checked) {
				count++;
			}
		});
		$("#slt_ids_count2").empty();
		$("#slt_ids_count2").append(count);

		if (count == $("input[type='checkbox'][name='ids']").length) {
			$("#checkall").attr("checked", "checked");
		} else {
			$("#checkall").attr("checked", null);
		}
	}

	function checkAll() {
		if ($("#checkall")[0].checked) {
			$("input[type='checkbox'][name='ids']").attr("checked", "checked");
			$("#slt_ids_count2").empty();
			$("#slt_ids_count2").append(
					$("input[type='checkbox'][name='ids']").length);
		} else {
			$("input[type='checkbox'][name='ids']").attr("checked", null);
			$("#slt_ids_count2").empty();
			$("#slt_ids_count2").append(0);
		}
	}
</script>
</html>
