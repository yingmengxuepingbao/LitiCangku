var _buz_service,_frontBasic;
console.log(window.parent.operationData);

$(function(){
    _buz_service={
        //获取列表信息
        getDataList : function(data, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/task/list",
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
                url		: $.mpbGetHeaderPath()+"/dictItem/getDictType/order_out_type",
                headers:{'token':localStorage.getItem('token')},
                data    : { code :"order_out_type"},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var options = '';
                        options +='<option value=""></option>'
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.dicItemCode+'">'+ele.dicItemName+'</option>'
                        }

                        $("#taskType").html(options);
                    }
                },
                error	: reject
            });
        },
        getWarehouseCode : function(data, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/warehouse/getWarehouseCode/warehouseCode",
                headers:{'token':localStorage.getItem('token')},
                data    : { code :"warehouseCode"},
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
                        $("#warehouseCode").html(options);
                    }
                },
                error	: reject
            });
        },
        getAreaCodeAll : function(data, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/area/getAreaCodeAll",
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
                        $("#areaCode").html(options);
                    }
                },
                error	: reject
            });
        },

        //合并出库单
        combineOrder: function(id,resolve, reject){
        $.ajax({
            type		:"post",
            url			: $.mpbGetHeaderPath()+"/orderOutDetail/combineOrder",
            headers:{'token':localStorage.getItem('token')},
            data		: {
                ids  :   id
            },
            success		: resolve,
            error		: reject
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
            //全选/全不选 checkbox
            $(document).on('click', '#check-all', function () {
                var _self=$(this);
                if(_self.is(':checked')){
                    //全选中设置
                    $("#formTable tbody tr").each(function(index,item){
                        $(item).find('input[type="checkbox"]').prop("checked","checked");
                    })
                }
                else{
                    //全不选设置
                    $("#formTable tbody tr").each(function(index,item){
                        $(item).find('input[type="checkbox"]').prop("checked", false);
                    })
                }
            })
            //有一个未选中则取消全选  /  全都选中则全选选中
            $(document).on('click', '#formTable tbody input[type="checkbox"]', function (e) {
                var isCheckAll=true;
                if(!$(this).is(':checked')){
                    //有一个没选中的就取消全选
                    $("#check-all").prop("checked", false);
                }
                else{
                    $("#formTable tbody tr").each(function(index,item){
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

            //主任务状态修改
            $(document).on('click', '#mainTaskStatus', function () {
                var monitorIdArr=[];
                $("#formTable tbody tr").each(function(index,item){
                    var _self=$(item);
                    if(_self.find('input[type="checkbox"]').is(':checked')){
                        var monitorId = _self.data('id');
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

                $("#formModel").modal({backdrop:false});

                $("#taskStatus").val("");
                $("#id").val(monitorStr);
            })

            //上传wms状态修改
            $(document).on('click', '#uploadWms', function () {
                var monitorIdArr=[];
                $("#formTable tbody tr").each(function(index,item){
                    var _self=$(item);
                    if(_self.find('input[type="checkbox"]').is(':checked')){
                        var monitorId = _self.data('id');
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

                $("#formModelUp").modal({backdrop:false});

                $("#reportWms").val("");
                $("#idUp").val(monitorStr);
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
                $("#check-all").prop("checked", false);
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
                $("#check-all").prop("checked", false);
                _this.loadBasicList();
            });
            //点击搜索
            $(document).on('click', '#refrechTop', function () {
                $(".currentPage").text("1");
                $("#check-all").prop("checked", false);
                _this.loadBasicList();
            });
            // 删除任务
            $(document).on('click', '#delTask', function () {
                var monitorIdArr=[];
                $("#formTable tbody tr").each(function(index,item){
                    var _self=$(item);
                    if(_self.find('input[type="checkbox"]').is(':checked')){
                        var monitorId = _self.data('id');
                        monitorIdArr.push(monitorId);
                    }
                })
                if(monitorIdArr.length <= 0){
                    $.DialogByZ.Autofade({Content:"请选择要删除的任务！"});
                    return;
                }else if(monitorIdArr.length > 1){
                    $.DialogByZ.Autofade({Content:"只能选择一条任务！"});
                    return;
                }
                var selectId=monitorIdArr.join(",");
                let data = {id:selectId};
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content:"确定要删除该任务?",
                    FunL:function(){
                        $.ajax({
                            type	:"post",
                            dataType: "json",//预期服务器返回的数据类型
                            url		: $.mpbGetHeaderPath()+"/task/delete",
                            headers:{'token':localStorage.getItem('token')},
                            data    : data,
                            success	: function (res) {
                                var result=res;
                                if(result.code == 0){
                                    _commonFun.successTips(result.data);
                                    _frontBasic.loadBasicList();
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

            });
        },
        //渲染库存列表
        loadBasicList : function () {
            var _this = this;

            var num =  $.trim($('#taskIdPoP').val())
            console.log("num",num)

            if (""!=num){
                var re = /^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 （判断正整数 /^[1-9]+[0-9]*]*$/）
                if (!re.test(num)){
                    console.log("非数字")
                }
            }


            var info={
                taskId    : $.trim($('#taskIdPoP').val()),
                palletCode    : $.trim($('#palletCodePoP').val()),
                taskType    : $.trim($('#taskTypePoP').val()),
                taskStatus    : $.trim($('#taskStatusPoP').val()),
                currentPage     : $.trim($(".currentPage").text()),
                orderBy         : _this.data.orderBy,
                numberOnePage   : _this.data.numberOnePage
            };
            _buz_service.getDataList(info,function (res) {
                var result=res;
                // console.log(res);
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


            for(var ele in list){
                var taskStatusText="";
                var taskStatus = list[ele].taskStatus;
                if(taskStatus =="1"){
                    taskStatusText = "创建";
                }else if(taskStatus =="2"){
                    taskStatusText = "执行中";
                }else if(taskStatus =="3"){
                    taskStatusText = "完成";
                }else if(taskStatus =="4"){
                    taskStatusText = "异常";
                }else if(taskStatus =="5"){
                    taskStatusText = "取消";
                }
                var reportWmsText = "";
                var reportWms = list[ele].reportWms;

                if (reportWms == "0"){
                    reportWmsText = "未上传";
                }else if (reportWms == "1"){
                    reportWmsText = "成功上传";
                }else if (reportWms == "2"){
                    reportWmsText = "业务异常";
                }else if (reportWms == "3"){
                    reportWmsText = "上传失败";
                }

                var taskTypeText="";
                var taskType = list[ele].taskType;
                if(taskType =="10"){
                    taskTypeText = "生产入库";
                }else if(taskType =="11"){
                    taskTypeText = "分拣入库";
                }else if(taskType =="12"){
                    taskTypeText = "盘点入库";
                }else if(taskType =="13"){
                    taskTypeText = "包材入库";
                }else if(taskType =="14"){
                    taskTypeText = "异常口入库";
                }else if(taskType =="15"){
                    taskTypeText = "虚拟托盘入库";
                }else if(taskType =="16"){
                    taskTypeText = "人工入库";
                }else if(taskType =="20"){
                    taskTypeText = "直发出库";
                }else if(taskType =="21"){
                    taskTypeText = "分拣出库";
                }else if(taskType =="22"){
                    taskTypeText = "盘点出库";
                }else if(taskType =="23"){
                    taskTypeText = "包材出库";
                }else if(taskType =="24"){
                    taskTypeText = "异常出库";
                }else if(taskType =="25"){
                    taskTypeText = "虚拟托盘出库";
                }else if(taskType =="26"){
                    taskTypeText = "人工出库";
                }else if(taskType =="30"){
                    taskTypeText = "普通移库";
                }else if(taskType =="31"){
                    taskTypeText = "出库移库";
                }else if(taskType =="40"){
                    taskTypeText = "生产越库";
                }
                tbody+='<tr data-id="'+list[ele].id+'" data-taskId ="'+list[ele].taskId+'">' +
                    '<td><div class="checkbox-custom checkbox-default"><input type="checkbox" >' +
                    '<label></label></div></td>'+

                    '<td>'+list[ele].taskId+'</td>'+
                    '<td>'+list[ele].palletCode+'</td>'+
                    '<td>'+taskStatusText+'</td>'+
                    '<td>'+taskTypeText+'</td>'+
                    '<td>'+reportWmsText+'</td>'+

                    '<td>'+list[ele].createBy+'</td>'+
                    '<td>'+list[ele].gmtCreate+'</td>'+
                    '<td>'+list[ele].fromAddress+'</td>'+


                    '<td>'+list[ele].toAddress+'</td>';
                // tbody += '<td>'+'<a onclick="editOneRecord(\''+list[ele].id+'\',1)"><i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                // tbody += '<td>'+ '<a onclick="deleteOneRecord(\''+list[ele].id+'\')"><i class="fa-table-line-delete" title="删除"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                tbody += '<td>'+  '<a  onclick="editSelect(\''+list[ele].id+'\',\''+list[ele].taskId+'\',\''+list[ele].taskStatus+'\')" > <i class="fa-table-line-look" title="查看"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                // tbody +=  '<a  onclick="distribution(\''+list[ele].id+'\',\''+list[ele].taskId+'\')" > <i title="分配库存">分配</i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                // tbody +=  '<a  onclick="cancelDis(\''+list[ele].id+'\',\''+list[ele].taskId+'\')" > <i title="取消分配">取消分配</i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                tbody += '</td>'+'</tr>'
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
        $("#taskTypePoP").val("");
        $("#taskStatusPoP").val("");
    });
    //点击设备列表中的查看
    $(document).on('click', '.a-equipment-look', function (e) {
        var _self = $(this);
        //获取详情
        console.log(_self.parent().parent());
        alert(_self.parent().parent());
        var id = _self.parent().parent().data('taskId');
        window.location.href="addTaskExecution.html?id="+id;
    })
    //confirmPerson chosen change事件
    $(document).on('change', '#confirmPerson', function (e){
        $("#confirmPerson").val($("#confirmPerson option:selected").attr("value"));
    });
    //新增
    $("#addButton").on("click",function () {
        //供应商
        // _buz_service.getSupplyList();
        //备件确认人
        // $("#confirmPerson").prop("disabled",false);
        //显示 提交按钮和启用下拉框和 input
        $("#saveButton").show().text("保存");
        $("#formInfo").find("i").show();
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        $("#taskId").val("系统生成");
        $("#taskId").prop("disabled",true);
        $("#id").val("");
        $("#taskType").val("1");
        $("#taskType").prop("disabled",true);
        $("#remark").val("");
        $("#taskStatus").val("1");
        $("#taskStatus").prop("disabled",true);
        $("#myModalLabel").text('新增出库主信息');
        $("#formModel").modal({backdrop:false});
    });
    $("#saveButton").on("click",function () {

        var id = $.trim($("#id").val());
        var taskStatus = $.trim($("#taskStatus").val());
        //添加数据校验
        if(_commonFun.isBlank(id)){
            $.DialogByZ.Autofade({Content: "id不能为空"});
            return false;
        }
        if(_commonFun.isBlank(taskStatus)){
            $.DialogByZ.Autofade({Content: "任务状态不能为空"});
            return false;
        }

        var data= {
            id:id,
            taskStatus:taskStatus
        };

        //不存在新增
        $.ajax({
            type	:"POST",
            url		: $.mpbGetHeaderPath()+"/task/changeMain",
            headers:{'token':localStorage.getItem('token')},
            data    : data,
            success	: function (res) {
                var result=res;
                if(result.code == 0){
                    _commonFun.successTips(result.data);
                    _frontBasic.loadBasicList();
                }else {
                    _commonFun.errorTips(result.message);
                }
            },
            error	: function () {
                _commonFun.errorTips("错误");
            }
        });
    });
    //修改上传wms状态
    $("#saveButtonUp").on("click",function () {

        var id = $.trim($("#idUp").val());
        var reportWms = $.trim($("#reportWms").val());
        //添加数据校验
        if(_commonFun.isBlank(id)){
            $.DialogByZ.Autofade({Content: "id不能为空"});
            return false;
        }
        if(_commonFun.isBlank(reportWms)){
            $.DialogByZ.Autofade({Content: "状态不能为空"});
            return false;
        }

        var data= {
            id:id,
            reportWms:reportWms
        };

        //不存在新增
        $.ajax({
            type	:"POST",
            url		: $.mpbGetHeaderPath()+"/task/changeReportWms",
            headers:{'token':localStorage.getItem('token')},
            data    : data,
            success	: function (res) {
                var result=res;
                if(result.code == 0){
                    _commonFun.successTips(result.data);
                    _frontBasic.loadBasicList();
                }else {
                    _commonFun.errorTips(result.message);
                }
            },
            error	: function () {
                _commonFun.errorTips("错误");
            }
        });
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
//点击设备列表中的查看
// $(document).on('click', '.a-equipment-look', function (e) {
//     var _self = $(this);
//     //获取详情
//     console.log(_self.parent().parent());
//     alert(_self.parent().parent());
//     var id = _self.parent().parent().data('taskId');
//     window.location.href="addTaskExecution.html?id="+id;
// })

function editSelect(id,taskId,taskStatus) {
    // var _self = $(this);
    //获取详情
    console.log(id);
    // var id = _self.parent().parent().data('taskId');
    window.location.href="addTaskExecution.html?id="+id+"&taskId="+taskId+"&taskStatus="+taskStatus;
}


//编辑 type  0 表示 查看  1 表示 编辑  2表示 审核
function editOneRecord(id,type) {
    var id = id;
    $("#type").val(type);
    //供应商
    // _buz_service.getSupplyList();
    //查看时，隐藏提交按钮 和禁用下拉框和input
    if(type == 0 ){
        $("#formInfo").find("i").hide();
        $("#myModalLabel").text('查看出库单主信息');
        $("#saveButton").hide();
        $("form  select").prop("disabled",true);
        $("form  input").prop("disabled",true);
    }else if (type == 1){
        $("#formInfo").find("i").show();
        $("#myModalLabel").text('编辑出库单主信息');
        //编辑时，显示 提交按钮和启用下拉框和 input
        $("#saveButton").show().text("保存");
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        $("#taskId").prop("disabled",true);
        $("#taskStatus").prop("disabled",true);

    }

    //查看备件信息 模态框弹出
    $.ajax({
        type	:"get",
        url		: $.mpbGetHeaderPath()+"/orderOut/r/"+id,
        headers:{'token':localStorage.getItem('token')},
        data    : {id:id},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                if(data != null){
                    $("#id").val(data.id);
                    $("#taskId").val(data.taskId);
                    $("#taskType").val(data.taskType);

                    $("#taskStatus").val(data.taskStatus);
                    $("#remark").val(data.remark);
                }
                //不是创建状态不可修改
                if(data.taskStatus!="1"){
                    _commonFun.errorTips("不是创建状态不可修改");
                }else {
                    //显示模态框
                    $("#formModel").modal({backdrop:false});
                }

            }
        },
        error	: function () {
            _commonFun.errorTips('');
        }
    });

}

//删除出库单
function deleteOneRecord(id) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要删除该任务执行?",
        FunL:function(){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/task/delete",
                headers:{'token':localStorage.getItem('token')},
                data    : {id:id},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        _commonFun.successTips(result.data);
                        _frontBasic.loadBasicList();
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
function distribution(id,taskId) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要分配此出库单库存?",
        FunL:function(){
            console.log("xxxxx");
            console.log("id",id);
            console.log("taskId",taskId);
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/orderOutDetail/distribution",
                headers:{'token':localStorage.getItem('token')},
                data    : {
                    id : id,
                    taskId : taskId
                },
                success	: function (res) {
                    var result=res;
                    console.log(result);
                    if(result.code == 0){
                        _commonFun.successTips(result.data);
                        _frontBasic.loadBasicList();
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
function cancelDis(id,taskId) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要取消分配此出库单库存?",
        FunL:function(){
            console.log("xxxxx");
            console.log("id",id);
            console.log("taskId",taskId);
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/orderOutDetail/cancel",
                headers:{'token':localStorage.getItem('token')},
                data    : {
                    id : id,
                    taskId : taskId
                },
                success	: function (res) {
                    var result=res;
                    console.log(result);
                    if(result.code == 0){
                        _commonFun.successTips(result.data);
                        _frontBasic.loadBasicList();
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

