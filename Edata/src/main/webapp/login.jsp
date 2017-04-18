<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>乐语音后台查询系统-登录</title>
<link href="/resources/css/login-box.css" rel="stylesheet" type="text/css" />
	<style>
		.box { 
		position:absolute; 
		top:50%; 
		left:50%; 
		margin:-150px 0 0 -200px; 
		width:400px; 
		height:300px; 
		border:0px solid red; 
		} 
	</style>
	<script type="text/javascript" src="/resources/js/jquery-1.4.2.js"></script>
   	<script type="text/javascript" src="/resources/js/jquery.form.js" ></script>
	<script type="text/javascript">
		function login(){
			$("#iform").ajaxSubmit({
		        beforeSubmit : function(){
		        },
		        success : function(json){
						if(json=="0"){
							document.location.href="<%=path%>/mainframe.jsp";
						}else{
							alert("用户名密码错误!");
						}	
		        },
		        error : function (json){
						alert("系统异常!");
		        }
		   	});
		}
		
		function keypress(ev){
			if(ev.keyCode == 13){
				login();
			}
		}
	</script>
</head>
<body>
	
	<form action="<%=path%>/login.do" method="post" id="iform">
	<div style="position: absolute; top: 5%; left: 35%;width:500px;margin:0 auto;padding: 100 0 0 0;">
		<div id="login-box">
			<H2>乐语音</H2>
				后台统计系统 - 登录
<br />
<br />
			<div id="login-box-name" style="margin-top:20px;">用户名:</div>
			<div id="login-box-field" style="margin-top:20px;">
				<input name="username" class="form-login" title="username" value="" size="30" maxlength="2048" />
			</div>
			<div id="login-box-name">密码:</div>
			<div id="login-box-field">
				<input name="password" type="password" class="form-login" title="password" value="" size="30" maxlength="2048" onkeypress="keypress(event)"/>
			</div>
<br />
			<span class="login-box-options"></span>
<br />
<br />
			<a href="javascript:login();"><img src="/resources/images/login-btn.png" width="103" height="42" style="margin-left:90px;" /></a>
		</div>

	</div>
</form>
</body>
</html>