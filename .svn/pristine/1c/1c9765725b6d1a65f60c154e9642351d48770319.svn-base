var pageNumber;
$('.addNewReport').click(function(){
	$('#addNewReport').css('display','block');
	$('.mask').css('display','block');
});

$('.closeWindows').click(function(){
	$(this).parent().css('display','none');
	$('.mask').css('display','none');
	$(this).parents('.parentWindows').find('input').val('');
	$(this).parents('.parentWindows').children('textarea').val('');
})
$('.closeWindowsButton').click(function(){
	$(this).parents('.parentWindows').css('display','none');
	$('.mask').css('display','none');
	$(this).parents('.parentWindows').find('input').val('');
	$(this).parents('.parentWindows').children('textarea').val('');
})
$('.previousPage').click(function(){
	if($('.currentPage').val()==1){
		return;
	}
	var page=$('.currentPage').val()-(1);
	if(page<1){
		page=1;
	}
	$('.currentPage').val(page);
	var dataPerPage =$('.dataPerPage').val();
	getList();
});

$('.nextPage').click(function(){
	if($('.currentPage').val()==pageNumber){
		return;
	}
	var page=$('.currentPage').val()-(-1);
	var dataPerPage =$('.dataPerPage').val();
	if(page<pageNumber){
		page=pageNumber;
	}
	$('.currentPage').val(page);
	getList();
});

$('#refrech').click(function(){
	var page=$('.currentPage').val();
	var dataPerPage =$('.dataPerPage').val();
	getList();
})



getList();
function getList(){
	getReportList();
}
function getReportList(){
    new doAjax({
        type:'get',
        url: $.mpbGetHeaderPath()+"/reportManagement/getReportList",
        data:{
            "currentPage":$('.currentPage').val()
        },
        callBack: function (result) {
            if(result.code=='0'){
                var dataCount=result.data.dataCount;
                $('.dataNumber').html(dataCount);
                pageNumber=parseInt(dataCount/20)+1
                $('.pageNumber').html(pageNumber);
                showReportList(result.data.dataList);
            }else{
                Confirm.show('获取报表列表失败',result.message||"获取报表列表失败");
            }
        }
    });
}
function showReportList(list){
	var table=$('.reportsTableContainer table tbody');
	table.empty();
//	$(table)[0].parentNode.removeChild(table);
//	$(table)[0].parentNode.removeChild(table);
	
	
	$(list).each(function(index,item){

		var tr=$('<tr></tr>');
		var reportName=$("<td>"+item.title+"</td>");
		// var reportUrl=$("<td>"+item.url+"</td>");
		var reportDescription=$("<td>"+item.description+"</td>");
		// tr.append(reportName).append(reportUrl).append(reportDescription);
		tr.append(reportName).append(reportDescription);
		tr.appendTo(table);
	})
}
function editReport(button){
	$('#editReport').css('display','block');
	$('.mask').css('display','block');
}
function deleteReport(button){
	Confirm.show('获取报表列表失败',result.message||"获取报表列表失败",
			{
			  '确认': {
			  	 'callback': function(){
			  	 }
			}
		}
	);
}
$('.submitNewReport').click(function(){
	var reportName=$('#addNewReport .reportName').val();
	var reportFlie=$('#addNewReport .reportFile').val();
	var reportDescription=$('#addNewReport .reportDescription').val();
	if(/.cpt$/.test(reportFlie)==false && /.frm$/.test(reportFlie)==false){
			Confirm.show('提示','请选择后缀为.cpt的报表文件');
	}
	else if(reportFlie==null||reportFlie==''||reportName==''){
			Confirm.show('提示','请填写带<span style="color:red">*</span>星号内容');
	}
	else{
		var form=new FormData(document.getElementById('fileForm'))
        new doAjax({
            url: $.mpbGetHeaderPath()+"/reportManagement/addReport",
            data:form,
            processData:false,
            contentType:false,
            callBack: function (result) {
                result=JSON.parse(decodeURIComponent(result));
                if(result.code=='0'){
                    Confirm.show('添加报表',result.message||"添加成功");
                    getList();

                }else{
                    Confirm.show('获取报表列表失败',result.message||"获取报表列表失败");
                }
            }
        });
	}
})
