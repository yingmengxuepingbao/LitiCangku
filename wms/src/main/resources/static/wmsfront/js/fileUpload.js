// $(function(){
//

    var _maxTotalSize = 20;//单次上传总文件大小限制，单位为兆M
    var _maxSingleSize = 4;//单次上传单个文件大小限制，单位为兆M
    var _maxNum = 5;//每个属性最大上传数量

    var _fileUpload_service={
        //上传图片
        fileUpload : function(filId, resolve, reject){
            $.ajaxFileUpload({
                type: "post",
                url: "/IEAM/fileUploadManagement/uploadFile",
                fileElementId:filId,//文件选择框的id属性
                dataType: 'json',//服务器返回的格式,ajaxFileUpload声明json有问题
                async : false,
                success: resolve,
                error: reject
            });
        },

        //删除图片
        deleteFileUploadInfoByID : function(id, filePath, resolve, reject){
            $.ajax({
                type:"post",
                url	: "/IEAM/fileUploadManagement/deleteFileUploadInfoByID",
                data: {
                    id : id,
                    filePath : filePath
                },
                success: resolve,
                error: reject
            });
        },

        //根据ids获取图片列表
        getFilesByIDs : function(ids, resolve, reject){
            $.ajax({
                type:"get",
                url	: "/IEAM/fileUploadManagement/getFilesByIDs",
                data: {
                    ids : ids
                },
                success: resolve,
                error: reject
            });
        }
    }

    var _fileUpload= {
        init: function () {
            this.bindEvent();
        },
        bindEvent: function () {

            //点击添加按钮
            $(document).on('click', '.imgClick', function () {

                var _files = $(".imgFiles");

                _files.click();
                _files.off().on('change',function(){
                    _fileUpload.fileUploadProcess(_maxNum);//选择图片后自动上传
                });
            });

            //删除文件
            $(document).on('click', '.delImg', function () {
                var _self=$(this);
                var fileId = _self.parent().data('id');//文件存储id，用于删除数据库信息
                var filePath = _self.data('id');//文件存储路径，用于删除物理文件
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除该文件吗？",
                    FunL:function(){
                        _fileUpload_service.deleteFileUploadInfoByID(fileId, filePath, function (res) {
                            var result=JSON.parse(decodeURIComponent(res));
                            if(result.code==0) {

                                _fileUpload.fileDelSource(fileId,_self);
                                $.DialogByZ.Autofade({Content: "删除成功！"});
                            }else {
                                $.DialogByZ.Autofade({Content: result.message || "删除失败！"});
                            }
                        }, function (err) {
                            _commonFun.errorTips('');
                        })
                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            });

            //点击图片，缩放文件
            $(document).on('click', '.imsg', function () {
                var _this = $(this);//将当前的pimg元素作为_this传入函数
                _fileUpload.showImg("#outerdiv", "#innerdiv", "#bigimg", _this);
            });
        },

        //上传文件，传入参数为单次最大上传数量，在js文件顶部单独定义
        fileUploadProcess:function (MAX) {
            var files = $("#myFile")[0].files;

            if(files.length==0){
                $.DialogByZ.Autofade({Content: "请先选择需上传文件"});
                return;
            }
            var _filesTotalSize = 0;
            var _fileSize;
            for(var i=0,file;file=files[i++];){
               if(file.size>_maxSingleSize * 1024 * 1024){
                   alert('单个文件大小不能大于'+_maxSingleSize+'M');
                   return false;
               }
                _filesTotalSize+=file.size;
            }

            if(_filesTotalSize>=_maxTotalSize * 1024 * 1024){
                alert('单次上传文件大小不能大于'+_maxTotalSize+'M');
                return false;
            }
            if(files.length > MAX ){
                alert('上传数量不能大于'+MAX+'张');
                return false;
            };
            var lens = $(".imgAll").find('li').length+files.length ;
            if(lens > MAX ){
                alert('总数不能大于'+MAX+'张');
                return false;
            };

            _fileUpload_service.fileUpload("myFile", function (res) {
      //          var result=JSON.parse(decodeURIComponent(res.replace(/<.*?>/ig,"").replace(/\"/g,"")));
                var result=JSON.parse(decodeURIComponent(res));
                if(result.code==0) {
                    var data=result.data.fileUploads;
                    _fileUpload.fileUploadsSource(data);//上传成功，预览图片
                    $.DialogByZ.Autofade({Content: "上传成功！"});
                }else {
                    $.DialogByZ.Autofade({Content:result.message || "上传失败！"});
                }
            }, function (err) {
                _commonFun.errorTips('');
            })
        },

        //上传成功后，添加图片预览
        fileUploadsSource : function(list) {
            // var pathName = document.location.pathname;
            // var index = pathName.substr(1).indexOf("/");
            // var result = pathName.substr(0,index+1);//项目相对路径 /eam
            var _idArr = [];
            for(ele in list){

                this.dom = document.createElement('li');
                this.dom.innerHTML =
                    '    <img  alt="" src="'+list[ele].file_path_server +'" class="imsg">'+
                    '    <i class="delImg" data-id="'+list[ele].file_path_real+'">'+
                    '        X'+
                    '    </i>'
                $(this.dom).attr({'data-id':list[ele].id,'data-delName':this.imgName});
                $(".imgAll").find("ul").append(this.dom);
                _idArr.push(list[ele].id);
            }
            //将存入数据库的信息赋值给页面指定元素
            var _ids = _idArr.join(",");
            var _idSpace = $(".box").find("#fileIds");
            var _originalIds = _idSpace.val();
            if(_originalIds==null || _originalIds=="" || _originalIds == undefined){
                _idSpace.val(_ids);
            }else{
                _idSpace.val(_originalIds+","+_ids);
            }
        },

        //删除成功后，删除图片预览，移除图片ID
        fileDelSource : function(id,obj) {

            var _idSpace = $(".box").find("#fileIds");
            var _originalIds = _idSpace.val();
            var _idArr;
            //没有逗号，说明只有一张图片的id，直接赋值“”
            if(_originalIds.indexOf(",")<0){
                _idSpace.val("");
                obj.parent().remove();
            }else{
                _idArr = _originalIds.split(",");
               for (var i = 0; i < _idArr.length; i++) {
                    if (_idArr[i] == id){
                        _idArr.splice(i, 1);
                    }
                }
                /*_idArr.splice($.inArray(id, _idArr), 1);*/
                _idSpace.val(_idArr.join(","));
                obj.parent().remove();
            }
        },

        //供别处调用，根据传入的ids获取数据库存储的文件信息及物理文件信息
        showFilesByIDs :function(ids) {
            _fileUpload_service.getFilesByIDs(ids, function (res) {
                var result=JSON.parse(decodeURIComponent(res));
                if(result.code==0) {
                    var data=result.data.fileUploads;
                    _fileUpload.fileUploadsSource(data);
                }else {
                    $.DialogByZ.Autofade({Content:result.message || "获取相关文件失败！"});
                }
            }, function (err) {
                _commonFun.errorTips("获取相关文件失败！");
            })
        },

        //点击图片，展示大图
        showImg :function(outerdiv, innerdiv, bigimg, _this){
            var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
            $(bigimg).attr("src", src);//设置#bigimg元素的src属性

            /*获取当前点击图片的真实大小，并显示弹出层及大图*/
            $(bigimg).on("load",function(){
                var windowW = $(window).width();//获取当前窗口宽度
                var windowH = $(window).height();//获取当前窗口高度
                var realWidth = this.width;//获取图片真实宽度
                var realHeight = this.height;//获取图片真实高度
                var imgWidth, imgHeight;
                var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

                if(realHeight>windowH*scale) {//判断图片高度
                    imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
                    imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
                    if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度
                        imgWidth = windowW*scale;//再对宽度进行缩放
                    }
                } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度
                    imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放
                    imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度
                } else {//如果图片真实高度和宽度都符合要求，高宽不变
                    imgWidth = realWidth;
                    imgHeight = realHeight;
                }
                $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放

                var w = (windowW-imgWidth)/2;//计算图片与窗口左边距
                var h = (windowH-imgHeight)/2;//计算图片与窗口上边距
                $(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性
                $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
            });

            $(outerdiv).click(function(){//再次点击淡出消失弹出层
                $(this).fadeOut("fast");
            });
    }
}


    _fileUpload.init();
// })
