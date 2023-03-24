var _addEquipment;
$(function(){
    _edgeBox_service={



        //根据id获取设备采集终端信息
        getEdgeBoxById : function(taskId, resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/task/queryDetailByTaskId/"+taskId,
                headers:{'token':localStorage.getItem('token')},
                data    : {
                    taskId  : taskId
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
            //全选/全不选 checkbox
            $(document).on('click', '#check-all', function () {
                var _self=$(this);
                if(_self.is(':checked')){
                    //全选中设置
                    $("#deviceUnusedList tbody tr").each(function(index,item){
                        $(item).find('input[type="checkbox"]').prop("checked","checked");
                    })
                }
                else{
                    //全不选设置
                    $("#deviceUnusedList tbody tr").each(function(index,item){
                        $(item).find('input[type="checkbox"]').prop("checked", false);
                    })
                }
            })
            //有一个未选中则取消全选  /  全都选中则全选选中
            $(document).on('click', '#deviceUnusedList tbody input[type="checkbox"]', function (e) {
                var isCheckAll=true;
                if(!$(this).is(':checked')){
                    //有一个没选中的就取消全选
                    $("#check-all").prop("checked", false);
                }
                else{
                    $("#deviceUnusedList tbody tr").each(function(index,item){
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
            //商品选择
            $(document).on('change', '#goodsCode', function () {
                var goodsCode = $.trim($("#goodsCode").val());

                // findDevices(deptId);
                console.log("goodsCode",goodsCode);
                // _edgeBox_service.findBatchNo(goodsCode);
                $("#availableAmount").val("");
            })
            //changeTaskStatus
            $(document).on("click", "#changeTaskStatus", function () {
                var monitorIdArr=[];
                var taskForm = '';
                $("#deviceUnusedList tbody tr").each(function(index,item){
                    var _self=$(item);
                    if(_self.find('input[type="checkbox"]').is(':checked')){

                        // console.log("_self.parent().parent()",_self.parent().parent())
                        var monitorId = _self.data('id');
                        console.log(_self.attr('data-taskForm'));
                        taskForm =  _self.attr('data-taskForm');
                            // _self.parent().parent().data('taskForm');
                        // console.log("taskForm",taskForm)
                        monitorIdArr.push(monitorId);
                    }
                })
                if(monitorIdArr.length <= 0){
                    $.DialogByZ.Autofade({Content:"请选择要更改的任务！"});
                    return;
                }else if(monitorIdArr.length > 1){
                    $.DialogByZ.Autofade({Content:"只能选择一条任务！"});
                    return;
                }
                var monitorStr=monitorIdArr.join(",");
                console.log("monitorStr",monitorStr);
                //供应商
                // _buz_service.getSupplyList();
                // _buz_service.getWarehouseCode();
                // _edgeBox_service.getAvailableGoods();
                //备件确认人
                // $("#confirmPerson").prop("disabled",false);
                //显示 提交按钮和启用下拉框和 input
                $("#saveButton").show().text("保存");
                $("#formInfo").find("i").show();
                $("form  select").prop("disabled",false);
                $("form  input").prop("disabled",false);
                $("#taskForm").val(taskForm);
                $("#taskId").val("");
                $("#taskId").val(monitorStr);

                //路线编码默认不可编辑
                $("#taskForm").prop("disabled",true);

                $("#taskStatus").val("");
                $("#myModalLabel").text('更改任务状态');
                $("#formModel").modal({backdrop:false});

            })

            //新增或更新信息
            $("#saveButton").on("click",function () {

                var taskId = $.trim($("#taskId").val());
                var taskForm = $.trim($("#taskForm").val());
                console.log("taskForm",taskForm);
                var taskStatus = $.trim($("#taskStatus").val());
                // var goodsCode = $.trim($("#goodsCode").val());
                // var planAmount = $.trim($("#planAmount").val());
                // var remark = $.trim($("#remark").val());

                //添加数据校验
                if(_commonFun.isBlank(taskForm)){
                    $.DialogByZ.Autofade({Content: "任务类型不能为空"});
                    return false;
                }
                if(_commonFun.isBlank(taskStatus)){
                    $.DialogByZ.Autofade({Content: "任务状态不能为空"});
                    return false;
                }


                var data= {

                    taskId:taskId,
                    taskForm:taskForm,
                    taskStatus:taskStatus
                };
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/task/changeTaskStatus",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            _commonFun.successTips(result.data);
                            _this.loadEquipmentInfo();
                        }else {
                            _commonFun.errorTips(result.message || "修改失败！");
                            // $.DialogByZ.Autofade({Content:result.message || "修改失败！"});
                        }
                    },
                    error	: function () {
                        _commonFun.errorTips("错误");
                    }
                });

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
                window.location.href="wcsTaskExecutionList.html";
            })
        },
        //加载设备信息
        loadEquipmentInfo : function () {
            var _this = this;
            var req = GetRequest();

            var orderCrossInId = req['orderCrossInId'];
            console.log("orderCrossInId",orderCrossInId);
            _this.data.orderCrossInId = orderCrossInId;
            var taskId = req["taskId"];
            console.log("taskId",taskId);
            var orderStatus = req["orderStatus"];
            _this.data.orderStatus = orderStatus;
            console.log("orderStatus",orderStatus);



            if(taskId==-1) {
                _this.data.orderCrossInDetailId = "";
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
                $("#orderCrossInDetailId").val("");
                $("#twoLog").empty();
            }
            else {//编辑
                $(".breadcrumb .active").text("任务详情");
                _this.data.taskId = taskId;
                // console.log(window.location.href.split("="));
                _edgeBox_service.getEdgeBoxById(_this.data.taskId, function (res) {
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
                    var taskStatusText="";
                    var taskStatus = list[ele].taskStatus;
                    if(taskStatus =="1"){
                        taskStatusText = "创建";
                    }else if(taskStatus =="2"){
                        taskStatusText = "申请任务";
                    }else if(taskStatus =="3"){
                        taskStatusText = "执行任务（下发）";
                    }else if(taskStatus =="4"){
                        taskStatusText = "执行";
                    }else if(taskStatus =="5"){
                        taskStatusText = "取货完成";
                    }else if(taskStatus =="6"){
                        taskStatusText = "任务完成";
                    }else if(taskStatus =="21"){
                        taskStatusText = "申请失败";
                    }else if(taskStatus =="31"){
                        taskStatusText = "下发失败";
                    }

                    var taskAgvStatusText="";
                    var taskAgvStatus = list[ele].taskAgvStatus;
                    if(taskAgvStatus =="1"){
                        taskAgvStatusText = "创建";
                    }else if(taskAgvStatus =="2"){
                        taskAgvStatusText = "下发";
                    }else if(taskAgvStatus =="3"){
                        taskAgvStatusText = "到达缓冲区";
                    }else if(taskAgvStatus =="4"){
                        taskAgvStatusText = "等待";
                    }else if(taskAgvStatus =="5"){
                        taskAgvStatusText = "继续入库";
                    }else if(taskAgvStatus =="6"){
                        taskAgvStatusText = "完成";
                    }else if(taskAgvStatus =="7"){
                        taskAgvStatusText = "取消";
                    }else if(taskAgvStatus =="21"){
                        taskAgvStatusText = "下发失败";
                    }else if(taskAgvStatus =="51"){
                        taskAgvStatusText = "继续入库任务失败";
                    }
                    var edit = '<td><a onclick="editOneRecord(\''+list[ele].orderCrossInDetailId+'\',1)"> <i class="fa-table-line-edit"  title="编辑"></i></a>&nbsp;';
                    var deleteA = '<td><a onclick="deleteOneRecord(\''+list[ele].orderCrossInDetailId+'\')"><i class="fa-table-line-delete"  title="删除"></i></a></td>';
                    str+='<tr  data-id="'+list[ele].taskId+'" data-taskForm ="'+list[ele].taskForm+'">' +
                        '<td><div class="checkbox-custom checkbox-default"><input type="checkbox" >' +
                        '<label></label></div></td>'+
                        '<td  class="taskId">' + list[ele].taskId + '</td>' +
                        '<td  class="taskNo">' + list[ele].taskNo + '</td>' +
                        '<td  class="taskForm">' + list[ele].taskForm + '</td>' +
                        '<td  class="actionType">' + list[ele].actionType + '</td>' +
                        '<td  class="locationId">' + list[ele].locationId + '</td>' +
                        '<td  class="pathId">' + list[ele].pathId + '</td>' +
                        // '<td  class="gmtStart">' + list[ele].gmtStart + '</td>'+
                        '<td  class="gmtEnd">' + list[ele].gmtEnd + '</td>'+
                        '<td  class="taskAgvStatus">' + taskAgvStatusText+ '</td>'+
                        '<td  class="taskStatus">' + taskStatusText+ '</td>'+

                        '<td  class="gmtCreate">' + list[ele].gmtCreate + '</td>'
                        // if(this.data.orderStatus == "1"){
                        //     str+=edit
                        //     str+=deleteA
                        // }
                    str+='<td><input type="hidden" class="hidUnusedVal" value="'+list[ele].orderCrossInDetailId+'"/></td>' +
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
function editOneRecord(orderCrossInDetailId,type) {
    var id = orderCrossInDetailId;
    $("#type").val(type);
    //供应商
    _edgeBox_service.getAvailableGoods();
    //查看时，隐藏提交按钮 和禁用下拉框和input
    if(type == 0 ){
        $("#formInfo").find("i").hide();
        $("#myModalLabel").text('查看任务详情信息');
        $("#saveButton").hide();
        $("form  select").prop("disabled",true);
        $("form  input").prop("disabled",true);
    }else if (type == 1){
        $("#formInfo").find("i").show();
        $("#myModalLabel").text('编辑任务详情信息');
        //编辑时，显示 提交按钮和启用下拉框和 input
        $("#saveButton").show().text("保存");
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        $("#taskId").prop("disabled",true);

    }

    //查看备件信息 模态框弹出
    $.ajax({
        type	:"get",
        url		: $.mpbGetHeaderPath()+"/orderCrossInDetail/r/"+id,
        headers:{'token':localStorage.getItem('token')},
        data    : {id:id},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                if(data != null){
                    $("#orderCrossInDetailId").val(data.orderCrossInDetailId);
                    $("#taskId").val(data.taskId);
                    $("#goodsCode").val(data.goodsCode);
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
        Content:"确定要删除任务详情?",
        FunL:function(){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/orderCrossInDetail/delete",
                headers:{'token':localStorage.getItem('token')},
                data    : {orderCrossInDetailId:id},
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
