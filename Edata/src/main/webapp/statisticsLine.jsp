<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@include file="base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <title>后台管理--pc统计详细信息 </title>
  <link type="text/css" rel="stylesheet" href="/resources/css/style.css"></link>
	<script type="text/javascript" src="/resources/js/jquery.js"></script>	
	<script type="text/javascript" src="/resources/js/jquery.form.js" ></script>
	<script type="text/javascript" src="/resources/js/jquery-1.4.2.js"></script>
  	<script type="text/javascript" src="/resources/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	$(function () {
	    var chart;
	    var num = "<%=request.getAttribute("num") %>";
	    var date = "<%=request.getAttribute("date") %>";
	    var xTime = date.split(",");
	    var yNum = num.split(",");
	    /* 转为数值型*/
	    for(i = 0; i < yNum.length; i++) {
	    	yNum[i] = parseFloat(yNum[i]);
	    }
	    var commandName = "<%=request.getAttribute("name")  %>"
	    $(document).ready(function() {
	        chart = new Highcharts.Chart({
	            chart: {
	                renderTo: 'container',
	                type: 'line'
	       //         marginRight: 130,
	      //          marginBottom: 25
	            },
	            title: {
	            	align:'left',
	                text: commandName +'趋势',
	                x: 20 //center
	            },
	            subtitle: {
	                text: '',
	                x: -20
	            },
	            xAxis: {
	                categories: xTime
	            },
	            yAxis: {
	                title: {
	                    text: '计数'
	                },
	                plotLines: [{
	                    value: 0,
	                    width: 1,
	                    color: '#808080'
	                }]
	            },
	            tooltip: {
	                formatter: function() {
	                        return '<b>'+ this.series.name +'</b><br/>'+
	                        this.x +':   '+ this.y;
	                }
	            },
	            legend: {
	                align: 'right',
	                verticalAlign: 'top',
	                y: 20,
	                floating: true,
	                borderWidth: 0
	            },
	            series: [{
	                name: commandName,
	                data: yNum
	               // data: [1]
	            }]
	        });
	    });
	    
	});
	
	function changeDate(){
		var d1 = document.getElementById("dateStart").value;
		var d2 = document.getElementById("datestr").value;
		var d1Arr=d1.split('-');
		var d2Arr=d2.split('-');
		var v1=new Date(d1Arr[0],d1Arr[1],d1Arr[2]);
		var v2=new Date(d2Arr[0],d2Arr[1],d2Arr[2]);
		if(v1.getTime()>v2.getTime()) {
			alert("起始日期不能大于终止日期");
			return flase;
		}
		
		var iform = document.getElementById('iform');
		iform.submit();
	}

	
	</script>
  </head>
<body>
	<fieldset style="width=100%">
		<legend>输入查询日期:</legend>
		<form id="iform" action="/statisticsLine.do">
		<input id="dateStart" name="dateStart" class="Wdate" type="text" 
					    	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'2012-04-01',maxDate:'%y-{%M}-{%d}',readOnly:true})"
					    	value="${dateStart}" onchange="changeDate()"/>
		&nbsp;至&nbsp;
		<input id="datestr" name="datestr" class="Wdate" type="text" 
					    	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'2012-04-01',maxDate:'%y-{%M}-{%d}',readOnly:true})"
					    	value="${datestr }" onchange="changeDate()"/>
		<input id="tag" name="tag" type="hidden" value="${tag}"/>
		<input id="name" name="name" type="hidden" value="${name}"/>
		</form>
					    	
	</fieldset> 			    	
 	<table width="99.8%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
        <tr>
        	<td>&nbsp;&nbsp;<div id="container" style="min-width: 400px; height: 400px; margin: 0 auto"></div></td>
        </tr>
        <tr><td>
       	<a class="last" target="mainFrame" href="<%=path%>/mainDataList.do?datestr=${datestr0}">返回</a>
        </td></tr>
    </table>
</body>
<script type="text/javascript" src="/resources/js/hightcharts/highcharts.js"></script>	
<script type="text/javascript" src="/resources/js/hightcharts/exporting.js"></script>	
</html>