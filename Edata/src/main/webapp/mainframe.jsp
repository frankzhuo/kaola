<%@ page language="java" pageEncoding="utf-8" 
import="java.util.Calendar;"%>
<%
String path = request.getContextPath();

Calendar c = Calendar.getInstance();
c.add(Calendar.DAY_OF_MONTH, -1);
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH)+1;

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset//EN">
<HTML><HEAD><TITLE>后台管理 </TITLE>
<META content="MSHTML 6.00.2900.5848" name=GENERATOR></HEAD>
<FRAMESET id=index border=0 frameSpacing=0 frameBorder=no cols=170,* >
<FRAME id=leftFrame name=leftFrame src="<%=path%>/left.jsp" noResize scrolling=no>
	
	<FRAMESET border=0 frameSpacing=0 frameBorder=no rows="70,*">
		<FRAME id=topFrame name=topFrame src="<%=path%>/top.jsp" noResize scrolling=no>
		<FRAME id=mainFrame name=mainFrame src="<%=path%>/mainSummaryInfo.do" noResize scrolling=yes>
	</FRAMESET>

</FRAMESET>
<noframes></noframes>
</HTML>