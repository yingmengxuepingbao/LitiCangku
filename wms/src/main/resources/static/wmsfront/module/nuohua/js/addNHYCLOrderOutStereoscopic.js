var _addTask_service;
$(function(){
    _addTask_service={
        //获取基础商品列表
        getGoodsCodeAll: function(resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/goods/getHBGoodsCodeAll/batchNoByType?goodType=0",
                headers:{'token':localStorage.getItem('token')},
                success	: resolve,
                error	: reject
            });
        },
        //根据商品编码，获取批次号
        getHBBatchNo: function(data ,resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/goods/getGoodsCodeAll/getHBBatchNo",
                headers:{'token':localStorage.getItem('token')},
                data    : data,
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
        //获取出货口下拉框
        getOutAddress : function(receivers){
            var receiverIds = [];
            if(receivers != null){
                receiverIds  =receivers.split(",");
            }
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/wmsAddressRealRela/getHBStereoscopicOutAddress",
                headers:{'token':localStorage.getItem('token')},
                data    : { code :"platform"},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var options = '';
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            if(receiverIds.length > 0 && $.inArray(ele.addressCode.toString(),receiverIds) > -1){
                                options += "<option value='"+ele.addressCode+"' selected >"+ele.addressName+"</option>";
                            }else{
                                options +='<option value="'+ele.addressCode+'">'+ele.addressName+'</option>'
                            }
                        }

                        $("#outAddress").html(options);
                    }
                }
            });
        },
        //获取单据类型下拉框
        getOrderTypePriorities : function(resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/dictItem/getDictType/order_out_type",
                headers:{'token':localStorage.getItem('token')},
                data    : { code :"order_out_type"},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var options = '';
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
        //获取商品可用数量/重量
        getGoodsUsableAmount  : function(goodsCode,batchNo,resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/location/getHBUseAbleAmount",
                headers:{'token':localStorage.getItem('token')},
                data    : {
                    goodsCode : goodsCode,
                    batchNo : batchNo
                },
                success	: resolve,
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
        getTaskInfo: function(orderNo,resolve, reject){
            $.ajax({
                type		:"get",
                url		: $.mpbGetHeaderPath()+"/wmsOrderOutStereoscopicDeail/queryByAny",
                headers:{'token':localStorage.getItem('token')},
                data		: {
                    orderNo: orderNo
                },
                success		: resolve,
                error		: reject
            });
        },
        //查询单据是否已经存在
        sel: function(data,resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/wmsOrderOutStereoscopic/sel",
                headers:{'token':localStorage.getItem('token')},
                contentType: 'application/json',
                data    :JSON.stringify(data),
                success	: resolve,
                error	: reject
            });
        },
        //新增、修改维护单据
        submitTask: function(data,resolve, reject){
            console.log("submitTask: " + JSON.stringify(data));
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/wmsOrderOutStereoscopic/u",
                headers:{'token':localStorage.getItem('token')},
                contentType: 'application/json',
                data    :JSON.stringify(data),
                success	: resolve,
                error	: reject
            });
        }
    };
    var _addTask= {
        data: {
            taskId         : "" ,//存储编辑时保存的单据id
            deviceId       : "", //存放编辑时商品id
            batchNo        : "", //存放编辑时的批次号
            planAmount     : "", //存放编辑时商品计划数量
            useAbleAmount     : "", //存放编辑时商品可用数量
            goodsType : 0, //商品类型：原材料
            deviceList     : []
        },
        init: function () {
            this.onLoad();
            this.bindEvent();
        },
        onLoad: function () {
            //商品列表
            this.loadBaseEquipments();
            //获取批次号
            this.BatchNoEquipments();
            this.loadPrioritiesOption();
            $("#taskDeviceList").empty();
            $("#goodsCodePop").chosen();
            //批次号
            $("#batchNoPop").chosen();
            // $("#startTimeInput").val("");
            $("#assessorIdPop").attr("disabled","disabled");
            $(".fa-deviceInfoPop").hide();
            this.loadOrderDetailInfo();
        },
        bindEvent: function () {
            var _this = this;
            //商品编码： change事件
            $(document).on('change', '#goodsCodePop', function (e){
                var _self=$(this);
                _this.data.deviceId=_self.val().split("#")[0];
                console.log("_this.data.deviceId",_this.data.deviceId);
                var data = {
                    goodsCode:_this.data.deviceId
                }
                //批次号更改
                _this.BatchNoEquipments(data);

                var batchNo = $("#batchNoPop").val();
                console.log("批次号更改- batchNo = "+batchNo);
                if(batchNo=="空批次" || batchNo== "请选择批次号" ){
                    batchNo = "";
                }
                // 更新可用数量/重量
                _this.loadUseAbleAmount(_this.data.deviceId, batchNo);

            })
            //批次号： change事件
            $(document).on('change', '#batchNoPop', function (e){
                var _self=$(this);
                _this.data.batchNo=_self.val().split("#")[0];
                console.log("_this.data.batchNo:",_this.data.batchNo);
                _this.data.deviceId = $("#goodsCodePop option:selected").val();
                console.log("_this.data.deviceId:", _this.data.deviceId )
                if(_this.data.batchNo=="空批次" ||_this.data.batchNo== "请选择批次号"){
                    _this.data.batchNo = "";
                }
                // 更新可用数量/重量
                _this.loadUseAbleAmount(_this.data.deviceId,_this.data.batchNo);
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
                $("#deviceCodeInputPop").val("");
                $("#planAmount").val("");
                $("#realAmount").val("");
                $("#planPalletAmount").val("");
                $("#realPalletAmount").val("");
                $("#useAbleAmount").val("");
                $("#goodsCodePop").val("");
                $("#goodsCodePop").chosen();
                $("#goodsCodePop").trigger("chosen:updated");
                $("#batchNoPop").val("");
                $("#batchNoPop").chosen();
                $("#batchNoPop").trigger("chosen:updated");
                //原材料类型0
                $("#goodsType").val(0);
                $("#addDevicePop").modal();
                $("#goodsCodePop span").text("请选择商品");
            })
            //编辑商品
            $(document).on('click' ,'.a-equipment-edit', function (e) {
                e.stopPropagation();
                var _self = $(this);
                $("#add-chosen").show();
                $("#edit-input").hide();
                // $("#add-chosen").hide();
                // $("#edit-input").hide();
                //获取快捷详情
                _this.data.deviceId= _self.parent().parent().attr('id');

                var goodsCode = _self.parent().parent().attr('id');
                var planAmount= _self.parent().parent().attr('planAmount');
                var useAbleAmount= _self.parent().parent().attr('useAbleAmount');
                var batchNo= _self.parent().parent().attr('batchNo');
                console.log("goodsCode ="+ goodsCode );
                console.log("planAmount ="+ planAmount );
                console.log("useAbleAmount ="+ useAbleAmount );
                console.log("batchNo ="+ batchNo );
                var data = {
                    goodsCode  :  _this.data.deviceId
                }
                $("#deviceCodeInputPop").val(goodsCode);
                $("#planAmount").val(planAmount);

                $("#batchNoPop").val(batchNo);
                $("#batchNoPop").trigger("liszt:updated");

                $("#goodsCodePop").val(goodsCode);
                $("#goodsCodePop").trigger("liszt:updated");

                //批次号更改
                _addTask_service.getHBBatchNo(data, function (res) {
                    var result=res;
                    if(result.code==0) {
                        var data=result.data;
                        console.log("data = "+ data);
                        var device ="";
                        device +="<option value=''>请选择批次号</option>";
                        $("#batchNoPop").html("");
                        for (var i = 0; i <data.length; i++) {
                            var batchNoN ="";
                            if(data[i]!=null &&data[i] !="" && data[i] != undefined){
                                batchNoN = data[i].batchNo;
                                console.log("批次号：" + batchNoN)
                                if(batchNoN == batchNo){
                                    device += "<option value="+ batchNoN +" selected>"+batchNoN+"</option>";
                                }else{
                                    device += "<option value="+ batchNoN +">"+batchNoN+"</option>";
                                }

                            }
                        }
                        $("#batchNoPop").html(device);

                        $("#batchNoPop").chosen({
                            no_results_text : "未找到此选项!"
                        });
                        $("#batchNoPop").trigger("liszt:updated");
                    }
                },function(err){
                    _commonFun.errorTips('');
                })
                if(batchNo == "请选择批次号" || batchNo=="空批次"){
                    batchNo="";
                }
                //可选重量
                _addTask_service.getGoodsUsableAmount(goodsCode,batchNo, function (res) {
                    var result=res;
                    console.log("loadUseAbleAmount result" + result);
                    if(result.code==0) {
                        console.log("loadUseAbleAmount = " + result.data);
                        $("#useAbleAmount").val(result.data);
                    }
                },function(err){
                    _commonFun.errorTips('');
                })

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
                var sapOrderNo	    = $.trim($("#sapOrderNo").val());
                var orderNo	    = $.trim($("#orderNo").val());
                var orderType = $.trim($("#orderType").val());
                //var orderType = $("#orderType").find('option:selected').text();
                console.log("orderType = "+orderType);
                var outAddress    = $.trim($("#outAddress").val());
                var remark	= $.trim($("#remark").val());
                var userDefined1 = $.trim($("#userDefined1").val());

                if(orderType==""){
                    $("#orderType").css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "单据类型，不能为空！"});
                    return;
                }
                if(_this.data.deviceList.length>0){

                    var deviceList = _this.data.deviceList;
                    // for (var ele in deviceList) {
                    //     var param = deviceList[ele].param;
                    //     if (param == null || param == "" || param.length == 0 ) {
                    //         _commonFun.errorTips(deviceList[ele].deviceName + "商品没有配置点巡检参数，请配置后保存！");
                    //         return false;
                    //     }
                    // }
                    //
                    // _self.attr("disabled","disabled");
                    $.DialogByZ.Confirm({
                        Title: "操作提示",
                        Content: "确定要提交该单据吗？",
                        FunL:function(){
                            var data = {
                                userDefined2:"50",
                                sapOrderNo	    : sapOrderNo,
                                orderNo	    : orderNo,
                                orderType    : orderType,
                                remark : remark,
                                userDefined1: userDefined1,
                                outAddress	: outAddress,
                                wmsOrderOutStereoscopicDeailList     : _this.data.deviceList
                            };
                            // console.log("submitData: " + JSON.stringify(data));

                            _addTask_service.sel(data, function (res) {
                                var result=res;
                                if (result.code == 0) {
                                    _addTask_service.submitTask(data, function (res) {
                                        var result=res;
                                        if (result.code == 0) {
                                            $.DialogByZ.Autofade({Content: "单据提交成功！"});
                                            window.location.href = "wmsNHYCLOrderOutStereoscopicList.html";
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
                                    _self.removeAttr("disabled");
                                    $.DialogByZ.Autofade({Content: result.message || "单据提交失败！"});
                                }
                            }, function (err) {
                                _self.removeAttr("disabled");
                                _commonFun.errorTips('');
                            })
                            $.DialogByZ.Close();
                        },
                        FunR:function(){
                            _self.removeAttr("disabled");
                            $.DialogByZ.Close();
                        }
                    })
                }
                else{
                    $.DialogByZ.Autofade({Content: "出库单商品不能为空！"});
                }
            })
            //弹窗保存商品
            $(document).on('click', '#saveDevice', function () {
                var planAmount      = $("#planAmount").val();
                var realAmount      = 0;
                var planPalletAmount      = 0;
                var realPalletAmount      = 0;
                var useAbleAmount      = $("#useAbleAmount").val();
                var deviceId	    = _this.data.deviceId;
                var code            = $("#goodsCodePop option:selected").data('code');
                var deviceCode      = $.trim(code);
                var dicItemName      = $("#goodsCodePop option:selected").attr('dicItemName');
                var dicItemCode      = $("#goodsCodePop option:selected").attr('dicItemCode');
                //var batchNo      = $("#batchNoPop option:selected").attr('batchNo');
                var batchNo      = $("#batchNoPop").find('option:selected').text();
                var assessorId      = $("#assessorIdPop").val();
                var assessorName    = $("#assessorIdPop  option:selected").text();
                var assessorPostName = $("#assessorIdPop").attr("data-name");
                var repairmanId	 = $("#distributeIdPop").val();
                var repairmanName   = $("#distributeIdPop  option:selected").text();
                var repairmanPostName = $("#distributeIdPop").attr("data-name");
                var isAutoassess	= "";
                var isAutoDistribute	= "";
                var flagParam   = true;
                if(batchNo=="请选择批次号"){
                    batchNo="";
                }
                console.log(planAmount,deviceId,code,deviceCode,dicItemName,dicItemCode,assessorId,assessorName,batchNo)
                //                  3 lizi01 undefined undefined 栗子 lizi01 undefined
                if(deviceId==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请选择商品编码！");
                    setTimeout(function() {
                        $(".fa-deviceInfoPop").hide();
                    }, 1000);
                    return;
                }
                if(batchNo==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text("请选择批次号！");
                    setTimeout(function() {
                        $(".fa-deviceInfoPop").hide();
                    }, 1000);
                    return;
                }
                if(planAmount==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请输入计划重量！");
                    setTimeout(function() {
                        $(".fa-deviceInfoPop").hide();
                    }, 1000);
                    return;
                }
                if(eval(planAmount) > eval(useAbleAmount)){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 计划重量大于可用重量！");
                    setTimeout(function() {
                        $(".fa-deviceInfoPop").hide();
                    }, 1000);
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
                if( $("#deviceCodeInputPop").val()==""){
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
                        device.realAmount       = realAmount;
                        device.planPalletAmount       = planPalletAmount;
                        device.realPalletAmount       = realPalletAmount;
                        device.deviceId       = deviceId;
                        device.goodsCode     = dicItemCode;
                        device.goodsName     = dicItemName;
                        device.batchNo     = batchNo;
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
                    $(_this.data.deviceList).each(function(i,item){
                        if(item.goodsCode==deviceId){
                            item.planAmount   = planAmount;
                            item.batchNo   = batchNo;
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
                var id = _self.parent().parent().attr('id');
                console.log("id:" + id);
                console.log("idddddd:" + JSON.stringify(_this.data.deviceList));
                $(_this.data.deviceList).each(function(index,item){
                    if(item.goodsCode==id){
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
                        var deviceId = _self.attr('id');
                        deviceIdArr.push(deviceId);
                    }
                });
                if(deviceIdArr.length>0) {
                    for(var i=0;i<deviceIdArr.length;i++){
                        $(_this.data.deviceList).each(function(index,item){
                            if(item.goodsCode==deviceIdArr[i]){
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
                $("#goodsCodePop").val("");
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
                $("#goodsCodePop").val("");
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
                        _this.data.batchNo    = "" ;
                        _this.data.deviceList  = [];
                        window.location.href   = "wmsNHYCLOrderOutStereoscopicList.html";
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
            // _this.data.taskId = window.location.href.split("=")[1];
            var param = window.location.href.split("=")[1];
            // console.log("_this.data.taskId: " + _this.data.taskId)
            // console.log("isNaN(_this.data.taskId): " + isNaN(_this.data.taskId))
            // if( _this.data.taskId=="" || isNaN(_this.data.taskId)){
            if( param=="" || param==null){
                //新增
                _this.data.taskId="-1";  //新增
                $(".breadcrumb").find(".active").text('新建出库单');
                $("#remark").val("");
                $("#orderType").val("");
                $("#userDefined1").val("");
                $("#orderNo").val("");
                $("#sapOrderNo").val("");
                // $("#startTimeInput").val("");
                // $("#timeLimitInput").val("");
                // $("#intervalInput").val("");
                $(".hide-area-noclass").hide();
                $(".totalDeviceCount span").text('0');
            }
            else {
                _this.data.taskId = param.split(",")[0];
                $("#orderNo").val(_this.data.taskId);
                console.log("传入的orderNo :"+_this.data.taskId);
                // 0 表示 查看  1 表示 编辑
                var type = param.split(",")[1];
                if(type == '0'){
                    $("#submitAll").hide();
                    // $("#orderType").
                    // $("#remark").hide();
                    $("#deleteTasksBtn").hide();
                    $("#addTaskBtn").hide();
                }else{
                    $("#submitAll").show();
                    // $("#orderType").show();
                    // $("#remark").show();
                    $("#deleteTasksBtn").show();
                    $("#addTaskBtn").show();
                }

                //编辑
                $(".breadcrumb").find(".active").text('编辑出库单');
                _addTask_service.getTaskInfo(_this.data.taskId, function (res) {
                    var result=res;
                    console.log("result:" + result);
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
            console.log("data:" + data);
            console.log("data.remark1:" + data[0].remark1);
            console.log("data.outAddress:" + data[0].outAddress);
            console.log("data.orderNo:" + data[0].orderNo);
            console.log("data.userDefined1:" + data[0].userDefined1);
            console.log("data.batchNo:" + data[0].batchNo);
            var _this=this;
            $(".hide-area-noclass").show();
            $("#remark").val(data[0].remark1);
            var orderTypeTxt = "";
            if(data[0] !=null &&data[0].orderType =="20"){
                orderTypeTxt = "销售出库";
            } else if(data[0] !=null &&data[0].orderType =="26"){
                orderTypeTxt = "人工出库";
            }else{
                orderTypeTxt = "采购出库";
            }
            console.log("data[0].orderType = "+data[0].orderType);
            console.log("orderTypeTxt = "+orderTypeTxt);
            $("#orderType option[value=orderTypeTxt]").prop("selected",true);
            //$("#orderType").val(orderTypeTxt);
            $("#userDefined1").val(data[0].userDefined1);
            $("#orderNo").val(data[0].orderNo);
            $("#sapOrderNo").val(data[0].orderNo);

            //出库口
            _addTask_service.getOutAddress(data[0].outAddress);

            setTimeout(function () {
                $("#orderType").val(data[0].orderType);
            }, 100)

            _this.data.deviceList=data;
            _this.loadBaseEquipmentsHtml(data);
        },
        //------获取批次号
        BatchNoEquipments: function (data) {
            //更新 批次号
            _addTask_service.getHBBatchNo(data, function (res) {
                var result=res;
                if(result.code==0) {
                    var data=result.data;
                    console.log("data = "+ data);
                    var device ="";
                    device +="<option value=''>请选择批次号</option>";
                    $("#batchNoPop").html("");
                    for (var i = 0; i <data.length; i++) {
                        console.log("data.length = "+data.length);
                        var batchNo ="";
                        if(data[i]!=null &&data[i] !="" && data[i] != undefined){
                            batchNo = data[i].batchNo;
                            console.log("批次号：" + batchNo)
                            device += "<option value=" + batchNo +">" +  data[i].batchNo + "</option>";
                        }else{
                            if(isNaN(batchNo) || batchNo == ""){
                                batchNo = "空批次";
                            }
                            device += "<option value=" + batchNo +"></option>";
                        }
                    }
                    $("#batchNoPop").html(device);

                    $("#batchNoPop").chosen({
                        no_results_text : "未找到此选项!"
                    });
                    $("#batchNoPop").trigger("liszt:updated");
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        //------获取商品列表
        loadBaseEquipments: function () {
            var _this=this;
            _addTask_service.getGoodsCodeAll(function (res) {
                var result=res;
                if(result.code==0) {
                    var data=result.data;
                    $("#goodsCodePop").html("");
                    _this.chose_get_ini("#goodsCodePop");
                    var device = "<option value=''>请选择商品</option>";
                    for (var i = 0; i <data.length; i++) {
                        //console.log("data.length = "+data.length);
                        var batchNo = data[i].batchNo;
                        //console.log("批次号：" + batchNo)
                        // if(isNaN(batchNo) || batchNo == ""){
                        //     batchNo = "空批次";
                        // }
                        var goodsName = data[i].goodsName;
                        var goodsCode = data[i].goodsCode;
                        if(goodsName == "" || goodsName=="undefined"){
                            goodsName = "''";
                        }
                        if(goodsCode == "" || goodsCode=="undefined"){
                            goodsCode = "''";
                        }
                        if(batchNo == "" || batchNo=="undefined"){
                            batchNo = "''";
                        }
                        //var goodsCodeBatchNo = goodsCode + "#" + batchNo;
                        var goodsCodeBatchNo = goodsCode;
                        device += "<option value=" + goodsCodeBatchNo + " dicItemName=" + goodsName + "  batchNo=" + batchNo + " dicItemCode="+goodsCode+">" + data[i].goodsName  + "</option>";
                    }
                    $("#goodsCodePop").html(device);
                    _this.chose_get_ini("#goodsCodePop");

                    $("#goodsCodePop").chosen({
                        no_results_text : "未找到此选项!"
                    });
                    $("#goodsCodePop").trigger("liszt:updated");
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
                    str+='<tr id="'+list[ele].goodsCode+'" data-ind="'+m+'" planAmount="'+list[ele].planAmount+'"  batchNo="'+list[ele].batchNo+'"  data-assessorid="'+list[ele].assessorId+'" data-categoryid="'+list[ele].categoryId+'">' +
                        '<td  style="width: 50px"><div class="checkbox-custom checkbox-default"><input type="checkbox" >' +
                        '<label></label></div></td>'+
                        // '<td style="width: 15%" class="goodsCode">' + list[ele].orderNo + '</td>' +
                        '<td style="width: 20%" class="goodsName">' + list[ele].goodsName + '</td>' +
                        '<td style="width: 20%" class="goodsCode">' + list[ele].goodsCode + '</td>' +
                        '<td style="width: 10%" class="batchNo">' + list[ele].batchNo + '</td>' +
                        '<td style="width: 10%" class="planAmount">' + list[ele].planAmount + '</td>' +
                        '<td style="width: 10%" class="realAmount">' + list[ele].realAmount + '</td>' +
                        '<td style="width: 10%" class="planPalletAmount">' + list[ele].planPalletAmount + '</td>' +
                        '<td style="width: 10%" class="realPalletAmount">' + list[ele].realPalletAmount + '</td>' +
                        // '<td style="width: 10%" class="planAmount">' + list[ele].realAmount + '</td>' +
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
        // 更新可用数量
        loadUseAbleAmount: function (goodsCode, batchNo) {
            _addTask_service.getGoodsUsableAmount(goodsCode, batchNo, function (res) {
                var result=res;
                console.log("loadUseAbleAmount result" + result);
                if(result.code==0) {
                    console.log("loadUseAbleAmount" + result.data);
                    $("#useAbleAmount").val(result.data);
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
//------获取工单优先级列表
        loadPrioritiesOption: function () {
            _addTask_service.getOutAddress();
            _addTask_service.getOrderTypePriorities();
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
    $("#goodsCodePop").chosen({
        no_results_text: "没有匹配结果",
        disable_search: false,
        placeholder_text_multiple: "请选择"
    });
    _addTask.init();
});
//安全库存 数据校验
function checkStockNumber(that) {
    var stockNumber = $(that).val();
    var useAbleAmount = $("#useAbleAmount").val();
    console.log("stockNumber: " + stockNumber);
    console.log("useAbleAmount: " + useAbleAmount);
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
        if(eval(stockNumber) > eval(useAbleAmount)){
            _commonFun.errorTips("计划数量必须小于等于可用数量");
            return false;
        }
    }else{
        _commonFun.errorTips("不能为空");
        $(that).val(1);
        return false;
    }
}
