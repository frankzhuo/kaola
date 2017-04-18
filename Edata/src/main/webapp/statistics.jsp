<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@include file="base.jsp"%>

<html>
<head>
<title>mobile用户量统计</title>
 <link type="text/css" rel="stylesheet" href="/resources/css/style.css"></link>
	<script type="text/javascript" src="/resources/js/jquery.js"></script>	
	<script type="text/javascript" src="/resources/js/jquery.form.js" ></script>
	<script type="text/javascript" src="/resources/js/jquery-1.4.2.js"></script>
  	<script type="text/javascript" src="/resources/js/My97DatePicker/WdatePicker.js"></script>
</head>

<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
		<tr style="font-weight: bold; background: #d3eaef;">
			<td bgcolor="d3eaef" class="STYLE6">日期</td>
			<td bgcolor="d3eaef" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("打开乐语音应用终端总累计数","utf-8") %>&tag=LEYUYIN_USER&datestr=${datestr}&modelType=${modelType}&type=0">打开乐语音应用终端总累计数</a></td>
			<td bgcolor="d3eaef" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("打开乐语音应用终端总新增量","utf-8") %>&tag=LEYUYIN_USER_ADD&datestr=${datestr}&modelType=${modelType}&type=0">新增量</a></td>
			<td bgcolor="d3eaef" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("使用语音控终端累计数","utf-8") %>&tag=YUYINKONG_USER&datestr=${datestr}&modelType=${modelType}&type=0">使用语音控终端累计数</a></td>
			<td bgcolor="d3eaef" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("使用语音控终端新增量","utf-8") %>&tag=YUYINKONG_USER_ADD&datestr=${datestr}&modelType=${modelType}&type=0">新增量</a></td>
			<td bgcolor="d3eaef" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("使用语音控终端数","utf-8") %>&tag=YUYINKONG_USER_DAY&datestr=${datestr}&modelType=${modelType}&type=0">使用语音控终端数</a></td>
			<td bgcolor="d3eaef" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("语音控命令使用次数","utf-8") %>&tag=YUYINKONG_ORDERS&datestr=${datestr}&modelType=${modelType}&type=0">语音控命令使用次数</a></td>
			<td bgcolor="d3eaef" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("语音控命令使用新增量","utf-8") %>&tag=YUYINKONG_ORDERS_ADD&datestr=${datestr}&modelType=${modelType}&type=0">新增量</a></td>
			<td bgcolor="d3eaef" class="STYLE6" colspan="2"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("活跃用户量","utf-8") %>&tag=ACTIVE_USER_COUNT&datestr=${datestr}&modelType=${modelType}&type=0">活跃用户量</a></td>
			<td bgcolor="d3eaef" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("重度用户量","utf-8") %>&tag=HEAVY_USER_COUNT&datestr=${datestr}&modelType=${modelType}&type=0">重度用户量</a></td>
			<td bgcolor="d3eaef" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("用户留存率","utf-8") %>&tag=KEEP_USER_RATE&datestr=${datestr}&modelType=${modelType}&type=0">用户留存率</a></td>
			<td bgcolor="d3eaef" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("随身记用户累计数","utf-8") %>&tag=NOTE_USER&datestr=${datestr}&modelType=${modelType}&type=0">随身记用户累计数</a></td>
			<td bgcolor="d3eaef" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("随身记用户新增量","utf-8") %>&tag=NOTE_COUNT_ADD&datestr=${datestr}&modelType=${modelType}&type=0">新增量</a></td>
			<td bgcolor="d3eaef" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("使用随身记用户数","utf-8") %>&tag=NOTE_USER_DAY&datestr=${datestr}&modelType=${modelType}&type=0">使用随身记用户数</a></td>
			<td bgcolor="d3eaef" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("随身记记录累计数","utf-8") %>&tag=NOTE_COUNT&datestr=${datestr}&modelType=${modelType}&type=0">随身记记录累计数</a></td>
			<td bgcolor="d3eaef" class="STYLE6"><a class="last" target="mainFrame" href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("随身记记录新增量","utf-8") %>&tag=NOTE_COUNT_ADD&datestr=${datestr}&modelType=${modelType}&type=0">新增量</a></td>
		</tr>
		<c:forEach items="${listMain}" var="ls" varStatus="stauts">

			<c:choose>
				<c:when test="${ls.opeType == 1}">
					<tr bgcolor="#dff4fc">
				</c:when>
				<c:when test="${ls.opeType == 2}">
					<tr bgcolor="#fcf1fe">
				</c:when>
				<c:otherwise>
					<tr bgcolor="#ffffff">
				</c:otherwise>
			</c:choose>
					    <td class="STYLE19">${ls.opeDate}</td>
						<td class="STYLE19">${ls.leyuyinUserCount}</td>
						<td class="STYLE19">${ls.leyuyinUserAdd}</td>
						<td class="STYLE19">${ls.yuyinkongUserCount}</td>
						<td class="STYLE19">${ls.yuyinkongUserAdd}</td>
						<td class="STYLE19">${ls.yuyinkongUserDay}</td>
						<td class="STYLE19">${ls.yuyinkongCount}</td>
						<td class="STYLE19">${ls.yuyinkongAdd}</td>
						<c:choose>
							<c:when test="${ls.opeType == 2}">
								<td class="STYLE19">5天量:${ls.activeUserCount}</td>
								<td class="STYLE19">1天量:${ls.activeUserCount2}</td>
							</c:when>
							<c:otherwise>
								<td class="STYLE19" colspan="2">${ls.activeUserCount}</td>
							</c:otherwise>
						</c:choose>
						<td class="STYLE19">${ls.heavyUserCount}</td>
						<td class="STYLE19">${ls.keepUserRate}%</td>
						<td class="STYLE19">${ls.noteUserCount}</td>
						<td class="STYLE19">${ls.noteUserAdd}</td>
						<td class="STYLE19">${ls.noteUserDay}</td>
						
						<td class="STYLE19">${ls.noteCount}</td>
						<td class="STYLE19">${ls.noteAdd}</td>
					</tr>
		</c:forEach>
	</table>
	<br>
	<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
		<tr style="font-weight: bold; background: #d3eaef;">
			<td colspan=7>&nbsp;&nbsp;Summary</td>
		</tr>
		<tr style="font-weight: bold; background: #d3eaef;">
			<td bgcolor="d3eaef" class="STYLE6">使用语音控终端累计数</td>
			<td bgcolor="d3eaef" class="STYLE6">语音控终端新增量</td>
			<td bgcolor="d3eaef" class="STYLE6">语音控终端当天使用数</td>
			<td bgcolor="d3eaef" class="STYLE6">语音控命令使用累计数</td>
			<td bgcolor="d3eaef" class="STYLE6">语音控命令当天使用数</td>
			<td bgcolor="d3eaef" class="STYLE6">随身记当天用户</td>
			<td bgcolor="d3eaef" class="STYLE6">随身记记录累计数</td>
		</tr>
		<c:forEach  items="${listSummary}" var="lsSummary" varStatus="stauts">
		<tr bgcolor="#ffffff">
			<td class="STYLE19">${lsSummary.yuyinkongUserCount}</td>
			<td class="STYLE19">${lsSummary.yuyinkongUserAdd}</td>
			<td class="STYLE19">${lsSummary.yuyinkongUserDay}</td>
			<td class="STYLE19">${lsSummary.yuyinkongCount}</td>
			<td class="STYLE19">${lsSummary.yuyinkongAdd}</td>
			<td class="STYLE19">${lsSummary.noteUserDay}</td>
			<td class="STYLE19">${lsSummary.noteCount}</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>