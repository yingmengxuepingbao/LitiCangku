var _buz_service,_frontBasic;
var stackerId ;
var stackerNum = '';
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
        //获取堆垛机信息
        getStackerStatus : function(stackerNum, resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/stackerStatus/getStatusByNum",
                headers:{'token':localStorage.getItem('token')},
                data    : {"stackerNum" : stackerNum},
                success	: function (res) {
                    var result = res;
                    if (result.code == 0) {

                        var data = result.data;
                        // 工作模式
                        var stackerMode = data.stackerMode;
                        // 任务编号
                        var taskNo = data.taskNo;
                        // 反馈任务号
                        var feedbackTaskNo = data.feedbackTaskNo;

                        var stackerStatus = data.stackerStatus;
                        if(stackerStatus != null){
                            // 1堆垛机动作中 2堆垛机空闲 3备用 4 手动状态 5故障状态
                            var equipmentStatus = data.stackerStatus.equipmentStatus;
                            // 当前设备状态：1自动；2半自动；3手动；4故障
                            var statusText = '';
                            if (equipmentStatus == '1'){
                                statusText = '自动';
                            }else if (equipmentStatus == '2') {
                                statusText = '半自动';
                            }else if (equipmentStatus == '3') {
                                statusText = '手动';
                            }else if (equipmentStatus == '4') {
                                statusText = '故障';
                            }

                            // x轴速度
                            var speedX = data.stackerStatus.speedX;
                            // y轴速度
                            var speedY = data.stackerStatus.speedY;
                            // x轴坐标
                            var positionX = data.stackerStatus.positionX;
                            // z轴坐标
                            var positionZ = data.stackerStatus.positionZ;

                            // 设备故障代码，关于上位机:1空指令 2 指令格式不合法 4 相邻指令相同
                            var hostErrCode = data.stackerStatus.hostErrCode;
                            // 伺服电机故障代码：
                            var servoMotorErrorCode = data.stackerStatus.servoMotorErrorCode;

                            console.info("positionX: " + positionX)

                            $("#equipmentStatus").val(statusText);
                            $("#speedX").val(speedX);
                            $("#speedY").val(speedY);
                            $("#positionX").val(positionX);
                            $("#positionZ").val(positionZ);
                            $("#hostErrCode").val(hostErrCode);
                            $("#servoMotorErrorCode").val(equipmentStatus);
                            var obj = document.getElementById("errorButton");
                            obj.style.backgroundColor= "#1DBBF4";
                            //故障状态
                            if (equipmentStatus == '5'){
                                var obj = document.getElementById("errorButton");
                                obj.style.backgroundColor= "red";
                            } else {
                                var obj = document.getElementById("errorButton");
                                obj.style.backgroundColor= "#1DBBF4";
                            }

                            var errDetail0 = data.stackerStatus.errDetail0;
                            var errDetail1 = data.stackerStatus.errDetail1;
                            var errDetail2 = data.stackerStatus.errDetail2;
                            var errDetail3 = data.stackerStatus.errDetail3;
                            var errDetail4 = data.stackerStatus.errDetail4;

                            if (errDetail0 != "0000000000000000"){
                                var obj = document.getElementById("errorButton");
                                obj.style.backgroundColor= "red";
                            }
                            if (errDetail1 != "0000000000000000"){
                                var obj = document.getElementById("errorButton");
                                obj.style.backgroundColor= "red";
                            }
                            if (errDetail2 != "0000000000000000"){
                                var obj = document.getElementById("errorButton");
                                obj.style.backgroundColor= "red";
                            }
                            if (errDetail3 != "0000000000000000"){
                                var obj = document.getElementById("errorButton");
                                obj.style.backgroundColor= "red";
                            }
                            if (errDetail4 != "0000000000000000"){
                                var obj = document.getElementById("errorButton");
                                obj.style.backgroundColor= "red";
                            }


                        }
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
            var uptime = 3*1000;
            var timeControl = setInterval(function () {
                console.log("定时器+stackerNum",stackerNum);
                if (stackerNum!=''){
                    _buz_service.getStackerStatus(stackerNum)
                }

            }, uptime);
            console.log("test")
        },
        bindEvent: function () {
            var _this = this;

            //堆垛机选择
            $(document).on('change', '#stackerSelect', function () {
                 stackerNum = $("#stackerSelect").val();
                stackerId = stackerNum;
                console.info("stackerNum: " + stackerNum)
                _buz_service.getStackerStatus(stackerNum)
            });

            //手动指令
            $("#handOrder").on("click",function () {
                console.info("手动指令")
                $("#updateId").val("");
                $("#addressId").val("");
                $("#formModel").modal({backdrop:false});
            });

            //异常警告
            $("#errorButton").on("click",function () {
                console.info("errorButton")
                // $("#updateId").val("");
                // $("#addressId").val("");
                $("#formTable tbody").empty();
                $("#formModelError").modal({backdrop:false});
                $.ajax({
                    type	:"post",
                    url		: $.mpbGetHeaderPath()+"/stackerStatus/getErrorInfo",
                    headers:{'token':localStorage.getItem('token')},
                    data    : {"stackerNum" : stackerNum},
                    success	: function (res) {
                        var result=res;
                        console.log(res);
                        if(result.code==0) {
                            if(result.data!=null){
                                var list=result.data;
                                // $("#formTable tbody").empty();
                                var tbody='';
                                var userId = localStorage.getItem("userId");
                                for(var ele in list){

                                    var flagText="";
                                    var flag = list[ele].flag;
                                    if(flag =="0"){
                                        flagText = '<div class="circleGreen"></div>';
                                    }else if(flag =="1"){
                                        flagText = '<div class="circleRed"></div>';
                                    }

                                    tbody+='<tr data-id="'+list[ele].infoCode+'">' +
                                        '<td>'+list[ele].infoText+'</td>'+
                                        // '<td>'+list[ele].stackerId+'</td>'+
                                        '<td>'+flagText+'</td>'
                                    // tbody += '<td>'+'<a onclick="editOneRecord(\''+list[ele].shelfId+'\',1)"><i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                                    // tbody +=  '<a onclick="deleteOneRecord(\''+list[ele].shelfId+'\')"><i class="fa-table-line-delete" title="删除"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                                    tbody += +'</tr>'
                                }
                                $("#formTable tbody").html(tbody);

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
                    },
                    error	: function () {

                    }
                });

            });

            //点位开关信息
            $("#openButton").on("click",function () {
                console.info("openButton")
                $("#formTableOpen tbody").empty();
                // $("#updateId").val("");
                // $("#addressId").val("");
                $("#formModelOpen").modal({backdrop:false});
                $.ajax({
                    type	:"post",
                    url		: $.mpbGetHeaderPath()+"/stackerStatus/getOpenInfo",
                    headers:{'token':localStorage.getItem('token')},
                    data    : {"stackerNum" : stackerNum},
                    success	: function (res) {
                        var result=res;
                        console.log(res);
                        if(result.code==0) {
                            if(result.data!=null){
                                var list=result.data;
                                // $("#formTableOpen tbody").empty();
                                var tbody='';
                                var userId = localStorage.getItem("userId");
                                for(var ele in list){

                                    var flagText="";
                                    var flag = list[ele].flag;
                                    if(flag =="0"){
                                        flagText = '关'+'&nbsp;&nbsp;'+'<div class="circleRed"></div>';
                                    }else if(flag =="1"){
                                        flagText = '开'+'&nbsp;&nbsp;'+'<div class="circleGreen"></div>';
                                    }

                                    tbody+='<tr data-id="'+list[ele].infoCode+'">' +
                                        '<td>'+list[ele].infoCode+'</td>'+
                                        '<td>'+list[ele].infoText+'</td>'+
                                        // '<td>'+list[ele].stackerId+'</td>'+
                                        '<td>'+flagText+'</td>'
                                    // tbody += '<td>'+'<a onclick="editOneRecord(\''+list[ele].shelfId+'\',1)"><i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                                    // tbody +=  '<a onclick="deleteOneRecord(\''+list[ele].shelfId+'\')"><i class="fa-table-line-delete" title="删除"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;';
                                    tbody += +'</tr>'
                                }
                                $("#formTableOpen tbody").html(tbody);

                            }
                            else {
                                $("#formTableOpen tbody").empty();
                                $.DialogByZ.Autofade({Content: "查无数据！"});
                                $(".pagination-con").hide();
                            }
                        }
                        else {
                            $.DialogByZ.Autofade({Content: result.message ||  "查无数据！"});
                        }
                    },
                    error	: function () {

                    }
                });

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
                    url		: $.mpbGetHeaderPath()+"/stackerStatus/handOrder",
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

            //手动指令
            $("#modeChange").on("click",function () {
                console.info("模式切换")
                $("#mode").val("");
                $("#modeChangeModel").modal({backdrop:false});
            });

            $("#saveModeButton").on("click",function () {
                let mode = $("#mode").val();
                if(_commonFun.isBlank(mode)){
                    $.DialogByZ.Autofade({Content: "运行模式不能为空！"});
                    return false;
                }
                var data= {
                    stackerId:stackerId,
                    mode:mode
                };

                $.ajax({
                    type	:"POST",
                    url		: $.mpbGetHeaderPath()+"/stackerStatus/modeChange",
                    headers:{'token':localStorage.getItem('token')},
                    data    : data,
                    success	: function (res) {
                        var result=res;
                        if(result.code == 0){
                            $("#mode-show").text(result.message);
                            _commonFun.successTips(null);
                        }else {
                            $("#mode-show").text("工作模式");
                            $.DialogByZ.Autofade({Content:result.message || "手动指令调用失败！"});
                        }
                    },
                    error	: function () {
                        _commonFun.errorTips("错误");
                    }
                });
            });


            //急停
            $("#stop").on("click",function () {
                if(_commonFun.isBlank(stackerId)){
                    $.DialogByZ.Autofade({Content: "请选择堆垛机！"});
                    return false;
                };
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确认要急停堆垛机?",
                    FunL: function () {
                        let data = {stackerId:stackerId};
                        $.ajax({
                            type	:"POST",
                            url		: $.mpbGetHeaderPath()+"/stackerStatus/stop",
                            headers:{'token':localStorage.getItem('token')},
                            data:data,
                            success	: function (res) {
                                var result=res;
                                if(result.code == 0){
                                    _commonFun.successTips(null);
                                }else {
                                    $.DialogByZ.Autofade({Content:result.message || "手动指令调用失败！"});
                                }
                            },
                            error	: function () {
                                _commonFun.errorTips("错误");
                            }
                        });
                        $.DialogByZ.Close();
                    },
                    FunR: function () {
                        $.DialogByZ.Close();
                    }
                });

            });

        //     无货行驶
            $("#runWithoutGoods").on("click",function () {
                if(_commonFun.isBlank(stackerId)){
                    $.DialogByZ.Autofade({Content: "请选择堆垛机！"});
                    return false;
                };
                let locationId = $("#locationId").val();
                if(_commonFun.isBlank(locationId)){
                    $.DialogByZ.Autofade({Content: "请输入目的地库位！"});
                    return false;
                };
                // if (locationId<10100||locationId>22400){
                //     $.DialogByZ.Autofade({Content: "目的地库位格式错误！"});
                //     return false;
                // }
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确认要 无货行驶到库位 "+locationId+"?",
                    FunL: function () {
                        let data = {stackerId:stackerId,locationCode:locationId};
                        $.ajax({
                            type	:"POST",
                            url		: $.mpbGetHeaderPath()+"/stackerStatus/runWithoutGoods",
                            headers:{'token':localStorage.getItem('token')},
                            data:data,
                            success	: function (res) {
                                var result=res;
                                if(result.code == 0){
                                    _commonFun.successTips(null);
                                }else {
                                    $.DialogByZ.Autofade({Content:result.message || "手动指令调用失败！"});
                                }
                            },
                            error	: function () {
                                _commonFun.errorTips("错误");
                            }
                        });
                        $.DialogByZ.Close();
                    },
                    FunR: function () {
                        $.DialogByZ.Close();
                    }
                });

            });

            //     有货行驶
            $("#runWithGoods").on("click",function () {
                if(_commonFun.isBlank(stackerId)){
                    $.DialogByZ.Autofade({Content: "请选择堆垛机！"});
                    return false;
                };
                let locationId = $("#locationId").val();
                if(_commonFun.isBlank(locationId)){
                    $.DialogByZ.Autofade({Content: "请输入目的地库位！"});
                    return false;
                };
                // if (locationId<10100||locationId>22400){
                //     $.DialogByZ.Autofade({Content: "目的地库位格式错误！"});
                //     return false;
                // }
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确认要 有货行驶到库位 "+locationId+"?",
                    FunL: function () {
                        let data = {stackerId:stackerId,locationCode:locationId};
                        $.ajax({
                            type	:"POST",
                            url		: $.mpbGetHeaderPath()+"/stackerStatus/runWithGoods",
                            headers:{'token':localStorage.getItem('token')},
                            data:data,
                            success	: function (res) {
                                var result=res;
                                if(result.code == 0){
                                    _commonFun.successTips(null);
                                }else {
                                    $.DialogByZ.Autofade({Content:result.message || "手动指令调用失败！"});
                                }
                            },
                            error	: function () {
                                _commonFun.errorTips("错误");
                            }
                        });
                        $.DialogByZ.Close();
                    },
                    FunR: function () {
                        $.DialogByZ.Close();
                    }
                });

            });

            //     只取不放
            $("#onlyGet").on("click",function () {
                if(_commonFun.isBlank(stackerId)){
                    $.DialogByZ.Autofade({Content: "请选择堆垛机！"});
                    return false;
                };
                let locationId = $("#locationId").val();
                if(_commonFun.isBlank(locationId)){
                    $.DialogByZ.Autofade({Content: "请输入取货库位！"});
                    return false;
                };
                // if (locationId<10100||locationId>22400){
                //     $.DialogByZ.Autofade({Content: "目的地库位格式错误！"});
                //     return false;
                // }
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确认要 到库位 "+locationId+" 取货?",
                    FunL: function () {
                        let data = {stackerId:stackerId,locationCode:locationId};
                        $.ajax({
                            type	:"POST",
                            url		: $.mpbGetHeaderPath()+"/stackerStatus/onlyGet",
                            headers:{'token':localStorage.getItem('token')},
                            data:data,
                            success	: function (res) {
                                var result=res;
                                if(result.code == 0){
                                    _commonFun.successTips(null);
                                }else {
                                    $.DialogByZ.Autofade({Content:result.message || "手动指令调用失败！"});
                                }
                            },
                            error	: function () {
                                _commonFun.errorTips("错误");
                            }
                        });
                        $.DialogByZ.Close();
                    },
                    FunR: function () {
                        $.DialogByZ.Close();
                    }
                });

            });
            // 只放不取
            $("#onlySet").on("click",function () {
                if(_commonFun.isBlank(stackerId)){
                    $.DialogByZ.Autofade({Content: "请选择堆垛机！"});
                    return false;
                };
                let locationId = $("#locationId").val();
                if(_commonFun.isBlank(locationId)){
                    $.DialogByZ.Autofade({Content: "请输入放货库位！"});
                    return false;
                };
                // if (locationId<10100||locationId>22400){
                //     $.DialogByZ.Autofade({Content: "目的地库位格式错误！"});
                //     return false;
                // }
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确认要 到库位 "+locationId+" 放货?",
                    FunL: function () {
                        let data = {stackerId:stackerId,locationCode:locationId};
                        $.ajax({
                            type	:"POST",
                            url		: $.mpbGetHeaderPath()+"/stackerStatus/onlySet",
                            headers:{'token':localStorage.getItem('token')},
                            data:data,
                            success	: function (res) {
                                var result=res;
                                if(result.code == 0){
                                    _commonFun.successTips(null);
                                }else {
                                    $.DialogByZ.Autofade({Content:result.message || "手动指令调用失败！"});
                                }
                            },
                            error	: function () {
                                _commonFun.errorTips("错误");
                            }
                        });
                        $.DialogByZ.Close();
                    },
                    FunR: function () {
                        $.DialogByZ.Close();
                    }
                });

            });
            //回站台
            $("#backStation").on("click",function () {
                if(_commonFun.isBlank(stackerId)){
                    $.DialogByZ.Autofade({Content: "请选择堆垛机！"});
                    return false;
                };
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确认要回站台?",
                    FunL: function () {
                        let data = {stackerId:stackerId};
                        $.ajax({
                            type	:"POST",
                            url		: $.mpbGetHeaderPath()+"/stackerStatus/backStation",
                            headers:{'token':localStorage.getItem('token')},
                            data:data,
                            success	: function (res) {
                                var result=res;
                                if(result.code == 0){
                                    _commonFun.successTips(null);
                                }else {
                                    $.DialogByZ.Autofade({Content:result.message || "手动指令调用失败！"});
                                }
                            },
                            error	: function () {
                                _commonFun.errorTips("错误");
                            }
                        });
                        $.DialogByZ.Close();
                    },
                    FunR: function () {
                        $.DialogByZ.Close();
                    }
                });

            });
        }
    };
    _frontBasic.init();


});

