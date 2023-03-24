//import "../css/login.css";
$("#login").click(function(){
	submitUserInfo();
})
//轮播图
$(".carousel-inner").carousel({
	interval:2000,
	pause:"hover",
	wrap:true,
	keyboard:true
})
document.onkeydown=function(event){
  	var e = event || window.event;    
    if(e && e.keyCode==13){ 
		submitUserInfo();
          //阻止回车默认事件，全部阻止
        return false;
     }
	 //阻止所有键盘默认事件
	//return false;
}; 
function submitUserInfo(){
	var username=$('#username').val();
	var userpassword=$("#userpassword").val();
	var identify=$('.identifycode').val();
	if(username!=""&&userpassword!=""&&identify!=""){
		$("#login").button('loading');
		$.ajax({
			type:"post",
			url:$.mpbGetHeaderPath()+"/userManagement/login",
			data:{
				"account":username,
				"password":userpassword
				// "verificationCode":identify
			},
	//		dataType:'json',
			success: function(result){
				$("#login").button('reset');
				// result=JSON.parse(decodeURIComponent(result));
				var code = result.code;
				switch(code){
					case "0":
							localStorage.account=result.data.userInfo.account;
							localStorage.userId=String(result.data.userInfo.id);
							localStorage.nickname=result.data.userInfo.nickname;
                        	localStorage.token=result.data.token;
							location.href="index.html";
	//						loadidentifyimg();
							break;
					default:
						if(!$('.alertwarning').children("#warning").length){
							var div=$('<div class="alert alert-warning alert-dismissible" id="warning" role="alert">');
							var button1=$('<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
							var strong=$('<strong>Warning!</strong><span class="erromessage">'+result.message+'</span>');
							button1.appendTo(div);
							strong.appendTo(div);
							div.prependTo($('.alertwarning'));
							loadidentifyimg();
						}else{
							$('.alertwarning .erromessage').html(result.message);
							loadidentifyimg();
						}
					break;
				}
			},
			error:function(result){
							$("#login").button('reset');
							if(!$('.alertwarning').children("#warning").length){
				//				如果
								var div=$('<div class="alert alert-warning alert-dismissible" id="warning" role="alert">');
								var button1=$('<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
								var strong=$('<strong>Warning!</strong><span class="erromessage">'+'服务器异常，请联系管理员'+'</span>');
								button1.appendTo(div);
								strong.appendTo(div);
								div.prependTo($('.alertwarning'));
								var message =  JSON.parse(result.responseText);

							}else{
								var message =  JSON.parse(result.responseText);
								

								$('.alertwarning .erromessage').html('服务器运行异常！');
							}
							loadidentifyimg();
			},
			async:false
		});
	}
}
$(function(){
	//替换主页名字
    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(1,pathName.substr(1).indexOf('/')+1);
    if ("wms"==projectName){
        document.getElementById("owner-name").innerText="宝佳集团WMS";
	}else if ("wcs"==projectName){
        document.getElementById("owner-name").innerText="宝佳集团WCS";
    }else{
        console.log("根路径="+projectName);
	}
})
//$('.identifyimg').click(function(){
//	$('.identifyimg')[0].src="/javawebframework/userManagement/getVerificationCode?date=" + new Date();
//	$('.identifyimg').attr('src','/javawebframework/userManagement/getVerificationCode?date=' + new Date());
//	document.getElementsByClassName('identifyimg').src="/javawebframework/userManagement/getVerificationCode?date=" + new Date();
//	console.log('qqq');
//})
function loadidentifyimg(){
//	$('.identifyimg').attr('src','/javawebframework/userManagement/getVerificationCode?date=' + new Date());
// 		$('.identifyimg')[0].src=$.mpbGetHeaderPath()+"/userManagement/getVerificationCode?date=" +Math.random();
//		$('.identifyimg')[0].src="/javawebframework/userManagement/getVerificationCode";
//	document.getElementsByClassName('identifyimg').src="/javawebframework/userManagement/getVerificationCode?date=" + new Date();
}

