var _fileManage={
    data:{
        _isSaveFold:true,
        _fileTypeArr:[],
        _curParentPath:'/',
        _curFolderName:'',
        _preFileDom:''
    },
    init:function () {
        this.onLoad();
        this.bindEvent();
    },
    onLoad:function () {
        this.getFileType();
        //this.getFolders('/');
    },
    bindEvent:function () {
        var _this = this;
        //全选/全不选 checkbox
        $(document).on('click', '#checkAllUsers', function (e) {
            e.stopPropagation();
            var _self = $(this);
            if (_self.is(':checked')) {
                //全选中设置
                $("#files .file-item").each(function (index, item) {
                    if($(item).find('input[type="checkbox"]').length>0){
                        $(item).find('input[type="checkbox"]').prop("checked", "checked");
                        $(item).addClass("file_active");
                    }
                })
            }
            else {
                //全不选设置
                $("#files .file-item").each(function (index, item) {
                    $(item).find('input[type="checkbox"]').prop("checked", false);
                    $(item).removeClass("file_active");
                })
            }
        })
        //有一个未选中则取消全选  /  全都选中则全选选中
        $(document).on('click', '#files .file-item input[type="checkbox"]', function (e) {
            e.stopPropagation();
            var isCheckAll = true;
            if (!$(this).is(':checked')) {
                //有一个没选中的就取消全选
                $("#checkAllUsers").prop("checked", false);
                $(this).parents(".file-item").removeClass("file_active");
            }
            else {
                $(this).parents(".file-item").addClass("file_active");
                $("#files .file-item").each(function (index, item) {
                    if ($(item).find('input[type="checkbox"]').length>0){
                        if (!$(item).find('input[type="checkbox"]').is(':checked')) {
                            isCheckAll = false;
                        }
                    }
                })
                //全都选中则全选选中
                if (isCheckAll) {
                    $("#checkAllUsers").prop("checked", "checked");
                }
            }
        })
        //点击行只选中本行
        $(document).on('click', '#files .file-item', function (e) {
            e.stopPropagation();
            if($(this).attr("data-type")!='folder'){
                if ($(this).find('input[type="checkbox"]').is(':checked')) {
                    $("#files .file-item").each(function (index, item) {
                        $(item).find('input[type="checkbox"]').prop("checked", false);
                        $(item).removeClass("file_active");
                    })
                    $("#checkAllUsers").prop("checked", false);
                    $(this).addClass("file_active");
                    $(this).find('input[type="checkbox"]').prop("checked", "checked");
                }
                else{
                    $("#files .file-item").each(function (index, item) {
                        $(item).find('input[type="checkbox"]').prop("checked", false);
                        $(item).removeClass("file_active");
                    })
                    $("#checkAllUsers").prop("checked", false);
                    $(this).addClass("file_active");
                    $(this).find('input[type="checkbox"]').prop("checked","checked");
                }
            }
        })
        //创建文件夹
        $(document).on('click', '#createFolderBtn', function (e) {
            if($("#files").find(".add-newF-item").length>0){
                $("#addFolderNameTxt").focus();
            }
            else{
                var folderDom=' <div class="file-item add-newF-item" data-parentpath="'+_this.data._curParentPath+'" data-type="folder" >' +
                    '                <div class="th ck-box"></div>' +
                    '                <div class="th name">' +
                    '                    <i class="folder_"></i>' +
                    '                    <span class="fName"><div class="folderNameInput" style="display: inline-block;float: left">' +
                    '<input type="text" id="addFolderNameTxt" class="form-control add-folder-input" value="新建文件夹"/></div>' +
                    ' <span class="fa fa-check fa-check-btn" title="保存" id="createFolder"></span><span title="取消" class="fa fa-close fa-check-btn"' +
                    'style="padding: 5px 6px;margin-left: 0px;" id="cancelCreateF"></span> <div class="checkbox-custom" style="display: inline-block;line-height: 47px;">' +
                    '  <input type="checkbox" class="customCk" id="isForceFlag" name="checkbox"><label style="margin-left: 24px;">当父文件夹不存在，是否强制创建</label></div></span>' +
                    '                    <div class="file-operate" style="width: 48px">' +
                    '                    </div>' +
                    '                </div>' +
                    '                <div class="th modify-name">-</div>' +
                    '                <div class="th size">-</div>' +
                    '                <div class="th modify-time">-</div>' +
                    '            </div>';
                $("#files").prepend(folderDom);
                $("#addFolderNameTxt").focus();
                $("#addFolderNameTxt").select();
            }
        })
        //新建文件夹
        $(document).on('click', '#createFolder', function (e) {
            var _self=$(this);
            var folderName=$.trim($("#addFolderNameTxt").val());
            var nameReg=new RegExp('^[^\\\\\\/:*?？\\"<>|]+$');
            if(!nameReg.test(folderName)){
                $.DialogByZ.Autofade({Content: "文件名不能包含以下字符：<,>,|,*,?,,/"});
                return;
            }
            if(folderName==""){
                $.DialogByZ.Autofade({Content: "文件名不能为空"});
                return;
            }
            else{
                $.ajax({
                    type:"post",
                    url: $.mpbGetHeaderPath()+"/fileManagement/dir/create",
                    data:{
                        parentPath:_this.data._curParentPath,
                        folderName:folderName,
                        force:$("#isForceFlag").prop("checked") ? 1:0
                    },
                    success:function (result) {
                        result=JSON.parse(decodeURIComponent(result));
                        if(result.code=='0'){
                            console.log(result)
                            _self.parents(".file-item").removeClass("add-newF-item");
                            _self.parents(".file-item").find(".file-operate").html('<span class="fa fa-trash-o itemFileDelete" title="删除"></span>');
                            _self.parents(".file-item").find(".modify-name").text(localStorage.getItem("nickname"));
                            _self.parents(".file-item").find(".modify-time").text(_this.timeFormatting(new Date()));
                            _self.parents(".file-item").find(".fName").text(folderName);
                        }
                        else{
                            $.DialogByZ.Autofade({Content: result.message || "创建失败！"});
                        }
                    },
                    error:function (msg) {
                        console.log(msg)
                    }
                })
            }
        })
        //取消创建文件夹
        $(document).on('click', '#cancelCreateF', function (e) {
            e.stopPropagation();
            $(this).parents(".file-item").remove();
        })
        //关闭弹窗
        $(document).on('click', '.closePreviewModal', function (e) {
            e.stopPropagation();
            $("#mainContentIframe").empty()
        })

        //双击获取文件及文件夹
        $(document).on('dblclick', '#files .file-item', function (e) {
            e.stopPropagation();
            var fileType=$(this).attr("data-type");
            if(fileType=="folder"){
                if($("#files").find(".add-newF-item").length<=0){
                    var parentPath=$(this).attr("data-parentpath");
                    var folderName=$(this).find(".fName").text();
                    _this.data._curParentPath=parentPath+folderName+'/';
                    _this.getFolders(parentPath+folderName+'/');
                    $("#crumbsDir li").append('<a class="a-unlink" title="'+parentPath+folderName+'/'+'">'+folderName+'</a>');
                    $("#crumbsDir li a").eq(-2).attr("class","a-link");
                    $("#crumbsDir li a").eq(-2).after('<span style="color: #c5d8f3;"> > </span> ');
                    if($("#crumbsDir li").find(".backup").length<=0){
                        $("#crumbsDir li").prepend('<a class="backup" id="backUpCrumb">返回上一级 </a><span  style="color: #c5d8f3;"> | </span>')
                    }
                }
            }
        })
        //单击文件名获取文件及文件夹
        $(document).on('click', '.file-item .fName', function (e) {
            e.stopPropagation();
            var fileType=$(this).parents('.file-item').attr("data-type");
            if(fileType=="folder") {
                if ($("#files").find(".add-newF-item").length <= 0) {
                    var parentPath = $(this).parents('.file-item').attr("data-parentpath");
                    var folderName = $(this).text();
                    _this.data._curParentPath = parentPath + folderName + '/';
                    _this.getFolders(parentPath + folderName + '/');
                    $("#crumbsDir li").append('<a class="a-unlink" title="' + parentPath + folderName + '/' + '">' + folderName + '</a>');
                    $("#crumbsDir li a").eq(-2).attr("class", "a-link");
                    $("#crumbsDir li a").eq(-2).after('<span style="color: #c5d8f3;"> > </span> ');
                    if ($("#crumbsDir li").find(".backup").length <= 0) {
                        $("#crumbsDir li").prepend('<a class="backup" id="backUpCrumb">返回上一级 </a><span  style="color: #c5d8f3;"> | </span>')
                    }
                }
            }
        })
        $(document).on('click', '#crumbsDir li .a-link', function (e) {
            var parentPath=$(this).attr("title");
            _this.getFolders(parentPath);
            $(this).nextAll().remove();
            $(this).attr("class","a-unlink");
            if($("#crumbsDir li").find(".backup").length>0){
                if($("#crumbsDir li").find("a").length==2){
                    $("#crumbsDir li").find(".backup").next('span').remove();
                    $("#crumbsDir li").find(".backup").remove();
                }
            }
        })
        $(document).on('click', '#crumbsDir li .backup', function (e) {
            var parentPath=$("#crumbsDir li a").eq(-2).attr("title");
            _this.getFolders(parentPath);
            $("#crumbsDir li a").eq(-2).nextAll().remove();
            $("#crumbsDir li a").eq(-1).attr("class","a-unlink");
            if($("#crumbsDir li").find(".backup").length>0){
                if($("#crumbsDir li").find("a").length==2){
                    $("#crumbsDir li").find(".backup").next('span').remove();
                    $("#crumbsDir li").find(".backup").remove();
                }
            }
        })
        //删除文件、文件夹
        $(document).on('click', '.itemFileDelete', function (e) {
            e.stopPropagation();
            var _self=$(this);
            var type=_self.parents(".file-item").attr("data-type");
            var parentPath=_self.parents(".file-item").attr("data-parentpath");
            var folderName=_self.parents(".file-item").find(".fName").text();
            if(type=='folder'){
                //文件夹删除
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除该文件夹及子目录吗?",
                    FunL: function () {
                        $.ajax({
                            type:"post",
                            url: $.mpbGetHeaderPath()+"/fileManagement/dir/delete",
                            data:{
                                parentPath:parentPath,
                                folderName:folderName
                            },
                            success:function (result) {
                                result=JSON.parse(decodeURIComponent(result));
                                if(result.code=='0'){
                                    console.log(result)
                                    _self.parents(".file-item").remove();
                                    $.DialogByZ.Autofade({Content: "删除成功！"});
                                }
                                else{
                                    $.DialogByZ.Autofade({Content: result.message  || "删除失败！"});
                                }
                            },
                            error:function (msg) {
                                console.log(msg)
                            }
                        })
                        $.DialogByZ.Close();
                    },
                    FunR: function () {
                        $.DialogByZ.Close();
                    }
                })
            }
            else {
                //文件删除
                var fileId=_self.parents(".file-item").attr("data-fileid");
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除该文件吗?",
                    FunL: function () {
                        $.ajax({
                            type:"post",
                            url: $.mpbGetHeaderPath()+"/fileManagement/file/delete",
                            data:{
                                fileIds:fileId
                            },
                            success:function (result) {
                                result=JSON.parse(decodeURIComponent(result));
                                if(result.code=='0'){
                                    console.log(result)
                                    _self.parents(".file-item").remove();
                                    $.DialogByZ.Autofade({Content: "删除成功！"});
                                }
                                else{
                                    $.DialogByZ.Autofade({Content: result.message || "删除失败！"});
                                }
                            },
                            error:function (msg) {
                                console.log(msg)
                            }
                        })
                        $.DialogByZ.Close();
                    },
                    FunR: function () {
                        $.DialogByZ.Close();
                    }
                })
            }
        })
        //批量删除文件
        $(document).on('click', '#deleteFilesBtn', function (e) {
            var _self=$(this);
            var idArr=[];
            $("#files .file-item").each(function(index,item){
                if($(item).find('input[type="checkbox"]').length>0){
                    if($(item).find('input[type="checkbox"]').is(':checked')){
                        var fileId = $(item).attr('data-fileid');
                        idArr.push(fileId);
                    }
                }
            })
            if(idArr.length>0){
                var idArrStr=idArr.join(",");
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除所选文件吗?",
                    FunL: function () {
                        $.ajax({
                            type:"post",
                            url: $.mpbGetHeaderPath()+"/fileManagement/file/delete",
                            data:{
                                fileIds:idArrStr
                            },
                            success:function (result) {
                                result=JSON.parse(decodeURIComponent(result));
                                if(result.code=='0'){
                                    $("#files .file-item").each(function(index,item){
                                        if($(item).find('input[type="checkbox"]').length>0){
                                            if($(item).find('input[type="checkbox"]').is(':checked')){
                                                $(item).remove();
                                            }
                                        }
                                    })

                                    $.DialogByZ.Autofade({Content: "删除成功！"});
                                }
                                else{
                                    $.DialogByZ.Autofade({Content:result.message ||  "删除失败！"});
                                }
                            },
                            error:function (msg) {
                                console.log(msg)
                            }
                        })
                        $.DialogByZ.Close();
                    },
                    FunR: function () {
                        $.DialogByZ.Close();
                    }
                })
            }
            else{
                $.DialogByZ.Autofade({Content: "请选择要删除的文件！"});
            }
        })
        //文件重命名
        $(document).on('click', '.fileRename', function (e) {
            e.stopPropagation();
            _this.data._preFileDom=($(this).parents(".file-item").html());
            if($("#files").find(".add-newF-item").length>0){
                $("#renameFileNameTxt").focus();
            }
            else{
                $(this).parents(".file-item").addClass('add-newF-item');
                var fileName=$(this).parents(".file-item").find(".fName").text();
                var renameStr='<div class="fileRenameInput" style="display: inline-block;float: left">' +
                    '                    <input type="text" id="renameFileNameTxt" class="form-control add-folder-input" value="'+fileName+'"/></div> '+
                    '                     <span class="fa fa-check fa-check-btn" title="保存" id="renameFileName"></span><span title="取消" class="fa fa-close fa-check-btn"' +
                    '                    style="padding: 5px 6px;margin-left: 0px;" id="cancelCreateFile"></span>';
                $(this).parents(".file-item").find(".fName").html(renameStr);
            }
        })
        //取消创建文件夹
        $(document).on('click', '#cancelCreateFile', function (e) {
            e.stopPropagation();
            $(this).parents(".file-item").removeClass('add-newF-item');
            $(this).parents(".file-item").html(_this.data._preFileDom);
        })
        //文件搜索回车键
        $(document).on('keyup', '#searchFilesInput', function (e) {
            e.stopPropagation();
            if (e && e.keyCode == 13) {
                var content=$.trim($("#searchFilesInput").val());
                if (content != "") {
                    var contentReg = new RegExp('^[^\\\\\\/:*?？\\！~·、，。‘；】【}{.@#￥%^……&"<>|]+$');
                    if (!contentReg.test(content)) {
                        $.DialogByZ.Autofade({Content: "搜索内容不能含有特殊字符！"});
                        $("#searchFilesInput").val("");
                        return;
                    }
                }
                _this.getFiles();
                $("#crumbsDir li").html('<a class="a-unlink" title="/">全部文件</a>');

            }
        })
        //文件搜索回车键(设备台账)
        $(document).on('keyup', '#searchFilesInput2', function (e) {
            e.stopPropagation();
            if (e && e.keyCode == 13) {
                var content=$.trim($("#searchFilesInput2").val());
                if (content != "") {
                    var contentReg = new RegExp('^[^\\\\\\/:*?？\\！~·、，。‘；】【}{.@#￥%^……&"<>|]+$');
                    if (!contentReg.test(content)) {
                        $.DialogByZ.Autofade({Content: "搜索内容不能含有特殊字符！"});
                        $("#searchFilesInput2").val("");
                        return;
                    }
                }
                _this.getFilesByLedger();
                $("#crumbsDir li").html('<a class="a-unlink" title="/">全部文件</a>');

            }
        })
        //文件搜索
        $(document).on('click', '#searchFiles', function (e) {
            e.stopPropagation();
            var content=$.trim($("#searchFilesInput").val());
            if (content != "") {
                var contentReg=new RegExp('^[^\\\\\\/:*?？\\！~·、，。‘；】【}{.@#￥%^……&"<>|]+$');
                if(!contentReg.test(content)){
                    $.DialogByZ.Autofade({Content: "搜索内容不能含有特殊字符！"});
                    $("#searchFilesInput").val("");
                    return;
                }
            }
            _this.getFiles();
            $("#crumbsDir li").html('<a class="a-unlink" title="/">全部文件</a>');
        })
        //文件搜索(设备台账)
        $(document).on('click', '#searchFiles2', function (e) {
            e.stopPropagation();
            var content=$.trim($("#searchFilesInput2").val());
            if (content != "") {
                var contentReg=new RegExp('^[^\\\\\\/:*?？\\！~·、，。‘；】【}{.@#￥%^……&"<>|]+$');
                if(!contentReg.test(content)){
                    $.DialogByZ.Autofade({Content: "搜索内容不能含有特殊字符！"});
                    $("#searchFilesInput2").val("");
                    return;
                }
            }
            _this.getFilesByLedger();
            $("#crumbsDir li").html('<a class="a-unlink" title="/">全部文件</a>');
        })
        //重命名文件保存
        $(document).on('click', '#renameFileName', function (e) {
            var _self=$(this);
            var fileName=$.trim($("#renameFileNameTxt").val());
            var fileId=_self.parents(".file-item").attr("data-fileid");
            var nameReg=new RegExp('^[^\\\\\\/:*?\\"<>|]+$');
            if(!nameReg.test(fileName)){
                $.DialogByZ.Autofade({Content: '文件夹名称不能包含以下字符：\ ,/, :, *, ?, " ,<, >, |'});
                return;
            }
            if(fileName==""){
                $.DialogByZ.Autofade({Content: "文件夹名称不能为空"});
                return;
            }
            else{
                var point=1+fileName.lastIndexOf(".");
                var fileType = fileName.substr(point);
                console.log(fileType);
                $.ajax({
                    type:"post",
                    url: $.mpbGetHeaderPath()+"/fileManagement/file/rename",
                    data:{
                        fileId:fileId,
                        newFileName:fileName
                    },
                    success:function (result) {
                        result=JSON.parse(decodeURIComponent(result));
                        if(result.code=='0'){
                            console.log(result)
                            _self.parents(".file-item").attr("data-type",fileType);
                            _self.parents(".file-item").removeClass("add-newF-item");
                            _self.parents(".file-item").find(".name").find('i').attr("class",_this.returnFileType(fileType));
                            _self.parents(".file-item").find(".fName").text(fileName);
                        }
                        else{
                            $.DialogByZ.Autofade({Content: result.message || "创建失败！"});
                        }
                    },
                    error:function (msg) {
                        console.log(msg)
                    }
                })
            }
        })
        //文件下载
        $(document).on('click', '.fileDownload', function (e) {
            e.stopPropagation();
            var fileId = $(this).parents(".file-item").attr('data-fileid');
            window.open($.mpbGetHeaderPath()+"/fileManagement/file/download/"+fileId);
        })
        //文件预览
        $(document).on('click', '.filePreview', function (e) {
            e.stopPropagation();
            var fileId=$(this).parents(".file-item").attr("data-fileid");
            var fileType=$(this).parents(".file-item").attr("data-type");
            var mainContent = document.getElementById('mainContentIframe');
            mainContent.src = $.mpbGetHeaderPath()+'/framework/module/fileService/previewPage.html?fileId='+fileId+'&type='+fileType;//preview/file/'+fileId+'/'+fileType;            mainContent.style.display = "none"; //隐藏
            $("#filePreviewModal").modal("show");
        })
        //点击路径跳转
        $(document).on('click', '.file-item .parent-path', function (e) {
            e.stopPropagation();
            var path = $(this).text();
            var pathArr=path.split('/');
            var pathAll="";
            for(var i=0;i<pathArr.length-1;i++){
                if(i==0){
                    pathAll+='/';
                    $("#crumbsDir li").html('<a class="a-link" title="/">全部文件 </a><span style="color: #c5d8f3;"> > </span> ');
                }
                else if(i==pathArr.length-2){
                    $("#crumbsDir li").append('<a class="a-unlink" >' + pathArr[i] + '</a>');
                }else{
                    pathAll+=pathArr[i]+"/";
                    $("#crumbsDir li").append('<a class="a-link" title="'+pathAll+'">'+pathArr[i]+'</a><span style="color: #c5d8f3;"> > </span>');
                }
            }
            _this.getFolders(path);
        })
    },
    //获取文件夹
    getFolders:function (parentPath,folderName) {
        var _this=this;
        $("#files").empty();
        $.ajax({
            type:"get",
            url: $.mpbGetHeaderPath()+"/fileManagement/folders",
            data:{
                parentPath:parentPath,
                folderName:folderName
            },
            success:function (result) {
                result=JSON.parse(decodeURIComponent(result));
                if(result.code=='0'){
                    console.log(result)
                    var folders=result.data;
                    var len=folders.length;
                    if(len>0){
                        var folderStr='',creatorNameStr='',createTimeStr='';
                        for(var i=0;i<len;i++){
                            typeof (folders[i].creatorName)== "undefined" ? creatorNameStr="-" : creatorNameStr=folders[i].creatorName;
                            typeof (folders[i].createTime)== "undefined" ? createTimeStr="-" : createTimeStr=folders[i].createTime;
                            folderStr+=' <div data-parentpath="'+folders[i].parentPath+'" data-type="folder" class="file-item ">' +
                                '                <div class="th ck-box"></div>' +
                                '                <div class="th name">' +
                                '                    <i class="folder_"></i>' +
                                '                    <span class="fName cursor-folder">'+folders[i].fileName+'</span>' +
                                '                    <div class="file-operate" style="width: 48px">' +
                                '                        <span class="fa fa-trash-o itemFileDelete" title="删除"></span>' +
                                '                    </div>' +
                                '                </div>' +
                                '                <div class="th modify-name">'+creatorNameStr+'</div>' +
                                '                <div class="th size">-</div>' +
                                '                <div class="th modify-time">'+createTimeStr+'</div>' +
                                '                <div class="th parent-path">'+folders[i].parentPath+'</div>' +
                                '            </div>'
                        }
                        $("#files").append(folderStr);
                    }
                    _this.getFiles(parentPath,folderName);
                }
                else{
                    $.DialogByZ.Autofade({Content: result.message || "获取失败！"});
                }
            },
            error:function (msg) {
                console.log(msg)
            }
        })
    },
    //设备台账获取文件
    getFilesByLedger:function () {
        var _this=this;
        var deviceId = $("#deviceId").val();
        var fileName = $.trim($("#searchFilesInput2").val());
        $.ajax({
            type:"get",
            url: $.mpbGetHeaderPath()+"/deviceManagement/baseEquipment/getFileInfoForDevice",
            data:{
                parentPath:"/device/device"+deviceId+"/",
                fileName:fileName
            },
            success:function (result) {
                result=JSON.parse(decodeURIComponent(result));
                if(result.code=='0'){
                    $("#files").empty();
                    var fileList=result.data;
                    var len=fileList.length;
                    if(len>0){
                        var filesStr='',creatorNameStr='',createTimeStr='';
                        for(var i=0;i<len;i++){
                            typeof (fileList[i].creatorName)== "undefined" ? creatorNameStr="-" : creatorNameStr=fileList[i].creatorName;
                            typeof (fileList[i].createTime)== "undefined" ? createTimeStr="-" : createTimeStr=fileList[i].createTime;
                            var classNameStr=_this.returnFileType(fileList[i].extentionName);
                            var preDomStr='',nameClassCursor='';
                            if(_this.data._fileTypeArr.indexOf(fileList[i].extentionName)>-1){
                                preDomStr=' <span class="fa fa-eye filePreview" title="预览"></span>';
                                nameClassCursor="preview-name";
                            }
                            filesStr+='<div data-parentpath="'+fileList[i].parentPath+'" data-type="'+fileList[i].extentionName+'" data-fileid="'+fileList[i].fileId+'"  class="file-item ">' +
                                '                <div class="th name">' +
                                '                    <i class="'+classNameStr+'"></i>' +
                                '                    <span class="fName '+nameClassCursor+'">'+fileList[i].name+'</span>' +
                                '                    <div class="file-operate">' +
                                '                        <span class="fa fa-download fileDownload" title="下载"></span>' + preDomStr+
                                '                    </div>' +
                                '                </div>' +
                                '                <div class="th modify-name">'+creatorNameStr+'</div>' +
                                '                <div class="th size">'+fileList[i].fileSizeFormatted+'</div>' +
                                '                <div class="th modify-time">'+createTimeStr+'</div>' +
                                '            </div>';
                        }
                        $("#files").append(filesStr);
                    }
                    if($("#files").find('.file-item').length<=0){
                        var emptyMsg='<img src="../../images/img/emptyfile_intro.png" style="display: block;text-align: center;width:200px;margin: 100px auto 30px">' +
                            '<p style="color: #424e67;margin: 20px 0;font-weight: 200;text-align: center">您还没有上传过文件哦</p>';
                        $("#files").html(emptyMsg);
                    }
                }
                else{
                    $.DialogByZ.Autofade({Content: result.message || "获取失败！"});
                }
            },
            error:function (msg) {
                console.log(msg)
            }
        })
    },
    //获取文件
    getFiles:function () {
        var _this=this;
        var deviceId = $("#deviceId").val();
        var fileName = $.trim($("#searchFilesInput").val());
        $.ajax({
            type:"get",
            url: $.mpbGetHeaderPath()+"/deviceManagement/baseEquipment/getFileInfoForDevice",
            data:{
                parentPath:"/device/device"+deviceId+"/",
                fileName:fileName
            },
            success:function (result) {
                result=JSON.parse(decodeURIComponent(result));
                if(result.code=='0'){
                    $("#files").empty();
                    var fileList=result.data;
                    var len=fileList.length;
                    if(len>0){
                        var filesStr='',creatorNameStr='',createTimeStr='';
                        for(var i=0;i<len;i++){
                            typeof (fileList[i].creatorName)== "undefined" ? creatorNameStr="-" : creatorNameStr=fileList[i].creatorName;
                            typeof (fileList[i].createTime)== "undefined" ? createTimeStr="-" : createTimeStr=fileList[i].createTime;
                            var classNameStr=_this.returnFileType(fileList[i].extentionName);
                            var preDomStr='',nameClassCursor='';
                            if(_this.data._fileTypeArr.indexOf(fileList[i].extentionName)>-1){
                                preDomStr=' <span class="fa fa-eye filePreview" title="预览"></span>';
                                nameClassCursor="preview-name";
                            }
                            filesStr+='<div data-parentpath="'+fileList[i].parentPath+'" data-type="'+fileList[i].extentionName+'" data-fileid="'+fileList[i].fileId+'"  class="file-item ">' +
                                '                <div class="th ck-box"> <div class="checkbox-custom">' +
                                '                        <input type="checkbox" class="customCk" name="checkbox"><label></label></div></div>' +
                                '                <div class="th name">' +
                                '                    <i class="'+classNameStr+'"></i>' +
                                '                    <span class="fName '+nameClassCursor+'">'+fileList[i].name+'</span>' +
                                '                    <div class="file-operate">' +
                                '                        <span class="fa fa-download fileDownload" title="下载"></span>' +
                                '                        <span class="fa fa-edit fileRename" title="重命名"></span>'+preDomStr+
                                '<span class="fa fa-trash-o itemFileDelete" title="删除"></span> ' +
                                '                    </div>' +
                                '                </div>' +
                                '                <div class="th modify-name">'+creatorNameStr+'</div>' +
                                '                <div class="th size">'+fileList[i].fileSizeFormatted+'</div>' +
                                '                <div class="th modify-time">'+createTimeStr+'</div>' +
                                '            </div>';
                        }
                        $("#files").append(filesStr);
                    }
                    if($("#files").find('.file-item').length<=0){
                        var emptyMsg='<img src="../../images/img/emptyfile_intro.png" style="display: block;text-align: center;width:200px;margin: 100px auto 30px">' +
                            '<p style="color: #424e67;margin: 20px 0;font-weight: 200;text-align: center">您还没有上传过文件哦</p>';
                        $("#files").html(emptyMsg);
                    }
                }
                else{
                    $.DialogByZ.Autofade({Content: result.message || "获取失败！"});
                }
            },
            error:function (msg) {
                console.log(msg)
            }
        })
    },
    getFileType:function () {
        var _this=this;
        $.ajax({
            type:"get",
            url: $.mpbGetHeaderPath()+"/preview/type",
            success:function (result) {
                result=JSON.parse(decodeURIComponent(result));
                if(result.code=='0'){
                    console.log(result);
                    _this.data._fileTypeArr=result.data.types;
                }else{
                    $.DialogByZ.Autofade({Content: result.message || "获取失败！"});
                }
            },
            error:function (msg) {
                console.log(msg)
            }
        })
    },
    //时间格式化
    timeFormatting:function () {
        let timer = new Date();
        let year = timer.getFullYear();
        let month = timer.getMonth() + 1;
        let date = timer.getDate();
        let hour = timer.getHours();
        let min = timer.getMinutes();
        let sec = timer.getSeconds();
        month = month< 10 ? ('0' + month) : month;
        date = date< 10 ? ('0' + date) : date;
        hour = hour < 10 ? ('0' + hour) : hour;
        min = min < 10 ? ('0' + min) : min;
        sec = sec < 10 ? ('0' + sec) : sec;
        let time = year + "-" + month + "-" + date + "  " + hour + ":" + min + ":" + sec;
        return time;
    },
    returnFileType:function (type) {
        var classNameStr='';
        switch(type){
            case "png" :
            case "jpg" :
            case "gif" :
            case "jpeg" :
            case "bmp" :
            case "pcx" :
            case "tiff" :
                classNameStr="img_";
                break;
            case "docx":
            case "doc":
                classNameStr="doc_";
                break;
            case "xls":
                classNameStr="xls_";
                break;
            case "pdf":
                classNameStr="pdf_";
                break;
            case "pptx":
            case "ppt":
                classNameStr="ppt_";
                break;
            case "txt":
            case "css":
            case "xml":
                classNameStr="txt_";
                break;
            case "psd":
                classNameStr="psd_";
                break;
            case "fla":
                classNameStr="flash_";
                break;
            case "rar":
            case "zip":
                classNameStr="rarzip_";
                break;
            case "html":
            case "aspx":
            case "php":
            case "jsp":
            case "java":
                classNameStr="html_";
                break;
            case "torrent":
                classNameStr="torrent_";
                break;
            case "avi":
            case "wmv":
            case "mp4":
            case "mov":
            case "mkv":
            case "flv":
            case "f4v":
            case "m4v":
            case "rmvb":
            case "rm":
            case "mpeg":
            case "3gp":
            case "dat":
            case "ts":
            case "mts":
            case "vob":
                classNameStr="video_";
                break;
            case "CD":
            case "wav":
            case "mp3":
            case "aiff":
            case "wma":
            case "aac":
            case "amr":
                classNameStr="audio_";
                break;
            default:
                classNameStr="default_";
        }
        return classNameStr;
    }
}
_fileManage.init();