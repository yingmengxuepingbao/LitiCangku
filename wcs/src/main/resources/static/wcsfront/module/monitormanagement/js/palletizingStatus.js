var _buz_service,_frontBasic;
var palletNum = '';
$(function(){
    _buz_service={
        //获取码垛机下拉框列表
        getPalletizingSelect : function(palletizingNum, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/device/finddeviceby",
                headers:{'token':localStorage.getItem('token')},
                data    : {"deviceType" : 1},
                success	: function (res) {
                    var result = res;
                    if (result.code == 0) {
                        var options = '';
                        options += '<option value="">请选择码垛机</option>'
                        for (var i = 0; i < result.data.length; i++) {
                            var ele = result.data[i];
                            options += '<option value="'+ ele.dicItemCode+'" data-name ="' + ele.dicItemName + '">' + ele.dicItemName+ '</option>'
                        }
                        $("#palletizingSelect").html(options);
                    }
                },
                error	: reject
            });
        },
        //获取码垛机信息
        getPalletizingStatus : function(palletizingNum, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/palletizerstatus/getpalletizer",
                headers:{'token':localStorage.getItem('token')},
                data    : {"palletizingNum" : palletizingNum},
                success	: function (res) {
                    var result = res;
                    if (result.code == 0) {
                        var data = result.data;
                        if (data != null){
                            // 叉车叉货
                            var reqTrans = data.reqTrans;
                            // 码垛中
                            var recFinish = data.recFinish;
                            // 商品编码
                            var goodsCode = data.goodsCode;
                            // 批次号
                            var batchNo = data.batchNo;
                            // 托盘号
                            var palletCode = data.palletCode;
                            // 数量
                            var amount = data.amount;


                            $("#reqTrans").val(reqTrans);
                            $("#recFinish").val(recFinish);
                            $("#goodsCode").val(goodsCode);
                            $("#batchNo").val(batchNo);
                            $("#palletCode").val(palletCode);
                            $("#amount").val(amount);
                        }
                    }
                },
                error	: function () {
                    $("#reqTrans").val("");
                    $("#recFinish").val("");
                    $("#goodsCode").val("");
                    $("#batchNo").val("");
                    $("#palletCode").val("");
                    $("#amount").val("");
                }
            });
        },
    };
    _frontBasic = {
        data: {
        },
        init: function () {
            this.onLoad();
            this.bindEvent();
        },
        onLoad: function () {
            // _buz_service.getPalletizingSelect();
            var uptime = 3*1000;
            var timeControl = setInterval(function () {
                console.log("定时器+palletNum",palletNum);
                if (palletNum!=''){
                    _buz_service.getPalletizingStatus(palletNum)
                }

            }, uptime);
        },
        bindEvent: function () {
            var _this = this;

            //码垛机选择
            $(document).on('change', '#palletizingSelect', function () {
                var palletizingNum = $("#palletizingSelect").val();
                palletNum = $("#palletizingSelect").val();
                console.info("palletizingNum: " + palletizingNum)

                if (palletizingNum==""){
                    $("#reqTrans").val("");
                    $("#recFinish").val("");
                    $("#goodsCode").val("");
                    $("#batchNo").val("");
                    $("#palletCode").val("");
                    $("#amount").val("");
                }else {
                    _buz_service.getPalletizingStatus(palletizingNum)

                }
            });
            // 手动接受完成
            $("#handReceive").on("click",function () {

                var palletizingNum = $("#palletizingSelect").val();
                console.info("palletizingNum: " + palletizingNum)
                if (palletizingNum == undefined || palletizingNum == ''){
                    _commonFun.errorTips('请选择码垛机通道');
                    return;
                }
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content:"确定要重置通道"+palletizingNum+"为接受完成?",
                    FunL:function(){
                        $.ajax({
                            type	:"post",
                            url		: $.mpbGetHeaderPath()+"/palletizerstatus/handReceive",
                            headers:{'token':localStorage.getItem('token')},
                            data    : {palletizingNum:palletizingNum},
                            success	: function (res) {
                                var result=res;
                                if(result.code == 0){
                                    _commonFun.successTips(result.data);
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

            });



            //手动指令
            $("#handOrder").on("click",function () {
                console.info("手动指令")
                $("#updateId").val("");
                $("#addressId").val("");
                $("#formModel").modal({backdrop:false});
            });

            $("#saveButton").on("click",function () {

                var code = $.trim($("#code").val());
                var speed = $.trim($("#speed").val());
                //添加数据校验
                if(_commonFun.isBlank(code)){
                    $.DialogByZ.Autofade({Content: "动作代码不能为空"});
                    return false;
                }
                if(_commonFun.isBlank(speed)){
                    $.DialogByZ.Autofade({Content: "速度不能为空"});
                    return false;
                }

                var data= {
                    code:code,
                    speed:speed
                };

                //不存在新增
                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/palletizerstatus/handOrder",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            _commonFun.successTips(result.data);
                            _frontBasic.loadBasicList();
                        }else {
                            $.DialogByZ.Autofade({Content:result.message || "手动指令调用失败！"});
                        }
                    },
                    error	: function () {
                        _commonFun.errorTips("错误");
                    }
                });
            });
        }
    };
    _frontBasic.init();
});
