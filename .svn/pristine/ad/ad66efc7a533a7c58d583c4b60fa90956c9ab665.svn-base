// loaderdiv
$(function () {
    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(1,pathName.substr(1).indexOf('/')+1);
    var mainPageUrl;
    if ("wms"==projectName){
        mainPageUrl = $.mpbGetHeaderPath()+'/wmsfront/main/main.html';
        $('#title').text("WMS管理系统");
    }else if ("wcs"==projectName){
		$('#title').text("WCS管理系统");
    }else{
        console.log("根路径="+projectName);
    }
	$("#loaderdiv").load(mainPageUrl);
});
//加载菜单
$.ajax({
	type:"get",
	url:$.mpbGetHeaderPath()+ '/menuManagement/getUserMenuList',
    beforeSend: function(xhr){xhr.setRequestHeader('token', localStorage.getItem('token'));},//这里设置header
    headers:{'Content-Type':'application/json;charset=utf8','token':localStorage.getItem('token')},
    success: successmenu,
	error: function(XMLHttpRequest, textStatus, errorThrown) {
		
		},
});
//登录，获取用户信息
new doAjax({
    type:'get',
    url: $.mpbGetHeaderPath()+'/userManagement/getUserById',
    data:{
        userId:localStorage.getItem("userId")
    },
    callBack: function (result) {
        if(result.code=='0'){
            console.log(result)
            localStorage.account=result.data.account;
            localStorage.userId=result.data.id;
            localStorage.nickname=result.data.nickname;
			localStorage.departmentIds=result.data.departmentIds;
			localStorage.roleIds=result.data.roleIds;
			localStorage.entryDate=result.data.entryDate;
			localStorage.officePhone=result.data.officePhone;
			localStorage.phone=result.data.phone;
			localStorage.email=result.data.email;
			localStorage.description=result.data.description;
			localStorage.status=result.data.status;
			localStorage.creatDate=result.data.creatDate;
            $('.username').html(result.data.nickname);
            //登入成功
			//let nowTime = timestampToTime(new Date().toLocaleString('en-US',{hour12: false}).split(" "));
			//let creatTime = timestampToTime(new Date(result.data.creatDate).toLocaleString('en-US',{hour12: false}).split(" "));
			var nowTime =Date.parse(new Date());
			var creatTime =Date.parse(new Date(result.data.creatDate));
			var thisDays=(nowTime- creatTime)/(24*60*60*1000);
			console.log("时间："+nowTime  +"表："+creatTime+"thisDays:"+thisDays)
			var day=90;
			if(thisDays > day){
				alert("您密码已经"+day+"天未更新，请及时更换新密码！");
			}
        }else{
            location.href="login.html";
        }
    }
});

//时间转换：data 转换成：2022-8-31 11:08:34
function timestampToTime(times) {
	let time = times[1]
	let mdy = times[0]
	mdy = mdy.split('/')
	let month = parseInt(mdy[0]);
	let day = parseInt(mdy[1]);
	let year = parseInt(mdy[2])
	return year + '-' + month + '-' + day + ' ' + time
}


function successmenu(menujson){
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
        beforeSend: function(xhr){xhr.setRequestHeader('token', localStorage.getItem('token'));},//这里设置header
        headers:{'Content-Type':'application/json;charset=utf8','token':localStorage.getItem('token')},
		data:{
			"_method":"delete"
		},
		success: function(result){
			var code = result.code;
			switch(code){
				case 0:
						location.href="login.html";
						break;
				default:

						break;
			}
			location.href="login.html";
		},
		async:true
	});

	location.href="login.html";
}

$('.username').html(localStorage.nickname);

logoImgUrl('./images/default/yunlogo.png','./images/default/yunlogobf.png');
//父页面方法返回页面参数供子页面调用
var parentModelParam=null;
function returnModelParam(paramId) {
    parentModelParam=paramId;
    return paramId;
}

var parentPredictParam=null;
function returnPredictParam(paramId) {
    parentPredictParam=paramId;
    return paramId;
}
//session 时间长修改
$("select[name='sessionSelect']").change(function(){
	var sessionTime = $(this).val();
	console.log("sessionTime : "+sessionTime);
	$.ajax({
		type:"put",
		url:$.mpbGetHeaderPath()+"/userManagement/usersUpSession",
		headers:{'token':localStorage.getItem('token')},
		data    : {sessionTime:sessionTime},
		success:function (result) {
			if(result.code=='0'){
				Confirm.show('session时长',"修改成功！");
			}else{
				Confirm.show('session时长',"修改失败！");
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			Confirm.show('session时长',"修改异常！");
		},
	});
});