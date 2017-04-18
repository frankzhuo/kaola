<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@include file="base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title></title>
<link type="text/css" rel="stylesheet"
	href="/resources/css/manage.css"></link>
<script type="text/javascript" src="/resources/js/public.js"></script>
<script src="/resources/js/AC_RunActiveContent.js"></script>
<script type="text/javascript" src="/resources/js/jquery-1.4.2.js"></script>
<script type="text/javascript">

	// 所需 Flash 的主版本号
	var requiredMajorVersion = 10;
	// 所需 Flash 的次版本号
	var requiredMinorVersion = 0;
	// 所需 Flash 的版本号
	var requiredRevision = 2;
	
	function initflash(){
		
		//var url = "http://localhost:8080/flash_service/DownloadMp3";
		//var url = "http://voice.lenovomm.com:8080/manage/note/noteFeedBackAudioDownload.do?id="+${feedBackAudio.id};
		var url = "http://localhost:81/manage/note/noteFeedBackAudioDownload.do?id="+${feedBackAudio.id};
		thisMovie("myflash").getMp3Url(url);
	}
	
	function test(url){
		
	}
	
	var jsReady = false;
	function isReady() {
		return jsReady;
    }
    function pageInit() {
        jsReady = true;
    }
	
	function thisMovie(movieName) {
		if (navigator.appName.indexOf("Microsoft") != -1) {
			return window[movieName]
		}
		else {
		    return document[movieName]
		}
	}


	function noteFeedBackUpdate() {
		var noteTypeName = $("#noteTypeName").val();
		var feedBack = $("#feedBack").val();
		if(feedBack==null || feedBack.length >200){
			alert("反馈信息过长")
			return ;
		}
		var id = $("#id").val();
		var isSys=$("#isSys").val();
		$.ajax({
			type : "POST",
			url : "${path }/manage/note/noteFeedBackAudioUpdate.do",
			dataType : "data",
			data : "id=" + id + "&feedBack=" + feedBack,
			error : function() {
				alert("网络异常!");
			},
			success : function(json) {
				json = eval('('+json+')');
				if (json['result'] == true) {
					window.opener.document.location.reload();
					window.close();
				} else {
					alert("修改失败!");
				}
			}
		});
	}
	
	
	
	
</script>
</head>
<body>
	<table width="90%" cellpadding="3" cellspacing="3" border="1" 
		align="center" style="margin:50px auto;">
		<tr><td colspan="2" style="background:#ccc;text-align: center;">用户反馈处理</td></tr>
		<tr>
			<td width="30" align="right">ID号:</td>
			<td width="30" align="center">${feedBackAudio.id} <input
				type="hidden" id="id" value="${feedBackAudio.id}" />
			</td>
		</tr>
		<tr>
			<td width="50%" align="right">IMEI:</td>
			<td width="50%" align="center">${feedBackAudio.imei}</td>
		</tr>
		<tr>
			<td width="50%" align="right">手机号码:</td>
			<td width="50%" align="center">${feedBackAudio.phoneNumber}</td>
		</tr>
		<tr>
			<td width="50%" align="right">音频名称:</td>
			<td width="50%" align="center">${feedBackAudio.audioName}</td>
		</tr>
		<tr>
			<td width="50%" align="right">音频类型:</td>
			<td width="50%" align="center">${feedBackAudio.audioType}</td>
		</tr>
		<tr>
			<td width="50%" align="right">音频大小:</td>
			<td width="50%" align="center">${feedBackAudio.fileSize}</td>
		</tr>
		<tr>
			<td width="50%" align="right">音频时长:</td>
			<td width="50%" align="center">${feedBackAudio.audioTime}s</td>
		</tr>
		<tr>
			<td width="50%" align="right">音频文本:</td>
			<td width="50%" align="center">${feedBackAudio.audioText}</td>
		</tr>
		
		<tr>
			<td width="50%" align="right">处理状态:</td>
			<td width="50%" align="center">${feedBackAudio.state==0?"未处理":"已处理"}</td>
		</tr>
		<tr>
			<td width="50%" align="right">是否删除:</td>
			<td width="50%" align="center">${feedBackAudio.delFlg==0?"在用":"废弃"}</td>
		</tr>
		<tr>
			<td width="50%" align="right">创建时间:</td>
			<td width="50%" align="center">${feedBackAudio.createTime}</td>
		</tr>
		<tr>
			<td width="50%" align="right">修改时间:</td>
			<td width="50%" align="center">${feedBackAudio.modifyTime}</td>
		</tr>
		<tr>
			<td width="50%" align="right">反馈信息:</td>
			<td width="50%" align="center"><textarea rows="5" cols="20"
					id="feedBack" >${feedBackAudio.feedBack}</textarea></td>
		</tr>
		<tr>
			<td width="50%" align="right">音频播放:</td>
			<td width="50%" align="center">
				<div>
					<script language="JavaScript" type="text/javascript">
			
						var hasRightVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);
						if(hasRightVersion) {  // 如果我们检测到了可接受的版本
							// 嵌入 Flash 影片
							AC_FL_RunContent(
								'codebase', 'http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,2,0',
								'width', '300',
								'height', '120',
								'src', '${path}/resources/flash/mp3',
								'quality', 'best',
								'pluginspage', 'http://www.adobe.com/go/getflashplayer',
								'align', 'middle',
								'play', 'true',
								'loop', 'true',
								'scale', 'showall',
								'wmode', 'opaque',
								'devicefont', 'false',
								'id', 'myflash',
								'bgcolor', '#ffffff',
								'name', 'myflash',
								'menu', 'false',
								'allowFullScreen', 'false',
								'allowScriptAccess','sameDomain',
								'movie', '${path}/resources/flash/mp3',
								'salign', ''
								); //end AC code
						} else {  // Flash 太旧或者我们无法检测到插件
						var alternateContent;
							if (navigator.appName.indexOf("Microsoft") != -1) {
						        alternateContent = '您的系统或浏览器没有安装flash插件<br>或flash插件版本过低<br>请下载相关插件<br> '
								+ '<a href="http://fpdownload.macromedia.com/get/flashplayer/current/licensing/win/install_flash_player_10_active_x.exe">可点击这里获得Flash</a>';
						    }
						    else {
						        alternateContent = '您的系统或浏览器没有安装flash插件<br>或flash插件版本过低<br>请下载相关插件<br> '
								+ '<a href="http://www.adobe.com/go/getflashplayer/">可点击这里获得Flash</a>';
						    }
															
							document.write(alternateContent);  // 插入非 Flash 内容							
						
						}
					</script>
				</div>
			</td>
		</tr>
	</table>
	<div style="float: right; margin-right: 20px;">
		<input type="button" onclick="noteFeedBackUpdate();" value="处理">
		<input type="button" onclick="window.close();" value="关闭">
	</div>
	<script type="text/javascript">   
		window.onload=pageInit;
	</script>
</body>
</html>