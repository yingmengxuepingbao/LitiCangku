var _buz_service,_frontBasic;
//console.log(window.parent.operationData);

$(function(){
    _buz_service={
        //获取列表信息
        getDataList : function(data, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/taskLog/list",
                headers:{'token':localStorage.getItem('token')},
                data    : data || {},
                success	: resolve,
                error	: reject
            });
        },

        updateStatsTask : function(data, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/nuohua/report/taskStatusUpdate",
                headers:{'token':localStorage.getItem('token')},
                data    : data || {},
                success	: resolve,
                error	: reject
            });
        },
        //获取供应商列表
        getSupplyList : function(data, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/dictItem/getDictType/wmsTaskExecutionLogService_type",
                headers:{'token':localStorage.getItem('token')},
                data    : { code :"customer_type"},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var options = '';
                        options +='<option value=""></option>'
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.dicItemCode+'">'+ele.dicItemName+'</option>'
                        }

                        $("#customerType").html(options);
                    }
                },
                error	: reject
            });
        },
    };
    _frontBasic = {
        data: {
            currentNumber   : 0,  //记录当前页有多少条数据
            totalPageNum    : 0,  //记录一共多少页
            numberOnePage   : 10 //一页几条数据
        },
        init: function () {
            this.onLoad();
            this.bindEvent();
        },
        onLoad: function () {
            this.loadBasicList();
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
                _this.loadBasicList();
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
                _this.loadBasicList();
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
                _this.loadBasicList();
            });
            //点击搜索
            $(document).on('click', '#refrechTop', function () {
                $(".currentPage").text("1");
                _this.loadBasicList();
            });
        },
        //渲染库存列表
        loadBasicList : function () {
            var _this = this;
            var req = GetRequest();
            var orderNo = req['orderNo'];
            var taskType = req['taskType'];
            var userDefined2 = req['userDefined2'];
            var taskId = $.trim($('#taskIdPoP').val());
            var userDefined3 = $.trim($('#userDefined3PoP').val());
            console.log("orderNo:"+orderNo+" userDefined2:"+userDefined2 +" taskId: "+taskId +" taskType= "+taskType);
            var info;
            if (taskId == ""){
                info={
                    orderNo:orderNo,
                    userDefined2:userDefined2,
                    palletCode          : $.trim($('#palletCodePoP').val()),
                    taskStatus          : $.trim($('#taskStatusPoP').val()),
                    //taskType            : $.trim($('#taskTypePoP').val()),
                    taskType            :taskType,
                    currentPage     : $.trim($(".currentPage").text()),
                    orderBy         : _this.data.orderBy,
                    numberOnePage   : _this.data.numberOnePage
                };
            } else {
                info={
                    orderNo:orderNo,
                    userDefined2:userDefined2,
                    taskId    : Number($.trim($('#taskIdPoP').val())),
                    userDefined3:$.trim($('#userDefined3PoP').val()),
                    palletCode          : $.trim($('#palletCodePoP').val()),
                    taskStatus          : $.trim($('#taskStatusPoP').val()),
                    //taskType            : $.trim($('#taskTypePoP').val()),
                    taskType            :taskType,
                    currentPage     : $.trim($(".currentPage").text()),
                    orderBy         : _this.data.orderBy,
                    numberOnePage   : _this.data.numberOnePage
                };
            }

            _buz_service.getDataList(info,function (res) {
                var result=res;
                if(result.code==0) {
                    if(result.data.totalNumber!=0){
                        var data=result.data.infoList;
                        _this.basicListSource(data);
                        if(result.data.totalNumber>_this.data.numberOnePage){
                            $(".pagination-con").show();
                            _this.data.totalPageNum=result.data.totalNumber;
                            _this.loadPagingNumber(_this.data.totalPageNum);
                        }
                        else {
                            $(".pagination-con").hide();
                        }
                    }
                    else {
                        $("#formTable tbody").empty();
                        $.DialogByZ.Autofade({Content: "查无数据！"});
                        $(".pagination-con").hide();
                    }
                }
                else {
                    $.DialogByZ.Autofade({Content: result.message ||  "查无数据！"});
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        basicListSource : function(list) {
            $("#formTable tbody").empty();
            var tbody='';
            var userId = localStorage.getItem("userId");
            var taskStatusText = "";
            var taskTypeText = "";
            var userDefined3Text = "";
            for(var ele in list){
                var taskStatus = list[ele].taskStatus;
                if(taskStatus =="1"){
                    taskStatusText = "创建";
                }else if(taskStatus =="2"){
                    taskStatusText = "执行";
                }else if(taskStatus =="3"){
                    taskStatusText = "完成";
                }else if(taskStatus =="4"){
                    taskStatusText = "异常";
                }else if(taskStatus =="5"){
                    taskStatusText = "取消";
                }else if(taskStatus =="6"){
                    taskStatusText = "创建失败";
                }else if(taskStatus =="7"){
                    taskStatusText = "待出库";
                }else if(taskStatus =="6"){
                    taskStatusText = "入库任务结束后出库";
                }
                var taskType = list[ele].taskType;
                console.log("taskType="+taskType)
                if(taskType =="10" ){
                    taskTypeText = "成品入库";
                }else if(taskType =="15"){
                    taskTypeText = "虚拟托盘入库";
                }else if(taskType =="16"){
                    taskTypeText = "人工入库";
                }else if(taskType =="20"){
                    taskTypeText = "直发出库";
                }else if(taskType =="25"){
                    taskTypeText = "虚拟托盘出库";
                }else if(taskType =="26"){
                    taskTypeText = "人工出库";
                }else if(taskType =="30"){
                    taskTypeText = "移库";
                }else if(taskType =="50"){
                    taskTypeText = "原材料入库";
                }else {
                    taskTypeText = taskType;
                }
                //是否确认对账
                var userDefined3 = list[ele].userDefined3;
                if(userDefined3=="0"){
                    userDefined3Text = "已确认";
                }else if(userDefined3=="1"){
                    userDefined3Text = "未确认";
                }else {
                    userDefined3Text = userDefined3;
                }
                tbody+='<tr data-id="'+list[ele].id+'">' +
                    '<td>'+list[ele].taskId+'</td>'+
                    '<td>'+list[ele].palletCode+'</td>'+
                    '<td>'+taskTypeText+'</td>'+
                    '<td>'+taskStatusText+'</td>'+
                    '<td>'+list[ele].errorMsg+'</td>'+
                    '<td>'+list[ele].inAddress+'</td>'+
                    '<td>'+list[ele].outAddress+'</td>'+
                    '<td>'+list[ele].goodsCode+'</td>'+
                    '<td>'+list[ele].batchNo+'</td>'+
                    // '<td>'+list[ele].warehouseCode+'</td>'+
                    // '<td>'+list[ele].areaCode+'</td>'+
                    '<td>'+list[ele].locationCode+'</td>'+
                    '<td>'+userDefined3Text+'</td>'+
                    '<td>'+list[ele].gmtCreate+'</td>';
                // tbody += '<td>'+'<a onclick="editOneRecord(\''+list[ele].id+'\',1)"><i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                // tbody +=  '<a onclick="deleteOneRecord(\''+list[ele].id+'\')"><i class="fa-table-line-delete" title="删除"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                tbody += '<td>'+'<a onclick="lookRecord(\''+list[ele].taskId+'\', \''+list[ele].palletCode+'\', \''+list[ele].locationCode+'\')" > <i class="fa-table-line-look" title="过账"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;' +'</td>';
                tbody += '</tr>'
            }
            $("#formTable tbody").html(tbody);
        },
        //加载分页
        loadPagingNumber: function(num) {
            parseInt(num%this.data.numberOnePage)==0 ?
                this.data.totalPageNum=parseInt(num/this.data.numberOnePage) :
                this.data.totalPageNum=parseInt(num/this.data.numberOnePage)+1;

            $(".dataNumber").html(num);//共多少条
            $(".pageNumber").html(this.data.totalPageNum);//共多少页
            $(".pageOneNumber").text(this.data.numberOnePage);//每页几条
        },

    };
    _frontBasic.init();

    //表头排序
    $(document).on('click', '#formTable thead tr th', function (e) {
        e.stopPropagation();
        var _self=$(this);
        var orderBy=_self.data('sort');

        if(_self.find('i').hasClass('fa-sort-amount-asc')){
            _self.parents("thead").find('i').removeClass("th-sort-active");
            _self.find('i').removeClass('fa-sort-amount-asc').addClass('fa-sort-amount-desc th-sort-active');
            _frontBasic.data.orderBy=orderBy + " desc";
            _frontBasic.loadBasicList();

        }
        else if(_self.find('i').hasClass('fa fa-sort-amount-desc')){
            _self.parents("thead").find('i').removeClass("th-sort-active");
            _self.find('i')
                .removeClass('fa-sort-amount-desc')
                .addClass('fa-sort-amount-asc th-sort-active');
            _frontBasic.data.orderBy=orderBy + " asc";
            _frontBasic.loadBasicList();
        }

    });
    //重置
    $("#reset").on("click",function () {
        $("#taskIdPoP").val("");
        $("#palletCodePoP").val("");
    });
    //confirmPerson chosen change事件
    $(document).on('change', '#confirmPerson', function (e){
        $("#confirmPerson").val($("#confirmPerson option:selected").attr("value"));
    });
    //新增
    $("#addButton").on("click",function () {
        //供应商
        _buz_service.getSupplyList();
        //备件确认人
        // $("#confirmPerson").prop("disabled",false);
        //显示 提交按钮和启用下拉框和 input
        $("#saveButton").show().text("保存");
        $("#formInfo").find("i").show();
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        // $("#sparePartCode").prop("disabled",true);
        $("#id").val("");
        $("#taskId").val("");
        $("#palletCode").val("");
        $("#customerShortName").val("");
        $("#customerEngName").val("");
        $("#customerType").val("");
        $("#address").val("");
        $("#contacts").val("");
        $("#website").val("");
        $("#myModalLabel").text('新增任务');
        $("#formModel").modal({backdrop:false});
    });
    //新增或更新信息
    $("#saveButton").on("click",function () {
            var id = $.trim($("#id").val());
            console.log("id",id);
            var taskId = $.trim($("#taskId").val());
            var palletCode = $.trim($("#palletCode").val());
            var customerShortName = $.trim($("#customerShortName").val());
            var customerEngName = $.trim($("#customerEngName").val());
            var customerType = $.trim($("#customerType").val());
            var customerLevel = $.trim($("#customerLevel").val());
            var address = $.trim($("#address").val());
            var contacts = $.trim($("#contacts").val());
            var website = $.trim($("#website").val());
            //添加数据校验
            if(_commonFun.isBlank(taskId)){
                $.DialogByZ.Autofade({Content: "任务编码不能为空"});
                return false;
            }
            if(_commonFun.isBlank(palletCode)){
                $.DialogByZ.Autofade({Content: "托盘编码不能为空"});
                return false;
            }

            var data= {
                taskId:taskId,
                palletCode:palletCode,
                customerShortName:customerShortName,
                customerEngName:customerEngName,
                customerType:customerType,
                customerLevel:customerLevel,
                address:address,
                contacts:contacts,
                website:website
            };

            //判断是新增还是修改
            if (!_commonFun.isNotBlank(id)){
                //不存在新增
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/wmsTaskExecutionLogService/c",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            _commonFun.successTips(result.data);
                            _frontBasic.loadBasicList();
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
                data.id = id;
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/wmsTaskExecutionLogService/u",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            _commonFun.successTips(result.data);
                            _frontBasic.loadBasicList();
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
function editOneRecord(id,type) {
    var id = id;
    $("#type").val(type);
    //供应商
    _buz_service.getSupplyList();
    //查看时，隐藏提交按钮 和禁用下拉框和input
    if(type == 0 ){
        $("#formInfo").find("i").hide();
        $("#myModalLabel").text('查看任务信息');
        $("#saveButton").hide();
        $("form  select").prop("disabled",true);
        $("form  input").prop("disabled",true);
    }else if (type == 1){
        $("#formInfo").find("i").show();
        $("#myModalLabel").text('编辑任务信息');
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
        url		: $.mpbGetHeaderPath()+"/wmsTaskExecutionLogService/r/"+id,
        headers:{'token':localStorage.getItem('token')},
        data    : {id:id},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                if(data != null){
                    $("#id").val(data.id);
                    $("#taskId").val(data.taskId);
                    $("#palletCode").val(data.palletCode);
                    $("#customerShortName").val(data.customerShortName);
                    $("#customerEngName").val(data.customerEngName);
                    $("#customerType").val(data.customerType);
                    $("#customerLevel").val(data.customerLevel);
                    $("#address").val(data.address);
                    $("#contacts").val(data.contacts);
                    $("#website").val(data.website);
                    $("#purchaseUnit").val(data.purchaseUnit);
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
//过账
function  lookRecord(taskId,goodsCode,batchNo){
    console.log(taskId);
    $("#taskId_h").val(taskId);
    $("#myGuozhangModalLabel").text("过账："+taskId);
    var url = "data:image/png;base64,";
    var goodsCode_img="";
    var batchNo_img="";

    //获取物料二维码
    $.ajax({
        type	:"get",
        async:false,
        url		: $.mpbGetHeaderPath()+"/nuohua/get128yards",
        headers:{'token':localStorage.getItem('token')},
        data    : {str:goodsCode},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                if(data != null){
                    console.log("data : "+data);
                    goodsCode_img = data;
                }
            }
        },
        error	: function () {
        _commonFun.errorTips('');
        }
    });
    //获取货位码二维码
    $.ajax({
        type	:"get",
        async:false,
        url		: $.mpbGetHeaderPath()+"/nuohua/get128yards",
        headers:{'token':localStorage.getItem('token')},
        data    : {str:batchNo},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                if(data != null){
                    console.log("data : "+data);
                    batchNo_img=data;
                }
            }
        },
        error	: function () {
            _commonFun.errorTips('');
        }
    });
    console.log("goodsCode_img : "+goodsCode_img);
    console.log("batchNo_img : "+batchNo_img);
    //显示模态框
    $("#goodsCode").attr('src', url+""+ goodsCode_img);
    $("#batchNo").attr('src', url+""+ batchNo_img);
    $("#guozhangModel").modal({backdrop: false});
}
//确认过账
$("#queRenButton").on("click",function () {

    let taskId =  Number($("#taskId_h").val());
    var info ={
            taskId:taskId,
            userDefined3:"0"
        };
    console.log("taskId:"+taskId);
    _buz_service.updateStatsTask(info,function (res) {

        var result=res;
        if(result.code==0) {
            _commonFun.successTips(result.data);
            _frontBasic.loadBasicList();
        }
        else {
            $.DialogByZ.Autofade({Content: result.message ||  "查无数据！"});
        }
    },function(err){
        _commonFun.errorTips('');
    })

});
//暂缓过账
$("#zanhuanButton").on("click",function () {

    let taskId =  Number($("#taskId_h").val());
    var info ={
        taskId:taskId,
        userDefined3:"1"
    };
    console.log("taskId:"+taskId);
    _buz_service.updateStatsTask(info,function (res) {

        var result=res;
        if(result.code==0) {
            _commonFun.successTips(result.data);
            _frontBasic.loadBasicList();
        }
        else {
            $.DialogByZ.Autofade({Content: result.message ||  "查无数据！"});
        }
    },function(err){
        _commonFun.errorTips('');
    })

});
//获取页面跳转的数据
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
//删除备件
function deleteOneRecord(id) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要删除任务信息?",
        FunL:function(){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/wmsTaskExecutionLogService/delete",
                headers:{'token':localStorage.getItem('token')},
                data    : {id:id},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        _commonFun.successTips(result.data);
                        _frontBasic.loadBasicList();
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

