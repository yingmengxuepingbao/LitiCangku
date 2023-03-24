var permissions ={};
var _buz_service,_task;
if(_commonFun.isNotBlank(window.parent.operationData)){
    permissions = window.parent.operationData.permissions;
}
//控制 新增按钮 显示
// if(permissions["addPreventiveTask"]){
//     $("#addTaskBtn").show();
// }else {
//     $("#addTaskBtn").hide();
// }
$(function(){
     _buz_service={
         getGoodsCodeAll : function(data, reject){
             $.ajax({
                 type	:"get",
                 url		: $.mpbGetHeaderPath()+"/goods/getGoodsCodeAll",
                 headers:{'token':localStorage.getItem('token')},
                 // data    : { code :"warehouseCode"},
                 async:false,
                 success	: function (res) {
                     var result=res;
                     if(result.code == 0){
                         var options = '';
                         options +='<option value=""></option>'
                         for(var i=0;i < result.data.length; i++){
                             var ele = result.data[i];
                             options +='<option value="'+ele.dicItemCode+'">'+ele.dicItemCode+'('+ele.dicItemName+')</option>'
                         }
                         $("#goodsCode").html(options);
                     }
                 },
                 error	: reject
             });
         },
        //获取供应商列表
        getOrderTypeList : function(data, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/dictItem/getDictType/order_in_type",
                headers:{'token':localStorage.getItem('token')},
                data    : { code :"order_in_type"},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var options = '';
                        options +='<option value="">单据类型</option>'
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.dicItemCode+'">'+ele.dicItemName+'</option>'
                        }

                        $("#orderTypeSearch").html(options);
                    }
                },
                error	: reject
            });
        },

        //1.1.1	删除维护单据
        deleteTask: function(id,resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/orderIn/delete",
                headers:{'token':localStorage.getItem('token')},
                data	: {
                    orderInId:id
                },
                success	: resolve,
                error	: reject
            });
        },
        //1.1.2	停用维护单据（可批量操作）
        stopTask: function(ids , resolve, reject){
            $.ajax({
                type		:"post",
                url			: "/IEAM/workOrderManagement/preventiveTask/stopTask",
                headers:{'token':localStorage.getItem('token')},
                data		: {
                    ids:ids
                },
                success		: resolve,
                error		: reject
            });
        },
        //1.1.3	启用维护单据（可批量操作）
        startTask:function(ids,resolve, reject){
            $.ajax({
                type	:"post",
                url		: "/IEAM/workOrderManagement/preventiveTask/startTask",
                headers:{'token':localStorage.getItem('token')},
                data		: {
                    ids:ids
                },
                success	: resolve,
                error	: reject
            });
        },
        //1.1.4	获取单据列表
        getTasks: function(tasks,resolve, reject){
            $.ajax({
                type		:"post",
                url			: $.mpbGetHeaderPath()+"/orderIn/list",
                headers:{'token':localStorage.getItem('token')},
                data		: tasks,
                success		: resolve,
                error		: reject
            });
        },
        //1.1.5	获取当前单据对应工单列表
        getTaskOrders: function(id,resolve, reject){
            $.ajax({
                type		:"get",
                url			: $.mpbGetHeaderPath()+"/dictItem/getDictType/customer_type",
                headers:{'token':localStorage.getItem('token')},
                data		: {
                    id:id
                },
                success		: resolve,
                error		: reject
            });
        },
        //获取当前登陆用户信息
        getCurrentUser  : function(resolve, reject){
            $.ajax({
                type	:"get",
                url		: "/IEAM/userManagement/getCurrentUser",
                headers:{'token':localStorage.getItem('token')},
                success	: resolve,
                error	: reject
            });
        }
    };
     _task= {
        data: {
            currentNumber   : 0,  //记录当前页有多少条数据
            totalPageNum    : 0,  //记录一共多少页
            numberOnePage   : 10, //一页几条数据
            currentTreeRes  : [] , //放获取的部门树列表
            orderBy         : "",  //表头排序字段
            userId            :""  //当前登录用户ID
        },
        init: function () {
            this.onLoad();
            this.bindEvent();
        },
        onLoad: function () {
            // this.loadCurrentUser();
            this.loadTasks();
            this.initDatetime();
            _buz_service.getOrderTypeList();
            $(".hidden-search-type").slideUp();
            $("#hide-device-tips").hide();
        },
        bindEvent: function () {
            var _this = this;
            //搜索条件隐藏及显示
            $(document).on('click', '#more-search-condition span', function () {
                $(".hidden-search-type").slideToggle();
                if($(this).find("i").hasClass("fa-angle-down")){
                    $(this).find("i").removeClass("fa-angle-down").addClass("fa-angle-up");
                }
                else {
                    $(this).find("i").removeClass("fa-angle-up").addClass("fa-angle-down");
                }
            })
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
                $("#check-all").prop("checked", false);
                _this.loadTasks();
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
                $("#check-all").prop("checked", false);
                _this.loadTasks();
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
                $("#check-all").prop("checked", false);
                _this.loadTasks();
            });
            //搜索文本框变化
            $("#contentSearch").bind("input propertychange", function () {
                $(".currentPage").text("1");
                $("#check-all").prop("checked", false);
                _this.loadTasks();
            });
            //点击搜索
            $(document).on('click', '#topSearchInfoList', function () {
                $(".currentPage").text("1");
                $("#check-all").prop("checked", false);
                _this.loadTasks();
            });
            //点击重置筛选条件
            $(document).on('click', '#resetSearchConditions', function () {
                $("#taskStatusSelect").val("");
                $("#goodsCodeSearch").val("");
                $("#installDate_begin").val("");
                $("#installDate_end").val("");
                $("#orderTypeSearch").val("");
                $("#orderNoSearch").val("");
                $(".currentPage").text("1");
                _this.data.orderBy="";
                $("#check-all").prop("checked", false);
                _this.loadTasks();
            });
            //修改
            $(document).on('click', '#addTaskBtn', function () {
                var id="";
                window.location.href="addOrderInTask.html?id="+id;
            });
            //关闭设备列表弹窗
            $(document).on('click', '#deviceNumber-close', function () {
                $("#hideInfoIdPop").val("");
                $("#taskContPop").val("");
                $("#deviceNumberPop").hide();
            })
            //关闭设备详情弹窗
            $(document).on('click', '#taskDetail-close', function () {
                $("#hideInfoIdPop").val("");
                $("#taskContPop").val("");
                $("#taskDetailPop").hide();
            })
            //清空弹窗文本内容
            $(document).on('click', '.clear', function () {
                $("#taskContPop").val("");
            })
            //table 行点击事件 显示详情
            // $(document).on('click' ,'#taskLists tbody tr', function (e) {
            //     e.stopPropagation();
            //     var _self=$(this);
            //     var id=_self.data("id");
            //     _buz_service.getTaskInfo(id, function (res) {
            //         var result=res;
            //         console.log(result);
            //         if(result.code==0) {
            //             var data=result.data;
            //             var orderStatusTxt='',timeLimitUnit='',intervalUnit='';
            //             switch (data.timeLimitUnit){
            //                 case 'day' : timeLimitUnit='日';break;
            //                 case 'month' : timeLimitUnit='月';break;
            //                 case 'week' : timeLimitUnit='周';break;
            //             }
            //             switch (data.intervalUnit){
            //                 case 'day' : intervalUnit='日';break;
            //                 case 'month' : intervalUnit='月';break;
            //                 case 'week' : intervalUnit='周';break;
            //             }
            //             data.orderStatus=='on' ? orderStatusTxt='运行' : orderStatusTxt='停止';
            //             $("#orderNoSpan").text(data.orderNo);
            //             $("#taskNameSpan").text(data.orderItem);
            //             $("#orderStatusNameSpan").text(orderStatusTxt);
            //             $("#createNameSpan").text(data.createBy);
            //             $("#planAmountSpan").text(data.planAmount);
            //             $("#gmtCreateSpan").text(data.gmtCreate);
            //             $("#priorityNameSpan").text(data.priorityName);
            //             $("#descriptionSpan").text(data.description);
            //             $("#planSpan").text(data.plan);
            //             $("#startTimeSpan").text(data.startTime);
            //             $("#timeLimitSpan").text(data.timeLimit);
            //             $("#timeLimitUnitSpan").text(timeLimitUnit);
            //             $("#triggerTimeSpan").text(data.triggerTime);
            //             $("#intervalSpan").text(data.interval);
            //             $("#intervalUnitSpan").text(intervalUnit);
            //             $(".totalDeviceNum").text(data.devices.length);
            //             $("#deviceListPop tbody").empty();
            //             if(data.devices.length>0){
            //                 $("#hide-device-tips").hide();
            //                 _this.loadDeviceListHtml(data.devices);
            //             }
            //             else {
            //                 $("#hide-device-tips").show();
            //             }
            //             $("#taskDetailPop").show();
            //         }
            //         else {
            //             $.DialogByZ.Autofade({Content: result.message || "查看失败！"});
            //         }
            //     }, function (err) {
            //         _commonFun.errorTips('');
            //     })
            // })
            //table 数量点击事件 显示列表
            $(document).on('click' ,'.a-planAmount', function (e) {
                e.stopPropagation();
                var _self=$(this);
                var id      = _self.parent().parent().data("id");
                var orderNo  = _self.parent().parent().find('.orderNo').text();
                var orderItem    = _self.parent().parent().find('.orderItem').text();
                var orderStatus  =_self.parent().parent().find('.orderStatus').text();

                var planAmount  =_self.parent().parent().find('.planAmount').text();
                $("#orderIdLi").text(orderNo);
                $("#orderTypeLi").text(orderItem);
                $("#orderStatusLi").text(orderStatus);

                $("#planAmount").text(planAmount);
                $("#orderPopTitle").text(orderItem+"工单列表");
                _buz_service.getTaskOrders(id, function (res) {
                    var result=res;
                    if(result.code==0) {
                        var list=result.data.workOrders;
                        if(result.data.totalNumber>0){
                            $(".totalOrderNumP span").show();
                            $(".totalOrderNumP span").text(result.data.totalNumber);
                            $("#deviceListTBPop").empty();
                            var str = '',deviceNameText='';
                            for (ele in list) {
                                typeof(list[ele].deviceName)!='undefined' ? deviceNameText=list[ele].deviceName:deviceNameText='';
                                str+='<tr>' +
                                    '<td style="width: 15%" class="deviceName">' + deviceNameText + '</td>' +
                                    '<td style="width: 15%" class="orderId">' + list[ele].orderId + '</td>' +
                                    '<td style="width: 15%" class="orderStatusName">' + list[ele].orderStatusName + '</td>' +
                                    '<td style="width: 15%" class="repairmanName">' + list[ele].repairmanName + '</td>' +
                                    '<td style="width: 15%" class="gmtCreate">' + list[ele].gmtCreate + '</td>' +
                                    '</tr>';
                            }
                            $("#deviceListTBPop").html(str);
                        }
                        else {
                            $(".totalOrderNumP span").hide();
                        }

                        $("#deviceNumberPop").show();
                    }
                    else {
                        $.DialogByZ.Autofade({Content: result.message || "查看失败！"});
                    }
                }, function (err) {
                    _commonFun.errorTips('');
                })
            })
            //表头排序
            $(document).on('click', '#taskLists thead tr th', function (e) {
                e.stopPropagation();
                var _self=$(this);
                var orderBy=_self.data('sort');

                if(_self.find('i').hasClass('fa-sort-amount-asc')){
                    _self.parents("thead").find('i').removeClass("th-sort-active");
                    _self.find('i').removeClass('fa-sort-amount-asc').addClass('fa-sort-amount-desc th-sort-active');
                    _this.data.orderBy=orderBy + " desc";
                    _this.loadTasks();

                }
                else if(_self.find('i').hasClass('fa fa-sort-amount-desc')){
                    _self.parents("thead").find('i').removeClass("th-sort-active");
                    _self.find('i')
                        .removeClass('fa-sort-amount-desc')
                        .addClass('fa-sort-amount-asc th-sort-active');
                    _this.data.orderBy=orderBy + " asc";
                    _this.loadTasks();
                }

            });
            // 单据详情 弹窗关闭
            $(document).on('click' ,'#taskDetail-close', function (e) {
                $("#orderNoSpan").text("");
                $("#taskNameSpan").text("");
                $("#orderStatusNameSpan").text("");
                $("#createNameSpan").text("");
                $("#planAmountSpan").text("");
                $("#gmtCreateSpan").text("");
                $("#priorityNameSpan").text("");
                $("#descriptionSpan").text("");
                $("#planSpan").text("");
                $("#startTimeSpan").text("");
                $("#timeLimitSpan").text("");
                $("#triggerTimeSpan").text("");
                $("#intervalSpan").text("");
                $(".totalDeviceNum").text("");
                $("#deviceListPop tbody").empty();
                $("#taskDetailPop").hide();
            })
            //关闭设备列表弹窗
            $(document).on('click' ,'#deviceNumber-close', function (e) {
                $("#orderIdLi").text("");
                $("#orderTypeLi").text("");
                $("#orderStatusLi").text("");
                $("#orderPopTitle").text("");
                $("#deviceListTBPop").empty();
            })
            //全选/全不选 checkbox
            $(document).on('click', '#check-all', function (e) {
                e.stopPropagation();
                var _self=$(this);
                if(_self.is(':checked')){
                    //全选中设置
                    $("#taskLists tbody tr").each(function(index,item){
                        $(item).find('input[type="checkbox"]').prop("checked","checked");
                    })
                }
                else{
                    //全不选设置
                    $("#taskLists tbody tr").each(function(index,item){
                        $(item).find('input[type="checkbox"]').prop("checked", false);
                    })
                }
            })
            //有一个未选中则取消全选  /  全都选中则全选选中
            $(document).on('click', '#taskLists tbody input[type="checkbox"]', function (e) {
                e.stopPropagation();
                var isCheckAll=true;
                if(!$(this).is(':checked')){
                    //有一个没选中的就取消全选
                    $("#check-all").prop("checked", false);
                }
                else{
                    $("#taskLists tbody tr").each(function(index,item){
                        if(!$(item).find('input[type="checkbox"]').is(':checked')){
                            isCheckAll=false;
                        }
                    })
                    //全都选中则全选选中
                    if(isCheckAll){
                        $("#check-all").prop("checked","checked");
                    }
                }
            })
            //单据启动
            $(document).on('click', '.setTask-begin', function (e) {
                e.stopPropagation();
                var _self=$(this);
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要启动单据吗？",
                    FunL:function(){
                        var id=_self.parent().parent().data('id');
                        _buz_service.startTask(id, function (res) {
                            var result=res;
                            if(result.code==0){
                                $.DialogByZ.Autofade({Content:result.message ||  "单据启动成功！"});
                                $("#check-all").prop("checked", false);
                                _this.loadTasks();

                            }
                            else {
                                $.DialogByZ.Autofade({Content:result.message ||  "单据启动失败！"});
                            }
                        }, function (err) {
                            _commonFun.errorTips('');
                            $("#baseEquipmentList tbody").empty();
                        });
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            })
            //批量单据启动
            $(document).on('click', '#startTaskBtn', function (e) {
                var orderNoArr=[];
                $("#taskLists tbody tr").each(function(index,item){
                    var _self=$(item);
                    if(_self.find('input[type="checkbox"]').is(':checked')){
                        var orderNo = _self.data('id');
                        orderNoArr.push(orderNo);
                    }
                })
                var taskStr=orderNoArr.join(",");
                if(orderNoArr.length>0) {
                    $.DialogByZ.Confirm({
                        Title: "操作提示",
                        Content: "确定要批量启动单据吗？",
                        FunL: function () {
                            _buz_service.startTask(taskStr, function (res) {
                                var result = res;
                                if (result.code == 0) {
                                    $.DialogByZ.Autofade({Content: result.message || "单据启动成功！"});
                                    $("#check-all").prop("checked", false);
                                    _this.loadTasks();

                                }
                                else {
                                    $.DialogByZ.Autofade({Content: result.message || "单据启动失败！"});
                                }
                            }, function (err) {
                                _commonFun.errorTips('');
                            });
                            $.DialogByZ.Close();
                        },
                        FunR: function () {
                            $.DialogByZ.Close();
                        }
                    })
                }
                else {
                    $.DialogByZ.Autofade({Content:"请选择要启动的单据！"});
                }
            })
            //单据停止 setTask-stop stopTask
            $(document).on('click', '.setTask-stop', function (e) {
                e.stopPropagation();
                var _self=$(this);
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要停止单据吗？",
                    FunL:function(){
                        var id=_self.parent().parent().data('id');
                        _buz_service.stopTask(id, function (res) {
                            var result=res;
                            if(result.code==0){
                                $.DialogByZ.Autofade({Content:result.message ||  "单据停止成功！"});
                                $("#check-all").prop("checked", false);
                                _this.loadTasks();

                            }
                            else {
                                $.DialogByZ.Autofade({Content:result.message ||  "单据停止失败！"});
                            }
                        }, function (err) {
                            _commonFun.errorTips('');
                        });
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            })
            //批量单据停止 setTask-stop stopTask
            $(document).on('click', '#endTaskBtn', function (e) {
                var orderNoArr=[];
                $("#taskLists tbody tr").each(function(index,item){
                    var _self=$(item);
                    if(_self.find('input[type="checkbox"]').is(':checked')){
                        var orderNo = _self.data('id');
                        orderNoArr.push(orderNo);
                    }
                })
                var taskStr=orderNoArr.join(",");
                if(orderNoArr.length>0) {
                    $.DialogByZ.Confirm({
                        Title: "操作提示",
                        Content: "确定要批量停止单据吗？",
                        FunL: function () {
                            _buz_service.stopTask(taskStr, function (res) {
                                var result = res;
                                if (result.code == 0) {
                                    $.DialogByZ.Autofade({Content: result.message || "单据停止成功！"});
                                    $("#check-all").prop("checked", false);
                                    _this.loadTasks();

                                }
                                else {
                                    $.DialogByZ.Autofade({Content: result.message || "单据停止失败！"});
                                }
                            }, function (err) {
                                _commonFun.errorTips('');
                            });
                            $.DialogByZ.Close();
                        },
                        FunR: function () {
                            $.DialogByZ.Close();
                        }
                    })
                }
                else {
                    $.DialogByZ.Autofade({Content:"请选择要停止的单据！"});
                }
            })
            //点击列表中的编辑
            $(document).on('click', '.a-task-edit', function (e) {
                e.stopPropagation();
                var _self = $(this);
                var id = _self.parent().parent().data('id');
                window.location.href="addOrderInTask.html?id="+id;
            })
            //点击列表中的删除
            $(document).on('click', '.task-delete', function (e) {
                e.stopPropagation();
                var _self = $(this);
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除此单据吗？",
                    FunL:function(){
                        var id=_self.parent().parent().data('id');
                        _buz_service.deleteTask(id, function (res) {
                            var result=res;
                            if(result.code==0){
                                $.DialogByZ.Autofade({Content:result.message ||  "单据删除成功！"});
                                $("#check-all").prop("checked", false);
                                _this.loadTasks();

                            }
                            else {
                                $.DialogByZ.Autofade({Content:result.message ||  "单据删除失败！"});
                            }
                        }, function (err) {
                            _commonFun.errorTips('');
                        });
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            })
        },
        //单据列表
        loadTasks: function () {
            var _this      =this;
            var task  ={
                orderStatus          : $.trim($("#taskStatusSelect").val()),
                type            : "0",
                orderNo    : $.trim($("#orderNoSearch").val()),
                goodsCode    : $.trim($("#goodsCodeSearch").val()),
                orderType    : $.trim($("#orderTypeSearch").val()),
                gmtCreateMin       : $.trim($("#installDate_begin").val()),
                gmtCreateMax         : $.trim($("#installDate_end").val()),
                currentPage     : $.trim($(".currentPage").text()),
                orderBy         : _this.data.orderBy,
                numberOnePage   : _this.data.numberOnePage
            };
            _buz_service.getTasks(task, function (res) {
                var result=res;
                console.log(result);
                if(result.code==0){
                    var data=result.data;
                    _this.loadTasksHtml(data.infoList);
                    if(result.data.totalNumber>_this.data.numberOnePage){
                        $(".pagination-con").show();
                        _this.loadPagingNumber(result.data.totalNumber);
                    }
                    else {
                        $(".pagination-con").hide();
                    }
                }
                else {
                    $.DialogByZ.Autofade({Content: "查无数据！"});
                    $("#baseEquipmentList tbody").empty();
                    $(".pagination-con").hide();
                }
            }, function (err) {
                _commonFun.errorTips('');
                $("#baseEquipmentList tbody").empty();
            });
        },
        loadTasksHtml : function(list) {
            $("#taskLists tbody").empty();
            var strCheck='<td><div class="checkbox-custom checkbox-default"><input type="checkbox" >' +
                '<label></label></div></td>';
            var str="",orderStatusText="",goodsName="",createBy="",userId = _task.data.userId;

            for(ele in list){
                var orderStatus = list[ele].orderStatus;
                if(orderStatus =="1"){
                    orderStatusText = "创建";
                }else if(orderStatus =="2"){
                    orderStatusText = "收货中";
                }else if(orderStatus =="3"){
                    orderStatusText = "完成";
                }
                typeof(list[ele].goodsName)!='undefined' ? goodsName=list[ele].goodsName:deviceNameText='';
                typeof(list[ele].createBy)!='undefined' ? createBy=list[ele].createBy:createBy='';
                var iDelete='&nbsp;&nbsp;&nbsp;&nbsp;<a class="link task-delete"><i class="fa-table-line-delete" title="删除"></i></a>';
                var iEdit ='<a onclick="editOneRecord(\''+list[ele].orderInId+'\',1)"><i class="fa-table-line-edit" title="编辑"></i></a>';

                str+='<tr data-id="'+list[ele].orderInId+'">' ;
                    str+=strCheck;
                    str+=
                        '<td class="orderNo">'+list[ele].orderNo+'</td>'+
                        '<td class="orderItem">'+list[ele].orderItem+'</td>'+
                        '<td class="goodsCode ">'+list[ele].goodsCode+'</td>'+
                        '<td class="goodsName ">'+goodsName+'</td>'+
                        '<td class="orderType">'+list[ele].orderType+'</td>'+
                        '<td class="orderStatus">'+orderStatusText+'</td>'+
                        '<td class="planAmount" >'+list[ele].planAmount+'</td>';
                        if(list[ele].orderStatus!="1") {
                            iDelete='';///状态不为创建
                        }
                        if(list[ele].orderStatus!="1") {
                            iEdit='';//状态不为创建
                        }
                        str += '<td class="realAmount">' + list[ele].realAmount + '</td>';
                        str += '<td class="remark">' + list[ele].remark + '</td>';
                        str+='<td class="createBy">'+createBy+'</td>'+
                        '<td class="gmtCreate">'+list[ele].gmtCreate+'</td>';
                        str+='<td  style="width: 90px">'+iEdit+iDelete+'</td>';


                str+="</tr>";
            }
            $("#taskLists tbody").html(str);
        },
        loadDeviceListHtml: function(list) {
            $("#deviceListPop tbody").empty();
            var str="",isAutoassess="",assessorName="",isAutoDistribute="",repairmanName="";
            for(ele in list){
                if( list[ele].isAutoassess=="1" ){
                    isAutoassess="是";
                    assessorName='无';
                }
                else{
                    isAutoassess="否";
                    assessorName=list[ele].assessorName;
                }
                if( list[ele].isAutoDistribute=="1" ){
                    isAutoDistribute="是";
                    repairmanName=list[ele].repairmanName;
                }
                else{
                    isAutoDistribute="否";
                    repairmanName='无';
                }
                str+= '<tr><td style="width: 14%" class="deviceCode">' + list[ele].deviceCode + '</td>' +
                    '<td style="width: 20%" class="deviceName">' + list[ele].deviceName + '</td>' +
                    '<td style="width: 13%" class="isAutoassess">' + isAutoassess + '</td>' +
                    '<td style="width: 20%" class="assessorName">' + assessorName + '</td>' +
                    '<td style="width: 13%" class="isAutoDistribute">' + isAutoDistribute + '</td>' +
                    '<td style="width: 20%" class="repairmanName">' + repairmanName + '</td></tr>';
            }
            $("#deviceListPop tbody").html(str);
        },
        //加载当前用户信息
        loadCurrentUser:function(){
            _buz_service.getCurrentUser(function (res) {
                var result=res;
                if(result.code==0) {
                    var user = result.data;
                    _task.data.userId = user.id;
                }
                else {
                    $.DialogByZ.Autofade({Content: result.message ||  "获取当前用户信息失败！"});
                }
            },function (err) {
                _commonFun.errorTips('');
            })
        },
        //加载分页=
        loadPagingNumber: function(num) {
            parseInt(num%this.data.numberOnePage)==0 ?
                this.data.totalPageNum=parseInt(num/this.data.numberOnePage) :
                this.data.totalPageNum=parseInt(num/this.data.numberOnePage)+1;

            $(".dataNumber").html(num);//共多少条
            $(".pageNumber").html(this.data.totalPageNum);//共多少页
            $(".pageOneNumber").text(this.data.numberOnePage);//每页几条
        },
        initDatetime :function  () {
            $(".datainp").jeDate({
                festival: false,
                ishmsVal: false,
                initAddVal:[0],
                format: "YYYY-MM-DD",
                zIndex: 3000
            });
        }
    };
    _task.init();
});

