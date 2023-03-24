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
        // 获取地址类型
        getAddressType : function(resolve, reject){
            var _this=this;
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/wmsAddressRealRela/getAddressType",
                headers:{'token':localStorage.getItem('token')},
                // 0可用1入库中2出库中3占用4异常
                data    : { useStatus :""},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var data=result.data;
                        $("#addressTypeSelect").html("");
                        var commonDevice0 = "<option value=''>请选择地址类型</option>";
                        var device = "";
                        for (var i = 0; i <data.length; i++) {
                            var addressType = data[i].addressType.toString();
                            var addressTypeDesc = data[i].addressTypeDesc.toString();
                            device += "<option value='"+addressType+"'>"+addressTypeDesc + "</option>";
                        }
                        // console.log("device1:" + device)
                        $("#addressTypeSelect").html(commonDevice0 + device);
                    }
                },
                error	: reject
            });
        },
        // 获取地址类型
        getAddressType1 : function(resolve, reject){
            var _this=this;
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/wmsAddressRealRela/getAddressType",
                headers:{'token':localStorage.getItem('token')},
                // 0可用1入库中2出库中3占用4异常
                data    : { useStatus :""},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var data=result.data;
                        $("#addressType").html("");
                        var commonDevice0 = "<option value=''>请选择地址类型</option>";
                        var device = "";
                        for (var i = 0; i <data.length; i++) {
                            var addressType = data[i].addressType.toString();
                            var addressTypeDesc = data[i].addressTypeDesc.toString();
                            device += "<option value='"+addressType+"'>"+addressTypeDesc + "</option>";
                        }

                        $("#addressType").html(device);

                        $('#addressType').chosen().trigger("chosen:updated");
                    }
                },
                error	: reject
            });
        },
        //根据筛选条件获取EdgeBox列表
        getEdgeBoxs: function(edgeBoxInfo,resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/wmsAddressRealRela/list",
                headers:{'token':localStorage.getItem('token')},
                data	: edgeBoxInfo,
                success	: resolve,
                error	: reject
            });
        },
        //删除出库单
        deleteEdgeBoxInfo: function(id,resolve, reject){
            var data = {
                id  :  id
            }
            $.ajax({
                type		:"post",
                url		: $.mpbGetHeaderPath()+"/wmsAddressRealRela/d",
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
            _box_service.getAddressType();
            // setTimeout(_box_service.getAddressType1(), 3000)
            // _box_service.getAddressType1();
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
            //搜索文本框-实际物理地址
            $("#realAddress_search").bind("input propertychange", function () {
                $(".currentPage").text("1");
                $("#check-all").prop("checked", false);
                _this.loadEdgeBoxs();
            });
            //搜索文本框-地址名称
            $("#addressName_search").bind("input propertychange", function () {
                $(".currentPage").text("1");
                $("#check-all").prop("checked", false);
                _this.loadEdgeBoxs();
            });
            //搜索文本框-地址编码
            $("#addressCode_search").bind("input propertychange", function () {
                $(".currentPage").text("1");
                $("#check-all").prop("checked", false);
                _this.loadEdgeBoxs();
            });
            //搜索文本框-地址类型
            $("#addressTypeSelect").bind("input propertychange", function () {
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
                $("#addressCode_search").val("");
                $("#addressName_search").val("");
                $("#realAddress_search").val("");
                $("#addressTypeSelect").val("");
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
                _box_service.getAddressType1();
                $("#addressCode").val("");
                $("#addressName").val("");
                $("#realAddress").val("");
                $("#remark").val("");
                $("#id").val("");

                $(".fa-deviceInfoPop").hide();
                $("#formModel").modal({backdrop:false});
            });
            //编辑
            $(document).on('click', '.a-equipment-edit', function (e) {
                // 下拉框编辑 待研究
                e.stopPropagation();
                var _self = $(this);

                _box_service.getAddressType1();

                var id = _self.parent().parent().attr('id');

                $.ajax({
                    type	:"get",
                    url		: $.mpbGetHeaderPath()+"/wmsAddressRealRela/r/" + id,
                    headers:{'token':localStorage.getItem('token')},
                    // 0可用1入库中2出库中3占用4异常
                    data    : {},
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            var data=result.data;

                            var addressCode = data.addressCode;
                            var addressName = data.addressName;
                            var realAddress = data.realAddress;
                            var remark = data.remark;
                            var addressType = data.addressType;
                            var id = data.id;

                            $("#moveNodiv").show();
                            $("#addressCode").val(addressCode);
                            $("#addressName").val(addressName);
                            $("#realAddress").val(realAddress);
                            $("#remark").val(remark);
                            $("#id").val(id);

                            $('#addressType').val(addressType.split(","));
                            $('#addressType').chosen().trigger("chosen:updated");

                            $(".fa-deviceInfoPop").hide();
                            $("#formModel").modal({backdrop:false});
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
                $("#addressCode").val("");
                $("#addressName").val("");
                $("#realAddress").val("");
                $("#remark").val("");
                $(".fa-deviceInfoPop").hide();
                // $(".modal").hide();
            })
            //弹窗保存
            // $(document).on('click' ,'#modalSaveButton', function (e) {
            $("#modalSaveButton").on("click",function () {
                var addressCode = $("#addressCode").val();
                var addressName = $("#addressName").val();
                var realAddress = $("#realAddress").val();
                var remark = $("#remark").val();
                var addressType = $("#addressType").val();
                var id = $("#id").val();

                console.info("addressType:" + addressType);

                $(".fa-deviceInfoPop").hide();
                if(addressCode==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请填写地址编码！");
                    return false;
                }
                if(addressName==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请填写地址名称！");
                    return false;
                }
                if(realAddress==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请填写实际物理地址！");
                    return false;
                }
                for(var i=0; i<realAddress.length; i++){
                    if(realAddress.charAt(i)<'0' || realAddress.charAt(i)>'9'){
                        $(".fa-deviceInfoPop").show();
                        $(".fa-deviceInfoPop").text(" 实际物理地址只能是数字！");
                        return false;
                    }
                }
                if(realAddress.length > 10){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 实际物理地址必须小于10数字！");
                    return false;
                }
                if(addressType==null){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请选择地址类型！");
                    return false;
                }

                var data= {
                    id:id,
                    addressCode:addressCode,
                    addressName:addressName,
                    realAddress:realAddress,
                    addressType:addressType.toString(),
                    remark:remark
                };

                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/wmsAddressRealRela/create/edit",
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
                    Content: "确定要删除地址吗？",
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
            var _this      =this;
            var equipment  ={
                addressCode    : $.trim($("#addressCode_search").val()),
                addressName    : $.trim($("#addressName_search").val()),
                realAddress    : $.trim($("#realAddress_search").val()),
                addressType    : $.trim($("#addressTypeSelect").val()),
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
                str+='<tr id="'+list[ele].id+'" palletCode="'+list[ele].palletCode+'"goodsName="'+list[ele].goodsName+'"batchNo="'+list[ele].batchNo+'"moveNo="'+list[ele].moveNo+'"fromLocationCode="'+list[ele].fromLocationCode+'"toLocationCode="'+list[ele].toLocationCode+'">' +
                    '<td><div class="checkbox-custom checkbox-default"><input type="checkbox" >' +
                    '<label></label></div></td>'+
                    '<td class="addressCode">'+list[ele].addressCode+'</td>'+
                    '<td class="addressName">'+list[ele].addressName+'</td>'+
                    '<td class="addressType">'+list[ele].addressType+'</td>'+
                    '<td class="realAddress ">'+list[ele].realAddress+'</td>'+
                    '<td class="createBy ">'+list[ele].createBy+'</td>'+
                    '<td class="gmtCreate ">'+list[ele].gmtCreate+'</td>';

                str+='<td>';
                // if(list[ele].moveStatus == 1){
                    str += '<a class="link a-equipment-edit"><i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    str += '<a class="link a-equipment-delete" > <i class="fa-table-line-delete"  title="删除"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                // }
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
