var _buz_service,_frontBasic;
console.log(window.parent.operationData);

$(function(){
    _buz_service={
        //获取列表信息
        getDataList : function(data, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/wmsOutTemporaryBoxBarcode/list",
                headers:{'token':localStorage.getItem('token')},
                data    : data || {},
                dataType: "json",
                success	: resolve,
                error	: reject
            });
        },
        //获取供应商列表
        getSupplyList : function(data, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/dictItem/getDictType/supplier_type",
                headers:{'token':localStorage.getItem('token')},
                data    : { code :"supplier_type"},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var options = '';
                        options +='<option value=""></option>'
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.dicItemCode+'">'+ele.dicItemName+'</option>'
                        }

                        $("#supplierType").html(options);
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
            var serachOb = window.location.href.split("=")[1];
            var info={
                goodsCode: serachOb,
                orderNo : $.trim($('#orderNo').val()),
                sendNo : $.trim($('#sendNo').val()),
                page : $.trim($(".currentPage").text()),
                rows : _this.data.numberOnePage,
                orderBy : _this.data.orderBy
            };
            _buz_service.getDataList(info,function (res) {
                var result=res;
                console.log("res: " + res);
                console.log("jsonres: " + JSON.stringify(res));
                if(result.code==0) {
                    if(result.data.totalCount!=0){
                        var records=result.data.records;
                        var totalCount=result.data.totalCount;
                        _this.basicListSource(records);
                        if(totalCount>_this.data.numberOnePage){
                            $(".pagination-con").show();
                            _this.data.totalPageNum=totalCount;
                            _this.loadPagingNumber(_this.data.totalPageNum);
                        }
                        else {
                            $(".pagination-con").hide();
                        }
                    }
                    else {
                        $("#formTable tbody").empty();
                        console.log("查无数据！");
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
                tbody+='<tr data-id="'+list[ele].id+'">' +
                    '<td>'+list[ele].goodsCode+'</td>'+
                    '<td>'+list[ele].goodsName+'</td>'+
                    '<td>'+list[ele].batchNo+'</td>'+
                    '<td>'+list[ele].orderNo+'</td>'+
                    '<td>'+list[ele].sendNo+'</td>'+
                    '<td>'+list[ele].boxBarcode+'</td>';
                tbody += '</tr>';
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
        $("#orderNo").val("");
        $("#sendNo").val("");
    });
    $("#rtn").on("click",function () {
        window.location.href="WmsOutTemporaryList.html";
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
        $("#supplierId").val("");
        $("#supplierCode").val("");
        $("#supplierFullName").val("");
        $("#supplierShortName").val("");
        $("#supplierEngName").val("");
        $("#supplierType").val("");
        $("#address").val("");
        $("#contacts").val("");
        $("#website").val("");
        $("#myModalLabel").text('新增供应商');
        $("#formModel").modal({backdrop:false});
    });
    //新增或更新信息
    $("#saveButton").on("click",function () {
            var supplierId = $.trim($("#supplierId").val());
            console.log("supplierId",supplierId);
            var supplierCode = $.trim($("#supplierCode").val());
            var supplierFullName = $.trim($("#supplierFullName").val());
            var supplierShortName = $.trim($("#supplierShortName").val());
            var supplierEngName = $.trim($("#supplierEngName").val());
            var supplierType = $.trim($("#supplierType").val());
            var supplierLevel = $.trim($("#supplierLevel").val());
            var address = $.trim($("#address").val());
            var contacts = $.trim($("#contacts").val());
            var website = $.trim($("#website").val());
            //添加数据校验
            if(_commonFun.isBlank(supplierCode)){
                $.DialogByZ.Autofade({Content: "供应商编码不能为空"});
                return false;
            }
            if(_commonFun.isBlank(supplierFullName)){
                $.DialogByZ.Autofade({Content: "中文全称不能为空"});
                return false;
            }

            var data= {
                supplierCode:supplierCode,
                supplierFullName:supplierFullName,
                supplierShortName:supplierShortName,
                supplierEngName:supplierEngName,
                supplierType:supplierType,
                supplierLevel:supplierLevel,
                address:address,
                contacts:contacts,
                website:website
            };

            //判断是新增还是修改
            if (!_commonFun.isNotBlank(supplierId)){
                //不存在新增
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/supplier/c",
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
                data.supplierId = supplierId;
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/supplier/u",
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
function editOneRecord(supplierId,type) {
    var id = supplierId;
    $("#type").val(type);
    //供应商
    _buz_service.getSupplyList();
    //查看时，隐藏提交按钮 和禁用下拉框和input
    if(type == 0 ){
        $("#formInfo").find("i").hide();
        $("#myModalLabel").text('查看供应商信息');
        $("#saveButton").hide();
        $("form  select").prop("disabled",true);
        $("form  input").prop("disabled",true);
    }else if (type == 1){
        $("#formInfo").find("i").show();
        $("#myModalLabel").text('编辑供应商信息');
        //编辑时，显示 提交按钮和启用下拉框和 input
        $("#saveButton").show().text("保存");
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        $("#supplierCode").prop("disabled",true);

    }

    //查看备件信息 模态框弹出
    $.ajax({
        type	:"get",
        url		: $.mpbGetHeaderPath()+"/supplier/r/"+id,
        headers:{'token':localStorage.getItem('token')},
        data    : {id:id},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                if(data != null){
                    $("#supplierId").val(data.supplierId);
                    $("#supplierCode").val(data.supplierCode);
                    $("#supplierFullName").val(data.supplierFullName);
                    $("#supplierShortName").val(data.supplierShortName);
                    $("#supplierEngName").val(data.supplierEngName);
                    $("#supplierType").val(data.supplierType);
                    $("#supplierLevel").val(data.supplierLevel);
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
        Content:"确定要删除供应商信息?",
        FunL:function(){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/supplier/delete",
                headers:{'token':localStorage.getItem('token')},
                data    : {supplierId:id},
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

