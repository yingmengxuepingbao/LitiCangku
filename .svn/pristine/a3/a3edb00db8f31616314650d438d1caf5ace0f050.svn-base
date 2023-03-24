$(function(){
    var _dictionary_service={
        //获取字典列表
        getDictionary: function(keyInfo,resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+'/dictType/list',
                headers:{ 'token':localStorage.getItem('token')},
                data	: keyInfo,
                success	: resolve,
                error	: reject
            });
        },
        //获取字典子表列表
        getItemDictionary: function(keyInfo,resolve, reject){
            $.ajax({
                type	:"post",
                url		: $.mpbGetHeaderPath()+'/dictItem/listItem',
                headers:{ 'token':localStorage.getItem('token')},
                data	: keyInfo,
                success	: resolve,
                error	: reject
            });
        },
        //新增字典
        addDictionary: function(dictionaryInfo, resolve, reject){
            $.ajax({
                type		:"post",
                url			: $.mpbGetHeaderPath()+'/dictType/c',
                headers:{ 'token':localStorage.getItem('token')},
                data		: dictionaryInfo,
                success		: resolve,
                error		: reject
            });

        },
        //新增字典子表
        addItemDictionary: function(dictionaryInfo, resolve, reject){
            $.ajax({
                type		:"post",
                url			: $.mpbGetHeaderPath()+'/dictItem/c',
                headers:{ 'token':localStorage.getItem('token')},
                data		: dictionaryInfo,
                success		: resolve,
                error		: reject
            });

        },
        //检查编码是否可用
        checkCode:function(data,resolve, reject){
            $.ajax({
                type	:"get",
                url		: checkDictionaryCodeUrl,
                data	: data,
                success	: resolve,
                error	: reject
            });
        },
        //根据字典ID获取字典详细信息
        getDictionaryInfo: function(id,resolve, reject){
            $.ajax({
                type		:"get",
                url			: $.mpbGetHeaderPath()+'/dictType/r/'+id,
                headers:{ 'token':localStorage.getItem('token')},
                data		: {
                    id:id
                },
                success		: resolve,
                error		: reject
            });
        },
        //根据字典ID获取字典详细信息(子表)
        getDictionaryItemInfo: function(id,resolve, reject){
            $.ajax({
                type		:"get",
                url			: $.mpbGetHeaderPath()+'/dictItem/r/'+id,
                headers:{ 'token':localStorage.getItem('token')},
                data		: {
                    id:id
                },
                success		: resolve,
                error		: reject
            });
        },
        //编辑字典信息
        editDictionary: function(dictionaryInfo ,resolve, reject){
            $.ajax({
                type		:"post",
                url			: $.mpbGetHeaderPath()+'/dictType/u',
                headers:{ 'token':localStorage.getItem('token')},
                data		: dictionaryInfo ,
                success		: resolve,
                error		: reject
            });
        },
        //编辑字典信息(子表)
        editItemDictionary: function(dictionaryInfo ,resolve, reject){
            $.ajax({
                type		:"post",
                url			: $.mpbGetHeaderPath()+'/dictItem/u',
                headers:{ 'token':localStorage.getItem('token')},
                data		: dictionaryInfo ,
                success		: resolve,
                error		: reject
            });
        },
        //	删除字典deleteItemDictionary
        deleteDictionary: function(dictionaryId,resolve, reject){
            $.ajax({
                type		:"post",
                url			: $.mpbGetHeaderPath()+'/dictType/deleteDictType',
                headers:{ 'token':localStorage.getItem('token')},
                data		: {
                    dictTypeId:dictionaryId
                },
                success		: resolve,
                error		: reject
            });
        },
        //	删除字典子表
        deleteItemDictionary: function(dictionaryId,resolve, reject){
            $.ajax({
                type		:"post",
                url			: $.mpbGetHeaderPath()+'/dictItem/deleteDictItem',
                headers:{ 'token':localStorage.getItem('token')},
                data		: {
                    dictItemId:dictionaryId
                },
                success		: resolve,
                error		: reject
            });
        }
    };
    var  selectSearchTreeNode=null, selectPopTreeNode=null;
    var _person= {
        data: {
            tabNumber : 0,  //面包屑个数
            type : "", //查询类型，默认为空，查询下一级
            parentId : 0,  //默认上级ID为0
            currentNumber   : 0,  //记录当前页有多少条数据
            totalPageNum    : 0,  //记录一共多少页
            numberOnePage   : 10 //一页几条数据
        },
        init: function () {
            this.onLoad();
            this.bindEvent();
        },
        onLoad: function () {
            this.initDatetime();
            this.loadDictionaryList(1);
            $(".fa-personInfoPop").hide();
        },
        bindEvent: function () {
            var _this = this;
            //分页前一页
            $(document).on('click', '.previousPage', function () {
                if(parseInt($('.currentPage').text())==1){
                    return;
                }
                var currentPage=parseInt($('.currentPage').text())-(1);
                if(currentPage<1){
                    currentPage=1;
                }
                $('.currentPage').text(currentPage);
                var parentId  = _this.data.parentId;
                if(parentId == 0) {
                    _this.loadDictionaryList(currentPage);
                }else {
                    _this.loadDictionaryItemList(currentPage);
                }
            });
            //分页下一页
            $(document).on('click', '.nextPage', function () {
                if(parseInt($('.currentPage').text())==_this.data.totalPageNum){
                    $('.currentPage').text(_this.data.totalPageNum);
                    return;
                }
                var currentPage= parseInt($('.currentPage').text())-(-1);

                if(currentPage>_this.data.totalPageNum){
                    currentPage=_this.data.totalPageNum;
                }
                $('.currentPage').text(currentPage);
                console.log("_this.data",_this.data)
                var parentId  = _this.data.parentId;
                if(parentId == 0) {
                    _this.loadDictionaryList(currentPage);
                }else {
                    _this.loadDictionaryItemList(currentPage);
                }
            });
            //分页跳转
            $(document).on('click', '#pageJump', function () {
                var currentPage = $('.currentPageBtn').val();
                if(currentPage>_this.data.totalPageNum){
                    currentPage=_this.data.totalPageNum;
                }
                if(currentPage<1){
                    currentPage=1;
                }
                $('.currentPage').text(currentPage);
                var parentId  = _this.data.parentId;
                if(parentId == 0) {
                    _this.loadDictionaryList(currentPage);
                }else {
                    _this.loadDictionaryItemList(currentPage);
                }
            });
            $(document).on('click' ,'.personw , .pop-body',function (e) {
                e.stopPropagation();
                $(".departPopTree").slideUp();
                $("#belongDepartmentSelet").removeAttr('style');
            })

            //点击搜索
            $(document).on('click', '#dictionaryInfoS', function () {

                console.log("sous",_this);
                $(".currentPage").text("1");
                var parentId  = _this.data.parentId;
                if(parentId == 0) {
                    _this.loadDictionaryList(1);
                }else {
                    _this.loadDictionaryItemList(1);
                }
            });
            //点击查询下级信息
            $(document).on('click', '.children-show', function () {
                var _self = $(this);
                var dictionaryId = _self.parent().parent().data('dictionaryid');
                var dictionaryName = _self.parent().parent().find('.dictionaryName').text();
                var dictionaryCode = _self.parent().parent().find('.dictionaryCode').text();
                var number = ++_this.data.tabNumber;   //面包屑个数加1
                $(".breadcrumb").append("<li><a id='"+number+"' href='#' class='tabLink' data-id='"+dictionaryId+"'>"+dictionaryName+"</a></li>");
                _this.data.type = "";
                _this.data.parentId = dictionaryCode;  //保存当前数据的ID，作为上级ID
                $("#dictionaryName").val('');
                $("#dictionaryCode").val('');
                _this.loadDictionaryItemList(1); //默认加载第一页
            });
            //点击面包屑查询信息
            $(document).on('click', '.tabLink', function () {
                var number = _this.data.tabNumber;
                _this.data.type = "";  //查询类型为：查看下一级
                _this.data.parentId = $(this).attr("data-id");
                var id = $(this).attr("id");  //获取点击的是第几个面包屑，默认第一个为0
                for(var i = number;i>id;i--) {
                    $("ol li:eq("+i+")").remove();  //从最后一个面包屑开始依次移除
                    _this.data.tabNumber--;  //面包屑个数减1
                }
                _this.loadDictionaryList(1);
            });
            //点击返回上级信息
            $(document).on('click', '#returnBtn', function () {
                $("ol li:eq("+_this.data.tabNumber+")").remove();  //移除最后一个面包屑
                _this.data.tabNumber--;  //面包屑个数减1
                _this.data.type = "return";  //查询类型为：返回上一级
                //清空搜索框内容
                $("#dictionaryName").val('');
                $("#dictionaryCode").val('');
                _this.loadDictionaryList(1);
            });
            //点击添加字典按钮
            $(document).on('click', '#addDictionaryBtn', function () {
                $(".cusPopover-title span").text("新增字典信息");
                $("#dictionaryNamePop").val('');
                $("#dictionaryCodePop").val('');
                $("#sequenceNumberPop").val('');
                $("#dictionaryOrdyByPop").val('');
                $("#hideDictionaryIdPop").val('');
                $("#dictionaryCodePop").removeAttr("disabled");
                $("#personPopover").modal();
                $(".fa-personInfoPop").hide();
                //判断主子表
                var parentId  = _this.data.parentId;
                if (parentId==0){
                    //主表
                    $("#sequenceNumberPoPHidden").css("display","none")
                } else {
                    //子表
                    $("#sequenceNumberPoPHidden").css("display","")
                }
            });
            //检查编码是否可用
            $(document).on('blur', '#dictionaryCodePop', function () {
                var code = $.trim($("#dictionaryCodePop").val());
                if(code==""){
                    $(this).css("border","1px solid #EF4836");
                    $(".fa-personInfoPop").show();
                    $(".fa-personInfoPop").text("编码不能为空！");
                    return;
                }
                $(this).removeAttr('style');
                $(".fa-personInfoPop").hide();
                var dictionaryId = $.trim($("#hideDictionaryIdPop").val());
                var dictionary={
                    id :dictionaryId,
                    code:code,
                    parentId:_this.data.parentId
                }
                _dictionary_service.checkCode(dictionary,function(res){
                    var result=JSON.parse(decodeURIComponent(res));
                    if(result.code == 0) {
                        if (result.data == true) {
                            $(".fa-personInfoPop").show();
                            $(".fa-personInfoPop").text("编码已存在！");
                            $("#dictionaryCodePop").css("border","1px solid #EF4836");
                            $('#dictionaryCodePop').val("");
                        }
                    }else {
                        $.DialogByZ.Autofade({Content:res.message || "！"});
                    }
                },function(errMsg){
                    _commonFun.errorTips('');
                })
            })
            $(document).on('blur', '#dictionaryNamePop', function (){
                if($.trim($(this).val())==""){
                    $(this).css("border","1px solid #EF4836");
                    $(".fa-personInfoPop").show();
                    $(".fa-personInfoPop").text("名称不能为空！");
                    return;
                }
                $(this).removeAttr('style');
                $(".fa-personInfoPop").hide();
            });
            //点击字典列表中的编辑功能
            $(document).on('click', '#dictionaryList-table .a-dictionary-edit', function (e) {
                e.stopPropagation();
                $(".cusPopover-title span").text("编辑字典信息");
                var _self = $(this);
                $(".fa-personInfoPop").hide();

                //根据字典Id获取字典详情
                var dictionaryId = _self.parent().parent().data('dictionaryid');
                //将字典ID放入隐藏域里
                $("#hideDictionaryIdPop").val(dictionaryId);
                //判断主子表
                var parentId  = _this.data.parentId;
                if(parentId == 0) {
                    //主表id不可修改
                    $("#dictionaryCodePop").attr("disabled","disabled");
                    $("#sequenceNumberPoPHidden").css("display","none")
                    _dictionary_service.getDictionaryInfo(dictionaryId, function (res) {
                        var res=res;
                        if(res.code==0){
                            var data=res.data;
                            $("#dictionaryNamePop").val(data.dicTypeName);
                            $("#dictionaryCodePop").val(data.dicTypeCode);
                            // $("#dictionaryOrdyByPop").val(data.ordyBy);

                            $("#personPopover").modal();
                        }
                    }, function (err) {
                        _commonFun.errorTips('');
                    })
                }else {
                    //子表
                    $("#dictionaryCodePop").removeAttr("disabled");
                    $("#sequenceNumberPoPHidden").css("display","")
                    _dictionary_service.getDictionaryItemInfo(dictionaryId, function (res) {
                        var res=res;
                        if(res.code==0){
                            var data=res.data;
                            $("#dictionaryNamePop").val(data.dicItemName);
                            $("#dictionaryCodePop").val(data.dicItemCode);
                            $("#sequenceNumberPop").val(data.sequenceNumber);
                            // $("#dictionaryOrdyByPop").val(data.ordyBy);

                            $("#personPopover").modal();
                        }
                    }, function (err) {
                        _commonFun.errorTips('');
                    })
                }

            })
            //保存字典详情
            $(document).on('click', '#saveDictionaryBtn', function () {
                var dictionaryCode	=$.trim($("#dictionaryCodePop").val());
                var dictionaryName	=$.trim($("#dictionaryNamePop").val());
                var sequenceNumber	=$.trim($("#sequenceNumberPop").val());
                var parentId    =_this.data.parentId;
                var dictionaryOrdyBy	=$.trim($("#dictionaryOrdyByPop").val());

                if(dictionaryCode==""){
                    $("#dictionaryCodePop").css("border","1px solid #EF4836");
                    $(".fa-personInfoPop").show();
                    $(".fa-personInfoPop").text("编码不能为空！");
                    return;
                }
                $("#dictionaryCodePop").removeAttr('style');
                if(dictionaryName==""){
                    $("#dictionaryNamePop").css("border","1px solid #EF4836");
                    $(".fa-personInfoPop").show();
                    $(".fa-personInfoPop").text("名称不能为空！");
                    return;
                }
                $("#dictionaryNamePop").removeAttr('style');
                // if(dictionaryOrdyBy==""){
                //     $("#dictionaryOrdyByPop").css("border","1px solid #EF4836");
                //     $(".fa-personInfoPop").show();
                //     $(".fa-personInfoPop").text("序号不能为空！");
                //     return;
                // }
                // $("#dictionaryOrdyByPop").removeAttr('style');

                $(".fa-personInfoPop").hide();
                var dictionaryId=$.trim($("#hideDictionaryIdPop").val());
                if(dictionaryId==""){
                    //判断主子表
                    if(parentId == 0){
                        //主表新增
                        //新增 保存字典
                        var data={
                            // id:dictionaryId,
                            dicTypeCode:dictionaryCode,
                            dicTypeName:dictionaryName,
                            parentId:parentId
                        };
                        _dictionary_service.addDictionary(data, function (res) {
                            var res=res;
                            if(res.code==0) {
                                $.DialogByZ.Autofade({Content: "新增字典成功！"});
                                $("#personPopover").modal('hide');
                                //刷新字典列表
                                _this.loadDictionaryList(1);
                            }
                            else {
                                $.DialogByZ.Autofade({Content:res.message || "保存失败！"});
                            }
                        }, function (err) {
                            _commonFun.errorTips('');
                        })
                    }else {
                        //子表新增addItemDictionary
                        var data={
                            // id:dictionaryId,
                            dicItemCode:dictionaryCode,
                            dicItemName:dictionaryName,
                            sequenceNumber:sequenceNumber,
                            dicTypeCode:parentId
                        };
                        _dictionary_service.addItemDictionary(data, function (res) {
                            var res=res;
                            if(res.code==0) {
                                $.DialogByZ.Autofade({Content: "新增字典成功！"});
                                $("#personPopover").modal('hide');
                                //刷新字典列表
                                _this.loadDictionaryItemList(1);
                            }
                            else {
                                $.DialogByZ.Autofade({Content:res.message || "保存失败！"});
                            }
                        }, function (err) {
                            _commonFun.errorTips('');
                        })

                    }


                }
                else{

                    //编辑 保存字典信息

                    //判断主子表
                    if(parentId == 0){
                        //主表修改
                        var data={
                            dictTypeId:dictionaryId,
                            dicTypeCode:dictionaryCode,
                            dicTypeName:dictionaryName,

                            // parentId:parentId
                        }
                        _dictionary_service.editDictionary(data, function (res) {
                            var result=res;
                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "修改字典成功！"});
                                $("#personPopover").modal('hide');
                                //刷新字典列表
                                _this.loadDictionaryList(1);
                            }
                            else {
                                $.DialogByZ.Autofade({Content:res.message || "保存失败！"});
                            }
                        }, function (err) {
                            _commonFun.errorTips('');
                        })
                    }else {
                        //子表修改
                        var data={
                            dictItemId:dictionaryId,
                            dicItemCode:dictionaryCode,
                            dicItemName:dictionaryName,
                            sequenceNumber:sequenceNumber,
                            // parentId:parentId
                        }
                        _dictionary_service.editItemDictionary(data, function (res) {
                            var result=res;
                            if(result.code==0) {
                                $.DialogByZ.Autofade({Content: "修改字典成功！"});
                                $("#personPopover").modal('hide');
                                //刷新字典列表
                                _this.loadDictionaryItemList(1);
                            }
                            else {
                                $.DialogByZ.Autofade({Content:res.message || "保存失败！"});
                            }
                        }, function (err) {
                            _commonFun.errorTips('');
                        })
                    }

                }
            })
            //序号不能为负
            $(document).on('keyup', '#dictionaryOrdyByPop', function () {
                var value = $("#dictionaryOrdyByPop").val();
                if (value<=0) {
                    $("#dictionaryOrdyByPop").val("");
                }
            })
            //关闭字典弹窗
            $(document).on('click', '#popClose', function () {
                $("#dictionaryNamePop").val("");
                $("#dictionaryCodePop").val("");
                $("#sequenceNumberPop").val("");
                $("#dictionaryOrdyByPop").val("");
                $("#personPopover").modal('hide');
            })
            //关闭弹窗
            $(document).on('click', '#cancelDictionaryBtn', function () {
                $("#dictionaryNamePop").val("");
                $("#dictionaryCodePop").val("");
                $("#sequenceNumberPop").val("");
                $("#dictionaryOrdyByPop").val("");
                $("#personPopover").modal('hide');
            })
            //删除字典
            $(document).on('click', '#dictionaryList-table .a-deleteDictionary', function (e) {
                e.stopPropagation();
                var _self=$(this);
                console.log("_self",_self);
                var parentId  = _this.data.parentId;
                console.log("parentId",parentId);
                var dictionaryId = _self.parent().parent().data('dictionaryid');
                var dictionaryCode = _self.parent().parent().data('dictionaryCode');
                console.log("dictionaryCode",dictionaryCode);
                $.DialogByZ.Confirm({
                    Title: "操作提示",
                    Content: "确定要删除该字典吗？",
                    FunL:function(){
                        if (parentId == 0) {
                            _dictionary_service.deleteDictionary(dictionaryId, function (res) {
                                var result=res;
                                if(result.code==0) {
                                    $.DialogByZ.Autofade({Content: "删除成功！"});
                                    _this.loadDictionaryList(1);
                                }
                            }, function (err) {
                                _commonFun.errorTips('删除失败');
                            })
                        }else{
                            _dictionary_service.deleteItemDictionary(dictionaryId, function (res) {
                                var result=res;
                                if(result.code==0) {
                                    $.DialogByZ.Autofade({Content: "删除成功！"});
                                    _this.loadDictionaryItemList(1);
                                }
                            }, function (err) {
                                _commonFun.errorTips('删除失败');
                            })
                        }

                        $.DialogByZ.Close();
                    },
                    FunR:function(){
                        $.DialogByZ.Close();
                    }
                })
            })
        },
        //时间控件初始化
        initDatetime:function () {
            $(".datainp").jeDate({
                festival: false,
                ishmsVal: false,
                initAddVal:[0],
                format: "YYYY-MM-DD",
                zIndex: 3000
            });
            //将当前时间转换成yyyy-mm-dd格式
            function nowTime(){
                var mydate = new Date();
                var str = "" + mydate.getFullYear() + "-";
                var mm = mydate.getMonth()+1
                if(mydate.getMonth()>9){
                    str += mm;
                    str = str + "-";
                }
                else{
                    str += "0" + mm;
                    str = str + "-";
                }
                if(mydate.getDate()>9){
                    str += mydate.getDate();
                }
                else{
                    str += "0" + mydate.getDate();
                }
                return str;
            }
            var startTime= nowTime();
            $("#entryDatePop").val(startTime);
        },
        //渲染字典列表
        loadDictionaryList : function (currentPage) {
            var _this = this;
            console.log("隐藏createBy")
            $(".sequenceNumber").css("display","none")
            var parentId = _this.data.parentId;
            console.log("parentId",parentId)
            if (parentId == 0) {
                $("#returnBtn").hide();
            } else {
                $("#returnBtn").show();
            }
            var keyInfo={
                type : _this.data.type,
                dicTypeName    :$.trim($("#dictionaryName").val()),
                dicTypeCode     :$.trim($("#dictionaryCode").val()),
                parentId    :parentId,
                currentPage     :currentPage,
                numberOnePage   :_this.data.numberOnePage
            }
            _dictionary_service.getDictionary(keyInfo,function (res) {
                var result=res;
                if(result.code==0) {
                    $('.currentPage').text(currentPage);
                    $('.currentPageBtn').val(currentPage);
                    if(result.data.totalNumber!=0){
                        var data=result.data.infoList;
                        _this.loadDictionarySource(data);
                        $(".sequenceNumberHidden").css("display","none")
                    }
                    else {
                        $.DialogByZ.Autofade({Content: "查无数据！"});
                        $("#dictionaryList-table").empty();
                    }
                    _this.loadPagingNumber(result.data.totalNumber);
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        //渲染字典列表
        //子表
        loadDictionaryItemList : function (currentPage) {
            var _this = this;
            console.log("子表显示")
            $(".sequenceNumber").css("display","")
            var parentId = _this.data.parentId;
            if (parentId == 0) {
                $("#returnBtn").hide();
            } else {
                $("#returnBtn").show();
            }
            var keyInfo={
                type : _this.data.type,
                dicItemName    :$.trim($("#dictionaryName").val()),
                dicItemCode     :$.trim($("#dictionaryCode").val()),
                dicTypeCode    :parentId,
                currentPage     :currentPage,
                numberOnePage   :_this.data.numberOnePage
            }
            console.log("$.trim" , keyInfo);
            //子表
            _dictionary_service.getItemDictionary(keyInfo,function (res) {
                var result=res;
                if(result.code==0) {
                    $('.currentPage').text(currentPage);
                    $('.currentPageBtn').val(currentPage);
                    if(result.data.totalNumber!=0){
                        var data=result.data.infoList;
                        //子表
                        _this.loadDictionaryItemSource(data);

                    }
                    else {
                        $.DialogByZ.Autofade({Content: "查无数据！"});
                        $("#dictionaryList-table").empty();
                    }
                    _this.loadPagingNumber(result.data.totalNumber);
                }
            },function(err){
                _commonFun.errorTips('');
            })
        },
        loadDictionarySource : function(list) {
            var dictionaryCodePop="",dictionaryNamePop="",dictionaryLevelPop="",dictionaryPBmPop="";
            $("#dictionaryList-table").empty();
            var str='';
            this.data.parentId = list[0].parentId;
            if (this.data.parentId == 0) {
                $("#returnBtn").hide();
            } else {
                $("#returnBtn").show();
            }
            for(ele in list){
                typeof(list[ele].dicTypeCode) == "undefined" ? dictionaryCodePop="" : dictionaryCodePop=list[ele].dicTypeCode;
                typeof(list[ele].dicTypeName) == "undefined" ? (dictionaryNamePop="") : (dictionaryNamePop=list[ele]. dicTypeName);
                typeof(list[ele].level) == "undefined" ? (dictionaryLevelPop="") : (dictionaryLevelPop=list[ele].level);
                typeof(list[ele].dicTypeCode) == "undefined" ? (dictionaryPBmPop="") : (dictionaryPBmPop=list[ele].dicTypeCode);
                var isDelete = list[ele].isDelete;
                str+='<tr data-dictionaryid="'+list[ele].dictTypeId+'">' +
                    // '<td class="dictionaryOrdyBy">'+list[ele].ordyBy+'</td>'+
                    '<td class="dictionaryName"><a class="children-show">'+dictionaryNamePop+'</a></td>'+
                    '<td class="dictionaryCode">'+dictionaryCodePop+'</td>'+
                    '<td class="dictionaryLevel">'+dictionaryLevelPop+'</td>'+
                    '<td class="dictionaryPBm">'+dictionaryPBmPop+'</td>'+
                    '<td class="createByHidden">'+list[ele].createBy+'</td>'+
                    '<td class="gmtCreate">'+list[ele].gmtCreate+'</td>';
                if (isDelete == 0) {  //不能删除
                    str+='<td style="min-width: 220px"><a class="link a-dictionary-edit"><i class="fa-table-line-edit" title="编辑"></i></a></td></tr>';
                } else {  //可以删除
                    str+='<td style="min-width: 220px"><a class="link a-dictionary-edit"><i class="fa-table-line-edit" title="编辑"></i></a>'+
                    '<a class="link a-deleteDictionary"><i class="fa-table-line-delete" title="删除"></i></a></td></tr>';
                }
            }
            $("#dictionaryList-table").html(str);
            this.data.type = "";  //将查询类型重置为空，默认查询下一级
        },
        //子表
        loadDictionaryItemSource : function(list) {
            var dictionaryCodePop="",dictionaryNamePop="",dictionaryLevelPop="",dictionaryPBmPop="";
            $("#dictionaryList-table").empty();
            var str='';
            this.data.parentId = list[0].parentId;
            if (this.data.parentId == 0) {
                $("#returnBtn").hide();
            } else {
                $("#returnBtn").show();
            }
            for(ele in list){
                //子表
                typeof(list[ele].dicItemCode) == "undefined" ? dictionaryCodePop="" : dictionaryCodePop=list[ele].dicItemCode;
                typeof(list[ele].dicItemName) == "undefined" ? (dictionaryNamePop="") : (dictionaryNamePop=list[ele]. dicItemName);
                typeof(list[ele].level) == "undefined" ? (dictionaryLevelPop="") : (dictionaryLevelPop=list[ele].level);
                typeof(list[ele].dicTypeCode) == "undefined" ? (dictionaryPBmPop="") : (dictionaryPBmPop=list[ele].dicTypeCode);
                var isDelete = list[ele].isDelete;
                str+='<tr data-dictionaryid="'+list[ele].dictItemId+'">' +
                    // '<td class="dictionaryOrdyBy">'+list[ele].ordyBy+'</td>'+
                    '<td class="dictionaryName">'+dictionaryNamePop+'</a></td>'+
                    '<td class="dictionaryCode">'+dictionaryCodePop+'</td>'+
                    '<td class="dictionaryLevel">'+dictionaryLevelPop+'</td>'+
                    '<td class="dictionaryPBm">'+dictionaryPBmPop+'</td>'+
                    '<td class="sequenceNumberHidden">'+list[ele].sequenceNumber+'</td>'+
                    '<td class="createByHidden">'+list[ele].createBy+'</td>'+
                    '<td class="gmtCreate">'+list[ele].gmtCreate+'</td>';
                if (isDelete == 0) {  //不能删除
                    str+='<td style="min-width: 220px"><a class="link a-dictionary-edit"><i class="fa-table-line-edit" title="编辑"></i></a></td></tr>';
                } else {  //可以删除
                    str+='<td style="min-width: 220px"><a class="link a-dictionary-edit"><i class="fa-table-line-edit" title="编辑"></i></a>'+
                        '<a class="link a-deleteDictionary"><i class="fa-table-line-delete" title="删除"></i></a></td></tr>';
                }
            }
            $("#dictionaryList-table").html(str);
            this.data.type = "";  //将查询类型重置为空，默认查询下一级
        },

        //加载分页
        loadPagingNumber: function(num) {
            parseInt(num % this.data.numberOnePage)==0 ?
                this.data.totalPageNum=parseInt(num/this.data.numberOnePage) :
                this.data.totalPageNum=parseInt(num/this.data.numberOnePage)+1;

            $(".dataNumber").html(num);//共多少条
            $(".pageNumber").html(this.data.totalPageNum);//共多少页
            $(".pageOneNumber").text(this.data.numberOnePage);//每页几条
        }
    };
    _person.init();
});
