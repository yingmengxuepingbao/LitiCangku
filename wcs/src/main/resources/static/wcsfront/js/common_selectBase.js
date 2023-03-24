/**
 * 解析页面input元素
 * rights reserved.
 * @created 2018-01-12
 * @autor cyj
 */

(function($,window){
	var myDateTime1 = "";
	var thePageNum;
	var everyPageSize;
	var totalCount;
	//获取modal弹窗数据
	function getPublicModal_new(queryParams){
		var htmlModal="";//装模态框的html
		var tempChooseHtml;//装模态框中筛选条件的table +下一个table的表头 HTML

			$("body").find("#myModal_public").remove();
			htmlModal= "<div class='modal fade' id='myModal_public'  tabindex='-1' role='dialog' aria-labelledby='myModalLabel' ia-hidden='true'>"	
		   	+"<div class='modal-dialog'>"
		   	+"<div class='modal-content'>"
		   	+"<div class='modal-header' >"
		   	+"<button type='button' class='close'  data-dismiss='modal' aria-hidden='true'>&times;</button>"
		   	+"<h4 class='modal-title' id='myModalLabel'>筛选结果</h4></div>"
		   	+"<div class='modal-body'> </div></div></div></div>";   
		   	
		 	$("body").append(htmlModal);	   	
		   	
		   	tempChooseHtml="<table id='choose_pubtable' class='table '><tr>";
			
			
			tempChooseHtml+="<td class='labletxt'>值</td><td><input  type='text' class='form-control' id='CodeNo'>";
			tempChooseHtml+="<td class='labletxt'>描述</td><td><input  type='text' class='form-control' id='CodeName'>";

			tempChooseHtml+="</tr><tr>"

			tempChooseHtml+="<td colspan='3'></td><td align='left'><input type='button' class='btn btn-primary' id='searchInpt' value='查询'><input type='button' class='btn  btn-primary ' id='clearInpt'  value='清空'></td></tr></table>";
			tempChooseHtml+="<div id='packagetable'><h6 style='color: #e78a29'>注:请双击选择</h6>";
			
			//数据表头html
			tempChooseHtml+="<table id='pubtable_list' class='table' style='word-break: keep-all; white-space: nowrap;'>";
			tempChooseHtml+="</table>";
			tempChooseHtml+="<div class='j-pagination' id='j-pagination'>";
			tempChooseHtml+="</div>";
			tempChooseHtml+="</div>";
		
			
		    //将筛选条件和数据表头写入模态框
			$("#myModal_public .modal-body").html(tempChooseHtml);
			
			//显示模态框
			$("#myModal_public").modal('show');
								
			//模态框查询按钮单击事件
			$("#myModal_public").delegate("#searchInpt","click",function(){
				
				//请求列表数据
				var params = {};
				$.extend(true,params,queryParams);
				params["CodeNo"] = $("#myModal_public").find("#CodeNo").val();
				params["CodeName"] = $("#myModal_public").find("#CodeName").val();
				params["pageSize"] = 5;
				//180731改为通用table通用查询
				getPublicData_list_new(params);
				
			});		
			
			//筛选条件文本框清空数据
			$("#myModal_public").delegate("#clearInpt","click",function(){
					
				$("#myModal_public").find("#CodeNo").val("");
				$("#myModal_public").find("#CodeName").val("");
				
				//请求列表数据
				var params = {};
				$.extend(true,params,queryParams);
				params["CodeNo"] = $("#myModal_public").find("#CodeNo").val();
				params["CodeName"] = $("#myModal_public").find("#CodeName").val();
				params["pageSize"] = 5;
				//180731改为通用table通用查询
				getPublicData_list_new(params);
			});	
			//自动触发
			$("#myModal_public #searchInpt").trigger("click");
	};
	//调用公共查询组件
	function getPublicData_list_new(params){
		params.appendSql = params.appendSql.replace(/`/g,"'");
		
		$("#myModal_public #pubtable_list").tableDatagrid({
			url : $.mpbGetHeaderPath() + "/getCommonTabCode",
			queryParams : params,
			columns : [ {
				field : 'codeNo',
				title : '值'
			}, {
				field : 'codeName',
				title : '描述'
			}],
			rownumbers : false,
			pager : "myModal_public #j-pagination",
			mutiselect : false,
			callback:getPublicData_new
		});
	}
	//回调函数 反显选中字段内容
	function getPublicData_new(){
		
		$("#myModal_public #pubtable_list tbody tr").on("dblclick",function(e){
			
				var codeNo = $(this).find("#td_codeNo").get(0).innerText;
				$(contextObj.valueTarget).val(codeNo);		
				//回调函数
				if($.isFunction(contextObj.callback)){
					contextObj.callback();
				}
				//end
		        e = e || window.event;
		        e.stopPropagation();
		        //modal隐藏
		        $('#myModal_public').modal('hide');
			
		});
		
		
	}
	
	
	//------------------------------------------------------------------------------------------------------------------------------
	var contextObj={};
	
	var queryParams={};
	
	if(!$.mdmPlugins){
		$.mdmPlugins={}
	}
	$.extend($.mdmPlugins,{
		selectBase:function(params,context){
			queryParams={};
			contextObj={};
			$.extend(queryParams,params);
			$.extend(contextObj,context);
			/*
			 * 增加对目标控件的可用性检查
			 */
			if($(contextObj.valueTarget).prop("disable")
					||$(contextObj.valueTarget).prop("readOnly")){
				return
			}
			/*
			 * 增加前置检查函数
			 * */
			if($.isFunction(contextObj.callprepare)){
				if(!contextObj.callprepare()){
					return 
				}
			}
			/*
			 * 
			 */
			
			//增加对parentValueLow的函数支持
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
		
			//调用模态窗口函数

			getPublicModal_new(queryParams);
		}
	});

})(jQuery,window)
