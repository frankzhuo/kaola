<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@include file="base.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <link type="text/css" rel="stylesheet" href="/resources/css/style.css"></link>
	<script type="text/javascript" src="/resources/js/jquery.js"></script>	
	<script type="text/javascript" src="/resources/js/jquery.form.js" ></script>
	<script type="text/javascript" src="/resources/js/jquery-1.4.2.js"></script>
  	<script type="text/javascript" src="/resources/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	
	function postUrl(divId){
		var datestr = document.getElementById('d421').value;
		$.post("/heavyActiveDetailList.do?imei="+divId+"&datestr="+datestr,function(data){
			$("#"+divId).html(data);
		});
	}
	
	function changeDate(){
		var date = document.getElementById("d421");
		var iform = document.getElementById('iform');
		iform.submit();
	}
	
	function showOrHide(divId){
		childObj = document.getElementById(divId);
		if (childObj.style.display == 'none')
		{
			childObj.style.display = 'block';
			postUrl(divId);
			
		}
		else{
			//childObj.style.display == 'none'
			$("#"+divId).hide(); 
			
		}
		 
	}
	</script>
  </head>
<body>
	<fieldset style="width=95%">
		<legend>输入查询日期(默认上个月):</legend>
		<form id="iform" action="/heavyActiveImeiList.do">
		<input id="d421" name="datestr" class="Wdate" type="text" 
					    	onfocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'2012-04',maxDate:'%y-{%M-1}',readOnly:true})"
					    	value="${datestr }" onchange="changeDate()"/>
		</form>
	</fieldset> 			    	
  <table width="100%" border="0" cellpadding="0" cellspacing="1"  id="changecolor"
  	style="background-color:#a8c7ce">
	<tr style="font-weight: bold;background: #b3d4fd;">
		<td >${datestr }重度活跃用户(一个月内使用天数至少8天的终端)</td>
	</tr>
    <c:forEach items="${haImeiList}" var="first" varStatus="stauts">
   		<tr>
   			<td style="font-weight: bold;color: #000;text-align:left">
   				<div style="background-color: #ffffff;">
   				
   					<a href="javascript:void(0);" onclick="showOrHide('${first.imei }');">${first.imei }</a>
   				
   				</div>  			
   				<div id="${first.imei }" style="display:none"></div>
   			</td>
   		</tr>
   	</c:forEach>
    </table>
</body>
</html>