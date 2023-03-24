var _addEquipment;
$(function(){
    _edgeBox_service={
        //获取设备检列表
        getEdgeBoxs: function(equipments,resolve, reject){
            $.ajax({
                type	:"get",
                url		: "/IEAM/deviceManagement/edgeBox/getEdgeBoxInfo",
                data	: equipments,
                success	: resolve,
                error	: reject
            });
        },
        //新增设备信息
        addEdgeBoxInfo: function(equipmentInfo,resolve, reject){
            $.ajax({
                type	:"post",
                url		: "/IEAM/deviceManagement/edgeBox/addEdgeBox",
                data	: equipmentInfo,
                success	: resolve,
                error	: reject
            });
        },
        //检查基础设备编码是否可用
        checkCodeAvailable: function(codeInfo , resolve, reject){
            $.ajax({
                type		:"get",
                url			: "/IEAM/deviceManagement/edgeBox/checkCodeOrSerialAvailable",
                data		: codeInfo,//必含 code 编辑时含id
                success		: resolve,
                error		: reject
            });
        },
        //检查序列号是否可用
        checkSerialNumberAvailable: function(serialNumberInfo , resolve, reject){
            $.ajax({
                type		:"get",
                url			: "/IEAM/deviceManagement/edgeBox/checkCodeOrSerialAvailable",
                data		: serialNumberInfo,//必含 serialNumber 编辑时含id
                success		: resolve,
                error		: reject
            });
        },
        //修改设备采集终端信息
        editEdgeBoxInfo:function(equipmentInfo,resolve, reject){
            $.ajax({
                type	:"post",
                url		: "/IEAM/deviceManagement/edgeBox/updateEdgeBox",
                data	: equipmentInfo,
                success	: resolve,
                error	: reject
            });
        },
        //根据id获取设备采集终端信息
        getEdgeBoxById : function(orderNo, resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/orderOutDetail/queryByOrderNo/"+orderNo,
                headers:{'token':localStorage.getItem('token')},
                data    : {
                    orderNo  : orderNo
                },
                success	: resolve,
                error	: reject
            });
        },
        //获取未配置EdgeBox的采集终端列表
        getUnusedEdgeBoxMonitors : function(resolve, reject){
            $.ajax({
                type	: "get",
                url     : $.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                success : resolve,
                error   : reject
            });
        },
        getAvailableGoods : function(data, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/wmsStoragePlane/getAvailableGoods",
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
                        $("#goodsCode").html(options);
                    }
                },
                error	: reject
            });
        },
        findBatchNo : function(data, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/wmsStoragePlane/findBatchNo/"+data,
                headers:{'token':localStorage.getItem('token')},
                data    : { goodsCode :data},
                async:false,
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var options = '';
                        options +='<option value=""></option>'
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.dicItemCode+'">'+ele.dicItemCode+'</option>'
                        }
                        $("#batchNo").html(options);
                    }
                },
                error	: reject
            });
        },
        findLocation : function(goodsCode,batchNo, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/wmsStoragePlane/findLocation/",
                headers:{'token':localStorage.getItem('token')},
                data    : {
                    goodsCode :goodsCode,
                    batchNo:batchNo
                },
                async:false,
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        var options = '';
                        options +='<option value=""></option>'
                        for(var i=0;i < result.data.length; i++){
                            var ele = result.data[i];
                            options +='<option value="'+ele.dicItemCode+'">'+ele.dicItemCode+'</option>'
                        }
                        $("#locationCode").html(options);
                    }
                },
                error	: reject
            });
        },

        findAvaAmount : function(goodsCode,batchNo,locationCode, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/wmsStoragePlane/findAvaAmount/",
                headers:{'token':localStorage.getItem('token')},
                data    : {
                    goodsCode :goodsCode,
                    batchNo:batchNo,
                    locationCode:locationCode
                },
                async:false,
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        console.log(result);
                        var availableAmount = result.data[0].availableAmount;
                        console.log("availableAmount",availableAmount);
                        $("#availableAmount").val(availableAmount);
                        // options +='<option value=""></option>'
                        // for(var i=0;i < result.data.length; i++){
                        //     var ele = result.data[i];
                        //     options +='<option value="'+ele.dicItemCode+'">'+ele.dicItemCode+'('+ele.dicItemName+')</option>'
                        // }
                        // $("#batchNo").html(options);
                    }
                },
                error	: reject
            });
        },
    };
    //-----------------类别树代码-begin----------------------

    var getCategoryStructureTreeNode="",categoryTreeNodeId="", categoryTreeNodeName="";

    var treeObj="",loadNumber=0,interval="";
