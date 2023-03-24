
var _post_service={
    //获取菜单列表及对应岗位
    getMenuPosts: function (resolve, reject) {
        $.ajax({
            type	: "get",
            url		: $.mpbGetHeaderPath()+'/menuManagement/getMenuPosts',
            success	: resolve,
            error	: reject
        });
    },
    //获取岗位权限列表
    getPostsAndPermissions : function(resolve, reject){
        $.ajax({
            type	: "get",
            url     : $.mpbGetHeaderPath()+'/basicInfoManagement/postManagement/getPostsAndPermissions',
            success : resolve,
            error   : reject
        });
    },
    //加载部门树
    getDepartmentStructure : function(resolve, reject){
        $.ajax({
            type	:"get",
            url		: $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/getDepartmentStructure',
            success	:resolve,
            error	:reject
        });
    },
    //获取权限列表
    getPermissions: function(resolve, reject){
        $.ajax({
            type	: "get",
            url     : $.mpbGetHeaderPath()+'/permissionManagement/permission/getPermissions',
            success : resolve,
            error   : reject
        });
    },
    savePostPermis: function(postPerInfo,resolve, reject){
        $.ajax({
            type	: 'post',
            url     : $.mpbGetHeaderPath()+'/basicInfoManagement/postManagement/savePostsAndPermissions',
            data	: postPerInfo,
            success : resolve,
            error   : reject
        });
    },
    //根据岗位id获取岗位详情
    getPostInfoDetail: function(postId,resolve, reject){
        $.ajax({
            type	: "get",
            url     : $.mpbGetHeaderPath()+'/basicInfoManagement/postManagement/getPostInfo',
            data	: {
                postId:postId
            },
            success : resolve,
            error   : reject
        });
    },
    addPosts: function(PostPerInfo,resolve, reject){
        $.ajax({
            type	: 'post',
            url     : $.mpbGetHeaderPath()+'/basicInfoManagement/postManagement/addPost',
            data	: PostPerInfo,
            success : resolve,
            error   : reject
        });
    },
    editPostInfo: function(PostPerInfo,resolve, reject){
        $.ajax({
            type	: 'post',
            url     : $.mpbGetHeaderPath()+'/basicInfoManagement/postManagement/editPostInfo',
            data	: PostPerInfo,
            success : resolve,
            error   : reject
        });
    },
    checkPostCodeAvailable: function(post,resolve, reject){
        $.ajax({
            type	: 'post',
            url     : $.mpbGetHeaderPath()+'/basicInfoManagement/postManagement/checkPostCodeAvailable',
            data	: post,
            success : resolve,
            error   : reject
        });
    },
    //根据岗位ID获取已指派到该岗位的人员
    getEmployeeByPost: function(postId,resolve, reject){
        $.ajax({
            type	: "get",
            url     : $.mpbGetHeaderPath()+'/basicInfoManagement/postManagement/getEmployeeByPost',
            data	: {
                postId:postId
            },
            success : resolve,
            error   : reject
        });
    },
    getEmployeesNotAssigned: function(resolve, reject){
        $.ajax({
            type	: "get",
            url     : $.mpbGetHeaderPath()+'/basicInfoManagement/employeeManagement/getEmployeesNotAssigned',
            success : resolve,
            error   : reject
        });
    },
    //保存岗位-人员指派关系
    saveAssignedEmployee: function(employeeInfo,resolve, reject){
        $.ajax({
            type	: "get",
            url     : $.mpbGetHeaderPath()+'/basicInfoManagement/postManagement/saveAssignedEmployee',
            data	: employeeInfo,
            success : resolve,
            error   : reject
        });
    },
    //删除岗位
    deletePost: function(postId,resolve, reject){
        $.ajax({
            type	: "get",
            url     : $.mpbGetHeaderPath()+'/basicInfoManagement/postManagement/deletePost',
            data	: {
                postId:postId
            },
            success : resolve,
            error   : reject
        });
    },
    //获取权限列表
    getPermissionsWithGroup: function(resolve, reject){
        $.ajax({
            type	: "get",
            url     :$.mpbGetHeaderPath()+ '/permissionManagement/permission/getPermissionsWithGroup',
            success : resolve,
            error   : reject
        });
    },
    //根据部门ID获取岗位列表
    getPostsByDepartmentId: function(departmentId,resolve, reject){
        $.ajax({
            type	:"get",
            url		: $.mpbGetHeaderPath()+'/basicInfoManagement/postManagement/getPosts',
            data    :{
                departmentId:departmentId
            },
            success	: resolve,
            error	: reject
        });
    },

};
$(function(){
    //部门树
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
            onClick: onClickInitDepartTree
        }
    };

    var treeAuthoritySetting={
        view: {
            dblClickExpand: false
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y" : "ps", "N" : "ps" }
        },
        edit: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true,
            },
            key: {
                title   :'', //存id
                children: "subMenu",
                name: "title"
            }
        }

    };
    var selectPopTreeNode='', selectInitTreeNode='',treeAuthorityTreeNode='';
    //选中的部门Id
    var treeNodeId="",treeNodeName;
    //部门树点击事件
    function  onClickInitDepartTree(event, treeId, treeNode) {
        treeNodeId= treeNode.departmentId;
        treeNodeName=treeNode.departmentName;
        _post.loadPostListById(treeNodeId);
    }
    var _post={
        data:{
            loadPostsList   : [] , //load时获取的所有岗位列表
            changePostsList	: [] , //放通过部门查询获得的岗位列表
            employeeAllList : [] , //包含员工id 姓名 用于展示和搜索
            submitEmployees	: [] , //只包含员工Id，用于提交给后台
            currentPostId	: '' , //点击编辑时，获取该岗位的岗位id 用于后面编辑保存
            empPostId		: '' ,//存放点击指派人员按钮记录下岗位Id 用于后期使用
            pattern         : /^[\u4E00-\u9FA5A-Za-z0-9_-]+$|^$/,
            roleId          :''

        },
        init:function(){
            this.onLoad();
            this.bindEvent();
        },
        onLoad:function(){
            this.loadDepartTree();
            this.loadPermissions();
            this.loadRolesOption();
            $(".fa-postPop").hide();
            $(".post-list table tbody").html("<div style='border-bottom: 1px solid #ddd;padding: 10px 0 8px 10px'>请选择部门</div>");
        },
        bindEvent:function(){
            var _this=this;
            //点击新增岗位
            $(document).on('click','.addPostBtn', function(){
                if(treeNodeId==""){
                    $.DialogByZ.Autofade({Content: "请先选择部门！"});
                    return;
                }
                $("#departText").text(treeNodeName);
                $("#getHaveRole").show();
                $("#btPostCode").removeAttr("disabled");
                $(".hideInputPostId").val("");
                $("#btPostCode").val("");
                $("#btPostName").val("");
                $(".fa-postPop").hide();
                $("#jobPopover").show();
            })
            //编辑岗位 selectPopTreeNode.cancelSelectedNode();//取消当前所有被选中节点的选中状态
            $(document).on('click','.post-list .a-post-edit', function(e){
                e.stopPropagation();
                var _self=$(this);
                var roleDesc=_self.parent().parent().find(".txt").text();
                _self.parent().parent().find(".roleDesc").html('<input type="text" class="form-control editDescription" value="'+roleDesc+'" />');
                _self.attr("class","link a-post-save");
                _self.text("保存");
            })
            $(document).on('click','.post-list .a-post-save', function(e){
                e.stopPropagation();
                var _self=$(this);
                var postId=_self.parent().parent().data('id');
                var postName=_self.parent().parent().find(".editDescription").val();
                var pat=/^[\u4E00-\u9FA5A-Za-z0-9_-]+$/;
                if(!pat){
                    $.DialogByZ.Autofade({Content: "岗位描述只能填字母数字_-"});
                    return
                }
                new doAjax({
                    url: $.mpbGetHeaderPath()+'/postManagement/editPostInfo',
                    data:{
                        id:postId,
                        postName:postName
                    },
                    callBack: function (result) {
                        if(result.code==0) {
                            _self.parent().parent().find(".roleDesc").html('<span class="txt">'+postName+'</span>');
                            _self.attr("class","link a-post-edit");
                            _self.text("编辑");
                        }
                        else{
                            $.DialogByZ.Autofade({Content:result.message ||  "修改失败"});
                        }
                    }
                });
            })
            //保存新增的岗位详情
            $(document).on('click','#savePostInfoBtn', function(){
                //获取modal上的字段
                var postCode		= $.trim($("#btPostCode").val());
                var postName		= $.trim($("#btPostName").val());
                var roleId          = $("#rolesTextVal").val();
                var postData={departmentId:treeNodeId}
                if(roleId!=""){
                    postData.roleId=roleId
                }
                else{
                    if(postCode==""){
                        $("#btPostCode").css("border","1px solid #EF4836");
                        $(".fa-postPop").show();
                        $(".fa-postPop").text(" 编码，不能为空！");
                        return;
                    }
                    $("#btPostCode").removeAttr('style');
                    if(postName==""){
                        $("#btPostName").css("border","1px solid #EF4836");
                        $(".fa-postPop").show();
                        $(".fa-postPop").text(" 名称，不能为空！");
                        return;
                    }
                    $("#btPostName").removeAttr('style');
                    if (!_this.data.pattern.test(postCode) || !_this.data.pattern.test(postName)) {
                        $(".fa-postPop").show();
                        $(".fa-postPop").text("岗位信息只能输入字母数字_-");
                        return;
                    }
                    $(".fa-postPop").hide();
                    postData.postCode=postCode;
                    postData.postName=postName;
                }console.log(postData)
                new doAjax({
                    url: $.mpbGetHeaderPath()+'/postManagement/addPost',
                    data:postData,
                    callBack: function (result) {
                        if(result.code==0) {
                            $.DialogByZ.Autofade({Content: "新增成功！"});
                            _this.loadPostListById(treeNodeId)
                            $("#jobPopover").hide();
                        }
                        else {
                            $.DialogByZ.Autofade({Content: result.message || "新增失败！"});
                        }
                    }
                });
            })
            //关闭弹窗
            $(document).on('click','#popPostClose', function(){
                $("#jobPopover").hide();
                $("#btPostCode").val("");
                $("#btPostName").val("");
                $("#btPostDepart").val("");
                $(".popPostSpan").text('请选择部门');
                $(".popPostSpan").attr('data-id','');
            })
            //取消按钮
            $(document).on('click','#cancelPostBtn', function(){
                $("#jobPopover").hide();
                $("#btPostCode").val("");
                $("#btPostName").val("");
                $("#btPostDepart").val("");
                $(".popPostSpan").text('请选择部门');
                $(".popPostSpan").attr('data-id','');
            })
            //取消按钮
            $(document).on('click','.sourceCancel', function(){
                $("#sources").hide();
            })
            //分配资源
            $(document).on('click','.post-list .a-post-resource', function(e) {
                e.stopPropagation();
                var $this=$(this)
                var postId=$this.parent().parent().data('id');
                _this.data.roleId=postId;
                $("#sources").show()
                treeAuthorityTreeNode.checkAllNodes(false);//全不选
                new doAjax({
                    type:'get',
                    url: $.mpbGetHeaderPath()+'/permissionManagement/role/queryRole',
                    data:{
                        roleId:postId
                    },
                    callBack: function (result) {
                        var list=result.data.resourceList;
                        console.log(list)
                        if(result.code==0) {
                            //选中列表中的权限
                            for(var i=0;i<list.length;i++){
                                var node = treeAuthorityTreeNode.getNodeByParam("id",list[i].id);
                                treeAuthorityTreeNode.checkNode(node, true, false);
                            }
                        }
                        else {
                            $.DialogByZ.Autofade({Content: result.message });
                        }
                    }
                });
            })
            // 分配资源保存
            $(document).on('click','#saveSourceInfoBtn', function(e){
                var roleId=_this.data.roleId;
                var resourceIds=[];
                var nodes= treeAuthorityTreeNode.getCheckedNodes(true);
                console.log(nodes)
                for(var i=0;i<nodes.length;i++){
                    resourceIds.push(nodes[i].id)
                }
                if(resourceIds.length>0){
                    var resourceIdStr=resourceIds.join(",")
                    new doAjax({
                        url: $.mpbGetHeaderPath()+'/permissionManagement/role/saveRolesAndResources',
                        data:{
                            roleId:roleId,
                            resourceIds:resourceIdStr
                        },
                        callBack: function (result) {
                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "保存成功！"});
                                _this.data.roleId=""
                                $("#sources").modal('hide')
                            }
                            else {
                                $.DialogByZ.Autofade({Content: result.message || "删除失败！"});
                            }
                        }
                    });
                }
            })
            //删除岗位
            $(document).on('click','.post-list .a-post-delete', function(e){
                e.stopPropagation();
                var _self=$(this);
                var postId=_self.parent().parent().data('id');
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除该岗位吗？",
                    FunL:function(){
                        new doAjax({
                            url: $.mpbGetHeaderPath()+'/postManagement/deletePost',
                            data:{
                                departmentId:treeNodeId,
                                postId:postId
                            },
                            callBack: function (result) {
                                if(result.code==0) {
                                    $.DialogByZ.Autofade({Content: "删除成功！"});
                                    _this.loadPostListById(treeNodeId)
                                }
                                else {
                                    $.DialogByZ.Autofade({Content: result.message || "删除失败！"});
                                }
                            }
                        });
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            })
            $('#tabList a[href="#postInf"]').click(function (e) {
                e.preventDefault()
                $(this).tab('show')
                $("#rolesTextVal").val("")
            })
            $('#tabList a[href="#rolesInf"]').click(function (e) {
                e.preventDefault()
                $(this).tab('show')
                $("#btPostCode").val("")
                $("#btPostName").val("")
            })
        },
        //获取所有资源
        loadPermissions:function() {
            new doAjax({
                type:'get',
                url: $.mpbGetHeaderPath()+'/resourceManagement/getAllResourceList',
                callBack: function (result) {
                    if(result.code==0) {
                        $.fn.zTree.init($("#treeAuthorityContainerUL"), treeAuthoritySetting, result.data);
                        treeAuthorityTreeNode= $.fn.zTree.getZTreeObj("treeAuthorityContainerUL");
                        treeAuthorityTreeNode.expandAll('true');
                        //资源树checkbox可用
                        dynamicUpdateNodeCheck(false);
                    }
                }
            });
        },
        //获取部门结构树
        loadDepartTree: function () {
            new doAjax({
                type:'get',
                url: $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/getDepartmentStructure',
                callBack: function (result) {
                    $(".tree-cont").html('<div class="sm-loading"></div>');
                    if(result.code==0) {
                        $.fn.zTree.init($("#treeListContainerUL"), treeInitSetting, result.data);
                        selectInitTreeNode= $.fn.zTree.getZTreeObj("treeListContainerUL");
                        selectInitTreeNode.expandAll('true');
                    }
                    else{
                        $.DialogByZ.Autofade({Content:'获取部门结构失败！'});
                        $(".tree-cont").html('');
                    }
                }
            });
        },
        //通过部门获取岗位列表
        loadPostListById:function (id) {
            var _this=this;
            new doAjax({
                type:'get',
                url: $.mpbGetHeaderPath()+'/basicInfoManagement/departmentManagement/getPostsUnderDepartment',
                data:{departmentId:id},
                callBack: function (result) {
                    if(result.code==0) {
                        if(result.data && result.data.length>0){
                            _this.showPosts(result.data);
                        }
                        else {
                            $(".post-list table tbody").html("<div style='border-bottom: 1px solid #ddd;padding: 10px 0 8px 10px'>无岗位信息</div>");
                        }
                    }
                }
            });
        },
        //从现有角色中选择
        loadRolesOption:function () {
            new doAjax({
                type:'get',
                url: $.mpbGetHeaderPath()+'/permissionManagement/role/getRolesAndResources',
                callBack: function (result) {
                    if(result.code==0) {
                        var data=result.data;
                        $("#rolesTextVal").empty();
                        var options = "<option value=''>-请选择现有角色-</option>";
                        for (var i = 0; i < data.length; i++) {
                            options += "<option value='" + data[i].id + "'>" + data[i].name + '___'+ data[i].description +"</option>";
                        }
                        $("#rolesTextVal").html(options);
                    }
                }
            });
        },
        //展示岗位列表
        showPosts:function(data){
            $(".post-list table tbody").empty();
            var strHtml="";
            $.each(data,function(index,item){
                strHtml+='<tr data-id="'+item.id+'" >'+
                    '<td style="width: 130px;padding-left: 10px;">'+item.name+'</td>'+
                    '<td style="width:170px;padding: 11px 0 0px 10px;" class="roleDesc"><span class="txt">'+item.description+'</span></td>'+
                    '<td style="width:225px;padding-left: 10px;"><a class="link a-post-edit">编辑</a><a class="link a-post-resource" data-toggle="modal" data-target="#sources">分配资源</a>'+
                    '<a class="link a-post-delete del">删除</a></td></tr>';
            });
            $(".post-list table tbody").html(strHtml);
        },
        //获取权限列表
        loadPermissionsT:function(){
            _post_service.getPermissionsWithGroup(function(res){
                var result=JSON.parse(decodeURIComponent(res));
                if(result.code==0) {
                    var data=result.data;
                    selectData(data);
                    $.fn.zTree.init($("#treeAuthorityContainerUL"), treeAuthoritySetting, data);
                    treeAuthorityTreeNode= $.fn.zTree.getZTreeObj("treeAuthorityContainerUL");
                    treeAuthorityTreeNode.expandAll('true');
                    //权限树可用
                    // dynamicUpdateNodeCheck(false);
                }
            },function(res){
                _commonFun.errorTips('');
            });
        },
        //通过选中的岗位ID获取权限列表并返回列表显示选中效果
        getCheckedPermissions:function(id,data){
            var _this=this;
            $.each(data,function(index,item){
                if(item.postId==id){
                    if(item.permissionIds){
                        _this.checkPermissions(item.permissionIds);
                    }
                }
            })
        },
        //选中列表中的权限
        checkPermissions:function(list){
            for(var i=0;i<list.length;i++){
                var node = treeAuthorityTreeNode.getNodeByParam("id",list[i]);
                if(node.type=="PERMISSION"){
                    treeAuthorityTreeNode.checkNode(node, true, true);
                }
            }
        },
        //保存岗位权限的列表修改
        savePostAndPermis:function(arr){
            var posts=[];
            $.each(arr,function (e,item) {
                var detail={};
                detail.postId=item.postId;
                detail.permissionIds=item.permissionIds || [];
                posts.push(detail);
            })
            var source={'posts':posts};
            var data=JSON.stringify(source);
            var info={'posts':data};
            _post_service.savePostPermis(info,function(res){
                var result=JSON.parse(decodeURIComponent(res));
                if(result.code==0) {
                    $.DialogByZ.Autofade({Content: "岗位权限列表保存成功！"});
                }
                else {
                    $.DialogByZ.Autofade({Content: result.message});
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        //展示人员列表
        showModalEmployee:function(arr){
            $(".checkbox-list").html('<div class="sm-loading"></div>');
            var strEmp='';
            $(arr).each(function(i,item){
                if(item.selected){
                    strEmp += '<label data-id='+item.employeeId+'><input type="checkbox" checked>'+item.employeeName+'</label>';
                }
                else{
                    strEmp += '<label data-id='+item.employeeId+'><input type="checkbox">'+item.employeeName+'</label>';
                }
            });
            $(".checkbox-list").html(strEmp);
        }
    };

    function showIconForTree(treeId, treeNode) {
        return !treeNode.isParent;
    }
    function selectData(zNodes){
        $(zNodes).each(function(i,el){
            if(el.children){
                selectData(el.subMenu);
            }
            else{
                if(el.type=="PERMISSION"){
                    el.icon="../../css/zTreeStyle/img/diy/11.png";
                }
                else{
                    el.icon="../../css/zTreeStyle/img/diy/12.png";
                }
            }
        })
    }
    //过滤节点的机制 直接return node表示不做任何过滤
    function filter(node) {
        return node;
    }
    ///动态设置zTree的所有节点有checkbox
    function dynamicUpdateNodeCheck(obj) {
        //根据过滤机制获得zTree的所有节点
        var nodes = treeAuthorityTreeNode.getNodesByFilter(filter);
        //遍历每一个节点然后动态更新nocheck属性值
        for (var i = 0; i < nodes.length; i++) {
            treeAuthorityTreeNode.setChkDisabled(nodes[i],obj);
        }
    }
    _post.init();
});
