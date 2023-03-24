
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
    var treeAuthorityTreeNode='';
    var _post={
        data:{
            loadPostsList   : [] , //load时获取的所有岗位列表
            changePostsList	: [] , //放通过部门查询获得的岗位列表
            employeeAllList : [] , //包含员工id 姓名 用于展示和搜索
            submitEmployees	: [] , //只包含员工Id，用于提交给后台
            currentPostId	: null , //点击编辑时，获取该岗位的岗位id 用于后面编辑保存
            empPostId		: '' , //存放点击指派人员按钮记录下岗位Id 用于后期使用
            roleText        : '角色',// or 岗位
            pattern         : /^[\u4E00-\u9FA5A-Za-z0-9_-]+$|^$/,
        },
        init:function(){
            this.onLoad();
            this.bindEvent();
            $(".roleOrDepart").text(this.data.roleText);
            $(".addPostBtn").val('添加'+this.data.roleText)
        },
        onLoad:function(){
            this.loadPosts();
            this.loadPermissions();
            $(".fa-postPop").hide();
        },
        bindEvent:function(){
            var _this=this;
            //左侧角色岗位选中事件
            $(document).on('click','.post-list table tbody tr', function(e){
                e.stopPropagation();
                var $this=$(this);
                var roleId=$this.data('id');//获取当前点击的角色ID
                //权限树可用
                dynamicUpdateNodeCheck(false);
                //勾选全部取消
                treeAuthorityTreeNode.checkAllNodes(false);//全不选
                _this.data.currentPostId=roleId;//赋值给currentPostId
                $this.siblings().removeClass('act');
                $this.addClass('act');
                new doAjax({
                    type:'get',
                    url: $.mpbGetHeaderPath()+'/permissionManagement/role/queryRole',
                    data:{
                        roleId:roleId
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
            });
            //保存修改的岗位权限数据
            $(document).on('click','.savePostPermis', function(){
                if(_this.data.currentPostId==null){
                    $.DialogByZ.Autofade({Content:"请先选择"+_this.data.roleText});
                    return
                }
                var resourceIds=[];
                var nodes= treeAuthorityTreeNode.getCheckedNodes(true);
                for(var i=0;i<nodes.length;i++){
                    resourceIds.push(nodes[i].id)
                }
                if(resourceIds.length>0){
                    var resourceIdStr=resourceIds.join(",");
                    new doAjax({
                        type:"post",
                        url: $.mpbGetHeaderPath()+'/permissionManagement/role/saveRolesAndResources',
                        data:{
                            roleId:_this.data.currentPostId,
                            resourceIds:resourceIdStr
                        },
                        callBack: function (result) {
                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "保存成功！"});
                            }
                            else {
                                $.DialogByZ.Autofade({Content: result.message || "保存失败！"});
                            }
                        }
                    });
                }
            });
            //点击新增角色/岗位按钮
            $(document).on('click','.addPostBtn', function(){
                $(".hideInputPostId").val("");
                $("#btPostCode").val("");
                $("#btPostName").val("");
                $(".fa-postPop").hide();
                $("#jobPopover").show();
            })
            //检查角色/岗位名称是否可用
            $(document).on('blur', '#btPostCode', function () {
                var roleName= $.trim($(this).val());
                if(roleName==""){
                    $(this).css("border","1px solid #EF4836");
                    $(".fa-postPop").show();
                    $(".fa-postPop").text("编码，不能为空！");
                    return;
                }
                $(this).removeAttr('style');
                $(".fa-postPop").hide();
                var post={
                    roleName:roleName
                }
                new doAjax({
                    url: $.mpbGetHeaderPath()+'/permissionManagement/role/checkRoleNameNotExist',
                    data:post,
                    callBack: function (result) {
                        if(result.code!=0) {
                            $(".fa-postPop").show();
                            $(".fa-postPop").text(result.message || "岗位编码不可用！");
                            $("#btPostCode").css("border","1px solid #EF4836");
                            $("#btPostCode").val("");
                        }
                    }
                });
            })
            $(document).on('blur', '#btPostName', function (){
                if($.trim($(this).val())==""){
                    $(this).css("border","1px solid #EF4836");
                    $(".fa-postPop").show();
                    $(".fa-postPop").text(" 岗位名称，不能为空！");
                    return;
                }
                $(this).removeAttr('style');
                $(".fa-postPop").hide();
            })
            //编辑角色
            $(document).on('click','.post-list .a-post-edit', function(e){
                e.stopPropagation();
                var _self=$(this);
                var roleDesc=_self.parent().parent().find(".txt").text();
                _self.parent().parent().find(".roleDesc").html('<input type="text" class="form-control editDescription" value="'+roleDesc+'" />');
                _self.attr("class","link a-post-save");
                _self.text("保存");
            })
            //保存修改的角色
            $(document).on('click','.post-list .a-post-save', function(e){
                e.stopPropagation();
                var _self=$(this);
                var postId=_self.parent().parent().data('id');
                var description=_self.parent().parent().find(".editDescription").val();
                var pat=/^[\u4E00-\u9FA5A-Za-z0-9_-]+$/;
                if(!pat){
                    $.DialogByZ.Autofade({Content: "描述只能填字母数字_-"});
                    return
                }
                var postData={
                    roleId			: postId,
                    roleDescription	: description
                }
                new doAjax({
                    url: $.mpbGetHeaderPath()+'/permissionManagement/role/editRole',
                    data:postData,
                    callBack: function (result) {
                        if(result.code==0) {
                            _self.parent().parent().find(".roleDesc").html('<span class="txt">'+description+'</span>');
                            _self.attr("class","link a-post-edit");
                            _self.text("编辑");
                        }
                        else{
                            $.DialogByZ.Autofade({Content:result.message ||  "修改失败"});
                        }
                    }
                });
            })
            //保存新增的角色
            $(document).on('click','#savePostInfoBtn', function(){
                var roleName		= $.trim($("#btPostCode").val());
                var roleDescription	= $.trim($("#btPostName").val());
                if(roleName==""){
                    $("#btPostCode").css("border","1px solid #EF4836");
                    $(".fa-postPop").show();
                    $(".fa-postPop").text(" 编码，不能为空！");
                    return;
                }
                $("#btPostCode").removeAttr('style');
                if(roleDescription==""){
                    $("#btPostName").css("border","1px solid #EF4836");
                    $(".fa-postPop").show();
                    $(".fa-postPop").text(" 名称，不能为空！");
                    return;
                }
                $("#btPostName").removeAttr('style');
                if (!_this.data.pattern.test(roleName) || !_this.data.pattern.test(roleDescription)) {
                    $(".fa-postPop").show();
                    $(".fa-postPop").text("信息只能输入字母数字_-");
                    return;
                }
                $(".fa-postPop").hide();
                var postData={
                    roleName:roleName,
                    roleDescription:roleDescription
                }
                new doAjax({
                    url: $.mpbGetHeaderPath()+'/permissionManagement/role/addRole',
                    data:postData,
                    callBack: function (result) {
                        if(result.code==0) {
                            $.DialogByZ.Autofade({Content: "新增成功！"});
                            _this.loadPosts()
                            _this.data.currentPostId=null;
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
            })
            //取消按钮
            $(document).on('click','#cancelPostBtn', function(){
                $("#jobPopover").hide();
                $("#btPostCode").val("");
                $("#btPostName").val("");
            })
            //删除岗位or角色
            $(document).on('click','.post-list .a-post-delete', function(e){
                e.stopPropagation();
                var _self=$(this);
                var postId=_self.parent().parent().data('id');
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除吗？",
                    FunL:function(){
                        new doAjax({
                            type:'post',
                            url: $.mpbGetHeaderPath()+'/permissionManagement/role/deleteRole',
                            data:{
                                roleId:postId
                            },
                            callBack: function (result) {
                                if(result.code==0) {
                                    $.DialogByZ.Autofade({Content: "删除成功！"});
                                    _this.loadPosts();
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
        //获取角色/or岗位列表
        loadPosts:function () {
            var _this=this;
            new doAjax({
                type:'get',
                url: $.mpbGetHeaderPath()+'/permissionManagement/role/getRolesAndResources',
                callBack: function (result) {
                    if(result.code==0) {
                        var data=result.data;
                        _this.showPosts(data);
                    }
                    else {
                        $(".post-list table tbody").html("<div style='border-bottom: 1px solid #ddd;padding: 10px 0 8px 10px'>无岗位信息</div>");
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
                    '<td style="width:160px;padding: 11px 0 0px 10px;"  class="roleDesc"><span class="txt">'+item.description+'</span></td>'+
                    '<td style="width:120px;padding-left: 10px;"><a class="link a-post-edit">编辑</a>'+
                    '<a class="link a-post-delete del">删除</a></td></tr>';
            });
            $(".post-list table tbody").html(strHtml);
        }
    };
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