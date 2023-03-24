$(function(){
    var _addEquipment_service={
        //新增设备信息
        addBaseEquipmentInfo: function(equipmentInfo,resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+"/location/list",
                headers:{'token':localStorage.getItem('token')},
                data	: equipmentInfo,
                success	: resolve,
                error	: reject
            });
        },
        //检查设备编码是否可用
        checkEquipmentCodeAvailable: function(codeInfo , resolve, reject){
            $.ajax({
                type		:"get",
                url			: $.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                data		: codeInfo,//必含 code 编辑时含id
                success		: resolve,
                error		: reject
            });

        },
        //修改设备采集终端信息
        editBaseEquipmentInfo:function(equipmentInfo,resolve, reject){
            $.ajax({
                type	:"post",
                url		:$.mpbGetHeaderPath()+"/location/list",
                headers:{'token':localStorage.getItem('token')},
                data	: equipmentInfo,
                success	: resolve,
                error	: reject
            });
        },
        //根据id获取设备信息
        queryBaseEquipmentByID : function(id, resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                data    : {
                    id  : id
                },
                success	: resolve,
                error	: reject
            });
        },
        //查询参数是否已使用
        queryParamIsUsed : function(id, resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                data    : {
                    id  : id
                },
                success	: resolve,
                error	: reject
            });
        },
        /*//获取部门列表
        getDepartment : function(resolve, reject){
            $.ajax({
                type	:"get",
                url		: "/IEAM/basicInfoManagement/departmentManagement/getDeptList",
                success	: resolve,
                error	: reject
            });
        },*/
        //获取设备分类结构
        getCategoryStructure : function(resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                success	: resolve,
                error	: reject
            });
        },
        //获取设备状态信息
        getDeviceStatus: function(resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                success	: resolve,
                error	: reject
            });
        },
        //获取组织机构结构树
        getDepartmentStructure: function(resolve, reject){
            $.ajax({
                type	:"get",
                url		:$.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                success	: resolve,
                error	: reject
            });
        },
        //根据设备分类，获取对应的出厂参数
        getCategoryParameter : function(data,resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                data : data,
                success	: resolve,
                error	: reject
            });
        },
        //获取出厂参数信息中，属性为下拉列表或多选框的数据源信息
        getFactoryPara: function(data,resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                data	: data,
                success	: resolve,
                error	: reject
            });
        },
        //获取父级关联设备信息
        getParentEquipment: function(device,resolve, reject){
            $.ajax({
                type	:"get",
                url		:$.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                data : device,
                success	: resolve,
                error	: reject
            });
        },
        //获取岗位权限列表
        getPostsAndPermissions : function(resolve, reject){
            $.ajax({
                type	: "get",
                url     : $.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                success : resolve,
                error   : reject
            });
        },
        //获取未配置设备的采集终端列表
        getUnusedDeviceMonitors : function(resolve, reject){
            $.ajax({
                type	: "get",
                url     : $.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                success : resolve,
                error   : reject
            });
        },
        //获取备件列表
        getSparePartBasicList : function(data, resolve, reject){
            $.ajax({
                type	:"get",
                url		: $.mpbGetHeaderPath()+"/area/getAreaCodeAll",
                headers:{'token':localStorage.getItem('token')},
                data    : data || {},
                success	: resolve,
                error	: reject
            });
        }
    };

    //-----------------岗位树代码-begin----------------------
    var InspectionInitSetting={
        view: {
            dblClickExpand: false,
            showIcon: false
        },
        edit: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
            },
            key: {
                children: "subCategories",
                name: "NAME"
            }
        },
        callback: {
            onClick: onClickInspectionTree
        }
    }
    var MaintainInitSetting={
        view: {
            dblClickExpand: false,
            showIcon: false
        },
        edit: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
            },
            key: {
                children: "subCategories",
                name: "NAME"
            }
        },
        callback: {
            onClick: onClickMaintainTree
        }
    }
    var OperationInitSetting={
        view: {
            dblClickExpand: false,
            showIcon: false
        },
        edit: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
            },
            key: {
                children: "subCategories",
                name: "NAME"
            }
        },
        callback: {
            onClick: onClickOperationTree
        }
    }
    var CheckInitSetting={
        view: {
            dblClickExpand: false,
            showIcon: false
        },
        edit: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
            },
            key: {
                children: "subCategories",
                name: "NAME"
            }
        },
        callback: {
            onClick: onClickCheckTree
        }
    }

    //-----------------岗位树代码-end----------------------
    //-----------------类别树代码-begin----------------------

    var categoryInitSetting={
        view: {
            dblClickExpand: false,
            showIcon: false
        },
        edit: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
            },
            key: {
                children: "subCategories",
                name: "name"
            }
        },
        callback: {
            onClick: onClickCategoryTree
        }
    }
    var getCategoryStructureTreeNode="",categoryTreeNodeId="", categoryTreeNodeName="",deptZtreeObj;

    var getPostStructureTreeNode1="",getPostStructureTreeNode2="",getPostStructureTreeNode3="",getPostStructureTreeNode4="";

    var treeObj="",loadNumber=0,interval="";
    var paramNumber = 0;  //记录出厂参数个数
