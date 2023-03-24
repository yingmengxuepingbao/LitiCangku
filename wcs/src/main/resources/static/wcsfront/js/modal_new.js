/**
 * MES system created by cyj
 * rights reserved.
 * @created 2018/01/05
 * 用于解析界面table
 * options{url:查询的url地址,
 * 		   queryParams:传输参数,
 * 		   columns:[]展示字段，json字符串
 * 		   callback：回调函数 数据加载完后展示的数据
 *        }
 *        
 */

(function($){
	var $publicModalParams = {};
	//获取modal弹窗数据
	$.getPublicModal = function(trId,valueCode,chooseList,tableList,url,callback){
		var htmlModal="";//装模态框的html
		var tempChooseHtml;//装模态框中筛选条件的table +下一个table的表头 HTML
		var tableNumberList;//装模态框中数据table的 HTML
		var chooseJsonList={};//存放筛选条件的键值对以备ajas请求
		
			$("body").find("#myModal_public_new").remove();
			htmlModal= "<div class='modal fade' id='myModal_public_new'  tabindex='-1' role='dialog' aria-labelledby='myModalLabel' ia-hidden='true'>"	
		   	+"<div class='modal-dialog'>"
		   	+"<div class='modal-content'>"
		   	+"<div class='modal-header' >"
		   	+"<button type='button' class='close'  data-dismiss='modal' aria-hidden='true'>&times;</button>"
		   	+"<h4 class='modal-title' id='myModalLabel'>"+commonModalArr[0]+"</h4></div>"
		   	+"<div class='modal-body'> </div></div></div></div>";   
		   	
		 	$("body").append(htmlModal);	   	
		   	
		   	tempChooseHtml="<table id='choose_pubtable' class='table '><tr>";
			
			for(var i=0; i<chooseList.length;i++){  
				
				var choose = chooseList[i];
				 if(undefined == choose.defaultValue)
				 { 
					 tempChooseHtml+="<td class='labletxt'>"+choose.title+":</td><td><input  type='text' class='form-control' id="+choose.field+">"
				 }else{
					 if(undefined == choose.hidden){ 
						 tempChooseHtml+="<td class='labletxt'>"+choose.title+":</td><td><input  type='text' class='form-control' id="+choose.field+" value='"+choose.defaultValue+"' disabled='true'>"
					 }else{
						 if(choose.hidden=="true"){ 
							 tempChooseHtml+="<td class='labletxt'></td><td><input  class='form-control' id="+choose.field+" value='"+choose.defaultValue+"' type='hidden'>"
						 }else{
							 tempChooseHtml+="<td class='labletxt'>"+choose.title+":</td><td><input  type='text' class='form-control' id="+choose.field+" value='"+choose.defaultValue+"' disabled='true'>";
						 }
					 }
				 }
			    if(i==1||i==3){
			    	tempChooseHtml+="</tr><tr>"
			    }
			}
			
			tempChooseHtml+="<td></td><td><input type='button' class='btn btn-primary' id='searchInpt' value='"+commonModalArr[5]+"'><input type='button' class='btn  btn-primary ' id='clearInpt'  value='"+commonModalArr[6]+"'></td></tr></table>";
			tempChooseHtml+="<div id='packagetable'><input hidden='hidden' class='trId' value='"+trId+"'><h6 style='color: #e78a29'>"+commonModalArr[4]+":"+commonModalArr[3]+" </h6>";

			
			//数据表头html
			tempChooseHtml+="<table id='pubtable_list' class='table' style='word-break: keep-all; white-space: nowrap;'>";
			tempChooseHtml+="</table>";
			tempChooseHtml+="<div class='j-pagination' id='j-pagination'>";
			tempChooseHtml+="</div>";
			tempChooseHtml+="</div>";
			
		
			
		    //将筛选条件和数据表头写入模态框
			$("#myModal_public_new .modal-body").html(tempChooseHtml);
			
			//显示模态框
			$("#myModal_public_new").modal('show');
								
			//模态框查询按钮单击事件
			$("#myModal_public_new").delegate("#searchInpt","click",function(){	
				//装载筛选条件以备传给服务器请求数据
				for(var i=0; i<chooseList.length;i++){ 
				
				    var  condition=chooseList[i];
				    var  inputId=condition.field;
				    chooseJsonList[inputId]= $("#myModal_public_new").find("#"+inputId).val();					    					   
				}	
				chooseJsonList.pageSize = 5;
				//请求列表数据
				var params = {};
				params["trId"]=trId;
				params["url"] = url;
				params["chooseJsonList"] = chooseJsonList;
				params["tableList"] = tableList;
				params["valueCode"] = valueCode;
				params["callback"] = callback;
				//180731改为通用table通用查询
				$publicModalParams = {};
				$.extend($publicModalParams,params);
				getPublicData_list(params);
				
			});		
			
			//筛选条件文本框清空数据
			$("#myModal_public_new").delegate("#clearInpt","click",function(){
					
				for(var i=0; i<chooseList.length;i++){ 
					
				    var  condition=chooseList[i];
				    var  inputId=condition.field;
				    if(undefined == condition.defaultValue)
				    $("#myModal_public_new").find("#"+inputId).val("");					    					   
				}	
				
				//装载筛选条件以备传给服务器请求数据
				for(var i=0; i<chooseList.length;i++){ 
				
				    var  condition=chooseList[i];
				    var  inputId=condition.field;
				    chooseJsonList[inputId]= $("#myModal_public_new").find("#"+inputId).val();					    					   
				}	
				chooseJsonList.pageSize = 5;
				//请求列表数据
				var params = {};
				params["trId"]=trId;
				params["url"] = url;
				params["chooseJsonList"] = chooseJsonList;
				params["tableList"] = tableList;
				params["valueCode"] = valueCode;
				params["callback"] = callback;
				//180731改为通用table通用查询
				getPublicData_list(params);
			});	
			//自动触发
			$("#myModal_public_new #searchInpt").trigger("click");
		
	};
	//调用公共查询组件
	function getPublicData_list(params){
		var trId = params.trId;
		var url = params.url;
		var chooseJsonList = params.chooseJsonList;
		var tableList = params.tableList;
		var valueCode = params.valueCode;
		var callback =  params.callback;
		
		$("#myModal_public_new #pubtable_list").tableDatagrid({
			url : url,
			queryParams : chooseJsonList,
			columns : tableList,
			rownumbers : false,
			pager : "myModal_public_new #j-pagination",
			mutiselect : false,
			callback:getPublicData
		});
	}
	//回调函数 反显选中字段内容
	function getPublicData(){
		
		var trId = $publicModalParams.trId;
		var url = $publicModalParams.url;
		var chooseJsonList = $publicModalParams.chooseJsonList;
		var tableList = $publicModalParams.tableList;
		var valueCode = $publicModalParams.valueCode;
		var callback =  $publicModalParams.callback;
		
		$("#myModal_public_new #pubtable_list tbody tr").on("dblclick",function(e){
			
				var _value = $(this).find("#td_"+valueCode).get(0).innerText;
				$("#"+trId).val(_value);
		        //回调函数
		        if($.isFunction(callback)){
		        	callback();
		        }		
				//end
		        e = e || window.event;
		        e.stopPropagation();
		        //modal隐藏
		        $('#myModal_public_new').modal('hide');
			
		});
		
		
	}
	
})(jQuery)