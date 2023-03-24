var _buz_service,_frontBasic;

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
                type: "post",
                url: $.mpbGetHeaderPath() + "/shelf/findshelf",
                headers: {'token': localStorage.getItem('token')},
                data: {
                },
                async: false,
                success: function (res) {
                    var result = res;
                    if (result.code == 0) {
                        var options = '';
                        options += '<option value=""></option>'
                        for (var i = 0; i < result.data.length; i++) {
                            var ele = result.data[i];
                            options += '<option value="'+ ele.dicItemCode+'" data-name ="' + ele.dicItemName + '">' +"货架id-"+ ele.dicItemCode+"(堆垛机id-" +ele.dicItemName +")"+ '</option>'
                        }
                        $("#shelfId").html(options);
                    }
                },
                error: reject
            })
        },
        getAreaCodeAll : function(data, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath().substring(0,$.mpbGetHeaderPath().length-8)+"8090/wms/expose/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                // data    : { code :"warehouseCode"},
                // async:false,
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
                locationId    : $.trim($('#locationIdPoP').val()),
                shelfId          : $.trim($('#shelfIdPoP').val()),
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
                
                var directionText="";
                var direction = list[ele].direction;
                if(direction =="0"){
                    directionText = "右";
                }else if(direction =="1"){
                    directionText = "左";
                }

                tbody+='<tr data-id="'+list[ele].locationId+'">' +
                    // '<td>'+list[ele].areaCode+'</td>'+
                    '<td>'+list[ele].locationId+'</td>'+
             /*       '<td>'+list[ele].x+'</td>'+
                    '<td>'+list[ele].y+'</td>'+
                    '<td>'+list[ele].z+'</td>'+*/
                    '<td>'+list[ele].shelfId+'</td>'+
        /*            '<td>'+list[ele].stackerId+'</td>'+
                    '<td>'+directionText+'</td>'+*/
                    '<td>'+list[ele].row+'</td>'+
                    // '<td>'+list[ele].deep+'</td>'+
                    '<td>'+list[ele].col+'</td>';
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
    //重置
    $("#reset").on("click",function () {
        $("#locationIdPoP").val("");
        $("#shelfIdPoP").val("");
    });
    //confirmPerson chosen change事件
    $(document).on('change', '#confirmPerson', function (e){
        $("#confirmPerson").val($("#confirmPerson option:selected").attr("value"));
    });
    //新增
    $("#addButton").on("click",function () {
        //供应商
        _buz_service.getSupplyList();
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
        $("#updateId").val("");
        $("#locationId").val("");
        $("#x").val("");
        $("#y").val("");
        $("#z").val("");

        $("#shelfId").val("");
        $("#deviceStatus").val("");
        $("#direction").val("");
        $("#col").val("");
        $("#row").val("");
        // $("#deep").val("");
        $("#areaCode").val("");
        $("#myModalLabel").text('新增物理库位');
        $("#formModel").modal({backdrop:false});
    });
    //新增或更新信息
    $("#saveButton").on("click",function () {
            var updateId = $.trim($("#updateId").val());
            console.log("updateId",updateId);
            var locationId = $.trim($("#locationId").val());
            var x = $.trim($("#x").val());
            var y = $.trim($("#y").val());
            var z = $.trim($("#z").val());
            var shelfId = $.trim($("#shelfId").val());
            var stackerId = $("#shelfId").find("option:selected").attr("data-name");
            console.log("stackerId",stackerId);
            var direction = $.trim($("#direction").val());
            var col = $.trim($("#col").val());
            var row = $.trim($("#row").val());
            // var deep = $.trim($("#deep").val());
            var areaCode = $.trim($("#areaCode").val());
            //添加数据校验
            if(_commonFun.isBlank(locationId)){
                $.DialogByZ.Autofade({Content: "物理库位ID不能为空"});
                return false;
            }
            if(_commonFun.isBlank(x)){
                $.DialogByZ.Autofade({Content: "X坐标不能为空"});
                return false;
            }
            if(_commonFun.isBlank(y)){
                $.DialogByZ.Autofade({Content: "Y坐标不能为空"});
                return false;
            }
            if(_commonFun.isBlank(z)){
                $.DialogByZ.Autofade({Content: "Z坐标不能为空"});
                return false;
            }
            if(_commonFun.isBlank(shelfId)){
                $.DialogByZ.Autofade({Content: "所属货架id不能为空"});
                return false;
            }
            if(_commonFun.isBlank(direction)){
                $.DialogByZ.Autofade({Content: "方向不能为空"});
                return false;
            }
            if(_commonFun.isBlank(col)){
                $.DialogByZ.Autofade({Content: "列数不能为空"});
                return false;
            }
            if(_commonFun.isBlank(row)){
                $.DialogByZ.Autofade({Content: "行数不能为空"});
                return false;
            }


            var data= {
                locationId:locationId,
                x:x,
                y:y,
                z:z,
                shelfId:shelfId,
                stackerId:stackerId,
                direction:direction,
                col:col,
                row:row,
                // deep:deep,
                areaCode:areaCode
            };

            //判断是新增还是修改
            if (!_commonFun.isNotBlank(updateId)){
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

//编辑 type  0 表示 查看  1 表示 编辑  2表示 审核
function editOneRecord(locationId,type) {
    var id = locationId;
    $("#type").val(type);
    //供应商
    _buz_service.getSupplyList();
    _buz_service.getAreaCodeAll();
    //查看时，隐藏提交按钮 和禁用下拉框和input
    if(type == 0 ){
        $("#formInfo").find("i").hide();
        $("#myModalLabel").text('查看物理库位信息');
        $("#saveButton").hide();
        $("form  select").prop("disabled",true);
        $("form  input").prop("disabled",true);
    }else if (type == 1){
        $("#formInfo").find("i").show();
        $("#myModalLabel").text('编辑物理库位信息');
        //编辑时，显示 提交按钮和启用下拉框和 input
        $("#saveButton").show().text("保存");
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        $("#locationId").prop("disabled",true);

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
                    $("#updateId").val(data.locationId);
                    $("#locationId").val(data.locationId);
                    console.log("shelfId",data.shelfId)
                    $("#shelfId").val(data.shelfId.toString());
                    $("#direction").val(data.direction);
                    $("#x").val(data.x);
                    $("#y").val(data.y);
                    $("#z").val(data.z);
                    $("#col").val(data.col);
                    $("#row").val(data.row);
                    // $("#deep").val(data.deep);
                    $("#areaCode").val(data.areaCode);
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
        Content:"确定要删除物理库位信息?",
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

