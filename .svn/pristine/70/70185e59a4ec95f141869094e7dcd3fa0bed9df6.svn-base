var _buz_service,_frontBasic;
console.log(window.parent.operationData);
let nameJson = [
    // {
    //     name:'123'
    // },{
    //     name:'456'
    // }
];
let newVar = [];
$(function(){
    _buz_service={

        //搜索商品
        getGoodsCodeSelect: function(resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/goods/getGoodsCodeAll",
                headers:{'token':localStorage.getItem('token')},
                async:false,
                success	: function (res) {
                    var result=res;
                    if(result.code==0) {
                        // console.log("what?",result);
                        var data=result.data;
                        $(".chosen-select").html("");
                        // _this.chose_get_ini(".chosen-select");
                        var device = "<option value=''>请选择商品</option>";
                        // console.log("what?data[0].dicItemCode",data[0].dicItemCode);
                        for (var i = 0; i <data.length; i++) {
                            var goodsName = data[i].dicItemName.toString();
                            var goodsCode = data[i].dicItemCode.toString();
                            if(goodsName == "" || goodsName=="undefined"){
                                goodsName = "''";
                            }
                            if(goodsCode == "" || goodsCode=="undefined"){
                                goodsCode = "''";
                            }
                            device += "<option  value='"+goodsCode+"' goodsName='"+goodsName+"'>" + goodsName + "(" + goodsCode + ")</option>";
                        }

                        $(".chosen-select").html(device);
                        // _this.chose_get_ini(".chosen-select");

                        $(".chosen-select").chosen({
                            "width":280,
                            no_results_text : "未找到此选项!"
                        });
                        $(".chosen-select").trigger("chosen:updated");
                    }
                },
                error	: reject
            });
        },
        //获取列表信息
        getDataList : function(data, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/pallet/list",
                headers:{'token':localStorage.getItem('token')},
                data    : data || {},
                success	: resolve,
                error	: reject
            });
        },
        //获取箱码
        getHasBoxByGoods : function(data, resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/goods/queryByCode/"+data,
                headers:{'token':localStorage.getItem('token')},
                data    : { goodsCode :data},
                success	: resolve,
                error	: reject
            });
        },
        //获取供应商列表
        getSupplyList : function(data, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/dictItem/getDictType/pallet_type",
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
        getlocationCodeAll : function(data, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/location/getlocationCodeAll",
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
                        $("#locationCode").html(options);
                    }
                },
                error	: reject
            });
        },
        getGoodsCodeAll : function(data, reject){
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
                        // $("#goodsCode").html(options);
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
            $("#warehouseCodeHidden").css("display","none");
            $("#areaCodeHidden").css("display","none");
            $("#locationCodeHidden").css("display","none");
        },
        onLoad: function () {
            this.loadBaseGoods();
            this.loadBasicList();
            // $(".chosen-select").chosen();
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
                goodsCode    : $.trim($('#goodsCodePoP').val()),
                palletCode         : $.trim($('#palletCodePoP').val()),
                currentPage     : $.trim($(".currentPage").text()),
                orderBy         : _this.data.orderBy,
                numberOnePage   : _this.data.numberOnePage,
                orderBy: "gmt_create desc"
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
            var amount ="";
            var batchNo ="";


            for(var ele in list){
                amount =list[ele].amount == null?"":list[ele].amount;
                batchNo =list[ele].batchNo == null?"":list[ele].batchNo;

                tbody+='<tr data-id="'+list[ele].palletId+'">' +
                    // '<td>'+list[ele].warehouseCode+'</td>'+
                    '<td style="width: 4%">'+list[ele].areaCode+'</td>'+
                    '<td style="width: 7%">'+list[ele].locationCode+'</td>'+
                    '<td style="width: 13%">'+list[ele].palletCode+'</td>'+
                    '<td style="width: 10%">'+list[ele].goodsCode+'</td>'+
                    '<td style="width: 14%">'+list[ele].goodsName+'</td>'+
                    '<td style="width: 3%">'+amount+'</td>'+
                    '<td style="width: 7%">'+batchNo+'</td>'+
                    // '<td style="width: 5%">'+list[ele].hasBoxCode+'</td>'+
                    '<td style="width: 22%">'+list[ele].boxBarcodeList+'</td>'+
                    '<td style="width: 10%">'+list[ele].lockBy+'</td>';
                tbody += '<td style="width: 10%">'+'<a onclick="editOneRecord(\''+list[ele].palletId+'\',1)"><i >绑定</i></a>&nbsp;&nbsp;';
                tbody +=  '<a onclick="relieve(\''+list[ele].palletId+'\')"><i >解绑</i></a>&nbsp;&nbsp;';
                tbody +=  '<a onclick="deleteOneRecord(\''+list[ele].palletId+'\')"><i class="fa-table-line-delete" title="删除"></i></a>&nbsp;&nbsp;';
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
        //多选select 数据同步
        chose_get_ini : function (select){
            $(select).chosen().change(function(){$(select).trigger("liszt:updated");});
        },
        //------获取商品列表
        loadBaseGoods: function () {
            var _this=this;
            _buz_service.getGoodsCodeSelect();
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
        $("#goodsCodePoP").val("");
        $("#palletCodePoP").val("");
    });
    //点击设备列表中的查看
    $(document).on('click', '.a-equipment-look', function (e) {
        var _self = $(this);
        //获取详情
        console.log(_self.parent().parent());
        alert(_self.parent().parent());
        var id = _self.parent().parent().data('goodsCode');
        window.location.href="addEdgeBox.html?id="+id;
    })
    //confirmPerson chosen change事件
    $(document).on('change', '#confirmPerson', function (e){
        $("#confirmPerson").val($("#confirmPerson option:selected").attr("value"));
    });
    $(document).on('change', '#goodsCode', function () {
        var goodsCode = $.trim($("#goodsCode").val());
        // findDevices(deptId);
        console.log("goodsCode",goodsCode);
        _buz_service.getHasBoxByGoods(goodsCode,function (res) {
            console.log("getHasBoxByGoods",res);
            var result=res;
            if(result.code == 0){
                if (result.data!=null){
                    if(result.data.hasBoxCode!=""){
                        $("#hasBoxCode").val(result.data.hasBoxCode);
                        if(result.data.hasBoxCode == "1"){
                            $("#testHidden").css("display","");
                            $("#testBoxHidden").css("display","");
                            $("#amount").prop("disabled",true);
                        }else {
                            $("#testHidden").css("display","none");
                            $("#testBoxHidden").css("display","none");
                            $("#amount").prop("disabled",false);
                        }
                    }
                }
            }
        });
    })
    //新增
    $("#addButton").on("click",function () {
        //供应商
        _buz_service.getSupplyList();
        // _buz_service.getWarehouseCode();
        // _buz_service.getAreaCodeAll();
        // _buz_service.getlocationCodeAll();
        // _buz_service.getGoodsCodeAll();
        //备件确认人
        // $("#confirmPerson").prop("disabled",false);
        //显示 提交按钮和启用下拉框和 input
        $("#saveButton").show().text("保存");
        $("#formInfo").find("i").show();
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        // $("#sparePartCode").prop("disabled",true);
        $("#palletId").val("");
        $("#warehouseCode").val("");
        $("#areaCode").val("");
        $("#goodsCode").val("");
        $("#goodsName").val("");
        // $("#lockBy").val("");
        $("#palletCode").val("");
        $("#amount").val("");
        $("#batchNo").val("");
        $("#testInput").val("");

        $(".chosen-select").val("");
        $(".chosen-select").chosen();
        $(".chosen-select").trigger("chosen:updated");

        nameJson =[];
        var htmlEmpyLL= insertName(nameJson);
        newVar = nameJson.map(function (a) {
            return a.name;
        })
        $("#textTest").html(htmlEmpyLL);
        $("#testHidden").css("display","none");
        $("#testBoxHidden").css("display","none");
        $("#goodsCodeHidden").css("display","none");
        $("#batchNoHidden").css("display","none");
        $("#amountHidden").css("display","none");
        $("#hasBoxCodeHidden").css("display","none");
        $("#amount").prop("disabled",false);
        $("#myModalLabel").text('新增托盘');
        $("#formModel").modal({backdrop:false});
    });






    $("#testButton").on("click",function () {
        var testInput = $.trim($("#testInput").val());
        console.log("testInput",testInput);
        if (testInput!="") {

            console.log("newVar , check " , newVar);
            if(newVar.indexOf(testInput)!=-1){
                $.DialogByZ.Autofade({Content:"此箱码已在列表中！"});
                return;
            }

            nameJson.push({name:testInput});
            var htmlEmpy= insertName(nameJson);
            $("#textTest").html(htmlEmpy);
            var amount = $.trim($("#amount").val());
            $("#amount").val(Number(amount)+1);
        }

    });
    //新增或更新信息
    $("#saveButton").on("click",function () {
            var palletId = $.trim($("#palletId").val());
            console.log("palletId",palletId);
            var warehouseCode = $.trim($("#warehouseCode").val());locationCode
            var areaCode = $.trim($("#areaCode").val());
            var locationCode = $.trim($("#locationCode").val());
            var goodsCode = $.trim($("#goodsCode").val());
            var goodsName = $.trim($("#goodsName").val());
            // var lockBy = $.trim($("#lockBy").val());
            var palletCode = $.trim($("#palletCode").val());
            var amount = $.trim($("#amount").val());
            var batchNo = $.trim($("#batchNo").val());

            //添加数据校验
            if(_commonFun.isBlank(palletCode)){
                $.DialogByZ.Autofade({Content: "托盘编码不能为空"});
                return false;
            }


            var data= {
                warehouseCode:warehouseCode,
                areaCode:areaCode,
                locationCode:locationCode,
                goodsCode:goodsCode,
                goodsName:goodsName,
                // lockBy:lockBy,
                palletCode:palletCode,
                amount:amount,
                batchNo:batchNo,
                boxList:newVar
            };
            //判断是新增还是修改
            if (!_commonFun.isNotBlank(palletId)){
                //不存在新增
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/pallet/c",
                    headers:{'Content-Type': 'application/json','token':localStorage.getItem('token')},
                    data    : JSON.stringify(data),
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
                if(_commonFun.isBlank(goodsCode)){
                    $.DialogByZ.Autofade({Content: "商品编码不能为空"});
                    return false;
                }
                if(_commonFun.isBlank(amount)){
                    $.DialogByZ.Autofade({Content: "数量不能为空"});
                    return false;
                }
                console.log("amount",amount);
                if(amount==0){
                    $.DialogByZ.Autofade({Content: "数量不能为0"});
                    return false;
                }
                if(_commonFun.isBlank(batchNo)){
                    $.DialogByZ.Autofade({Content: "批次不能为空"});
                    return false;
                }
                //存在id修改
                data.palletId = palletId;
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/pallet/u",
                    headers:{'Content-Type': 'application/json','token':localStorage.getItem('token')},
                    data    : JSON.stringify(data),
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
                        $.DialogByZ.Autofade({Content:result.message || "绑定失败！"});
                        // _commonFun.errorTips("错误");
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
//     var id = _self.parent().parent().data('goodsCode');
//     window.location.href="addEdgeBox.html?id="+id;
// })

// function editSelect(goodsCode) {
//     // var _self = $(this);
//     //获取详情
//
//     // var id = _self.parent().parent().data('goodsCode');
//     window.location.href="addEdgeBox.html?goodsCode="+goodsCode;
// }

//编辑 type  0 表示 查看  1 表示 编辑  2表示 审核
function editOneRecord(palletId,type) {
    var id = palletId;
    $("#type").val(type);
    //供应商
    _buz_service.getSupplyList();
    // _buz_service.getWarehouseCode();
    // _buz_service.getAreaCodeAll();
    // _buz_service.getlocationCodeAll();
    // _buz_service.getGoodsCodeAll();

    $("#testHidden").css("display","none")
    $("#testBoxHidden").css("display","none")
    $("#goodsCodeHidden").css("display","");
    $("#batchNoHidden").css("display","");
    $("#amountHidden").css("display","");
    $("#hasBoxCodeHidden").css("display","");
    //查看时，隐藏提交按钮 和禁用下拉框和input
    if(type == 0 ){

        $("#formInfo").find("i").hide();
        $("#myModalLabel").text('查看托盘信息');
        $("#saveButton").hide();
        $("form  select").prop("disabled",true);
        $("form  input").prop("disabled",true);
        $("#amount").prop("disabled",false);
    }else if (type == 1){
        $("#formInfo").find("i").show();
        $("#myModalLabel").text('托盘绑定商品');
        //编辑时，显示 提交按钮和启用下拉框和 input
        $("#saveButton").show().text("保存");
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        $("#palletCode").prop("disabled",true);
        $("#hasBoxCode").prop("disabled",true);
        $("#amount").prop("disabled",false);



    }

    //查看备件信息 模态框弹出
    $.ajax({
        type	:"get",
        url		: $.mpbGetHeaderPath()+"/pallet/r/"+id,
        headers:{'token':localStorage.getItem('token')},
        data    : {id:id},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                _buz_service.getGoodsCodeSelect();
                if(data != null){
                    nameJson =[];
                    var htmlEmpyLL= insertName(nameJson);
                    newVar = nameJson.map(function (a) {
                        return a.name;
                    })
                    $("#textTest").html(htmlEmpyLL);

                    $("#palletId").val(data.palletId);
                    $("#warehouseCode").val(data.warehouseCode);
                    $("#areaCode").val(data.areaCode);
                    $("#locationCode").val(data.locationCode);
                    $("#goodsCode").val(data.goodsCode);
                    //更新
                    $(".chosen-select").val(data.goodsCode);
                    $(".chosen-select").chosen();
                    $(".chosen-select").trigger("chosen:updated");
                    $("#goodsName").val(data.goodsName);
                    // $("#lockBy").val(data.lockBy);
                    $("#palletCode").val(data.palletCode);
                    $("#amount").val(data.amount);

                    $("#batchNo").val(data.batchNo);
                    $("#hasBoxCode").val(data.hasBoxCode);
                    console.log("data.hasBoxCode",data.hasBoxCode)
                    if(data.hasBoxCode == 1){
                        $("#testHidden").css("display","");
                        $("#testBoxHidden").css("display","");
                        $("#amount").prop("disabled",true);
                    }else {
                        $("#amount").prop("disabled",false);
                    }
                    console.log("boxBarcodeList",data.boxBarcodeList);

                    var str = data.boxBarcodeList; //这是一字符串
                    if(str != ""){
                        var strs = new Array(); //定义一数组
                        strs = str.split(","); //字符分割

                        for (i=0; i<strs.length; i++ ){
                            nameJson.push({name:strs[i]});
                        }
                        console.log("nameJson1",nameJson)
                        var htmlEmpyLL= insertName(nameJson);
                        newVar = nameJson.map(function (a) {
                            return a.name;
                        })
                        $("#textTest").html(htmlEmpyLL);
                    }

                }
                //显示模态框
                $("#formModel").modal({backdrop:false});
            }else {
                $.DialogByZ.Autofade({Content: result.message});
                return false;
            }
        },
        error	: function () {
            _commonFun.errorTips('');
        }
    });

}

//解绑
function relieve(id) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要解绑托盘信息?",
        FunL:function(){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/pallet/relieve",
                headers:{'token':localStorage.getItem('token')},
                data    : {palletId:id},
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
//删除托盘信息
function deleteOneRecord(id) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要删除托盘信息?",
        FunL:function(){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/pallet/delete",
                headers:{'token':localStorage.getItem('token')},
                data    : {palletId:id},
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
function deleteTest(id) {
     nameJson = nameJson.filter( function (a) {
        return a.name != id;
    } )
    console.log(nameJson);
    var htmlEmpyLL= insertName(nameJson);
    newVar = nameJson.map(function (a) {
        return a.name;
    })
    $("#textTest").html(htmlEmpyLL);
    var amount = $.trim($("#amount").val());
    $("#amount").val(Number(amount)-1);
}
function insertName(nameJson){
    let htmlStr = '';
    console.log("nameJson2",nameJson)
    nameJson.forEach(function (data) {
        htmlStr += '<li>'+data.name+'<a onclick="deleteTest(\''+data.name+'\')"><i title="删除">删除</i></a></li>'
    })
    newVar = nameJson.map(function (a) {
        return a.name;
    })
    return htmlStr;
}
