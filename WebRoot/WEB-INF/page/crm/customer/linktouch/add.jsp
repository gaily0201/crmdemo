<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>填写拜访记录</title>
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

</head>

<body>
	<s:form name="ActionForm" action="linktouchAction_save.do" namespace="/crm" onSubmit="check();">
		<s:hidden name="id" />
		<div class="control">
			<button type='button' class='button'
				onMouseOver="this.className='button_over';"
				onMouseOut="this.className='button';"
				onClick="document.forms[0].submit()">
				<img
					src="${pageContext.request.contextPath}/ui/images/button/baocun.png"
					border='0' align='absmiddle'>&nbsp;保存
			</button>
			<button type='button' class='button'
				onMouseOver="this.className='button_over';"
				onMouseOut="this.className='button';"
				onClick="window.history.go(-1)">
				<img
					src="${pageContext.request.contextPath}/ui/images/button/fanhui.png"
					border='0' align='absmiddle'>&nbsp;返回
			</button>
		</div>

		<table width="100%" border="0" cellspacing="0" class="tabForm">
			<tr>
				<th colspan="4" class="th_head">
					<div id="menuArrow1"
						style="background:url(${pageContext.request.contextPath}/ui/images/down.gif) no-repeat center;float:left;">&nbsp;</div>
					<div id="menuTitle1" style="font-weight:bold">基本信息</div></th>
			</tr>
			<tr>
				<td>
				<div id="menu1">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="16%">联系人：</td>
								<td width="34%">
									<s:if test="%{#request.linkmansSelect!=null}">
										<s:select list="%{#request.linkmansSelect}" id='linkmanId'
											name='linkmanId' cssStyle='width:90%'
											listKey="id" listValue="name" 
											headerKey="" headerValue="-------"/>
									</s:if>
								</td>
								<td width="16%">联系方式：</td>
								<td width="34%">
								<s:if test="%{#request.linkFashionsSelect!=null}">
										<s:select list="%{#request.linkFashionsSelect}"
											id='linkFashion' name='linkFashion' cssStyle="width:90%"
											listKey="value" listValue="value" headerKey=""
											headerValue="-------">
										</s:select>
									</s:if>
								</td>
							</tr>
							<tr>
								<td width="16%">联系时间：</td>
								<td width="34%">
								<s:textfield id="linkTime" name="linkTime"
										cssStyle="width:90%" cssClass="dateClassStyle" />
								</td>
								<td width="16%">联系类别：</td>
								<td width="34%"><s:if
										test="%{#request.linkTypesSelect!=null}">
										<s:select list="%{#request.linkTypesSelect}" id='linkType'
											name='linkType' cssStyle="width:90%" listKey="value"
											listValue="value" headerKey="" headerValue="-------">
										</s:select>
									</s:if>
								</td>
							</tr>
							<tr>
								<td valign="top">联系记录：</td>
								<td colspan="3"><s:textarea id="content" name="content"
										cssStyle="width:96%" rows="4" />
								</td>
							</tr>

							<tr>
								<td>下次联系时间：</td>
								<td><s:textfield id="nextTouchDate" name="nextTouchDate"
										cssStyle="width:90%" cssClass="dateClassStyle" />
								</td>
								<td>业务员：</td>
								<td><s:if test="%{#request.userNamesSelect!=null}">
										<s:select list="%{#request.userNamesSelect}" id='userNameId'
											name='userNameId' cssStyle="width:90%" listKey="id"
											listValue="cnname" headerKey="" headerValue="-------">
										</s:select>
									</s:if>
								</td>
							</tr>
							<tr>
								<td valign="top">下次联系目标：</td>
								<td colspan="3"><s:textarea name="nextTouchAim" rows="4"
										id="nextTouchAim" cssStyle="width:96%" /></td>
							</tr>

							<tr>
								<td valign="top">备注：</td>
								<td colspan="3"><s:textarea id="remark" name="remark"
										cssStyle="width:96%" rows="4" />
								</td>
							</tr>
						</table>
					</div>
					</td>
			</tr>
		</table>

		<br>
		<table width="100%" border="0" cellspacing="0" class="tabForm">
			<tr>
				<td colspan="4" class="th_head">
					<div id="menuArrow2"
						style="background:url(${pageContext.request.contextPath}/ui/images/down.gif) no-repeat center;float:left;">&nbsp;</div>
					<div id="menuTitle2" style="font-weight:bold">其他信息</div></td>
			</tr>
			<tr>
				<td>
					<div id="menu2">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="16%">创建人：</td>
								<td width="34%"><s:textfield name="creater" id="creater"
										cssStyle="width:90%" cssClass="disabled" />
								</td>
								<td width="16%">创建日期：</td>
								<td width="34%"><s:textfield name="createTime"
										id="createTime" cssStyle="width:90%" cssClass="disabled" />
								</td>
							</tr>
							<tr>
								<td>修改人：</td>
								<td><s:textfield name="updater" id="updater"
										cssStyle="width:90%" cssClass="disabled" />
								</td>
								<td>修改日期：</td>
								<td><s:textfield name="updateTime" id="updateTime"
										cssStyle="width:90%" cssClass="disabled" />
								</td>
							</tr>
							<tr>
								<td>所属人：</td>
								<td>
									<!-- 保存所有人的姓名 --> <s:textfield name="dispensePerson"
										id="dispensePerson" cssStyle="width:90%" cssClass="disabled" />
									<!-- 保存所属人的id --> <s:hidden name="sysUserId" />
								</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
						</table>
					</div></td>
			</tr>
		</table>
	</s:form>
	<script src="${pageContext.request.contextPath}/ui/js/menu.js"
		type="text/javascript"></script>
</body>
</html>
