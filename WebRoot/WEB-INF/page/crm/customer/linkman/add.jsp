<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>新建联系人</title>
<link href="${pageContext.request.contextPath}/ui/css/style_cn.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/ui/js/win.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/ui/js/jquery-1.4.2.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/date_input/jquery.datepick.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/date_input/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ui/js/date_input/calendar.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/ui/js/date_input/jquery.datepick.css" type="text/css">

</head>
<script type="text/javascript">
	function name2pinyin(){
       var nameValue=$("#name").val();
       $.post("${pageContext.request.contextPath}/crm/linkmanAction_pinyin.do",{name:nameValue},function(data,textStatuts){
              $("#pycode").val(data);
       });
	}
</script>

<body>
<s:form name="ActionForm" action="linkmanAction_save.do" namespace="/crm">
<s:hidden name="id"/>
<s:hidden name="cid"/>
<div class="control">
	<button type='button' class='button' onMouseOver="this.className='button_over';" onMouseOut="this.className='button';"  
	        onClick="document.ActionForm.submit();"><img src="${pageContext.request.contextPath}/ui/images/button/baocun.png" border='0' align='absmiddle'>&nbsp;保存</button>
	<button type='button' class='button' onMouseOver="this.className='button_over';" onMouseOut="this.className='button';"  
	        onClick="window.history.go(-1)"><img src="${pageContext.request.contextPath}/ui/images/button/fanhui.png" border='0' align='absmiddle'>&nbsp;返回</button>
</div>
 
<table width="100%" border="0" cellspacing="0" class="tabForm">
  <tr>
    <td colspan="4" class="th_head">
		<div id="menuArrow1" style="background:url(${pageContext.request.contextPath}/ui/images/down.gif) no-repeat center;float:left;">&nbsp;</div>
		<div id="menuTitle1" style="font-weight:bold">基本信息</div>
	</td>
  </tr>
  <tr>
  	<td>
		<div id="menu1">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="16%"></td>
				<td width="34%"></td>
				<td width="16%"></td>
				<td width="34%"></td>
			</tr>		
		  <tr>
			<td class="red">姓名：</td>
			<td>
				<s:textfield name="name" id="name" cssStyle="width:90%" onblur="name2pinyin()" />
			</td>
			
			<td>性别：</td>
				<td>
				<s:radio list="{'男','女'}"  name="sex" id="sex" cssClass="radio"  value="%{'男'}"/></td>
		  </tr>
		  <tr>
			<td>编码：</td>
			<td>
			<s:textfield name="code" id="code" cssStyle="width:90%" value="%{#request.code}"/>
			</td>
			<td>拼音码：</td>
			<td>
			<s:textfield id="pycode" name="pycode" value="" cssStyle="width:90%" readonly="true" cssClass="disabled"/>
			</td>
		  </tr>
		  <tr>
			<td>出生日期：</td>
			<td>
			<s:textfield name="birthday" id="birthday" cssClass="dateClassStyle" cssStyle="width:90%"/>
			</td>
			<td>传真：</td>
			<td>
				<s:textfield name="fax" id="fax" cssStyle="width:90%"/>
			</td>
		  </tr>
		  <tr>
			<td>部门：</td>
			<td>
			<s:textfield name="department" id="department" cssStyle="width:90%"/>
			</td>
			<td>职务：</td>
			<td>
			<s:textfield name="duty" id="duty" cssStyle="width:90%"/></td>
		  </tr>
		  <tr>
			<td>办公电话：</td>
			<td>
			<s:textfield name="officeTel" id="office_tel" cssStyle="width:90%"/></td>
			<td>家庭电话：</td>
			<td>
			<s:textfield name="homeTel" id="home_tel" cssStyle="width:90%"/></td>
		  </tr>
		  <tr>
			<td>手机：</td>
			<td>
			<s:textfield name="mobile" id="mobile" cssStyle="width:90%"/></td>
			<td>是否主联系人：</td>
			<td>
			<s:radio list="#{'Y':'是','N':'否'}"  name="mainFlag" id="mainFlag" cssClass="radio" listKey="key" listValue="value" value="'Y'"/>
		  </tr>
		  <tr>
			<td>电子邮件：</td>
			<td>
			<s:textfield name="email" id="email" cssStyle="width:90%"/></td>
			<td>邮政编码：</td>
			<td>
			<s:textfield name="postcode" id="postcode" cssStyle="width:90%"/></td>
		  </tr>
		  <tr>
			<td>QQ号：</td>
			<td>
			<s:textfield name="imNum" id="im_num" cssStyle="width:90%"/></td>
			<td>QQ昵称：</td>
			<td>
			<s:textfield name="imName" id="im_name" cssStyle="width:90%"/></td>
		  </tr>
		  <tr>
		  	<td>所属公司：</td>
		  	<td>
				<s:if test="#request.companySelect!=null">
				    <s:select list="#request.companySelect" 
				    	id='companyId'  name="companyId" cssStyle='width:90%'
				         headerKey="" headerValue="--------"
				         listKey="id" listValue="name" >
				     </s:select>
				 </s:if>
		  	</td>
			<td>联系地址：</td>
			<td>
			<s:textfield name="address" id="address" cssStyle="width:90%"/></td>
		  </tr>
		  <tr>
			<td valign="top">其他联系：</td>
			<td colspan="3">
			<s:textfield name="otherLink" id="otherLink" cssStyle="width:96%"/></td>
		  </tr>
		  <tr>
			<td valign="top">爱好：</td>
			<td colspan="3">
			<s:textarea name="hobby" rows="4" id="hobby" cssStyle="width:96%"/></td>
		  </tr>
		  <tr>
			<td valign="top">忌讳：</td>
			<td colspan="3">
			<s:textarea name="taboo" rows="4" id="taboo" cssStyle="width:96%"/></td>
		  </tr>
		  <tr>
			<td valign="top">备注：</td>
			<td colspan="3">
			<s:textarea name="remark" rows="4" id="remark" cssStyle="width:96%"/></td>
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
		<div id="menuArrow2" style="background:url(${pageContext.request.contextPath}/ui/images/down.gif) no-repeat center;float:left;">&nbsp;</div>
		<div id="menuTitle2" style="font-weight:bold">其他信息</div>
	</td>
  </tr>
  <tr>
  	<td>
  <div id="menu2">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="16%">创建人：</td>
			<td width="34%">
			<s:textfield  name="creater" id="creater"  cssStyle="width:90%" cssClass="disabled" /></td>
			<td width="16%">创建日期：</td>
			<td width="34%">
			<s:textfield  name="createTime" id="createTime"  cssStyle="width:90%" cssClass="disabled" /></td>
		  </tr>
		  <tr>
			<td>修改人：</td>
			<td>
			<s:textfield  name="updater" id="updater"  cssStyle="width:90%" cssClass="disabled" /></td>
			<td>修改日期：</td>
			<td>
			<s:textfield  name="updateTime" id="updateTime"  cssStyle="width:90%"  cssClass="disabled" />
			</td>
		  </tr>
		  <tr>
			<td>所属人：</td>
			<td>
			 <!-- 保存所有人的姓名 -->
   			  <s:textfield  name="dispensePerson" id="dispensePerson" cssStyle="width:90%" cssClass="disabled"/>
			  <!-- 保存所属人的id -->
			  <s:hidden name="sysUserId"/>
			</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		  </tr>
  		</table>
	</div>
	</td>
  </tr>
</table>
</s:form>
<script src="${pageContext.request.contextPath}/ui/js/menu.js" type="text/javascript"></script>
<br/>
</body>
</html>
