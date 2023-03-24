var _buz_service,_frontBasic;
var stackerId;
$(function(){
    _buz_service={
        //获取堆垛机下拉框列表
        getStackerSelect : function(stackerNum, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/device/finddeviceby",
                headers:{'token':localStorage.getItem('token')},
                data    : {"deviceType" : 2},
                success	: function (res) {
                    var result = res;
                    if (result.code == 0) {
                        var options = '';
                        options += '<option value="">请选择堆垛机</option>'
                        for (var i = 0; i < result.data.length; i++) {
                            var ele = result.data[i];
                            options += '<option value="'+ ele.dicItemCode+'" data-name ="' + ele.dicItemName + '">' + ele.dicItemName+ '</option>'
                        }
                        $("#stackerSelect").html(options);
                    }
                },
                error	: reject
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
            _buz_service.getStackerSelect();
        },
        bindEvent: function () {
            var _this = this;

            //堆垛机选择
            $(document).on('change', '#stackerSelect', function () {
                var stackerNum = $("#stackerSelect").val();
                stackerId = stackerNum;
                console.info("stackerNum: " + stackerNum)
                _buz_service.getStackerStatus(stackerNum)
            });

            $("#modeChange").on("click",function () {
                console.info("新建")
                $("#mode").val("");
                $("#modeChangeModel").modal({backdrop:false});
            });

            $("#modeClear").on("click",function () {
                console.info("清除")
                $("#mode").val("");
                $("#modeClearModel").modal({backdrop:false});
            });

            $("#modeThreadClear").on("click",function () {
                console.info("清除线体任务")
                $("#mode").val("");
                $("#modeThreadClearModel").modal({backdrop:false});
            });

            $("#modeClearAlarm").on("click",function () {
                console.info("清除线体警报")
                $("#mode").val("");
                $("#modeClearAlarmModel").modal({backdrop:false});
            });

            $("#saveModeButton").on("click",function () {

                let taskNo = $("#taskNo").val();
                if(_commonFun.isBlank(taskNo)){
                    $.DialogByZ.Autofade({Content: "任务号不能为空！"});
                    return false;
                }

                let taskId = $("#taskId").val();
                if(_commonFun.isBlank(taskId)){
                    $.DialogByZ.Autofade({Content: "线体号不能为空！"});
                    return false;
                }
                var data= {
                    taskNo: taskNo,
                    taskId: taskId
                };

                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/wcsConveyorTask/restart",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            $("#mode-show").text(result.message);
                            $("#taskNo").val("");
                            $("#taskId").val("");
                            _commonFun.successTips("启动成功!");
                        }else {
                            $("#mode-show").text("工作模式");
                            $.DialogByZ.Autofade({Content:result.message || ",手动指令调用失败！"});
                        }
                    },
                    error	: function () {
                        _commonFun.errorTips("错误");
                    }
                });
            });

            $("#saveModeButtonClear").on("click",function () {

                let taskNoClear = $("#taskNoClear").val();
                if(_commonFun.isBlank(taskNoClear)){
                    $.DialogByZ.Autofade({Content: "任务号不能为空！"});
                    return false;
                }

                var data= {
                    taskNo: taskNoClear
                };

                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/wcsConveyorTask/clear",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            $("#mode-show").text(result.message);
                            $("#taskNoClear").val("");
                            _commonFun.successTips("清除任务成功!");
                        }else {
                            $("#mode-show").text("工作模式");
                            $.DialogByZ.Autofade({Content:result.message || ",手动指令调用失败！"});
                        }
                    },
                    error	: function () {
                        _commonFun.errorTips("错误");
                    }
                });
            });

            $("#saveModeButtonThreadClear").on("click",function () {

                let taskIdThreadClear = $("#taskIdThreadClear").val();
                if(_commonFun.isBlank(taskIdThreadClear)){
                    $.DialogByZ.Autofade({Content: "线体号不能为空！"});
                    return false;
                }

                var data= {
                    taskId: taskIdThreadClear
                };

                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/wcsConveyorTask/clear/thread",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            $("#mode-show").text(result.message);
                            $("#taskIdThreadClear").val("");
                            _commonFun.successTips("清除任务成功!");
                        }else {
                            $("#mode-show").text("工作模式");
                            $.DialogByZ.Autofade({Content:result.message || ",手动指令调用失败！"});
                        }
                    },
                    error	: function () {
                        _commonFun.errorTips("错误");
                    }
                });
            });

            $("#saveModeButtonClearAlarm").on("click",function () {

                let taskIdClearAlarm = $("#taskIdClearAlarm").val();
                if(_commonFun.isBlank(taskIdClearAlarm)){
                    $.DialogByZ.Autofade({Content: "线体号不能为空！"});
                    return false;
                }

                var data= {
                    taskId: taskIdClearAlarm
                };

                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/wcsConveyorTask/clear/alarm",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            $("#mode-show").text(result.message);
                            $("#taskIdClearAlarm").val("");
                            _commonFun.successTips("清除警报成功!");
                        }else {
                            $("#mode-show").text("工作模式");
                            $.DialogByZ.Autofade({Content:result.message || ",手动指令调用失败！"});
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

    // 定时器，页面定时刷新状态
    setInterval(function () {
        console.log("定时执行");

        $.ajax({
            type	:"GET",
            url		: $.mpbGetHeaderPath()+"/wcsConveyorTask/wcsConveyor/info",
            headers:{'token':localStorage.getItem('token')},
            success	: function (res) {
                var result=res;
                if(result.code == 0){

                    var data = result.data;
                    console.log("data: " + data);

                    for(var key in data){
                        console.log("属性：" + key + ",值："+ data[key]);
                        console.log("属性haveGoods：" + key + ",值："+ data[key]["haveGoods"]);
                        if(data[key]["haveGoods"] == '1'){
                            console.log("有货物");
                            $("#" + key).css("background-color","green");
                        }else{
                            console.log("没有货物");
                            $("#" + key).css("background-color","#00bbee");
                        }

                        if(data[key]["taskNo"] != null && data[key]["taskNo"] != "0"){
                            console.log("AAAAAAAA");
                            $("#" + key).empty();
                            $("#" + key).append("线体：" + key + "<br/>");
                            $("#" + key).append("任务号："+ data[key]["taskNo"]);
                        }else{
                            console.log("BBBBB");
                            $("#" + key).empty();
                            $("#" + key).append("线体：" + key);
                        }

                        if(data[key]["conveyorState"] == '1'){
                            console.log("错误状态");
                            $("#" + key).css("background-color","red");
                        }
                    }
                }else {
                    // $.DialogByZ.Autofade({Content:result.message || ",获取失败！"});
                }
            },
            error	: function () {
                // _commonFun.errorTips("获取数据发生错误");
            }
        });
    }, 2000);
});
