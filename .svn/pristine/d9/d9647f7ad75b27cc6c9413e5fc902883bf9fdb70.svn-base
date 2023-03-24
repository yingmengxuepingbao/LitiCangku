var menuTree,currentSelectNode=null,sourceList=[];
//资源设置
var menuSetting ={
    view: {
        dblClickExpand: false
        ,fontCss:setFontCss
    },
    edit: {
        enable: false
    },
    data: {
        simpleData: {
            enable: true
        },
        key:{
            children:"subMenu",
            name:"title",
            title:"title",
            url:"url",
            parentId:"parentId"
        }
    },
    callback: {
        beforeClick: beforeMenuClick,
        onClick: onClickMenu
    }
};
function beforeMenuClick(treeId, treeNode) {
    //如果是灰色就不能点击
    if(treeNode.isModifiable==0){
        return false;
    }
}
function onClickMenu(e, treeId, treeNode) {
    currentSelectNode = treeNode;
    console.log(treeNode)
    if (treeNode.subMenu && treeNode.subMenu.length > 0) {
        $('#targetFileChangeInput').val("");
        $('#targetFileChangeInput').attr("disabled", "disabled");
        $("#reportUrlSelect").val("");
        $("#reportUrlSelect").attr("disabled", "disabled");
        $('.currentImg').attr('src', treeNode.icon);
        $(".imgDiv").show()
    }
    else {
        $('#targetFileChangeInput').removeAttr("disabled");
        $('#targetFileChangeInput').val(treeNode.url);
        $("#reportUrlSelect").removeAttr("disabled");
        $("#reportUrlSelect").val(treeNode.url);
        console.log(treeNode.url)
        if(treeNode.resType=="3"){
            $("#reportUrlSelect").show();
            $('#targetFileChangeInput').hide();
        }
        else{
            $('#targetFileChangeInput').show();
            $("#reportUrlSelect").hide();
        }
        $('.currentImg').attr('src', ' ');
        $(".imgDiv").hide()
    }
    $('#menuName').val(treeNode.title);
    $('#menuSequence').val(treeNode.sequence);
    $('#menuCode').val(treeNode.resCode);
    $('#menuResType').val(treeNode.resType);
    $('#targetFileChangeInput').val(treeNode.url);
    $('#descriptTextarea').val(treeNode.description);
}
var _resource={
    data: {
        getMenuList: []	//获取到的菜单列表
    },
    init: function () {
        this.onLoad();
        this.bindEvent();
    },
    onLoad: function () {
        this.loadList();
        this.getMenuFilePath();
        this.loadImgList();
        $(".imgDiv").hide();
    },
    bindEvent: function () {
        var _this=this;
        // 资源保存修改
        $(document).on('click','.saveMenuChange',function () {
            if(currentSelectNode==null){
                $.DialogByZ.Autofade({Content:"请选择资源！"});
                return
            }
            var pattern = /^[\u4E00-\u9FA5A-Za-z0-9_-]+$/;
            var patternNon = /^[\u4E00-\u9FA5A-Za-z0-9_-]+$|^$/;//可以为空
            var sourceName=$('#menuName').val();
            var sourceSequence=$('#menuSequence').val();
            var sourceResCode=$('#menuCode').val();
            var sourceResType=$('#menuResType').val();
            var sourceUrl='';
            if(sourceResType=="3"){
                sourceUrl=$("#reportUrlSelect").val();
            }
            else{
                sourceUrl=$("#targetFileChangeInput").val();
            }
            var sourceDesc=$('#descriptTextarea').val();
            if (!pattern.test(sourceName)) {
                $.DialogByZ.Autofade({Content:"资源名称为必填项，且只能输入字母数字_-"});
                $('#menuName').val('')
                return;
            }
            if (!/^[0-9]+$/.test(sourceSequence)) {
                $.DialogByZ.Autofade({Content:"资源排序为必填项，且只能输入数字"});
                $('#menuSequence').val('')
                return;
            }
            if(!/^[\w\/\.-\:]+$/.test(sourceUrl)){
                $.DialogByZ.Autofade({Content:"输入的Url不允许有特殊字符！"});
                $('#targetFileChangeInput').val('');
                $('#targetFileChangeInput').attr("placeholder","只可输入数字、字母及._/:");
            }
            if (!patternNon.test(sourceResCode)) {
                $.DialogByZ.Autofade({Content:"资源编码只能输入字母数字_-"});
                $('#menuCode').val('')
                return;
            }
            if (!patternNon.test(sourceDesc)) {
                $.DialogByZ.Autofade({Content:"资源描述只能输入字母数字_-"});
                $('#descriptTextarea').val('')
                return;
            }
            var obj={
                id          : currentSelectNode.id,
                parentId    : currentSelectNode.parentId,
                level       : currentSelectNode.level,
                sequence    : sourceSequence,
                title       : sourceName,
                resCode     : sourceResCode,
                description : sourceDesc,
                icon        : $('.currentImg').attr('src'),
                resType     : sourceResType,
                url         : sourceUrl

            }
            //在这里删除根路径，保证数据库只存不带根路径的图片地址
            var pathName=window.document.location.pathname;
            var projectName = pathName.substring(1,pathName.substr(1).indexOf('/')+1);
            var projectNameLen = projectName.length;
            var icon = obj.icon;
            obj.icon = icon.substr(projectNameLen+1,icon.length);
            // console.log(obj);

            new doAjax({
                url: $.mpbGetHeaderPath()+'/resourceManagement/updateResource',
                data:obj,
                callBack: function (result) {
                    if(result.code=='0'){
                        _this.loadList();
                        $.DialogByZ.Autofade({Content:"保存成功！"});
                    }else{
                        $.DialogByZ.Autofade({Content:result.message||"保存失败！"});
                    }
                }
            });
        })
        //添加根节点
        $(document).on('click','.addRootMenu',function () {
            var rootInfo={
                parentId:0,
                level:1,
                sequence:sourceList[sourceList.length-1].sequence+1,
                title:"新建目录",
                resCode:"",
                description:"",
                icon:"",
                resType:"",
                url:""
            }
            _this.addSource(rootInfo)
        })
        $(document).on('click','.imgs img',function () {
            $('.currentImg').attr('src',$(this).attr('src'));
            $('.imgContainer').css('display','none');
            currentSelectNode.icon=$(this).attr('src');
        })
        $(document).on('click','#menuResType',function () {
            var _self=$(this);
            if($(this).val()=="3"){
                $("#targetFileChangeInput").hide();
                $("#reportUrlSelect").show()
            }
            else{
                $("#targetFileChangeInput").show();
                $("#reportUrlSelect").hide()
            }
        })
        $(document).on('click',function (e) {
            e.stopPropagation();
            $('.imgContainer').css('display','none');
        })
        $('.uploadMenuImg').click(function(event){
            $('.imgContainer').css('display','block');
            event.stopPropagation();
        })
        //添加子节点
        $(document).on('click','.addChildMenu',function () {
            if(currentSelectNode!=null){
                if(currentSelectNode.level=='2'){
                    $.DialogByZ.Autofade({Content:"资源层级限制在三级以内！"});
                    return
                }
                var childInfo={
                    parentId:currentSelectNode.id,
                    level:parseInt(currentSelectNode.level)+1,
                    sequence:currentSelectNode.subMenu ? sourceList[currentSelectNode.subMenu.length-1].sequence+1 : 0,
                    title:"新菜资源",
                    resCode:"",
                    description:"",
                    icon:"",
                    resType:"",
                    url:""
                }
                _this.addSource(childInfo)
            }
            else{
                $.DialogByZ.Autofade({Content:"请选择资源！"});
            }
        })
        //删除节点
        $(document).on('click','.deleteMenu',function () {
            if(currentSelectNode!=null){
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除该资源吗？",
                    FunL:function(){
                        new doAjax({
                            url: $.mpbGetHeaderPath()+'/resourceManagement/deleteResource',
                            data:{
                                resourceId:currentSelectNode.id
                            },
                            callBack: function (result) {
                                if(result.code=='0'){
                                    _this.loadList();
                                }else{
                                    $.DialogByZ.Autofade({Content:result.message||"删除失败"});
                                }
                            }
                        });
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            }
            else{
                $.DialogByZ.Autofade({Content:"请选择资源！"});
            }
        })
    },
    //获取所有资源
    loadList:function () {
        new doAjax({
            type:'get',
            url: $.mpbGetHeaderPath()+'/resourceManagement/getAllResourceList',
            callBack: function (result) {
                if(result.code=='0'){
                    sourceList=result.data;
                    //在这里填充上跟路径
                    var pathName=window.document.location.pathname;
                    var projectName=pathName.substring(1,pathName.substr(1).indexOf('/')+1);
                    for (var i = 0; i < sourceList.length; i++) {
                        sourceList[i].icon = "/" + projectName + sourceList[i].icon;
                    }
                    menuTree = $.fn.zTree.init($("#treeListContainerUL"), menuSetting, sourceList);
                    menuTree.expandAll('true');
                    currentSelectNode=null;
                }else{
                    $.DialogByZ.Autofade({Content:result.message||"获取资源失败"});
                }
            }
        });
    },
    addSource:function (data) {
        var _this=this;
        new doAjax({
            url: $.mpbGetHeaderPath()+'/resourceManagement/addResource',
            data:data,
            callBack: function (result) {
                if(result.code=='0'){
                    _this.loadList();
                    $.DialogByZ.Autofade({Content:"新建成功！"});
                }else{
                    $.DialogByZ.Autofade({Content:result.message||"新建失败！"});
                }
            }
        });
    },
    //获取报表列表
    getMenuFilePath:function () {
        new doAjax({
            type: 'get',
            url: $.mpbGetHeaderPath() + '/reportManagement/getAllReportList',
            callBack: function (result) {
                if(result.code==0){
                    var list=result.data;
                    console.log(list)
                    $("#reportUrlSelect").empty();
                    var options="<option value=''>-请选择报表路径-</option>";
                    for(var j=0;j<list.length;j++){
                        options+="<option value='"+list[j].url+"'>"+list[j].url+"</option>";
                    }
                    $("#reportUrlSelect").html(options);
                }
                else{
                    $("#reportUrlSelect").empty();
                    $.DialogByZ.Autofade({Content: result.message || "获取报表失败"});
                }
            }
        })
    },
    loadImgList:function () {
        new doAjax({
            type: 'get',
            url: $.mpbGetHeaderPath() + '/resourceManagement/getAllMenuIcons',
            callBack: function (result) {
                if(result.code=='0'){
                    var list=result.data;
                    var container=$('.imgs');
                    $(list).each(function(index,item){
                        var img=$('<img src='+item+' />');
                        img.appendTo(container);
                    })
                }else{
                    $.DialogByZ.Autofade({Content: result.message || "获取图标失败"});
                }
            }
        })
    }
}
_resource.init();

// getList();
var getMenuList=[];//获取到的菜单列表
var changeMenuList=[];//修改中的MenuList
var currentMenu={};//点击后，当前的Menu信息

var fileTree;
function getList(){
    loadList();
    getMenuFilePath();
    loadImgList();
}
function loadList(){
    $.ajax({
        type:'get',
        url: $.mpbGetHeaderPath()+"/menuManagement/getAllMenuList",
        success:function(result){
            result=JSON.parse(decodeURIComponent(result));
            if(result.code=='0'){
                showMenuList(result.data);
            }else{
                Confirm.show('获取列表失败',result.message||"获取失败");
            }
        },
        error :function(result){
            Confirm.show('获取列表失败','请检查网络连接');
        }
    })
}
function loadImgList(){
    $.ajax({
        type:'get',
        url: $.mpbGetHeaderPath()+"/menuManagement/getAllMenuIcons",
        success:function(result){
            result=JSON.parse(decodeURIComponent(result));
            if(result.code=='0'){
                showMenuIconList(result.data);
            }else{
                Confirm.show('获取列表失败',result.message||"获取失败");
            }
        },
        error :function(result){
            Confirm.show('获取列表失败','请检查网络连接');
        }
    })
}
function showMenuIconList(list){
    var container=$('.imgs');
    $(list).each(function(index,item){
        var img=$('<img src='+item+' onclick=selectImg(this) />')
        img.appendTo(container);
    })

}

// function showMenuList(list){
//     getMenuList=null;
//     changeMenuList=null;
//     getMenuList=$.extend(true, [getMenuList], list);
//     changeMenuList=$.extend(true, [changeMenuList], list);
//     menuTree = $.fn.zTree.init($("#treeListContainerUL"), menuSetting, changeMenuList);
//     menuTree.expandAll('true');
// }


function setFontCss(treeId, treeNode) {
//	设置颜色，灰色
    return treeNode.isModifiable == 0 ? {color:'#b0b0b0'} : {color:"black"};
};



var currentTreeNode;

// function onClickMenu(e, treeId, treeNode) {
//     currentTreeNode=treeNode;
//     treeNodeId= treeNode.departmentId;
//     if(currentTreeNode.subMenu && currentTreeNode.subMenu.length>0){
//         $('#targetFileChangeInput').val("");
//         $('#targetFileChangeInput').attr("disabled","disabled");
//     }
//     else{
//         $('#targetFileChangeInput').removeAttr("disabled");
//         $('#targetFileChangeInput').val(treeNode.url);
//     }
//     $('#menuName').val(treeNode.title);
//
//     $('#descriptTextarea').val(treeNode.description);
//     $('.currentImg').attr('src',' ');
//     $('.currentImg').attr('src',treeNode.icon);
    // $('#menuName').unbind('blur');
    // $('#menuName').blur(function(){
    //     treeNode.title=$('#menuName').val();
    //     var treeObj = $.fn.zTree.getZTreeObj("treeListContainerUL");
    //     var nodes = treeObj.getSelectedNodes();
    //     treeObj.editName(nodes[0]);
    //     treeObj.cancelEditName($('#menuName').val());
    // });
    // $('#targetFileChangeInput').unbind('blur');
    // $('#targetFileChangeInput').blur(function(){
    //     if($(this).val()!=""){
    //         if(/^[\w\/\.-]+$/.test($('#targetFileChangeInput').val())){
    //             treeNode.url=$('#targetFileChangeInput').val();
    //         }else{
    //             Confirm.show('提示','输入的Url含有特殊字符，保存信息失败');
    //             $('#targetFileChangeInput').val('');
    //             $('#targetFileChangeInput').attr("placeholder","只可输入数字、字母及._/");
    //         }
    //     }
    //     else{
    //         treeNode.url="";
    //     }
    // });
    //
    // $('#descriptTextarea').unbind('blur');
    // $('#descriptTextarea').blur(function(){
    //     treeNode.description=$('#descriptTextarea').val();
    // });
    //
    // $('.imgs img').unbind('click');
    // $('.imgs img').click(function(){
    //     $('.currentImg').attr('src',$(this).attr('src'));
    //     $('.imgContainer').css('display','none');
    //     treeNode.icon=$(this).attr('src');
    // });
    // if(treeNode.getParentNode()==null){
    //     $('.imgDiv').css('display','block');
    // }else{
    //     $('.imgDiv').css('display','none');
    // }
// }

// $('.saveMenuChange').click(function(){
    // var treeNodes=$.fn.zTree.getZTreeObj("treeListContainerUL").getNodes();
    // var targetData=toListData(treeNodes);
    // var isHaveUrl=true,name='';
    // $(targetData).each(function(index,item){
    //     if(item.subMenu && item.subMenu.length>0){
    //         $(item.subMenu).each(function(index,subItem){
    //             if(subItem.subMenu && subItem.subMenu.length>=1){
    //                 $(subItem.subMenu).each(function(index,lastItem){
    //                     if(lastItem.url==undefined || lastItem.url==""){
    //                         isHaveUrl=false;
    //                         name+=lastItem.title+',';
    //                         return false;
    //                     }
    //                 })
    //             }
    //             else{
    //                 if(subItem.url==undefined || subItem.url==""){
    //                     isHaveUrl=false;
    //                     name+=subItem.title+',';
    //                     return false;
    //                 }
    //             }
    //         })
    //     }
    //     else {
    //         if(item.url==undefined ||  item.url==""){
    //             isHaveUrl=false;
    //             name+=item.title+',';
    //             return false;
    //         }
    //     }
    // })
    // if(isHaveUrl==false){
    //     Confirm.show('提示','名为 “'+name+'” 的菜单路径为空,请填写！');
    //     return
    // }
    // targetData=JSON.stringify(targetData);
    // Confirm.show('操作提示',"是否保存所有修改",{
    //     '保存':{
    //         'callback':function(){
    //             $('.saveMenuChange').button('loading');
    //             $.ajax({
    //                 type:'put',
    //                 url: $.mpbGetHeaderPath()+"/menuManagement/updateMenu",
    //                 data:{
    //                     "menuListString":targetData
    //                 },
    //                 success:function(result){
    //                     $('.saveMenuChange').button('reset');
    //                     result=JSON.parse(decodeURIComponent(result));
    //                     if(result.code=='0'){
    //                         Confirm.show('保存提示提示',"保存成功");
    //                     }else{
    //                         Confirm.show('保存提示',result.message||"保存");
    //                     }
    //                 },
    //                 error :function(result){
    //                     $('.saveMenuChange').button('reset');
    //                     Confirm.show('保存失败','请检查网络连接');
    //                 }
    //             })
    //         }
    //     }
    // });
// });

// function toListData(data){
//     var list=[];
//     var listlegal=true;
//     $(data).each(function(index,item){
//         var itemList={}
//         if(item.subMenu){
//             itemList.menuIcon=item.icon;
//             itemList.title=item.title;
//             itemList.description=item.description;
//             itemList.url=item.url;
//             itemList.isModifiable=item.isModifiable;
//             itemList.subMenu=toListData(item.subMenu);
//         }else{
//             itemList.menuIcon=item.icon;
//             itemList.title=item.title;
//             itemList.description=item.description;
//             itemList.url=item.url;
//             itemList.isModifiable=item.isModifiable;
//         }
//
//         list.push(itemList);
//     })
//     return list;
// }
// $('.deleteMenu').click(function(){
//     remove();
// })
// $('.addRootMenu').click(function(){
//     addRoot();
// })
// $('.addChildMenu').click(function(){
//     add();
// })
// var newCount = 1;
// function add() {
//     var zTree = $.fn.zTree.getZTreeObj("treeListContainerUL"),
//         nodes = zTree.getSelectedNodes(),
//         treeNode = nodes[0];
//     if (treeNode) {
//         if(treeNode.level=='1'){
//             Confirm.show('提示',"菜单层级限制为两层！");
//             return;
//         }
//         treeNode = zTree.addNodes(treeNode, {title:"新菜单项",subMenu:[],description:'',url:'',isModifiable:1,id:(100 + newCount), pId:treeNode.id
//             , name:"new node" + (newCount++)});
//     } else {
//         treeNode = zTree.addNodes(null, {title:"新菜单项",subMenu:[],description:'',url:'',isModifiable:1,id:(100 + newCount), pId:0
//             , name:"new node" + (newCount++)});
//     }
//     if (treeNode) {
//     } else {
//         Confirm.show('',"叶子节点被锁定，无法增加子节点");
//     }
// };
// function addRoot(){
//     var zTree = $.fn.zTree.getZTreeObj("treeListContainerUL"),
//         nodes = zTree,
//         treeNode = nodes[0];
//     if (treeNode) {
//         treeNode = zTree.addNodes(treeNode, {title:"新菜单项",subMenu:[],description:'',url:null,isModifiable:1,id:(100 + newCount), pId:treeNode.id
//             , name:"new node" + (newCount++)});
//     } else {
//         treeNode = zTree.addNodes(null, {title:"新菜单项",subMenu:[],description:'',url:null,isModifiable:1,id:(100 + newCount), pId:0
//             , name:"new node" + (newCount++)});
//     }
//     if (treeNode) {
//     } else {
//         Confirm.show('提示',"叶子节点被锁定，无法增加子节点");
//
//     }
// }
// function remove() {
//     var zTree = $.fn.zTree.getZTreeObj("treeListContainerUL"),
//         nodes = zTree.getSelectedNodes(),
//         treeNode = nodes[0];
//     if (nodes.length == 0) {
//         Confirm.show('提示',"请选择要删除节点");
//         return;
//     }
//     if(treeNode.isModifiable==0){
//         Confirm.show('提示',"不可操作此菜单");
//         return
//     }
//     if(treeNode.isParent){
//         Confirm.show('删除提示', "是否删除"+treeNode.title +"和它的所有子菜单？",{
//             '确认删除':{
//                 'callback':function(){
//                     zTree.removeNode(treeNode, false);
//                 }
//             }
//         });
//     }else{
//         Confirm.show('删除提示','是否删除'+treeNode.title + "？",{
//             '确认删除':{
//                 'callback':function(){
//                     zTree.removeNode(treeNode, false);
//                 }
//             }
//         });
//     }
// };
//
// function getMenuFilePath(){
//     $.ajax({
//         type:'GET',
//         url: $.mpbGetHeaderPath()+"/menuManagement/getMenuFilePath",
// //		url:"./json/ml_menuListTreeData.json",
//         success:function(result){
//             result=JSON.parse(decodeURIComponent(result));
//             if(result.code=='0'){
//                 showMenuFilePath(result.data);
//             }else{
//                 Confirm.show('删除',result.message||"删除失败");
//             }
//         },
//         error :function(result){
//             Confirm.show('服务器错误','请检查网络连接');
//         }
//     })
//
// }
//
// function showMenuFilePath(path){
//     $.fn.zTree.init($("#targetFileChangeUl"), fileChangeSetting, path);
// }
//
//
// function beforeFileChangeClick(treeId, treeNode) {
//     var check = (treeNode && !treeNode.isParent);
//     if(treeNode.name.indexOf(".") > 0 ){
//     }
//     return check;
// }
//
// function onClickFileChange(e, treeId, treeNode) {
//     var zTree = $.fn.zTree.getZTreeObj("targetFileChangeUl"),
//         nodes = zTree.getSelectedNodes(),
//         v = "";
//
//     var path = "";
//     p = treeNode.getParentNode();
//     while (p != null){
//         path = p.name + "/" + path;
//         p = p.getParentNode();
//     }
//
//     var cityObj = $("#targetFileChangeInput");
//     cityObj.val(path+treeNode.name);
//
//     currentTreeNode.url=path+treeNode.name;
// }
// function showMenuTree(){
//     $("#targetFileChangeInputContent")
//         .css({right:"60px", top: "24px",})
//         .slideDown("fast");
//     $("body").bind("mousedown", onBodyDownChange);
// }
// function hideMenuFileChange(){
//     $("#targetFileChangeInputContent").fadeOut("fast");
//     $("body").unbind("mousedown", onBodyDownChange);
// }
// function onBodyDownChange(event){
//     if (!(event.target.id == "menuBtn" || event.target.id == "targetFileChangeInputContent" || $(event.target).parents("#targetFileChangeInputContent").length>0)) {
//         hideMenuFileChange();
//     }
// }
