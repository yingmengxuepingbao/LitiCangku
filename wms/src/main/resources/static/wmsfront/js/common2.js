
$(window).load(function() {
	//页面中文本失去焦点过滤特殊字符
	$("body").on("blur","input,textarea",function(){
		var s = $(this).val();
		function stripscript(s){ 
			var str = "[<>''\\[\\]<>\\\\[\r\n]";
			var pattern = new RegExp(str) ;
			var rs = ""; 
			for (var i = 0; i < s.length; i++) { 
				rs = rs+s.substr(i, 1).replace(pattern, ''); 
			} 
			return rs;
		}
		$(this).val(stripscript(s));
	});
	
});

function doReturn(action){
	//返回查询条件状态
	var searchData = $.mesGetUrlParameter("searchData");
	window.location=$.mpbGetHeaderPath()+"/"+action+"?searchData="+searchData;
}

$(document).ready(function() {
	$("#searchButton").on("click",function(){
		this.disabled = 'disabled';
		setTimeout(function() {
			$("#searchButton").removeAttr("disabled");
		}, 2000);//2秒后才能点击
										
	});
	
	$("#clearTextButton").on("click",function(){
		this.disabled = 'disabled';
		setTimeout(function() {
			$("#clearTextButton").removeAttr("disabled");
		}, 2000);//2秒后才能点击
										
	});
 });
/**
 * MES system created by cyj
 * rights reserved.
 * @created 2018/01/05
 * 获取请求头路径
 *        
 */
$.mpbGetHeaderPath = function() {
	var strFullpath = window.document.location.href;
	var strPath = window.document.location.pathname;
	var pos = strFullpath.indexOf(strPath);
	var prePath = strFullpath.substring(0, pos);
	var enName = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
	return prePath + enName;
};
/**
 * MES system created by cyj
 * rights reserved.
 * @created 2018/04/16
 * 增加通用组件封装
 *        
 */
