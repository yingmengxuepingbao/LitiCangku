$(function(){
    var _addTask_service={
        //获取基础商品列表
        getGoodsCodeAll: function(resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/goods/getGoodsCodeAll",
                headers:{'token':localStorage.getItem('token')},
                success	: resolve,
                error	: reject
            });
        },
        //获取商品分类参数
        getDeviceParam : function(data,resolve, reject){
            $.ajax({
                type	:"get",
                url		: "/IEAM/deviceCategoryPara/getParaListByDevice",
                data    : data,
                success	: resolve,
                error	: reject
            });
        },
        //获取故障优先级列表
        getFaultPriorities : function(resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/dictItem/getDictType/order_in_type",
                headers:{'token':localStorage.getItem('token')},
                data    : { code :"order_in_type"},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var options = '';
                        options +='<option value=""></option>'
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.dicItemCode+'">'+ele.dicItemName+'</option>'
                        }

                        $("#orderType").html(options);
                    }
                },
                error	: reject
            });
        },
        //获取审核人员 维修人员
        getAssessorsAnd  : function(id,resolve, reject){
            $.ajax({
                type	:"get",
                url		: "/IEAM/workOrderManagement/workOrder/getAssessorsAndRepairmans",
                data    :{
                    id:id
                },
                success	: resolve,
                error	: reject
            });
        },
        //获取单据详情信息
        getTaskInfo: function(id,resolve, reject){
            $.ajax({
                type		:"get",
                url			: "/IEAM/workOrderManagement/preventiveTask/getTaskInfo",
                data		: {
                    id:id
                },
                success		: resolve,
                error		: reject
            });
        },
        //新增、修改维护单据
        submitTask: function(orderInfo,resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/orderIn/creatOrderIn",
                headers:{'token':localStorage.getItem('token')},
                data    :orderInfo,
                success	: resolve,
                error	: reject
            });
        }
    };
    var _addTask= {
        data: {
            taskId         : "" ,//存储编辑时保存的单据id
            deviceId       : "", //存放编辑时商品id
            deviceList     : []
        },
        init: function () {
            this.onLoad();
            this.bindEvent();
        },
        onLoad: function () {
            this.loadBaseEquipments();
            this.loadPrioritiesOption();
            $("#taskDeviceList").empty();
            $(".chosen-select").chosen();
            // $("#startTimeInput").val("");
            $("#assessorIdPop").attr("disabled","disabled");
            $(".fa-deviceInfoPop").hide();
            this.loadOrderDetailInfo();
        },
        bindEvent: function () {
            var _this = this;
            //chosen change事件
            $(document).on('change', '.chosen-select', function (e){
                var _self=$(this);
                var name=$(".chosen-select option:selected").attr("name");
                $("#goodsNamePop").text("");
                $("#goodsNamePop").text($.trim(name));
                console.log("$.trim(name)",$.trim(name))

                _this.data.deviceId=_self.val();
                console.log("_this.data.deviceId",_this.data.deviceId)
                // _this.loadAssessorsAnd(_self.val());
            })
            //判断是否自动审核
            $(document).on('click', '#need-person-check', function (e) {
                e.stopPropagation();
                if($(this).is(':checked')){
                    //自动审核则审核人不需要选
                    $("#assessorIdPop").attr("disabled","disabled");
                    $("#assessorIdPop").val("");
                    $(this).attr("checked","checked");
                }
                else{
                    $("#assessorIdPop").removeAttr("disabled");
                    $(this).removeAttr("checked");
                }
            })
            //判断是否自动派工
            $(document).on('click', '#need-person-distribute', function (e) {
                e.stopPropagation();
                if(!$(this).is(':checked')){
                    //不自动派工不需要选择入库人
                    $("#distributeIdPop").attr("disabled","disabled");
                    $("#distributeIdPop").val("");
                    $(this).removeAttr("checked");
                }
                else{
                    $("#distributeIdPop").removeAttr("disabled");
                    $(this).attr("checked","checked");
                }
            })
            //新增商品
            $(document).on('click' ,'#addTaskBtn', function (e) {
                $("#add-chosen").show();
                $("#edit-input").hide();
                $("#goodsCodeInputPop").val("");
                $("#planAmount").val("");
                _this.data.deviceId="";
                $("#goodsNamePop").text("");
                $("#need-person-check").attr("checked","checked");
                $("#assessorIdPop").attr("disabled","disabled");
                $("#assessorIdPop").val("");
                $("#need-person-distribute").attr("checked","checked");
                $("#distributeIdPop").removeAttr("disabled");
                $("#distributeIdPop").val("");
                $(".chosen-select").val("");
                $(".chosen-select").chosen();
                $(".chosen-select").trigger("chosen:updated");
                $("#addDevicePop").modal();
                $(".chzn-single span").text("请选择商品");
            })
            //编辑商品
            $(document).on('click' ,'.a-equipment-edit', function (e) {
                e.stopPropagation();
                var _self = $(this);
                $("#add-chosen").hide();
                $("#edit-input").show();
                //获取快捷详情
                _this.data.deviceId= _self.parent().parent().data('id');
                var goodsName = _self.parent().parent().attr('goodsName');
                var planAmount  = _self.parent().parent().attr('planAmount');
                var goodsCode  = _self.parent().parent().find('.goodsCode').text();
                $("#goodsCodeInputPop").val(goodsCode);
                console.log("planAmount",planAmount,_this.data.deviceId,goodsName);
                $("#planAmount").val(planAmount);
                // _this.loadAssessorsAnd( _this.data.deviceId);
                // if(_self.parent().parent().find('.isAutoassess').text()=="是"){
                //     $("#need-person-check").prop("checked", "checked");
                //     $("#assessorIdPop").prop("disabled","disabled");
                //     $("#assessorIdPop").val("");
                // }
                // else{
                //     $("#need-person-check").prop("checked", false);
                //     $("#assessorIdPop").removeAttr("disabled");
                //     setTimeout(function () {
                //         $("#assessorIdPop").val(assessorId);
                //     },500);
                // }
                // if(_self.parent().parent().find('.isAutoDistribute').text()=="是"){
                //     $("#need-person-distribute").prop("checked", "checked");
                //     $("#distributeIdPop").removeAttr("disabled");
                //     setTimeout(function () {
                //         $("#distributeIdPop").val(repairmanId);
                //     },500);
                // }
                // else{
                //     $("#need-person-distribute").prop("checked", false);
                //     $("#distributeIdPop").prop("disabled","disabled");
                //     $("#distributeIdPop").val("");
                // }
                $("#addDevicePop").modal();
            })
            //设置商品分类参数
            $(document).on('click' ,'.a-equipment-param', function (e) {
                var _self = $(this);
                var equipmentId = _self.parent().parent().data('id');
                var categoryId  = _self.parent().parent().data('categoryid');
                var deviceId  = _self.parent().parent().data('ind');
                $("#deviceId").val(deviceId);
                var data = {
                    deviceId  :  equipmentId,
                    categoryId  : categoryId,
                    type  : "5"
                }
                _addTask_service.getDeviceParam(data,function (res) {
                        var result=JSON.parse(decodeURIComponent(res));
                        if(result.code==0) {
                            var list = result.data;
                            var listSize = list.length;
                            var str = '';
                            $("#paramList").html("");
                            for (var ele in list) {
                                str+='<tr data-id="'+list[ele].id+'"><td style="width: 10%"><div class="checkbox-custom checkbox-default"><input type="checkbox" >' +
                                    '<label></label></div></td>'+
                                    '<td style="width: 20%" class="name">' + list[ele].name + '</td>' +
                                    '<td style="width: 20%" class="checkMethod">' + list[ele].checkMethod + '</td>' +
                                    '<td style="width: 35%" class="checkStandard">' + list[ele].checkStandard + '</td>' +
                                    '<td style="width: 25%" class="sparepartsName">' + list[ele].sparepartsName + '</td>' +
                                    '</tr>';
                            }
                            $("#paramList").html(str);
                            if (_this.data.taskId != "" && _this.data.taskId != "undefined" && _this.data.taskId != null) {  //编辑商品参数
                                var paramList = _this.data.deviceList[deviceId].param;
                                if (paramList != "undefined" && paramList != "" && paramList != null) {  //商品参数存在
                                    var paramListSize = paramList.length;
                                    if (listSize == paramListSize) {
                                        $("#check-allParam").prop("checked",true);
                                    }
                                    for (var ala in paramList) {
                                        $("#paramList tr").each(function (index,item) {
                                            var tr_id = $(this).data("id");
                                            if (tr_id == paramList[ala]) {
                                                $(this).find("input[type=checkbox]").attr("checked","checked");
                                                return false;  //结束each循环
                                            }
                                        })
                                    }
                                }
                            }
                            $("#addDeviceParamPop").modal();
                        }else {
                            $.DialogByZ.Autofade({Content: result.message || "查看失败！"});
                        }
                    },function (err) {
                        _commonFun.errorTips('');
                    }
                )
            })
            //保存单据
            $(document).on('click' ,'#submitAll', function (e){
                e.stopPropagation();
                var _self= $(this);
                var name	    = $.trim($("#taskNameInput").val());
                var remark = $.trim($("#remark").val());
                var orderNo    = $.trim($("#orderNo").val());
                var orderType    = $.trim($("#orderType").val());
                var startTime	= $.trim($("#startTimeInput").val());
                var endTime = $.trim($("#endTimeInput").val());
                var timeLimit	= $.trim($("#timeLimitInput").val());
                var timeLimitUnit   = $("#timeLimitUnit  option:selected").val();
                var interval    = $.trim($("#intervalInput").val());
                var intervalUnit   = $("#intervalUnit  option:selected").val();


                //
                $("#taskNameInput").removeAttr('style');
                if(orderType==""){
                    $("#orderType").css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "单据类型，不能为空！"});
                    return;
                }


                $("#intervalInput").removeAttr('style');
                if(_this.data.deviceList.length>0){

                    var deviceList = _this.data.deviceList;
                    // for (var ele in deviceList) {
                    //     var param = deviceList[ele].param;
                    //     if (param == null || param == "" || param.length == 0 ) {
                    //         _commonFun.errorTips(deviceList[ele].deviceName + "商品没有配置点巡检参数，请配置后保存！");
                    //         return false;
                    //     }
                    // }

                    _self.attr("disabled","disabled");
                    $.DialogByZ.Confirm({
                        Title: "操作提示",
                        Content: "确定要提交该单据吗？",
                        FunL:function(){
                            var data = {};
                            if (_this.data.taskId == "" || _this.data.taskId == "undefined" || _this.data.taskId == "-1") {
                                //新增
                                data = {
                                    name	    : name,
                                    remark : remark,
                                    orderNo    : orderNo,
                                    orderType    : orderType,
                                    startTime	: startTime,
                                    endTime:  endTime,
                                    timeLimit	: timeLimit,
                                    timeLimitUnit	: timeLimitUnit,
                                    interval    : interval,
                                    intervalUnit  :intervalUnit,
                                    taskType      : "0",
                                    //区分成品：10/原材料：50
                                    userDefined2: "10",
                                    userDefined1     : JSON.stringify(_this.data.deviceList)
                                };
                                _addTask_service.submitTask(data, function (res) {
                                    var result=res;
                                    if (result.code == 0) {
                                        $.DialogByZ.Autofade({Content: "单据提交成功！"});
                                        window.location.href = "orderCPInTask.html";
                                    }
                                    else {
                                        _self.removeAttr("disabled");
                                        $.DialogByZ.Autofade({Content: result.message || "单据提交失败！"});
                                    }
                                }, function (err) {
                                    _self.removeAttr("disabled");
                                    _commonFun.errorTips('');
                                })
                            }
                            else {
                                data = {
                                    id          : _this.data.taskId,
                                    name	    : name,
                                    remark : remark,
                                    orderType    : orderType,
                                    startTime	: startTime,
                                    endTime:  endTime,
                                    timeLimit	: timeLimit,
                                    timeLimitUnit	: timeLimitUnit,
                                    interval    : interval,
                                    intervalUnit  :intervalUnit,
                                    taskType      : "0",
                                    deviceInfo     : JSON.stringify(_this.data.deviceList)
                                };
                                _addTask_service.submitTask(data, function (res) {
                                    var result=eval('(' + decodeURIComponent(res) + ')');
                                    if (result.code == 0) {
                                        $.DialogByZ.Autofade({Content: "单据提交成功！"});
                                        window.location.href = "orderCPInTask.html";
                                    }
                                    else {
                                        _self.removeAttr("disabled");
                                        $.DialogByZ.Autofade({Content: result.message || "单据修改失败！"});
                                    }
                                }, function (err) {
                                    _self.removeAttr("disabled");
                                    _commonFun.errorTips('');
                                })
                            }
                            $.DialogByZ.Close();
                        },
                        FunR:function(){
                            _self.removeAttr("disabled");
                            $.DialogByZ.Close();
                        }
                    })
                }
                else{
                    $.DialogByZ.Autofade({Content: "单据商品不能为空！"});
                }
            })
            //弹窗保存商品
            $(document).on('click', '#saveDevice', function () {
                var planAmount      = $("#planAmount").val();
                var deviceId	    = _this.data.deviceId;
                var code            = $(".chosen-select option:selected").data('code');
                var deviceCode      = $.trim(code);
                var dicItemName      = $(".chosen-select option:selected").attr('dicItemName');
                var dicItemCode      = $(".chosen-select option:selected").attr('dicItemCode');
                var assessorId      = $("#assessorIdPop").val();
                var assessorName    = $("#assessorIdPop  option:selected").text();
                var assessorPostName = $("#assessorIdPop").attr("data-name");
                var repairmanId	 = $("#distributeIdPop").val();
                var repairmanName   = $("#distributeIdPop  option:selected").text();
                var repairmanPostName = $("#distributeIdPop").attr("data-name");
                var isAutoassess	= "";
                var isAutoDistribute	= "";
                var flagParam   = true;
                console.log(planAmount,deviceId,code,deviceCode,dicItemName,dicItemCode,assessorId,assessorName)
                //                  3 lizi01 undefined undefined 栗子 lizi01 undefined
                if(deviceId==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请选择商品编码！");
                    return;
                }
                if(planAmount==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请输入计划数量！");
                    return;
                }

                // if($("#need-person-check").is(':checked')){
                //     //自动审核
                //     isAutoassess=1;
                //     assessorName="无";
                // } else{
                //     if(assessorId==""){
                //         $("#assessorIdPop").css("border","1px solid #EF4836");
                //         $(".fa-deviceInfoPop").show();
                //         $(".fa-deviceInfoPop").text(" 请选择审核人员！");
                //         return;
                //     }
                //     $("#assessorIdPop").removeAttr('style');
                //     isAutoassess=0;
                // }
                // if(!$("#need-person-distribute").is(':checked')){
                //     //不自动派发工单
                //     isAutoDistribute=0;
                //     repairmanName="无";
                // } else{
                //     if(repairmanId==""){
                //         $("#distributeIdPop").css("border","1px solid #EF4836");
                //         $(".fa-deviceInfoPop").show();
                //         $(".fa-deviceInfoPop").text(" 请选择入库人员！");
                //         return;
                //     }
                //     $("#distributeIdPop").removeAttr('style');
                //     isAutoDistribute=1;
                // }
                $(".fa-deviceInfoPop").hide();
                if( $("#goodsCodeInputPop").val()==""){
                    //新增
                    $(_this.data.deviceList).each(function(i,item){
                        if(item.deviceId==deviceId){
                            flagParam=false;
                        }
                    })
                    if(flagParam){
                        // 不存在 则 push
                        var device  ={};
                        device.planAmount       = planAmount;
                        device.deviceId       = deviceId;
                        device.goodsCode     = dicItemCode;
                        device.goodsName     = dicItemName;
                        device.isAutoassess   = isAutoassess;
                        device.assessorId     = assessorId;
                        device.assessorName   = assessorName;
                        device.isAutoDistribute   = isAutoDistribute;
                        device.repairmanId    = repairmanId;
                        device.repairmanName  = repairmanName;
                        device.repairmanPostName = repairmanPostName;
                        device.param = [];
                        _this.data.deviceList.push(device);
                        console.log(_this.data.deviceList);
                        _this.loadBaseEquipmentsHtml(_this.data.deviceList);
                        $(".totalDeviceCount span").text(_this.data.deviceList.length);
                        $.DialogByZ.Autofade({Content: "保存成功！"});
                        $("#addDevicePop").modal('hide');
                    }
                    else{
                        //存在 则 提示
                        $(".fa-deviceInfoPop").show();
                        $(".fa-deviceInfoPop").text(" 商品已存在！");
                    }
                }
                else{
                    //编辑
                    console.log("编辑",_this.data.deviceList,deviceId)
                    $(_this.data.deviceList).each(function(i,item){
                        if(item.deviceId==deviceId){
                            item.isAutoDistribute    = isAutoDistribute;
                            item.repairmanId    = repairmanId;
                            item.repairmanName  = repairmanName;
                            item.repairmanPostName = repairmanPostName;
                            item.isAutoassess   = isAutoassess;
                            item.assessorId     = assessorId;
                            item.assessorName   = assessorName;
                            item.planAmount   = planAmount;
                        }
                    })
                    _this.loadBaseEquipmentsHtml(_this.data.deviceList);
                    $.DialogByZ.Autofade({Content: "修改成功！"});
                    $("#addDevicePop").modal('hide');
                }
            })
            //删除弹窗商品
            $(document).on('click', '.a-equipment-delete', function (e) {
                e.stopPropagation();
                var _self=$(this);
                var id = _self.parent().parent().data('id');
                $(_this.data.deviceList).each(function(index,item){
                    if(item.deviceId==id){
                        _this.data.deviceList.splice(index, 1);
                    }
                })
                _this.loadBaseEquipmentsHtml(_this.data.deviceList);
            })
            //全选/全不选 checkbox
            $(document).on('click', '#check-all', function (e) {
                e.stopPropagation();
                var _self=$(this);
                if(_self.is(':checked')){
                    //全选中设置
                    $("#taskDeviceList  tr").each(function(index,item){
                        $(item).find('input[type="checkbox"]').prop("checked","checked");
                    })
                }
                else{
                    //全不选设置
                    $("#taskDeviceList  tr").each(function(index,item){
                        $(item).find('input[type="checkbox"]').prop("checked", false);
                    })
                }
            })
            //有一个未选中则取消全选  /  全都选中则全选选中
            $(document).on('click', '#taskDeviceList  input[type="checkbox"]', function (e) {
                e.stopPropagation();
                var isCheckAll=true;
                if(!$(this).is(':checked')){
                    //有一个没选中的就取消全选
                    $("#check-all").prop("checked", false);
                }
                else{
                    $("#taskDeviceList  tr").each(function(index,item){
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
            //全选/全不选 checkbox
            $(document).on('click', '#check-allParam', function (e) {
                e.stopPropagation();
                var _self=$(this);
                if(_self.is(':checked')){
                    //全选中设置
                    $("#paramList  tr").each(function(index,item){
                        $(item).find('input[type="checkbox"]').prop("checked","checked");
                    })
                }
                else{
                    //全不选设置
                    $("#paramList  tr").each(function(index,item){
                        $(item).find('input[type="checkbox"]').prop("checked", false);
                    })
                }
            })
            //有一个未选中则取消全选  /  全都选中则全选选中
            $(document).on('click', '#paramList  input[type="checkbox"]', function (e) {
                e.stopPropagation();
                var isCheckAll=true;
                if(!$(this).is(':checked')){
                    //有一个没选中的就取消全选
                    $("#check-allParam").prop("checked", false);
                }
                else{
                    $("#paramList  tr").each(function(index,item){
                        if(!$(item).find('input[type="checkbox"]').is(':checked')){
                            isCheckAll=false;
                        }
                    })
                    //全都选中则全选选中
                    if(isCheckAll){
                        $("#check-allParam").prop("checked","checked");
                    }
                }
            })
            //批量删除弹窗商品
            $(document).on('click', '#deleteTasksBtn', function (e) {
                var deviceIdArr=[];
                $("#taskDeviceList  tr").each(function(index,item){
                    var _self=$(item);
                    if(_self.find('input[type="checkbox"]').is(':checked')){
                        var deviceId = _self.data('id');
                        deviceIdArr.push(deviceId);
                    }
                });
                if(deviceIdArr.length>0) {
                    for(var i=0;i<deviceIdArr.length;i++){
                        $(_this.data.deviceList).each(function(index,item){
                            if(item.deviceId==deviceIdArr[i]){
                                _this.data.deviceList.splice(index, 1);
                            }
                        });
                    }
                    $("#check-all").prop("checked", false);
                    _this.loadBaseEquipmentsHtml(_this.data.deviceList);
                }
                else {
                    $.DialogByZ.Autofade({Content:"没有要删除的商品！"});
                }
            })
            //弹窗保存商品参数
            $(document).on('click', '#saveParam', function (e) {
                var deviceId = $("#deviceId").val();
                var params = [];
                $("#paramList tr").each(function(index,item){
                    var _self=$(item);
                    if(_self.find('input[type="checkbox"]').is(':checked')){
                        var paramId = _self.data('id');
                        params.push(paramId);
                    }
                });
                if (params == "" || params == []) {
                    $.DialogByZ.Autofade({Content:"没有选中参数！"});
                }
                $("#device"+deviceId).attr("data-params",JSON.stringify(params));
                _this.data.deviceList[deviceId].param= [];
                _this.data.deviceList[deviceId].param = params;
                $("#check-allParam").prop("checked", false);
                $("#addDeviceParamPop").modal('hide');
            })
            //弹窗关闭
            $(document).on('click' ,'#pop-device-close', function (e) {
                $(".chosen-select").val("");
                $("#goodsNamePop").text("");
                $("#distributeIdPop").val("");
                $("#assessorIdPop").attr("disabled","disabled");
                $("#assessorIdPop").val("");
                $("#need-person-distribute").prop("checked","checked");
                $("#need-person-check").prop("checked","checked");
                // $(".modal").hide();
            })
            //弹窗关闭
            $(document).on('click' ,'#closeDevice', function (e) {
                $(".chosen-select").val("");
                $("#goodsNamePop").text("");
                $("#distributeIdPop").val("");
                $("#assessorIdPop").attr("disabled","disabled");
                $("#assessorIdPop").val("");
                $("#need-person-distribute").prop("checked","checked");
                $("#need-person-distribute").next("label");
                $("#need-person-check").prop("checked","checked");
                // $(".modal").hide();
            })
            //弹窗关闭
            $(document).on('click' ,'#pop-deviceParam-close', function (e) {
                $("#check-allParam").prop("checked", false);
                // $(".modal").hide();
            })
            //弹窗关闭
            $(document).on('click' ,'#closeParam', function (e) {
                $("#check-allParam").prop("checked", false);
                // $(".modal").hide();
            })
            //返回
            $(document).on('click', '.return', function (){
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要返回吗，返回将不保存本页数据？",
                    FunL:function(){
                        _this.data.taskId      = "" ;
                        _this.data.deviceId    = "" ;
                        _this.data.deviceList  = [];
                        window.location.href   = "orderCPInTask.html";
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            })
        },
        //渲染单据
        loadOrderDetailInfo : function () {
            var _this = this;
            _this.data.taskId = window.location.href.split("=")[1];
            if( _this.data.taskId=="" || _this.data.taskId=="undefined"  ){
                //新增
                _this.data.taskId="-1";  //新增
                $(".breadcrumb").find(".active").text('新建单据');
                $("#taskNameInput").val("");
                $("#remark").val("");
                $("#orderType").val("");
                // $("#startTimeInput").val("");
                // $("#timeLimitInput").val("");
                // $("#intervalInput").val("");
                $(".hide-area").hide();
                $(".totalDeviceCount span").text('0');
            }
            else {
                //编辑
                $(".breadcrumb").find(".active").text('编辑单据');
                _addTask_service.getTaskInfo(_this.data.taskId, function (res) {
                    var result=JSON.parse(decodeURIComponent(res));
                    console.log(result);
                    if(result.code==0) {
                        var data=result.data;
                        _this.orderInfoSource(data);
                    }
                    else {
                        $.DialogByZ.Autofade({Content: result.message ||  "查无数据！"});
                    }
                },function(err){
                    _commonFun.errorTips('');
                })
            }
        },
        //商品获取的数据源 显示在页面
        orderInfoSource:function (data) {
            var _this=this;
            var statusTxt='';
            data.status=='on' ? statusTxt='启动': statusTxt='停止';
            $(".hide-area").show();
            $("#taskCodeSpan").text(data.taskId);
            $("#taskTypeSpan").text(statusTxt);
            $("#orderNumberSpan").text(data.orderNum);
            $("#taskCreateTimeSpan").text(data.createTime);
            $("#taskCreatePersonSpan").text(data.createrName);

            $("#taskNameInput").val(data.name);
            $("#taskNameInput").attr("disabled","disabled");
            $("#remark").val(data.remark);
            $("#startTimeInput").val(data.startTime);
            $("#endTimeInput").val(data.endTime);
            $("#timeLimitInput").val(data.timeLimit);
            $("#timeLimitUnit").val(data.timeLimitUnit);
            $("#intervalInput").val(data.interval);
            $("#intervalUnit").val(data.intervalUnit);
            $("#taskType").val(data.taskType);
            _this.data.deviceList=data.devices;
            $(".totalDeviceCount span").text(data.devices.length);
            _this.loadBaseEquipmentsHtml(_this.data.deviceList);
            setTimeout(function () {
                $("#orderType").val(data.orderType);
            },500);
        },
        //------获取商品列表
        loadBaseEquipments: function () {
            var _this=this;
            _addTask_service.getGoodsCodeAll(function (res) {
                var result=res;
                if(result.code==0) {
                    var data=result.data;
                    $(".chosen-select").html("");
                    _this.chose_get_ini(".chosen-select");
                    var device = "<option value=''>请选择商品</option>";
                    for (var i = 0; i <data.length; i++) {
                        device += "<option value=" + data[i].dicItemCode + " dicItemName=" + data[i].dicItemName + "  data-code=" + data[i].code + " dicItemCode="+data[i].dicItemCode+">" + data[i].dicItemCode + " -- " + data[i].dicItemName + "</option>";
                    }
                    $(".chosen-select").html(device);
                    _this.chose_get_ini(".chosen-select");

                    $(".chosen-select").chosen({
                        no_results_text : "未找到此选项!"
                    });
                    $(".chosen-select").trigger("liszt:updated");
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        //多选select 数据同步
        chose_get_ini : function (select){
            $(select).chosen().change(function(){$(select).trigger("liszt:updated");});
        },
        //select value获取
        chose_get_value:function (select){
            return $(select).val();
        },
        //渲染商品列表table
        loadBaseEquipmentsHtml: function (list) {
            $(".totalDeviceCount span").text("0");
            if(list.length>0){
                var m=0;
                $(".hide-num-img").hide();
                $("#taskDeviceList").empty();
                $(".totalDeviceCount span").text(list.length);
                var str = '', isAutoassess="",assessorName="",isAutoDistribute="",repairmanName="";
                for (var ele in list) {
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
                    str+='<tr id="device'+list[ele].deviceId+'" data-ind="'+m+'" data-id="'+list[ele].deviceId+'"  goodsName="'+list[ele].goodsName+'"  planAmount="'+list[ele].planAmount+'" data-categoryid="'+list[ele].categoryId+'">' +
                        '<td  style="width: 50px"><div class="checkbox-custom checkbox-default"><input type="checkbox" >' +
                        '<label></label></div></td>'+
                        '<td style="width: 20%" class="goodsCode">' + list[ele].goodsCode + '</td>' +
                        '<td style="width: 20%" class="goodsName">' + list[ele].goodsName + '</td>' +
                        '<td style="width: 20%" class="planAmount">' + list[ele].planAmount + '</td>' +
                        // '<td style="width: 10%" class="isAutoassess">' + isAutoassess + '</td>' +
                        // '<td style="width: 10%" class="assessorName">' + assessorName + '</td>' +
                        // '<td style="width: 10%" class="isAutoDistribute">' + isAutoDistribute + '</td>' +
                        // '<td style="width: 10%" class="repairmanName">' + repairmanName + '</td>' +
                        '<td><a class="link a-equipment-edit"> <i class="fa-table-line-edit"  title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'+
                        // '<a class="link a-equipment-param"> <i class="fa-table-line-para"  title="检测项"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'+
                        '<a class="link a-equipment-delete" > <i class="fa-table-line-delete"  title="删除"></i></a></td></tr>';
                    m++;
                }
                $("#taskDeviceList").html(str);
            }
            else {
                $(".hide-num-img").show();
                $("#taskDeviceList").empty();
            }
        },
//------获取审核员/维修员列表
        loadAssessorsAnd: function (id) {
            _addTask_service.getAssessorsAnd(id,function (res) {
                var result=JSON.parse(decodeURIComponent(res));
                console.log(result);
                if(result.code==0) {
                    var assessors=result.data.assessors;
                    var inspections=result.data.inspections;
                    var inspectionsName=result.data.inspectionsName;
                    $("#assessorIdPop").empty();
                    var optionAss="<option value=''>请选择</option>";
                    for (var i = 0; i < assessors.length; i++) {
                        optionAss += "<option value='" + assessors[i].userId + "'>" + assessors[i].employeeName + "</option>";
                    }
                    $("#assessorIdPop").html(optionAss);
                    $("#distributeIdPop").empty();
                    var optionRep="<option value=''>请选择</option>";
                    for (i = 0; i < inspections.length; i++) {
                        optionRep += "<option value='" + inspections[i].userId + "'>" + inspections[i].employeeName + "</option>";
                    }
                    $("#distributeIdPop").html(optionRep);
                    $("#distributeIdPop").attr("data-name",inspectionsName);
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
//------获取工单优先级列表
        loadPrioritiesOption: function () {
            _addTask_service.getFaultPriorities();
        }

    };
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
    // jeDate("#startTimeInput", {
    //     format: "YYYY-MM-DD",
    //     zIndex: 3000,
    //     minDate: startTime,
    //     theme:{ bgcolor:"#00A1CB",color:"#ffffff", pnColor:"#00CCFF"},
    //     donefun: function(obj){
    //         var value = obj.val;
    //         var endTimeInput = $("#endTimeInput").val();  //结束日期
    //         if (endTimeInput != null && endTimeInput != "") {
    //             if (value > endTimeInput) {
    //                 $.DialogByZ.Autofade({Content:'开始执行日期不能晚于单据终止日期！'});
    //                 $("#startTimeInput").val("");
    //                 return;
    //             }
    //         }
    //     }
    // });
    // jeDate("#endTimeInput", {
    //     format: "YYYY-MM-DD",
    //     zIndex: 3000,
    //     minDate: startTime,
    //     theme:{ bgcolor:"#00A1CB",color:"#ffffff", pnColor:"#00CCFF"},
    //     donefun: function(obj){
    //         var value = obj.val;
    //         var startTimeInput = $("#startTimeInput").val();  //开始日期
    //         if (startTimeInput != null && startTimeInput != "") {
    //             if (value < startTimeInput) {
    //                 $.DialogByZ.Autofade({Content:'单据终止日期不能早于开始执行日期！'});
    //                 $("#endTimeInput").val("");
    //                 return;
    //             }
    //         }
    //     }
    // });
    $(".chosen-select").chosen({
        no_results_text: "没有匹配结果",
        disable_search: false,
        placeholder_text_multiple: "请选择"
    });
    _addTask.init();
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
