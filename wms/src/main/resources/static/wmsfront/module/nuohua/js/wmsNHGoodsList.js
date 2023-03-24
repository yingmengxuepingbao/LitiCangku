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
        //获取商品列表
        getSparePartBasicList : function(data, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/goods/list",
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
                url		: $.mpbGetHeaderPath()+"/dictItem/getDictType/goods_type",
                headers:{'token':localStorage.getItem('token')},
                data    : { code :"goods_type"},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var options = '';
                        options +='<option value=""></option>'
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.dicItemCode+'">'+ele.dicItemName+'</option>'
                        }

                        $("#goodsAttr").html(options);
                    }
                },
                error	: reject
            });
        },
        //导入
        fileAjaxUpload : function(file, resolve, reject){
            $.ajax({
                type	: "post",
                url     : $.mpbGetHeaderPath()+"/goods/upload/location",
                headers:{'token':localStorage.getItem('token')},
                data    : file,
                success : resolve,
                error   : reject,
                async: false,
                cache: false,
                contentType: false,
                processData: false
            });
        },
        //获取商品确认人
        getConfirmPersonList : function(){
            $.ajax({
                type	:"get",
                url		: "/IEAM/userManagement/findUserListByAuth",
                headers:{'token':localStorage.getItem('token')},
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
                headers:{'token':localStorage.getItem('token')},
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
                goodsCode    : $.trim($('#goodsCodePop').val()),
                goodsName          : $.trim($('#goodsNamePoP').val()),
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
                        var data=result.data.infoList;
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
                        $("#sparePartTable tbody").empty();
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
            var boxLength = "";
            var boxWidth = "";
            var boxHeight = "";
            var splitCount = "";
            var grossWeight = "";
            var layerQuantity = "";
            var qualityGuaranteeDays = "";
            for(var ele in list){
                layerQuantity =list[ele].layerQuantity == null?"":list[ele].layerQuantity;
                boxLength =list[ele].boxLength == null?"":list[ele].boxLength;
                boxWidth =list[ele].boxWidth == null?"":list[ele].boxWidth;
                boxHeight =list[ele].boxHeight == null?"":list[ele].boxHeight;
                qualityGuaranteeDays =list[ele].qualityGuaranteeDays == null?"":list[ele].qualityGuaranteeDays;
                splitCount =list[ele].splitCount == null?"":list[ele].splitCount;
                grossWeight =list[ele].grossWeight == null?"":list[ele].grossWeight;
                tbody+='<tr data-id="'+list[ele].goodsId+'">' +
                    '<td>'+list[ele].goodsCode+'</td>'+
                    '<td>'+list[ele].goodsName+'</td>'+
                    '<td>'+list[ele].goodsDesc+'</td>'+
                    // '<td>'+list[ele].owner+'</td>'+
                    '<td>'+list[ele].userDefined1+'</td>'+
                    '<td>'+list[ele].goodsAttr+'</td>'+
                    '<td>'+list[ele].goodsType+'</td>'+
                    '<td>'+list[ele].unit+'</td>'+
                    '<td>'+list[ele].hasBoxCode+'</td>'+
                    '<td>'+layerQuantity+'</td>'+
                    '<td>'+boxLength+'*'+boxWidth+'*'+boxHeight+'</td>'+
                    // '<td>'+boxWidth+'</td>'+
                    // '<td>'+boxHeight+'</td>'+
                    '<td>'+qualityGuaranteeDays+'</td>'+
                    '<td>'+list[ele].splitUnit+'</td>'+
                    '<td>'+splitCount+'</td>'+
                    '<td>'+grossWeight+'</td>';
                tbody += '<td>'+'<a onclick="editSparePart(\''+list[ele].goodsId+'\',1)"><i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                tbody +=  '<a onclick="deleteSparePart(\''+list[ele].goodsId+'\')"><i class="fa-table-line-delete" title="删除"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                tbody += '</td>'+'</tr>'
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
    //导入弹窗
    $(document).on('click', '#uploadFile', function () {
        $("#importFile").show();
    });
    //关闭导入弹窗
    $(document).on('click', '#popClose', function () {
        $("#upfile").val('');
        $("#importFile").hide();
    });
    //下载模版
    $(document).on('click', '#downloadModel', function () {
        window.location.href=$.mpbGetHeaderPath()+"/excel/goods/template";
    })
    //导入
    $(document).on('click', '#uploadDetailFile', function () {
        var formData = new FormData($("#form1")[0]);
        var btn = $("#uploadDetailFile");
        btn.button('loading');
        setTimeout(function () { btn.button('reset'); },2000);
        console.log("formData",formData)
        if(formData!=null&&formData!=''){
            _sparePartBasic_service.fileAjaxUpload(formData, function (res) {
                var result=res;
                if(result.code==0) {
                    $.DialogByZ.Autofade({Content: result.message || "导入成功！"});
                    $("#check-all").prop("checked", false);
                    $("#upfile").val('');
                    $("#importFile").hide();
                    _sparePartBasic.loadSparePartBasicList();
                }
                else {
                    if (result.data!=null){
                        var checkMessage = result.message+"\n"+result.data.join("\n");
                        alert(checkMessage);

                    }else {
                        $.DialogByZ.Alert({Title: "提示", Content: result.message || "导入失败！" , BtnL:"确定", FunL:$.DialogByZ.Close()});
                    }
                }
            }, function (err) {
                _commonFun.errorTips('');
            })
        }else {
            $.DialogByZ.Autofade({Content: "请选择上传附件！"});
        }

    })
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
        $("#goodsCodePop").val("");
        $("#goodsNamePoP").val("");
    });
    //confirmPerson chosen change事件
    $(document).on('change', '#confirmPerson', function (e){
        $("#confirmPerson").val($("#confirmPerson option:selected").attr("value"));
    });
    //新增商品
    $("#addSparePart").on("click",function () {
        //供应商
        _sparePartBasic_service.getSupplyList();
        //商品确认人
        // $("#confirmPerson").prop("disabled",false);
        // _sparePartBasic_service.getConfirmPersonList();
        //计量单位
        // _sparePartBasic_service.getCalculateUnitList();
        //显示 提交按钮和启用下拉框和 input
        $("#saveParePart").show().text("保存");
        $("#sparePartInfo").find("i").show();
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        // $("#sparePartCode").prop("disabled",true);
        $("#goodsId").val("");
        $("#goodsCode").val("");
        $("#goodsName").val("");
        $("#goodsDesc").val("");
        // $("#owner").val("");
        $("#userDefined1").val("");
        $("#goodsAttr").val("");
        $("#goodsType").val("");
        $("#unit").val("");
        // $("#hasBoxCode").val("");
        $("#layerQuantity").val("");
        $("#boxLength").val("");
        $("#boxWidth").val("");
        $("#boxHeight").val("");
        $("#qualityGuaranteeDays").val("");
        $("#splitUnit").val("");
        $("#splitCount").val("");
        $("#grossWeight").val("");
        $("input[name='hasBoxCode']").each(function () {
            if($(this).val()=="0"){
                $(this).prop("checked","checked");
            }
        });

        $("#myModalLabel").text('新增商品基础信息');
        $("#sparePartModel").modal({backdrop:false});
    });
    //新增或更新商品信息
    $("#saveParePart").on("click",function () {

            var goodsId = $.trim($("#goodsId").val());
            console.log("goodsId",goodsId);
            var goodsCode = $.trim($("#goodsCode").val());
            var goodsName = $.trim($("#goodsName").val());
            var goodsDesc = $.trim($("#goodsDesc").val());
            // var owner = $.trim($("#owner").val());
            var userDefined1 = $.trim($("#userDefined1").val());
            var goodsAttr = $.trim($("#goodsAttr").val());
            var goodsType = $.trim($("#goodsType").val());
            var unit = $.trim($("#unit").val());
            // var hasBoxCode = $.trim($("#hasBoxCode").val());
            var layerQuantity = $.trim($("#layerQuantity").val());
            // var boxLength = $.trim($("#boxLength").val());
            // var boxWidth = $.trim($("#boxWidth").val());
            // var boxHeight = $.trim($("#boxHeight").val());
            var qualityGuaranteeDays = $.trim($("#qualityGuaranteeDays").val());
            var splitUnit = $.trim($("#splitUnit").val());
            var splitCount = $.trim($("#splitCount").val());
            var grossWeight = $.trim($("#grossWeight").val());
            var hasBoxCode = 0;
            // $("input[name='hasBoxCode']").each(function () {
            //     if($(this).prop("checked")){
            //         hasBoxCode = $(this).val();
            //     }
            // });

            //添加数据校验
            if(_commonFun.isBlank(goodsCode)){
                $.DialogByZ.Autofade({Content: "商品编码不能为空"});
                return false;
            }
            if(_commonFun.isBlank(goodsName)){
                $.DialogByZ.Autofade({Content: "商品名称不能为空"});
                return false;
            }

            var data= {
                goodsCode:goodsCode,
                goodsName:goodsName,
                goodsDesc:goodsDesc,
                // owner:owner,
                userDefined1:userDefined1,
                goodsAttr:goodsAttr,
                goodsType:goodsType,
                unit:unit,
                hasBoxCode:hasBoxCode,
                layerQuantity:layerQuantity,
                // boxLength:boxLength,
                // boxWidth:boxWidth,
                qualityGuaranteeDays:qualityGuaranteeDays,
                splitUnit:splitUnit,
                grossWeight:grossWeight
            };

            //判断是新增还是修改
            if (!_commonFun.isNotBlank(goodsId)){
                //不存在新增
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/goods/c",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            _commonFun.successTips(result.data);
                            _sparePartBasic.loadSparePartBasicList();
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
                data.goodsId = goodsId;
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/goods/u",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            _commonFun.successTips(result.data);
                            _sparePartBasic.loadSparePartBasicList();
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

//编辑商品 type  0 表示 查看  1 表示 编辑  2表示 审核
function editSparePart(goodsId,type) {
    var id = goodsId;
    $("#type").val(type);
    //供应商
    _sparePartBasic_service.getSupplyList();
    //商品确认人
    // _sparePartBasic_service.getConfirmPersonList();
    //计量单位
    // _sparePartBasic_service.getCalculateUnitList();
    //查看时，隐藏提交按钮 和禁用下拉框和input
    if(type == 0 || type == 2){
        $("#sparePartInfo").find("i").hide();
        $("#myModalLabel").text('查看商品基础信息');
        $("#saveParePart").hide();
        $("form  select").prop("disabled",true);
        $("form  input").prop("disabled",true);
        if(type == 2){
            $("#myModalLabel").text('审核商品基础信息');
            $("#saveParePart").show().text("确认商品");
            //显示 确认区域
        }
    }else if (type == 1){
        $("#sparePartInfo").find("i").show();
        $("#myModalLabel").text('编辑商品基础信息');
        //编辑时，显示 提交按钮和启用下拉框和 input
        $("#saveParePart").show().text("保存");
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        $("#goodsCode").prop("disabled",true);

    }

    //查看商品信息 模态框弹出
    $.ajax({
        type	:"get",
        url		: $.mpbGetHeaderPath()+"/goods/r/"+id,
        headers:{'token':localStorage.getItem('token')},
        data    : {id:id},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                if(data != null){
                    $("#goodsId").val(data.goodsId);
                    $("#goodsCode").val(data.goodsCode);
                    $("#goodsName").val(data.goodsName);
                    $("#goodsDesc").val(data.goodsDesc);
                    // $("#owner").val(data.owner);

                    $("#userDefined1").val(data.userDefined1);
                    $("#goodsAttr").val(data.goodsAttr);
                    $("#goodsType").val(data.goodsType);
                    $("#unit").val(data.unit);
                    // $("#hasBoxCode").val("");
                    $("#layerQuantity").val(data.layerQuantity);
                    $("#boxLength").val(data.boxLength);
                    $("#boxWidth").val(data.boxWidth);
                    $("#boxHeight").val(data.boxHeight);
                    $("#qualityGuaranteeDays").val(data.qualityGuaranteeDays);
                    $("#splitUnit").val(data.splitUnit);
                    $("#splitCount").val(data.splitCount);
                    $("#grossWeight").val(data.grossWeight);
                    $("input[name='hasBoxCode']").each(function () {
                        if($(this).val()==data.hasBoxCode){
                            $(this).prop("checked",true);
                        }else{
                            $(this).prop("checked",false);
                        }
                    });
                    // $("#cycle").val(data.cycle);
                    // $("#unit").val(data.unit);
                    // $("#minStock").val(data.minStock);
                    // $("#maxStock").val(data.maxStock);
                    // $("#confirmPerson").val(data.confirmPerson).trigger("chosen:updated");
                    // if(type == 0){
                    //     $("#confirmPerson").chosen('destroy');
                    // }
                    // $("input[name='isPivotal']").each(function () {
                    //     if($(this).val()==data.isPivotal){
                    //         $(this).prop("checked",true);
                    //     }else{
                    //         $(this).prop("checked",false);
                    //     }
                    // });
                    // $("input[name='isShare']").each(function () {
                    //     if($(this).val()==data.isShare){
                    //         $(this).prop("checked",true);
                    //     }else{
                    //         $(this).prop("checked",false);
                    //     }
                    // });
                    // $("input[name='isForceReplace']").each(function () {
                    //     if($(this).val()== data.isForceReplace){
                    //         $(this).prop("checked",true);
                    //     }else{
                    //         $(this).prop("checked",false);
                    //     }
                    // });
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

//删除商品
function deleteSparePart(id) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要删除商品信息?",
        FunL:function(){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/goods/delete",
                headers:{'token':localStorage.getItem('token')},
                data    : {goodsId:id},
                success	: function (res) {
                    var result=res;
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
//停用商品
function stopUseSparePart(id) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要停用该商品?",
        FunL:function(){
            $.ajax({
                type	:"get",
                url		: "/IEAM/sparePartManagement/basic/confirmSparePartById",
                data    : {id:id,status:2},
                success	: function (res) {
                    var result=JSON.parse(decodeURIComponent(res));
                    if(result.code == 0){
                        _commonFun.successTips("停用商品成功");
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
