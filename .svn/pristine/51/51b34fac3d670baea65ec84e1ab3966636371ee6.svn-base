getList();
var getMenuList=[];//获取到的菜单列表
var changeMenuList=[];//修改中的MenuList
var currentMenu={};//点击后，当前的Menu信息
var menuTree;
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

function showMenuList(list){
    getMenuList=null;
    changeMenuList=null;
    getMenuList=$.extend(true, [getMenuList], list);
    changeMenuList=$.extend(true, [changeMenuList], list);
    menuTree = $.fn.zTree.init($("#treeListContainerUL"), menuSetting, changeMenuList);
    menuTree.expandAll('true');
}


function setFontCss(treeId, treeNode) {
//	设置颜色，灰色
    return treeNode.isModifiable == 0 ? {color:'#b0b0b0'} : {color:"black"};
};
//菜单设置
var menuSetting =
    {
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
                children:"subMenu"
                ,name:"title"
                ,title:"title"
                ,url:"menuUrl",
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
var currentTreeNode;

function onClickMenu(e, treeId, treeNode) {
    currentTreeNode=treeNode;
    treeNodeId= treeNode.departmentId;
    if(currentTreeNode.subMenu && currentTreeNode.subMenu.length>0){
        $('#targetFileChangeInput').val("");
        $('#targetFileChangeInput').attr("disabled","disabled");
    }
    else{
        $('#targetFileChangeInput').removeAttr("disabled");
        $('#targetFileChangeInput').val(treeNode.url);
    }
    $('#menuName').val(treeNode.title);

    $('#descriptTextarea').val(treeNode.description);
    $('.currentImg').attr('src',' ');
    $('.currentImg').attr('src',treeNode.icon);
    $('#menuName').unbind('blur');
    $('#menuName').blur(function(){
        treeNode.title=$('#menuName').val();
        var treeObj = $.fn.zTree.getZTreeObj("treeListContainerUL");
        var nodes = treeObj.getSelectedNodes();
        treeObj.editName(nodes[0]);
        treeObj.cancelEditName($('#menuName').val());
    });
    $('#targetFileChangeInput').unbind('blur');
    $('#targetFileChangeInput').blur(function(){
        if($(this).val()!=""){
            if(/^[\w\/\.-]+$/.test($('#targetFileChangeInput').val())){
                treeNode.url=$('#targetFileChangeInput').val();
            }else{
                Confirm.show('提示','输入的Url含有特殊字符，保存信息失败');
                $('#targetFileChangeInput').val('');
                $('#targetFileChangeInput').attr("placeholder","只可输入数字、字母及._/");
            }
        }
        else{
            treeNode.url="";
        }
    });

    $('#descriptTextarea').unbind('blur');
    $('#descriptTextarea').blur(function(){
        treeNode.description=$('#descriptTextarea').val();
    });

    $('.imgs img').unbind('click');
    $('.imgs img').click(function(){
        $('.currentImg').attr('src',$(this).attr('src'));
        $('.imgContainer').css('display','none');
        treeNode.icon=$(this).attr('src');
    });
    if(treeNode.getParentNode()==null){
        $('.imgDiv').css('display','block');
    }else{
        $('.imgDiv').css('display','none');
    }
}
$('.uploadMenuImg').click(function(event){
    $('.imgContainer').css('display','block');
    event.stopPropagation();
})
$('body').click(function(){
    $('.imgContainer').css('display','none');
})
$('.saveMenuChange').click(function(){
    // var treeNodes=$.fn.zTree.getZTreeObj("treeListContainerUL").getNodes();
    // var targetData=toListData(treeNodes);
    var isHaveUrl=true,name='';
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
    if(isHaveUrl==false){
        Confirm.show('提示','名为 “'+name+'” 的菜单路径为空,请填写！');
        return
    }
    // targetData=JSON.stringify(targetData);
    Confirm.show('操作提示',"是否保存所有修改",{
        '保存':{
            'callback':function(){
                $('.saveMenuChange').button('loading');
                $.ajax({
                    type:'put',
                    url: $.mpbGetHeaderPath()+"/menuManagement/updateMenu",
                    data:{
                        "menuListString":targetData
                    },
                    success:function(result){
                        $('.saveMenuChange').button('reset');
                        result=JSON.parse(decodeURIComponent(result));
                        if(result.code=='0'){
                            Confirm.show('保存提示提示',"保存成功");
                        }else{
                            Confirm.show('保存提示',result.message||"保存");
                        }
                    },
                    error :function(result){
                        $('.saveMenuChange').button('reset');
                        Confirm.show('保存失败','请检查网络连接');
                    }
                })
            }
        }
    });
});

function toListData(data){
    var list=[];
    var listlegal=true;
    $(data).each(function(index,item){
        var itemList={}
        if(item.subMenu){
            //menuicon？？？？？？
            itemList.menuIcon=item.icon;
            itemList.title=item.title;
            itemList.description=item.description;
            itemList.url=item.url;
            itemList.isModifiable=item.isModifiable;
            itemList.subMenu=toListData(item.subMenu);
        }else{
            itemList.menuIcon=item.icon;
            itemList.title=item.title;
            itemList.description=item.description;
            itemList.url=item.url;
            itemList.isModifiable=item.isModifiable;
        }

        list.push(itemList);
    })
    return list;
}
// $('.reSetMenuList').click(function(){
//     Confirm.show('操作提示',"是否还原到打开页面时菜单配置",{
//         '还原':{
//             'callback':function(){
//                 //还原的时候还要提交一次
//                 menuTree = $.fn.zTree.init($("#treeListContainerUL"), menuSetting, changeMenuList);
//                 menuTree.expandAll('true');
//                 $('.infoList input').val('');
//             }
//         }
//     });
// })
// $('.moveUp').click(function(){
//     moveUp();
// })
// $('.moveDown').click(function(){
//     moveDown();
// })
$('.deleteMenu').click(function(){
    remove();
})
$('.addRootMenu').click(function(){
    addRoot();
})
$('.addChildMenu').click(function(){
    add();
})
// function moveUp(){
//     var zTree = $.fn.zTree.getZTreeObj("treeListContainerUL"),
//         nodes = zTree.getSelectedNodes(),
//         treeNode = nodes[0];
//     var preNode = treeNode.getPreNode();
//     if(preNode!=null){
//         zTree.moveNode(preNode, treeNode, "prev");
//     }
// }
// function moveDown(){
//     var zTree = $.fn.zTree.getZTreeObj("treeListContainerUL"),
//         nodes = zTree.getSelectedNodes(),
//         treeNode = nodes[0];
//     var nextNode = treeNode.getNextNode();
//     if(nextNode!=null){
//         zTree.moveNode(nextNode, treeNode, "next");
//     }
// }
var newCount = 1;
function add() {
    var zTree = $.fn.zTree.getZTreeObj("treeListContainerUL"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (treeNode) {
        if(treeNode.level=='1'){
            Confirm.show('提示',"菜单层级限制为两层！");
            return;
        }
        treeNode = zTree.addNodes(treeNode, {title:"新菜单项",subMenu:[],description:'',url:'',isModifiable:1,id:(100 + newCount), pId:treeNode.id
            , name:"new node" + (newCount++)});
    } else {
        treeNode = zTree.addNodes(null, {title:"新菜单项",subMenu:[],description:'',url:'',isModifiable:1,id:(100 + newCount), pId:0
            , name:"new node" + (newCount++)});
    }
    if (treeNode) {
    } else {
        Confirm.show('',"叶子节点被锁定，无法增加子节点");
    }
};
function addRoot(){
    var zTree = $.fn.zTree.getZTreeObj("treeListContainerUL"),
        nodes = zTree,
        treeNode = nodes[0];
    if (treeNode) {
        treeNode = zTree.addNodes(treeNode, {title:"新菜单项",subMenu:[],description:'',url:null,isModifiable:1,id:(100 + newCount), pId:treeNode.id
            , name:"new node" + (newCount++)});
    } else {
        treeNode = zTree.addNodes(null, {title:"新菜单项",subMenu:[],description:'',url:null,isModifiable:1,id:(100 + newCount), pId:0
            , name:"new node" + (newCount++)});
    }
    if (treeNode) {
    } else {
        Confirm.show('提示',"叶子节点被锁定，无法增加子节点");

    }
}
function remove() {
    var zTree = $.fn.zTree.getZTreeObj("treeListContainerUL"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (nodes.length == 0) {
        Confirm.show('提示',"请选择要删除节点");
        return;
    }
    if(treeNode.isModifiable==0){
        Confirm.show('提示',"不可操作此菜单");
        return
    }
    if(treeNode.isParent){
        Confirm.show('删除提示', "是否删除"+treeNode.title +"和它的所有子菜单？",{
            '确认删除':{
                'callback':function(){
                    zTree.removeNode(treeNode, false);
                }
            }
        });
    }else{
        Confirm.show('删除提示','是否删除'+treeNode.title + "？",{
            '确认删除':{
                'callback':function(){
                    zTree.removeNode(treeNode, false);
                }
            }
        });
    }
};

function getMenuFilePath(){
    $.ajax({
        type:'GET',
        url: $.mpbGetHeaderPath()+"/menuManagement/getMenuFilePath",
//		url:"./json/ml_menuListTreeData.json",
        success:function(result){
            result=JSON.parse(decodeURIComponent(result));
            if(result.code=='0'){
                showMenuFilePath(result.data);
            }else{
                Confirm.show('删除',result.message||"删除失败");
            }
        },
        error :function(result){
            Confirm.show('服务器错误','请检查网络连接');
        }
    })
}

function showMenuFilePath(path){
    $.fn.zTree.init($("#targetFileChangeUl"), fileChangeSetting, path);
}
var fileChangeSetting =
    {
        view: {
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true
            },
            key:{
                children:"children"
                ,name:"name"
                ,title:"title"
                ,url:"menuUrl"
                ,parentId:"parentId"
            }
        },
        callback: {
            beforeClick: beforeFileChangeClick,
            onClick: onClickFileChange
        }
    };

function beforeFileChangeClick(treeId, treeNode) {
    var check = (treeNode && !treeNode.isParent);
    if(treeNode.name.indexOf(".") > 0 ){
    }
    return check;
}

function onClickFileChange(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("targetFileChangeUl"),
        nodes = zTree.getSelectedNodes(),
        v = "";

    var path = "";
    p = treeNode.getParentNode();
    while (p != null){
        path = p.name + "/" + path;
        p = p.getParentNode();
    }

    var cityObj = $("#targetFileChangeInput");
    cityObj.val(path+treeNode.name);

    currentTreeNode.url=path+treeNode.name;
}
function showMenuTree(){
    $("#targetFileChangeInputContent")
        .css({right:"60px", top: "24px",})
        .slideDown("fast");
    $("body").bind("mousedown", onBodyDownChange);
}
function hideMenuFileChange(){
    $("#targetFileChangeInputContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDownChange);
}
function onBodyDownChange(event){
    if (!(event.target.id == "menuBtn" || event.target.id == "targetFileChangeInputContent" || $(event.target).parents("#targetFileChangeInputContent").length>0)) {
        hideMenuFileChange();
    }
}
