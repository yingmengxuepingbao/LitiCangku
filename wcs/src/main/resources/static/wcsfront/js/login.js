//import "../css/login.css";
$("#login").click(function(){
	submitUserInfo();
})
//点击打开公众号弹窗
$(document).on('click', '#popOpen', function () {
    $(".modal1").show();
});
//点击打开安卓app下载弹窗
$(document).on('click', '#popOpen_an', function () {
    $(".modal2").show();
});
//关闭弹窗
$(document).on('click', '#popClose', function () {
    $(".modal").hide();
})
//关闭弹窗
$(document).on('click', '#popClose_an', function () {
    $(".modal").hide();
})
//轮播图
$(".carousel-inner").carousel({
	interval:2000,
	pause:"hover",
	wrap:true,
	keyboard:true
})
//取消提示框
// $(document).on('click', '#warning .close', function () {
//     $(".userform").css("height","450px");
// });
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

//登陆findb
function loginReport(account,password) {
	var userName = account;
	var pwd = password;
	if (userName=='root'){
		//升级为帆软管理员
		userName='admin';
		pwd='admin';
	}
	// var userName = "admin";
	// var pwd = "admin";
	$.ajax({
		type:'get',
		url: localStorage.reportAddr,
		dataType: "jsonp",
		data:{
			"op":"api",
			"cmd":"bi_login",
			"bi_username":userName,
			"bi_password":pwd
		},
		success:function(result){
			if(result.result=="Login success!"){
				localStorage.report = 1;
			}else {
				console.error(result);
			}
		},
		error :function(error){
			console.error(error);
		}
	})
}
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
				"password":userpassword,
				"verificationCode":identify
			},
	//		dataType:'json',
			success: function(result){
				$("#login").button('reset');
				result=JSON.parse(decodeURIComponent(result));
				var code = result.code;
				switch(code){
					case "0":

							localStorage.account=result.data.account;
							localStorage.password=userpassword;
							localStorage.userId=String(result.data.id);
							localStorage.nickname=result.data.nickname;
							localStorage.reportAddr = result.message;
                        	localStorage.departmentId=result.data.deptId;
                        	localStorage.setItem("departmentName",result.data.deptName);
							var oldHref=window.location.href;
                        	var newHref=oldHref.replace("login","index");
                        	//报表未登录
                            // localStorage.report = 0;
                            // loginReport(username,userpassword);
                            // var onlyBigScreen = result.data.isOnlyBigScreen;
                            // if (onlyBigScreen == "Y"){
                            //     location.href = "http://www.haibigdata.com/bigscreen/index.html";
                            // }else {
							location.href=newHref;
							// }
							loadidentifyimg();
							break;
					default:
						if(!$('#war').children("#warning").length){
						    // $(".userform").css("height","580px");
							var div=$('<div class="alert alert-warning alert-dismissible" id="warning" role="alert">');
							var button1=$('<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
							var strong=$('<strong>Warning!</strong><span class="erromessage">'+result.message+'</span>');
							button1.appendTo(div);
							strong.appendTo(div);
							div.prependTo($('#war'));
							loadidentifyimg();
						}else{
							$('#war .erromessage').html(result.message);
							loadidentifyimg();
						}
					break;
				}
			},
			error:function(result){
							$("#login").button('reset');
							if(!$('#war').children("#warning").length){
				//				如果
								var div=$('<div class="alert alert-warning alert-dismissible" id="warning" role="alert">');
								var button1=$('<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
								var strong=$('<strong>Warning!</strong><span class="erromessage">'+'服务器异常，请联系管理员'+'</span>');
								button1.appendTo(div);
								strong.appendTo(div);
								div.prependTo($('#war'));
								var message =  JSON.parse(result.responseText);

							}else{
								var message =  JSON.parse(result.responseText);
								

								$('#war .erromessage').html('服务器运行异常！');
							}
							loadidentifyimg();
			},
			async:false
		});
	}
}
$(function(){
	
})
//$('.identifyimg').click(function(){
//	$('.identifyimg')[0].src="/IEAM/userManagement/getVerificationCode?date=" + new Date();
//	$('.identifyimg').attr('src','/IEAM/userManagement/getVerificationCode?date=' + new Date());
//	document.getElementsByClassName('identifyimg').src="/IEAM/userManagement/getVerificationCode?date=" + new Date();
//	console.log('qqq');
//})
function loadidentifyimg(){
//	$('.identifyimg').attr('src','/IEAM/userManagement/getVerificationCode?date=' + new Date());
// 		$('.identifyimg')[0].src=$.mpbGetHeaderPath()+"/userManagement/getVerificationCode?date=" +Math.random();
//		$('.identifyimg')[0].src="/IEAM/userManagement/getVerificationCode";
//	document.getElementsByClassName('identifyimg').src="/IEAM/userManagement/getVerificationCode?date=" + new Date();
}

