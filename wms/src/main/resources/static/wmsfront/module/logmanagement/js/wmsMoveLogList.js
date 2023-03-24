var _buz_service,_frontBasic;
console.log(window.parent.operationData);

$(function(){
    _buz_service={
        //获取列表信息
        getDataList : function(data, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/moveLog/list",
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
                url		: $.mpbGetHeaderPath()+"/dictItem/getDictType/moveLog_type",
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
            this.initDatetime();
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

            var gmtCreateMin = $.trim($('#createDate_begin').val());
            if(gmtCreateMin!=""&&gmtCreateMin!=null){
                gmtCreateMin = $.trim($('#createDate_begin').val()) + " 00:00:00";
            }
            var gmtCreateMax = $.trim($('#createDate_end').val());
            if(gmtCreateMax!=""&&gmtCreateMax!=null){
                gmtCreateMax = $.trim($('#createDate_end').val()) + " 23:59:59";
            }
            var info={
                palletCode    : $.trim($('#palletCodePoP').val()),
                fromLocationCode    : $.trim($('#fromLocationCodePoP').val()),
                toLocationCode : $.trim($('#toLocationCodePoP').val()),
                gmtCreateMin  : gmtCreateMin ,
                gmtCreateMax  : gmtCreateMax ,
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
            for(var ele in list){
                var moveResultText ="";
                var moveResult = list[ele].moveResult;
                if(moveResult =="1"){
                    moveResultText = "成功";
                }else if(moveResult =="2"){
                    moveResultText = "失败";
                }else if (moveResult == "0"){
                    moveResultText = "启动";
                }
                tbody+='<tr data-id="'+list[ele].customerId+'">' +
                    '<td>'+list[ele].palletCode+'</td>'+
                    '<td>'+list[ele].goodsCode+'</td>'+
                    '<td>'+list[ele].fromLocationCode+'</td>'+
                    '<td>'+list[ele].batchNo+'</td>'+
                    '<td>'+list[ele].toLocationCode+'</td>'+
                    '<td>'+list[ele].amount+'</td>'+
                    // '<td>'+list[ele].warehouseCode+'</td>'+
                    '<td>'+list[ele].areaCode+'</td>'+
                    '<td>'+moveResultText+'</td>'+
                    '<td>'+list[ele].createBy+'</td>'+
                    '<td>'+list[ele].gmtCreate+'</td>';
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
        initDatetime :function  () {
            $(".datainp").jeDate({
                festival: false,
                ishmsVal: false,
                initAddVal:[0],
                format: "YYYY-MM-DD",
                zIndex: 3000
            });
        }

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
        $("#palletCodePoP").val("");
        $("#fromLocationCodePoP").val("");
        $("#toLocationCodePoP").val("");
        $("#createDate_begin").val("");
        $("#createDate_end").val("");
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
        $("#customerId").val("");
        $("#palletCode").val("");
        $("#fromLocationCode").val("");
        $("#customerEngName").val("");
        $("#customerType").val("");
        $("#address").val("");
        $("#contacts").val("");
        $("#website").val("");
        $("#myModalLabel").text('新增客户');
        $("#formModel").modal({backdrop:false});
    });
    //新增或更新信息
    $("#saveButton").on("click",function () {
            var customerId = $.trim($("#customerId").val());
            console.log("customerId",customerId);
            var palletCode = $.trim($("#palletCode").val());
            var fromLocationCode = $.trim($("#fromLocationCode").val());
            var customerEngName = $.trim($("#customerEngName").val());
            var customerType = $.trim($("#customerType").val());
            var customerLevel = $.trim($("#customerLevel").val());
            var address = $.trim($("#address").val());
            var contacts = $.trim($("#contacts").val());
            var website = $.trim($("#website").val());
            //添加数据校验
            if(_commonFun.isBlank(palletCode)){
                $.DialogByZ.Autofade({Content: "客户编码不能为空"});
                return false;
            }
            if(_commonFun.isBlank(fromLocationCode)){
                $.DialogByZ.Autofade({Content: "中文全称不能为空"});
                return false;
            }

            var data= {
                palletCode:palletCode,
                fromLocationCode:fromLocationCode,
                customerEngName:customerEngName,
                customerType:customerType,
                customerLevel:customerLevel,
                address:address,
                contacts:contacts,
                website:website
            };

            //判断是新增还是修改
            if (!_commonFun.isNotBlank(customerId)){
                //不存在新增
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/moveLog/c",
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
                data.customerId = customerId;
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/moveLog/u",
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
function editOneRecord(customerId,type) {
    var id = customerId;
    $("#type").val(type);
    //供应商
    _buz_service.getSupplyList();
    //查看时，隐藏提交按钮 和禁用下拉框和input
    if(type == 0 ){
        $("#formInfo").find("i").hide();
        $("#myModalLabel").text('查看客户信息');
        $("#saveButton").hide();
        $("form  select").prop("disabled",true);
        $("form  input").prop("disabled",true);
    }else if (type == 1){
        $("#formInfo").find("i").show();
        $("#myModalLabel").text('编辑客户信息');
        //编辑时，显示 提交按钮和启用下拉框和 input
        $("#saveButton").show().text("保存");
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        $("#palletCode").prop("disabled",true);

    }

    //查看备件信息 模态框弹出
    $.ajax({
        type	:"get",
        url		: $.mpbGetHeaderPath()+"/moveLog/r/"+id,
        headers:{'token':localStorage.getItem('token')},
        data    : {id:id},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                if(data != null){
                    $("#customerId").val(data.customerId);
                    $("#palletCode").val(data.palletCode);
                    $("#fromLocationCode").val(data.fromLocationCode);
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

//删除备件
function deleteOneRecord(id) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要删除客户信息?",
        FunL:function(){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/moveLog/delete",
                headers:{'token':localStorage.getItem('token')},
                data    : {customerId:id},
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

