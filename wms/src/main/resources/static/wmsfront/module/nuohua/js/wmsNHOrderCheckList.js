var permissions ={};
if(_commonFun.isNotBlank(window.parent.operationData)){
    permissions = window.parent.operationData.permissions;
}
//控制 新增按钮 显示
// if(permissions["addEdgeBox"]){
//     $("#addEquipment").show();
// }else {orderNo_search
//     $("#addEquipment").hide();
// }
var _box_service,_edgeBox;
$(function(){
    _box_service={
        // 获取盘点口
        getOutAddress : function(resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/wmsAddressRealRela/getHBStereoscopicOutAddress",
                headers:{'token':localStorage.getItem('token')},
                data    : { code :"checkOutAddress"},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var options = '';
                        options +='<option value=""></option>'
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.addressCode+'">'+ele.addressName+'</option>'
                        }

                        $("#outAddress").html(options);
                    }
                },
                error	: reject
            });
        },
        // 获取库区
        getAreaCode : function(resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                data    : { },
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var options = '';
                        options +='<option value=""></option>'
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.dicItemCode+'">'+ele.dicItemName+'</option>'
                        }

                        $("#areaCode").html(options);
                        $("#areaCode1").html(options);
                    }
                },
                error	: reject
            });
        },
        // 获取库位
        getLocationCode : function(areaCode){
            var _this=this;
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/location/getLocationCodeList/all",
                headers:{'token':localStorage.getItem('token')},
                // 0可用1入库中2出库中3占用4异常
                data    : {
                    useStatus :"",
                    areaCode : areaCode
                },
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var data=result.data;
                        $("#fromLocationCode").html("");
                        var device = "";
                        for (var i = 0; i <data.length; i++) {
                            var item = data[i];
                            // if(receiverIds.length > 0 && $.inArray(item.dicItemCode.toString(),receiverIds) > -1){
                            //     device += "<option value='"+item.locationCode+"' selected >"+item.locationCode+"</option>";
                            // }else{
                                device += "<option value='"+item.locationCode+"'>"+item.locationCode+"</option>";
                            // }
                        }
                        console.log("device:" + device);
                        $("#locationCode").html(device);

                        $('#locationCode').chosen().trigger("chosen:updated");
                    }
                },
                error	: function(result){
                    console.log("库位获取异常！");
                },
            });
        },
        // 获取商品
        getGoodsCode : function(){
            var _this=this;
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/goods/getGoodsCodeAll",
                headers:{'token':localStorage.getItem('token')},
                data    : {
                },
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var data=result.data;
                        $("#goodsCode").html("");
                        var device = "";
                        for (var i = 0; i <data.length; i++) {
                            var item = data[i];
                            // if(receiverIds.length > 0 && $.inArray(item.dicItemCode.toString(),receiverIds) > -1){
                            //     device += "<option value='"+item.locationCode+"' selected >"+item.locationCode+"</option>";
                            // }else{
                            device += "<option value='"+item.dicItemCode+"'>"+item.dicItemName + "</option>";
                            // }
                        }
                        console.log("device:" + device);
                        $("#goodsCode").html(device);

                        $('#goodsCode').chosen().trigger("chosen:updated");
                    }
                },
                error	: function(result){
                    console.log("库位获取异常！");
                },
            });
        },
        //根据筛选条件获取EdgeBox列表
        getEdgeBoxs: function(edgeBoxInfo,resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/wmsHBOrderCheck/list",
                headers:{'token':localStorage.getItem('token')},
                data	: edgeBoxInfo,
                success	: resolve,
                error	: reject
            });
        },
        //删除出库单
        deleteEdgeBoxInfo: function(checkId,resolve, reject){
            var data = {
                checkId  :  checkId
            }
            $.ajax({
                type		:"post",
                url		: $.mpbGetHeaderPath()+"/wmsHBOrderCheck/deleteByCheckId",
                headers:{'token':localStorage.getItem('token')},
                data		: data,
                success		: resolve,
                error		: reject
            });
        },
        //删除出库单
        deleteEdgeBoxInfo1: function(orderNo,resolve, reject){
            var data = {
                orderNo  :  orderNo
            }
            $.ajax({
                type		:"post",
                url		: $.mpbGetHeaderPath()+"/wmsHBOrderCheck/deleteByOrderNo",
                headers:{'token':localStorage.getItem('token')},
                data		: data,
                success		: resolve,
                error		: reject
            });
        },
        //启动出库任务
        disableEdgeBoxInfo: function(moveId,resolve, reject){
            $.ajax({
                type		:"post",
                url		: $.mpbGetHeaderPath()+"/wmsMoveStereoscopic/start",
                headers:{'token':localStorage.getItem('token')},
                data		: {
                    moveId  :   moveId
                },
                success		: resolve,
                error		: reject
            });
        },
        equipmentAjaxUpload : function(file, resolve, reject){
            $.ajax({
                type	: "post",
                url     : "/IEAM/deviceManagement/edgeBox/equipmentAjaxUpload",
                data    : file,
                success : resolve,
                error   : reject,
                async: false,
                cache: false,
                contentType: false,
                processData: false
            });
        }
    };
