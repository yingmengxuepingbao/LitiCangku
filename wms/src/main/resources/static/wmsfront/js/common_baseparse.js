/**
 * 解析页面input元素
 * rights reserved.
 * @created 2018-01-11
 * @autor cyj
 */

 var commonSearchArr = new Array(
			"查询无结果",
			"请选择"
			);
 var language = "zh_CN";
 
(function($){
	var dataName="baseParseData";
	var $searchButton;
	//var $descLabel;
	var baseMetaData={};
	/**
	 * 初始化  自动补全
	 */
	function _parseAuto(){
		//显式处理
		if(baseMetaData.contextObj.noautocomplete){
			return;
		}
		/*
		 * 增加前置检查函数
		 * */
		if(baseMetaData.contextObj.callprepare){
			if(!baseMetaData.contextObj.callprepare()){
				return 
			}
		}
		//增加对parentValueLow的函数支持
		if(baseMetaData.queryParams.parentValueLow){
			if($.isFunction(baseMetaData.queryParams.parentValueLow)){
				baseMetaData.queryParams.parentValueLow=baseMetaData.queryParams.parentValueLow();
			}
		}
		if(baseMetaData.queryParams.appendSql){
			if($.isFunction(baseMetaData.queryParams.appendSql)){
				if(!baseMetaData.queryParams.appendSql()){
					return 
				}else{
					baseMetaData.queryParams.appendSql=baseMetaData.queryParams.appendSql();
				}
			}
		}
		baseMetaData.queryParams.appendSql = baseMetaData.queryParams.appendSql.replace(/`/g,"'");
		
		var ignorevalue = "更多......";
		var _params=$.extend({},baseMetaData.queryParams);
		
		var _baseMetaData=$.extend({},baseMetaData);
		
		//$.mpbGetHeaderPath()+"/autoCompleteBaseCode"
		 $.autocompleter('clearCache');
		 $(_baseMetaData.contextObj.valueTarget).autocompleter({
		        // marker for autocomplete matches
		        highlightMatches: true,

		        // object to local or url to remote search
		        source: $.mpbGetHeaderPath()+"/autoCompleteBaseCode/"+JSON.stringify(_params),
		        //是否缓存
		        cache:true,
		        
		        customValue:"codeNo",
		        customLabel:"codeName",

		        // custom template
		        template: '{{ codeNo }}',

		        // max results
		        limit: 20,

		        callback: function (value, index, selected) {
		        	if(value == ignorevalue){
		        		$(_baseMetaData.contextObj.valueTarget).val("");
		        		$.mdmPlugins.selectBase(_baseMetaData.queryParams,_baseMetaData.contextObj);
		        	}else{
		        		//input框增加一个data属性存放值
		        		//$(_baseMetaData.contextObj.valueTarget).attr("data",selected.codeNo);
		        		//回调函数
//			        	if(_baseMetaData.contextObj.callback){
//	            			_baseMetaData.contextObj.callback();
//	            		}
		        	}
		        }
		    });
	}
	/**
	 * 初始化 OnChange方法
	 */
	function _parseChange(){
		var _params=$.extend({},baseMetaData.queryParams);
		var _context=$.extend({},baseMetaData.contextObj);
		var changeFunction=function(){
			return function (){
				var params=_params;
				var context=_context;
				var deffer = $.Deferred();
				setTimeout(function(){deffer.resolve(); $.mdmPlugins.baseCheck(params,context);},100);
				window.baseCheckDeffer = deffer.promise();
			}
		}();
		//binding时间
			$(baseMetaData.contextObj.valueTarget).unbind("change")
			$(baseMetaData.contextObj.valueTarget).change(changeFunction);

	}
	/**
	 * 初始化 按钮弹窗功能
	 * */
	function _parseButton(){
		if(baseMetaData.queryParams){
			var _params=$.extend({},baseMetaData.queryParams);
			var _context=$.extend({},baseMetaData.contextObj);	
			var clickFunciton=function(){
				return function(){
					var params=_params;
					var context=_context;
					$.mdmPlugins.selectBase(params,context)
				}
				
			}();
			//处理普通的
			$searchButton.unbind("click");
			$searchButton.click(clickFunciton);  
		}else{
			$.messager.alert("提示","[mes-data]queryParamse属性必填！");
			return;
		}

	}
	function _addElement(){
		
		$searchButton = $("<span style='padding: 6px 1px;cursor: pointer;' class='glyphicon glyphicon-search'></span>").insertAfter($(baseMetaData.contextObj.valueTarget));
		//$descLabel = $("<label>1</label>").insertAfter($searchButton);
		var data={
				button:$searchButton
				//label:$descLabel
			};
		$(baseMetaData.contextObj.valueTarget).dataObj(dataName,data)
			
		//baseMetaData.contextObj.nameTarget=$descLabel.get(0);
	}
	
	function _parsePicker(){
		/*
		 * 增加前置检查函数
		 * */
		if(baseMetaData.contextObj.callprepare){
			if(!baseMetaData.contextObj.callprepare()){
				return 
			}
		}
		//增加对parentValueLow的函数支持
		if(baseMetaData.queryParams.parentValueLow){
			if($.isFunction(baseMetaData.queryParams.parentValueLow)){
				baseMetaData.queryParams.parentValueLow=baseMetaData.queryParams.parentValueLow();
			}
		}

		$.ajax({
			type : 'POST',
			dataType : "json",
			async :false,
			url : $.mpbGetHeaderPath()+"/getCommonBaseCode",
			data : baseMetaData.queryParams,
			success : function(data) {
				if(!data.success){
					//将表数据追加到模态框中
					alert(data.errMsg);
					return false;
				}
				var modelOrgOption = "<option value=''>-"+commonSearchArr[1]+"-</option>";
				for(var x=0;x<data.dataList.length;x++){
					org = data.dataList[x];
					if(org.codeNo == baseMetaData.queryParams.data){
						modelOrgOption = modelOrgOption + "<option value='"+org.codeNo+"' selected='true'>"+org.codeName+"</option>";
					}else{
						modelOrgOption = modelOrgOption + "<option value='"+org.codeNo+"'>"+org.codeName+"</option>";
					}
				}

				$(baseMetaData.contextObj.valueTarget).html(modelOrgOption);
				if($(baseMetaData.contextObj.valueTarget).hasClass("selectpicker")){
					$(baseMetaData.contextObj.valueTarget).selectpicker('refresh');
				}
				
			}
		});
	}
	
	$.fn.baseparse=function(_baseMetaData){
		var dataParseObj={};
		if(_baseMetaData){
			dataParseObj=$.extend(dataParseObj,_baseMetaData);
		}else{
			var metaData=eval("("+$(this).attr("mes-data")+")");
			dataParseObj=$.extend(dataParseObj,_baseMetaData);
		}
		if(!dataParseObj.contextObj){
			dataParseObj.contextObj={};
		}
		dataParseObj.contextObj.valueTarget=this.get(0);
		baseMetaData=dataParseObj;

		 if(baseMetaData.queryParams.type && !baseMetaData.queryParams.tableName){
			 _parsePicker();
		 }else{
			 if(baseMetaData.queryParams.hidButton){
				 null;
			 }else{
				 _addElement();
				 _parseButton();
			 }
			 		 
			 _parseChange();
			 //_parseAuto();
		 }
		
		_destroy();
		
	}
	
	function _destroy(){
		 //TODO
		 $searchButton=null;
		 baseMetaData={};
	}
	
})(jQuery)