//安全库存 数据校验
function checkStockNumber(that) {
    var stockNumber = $(that).val();
    if(_commonFun.isNotBlank(stockNumber)){
        //必须 大于0 且只能是整数
        if(stockNumber <= 0){
            _commonFun.errorTips("必须大于0");
            $(that).val(1);
            return false;
        }
        if(!/^[1-9]\d*$/.test(stockNumber)){
            _commonFun.errorTips("只能是正整数");
            $(that).val(parseInt(stockNumber)+1);
            return false;
        }
    }else{
        _commonFun.errorTips("不能为空");
        $(that).val(1);
        return false;
    }
}

//编辑 type  0 表示 查看  1 表示 编辑  2表示 审核
function editOneRecord(customerId,type) {
    var id = customerId;
    $("#type").val(type);
    //供应商
    _buz_service.getGoodsCodeAll();
    //查看时，隐藏提交按钮 和禁用下拉框和input
    if(type == 0 ){
        $("#formInfo").find("i").hide();
        $("#myModalLabel").text('查看明细信息');
        $("#saveButton").hide();
        $("form  select").prop("disabled",true);
        $("form  input").prop("disabled",true);
    }else if (type == 1){
        $("#formInfo").find("i").show();
        $("#myModalLabel").text('编辑明细信息');
        //编辑时，显示 提交按钮和启用下拉框和 input
        $("#saveButton").show().text("保存");
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        $("#orderNo").prop("disabled",true);

    }

    //查看备件信息 模态框弹出
    $.ajax({
        type	:"get",
        url		: $.mpbGetHeaderPath()+"/orderIn/r/"+id,
        headers:{'token':localStorage.getItem('token')},
        data    : {id:id},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                if(data != null){
                    $("#orderInId").val(data.orderInId);
                    $("#orderNo").val(data.orderNo);
                    $("#goodsCode").val(data.goodsCode);
                    $("#planAmount").val(data.planAmount);
                    var orderStatus = data.orderStatus;
                    if(orderStatus!="1"){
                        $.DialogByZ.Autofade({Content: "单据状态不为创建不可修改"});
                        return false;
                    }

                }
                //显示模态框
                $("#formModel").modal({backdrop:false});
            }
        },
        error	: function () {
            _commonFun.errorTips('');
        }
    });

}