(function($){
	$.fn.mesSearchFormToJsonString = function(){
		if(!this.is("form")){
			alert("此函数只针对form控件使用");
			return;
		}
		var form = this;
		var result = "";
		$.each($(form).find(":input"),function (i,item){
			if($.isNotBlank($(item).val()) && $(item).val().indexOf('"')!=-1){
				result = result + "\"" + $(item).attr("id") + "\":'" + $(item).val()+"',";
			}else{
				result = result + "\"" + $(item).attr("id") + "\":\"" + $(item).val()+"\",";
			}
		})
		if($.isNotBlank(result)){
			result= result.substring(0,result.length-1);
		}
		//result = "{" + result + "}";
		return result;
	};

	//获取参数值(根据key获取value)
	$.mesGetUrlParameter = function(key) {
		var str = window.location.search;//从当前URL的?号开始的字符串
		num = str.indexOf("&" + key + "=") != -1 ? str.indexOf("&" + key + "=")
				: str.indexOf("?" + key + "=");//indexOf(string) 如果要检索的字符串值没有出现，则返回 -1
		if (num == -1) {
			return null;
		}
		str = str.substr(num + 1);
		var arrtmp = str.split("&");
		num = arrtmp[0].indexOf("=");
		if (num == -1) {
			return null;
		}
		return decodeURIComponent(arrtmp[0].substr(num + 1));
	};
	// 判断是否为空
	$.isNotBlank = function(value) {
		if (value != undefined && value != "undefined" && value != null
				&& value != "null" && value != "") {
			return true;
		}
		return false;
	};
	$.fn.mesSearchFormLoad = function(data) {
		data = eval('('+ "{" +data+ "}" +')');
		$(this).formload(data);
	};
	$.fn.formload=function(data){
		//限定只有form能使用此功能
		if(!$(this).is("form")){
			return
		}
		if(!data||data==""||data=={}){
			return
		}
		for(var dataname in data){
			var _childindex="#"+dataname;
			var $_child=$(this).find(_childindex);
			if(!$_child||$_child.length==0){
				//如果根据ID查找不到 那就根据name查一遍
				_childindex="[name="+dataname+"]"
				$_child=$(this).find(_childindex);
				if(!$_child||$_child.length==0){
					continue;
				}
			}
			if($_child.hasClass("easyui-textbox")){
				$_child.textbox('setValue',data[dataname]);
			}else if($_child.hasClass("easyui-numberbox")){
				$_child.numberbox('setValue',data[dataname]);
			}else if($_child.hasClass("easyui-datebox")){
				$_child.datebox('setValue',data[dataname]);
			}else if($_child.hasClass("easyui-combobox")){
				$_child.combobox('setValue',data[dataname]);
			}else if($_child.hasClass("Wdate")){//时间格式 触发blur事件
				var date = data[dataname];
				if(date&&date.indexOf('T') > -1){
					date=date.split("T")[0]
				}
				$_child.val(date);
			}else{
				$_child.val(data[dataname])

			}
		}
	};
	//清空form中的值
	$.fn.clearform = function(){
		if(!this.is("form")){
			alert("此函数只针对form控件使用");
			return;
		}
		var form = this;
		var result = "";
		$.each($(form).find(":input"),function (i,item){
			if($(item)[0].type != "radio" && $(item)[0].type != "checkbox"){
				if($.isNotBlank($(item).val())){
					$(item).val("");
				}
			}
		})
		
	};
	$.mesConfirm = function(opts){
		var def = {
				title : MESResource["common.notice"],
				content : "",
				callback : function(){}
			};
			opts = $.extend(true, def, opts);
		$.messager.confirm(opts.title,opts.content,
				function(r) {
					if (r) {
						opts.callback(r);
					}
				});
	}
	//返回编码规则
	$.getRuleCode = function(ruleCode){
		var rc = "";
		$.ajax({
			   type: "POST",
			   dataType:"json",
			   url: $.mpbGetHeaderPath()+"/getCommonRule",
			   data: {
				   "ruleCode":ruleCode
			   },
			   success: function(data){
				   if(data.success){
					   rc = data.ruleCode;
				   }else{
					   $.messager.alert(MESResource["common.notice"],
								MESResource.pageConfig["pageConfig.colum.addRuleError"]);
					   return false;
				   }
			   }
			});
		return rc;
	}
	//初始化组件
	$.baseParseStr = function(formName){
		$('#'+formName+' [mes-data]').each(function(){
			var x= $(this).attr('mes-data');
			var metaData=eval("("+x+")");
			$(this).baseparse(metaData);
		});
		$('.datainp').each(function(){
			if($.isNotBlank($(this).attr('placeholder'))){
				if($(this).attr('placeholder') == 'YYYY-MM-DD hh:mm:ss'){
					$(this).jeDate({
				        festival: false,
				        format: "YYYY-MM-DD hh:mm:ss",
				        zIndex: 3000
				    });
				}else{
					$(this).jeDate({
				        festival: false,
				        format: "YYYY-MM-DD",
				        zIndex: 3000
				    });
				}
			}
		});
		
	}
	//返回中文还是英文
	$.getLangName = function(nameCn,nameEn,lang){
		
		if(lang == "zh_CN"){
			return nameCn;
		}else if(lang == "en_US"){
			return nameEn;
		}else{
			return "";
		}
	}
	//返回不同的控件类型
	$.returnFormElement = function(searchList){
		//字符
		if(searchList.columType == 0){
			//文本框
			if(searchList.widgetType == 0){
				return "<input id='"+searchList.columAlias+"' name='"+searchList.columAlias+"' type='text' class='form-control'>";
			//下拉框
			}else if(searchList.widgetType == 1){
				return "<select id='"+searchList.columAlias+"'  class='form-control' mes-data = '"+searchList.baseCode+"'>"
			    		+"</select>";
			//级联下拉框
			}else if(searchList.widgetType == 2){
			
			//默认弹出窗体
			}else if(searchList.widgetType == 3){
				return "<input id='"+searchList.columAlias+"' name='"+searchList.columAlias+"' type='text' class='form-control' mes-data = '"+searchList.modalParam+"' >";
			//自定义弹出窗体
			}else if(searchList.widgetType == 4){
				var htmlStr = "<input id='"+searchList.columAlias+"' name='"+searchList.columAlias+"' type='text' class='form-control'";
				if(searchList.readOnlyProperty == '1'){
					htmlStr += " readonly='readonly' ";
				}
				htmlStr += "><span style='padding: 6px 1px;cursor: pointer;' class='glyphicon glyphicon-search' ";
				htmlStr += "onclick='"+searchList.modalEvent+"();'";
				htmlStr += "></span>";
				return htmlStr;
			}else{
				return "";
			}
		//日期
		}else if(searchList.columType == 1){
			return "<input id='"+searchList.columAlias+"' name='"+searchList.columAlias+"' type='text' class='form-control datainp wicon' placeholder='YYYY-MM-DD'>";
		}else{
			return "";
		}
	}
	//判断小数位数
	$.blurNum = function(obj,num){
		var input = $(obj);  
	    var v = $.trim(input.val());  
	    if(num == "" || num == "0"){
	    	var reg = eval("/^\\d+?$/"); 
	    }else{
	    	var reg = eval("/^\\d+(\\.\\d{0,"+num+"})?$/"); 
	    }
	    
	    if(v != null && v != ""){
	        if (!reg.test(v)) {  
	            var error = "<span class='help-block' style='color: red'>"+MESResource["format.error"]+"</span>";
	            input.val("");  
	            input.after(error);
	        }  
	    }
	}
})(jQuery);

function selectOneRow(tableId){
	if ($("#"+tableId+" tbody tr[class='table-tr-active']")) {
		if ($("#"+tableId+" tbody tr[class='table-tr-active']").length != 1) {
			return false;
		}
	} else {
		return false;
	}
	return true;
}