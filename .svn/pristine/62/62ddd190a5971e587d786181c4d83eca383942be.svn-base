var _sparePartBasic_service,_sparePartBasic;
var permissions ={};
console.log(window.parent.operationData);
// if(_commonFun.isNotBlank(window.parent.operationData)){
//     permissions = window.parent.operationData.permissions;
// }
//控制 新增按钮 显示
if(permissions["addSparePartBasicList"]){
    $("#addSparePart").show();
}else {
    //--改为显示
    $("#addSparePart").show();
}
$(function(){
    _sparePartBasic_service={
        //获取备件列表
        getSparePartBasicList : function(data, resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/sparePartManagement/basic/getAllSparePart",
                headers:{'Content-Type':'application/json;charset=utf8','token':localStorage.getItem('token')},
                data    : data || {},
                success	: resolve,
                error	: reject
            });
        },
        //获取供应商列表
        getSupplyList : function(data, reject){
            $.ajax({
                type	:"get",
                url		: "/IEAM/basicInfoManagement/supplier/getAllSupplier",
                data    : data || {},
                success	: function (res) {
                    var result=JSON.parse(decodeURIComponent(res));
                    if(result.code == 0){
                        var options = '';
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.id+'">'+ele.supplierName+'</option>'
                        }
                        $("#supplier").html(options);
                    }
                },
                error	: reject
            });
        },
        //获取备件确认人
        getConfirmPersonList : function(){
            $.ajax({
                type	:"get",
                url		: "/IEAM/userManagement/findUserListByAuth",
                async   : false,
                success	: function (res) {
                    var result=JSON.parse(decodeURIComponent(res));
                    if(result.code == 0){
                        var options = '';
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.id+'">'+ele.nickname+'</option>';
                        }
                        $("#confirmPerson").html(options);
                        $("#confirmPerson").chosen({width:"85%"}).trigger("chosen:updated");
                    }
                },
                error	: function () {
                    _commonFun.errorTips('');
                }
            });
        },
        //获取计量单位
        getCalculateUnitList : function(reject){
            $.ajax({
                type	:"get",
                url		: "/IEAM/basicInfoManagement/dictionaryManagement/getDictionaryByCode",
                data    : {code:'JLDW'},
                success	: function (res) {
                    var result=JSON.parse(decodeURIComponent(res));
                    if(result.code == 0){
                        var options = '';
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.id+'">'+ele.name+'</option>'
                        }
                        $("#calculateUnit").html(options);
                    }
                },
                error	: reject
            });
        },
    };
    _sparePartBasic = {
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
            this.loadSparePartBasicList();
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
                _this.loadSparePartBasicList();
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
                _this.loadSparePartBasicList();
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
                _this.loadSparePartBasicList();
            });
            //点击搜索
            $(document).on('click', '#refrechTop', function () {
                $(".currentPage").text("1");
                _this.loadSparePartBasicList();
            });
        },
        //渲染库存列表
        loadSparePartBasicList : function () {
            var _this = this;
            var info={
                queryContent    : $.trim($('#queryContent').val()),
                status          : $.trim($('#status').val()),
                currentPage     : $.trim($(".currentPage").text()),
                orderBy         : _this.data.orderBy,
                numberOnePage   : _this.data.numberOnePage
            };
            _sparePartBasic_service.getSparePartBasicList(info,function (res) {
                // var result=JSON.parse(decodeURIComponent(res));
                var result=res;
                console.log(res);
                if(result.code==0) {
                    if(result.data.totalNumber!=0){
                        var data=result.data.spareParts;
                        _this.sparePartBasicListSource(data);
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
        sparePartBasicListSource : function(list) {
            $("#sparePartTable tbody").empty();
            var tbody='';
            var userId = localStorage.getItem("userId");
            for(var ele in list){
                tbody+='<tr data-id="'+list[ele].id+'">' +
                    '<td>'+list[ele].sparePartCode+'</td>'+
                    '<td>'+list[ele].sparePartName+'</td>'+
                    '<td>'+list[ele].specAndType+'</td>'+
                    '<td>'+list[ele].supplier+'</td>'+
                    '<td>'+list[ele].isPivotal+'</td>'+
                    '<td>'+list[ele].isForceReplace+'</td>';
                if(list[ele].cycle == 0 || _commonFun.isBlank(list[ele].cycle)){
                    tbody +='<td></td>';
                }else{
                    tbody += '<td>'+list[ele].cycle+list[ele].unit+'</td>';
                }
                tbody += '<td>'+(list[ele].minStock > 0 ? list[ele].minStock : '')+'</td>';
                if(list[ele].status == 0){
                    tbody +='<td>未确认</td><td>';
                    if(permissions["eidtSparePartBasicList"]){
                        tbody += '<a onclick="editSparePart('+list[ele].id+',1)"><i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    }
                    if(permissions["deleteSparePartBasicList"]){
                        tbody +=  '<a onclick="deleteSparePart('+list[ele].id+')"><i class="fa-table-line-delete" title="删除"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    }
                    if(list[ele].confirmPerson == userId){
                        if(permissions["auditSparePartBasicList"]){
                            tbody += '<a onclick="editSparePart('+list[ele].id+',2)"><i class="fa-table-line-check" title="审核"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                        }
                    }
                }else if(list[ele].status == 1){
                    tbody +='<td>在用</td><td>';
                    if(list[ele].confirmPerson == userId){
                        if(permissions["stopSparePartBasicList"]){
                            tbody += '<a onclick="stopUseSparePart('+list[ele].id+')"><i class="fa-table-line-stop" title="停用"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                        }
                    }
                }else if(list[ele].status == 2){
                    tbody +='<td>停用</td><td>';
                }else {
                    tbody +='<td></td><td>';
                }
                tbody +='<a onclick="editSparePart('+list[ele].id+',0)"><i class="fa-table-line-look" title="查看"></i></a></td></tr>';
            }
            $("#sparePartTable tbody").html(tbody);
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
    _sparePartBasic.init();

    //表头排序
    $(document).on('click', '#sparePartTable thead tr th', function (e) {
        e.stopPropagation();
        var _self=$(this);
        var orderBy=_self.data('sort');

        if(_self.find('i').hasClass('fa-sort-amount-asc')){
            _self.parents("thead").find('i').removeClass("th-sort-active");
            _self.find('i').removeClass('fa-sort-amount-asc').addClass('fa-sort-amount-desc th-sort-active');
            _sparePartBasic.data.orderBy=orderBy + " desc";
            _sparePartBasic.loadSparePartBasicList();

        }
        else if(_self.find('i').hasClass('fa fa-sort-amount-desc')){
            _self.parents("thead").find('i').removeClass("th-sort-active");
            _self.find('i')
                .removeClass('fa-sort-amount-desc')
                .addClass('fa-sort-amount-asc th-sort-active');
            _sparePartBasic.data.orderBy=orderBy + " asc";
            _sparePartBasic.loadSparePartBasicList();
        }

    });
    //重置
    $("#reset").on("click",function () {
        $("#queryContent").val("");
        $("#status").val("");
    });
    //confirmPerson chosen change事件
    $(document).on('change', '#confirmPerson', function (e){
        $("#confirmPerson").val($("#confirmPerson option:selected").attr("value"));
    });
    //新增备件
    $("#addSparePart").on("click",function () {
        //供应商
        _sparePartBasic_service.getSupplyList();
        //备件确认人
        $("#confirmPerson").prop("disabled",false);
        _sparePartBasic_service.getConfirmPersonList();
        //计量单位
        _sparePartBasic_service.getCalculateUnitList();
        //显示 提交按钮和启用下拉框和 input
        $("#saveParePart").show().text("保存");
        $("#sparePartInfo").find("i").show();
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        $("#sparePartCode").prop("disabled",true);
        $("#sparePartId").val("");
        $("#sparePartCode").val("");
        $("#sparePartName").val("");
        $("#specAndType").val("");
        $("#brand").val("");
        $("#manufacturer").val("");
        $("#purchaseCycle").val("");
        $("#purchaseUnit").val('天');
        $("#cycle").val("");
        $("#unit").val("天");
        $("#minStock").val(1);
        $("#maxStock").val(1);
        //
        $("input[name='isPivotal']").each(function () {
            if($(this).val()=="1"){
                $(this).prop("checked","checked");
            }
        });
        $("input[name='isShare']").each(function () {
            if($(this).val()=="0"){
                $(this).prop("checked","checked");
            }
        });
        $("input[name='isForceReplace']").each(function () {
            if($(this).val()=="1"){
                $(this).prop("checked","checked");
            }
        });
        $("#myModalLabel").text('新增备件基础信息');
        $("#sparePartModel").modal({backdrop:false});
    });
    //新增或更新备件信息
    $("#saveParePart").on("click",function () {
        var id = $.trim($("#sparePartId").val());
        var type = $("#type").val();
        if(type==2){
            $.DialogByZ.Confirm({
                Title: "操作提示",
                Content:"确定要确认备件基础信息?",
                FunL:function(){
                    $.ajax({
                        type	:"get",
                        url		: "/IEAM/sparePartManagement/basic/confirmSparePartById",
                        data    : {id:id,status: 1},
                        success	: function (res) {
                            var result=JSON.parse(decodeURIComponent(res));
                            if(result.code == 0){
                                _commonFun.successTips(result.data);
                                _sparePartBasic.loadSparePartBasicList();
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
        }else{
            var id = $.trim($("#sparePartId").val());
            var sparePartCode = $.trim($("#sparePartCode").val());
            var sparePartName = $.trim($("#sparePartName").val());
            var specAndType = $.trim($("#specAndType").val());
            var brand = $.trim($("#brand").val());
            var supplier = $.trim($("#supplier").val());
            var manufacturer = $.trim($("#manufacturer").val());
            var calculateUnit = $.trim($("#calculateUnit").val());
            var purchaseCycle = $.trim($("#purchaseCycle").val());
            var purchaseUnit = $.trim($("#purchaseUnit").val());
            var isPivotal,isShare,isForceReplace;
            $("input[name='isPivotal']").each(function () {
                if($(this).prop("checked")){
                    isPivotal = $(this).val();
                }
            });
            $("input[name='isShare']").each(function () {
                if($(this).prop("checked")){
                    isShare = $(this).val();
                }
            });
            $("input[name='isForceReplace']").each(function () {
                if($(this).prop("checked")){
                    isForceReplace = $(this).val();
                }
            });
            var cycle = $.trim($("#cycle").val());
            var unit = $.trim($("#unit").val());
            var minStock = $.trim($("#minStock").val());
            var maxStock = $.trim($("#maxStock").val());
            var confirmPerson = $.trim($("#confirmPerson  option:selected").attr("value"));
            //添加数据校验
            if(_commonFun.isBlank(sparePartName)){
                $.DialogByZ.Autofade({Content: "备件名称不能为空"});
                return false;
            }
            // if(_commonFun.isBlank(specAndType)){
            //     $.DialogByZ.Autofade({Content: "规格型号不能为空"});
            //     return false;
            // }
            if(_commonFun.isBlank(confirmPerson)){
                $.DialogByZ.Autofade({Content: "备件确认人不能为空"});
                return false;
            }
            //强制更换为是 的时候，更换周期必填，更换周期单位为天、周、月
            if(_commonFun.isNotBlank(isForceReplace) && isForceReplace == 1){
                if(_commonFun.isBlank(cycle)){
                    $.DialogByZ.Autofade({Content: "更换周期不能为空"});
                    return false;
                }
                if(_commonFun.isBlank(unit)){
                    $.DialogByZ.Autofade({Content: "更换周期单位不能为空"});
                    return false;
                }
            }
            if(_commonFun.isNotBlank(minStock) && minStock <=0){
                $.DialogByZ.Autofade({Content: "安全库存必须大于0"});
                return false;
            }
            //安全库存校验-后者不能小于前者
            // if(_commonFun.isNotBlank(minStock) && _commonFun.isNotBlank(maxStock)){
            //     if(parseInt(minStock) > parseInt(maxStock)){
            //         $.DialogByZ.Autofade({Content: "安全库存最小值不能大于最大值"});
            //         return false;
            //     }
            // }
            //更换周期添加默认值
            if(_commonFun.isBlank(cycle)){
                cycle = 0;
            }
            var data= {
                sparePartCode:sparePartCode,
                sparePartName:sparePartName,
                specAndType:specAndType,
                brand:brand,
                supplier:supplier,
                manufacturer:manufacturer,
                calculateUnit:calculateUnit,
                purchaseCycle:_commonFun.isBlank(purchaseCycle) ? 0:purchaseCycle,
                purchaseUnit:purchaseUnit,
                isPivotal:isPivotal,
                isShare:isShare,
                isForceReplace:isForceReplace,
                cycle:parseInt(cycle),
                unit:unit,
                minStock:parseInt(minStock),
                maxStock:parseInt(maxStock),
                confirmPerson:confirmPerson
            };
            if(_commonFun.isNotBlank(id)){
                data.id = id;
            }
            $.ajax({
                type	:"POST",
                url		: "/IEAM/sparePartManagement/basic/saveSparePart",
                contentType:'application/json;charset=UTF-8',
                data    : JSON.stringify(data),
                success	: function (res) {
                    var result=JSON.parse(decodeURIComponent(res));
                    if(result.code == 0){
                        _commonFun.successTips(result.data);
                        _sparePartBasic.loadSparePartBasicList();
                    }
                },
                error	: function () {
                    _commonFun.errorTips('');
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
            _commonFun.errorTips("安全库存必须大于0");
            $(that).val(1);
            return false;
        }
        if(!/^[1-9]\d*$/.test(stockNumber)){
            _commonFun.errorTips("安全库存只能是正整数");
            $(that).val(parseInt(stockNumber)+1);
            return false;
        }
    }else{
        _commonFun.errorTips("安全库存不能为空");
        $(that).val(1);
        return false;
    }
}

//编辑备件 type  0 表示 查看  1 表示 编辑  2表示 审核
function editSparePart(id,type) {
    $("#type").val(type);
    //供应商
    _sparePartBasic_service.getSupplyList();
    //备件确认人
    _sparePartBasic_service.getConfirmPersonList();
    //计量单位
    _sparePartBasic_service.getCalculateUnitList();
    //查看时，隐藏提交按钮 和禁用下拉框和input
    if(type == 0 || type == 2){
        $("#sparePartInfo").find("i").hide();
        $("#myModalLabel").text('查看备件基础信息');
        $("#saveParePart").hide();
        $("form  select").prop("disabled",true);
        $("form  input").prop("disabled",true);
        if(type == 2){
            $("#myModalLabel").text('审核备件基础信息');
            $("#saveParePart").show().text("确认备件");
            //显示 确认区域
        }
    }else if (type == 1){
        $("#sparePartInfo").find("i").show();
        $("#myModalLabel").text('编辑备件基础信息');
        //编辑时，显示 提交按钮和启用下拉框和 input
        $("#saveParePart").show().text("保存");
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        $("#sparePartCode").prop("disabled",true);
    }
    //查看备件信息 模态框弹出
    $.ajax({
        type	:"get",
        url		: "/IEAM/sparePartManagement/basic/getSparePartById",
        data    : {id:id},
        success	: function (res) {
            var result=JSON.parse(decodeURIComponent(res));
            if(result.code == 0){
                var data = result.data;
                if(data != null){
                    $("#sparePartId").val(data.id);
                    $("#sparePartCode").val(data.sparePartCode);
                    $("#sparePartName").val(data.sparePartName);
                    $("#supplier").val(data.supplier);
                    $("#calculateUnit").val(data.calculateUnit);
                    $("#specAndType").val(data.specAndType);
                    $("#brand").val(data.brand);
                    $("#manufacturer").val(data.manufacturer);
                    $("#purchaseCycle").val(data.purchaseCycle);
                    $("#purchaseUnit").val(data.purchaseUnit);
                    $("#cycle").val(data.cycle);
                    $("#unit").val(data.unit);
                    $("#minStock").val(data.minStock);
                    $("#maxStock").val(data.maxStock);
                    $("#confirmPerson").val(data.confirmPerson).trigger("chosen:updated");
                    if(type == 0){
                        $("#confirmPerson").chosen('destroy');
                    }
                    $("input[name='isPivotal']").each(function () {
                        if($(this).val()==data.isPivotal){
                            $(this).prop("checked",true);
                        }else{
                            $(this).prop("checked",false);
                        }
                    });
                    $("input[name='isShare']").each(function () {
                        if($(this).val()==data.isShare){
                            $(this).prop("checked",true);
                        }else{
                            $(this).prop("checked",false);
                        }
                    });
                    $("input[name='isForceReplace']").each(function () {
                        if($(this).val()== data.isForceReplace){
                            $(this).prop("checked",true);
                        }else{
                            $(this).prop("checked",false);
                        }
                    });
                }
                //显示模态框
                $("#sparePartModel").modal({backdrop:false});
            }
        },
        error	: function () {
            _commonFun.errorTips('');
        }
    });

}

//删除备件
function deleteSparePart(id) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要删除备件基础信息?",
        FunL:function(){
            $.ajax({
                type	:"get",
                url		: "/IEAM/sparePartManagement/basic/deleteSparePartById",
                data    : {id:id},
                success	: function (res) {
                    var result=JSON.parse(decodeURIComponent(res));
                    if(result.code == 0){
                        _commonFun.successTips(result.data);
                        _sparePartBasic.loadSparePartBasicList();
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
//停用备件
function stopUseSparePart(id) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要停用该备件?",
        FunL:function(){
            $.ajax({
                type	:"get",
                url		: "/IEAM/sparePartManagement/basic/confirmSparePartById",
                data    : {id:id,status:2},
                success	: function (res) {
                    var result=JSON.parse(decodeURIComponent(res));
                    if(result.code == 0){
                        _commonFun.successTips("停用备件成功");
                        _sparePartBasic.loadSparePartBasicList();
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
