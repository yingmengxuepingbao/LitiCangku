//$.ajax({
//type: 'POST',
//url: '/testDemo/test/getUserInfo',
//dataType: 'json',
//success: successuserinfo
//});
//function successuserinfo(res){
//	$('.userimage').attr("src",res.avatarIcon);
//	$('.username').html(res.userName);
//}
//$('#menu').perfectScrollbar({
//	wheelSpeed:0.1
//});
//加载菜单
$.ajax({
	type:"get",
//	url: './json/backstagemenu.json',
	url: '/IEAM/menuManagement/getUserMenuList',
	success: successmenu,
	error: function(XMLHttpRequest, textStatus, errorThrown) {
		
		},
});
//登录，获取用户信息
$.ajax({
//	url: './json/backstagemenu.json',
	url: '/IEAM/userManagement/getCurrentUser',
	success: function(result){
		result=JSON.parse(decodeURIComponent(result));
		if(result.code=='0'){
			localStorage.account=result.data.account;
			localStorage.userId=result.data.id;
			var isDefaultPwd = result.data.isDefaultPwd;
			if (isDefaultPwd == 1) { //是默认密码，提示修改
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "当前为默认密码，请及时修改密码!",
                    BtnL:"修改密码",
                    FunL:function(){
                        createNewTag('00002','修改密码','../accountManagement/modifyPersonalPassword');
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            }
			$('.username').html(result.data.nickname);
		}else{
			location.href="login.html";
		}
	},
	error: function(XMLHttpRequest, textStatus, errorThrown) {
		location.href="login.html";
	},
});
//全屏函数
function fullScream(element){
    var requestMethod = element.requestFullScreen || element.webkitRequestFullScreen || element.mozRequestFullScreen || element.msRequestFullScreen;
    requestMethod.call(element);
    fullscreamdiv.style.height="100%";
    fullscreamdiv.style.width="100%";
}

//退出全屏
function exitFullsSream(element){
    var elem= document;
    if (elem.webkitCancelFullScreen) {
        elem.webkitCancelFullScreen();
    } else if (elem.mozCancelFullScreen) {
        element.mozCancelFullScreen();
    } else if (elem.cancelFullScreen) {
        elem.cancelFullScreen();
    } else if (elem.exitFullscreen) {
        elem.exitFullscreen();
    } else {
        //浏览器不支持全屏API或已被禁用
    };
}
function successmenu(menujson){
	menujson=JSON.parse(decodeURIComponent(menujson));
	createMenu(menujson.data);
}
if(localStorage.account==null){
	location.href="login.html";
}
function logout(){
    localStorage.clear();
    $.ajax({
        type:"post",
        url:$.mpbGetHeaderPath()+"/userManagement/logout",
        data:{
            "_method":"delete"
        },
        success: function(result){

        },
        async:true
    });
    location.href=window.location.href.replace("index","login");
}

$('.username').html(localStorage.nickname);

logoImgUrl('./../framework/images/default/public-logo.png','./../framework/images/default/yunlogobf.png');

//获取未读的消息列表
getWorkList();
getNoticeLogList()
setInterval(function () {
    getWorkList();
    getNoticeLogList()
},300000);

function getWorkList(){
    $.ajax({
        type:"get",
        url:"/IEAM/messageRecord/getUnreadList",
        data:{
            status:'0'
        },
        success: function(res){
            var result=eval('(' + decodeURIComponent(res) + ')');
            if (result.code == 0) {
                $("#messageList").empty();
                var str='';
                if(result.data.totalNumber>0){
                    var data=result.data.messageRecords;
                    $("#messageNumber").show();
                    $("#messageNumber").text(result.data.totalNumber);
                    for(var i=0;i<data.length;i++){
                        if(i<3){
                            str+='<li>'+data[i].workName+'</li>';
                        }
                    }
                }
                else{
                    $("#messageNumber").hide();
                    $("#messageNumber").text("0");
                }
                str+='<li><input type="button" class="btn btn-self"  id="getAllWorkList"' +
                    'onclick="createNewTag(\'00003\',\'消息列表\',\'../messageRecordView/messageRecordList\')" value="查看全部 >"></li>';
                $("#messageList").html(str);
            }
            else {
                $.DialogByZ.Autofade({Content: result.message || "保存失败！"});
            }
        },
        error:function (err) {
            _commonFun.errorTips('');
        }
    });
}
function getNoticeLogList(){
    $.ajax({
        type:"get",
        url:"/IEAM/faultNotice/getNoticeLogList",
        data:{
            status:'0'
        },
        success: function(res){
            var result=eval('(' + decodeURIComponent(res) + ')');
            if (result.code == 0) {
                $("#callList").empty();
                var str='';

                if(result.data.totalNumber>0){
                    var data=result.data.faultNotices;

                    $("#callNumber").show();
                    $("#callNumber").text(result.data.totalNumber);
                    for(var i=0;i<data.length;i++){
                        if(i<3){
                            str+='<li>'+data[i].noticeContent+'</li>';
                        }
                    }
                }
                else{
                    $("#callNumber").hide();
                    $("#callNumber").text("0");
                }
                str+='<li><input type="button" class="btn btn-self"  id="getWorkLogList"' +
                    'onclick="createNewTag(\'00004\',\'报警信息\',\'../accountManagement/workCallLogList\')" value="查看全部 >"></li>';
                $("#callList").html(str);
            }
            else {
                $.DialogByZ.Autofade({Content: result.message || "保存失败！"});
            }
        },
        error:function (err) {
            _commonFun.errorTips('');
        }
    });
}
function toLargeScreen(){
    // $.ajax({
    //     type:"get",
    //     url:"/IEAM/userManagement/findScreenByUserAuth",
    //     data:{
    //     },
    //     success: function(res){
    //         var result=eval('(' + decodeURIComponent(res) + ')');
    //         if (result.code == 0) {
    //             var orgCOde = result.data;
    //             if (orgCOde == "HBD"){
    //                 window.open("http://39.105.202.17/donghua/Heda/index.html");
    //             }else if (orgCOde == "DHSN"){
    //                 window.open("http://39.105.202.17/donghua/index.html");
    //             }else if (orgCOde == "GLDJ"){
    //                 window.open("http://39.105.188.127:8081/gongli_page/index.html");
    //             }else{
    //                 $.DialogByZ.Autofade({Content: "您没有权限打开对应的大屏展示！"});
    //             }
    //         }else{
    //             $("#messageNumber").hide();
    //             $("#messageNumber").text("0");
    //             $("#messageList").empty();
    //         }
    //     },
    //     error:function (err) {
    //         _commonFun.errorTips('');
    //     }
    // });
    window.open("http://127.0.0.1:8848/bigscreen/index.html");
}
$(function () {
    // $(document).on('mouseenter','#messageRg',function (e) {
    //     $(".message-list-hide").slideDown(200);
    // });
    // $(document).on('mouseleave','#messageRg',function (e) {
    //     $(".message-list-hide").slideUp(200);
    // });
    // $(document).on('mouseenter','#callPolice',function (e) {
    //     $(".call-list-hide").slideDown(200);
    // });
    // $(document).on('mouseleave','#callPolice',function (e) {
    //     $(".call-list-hide").slideUp(200);
    // });
    $(document).on('click','#setFullScreen',function (e) {
        /*fullScream(fullscreamdiv);*/
        toLargeScreen();
    });
    //加载用户权限
    getOperationPermission();
});
//页面所拥有的权限 控制到 按钮的 显示
var operationData = {};
//查询用户的 权限列表
// 标码
// add  新增
// eidt  编辑
// delete  删除
// import  导入
// export  导出
function getOperationPermission(menuid) {
    $.ajax({
        type:"get",
        url:"/IEAM/permissionManagement/group/getPermissionsByUrlId",
        data:{
            urlId:menuid
        },
        success: function(res){
            var result = JSON.parse(decodeURIComponent(res));
            if(result.code == 0 ){
                operationData = result.data;
            }
        },
        error: function (res) {

        }
    });
}
//父页面方法返回页面参数供子页面调用
var parentOrderParam=null;
function returnOrderParam(paramId) {
    parentOrderParam=paramId;
    return paramId;
}
createNewTag('758','工作台','/IEAM/accountManagement/workBench');

