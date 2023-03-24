var _buz_service,_frontBasic;
console.log(window.parent.operationData);

$(function(){
    _buz_service={
        //获取列表信息
        getDataList : function(data, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/planelocation/list",
                headers:{'token':localStorage.getItem('token')},
                data    : data || {},
                success	: resolve,
                error	: reject
            });
        },
        //获取字典
        getDictType :function(receivers){
            var receiverIds = [];
            if(receivers != null){
                receiverIds  =receivers.split(",");
            }
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/dictItem/getDictType/planelocation_attr",
                headers:{'token':localStorage.getItem('token')},
                data    : { code :"area_attr"},

                success: function(result){

                    $('#noticeReceivers').empty();
                    var code = result.code;
                    var data = result.data;
                    var options = '',item;
                    switch(code){
                        case '0':
                            for(var j in data){
                                item = data[j];
                                if(receiverIds.length > 0 && $.inArray(item.dicItemCode.toString(),receiverIds) > -1){
                                    options += "<option value='"+item.dicItemCode+"' selected >"+item.dicItemName+"</option>";
                                }else{
                                    options += "<option value='"+item.dicItemCode+"'>"+item.dicItemName+"</option>";
                                }
                            }
                            $('#areaAttr').html(options);
                            $('#areaAttr').chosen({"width":280}).trigger("chosen:updated");
                            break;
                        default:
                            $('#warningDiv').modal('show');
                            $('#warningDiv .modal-body span').html(result.message);
                            break;
                    }
                },
                error:function(result){
                    $('#warningDiv .modal-body span').html(result.message);
                },
                async:true
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
        findArea : function(warehouseCode, reject) {
            $.ajax({
                type: "post",
                url: $.mpbGetHeaderPath() + "/area/findareacode/",
                headers: {'token': localStorage.getItem('token')},
                data: {
                    warehouseCode: warehouseCode
                },
                async: false,
                success: function (res) {
                    var result = res;
                    if (result.code == 0) {
                        var options = '';
                        options += '<option value=""></option>'
                        for (var i = 0; i < result.data.length; i++) {
                            var ele = result.data[i];
                            options += '<option value="' + ele.dicItemCode + '">' + ele.dicItemCode + '</option>'
                        }
                        $("#areaCode").html(options);
                    }
                },
                error: reject
            })
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
            // _buz_service.getWarehouseCode();
            // _buz_service.getDictType();
        },
        bindEvent: function () {
            var _this = this;
            //分页前一页·
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
                warehouseCode    : $.trim($('#warehouseCodePoP').val()),
                locationCode    : $.trim($('#locationCodePoP').val()),
                locationName          : $.trim($('#locationNamePoP').val()),
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
            var maxCapacity = "";

            for(var ele in list){
                maxCapacity =list[ele].maxCapacity == null?"":list[ele].maxCapacity;
                tbody+='<tr data-id="'+list[ele].locationId+'">' +
                    '<td>'+list[ele].warehouseCode+'</td>'+
                    '<td>'+list[ele].areaCode+'</td>'+
                    '<td>'+list[ele].locationCode+'</td>'+
                    '<td>'+list[ele].locationName+'</td>'+
                    '<td>'+list[ele].remark+'</td>'+
                    '<td>'+list[ele].createBy+'</td>'+
                    '<td>'+list[ele].gmtCreate+'</td>';
                tbody += '<td>'+'<a onclick="editOneRecord(\''+list[ele].locationId+'\',1)"><i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                tbody +=  '<a onclick="deleteOneRecord(\''+list[ele].locationId+'\')"><i class="fa-table-line-delete" title="删除"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
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

    //库区选择
    $(document).on('change', '#warehouseCode', function () {
        var warehouseCode = $.trim($("#warehouseCode").val());

        _buz_service.findArea(warehouseCode);


    })
    //重置
    $("#reset").on("click",function () {
        $("#warehouseCodePoP").val("");
        $("#locationCodePoP").val("");
        $("#locationNamePoP").val("");
    });
    //confirmPerson chosen change事件
    $(document).on('change', '#confirmPerson', function (e){
        $("#confirmPerson").val($("#confirmPerson option:selected").attr("value"));
    });
    //新增
    $("#addButton").on("click",function () {
        //供应商
        _buz_service.getDictType();
        _buz_service.getWarehouseCode();
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
        $("#locationName").val("");
        $("#remark").val("");
        $("#myModalLabel").text('新增平库库位');
        $("#formModel").modal({backdrop:false});
    });
    //新增或更新信息
    $("#saveButton").on("click",function () {
            var areaAttrIds =[];
            var locationId = $.trim($("#locationId").val());
            console.log("locationId",locationId);
            var warehouseCode = $.trim($("#warehouseCode").val());
            var areaCode = $.trim($("#areaCode").val());
            var locationCode = $.trim($("#locationCode").val());
            var locationName = $.trim($("#locationName").val());
            var remark = $.trim($("#remark").val());

            //添加数据校验
            if(_commonFun.isBlank(locationCode)){
                $.DialogByZ.Autofade({Content: "平库库位编码不能为空"});
                return false;
            }
            if(_commonFun.isBlank(locationName)){
                $.DialogByZ.Autofade({Content: "平库库位名称不能为空"});
                return false;
            }
            if(_commonFun.isBlank(warehouseCode)){
                $.DialogByZ.Autofade({Content: "所属仓库不能为空"});
                return false;
            }
            if(_commonFun.isBlank(areaCode)){
                $.DialogByZ.Autofade({Content: "所属库区不能为空"});
                return false;
            }

            var data= {
                warehouseCode:warehouseCode,
                areaCode:areaCode,
                locationCode:locationCode,
                locationName:locationName,
                remark:remark,
            };

            //判断是新增还是修改
            if (!_commonFun.isNotBlank(locationId)){
                //不存在新增
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/planelocation/c",
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
                    url		: $.mpbGetHeaderPath()+"/planelocation/u",
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

//参数n为休眠时间，单位为毫秒:
function sleep(n) {
    var start = new Date().getTime();
    //  console.log('休眠前：' + start);
    while (true) {
        if (new Date().getTime() - start > n) {
            break;
        }
    }
    // console.log('休眠后：' + new Date().getTime());
}


//编辑 type  0 表示 查看  1 表示 编辑  2表示 审核
function editOneRecord(locationId,type) {
    var id = locationId;
    $("#type").val(type);

    //供应商
    _buz_service.getDictType();
    _buz_service.getWarehouseCode();

    sleep(100);
    //查看时，隐藏提交按钮 和禁用下拉框和input
    if(type == 0 ){
        $("#formInfo").find("i").hide();
        $("#myModalLabel").text('查看平库库位信息');
        $("#saveButton").hide();
        $("form  select").prop("disabled",true);
        $("form  input").prop("disabled",true);
    }else if (type == 1){
        $("#formInfo").find("i").show();
        $("#myModalLabel").text('编辑平库库位信息');
        //编辑时，显示 提交按钮和启用下拉框和 input
        $("#saveButton").show().text("保存");
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        $("#locationCode").prop("disabled",true);
        $("#warehouseCode").prop("disabled",true);

    }

    //查看备件信息 模态框弹出
    $.ajax({
        type	:"get",
        url		: $.mpbGetHeaderPath()+"/planelocation/r/"+id,
        headers:{'token':localStorage.getItem('token')},
        data    : {id:id},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                if(data != null){

                    var data=res.data;
                    _buz_service.getDictType(data.areaAttr);


                    $("#areaAttr").val(data.areaAttr);


                    $("#locationId").val(data.locationId);
                    $("#warehouseCode").val(data.warehouseCode);
                    _buz_service.findArea(data.warehouseCode)

                    $("#areaCode").val(data.areaCode);


                    $("#locationCode").val(data.locationCode);
                    $("#locationName").val(data.locationName);
                    $("#remark").val(data.remark);

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
        Content:"确定要删除平库库位信息?",
        FunL:function(){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/planelocation/delete",
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

