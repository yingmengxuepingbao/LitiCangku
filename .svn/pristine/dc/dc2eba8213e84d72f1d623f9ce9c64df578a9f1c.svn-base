var _buz_service,_frontBasic;
console.log(window.parent.operationData);

$(function(){
    _buz_service={
        //获取列表信息
        getDataList : function(data, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/location/list",
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
                url		: $.mpbGetHeaderPath()+"/dictItem/getDictType/location_type",
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
        //导入
        fileAjaxUpload : function(file, resolve, reject){
            $.ajax({
                type	: "post",
                url     : $.mpbGetHeaderPath()+"/location/upload/location",
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

        forbidden: function(id,resolve, reject){
            $.ajax({
                type		:"post",
                url			: $.mpbGetHeaderPath()+"/location/forbidden",
                headers:{'token':localStorage.getItem('token')},
                data		: {
                    ids  :   id
                },
                success		: resolve,
                error		: reject
            });
        },

        openFlag: function(id,resolve, reject){
            $.ajax({
                type		:"post",
                url			: $.mpbGetHeaderPath()+"/location/openFlag",
                headers:{'token':localStorage.getItem('token')},
                data		: {
                    ids  :   id
                },
                success		: resolve,
                error		: reject
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
                locationCode    : $.trim($('#locationCodePoP').val()),
                locationDesc          : $.trim($('#locationDescPoP').val()),
                shelvesNumber          : $.trim($('#shelvesNumberPoP').val()),
                floorNumber          : $.trim($('#floorNumberPoP').val()),
                columnNumber          : $.trim($('#columnNumberPop').val()),
                layerNumber          : $.trim($('#layerNumberPoP').val()),
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
            var inSeq ="";
            var outSeq ="";
            var layerNumber ="";
            var columnNumber ="";
            var floorNumber ="";
            var shelvesNumber ="";
            var highOrLowLoc = "";
            var activeFlag = "";

            for(var ele in list){
                inSeq =list[ele].inSeq == null?"":list[ele].inSeq;
                outSeq =list[ele].outSeq == null?"":list[ele].outSeq;
                layerNumber =list[ele].layerNumber == null?"":list[ele].layerNumber;
                columnNumber =list[ele].columnNumber == null?"":list[ele].columnNumber;
                shelvesNumber =list[ele].shelvesNumber == null?"":list[ele].shelvesNumber;
                console.log("楼层："+list[ele].floorNumber);
                floorNumber =list[ele].floorNumber == null?"":list[ele].floorNumber;
                highOrLowLoc = list[ele].userDefined1 == null?"":list[ele].userDefined1;
                if (highOrLowLoc=='1'){
                    highOrLowLoc='低货位';
                }else if(highOrLowLoc=='2'){
                    highOrLowLoc='高货位';
                }else{

                }
                activeFlag = list[ele].activeFlag == null?"":list[ele].activeFlag;
                if(activeFlag == 0){
                    activeFlag = '是'
                }else {
                    activeFlag = '否'
                }
                tbody+='<tr data-id="'+list[ele].locationId+'" data-locationCode ="'+list[ele].locationCode+'">' +
                    '<td><div class="checkbox-custom checkbox-default"><input type="checkbox" >' +
                    '<label></label></div></td>'+
                    '<td>'+list[ele].warehouseCode+'</td>'+
                    '<td>'+list[ele].areaCode+'</td>'+
                    '<td>'+list[ele].locationCode+'</td>'+
                    '<td>'+list[ele].locationDesc+'</td>'+
                    '<td>'+list[ele].locationAttr+'</td>'+
                    '<td>'+list[ele].palletCode+'</td>'+
                    '<td>'+inSeq+'</td>'+
                    '<td>'+outSeq+'</td>'+
                    '<td>'+shelvesNumber+'</td>'+
                    '<td>'+layerNumber+'</td>'+
                    '<td>'+columnNumber+'</td>'+
                    '<td>'+floorNumber+'</td>'+
                    '<td>'+list[ele].useStatus+'</td>'+
                    '<td>'+list[ele].allowMix+'</td>'+
                    '<td>'+highOrLowLoc+'</td>'+
                    '<td>'+activeFlag+'</td>';
                tbody += '<td>'+'<a onclick="editOneRecord(\''+list[ele].locationId+'\',1)"><i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                tbody +=  '<a onclick="deleteOneRecord(\''+list[ele].locationId+'\')"><i class="fa-table-line-delete" title="删除"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                tbody +=  '<a  onclick="editSelect(\''+list[ele].locationCode+'\')" > <i class="fa-table-line-look" title="查看"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
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
        window.location.href=$.mpbGetHeaderPath()+"/excel/locationstereoscopic/template";
    })
    //导入
    $(document).on('click', '#uploadDetailFile', function () {
        var formData = new FormData($("#form1")[0]);
        var btn = $("#uploadDetailFile");
        btn.button('loading');
        setTimeout(function () { btn.button('reset'); },2000);
        console.log("formData",formData)
        if(formData!=null&&formData!=''){
            _buz_service.fileAjaxUpload(formData, function (res) {
                var result=res;
                if(result.code==0) {
                    $.DialogByZ.Autofade({Content: result.message || "导入成功！"});
                    $("#check-all").prop("checked", false);
                    $("#upfile").val('');
                    $("#importFile").hide();
                    _frontBasic.loadBasicList();
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

    //全选/全不选 checkbox
    $(document).on('click', '#check-all', function () {
        var _self=$(this);
        if(_self.is(':checked')){
            //全选中设置
            $("#formTable tbody tr").each(function(index,item){
                $(item).find('input[type="checkbox"]').prop("checked","checked");
            })
        }
        else{
            //全不选设置
            $("#formTable tbody tr").each(function(index,item){
                $(item).find('input[type="checkbox"]').prop("checked", false);
            })
        }
    })
    //有一个未选中则取消全选  /  全都选中则全选选中
    $(document).on('click', '#formTable tbody input[type="checkbox"]', function (e) {
        var isCheckAll=true;
        if(!$(this).is(':checked')){
            //有一个没选中的就取消全选
            $("#check-all").prop("checked", false);
        }
        else{
            $("#formTable tbody tr").each(function(index,item){
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

    //批量禁用
    $(document).on('click', '#forbidden', function () {
        var monitorIdArr=[];
        $("#formTable tbody tr").each(function(index,item){
            var _self=$(item);
            if(_self.find('input[type="checkbox"]').is(':checked')){
                var monitorId = _self.data('id');
                monitorIdArr.push(monitorId);
            }
        })
        var monitorStr=monitorIdArr.join(",");
        console.log("monitorStr",monitorStr);
        if(monitorIdArr.length>0){
            $.DialogByZ.Confirm({
                Title: "操作提示",
                Content: "确定要禁用选中库位吗？",
                FunL:function(){
                    _buz_service.forbidden(monitorStr, function (res) {
                        var result=res;
                        if(result.code==0) {
                            $.DialogByZ.Autofade({Content: "禁用成功！"});
                            $("#check-all").prop("checked", false);
                            _frontBasic.loadBasicList();
                        }
                        else {
                            $.DialogByZ.Autofade({Content: result.message || "禁用失败！"});
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
            $.DialogByZ.Autofade({Content:"请选择要禁用的库位！"});
        }
    })

    //批量解用库位
    $(document).on('click', '#openFlag', function () {
        var monitorIdArr=[];
        $("#formTable tbody tr").each(function(index,item){
            var _self=$(item);
            if(_self.find('input[type="checkbox"]').is(':checked')){
                var monitorId = _self.data('id');
                monitorIdArr.push(monitorId);
            }
        })
        var monitorStr=monitorIdArr.join(",");
        console.log("monitorStr",monitorStr);
        if(monitorIdArr.length>0){
            $.DialogByZ.Confirm({
                Title: "操作提示",
                Content: "确定要解用选中库位吗？",
                FunL:function(){
                    _buz_service.openFlag(monitorStr, function (res) {
                        var result=res;
                        if(result.code==0) {
                            $.DialogByZ.Autofade({Content: "解用成功！"});
                            $("#check-all").prop("checked", false);
                            _frontBasic.loadBasicList();
                        }
                        else {
                            $.DialogByZ.Autofade({Content: result.message || "解用失败！"});
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
            $.DialogByZ.Autofade({Content:"请选择要解用的库位！"});
        }
    })








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
        $("#locationCodePoP").val("");
        $("#locationDescPoP").val("");
        $("#shelvesNumberPoP").val("");
        $("#floorNumberPoP").val("");
        $("#columnNumberPop").val("");
        $("#layerNumberPoP").val("");
    });
    //点击设备列表中的查看
    $(document).on('click', '.a-equipment-look', function (e) {
        var _self = $(this);
        //获取详情
        console.log(_self.parent().parent());
        alert(_self.parent().parent());
        var id = _self.parent().parent().data('locationCode');
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
        $("#locationId").val("");
        $("#warehouseCode").val("");
        $("#areaCode").val("");
        $("#locationCode").val("");
        $("#locationDesc").val("");
        $("#locationAttr").val("");
        // $("#palletCode").val("");
        $("#inSeq").val("");
        $("#outSeq").val("");
        $("#layerNumber").val("");
        $("#columnNumber").val("");
        $("#shelvesNumber").val("");
        $("#floorNumber").val("");
        // $("#useStatus").val("");
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
            var locationId = $.trim($("#locationId").val());
            console.log("locationId",locationId);
            var warehouseCode = $.trim($("#warehouseCode").val());
            var areaCode = $.trim($("#areaCode").val());
            var locationCode = $.trim($("#locationCode").val());
            var locationDesc = $.trim($("#locationDesc").val());
            var locationAttr = $.trim($("#locationAttr").val());
            // var palletCode = $.trim($("#palletCode").val());
            var inSeq = $.trim($("#inSeq").val());
            var outSeq = $.trim($("#outSeq").val());
            var layerNumber = $.trim($("#layerNumber").val());
            var columnNumber = $.trim($("#columnNumber").val());
            var shelvesNumber = $.trim($("#shelvesNumber").val());
            var floorNumber = $.trim($("#floorNumber").val());
            //高低货位
            var userDefined1 = $.trim($("#userDefined1").val());
            // var useStatus = $.trim($("#useStatus").val());
            var allowMix;
            $("input[name='allowMix']").each(function () {
                if($(this).prop("checked")){
                    allowMix = $(this).val();
                }
            });
            //添加数据校验
            if(_commonFun.isBlank(locationCode)){
                $.DialogByZ.Autofade({Content: "库位编码不能为空"});
                return false;
            }
            if(_commonFun.isBlank(locationDesc)){
                $.DialogByZ.Autofade({Content: "库位描述不能为空"});
                return false;
            }

            var data= {
                warehouseCode:warehouseCode,
                areaCode:areaCode,
                locationCode:locationCode,
                locationDesc:locationDesc,
                locationAttr:locationAttr,
                // palletCode:palletCode,
                inSeq:inSeq,
                outSeq:outSeq,
                layerNumber:layerNumber,
                columnNumber:columnNumber,
                shelvesNumber:shelvesNumber,
                floorNumber:floorNumber,
                // useStatus:useStatus,
                allowMix:allowMix,
                userDefined1:userDefined1
            };

            //判断是新增还是修改
            if (!_commonFun.isNotBlank(locationId)){
                //不存在新增
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/location/c",
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
                data.locationId = locationId;
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/location/u",
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
//     var id = _self.parent().parent().data('locationCode');
//     window.location.href="addEdgeBox.html?id="+id;
// })

function editSelect(locationCode) {
    // var _self = $(this);
    //获取详情

    // var id = _self.parent().parent().data('locationCode');
    window.location.href="./../wmsbasicmanagement/addEdgeBox.html?locationCode="+locationCode;
}

//编辑 type  0 表示 查看  1 表示 编辑  2表示 审核
function editOneRecord(locationId,type) {
    var id = locationId;
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
        $("#locationCode").prop("disabled",true);

    }

    //查看备件信息 模态框弹出
    $.ajax({
        type	:"get",
        url		: $.mpbGetHeaderPath()+"/location/r/"+id,
        headers:{'token':localStorage.getItem('token')},
        data    : {id:id},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                if(data != null){
                    $("#locationId").val(data.locationId);
                    $("#warehouseCode").val(data.warehouseCode);
                    $("#areaCode").val(data.areaCode);
                    $("#locationCode").val(data.locationCode);
                    $("#locationDesc").val(data.locationDesc);
                    $("#locationAttr").val(data.locationAttr);
                    // $("#palletCode").val(data.palletCode);
                    $("#inSeq").val(data.inSeq);
                    $("#outSeq").val(data.outSeq);
                    $("#layerNumber").val(data.layerNumber);
                    $("#columnNumber").val(data.columnNumber);
                    $("#shelvesNumber").val(data.shelvesNumber);
                    $("#floorNumber").val(data.floorNumber);
                    $("#userDefined1").val(data.userDefined1);
                    // $("#useStatus").val(data.useStatus);
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
                url		: $.mpbGetHeaderPath()+"/location/delete",
                headers:{'token':localStorage.getItem('token')},
                data    : {locationId:id},
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

