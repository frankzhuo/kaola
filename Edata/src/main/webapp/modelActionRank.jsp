<%@page import="org.apache.velocity.runtime.directive.Foreach"%>
<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@include file="base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>后台管理--统计详细信息</title>
<link type="text/css" rel="stylesheet" href="/resources/css/style.css"></link>
<script type="text/javascript" src="/resources/js/jquery.js"></script>
<script type="text/javascript" src="/resources/js/jquery.form.js"></script>
<script type="text/javascript" src="/resources/js/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="/resources/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="1"
						bgcolor="#a8c7ce">
						<tr style="font-weight: bold; background: #d3eaef;">
							<td colspan="5" class="STYLE6">top 20的功能表和对应总使用次数</td>
						</tr>
						<tr style="font-weight: bold; background: #d3eaef;">
							<td bgcolor="d3eaef" class="STYLE6">排名</td>
							<td bgcolor="d3eaef" class="STYLE6">语音控命令ID</td>
							<td bgcolor="d3eaef" class="STYLE6">语音控命令名称</td>
							<td bgcolor="d3eaef" class="STYLE6">使用该命令所有终端数</td>
							<td bgcolor="d3eaef" class="STYLE6">命令次数</td>
						</tr>
						<c:forEach items="${actionList}" var="al" varStatus="stauts">
							<tr bgcolor="#ffffff">
								<td class="STYLE19">${al.rank }</td>
								<td class="STYLE19">${al.actionId}</td>
								<td style="text-align: left;" class="STYLE19">&nbsp;&nbsp;${al.actionName}</td>
								<td class="STYLE19">${al.imeiCount}</td>
								<td class="STYLE19">${al.actionIdCount}</td>
							</tr>
						</c:forEach>
						<tr style="font-weight: bold; background: #d3eaef;">
							<td colspan="5" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/modelList.do?datestr=${datestr}&modelType=${modelType}">返回</a></td>
						</tr>
	</table>		
</body>
</html>