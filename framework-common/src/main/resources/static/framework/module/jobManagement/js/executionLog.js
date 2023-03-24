//返回查询条件状态
$(function(){
    var searchData = $.mesGetUrlParameter("searchData");
    if ($.isNotBlank(searchData)) {
        $("#searchForm").mesSearchFormLoad(searchData);
    }
    // getExecutionDetailLogList();
});
function getExecutionLogList(type){
    var url = $.mpbGetHeaderPath()+'/job/jobExecutionLogList';
    //获取查询条件
    var jobName = $("#jobNameSearch").val();

    $("#tableDatagrid").tableDatagrid({
        url:url,
        //新增参数
        // requestType:'GET',
        // dataType:'text',
        queryParams:{
            jobName:jobName,
        },
        columns:[
            // {field:'id',checkbox:true},
            {field:'jobName',title:"任务名称",sort:false},
            {field:'jobType',title:"任务类型",sort:false},
            {field:'jobClass',title:"任务执行类",sort:false},
            {field:'expression',title:"表达式",sort:false}
        ],
        rownumbers: false,
        pager: "j-pagination",
        mutiselect:false,
    });

}

//清空查询条件，返回初始页面
function clearText(){
    $("#jobNameSearch").val("");
    // getCodeDefList("search");
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