//-----------------类别树代码-end----------------------
     _addEquipment= {
        data: {
            deviceId :"",  //从列表传过来的
            deviceName :"",  //从列表传过来的
            equipmentId       : "",
            currentNumber   : 0,  //记录当前页有多少条数据
            totalPageNum    : 0,  //记录一共多少页
            numberOnePage   : 8, //一页几条数据
            equipments_id   : "",
            equipments_name :"",
            monitorsList    :[], //存储未配置设备列表 用于提交的
            monitorsUnusedList:[] ,//获取未配置设备的采集终端列表 用于chosen 下拉用
            isSaveBeforeDevice : true ,//标志 ， 是否保存了之前编辑的采集终端，保存 true，未保存 false
            codeUnused         : "",//未分配采集终端保存code 用
            idBeforeEdit : ""   //编辑之前的终端ID
        },
        init: function () {
            this.onLoad();
            this.bindEvent();
        },
        onLoad: function () {
            // this.loadUnusedDeviceMonitors();//获取未配置设备的采集终端列表
            this.loadEquipmentInfo();//加载设备信息
        },
        bindEvent: function () {
            var _this = this;
            //商品选择
            $(document).on('change', '#goodsCode', function () {
                var goodsCode = $.trim($("#goodsCode").val());

                // findDevices(deptId);
                console.log("goodsCode",goodsCode);
                _edgeBox_service.findBatchNo(goodsCode);
                $("#availableAmount").val("");
            })
            //批次选择
            $(document).on('change', '#batchNo', function () {
                var goodsCode = $.trim($("#goodsCode").val());
                var batchNo = $.trim($("#batchNo").val());

                // findDevices(deptId);
                console.log("goodsCode",goodsCode);
                console.log("batchNo",batchNo);
                _edgeBox_service.findLocation(goodsCode,batchNo);
                $("#availableAmount").val("");

            })
            //库位选择
            $(document).on('change', '#locationCode', function () {
                var goodsCode = $.trim($("#goodsCode").val());
                var batchNo = $.trim($("#batchNo").val());
                var locationCode = $.trim($("#locationCode").val());
                _edgeBox_service.findAvaAmount(goodsCode,batchNo,locationCode);
            })


            $(document).on('change', '#equipments-table input[type="checkbox"]', function () {
                var _self=$(this);
                $('#equipments-table input[type="checkbox"]').removeAttr("checked");
                _self.prop("checked","checked");
                _this.data.equipments_id=_self.parent().parent().parent().data("id");
                _this.data.equipments_name=_self.parent().parent().parent().find('.name').text();
            })
            //检查EdgeBox编号是否可用
            $(document).on('blur', '#equipmentCode', function () {
                var code    = $.trim($(this).val());
                var data    = {};
                if(code==""){
                    $(this).css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "EdgeBox编号，不能为空！"});
                    return;
                }
                $(this).removeAttr('style');
                if( _this.data.equipmentId=="" || _this.data.equipmentId=="undefined"  ){
                    data={
                        code : code
                    }
                }
                else {
                    data={
                        code    : code,
                        id      :  _this.data.equipmentId
                    }
                }
                _edgeBox_service.checkCodeAvailable(data ,function(res){
                    var result=JSON.parse(decodeURIComponent(res));
                    if(result.code==0) {
                        if (!result.data) {
                            $.DialogByZ.Autofade({Content:"EdgeBox编号不可用"});
                            $("#equipmentCode").val('');
                        }
                    } else {
                        $.DialogByZ.Autofade({Content:result.message});
                    }
                },function(errMsg){
                    _commonFun.errorTips('');
                })
            })
            //检查序列号是否可用
            $(document).on('blur', '#serialNumber', function () {
                var value  = $.trim($(this).val());
                var data  = {};
                if(value==""){
                    $(this).css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "序列号，不能为空！"});
                    return;
                }
                $(this).removeAttr('style');
                if( _this.data.equipmentId=="" || _this.data.equipmentId=="undefined"  ){
                    data={
                        serialNumber    : value,
                    }
                }
                else {
                    data={
                        serialNumber    : value,
                        id      :  _this.data.equipmentId
                    }
                }
                _edgeBox_service.checkSerialNumberAvailable(data ,function(res){
                    var result=JSON.parse(decodeURIComponent(res));
                    if(result.code==0) {
                        if (!result.data) {
                            $.DialogByZ.Autofade({Content:"序列号不可用"});
                            $("#serialNumber").val('');
                        }
                    } else {
                        $.DialogByZ.Autofade({Content:result.message});
                    }
                },function(errMsg){
                    _commonFun.errorTips('');
                })
            })
            $(document).on('blur', '#equipmentName', function (){
                if($.trim($(this).val())==""){
                    $(this).css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "EdgeBox名称，不能为空！"});
                    return;
                }
                $(this).removeAttr('style');
            })
            //保存保存详情
            $(document).on('click','.saveAll', function () {
                var monitors=[],monitorIds="",params="";
                $(_this.data.monitorsList).each(function (index, item) {
                    monitors.push(item.id);
                })
                if(monitors.length>0){
                    monitorIds= monitors.join(",");
                }
                if($.trim($("#equipmentCode").val())==""){
                    $("#equipmentCode").css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "EdgeBox编号，不能为空！"});
                    return;
                }
                $("#equipmentCode").removeAttr('style');
                if($.trim($("#equipmentName").val())==""){
                    $("#equipmentName").css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "EdgeBox名称，不能为空！"});
                    return;
                }
                $("#equipmentName").removeAttr('style');
                if($.trim($("#serialNumber").val())==""){
                    $("#serialNumber").css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "序列号，不能为空！"});
                    return;
                }
                $("#serialNumber").removeAttr('style');

                if(_this.data.monitorsList==[] || _this.data.monitorsList=="") {
                    $.DialogByZ.Confirm({
                        Title: "操作提示",
                        Content: "关联采集终端尚未添加，是否现在去完善？",
                        BtnL:"去完善",
                        BtnR:"否",
                        FunL:function(){
                            $('#myTabs a[href="#unusedList"]').tab('show');
                            $.DialogByZ.Close();
                        },
                        FunR:function(){
                            if( _this.data.equipmentId=="" || _this.data.equipmentId=="undefined"  ){
                                //新增 采集终端
                                var data={
                                    code	            : $.trim($("#equipmentCode").val()),
                                    name	            : $.trim($("#equipmentName").val()),
                                    specification        : $.trim($("#specification").val()),
                                    type	    : $.trim($("#type").val()),
                                    serialNumber	    : $.trim($("#serialNumber").val()),
                                    purchaseTime	    : $.trim($("#purchaseTime").val()),
                                    installTime	        : $.trim($("#installTime").val()),
                                    disableTime	        : $.trim($("#disableTime").val()),
                                    location          : $("#location").val(),
                                    monitorIds          : monitorIds
                                }

                                _edgeBox_service.addEdgeBoxInfo(data, function (res) {
                                    var result=JSON.parse(decodeURIComponent(res));

                                    if(result.code==0) {
                                        $.DialogByZ.Autofade({Content: "新增EdgeBox成功！"});
                                        window.location.href="edgeBox.html";
                                    }
                                    else {
                                        $.DialogByZ.Autofade({Content:result.message || "保存失败！"});
                                    }
                                }, function (err) {
                                    _commonFun.errorTips('');
                                })
                            }
                            else{
                                //编辑
                                var data={
                                    id                  : _this.data.equipmentId,
                                    code	            : $.trim($("#equipmentCode").val()),
                                    name	            : $.trim($("#equipmentName").val()),
                                    specification        : $.trim($("#specification").val()),
                                    type	    : $.trim($("#type").val()),
                                    serialNumber	    : $.trim($("#serialNumber").val()),
                                    purchaseTime	    : $.trim($("#purchaseTime").val()),
                                    installTime	        : $.trim($("#installTime").val()),
                                    disableTime	        : $.trim($("#disableTime").val()),
                                    location          : $("#location").val(),
                                    monitorIds          : monitorIds
                                }

                                _edgeBox_service.editEdgeBoxInfo(data, function (res) {
                                    var result=JSON.parse(decodeURIComponent(res));
                                    if(result.code==0) {
                                        $.DialogByZ.Autofade({Content: "修改EdgeBox成功！"});
                                        window.location.href="edgeBox.html";
                                    }
                                    else {
                                        $.DialogByZ.Autofade({Content:res.message || "修改失败！"});
                                    }
                                }, function (err) {
                                    _commonFun.errorTips('');
                                })
                            }
                        }
                    })
                }
                else{
                    if( _this.data.equipmentId=="" || _this.data.equipmentId=="undefined"  ){
                        //新增 采集终端
                        var data={
                            code	            : $.trim($("#equipmentCode").val()),
                            name	            : $.trim($("#equipmentName").val()),
                            specification        : $.trim($("#specification").val()),
                            type	    : $.trim($("#type").val()),
                            serialNumber	    : $.trim($("#serialNumber").val()),
                            purchaseTime	    : $.trim($("#purchaseTime").val()),
                            installTime	        : $.trim($("#installTime").val()),
                            disableTime	        : $.trim($("#disableTime").val()),
                            location          : $("#location").val(),
                            monitorIds          : monitorIds
                        }

                        _edgeBox_service.addEdgeBoxInfo(data, function (res) {
                            var result=JSON.parse(decodeURIComponent(res));

                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "新增EdgeBox成功！"});
                                window.location.href="edgeBox.html";
                            }
                            else {
                                $.DialogByZ.Autofade({Content:result.message || "保存失败！"});
                            }
                        }, function (err) {
                            _commonFun.errorTips('');
                        })
                    }
                    else{
                        //编辑 级别
                        var data={
                            id                  : _this.data.equipmentId,
                            code	            : $.trim($("#equipmentCode").val()),
                            name	            : $.trim($("#equipmentName").val()),
                            specification        : $.trim($("#specification").val()),
                            type	    : $.trim($("#type").val()),
                            serialNumber	    : $.trim($("#serialNumber").val()),
                            purchaseTime	    : $.trim($("#purchaseTime").val()),
                            installTime	        : $.trim($("#installTime").val()),
                            disableTime	        : $.trim($("#disableTime").val()),
                            location          : $("#location").val(),
                            monitorIds          : monitorIds
                        }
                        _edgeBox_service.editEdgeBoxInfo(data, function (res) {
                            var result=JSON.parse(decodeURIComponent(res));
                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "修改EdgeBox成功！"});
                                window.location.href="edgeBox.html";
                            }
                            else {
                                $.DialogByZ.Autofade({Content:res.message || "修改失败！"});
                            }
                        }, function (err) {
                            _commonFun.errorTips('');
                        })
                    }
                }
            })
            //第二个tab新增未配置采集终端
            $(document).on("click", "#addUnusedDevice", function () {
                //供应商
                // _buz_service.getSupplyList();
                // _buz_service.getWarehouseCode();
                _edgeBox_service.getAvailableGoods();
                //备件确认人
                // $("#confirmPerson").prop("disabled",false);
                //显示 提交按钮和启用下拉框和 input
                $("#saveButton").show().text("保存");
                $("#formInfo").find("i").show();
                $("form  select").prop("disabled",false);
                $("form  input").prop("disabled",false);
                $("#orderNo").val(_this.data.orderNo);


                //路线编码默认不可编辑
                $("#orderNo").prop("disabled",true);
                $("#availableAmount").val("");
                $("#availableAmount").prop("disabled",true);
                $("#orderOutId").val("");
                $("#warehouseCode").val("");
                $("#areaCode").val("");

                $("#goodsCode").val("");
                $("#batchNo").val("");
                $("#locationCode").val("");
                $("#palletCode").val("");
                $("#planAmount").val("");
                $("#outSeq").val("");
                $("#layerNumber").val("");
                $("#columnNumber").val("");
                $("#useStatus").val("");
                $("#myModalLabel").text('新增出库明细');
                $("#formModel").modal({backdrop:false});


                // if(_this.data.monitorsUnusedList==[] || _this.data.monitorsUnusedList==""){
                //     $.DialogByZ.Autofade({Content:'无可关联的采集终端！'});
                // }
                // else {
                //     _this.chose_get_ini(".chosen-select");
                //     var tableLine='<tr data-id=""><td class="code"><select  data-placeholder="请选择采集终端" id="popDeviceId" class="chosen-select"' +
                //         'style="width:250px;" tabindex="4" ></select></td><td class="name"></td><td class="description"></td>'+
                //         '<td><a class="link a-saveUnused"><i class="fa-table-line-save"  title="保存"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="link a-cancelUnused"><i class="fa-table-line-undo"  title="撤销"></i></a></td><td><input type="hidden" class="hidUnusedVal" value=""/></td></tr>';
                //
                //     if(_this.data.isSaveBeforeDevice){
                //         $("#deviceUnusedList tbody").append(tableLine);
                //         $(".chosen-select").html("");
                //         $(".chosen-select").chosen();
                //         $(".chosen-select").chosen("destroy");
                //         _this.chose_get_ini(".chosen-select");
                //         $(".chosen-select").trigger("liszt:updated");
                //
                //         var unused = "<option value=''>请选择采集终端</option>";
                //         for (var i = 0; i < _this.data.monitorsUnusedList.length; i++) {
                //             unused += "<option value="+ _this.data.monitorsUnusedList[i].id +">" + _this.data.monitorsUnusedList[i].code + " --- " + _this.data.monitorsUnusedList[i].name + " </option>";
                //         }
                //         $(".chosen-select").html(unused);
                //         _this.chose_get_ini(".chosen-select");
                //         $(".chosen-select").trigger("liszt:updated");
                //         _this.data.isSaveBeforeDevice=false;
                //     }
                //     else {
                //         $.DialogByZ.Autofade({Content:'请先保存当前采集终端！'});
                //     }
                // }

            })

            //新增或更新信息
            $("#saveButton").on("click",function () {
                var orderOutDetailId = $.trim($("#orderOutDetailId").val());
                console.log("orderOutDetailId",orderOutDetailId);
                var orderNo = $.trim($("#orderNo").val());
                var goodsCode = $.trim($("#goodsCode").val());
                var batchNo = $.trim($("#batchNo").val());
                var locationCode = $.trim($("#locationCode").val());
                var planAmount = $.trim($("#planAmount").val());
                var remark = $.trim($("#remark").val());

                //添加数据校验
                if(_commonFun.isBlank(orderNo)){
                    $.DialogByZ.Autofade({Content: "出库单号不能为空"});
                    return false;
                }
                if(_commonFun.isBlank(goodsCode)){
                    $.DialogByZ.Autofade({Content: "商品编码不能为空"});
                    return false;
                }
                if(_commonFun.isBlank(batchNo)){
                    $.DialogByZ.Autofade({Content: "批次号不能为空"});
                    return false;
                }
                if(_commonFun.isBlank(locationCode)){
                    $.DialogByZ.Autofade({Content: "库位不能为空"});
                    return false;
                }
                if(_commonFun.isBlank(planAmount)){
                    $.DialogByZ.Autofade({Content: "计划数量不能为空"});
                    return false;
                }

                var data= {

                    orderNo:orderNo,
                    goodsCode:goodsCode,
                    batchNo:batchNo,
                    locationCode:locationCode,
                    planAmount:planAmount,
                    remark:remark

                };

                //判断是新增还是修改
                if (!_commonFun.isNotBlank(orderOutDetailId)){
                    data.orderOutId =  _this.data.orderOutId;
                    //不存在新增
                    $.ajax({
                        type	:"POST",
                        url		: $.mpbGetHeaderPath()+"/orderOutDetail/c",
                        headers:{'token':localStorage.getItem('token')},
                        data    : data,
                        success	: function (res) {
                            var result=res;
                            if(result.code == 0){
                                _commonFun.successTips(result.data);
                                _this.loadEquipmentInfo();
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
                    data.orderOutDetailId = orderOutDetailId;
                    $.ajax({
                        type	:"POST",
                        url		: $.mpbGetHeaderPath()+"/orderOutDetail/u",
                        headers:{'token':localStorage.getItem('token')},
                        data    : data,
                        success	: function (res) {
                            var result=res;
                            if(result.code == 0){
                                _commonFun.successTips(result.data);
                                _this.loadEquipmentInfo();
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


            //未分配采集终端中的新增chosen  change事件
            $(document).on('change', '#popDeviceId', function (e){
                e.stopPropagation();
                var _self=$(this);
                var id=_self.val();
                _self.parent().parent().attr("data-id",id);
                _self.parent().parent().find(".hidUnusedVal").val(id);
                $(_this.data.monitorsUnusedList).each(function (index,item){
                    if(item.id==id){
                        _self.parent().parent().find(".name").text(item.name);
                        _self.parent().parent().find(".description").text(item.description);
                        _this.data.codeUnused=item.code;
                    }
                })
            })
            //未分配采集终端列表中的保存
            $(document).on('click', '.a-saveUnused', function (e){
                e.stopPropagation();
                var _self=$(this);
                var id= _self.parent().parent().find(".hidUnusedVal").val();
                if(id!=""){
                    $(_this.data.monitorsList).each(function(index,item){
                        if(item.id==_this.data.idBeforeEdit){
                            _this.data.monitorsList.splice(index, 1);  //保存之前，先把编辑之前的终端删除
                        }
                    })
                    $(_this.data.monitorsUnusedList).each(function(index,item){
                        if(item.id==id){
                            var unused  ={};
                            unused.id   = item.id;
                            unused.code = item.code;
                            unused.name =item.name;
                            unused.description= item.description;
                            _this.data.monitorsList.push(unused);
                            _this.data.monitorsUnusedList.splice(index, 1);
                            _self.parent().parent().find('.code').text(_this.data.codeUnused);
                        }
                    })
                    //保存 取消 变 编辑 删除
                    _self.removeClass("a-saveUnused").addClass("a-editUnused");
                    _self.find("i").removeClass("fa-table-line-save").addClass("fa-table-line-edit");
                    _self.siblings().removeClass("a-cancelUnused").addClass("a-deleteUnused");
                    _self.siblings().find("i").removeClass("fa-table-line-undo").addClass("fa-table-line-delete");

                    _this.data.isSaveBeforeDevice=true;
                }
                else {
                    $.DialogByZ.Autofade({Content:'请选择采集终端！'});
                }
            })
            //未分配采集终端列表中的取消
            $(document).on('click', '.a-cancelUnused', function (e){
                e.stopPropagation();
                var _self=$(this);
                _this.data.isSaveBeforeDevice=true;
                _self.parent().parent().remove();
            })
            //未分配采集终端列表中的编辑
            $(document).on('click', '.a-editUnused', function (){
                var _self   =$(this);
                //可以正常编辑
                if(_this.data.isSaveBeforeDevice){
                    var id=_self.parent().parent().find(".hidUnusedVal").val();
                    // 添加到_this.data.monitorsUnusedList 用于显示
                    $(_this.data.monitorsList).each(function(index,item){
                        if(item.id==id){
                            var unused  ={};
                            unused.id   = item.id;
                            unused.code = item.code;
                            unused.name = item.name;
                            unused.description= item.description;
                            _this.data.monitorsUnusedList.push(unused);
                            _this.data.idBeforeEdit = id;
                        }
                    })

                    _this.chose_get_ini(".chosen-select");
                    _self.parent().parent().find(".code").html("");
                    var tableLine='<select id="popDeviceId" class="chosen-select" style="width:250px;" tabindex="4" ><option value="">请选择采集终端</option>';
                        for (var i = 0; i < _this.data.monitorsUnusedList.length; i++) {
                            tableLine += "<option value=" + _this.data.monitorsUnusedList[i].id + " data-code="+ _this.data.monitorsUnusedList[i].code + " data-name="+ _this.data.monitorsUnusedList[i].name+ ">" + _this.data.monitorsUnusedList[i].code + " ( " + _this.data.monitorsUnusedList[i].name + " ) </option>";
                        }
                    tableLine+='</select>';
                    _self.parent().parent().find(".code").html(tableLine);
                    $(".chosen-select").chosen("destroy");
                    $(".chosen-select").trigger("chosen:updated");
                    //清空Id隐藏域
                    _self.parent().parent().find(".hidUnusedVal").val("");
                    _this.data.isSaveBeforeDevice=false;
                    //编辑 删除 变 保存 取消
                    _self.removeClass("a-editUnused").addClass("a-saveUnused");
                    _self.find("i").removeClass("fa-table-line-edit").addClass("fa-table-line-save");
                    _self.siblings().removeClass("a-deleteUnused").addClass("a-cancelUnusedForEdit");
                    _self.siblings().find("i").removeClass("fa-table-line-delete").addClass("fa-table-line-undo");
                }
                else {
                    $.DialogByZ.Autofade({Content:'请先保存采集终端！'});
                }
            })
            //正在编辑中的采集终端的取消
            $(document).on('click', '.a-cancelUnusedForEdit', function (e){
                e.stopPropagation();
                _this.data.isSaveBeforeDevice=true;
                _this.loadEquipmentUnusedList(_this.data.monitorsList);  //重新加载关联采集终端列表
            })
            //未分配采集终端列表中的删除
            $(document).on('click', '.a-deleteUnused', function (){
                var _self = $(this);
                var id = _self.parent().parent().find(".hidUnusedVal").val();
                //防止重复 准备提交给后台  && 添加到_this.data.monitorsUnusedList 用于其他使用
                $(_this.data.monitorsList).each(function (index, item) {
                    if (item.id == id) {
                        var unused = {};
                        unused.id = item.id;
                        unused.code = item.code;
                        unused.name = item.name;
                        unused.description = item.description;
                        _this.data.monitorsUnusedList.push(unused);
                        _this.data.monitorsList.splice(index, 1);
                        return;
                    }
                })
                _this.data.isSaveBeforeDevice = true;
                $(".chosen-select").empty();
                $(".chosen-select").chosen("destroy");
                $(".chosen-select").trigger("liszt:updated");
                _self.parent().parent().remove();
            })
            //清空弹窗文本内容
            $(document).on('click', '.clearAll', function () {
                $("#equipmentCode").val("");
                $("#equipmentName").val("");
                $("#specification").val("");
                $("#type").val("");
                $("#serialNumber").val("");
                $("#purchaseTime").val("");
                $("#installTime").val("");
                $("#disableTime").val("");
                $("#location").val("");
            })
            //返回
            $(document).on('click', '.return', function (){
                // $.DialogByZ.Confirm({
                //     Title: "操作提示",
                //     Content: "确定要返回吗，返回将不保存本页数据？",
                //     FunL:function(){
                //
                //         $.DialogByZ.Close();
                //     },
                //     FunR:function(){
                //         $.DialogByZ.Close();
                //     }
                // })
                _this.data.equipments_id        ="";
                _this.data.equipments_name      ="";
                _this.data.monitorsList         =[]; //存储未配置设备列表 用于提交的
                _this.data.monitorsUnusedList   =[];//获取未配置设备的采集终端列表 用于chosen 下拉用
                _this.data.isSaveBeforeDevice   = true ;
                window.location.href="wmsOrderOutPlaneList.html";
            })
        },
        //加载设备信息
        loadEquipmentInfo : function () {
            var _this = this;
            var req = GetRequest();

            var orderOutId = req['orderOutId'];
            console.log("orderOutId",orderOutId);
            _this.data.orderOutId = orderOutId;
            var orderNo = req["orderNo"];
            console.log("orderNo",orderNo);
            var orderStatus = req["orderStatus"];
            _this.data.orderStatus = orderStatus;
            $("#addUnusedDevice").css("display","");
            if(orderStatus!=1){
                $("#addUnusedDevice").css("display","none");
            }


            if(orderNo==-1) {
                _this.data.equipmentId = "";
                //新增
                $("#equipmentCode").val("");
                $("#equipmentName").val("");
                $("#specification").val("");
                $("#type").val("");
                $("#serialNumber").val("");
                $("#purchaseTime").val("");
                $("#installTime").val("");
                $("#disableTime").val("");
                $("#location").val("");
                $("#twoLog").empty();
            }
            else {//编辑
                $(".breadcrumb .active").text("出库明细");
                _this.data.orderNo = orderNo;
                // console.log(window.location.href.split("="));
                _edgeBox_service.getEdgeBoxById(_this.data.orderNo, function (res) {
                    var result=res;
                    if(result.code==0) {
                        var data=result.data;
                        interval=setInterval(function () {
                            console.log("data",data)
                            _this.equipmentInfoSource(data);
                        },10);
                    }
                    else {
                        $.DialogByZ.Autofade({Content: result.message ||  "查无数据！"});
                    }
                },function(err){
                    _commonFun.errorTips('');
                })
            }
        },
        //编辑采集终端获取的数据源 显示在页面
        equipmentInfoSource:function (data) {
            var _this=this;
            clearInterval(interval);
            // $("#equipmentCode").val(data.edgeBox.code);
            // $("#equipmentName").val(data.edgeBox.name);
            // $("#specification").val(data.edgeBox.specification);
            // $("#type").val(data.edgeBox.type);
            // $("#serialNumber").val(data.edgeBox.serialNumber);
            // $("#purchaseTime").val(data.edgeBox.purchaseTime);
            // $("#installTime").val(data.edgeBox.installTime);
            // $("#disableTime").val(data.edgeBox.disableTime);
            // $("#location").val(data.edgeBox.location);
            _this.data.monitorsList     =data || [];
            console.log("_this.data.monitorsList",_this.data.monitorsList);
            _this.loadEquipmentUnusedList(_this.data.monitorsList);
        },
        //编辑采集终端的修改记录列表渲染
        loadModifyHistory:function (list) {
            $("#historicalRecord table").empty();
            var str="";
            for(ele in list){
                str+='<tr>' +
                    '<td><i class="font-i"></i>'+list[ele].person+'</td>'+
                    '<td>'+list[ele].operateTime+'</td>'+
                    '</tr>';
            }
            $("#historicalRecord table").html(str);
        },
        //编辑采集终端的创建和最新修改渲染
        loadCreateAndUpdate:function (list) {
            $("#twoLog").empty();
            var str="";
            for(ele in list){
                str+='<tr>' +
                    '<td>'+list[ele].person+'</td><td></td>'+
                    '<td>'+list[ele].operateTime+'</td>';
            }
            str+='<td class="more-log" id="more-log">查看更多></td>' +
                '</tr>'
            $("#twoLog").html(str);
        },
        //获取未配置设备的采集终端列表
        loadUnusedDeviceMonitors:function () {
            var _this=this;
            _edgeBox_service.getUnusedEdgeBoxMonitors(function (res) {
                var result=res;
                if(result.code==0) {
                    _this.data.monitorsUnusedList=result.data.monitors;
                    console.log("monitorsUnusedList",_this.data.monitorsUnusedList);
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        //未配置设备的采集终端列表渲染
        loadEquipmentUnusedList:function (list) {
            console.log("list",list);
            if(list!=[]){
                $("#deviceUnusedList tbody").empty();
                var str= "";
                for(ele in list){
                    var edit = '<td><a onclick="editOneRecord(\''+list[ele].orderOutDetailId+'\',1)"> <i class="fa-table-line-edit"  title="编辑"></i></a>&nbsp;';
                    var deleteA = '<td><a onclick="deleteOneRecord(\''+list[ele].orderOutDetailId+'\')"><i class="fa-table-line-delete"  title="删除"></i></a></td>';
                    str+='<tr  data-id="'+list[ele].orderOutDetailId+'">' +
                        '<td  class="orderNo">' + list[ele].orderNo + '</td>' +
                        '<td  class="goodsCode">' + list[ele].goodsCode + '</td>' +
                        '<td  class="goodsName">' + list[ele].goodsName + '</td>' +
                        '<td  class="batchNo">' + list[ele].batchNo + '</td>' +
                        '<td  class="locationCode">' + list[ele].locationCode + '</td>' +
                        '<td  class="planAmount">' + list[ele].planAmount + '</td>' +
                        '<td  class="realAmount">' + list[ele].realAmount + '</td>'
                        if(this.data.orderStatus == "1"){
                            str+=edit
                            str+=deleteA
                        }
                    str+='<td><input type="hidden" class="hidUnusedVal" value="'+list[ele].orderOutDetailId+'"/></td>' +
                        '</tr>';
                }
                $("#deviceUnusedList tbody").html(str);
            }
        },
        //多选select 数据同步
        chose_get_ini : function (select){
            $(select).chosen().change(function(){$(select).trigger("liszt:updated");});
        },
        //select value获取
        chose_get_value:function (select){
            return $(select).val();
        },
        //select text获取，多选时请注意
        chose_get_text:function (select){
            return $(select+" option:selected").text();
        }
    };
    //时间控件初始化
    function  initDatetime () {
        $(".datainp").jeDate({
            festival: false,
            ishmsVal: false,
            initAddVal:[0],
            format: "YYYY-MM-DD",
            zIndex: 3000
        });
    }
    initDatetime();

    _addEquipment.init();


});

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]]=strs[i].split("=")[1];
        }
    }
    return theRequest;
}
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
function editOneRecord(orderOutDetailId,type) {
    var id = orderOutDetailId;
    $("#type").val(type);
    //供应商
    // _buz_service.getSupplyList();
    // _buz_service.getWarehouseCode();
    // _buz_service.getAreaCodeAll();
    _edgeBox_service.getAvailableGoods();
    //查看时，隐藏提交按钮 和禁用下拉框和input
    if(type == 0 ){
        $("#formInfo").find("i").hide();
        $("#myModalLabel").text('查看出库明细信息');
        $("#saveButton").hide();
        $("form  select").prop("disabled",true);
        $("form  input").prop("disabled",true);
    }else if (type == 1){
        $("#formInfo").find("i").show();
        $("#myModalLabel").text('编辑出库明细信息');
        //编辑时，显示 提交按钮和启用下拉框和 input
        $("#saveButton").show().text("保存");
        $("form  select").prop("disabled",false);
        $("form  input").prop("disabled",false);
        //路线编码默认不可编辑
        $("#orderNo").prop("disabled",true);
        $("#availableAmount").prop("disabled",true);

    }

    //查看备件信息 模态框弹出
    $.ajax({
        type	:"get",
        url		: $.mpbGetHeaderPath()+"/orderOutDetail/r/"+id,
        headers:{'token':localStorage.getItem('token')},
        data    : {id:id},
        success	: function (res) {
            var result=res;
            if(result.code == 0){
                var data = result.data;
                if(data != null){
                    $("#orderOutDetailId").val(data.orderOutDetailId);
                    $("#orderNo").val(data.orderNo);
                    $("#goodsCode").val(data.goodsCode);
                    //批次选择
                    _edgeBox_service.findBatchNo(data.goodsCode);

                    $("#batchNo").val(data.batchNo);
                    _edgeBox_service.findLocation(data.goodsCode,data.batchNo);

                    $("#locationCode").val(data.locationCode);
                    //可用数量赋值
                    _edgeBox_service.findAvaAmount(data.goodsCode,data.batchNo,data.locationCode);
                    $("#planAmount").val(data.planAmount);

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

//删除
function deleteOneRecord(id) {
    $.DialogByZ.Confirm({
        Title: "操作提示",
        Content:"确定要删除出库明细?",
        FunL:function(){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/orderOutDetail/delete",
                headers:{'token':localStorage.getItem('token')},
                data    : {orderOutDetailId:id},
                success	: function (res) {
                    var result=res;
                    if(result.code == 0){
                        _commonFun.successTips(result.data);
                        _addEquipment.loadEquipmentInfo();
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
