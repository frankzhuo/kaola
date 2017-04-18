<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@include file="base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <link type="text/css" rel="stylesheet" href="/resources/css/style.css"></link>
	<script type="text/javascript" src="/resources/js/jquery.js"></script>	
	<script type="text/javascript" src="/resources/js/jquery.form.js" ></script>
	<script type="text/javascript" src="js/jquery-1.4.2.js"></script>

  </head>
<body>
				    	
  <table width="100%" border="0" cellpadding="0" cellspacing="1"  id="changecolor"
  	style="background-color:#a8c7ce">
	<tr style="font-weight: bold;background: #b3d4fd;">
		<td >命令ID</td><td >1号</td><td >2号</td><td >3号</td><td >4号</td><td >5号</td>
		<td >6号</td><td >7号</td><td >8号</td><td >9号</td><td >10号</td>
		<td >11号</td><td >12号</td><td >13号</td><td >14号</td><td >15号</td>
		<td >16号</td><td >17号</td><td >18号</td><td >19号</td><td >20号</td>
		<td >21号</td><td >22号</td><td >23号</td><td >24号</td><td >25号</td>
		<td >26号</td><td >27号</td><td >28号</td><td >29号</td><td >30号</td><td >31号</td>
	</tr>
    <c:forEach items="${haDetailList}" var="first" varStatus="stauts">
    	<c:choose>
	    	<c:when test="${stauts.index % 2 == 0}">
	    		<tr class="odd">
	    	</c:when>
   			<c:otherwise>
   				<tr class="even">
   			</c:otherwise>
   		</c:choose>
   			<td style="font-weight: bold;color: #000;">${first["actionId"] }</td>
   			<td title="01号">${first["01"]}</td><td title="02号">${first["02"]}</td><td title="03号">${first["03"]}</td>
   			<td title="04号">${first["04"]}</td><td title="05号">${first["05"]}</td><td title="06号">${first["06"]}</td>
   			<td  title="07号">${first["07"]}</td><td title="08号">${first["08"]}</td><td title="09号">${first["09"]}</td>
   			<td title="10号">${first["10"]}</td><td title="11号">${first["11"]}</td><td title="12号">${first["12"]}</td>
   			<td title="13号">${first["13"]}</td><td title="14号">${first["14"]}</td><td title="15号">${first["15"]}</td>
   			<td title="16号">${first["16"]}</td><td title="17号">${first["17"]}</td><td title="18号">${first["18"]}</td>
   			<td title="19号">${first["19"]}</td><td title="20号">${first["20"]}</td><td title="21号">${first["21"]}</td>
   			<td title="22号">${first["22"]}</td><td title="23号">${first["23"]}</td><td title="24号">${first["24"]}</td>
   			<td title="25号">${first["25"]}</td><td title="26号">${first["26"]}</td><td title="27号">${first["27"]}</td>
   			<td title="28号">${first["28"]}</td><td title="29号">${first["29"]}</td><td title="30号">${first["30"]}</td>
   			<td title="31号">${first["31"]}</td>
   		</tr>
   	</c:forEach>
    </table>
</body>
</html>