<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@include file="base.jsp"%>
<%@ taglib uri="http://www.page-web.com/tags" prefix="pg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/resources/css/manage.css"></link>
<script type="text/javascript" src="/resources/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/resources/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="/resources/js/public.js" ></script>
<link type="text/css" rel="stylesheet" href="/resources/css/page.css"></link>
<title></title>
<script type="text/javascript">
	function clearCheckBox(){
		var arrChk=$("input[name='checkId']:checked");
		$(arrChk).each(function(){
			 $(this).attr("checked", false); 
		});
	}

	function feedBackDelete(){
		var arrChk=$("input[name='checkId']:checked");
		if( arrChk.length == 0 ){
			alert("请选中一项进行操作");
		}else{		
			if(confirm("确定要删除吗?")){
				var ids = "";
				$(arrChk).each(function(){
					ids = ids+this.value+",";
				});
				if( ids.length > 0 ){
					ids = ids.substring(0,ids.length-1);
				}
				$.ajax({
			        type: "POST",
			        url: "${path }/manage/note/noteFeedBackAudioDelte.do",
			        dataType: "data",
			        data:"ids="+ids,
			        error:function() {
			        	alert("网络异常");
			        },
			        success: function(json) {
			        	json = eval('('+json+')');
			        	if(json['result'] == true){
			        		window.document.location.reload();
							clearCheckBox();
						}else{
							alert("删除失败!");
						}	
			        }
			    });	
			}
			else{return false;}
				
		}
	}
	
	function feedBackUpdateState(){
		var arrChk=$("input[name='checkId']:checked");
		if( arrChk.length == 0 ){
			alert("请选中一项进行操作");
		}else{
			if(confirm("确定要处理吗?")){
				var ids = "";
				$(arrChk).each(function(){
					ids = ids+this.value+",";
				});
				if( ids.length > 0 ){
					ids = ids.substring(0,ids.length-1);
				}
				$.ajax({
			        type: "POST",
			        url: "${path }/manage/note/noteFeedBackAudioUpdateForIds.do",
			        dataType: "data",
			        data:"ids="+ids,
			        error:function() {
			        	alert("网络异常");
			        },
			        success: function(json) {
			        	json = eval('('+json+')');
			        	if(json['result'] == true){
			        		window.document.location.reload();
							clearCheckBox();
						}else{
							alert("处理失败!");
						}	
			        }
			    });
			}
			else{
				return false;
			}
					
		}
	}
	
	function downloadAll(){
		if(confirm("本功能将下载前100条记录，确定要下载吗?")){
			$.ajax({
		        type: "POST",
		        url: "${path }/manage/note/noteFeedBackAudioListDownload.do",
		        dataType: "data",
		        data:"downloadType=1",
		        error:function() {
		        	alert("网络异常");
		        },
		        success: function(msg) {
		        	var obj = eval('('+msg+')');		        	
		        	if(obj.flag==true){
						path = obj.msg;		
						alert(path);
						window.location.href = "${path }/manage/note/noteFeedBackAudioDownloadZip.do?zipFilePath="+path;
					}else{
						alert("下载失败!");
					}
		        }
		    });
		}
		else{
			return false;
		}
	}
	
	function downloadSelect(){
		var arrChk=$("input[name='checkId']:checked");
		if( arrChk.length == 0 ){
			alert("至少选中一项");
		}else{
			var ids = "";
			$(arrChk).each(function(){
				ids = ids+this.value+",";
			});
			if( ids.length > 0 ){
				ids = ids.substring(0,ids.length-1);
			}
			$.ajax({
		        type: "POST",
		        url: "${path }/manage/note/noteFeedBackAudioListDownload.do",
		        dataType: "data",
		        data:"ids="+ids,
		        error:function() {
		        	alert("网络异常");
		        },
		        success: function(msg) {
		        	var obj = eval('('+msg+')');		        	
		        	if(obj.flag==true){
						path = obj.msg;		
						window.location.href = "${path }/manage/note/noteFeedBackAudioDownloadZip.do?zipFilePath="+path;
					}else{
						alert("下载失败!");
					}
		        }
		    });			
		}
	}
	
	
	
	function selectAll(checked){
		var allCheckBoxs=document.getElementsByName("checkId") ;
		for (var i=0;i<allCheckBoxs.length ;i++){
			if(allCheckBoxs[i].type=="checkbox"){
			allCheckBoxs[i].checked=checked;
       }
    }  
}
</script>
</head>
<body>
<br>
    <fieldset style="width=90%">
  	<legend>信息反馈查询条件:</legend>
  		<form action="${path }/manage/note/noteFeedBackAudioListSearch.do" method="post" id="iform" name="iform">
	 		<table width="90%" cellpadding="1" cellspacing="1" border="0" align="center">
	 			<tr>
	 				<td align="right">创建时间:</td>
		    		<td align="left" colspan="3">
		    			<input type="text" id="createTimeStart" name="createTimeStart" 
		    				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" style="width:150px" value="${createTimeStart}"/>		
		    			- <input type="text" id="createTimeEnd" name="createTimeEnd" 
		    				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" style="width:150px" value="${createTimeEnd}"/>	
		    		</td>
		    		<td align="right">处理状态：</td>
		    		<td align="left"> 
	 					<select name="state">
	 						<option value="">全部</option>
	 						<option value="1" ${state ==1 ?'selected':'' }>已处理</option>
	 						<option value="0" ${state ==0 ?'selected':'' }>未处理</option>
	 					</select>
	 				</td>	
	 				<td align="right">手机号码：</td>
	 				<td align="left"><input type="text" name="phoneNumber" value="${phoneNumber}"></td>
		    					
	 			</tr>
	 			<tr>
	 				<td align="right">修改时间:</td>
		    		<td align="left" colspan="">
		    			<input type="text" id="modifyTimeStart" name="modifyTimeStart" 
		    				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" style="width:150px" value="${modifyTimeStart}"/>		
		    			- <input type="text" id="modifyTimeEnd" name="modifyTimeEnd" 
		    				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" style="width:150px" value="${modifyTimeEnd}"/>
		    		</td>	
	 				<td align="right">机型：</td>
	 				<td align="left"><input type="text" name="model" value="${model}"></td> 				
	 				<td colspan="4" align="right"><input type="submit" value="查询" /></td>
	 			
	 			</tr>
	 		</table>
  		</form>
  	</fieldset>
  	<br>
  	<div style="margin-bottom: 5px;float: right;">
  		<form action="">
  			<div id="showMsg"></div>
  			<!--<input type="button" onclick="downloadAll();" value="全部下载" />-->
  			<input type="button" onclick="downloadSelect();" value="下载" />
	  		<input type="button" onclick="feedBackUpdateState();" value="处理" />
	  		<input type="button" onclick="feedBackDelete();" value="删除" />
  		</form>
  	</div>
	<table id="mytable" class="test1" cellspacing="3" cellpadding="1" border="1" style="border-collapse: collapse;width:100%;border-color: #ccc;">
		 <tr style="font-weight: bold;background: #b3d4fd;">		 	
		 	<td height="18"><input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll(this.checked)"></td>
		 	<td><div align="center" >序号</div></td>
			<td height="18">ID号</td>
			<td height="18">IMEI</td>
			<td height="18">手机号码</td>
			<td height="18">音频名称</td>
			<td height="18">音频类型</td>
			<td height="18">音频大小</td>
			<td height="18">音频时长</td>
			<td height="18">音频文本</td>
			<td height="18">反馈信息</td>
			<td height="18">处理状态</td>
			<td height="18">是否删除</td>
			<td height="18">创建时间</td>
			<td height="18">修改时间</td>
			<td height="18" colspan="2">操作</td>
		</tr>
		<c:forEach items="${page.listData}" var="feedBackAudio" varStatus="stauts">
			<tr>				
				<td ><input type="checkbox" name="checkId" value="${feedBackAudio.id}"></td>
				<td align="center" >${stauts.index+1+page.pageSize*(page.pageIndex-1)}</td>
				<td style="${feedBackAudio.state==0?'':'color:red;'}">${feedBackAudio.id}</td>
				<td style="${feedBackAudio.state==0?'':'color:red;'}">${feedBackAudio.imei}</td>
				<td style="${feedBackAudio.state==0?'':'color:red;'}">${feedBackAudio.phoneNumber}</td>
				<td style="${feedBackAudio.state==0?'':'color:red;'}">${feedBackAudio.audioName}</td>
				<td style="${feedBackAudio.state==0?'':'color:red;'}">${feedBackAudio.audioType}</td>
				<td style="${feedBackAudio.state==0?'':'color:red;'}">${feedBackAudio.fileSize}</td>
				<td style="${feedBackAudio.state==0?'':'color:red;'}">${feedBackAudio.audioTime}s</td>
				<td style="${feedBackAudio.state==0?'':'color:red;'}">${feedBackAudio.audioText}</td>
				<td style="${feedBackAudio.state==0?'':'color:red;'}">${feedBackAudio.feedBack}</td>
				<td style="${feedBackAudio.state==0?'':'color:red;'}">${feedBackAudio.state==0?"未处理":"已处理"}</td>
				<td style="${feedBackAudio.state==0?'':'color:red;'}">${feedBackAudio.delFlg==0?"在用":"废弃"}</td>
				<td style="${feedBackAudio.state==0?'':'color:red;'}">${feedBackAudio.createTime}</td>
				<td style="${feedBackAudio.state==0?'':'color:red;'}">${feedBackAudio.modifyTime}</td>
				<td style="${feedBackAudio.state==0?'':'color:red;'}">
					<c:if test="${feedBackAudio.state==0}">
						<a href="#" onclick="openWin('${path }/manage/note/noteFeedBackAudioDetail.do?id=${feedBackAudio.id}','修改反馈信息','500','600',1)">处理</a>
					</c:if>					
				</td>
				<td>
					<a href="${path }/manage/note/noteFeedBackAudioDownload.do?id=${feedBackAudio.id}">下载</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div style="margin:20px 0;color: red;text-align: center;clear: both;">
    <pg:page url="${path }/manage/note/noteFeedBackAudioListSearch.do" page="${page}" 
    	pageIndex="pageIndex" homePage="首页" lastPage="末页" 
    	previousPage="上一页" nextPage="下一页" divClass="pageNav"/>
    </div>
</body>
</html>