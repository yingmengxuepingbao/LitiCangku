var authorityUrl = {};//按钮权限，以后做
var recordNumber=0;//记录当前页有多少数据
var pageNumber=0;//记录一共多少页
var ifWindowOpen=false;
$(authorityUrl).each(function(index,item){
});
var eyeClose = true//查看密码眼睛图标状态，true为关闭；
var accountRegExp=/^[A-Za-z0-9_]{3,20}$/;
var userPasswordRegExp=/^[a-zA-Z0-9,.'"!@#$%^&*-=+]{8,18}$/;
var nameRegExp=/^[A-Za-z0-9_\u4e00-\u9fa5]{2,16}$/;
var emailRegExp=/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
var phoneNumberRegExp=/^1[0-9]{10}$/;
//拖动窗口
$('#searchState').width($('#searchAccount').width()+3);
$('#searchState').height($('#searchAccount').height()+5);
getUserlist();
//获取当前用户可以管理的用户列表
function getUserlist(){
	$('#loading').css('display','block');
	$('#reloadTable').css('display','block');
	$.ajax({
		type:"get",
		url: $.mpbGetHeaderPath()+'/userManagement/getUserList',
		data:{
			"account":$('#searchAccount').val()
			,"nickname":$('#searchNickName').val()
			,"email":$('#searchEmail').val()
			,"phone":$('#searchPhone').val()
			,"status":$('#searchState').val()
			,"currentPage":Number($('.currentPage').val())
		},
		success: function(result){
			result=JSON.parse(decodeURIComponent(result));
			$('#loading').css('display','none');
			var code = result.code;
			switch(code){
				case '0':
					successfulAccess(result.data);
					break;
				default:
					$('#warningDiv').modal('show');
					$('#warningDiv .modal-body span').html(result.message);
					break;
			}
		},
		error:function(result){
			$('#loading').css('display','none');
			$('#warningDiv').modal('show');
			$('#warningDiv .modal-body span').html(result.message);
		},
		async:true
	});
}
$('#refrechTop').click(function(){
	$('.currentPage').val(1);
	getUserlist();
})
//检测用户名等
var accountReady=false;
var passwordReady=false;
var nicknameReady=false;
var emailReady=false;
var phoneReady=false;
$('#account').bind('input propertychange',function(){
	var glyphicon=$('#account').siblings('.glyphicon');
	if(accountRegExp.test($('#account').val())){
		accountReady=true;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-remove');
		glyphicon.addClass('glyphicon-ok');
		glyphicon.html('')
		glyphicon.css('color','green');
	}else{
		accountReady=false;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-ok');
		glyphicon.addClass('glyphicon-remove');
		glyphicon.html('');
		glyphicon.css('color','red');
	}
});
$('#password').bind('input propertychange',function(){
	var glyphicon=$('#password').siblings('.require');
	if(userPasswordRegExp.test($('#password').val())){
		passwordReady=true;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-remove');
		glyphicon.addClass('glyphicon-ok');
		glyphicon.html('')
		glyphicon.css('color','green');
	}else{
		passwordReady=false;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-ok');
		glyphicon.addClass('glyphicon-remove');
		glyphicon.html('');
		glyphicon.css('color','red');
	}
});
$('#nickname').bind('input propertychange',function(){
	var glyphicon=$('#nickname').siblings('.glyphicon');
	if(nameRegExp.test($('#nickname').val())){
		nicknameReady=true;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-remove');
		glyphicon.addClass('glyphicon-ok');
		glyphicon.html('')
		glyphicon.css('color','green');
	}else{
		nicknameReady=false;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-ok');
		glyphicon.addClass('glyphicon-remove');
		glyphicon.html('');
		glyphicon.css('color','red');
	}
});
$('#email').bind('input propertychange',function(){
	var glyphicon=$('#email').siblings('.glyphicon');
	if(emailRegExp.test($('#email').val())){
		emailReady=true;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-remove');
		glyphicon.addClass('glyphicon-ok');
		glyphicon.html('')
		glyphicon.css('color','green');
	}else{
		emailReady=false;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-ok');
		glyphicon.addClass('glyphicon-remove');
		glyphicon.html('');
		glyphicon.css('color','red');
	}
});
$('#phone').bind('input propertychange',function(){
	var glyphicon=$('#phone').siblings('.glyphicon');
	if(phoneNumberRegExp.test($('#phone').val())){
		phoneReady=true;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-remove');
		glyphicon.addClass('glyphicon-ok');
		glyphicon.html('')
		glyphicon.css('color','green');
	}else{
		phoneReady=false;
		glyphicon.css('display','inline-block');
		glyphicon.removeClass('glyphicon-ok');
		glyphicon.addClass('glyphicon-remove');
		glyphicon.html('');
		glyphicon.css('color','red');
	}
});

//添加用户按钮
$('#addUserWindows .submit').click(function(){
	if(accountReady&&passwordReady&&nicknameReady&&emailReady&&phoneReady){
		$('#addUserWindows .submit').button('loading');
		var account=$('#addUserWindows #account').val()
		var password=$('#addUserWindows #password').val()
		var nickname=$('#addUserWindows #nickname').val()
		var email=$('#addUserWindows #email').val()
		var phone=$('#addUserWindows #phone').val()
		$.ajax({
			type:"post",
			url: $.mpbGetHeaderPath()+"/userManagement/addUser",
			data:{
				"account":account,
				"password":password,
				"nickname":nickname,
				"email":email,
				"phone":phone
			},
			success: function(result){
				result=JSON.parse(decodeURIComponent(result));

				var code = result.code;
				switch(code){
					case 0:
					$('#warningDiv').modal('show');
					$('#warningDiv .modal-body span').html(result.message);
							break;
					default:
					$('#warningDiv').modal('show');
					$('#warningDiv .modal-body span').html(result.message);
							break;
				}
				$('#addUserWindows .submit').button('reset');
				closeWindows();
				var page=$('.currentPage').val();
				var dataPerPage =$('.dataPerPage').val();
				reload(page,dataPerPage);
				
			},
			error:function(result){
				$('#addUserWindows .submit').button('reset');
				$('#warningDiv').modal('show');
				$('#warningDiv .modal-body span').html('服务器错误');
				var page=$('.currentPage').val();
				var dataPerPage =$('.dataPerPage').val();
				reload(page,dataPerPage);
			},
			async:true
		});
	}
});

//查看密码
$('#showPassword').click(function(){
	if(eyeClose){
	$('#showPassword').removeClass('glyphicon-eye-close');
	$('#showPassword').addClass('glyphicon-eye-open');
	$('#addUserWindows #password').attr('type','text');
	eyeClose=false;
	}else{
	$('#showPassword').removeClass('glyphicon-eye-open');
	$('#showPassword').addClass('glyphicon-eye-close');
	$('#addUserWindows #password').attr('type','password');						
	eyeClose=true;
	}
});

//将用户列表添加到table中
function successfulAccess(data){
	$('#logs table tbody').empty();
//	$('#logs table tbody')[0].parentNode.removeChild($('#logs table tbody')[0]);
	$('.dataNumber').html(data.userTotalCount);
	$(data.userList).each(function(index,item){
		var tr=$('<tr></tr>');
		var account=$('<td class="account" userId="'+item.id+'">'+item.account+'</td>');
		var nickname=$('<td>'+item.nickname+'</td>');
		var email=$('<td>'+item.email+'</td>');
		var phone=$('<td>'+item.phone+'</td>');
		
		var statusName='';
		var status='';
		var buttonName="";
		if(item.status=="0"){
			statusName='正常';
			buttonName='停用'
			status="disableUser"
		}else{
			statusName='停用';
			
			buttonName='启用'
			status="enableUser"
		}
		var state=$('<td>'+statusName+'</td>');
		var operation=$('<td><button class="btn btn-primary btn-xs" onclick="resetPassword(this,this)">重置密码</button><button class="btn btn-primary btn-xs" onclick="'+status+'(this,this)">'+buttonName+'</button><button class="btn btn-primary btn-xs" onclick="deleteUser(this,this)">删除</button></td>');
		var _this_status=item.status;
		$(authorityUrl).each(function(index,item){
			if(_this_status=="0"&&item.operation=='enableUser'){
			}
			else if(_this_status=="1"&&item.operation=='disableUser'){
				
			}else{
			};
		});
		account.appendTo(tr);
		nickname.appendTo(tr);
		email.appendTo(tr);
		phone.appendTo(tr);
		state.appendTo(tr)
		operation.appendTo(tr)
		tr.appendTo($('#logs table tbody'));
	});
	count=data.userTotalCount;
	pageNumber=parseInt(count/20)+1;
	$(".dataNumber").html(data.count);
	$(".pageNumber").html(pageNumber);
	
	//表格配置
	$('#userTables').dataTable({
		retrieve:   true,
		"paging":   true,
    	"ordering": true,
    	"info":     true,
    	"searching":true,
    	autoWidth:  false,
    	"aLengthMenu": [[10,5, 25, 50, -1], ["10条","5条", "25条", "50条", "所有"]],
        "columns": [
            { "data": "账户名" },
            { "data": "用户" },
            { "data": "邮箱" },
            { "data": "手机号" },
            { "data": "状态" },
            { "data": "操作" }
        ],
         initComplete: function () {
            var api = this.api();
            api.columns().indexes().flatten().each( function ( i ) {
                var column = api.column( i );
                if(i==5){
                	return true;
                }
                var select = $('<select><option value=""></option></select>')
                    .appendTo($(column.footer()).empty())
                    .on( 'change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );
                        column
                            .search( val ? '^'+val+'$' : '', true, false )
                            .draw();
                    } );
                column.data().unique().sort().each( function ( d, j ) {
                		select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
        },
        "dom": '<"top"l>rt<"bottom"ip><"span">',
        language: {
            "sProcessing":   "处理中...",
            "sLengthMenu":   "每页 _MENU_ 项",
            "sZeroRecords":  "没有匹配结果",
            "sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
            "sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix":  "",
            "sSearch":       "筛选:",
			//搜索或者筛选
            "sUrl":          "",
            "sEmptyTable":     "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands":  ",",
            "oPaginate": {
                "sFirst":    "首页",
                "sPrevious": "上页",
                "sNext":     "下页",
                "sLast":     "末页",
                "sJump":     "跳转"
            },
            "oAria": {
                "sSortAscending":  ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        COLUMN: {
            CHECKBOX: { //复选框单元格
                className: "td-checkbox",
                orderable: false,
                width: "30px",
                data: null,
                render: function (data, type, row, meta) {
                    return '<input type="checkbox" class="iCheck">';
                }
            }
        }
    });
}
function resetPassword(user,url){
	$('#loading').css('display','block');
	var userId=$(user).parent('td').parent('tr').children('.account').attr('userId');
	$.ajax({
		type:"put",
		url: $.mpbGetHeaderPath()+"/userManagement/resetPassword",
		data:{
			'userId':userId
		},
		success: function(result){
			result=JSON.parse(decodeURIComponent(result));
			var code = result.code;
			switch(code){
				case "0":
					$('#warningDiv').modal('show');
					$('#warningDiv .modal-body span').html(result.message);
					getUserlist();
						break;
				default:
					$('#warningDiv').modal('show');
					$('#warningDiv .modal-body span').html(result.message);
					$('#loading').css('display','none');
						break;
			}
		},
		error:function(result){
				$('#loading').css('display','none');
				$('#warningDiv').modal('show');
				$('#warningDiv .modal-body span').html(result.message);
		},
		async:true
	});
}

function disableUser(user,url){
	$('#loading').css('display','block');
	var userId=$(user).parent('td').parent('tr').children('.account').attr('userId');
	$.ajax({
		type:"put",
		url:$.mpbGetHeaderPath()+"/userManagement/disableUser",
		data:{
			'userId':userId
		},
		success: function(result){
			result=JSON.parse(decodeURIComponent(result));
			var code = result.code;
			switch(code){
				case "0":
					$('#warningDiv').modal('show');
					$('#warningDiv .modal-body span').html(result.message);
					getUserlist();
						break;
				default:
					$('#warningDiv').modal('show');
					$('#warningDiv .modal-body span').html(result.message);
					$('#loading').css('display','none');
						break;
			}
		},
		error:function(result){
			$('#warningDiv').modal('show');
			$('#warningDiv .modal-body span').html(result.message);
			$('#loading').css('display','none');
			
		},
		async:true
	});
}

function deleteUser(user,url){
	
    Confirm.show('操作提示', '是否删除此用户', {
        '确认': {
            'callback': function(){
				    $('#loading').css('display','block');
				    var userId=$(user).parent('td').parent('tr').children('.account').attr('userId');
				    $.ajax({
				    	type:"post",
				    	url:$.mpbGetHeaderPath()+"/userManagement/deleteUser",
				    	data:{
				    		'userId':userId,
				    		_method:"delete"
				    	},
				    	success: function(result){
				    		result=JSON.parse(decodeURIComponent(result));
				    		var code = result.code;
				    		switch(code){
				    			case "0":
				    				$('#warningDiv').modal('show');
				    				$('#warningDiv .modal-body span').html(result.message);
				    				getUserlist();
				    					break;
				    			default:
				    				$('#warningDiv').modal('show');
				    				$('#warningDiv .modal-body span').html(result.message);
				    				$('#loading').css('display','none');
				    					break;
				    		}
				    	},
				    	error:function(result){
				    		$('#warningDiv').modal('show');
				    		$('#warningDiv .modal-body span').html(result.message);
				    		$('#loading').css('display','none');
				    	},
				    	async:true
				    });
            	
            }
        }
    }
    );
}

function enableUser(user,url){
	$('#loading').css('display','block');
	var userId=$(user).parent('td').parent('tr').children('.account').attr('userId');
	$.ajax({
		type:"put",
		url:$.mpbGetHeaderPath()+"/userManagement/enableUser",
		data:{
			'userId':userId
		},
		success: function(result){
			result=JSON.parse(decodeURIComponent(result));
			var code = result.code;
			switch(code){
				case "0":
					$('#warningDiv').modal('show');
					$('#warningDiv .modal-body span').html(result.message);
					getUserlist();
					break;
				default:
					$('#warningDiv').modal('show');
					$('#warningDiv .modal-body span').html(result.message);	
					$('#loading').css('display','none');
					break;
			}
		},
		error:function(result){
			$('#warningDiv').modal('show');
			$('#warningDiv .modal-body span').html(result.message);
			$('#loading').css('display','none');
			
		},
		async:true
	});
}

//添加用户按钮
function addUser(){
	if(ifWindowOpen){
		return;
	}
	$('#addUserWindows').css('display','block');
	$('.mask').css('display','block');
	eyeClose=true;
	ifWindowOpen=true;
}
//关闭窗口
$('#closeWindows').click(function(){
	closeWindows();
});

// 关闭窗口
function closeWindows() {
	$('#addUserWindows').css('display','none');
	$('.mask').css('display','none');
	$('#addUserWindows #account').val('')
	$('#addUserWindows #password').val('')
	$('#addUserWindows #nickname').val('')
	$('#addUserWindows #email').val('')
	$('#addUserWindows #phone').val('')
	
	accountReady=false;
	passwordReady=false;
	nicknameReady=false;
	emailReady=false;
	phoneReady=false;

	var glyphicon=$('#addUserWindows .require')
	glyphicon.css('display','inline-block');
	glyphicon.removeClass('glyphicon-ok');
	glyphicon.removeClass('glyphicon-remove');
	glyphicon.html('*');
	glyphicon.css('color','red');
	
	ifWindowOpen=false;
}

$('.previousPage').click(function(){
	if($('.currentPage').val()==1){
		return;
	}
	var page=$('.currentPage').val()-(1);
	if(page<1){
		page=1;
	}
	$('.currentPage').val(page);
	var dataPerPage =$('.dataPerPage').val();
	reload(page,dataPerPage);
});

$('.nextPage').click(function(){
	if($('.currentPage').val()==pageNumber){
		return;
	}
	var page=$('.currentPage').val()-(-1);
	var dataPerPage =$('.dataPerPage').val();
	if(page>pageNumber){
		page=pageNumber;
	}
	$('.currentPage').val(page);
	reload(page,dataPerPage);
});

$('#refrech').click(function(){
	var page=$('.currentPage').val();
	if(page<1){
		page=1;
	}
	if(page>pageNumber){
		page=pageNumber;
	}
	$('.currentPage').val(page);
	var dataPerPage =$('.dataPerPage').val();
	reload(page,dataPerPage);
})

//刷新当前列表，加载下一页等。
function reload(page,dataPerPage){
	getUserlist(page,dataPerPage);
}
