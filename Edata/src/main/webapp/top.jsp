<%@ page contentType="text/html;charset=utf-8" language="java"
import="java.util.Calendar;" %>
<%
String path = request.getContextPath();
Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH)+1;
int day = c.get(Calendar.DATE);
%>
<html>
<head>
	<link type="text/css" rel="stylesheet" href="/resources/css/top.css">
</head>
<body>
<div id="templatemo_menu_wrapper">
  <div id="templatemo_menu_panel">
    <ul>
      <li><a class="current" target="mainFrame" href="<%=path%>/mainDataList.do">用户量</a></li>
      <%-- 2012-09-20 新增机型统计 dwg --%>
      <li><a class="last" target="mainFrame" href="<%=path%>/modelList.do">机型</a></li>
      <li><a class="last" target="mainFrame" href="<%=path%>/versionStatisticsList.do">版本</a></li>
      <li><a target="mainFrame" href="<%=path%>/actionIdRank.do">用户命令使用排行</a></li>
      <li><a target="mainFrame" href="<%=path%>/imeiDetail.do">终端用户数据</a></li>
      <li><a class="last" target="mainFrame" href="<%=path%>/heavyActiveImeiList.do">重度活跃详细数据</a></li>
      <li><a class="last" target="mainFrame" href="<%=path%>/manage/note/noteFeedBackAudioListSearch.do">用户反馈</a></li>
    </ul>
  </div>
</div>

</body>
</html>