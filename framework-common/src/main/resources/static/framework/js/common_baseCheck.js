/**
 * 解析页面失去焦点时判断
 * rights reserved.
 * @created 2018-01-11
 * @autor cyj
 */

(function($){
	var _queryUrl=$.mpbGetHeaderPath()+"/checkCommonBaseCode";
	function _init(){
		
	}
	function _check(){
		if($(contextObj.valueTarget).length==0){
			return
		}	
		_normalcheck();

		
	}
	function _normalcheck(){
		if($(contextObj.valueTarget).val()==""){
//			$(contextObj.valueTarget).attr("data","");
			return;
		}
		var result = _queryName($(contextObj.valueTarget).val());
		var res = $.parseJSON(result);
		if(!res.success){
			$.messager.alert("提示","接口查询失败！");
			$(contextObj.valueTarget).val("");
			//input框增加一个data属性存放值 清空
//    		$(contextObj.valueTarget).attr("data","");
		}else{
			if(res.totalCount.totalCount!='1'){
				$.messager.alert("提示","不存在的编码！");
				$(contextObj.valueTarget).val("");
				//input框增加一个data属性存放值 清空
//	    		$(contextObj.valueTarget).attr("data","");
			}else{
				if(contextObj.callback){
					contextObj.callback();
				}
			}
		}

		
	}
	
	function _queryName(value){
		queryParams.value=value;
		queryParams.appendSql = queryParams.appendSql.replace(/`/g,"'");
		$.ajaxSetup({
			  async: false
		});
		var result={};
		$.post(_queryUrl,
				queryParams,
				function(data){
				 result=data;
			});
		$.ajaxSetup({
			  async: true
		});
		return result;
	}
	/*
	 * queryParams{ 传输后台的附加参数
	 *  }
	 */
	var queryParams={};
	
	/**
	 * contextObj{ valueTarget【检查取值的地址】 nameTarget 【返回名称的地址】 callback
	 * 【操作完毕之后的返回函数】 }
	 */
	var	contextObj={};
	
	if(!$.mdmPlugins){
		$.mdmPlugins={}
	}
	$.extend($.mdmPlugins,{
		baseCheck:function(params,context){

			queryParams={};
			contextObj={};
			$.extend(queryParams,params);
			$.extend(contextObj,context);
			if(queryParams.parentValueLow){
				if($.isFunction(queryParams.parentValueLow)){
					queryParams.parentValueLow=queryParams.parentValueLow();
				}
			}
			//增加对appendSql的函数支持
			if(queryParams.appendSql){
				if($.isFunction(queryParams.appendSql)){
					if(!queryParams.appendSql()){
						return 
					}else{
						queryParams.appendSql=queryParams.appendSql();
					}
				}
			}

			/*
			 * 增加前置检查函数
			 * */
			if(contextObj.callprepare){
				if(!contextObj.callprepare()){
					return 
				}
			}
			
			_check();
		}
	});
})(jQuery)
