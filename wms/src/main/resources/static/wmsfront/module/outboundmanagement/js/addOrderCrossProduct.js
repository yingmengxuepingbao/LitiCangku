var _addEquipment;
$(function(){
    _edgeBox_service={



        //根据id获取设备采集终端信息
        getEdgeBoxById : function(orderNo, resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/orderCrossProductDetail/queryByOrderNo/"+orderNo,
                headers:{'token':localStorage.getItem('token')},
                data    : {
                    orderNo  : orderNo
                },
                success	: resolve,
                error	: reject
            });
        },
        //获取未配置EdgeBox的采集终端列表
        getUnusedEdgeBoxMonitors : function(resolve, reject){
            $.ajax({
                type	: "get",
                url     : $.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                success : resolve,
                error   : reject
            });
        },
        getAvailableGoods : function(data, reject){
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
    };
    //-----------------类别树代码-begin----------------------


    var treeObj="",loadNumber=0,interval="";
//-----------------类别树代码-end----------------------
     _addEquipment= {
        data: {
            deviceId :"",  //从列表传过来的
            deviceName :"",  //从列表传过来的
            equipmentId       : "",
            currentNumber   : 0,  //记录当前页有多少条数据
            totalPageNum    : 0,  //记录一共多少页
            numberOnePage   : 8, //一页几条数据
            equipments_id   : "",
            equipments_name :"",
            monitorsList    :[], //存储未配置设备列表 用于提交的
            monitorsUnusedList:[] ,//获取未配置设备的采集终端列表 用于chosen 下拉用
            isSaveBeforeDevice : true ,//标志 ， 是否保存了之前编辑的采集终端，保存 true，未保存 false
            codeUnused         : "",//未分配采集终端保存code 用
            idBeforeEdit : ""   //编辑之前的终端ID
        },
        init: function () {
            this.onLoad();
            this.bindEvent();
        },
        onLoad: function () {
            this.loadEquipmentInfo();//加载设备信息
        },
        bindEvent: function () {
            var _this = this;
            //商品选择
            $(document).on('change', '#goodsCode', function () {
                var goodsCode = $.trim($("#goodsCode").val());

                // findDevices(deptId);
                console.log("goodsCode",goodsCode);
                // _edgeBox_service.findBatchNo(goodsCode);
                $("#availableAmount").val("");
            })


            //第二个tab新增未配置采集终端
            $(document).on("click", "#addUnusedDevice", function () {
                //供应商
                // _buz_service.getSupplyList();
                // _buz_service.getWarehouseCode();
                _edgeBox_service.getAvailableGoods();
                //备件确认人
                // $("#confirmPerson").prop("disabled",false);
                //显示 提交按钮和启用下拉框和 input
                $("#saveButton").show().text("保存");
                $("#formInfo").find("i").show();
                $("form  select").prop("disabled",false);
                $("form  input").prop("disabled",false);
                $("#orderNo").val(_this.data.orderNo);


                //路线编码默认不可编辑
                $("#orderNo").prop("disabled",true);
                // $("#availableAmount").val("");
                // $("#availableAmount").prop("disabled",true);
                $("#orderCrossProductId").val("");
                $("#warehouseCode").val("");
                $("#areaCode").val("");

                $("#goodsCode").val("");
                $("#batchNo").val("");
                // $("#locationCode").val("");
                $("#palletCode").val("");
                $("#planAmount").val("");
                $("#outSeq").val("");
                $("#layerNumber").val("");
                $("#columnNumber").val("");
                $("#useStatus").val("");
                $("#myModalLabel").text('新增出库明细');
                $("#formModel").modal({backdrop:false});

            })

            //新增或更新信息
            $("#saveButton").on("click",function () {
                var orderCrossProductDetailId = $.trim($("#orderCrossProductDetailId").val());
                console.log("orderCrossProductDetailIdxxxxx",orderCrossProductDetailId);
                var orderNo = $.trim($("#orderNo").val());
                var goodsCode = $.trim($("#goodsCode").val());
                var batchNo = $.trim($("#batchNo").val());
                var planAmount = $.trim($("#planAmount").val());
                var remark = $.trim($("#remark").val());

                //添加数据校验
                if(_commonFun.isBlank(orderNo)){
                    $.DialogByZ.Autofade({Content: "出库单号不能为空"});
                    return false;
                }
                if(_commonFun.isBlank(goodsCode)){
                    $.DialogByZ.Autofade({Content: "商品编码不能为空"});
                    return false;
                }
                if(_commonFun.isBlank(batchNo)){
                    $.DialogByZ.Autofade({Content: "批次号不能为空"});
                    return false;
                }

                if(_commonFun.isBlank(planAmount)){
                    $.DialogByZ.Autofade({Content: "计划数量不能为空"});
                    return false;
                }
                var data= {

                    orderNo:orderNo,
                    goodsCode:goodsCode,
                    batchNo:batchNo,
                    planAmount:planAmount,
                    remark:remark

                };

                //判断是新增还是修改
                if (!_commonFun.isNotBlank(orderCrossProductDetailId)){
                    data.orderCrossProductId =  _this.data.orderCrossProductId;
                    //不存在新增
                    $.ajax({
                        type	:"POST",
                        url		: $.mpbGetHeaderPath()+"/orderCrossProductDetail/c",
                        headers:{'token':localStorage.getItem('token')},
                        data    : data,
                        success	: function (res) {
                            var result=res;
                            if(result.code == 0){
                                _commonFun.successTips(result.data);
                                _this.loadEquipmentInfo();
                            }else {
                                $.DialogByZ.Autofade({Content:result.message || "新增失败！"});
                            }
                        },
                        error	: function () {
                            _commonFun.errorTips("错误");
                        }
                    });
                }else {
                    //存在id修改
                    data.orderCrossProductDetailId = orderCrossProductDetailId;
                    $.ajax({
                        type	:"POST",
                        url		: $.mpbGetHeaderPath()+"/orderCrossProductDetail/u",
                        headers:{'token':localStorage.getItem('token')},
                        data    : data,
                        success	: function (res) {
                            var result=res;
                            if(result.code == 0){
                                _commonFun.successTips(result.data);
                                _this.loadEquipmentInfo();
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
            //清空弹窗文本内容
            $(document).on('click', '.clearAll', function () {
                $("#equipmentCode").val("");
                $("#equipmentName").val("");
                $("#specification").val("");
                $("#type").val("");
                $("#serialNumber").val("");
                $("#purchaseTime").val("");
                $("#installTime").val("");
                $("#disableTime").val("");
                $("#location").val("");
            })
            //返回
            $(document).on('click', '.return', function (){
                _this.data.equipments_id        ="";
                _this.data.equipments_name      ="";
                _this.data.monitorsList         =[]; //存储未配置设备列表 用于提交的
                _this.data.monitorsUnusedList   =[];//获取未配置设备的采集终端列表 用于chosen 下拉用
                _this.data.isSaveBeforeDevice   = true ;
                console.log("wmsOrderCrossProductList");
                window.location.href="wmsOrderCrossProductList.html";
            })
        },
        //加载设备信息
        loadEquipmentInfo : function () {
            var _this = this;
            var req = GetRequest();

            var orderCrossProductId = req['orderCrossProductId'];
            console.log("orderCrossProductId",orderCrossProductId);
            _this.data.orderCrossProductId = orderCrossProductId;
            var orderNo = req["orderNo"];
            console.log("orderNo",orderNo);
            var orderStatus = req["orderStatus"];
            _this.data.orderStatus = orderStatus;
            $("#addUnusedDevice").css("display","");
            if(orderStatus!=1){
                $("#addUnusedDevice").css("display","none");
            }


            if(orderNo==-1) {
                _this.data.orderCrossProductDetailId = "";
                //新增
                $("#equipmentCode").val("");
                $("#equipmentName").val("");
                $("#specification").val("");
                $("#type").val("");
                $("#serialNumber").val("");
                $("#purchaseTime").val("");
                $("#installTime").val("");
                $("#disableTime").val("");
                $("#location").val("");
                $("#orderCrossProductDetailId").val("");
                $("#twoLog").empty();
            }
            else {//编辑
                $(".breadcrumb .active").text("出库明细");
                _this.data.orderNo = orderNo;
                // console.log(window.location.href.split("="));
                _edgeBox_service.getEdgeBoxById(_this.data.orderNo, function (res) {
                    var result=res;
                    if(result.code==0) {
                        var data=result.data;
                        interval=setInterval(function () {
                            console.log("data",data)
                            _this.equipmentInfoSource(data);
                        },10);
                    }
                    else {
                        $.DialogByZ.Autofade({Content: result.message ||  "查无数据！"});
                    }
                },function(err){
                    _commonFun.errorTips('');
                })
            }
        },
        //编辑采集终端获取的数据源 显示在页面
        equipmentInfoSource:function (data) {
            var _this=this;
            clearInterval(interval);
            _this.data.monitorsList     =data || [];
            console.log("_this.data.monitorsList",_this.data.monitorsList);
            _this.loadEquipmentUnusedList(_this.data.monitorsList);
        },
        //未配置设备的采集终端列表渲染
        loadEquipmentUnusedList:function (list) {
            console.log("list",list);
            if(list!=[]){
                $("#deviceUnusedList tbody").empty();
                var str= "";
                for(ele in list){
                    var edit = '<td><a onclick="editOneRecord(\''+list[ele].orderCrossProductDetailId+'\',1)"> <i class="fa-table-line-edit"  title="编辑"></i></a>&nbsp;';
                    var deleteA = '<td><a onclick="deleteOneRecord(\''+list[ele].orderCrossProductDetailId+'\')"><i class="fa-table-line-delete"  title="删除"></i></a></td>';
                    str+='<tr  data-id="'+list[ele].orderCrossProductDetailId+'">' +
                        '<td  class="orderNo">' + list[ele].orderNo + '</td>' +
                        '<td  class="goodsCode">' + list[ele].goodsCode + '</td>' +
                        '<td  class="goodsName">' + list[ele].goodsName + '</td>' +
                        '<td  class="batchNo">' + list[ele].batchNo + '</td>' +
                        // '<td  class="locationCode">' + list[ele].locationCode + '</td>' +
                        '<td  class="planAmount">' + list[ele].planAmount + '</td>' +
                        '<td  class="realAmount">' + list[ele].realAmount + '</td>'+
                        '<td  class="remark">' + list[ele].remark + '</td>'
                        if(this.data.orderStatus == "1"){
                            str+=edit
                            str+=deleteA
                        }
                    str+='<td><input type="hidden" class="hidUnusedVal" value="'+list[ele].orderCrossProductDetailId+'"/></td>' +
                        '</tr>';
                }
                $("#deviceUnusedList tbody").html(str);
            }
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
        }
    };
    //时间控件初始化
    function  initDatetime () {
        $(".datainp").jeDate({
            festival: false,
            ishmsVal: false,
            initAddVal:[0],
            format: "YYYY-MM-DD",
            zIndex: 3000
        });
    }
    initDatetime();

    _addEquipment.init();


});

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]]=strs[i].split("=")[1];
        }
    }
    return theRequest;
}
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
function editOneRecord(orderCrossProductDetailId,type) {
    var id = orderCrossProductDetailId;
    $("#type").val(type);
    //供应商
    _edgeBox_service.getAvailableGoods();
    //查看时，隐藏提交按钮 和禁用下拉框和input
    if(type == 0 ){
        $("#formInfo").find("i").hide();
        $("#myModalLabel").text('查看出库明细信息');
        $("#saveButton").hide();
        $("form  select").prop("disabled",true);
        $("form  input").prop("disabled",true);
    }else if (type == 1){
        $("#formInfo").find("i").show();
        $("#myModalLabel").text('编辑出库明细信息');
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
        url		: $.mpbGetHeaderPath()+"/orderCrossProductDetail/r/"+id,
        headers:{'token':localStorage.getItem('token')},
        data    : {id:id},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                if(data != null){
                    $("#orderCrossProductDetailId").val(data.orderCrossProductDetailId);
                    $("#orderNo").val(data.orderNo);
                    $("#goodsCode").val(data.goodsCode);
                    $("#batchNo").val(data.batchNo);
                    $("#planAmount").val(data.planAmount);
                    $("#remark").val(data.remark);

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

//删除
function deleteOneRecord(id) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要删除出库明细?",
        FunL:function(){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/orderCrossProductDetail/delete",
                headers:{'token':localStorage.getItem('token')},
                data    : {orderCrossProductDetailId:id},
                success	: function (res) {
                    var result=res;

                    if(result.code == 0){
                        _commonFun.successTips(result.message);
                        _addEquipment.loadEquipmentInfo();
                    }else {
                        _commonFun.errorTips(result.message);
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