//-----------------类别树代码-end----------------------
    var _addEquipment= {
        data: {
            deviceId :"",  //从列表传过来的
            deviceName :"",  //从列表传过来的
            departmentId    :"",//从列表传过来的
            equipmentId       : "",
            currentNumber   : 0,  //记录当前页有多少条数据
            totalPageNum    : 0,  //记录一共多少页
            numberOnePage   : 8, //一页几条数据
            paramsNameVal   : "",//存放编辑时参数name
            paramsNameVal2   : "",//存放编辑时点巡检项name
            paramsNameVal3   : "",//存放编辑时保养参数name
            equipments_id   : "",
            equipments_name :"",
            equipmentParams :[], //存储采集参数
            factoryParamsList :[],//存储出厂参数
            monitorsList    :[], //存储未配置设备列表 用于提交的
            checkParamList  :[], //存储点巡检项信息
            preventiveParamList  :[],  //存储保养参数信息
            monitorsUnusedList:[] ,//获取未配置设备的采集终端列表 用于chosen 下拉用
            isSaveBeforeDevice : true ,//标志 ， 是否保存了之前编辑的采集终端，保存 true，未保存 false
            codeUnused         : "",//未分配采集终端保存code 用
            idBeforeEdit : "",   //编辑之前的终端ID
            source:"" //页面来源
        },
        init: function () {
            this.onLoad();
            this.bindEvent();
        },
        onLoad: function () {
            // this.data.source = commonNew.getQueryString("source")|| "";//判断页面来源
            this.data.source = "";
            // this.loadDepartmentTreeOptions();//部门下拉框
            // this.loadDeviceStatus();  //获取设备状态列表
            // this.loadPostsList();//维修方、操作方chosen列表
            // this.loadUnusedDeviceMonitors();//获取未配置设备的采集终端列表
            this.loadEquipmentInfo();//加载设备信息
            // this.loadCheckDictionaryParam();//新增点巡检参数--加载字典列表信息
            // this.loadSparePartBasicList();
        },
        bindEvent: function () {
            var _this = this;
            //搜索文本框变化
            $("#codeOrName_search").bind("input propertychange", function () {
                _this.loadBaseEquipments();
            });
            //点击搜索
            $(document).on('click', '#searchEquipments', function () {
                _this.loadBaseEquipments();
            });
            //关闭所属设备弹窗
            $(document).on('click', '#pop-equipment-close', function () {
                $("#codeOrName_search").val("");
                $("#treeListContainerUL").empty();
                $("#equipmentSelect-modal").hide();
            })
            $(document).on('change', '#equipments-table input[type="checkbox"]', function () {
                var _self=$(this);
                $('#equipments-table input[type="checkbox"]').removeAttr("checked");
                _self.prop("checked","checked");
                _this.data.equipments_id=_self.parent().parent().parent().data("id");
                _this.data.equipments_name=_self.parent().parent().parent().find('.name').text();
            })
            //选择设备分类
            $(document).on('click','#deviceCategorySelect' ,function (e) {
                e.stopPropagation();
                _this.loadDeviceTreeOptions();//加载设备分类信息
            })
            //收起设备分类树
            $(document).on('click' ,'.tab-content', function (e) {
                e.stopPropagation();
                $(".categorySearchTree").slideUp();
                $(".posiInspectionSearchTree").slideUp();
                $(".posiMaintainSearchTree").slideUp();
                $(".posiOperationSearchTree").slideUp();
                $(".posiCheckSearchTree").slideUp();
            });
            //展开设备分类树
            $(document).on('click' ,'treeSearchContainerUL' , function (e) {
                e.stopPropagation();
                var nodes = getCategoryStructureTreeNode.getSelectedNodes();
                var data_id = $(".categorySpan").attr("data-id");
                if (nodes.length<=0 && data_id == "" ) {
                    $(".categorySpan").attr("data-id","");
                    $(".categorySpan").text('设备分类');
                }
                $(".categorySearchTree").slideUp();
            });
            //展开点巡检岗
            $(document).on('click' ,'#posiInspectionSelect' , function (e) {
                e.stopPropagation();
                var nodes = getPostStructureTreeNode1.getSelectedNodes();
                var data_id = $(".posiInspectionSpan").attr("data-id");
                if (nodes.length<=0 && data_id == "" ) {
                    $(".posiInspectionSpan").attr("data-id","");
                    $(".posiInspectionSpan").text('点巡检岗');
                }
                $(".posiInspectionSearchTree").slideToggle();
            });
            //展开维修岗
            $(document).on('click' ,'#posiMaintainSelect' , function (e) {
                e.stopPropagation();
                var nodes = getPostStructureTreeNode2.getSelectedNodes();
                var data_id = $(".posiMaintainSpan").attr("data-id");
                if (nodes.length<=0 && data_id == "" ) {
                    $(".posiMaintainSpan").attr("data-id","");
                    $(".posiMaintainSpan").text('维修岗');
                }
                $(".posiMaintainSearchTree").slideToggle();
            });
            //展开操作岗
            $(document).on('click' ,'#posiOperationSelect' , function (e) {
                e.stopPropagation();
                var nodes = getPostStructureTreeNode3.getSelectedNodes();
                var data_id = $(".posiOperationSpan").attr("data-id");
                if (nodes.length<=0 && data_id == "" ) {
                    $(".posiOperationSpan").attr("data-id","");
                    $(".posiOperationSpan").text('操作岗');
                }
                $(".posiOperationSearchTree").slideToggle();
            });
            //展开审核岗
            $(document).on('click' ,'#posiCheckSelect' , function (e) {
                e.stopPropagation();
                var nodes = getPostStructureTreeNode4.getSelectedNodes();
                var data_id = $(".posiCheckSpan").attr("data-id");
                if (nodes.length<=0 && data_id == "" ) {
                    $(".posiCheckSpan").attr("data-id","");
                    $(".posiCheckSpan").text('审核岗');
                }
                $(".posiCheckSearchTree").slideToggle();
            });
            //所属部门点击事件
            $(document).on('click','#departmentSelect' ,function (e) {
                showDepartment();
            });
            //重置点巡检参数弹窗
            $(document).on('click', '#clearParam2', function () {
                $(".errorMsg").empty();
                $("#paramsNamePop2").val("");
                $("#paramsMethod").val("");
                $("#paramsStandard").val("");
                $("#paramsTypePop").val("1");
                $("#dictionaryPop").val("");
                $("#dictionaryPop").attr("disabled","disabled");
                $("#dictionaryPop option[value='']").attr('selected', 'selected');
                $(".chzn-single").children("span").text("请选择");
                $("#dictionaryPop").trigger("chosen:updated");
                $("#sel2").hide();
                $("#paramsDataSourcePop").val("");
            })
            //重置保养参数弹窗
            $(document).on('click', '#clearParam3', function () {
                $(".errorMsg").empty();
                $("#paramsNamePop3").val("");
                $("#paramsMethod2").val("");
                $("#paramsStandard2").val("");
                $("#spareParts").val("");
            })
            //关闭点巡检参数弹窗
            $(document).on('click', '#pop-params-close2', function () {
                $(".errorMsg").empty();
                $("#paramsNamePop2").val("");
                $("#paramsMethod").val("");
                $("#paramsStandard").val("");
                $("#paramsTypePop").val("1");
                $("#dictionaryPop").val("");
                $("#dictionaryPop").attr("disabled","disabled");
                $("#paramsDataSourcePop").val("");
                $("#addCheckParamPop").modal('hide');
            })
            //关闭保养参数弹窗
            $(document).on('click', '#pop-params-close3', function () {
                $(".errorMsg").empty();
                $("#paramsNamePop3").val("");
                $("#paramsMethod2").val("");
                $("#paramsStandard2").val("");
                $("#spareParts").val("");
                $("#addPreventiveParamPop").modal('hide');
            })
            //所属设备弹窗 关闭 赋值
            $(document).on('click', '.sureEquipment', function () {
                $("#codeOrName_search").val("");
                $("#treeListContainerUL").empty();
                $("#equipmentSelect-modal").hide();
                $("#deviceType").val(_this.data.equipments_name);
                $("#hiddenTypeValue").val(_this.data.equipments_id);
            })
            //检查设备编号是否可用
            $(document).on('blur', '#equipmentCode', function () {
                var code    = $.trim($(this).val());
                var data    = {};
                if(code==""){
                    $(this).css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "设备编码，不能为空！"});
                    $("#equipmentCode").focus();
                    return false;
                }
                $(this).removeAttr('style');
                if(_commonFun.isBlank(_this.data.equipmentId)){
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
                _addEquipment_service.checkEquipmentCodeAvailable(data ,function(res){
                    var result=res;
                    
                    if(result.code!=0) {
                        $.DialogByZ.Autofade({Content:result.message || "设备编号不可用"});
                        $("#equipmentCode").focus();
                        return false;
                    }
                },function(errMsg){
                    _commonFun.errorTips('');
                })
            })
            $(document).on('blur', '#equipmentName', function (){
                if($.trim($(this).val())==""){
                    $(this).css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "设备名称，不能为空！"});
                    $("#equipmentName").focus();
                    return false;
                }
                $(this).removeAttr('style');
            })
            //保存保存详情
            $(document).on('click','.saveAll', function () {
                var monitors=[],monitorIds="",params="",factoryParams="",checkParams="",preventiveParams="";
                $(_this.data.monitorsList).each(function (index, item) {
                    monitors.push(item.id);
                })
                if(monitors.length>0){
                    monitorIds= monitors.join(",");
                }
                if($.trim($("#equipmentCode").val())==""){
                    $("#equipmentCode").css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "设备编码，不能为空！"});
                    return;
                }
                $("#equipmentCode").removeAttr('style');
                if($.trim($("#equipmentName").val())==""){
                    $("#equipmentName").css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "设备名称，不能为空！"});
                    return;
                }
                $("#equipmentName").removeAttr('style');
                if($.trim($(".departmentSpan").attr("data-id"))=="" && $.trim($(".companySpan").attr("data-id"))==""){
                    $("#departmentSelect").css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "组织名称，不能为空！"});
                    return;
                }
                $("#departmentSelect").removeAttr('style');
                if($.trim($(".categorySpan").attr("data-id"))==""){
                    $("#deviceCategorySelect").css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "设备分类，不能为空！"});
                    return;
                }
                $("#deviceCategorySelect").removeAttr('style');
                var productionDate = $.trim($("#productionDate").val());
                var installDate = $.trim($("#installDate").val());
                if (productionDate != null && productionDate != "" && installDate != null && installDate != "") {
                    if (productionDate > installDate) {
                        $.DialogByZ.Autofade({Content:'出厂日期不能晚于安装日期！'});
                        return;
                    }
                }
                /*var machinePropertyId = $("#machinePropertyId").val();
                if (machinePropertyId == 1) {
                    var parentId = $("#parentId").val();
                    if (parentId == "") {
                        $("#parentId").css("border","1px solid #EF4836");
                        $.DialogByZ.Autofade({Content: "关联设备，不能为空！"});
                        return;
                    }
                }
                $("#parentId").removeAttr('style');*/
                params=JSON.stringify(_this.data.equipmentParams);
                _this.data.factoryParamsList = [];
                var factoryParamList = $(".factoryParam");
                for(var i=0; i< factoryParamList.length;i++){
                    var j = $(factoryParamList[i]).attr("data-id");
                    var value = $("#paramValue"+j).val();
                    /*if (_commonFun.isBlank(value)) {
                        _commonFun.errorTips("出厂参数的默认值，不能为空！");
                        return false;
                    }*/
                    var facParam = {};
                    facParam.deviceId = _this.data.equipmentId;
                    facParam.categoryParameterId = $("#paramId"+j).val();
                    facParam.name = $("#paramName"+j).text();
                    facParam.paraType = $("#paraType"+j).val();
                    facParam.dictionaryId = ($("#dictionaryId"+j).val() == "undefined") ? 0 : $("#dictionaryId"+j).val();
                    facParam.settingValue = value;
                    _this.data.factoryParamsList.push(facParam);
                }
                factoryParams = JSON.stringify(_this.data.factoryParamsList);
                checkParams = JSON.stringify(_this.data.checkParamList);
                preventiveParams = JSON.stringify(_this.data.preventiveParamList);
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
                            $.DialogByZ.Close();
                            if(_commonFun.isBlank(_this.data.equipmentId)){
                                //新增 采集终端
                                var data={
                                    code	            : $.trim($("#equipmentCode").val()),
                                    name	            : $.trim($("#equipmentName").val()),
                                    companyId       : $.trim($(".companySpan").attr("data-id")),
                                    departmentId       : $.trim($(".departmentSpan").attr("data-id")),
                                    specAndType        : $.trim($("#equipmentSpecAndType").val()),
                                    machinePropertyId : $.trim($("#machinePropertyId").val()),
                                    parentId           : $.trim($("#parentId").val()),
                                    deviceCategoryId	: $(".categorySpan").attr('data-id'),
                                    deviceStatusId    : $("#deviceStatusId").val(),
                                    posiInspection	    : $(".posiInspectionSpan").attr('data-id'),
                                    posiMaintain	    : $(".posiMaintainSpan").attr('data-id'),
                                    posiOperation	    : $(".posiOperationSpan").attr('data-id'),
                                    posiCheck           : $(".posiCheckSpan").attr('data-id'),
                                    manufacturer	    : $.trim($("#manufacturer").val()),
                                    serialNumber	    : $.trim($("#serialNumber").val()),
                                    productionDate	    : productionDate,
                                    installDate	        : installDate,
                                    description	        : $.trim($("#equipmentDescription").val()),
                                    fixedAssetNumber	    : $.trim($("#fixedAssetNumber").val()),
                                    pictureUid          : $("#fileIds").val(),
                                    factoryParams       :factoryParams,
                                    equipmentparams     : params,
                                    monitorIds          : monitorIds,
                                    checkParams          : checkParams,
                                    preventiveParams     : preventiveParams
                                }
                                _addEquipment_service.addBaseEquipmentInfo(data, function (res) {
                                    var result=res;
                                    if(result.code==0) {
                                        $.DialogByZ.Autofade({Content: "新增设备成功！"});
                                        if(_this.data.source == "source"){
                                            window.location.href="../reportsManagement/deviceLedger.html";
                                        }else {
                                            window.location.href="baseEquipment.html";
                                        }
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
                                    companyId       : $.trim($(".companySpan").attr("data-id")),
                                    departmentId       : $.trim($(".departmentSpan").attr("data-id")),
                                    specAndType        : $.trim($("#equipmentSpecAndType").val()),
                                    machinePropertyId : $.trim($("#machinePropertyId").val()),
                                    parentId           : $.trim($("#parentId").val()),
                                    deviceCategoryId	: $(".categorySpan").attr('data-id'),
                                    deviceStatusId    : $("#deviceStatusId").val(),
                                    posiInspection	    : $(".posiInspectionSpan").attr('data-id'),
                                    posiMaintain	    : $(".posiMaintainSpan").attr('data-id'),
                                    posiOperation	    : $(".posiOperationSpan").attr('data-id'),
                                    posiCheck           : $(".posiCheckSpan").attr('data-id'),
                                    manufacturer	    : $.trim($("#manufacturer").val()),
                                    serialNumber	    : $.trim($("#serialNumber").val()),
                                    productionDate	    : productionDate,
                                    installDate	        : installDate,
                                    description	        : $.trim($("#equipmentDescription").val()),
                                    fixedAssetNumber	    : $.trim($("#fixedAssetNumber").val()),
                                    pictureUid          : $("#fileIds").val(),
                                    factoryParams       :factoryParams,
                                    equipmentparams     : params,
                                    monitorIds          : monitorIds,
                                    checkParams          : checkParams,
                                    preventiveParams     : preventiveParams
                                }
                                _addEquipment_service.editBaseEquipmentInfo(data, function (res) {
                                    var result=res;
                                    if(result.code==0) {
                                        $.DialogByZ.Autofade({Content: "修改设备成功！"});
                                        window.location.href="baseEquipment.html";
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
                else {
                    if(_commonFun.isBlank(_this.data.equipmentId)){
                        //新增 采集终端
                        var data={
                            code	            : $.trim($("#equipmentCode").val()),
                            name	            : $.trim($("#equipmentName").val()),
                            companyId       : $.trim($(".companySpan").attr("data-id")),
                            departmentId       : $.trim($(".departmentSpan").attr("data-id")),
                            specAndType        : $.trim($("#equipmentSpecAndType").val()),
                            machinePropertyId : $.trim($("#machinePropertyId").val()),
                            parentId           : $.trim($("#parentId").val()),
                            deviceCategoryId	: $(".categorySpan").attr('data-id'),
                            deviceStatusId    : $("#deviceStatusId").val(),
                            posiInspection	    : $(".posiInspectionSpan").attr('data-id'),
                            posiMaintain	    : $(".posiMaintainSpan").attr('data-id'),
                            posiOperation	    : $(".posiOperationSpan").attr('data-id'),
                            posiCheck           : $(".posiCheckSpan").attr('data-id'),
                            manufacturer	    : $.trim($("#manufacturer").val()),
                            serialNumber	    : $.trim($("#serialNumber").val()),
                            productionDate	    : productionDate,
                            installDate	        : installDate,
                            description	        : $.trim($("#equipmentDescription").val()),
                            fixedAssetNumber	    : $.trim($("#fixedAssetNumber").val()),
                            pictureUid          : $("#fileIds").val(),
                            factoryParams       :factoryParams,
                            equipmentparams     : params,
                            monitorIds          : monitorIds,
                            checkParams          : checkParams,
                            preventiveParams     : preventiveParams
                        }

                        _addEquipment_service.addBaseEquipmentInfo(data, function (res) {
                            var result=res;

                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "新增设备成功！"});
                                window.location.href="baseEquipment.html";
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
                            companyId       : $.trim($(".companySpan").attr("data-id")),
                            departmentId       : $.trim($(".departmentSpan").attr("data-id")),
                            specAndType        : $.trim($("#equipmentSpecAndType").val()),
                            machinePropertyId : $.trim($("#machinePropertyId").val()),
                            parentId           : $.trim($("#parentId").val()),
                            deviceCategoryId	: $(".categorySpan").attr('data-id'),
                            deviceStatusId    : $("#deviceStatusId").val(),
                            posiInspection	    : $(".posiInspectionSpan").attr('data-id'),
                            posiMaintain	    : $(".posiMaintainSpan").attr('data-id'),
                            posiOperation	    : $(".posiOperationSpan").attr('data-id'),
                            posiCheck           : $(".posiCheckSpan").attr('data-id'),
                            manufacturer	    : $.trim($("#manufacturer").val()),
                            serialNumber	    : $.trim($("#serialNumber").val()),
                            productionDate	    : productionDate,
                            installDate	        : installDate,
                            description	        : $.trim($("#equipmentDescription").val()),
                            fixedAssetNumber	    : $.trim($("#fixedAssetNumber").val()),
                            pictureUid          : $("#fileIds").val(),
                            factoryParams       :factoryParams,
                            equipmentparams     : params,
                            monitorIds          : monitorIds,
                            checkParams          : checkParams,
                            preventiveParams     : preventiveParams
                        }

                        _addEquipment_service.editBaseEquipmentInfo(data, function (res) {
                            var result=res;
                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "修改设备成功！"});
                                window.location.href="baseEquipment.html";
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
            //第二个tab 新增采集参数
            $(document).on('click', '#addParam', function (){
                _this.data.paramsNameVal="";
                $("#hideInfoIdPop").val("");
                $("#paramsNamePop").val("");
                $("#paramsUnitPop").val("");
                $("#upperLimitPop").val("");
                $("#lowerLimitPop").val("");
                $("#paramsDescriptionPop").val("");
                $("#addParamsPop").modal();
            })
            //第二个tab 编辑采集参数
            $(document).on('click', '.a-params-edit', function (){
                var _self=$(this);
                _this.data.paramsNameVal=_self.parent().parent().find(".name").text();
                $("#paramsNamePop").val(_self.parent().parent().find(".name").text());
                $("#upperLimitPop").val(_self.parent().parent().find(".upperLimit").text());
                $("#lowerLimitPop").val(_self.parent().parent().find(".lowerLimit").text());
                $("#paramsUnitPop").val(_self.parent().parent().find(".unit").text());
                $("#paramsDescriptionPop").val(_self.parent().parent().find(".description").text());
                $("#addParamsPop").modal();
            })
            //第四个tab 新增点巡检参数
            $(document).on('click', '#addCheckParam', function (){
                _this.data.paramsNameVal2="";
                $("#paramsNamePop2").val("");
                $("#paramsMethod").val("");
                $("#paramsStandard").val("");
                $("#paramsTypePop").val("1");
                $("#dictionaryPop").val("");
                $("#dictionaryPop").attr("disabled","disabled");
                $("#paramsDataSourcePop").val("");
                $(".cusPopover-title span").text("新增点巡检项");
                $("#dictionaryPop option[value='']").attr('selected', 'selected');
                $(".chzn-single").children("span").text("请选择");
                $("#dictionaryPop").trigger("chosen:updated");
                $("#sel2").hide();
                $("#addCheckParamPop").modal();
            })
            //第四个tab 编辑点巡检参数
            $(document).on('click', '.a-checkParams-edit', function (){
                var _self=$(this);
                _this.data.paramsNameVal2=_self.parent().parent().find(".name").text();
                $("#paramsNamePop2").val(_self.parent().parent().find(".name").text());
                $("#paramsMethod").val(_self.parent().parent().find(".checkMethod").text());
                $("#paramsStandard").val(_self.parent().parent().find(".checkStandard").text());
                var paraType = _self.parent().parent().find(".paraTypeName").data("id");
                $("#paramsTypePop").val(paraType);
                if (paraType == 2 || paraType == 4) {
                    $("#dictionaryPop").removeAttr("disabled");
                    $("#dictionaryPop").val(_self.parent().parent().find(".dictionaryId").text());
                    $("#dictionaryPop option[value='" + _self.parent().parent().find(".dictionaryId").text() + "']").attr('selected', 'selected');
                    $("#dictionaryPop").trigger("chosen:updated");
                    $(".chzn-single").children("span").text(_self.parent().parent().find(".dictionaryName").text());
                    $("#dictionaryPop").trigger("chosen:updated");
                    $("#sel2").show();
                } else {
                    $("#dictionaryPop").attr("disabled","disabled");
                    $("#dictionaryPop").val("");
                    $("#sel2").hide();
                }
                $("#dictionaryPop").val(_self.parent().parent().find(".dictionaryName").data("id"));
                $("#paramsDataSourcePop").val(_self.parent().parent().find(".dataSourceId").text());
                $("#dictionaryPop").chosen();
                $(".cusPopover-title span").text("编辑点巡检项");
                $("#addCheckParamPop").modal();
            })
            //第五个tab 新增保养参数
            $(document).on('click', '#addPreventiveParam', function (){
                _this.data.paramsNameVal3="";
                $("#paramsNamePop3").val("");
                $("#paramsMethod2").val("");
                $("#paramsStandard2").val("");
                $("#spareParts").val("");
                $("#addPreventiveParamPop").modal();
            })
            //第五个tab 编辑保养参数
            $(document).on('click', '.a-preventiveParams-edit', function (){
                var _self=$(this);
                $("#spareParts").val("");
                _this.data.paramsNameVal3=_self.parent().parent().find(".name").text();
                $("#paramsNamePop3").val(_self.parent().parent().find(".name").text());
                $("#paramsMethod2").val(_self.parent().parent().find(".checkMethod").text());
                $("#paramsStandard2").val(_self.parent().parent().find(".checkStandard").text());
                var sparePartsIds = _self.parent().parent().find(".sparepartsId").data("id");
                if ((sparePartsIds+"").indexOf(",") != -1) {  //选中多个备件
                    var shuzu = sparePartsIds.split(",");
                    for (var ele in shuzu) {
                        $("#spareParts option").each(function (i,item) {
                            if (shuzu[ele] == item.value) {
                                $(this).attr("selected","selected");
                                return false;
                            }
                        })
                    }
                } else if (sparePartsIds != "" && sparePartsIds != null) {  //只选中一个备件
                    $("#spareParts option[value="+sparePartsIds+"]").attr("selected","selected");
                }

                $("#addPreventiveParamPop").modal();
            })
            //根据字段类型的选择，决定字典列表是否可编辑
            $(document).on('change','#paramsTypePop',function () {
                var type = $("#paramsTypePop").val();
                if (type == 2 || type == 4) {
                    $("#dictionaryPop").removeAttr("disabled");
                    $("#sel2").show();
                } else {
                    $("#dictionaryPop").attr("disabled","disabled");
                    $("#sel2").hide();
                }
            })

            $(document).on('blur', '#paramsNamePop', function () {
                if($.trim($(this).val())==""){
                    $(this).css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "参数名称，不能为空！"});
                    return;
                }
                $(this).removeAttr('style');
            })
            $(document).on('blur', '#paramsUnitPop', function () {
                if($.trim($(this).val())==""){
                    $(this).css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "参数单位，不能为空！"});
                    return;
                }
                $(this).removeAttr('style');
            })
            $(document).on('blur', '#upperLimitPop', function () {
                if($.trim($(this).val())==""){
                    $(this).css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "参数上限，不能为空！"});
                    return;
                }
                if( parseInt($.trim($("#lowerLimitPop").val())) > parseInt($.trim($("#upperLimitPop").val()))){
                    $.DialogByZ.Autofade({Content: "下限不能大于上限！"});
                    $("#upperLimitPop").css("border","1px solid #EF4836");
                    $("#upperLimitPop").val("");
                    return;
                }
                $(this).removeAttr('style');
            })
            $(document).on('blur', '#lowerLimitPop', function () {
                if($.trim($(this).val())==""){
                    $(this).css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "参数下限，不能为空！"});
                    return;
                }
                $(this).removeAttr('style');
            })
            //第二个tab 保存采集参数
            $(document).on('click', '#addCaiJi', function (){
                var name        = $("#paramsNamePop").val();
                var upperLimit  = $("#upperLimitPop").val();
                var lowerLimit  = $("#lowerLimitPop").val();
                var unit        = $("#paramsUnitPop").val();
                var description =$("#paramsDescriptionPop").val();
                var flagParam   = true;
                if(name==""){
                    $("#paramsNamePop").css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "参数名称，不能为空！"});
                    return;
                }
                $("#paramsNamePop").removeAttr('style');
                if(unit==""){
                    $("#paramsUnitPop").css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "参数单位，不能为空！"});
                    return;
                }
                $("#paramsUnitPop").removeAttr('style');
                if(upperLimit==""){
                    $("#upperLimitPop").css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "参数上限，不能为空！"});
                    return;
                }
                $("#upperLimitPop").removeAttr('style');
                if(lowerLimit==""){
                    $("#lowerLimitPop").css("border","1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "参数下限，不能为空！"});
                    return;
                }
                $("#lowerLimitPop").removeAttr('style');
                if( parseInt(lowerLimit) > parseInt(upperLimit)){
                    $.DialogByZ.Autofade({Content: "下限不能大于上限！"});
                    $("#upperLimitPop").css("border","1px solid #EF4836");
                    $("#upperLimitPop").val("");
                    return;
                }
                $("#upperLimitPop").removeAttr('style');
                if(_this.data.paramsNameVal==""){
                    //新增
                    $(_this.data.equipmentParams).each(function(i,item){
                        if(item.name==name){
                            flagParam=false;
                        }
                    })
                    if(flagParam){
                        // 不存在 则 push
                        var param       ={};
                        param.name          = name;
                        param.upperLimit    = upperLimit;
                        param.lowerLimit    = lowerLimit;
                        param.unit          = unit;
                        param.description   = description;
                        _this.data.equipmentParams.push(param);
                        _this.loadEquipmentParams(_this.data.equipmentParams);
                        $.DialogByZ.Autofade({Content: "保存成功！"});
                        $("#addParamsPop").modal('hide');
                    }
                    else{
                        //存在 则 提示
                        $("#paramsNamePop").css("border","1px solid #EF4836");
                        $("#paramsNamePop").val("");
                        $.DialogByZ.Autofade({Content: "参数名已存在！"});
                    }
                }
                else{
                    //编辑
                    if(_this.data.paramsNameVal==name){
                        //参数名不变的情况下 修改其他字段
                        $(_this.data.equipmentParams).each(function(i,item){
                            if(item.name==name){
                                item.upperLimit    = upperLimit;
                                item.lowerLimit    = lowerLimit;
                                item.unit          = unit;
                                item.description   = description;
                            }
                        })
                        _this.loadEquipmentParams(_this.data.equipmentParams);
                        $.DialogByZ.Autofade({Content: "保存成功！"});
                        $("#addParamsPop").modal('hide');
                    }
                    else{
                        //参数名变了 要判断是否重复
                        $(_this.data.equipmentParams).each(function(i,item){
                            if(item.name==name){
                                flagParam=false;
                            }
                        })
                        if(flagParam){
                            //不重复 则找到这条数据 全部字段都修改了
                            $(_this.data.equipmentParams).each(function(i,item){
                                if(item.name==_this.data.paramsNameVal){
                                    item.name          = name;
                                    item.upperLimit    = upperLimit;
                                    item.lowerLimit    = lowerLimit;
                                    item.unit          = unit;
                                    item.description   = description;
                                }
                            })
                            _this.loadEquipmentParams(_this.data.equipmentParams);
                            $.DialogByZ.Autofade({Content: "保存成功！"});
                            $("#addParamsPop").modal('hide');
                        }
                        else{
                            //重复 则 提示
                            $("#paramsNamePop").css("border","1px solid #EF4836");
                            $("#paramsNamePop").val("");
                            $.DialogByZ.Autofade({Content: "参数名已存在！"});
                        }
                    }
                }
            })
            //第三个tab新增未配置采集终端
            $(document).on("click", "#addUnusedDevice", function () {
                if(_this.data.monitorsUnusedList==[] || _this.data.monitorsUnusedList==""){
                    $.DialogByZ.Autofade({Content:'无可关联的采集终端！'});
                }
                else {
                    _this.chose_get_ini(".chosen-select");
                    var tableLine='<tr data-id=""><td class="code"><select  data-placeholder="请选择采集终端" id="popDeviceId" class="chosen-select"' +
                        'style="width:250px;" tabindex="4" ></select></td><td class="name"></td><td class="description"></td>'+
                        '<td><a class="link a-saveUnused"><i class="fa-table-line-save"  title="保存"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="link a-cancelUnused"><i class="fa-table-line-undo"  title="撤销"></i></a></td><td><input type="hidden" class="hidUnusedVal" value=""/></td></tr>';

                    if(_this.data.isSaveBeforeDevice){
                        $("#deviceUnusedList tbody").append(tableLine);
                        $(".chosen-select").html("");
                        $(".chosen-select").chosen();
                        $(".chosen-select").chosen("destroy");
                        _this.chose_get_ini(".chosen-select");
                        $(".chosen-select").trigger("liszt:updated");

                        var unused = "<option value=''>请选择采集终端</option>";
                        for (var i = 0; i < _this.data.monitorsUnusedList.length; i++) {
                            unused += "<option value="+ _this.data.monitorsUnusedList[i].id +">" + _this.data.monitorsUnusedList[i].code + " --- " + _this.data.monitorsUnusedList[i].name + " </option>";
                        }
                        $(".chosen-select").html(unused);
                        _this.chose_get_ini(".chosen-select");
                        $(".chosen-select").trigger("liszt:updated");
                        _this.data.isSaveBeforeDevice=false;
                    }
                    else {
                        $.DialogByZ.Autofade({Content:'请先保存当前采集终端！'});
                    }
                }

            })
            //第四个tab 保存点巡检参数
            $(document).on('click', '#addCheck', function () {
                var id = $.trim($("#hideInfoIdPop").val());
                var name = $.trim($("#paramsNamePop2").val());
                var method = $.trim($("#paramsMethod").val());
                var standrad = $.trim($("#paramsStandard").val());
                var type = $.trim($("#paramsTypePop").val());
                var typeName = $.trim($("#paramsTypePop option:selected").text());
                var dictionary = $.trim($("#dictionaryPop").val());
                var dictionaryName = "";
                var value = $.trim($("#paramsDataSourcePop").val());
                var flagParam   = true;
                if (name == "") {
                    $("#paramsNamePop2").css("border", "1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "点巡检内容，不能为空！"});
                    return;
                }
                if (method == "") {
                    $("#paramsMethod").css("border", "1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "点巡检方法，不能为空！"});
                    return;
                }
                if (standrad == "") {
                    $("#paramsStandard").css("border", "1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "点巡检标准，不能为空！"});
                    return;
                }
                $("#paramsNamePop2").removeAttr("style");
                $("#paramsMethod").removeAttr("style");
                $("#paramsStandard").removeAttr("style");

                if (type == 2 || type == 4) {  //当字段类型是下拉列表或复选列表多选时，字典列表为必填项
                    if (dictionary == null || dictionary == "") {  //判断字典列表是否为必填项
                        $("#dictionaryPop").css("border", "1px solid #EF4836");
                        $.DialogByZ.Autofade({Content: "字典列表，不能为空！"});
                        return;
                    }
                    dictionaryName = $.trim($("#dictionaryPop option:selected").text());
                } else {  //当字段类型是其它2种时，字典列表值为空
                    dictionary = "";
                    dictionaryName = "";
                }
                $("#dictionaryPop").removeAttr("style");

                if(_this.data.paramsNameVal2==""){
                    //新增
                    $(_this.data.checkParamList).each(function(i,item){
                        if(item.name==name){
                            flagParam=false;
                        }
                    })
                    if(flagParam){
                        // 不存在 则 push
                        var param       ={};
                        param.name          = name;
                        param.checkMethod    = method;
                        param.checkStandard    = standrad;
                        param.paraType          = type;
                        param.paraTypeName     = typeName;
                        param.dictionaryId   = dictionary;
                        param.dictionaryName    = dictionaryName;
                        param.dataSourceId   = value;
                        param.type   = 4;
                        _this.data.checkParamList.push(param);
                        _this.loadCheckParams(_this.data.checkParamList);
                        $.DialogByZ.Confirm({
                            Title: "操作提示",
                            Content: "保存成功，是否继续添加？",
                            BtnL:"继续添加",
                            BtnR:"否",
                            FunL: function(){
                                $.DialogByZ.Close();
                                $("#clearParam2").click(); //输入框重置
                            },
                            FunR: function () {
                                $.DialogByZ.Close();
                                $("#addCheckParamPop").modal('hide');
                            }
                        })
                    }
                    else{
                        //存在 则 提示
                        $("#paramsNamePop2").css("border","1px solid #EF4836");
                        $("#paramsNamePop2").val("");
                        $.DialogByZ.Autofade({Content: "参数名已存在！"});
                    }
                }
                else{
                    //编辑
                    if(_this.data.paramsNameVal2==name){
                        //参数名不变的情况下 修改其他字段
                        $(_this.data.checkParamList).each(function(i,item){
                            if(item.name==name){
                                item.checkMethod    = method;
                                item.checkStandard    = standrad;
                                item.paraType          = type;
                                item.paraTypeName   = typeName;
                                item.dictionaryId          = dictionary;
                                item.dictionaryName   = dictionaryName;
                                item.dataSourceId   = value;
                            }
                        })
                        _this.loadCheckParams(_this.data.checkParamList);
                        $.DialogByZ.Autofade({Content: "保存成功！"});
                        $("#addCheckParamPop").modal('hide');
                    }
                    else{
                        //参数名变了 要判断是否重复
                        $(_this.data.checkParamList).each(function(i,item){
                            if(item.name==name){
                                flagParam=false;
                            }
                        })
                        if(flagParam){
                            //不重复 则找到这条数据 全部字段都修改了
                            $(_this.data.checkParamList).each(function(i,item){
                                if(item.name==_this.data.paramsNameVal2){
                                    item.name          = name;
                                    item.checkMethod    = method;
                                    item.checkStandard    = standrad;
                                    item.paraType          = type;
                                    item.paraTypeName   = typeName;
                                    item.dictionaryId          = dictionary;
                                    item.dictionaryName   = dictionaryName;
                                    item.dataSourceId   = value;
                                }
                            })
                            _this.loadCheckParams(_this.data.checkParamList);
                            $.DialogByZ.Autofade({Content: "保存成功！"});
                            $("#addCheckParamPop").modal('hide');
                        }
                        else{
                            //重复 则 提示
                            $("#paramsNamePop2").css("border","1px solid #EF4836");
                            $("#paramsNamePop2").val("");
                            $.DialogByZ.Autofade({Content: "参数名已存在！"});
                        }
                    }
                }
            })
            //第五个tab 保存保养参数
            $(document).on('click', '#addPreventive', function () {
                var id = $.trim($("#hideInfoIdPop2").val());
                var name = $.trim($("#paramsNamePop3").val());
                var method = $.trim($("#paramsMethod2").val());
                var standrad = $.trim($("#paramsStandard2").val());
                var sparepartsId = $.trim($("#spareParts").val());
                var sparepartsName = "";
                var options = $("#spareParts option:selected");
                if (sparepartsId != "") { //选中了备件，将id和name分别用逗号隔开
                    sparepartsId = "";  //重新赋值为空
                    if(options.length>0){
                        for (var i=0;i<options.length;i++){
                            if (options[i].value == "") {
                                continue;
                            }
                            sparepartsId += options[i].value;
                            sparepartsName += options[i].text;
                            if (i != options.length-1) {
                                sparepartsId += ",";
                                sparepartsName += ",";
                            }

                        }
                    }
                }
                var flagParam   = true;
                if (name == "") {
                    $("#paramsNamePop3").css("border", "1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "保养内容，不能为空！"});
                    return;
                }
                if (method == "") {
                    $("#paramsMethod2").css("border", "1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "保养方法，不能为空！"});
                    return;
                }
                if (standrad == "") {
                    $("#paramsStandard2").css("border", "1px solid #EF4836");
                    $.DialogByZ.Autofade({Content: "保养标准，不能为空！"});
                    return;
                }
                $("#paramsNamePop3").removeAttr("style");
                $("#paramsMethod2").removeAttr("style");
                $("#paramsStandard2").removeAttr("style");

                if(_this.data.paramsNameVal3==""){
                    //新增
                    $(_this.data.preventiveParamList).each(function(i,item){
                        if(item.name==name){
                            flagParam=false;
                        }
                    })
                    if(flagParam){
                        // 不存在 则 push
                        var param       ={};
                        param.name          = name;
                        param.checkMethod    = method;
                        param.checkStandard    = standrad;
                        param.sparepartsId       = sparepartsId;
                        param.sparepartsName       = sparepartsName;
                        param.type   = 5;
                        _this.data.preventiveParamList.push(param);
                        _this.loadPreventiveParams(_this.data.preventiveParamList);
                        $.DialogByZ.Confirm({
                            Title: "操作提示",
                            Content: "保存成功，是否继续添加？",
                            BtnL:"继续添加",
                            BtnR:"否",
                            FunL: function(){
                                $.DialogByZ.Close();
                                $("#clearParam3").click(); //输入框重置
                            },
                            FunR: function () {
                                $.DialogByZ.Close();
                                $("#addPreventiveParamPop").modal('hide');
                            }
                        })
                    }
                    else{
                        //存在 则 提示
                        $("#paramsNamePop3").css("border","1px solid #EF4836");
                        $("#paramsNamePop3").val("");
                        $.DialogByZ.Autofade({Content: "参数名已存在！"});
                    }
                }
                else{
                    //编辑
                    if(_this.data.paramsNameVal3==name){
                        //参数名不变的情况下 修改其他字段
                        $(_this.data.preventiveParamList).each(function(i,item){
                            if(item.name==name){
                                item.checkMethod    = method;
                                item.checkStandard    = standrad;
                                item.sparepartsId     = sparepartsId;
                                item.sparepartsName       = sparepartsName;
                            }
                        })
                        _this.loadPreventiveParams(_this.data.preventiveParamList);
                        $.DialogByZ.Autofade({Content: "保存成功！"});
                        $("#addCheckParamPop").modal('hide');
                    }
                    else{
                        //参数名变了 要判断是否重复
                        $(_this.data.preventiveParamList).each(function(i,item){
                            if(item.name==name){
                                flagParam=false;
                            }
                        })
                        if(flagParam){
                            //不重复 则找到这条数据 全部字段都修改了
                            $(_this.data.preventiveParamList).each(function(i,item){
                                if(item.name==_this.data.paramsNameVal3){
                                    item.name          = name;
                                    item.checkMethod    = method;
                                    item.checkStandard    = standrad;
                                    item.sparepartsId     = sparepartsId;
                                    item.sparepartsName       = sparepartsName;
                                }
                            })
                            _this.loadPreventiveParams(_this.data.preventiveParamList);
                            $.DialogByZ.Autofade({Content: "保存成功！"});
                            $("#addPreventiveParamPop").modal('hide');
                        }
                        else{
                            //重复 则 提示
                            $("#paramsNamePop3").css("border","1px solid #EF4836");
                            $("#paramsNamePop3").val("");
                            $.DialogByZ.Autofade({Content: "参数名已存在！"});
                        }
                    }
                    $(".modal").modal('hide');
                }
            })
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
                    //编辑 变 保存
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
            $(document).on('change', '#machinePropertyId', function (e){
                var value = $("#machinePropertyId").val();
                if (value == 1) {
                    $("#parentId").removeAttr("disabled");
                } else if (value ==0) {
                    $("#parentId").attr("disabled","disabled");
                    $("#parentId").val("");
                }
            })
            //删除采集参数
            $(document).on('click', '.a-params-delete', function () {
                var _self=$(this);
                var name = _self.parent().parent().find(".name").text();
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除该参数信息吗？",
                    FunL:function(){
                        $(_this.data.equipmentParams).each(function(index,item){
                            if(item.name==name){
                                _this.data.equipmentParams.splice(index, 1);
                            }
                        })
                        _this.loadEquipmentParams(_this.data.equipmentParams);
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            })
            //删除点巡检参数
            $(document).on('click', '.a-checkParams-delete', function () {
                var _self=$(this);
                var name = _self.parent().parent().find(".name").text();
                var id = _self.parent().parent().attr("data-id");
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除该点巡检项信息吗？",
                    FunL:function(){
                        _addEquipment_service.queryParamIsUsed(id,function (res) {
                            var result=res;
                            if (result.code == 0) {
                                $(_this.data.checkParamList).each(function(index,item){
                                    if(item.name==name){
                                        _this.data.checkParamList.splice(index, 1);
                                    }
                                })
                                _this.loadCheckParams(_this.data.checkParamList);
                            } else {
                                $.DialogByZ.Autofade({Content: result.message});
                            }
                        },function (err) {
                            _commonFun.errorTips('');
                        })
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            })
            //删除保养项参数
            $(document).on('click', '.a-preventiveParams-delete', function () {
                var _self=$(this);
                var name = _self.parent().parent().find(".name").text();
                var id = _self.parent().parent().attr("data-id");
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除该保养项信息吗？",
                    FunL:function(){
                        _addEquipment_service.queryParamIsUsed(id,function (res) {
                            var result=res;
                            if (result.code == 0) {
                                $(_this.data.preventiveParamList).each(function(index,item){
                                    if(item.name==name){
                                        _this.data.preventiveParamList.splice(index, 1);
                                    }
                                })
                                _this.loadPreventiveParams(_this.data.preventiveParamList);
                            } else {
                                $.DialogByZ.Autofade({Content: result.message});
                            }
                        },function (err) {
                            _commonFun.errorTips('');
                        })
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            })
            //关闭采集参数弹窗
            $(document).on('click', '#pop-params-close', function () {
                $(".errorMsg").empty();
                $("#hideInfoIdPop").val("");
                $("#paramsNamePop").val("");
                $("#paramsUnitPop").val("");
                $("#upperLimitPop").val("");
                $("#lowerLimitPop").val("");
                $("#paramsDescriptionPop").val("");
                $("#addParamsPop").modal('hide');
            })
            //重置采集参数弹窗
            $(document).on('click', '#clearParam1', function () {
                $(".errorMsg").empty();
                $("#hideInfoIdPop").val("");
                $("#paramsNamePop").val("");
                $("#upperLimitPop").val("");
                $("#lowerLimitPop").val("");
                $("#paramsUnitPop").val("");
                $("#paramsDescriptionPop").val("");
            })
            //清空弹窗文本内容
            $(document).on('click', '.clearAll', function () {
                $("#equipmentCode").val("");
                $("#equipmentName").val("");
                $(".departmentSpan").text("所属部门");
                $(".departmentSpan").attr("data-id","");
                $(".categorySpan").text("设备分类");
                $(".categorySpan").attr("data-id","");
                $("#equipmentSpecAndType").val("");
                $("#machinePropertyId").val("0");
                $("#parentId").val("");
                $(".posiInspectionSpan").text("点巡检岗");
                $(".posiInspectionSpan").attr("data-id","");
                $(".posiMaintainSpan").text("维修岗");
                $(".posiMaintainSpan").attr("data-id","");
                $(".posiOperationSpan").text("操作岗");
                $(".posiOperationSpan").attr("data-id","");
                $(".posiCheckSpan").text("审核岗");
                $(".posiCheckSpan").attr("data-id","");
                $("#deviceStatusId").val("1"),
                $("#manufacturer").val("");
                $("#serialNumber").val("");
                $("#productionDate").val("");
                $("#installDate").val("");
                $("#equipmentDescription").val("");
                $("#fixedAssetNumber").val("");
            })
            //返回
            $(document).on('click', '.return', function (){
                var type = _commonFun.getQueryString("type");
                if(type =='look'){
                    window.location.href="baseEquipment.html";
                }else{
                    $.DialogByZ.Confirm({
                        Title: "操作提示",
                        Content: "确定要返回吗，返回将不保存本页数据？",
                        FunL:function(){
                            _this.data.equipments_id        ="";
                            _this.data.equipments_name      ="";
                            _this.data.equipmentParams      =[];
                            _this.data.monitorsList         =[]; //存储未配置设备列表 用于提交的
                            _this.data.monitorsUnusedList   =[];//获取未配置设备的采集终端列表 用于chosen 下拉用
                            _this.data.checkParamList      = [];//存储点巡检项信息
                            _this.data.isSaveBeforeDevice   = true ;
                            if(_this.data.source == "source"){
                                window.location.href="../reportsManagement/deviceLedger.html";
                            }else {
                                window.location.href="baseEquipment.html";
                            }
                            $.DialogByZ.Close();
                        },
                        FunR:function(){
                            $.DialogByZ.Close();
                        }
                    })
                }
            });
            //查看更多操作记录
            $(document).on('click', '#more-log', function () {
                $("#historicalRecord").modal();
            })
            //关闭更多操作记录窗口
            $(document).on('click', '.closeLog', function () {
                $("#historicalRecord").modal('hide');
            })
            //关闭历史操作记录弹窗
            $(document).on('click', '#pop-record-close', function () {
                $("#historicalRecord").modal('hide');
            })
            //出厂参数删除
            $(document).on('click', '.a-param-delete', function () {
                var item = $(this).parent().parent();
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除该出厂参数项吗？",
                    FunL:function(){
                        $(item).remove();
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                });
            });

            // 设备 子设备与 关联设备的联动
            $("#machinePropertyId").on("change",function () {
               var value = $(this).val();
               if(value == 0){
                   //设备
                   $("#parentId").prop("disabled",true);
               }else if(value == 1){
                   if(_commonFun.isNotBlank(_this.data.equipmentId)){
                       var data = {parentId:_this.data.equipmentId};
                       //当主设备 修改为 子设备时，需要校验 主设备是否还有子设备，如果有先解除子设备
                       _addEquipment_service.getParentEquipment(data,function (res) {
                           var result = res;
                           if(result.code == 0){
                               if(result.data != null && result.data.length > 0){
                                   _commonFun.successTips('该设备还有子设备，请先移除子设备！');
                                   $("#machinePropertyId").val(0);
                                   $("#parentId").prop("disabled",true);
                               }else{
                                   //子设备
                                   $("#parentId").prop("disabled",false);
                                   //禁用当前的主设备
                                   $("#parentId option").each(function () {
                                       if($(this).attr("value") == _this.data.equipmentId){
                                           $(this).prop("disabled",true);
                                       }
                                   });
                               }
                           }
                       },function (res) {
                           _commonFun.errorTips('')
                       });
                   }
               }
            });
        },
        //加载设备信息
        loadEquipmentInfo : function () {
            var _this = this;
            var deviceId = _commonFun.getQueryString("id");
            console.log("deviceId",deviceId);
            if(_commonFun.isBlank(deviceId)){
                deviceId = window.parent.parentOrderParam;
            }
            _this.data.equipmentId = deviceId;
            if (_commonFun.isBlank(_this.data.equipmentId)) {
                //新增
                $("#equipmentCode").val("");
                $("#equipmentName").val("");
                $("#equipmentSpecAndType").val("");
                $("#posiInspectionSelect").val("");
                $("#posiMaintainSelect").val("");
                $("#posiOperationSelect").val("");
                $("#posiCheckSelect").val("");
                $("#manufacturer").val("");
                $("#serialNumber").val("");
                $("#productionDate").val("");
                $("#installDate").val("");
                $("#equipmentDescription").val("");
                $("#fixedAssetNumber").val("");
                $("#twoLog").empty();
                //新增时，设备状态默认为在用，不可编辑
                setTimeout(function () {
                    $("#deviceStatusId").val("1");
                    $("#deviceStatusId").attr("disabled","disabled");
                },1000)
                if(_this.data.source == "source"){
                    // var accounts = commonNew.getQueryString("accounts") || "";//获取从设备台账传值 （新增）
                    var accounts = "";
                    accounts = parseInt(accounts)
                    _this.loadParentEquipment(accounts);
                    $("#parentId").val(accounts);
                    $("#machinePropertyId").val("1");
                    $("#machinePropertyId").attr("disabled","disabled");
                    //加载主设备信息
                    _this.loadParentEquipmentInfo(accounts);
                }else {
                    _this.loadParentEquipment();
                }

            }
            else {//编辑
                $(".breadcrumb .active").text("编辑基础设备");
                _addEquipment_service.queryBaseEquipmentByID(_this.data.equipmentId, function (res) {
                    // var result=eval('(' + res + ')');
                    var result= res;
                    if(result.code==0) {
                        var data=result.data;
                        interval=setInterval(function () {
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
        //编辑设备信息，显示在页面
        equipmentInfoSource:function (data) {
            var _this=this;
            clearInterval(interval);
            //编辑时，设备种类不可修改
            //$("#machinePropertyId").attr("disabled","disabled");
            $("#machinePropertyId").val(data.baseEquipment.machinePropertyId);
            //当设备种类为非设备时，父级关联设备可编辑
            if (data.baseEquipment.machinePropertyId != 0) {
                var parentId = data.baseEquipment.parentId;
                $("#parentId").removeAttr("disabled");
                $("#parentId").val(parentId);
                //当关联设备为0时，即没有关联设备
                if (parentId == 0) {
                    _this.loadParentEquipment("");
                } else {
                    _this.loadParentEquipment(parentId);
                }
            } else {
                _this.loadParentEquipment("");
            }
            $("#deviceId").val(data.baseEquipment.id);
            $("#equipmentCode").val(data.baseEquipment.code);
            $("#equipmentName").val(data.baseEquipment.name);
            var departmentId = data.baseEquipment.departmentId;
            if (departmentId != null && departmentId != 0) {
                $(".departmentSpan").attr("data-id",departmentId);
                $(".departmentSpan").text(data.baseEquipment.departmentName);
                $(".companySpan").attr("data-id",data.baseEquipment.companyId);
                $(".companySpan").text(data.baseEquipment.companyName);
                $(".companySpan").hide();
                $(".departmentSpan").show();
            } else {
                $(".companySpan").attr("data-id",data.baseEquipment.companyId);
                $(".companySpan").text(data.baseEquipment.companyName);
                $(".companySpan").show();
                $(".departmentSpan").hide();
            }
            //$("#deviceCategorySelect").attr("disabled","disabled");
            $("#equipmentSpecAndType").val(data.baseEquipment.specAndType);
            $(".categorySpan").attr('data-id',data.baseEquipment.deviceCategoryId);
            $(".categorySpan").text(data.baseEquipment.deviceCategoryName);
            $("#deviceStatusId").val(data.baseEquipment.deviceStatusId);

            /*//指定选中ID的节点
            var n = getCategoryStructureTreeNode.getNodeByParam("id",data.baseEquipment.deviceCategoryId);
            getCategoryStructureTreeNode.selectNode(n,true);*/

            /*$("#posiInspectionSelect").val(data.baseEquipment.posiInspection);
            $("#posiMaintainSelect").val(data.baseEquipment.posiMaintain);
            $("#posiOperationSelect").val(data.baseEquipment.posiOperation);
            $("#posiCheckSelect").val(data.baseEquipment.posiCheck);*/

            $(".posiInspectionSpan").attr('data-id',data.baseEquipment.posiInspection);
            $(".posiInspectionSpan").text(data.baseEquipment.posiInspectionName);
            $(".posiMaintainSpan").attr('data-id',data.baseEquipment.posiMaintain);
            $(".posiMaintainSpan").text(data.baseEquipment.posiMaintainName);
            $(".posiOperationSpan").attr('data-id',data.baseEquipment.posiOperation);
            $(".posiOperationSpan").text(data.baseEquipment.posiOperationName);
            $(".posiCheckSpan").attr('data-id',data.baseEquipment.posiCheck);
            $(".posiCheckSpan").text(data.baseEquipment.posiCheckName);

            $("#manufacturer").val(data.baseEquipment.manufacturer);
            $("#serialNumber").val(data.baseEquipment.serialNumber);
            $("#productionDate").val(data.baseEquipment.productionDateStr);
            $("#installDate").val(data.baseEquipment.installDateStr);
            $("#equipmentDescription").val(data.baseEquipment.description);
            $("#fixedAssetNumber").val(data.baseEquipment.fixedAssetNumber);

            //上传的图片
            _fileUpload.showFilesByIDs(data.baseEquipment.pictureUid);
            _this.data.equipmentParams  =data.equipmentParams || [];
            _this.data.monitorsList     =data.monitors || [];
            _this.data.factoryParamsList  =data.factoryParams || [];
            _this.data.checkParamList  =data.checkParams || [];
            _this.data.preventiveParamList = data.preventiveParams || [];
            _this.loadEquipmentParams(_this.data.equipmentParams);
            //_this.loadModifyHistory(data.modifyHistories);
            //_this.loadCreateAndUpdate(data.createAndUpdate);
            _this.loadEquipmentUnusedList(_this.data.monitorsList);
            _this.loadFactoryParameter(_this.data.factoryParamsList);
            _this.loadCheckInfoForEdit(_this.data.checkParamList);
            _this.loadPreventiveInfoForEdit(_this.data.preventiveParamList);
            _fileManage.getFiles();
            if (data.baseEquipment.deviceStatusId == 3) { //已报废设备的信息不能编辑，只能查看
                $("input").prop("disabled","disabled");
                $("select").prop("disabled","disabled");
                $("textarea").prop("disabled","disabled");
                $(".clearAll").hide();
                $(".saveAll").hide();
            }
        },
        //获取设备分类对应的出厂参数信息
        loadCategoryParameter : function (categoryId) {
            var _this=this;
            var data = {
                categoryId:categoryId,
                type:1
            };
            _addEquipment_service.getCategoryParameter(data,function (res) {
                var result=res;
                var type;
                var paraType;
                if(result.code==0) {
                    var data=result.data;
                    $("#factoryParam").html("");
                    var str = "";
                    paramNumber = 0;  //出厂参数个数重置0
                    for(var i = 0 ; i<data.length;i++) {
                        str = "";
                        var param = data[i];
                        paraType = param.paraType;
                        //创建 div
                        str += "<div class='form-group factoryParam' data-id='"+paramNumber+"'><input type='hidden' id='paramId"+paramNumber+"' value='"+param.id+"'>"+
                            "<input type='hidden' id='paraType"+paramNumber+"' value='"+param.paraType+"'>"+
                            "<input type='hidden' id='dictionaryId"+paramNumber+"' value='"+param.dictionaryId+"'>"+
                            "<label id='paramName"+paramNumber+"' class='col-sm-4 control-label'>"+param.name+"</label>"+
                            "<div class='col-sm-8'>";
                        if (paraType ==2 || paraType ==4) {
                            str += "<select class='form-control col-sm-9' style='width: 75%' id='paramValue"+paramNumber+"'></select>";
                        } else if (paraType ==5) {
                            str += "<input type='text' id='paramValue"+paramNumber+"' style='background-color: #fff; width: 75%;'" +
                                "value='"+param.defaultValue+"' class='form-control col-sm-9 datainp  wicon' readonly='readonly' />";
                        } else {
                            str += "<input type='text' class='form-control col-sm-9' style='width: 75%;' id='paramValue"+paramNumber+"' value='"+param.defaultValue+"'" +
                            "onkeyup=\"this.value=this.value.replace(/[=\\\\=&? ？ *%]/,'')\">";
                        }
                        str += '<a class="link a-param-delete col-sm-3"> <i class="fa-table-line-delete" title="删除"></i></a></div></div>';
                        $("#factoryParam").append(str);
                        if (paraType ==2 || paraType ==4) {
                            //加载下拉列表信息
                            _this.loadFactoryParam("paramValue"+paramNumber,param.dictionaryId,null);
                        }
                        paramNumber++;

                    }
                    initDatetime();
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        //获取设备分类对应的点巡检参数信息
        loadCategoryParameter_check : function (categoryId) {
            var _this=this;
            var data = {
                categoryId:categoryId,
                type:4
            };
            _addEquipment_service.getCategoryParameter(data,function (res) {
                var result=res;
                var type;
                var paraType;
                if(result.code==0) {
                    var data=result.data;
                    $("#checkList1 tbody").html("");
                    var str = "";
                    for(var i = 0 ; i<data.length;i++) {
                        var param = data[i];
                        str += "<tr data-id='"+param.id+"'><td>"+param.name+"</td><td>"+param.checkMethod+"</td>" +
                            "<td>"+param.checkStandard+"</td><td>"+param.paraTypeName+"</td>" +
                            "<td>"+param.dictionaryName+"</td><td>"+param.dataSourceId+"</td></tr>";
                    }
                    $("#checkList1 tbody").html(str);
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        //获取设备分类对应的保养参数信息
        loadCategoryParameter_preventive : function (categoryId) {
            var _this=this;
            var data = {
                categoryId:categoryId,
                type:5
            };
            _addEquipment_service.getCategoryParameter(data,function (res) {
                var result=res;
                var type;
                var paraType;
                if(result.code==0) {
                    var data=result.data;
                    $("#preventiveList1 tbody").html("");
                    var str = "";
                    for(var i = 0 ; i<data.length;i++) {
                        var param = data[i];
                        str += "<tr data-id='"+param.id+"'><td>"+param.name+"</td><td>"+param.checkMethod+"</td>" +
                            "<td>"+param.checkStandard+"</td><td>"+param.sparepartsId+"</td></tr>";
                    }
                    $("#preventiveList1 tbody").html(str);
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        //获取设备分类对应的出厂参数信息
        loadFactoryParam : function (selId,parentId,settingValue) {
            var data = {
                parentId:parentId
            };
            _addEquipment_service.getFactoryPara(data,function (res) {
                var result=res;
                var type;
                var paraType;
                if(result.code==0) {
                    var list = result.data.infoList;
                    var str = "";
                    for (var ele in list) {
                        str += "<option value='"+list[ele].name+"'>"+list[ele].name+"</option>";
                    }
                    $("#"+selId+"").append(str);
                }
                if (settingValue != null) {
                    $("#"+selId+"").val(settingValue);
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        //获取备件信息
        loadSparePartBasicList : function () {
            var _this=this;
            var data = {
                numberOnePage:100
            };
            _addEquipment_service.getSparePartBasicList(data,function (res) {
                var result=res;
                if(result.code==0) {
                    var data=result.data.spareParts;
                    var str = '<option value="">请选择备件...</option>';
                    for(var i = 0 ; i<data.length;i++) {
                        str += '<option value="'+data[i].id+'">'+data[i].sparePartName+'</option>';
                    }
                    $("#spareParts").html(str);
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        //获取字典列表的父节点
        loadCheckDictionaryParam : function (selId,parentId,settingValue) {
            var _this=this;
            var data = {
                parentId:0
            };
            $("#dictionaryPop").html("");
            _addEquipment_service.getFactoryPara(data,function (res) {
                var result=res;
                var type;
                var paraType;
                if(result.code==0) {
                    var list = result.data.infoList;
                    var str = "<option selected='selected' value=''>请选择</option>";
                    for (var ele in list) {
                        str += "<option value='"+list[ele].id+"'>"+list[ele].name+"</option>";
                    }
                    //$("#dictionaryPop").html(str);
                    $(".chosen-select").html(str);
                    _this.chose_get_ini(".chosen-select");

                    $(".chosen-select").chosen({
                        no_results_text : "未找到此选项!"
                    });
                    $(".chosen-select").trigger("liszt:updated");
                } else {
                    $.DialogByZ.Autofade({Content: result.message || "查无数据！"});
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        //编辑时，加载出厂参数信息
        loadFactoryParameter:function(data) {
            var str = "";
            for(var i = 0 ; i<data.length;i++) {
                str = "";
                var param = data[i];
                paraType = param.paraType;
                str += "<div class='form-group factoryParam' data-id='"+paramNumber+"'><input type='hidden' id='paramId"+paramNumber+"' value='"+param.id+"'>"+
                    "<label id='paramName"+paramNumber+"' class='col-sm-6 control-label'>"+param.name+"</label>"+
                    "<div class='col-sm-6'>";
                if (paraType ==2 || paraType ==4) {
                    str += "<select class='form-control' id='paramValue"+paramNumber+"'></select></div></div>";
                }  else if (paraType ==5) {
                    str += "<input type='text' id='paramValue"+paramNumber+"' style='background-color: #fff; width: 250px;'" +
                        "value='"+param.settingValue+"' class='form-control  datainp  wicon' readonly='readonly' /></div></div>";
                } else {
                    str += "<input type='text' class='form-control' id='paramValue"+paramNumber+"' value='"+param.settingValue+"'" +
                        "onkeyup=\"this.value=this.value.replace(/[=\\\\=&? ？ *%]/,'')\"></div></div>";
                }
                $("#factoryParam").append(str);
                if (paraType ==2 || paraType ==4) {
                    //加载下拉列表信息
                    this.loadFactoryParam("paramValue"+paramNumber,param.dictionaryId,param.settingValue);
                    $("#paramValue"+paramNumber+"").val();
                }
                paramNumber++;
            }
            initDatetime();
        },
        //编辑时，加载点巡检参数信息
        loadCheckInfoForEdit:function(list) {
            var str1 = "",str2 = "";
            for(var ele in list) {
                var categoryId = list[ele].categoryId;
                var dictionaryId;
                typeof (list[ele].dictionaryId) == "undefined" ? dictionaryId = "" : dictionaryId = list[ele].dictionaryId;
                if (categoryId != null && categoryId !="") {  //共有点巡检项
                    str1+='<tr>' +
                        '<td  class="name">'+list[ele].name+'</td>'+
                        '<td  class="checkMethod">'+list[ele].checkMethod+'</td>'+
                        '<td  class="checkStandard">'+list[ele].checkStandard+'</td>'+
                        '<td  class="paraTypeName" data-id="'+list[ele].paraType+'">'+list[ele].paraTypeName+'</td>'+
                        '<td  class="dictionaryName" data-id="'+dictionaryId+'">'+list[ele].dictionaryName+'</td>'+
                        '<td  class="dataSourceId">'+list[ele].dataSourceId+'</td></tr>';
                } else {  //独立点巡检项
                    str2+='<tr>' +
                        '<td  class="name">'+list[ele].name+'</td>'+
                        '<td  class="checkMethod">'+list[ele].checkMethod+'</td>'+
                        '<td  class="checkStandard">'+list[ele].checkStandard+'</td>'+
                        '<td  class="paraTypeName" data-id="'+list[ele].paraType+'">'+list[ele].paraTypeName+'</td>'+
                        '<td  class="dictionaryName" data-id="'+dictionaryId+'">'+list[ele].dictionaryName+'</td>'+
                        '<td  class="dataSourceId">'+list[ele].dataSourceId+'</td>'+
                        '<td><a class="link a-checkParams-edit"> <i class="fa-table-line-edit" title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'+
                        '<a   class="link a-checkParams-delete"><i class="fa-table-line-delete"  title="删除"></i></a></td></tr>';
                }
            }
            $("#checkList1 tbody").html(str1);
            $("#checkList2 tbody").html(str2);
        },
        //编辑时，加载保养参数信息
        loadPreventiveInfoForEdit:function(list) {
            var str1 = "",str2 = "";
            for(var ele in list) {
                var categoryId = list[ele].categoryId;
                if (categoryId != null && categoryId !="") {  //共有保养参数
                    str1+='<tr>' +
                        '<td  class="name">'+list[ele].name+'</td>'+
                        '<td  class="checkMethod">'+list[ele].checkMethod+'</td>'+
                        '<td  class="checkStandard">'+list[ele].checkStandard+'</td>'+
                        '<td  class="sparepartsId" data-id="'+list[ele].sparepartsId+'">'+list[ele].sparepartsName+'</td></tr>';
                } else {  //独立保养参数
                    str2+='<tr data-id="'+list[ele].id+'">' +
                        '<td  class="name">'+list[ele].name+'</td>'+
                        '<td  class="checkMethod">'+list[ele].checkMethod+'</td>'+
                        '<td  class="checkStandard">'+list[ele].checkStandard+'</td>'+
                        '<td  class="sparepartsId" data-id="'+list[ele].sparepartsId+'">'+list[ele].sparepartsName+'</td>'+
                        '<td><a class="link a-preventiveParams-edit"> <i class="fa-table-line-edit"  title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'+
                        '<a   class="link a-preventiveParams-delete"><i class="fa-table-line-delete"  title="删除"></i></a></td></tr>';
                }
            }
            $("#preventiveList1 tbody").html(str1);
            $("#preventiveList2 tbody").html(str2);
        },
        //加载父级关联设备信息
        loadParentEquipment:function(value){
            var device = {
                parentId : 0
            }
            _addEquipment_service.getParentEquipment(device,function(res){
                var result=res;
                if(result.code==0) {
                    var list = result.data;
                    var str = "";
                    for (var ele in list) {
                        str += "<option value='"+list[ele].id+"'>"+list[ele].name+"</option>"
                    }
                    $("#parentId").append(str);
                    $("#parentId").val(value);
                }
            },function(err){
                _commonFun.errorTips('');
            });
        },
        //设备台账时，加载主设备信息
        loadParentEquipmentInfo:function(id){
            //加载父设备信息,带出所属部门、点巡检岗、维修岗、操作岗、审核岗
            _addEquipment_service.queryBaseEquipmentByID(id, function (res) {
                var result=res;
                if(result.code==0) {
                    var data=result.data;
                    //设备所属部门
                    $(".departmentSpan").attr("data-id",data.baseEquipment.departmentId);
                    $(".departmentSpan").text(data.baseEquipment.departmentName);
                    //点巡检岗
                    $(".posiInspectionSpan").attr('data-id',data.baseEquipment.posiInspection);
                    $(".posiInspectionSpan").text(data.baseEquipment.posiInspectionName);
                    //维修岗
                    $(".posiMaintainSpan").attr('data-id',data.baseEquipment.posiMaintain);
                    $(".posiMaintainSpan").text(data.baseEquipment.posiMaintainName);
                    //操作岗
                    $(".posiOperationSpan").attr('data-id',data.baseEquipment.posiOperation);
                    $(".posiOperationSpan").text(data.baseEquipment.posiOperationName);
                    //审核岗
                    $(".posiCheckSpan").attr('data-id',data.baseEquipment.posiCheck);
                    $(".posiCheckSpan").text(data.baseEquipment.posiCheckName);
                }
                else {
                    $.DialogByZ.Autofade({Content: result.message ||  "查无数据！"});
                }
            },function(err){
                _commonFun.errorTips('');
            });
        },
        //------获取列表 (点巡检、操作方，维修方，审核方)
        loadPostsList:function(){
            _addEquipment_service.getPostsAndPermissions(function(res){
                var result=res;
                if(result.code==0) {
                    getPostStructureTreeNode1 = $.fn.zTree.init($("#posiInspectionSelectUL"),InspectionInitSetting, result.data);
                    getPostStructureTreeNode1.expandAll('true');
                    getPostStructureTreeNode2 = $.fn.zTree.init($("#posiMaintainSelectUL"),MaintainInitSetting, result.data);
                    getPostStructureTreeNode2.expandAll('true');
                    getPostStructureTreeNode3 = $.fn.zTree.init($("#posiOperationSelectUL"),OperationInitSetting, result.data);
                    getPostStructureTreeNode3.expandAll('true');
                    getPostStructureTreeNode4 = $.fn.zTree.init($("#posiCheckSelectUL"),CheckInitSetting, result.data);
                    getPostStructureTreeNode4.expandAll('true');
                    $(".posiInspectionSearchTree").slideUp();
                    $(".posiMaintainSearchTree").slideUp();
                    $(".posiOperationSearchTree").slideUp();
                    $(".posiCheckSearchTree").slideUp();
                }
                else{
                    $.DialogByZ.Autofade({Content:'获取部门岗位树失败！'});
                }
            },function(err){
                _commonFun.errorTips('');
            });
        },
 //------获取设备分类信息
        /*loadDeviceTreeOptions: function() {
            var url = decodeURI(window.location.href);
            var id = url.split("=")[1];
            var name = url.split("=")[2];
            id = id.split("&")[0];
            this.data.deviceId = id;
            this.data.deviceName = name;
            $(".categorySpan").attr('data-id',id);
            $("#deviceCategorySelect").val(name);
        },*/
        //编辑采集终端的采集信息列表渲染
        loadEquipmentParams:function (list) {
            if(list!=[]){
                $("#paramsLists tbody").empty();
                var str="";
                for(var ele in list){
                    str+='<tr>' +
                        '<td  class="name">'+list[ele].name+'</td>'+
                        '<td  class="lowerLimit">'+list[ele].lowerLimit+'</td>'+
                        '<td  class="upperLimit">'+list[ele].upperLimit+'</td>'+
                        '<td  class="unit">'+list[ele].unit+'</td>'+
                        '<td  class="description">'+list[ele].description+'</td>'+
                        '<td><a class="link a-params-edit"> <i class="fa-table-line-edit"  title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'+
                        '<a   class="link a-params-delete"><i class="fa-table-line-delete"  title="删除"></i></a></td></tr>';
                }
                $("#paramsLists tbody").html(str);
            }
        },
        //编辑采集终端的修改记录列表渲染
        loadModifyHistory:function (list) {
            $("#historicalRecord table").empty();
            var str="";
            for(var ele in list){
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
            for(var ele in list){
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
            _addEquipment_service.getUnusedDeviceMonitors(function (res) {
                var result=res;
                if(result.code==0) {
                    _this.data.monitorsUnusedList=result.data.monitors;
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        //未配置设备的采集终端列表渲染
        loadEquipmentUnusedList:function (list) {
            console.log(list);
            if(list!=[]){
                $("#deviceUnusedList tbody").empty();
                var str="";
                for(var ele in list){
                    str+='<tr  data-id="'+list[ele].id+'">' +
                        '<td  class="code">' + list[ele].code + '</td>' +
                        '<td  class="name">' + list[ele].name + '</td>' +
                        '<td  class="description">'+list[ele].description+'</td>'+
                        '<td><a class="link a-editUnused"> <i class="fa-table-line-edit"  title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'+
                        '<a   class="link a-deleteUnused"><i class="fa-table-line-delete"  title="删除"></i></a></td><td><input type="hidden" class="hidUnusedVal" value="'+list[ele].id+'"/></td>' +
                        '</tr>';
                }
                $("#deviceUnusedList tbody").html(str);
            }
        },
        //编辑点巡检项的信息列表渲染
        loadCheckParams:function (list) {
            if(list!=[]){
                $("#checkList2 tbody").empty();
                var str="";
                for(var ele in list){
                    var categoryId = list[ele].categoryId;
                    if (categoryId != null) {  //共有点巡检项不再显示
                        continue;
                    }
                    str+='<tr>' +
                        '<td  class="name">'+list[ele].name+'</td>'+
                        '<td  class="checkMethod">'+list[ele].checkMethod+'</td>'+
                        '<td  class="checkStandard">'+list[ele].checkStandard+'</td>'+
                        '<td  class="paraTypeName" data-id="'+list[ele].paraType+'">'+list[ele].paraTypeName+'</td>'+
                        '<td  class="dictionaryName" data-id="'+list[ele].dictionaryId+'">'+list[ele].dictionaryName+'</td>'+
                        '<td  class="dataSourceId">'+list[ele].dataSourceId+'</td>'+
                        '<td><a class="link a-checkParams-edit"> <i class="fa-table-line-edit"  title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'+
                        '<a   class="link a-checkParams-delete"><i class="fa-table-line-delete"  title="删除"></i></a></td></tr>';
                }
                $("#checkList2 tbody").html(str);
            }
        },
        //编辑保养参数的信息列表渲染
        loadPreventiveParams:function (list) {
            if(list!=[]){
                $("#preventiveList2 tbody").empty();
                var str="";
                for(var ele in list){
                    var categoryId = list[ele].categoryId;
                    if (categoryId != null) {  //共有点巡检项不再显示
                        continue;
                    }
                    str+='<tr>' +
                        '<td  class="name">'+list[ele].name+'</td>'+
                        '<td  class="checkMethod">'+list[ele].checkMethod+'</td>'+
                        '<td  class="checkStandard">'+list[ele].checkStandard+'</td>'+
                        '<td  class="sparepartsId" data-id="'+list[ele].sparepartsId+'">'+list[ele].sparepartsName+'</td>'+
                        '<td><a class="link a-preventiveParams-edit"> <i class="fa-table-line-edit"  title="编辑"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;'+
                        '<a   class="link a-preventiveParams-delete"><i class="fa-table-line-delete"  title="删除"></i></a></td></tr>';
                }
                $("#preventiveList2 tbody").html(str);
            }
        },
        //------获取设备状态列表
        loadDeviceStatus: function() {
            var _this=this;
            //获取设备状态列表
            _addEquipment_service.getDeviceStatus(function (res) {
                var result=res;
                if(result.code==0) {
                    var data = result.data.statuses;
                    var device = "";
                    for (var i = 0; i <data.length; i++) {
                        if (data[i].id == 3) {
                            device += "<option value='" + data[i].id + "' disabled>" + data[i].name + " </option>";
                        }else {
                            device += "<option value='" + data[i].id + "'>" + data[i].name + " </option>";
                        }
                    }
                    $("#deviceStatusId").html(device);
                }
                else{
                    $.DialogByZ.Autofade({Content:'获取设备状态列表失败！'});
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        //------获取设备分类列表 (下拉树)
        loadDeviceTreeOptions: function() {
            var _this=this;
            //获取设备列表列表树
            _addEquipment_service.getCategoryStructure(function (res) {
                var result=res;
                if(result.code==0) {
                    getCategoryStructureTreeNode = $.fn.zTree.init($("#treeSearchContainerUL"),categoryInitSetting, result.data);
                    getCategoryStructureTreeNode.expandAll('true');
                    $(".categorySearchTree").slideToggle();
                }
                else{
                    $.DialogByZ.Autofade({Content:'获取设备分类树失败！'});
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        //获取组织机构列表 (下拉树)
        loadDepartmentTreeOptions: function() {
            var _this=this;
            //获取组织机构列表树
            _addEquipment_service.getDepartmentStructure(function (res) {
                var result=res;
                if(result.code==0) {
                    var departmentInitSetting ={
                        view: {
                            dblClickExpand: false,
                            showIcon: false
                        },
                        edit: {
                            enable: false
                        },
                        data: {
                            simpleData: {
                                enable: true,
                                idKey: "id",
                                //pIdKey: "parentId",
                            },
                            key: {
                                children: "subDepartment",
                                name: "name"
                            }
                        },
                        callback: {
                            onClick: onClickDepartTree
                        }
                    }
                    deptZtreeObj = $.fn.zTree.init($("#departmentSelectZtree"),departmentInitSetting, result.data);
                    deptZtreeObj.expandAll('true');
                }
                else{
                    $.DialogByZ.Autofade({Content:'获取组织结构树失败！'});
                }
            },function(err){
                _commonFun.errorTips('');
            })
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
        $("#installDate").jeDate({
            festival: false,
            ishmsVal: false,
            initAddVal:[0],
            format: "YYYY-MM-DD",
            zIndex: 3000,
            okfun: function(obj){
                var value = obj.val;
                var productionDate = $("#productionDate").val();  //出厂日期
                if (productionDate != null && productionDate != "") {
                    if (value < productionDate) {
                        $.DialogByZ.Autofade({Content:'安装日期不能早于出厂日期！'});
                        $("#installDate").val("");
                        return;
                    }
                }
            }
        });
        $("#productionDate").jeDate({
            festival: false,
            ishmsVal: false,
            initAddVal:[0],
            format: "YYYY-MM-DD",
            zIndex: 3000,
            okfun: function(obj){
                var value = obj.val;
                var installDate = $("#installDate").val();  //安装日期
                if (installDate != null && installDate != "") {
                    if (value > installDate) {
                        $.DialogByZ.Autofade({Content:'出厂日期不能晚于安装日期！'});
                        $("#productionDate").val("");
                        return;
                    }
                }
            }
        });
    }
    //选中设备分类
    function onClickCategoryTree(event, treeId, treeNode) {
        categoryTreeNodeId= treeNode.id;
        categoryTreeNodeName=treeNode.name;
        $(".categorySpan").attr('data-id',categoryTreeNodeId);
        $(".categorySpan").text(categoryTreeNodeName);
        _addEquipment.loadCategoryParameter(categoryTreeNodeId);//根据设备分类，获取对应的出厂参数
        _addEquipment.loadCategoryParameter_check(categoryTreeNodeId);//根据设备分类，获取对应的点巡检参数
        _addEquipment.loadCategoryParameter_preventive(categoryTreeNodeId);//根据设备分类，获取对应的保养参数
    }
    //选中部门
    function onClickDepartTree (event, treeId, treeNode) {
        var id = treeNode.id;
        var companyId,deptId;
        if (id.match("C")) { //选中的是客户
            companyId = id.substring(2);
            $(".companySpan").attr('data-id',companyId);
            $(".companySpan").text(treeNode.name);
            $(".departmentSpan").attr('data-id',"");
            $(".departmentSpan").text("");
            $(".companySpan").show();
            $(".departmentSpan").hide();
        } else if (id.match("D")) {  //选中的是部门
            deptId = id.substring(2);
            companyId = treeNode.pId.substring(2);
            $(".companySpan").attr('data-id',companyId);
            $(".companySpan").text("");
            $(".departmentSpan").attr('data-id',deptId);
            $(".departmentSpan").text(treeNode.name);
            $(".companySpan").hide();
            $(".departmentSpan").show();
        }
        $("#departmentTreeDiv").slideToggle();
    }
    //选中点巡检岗
    function onClickInspectionTree (event, treeId, treeNode) {
        var nodeId = treeNode.id;
        if (nodeId.indexOf("P") == -1) {  //选中的不是岗位
            $.DialogByZ.Autofade({Content:'请选择岗位！'});
        } else {  //选中的是岗位，保存岗位ID
            nodeId = nodeId.substring(2);  //截取出ID
            $(".posiInspectionSpan").attr('data-id',nodeId);
            $(".posiInspectionSpan").text(treeNode.NAME);
        }
    }
    //选中维修岗
    function onClickMaintainTree (event, treeId, treeNode) {
        var nodeId = treeNode.id;
        if (nodeId.indexOf("P") == -1) {  //选中的不是岗位
            $.DialogByZ.Autofade({Content:'请选择岗位！'});
        } else {  //选中的是岗位，保存岗位ID
            nodeId = nodeId.substring(2);  //截取出ID
            $(".posiMaintainSpan").attr('data-id',nodeId);
            $(".posiMaintainSpan").text(treeNode.NAME);
        }
    }
    //选中操作岗
    function onClickOperationTree (event, treeId, treeNode) {
        var nodeId = treeNode.id;
        if (nodeId.indexOf("P") == -1) {  //选中的不是岗位
            $.DialogByZ.Autofade({Content:'请选择岗位！'});
        } else {  //选中的是岗位，保存岗位ID
            nodeId = nodeId.substring(2);  //截取出ID
            $(".posiOperationSpan").attr('data-id',nodeId);
            $(".posiOperationSpan").text(treeNode.NAME);
        }
    }
    //选中审核岗
    function onClickCheckTree (event, treeId, treeNode) {
        var nodeId = treeNode.id;
        if (nodeId.indexOf("P") == -1) {  //选中的不是岗位
            $.DialogByZ.Autofade({Content:'请选择岗位！'});
        } else {  //选中的是岗位，保存岗位ID
            nodeId = nodeId.substring(2);  //截取出ID
            $(".posiCheckSpan").attr('data-id',nodeId);
            $(".posiCheckSpan").text(treeNode.NAME);
        }
    }
    function showDepartment() {
        $("#departmentTreeDiv").slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    }
    function hideDepartment() {
        $("#departmentTreeDiv").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }
    function onBodyDown(event) {
        if (!(event.target.id == "departmentTreeDiv" || $(event.target).parents("#departmentTreeDiv").length>0)) {
            hideDepartment();
        }
    }
    initDatetime();

    _addEquipment.init();

    $('#myTabs li a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')
    });
    var config = {
        '.chosen-select': {},
        '.chosen-select-deselect': {allow_single_deselect: true},
        '.chosen-select-no-single': {disable_search_threshold: 10},
        '.chosen-select-no-results': {no_results_text: 'Oops, nothing found!'},
        '.chosen-select-width': {width: "95%"}
    };

    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }

//     //---------文件上传JS start
//     $('#input-b2').fileinput('destroy');
//     $("#input-b2").fileinput({
//         fileinputLocales:'zh',
//         language : 'zh',//设置语言
//         uploadUrl:$.mpbGetHeaderPath()+"/deviceManagement/baseEquipment/fileUpload?deviceId="+_addEquipment.data.equipmentId, //上传的地址
//         allowedFileExtensions : [ ],
//         uploadAsync: true, //默认异步上传
//         showUpload:true, //是否显示上传按钮
//         showRemove :true, //显示移除按钮
//         showPreview :true, //是否显示预览
//         showCaption:true,//是否显示标题
//         browseClass:"btn btn-primary", //按钮样式
//         dropZoneEnabled: true,//是否显示拖拽区域
//         maxFileSize:20480,//单位为kb，如果为0表示不限制文件大小  20M20480
//         //minFileCount: 0,
//         maxFileCount:4, //表示允许同时上传的最大文件个数
//         enctype:'multipart/form-data',
//         validateInitialCount:true,
//         previewFileIcon: "<iclass='glyphicon glyphicon-king'></i>",
//         msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
//     }).on('fileuploaded', function(event, data, previewId, index) {//filebatchuploadsuccess
//         var result=JSON.parse(decodeURIComponent(data.response));
//         if(result.code==0){
//             $("#"+previewId).find(".kv-file-remove").click();
//             $.DialogByZ.Autofade({Content: result.message || "上传成功！"});
//             _fileManage.getFiles();
//         }
//         else{
//             $("#"+previewId).find(".progress").children().attr("class","progress-bar bg-danger progress-bar-danger");
//             $("#"+previewId).find(".progress").children().text("上传失败");
//             $("#"+previewId).find(".file-upload-indicator i").attr("class","fas fa-exclamation-circle text-danger");
//             $(".kv-upload-progress").hide();
// //            $.DialogByZ.Alert({Title: "提示", Content:result.message || "服务器运行异常！",BtnL:"确定", FunL:$.DialogByZ.Close()});
//             $.DialogByZ.Autofade({Content: result.message || "上传失败！"});
//         }
//     })
});


