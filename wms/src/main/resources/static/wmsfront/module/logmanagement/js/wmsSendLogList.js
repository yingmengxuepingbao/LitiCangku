var _buz_service,_frontBasic;
console.log(window.parent.operationData);

$(function(){
    _buz_service={
        //获取列表信息
        getDataList : function(data, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/sendlog/list",
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
                url		: $.mpbGetHeaderPath()+"/dictItem/getDictType/send_type",
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

                        // $("#customerType").html(options);
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
            var info={
                sendNo    : $.trim($('#sendNoPoP').val()),
                locationDesc          : $.trim($('#locationDescPoP').val()),
                currentPage     : $.trim($(".currentPage").text()),
                orderBy         : _this.data.orderBy,
                numberOnePage   : _this.data.numberOnePage
            };
            _buz_service.getDataList(info,function (res) {
                var result=res;
                console.log(res);
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
            var sendAmount ="";
            var outSeq ="";
            var layerNumber ="";
            var columnNumber ="";

            for(var ele in list){
                sendAmount =list[ele].sendAmount == null?"":list[ele].sendAmount;

                var hasBoxBarcode = list[ele].hasBoxCode == "1"?"有":"无";
                tbody+='<tr data-id="'+list[ele].sendId+'" data-sendNo ="'+list[ele].sendNo+'">' +
                    '<td>'+list[ele].sendNo+'</td>'+
                    '<td>'+list[ele].goodsCode+'</td>'+
                    '<td>'+list[ele].goodsName+'</td>'+
                    '<td>'+sendAmount+'</td>'+
                    '<td>'+hasBoxBarcode+'</td>'+
                    '<td>'+list[ele].createBy+'</td>'+
                    '<td>'+list[ele].gmtCreate+'</td>';
                // tbody += '<td>'+'<a onclick="editOneRecord(\''+list[ele].sendId+'\',1)"><i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                // tbody +=  '<a onclick="deleteOneRecord(\''+list[ele].sendId+'\')"><i class="fa-table-line-delete" title="删除"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                tbody += '<td>'+'<a  onclick="editSelect(\''+list[ele].sendNo+'\',\''+list[ele].goodsCode+'\')" > <i class="fa-table-line-look" title="查看箱码"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
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
        $("#sendNoPoP").val("");
        $("#locationDescPoP").val("");
    });
    //点击设备列表中的查看
    $(document).on('click', '.a-equipment-look', function (e) {
        var _self = $(this);
        //获取详情
        console.log(_self.parent().parent());
        alert(_self.parent().parent());
        var id = _self.parent().parent().data('sendNo');
        window.location.href="addEdgeBox.html?id="+id;
    })
    //confirmPerson chosen change事件
    $(document).on('change', '#confirmPerson', function (e){
        $("#confirmPerson").val($("#confirmPerson option:selected").attr("value"));
    });
    //新增
    $("#addButton").on("click",function () {
        //供应商
        _buz_service.getSupplyList();
        _buz_service.getWarehouseCode();
        _buz_service.getAreaCodeAll();
        //备件确认人
        // $("#confirmPerson").prop("disabled",false);
        //显示 提交按钮和启用下拉框和 input
        $("#saveButton").show().text("保存");
        $("#formInfo").find("i").show();
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        // $("#sparePartCode").prop("disabled",true);
        $("#sendId").val("");
        $("#warehouseCode").val("");
        $("#areaCode").val("");
        $("#sendNo").val("");
        $("#locationDesc").val("");
        $("#locationAttr").val("");
        $("#palletCode").val("");
        $("#inSeq").val("");
        $("#outSeq").val("");
        $("#layerNumber").val("");
        $("#columnNumber").val("");
        $("#useStatus").val("");
        $("input[name='allowMix']").each(function () {
            if($(this).val()=="0"){
                $(this).prop("checked","checked");
            }
        });
        $("#myModalLabel").text('新增库位');
        $("#formModel").modal({backdrop:false});
    });
    //新增或更新信息
    $("#saveButton").on("click",function () {
            var sendId = $.trim($("#sendId").val());
            console.log("sendId",sendId);
            var warehouseCode = $.trim($("#warehouseCode").val());
            var areaCode = $.trim($("#areaCode").val());
            var sendNo = $.trim($("#sendNo").val());
            var locationDesc = $.trim($("#locationDesc").val());
            var locationAttr = $.trim($("#locationAttr").val());
            var palletCode = $.trim($("#palletCode").val());
            var inSeq = $.trim($("#inSeq").val());
            var outSeq = $.trim($("#outSeq").val());
            var layerNumber = $.trim($("#layerNumber").val());
            var columnNumber = $.trim($("#columnNumber").val());
            var useStatus = $.trim($("#useStatus").val());
            var allowMix;
            $("input[name='allowMix']").each(function () {
                if($(this).prop("checked")){
                    allowMix = $(this).val();
                }
            });
            //添加数据校验
            if(_commonFun.isBlank(sendNo)){
                $.DialogByZ.Autofade({Content: "单号不能为空"});
                return false;
            }
            if(_commonFun.isBlank(locationDesc)){
                $.DialogByZ.Autofade({Content: "库位描述不能为空"});
                return false;
            }

            var data= {
                warehouseCode:warehouseCode,
                areaCode:areaCode,
                sendNo:sendNo,
                locationDesc:locationDesc,
                locationAttr:locationAttr,
                palletCode:palletCode,
                inSeq:inSeq,
                outSeq:outSeq,
                layerNumber:layerNumber,
                columnNumber:columnNumber,
                useStatus:useStatus,
                allowMix:allowMix
            };

            //判断是新增还是修改
            if (!_commonFun.isNotBlank(sendId)){
                //不存在新增
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/sendlog/c",
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
                data.sendId = sendId;
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/sendlog/u",
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
//点击设备列表中的查看
// $(document).on('click', '.a-equipment-look', function (e) {
//     var _self = $(this);
//     //获取详情
//     console.log(_self.parent().parent());
//     alert(_self.parent().parent());
//     var id = _self.parent().parent().data('sendNo');
//     window.location.href="addEdgeBox.html?id="+id;
// })

function editSelect(sendNo,goodsCode) {

    // var _self = $(this);
    //获取详情

    // var id = _self.parent().parent().data('sendNo');
    window.location.href="addSendLog.html?sendNo="+sendNo+"&goodsCode="+goodsCode;
}

//编辑 type  0 表示 查看  1 表示 编辑  2表示 审核
function editOneRecord(sendId,type) {
    var id = sendId;
    $("#type").val(type);
    //供应商
    _buz_service.getSupplyList();
    _buz_service.getWarehouseCode();
    _buz_service.getAreaCodeAll();
    //查看时，隐藏提交按钮 和禁用下拉框和input
    if(type == 0 ){
        $("#formInfo").find("i").hide();
        $("#myModalLabel").text('查看库位信息');
        $("#saveButton").hide();
        $("form  select").prop("disabled",true);
        $("form  input").prop("disabled",true);
    }else if (type == 1){
        $("#formInfo").find("i").show();
        $("#myModalLabel").text('编辑库位信息');
        //编辑时，显示 提交按钮和启用下拉框和 input
        $("#saveButton").show().text("保存");
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        $("#sendNo").prop("disabled",true);

    }

    //查看备件信息 模态框弹出
    $.ajax({
        type	:"get",
        url		: $.mpbGetHeaderPath()+"/sendlog/r/"+id,
        headers:{'token':localStorage.getItem('token')},
        data    : {id:id},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                if(data != null){
                    $("#sendId").val(data.sendId);
                    $("#warehouseCode").val(data.warehouseCode);
                    $("#areaCode").val(data.areaCode);
                    $("#sendNo").val(data.sendNo);
                    $("#locationDesc").val(data.locationDesc);
                    $("#locationAttr").val(data.locationAttr);
                    $("#palletCode").val(data.palletCode);
                    $("#inSeq").val(data.inSeq);
                    $("#outSeq").val(data.outSeq);
                    $("#layerNumber").val(data.layerNumber);
                    $("#columnNumber").val(data.columnNumber);
                    $("#useStatus").val(data.useStatus);
                    $("input[name='allowMix']").each(function () {
                        if($(this).val()==data.allowMix){
                            $(this).prop("checked",true);
                        }else{
                            $(this).prop("checked",false);
                        }
                    });
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

//删除备件
function deleteOneRecord(id) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要删除库位信息?",
        FunL:function(){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/sendlog/delete",
                headers:{'token':localStorage.getItem('token')},
                data    : {sendId:id},
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

