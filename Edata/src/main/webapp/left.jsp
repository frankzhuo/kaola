<%@ page contentType="text/html;charset=utf-8" language="java"
  import="java.util.Calendar;" %>
<%String path = request.getContextPath();
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int day = c.get(Calendar.DATE);
%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<META http-equiv=Content-Type content="text/html; charset=utf-8"/>
<LINK href="/resources/css/admin.css" type="text/css" rel="stylesheet"/>
<SCRIPT language=javascript>
	function expand(el)
	{
		childObj = document.getElementById("child" + el);

		if (childObj.style.display == 'none')
		{
			childObj.style.display = '';
		}
		else
		{
			childObj.style.display = 'none';
		}
		return;
	}
	
	function qushiexpand()
	{
		childObj = document.getElementById("childqushi");

		if (childObj.style.display == 'none')
		{
			childObj.style.display = '';
		}
		else
		{
			childObj.style.display = 'none';
		}
		return;
	}
	
	
	function pc() {
		parent.leftFrame.location.href = "<%=path%>/pcLeft.jsp";
		parent.topFrame.location.href = "<%=path%>/pcTop.jsp"
	    parent.mainFrame.location.href = "<%=path%>/pcStatisticsList.do"
	}
</SCRIPT>



</head>
</br>
<body background=resources/images/menu_bg.jpg>
<TABLE height="100%" cellSpacing=0 cellPadding=0 width=170 border=0>
  <TR>
    <TD vAlign=top align=middle>
    	<TABLE id="child1" style="DISPLAY: " cellSpacing=0 cellPadding=0 width=170 border=0>
			<TR height=20>
				<TD align=middle width=30><IMG height=9 src="resources/images/menu_icon.gif" width=9></TD>
	          	<TD><A class=menuChild target=mainFrame href='<%=path%>/action_orders.html' target=main>语音命令集</A></TD>
	          <input type=button name=name value="PC统计" onclick=pc();> 
			</TR>
		</TABLE>
		
    	<TABLE cellSpacing=0 cellPadding=0 width=150 border=0>
	        <TR height=22>
	          <TD style="PADDING-LEFT: 30px" background=resources/images/menu_bt.jpg>
	          <A class="menuParent" onclick="javascript:qushiexpand();" href="javascript:void(0);">趋势图</A></TD></TR>
	        <TR height=4><TD></TD></TR>
		</TABLE>
		
		<TABLE id="childqushi" style="DISPLAY:"  cellSpacing=0 cellPadding=0 width=170 border=0>
        		<TR height=20>
        		<TD align=middle width=30><IMG height=9 src="<%=path%>/resources/images/menu_icon.gif" width=9></TD>
        		<TD><A class=menuChild target=mainFrame href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("语音控次数增量","utf-8") %>&tag=YUYINKONG_ORDERS_ADD&datestr=<%=year+"-"+month%>">语音控次数增量</A></TD>
        		</TR>   		
        		<TR height=20>
        		<TD align=middle width=30><IMG height=9 src="<%=path%>/resources/images/menu_icon.gif" width=9></TD>
        		<TD><A class=menuChild target=mainFrame href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("活跃用户","utf-8") %>&tag=ACTIVE_USER_COUNT&datestr=<%=year+"-"+month%>">活跃用户</A></TD>
        		</TR>   		
        		<TR height=20>
        		<TD align=middle width=30><IMG height=9 src="<%=path%>/resources/images/menu_icon.gif" width=9></TD>
        		<TD><A class=menuChild target=mainFrame href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("重度用户","utf-8") %>&tag=HEAVY_USER_COUNT&datestr=<%=year+"-"+month%>">重度用户</A></TD>
        		</TR>   
        		<TR height=20>
        		<TD align=middle width=30><IMG height=9 src="<%=path%>/resources/images/menu_icon.gif" width=9></TD>
        		<TD><A class=menuChild target=mainFrame href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("终端增量","utf-8") %>&tag=LEYUYIN_USER_ADD&datestr=<%=year+"-"+month%>">终端增量</A></TD>
        		</TR>   		
        		<TR height=20>
        		<TD align=middle width=30><IMG height=9 src="<%=path%>/resources/images/menu_icon.gif" width=9></TD>
        		<TD><A class=menuChild target=mainFrame href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("用户留存率","utf-8") %>&tag=KEEP_USER_RATE&datestr=<%=year+"-"+month%>">用户留存率</A></TD>
        		</TR>   
        		<TR height=20>
        		<TD align=middle width=30><IMG height=9 src="<%=path%>/resources/images/menu_icon.gif" width=9></TD>
        		<TD><A class=menuChild target=mainFrame href="<%=path%>/statisticsLine.do?name=<%=java.net.URLEncoder.encode("终端总量","utf-8") %>&tag=LEYUYIN_USER&datestr=<%=year+"-"+month%>">终端总量</A></TD>
        		</TR>   
       	</TABLE>	
		
		
		
	
    		
	<!-- 2013-01-04 全部自动生成日期 -->		
        <% 
        	//从2013年开始完全自动化输出时间
        	int yearNow = c.get(Calendar.YEAR);
        	int month2 = c.get(Calendar.MONTH)+1;
        	for(int year2=2012;year2<=yearNow;year2++){
        		out.println("<TABLE cellSpacing=0 cellPadding=0 width=150 border=0>");
        		out.println("<TR height=22><TD style='PADDING-LEFT: 30px' background=resources/images/menu_bt.jpg>");
        		out.println("<A class='menuParent' onclick='expand("+year2+")' href='javascript:void(0);'>"+year2+"年</A></TD></TR>");
        		out.println("<TR height=4><TD></TD></TR></TABLE>");
        		out.println("<TABLE id='child"+year2+"' style='DISPLAY: ' cellSpacing=0 cellPadding=0 width=170 border=0>");
        		 // 2012-11-05 月份修改为倒序
        		//for(int i=1;i<=month2;i++){
        		if (year2 == 2012) {
        			 //2012年3月31日上线，所以从4月份开始自动化输出日期
        				for (int i = 12; i >= 4; i--) {
        	        		out.println("<TR height=20><TD align=middle width=30><IMG height=9 src='resources/images/menu_icon.gif' width=9></TD>");
        	        		out.println("<TD><A class=menuChild target=mainFrame href='"+path+"/mainDataList.do?dateYear=2012&dateMonth="+i+"' target=main>"+i+"月份</A></TD></TR>");       		
        	       		} 
        		} else if (year2 == yearNow) {
					// 今年的从当月开始计算        			
	       			for(int i=month2;i>=1;i--){   
	        			out.println("<TR height=20><TD align=middle width=30><IMG height=9 src='resources/images/menu_icon.gif' width=9></TD>");
	        			out.println("<TD><A class=menuChild target=mainFrame href='"+path+"/mainDataList.do?dateYear="+year2+"&dateMonth="+i+"' target=main>"+i+"月份</A></TD></TR>");       			
	        		}
        		} else {
        			// 往年从12月开始计算
        			for(int i=12;i>=1;i--){   
	        			out.println("<TR height=20><TD align=middle width=30><IMG height=9 src='resources/images/menu_icon.gif' width=9></TD>");
	        			out.println("<TD><A class=menuChild target=mainFrame href='"+path+"/mainDataList.do?dateYear="+year2+"&dateMonth="+i+"' target=main>"+i+"月份</A></TD></TR>");       			
	        		}
        		}
        		out.println("</TABLE>");
        	}
        %>
	</TD>
   </TR>
</TABLE>

</body>
</html>
