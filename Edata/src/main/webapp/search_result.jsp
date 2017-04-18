<%@ page contentType="text/html;charset=utf-8" language="java"
import="com.kaola.edata.model.SearchData;"%>


<%@include file="base.jsp"%>

<html>
<head>
<title>搜索结果</title>
 <link type="text/css" rel="stylesheet" href="/resources/css/style.css"></link>
 <link href="./mainresource/summary.css" media="screen" rel="stylesheet" type="text/css">


	<style>
		. content_left {
		    width: 636px;
		    float: left;
		    padding-left: 35px;
        }
	</style>
	<script type="text/javascript" src="/resources/js/jquery.js"></script>	
	<script type="text/javascript" src="/resources/js/jquery.form.js" ></script>
	<script type="text/javascript" src="/resources/js/jquery-1.4.2.js"></script>
  	<script type="text/javascript" src="/resources/js/My97DatePicker/WdatePicker.js"></script>
</head>

<body>
 <form id="iform" action="/search.do">
         			<input type="text" id="str" name="str" class="box"     height="50" size="80" maxlength="255" />     <input class="box1"  type="submit" value="Edata搜索" id="stb">
 </form>
 <div class="body">
  <div class="hellobar" id="hellobar" style="display:block">
      <span>
      检索结果${data.total}条
      </span>

    </div>
    	<c:forEach  items="${data.list}" var="lsSummary" varStatus="stauts">
		<div class="hellobar" id="hellobar" style="display:block">
	
			<span class="STYLE19">${lsSummary}</span>
	
		
		</div>
		<br>
		</c:forEach>

</div>
</body>
</html>