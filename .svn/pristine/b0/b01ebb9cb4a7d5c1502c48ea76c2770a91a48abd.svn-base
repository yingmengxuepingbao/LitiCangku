$(".datainp").jeDate({
    festival: false,
    ishmsVal: false,
    initAddVal:[0],
    format: "YYYY-MM-DD",
    zIndex: 3000
});
//返回查询条件状态
$(function(){
    var searchData = $.mesGetUrlParameter("searchData");
    if ($.isNotBlank(searchData)) {
        $("#searchForm").mesSearchFormLoad(searchData);
    }
    // getLogList();
});
function getLogList(type){
    var url = $.mpbGetHeaderPath()+'/logManager/getLoginLogList';
    //获取查询条件
    var userId      = $("#userIdSearch").val();
    var userName    = $("#userNameSearch").val();
    var startTime   = $("#beginTimeSearch").val();
    var endTime     = $("#endTimeSearch").val();
    //noOrder 排序
    $("#tableDatagrid").tableDatagrid({
        url:url,
        //新增参数
        // requestType:'GET',
        // dataType:'text',
        queryParams:{
            userId      :userId,
            userName    :userName,
            startTime   :startTime,
            endTime     :endTime
        },
        columns:[
            {field:'id',checkbox:true,noOrder:true},
            {field:'userId',title:"用户ID",noOrder:true},
            {field:'name',title:"用户名",noOrder:true},
            {field:'loginIp',title:"登录IP",noOrder:true},
            // {field:'loginTime',title:"登录时间",noOrder:true},
            {field:'loginTime',title:"登录时间",noOrder:true,formatter : function(value, row) {
                var date = new Date(value);
                var dateStr = date.getFullYear()+'-'+(date.getMonth()+1)+'-'+ date.getDate()+' ';
                var hours = date.getHours()<10 ? '0'+date.getHours():date.getHours();
                var minutes = date.getMinutes()<10 ? '0'+date.getMinutes():date.getMinutes();
                var seconds = date.getSeconds()<10 ? '0'+date.getSeconds():date.getSeconds();
                dateStr = dateStr + hours+':'+minutes+':'+seconds;
                return dateStr;
            }}
        ],
        rownumbers: false,
        pager: "j-pagination",
        mutiselect:false,
    });

}

//清空查询条件，返回初始页面
function clearText(){
    $("#userNameSearch").val("");
    $("#beginTimeSearch").val("");
    $("#endTimeSearch").val("");
    $("#userIdSearch").val("");
}

var flogs_btn = 1;
$(".pull-right-btn").on("click",function(){
    if(flogs_btn)
    {
        $(this).find("span").text("展开");
        $(this).find("i").removeClass("down");
        $(".headbox").addClass("smal");
        flogs_btn = !flogs_btn;
    }
    else
    {
        $(this).find("span").text("收起");
        $(".headbox").removeClass("smal");
        $(this).find("i").addClass("down");
        flogs_btn = !flogs_btn;
    }
});
