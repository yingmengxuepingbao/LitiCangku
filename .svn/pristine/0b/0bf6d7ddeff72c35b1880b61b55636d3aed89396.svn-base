var accountReady=true;
var newPasswordReady=false;
var confirmPasswordReady=false;

var userPasswordRegExp=/^\w{8,18}$/;
//			旧密码失去焦点发送请求
			$('#oldPassword').blur(function(){
				
				var glyphicon=$('#newPassword').siblings('.glyphicon');

			});
	
	
			$('#newPassword').bind('input propertychange',function(){
				var glyphicon=$('#newPassword').siblings('.glyphicon');
				if(userPasswordRegExp.test($('#newPassword').val())){
					newPasswordReady=true;
					glyphicon.css('display','inline-block');
					glyphicon.removeClass('glyphicon-remove');
					glyphicon.addClass('glyphicon-ok');
					glyphicon.html('')
					glyphicon.css('color','green');
				}else{
					newPasswordReady=false;
					glyphicon.css('display','inline-block');
					glyphicon.removeClass('glyphicon-ok');
					glyphicon.addClass('glyphicon-remove');
					glyphicon.html('以字母开头，长度在8~18之间，只能包含字符、数字和下划线');
					glyphicon.css('color','red');
				}
				
			});
			
			$('#confirmPassword').bind('input propertychange',function(){
				var newPassword=$('#newPassword').val();
				var confrimPassword=$('#confirmPassword').val();
				var glyphicon=$('#confirmPassword').siblings('.glyphicon');
				if(newPassword==confrimPassword){
					confirmPasswordReady=true;
					glyphicon.css('display','inline-block');
					glyphicon.removeClass('glyphicon-remove');
					glyphicon.addClass('glyphicon-ok');
					glyphicon.html('');
					glyphicon.css('color','green');
				}else{
					confirmPasswordReady=false;
					glyphicon.css('display','inline-block');
					glyphicon.removeClass('glyphicon-ok');
					glyphicon.addClass('glyphicon-remove');
					glyphicon.html('')
					glyphicon.css('color','red');
				}
			});
			
			$('#myButton').on('click',function(){
				var newPassword1=$('#newPassword').val();
				var confirmPassword1=$('#confirmPassword').val();
				if(newPassword1!=null && newPassword1!=undefined && newPassword1 != confirmPassword1){
					newPasswordReady=false;
					var glyphicon=$('#confirmPassword').siblings('.glyphicon');
					glyphicon.css('display','inline-block');
					glyphicon.removeClass('glyphicon-ok');
					glyphicon.addClass('glyphicon-remove');
					glyphicon.html('输入的两次新密码，不一致！');
					glyphicon.css('color','red');
					return;
				}

				if(accountReady&&newPasswordReady&&confirmPasswordReady&&$('#oldPassword').val()!=''){
					
					$(this).attr('data-loading-text','密码修改中...');
					$(this).button('loading');
					
					var oldPassword=$('#oldPassword').val();
					var newPassword=$('#newPassword').val();
					var objD={
						userId:localStorage.userId,
                        oldPassword:oldPassword,
                        newPassword:newPassword
					};
                    new doAjax({
                        type:'post',
                        url: $.mpbGetHeaderPath()+'/userManagement/changePassword',
                        data:objD,
                        callBack: function (result) {
                            $('#warningDiv').modal('show');
                            $('#warningDiv .modal-body span').html(result.message);
                            $("#myButton").button('reset');
                        }
                    });
					// $.ajax({
                     //    type:"post",
                     //    url: $.mpbGetHeaderPath()+"/userManagement/changePassword",
                     //    beforeSend: function(xhr){xhr.setRequestHeader('token', localStorage.getItem('token'));},//这里设置header
                     //    headers:{'Content-Type':'application/json;charset=utf8','token':localStorage.getItem('token')},
					// 	data:{
					// 		userId:localStorage.userId,
					// 		oldPassword:oldPassword,
					// 		newPassword:newPassword
					// 	},
					// 	success: function(result){
					// 		$('#warningDiv').modal('show');
					// 		$('#warningDiv .modal-body span').html(result.message);
					// 		$("#myButton").button('reset');
					// 	},
					// 	error:function(result){
					// 		$('#warningDiv').modal('show');
					// 		$('#warningDiv .modal-body span').html('服务器错误');
					// 		$("#myButton").button('reset');
					// 	},
					// 	async:true
					// });
				}
			})
			
			
			
			
			