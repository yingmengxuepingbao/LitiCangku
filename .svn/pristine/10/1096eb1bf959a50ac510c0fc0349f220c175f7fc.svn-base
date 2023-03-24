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
                        var commonDevice = "<option value=''>请选择商品</option>";
                        for (var i = 0; i <data.length; i++) {
                            var item = data[i];
                            // if(receiverIds.length > 0 && $.inArray(item.dicItemCode.toString(),receiverIds) > -1){
                            //     device += "<option value='"+item.locationCode+"' selected >"+item.locationCode+"</option>";
                            // }else{
                            device += "<option value='"+item.dicItemCode+"'>"+item.dicItemName + "</option>";
                            // }
                        }
                        console.log("device:" + device);
                        $("#goodsCodeSelect").html(commonDevice + device);

                        $('#goodsCodeSelect').chosen().trigger("chosen:updated");
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
                url		: $.mpbGetHeaderPath()+"/wmsOrderCheck/pallet/boxBarcode/list",
                headers:{'token':localStorage.getItem('token')},
                data	: edgeBoxInfo,
                success	: resolve,
                error	: reject
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
            _box_service.getGoodsCode();
            this.loadEdgeBoxs();//加载EdgeBox列表
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
            //搜索文本框-箱码
            $("#boxBarcode_search").bind("input propertychange", function () {
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
            //点击返回
            $(document).on('click', '#back', function () {
                var orderNo = window.location.href.split("=")[1].split(",")[0];
                window.location.href="wmsOrderCheckPalletList.html?orderNo=" + orderNo;
            });
            //点击重置筛选条件
            $(document).on('click', '#resetSearchConditions', function () {
                $("#boxBarcode_search").val("");
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
        },

        //EdgeBox列表
        loadEdgeBoxs: function () {
            var _this      =this;
            var orderNo = window.location.href.split("=")[1].split(",")[0];
            var palletCode = window.location.href.split("=")[1].split(",")[1];
            $("#palletDiff").href = "wmsOrderCheckPalletList.html?orderNo="+orderNo;
            var equipment  ={
                orderNo : orderNo,
                palletCode    : palletCode,
                boxBarcode    : $.trim($("#boxBarcode_search").val()),
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
                        $.DialogByZ.Autofade({Content: "查无数据！"});
                        $("#edgeBoxList tbody").empty();
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
            var diffFlagText = '';
            var str = '';
            for (var ele in list) {
                var diffFlag = list[ele].diffFlag;
                if(diffFlag =="1"){
                    diffFlagText = "是";
                }else if(diffFlag =="0"){
                    diffFlagText = "否";
                }

                // str+='<tr id="'+list[ele].locationId+'" locationCode="'+list[ele].locationCode+'">' +
                str+='<tr id="'+list[ele].checkId+'" orderNo="'+list[ele].orderNo+'"goodsName="'+list[ele].goodsName+'"batchNo="'+list[ele].batchNo+'"moveNo="'+list[ele].moveNo+'"fromLocationCode="'+list[ele].fromLocationCode+'"toLocationCode="'+list[ele].toLocationCode+'">' +
                    '<td><div class="checkbox-custom checkbox-default"><input type="checkbox" >' +
                    '<label></label></div></td>'+
                    '<td class="orderNo">'+list[ele].orderNo+'</td>'+
                    '<td class="palletCode ">'+list[ele].palletCode+'</td>'+
                    '<td class="boxBarcode ">'+list[ele].boxBarcode+'</td>'+
                    '<td class="realBoxBarcode ">'+list[ele].realBoxBarcode+'</td>'+
                    '<td class="diffFlag">'+diffFlagText+'</td>';
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