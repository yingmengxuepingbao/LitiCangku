var permissions ={};
if(_commonFun.isNotBlank(window.parent.operationData)){
    permissions = window.parent.operationData.permissions;
}
//控制 新增按钮 显示
// if(permissions["addEdgeBox"]){
//     $("#addEquipment").show();
// }else {
//     $("#addEquipment").hide();
// }
var _box_service,_edgeBox;
$(function(){
    _box_service={
        //根据筛选条件获取EdgeBox列表
        getEdgeBoxs: function(edgeBoxInfo,resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/wmsNHOrderOutStereoscopic/list",
                headers:{'token':localStorage.getItem('token')},
                data	: edgeBoxInfo,
                success	: resolve,
                error	: reject
            });
        },
        //任务明细
        taskStartListBoxInfo: function(id,resolve, reject){
            var data = {
                orderNoList  :  id.split(','),
                userDefined2: "50",
                orderType:20
            }
            $.ajax({
                type		:"post",
                url		: $.mpbGetHeaderPath()+"/wmsNHOrderOutStereoscopic/taskStartList",
                headers:{'token':localStorage.getItem('token')},
                contentType: 'application/json',
                data		: JSON.stringify(data),
                success		: resolve,
                error		: reject
            });
        },
        //批量启动
        deleteEdgeBoxInfo: function(id,resolve, reject){
            var data = {
                orderNoList  :  id.split(',')
            }
            $.ajax({
                type		:"post",
                url		: $.mpbGetHeaderPath()+"/wmsNHOrderOutStereoscopic/batch/start",
                headers:{'token':localStorage.getItem('token')},
                contentType: 'application/json',
                data		: JSON.stringify(data),
                success		: resolve,
                error		: reject
            });
        },
        //批量合并
        deleteEdgeBoxInfo1: function(id,resolve, reject){
            var data = {
                orderOutIdList  :  id.split(',')
            }
            $.ajax({
                type		:"post",
                url		: $.mpbGetHeaderPath()+"/wmsNHOrderOutStereoscopic/batch/merge",
                headers:{'token':localStorage.getItem('token')},
                contentType: 'application/json',
                data		: JSON.stringify(data),
                success		: resolve,
                error		: reject
            });
        },
        //停用edgebox(包括批量停用)
        disableEdgeBoxInfo: function(id,resolve, reject){
            var data = { orderNo  :   id , orderStatus : 1}

            $.ajax({
                type		:"post",
                url			: $.mpbGetHeaderPath()+"/wmsNHOrderOutStereoscopic/disableEdgeBox",
                headers:{'token':localStorage.getItem('token')},
                contentType: 'application/json',
                data		: JSON.stringify(data),
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
            orderBy         : ""  //表头排序字段
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
            //搜索文本框-单据编号
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
                $("#orderNo").val("");
                $("#orderStatusSelect").val("");
                $("#orderTypeSelect").val("");
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
                $(".errorMsg2").empty();
                $("#hideInfoIdPop2").val("");
                $("#hideInfoValuePop2").val("");
                window.location.href="addNHYCLOrderOutStereoscopic.html";
            });
            //点击EdgeBox采集终端列表中的编辑
            $(document).on('click', '.a-equipment-edit', function (e) {
                e.stopPropagation();
                var _self = $(this);
                //获取等级详情
                var id = _self.parent().parent().data('orderOutId');
                alert("id"+ id)
                window.location.href="addNHYCLOrderOutStereoscopic.html?id="+id;
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
                var monitorId = _self.parent().parent().data('id');
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除该EdgeBox吗？",
                    FunL:function(){
                        _box_service.deleteEdgeBoxInfo(monitorId, function (res) {
                            var result=JSON.parse(decodeURIComponent(res));
                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "删除成功！"});
                                $("#check-all").prop("checked", false);
                                _this.loadEdgeBoxs();
                            }
                            else {
                                $.DialogByZ.Autofade({Content: result.message || "删除成功！"});
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
                        var monitorId = _self.attr('orderNo');
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

            //任务明细
            $(document).on('click', '.taskStartList', function () {
                var monitorIdArr=[];
                $("#edgeBoxList tbody tr").each(function(index,item){
                    var _self=$(item);
                    if(_self.find('input[type="checkbox"]').is(':checked')){
                        var monitorId = _self.attr('orderNo');
                        monitorIdArr.push(monitorId);
                    }
                })
                if(monitorIdArr.length <= 0){
                    $.DialogByZ.Autofade({Content:"请选择要查看的发货单据！"});
                    return;
                }else if(monitorIdArr.length > 1){
                    $.DialogByZ.Autofade({Content:"只能选择一条发货单据！"});
                    return;
                }
                var monitorStr=monitorIdArr.join(",");
                console.log("monitorStr",monitorStr);
                //判断任务是否已经生成，
                //未生成，生成任务
                _box_service.taskStartListBoxInfo(monitorStr, function (res) {
                    var result=res;
                    if(result.code==0) {
                        //已生成，调任务列表页面
                        window.location.href="wmsNHTaskExecutionLogList.html?userDefined2=50&&orderNo="+monitorStr+"&taskType=20";
                    }else {
                        // 生成失败
                        $.DialogByZ.Autofade({Content: result.message});
                        _this.loadEdgeBoxs();
                    }
                }, function (err) {
                    _commonFun.errorTips('');
                })
            });

            //合并出库按钮
            $(document).on('click', '.multiDelete1', function () {
                var monitorIdArr=[];
                $("#edgeBoxList tbody tr").each(function(index,item){
                    var _self=$(item);
                    if(_self.find('input[type="checkbox"]').is(':checked')){
                        var monitorId = _self.attr('id');
                        monitorIdArr.push(monitorId);
                    }
                })
                var monitorStr=monitorIdArr.join(",");
                if(monitorIdArr.length>0){
                    $.DialogByZ.Confirm({
                        Title: "操作提示",
                        Content: "确定要批量合并出库任务吗？",
                        FunL:function(){
                            _box_service.deleteEdgeBoxInfo1(monitorStr, function (res) {
                                var result=res;
                                if(result.code==0) {
                                    $.DialogByZ.Autofade({Content: "合并成功！"});
                                    $("#check-all").prop("checked", false);
                                    _this.loadEdgeBoxs();
                                }else {
                                    $.DialogByZ.Autofade({Content: result.message || "合并失败！"});
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
                    $.DialogByZ.Autofade({Content:"请选择要合并的出库任务！"});
                }
            });
        },

        //EdgeBox列表
        loadEdgeBoxs: function () {
            var _this      =this;
            var equipment  ={
                userDefined2:"50",
                orderStatus          : $.trim($("#orderStatusSelect").val()),
                orderType          : $.trim($("#orderTypeSelect").val()),
                orderNo    : $.trim($("#orderNo_search").val()),
                page     : $.trim($(".currentPage").text()),
                orderBy         : _this.data.orderBy,
                rows   : _this.data.numberOnePage
            }
            _box_service.getEdgeBoxs(equipment,function (res) {
                var result=res;
                if(result.code==0){
                    var records=result.data.records;
                    var totalCount=result.data.totalCount;
                    _this.loadEdgeBoxsHtml(records);
                    if(totalCount>_this.data.numberOnePage){
                        $(".pagination-con").show();
                        _this.loadPagingNumber(totalCount);
                    }
                    else {
                       /* $.DialogByZ.Autofade({Content: "查无数据！"});
                        $("#edgeBoxList tbody").empty();*/
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
            var orderStatusText = '';
            var outAddressText = '';
            var str = '';
            for (var ele in list) {
                var orderStatus = list[ele].orderStatus;
                if(orderStatus =="1"){
                    orderStatusText = "创建";
                }else if(orderStatus =="2"){
                    orderStatusText = "启动";
                }else if(orderStatus =="3"){
                    orderStatusText = "完成";
                }else if(orderStatus =="4"){
                    orderStatusText = "异常";
                }else if(orderStatus =="5"){
                    orderStatusText = "启动失败";
                }

                var outAddress = list[ele].outAddress;
                if(outAddress =="2001"){
                    outAddressText = "立库出库口1";
                }else if(outAddress =="2002"){
                    outAddressText = "立库出库口2";
                }else if(outAddress =="2003"){
                    outAddressText = "立库出库口3";
                }else {
                    outAddressText = outAddress;
                }

                str+='<tr id="'+list[ele].orderOutId+'" orderNo="'+list[ele].orderNo+'">' +
                    '<td><div class="checkbox-custom checkbox-default"><input type="checkbox" >' +
                    '<label></label></div></td>'+
                    '<td class="orderNo">'+list[ele].orderNo+'</td>'+
                    '<td class="orderStatus">'+orderStatusText+'</td>'+
                    '<td class="orderType">'+list[ele].orderType+'</td>'+
                    '<td class="outAddress" style="width: 150px">'+outAddressText+'</td>'+
                    '<td class="userDefined1 ">'+list[ele].userDefined1+'</td>'+
                    '<td class="gmtRun ">'+list[ele].gmtRun+'</td>'+
                    '<td class="gmtOver ">'+list[ele].gmtOver+'</td>'+
                    '<td class="createBy">'+list[ele].createBy+'</td>'+
                    '<td class="gmtCreate">'+list[ele].gmtCreate+'</td>';

                str+='<td>';
                if(list[ele].orderStatus == 1){
                    // if(permissions["eidtEdgeBox"]){
                    //     str += '<a class="link a-equipment-edit"> <i class="fa-table-line-edit"   title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    // }
                    str += '<a onclick="editOneRecord(\''+list[ele].orderNo+'\',1)"><i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                }
                if(list[ele].orderStatus == 2){
                    // if(permissions["stopEdgeBox"]) {
                        str += '<a onclick="disableOneRecord(\''+list[ele].orderNo+'\')" class="link a-equipment-disable"> <i class="fa-table-line-para"   title="停用"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    // }
                }
                if(list[ele].orderStatus == 1 ){
                    // if(permissions["deleteEdgeBox"]) {
                    //     str += '<a class="link a-equipment-delete" > <i class="fa-table-line-delete"  title="删除"></i></a>';
                    // }
                    str +=  '<a onclick="deleteOneRecord(\''+list[ele].orderNo+'\')"><i class="fa-table-line-delete" title="删除"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                }
                str +=  '<a onclick="editOneRecord(\''+list[ele].orderNo+'\', 0)" > <i class="fa-table-line-look" title="查看"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
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
    window.location.href="addNHYCLOrderOutStereoscopic.html?orderNo="+orderNo;
}

//编辑 type  0 表示 查看  1 表示 编辑  2表示 审核
function editOneRecord(orderNo,type) {
    // alert(orderNo);
    if(type == 0){
        window.location.href="infoNHOrderOutStereoscopic.html?orderNo="+orderNo+','+type +',50';
    }else{
        window.location.href="addNHYCLOrderOutStereoscopic.html?orderNo="+orderNo+','+type;
    }
}
//停用edgeBox
function disableOneRecord(orderNo) {
    var boxId = orderNo;
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content: "确定要停用该出库任务吗？",
        FunL:function(){
            console.log("boxId = "+boxId );
            _box_service.disableEdgeBoxInfo(boxId, function (res) {
                //var result=JSON.parse(decodeURIComponent(res));
                var result=res ;
                if(result.code==0) {
                    $.DialogByZ.Autofade({Content: "停用成功！"});
                    $("#check-all").prop("checked", false);
                    //重新加载列表
                    _edgeBox.loadEdgeBoxs();
                }
                else {
                    $.DialogByZ.Autofade({Content: result.message || "停用成功！"});
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
};
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