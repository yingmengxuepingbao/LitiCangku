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
        // 获取设备类型
        getSelect : function(resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/dictItem/getDictType/device_type",
                headers:{'token':localStorage.getItem('token')},
                data    : { code :"platform"},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var options = '';
                        options +='<option value="">请选择设备类型</option>'
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.dicItemCode+'">'+ele.dicItemName+'</option>'
                        }

                        $("#deviceTypeSelect").html(options);
                        $("#deviceType").html(options);
                    }
                },
                error	: reject
            });
        },
        getLocationCode : function(resolve, reject){
            var _this=this;
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/location/getLocationCodeList",
                headers:{'token':localStorage.getItem('token')},
                // 0可用1入库中2出库中3占用4异常
                data    : { useStatus :""},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var data=result.data;
                        $("#fromLocationCode").html("");
                        var commonDevice0 = "<option value=''>请选择源库位</option>";
                        var device = "";
                        for (var i = 0; i <data.length; i++) {
                            var locationCode = data[i].locationCode;
                            var palletCode = data[i].palletCode;
                            var batchNo = data[i].batchNo;
                            var goodsName = data[i].goodsName;
                            var goodsCode = data[i].goodsCode;
                            if(palletCode == "" || palletCode=="undefined"){
                                palletCode = "''";
                            }
                            if(batchNo == "" || batchNo=="undefined"){
                                batchNo = "''";
                            }
                            if(goodsName == "" || goodsName=="undefined"){
                                goodsName = "''";
                            }
                            if(goodsCode == "" || goodsCode=="undefined"){
                                goodsCode = "''";
                            }
                            device += "<option value=" + locationCode + " palletCode=" + palletCode + " batchNo=" + batchNo + " goodsCode=" + goodsCode + " goodsName="+goodsName+">" + data[i].locationCode + "</option>";
                        }
                        console.log("device:" + device)
                        $("#fromLocationCode").html(commonDevice0 + device);

                        $("#fromLocationCode").trigger("liszt:updated");
                    }
                },
                error	: reject
            });
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/location/getLocationCodeList",
                headers:{'token':localStorage.getItem('token')},
                // 0可用1入库中2出库中3占用4异常
                data    : { useStatus :""},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var data=result.data;
                        $("#toLocationCode").html("");
                        var commonDevice1 = "<option value=''>请选择目的库位</option>";
                        var device = "";
                        for (var i = 0; i <data.length; i++) {
                            var locationCode = data[i].locationCode;
                            var palletCode = data[i].palletCode;
                            var batchNo = data[i].batchNo;
                            var goodsName = data[i].goodsName;
                            if(palletCode == "" || palletCode=="undefined"){
                                palletCode = "''";
                            }
                            if(batchNo == "" || batchNo=="undefined"){
                                batchNo = "''";
                            }
                            if(goodsName == "" || goodsName=="undefined"){
                                goodsName = "''";
                            }
                            device += "<option value=" + locationCode + " palletCode=" + palletCode + " batchNo=" + batchNo + " goodsName="+goodsName+">" + data[i].locationCode + "</option>";
                        }
                        console.log("device:" + device)
                        $("#toLocationCode").html(commonDevice1 + device);

                        $("#toLocationCode").trigger("liszt:updated");
                    }
                },
                error	: reject
            });
        },
        //根据筛选条件获取EdgeBox列表
        getEdgeBoxs: function(edgeBoxInfo,resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/wcsAgvTaskPlane/list",
                headers:{'token':localStorage.getItem('token')},
                data	: edgeBoxInfo,
                success	: resolve,
                error	: reject
            });
        },
        //删除出库单
        deleteEdgeBoxInfo: function(id,resolve, reject){
            var data = {
                errId  :  id
            }
            $.ajax({
                type		:"post",
                url		: $.mpbGetHeaderPath()+"/wcsErrorCodeService/d",
                headers:{'token':localStorage.getItem('token')},
                data		: data,
                success		: resolve,
                error		: reject
            });
        },
        //启动出库任务
        disableEdgeBoxInfo: function(moveId,resolve, reject){
            console.log("moveId" + moveId)
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
            // _box_service.getSelect();//加载下拉框
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
            $(document).on('change', '#fromLocationCode', function (e){
                var _self=$(this);
                var palletCode=$("#fromLocationCode option:selected").attr("palletCode");
                var goodsCode=$("#fromLocationCode option:selected").attr("goodsCode");
                var goodsName=$("#fromLocationCode option:selected").attr("goodsName");
                var batchNo=$("#fromLocationCode option:selected").attr("batchNo");
                $("#palletCode").val(palletCode);
                $("#goodsCode").val(goodsCode);
                $("#goodsName").val(goodsName);
                $("#batchNo").val(batchNo);
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
            //搜索文本框-商品编码
            $("#taskNo_search").bind("input propertychange", function () {
                $(".currentPage").text("1");
                $("#check-all").prop("checked", false);
                _this.loadEdgeBoxs();
            });

            //搜索 任务状态
            $("#taskStatus_search").bind("input propertychange", function () {
                $(".currentPage").text("1");
                $("#check-all").prop("checked", false);
                _this.loadEdgeBoxs();
            });
            //搜索文本框-托盘编码
            $("#palletCode_search").bind("input propertychange", function () {
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
                $("#goodsCode_search").val("");
                $("#palletCode_search").val("");
                $("#taskStatus_search").val("");
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
            //点击添加
            $(document).on('click', '#addEquipment', function () {
                // _box_service.getLocationCode();
                $("#errId").val("");
                $("#errCode").val("");
                $("#description").val("");
                $("#deviceType").val("");
                $("#areaCode").val("");
                $(".fa-deviceInfoPop").hide();
                $("#formModel").modal({backdrop:false});
            });
            //编辑
            $(document).on('click', '.a-equipment-edit', function (e) {
                // 下拉框编辑 待研究
                e.stopPropagation();
                var _self = $(this);
                // _box_service.getLocationCode();
                var errId = _self.parent().parent().attr('id');
                $.ajax({
                    type	:"get",
                    url		: $.mpbGetHeaderPath()+"/wcsErrorCodeService/r/" + errId,
                    headers:{'token':localStorage.getItem('token')},
                    // 0可用1入库中2出库中3占用4异常
                    data    : {},
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            var data=result.data;

                            var areaCode = data.areaCode;
                            var errCode = data.errCode;
                            var description = data.description;
                            var deviceType = data.deviceType;

                            $("#errId").val(errId);
                            $("#errCode").val(errCode);
                            $("#description").val(description);
                            $("#deviceType").val(deviceType);
                            $("#areaCode").val(areaCode);
                        }
                    },
                    error	: {

                    }
                });
                $(".fa-deviceInfoPop").hide();
                $("#formModel").modal({backdrop:false});
            })
            //弹窗关闭
            $(document).on('click' ,'#modalCancelButton', function (e) {
                $("#errCode").val("");
                $("#description").val("");
                $("#deviceType").val("");
                $("#areaCode").val("");
                $(".fa-deviceInfoPop").hide();
                // $(".modal").hide();
            })
            //弹窗保存
            // $(document).on('click' ,'#modalSaveButton', function (e) {
            $("#modalSaveButton").on("click",function () {
                var errId = $("#errId").val();
                var errCode = $("#errCode").val();
                var description = $("#description").val();
                var deviceType = $("#deviceType").val();
                var areaCode = $("#areaCode").val();

                $(".fa-deviceInfoPop").hide();
                if(errCode==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请输入故障码！");
                    return false;
                }
                if(description==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请输入描述！");
                    return false;
                }
                if(deviceType==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请选择设备类型！");
                    return false;
                }

                var data= {
                    errId:errId,
                    errCode:errCode,
                    description:description,
                    deviceType:deviceType,
                    areaCode:areaCode
                };

                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/wcsErrorCodeService/c",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    async   : false,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            _edgeBox.data.flag = true;
                            _commonFun.successTips(result.data);
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
            //删除EdgeBox采集终端
            $(document).on('click', '.a-equipment-delete', function () {
                var _self=$(this);
                var monitorId = _self.parent().parent().attr('id');
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除故障码吗？",
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
            //启动移库任务
            $(document).on('click', '.a-equipment-disable', function () {
                var _self=$(this);
                var moveId = _self.parent().parent().attr('id');
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要启动该出库任务吗？",
                    FunL:function(){
                        _box_service.disableEdgeBoxInfo(moveId, function (res) {
                            // console.log(res)
                            var result=res;
                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "启动成功！"});
                                $("#check-all").prop("checked", false);
                                _this.loadEdgeBoxs();
                            }
                            else {
                                $.DialogByZ.Autofade({Content: result.message});
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
            //启动按钮
            $(document).on('click', '.multiDelete', function () {
                var monitorIdArr=[];
                $("#edgeBoxList tbody tr").each(function(index,item){
                    var _self=$(item);
                    if(_self.find('input[type="checkbox"]').is(':checked')){
                        var monitorId = _self.attr('locationCode');
                        monitorIdArr.push(monitorId);
                    }
                })
                var monitorStr=monitorIdArr.join(",");
                if(monitorIdArr.length>0){
                    $.DialogByZ.Confirm({
                        Title: "操作提示",
                        Content: "确定要批量启动出库任务吗？",
                        FunL:function(){
                            _box_service.deleteEdgeBoxInfo(monitorStr, function (res) {
                                var result=res;
                                if(result.code==0) {
                                    $.DialogByZ.Autofade({Content: "启动成功！"});
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
                }
                else {
                    $.DialogByZ.Autofade({Content:"请选择要启动的出库任务！"});
                }
            });
        },

        //EdgeBox列表
        loadEdgeBoxs: function () {
            // _box_service.getLocationCode();
            var _this      =this;
            var equipment  ={
                taskNo    : $.trim($("#taskNo_search").val()),
                palletCode    : $.trim($("#palletCode_search").val()),
                taskStatus    : $.trim($("#taskStatus_search").val()),
                page     : $.trim($(".currentPage").text()),
                orderBy         : _this.data.orderBy,
                rows   : _this.data.numberOnePage
            }
            _box_service.getEdgeBoxs(equipment,function (res) {
                var result=res;
                if(result.code==0){
                    var records=result.data.infoList;
                    var totalCount=result.data.totalNumber;
                    _this.loadEdgeBoxsHtml(records);
                    if(totalCount>_this.data.numberOnePage){
                        $(".pagination-con").show();
                        _this.loadPagingNumber(totalCount);
                    }
                    else {
                        $(".pagination-con").hide();
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
            var str = '';
            for (var ele in list) {
                var taskStatusStr = "";
                var taskStatus = list[ele].taskStatus;
                if(taskStatus =="1"){
                    taskStatusStr = "创建";
                }else if(taskStatus =="2"){
                    taskStatusStr = "下发";
                }else if(taskStatus =="3"){
                    taskStatusStr = "到达缓冲区";
                }else if(taskStatus =="4"){
                    taskStatusStr = "等待";
                }else if(taskStatus =="5"){
                    taskStatusStr = "继续入库";
                }else if(taskStatus =="6"){
                    taskStatusStr = "完成";
                }else if(taskStatus =="7"){
                    taskStatusStr = "取消";
                }else if(taskStatus =="21"){
                    taskStatusStr = "下发失败";
                }else if(taskStatus =="51"){
                    taskStatusStr = "继续入库任务失败";
                }

                str+='<tr id="'+list[ele].id+'">' +
                    // '<td><div class="checkbox-custom checkbox-default"><input type="checkbox" >' +
                    // '<label></label></div></td>'+
                    '<td class="agvTaskId">'+list[ele].agvTaskId+'</td>'+
                    '<td class="taskNo">'+list[ele].taskNo+'</td>'+
                    '<td class="palletCode">'+list[ele].palletCode+'</td>'+
                    '<td class="taskStatus">'+taskStatusStr+'</td>'+
                    '<td class="pathId">'+list[ele].pathId+'</td>'+
                    '<td class="gmtEnd">'+list[ele].gmtCreate+'</td>';

                // if(list[ele].moveStatus == 1){
                //     str += '<a class="link a-equipment-edit"><i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                //     str += '<a class="link a-equipment-delete" > <i class="fa-table-line-delete"  title="删除"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    // str += '<a class="link a-equipment-disable"> <i class="fa-table-line-submit" title="启动"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                // }
                str += '</tr>';
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
    window.location.href="addOrderOutStereoscopic.html?orderNo="+orderNo;
}

function editOneRecord(locationCode) {
    // alert(orderNo);
    // window.location.href="addOrderOutStereoscopic.html?orderNo="+orderNo+','+type;
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
