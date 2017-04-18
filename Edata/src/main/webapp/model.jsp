<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@include file="base.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <title>后台管理 </title>
  <link type="text/css" rel="stylesheet" href="/resources/css/style.css"></link>
	<script type="text/javascript" src="/resources/js/jquery.js"></script>	
	<script type="text/javascript" src="/resources/js/jquery.form.js" ></script>
	<script type="text/javascript" src="/resources/js/jquery-1.4.2.js"></script>
  	<script type="text/javascript" src="/resources/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	
	function changeDate(){
		var date = document.getElementById("d421");
		var iform = document.getElementById('iform');
		iform.submit();
	}
	
	function changeName() {
		var date = document.getElementById("d421");
		var iform = document.getElementById('iform');
		iform.submit();
	}
	</script>
  </head>
<body>
	<fieldset style="width=95%">
		<legend>输入查询日期:</legend>
		<form id="iform" action="/modelList.do">
		<input id="d421" name="datestr" class="Wdate" type="text" 
					    	onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'2012-04',maxDate:'%y-{%M}',readOnly:true})"
					    	value="${datestr }" onchange="changeDate()"/>
		&nbsp;&nbsp;&nbsp;&nbsp;机型
		<select id="modelType"  name="modelType" onchange="changeName()" >
		 <c:forEach items="${modeNameList}" var="mnl" varStatus="stauts" >
		 	 <c:choose>
	              <c:when test="${mnl eq modelType}">
	                  <option value="${mnl}"  selected="selected">${mnl}</option>
	              </c:when> 
	              <c:otherwise>
	                    <option value="${mnl}" >${mnl}</option>
	              </c:otherwise>
              </c:choose>
		 </c:forEach>
		</select>			    	
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="last" target="mainFrame" href="<%=path%>/modelActionRankList.do?datestr=${datestr}&modelType=${modelType}&type=0">top 20的功能表和对应总使用次数</a>
		</form>
	</fieldset> 			    	
 	<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
        <tr style="font-weight: bold;background: #d3eaef;">    
          <td bgcolor="d3eaef" class="STYLE6">日期</td>
	      <td bgcolor="d3eaef" class="STYLE6">打开乐语音应用终端总累计数</td>
	      <td bgcolor="d3eaef" class="STYLE6">打开乐语音应用终端新增量</td>
	      <td bgcolor="d3eaef" class="STYLE6">使用语音控2次以上用户累计数</td>
	      <td bgcolor="d3eaef" class="STYLE6">使用语音控2次以上用户新增量</td>
	      <td bgcolor="d3eaef" class="STYLE6">使用语音控3次以上用户累计数</td>
	      <td bgcolor="d3eaef" class="STYLE6">使用语音控3次以上用户新增量</td>
	      <td bgcolor="d3eaef" class="STYLE6">随身记记录条数大于20条的用户累计数</td>
	      <td bgcolor="d3eaef" class="STYLE6">随身记记录条数大于10条的用户新增量</td>
	      <!-- <td bgcolor="d3eaef" class="STYLE6">随身记用户数</td> -->
	      <td bgcolor="d3eaef" class="STYLE6">随身记记录条目累计数</td>
	      <!-- <td bgcolor="d3eaef" class="STYLE6">随身记同步数</td> -->
	      <td bgcolor="d3eaef" class="STYLE6">随身记记录条数大于5条的用户累计数</td>
	      <td bgcolor="d3eaef" class="STYLE6">随身记活跃用户新增量</td>
	      
	      
        </tr>
        <c:forEach items="${modelList}" var="ls" varStatus="stauts">

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
				          <td class="STYLE19">${ls.yuyinkongUser2}</td>
				          <td class="STYLE19">${ls.yuyinkongUserAdd2}</td>
				          <td class="STYLE19">${ls.yuyinkongUser3}</td>
				          <td class="STYLE19">${ls.yuyinkongUserAdd3}</td>
				          <td class="STYLE19">${ls.noteUserCount20}</td>
				          <td class="STYLE19">${ls.noteUserCountAdd10}</td>
				          <%-- <td class="STYLE19">${ls.noteUser}</td> --%>
				          <td class="STYLE19">${ls.noteCount}</td>
				          <%-- <td class="STYLE19">${ls.noteCountSynchro}</td> --%>
				          <td class="STYLE19">${ls.noteUserCount5}</td>
				          <td class="STYLE19">${ls.noteUserActive}</td>
			    </tr>
   	</c:forEach>
    </table>
</body>
</html>