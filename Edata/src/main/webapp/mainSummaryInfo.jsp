<%@page import="org.apache.velocity.runtime.directive.Foreach"%>
<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@include file="base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>后台管理--pc统计详细信息</title>
<link type="text/css" rel="stylesheet" href="/resources/css/style.css"></link>
<script type="text/javascript" src="/resources/js/jquery.js"></script>
<script type="text/javascript" src="/resources/js/jquery.form.js"></script>
<script type="text/javascript" src="/resources/js/jquery-1.4.2.js"></script>
<script type="text/javascript"
	src="/resources/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	$(function () {
		/*当前各机型用户数量总量和分布情况*/
	  	pie();
	    
	    /* 每周各机型用户数量变化情况 - 折线图*/
	 	line();
	    
	    /*本月各机型活动用户数量占总用户数百分比排名 - 折线图*/
 		line2();
	    /*本月各版本活动用户占数量排名总用户数百分比排名 - 折线图*/
	    line3();
	    /*本月各版本活动用户数量排名 - 柱状图*/
	    column();
	});
	
	/*当前各机型用户数量总量和分布情况*/
	function pie() {
		/* ----  获取参数 -------*/
		var c=new Array(); 

		/* 名称 */
		var name ="<%=request.getAttribute("models")%>" ;
		/* 百分比 */
		var num ="<%=request.getAttribute("nums")%>" ;
		
		var nameArr = name.split(",");
		var numArr = num.split(",");
		for (i = 0; i < nameArr.length; i++) {
			var a = nameArr[i];
			var b = parseFloat(numArr[i]);
			c[i] =  [a, b];
		}
		/* ----  pie 图 -------*/
		var chart;
		    $(document).ready(function() {
		    	
		        chart = new Highcharts.Chart({
		            chart: {
		                renderTo: 'container',
		                plotBackgroundColor: null,
		                plotBorderWidth: null,
		                plotShadow: false
		            },
		            title: {
		                text: '当前各机型用户数量总量和分布情况'
		            },
		            tooltip: {
		            	 formatter: function() {
		             		return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 2) +'% ('+
		                          Highcharts.numberFormat(this.y, 0, ',') +')';
		          			}
		            },
		            plotOptions: {
		                pie: {
		                    allowPointSelect: true,
		                    cursor: 'pointer',
		                    dataLabels: {
		                        enabled: true,
		                        color: '#000000',
		                        connectorColor: '#000000',
		                        formatter: function() {
		                            return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 2) +'%';
		                        }
		                    }
		                }
		            },
		            series: [{
		                type: 'pie',
		                name: '机型数量',
		                data: c
		            }]
		        });
		    });
	}
	
	function line() {
		var now=new Date().getTime();
		$.getJSON('/userCountLine.do?now='+now,function(json){//now表示每次刷新都会发送新的请求
			
			//json = eval("(" + json + ")");  eval? 
					
			/* $.each(json,function(i,item){
				alert("sid:   " +i);
				alert("name:   " +item);
			}); */
			//-----
			 var chart;
			    $(document).ready(function() {
			        chart = new Highcharts.Chart({
			            chart: {
			                renderTo: 'containerLine',
			                type: 'line'
			       //         marginRight: 130,
			      //          marginBottom: 25
			            },
			            title: {
			            	align:'left',
			                text: '各机型用户数量变化情况',
			                x: -2 //center
			            },
			            subtitle: {
			                text: '',
			                x: -20
			            },
			            xAxis: {
			                categories: json.categories
			            },
			            yAxis: {
			                title: {
			                    text: '用户数 (个)'
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
			                        this.x +': '+ this.y +'个';
			                }
			            },
			            legend: {
			                align: 'right',
			                verticalAlign: 'top',
			                y: 20,
			                floating: true,
			                borderWidth: 0
			            },
			            series: json.list
			            
			        });
			    });
			//------				
		});     
		
		
		<%-- alert("jsontest");
		$.getJSON("<%=path%>/jsonTest.do",{param:"gaoyusi"},function(data){
	　　//此处返回的data已经是json对象
	　　$.each(data,function(idx,item){
		　　if(idx==0){
			　	　return true;//同countinue，返回false同break
		　　}
		　　alert("name:"+item.name+",value:"+item.value);
	　　});
　　}); --%>
	}
	
	function line2() {

		var now=new Date().getTime();
		$.getJSON('/activeUserPercentLine.do?now='+now,function(json){//now表示每次刷新都会发送新的请求
			
			//json = eval("(" + json + ")");  eval? 
					
			/* $.each(json,function(i,item){
				alert("sid:   " +i);
				alert("name:   " +item);
			}); */
			//-----
			 var chart;
			    $(document).ready(function() {
			        chart = new Highcharts.Chart({
			            chart: {
			                renderTo: 'containerLine2',
			                type: 'line'
			       //         marginRight: 130,
			      //          marginBottom: 25
			            },
			            title: {
			            	align:'left',
			                text: '本月各机型活动用户数量占总用户数百分比',
			                x: -2 //center
			            },
			            subtitle: {
			                text: '',
			                x: -20
			            },
			            xAxis: {
			                categories: json.categories
			            },
			            yAxis: {
			                title: {
			                    text: '百分比 (%)'
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
			                        this.x +': '+ this.y +'%';
			                }
			            },
			            legend: {
			                align: 'right',
			                verticalAlign: 'top',
			                y: 20,
			                floating: true,
			                borderWidth: 0
			            },
			            series: json.list
			            
			        });
			    });
			//------				
		});     
	}
	
	function line3() {
		var now=new Date().getTime();
		$.getJSON('/activeUserVersionPercentLine.do?now='+now,function(json){//now表示每次刷新都会发送新的请求
			 var chart;
			    $(document).ready(function() {
			        chart = new Highcharts.Chart({
			            chart: {
			                renderTo: 'containerLine3',
			                type: 'line'
			       //         marginRight: 130,
			      //          marginBottom: 25
			            },
			            title: {
			            	align:'left',
			                text: '本月各版本活动用户数占总用户数百分比',
			                x: -2 //center
			            },
			            subtitle: {
			                text: '',
			                x: -20
			            },
			            xAxis: {
			                categories: json.categories
			            },
			            yAxis: {
			                title: {
			                    text: '百分比 (%)'
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
			                        this.x +': '+ this.y +'%';
			                }
			            },
			            legend: {
			                align: 'right',
			                verticalAlign: 'top',
			                y: 20,
			                floating: true,
			                borderWidth: 0
			            },
			            series: json.list
			        });
			    });
		});
		  
	}
	
	
	function column() {
		var now=new Date().getTime();
		$.getJSON('/activeUserVersionColumn.do?now='+now,function(json){//now表示每次刷新都会发送新的请求
			 var chart;
			    $(document).ready(function() {
			        chart = new Highcharts.Chart({
			            chart: {
			                renderTo: 'containerColumn',
			                type: 'column'
			            },
			            title: {
			                text: '本月各版本活动用户数量排名'
			            },
			            subtitle: {
			                text: ''
			            },
			            xAxis: {
			            	 categories: json.categories
			            },
			            yAxis: {
			                min: 0,
			                title: {
			                    text: '数量 (个)'
			                }
			            },
			            legend: {
			                layout: 'vertical',
			                backgroundColor: '#FFFFFF',
			                align: 'left',
			                verticalAlign: 'top',
			                x: 100,
			                y: 70,
			                floating: true,
			                shadow: true
			            },
			            tooltip: {
			                formatter: function() {
			                    return ''+
			                        this.x +': '+ this.y +' 个';
			                }
			            },
			            plotOptions: {
			                column: {
			                    pointPadding: 0.2,
			                    borderWidth: 0
			                }
			            },
			            series: json.list
			        });
			    });
		});
		
		    
	}
	
	
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
	<table width="99.8%" border="0" cellpadding="1" cellspacing="1"
		bgcolor="#a8c7ce">
		<tr>
			<td width="50%" ><div id="container"
					style="min-width: 200px; height: 400px; margin: 0 auto"></div>
			</td>
		</tr>
		
		<tr>
			<td><div id="containerLine"
					style="min-width: 200px; height: 400px; margin: 0 auto"></div></td>
		</tr>
		<tr>
			<td width="50%"><div id="containerLine2"
					style="min-width: 200px; height: 400px; margin: 0 auto"></div></td>
		</tr>
		<tr>
			<td><div id="containerLine3"
					style="min-width: 200px; height: 400px; margin: 0 auto"></div></td>
		</tr>
		<tr>
			<td><div id="containerColumn"
					style="min-width: 200px; height: 400px; margin: 0 auto"></div></td>
		</tr>
		
	</table>
		<div>
		<table width="100%" border="0" cellpadding="1" cellspacing="1"
						bgcolor="#a8c7ce">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="1" cellspacing="1"
						bgcolor="#a8c7ce">
						<tr style="font-weight: bold; background: #d3eaef;">
							<td bgcolor="d3eaef" class="STYLE6" colspan="2">各机型上不同软件版本的分布情况（占本机型总用户数量百分比,
								top3）</td>
						</tr>
						<c:forEach items="${mvList}" var="al" varStatus="stauts">
							<tr bgcolor="#ffffff">
								<td style="text-align: left;" class="STYLE19" colspan="2">${al.model}</td>
							</tr>
							<c:forEach items="${al.lsvList}" var="lsv" varStatus="stauts">
								<tr bgcolor="#ffffff">
									<td class="STYLE19" >${lsv.fullVersion }</td>
									<td class="STYLE19" >${lsv.percent }%</td>
								</tr>
							</c:forEach>
							<tr bgcolor="#ffffff">
							</tr>
						</c:forEach>
					</table>
				</td>
				<td align="left" valign="top">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
						bgcolor="#a8c7ce">
						<tr style="font-weight: bold; background: #d3eaef;">
							<td colspan="5" class="STYLE6">本月活动用户使用的top5的功能</td>
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
								<td class="STYLE19">${al.actionName}</td>
								<td class="STYLE19">${al.imeiCount}</td>
								<td class="STYLE19">${al.actionIdCount}</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript"
	src="/resources/js/hightcharts/highcharts.js"></script>
<script type="text/javascript"
	src="/resources/js/hightcharts/exporting.js"></script>
</html>