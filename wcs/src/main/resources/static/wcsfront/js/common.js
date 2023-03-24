/**
 * 获取请求头路径
 */
$.mpbGetHeaderPath = function() {
    var strFullpath = window.document.location.href;
    var strPath = window.document.location.pathname;
    var pos = strFullpath.indexOf(strPath);
    var prePath = strFullpath.substring(0, pos);
    var enName = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    return prePath + enName;
};
//接口
var ServiceUrl = '/IEAM/basicInfoManagement';
//1.1.1 获取部门结构树
var getDepartmentStructureUrl=ServiceUrl+'/departmentManagement/getDepartmentStructure';
//1.1.2 保存部门结构树
var saveDepartmentStructureUrl=ServiceUrl+ '/departmentManagement/saveDepartmentStructure';
//1.1.3 新增部门
var addDepartmentUrl=ServiceUrl+ '/departmentManagement/addDepartment';
//1.1.4 获取部门列表
var getDepartmentsUrl=ServiceUrl+ '/departmentManagement/getAllDeptByCompany';
//1.1.5 检查部门编码是否可用
var checkDepartmentCodeAvailableUrl=ServiceUrl+'/departmentManagement/checkDepartmentCodeAvailable';
//1.1.6	根据部门ID获取部门详细信息
var getDepartmentInfoUrl=ServiceUrl+'/departmentManagement/getDepartmentInfo';
//1.1.7	编辑部门信息
var editDepartmentUrl=ServiceUrl+'/departmentManagement/editDepartment';
//1.1.8	删除部门
var deleteDepartmentUrl=ServiceUrl+'/departmentManagement/deleteDepartment';
//1.1.9 获取部门属性列表
var getDepartmentTypesUrl=ServiceUrl+'/departmentManagement/getDepartmentTypes';
//1.1.10 添加部门属性
var addDepartmentTypesUrl=ServiceUrl+'/departmentManagement/addDepartmentType';
//1.1.11 编辑部门属性
var editDepartmentTypesUrl=ServiceUrl+'/departmentManagement/editDepartmentTypes';
//1.1.12 删除部门属性
var deleteDepartmentTypesUrl=ServiceUrl+'/departmentManagement/deleteDepartmentTypes';
//1.1.13 检测部门属性名称是否可用
// var checkDepartmentTypeAvailableUrl=ServiceUrl+'/departmentManagement/checkDepartmentTypeAvailable';
//1.2.1	获取岗位权限列表
var getPostsAndPermissionsUrl=ServiceUrl+'/postManagement/getPostsAndPermissions';
//1.2.2	新增岗位
var addPostUrl=ServiceUrl+'/postManagement/addPost';
//1.2.3	检查岗位编码是否可用
var checkPostCodeAvailableUrl=ServiceUrl+'/postManagement/checkPostCodeAvailable';
//1.2.4	根据岗位ID获取岗位详情
var getPostInfoDetailUrl=ServiceUrl+'/postManagement/getPostInfo';
//1.2.5	编辑岗位信息
var editPostInfoUrl=ServiceUrl+'/postManagement/editPostInfo';
//1.2.6	保存岗位权限关系
var savePostsAndPermissionsUrl=ServiceUrl+'/postManagement/savePostsAndPermissions';
//1.2.7	根据岗位ID获取已指派到该岗位的人员 
var getEmployeeByPostUrl=ServiceUrl+'/postManagement/getEmployeeByPost';
//1.2.8	保存岗位-人员指派关系
var saveAssignedEmployeeUrl=ServiceUrl+'/postManagement/saveAssignedEmployee';
//1.2.9	删除岗位
var deletePostUrl=ServiceUrl+'/postManagement/deletePost';
//1.3.1	获取员工列表
var getEmployeesUrl=ServiceUrl+'/employeeManagement/getEmployees';
//1.3.2	获取没有分配岗位的员工列表
var getEmployeesNotAssignedUrl=ServiceUrl+'/employeeManagement/getEmployeesNotAssigned';
//1.3.3	新增员工
var addEmployeeUrl=ServiceUrl+'/employeeManagement/addEmployee';
//1.3.5	检查员工号是否可用
var checkEmployeeNumberAvailableUrl=ServiceUrl+'/employeeManagement/checkEmployeeNumberAvailable';
//1.3.6	重置密码
var resetPasswordUrl=ServiceUrl+'/employeeManagement/resetPassword';
//1.3.7	根据员工ID获取员工详细信息
var getEmployeeInfoUrl=ServiceUrl+'/employeeManagement/getEmployeeInfo';
//1.3.8	编辑员工信息
var editEmployeeUrl=ServiceUrl+'/employeeManagement/editEmployee';
//1.3.9	删除员工
var deleteEmployeeUrl=ServiceUrl+'/employeeManagement/deleteEmployee';
//1.获取权限列表
var getPermissionsUrl='/IEAM/permissionManagement/permission/getPermissions';
//1.2.2	根据部门ID获取岗位列表
var getPostsByDepartmentIdUrl=ServiceUrl+'/postManagement/getPosts';
//1.4.1	获取菜单列表及对应岗位
var getMenuPostsUrl='/IEAM/menuManagement/getMenuPosts';
//1.4.2	修改菜单对应岗位信息
var updateMenuPostListUrl='/IEAM/menuManagement/updateMenuPostList';
//1.5.1 新增字典
var addDictionaryUrl=ServiceUrl+ '/dictionaryManagement/addDictionary';
//1.5.2 获取字典列表
var getDictionaryUrl=ServiceUrl+ '/dictionaryManagement/getDictionaryList';
//1.5.3 检查部门编码是否可用
var checkDictionaryCodeUrl=ServiceUrl+'/dictionaryManagement/checkDictionaryCode';
//1.5.4	根据字典ID获取字典详细信息
var getDictionaryByIdUrl=ServiceUrl+'/dictionaryManagement/getDictionaryById';
//1.5.5	编辑字典信息
var editDictionaryUrl=ServiceUrl+'/dictionaryManagement/editDictionary';
//1.5.6	删除字典
var deleteDictionaryUrl=ServiceUrl+'/dictionaryManagement/deleteDictionary';

