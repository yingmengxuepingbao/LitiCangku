var nameRegExp=/^[A-Za-z0-9_\u4e00-\u9fa5]{2,16}$/;
var accountRegExp=/^[A-Za-z0-9_]{8,16}$/;
var emailRegExp=/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
var phoneNumberRegExp=/^1[0-9]{10}$/;
var nameReady=false;
var emailRegExpReady=false;
var phoneNumberReady=false;
//输入判断
$('#name').bind('input propertychange',function(){
	var glyphicon=$('#name').siblings('.glyphicon');
	if(nameRegExp.test($(this).val())){
		nameReady=true;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-remove');
		glyphicon.addClass('glyphicon-ok');
		glyphicon.html('')
		glyphicon.css('color','green');		
	}else{
		nameReady=false;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-ok');
		glyphicon.addClass('glyphicon-remove');
		glyphicon.css('color','red');
	}
})
$('#email').bind('input propertychange',function(){
	var glyphicon=$('#email').siblings('.glyphicon');
	if(emailRegExp.test($(this).val())){
		emailRegExpReady=true;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-remove');
		glyphicon.addClass('glyphicon-ok');
		glyphicon.html('')
		glyphicon.css('color','green');		
	}else{
		emailRegExpReady=false;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-ok');
		glyphicon.addClass('glyphicon-remove');
		glyphicon.css('color','red');
	}
})
$('#phoneNumber').bind('input propertychange',function(){
	var glyphicon=$('#phoneNumber').siblings('.glyphicon');
	if(phoneNumberRegExp.test($(this).val())){
		phoneNumberReady=true;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-remove');
		glyphicon.addClass('glyphicon-ok');
		glyphicon.html('')
		glyphicon.css('color','green');		
	}else{
		phoneNumberReady=false;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-ok');
		glyphicon.addClass('glyphicon-remove');
		glyphicon.css('color','red');
	}
})


$('#account').val(localStorage.account);
$('#myButton').click(function(){
	if(nameReady&&phoneNumberReady&&emailRegExpReady){
		var userName=$('#name').val();
		var email=$('#email').val();
		var phoneNumber=String($('#phoneNumber').val());
		var dataObj={
            userId	: localStorage.getItem("userId"),
            nickname: userName,
            email	: email,
            phone	: phoneNumber,
            account	    	:localStorage.getItem("account"),
            departmentIds	:localStorage.getItem("departmentIds"),
            roleIds			:localStorage.getItem("roleIds"),
            entryDate		:localStorage.getItem("entryDate"),
            officePhone		:localStorage.getItem("officePhone"),
            description		:localStorage.getItem("description"),
            status      	:localStorage.getItem("status"),
		}
		// $.ajax({
         //    type:"post",
		// 	url:  $.mpbGetHeaderPath()+'/userManagement/updateUserInformation',
		// 	beforeSend: function(xhr){xhr.setRequestHeader('token', localStorage.getItem('token'));},//这里设置header
		// 	headers:{'Content-Type':'application/json;charset=utf8','token':localStorage.getItem('token')},
		// 	data:{
		// 		userId	: localStorage.getItem("userId"),
		// 		nickname: userName,
		// 		email	: email,
		// 		phone	: phoneNumber
		// 	},
		// 	success: function(result){
		// 		$('#warningDiv .modal-body span').html(result.message);
		// 		$('#warningDiv').modal('show');
		// 		var myButton = $("#myButton").button('reset');
		// 	},
		// 	error:function(error){
		// 		$('#warningDiv .modal-body span').html('修改失败，服务器无响应');
		// 		$('#warningDiv').modal('show');
		// 		$("#myButton").button('reset');
		// 	},
		// 	async:true
		// });
        new doAjax({
            type:'post',
            url: $.mpbGetHeaderPath()+'/userManagement/updateUserInformation',
            data:dataObj,
            callBack: function (result) {
                $('#warningDiv .modal-body span').html(result.message);
                		$('#warningDiv').modal('show');
                		var myButton = $("#myButton").button('reset');
            }
        });
	}
})
			