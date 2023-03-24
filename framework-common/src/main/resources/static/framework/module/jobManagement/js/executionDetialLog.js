//返回查询条件状态
$(function(){
    var searchData = $.mesGetUrlParameter("searchData");
    if ($.isNotBlank(searchData)) {
        $("#searchForm").mesSearchFormLoad(searchData);
    }
    // getExecutionDetailLogList();
});
function getExecutionDetailLogList(type){
    var url = $.mpbGetHeaderPath()+'/job/jobExecutionDetailLogList';
    //获取查询条件
    var jobName = $("#jobNameSearch").val();
    var jobClass = $("#jobClassSearch").val();
    var expression = $("#expressionSearch").val();

    //noOrder 排序
    $("#tableDatagrid").tableDatagrid({
        url:url,
        //新增参数
        // requestType:'GET',
        // dataType:'text',
        queryParams:{
            jobName:jobName,
            jobClass:jobClass,
            expression:expression
        },
        columns:[
            {field:'id',checkbox:true},
            {field:'jobName',title:"任务名称",className:'codeNo',noOrder:true},
            {field:'jobType',title:"任务类型",noOrder:true},
            {field:'jobClass',title:"任务执行类"},
            {field:'expression',title:"表达式"},
            {field:'executeStatus',title:"执行状态",noOrder:true,formatter: function(value, row) {
                var text='未定义'
                if(value=='0'){
                    text = '执行开始';
                }else if(value=='1'){
                    text = '执行结束';
                }
                return text;
            }},
            {field:'executeTime',title:"操作时间",formatter : function(value, row) {
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
        // callback:callback
    });

}
function callback(){
    //---------------绑定点击事件----------------------------------------------------------------
    $(".editBtn").on("click",function(event){
        var id = $(this).parent().parent().find(".id").val();
        window.location=$.mpbGetHeaderPath() + "/codeDefEdit?flag=edit&id="+id;
        event.stopPropagation();
    })
    //---------end----------------------------------------------------------------------------

}

//清空查询条件，返回初始页面
function clearText(){
    $("#jobNameSearch").val("");
    $("#jobClassSearch").val("");
    $("#expressionSearch").val("");
    // getCodeDefList("search");
}

function  exportFile(){
    var codeName = $("#codeNameSearch").val();
    var codeNo = $("#codeNoSearch").val();
    var typeName = $("#typeNameSearch").val();
    var typeCode = $("#typeCodeSearch").val();
    window.location=$.mpbGetHeaderPath() +'/codeDefDownLoad?codeName='+codeName+'&codeNo='+codeNo+'&typeName='+typeName+'&typeCode='+typeCode;
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