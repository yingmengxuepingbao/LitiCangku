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
        // 获取月台
        getyuetai : function(resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/dictItem/getDictType/platform",
                headers:{'token':localStorage.getItem('token')},
                data    : { code :"platform"},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var options = '';
                        options +='<option value=""></option>'
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.dicItemCode+'">'+ele.dicItemName+'</option>'
                        }

                        $("#outAddress").html(options);
                    }
                },
                error	: reject
            });
        },
        //根据筛选条件获取EdgeBox列表
        getEdgeBoxs: function(edgeBoxInfo,resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/basePdaVersion/list",
                headers:{'token':localStorage.getItem('token')},
                data	: edgeBoxInfo,
                success	: resolve,
                error	: reject
            });
        },
        //删除出库单
        deleteEdgeBoxInfo: function(id,resolve, reject){
            var data = {
                moveId  :  id
            }
            $.ajax({
                type		:"post",
                url		: $.mpbGetHeaderPath()+"/wmsMoveStereoscopic/d",
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
            //搜索文本框-版本编号
            $("#base_pda_version_id_search").bind("input propertychange", function () {
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
                $("#base_pda_version_id_search").val("");
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
                $("#moveNodiv").hide();
                $("#moveNo").val("");
                $("#fromLocationCode").val("");
                $("#toLocationCode").val("");
                $("#palletCode").val("");
                $("#goodsCode").val("");
                $("#goodsName").val("");
                $("#batchNo").val("");
                $(".fa-deviceInfoPop").hide();
                $("#formModel").modal({backdrop:false});
            });
            //编辑
            $(document).on('click', '.a-equipment-edit', function (e) {
                // 下拉框编辑 待研究
                e.stopPropagation();
                var _self = $(this);
                // _box_service.getLocationCode();
                var basePdaVersionId = _self.parent().parent().attr('id');
                var ip = _self.parent().parent().attr('ip');
                var port = _self.parent().parent().attr('port');
                var appName = _self.parent().parent().attr('appName');
                var url = _self.parent().parent().attr('url');
                var version = _self.parent().parent().attr('version');
                var wgtVersion = _self.parent().parent().attr('wgtVersion');

                $("#basePdaVersionId").val(basePdaVersionId);
                $("#ip").val(ip);
                $("#port").val(port);
                $("#appName").val(appName);
                $("#url").val(url);
                $("#version").val(version);
                $("#wgtVersion").val(wgtVersion);
                $(".fa-deviceInfoPop").hide();
                $("#formModel").modal({backdrop:false});
            })

            // 弹出二维码
            $(document).on('click', '.a-equipment-bar', function (e) {
                // 下拉框编辑 待研究
                e.stopPropagation();
                var _self = $(this);

                var basePdaVersionId = _self.parent().parent().attr('id');
                var url = $.mpbGetHeaderPath()+"/expose/basePdaVersion/qrCode/" + basePdaVersionId;
                $("#bar").attr('src', url);
                $("#barModel").modal({backdrop:false});
            })

            //弹窗关闭
            $(document).on('click' ,'#modalCancelButton', function (e) {
                $("#locationCode").val("");
                $("#goodsCode").val("");
                $("#batchNo").val("");
                $("#remark").val("");
                $(".fa-deviceInfoPop").hide();
                // $(".modal").hide();
            })
            //弹窗保存
            // $(document).on('click' ,'#modalSaveButton', function (e) {
            $("#modalSaveButton").on("click",function () {
                var basePdaVersionId = $("#basePdaVersionId").val();
                var ip = $("#ip").val();
                var port = $("#port").val();
                var appName = $("#appName").val();
                var url = $("#url").val();
                var version = $("#version").val();
                var wgtVersion = $("#wgtVersion").val();

                $(".fa-deviceInfoPop").hide();
                if(ip==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请选填写服务器IP！");
                    return false;
                }
                if(port==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请选填写服务器端口！");
                    return false;
                }
                if(appName==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请选填写应用名称！");
                    return false;
                }
                if(url==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请选填写初始APK！");
                    return false;
                }
                if(version==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请选填写初始版本！");
                    return false;
                }
                if(wgtVersion==""){
                    $(".fa-deviceInfoPop").show();
                    $(".fa-deviceInfoPop").text(" 请选填写更新版本！");
                    return false;
                }

                var data= {
                    basePdaVersionId:basePdaVersionId,
                    ip:ip,
                    port:port,
                    appName:appName,
                    url:url,
                    version:version,
                    wgtVersion:wgtVersion
                };

                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/basePdaVersion/update",
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
                    Content: "确定要删除移库单吗？",
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
                    Content: "确定要启动该移库任务吗？",
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
                basePdaVersionId    : $.trim($("#base_pda_version_id_search").val()),
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
                str+='<tr id="'+list[ele].basePdaVersionId+'" ip="'+list[ele].ip+'"port="'+list[ele].port+'"appName="'+list[ele].appName+'"url="'+list[ele].url+'"version="'+list[ele].version+'"wgtVersion="'+list[ele].wgtVersion+'">' +
                    '<td class="basePdaVersionId">'+list[ele].basePdaVersionId+'</td>'+
                    '<td class="ip">'+list[ele].ip+'</td>'+
                    '<td class="port">'+list[ele].port+'</td>'+
                    '<td class="appName ">'+list[ele].appName+'</td>'+
                    '<td class="url ">'+list[ele].url+'</td>'+
                    '<td class="version ">'+list[ele].version+'</td>'+
                    '<td class="wgtVersion">'+list[ele].wgtVersion+'</td>'+
                    '<td class="lastModifiedBy">'+list[ele].lastModifiedBy+'</td>'+
                    '<td class="gmtModified">'+list[ele].gmtModified+'</td>';

                str+='<td>';
                str += '<a class="link a-equipment-edit"><i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                str += '<a class="link a-equipment-bar" > <i class="fa-table-line-qrcode"  title="二维码"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
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