//-----------------主体_edgeBox-begin----------------------
    _edgeBox= {
        data: {
            currentNumber   : 0,  //记录当前页有多少条数据
            totalPageNum    : 0,  //记录一共多少页
            numberOnePage   : 8,   //一页几条数据
            orderBy         : "",  //表头排序字段
            flag            : false
        },
        init: function () {
            this.onLoad();
            this.bindEvent();
        },
        onLoad: function () {
            this.initDatetime();
            this.loadEdgeBoxs();//加载EdgeBox列表
        },
        bindEvent: function () {
            var _this = this;
            //导出
            $(document).on('click', '#exportBtn', function () {
                window.location.href="/IEAM/deviceManagement/edgeBox/equipmentDownLoad.do";
            });
            //导入弹窗
            $(document).on('click', '#importBtn', function () {
                $("#importFile").show();
            });

            // 仓库切换，对应的库位切换
            $(document).on('change', '#areaCode', function (e){
                var areaCode=$("#areaCode option:selected").val();
                // console.log("areaCode" + areaCode);
                _box_service.getLocationCode(areaCode);
            })
            //导入
            $(document).on('click', '#equipmentAjaxUpload', function () {
                var formData = new FormData($("#form1")[0]);
                if(formData!=null&&formData!=''){
                    _box_service.equipmentAjaxUpload(formData, function (res) {
                        var result=JSON.parse(decodeURIComponent(res));
                        if(result.code==0) {
                            $.DialogByZ.Autofade({Content: "导入成功！"});
                            $("#check-all").prop("checked", false);
                            _this.loadEdgeBoxs();
                        }
                        else {
                            $.DialogByZ.Autofade({Content: result.message || "导入失败！"});
                        }
                    }, function (err) {
                        _commonFun.errorTips('');
                    })
                }else {
                    $.DialogByZ.Autofade({Content: "请选择上传附件！"});
                }
                $.DialogByZ.Close();
            })
            //关闭导入弹窗
            $(document).on('click', '#popClose', function () {
                $("#importFile").hide();
            });
            //关闭新增弹窗
            $(document).on('click', '#popClose2', function () {
                $(".modal").hide();
            });
            //下载模版
            $(document).on('click', '#downloadModel', function () {
                window.location.href="/IEAM/deviceManagement/edgeBox/equipmentModalDownLoad.do";
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
                _this.loadEdgeBoxs();
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
                _this.loadEdgeBoxs();
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
                _this.loadEdgeBoxs();
            });
            //搜索文本框-盘点单号
            $("#orderNo_search").bind("input propertychange", function () {
                $(".currentPage").text("1");
                $("#check-all").prop("checked", false);
                _this.loadEdgeBoxs();
            });
            //搜索文本框-状态
            $("#orderStatusSelect").bind("input propertychange", function () {
                $(".currentPage").text("1");
                $("#check-all").prop("checked", false);
                _this.loadEdgeBoxs();
            });
            //搜索文本框-类型
            $("#orderTypeSelect").bind("input propertychange", function () {
                $(".currentPage").text("1");
                $("#check-all").prop("checked", false);
                _this.loadEdgeBoxs();
            });
            //点击搜索
            $(document).on('click', '#topSearchInfoList', function () {
                $(".currentPage").text("1");
                $("#check-all").prop("checked", false);
                _this.loadEdgeBoxs();
            });
            //点击重置筛选条件
            $(document).on('click', '#resetSearchConditions', function () {
                $("#orderTypeSelect").val("");
                $("#orderStatusSelect").val("");
                $("#orderNo_search").val("");
                $(".currentPage").text("1");
                _this.data.orderBy="";
                $("#check-all").prop("checked", false);
                _this.loadEdgeBoxs();
            });
            //表头排序
            $(document).on('click', '#edgeBoxList thead tr th', function (e) {
                e.stopPropagation();
                var _self=$(this);
                var orderBy=_self.data('sort');

                if(_self.find('i').hasClass('fa-sort-amount-asc')){
                    _self.parents("thead").find('i').removeClass("th-sort-active");
                    _self.find('i').removeClass('fa-sort-amount-asc').addClass('fa-sort-amount-desc th-sort-active');
                    _this.data.orderBy=orderBy + " desc";
                    _this.loadEdgeBoxs();

                }
                else if(_self.find('i').hasClass('fa fa-sort-amount-desc')){
                    _self.parents("thead").find('i').removeClass("th-sort-active");
                    _self.find('i')
                        .removeClass('fa-sort-amount-desc')
                        .addClass('fa-sort-amount-asc th-sort-active');
                    _this.data.orderBy=orderBy + " asc";
                    _this.loadEdgeBoxs();
                }

            });
            //点击 按库位制单
            $(document).on('click', '#addByLocation', function () {
                _box_service.getLocationCode();
                _box_service.getAreaCode();
                $("#moveNodiv").hide();
                // $("#locationCode").val("10852");
                // $('#locationCode').chosen().trigger("chosen:updated");
                $("#time").val("");
                $("#stocktakingMode").val("");
                $(".fa-deviceInfoPop").hide();
                $("#formModel").modal({backdrop:false});
            });
            //点击 按商品制单
            $(document).on('click', '#addByGoods', function () {
                _box_service.getGoodsCode();
                _box_service.getAreaCode();
                $("#moveNodiv").hide();
                $("#time1").val("");
                $("#stocktakingMode1").val("");
                $(".fa-deviceInfoPop1").hide();
                $("#formModel1").modal({backdrop:false});
            });
            //点击 显示差异
            $(document).on('click', '#showDiff', function () {
                var monitorIdArr=[];
                $("#edgeBoxList tbody tr").each(function(index,item){
                    var _self=$(item);
                    if(_self.find('input[type="checkbox"]').is(':checked')){
                        var monitorId = _self.attr('orderNo');
                        monitorIdArr.push(monitorId);
                    }
                })
                var monitorStr=monitorIdArr.join(",");
                if(monitorIdArr.length <= 0){
                    $.DialogByZ.Autofade({Content:"请选择要查看差异的盘点任务！"});
                    return;
                }else if(monitorIdArr.length > 1){
                    $.DialogByZ.Autofade({Content:"只能选择一条盘点任务！"});
                    return;
                }
                window.location.href="wmsNHOrderCheckPalletList.html?orderNo=" + monitorStr;
            });
            //点击 立库启动出库【校验】
            $(document).on('click', '#startOut', function () {
                var orderNo;
                var monitorIdArr=[];
                $("#edgeBoxList tbody tr").each(function(index,item){
                    var _self=$(item);
                    if(_self.find('input[type="checkbox"]').is(':checked')){
                        var monitorId = _self.attr('orderNo');
                        orderNo = _self.attr('orderNo');
                        monitorIdArr.push(monitorId);
                    }
                })
                var monitorStr=monitorIdArr.join(",");
                if(monitorIdArr.length <= 0){
                    $.DialogByZ.Autofade({Content:"请选择要启动出库的盘点任务！"});
                    return;
                }else if(monitorIdArr.length > 1){
                    $.DialogByZ.Autofade({Content:"只能选择一条盘点任务！"});
                    return;
                }

                $("#orderNo").val(orderNo);

                var data= {
                    orderNo:orderNo
                };

                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/wmsHBOrderCheck/start/check",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    async   : false,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            _box_service.getOutAddress();
                            $("#outAddress").val("");
                            $("#remark2").val("");
                            $(".fa-deviceInfoPop2").hide();
                            $("#formModel2").modal({backdrop:false});
                        }else {
                            $.DialogByZ.Autofade({Content:result.message});
                            return;
                        }
                    },
                    error	: function () {
                        $.DialogByZ.Autofade({Content:"启动检测异常！"});
                        return;
                    }
                });
            });

            //弹窗保存【立库启动出库】
            $("#modalSaveButton2").on("click",function () {
                var orderNo = $("#orderNo").val();
                var outAddress = $("#outAddress").val();
                var remark = $("#remark2").val();

                if(outAddress==""){
                    $(".fa-deviceInfoPop2").show();
                    $(".fa-deviceInfoPop2").text(" 请选择盘点口！");
                    return false;
                }

                var data= {
                    orderNo:orderNo,
                    outAddress:outAddress,
                    remark:remark
                };

                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/wmsHBOrderCheck/start",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    async   : false,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            _edgeBox.data.flag = true;
                            // _commonFun.successTips(result.message);
                            $.DialogByZ.Autofade({Content: "启动成功！"});
                            _this.loadEdgeBoxs();
                        }else {
                            _edgeBox.data.flag = false;
                            $.DialogByZ.Autofade({Content:result.message});
                        }
                    },
                    error	: function () {
                        _edgeBox.data.flag = false;
                        // _commonFun.errorTips({Content:result.message});
                        $.DialogByZ.Autofade({Content: result.message});
                    }
                });
                return _edgeBox.data.flag;
            })
            //弹窗关闭
            $(document).on('click' ,'#modalCancelButton', function (e) {
                $("#time").val("");
                $("#stocktakingMode").val("");
                $("#remark").val("");
                $(".fa-deviceInfoPop").hide();
                // $(".modal").hide();
            })
            //弹窗关闭
            $(document).on('click' ,'#modalCancelButton1', function (e) {
                $("#time1").val("");
                $("#stocktakingMode1").val("");
                $("#remark1").val("");
                $(".fa-deviceInfoPop").hide();
                // $(".modal").hide();
            })

            //弹窗保存【按库位制单】
            $("#modalSaveButton").on("click",function () {
                var areaCode = $("#areaCode").val();
                var locationCode = $("#locationCode").val();
                var time = $("#time").val();
                var stocktakingMode = $("#stocktakingMode").val();
                console.log("方式："+stocktakingMode);
                var remark = $("#remark").val();

                if(areaCode==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请选择库区！");
                    return false;
                }
                if(stocktakingMode==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请选择盘点方式！");
                    return false;
                }
                // if(time==""){
                //     $(".fa-deviceInfoPop").show();
                //     $(".fa-deviceInfoPop").text(" 请选择时间！");
                //     return false;
                // }
                // if(locationCode=="" || locationCode==null){
                //     $(".fa-deviceInfoPop").show();
                //     $(".fa-deviceInfoPop").text(" 请选择库位！");
                //     return false;
                // }

                var data= {
                    areaCode:areaCode,
                    time:time,
                    // 单据类型 1按库位 2按商品
                    orderType:'1',
                    //添加-1：线上 2：线下
                    userDefined2:stocktakingMode,
                    locationCode:locationCode == null ? "" :locationCode.toString(),
                    remark:remark
                };

                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/wmsHBOrderCheck/c",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    async   : false,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            _edgeBox.data.flag = true;
                            _commonFun.successTips(result.message);
                            _this.loadEdgeBoxs();
                        }else {
                            _edgeBox.data.flag = false;
                            $.DialogByZ.Autofade({Content:result.message});
                        }
                    },
                    error	: function () {
                        _edgeBox.data.flag = false;
                        _commonFun.errorTips({Content:result.message});
                    }
                });
                return _edgeBox.data.flag;
            })

            //弹窗保存【按商品制单】
            $("#modalSaveButton1").on("click",function () {
                var areaCode = $("#areaCode1").val();
                var time = $("#time1").val();
                var stocktakingMode = $("#stocktakingMode1").val();
                var goodsCode = $("#goodsCode").val();
                var remark = $("#remark").val();

                if(areaCode==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请选择库区！");
                    return false;
                }
                if(stocktakingMode==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请选择盘点方式！");
                    return false;
                }
                // if(time==""){
                //     $(".fa-deviceInfoPop").show();
                //     $(".fa-deviceInfoPop").text(" 请选择时间！");
                //     return false;
                // }
                // if(goodsCode=="" || goodsCode==null){
                //     $(".fa-deviceInfoPop").show();
                //     $(".fa-deviceInfoPop").text(" 请选择商品！");
                //     return false;
                // }

                var data= {
                    areaCode:areaCode,
                    time:time,
                    // 单据类型 1按库位 2按商品
                    orderType:'2',
                    //添加-1：线上 2：线下
                    userDefined2:stocktakingMode,
                    goodsCode:goodsCode == null ? "" :goodsCode.toString(),
                    remark:remark
                };

                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/wmsHBOrderCheck/c",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    async   : false,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            _edgeBox.data.flag = true;
                            _commonFun.successTips(result.message);
                            _this.loadEdgeBoxs();
                        }else {
                            _edgeBox.data.flag = false;
                            $.DialogByZ.Autofade({Content:result.message});
                        }
                    },
                    error	: function () {
                        _edgeBox.data.flag = false;
                        _commonFun.errorTips({Content:result.message});
                    }
                });
                return _edgeBox.data.flag;
            })

            //全选/全不选 checkbox
            $(document).on('click', '#check-all', function () {
                var _self=$(this);
                if(_self.is(':checked')){
                    //全选中设置
                    $("#edgeBoxList tbody tr").each(function(index,item){
                        $(item).find('input[type="checkbox"]').prop("checked","checked");
                    })
                }
                else{
                    //全不选设置
                    $("#edgeBoxList tbody tr").each(function(index,item){
                        $(item).find('input[type="checkbox"]').prop("checked", false);
                    })
                }
            })
            //有一个未选中则取消全选  /  全都选中则全选选中
            $(document).on('click', '#edgeBoxList tbody input[type="checkbox"]', function (e) {
                var isCheckAll=true;
                if(!$(this).is(':checked')){
                    //有一个没选中的就取消全选
                    $("#check-all").prop("checked", false);
                }
                else{
                    $("#edgeBoxList tbody tr").each(function(index,item){
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
            //删除盘点任务
            $(document).on('click', '.a-equipment-delete', function () {
                var _self=$(this);
                var monitorId = _self.parent().parent().attr('id');
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除该行盘点任务吗？",
                    FunL:function(){
                        _box_service.deleteEdgeBoxInfo(monitorId, function (res) {
                            var result=res;
                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "删除成功！"});
                                $("#check-all").prop("checked", false);
                                _this.loadEdgeBoxs();
                            }
                            else {
                                $.DialogByZ.Autofade({Content: result.message || "删除失败！"});
                            }
                        }, function (err) {
                            _commonFun.errorTips('');
                        })
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            });
            //根据盘点单号删除盘点任务
            $(document).on('click', '.multiDelete', function () {
                var monitorIdArr=[];
                $("#edgeBoxList tbody tr").each(function(index,item){
                    var _self=$(item);
                    if(_self.find('input[type="checkbox"]').is(':checked')){
                        var monitorId = _self.attr('orderNo');
                        monitorIdArr.push(monitorId);
                    }
                })
                var monitorStr=monitorIdArr.join(",");
                if(monitorIdArr.length <= 0){
                    $.DialogByZ.Autofade({Content:"请选择要删除的盘点任务！"});
                    return;
                }else if(monitorIdArr.length > 1){
                    $.DialogByZ.Autofade({Content:"只能选择一条盘点任务！"});
                    return;
                }

                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要根据盘点单号删除盘点任务吗？",
                    FunL:function(){
                        _box_service.deleteEdgeBoxInfo1(monitorStr, function (res) {
                            var result=res;
                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "删除成功！"});
                                $("#check-all").prop("checked", false);
                                _this.loadEdgeBoxs();
                            }else {
                                // 启动失败
                                $.DialogByZ.Autofade({Content: result.message});
                                _this.loadEdgeBoxs();
                            }
                        }, function (err) {
                            _commonFun.errorTips('');
                        })
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            });
        },

        //EdgeBox列表
        loadEdgeBoxs: function () {
            var _this      =this;
            var equipment  ={
                orderNo    : $.trim($("#orderNo_search").val()),
                orderStatus    : $.trim($("#orderStatusSelect").val()),
                orderType    : $.trim($("#orderTypeSelect").val()),
                page     : $.trim($(".currentPage").text()),
                orderBy         : _this.data.orderBy,
                rows   : _this.data.numberOnePage
            }
            _box_service.getEdgeBoxs(equipment,function (res) {
                var result=res;
                if(result.code==0){
                    var records=result.data.records;
                    var totalCount=result.data.totalCount;
                    if(totalCount == 0){
                       /* $.DialogByZ.Autofade({Content: "查无数据！"});
                        $("#edgeBoxList tbody").empty();*/
                        $(".pagination-con").hide();
                    }else{
                        _this.loadEdgeBoxsHtml(records);
                        if(totalCount>_this.data.numberOnePage){
                            $(".pagination-con").show();
                            _this.loadPagingNumber(totalCount);
                        }
                        else {
                            $(".pagination-con").hide();
                        }
                    }
                }
                else {
                    $.DialogByZ.Autofade({Content: "查无数据！"});
                    $("#edgeBoxList tbody").empty();
                    $(".pagination-con").hide();
                }
            }, function (err) {
                _commonFun.errorTips('');
                $("#edgeBoxList tbody").empty();
            });
        },
        //渲染EdgeBox列表table
        loadEdgeBoxsHtml: function (list) {
            $("#edgeBoxList tbody").empty();
            var orderTypeText = '';
            var orderStatusText = '';
            var diffFlagText = '';
            var userDefined2Text = '';
            var str = '';
            for (var ele in list) {
                var orderStatus = list[ele].orderStatus;
                if(orderStatus =="1"){
                    orderStatusText = "创建";
                }else if(orderStatus =="2"){
                    orderStatusText = "盘点中";
                }else if(orderStatus =="3"){
                    orderStatusText = "完成";
                }else if(orderStatus =="4"){
                    orderStatusText = "异常";
                }

                var orderType = list[ele].orderType;
                if(orderType =="1"){
                    orderTypeText = "按库位";
                }else if(orderType =="2"){
                    orderTypeText = "按商品";
                }

                var diffFlag = list[ele].diffFlag;
                if(diffFlag =="1"){
                    diffFlagText = "是";
                }else if(diffFlag =="0"){
                    diffFlagText = "否";
                }
                var userDefined2 = list[ele].userDefined2;
                if(userDefined2 =="1"){
                    userDefined2Text = "线上";
                }else if(userDefined2 =="2"){
                    userDefined2Text = "线下";
                }

                // str+='<tr id="'+list[ele].locationId+'" locationCode="'+list[ele].locationCode+'">' +
                str+='<tr id="'+list[ele].checkId+'" orderNo="'+list[ele].orderNo+'"orderStatus="'+list[ele].orderStatus+'"batchNo="'+list[ele].batchNo+'"moveNo="'+list[ele].moveNo+'"fromLocationCode="'+list[ele].fromLocationCode+'"toLocationCode="'+list[ele].toLocationCode+'">' +
                    '<td><div class="checkbox-custom checkbox-default"><input type="checkbox" >' +
                    '<label></label></div></td>'+
                    '<td class="orderNo">'+list[ele].orderNo+'</td>'+
                    '<td class="orderType">'+orderTypeText+'</td>'+
                    '<td class="orderStatus">'+orderStatusText+'</td>'+
                    '<td class="areaCode ">'+list[ele].areaCode+'</td>'+
                    '<td class="goodsName ">'+list[ele].goodsName+'</td>'+
                    '<td class="goodsCode ">'+list[ele].goodsCode+'</td>'+
                    '<td class="locationCode">'+list[ele].locationCode+'</td>'+
                    '<td class="planPalletAmount">'+list[ele].planPalletAmount+'</td>'+
                    '<td class="planPalletAmount">'+list[ele].realPalletAmount+'</td>'+
                    '<td class="planPalletAmount">'+list[ele].planGoodsAmount+'</td>'+
                    '<td class="planPalletAmount">'+list[ele].realGoodsAmount+'</td>'+
                    '<td class="diffFlag">'+diffFlagText+'</td>'+
                    '<td class="userDefined2">'+userDefined2Text+'</td>'+
                    '<td class="gmtCreate">'+list[ele].gmtCreate+'</td>';

                str+='<td>';
                if(list[ele].orderStatus == 1){
                    // str += '<a class="link a-equipment-edit"><i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    str += '<a class="link a-equipment-delete" > <i class="fa-table-line-delete"  title="删除"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                }
                str += '</td></tr>';
            }
            $("#edgeBoxList tbody").html(str);
        },
        //多选select 数据同步
        chose_get_ini : function (select){
            $(select).chosen().change(function(){$(select).trigger("liszt:updated");});
        },
        //select value获取
        chose_get_value:function (select){
            return $(select).val();
        },
        //select text获取，多选时请注意
        chose_get_text:function (select){
            return $(select+" option:selected").text();
        },
        initDatetime :function  () {
            $(".datainp").jeDate({
                festival: false,
                ishmsVal: false,
                initAddVal:[0],
                format: "YYYY-MM-DD",
                zIndex: 3000
            });
        },
        //加载分页
        loadPagingNumber: function(num) {
            parseInt(num%this.data.numberOnePage)==0 ?
                this.data.totalPageNum=parseInt(num/this.data.numberOnePage) :
                this.data.totalPageNum=parseInt(num/this.data.numberOnePage)+1;

            $(".dataNumber").html(num);//共多少条
            $(".pageNumber").html(this.data.totalPageNum);//共多少页
            $(".pageOneNumber").text(this.data.numberOnePage);//每页几条
        }
    };
    _edgeBox.init();
});


function editSelect(orderNo) {
    window.location.href="addNHOrderOutStereoscopic.html?orderNo="+orderNo;
}

function editOneRecord(locationCode) {
    // alert(orderNo);
    // window.location.href="addNHOrderOutStereoscopic.html?orderNo="+orderNo+','+type;
    // $("#addDevicePop").modal();
    // $("#addDevicePop").modal({backdrop:false});
}

//删除出库单
function deleteOneRecord(orderNo) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要删除出库任务?",
        FunL:function(){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/wmsOrderOutStereoscopic/delete",
                headers:{'token':localStorage.getItem('token')},
                data    : {orderNo:orderNo},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        _commonFun.successTips(result.data);
                        _edgeBox.loadEdgeBoxs();
                    }
                },
                error	: function (res) {
                    _commonFun.errorTips('');
                }
            });
        },
        FunR:function() {
            $.DialogByZ.Close();
        }
    });
}