//新增或更新信息
$("#saveButton").on("click",function () {
    var orderInId = $.trim($("#orderInId").val());
    console.log("orderInId",orderInId);
    var orderNo = $.trim($("#orderNo").val());
    var goodsCode = $.trim($("#goodsCode").val());
    var planAmount = $.trim($("#planAmount").val());
    //添加数据校验
    if(_commonFun.isBlank(goodsCode)){
        $.DialogByZ.Autofade({Content: "商品编码不能为空"});
        return false;
    }
    if(_commonFun.isBlank(planAmount)){
        $.DialogByZ.Autofade({Content: "数量不能为空"});
        return false;
    }

    var data= {
        orderInId:orderInId,
        orderNo:orderNo,
        goodsCode:goodsCode,
        planAmount:planAmount
    };

    //判断是新增还是修改
    if (!_commonFun.isNotBlank(orderInId)){
    }else {
        //存在id修改
        data.orderInId = orderInId;
        $.ajax({
            type	:"POST",
            url		: $.mpbGetHeaderPath()+"/orderIn/u",
            headers:{'token':localStorage.getItem('token')},
            data    : data,
            success	: function (res) {
                var result=res;
                if(result.code == 0){
                    _commonFun.successTips(result.data);
                    _task.loadTasks();
                }else {
                    $.DialogByZ.Autofade({Content:result.message || "修改失败！"});
                }
            },
            error	: function () {
                _commonFun.errorTips("错误");
            }
        });
    }



});