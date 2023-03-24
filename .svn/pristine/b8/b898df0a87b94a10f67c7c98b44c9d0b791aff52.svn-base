var treeInitSetting={
        view: {
            dblClickExpand: false,
            showIcon: showIconForTree
        },
        edit: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "departmentId",
            },
            key: {
                children: "subDepartment",
                name: "departmentName"
            }
        },
        callback: {
            onClick: onClickDepartTree
        }
    }
;//初始获得的数据的树 数据
var treeNodeId=null;
var treeNodeSelectId='';
function onClickDepartTree(event, treeId, treeNode) {
    treeNodeSelectId=treeNode;
    treeNodeId= treeNode.departmentId;

    _depart.loadDepartment(treeNodeId);
    $("#departmentAttr").val("");
}
function showIconForTree(treeId, treeNode) {
    return !treeNode.isParent;
}
var selectTreeNode='';
var _depart= {
    data: {
        currentTreeRes: [],	//当前的树 数据
        originalTreeRes: []    // 获取的原始树 数据
    },
    init: function () {
        this.onLoad();
        this.bindEvent();
    },
    onLoad: function () {
        this.loadDepartTree();
        this.loadDepartment(0);
        this.loadDepartTypeOptions();
        $(".fa-departmentPop").hide();
    },
    bindEvent: function () {
        var _this = this;
        //保存部门结构树
        $(document).on('click', '#saveTree', function () {
            var treeNodes = $.fn.zTree.getZTreeObj("treeListContainerUL").getNodes();
            var targetData=_this.toListData(treeNodes);
            targetData=JSON.stringify(targetData);
            $.DialogByZ.Confirm({
                Title: "操作提示",
                Content: "是否保存对部门结构的修改",
                FunL: function () {
                    $('.saveTree').val('保存中...');
                    $(".saveTree").attr("disabled", "true");
                    new doAjax({
                        type: "post",
                        url : $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/saveDepartmentStructure',
                        data: targetData,
                        callBack: function (result) {
                            if(result.code==0){
                                $('.saveTree').val('保存');
                                $(".saveTree").removeAttr("disabled");
                                $.DialogByZ.Autofade({Content: "部门结构保存成功！"});
                            }
                        }
                    });
                    $.DialogByZ.Close();
                },
                FunR: function () {
                    $.DialogByZ.Close();
                }
            })
        });
        //节点上移
        $(document).on('click', '#moveUp', function () {
            var zTree = $.fn.zTree.getZTreeObj("treeListContainerUL"),
                nodes = zTree.getSelectedNodes(),
                treeNode = nodes[0];
            var preNode = treeNode.getPreNode();
            if(preNode!=null){
                zTree.moveNode(preNode, treeNode, "prev");
            }
        });
        //节点下移
        $(document).on('click', '#moveDown', function () {
            var zTree = $.fn.zTree.getZTreeObj("treeListContainerUL"),
                nodes = zTree.getSelectedNodes(),
                treeNode = nodes[0];
            var nextNode = treeNode.getNextNode();
            if (nextNode != null){
                zTree.moveNode(nextNode, treeNode, "next");
            }
        })
        //节点还原
        $(document).on('click', '#reductTree', function () {
            $.DialogByZ.Confirm({
                Title: "操作提示",
                Content: "是否还原部门结构",
                FunL: function () {
                    _this.loadTreeSource(_this.data.originalTreeRes);
                    $.DialogByZ.Autofade({Content: "还原成功！"});
                    $.DialogByZ.Close();
                },
                FunR: function () {
                    $.DialogByZ.Close();
                }
            })
        })
        //点击添加部门按钮
        $(document).on('click', '#addDepartmentBtn', function () {
            $(".cusPopover-title span").text("添加部门");
            $("#departmentCode").val('');
            $("#departmentName").val('');
            $("#attributeSelet").val('');
            $("#departmtDecr").val('');
            $(".hideInputDepartmId").val("");
            $(".fa-departmentPop").hide();
            $("#dptPopover").show();
        });
        //通过部门属性Id获取部门列表
        $(document).on('change', '#departmentAttr', function () {
            var departmentType =  $(this).val();
            var data = {
                departmentType	: departmentType,
                parentId		: treeNodeId || 0
            };
            //获取部门列表并显示
            new doAjax({
                url : $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/getDepartments',
                data: data,
                callBack: function (result) {
                    if(result.code==0) {
                        console.log(result.data)
                        _this.loadDepartmentHtml(result.data);
                    }
                }
            });
        })
        //点击列表中的编辑功能
        $(document).on('click', '#dptInfoTb .a-edit-depart', function (e) {
            e.stopPropagation();
            var _self = $(this);
            $(".fa-departmentPop").hide();
            //根据部门Id获取部门详情
            var departmentId = _self.parent().parent().data('departmentid');
            //将部门ID放入隐藏域里
            $(".hideInputDepartmId").val(departmentId);
            $(".cusPopover-title span").text("部门修改");
            new doAjax({
                url : $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/getDepartmentInfo',
                data: {departmentId:departmentId},
                callBack: function (result) {
                    if(result.code==0){
                        var result=result.data;
                        $("#departmentCode").val(result.departmentCode);
                        $("#departmentName").val(result.departmentName);
                        $("#attributeSelet").val(result.departmentType);
                        $("#departmtDecr").val(result.departmentDescription);
                        $("#dptPopover").show();
                    }
                }
            });
        })
        //检查部门编码是否可用
        $(document).on('blur', '#departmentCode', function () {
            var departmentCode=$.trim($(this).val()),
                departmentId=$.trim($(".hideInputDepartmId").val()),
                department={};
            if(departmentCode==""){
                $(this).css("border","1px solid #EF4836");
                $(".fa-departmentPop").show();
                $(".fa-departmentPop").text(" 部门编码，不能为空！");
                return;
            }
            $(this).removeAttr('style');
            $(".fa-departmentPop").hide();
            if(departmentId==""){
                department={
                    departmentCode:departmentCode
                }
            }
            else {
                department={
                    departmentCode:departmentCode,
                    departmentId:departmentId
                }
            }
            new doAjax({
                url : $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/checkDepartmentCodeAvailable',
                data: department,
                callBack: function (result) {
                    if(!result.data){
                        $(".fa-departmentPop").show();
                        $(".fa-departmentPop").text(result.message ||  "部门编码不可用！");
                        $('#departmentCode').css("border","1px solid #EF4836");
                        $('#departmentCode').val("");
                    }
                }
            });
        })
        $(document).on('blur', '#departmentName', function (){
            if($.trim($(this).val())==""){
                $(this).css("border","1px solid #EF4836");
                $(".fa-departmentPop").show();
                $(".fa-departmentPop").text(" 部门名称，不能为空！");
                return;
            }
            $(this).removeAttr('style');
            $(".fa-departmentPop").hide();
        })
        $(document).on('blur', '#attributeSelet', function (){
            if($.trim($(this).val())==""){
                $(this).css("border","1px solid #EF4836");
                $(".fa-departmentPop").show();
                $(".fa-departmentPop").text(" 员工姓名，不能为空！");
                return;
            }
            $(this).removeAttr('style');
            $(".fa-departmentPop").hide();
        })
        //保存部门详情
        $(document).on('click', '.saveDepartmentInfo', function () {
            var departmentId = $.trim($(".hideInputDepartmId").val());
            //获取modal上的字段
            var departmentCode = $.trim($("#departmentCode").val());
            var departmentName = $.trim($("#departmentName").val());
            var departmentType = $.trim($("#attributeSelet").val());
            var departmentDescription = $.trim($("#departmtDecr").val());
            if(departmentCode==""){
                $("#departmentCode").css("border","1px solid #EF4836");
                $(".fa-departmentPop").show();
                $(".fa-departmentPop").text(" 部门编码，不能为空！");
                return;
            }
            $("#departmentCode").removeAttr('style');
            if(departmentName==""){
                $("#departmentName").css("border","1px solid #EF4836");
                $(".fa-departmentPop").show();
                $(".fa-departmentPop").text(" 部门名称，不能为空！");
                return;
            }
            $("#departmentName").removeAttr('style');
            if(departmentType==""){
                $("#attributeSelet").css("border","1px solid #EF4836");
                $(".fa-departmentPop").show();
                $(".fa-departmentPop").text(" 部门属性，不能为空！");
                return;
            }
            $("#attributeSelet").removeAttr('style');
            $(".fa-departmentPop").hide();
            if (departmentId == "") {
                var departData = {
                    departmentCode: departmentCode,
                    departmentName: departmentName,
                    departmentType: departmentType,
                    parentId	  : treeNodeId || 0,
                    departmentDescription: departmentDescription
                }
                new doAjax({
                    url : $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/addDepartment',
                    data: departData,
                    callBack: function (result) {
                        if(result.code==0) {
                            $.DialogByZ.Autofade({Content: "新增部门成功！"});
                            $("#dptPopover").hide();
                            //刷新部门树
                            _this.loadDepartTree();
                            _this.loadDepartment(treeNodeId);
                        }
                        else {
                            $.DialogByZ.Autofade({Content: result.message || "新增部门失败！"});
                        }
                    }
                });
            }
            else {
                var editDepartData = {
                    departmentId	: departmentId,
                    departmentCode	: departmentCode,
                    departmentName	: departmentName,
                    departmentType	: departmentType,
                    parentId		: treeNodeId || 0,
                    departmentDescription: departmentDescription
                }
                new doAjax({
                    url : $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/editDepartment',
                    data: editDepartData,
                    callBack: function (result) {
                        if(result.code==0) {
                            $.DialogByZ.Autofade({Content: "修改部门成功！"});
                            $("#dptPopover").hide();
                            //刷新部门树
                            _this.loadDepartTree();
                            _this.loadDepartment(treeNodeId);
                        }
                        else {
                            $.DialogByZ.Autofade({Content: result.message || "修改部门失败！"});
                        }
                    }
                });
            }
        })
        //关闭部门弹窗
        $(document).on('click', '#deptpopClose', function () {
            $("#departmentCode").val('');
            $("#departmentName").val('');
            $("#attributeSelet").val('');
            $("#departmtDecr").val('');
            $("#dptPopover").hide();
        })
        //关闭属性维护弹窗
        $(document).on('click', '#popCloseType', function () {
            $("#animalBtn").animate({
                top: '-34px'
            }, 600);
            _this.loadDepartTypeOptions();
            $("#departmentTypeText").hide();
            $("#typePopover").hide();
        })
        //关闭弹窗
        $(document).on('click', '.closeDepartmentInfo', function () {
            $("#departmentCode").val('');
            $("#departmentName").val('');
            $("#attributeSelet").val('');
            $("#departmtDecr").val('');
            $("#dptPopover").hide();
        })
        //删除部门
        $(document).on('click', '.dpt-info .a-delete', function (e) {
            e.stopPropagation();
            var _self = $(this);
            var departmentId = _self.parent().parent().data('departmentid');
            $.DialogByZ.Confirm({
                Title: "操作提示",
                Content: "确定要删除部门吗?",
                FunL: function () {
                    new doAjax({
                        url : $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/deleteDepartment',
                        data:{departmentId:departmentId},
                        callBack: function (result) {
                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "删除成功！"});
                                _this.loadDepartTree();
                                _this.loadDepartment(treeNodeId);
                            }
                            else{
                                $.DialogByZ.Alert({Title: "提示", Content:result.message || "操作失败！", BtnL:"确定", FunL:$.DialogByZ.Close()});
                            }
                        }
                    });
                    $.DialogByZ.Close();
                },
                FunR: function () {
                    $.DialogByZ.Close();
                }
            })
        })
        //点击属性按钮 modal
        $(document).on('click', '#editDepartmentTypeBtn', function () {
            $("#typePopover").show();
            _this.loadDepartmentType();
            $(".cusPopover-title span").text("属性维护");
            $("#departmentTypeText").hide();

        })
        //点击列表中的编辑属性按钮
        $(document).on('click', '#typePopover .editDepartType', function () {
            var _self = $(this);
            _self.parent().animate({
                // left: '-55px'
                top: '-44px'
            }, 600);

            var typeName = _self.parent().parent().parent().find(".type-item-left").text();

            var strInput = '<input type="text" class="form-control" value=\"' + typeName + '\" />';
            _self.parent().parent().parent().find(".type-item-left").html(strInput);
        })
        //点击属性弹窗中的编辑保存按钮
        $(document).on('click', '#typePopover .saveDepartType', function () {
            var _self = $(this);
            var typeName = _self.parent().parent().parent().find(".form-control").val();
            var typeId = _self.parent().parent().parent().find(".hideDepartmentType").val();
            if (typeName != "") {
                var data = {
                    typeId: typeId,
                    typeName: typeName
                };
                new doAjax({
                    url : $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/editDepartmentTypes',
                    data:data,
                    callBack: function (result) {
                        if(result.code==0) {
                            _self.parent().parent().parent().find(".type-item-left").empty();
                            _self.parent().parent().parent().find(".type-item-left").text(typeName);
                            _self.parent().animate({
                                // left: '0px'
                                top:0
                            }, 600);
                            $.DialogByZ.Autofade({Content: "修改成功！"});
                        }
                        else {
                            $.DialogByZ.Autofade({Content: result.message});
                            _self.parent().parent().parent().find(".form-control").val('');
                        }
                    }
                });
            }
        });
        //点击属性弹窗中的保存属性按钮
        $(document).on('click', '#saveDepartmentType', function () {
            var _self = $(this);
            var typeVal = $.trim($("#departmentTypeText").val());
            var _com_pattern = new RegExp("[=\\=&? ？ *%]"); //过滤特殊字符
            if (typeVal != "") {
                if (!_com_pattern.test(typeVal)) {
                    new doAjax({
                        url : $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/addDepartmentType',
                        data: {
                            typeName:typeVal
                        },
                        callBack: function (result) {
                            if(result.data) {
                                $("#departmentTypeText").val("");
                                _this.loadDepartmentType();
                            }
                            else{
                                $.DialogByZ.Autofade({Content:result.message});
                                $("#departmentTypeText").val("");
                            }
                        }
                    });
                }
            }
        })
        //点击列表中的新增属性功能
        $(document).on('click', '#addDepartmentType', function () {
            var _self = $(this);
            $("#animalBtn").animate({
                top: '0'
            }, 600);
            $("#departmentTypeText").show();
        });
        //删除部门属性
        $(document).on('click', '#typePopover .deleteDepartType', function (e) {
            var _self=$(this);
            e.stopPropagation();
            var typeId = _self.parents('.type-item').data('typeid');
            $.DialogByZ.Confirm({
                Title: "操作提示",
                Content: "确定要删除属性吗?",
                FunL: function () {
                    new doAjax({
                        url : $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/deleteDepartmentTypes',
                        data:{
                            typeId:typeId
                        },
                        callBack: function (result) {
                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "删除成功！"});
                                _self.parents('.type-item').remove();
                                _this.loadDepartmentType();
                            }
                            else {
                                $.DialogByZ.Autofade({Content:result.message});
                            }
                        }
                    });
                    $.DialogByZ.Close();
                },
                FunR: function () {
                    $.DialogByZ.Close();
                }
            })
        })
    },
    //渲染部门树
    loadDepartTree: function () {
        var _this = this;
        //获取部门结构树
        new doAjax({
            type:'get',
            url: $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/getDepartmentStructure',
            callBack: function (result) {
                console.log(result)
                if(result.code==0) {
                    _this.loadTreeSource(result.data);
                }
                else{
                    $.DialogByZ.Autofade({Content:'获取部门结构失败！'});
                }
            }
        });
    },
    //公共 展示部门树
    loadTreeSource: function (nodes) {
        this.data.originalTreeRes=nodes;
        this.data.currentTreeRes=nodes;
        selectTreeNode = $.fn.zTree.init($("#treeListContainerUL"),treeInitSetting, nodes);
        selectTreeNode.expandAll('true');
    },
    //load部门列表
    loadDepartment: function (treeNodeId) {
        var data = {
            parentId: treeNodeId || 0
        };
        var _this=this;
        new doAjax({
            type:'get',
            url: $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/getDepartments',
            data:data,
            callBack: function (result) {
                if(result.code==0){
                    var data=result.data;
                    _this.loadDepartmentHtml(data);
                }
                else {
                    $("#dptInfoTb").empty();
                }
            }
        });
    },
    //渲染部门列表table
    loadDepartmentHtml: function (list) {
        $("#dptInfoTb").empty();
        var str = '',departmentTypeName="";
        for (ele in list) {
            typeof(list[ele].departmentTypeName) == "undefined" ? departmentTypeName="" : departmentTypeName=list[ele].departmentTypeName;
            str += '<tr data-departmentid="' + list[ele].departmentId + '">' +
                '<td>' + list[ele].departmentCode + '</td>' +
                '<td>' + list[ele].departmentName + '</td>' +
                '<td>' + departmentTypeName + '</td>' +
                '<td>' + list[ele].departmentDescription + '</td>' +
                '<td style="min-width: 160px"><a class="link a-edit-depart">编辑</a>&nbsp;&nbsp;' +
                '<a class="link a-delete del" >删除</a></td></tr>';
        }
        $("#dptInfoTb").html(str);
    },
    //递归调用
    toListData:function(data) {
        var _this=this;
        var list = [];
        $(data).each(function (index, item) {
            var itemList = {};
            if (item.subDepartment) {
                itemList.id = item.departmentId;
                itemList.name = item.departmentName;
                itemList.subDepartment = _this.toListData(item.subDepartment);
            }
            else {
                itemList.id = item.departmentId;
                itemList.name = item.departmentName;
            }
            list.push(itemList);
        })
        return list;
    },
    loadDepartTypeOptions: function () {
        new doAjax({
            type:'get',
            url: $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/getDepartmentTypes',
            callBack: function (result) {
                if(result.code==0){
                    var data=result.data;
                    $("#attributeSelet").empty();
                    $("#departmentAttr").empty();
                    var options = "<option value=''>-请选择-</option>";
                    for (var i = 0; i < data.length; i++) {
                        options += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                    }
                    $("#attributeSelet").html(options);
                    $("#departmentAttr").html(options);
                }
            }
        });
    },
    loadDepartmentType: function () {
        new doAjax({
            type:'get',
            url: $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/getDepartmentTypes',
            callBack: function (result) {
                if(result.code==0){
                    var res=result.data;
                    $("#typePopover .type-con").empty();
                    var strType = "";
                    for (var ele in res) {
                        strType += '<div class="type-item" data-typeid="' + res[ele].id + '">' +
                            '<input class="hideDepartmentType" type="hidden" value=\"' + res[ele].id + '\">' +
                            '<div class="type-item-left">' + res[ele].name + '</div>' +
                            '<div class="type-item-right">' +
                            '<div class="edit-and-save">' +
                            '<input type="button" class="btn editDepartType" value="修改" />' +
                            '<input type="button" class="btn saveDepartType" value="保存"  />	' +
                            '</div>' +
                            '<input type="button" class="btn deleteDepartType" value="删除"/>' +
                            '</div></div>';
                    }
                    $("#typePopover .type-con").html(strType);
                }
                else{
                    $("#typePopover .type-con").empty();
                }
            }
        });
    }
};
$(function(){
    _depart.init();
});