//公共方法
//过滤特殊字符

var _commonFun = {
    //获取部门列表
    getDepartments: function(resolve, reject){
        $.ajax({
            type	:"get",
            url		: getDepartmentsUrl,
            success	: resolve,
            error	: reject
        });
    },
    //获取部门属性列表
    getDepartmentTypes: function(resolve, reject){
        $.ajax({
            type		:"get",
            url			: getDepartmentTypesUrl,
            success		: resolve,
            error		: reject
        });
    },
    //获取岗位权限列表
	getPosts : function(resolve, reject){
        $.ajax({
            type	:"get",
            url     : getPostsAndPermissionsUrl,
            success : resolve,
            error   : reject
        });
    },
    //根据部门ID获取岗位列表
    getPostsByDepartmentId: function(departmentId,resolve, reject){
        $.ajax({
            type	:"get",
            url		: getPostsByDepartmentIdUrl,
            data    :{
                departmentId:departmentId
            },
            success	: resolve,
            error	: reject
        });
    },
    //获取组织机构结构树
    getDepartmentStructure: function(resolve, reject){
        $.ajax({
            type	:"get",
            url		: "/IEAM/basicInfoManagement/departmentManagement/getDepartmentStructure",
            success	: resolve,
            error	: reject
        });
    },
	successTips:function(msg){
		$.DialogByZ.Alert({Title: "提示", Content:msg || "操作成功！", BtnL:"确定", FunL:$.DialogByZ.Close()});
	},
	//错误提示
	errorTips:function(msg){
		$.DialogByZ.Alert({Title: "提示", Content:msg || "服务器运行异常！",BtnL:"确定", FunL:$.DialogByZ.Close()});
	},
	//字段的验证，支持非空，手机，邮箱，编码判断
	validate:function(value, type){
		value =$.trim(value);
		//非空判断
		if('require' === type){
			return !!value;
		}
		//手机号验证
		if('phone'===type){
			return /^1\d{10}$/.test(value);
		}
		//邮箱格式验证
		if('email'===type){
			return /^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/.test(value);
		}
		//编码验证
		if('code'===type){
			return /^[a-zA-Z\d]+$/.test(value);
		}
		//数字
        if('decimal' ===type){
            return /^[0-9\d]+$/.test(value);
        }
	},
    //时间控件初始化
    initDatetime:function () {
        $(".datainp").jeDate({
            festival: false,
            ishmsVal: false,
            initAddVal:[0],
            format: "YYYY-MM-DD",
            zIndex: 3000
        });
        var startTime= this.nowDateTime();
        $("#entryDatePop").val(startTime);
    },
    //将当前时间转换成yyyy-mm-dd格式
    nowDateTime:function(){
        var mydate = new Date();
        var str = "" + mydate.getFullYear() + "-";
        var mm = mydate.getMonth()+1;
        if(mydate.getMonth() < 9){
            str += "0" + mm;
            str = str + "-";
        } else{
            str += mm;
            str = str + "-";
        }
        if(mydate.getDate()>9){
            str += mydate.getDate();
        } else{
            str += "0" + mydate.getDate();
        }
        return str;
    },
    //根据id获取工单详情信息
    getWorkOrderDetail: function(id, resolve, reject){
        $.ajax({
            type	: "get",
            url		: "/IEAM/workOrderManagement/workOrder/getWorkOrder",
            data    : {
                id:id
            },
            success	: resolve,
            error	: reject
        });
    },
    //获取工单状态列表
    getOrderStatuses: function(resolve, reject){
        $.ajax({
            type	:"get",
            url		: "/IEAM/workOrderManagement/workOrder/getOrderStatuses",
            success	: resolve,
            error	: reject
        });
    },
    //获取工单类型列表
    getOrderTypes : function(resolve, reject){
        $.ajax({
            type	:"get",
            url		: "/IEAM/workOrderManagement/workOrder/getOrderTypes",
            success	: resolve,
            error	: reject
        });
    },
    //字符串空值判断
    isNotBlank :function (str) {
        return !this.isBlank(str);
     },
    //字符串空值判断
    isBlank : function (str) {
        if (str == null || str == "" || str == undefined || str == 'undefined') {
            return true;
        } else {
            return false;
        }
    },
    //判断元素 是否在数组内
    containInArray : function(ele,array){
        var result = false;
        for(var i in array){
          if(ele == array[i]){
              result = true;
          }
        }
        return result;
    },
    formatDate : function (millisec,type) {
        var date,dateStr = '' ;
        if(this.isNotBlank(millisec)){
            date = new Date();
            date.setTime(millisec);
            var month = date.getMonth()+1;
            if(month < 10){
                month = "0"+month;
            }
            var day = date.getDate();
            if(day < 10){
                day = "0"+day;
            }
            dateStr = date.getFullYear()+'-'+month+'-'+ day;
        }
        if(type =='yyyy-MM-dd HH:mm:ss'){
            dateStr += ' ' + date.getHours()+':'+ date.getMinutes()+':' + date.getSeconds()
        }

        return dateStr;
        
    },
    /*获取url参数*/
    getQueryString: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)
            return decodeURI(r[2]);
        return null;
    },
    /**
     * 跳转页面，并打开新的tab
     * @param menuId  菜单对应的 urlId
     * @param menuName 菜单名称
     * @param menuUrl url地址
     * @param bussinessId 与页面相关的 业务数据  ，在页面通过 window.parent.parentOrderParam 获取数据
     */
    openNewTab : function (menuId,menuName,menuUrl,bussinessId) {
        parent.window.returnOrderParam(bussinessId);
        if(parent.window.opentaglist[menuId]){
            delete parent.window.opentaglist[menuId];
            $.each(parent.$(".centercontainer ul div"),function(i,item){
                if($(item).find("span").attr("menuid")==menuId) {
                    $(item).animate({width:"0px"},300);
                    $(item)[0].parentNode.removeChild(item);
                }
            })
            $.each(parent.$('#loaderdiv div'),function(index,object){
                if($(object).attr("iframeid")==menuId){
                    $(object)[0].parentNode.removeChild(object);
                }
            })
            var ulleft=parseInt(parent.window.$('.centercontainer ul').css('left'));
            var leftarrow=parent.window.$('#maincontainer .tabmenu .leftarrow');
            var rightarrow=parent.window.$('#maincontainer .tabmenu .rightarrow');
            var centercontainer=parseInt(parent.window.$('.tabmenu .centercontainer').css('width'));
            var ullength=parseInt(parent.window.$('.centercontainer ul').css('width'));
            if(ullength<centercontainer&&ulleft==0){
                leftarrow.css('color','gray');
                rightarrow.css('color','gray');
            }
            parent.window.createNewTag(menuId,menuName,menuUrl);
        }else{
            parent.window.createNewTag(menuId,menuName,menuUrl);
        }
    }
}
var _com_pattern_characters=/^[\u4E00-\u9FA5A-Za-z0-9_]+$/;
var _com_pattern = new RegExp("[=\\=&? ？ *%]"); //过滤特殊字符
function RegeMatch(name){
    var pattern = new RegExp("[=\\=&? ？ *%]");
    if($("#"+name).val() != "" && $("#"+name).val() != null){
        if(pattern.test($("#"+name).val())){
            $("#"+name).attr("value","");
            $("#"+name).focus();
            return false;
        }
    }
}
// Bootstrap modal模态框可拖动
/** 拖拽模态框*/
// $(document).ready(function(){
//     $(".modal").draggable();//为模态对话框添加拖拽
//     $(".modal").css("overflow", "hidden");//禁止模态对话框的半透明背景滚动
// })
var dragModal={
    mouseStartPoint:{"left":0,"top":  0},
    mouseEndPoint : {"left":0,"top":  0},
    mouseDragDown : false,
    basePoint : {"left":0,"top":  0},
    moveTarget:null,
    topleng:0
}
$(document).on("mousedown",".modal-header",function(e){
    //webkit内核和火狐禁止文字被选中
    $('body').addClass('select')
    //ie浏览器禁止文字选中
    document.body.onselectstart=document.body.ondrag=function(){
        return false;
    }
    if($(e.target).hasClass("close"))//点关闭按钮不能移动对话框
        return;
    dragModal.mouseDragDown = true;
    dragModal.moveTarget = $(this).parent().parent();
    dragModal.mouseStartPoint = {"left":e.clientX,"top":  e.pageY};
    dragModal.basePoint = dragModal.moveTarget.offset();
    dragModal.topLeng=e.pageY-e.clientY;
});
$(document).on("mouseup",function(e){
    dragModal.mouseDragDown = false;
    dragModal.moveTarget = undefined;
    dragModal.mouseStartPoint = {"left":0,"top":  0};
    dragModal.basePoint = {"left":0,"top":  0};
});
$(document).on("mousemove",function(e){
    if(!dragModal.mouseDragDown || dragModal.moveTarget == undefined)return;
    var mousX = e.clientX;
    var mousY = e.pageY;
    if(mousX < 0)mousX = 0;
    if(mousY < 0)mousY = 25;
    dragModal.mouseEndPoint = {"left":mousX,"top": mousY};
    var width = dragModal.moveTarget.width();
    var height = dragModal.moveTarget.height();
    var clientWidth=document.body.clientWidth
    var clientHeight=document.body.clientHeight;
    if(dragModal.mouseEndPoint.left<dragModal.mouseStartPoint.left - dragModal.basePoint.left){
        dragModal.mouseEndPoint.left=0;
    }
    else if(dragModal.mouseEndPoint.left>=clientWidth-width+dragModal.mouseStartPoint.left - dragModal.basePoint.left){
        dragModal.mouseEndPoint.left=clientWidth-width-38;
    }else{
        dragModal.mouseEndPoint.left =dragModal.mouseEndPoint.left-(dragModal.mouseStartPoint.left - dragModal.basePoint.left);//移动修正，更平滑

    }
    if(dragModal.mouseEndPoint.top-(dragModal.mouseStartPoint.top - dragModal.basePoint.top)<dragModal.topLeng){
        dragModal.mouseEndPoint.top=dragModal.topLeng;
    }else if(dragModal.mouseEndPoint.top-dragModal.topLeng>clientHeight-height+dragModal.mouseStartPoint.top - dragModal.basePoint.top){
        dragModal.mouseEndPoint.top=clientHeight-height-38+dragModal.topLeng;
    }
    else{
        dragModal.mouseEndPoint.top = dragModal.mouseEndPoint.top - (dragModal.mouseStartPoint.top - dragModal.basePoint.top);
    }
    dragModal.moveTarget.offset(dragModal.mouseEndPoint);
});
$(document).on('hidden.bs.modal','.modal',function(e){
    $('.modal-dialog').css({'top': '0px','left': '0px'})
    $('body').removeClass('select')
    document.body.onselectstart=document.body.ondrag=null;
})
//input 输入框 清除 输入的历史记录
$(function () {
    $("input").attr("autocomplete","off");
});

