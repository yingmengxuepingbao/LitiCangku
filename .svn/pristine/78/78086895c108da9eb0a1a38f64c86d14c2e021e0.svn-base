$(function(){
    var  selectSearchTreeNode=null, selectPopTreeNode=null;
    var _person= {
        data: {
            currentNumber   : 0,  //记录当前页有多少条数据
            totalPageNum    : 0,  //记录一共多少页
            numberOnePage   : 12, //一页几条数据
            cusStatus       : '',
            roles           : [],//含checkStr的所有角色
            roleIds         : []
        },
        init: function () {
            this.onLoad();
            this.bindEvent();
        },
        onLoad: function () {
            this.initDatetime();
            this.loadEmployeeList();
            // this.loadDepartPostOptions();
            this.loadRolesOption() //获取所有角色
            $(".fa-personInfoPop").hide();
        },
        bindEvent: function () {
            var _this = this;
            //分页前一页
            $(document).on('click', '.previousPage', function () {
                if(parseInt($('.currentPage').text())==1){
                    return;
                }
                var currentPage=parseInt($('.currentPage').text())-(1);
                if(currentPage<1){
                    currentPage=1;
                }
                $('.currentPage').text(currentPage);
                _this.loadEmployeeList();
            });
            //分页下一页
            $(document).on('click', '.nextPage', function () {
                if(parseInt($('.currentPage').text())==_this.data.totalPageNum){
                    $('.currentPage').text(_this.data.totalPageNum);
                    return;
                }
                var currentPage= parseInt($('.currentPage').text())-(-1);

                if(currentPage>_this.data.totalPageNum){
                    currentPage=_this.data.totalPageNum;
                }
                $('.currentPage').text(currentPage);
                _this.loadEmployeeList();
            });
            //分页跳转
            $(document).on('click', '#pageJump', function () {
                var currentPage = $('.currentPageBtn').val();
                if(currentPage>_this.data.totalPageNum){
                    currentPage=_this.data.totalPageNum;
                }
                if(currentPage<1){
                    currentPage=1;
                }
                $('.currentPage').text(currentPage);
                _this.loadEmployeeList();
            });
            // $(document).on('click','#belongDeptNameS' ,function (e) {
            //     e.stopPropagation();
            //     $(".departSearchTree").slideToggle();
            //     selectSearchTreeNode.cancelSelectedNode();
            // })
            // $(document).on('click' , function (e) {
            //     e.stopPropagation();
            //     var nodes = selectSearchTreeNode.getSelectedNodes();
            //     if (nodes.length<=0) {
            //         $(".departSpan").attr("data-id","");
            //         $(".departSpan").text('选择部门');
            //     }
            //     $(".departSearchTree").slideUp();
            // })
            $(document).on('click','.popDepartSpan' ,function (e) {
                e.stopPropagation();
                $(".departPopTree").slideToggle();
                $("#belongDepartmentSelet").css("border","1px solid #69b0e7");
            })
            $(document).on('click' ,'.personw , .pop-body',function (e) {
                e.stopPropagation();
                $(".departPopTree").slideUp();
                $("#belongDepartmentSelet").removeAttr('style');
            })
            //点击搜索
            $(document).on('click', '#employeeInfoS', function () {
                $(".currentPage").text("1");
                _this.loadEmployeeList();
            });
            //点击添加人员按钮
            $(document).on('click', '#addEmployeeBtn', function () {
                // selectPopTreeNode.cancelSelectedNode();
                $(".cusPopover-title span").text("单位管理");
                $(".errorMsg").empty();
                $(".popDepartSpan").text('请选择部门');
                $(".popDepartSpan").attr('data-id','');

                // $("#employeeCodePop").val("");
                // $("#departmentName").val('');
                // $("#employeeNamePop").val('');
                // $("#belongDepartmentSelet").val('');
                // $("#belongPostSelet").val('');
                // $("#entryDatePop").val('');
                // $("#officePhonePop").val('');
                // $("#employeeTelPop").val("");
                // $("#employeeEmailPop").val('');
                // $("#employeeDescPop").val('');
                $("#hideUnitIdPop").val("");
                $("#unitCodePop").val("");
                $("#unitZhNamePop").val("");
                $("#unitEnNamePop").val("");
                $("#unitChangePop").val("");
                $("#unitTypePop").val("");
                $("#personPopover").show();
                $(".fa-personInfoPop").hide();
                //所有角色清空勾选
                $("#allRoles .checkbox-custom").find('input[type="checkbox"]').prop("checked",false);
                $("#departmentRoles").html("本部门下岗位");
                $("#allRoles").hide();
                //区分是搜索的部门还是弹窗里的部门
                $("#unitCodePop").removeAttr("disabled");
            });
            $(document).on('blur', '#employeeTelPop', function () {
                if($.trim($(this).val())==""){
                    $(this).css("border","1px solid #EF4836");
                    $(".fa-personInfoPop").show();
                    $(".fa-personInfoPop").text(" 手机号码，不能为空！");
                    return;
                }
                if(!_commonFun.validate($.trim($(this).val()),'phone')){
                    $(this).css("border","1px solid #EF4836");
                    $(".fa-personInfoPop").show();
                    $(".fa-personInfoPop").text(" 手机号格式不正确！");
                    return;
                }
                $(this).removeAttr('style');
                $(".fa-personInfoPop").hide();
            })
            $(document).on('blur', '#employeeEmailPop', function () {
                if($.trim($(this).val())!=""){
                    if(!_commonFun.validate($.trim($(this).val()),'email')){
                        $(".fa-personInfoPop").show();
                        $(".fa-personInfoPop").text(" 邮箱格式不正确！");
                        $(this).css("border","1px solid #EF4836");
                        return;
                    }
                }
                $(this).removeAttr('style');
                $(".fa-personInfoPop").hide();
            })
            // //检查工号是否可用
            // $(document).on('blur', '#employeeCodePop', function () {
            //     var employeeCode = $.trim($("#employeeCodePop").val());
            //     if(employeeCode==""){
            //         $(this).css("border","1px solid #EF4836");
            //         $(".fa-personInfoPop").show();
            //         $(".fa-personInfoPop").text(" 员工工号，不能为空！");
            //         return;
            //     }
            //     $(this).removeAttr('style');
            //     $(".fa-personInfoPop").hide();
            //     var employee={
            //         employeeCode:employeeCode
            //     }
            //     new doAjax({
            //         url: $.mpbGetHeaderPath()+'/userManagement/checkEmployeeNumberAvailable',
            //         data:employee,
            //         callBack: function (result) {
            //             if(result.code!=0) {
            //                 $(".fa-personInfoPop").show();
            //                 $(".fa-personInfoPop").text(" 员工工号不可用！");
            //                 $("#employeeCodePop").css("border","1px solid #EF4836");
            //                 $('#employeeCodePop').val("");
            //             }
            //         }
            //     });
            // })
            // $(document).on('blur', '#employeeNamePop', function (){
            //     if($.trim($(this).val())==""){
            //         $(this).css("border","1px solid #EF4836");
            //         $(".fa-personInfoPop").show();
            //         $(".fa-personInfoPop").text(" 员工姓名，不能为空！");
            //         return;
            //     }
            //     $(this).removeAttr('style');
            //     $(".fa-personInfoPop").hide();
            // });
            //点击员工列表中的编辑功能
            $(document).on('click', '#personList-table .a-peron-edit', function (e) {
                e.stopPropagation();
                var _self = $(this);
                // selectPopTreeNode.cancelSelectedNode();
                $("#unitCodePop").attr("disabled","disabled");
                $(".fa-personInfoPop").hide();
                var unitId = _self.parent().parent().data('employeeid');
                $("#hideUnitIdPop").val(unitId);
                //所有角色清空勾选
                // $("#allRoles .checkbox-custom").find('input[type="checkbox"]').prop("checked",false);
                $("#personPopover").show();
                $("#allRoles").show();
                new doAjax({
                    type:'get',
                    url: $.mpbGetHeaderPath()+'/unitManagement/getBaseUnitById',
                    data:{unitId:unitId},
                    callBack: function (result) {
                        if(result.code==0){
                            var data=result.data;
                            //指定选中ID的节点
                            // var nd= selectPopTreeNode.getNodeByParam("departmentId",data.departmentIds[0]);
                            // selectPopTreeNode.selectNode(nd,true);
                            // data.departmentList[0] ?  $(".popDepartSpan").text(data.departmentList[0].departmentName): $(".popDepartSpan").text("请选择部门");
                            $("#unitCodePop").val(data.unitCode);
                            $("#unitZhNamePop").val(data.unitZhName);
                            $("#unitEnNamePop").val(data.unitEnName);
                            $("#unitChangePop").val(data.unitChange);
                            $("#unitTypePop").val(data.unitType);





                            // $(".popDepartSpan").attr('data-id',data.departmentIds[0]);
                            // $("#entryDatePop").val(data.entryDate);
                            // $("#officePhonePop").val(data.officePhone);
                            // $("#employeeTelPop").val(data.phone);
                            // $("#employeeEmailPop").val(data.email);
                            // $("#employeeDescPop").val(data.description);
                            // _this.data.cusStatus=data.status;
                            // _this.data.roleIds=data.roleIds;
                            // if(data.departmentIds[0]!="") {
                            //     //获取该部门下的岗位
                            //     _this.getPostById(data.departmentIds[0]);
                            //     // new doAjax({
                            //     //     type:'get',
                            //     //     url: $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/getPostsUnderDepartment',
                            //     //     data:{departmentId:data.departmentIds[0]},
                            //     //     callBack: function (result) {
                            //     //         if(result.code==0) {
                            //     //             var posts=result.data;
                            //     //             if(posts.length<=0){
                            //     //                 $("#departmentRoles").html("本部门下岗位");
                            //     //                 $("#departmentRoles").append("<div style='border-bottom: 1px solid #ddd;padding: 10px 0 8px 10px'>无岗位信息</div>");
                            //     //                 var sortArr1=_this.setCheckStr(allRoles,rolesId);
                            //     //                 sortArr1.sort(function(a,b){return a.checkStr - b.checkStr;})
                            //     //                 _this.showRoles(sortArr1);//渲染其他岗位
                            //     //                 //给该员工的其他岗位勾选
                            //     //                 _this.setAllRolesTrue(rolesId);
                            //     //                 //重置checkStr
                            //     //                 _this.setCheckStr(sortArr1);
                            //     //
                            //     //             }else{
                            //     //                 var iDs=[];//提取id
                            //     //                 for(var k=0;k<posts.length;k++){
                            //     //                     iDs.push(posts[k].unitId)
                            //     //                 }
                            //     //                 var afterDelete=_this.splitElement(allRoles,iDs);//从所有角色中删除该岗位下的角色
                            //     //                 var sortArr=_this.setCheckStr(afterDelete,rolesId);//为其他岗位设置checkStr 供排序用
                            //     //                 sortArr.sort(function(a,b){return a.checkStr - b.checkStr;})
                            //     //                 _this.showRoles(sortArr);//渲染其他岗位
                            //     //                 //渲染本部门下岗位
                            //     //                 _this.showPostList(posts);
                            //     //                 //给该员工部门下的岗位勾选
                            //     //                 _this.setDepartmentRolesTrue(rolesId);
                            //     //                 //给该员工的其他岗位勾选
                            //     //                 _this.setAllRolesTrue(rolesId);
                            //     //                 //重置checkStr
                            //     //                 _this.setCheckStr(sortArr);
                            //     //             }
                            //     //         }
                            //     //         else{
                            //     //             $.DialogByZ.Autofade({Content:result.message|| "获取部门下的岗位失败！"});
                            //     //         }
                            //     //     }
                            //     // });
                            // }
                            // else{
                            //     $("#departmentRoles").html("本部门下岗位");
                            //     $("#departmentRoles").append("<div style='border-bottom: 1px solid #ddd;padding: 10px 0 8px 10px'>无岗位信息</div>");
                            //     var allRoles=JSON.parse(JSON.stringify(_this.data.roles));//获取所有角色
                            //     var sortArr=_this.setCheckStr(allRoles,_this.data.roleIds);
                            //     sortArr.sort(function(a,b){return a.checkStr - b.checkStr;})
                            //     _this.showRoles(sortArr);//渲染其他岗位
                            //     //给该员工的其他岗位勾选
                            //     _this.setAllRolesTrue(_this.data.roleIds);
                            //     //重置checkStr
                            //     _this.setCheckStr(sortArr);
                            // }
                        }
                    }
                });
            })
            //重置密码
            $(document).on('click', '#personList-table .a-resetPassword', function (e) {
                event.stopPropagation();
                var _self=$(this);
                var userId = _self.parent().parent().data('employeeid');
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要重置密码吗？",
                    FunL:function(){
                        new doAjax({
                            url		: $.mpbGetHeaderPath()+"/userManagement/resetPassword",
                            data:{userId:userId},
                            callBack: function (result) {
                                if(result.code==0){
                                    $.DialogByZ.Autofade({Content: "重置成功！"});
                                }else{
                                    //提示 含一个确定按钮
                                    $.DialogByZ.Autofade({Content: "参数格式错误！"});
                                }
                            }
                        });
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            })
            //保存员工详情
            $(document).on('click', '#savePersonBtn', function () {
                var unitCodePop	=$.trim($("#unitCodePop").val());
                var unitZhNamePop	=$.trim($("#unitZhNamePop").val());
                var unitEnNamePop	=$.trim($("#unitEnNamePop").val());
                var unitChangePop	=$.trim($("#unitChangePop").val());
                var unitTypePop	=$.trim($("#unitTypePop").val());
                // var employeeName	=$.trim($("#employeeNamePop").val());
                // var departmentId    =$(".popDepartSpan").attr('data-id');
                // var entryDate		=$.trim($("#entryDatePop").val());
                // var officePhone		=$.trim($("#officePhonePop").val());
                // var employeeTel		=$.trim($("#employeeTelPop").val());
                // var employeeEmail	=$.trim($("#employeeEmailPop").val());
                // var employeeDescription	=$.trim($("#employeeDescPop").val());
                // var postIds	,  roleArr=[];
                // $("#departmentRoles .checkbox-custom").each(function(index,item){
                //     if($(item).find('input[type="checkbox"]').length>0){
                //         if($(item).find('input[type="checkbox"]').is(':checked')){
                //             roleArr.push($(item).find('input[type="checkbox"]').val());
                //         }
                //     }
                // })
                // $("#allRoles .checkbox-custom").each(function(index,item){
                //     if($(item).find('input[type="checkbox"]').length>0){
                //         if($(item).find('input[type="checkbox"]').is(':checked')){
                //             roleArr.push($(item).find('input[type="checkbox"]').val());
                //         }
                //     }
                // })
                // console.log(roleArr);
                // if(roleArr.length>0){
                //    var filterArr=_this.unique(roleArr);
                //     console.log(filterArr);
                //     postIds=filterArr.join(",");
                //     console.log(postIds);
                // }
                if(unitCodePop==""){
                    $("#unitCodePop").css("border","1px solid #EF4836");
                    $(".fa-personInfoPop").show();
                    $(".fa-personInfoPop").text("单位代码，不能为空！");
                    return;
                }
                $("#unitCodePop").removeAttr('style');
                if(unitZhNamePop==""){
                    $("#unitZhNamePop").css("border","1px solid #EF4836");
                    $(".fa-personInfoPop").show();
                    $(".fa-personInfoPop").text("单位名称，不能为空！");
                    return;
                }
                $("#unitZhNamePop").removeAttr('style');

                // if(employeeName==""){
                //     $("#employeeNamePop").css("border","1px solid #EF4836");
                //     $(".fa-personInfoPop").show();
                //     $(".fa-personInfoPop").text(" 员工姓名，不能为空！");
                //     return;
                // }
                // $("#employeeNamePop").removeAttr('style');
                // if(entryDate==""){
                //     $("#entryDatePop").css("border","1px solid #EF4836");
                //     $(".fa-personInfoPop").show();
                //     $(".fa-personInfoPop").text(" 入职时间，不能为空！");
                //     return;
                // }
                // $("#entryDatePop").removeAttr('style');
                // if(employeeTel!=""){
                //     if(!_commonFun.validate(employeeTel,'phone')){
                //         $("#employeeTelPop").css("border","1px solid #EF4836");
                //         $(".fa-personInfoPop").show();
                //         $(".fa-personInfoPop").text(" 手机号格式不正确！");
                //         return;
                //     }
                // }
                // $("#employeeTelPop").removeAttr('style');
                // $(".fa-personInfoPop").hide();
                // if(employeeEmail!=""){
                //     if(!_commonFun.validate(employeeEmail,'email')){
                //         $(".fa-personInfoPop").show();
                //         $(".fa-personInfoPop").text(" 邮箱格式不正确!");
                //         $("#employeeEmailPop").css("border","1px solid #EF4836");
                //         return;
                //     }
                // }
                // $("#employeeEmailPop").removeAttr('style');
                $(".fa-personInfoPop").hide();
                var unitId=$.trim($("#hideUnitIdPop").val());
                if(unitId==""){
                    //新增 保存员工
                    var data={
                        unitCode	    :unitCodePop,
                        unitZhName	:unitZhNamePop,
                        unitEnName	:unitEnNamePop,
                        unitChange			:unitChangePop,
                        unitType	:unitTypePop,

                    };
                    new doAjax({
                        url: $.mpbGetHeaderPath()+'/unitManagement/addBaseUnit',
                        data:data,
                        callBack: function (result) {
                            console.log("result",result)
                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "新增单位成功！"});
                                $("#personPopover").hide();
                                //刷新员工列表
                                _this.loadEmployeeList();
                            }
                            else {
                                $.DialogByZ.Autofade({Content:result.message || "保存失败！"});1
                            }
                        }
                    });
                }
                else{

                    //编辑 保存员工信息
                    var data={
                        unitId		:unitId,
                        unitCode	    :unitCodePop,
                        unitZhName	:unitZhNamePop,
                        unitEnName	:unitEnNamePop,
                        unitChange			:unitChangePop,
                        unitType	:unitTypePop,
                        // unitCode	    :employeeCode,
                        // unitZhName	:employeeName,
                        // departmentIds	:departmentId,
                        // roleIds			:postIds,
                        // entryDate		:entryDate,
                        // officePhone		:officePhone,
                        // phone		:employeeTel,
                        // email	    :employeeEmail,
                        // description	:employeeDescription,
                        // status      : _this.data.cusStatus
                    }
                    new doAjax({
                        type:'post',
                        url: $.mpbGetHeaderPath()+'/unitManagement/updateBaseUnit',
                        data:data,
                        callBack: function (result) {
                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "修改单位成功！"});
                                $("#personPopover").hide();
                                //刷新员工列表
                                _this.loadEmployeeList();
                            }
                            else {
                                $.DialogByZ.Autofade({Content:result.message || "修改失败！"});
                            }
                        }
                    });
                }
            })
            //显示其他岗位
            $(document).on('click', '.showAllRoles', function () {
                $("#allRoles").show();
                $(this).attr("class","btn selectRoles hideAllRoles");
                $(this).val("隐藏其他岗位");
            })
            //隐藏其他岗位
            $(document).on('click', '.hideAllRoles', function () {
                $("#allRoles").hide();
                $(this).attr("class","btn selectRoles showAllRoles");
                $(this).val("显示其他岗位");
            })
            $(document).on('click', '#popClose', function () {
                $("#unitCodePop").val("");
                $("#unitZhNamePop").val("");
                $("#unitEnNamePop").val("");
                $("#unitChangePop").val("");
                $("#unitTypePop").val("");
                // $("#employeeCodePop").val("");
                // $("#employeeNamePop").val("");
                // $("#belongDepartmentSelet").val("");
                // $("#belongPostSelet").val("");
                // $("#entryDatePop").val("");
                // $("#officePhonePop").val("");
                // $("#employeeTelPop").val("");
                // $("#employeeEmailPop").val("");
                // $("#employeeDescPop").val("");
                $("#personPopover").hide();
                $(".departPopTree").slideUp('slow');
            })
            //关闭弹窗
            $(document).on('click', '#cancelPersonBtn', function () {

                $("#unitCodePop").val("");
                $("#unitZhNamePop").val("");
                $("#unitEnNamePop").val("");
                $("#unitChangePop").val("");
                $("#unitTypePop").val("");
                //
                // $("#employeeCodePop").val("");
                // $("#employeeNamePop").val("");
                // $("#belongDepartmentSelet").val("");
                // $("#belongPostSelet").val("");
                // $("#entryDatePop").val("");
                // $("#officePhonePop").val("");
                // $("#employeeTelPop").val("");
                // $("#employeeEmailPop").val("");
                // $("#employeeDescPop").val("");
                $("#personPopover").hide();
                $(".departPopTree").slideUp('slow');
            })
            //停用员工
            $(document).on('click', '#personList-table .a-stopPassword', function (e) {
                e.stopPropagation();
                var _self=$(this);
                var userId = _self.parent().parent().data('employeeid');
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要停用该员工吗？",
                    FunL:function(){
                        new doAjax({
                            url: $.mpbGetHeaderPath()+"/userManagement/disableUser",
                            data:{
                                userId:userId
                            },
                            callBack: function (result) {
                                if(result.code==0) {
                                    $.DialogByZ.Autofade({Content: "停用成功！"});
                                    _this.loadEmployeeList();
                                }else{
                                    $.DialogByZ.Autofade({Content:result.message|| "停用操作失败！"});
                                }
                            }
                        });
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            })
            //启用员工
            $(document).on('click', '#personList-table .a-restart', function (e) {
                e.stopPropagation();
                var _self=$(this);
                var userId = _self.parent().parent().data('employeeid');
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要启用该员工吗？",
                    FunL:function(){
                        new doAjax({
                            type:"put",
                            url:$.mpbGetHeaderPath()+"/userManagement/enableUser",
                            data:{
                                'userId':userId
                            },
                            callBack: function (result) {
                                if(result.code==0) {
                                    $.DialogByZ.Autofade({Content: "启用成功！"});
                                    _this.loadEmployeeList();
                                }else{
                                    $.DialogByZ.Autofade({Content:result.message|| "启用操作失败！"});
                                }
                            }
                        });
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            })
            //删除员工
            $(document).on('click', '#personList-table .a-deletePerson', function (e) {
                e.stopPropagation();
                var _self=$(this);
                console.log("employeeid",_self);
                var unitId = _self.parent().parent().data('employeeid');
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除该单位吗？",
                    FunL:function(){
                        new doAjax({
                            url: $.mpbGetHeaderPath()+'/unitManagement/deleteBaseUnit',
                            data:{
                                unitId:unitId
                            },
                            callBack: function (result) {
                                if(result.code==0) {
                                    $.DialogByZ.Autofade({Content: "删除成功！"});
                                    _this.loadEmployeeList();
                                }else{
                                    $.DialogByZ.Autofade({Content:result.message|| "删除操作失败！"});
                                }
                            }
                        });
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            })
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
            //将当前时间转换成yyyy-mm-dd格式
            function nowTime(){
                var mydate = new Date();
                var str = "" + mydate.getFullYear() + "-";
                var mm = mydate.getMonth()+1
                if(mydate.getMonth()>9){
                    str += mm;
                    str = str + "-";
                }
                else{
                    str += "0" + mm;
                    str = str + "-";
                }
                if(mydate.getDate()>9){
                    str += mydate.getDate();
                }
                else{
                    str += "0" + mydate.getDate();
                }
                return str;
            }
            var startTime= nowTime();
            $("#entryDatePop").val(startTime);
        },
        //渲染员工列表
        loadEmployeeList : function () {
            var _this = this;
            var keyInfo={
                unitCode        :$.trim($("#unitCodeS").val()),
                unitZhName      :$.trim($("#employeeNameS").val()),
                // phone           :$.trim($("#employeeTelS").val()),
                // departmentId    :$(".departSpan").attr('data-id'),
                // roleId          :$.trim($("#belongPostNameS option:selected").val()),
                currentPage     :$.trim($(".currentPage").text()),
                numberOnePage   :_this.data.numberOnePage,
                // status          :$("#userStatus").val()
            }
            new doAjax({
                type:'get',
                url: $.mpbGetHeaderPath()+'/unitManagement/getBaseUnitList',
                data:keyInfo,
                callBack: function (result) {
                    if(result.data.userTotalCount!=0){
                        var data=result.data.userList;
                        console.log("data",data);
                        _this.loadEmployeeSource(data);
                        console.log("result",result);
                        console.log("result.data.userTotalCount",result.data.userTotalCount);
                        console.log("_this.data.numberOnePage",_this.data.numberOnePage);

                        if(result.data.userTotalCount>_this.data.numberOnePage){
                            $(".pagination-con").show();
                            _this.loadPagingNumber(result.data.userTotalCount);
                        }
                        else {
                            $(".pagination-con").hide();
                        }
                    }
                    else {
                        $.DialogByZ.Autofade({Content: "查无数据！"});
                        $(".pagination-con").hide();
                        $("#personList-table").empty();
                    }
                }
            });
        },
        loadEmployeeSource : function(list) {
            // var departmentName="",postName="",officePhone="",employeeTel="",employeeEmail="",employeeDescription="",status="",entryDate,statusStr='';
            var unitZhName = "",unitEnName="",unitChange="";
            $("#personList-table").empty();
            var str='';
            for(ele in list){
                // list[ele].departmentList.length<=0 ? departmentName="" : departmentName=list[ele].departmentList[0].departmentName;
                // list[ele].roleList.length<=0 ? postName="" : postName=list[ele].roleList[0].description;
                // list[ele].status==0 ? status="正常" : status="停用";
                typeof(list[ele].unitZhName) == "undefined" ? (unitZhName="") : (unitZhName=list[ele].unitZhName);
                typeof(list[ele].unitEnName) == "undefined" ? (unitEnName="") : (unitEnName=list[ele].unitEnName);
                typeof(list[ele].unitChange) == "undefined" ? (unitChange="") : (unitChange=list[ele].unitChange);

                var unitTypeText="";
                var unitType = list[ele].unitType;
                typeof(list[ele].unitType) == "undefined" ? (unitType="") : (unitType=list[ele].unitType);
                if(unitType =="1"){
                    unitTypeText = "长度";
                }else if(unitType =="2"){
                    unitTypeText = "质量";
                }else if(unitType =="3"){
                    unitTypeText = "温度";
                }else if(unitType =="4"){
                    unitTypeText = "面积";
                }else if(unitType =="5"){
                    unitTypeText = "体积";
                }else if(unitType =="6"){
                    unitTypeText = "压力";
                }else if(unitType =="7"){
                    unitTypeText = "力";
                }else if(unitType =="8"){
                    unitTypeText = "密度";
                }else if(unitType =="9"){
                    unitTypeText = "湿度";
                }


                // typeof(list[ele].officePhone) == "undefined" ? (officePhone="") : (officePhone=list[ele].officePhone);
                // typeof(list[ele].phone) == "undefined" ? (employeeTel="") : (employeeTel=list[ele].phone);
                // typeof(list[ele].email) == "undefined" ? (employeeEmail="") : (employeeEmail=list[ele].email);
                // typeof(list[ele].entryDate) == "undefined" ? (entryDate="") : (entryDate=list[ele].entryDate);
                // if(list[ele].status==0){
                //     statusStr= '<a class="link a-stopPassword" >停用</a>&nbsp;&nbsp;';
                // }
                // else{
                //     statusStr= '<a class="link a-restart" >启用</a>&nbsp;&nbsp;';
                // }
                str+='<tr data-employeeid="'+list[ele].unitId+'">' +
                    '<td class="employeeCode">'+list[ele].unitCode+'</td>'+
                    '<td class="employeeName">'+unitZhName+'</td>'+
                    '<td class="unitEnName">'+unitEnName+'</td>'+
                    '<td class="unitChange">'+unitChange+'</td>'+
                    '<td class="unitType">'+unitTypeText+'</td>'+
                    // '<td class="officePhone">'+officePhone+'</td>'+
                    // '<td class="employeeTel">'+employeeTel+'</td>'+
                    // '<td class="employeeEmail">'+employeeEmail+'</td>'+
                    '<td  style="min-width: 255px"><a class="link a-peron-edit">编辑</a>&nbsp;&nbsp;'+
                    // '<a class="link a-resetPassword" >重置密码</a>&nbsp;&nbsp;'+
                    // statusStr+
                    '<a class="link a-deletePerson del" >删除</a></td></tr>';
            }
            $("#personList-table").html(str);
        },
        //获取部门列表树 和 岗位列表
        loadDepartPostOptions: function() {
            new doAjax({
                type:'get',
                url: $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/getDepartmentStructure',
                callBack: function (result) {
                    if(result.code==0) {
                        var dataAr=result.data
                        var non={
                            departmentCode: "",
                            departmentDescription: "",
                            departmentId: "",
                            departmentName: "无部门",
                            departmentType: 20,
                            departmentTypeName: "",
                            parentId:'',
                            subDepartment:[]
                        }
                        dataAr.push(non);
                        $.fn.zTree.init($("#treeSearchContainerUL"), treeSearchSetting, dataAr);
                        selectSearchTreeNode= $.fn.zTree.getZTreeObj("treeSearchContainerUL");
                        selectSearchTreeNode.expandAll('true');
                        $.fn.zTree.init($("#treePopContainerUL"), treePopSetting, dataAr);
                        selectPopTreeNode=$.fn.zTree.getZTreeObj("treePopContainerUL");
                        selectPopTreeNode.expandAll('true');
                    }
                    else{
                        $.DialogByZ.Autofade({Content:'获取部门树失败！'});
                    }
                }
            });
            new doAjax({
                type:'get',
                url: $.mpbGetHeaderPath()+'/permissionManagement/role/getRolesAndResources',
                callBack: function (result) {
                    if(result.code==0){
                        var list=result.data;
                        $("#belongPostNameS").empty();
                        var options="<option value=''>-请选择-</option>";
                        for(var j=0;j<list.length;j++){
                            options+="<option value='"+list[j].unitId+"'>"+list[j].description+"</option>";
                        }
                        $("#belongPostNameS").html(options);
                    }
                    else{
                        $("#belongPostNameS").empty();
                    }
                }
            });
        },
        //获取所有角色
        loadRolesOption:function () {
            var _this=this;
            new doAjax({
                type:'get',
                url: $.mpbGetHeaderPath()+'/permissionManagement/role/getRolesAndResources',
                callBack: function (result) {
                    if(result.code==0) {
                        var roleList=result.data;
                        _this.data.roles=_this.setCheckStr(roleList);
                        _this.showRoles(_this.data.roles);//渲染其他岗位
                    }
                }
            });
        },
        //为岗位设置checkStr 供排序用all所有岗位 ids
        setCheckStr:function (all,ids) {
            if(ids && ids.length>0){
                for(var i=0;i<all.length;i++){
                    all[i].checkStr=1;
                    for(var j=0;j<ids.length;j++){
                        if(all[i].unitId==ids[j]){
                            all[i].checkStr=0;
                        }
                    }
                }
            }
            else{
                for(var i=0;i<all.length;i++){
                    all[i].checkStr=1;
                }
            }
            return all;
        },
        //删除本部门下的岗位，留其他岗位
        splitElement:function (roles,ids) {//(所有岗位，部门下岗位ids)
            if(ids.length>0){
                for(var r=0;r<roles.length;r++){
                    for(var d=0;d<ids.length;d++){
                        if(roles[r].unitId==ids[d]){
                            roles.splice(r,1)
                        }
                    }
                }
            }
            return roles
        },
        getPostById:function (id) {
            var _this=this;
            new doAjax({
                type:'get',
                url: $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/getPostsUnderDepartment',
                data:{departmentId:id},
                callBack: function (result) {
                    if(result.code==0) {
                        var posts=result.data;
                        var allRoles=JSON.parse(JSON.stringify(_this.data.roles));//获取所有角色
                        if(posts.length<=0){
                            $("#departmentRoles").html("本部门下岗位");
                            $("#departmentRoles").append("<div style='border-bottom: 1px solid #ddd;padding: 10px 0 8px 10px'>无岗位信息</div>");
                            var sortArr1=_this.setCheckStr(allRoles,_this.data.roleIds);
                            sortArr1.sort(function(a,b){return a.checkStr - b.checkStr;})
                            _this.showRoles(sortArr1);//渲染其他岗位
                            //给该员工的其他岗位勾选
                            _this.setAllRolesTrue(_this.data.roleIds);
                            //重置checkStr
                            _this.setCheckStr(sortArr1);
                        }else{
                            var iDs=[];//提取id
                            for(var k=0;k<posts.length;k++){
                                iDs.push(posts[k].unitId)
                            }
                            var afterDelete=_this.splitElement(allRoles,iDs);//从所有角色中删除该岗位下的角色
                            var sortArr=_this.setCheckStr(afterDelete,_this.data.roleIds);//为其他岗位设置checkStr 供排序用
                            sortArr.sort(function(a,b){return a.checkStr - b.checkStr;})
                            _this.showRoles(sortArr);//渲染其他岗位
                            //给该员工的其他岗位勾选
                            _this.setAllRolesTrue(_this.data.roleIds);
                            //重置checkStr
                            _this.setCheckStr(sortArr);
                            //渲染本部门下岗位
                            _this.showPostList(posts);
                            //给该员工部门下的岗位勾选
                            _this.setDepartmentRolesTrue(_this.data.roleIds);
                            //重置checkStr
                            _this.setCheckStr(sortArr);
                        }
                    }
                }
            });
        },
        setAllRolesTrue:function (roles) {
            $("#allRoles .checkbox-custom").each(function(index,item){
                for(let i=0;i<roles.length;i++){
                    if($(item).find('input[type="checkbox"]').val()==roles[i]){
                        $(item).find('input[type="checkbox"]').prop("checked",true)
                    }
                }
            })
        },
        setDepartmentRolesTrue :function (roles) {
            $("#departmentRoles .checkbox-custom").each(function(index,item){
                for(let j=0;j<roles.length;j++){
                    if($(item).find('input[type="checkbox"]').val()==roles[j]){
                        $(item).find('input[type="checkbox"]').prop("checked",true)
                    }
                }
            })
        },
        showRoles:function (rs) {
            $("#allRoles").html("其他岗位");
            if(rs && rs.length>0){
                var roleStr="";
                for(var i=0;i<rs.length;i++){
                    roleStr+='<div class="checkbox-custom">' +
                        '<input type="checkbox" value="'+rs[i].unitId+'" class="customCk" name="checkbox">' +
                        '<label>'+rs[i].description+'</label></div>';
                }
                $("#allRoles").append(roleStr);
            }
            else {
                $("#allRoles").append("<div style='border-bottom: 1px solid #ddd;padding: 10px 0 8px 10px'>无岗位信息</div>");
            }
        },
        showPostList:function (ps) {
            $("#departmentRoles").html("本部门下岗位");
            if(ps && ps.length>0){
                var postStr="";
                for(var i=0;i<ps.length;i++){
                    postStr+='<div class="checkbox-custom">' +
                    '<input type="checkbox" value="'+ps[i].unitId+'" class="customCk" name="checkbox">' +
                    '<label>'+ps[i].description+'</label></div>';
                }
                $("#departmentRoles").append(postStr);
            }
            else {
                $("#departmentRoles").append("<div style='border-bottom: 1px solid #ddd;padding: 10px 0 8px 10px'>无岗位信息</div>");
            }
        },
        //加载分页
        loadPagingNumber: function(num) {
            parseInt(num % this.data.numberOnePage)==0 ?
                this.data.totalPageNum=parseInt(num/this.data.numberOnePage) :
                this.data.totalPageNum=parseInt(num/this.data.numberOnePage)+1;
            $(".dataNumber").html(num);//共多少条
            $(".pageNumber").html(this.data.totalPageNum);//共多少页
            $(".pageOneNumber").text(this.data.numberOnePage);//每页几条
        },
        unique:function(arr) {
            var obj = {};
            return arr.filter(function(item, index, arr){
                return obj.hasOwnProperty(typeof item + item) ? false : (obj[typeof item + item] = true)
            })
        }
    };
    //搜索树
    var treeSearchSetting={
        view: {
            dblClickExpand: false,
            showIcon: showIconForTree
        },
        edit: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "departmentId",
            },
            key: {
                children: "subDepartment",
                name: "departmentName"
            }
        },
        callback: {
            onClick: onClickSearchDepartment
        }
    };
    //弹窗树
    var treePopSetting={
        view: {
            dblClickExpand: false,
            showIcon: showIconForTree
        },
        edit: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "departmentId",
            },
            key: {
                children: "subDepartment",
                name: "departmentName"
            }
        },
        callback: {
            onClick: onClickPopDepartment
        }
    };
    //搜索树点击事件
    function onClickSearchDepartment(event, treeId, treeNode) {
        $(".departSpan").attr('data-id',treeNode.departmentId);
        $(".departSpan").text(treeNode.departmentName);
    }
    //弹窗树点击事件
    function onClickPopDepartment(event, treeId, treeNode) {
        $(".popDepartSpan").attr('data-id',treeNode.departmentId);
        $(".popDepartSpan").text(treeNode.departmentName);
        _person.getPostById(treeNode.departmentId);
    }
    function showIconForTree(treeId, treeNode) {
        return !treeNode.isParent;
    }
    _person.init();
});
