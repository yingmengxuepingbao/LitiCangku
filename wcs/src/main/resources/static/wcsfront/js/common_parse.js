/**
 * 通用解析JS 解析页面上
 * 查公共代码表
 * mes-data = '{queryParams:{queryId:"materialTypeSearch_hid",type:"WL_MATERIAL_TYPE",parentValueLow:""},contextObj:{}}'
 * 查其它表
 * mes-data = '{queryParams:{tableName:"MES_Base_Code",selectCode:"CodeNo",selectCodeName:"CodeName",appendSql:"and TypeCode=`WL_MATERIAL_TYPE` and MarkAble=`1` and IsDel=`0`"},contextObj:{}}'
 * queryId:存放下拉框的codeNo
 * type:查询值集的codeType
 * parentValueLow:如果有父值 级联查询，目前没用到，后期可以扩展
 * contextObj:存在前置校验，回调函数，只读等等，方便扩展
 * autor:cyj
 * date:2018-01-11
 */
(function($){
	if(!$.mesPlugins){
		$.mesPlugins={}
	}
	
	$.extend($.mesPlugins,{
		parse:function(){
			$("[mes-data]").each(function(){
				var str = $(this).attr("mes-data");
				var metaData=eval("("+$(this).attr("mes-data")+")");
				if(!metaData.contextObj.valueTarget){
					metaData.contextObj.valueTarget=this;
				}
				$(this).baseparse(metaData);
			});
		}
	});
	
	
	//初始化调用函数
	// $(function(){
	// 	$.messager.progress({
	// 	    title: ' ',
	// 	    msg: '正在构建页面，请稍后'
	// 	});
	// 	$.mesPlugins.parse();
	// 	$.messager.progress("close");
	// })
	
})(jQuery)