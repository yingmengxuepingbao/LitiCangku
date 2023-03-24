var _chatMessageObj={
    data: {
        _curReceiveUserId:"",//当前聊天人id
        _curReceiveUserName:"",//当前聊天人昵称
        _itemTimer:"",//定时器
        _messageCount:'',//放在最顶部图标的总消息数
        _sysMessageCount:'',//放在最顶部图标的总消息数
        _isOpenMail:'',//是否开启站内信
        _pageSize:10,//每页条数
        _sysPageNo:1,
        _reg:/^[\u4E00-\u9FA5A-Za-z0-9_~·！!@#￥%……&()（）——+|、=\-【】[\]{}；?？：:'"‘“，,.。]+$/,
        _sysIsFirst:true,//判断是否为第一次请求系统历史消息 是 true 清空DOM 不是 false
        _userMap:{}//对象，键是userId，值：{count:0,haveMore:true,checkStr:"checked","isFirst":true} 每个人的：历史页数，是否还有历史记录，群发时是否被选中，是否是第一次点击该用户
    },
    init: function () {
        this.onLoad();
        this.bindEvent();
    },
    onLoad: function () {
        this.systemIsOpen();
        var _this=this;
        setTimeout(function () {
            if(_this._isOpenMail){
                setInterval(function () {
                    _this.getAllUsersAndMessage();
                },30000)
            }
        },30000)
    },
    bindEvent: function () {
        var _this = this;
        //点击按钮下拉
        $(document).on("click", ".chat-icon-animation", function () {
            if ($(".chatbar").is(":visible")) {
                $(".chatbar").slideUp();
                $(".icon-box").removeClass('shadow');
            } else {
                $(".chatbar").slideDown();
                $(".icon-box").addClass('shadow');
            }
        });
        //全选/全不选 checkbox
        $(document).on('click', '#checkAllUsers', function (e) {
            e.stopPropagation();
            var _self = $(this);
            if (_self.is(':checked')) {
                //全选中设置
                $("#checkItemUsers li").each(function (index, item) {
                    $(item).find('input[type="checkbox"]').prop("checked", "checked");
                    _this.data._userMap[$(item).attr("data-userid")].checkStr="checked";
                })
            }
            else {
                //全不选设置
                $("#checkItemUsers li").each(function (index, item) {
                    $(item).find('input[type="checkbox"]').prop("checked", false);
                    _this.data._userMap[$(item).attr("data-userid")].checkStr="";
                })
            }
        })
        //有一个未选中则取消全选  /  全都选中则全选选中
        $(document).on('click', '#checkItemUsers li input[type="checkbox"]', function (e) {
            e.stopPropagation();
            var isCheckAll = true;
            var dataUserId=$(this).parents("li").attr("data-userid");
            if (!$(this).is(':checked')) {
                //有一个没选中的就取消全选
                $("#checkAllUsers").prop("checked", false);
                _this.data._userMap[dataUserId].checkStr="";
            }
            else {
                _this.data._userMap[dataUserId].checkStr="checked";
                $("#checkItemUsers li").each(function (index, item) {
                    if (!$(item).find('input[type="checkbox"]').is(':checked')) {
                        isCheckAll = false;
                    }
                })
                //全都选中则全选选中
                if (isCheckAll) {
                    $("#checkAllUsers").prop("checked", "checked");
                }
            }
        })
        //点击其中一个人员名跳转到聊天列表
        $(document).on("click", ".chatbar-contacts-uls li .clickLine p", function () {
            var receiveUserId = $(this).parent().parent().attr("data-userid");
            _this.data._curReceiveUserId = receiveUserId;
            var userName = $(this).parent().find("p").text();
            _this.data._curReceiveUserName = userName;
            var messageId = 'itemMessage_' + receiveUserId;
            if (document.getElementById(messageId) == null) {
                //没找到该元素则创建
                $("#chatMessagesList").append('<div class="itemMessage_" id="' + messageId + '"></div>');
                _this.getOtherUnreadMessage(receiveUserId,messageId);
            }
            else{
                _this.getUnreadMessageList(receiveUserId, messageId);
            }
            $("#chatMessagesList").find(".itemMessage_").hide();
            $("#" + messageId).show();//展示
            //打开动画
            $(".chatbar-messages").css({
                "transform": "translate3d(0, 0, 0)"
            });
            $('.messages-title h6').text(userName);
            //判断该聊天对象是否还有历史消息,用来展示查看更多或没有更多
            _this.isHasMoreInfo(_this.data._userMap[receiveUserId].haveMore);
            $(".messages-content").focus();
            $('#chatMessagesList').animate({scrollTop:$('#'+messageId).height()+'px'},200);
            _this.data._itemTimer=setInterval(function () {
                _this.getUnreadMessageList(receiveUserId, messageId);
            },3000)
            setTimeout(function () {
                _this.getAllUsersAndMessage();
            },1000)
        });
        //点击查看历史
        $(document).on("click", ".his-get-more", function () {
            var curReceiveId = _this.data._curReceiveUserId;
            var curReceiveDomId = 'itemMessage_' + curReceiveId;
            var curPageNo = _this.data._userMap[curReceiveId].count;
            _this.getHistoricalDialogue(curPageNo, _this.data._pageSize, curReceiveId, curReceiveDomId);
        })
        $(document).on("click", ".return-icon", function () {
            $(".chatbar-messages").css({
                "transform": "translate3d(100%, 0, 0)"
            });
            clearInterval(_this.data._itemTimer);
        });
        //发送单条消息
        $(document).on('click',"#sendOneMsg", function() {
            var message = $.trim($('#sendOneMsgText').val().replace(/[\r\n]/g, ""));//过滤回车换行空白符
            if($.trim(message)==""){
                var messageTooltip = "<div class='message-tooltip-danger'>不能发送空白信息</div>";
                $('#sendOneMsgText').val("");
                $("#characterOnce").text(0);
                $("body").append(messageTooltip);
                setTimeout(function() {
                    $(".message-tooltip-danger").remove();
                }, 2000);
            }
            else if (_this.data._reg.test(message)) {
                $("#sendOneMsg").attr("disabled","disabled");
                _this.sendMessageCreate(_this.data._curReceiveUserId, message, 0);
                var dId = 'itemMessage_' + _this.data._curReceiveUserId;
                $('#chatMessagesList').animate({scrollTop:$('#'+dId).height()+'px'},200);
            }
            else {
                var messageTooltip = "<div class='message-tooltip-danger'>不能发送特殊字符</div>";
                $('#sendOneMsgText').val("");
                $("#characterOnce").text(0);
                $("body").append(messageTooltip);
                setTimeout(function () {
                    $(".message-tooltip-danger").remove();
                }, 2000);
            }
        });
        //单条发送响应键盘事件
        $(document).on("keyup", "#sendOneMsgText", function (e) {
            let len = $(this).val().length;
            if(len > 100){
                $(this).val($(this).val().substring(0,100));
                $("#characterOnce").text(100);
            }else{
                $("#characterOnce").text(len);
            }
            if (e && e.keyCode == 13) {
                if($("#sendOneMsg").attr("disabled")!="disabled"){
                    var message = $('#sendOneMsgText').val().replace(/[\r\n]/g, "");//过滤回车换行空白符
                    if($.trim(message)==""){
                        var messageTooltip = "<div class='message-tooltip-danger'>不能发送空白信息</div>";
                        $('#sendOneMsgText').val("");
                        $("#characterOnce").text(100);
                        $("body").append(messageTooltip);
                        setTimeout(function() {
                            $(".message-tooltip-danger").remove();
                        }, 2000);
                    }
                    else if (_this.data._reg.test(message)){
                        $("#sendOneMsg").attr("disabled","disabled");
                        _this.sendMessageCreate(_this.data._curReceiveUserId, message, 0);
                        var dId = 'itemMessage_' + _this.data._curReceiveUserId;
                        $('#chatMessagesList').animate({scrollTop:$('#'+dId).height()+'px'},200);
                    }
                    else {
                        var messageTooltip = "<div class='message-tooltip-danger'>不能发送特殊字符</div>";
                        $('#sendOneMsgText').val("");
                        $("#characterOnce").text(100);
                        $("body").append(messageTooltip);
                        setTimeout(function () {
                            $(".message-tooltip-danger").remove();
                        }, 2000);
                    }
                }
            }
        })
        //群发消息
        $(document).on("click", "#groupSendMsg", function (e) {
            _this.sendAllMessageEvent();
        })
        //群发响应键盘事件
        $(document).on("keyup", "#groupSendMsgText", function (e) {
            let len = $(this).val().length;
            if(len > 100){
                $(this).val($(this).val().substring(0,100));
                $("#characterGroup").text(100);
            }else{
                $("#characterGroup").text(len);
            }
            if (e && e.keyCode == 13) {
                if($("#groupSendMsgText").attr("disabled")!="disabled"){
                    _this.sendAllMessageEvent();
                }
            }
        })
        //搜索功能
        $(document).on("keyup", ".search-text", function () {
            var txt = $('.search-text').val();
            txt = txt.replace(/\s/g, '');
            $('.chatbar-contacts-uls li').each(function () {
                if (!$(this).is(':contains(' + txt + ')')) {
                    $(this).hide();
                } else {
                    $(this).show();
                }
            });
            return false;
        });
        //点击系统未读数弹窗
        $(document).on("click", "#systemMsgMd", function () {
            _this.getSystemMessage();//获取未读的系统消息
            setTimeout(function () {
                $('#modalBodyH').animate({scrollTop:document.getElementById("systemMsgUl").clientHeight+'px'},10);
            },200)
            $("#chatModal").modal("show");
            _this.getAllUsersAndMessage();
        });
        //系统消息显示更多
        $(document).on("click", "#isHasMoreSys", function () {
            _this.getHisSysMessage();
        });
        //系统消息关闭弹窗
        $(document).on("click", ".closeChatModal", function () {
            _this.data._sysPageNo=1;
        });
    },
    //是否开启站内信
    systemIsOpen:function () {
        var _this=this;
        new doAjax({
            type:"get",
            url: $.mpbGetHeaderPath()+"/system/conf",
            callBack: function (result) {
                if(result.code=='0'){
                    if(result.data.isOpenMail==1){
                        _this._isOpenMail=1;//开启站内信
                        _this.initChatDom();//创建DOM，初始化
                        _this.getOnceUsersMessage();//获取好友列表及消息未读条数
                    }
                    else {
                        _this._isOpenMail=0;//关闭站内信
                    }
                }
            }
        });
    },
    //初始化创建站内信DOM
    initChatDom:function () {
        if(!$(".topnav").hasClass(".chat-icon-animation")){
            var chatIconStr='<div class="chat-icon-animation chat-shadow"><span class="badge badge-items"></span><i class="fa fa-commenting chat-icon-animation"></i></div>';
            $("#userinfo").after(chatIconStr);
            var chatContentStr='<div class="chatbar ">' +
                '<div class="chatbar-box">' +
                '<div class="chatbar-contacts">' +
                '<div id="systemMsgMd" class="chat-sys-md"><i class="fa fa-cog"></i><span style="margin-left: 17px;font-weight: 600;">系统消息</span><i class="badge badge-item-sys" id="sysBadge"></i> </div>'+
                '<div class="contacts-search-box"><i class="fa fa-search"></i><input type="text" class="search-text " placeholder="请输入要搜索的联系人"></div>' +
                '<div style="padding: 3px 10px;"><div class="checkbox-custom"><input type="checkbox" class="customCk"  id="checkAllUsers" name="checkbox"><label>全选</label></div>' +
                '</div>' +
                '<ul class="chatbar-contacts-uls custom-scroll" id="checkItemUsers"></ul></div>' +
                '<div class="chatbar-messages">' +
                '<div class="messages-title"><i class="fa fa-user-circle-o"></i><h6></h6><i class="fa fa-arrow-circle-o-left return-icon"></i></div>' +
                '<div class="messages-text custom-scroll" id="chatMessagesList">' +
                '<div class="chat-his-icon"><div class="his-get-more"><i class="fa fa-clock-o"></i> 查看历史消息</div></div></div>' +
                '<div class="messages-box">' +
                '<textarea class="form-control messages-content" name="" id="sendOneMsgText" rows="5" maxlength="100"  style="resize: none;"></textarea><span class="right-character" id="characterOnce" style="right: 33px;">0</span><span class="right-character">/100</span>' +
                '<button type="button" class="btn btn-primary message-btn" id="sendOneMsg" >发送</button></div></div></div>' +
                '<div class="send-group">' +
                '<textarea  class="form-control " id="groupSendMsgText" rows="5" placeholder="输入群发消息......" maxlength="100"  style="resize: none;"></textarea><span class="right-character" style="top: 80px;right: 33px;" id="characterGroup">0</span><span class="right-character" style="top: 80px;">/100</span>' +
                '<button type="button" class="btn btn-primary group-send-msg" id="groupSendMsg"><i class="fa fa-send-o"></i> 群发</button></div></div>';
            $(".topnav").after(chatContentStr);
            $("#chartIcon").show();
            var modalStr='<div id="chatModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel"  data-backdrop="static" data-keyboard="false">' +
                '<div class="modal-dialog" role="document"><div class="modal-content">' +
                '<div class="modal-header"><button type="button" class="close closeChatModal" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
                '<h4 class="modal-title" id="gridSystemModalLabel">系统消息</h4></div>' +
                '<div class="modal-body"><div id="modalBodyH" class="dl-modal custom-scroll " ><div id="isHasMoreSys"></div><dl id="systemMsgUl" class=""></dl></div>' +
                '<div class="modal-footer" style="padding: 15px 0 0 0;"><button type="button" class="btn btn-default closeChatModal" data-dismiss="modal">关闭</button></div>' +
                '</div></div></div>';
            $(".container-fluid").after(modalStr);
        }// <span class="refresh-users"><i class="fa fa-refresh"></i> </span>
    },
    //获取我与其他人的未读消息计数只运行一次
    getOnceUsersMessage:function () {
        var _this=this;
        $.ajax({
            type:"get",
            url: $.mpbGetHeaderPath()+"/messageManagement/getUnreadCount",
            success:function (result) {
                result=JSON.parse(decodeURIComponent(result));
                if(result.code=='0'){

                    var _usersStr = "";
                    var _usersC=result.data;
                    if(_usersC.importantSysMessageCount > 0 || _usersC.messageNum > 0 || _usersC.normalSysMessageCount > 0){
                        var borswer = window.navigator.userAgent.toLowerCase();
                        if ( borswer.indexOf( "ie" ) >= 0){
                            //IE内核浏览器
                            var strEmbed = '<embed name="embedPlay" src="./images/audio/ding-dong.mp3"  autostart="true" hidden="true" loop="false"></embed>';
                            if ($("body").find("embed").length <= 0)
                                $("body").append(strEmbed);
                        } else{
                            //非IE内核浏览器
                            var strAudio = "<audio id='audioPlay' src='./images/audio/ding-dong.mp3' hidden='true'>";
                            if($("#audioPlay").length<=0){
                                $( "body" ).append( strAudio );
                            }
                            var audio = document.getElementById("audioPlay");
                            audio.play();
                        }
                        setTimeout(function () {
                            $("#audioPlay").remove();
                        },2000);
                    }
                    var _users=result.data.dataList;
                    if(_usersC.importantSysMessageCount>0){
                        _this.getSystemMessage();
                    }
                    //计算未读总数
                    _this.data._messageCount=_usersC.messageNum+_usersC.normalSysMessageCount + _usersC.importantSysMessageCount;
                    if(_this.data._messageCount > 0){
                        $(".chat-icon-animation .badge-items").text(_this.data._messageCount);
                    }
                    else  $(".chat-icon-animation .badge-items").text("");
                    _this.data._sysMessageCount=_usersC.normalSysMessageCount + _usersC.importantSysMessageCount;
                    if(_this.data._sysMessageCount>0) $("#sysBadge").text(_this.data._sysMessageCount);
                    else $("#sysBadge").text("");
                    for (let i = 0, len=_users.length; i < len; i++ ) {
                        _this.data._userMap[_users[i].userId]={};
                        _this.data._userMap[_users[i].userId].count=1;//默认请求历史记录从第一页开始取
                        _this.data._userMap[_users[i].userId].haveMore=true;//是否有更多历史
                        _this.data._userMap[_users[i].userId].checkStr="";//群发时是否选中
                        _this.data._userMap[_users[i].userId].isFirst=false;//是否是第一次点击该人员，第一次则请求含有历史记录的未读消息
                        //_userMap{count:0,haveMore:true,checkStr:"checked","isFirst":true}
                        let itemCount=_users[i].messageNum;
                        if(itemCount==0) itemCount="";
                        _usersStr += '<li data-userid='+_users[i].userId +'><div class="checkbox-custom">'+
                                        '<input type="checkbox" class="customCk"  name="checkbox">'+
                                        '<label></label></div><div class="clickLine"><p>' + _users[i].nickname + '</p>' +
                            '<span class="badge badge-item">'+itemCount+'</span></div></li>';
                    }
                    $(".chatbar-contacts-uls").html(_usersStr);
                }
            },
            error:function (msg) {
                console.log(msg)
            }
        })
    },
    //获取未读的系统消息
    getSystemMessage:function () {
        var _this=this;
        $.ajax({
            type:"get",
            url: $.mpbGetHeaderPath()+"/messageManagement/getSysMessage",
            success:function (result) {
                result=JSON.parse(decodeURIComponent(result));
                if(result.code=='0'){

                    if(result.data.count>0){
                        var _dlData=result.data;
                        $("#isHasMoreSys").html('<div class="sys-get-more"><i class="fa fa-clock-o"></i>查看历史消息</div>');
                        let noticeType=0;
                        let dlStr="";
                        let colorNameStr="";
                        for(let i=0,len=_dlData.dataList.length;i<len;i++){
                            if(_dlData.dataList[i].noticeType==0){
                                noticeType=0;
                                colorNameStr="gray"
                            }
                            else if(_dlData.dataList[i].noticeType==1){
                                noticeType=1;
                                colorNameStr="red"
                            }
                            dlStr+='<dt>'+_dlData.dataList[i].createDateStr+'</dt>'+
                                '<dd class="'+colorNameStr+'">'+ _dlData.dataList[i].content +'</dd>';
                        }
                        $("#systemMsgUl").append(dlStr);
                        if(noticeType>0){
                            $('#modalBodyH').animate({scrollTop:document.getElementById("systemMsgUl").clientHeight+'px'},10);
                            $("#chatModal").modal("show");
                            _this.getAllUsersAndMessage();
                        }
                    }
                    else{
                        $("#sysBadge").text("");
                        _this.data._sysIsFirst=true;
                        _this.getHisSysMessage();
                    }
                }
            },
            error:function (msg) {
                console.log(msg)
            }
        })
    },
    //获取系统历史数据
    getHisSysMessage:function () {
        var _this=this;
        $("#isHasMoreSys").html('<div class="sys-get-more"><img src="./images/common/loading.gif" style="width: 24px;height:24px"></div>');
        $.ajax({
            type:"get",
            url: $.mpbGetHeaderPath()+"/messageManagement/getHisSysMessage/"+_this.data._sysPageNo+'/'+_this.data._pageSize,
            success:function (result) {
                result=JSON.parse(decodeURIComponent(result));
                if(result.code=='0'){

                    var _dlData=result.data;
                    if(_dlData.totalCount>0){
                        if(_this.data._sysIsFirst){
                            $("#systemMsgUl").empty();
                            _this.data._sysIsFirst=false;
                            setTimeout(function () {
                                let clientHeight=document.getElementById("systemMsgUl").clientHeight;
                                $('#modalBodyH').animate({scrollTop:clientHeight+'px'},10);
                            },100)
                        }
                        if(_dlData.totalCount > _this.data._pageSize && _dlData.dataList.length==_this.data._pageSize){
                            $("#isHasMoreSys").html('<div class="sys-get-more"><i class="fa fa-clock-o"></i>查看历史消息</div>');
                        }
                        else{
                            $("#isHasMoreSys").html('<div class="no-have-his">没有更多消息了~</div>');
                        }
                        let dlStr="";
                        let colorNameStr="";
                        for(let i=0,len=_dlData.dataList.length;i<len;i++){
                            if(_dlData.dataList[i].noticeType==0) colorNameStr="gray";
                            else if(_dlData.dataList[i].noticeType==1)colorNameStr="red";
                            dlStr+='<dt>'+_dlData.dataList[i].createDateStr+'</dt>'+
                                '<dd class="'+colorNameStr+'">'+_dlData.dataList[i].content+'</dd>';
                        }
                        let subDomId="sysMsg-"+_this.data._sysPageNo;
                        let domStr=$("<div id='"+subDomId+"' style='position: relative;overflow: hidden'></div>").html(dlStr);
                        $("#systemMsgUl").prepend(domStr);
                        let hisHeight=document.getElementById(subDomId).clientHeight;
                        $('#modalBodyH').animate({scrollTop:hisHeight+'px'},10);
                        _this.data._sysPageNo+=1;
                    }
                    else{
                        $("#isHasMoreSys").html('<div class="no-have-his">没有更多消息了~</div>');
                    }
                }
            },
            error:function (msg) {
                console.log(msg)
            }
        })
    },
    //获取用户列表及未读消息数
    getAllUsersAndMessage:function () {
        var _this=this;
        $.ajax({
            type:"get",
            url: $.mpbGetHeaderPath()+"/messageManagement/getUnreadCount",
            success:function (result) {
                result=JSON.parse(decodeURIComponent(result));
                if(result.code=='0'){

                    var _usersStr = "";
                    var _usersC=result.data;
                    var _users=result.data.dataList;
                    if(_usersC.importantSysMessageCount > 0 || _usersC.messageNum > 0 || _usersC.normalSysMessageCount > 0){
                        var borswer = window.navigator.userAgent.toLowerCase();
                        if ( borswer.indexOf( "ie" ) >= 0){
                            //IE内核浏览器
                            var strEmbed = '<embed name="embedPlay" src="./images/audio/ding-dong.mp3"  autostart="true" hidden="true" loop="false"></embed>';
                            if ($("body").find("embed").length <= 0)
                                $("body").append(strEmbed);
                        } else{
                            //非IE内核浏览器
                            var strAudio = "<audio id='audioPlay' src='./images/audio/ding-dong.mp3' hidden='true'>";
                            if($("#audioPlay").length<=0){
                                $( "body" ).append( strAudio );
                            }
                            var audio = document.getElementById("audioPlay");
                            audio.play();
                        }
                        setTimeout(function () {
                            $("#audioPlay").remove();
                        },2000);
                    }
                    if(_usersC.importantSysMessageCount>0){
                        _this.getSystemMessage();
                        setTimeout(function () {
                            $('#modalBodyH').animate({scrollTop:document.getElementById("systemMsgUl").clientHeight+'px'},10);
                        },200)
                    }
                    //计算未读总数
                    _this.data._messageCount=_usersC.messageNum+_usersC.normalSysMessageCount + _usersC.importantSysMessageCount;
                    if(_this.data._messageCount > 0){
                        $(".chat-icon-animation .badge-items").text(_this.data._messageCount);
                    }
                    else  $(".chat-icon-animation .badge-items").text("");
                    _this.data._sysMessageCount=_usersC.normalSysMessageCount + _usersC.importantSysMessageCount;
                    if(_this.data._sysMessageCount>0) $("#sysBadge").text(_this.data._sysMessageCount);
                    else $("#sysBadge").text("");
                    for (let i = 0, len=_users.length; i < len; i++ ) {
                        if(!_this.data._userMap[_users[i].userId]){
                            _this.data._userMap[_users[i].userId]={};
                            _this.data._userMap[_users[i].userId].count=1;//默认请求历史记录从第一页开始取
                            _this.data._userMap[_users[i].userId].haveMore=true;//是否有更多历史
                            _this.data._userMap[_users[i].userId].checkStr="";//群发时是否选中
                            _this.data._userMap[_users[i].userId].isFirst=false;
                        }
                        let itemCount=_users[i].messageNum;
                        let checkStr= _this.data._userMap[_users[i].userId].checkStr;
                        if(itemCount==0) itemCount="";
                        _usersStr += "<li data-userid='"+_users[i].userId +"'>" +"<div class='checkbox-custom'>" +
                            "           <input type='checkbox' class='customCk' "+checkStr+"  name='checkbox'>" +
                            "           <label></label>" +
                            "        </div>"+ "<div class='clickLine'>" +"<p>" + _users[i].nickname + "</p><span class='badge badge-item'>"+itemCount+"</span>"+"</div>" +
                            "</li>";
                    }
                    $(".chatbar-contacts-uls").html(_usersStr);
                }
            },
            error:function (msg) {
                console.log(msg)
            }
        })
    },
    //获取和好友未读消息
    getUnreadMessageList:function (receiveUserId,domObj) {
        $.ajax({
            type:"get",
            url: $.mpbGetHeaderPath()+"/messageManagement/getMessage/"+receiveUserId,
            success:function (result) {
                result=JSON.parse(decodeURIComponent(result));
                if(result.code=='0'){

                    if(result.data.count>0){
                        let _unRData=result.data.dataList;
                        let str="";
                        for (let i=0, len=_unRData.length;i<len;i++){
                            str+= '<ul class="messages-text-uls"><li class="messages-text-lis  answer">'+
                                '<h6><span>' + _unRData[i].sendUserName + '</span><span class="time">' + _unRData[i].createDateStr + '</span>' +
                                '</h6><p>' + _unRData[i].content + '</p></li></ul>';
                        }
                        $("#"+domObj).append(str);
                        $('#chatMessagesList').animate({scrollTop:$('#'+domObj).height()+'px'},500);
                    }
                }
            },
            error:function (msg) {
                console.log(msg)
            }
        })
    },
    //第一次点击该人员
    getOtherUnreadMessage:function (receiveUserId,domObj) {
        var _this=this;
        $.ajax({
            type:"get",
            url: $.mpbGetHeaderPath()+"/messageManagement/getMessage/"+receiveUserId,
            success:function (result) {
                result=JSON.parse(decodeURIComponent(result));
                if(result.code=='0'){
                    $.ajax({
                        type:"get",
                        url: $.mpbGetHeaderPath()+"/messageManagement/getOtherUnreadMessage/"+receiveUserId,
                        success:function (result) {
                            result=JSON.parse(decodeURIComponent(result));
                            if(result.code=='0'){

                                let _unRData=result.data.dataList;
                                let str="";
                                for (let i=0, len=_unRData.length;i<len;i++){
                                    str+= '<ul class=\"messages-text-uls\"><li class=\"messages-text-lis  meMes\">'+
                                        '<h6><span>' + _unRData[i].sendUserName + '</span><span class=\"time\">'+_unRData[i].createDateStr + '</span></h6>' +
                                        '<p>' + _unRData[i].content + '</p></li></ul>';
                                }
                                $("#"+domObj).append(str);
                                $('#chatMessagesList').animate({scrollTop:$('#'+domObj).height()+'px'},500);
                                _this.getHistoricalDialogue(1,10,receiveUserId,domObj);
                            }
                        },
                        error:function (msg) {
                            console.log(msg)
                        }
                    })
                }
            },
            error:function (msg) {
                console.log(msg)
            }
        })
    },
    //发送消息(单发,群发)type==0 单发 1 群发
    sendMessageCreate:function (receiveUserIds,content,type){
        var _this=this;
        $.ajax({
            type:"POST",
            url: $.mpbGetHeaderPath()+"/messageManagement/createMessage",
            data:{
                receiveUserIds:receiveUserIds,
                content:content
            },
            success:function (result) {
                result=JSON.parse(decodeURIComponent(result));
                if(type==0){
                    if(result.code=='0'){
                        //单发消息
                        var timeStr=_this.timeFormatting(new Date());
                        var messageId='itemMessage_'+receiveUserIds;
                        var sendUserName=localStorage.getItem("nickname");
                        var str= '<ul class="messages-text-uls"><li class="messages-text-lis  meMes">'+
                            '<h6><span>' + sendUserName + '</span><span class="time">' + timeStr + '</span></h6>' +
                            '<p><i style="color: #28a745;width: 3px;height: 3px;margin-right:5px;margin-bottom:2px;"></i>' + content + '</p></li></ul>';
                        $("#"+messageId).append(str);
                        $("#sendOneMsgText").val("");
                        $("#characterOnce").text(0);
                        setTimeout(function () {
                            $("#sendOneMsg").removeAttr("disabled");
                        },1000)
                    }
                    else{
                        var timeStr=_this.timeFormatting(new Date());
                        var messageId='itemMessage_'+receiveUserIds;
                        var str= '<ul class="messages-text-uls"><li class="messages-text-lis  meMes">'+
                            '<h6><span>' + _this.data._curReceiveUserName + '</span><span class="time">'+ timeStr + '</span></h6>' +
                            '<p style="color: #d8534f">' + content + '<i class="fa fa-info-circle" style="color: #999;font-size: 12px">发送失败</i></p></li></ul>';
                        $("#"+messageId).append(str);
                        $("#sendOneMsgText").val("");
                        $("#characterOnce").text(0);
                        $("#sendOneMsg").removeAttr("disabled");
                    }
                }
                else{
                    if(result.code=='0'){
                        $("#checkItemUsers li").each(function (index, item) {
                            var receiveId=$(item).attr("data-userid");
                            var sendUserName=localStorage.getItem("nickname");
                            var messageDomId = 'itemMessage_' + receiveId;
                            if (document.getElementById(messageDomId) == null) {
                                //没找到该元素则创建
                                $("#chatMessagesList").append('<div class="itemMessage_" id="' + receiveId + '"></div>');
                            }
                            //分别往不同用户聊天的div里追加一条消息
                            var timeStr=_this.timeFormatting(new Date());
                            var str= '<ul class="messages-text-uls"><li class="messages-text-lis meMes"><h6><span>' + sendUserName + '</span>' +
                                '<span class="time">' + timeStr + '</span></h6><p>' + content + '</p></li></ul>';
                            $("#"+messageDomId).append(str);
                            $("#groupSendMsgText").val("");
                            $("#characterGroup").text(0);
                        })
                        var messageTooltip = "<div class='message-tooltip-success'>发送成功</div>";
                        $("body").append(messageTooltip);
                        setTimeout(function () {
                            $("#groupSendMsgText").removeAttr("disabled");
                        },1000);
                        setTimeout(function () {
                            $(".message-tooltip-success").remove();
                        }, 2000);
                    }
                    else{
                        var messageTooltip = "<div class='message-tooltip-danger'>发送失败请检查网络</div>";
                        $("body").append(messageTooltip);
                        $("#groupSendMsgText").removeAttr("disabled");
                        setTimeout(function () {
                            $(".message-tooltip-danger").remove();
                        }, 2000);
                    }
                }
            },
            error:function (msg) {
                console.log(msg)
                $("#groupSendMsgText").removeAttr("disabled");
                $("#sendOneMsgText").removeAttr("disabled");
            }
        })
    },
    sendAllMessageEvent:function () {
        var _this=this;
        var messageAll = $('#groupSendMsgText').val().replace(/[\r\n]/g, "");//过滤回车换行空白符
        if ($.trim(messageAll) =="") {
            var messageTooltip = "<div class='message-tooltip-danger'>不能发送空白信息</div>";
            $('#groupSendMsgText').val("");
            $('#groupSendMsgText').focus();
            $("#characterGroup").text(100);
            $("body").append(messageTooltip);
            setTimeout(function() {
                $(".message-tooltip-danger").remove();
            }, 2000);
        }
        else if (!_this.data._reg.test(messageAll)) {
            var messageTooltip = "<div class='message-tooltip-danger'>不能发送特殊字符</div>";
            $('#groupSendMsgText').val("");
            $('#groupSendMsgText').focus();
            $("#characterGroup").text(100);
            $("body").append(messageTooltip);
            setTimeout(function() {
                $(".message-tooltip-danger").remove();
            }, 2000);
        }
        else {
            var idArr=[];
            $("#checkItemUsers li").each(function(index,item){
                var _self=$(item);
                if(_self.find('input[type="checkbox"]').is(':checked')){
                    var recId = _self.attr('data-userid');
                    idArr.push(recId);
                }
            })
            if(idArr.length>0){
                var idStr=idArr.join(",");
                $('#sendOneMsgText').focus();
                $("#groupSendMsgText").attr("disabled","disabled");
                _this.sendMessageCreate(idStr,messageAll,1);
            }
            else{
                var messageTooltip = "<div class='message-tooltip-danger'>请选择人员再发送消息</div>";
                $("body").append(messageTooltip);
                setTimeout(function() {
                    $(".message-tooltip-danger").remove();
                }, 2000);
            }
        }
    },
    //获取两人之间的历史对话-翻页
    getHistoricalDialogue:function (pageNo,pageSize,receiveUserId,domObj) {
        var _this=this;
        $(".chat-his-icon").html('<div class="his-get-more"><img src="./images/common/loading.gif" style="width: 24px;height:24px"></div>');
        $.ajax({
            type:"get",
            url: $.mpbGetHeaderPath()+"/messageManagement/getHisMessage/"+pageNo+"/"+pageSize+"/"+receiveUserId,
            success:function (result) {
                result=JSON.parse(decodeURIComponent(result));
                if(result.code=='0'){
                    let _newData=result.data;
                    if(_newData.totalCount>0){
                        if(_newData.dataList.length<_this.data._pageSize){
                            _this.data._userMap[receiveUserId].haveMore=false;
                            _this.isHasMoreInfo(false);
                        }
                        else{
                            _this.data._userMap[receiveUserId].haveMore=true;
                            _this.data._userMap[_this.data._curReceiveUserId].count += 1;
                            _this.isHasMoreInfo(true);
                        }
                        let str="";
                        let data=_newData.dataList;
                        for (let i=0, len=data.length;i<len;i++){
                            if(data[i].sendUserId==_newData.currentUserId){
                                str+= '<ul class="messages-text-uls"><li class="messages-text-lis  meMes">'+
                                     '<h6><span> ' + data[i].sendUserName + '</span><span class="time">' + data[i].createDateStr + '</span>' +
                                    '</h6><p>' + data[i].content + '</p></li></ul>';

                            }
                            else{
                                str+= '<ul class="messages-text-uls"><li class="messages-text-lis  answer">'+
                                      '<h6><span>' + data[i].sendUserName + '</span><span class="time"> ' + data[i].createDateStr + '</span>' +
                                      '</h6><p> ' + data[i].content + '</p></li></ul>';
                            }
                        }
                        let subDomId="hisid-"+receiveUserId+_this.data._userMap[_this.data._curReceiveUserId].count;
                        let domStr=$("<div id='"+subDomId+"'></div>").html(str);
                        $("#"+domObj).prepend(domStr);
                        //获取历史消息的dom高度,实现滚动条向上滚动
                        let height=document.getElementById(subDomId).clientHeight;
                        // $("#"+domObj).scrollTop(height);
                        $('#chatMessagesList').animate({scrollTop:height+'px'},10);
                    }
                    else{
                        _this.data._userMap[receiveUserId].haveMore=false;
                        _this.isHasMoreInfo(false);
                    }
                }
            },
            error:function (msg) {
                console.log(msg)
            }
        })
    },
    isHasMoreInfo:function (bl) {
        if(bl)
            $(".chat-his-icon").html('<div class="his-get-more"><i class="fa fa-clock-o"></i> 查看历史消息</div>');
        else
            $(".chat-his-icon").html('<div class="no-have-his">没有更多消息了~</div>');
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
};
_chatMessageObj.init();