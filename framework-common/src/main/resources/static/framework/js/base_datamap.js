/**
 * 
 * @created 2018-01-12
 * 
 */
(function($){
	/*
	 * _dataogj 用于保存对象
	 * _dataogj[dataname]=dataobj
	 */
	var namePerfix='_mes_dataobj_';
	
	var _dataogj={
			
	}
	$.fn.dataObj=function(dataname){
		if(this.length>1){
			return
		}
		var datanameall=namePerfix+dataname;
		if(arguments.length>1){
			var id=getId();
			this.data(datanameall,id);
			_dataogj[id]=arguments[1];
			return;
		}
		return _dataogj[this.data(datanameall)];
	}
	$.fn.delDataObj=function(dataname){
		var datanameall=namePerfix+dataname;
		
		delete  _dataogj[this.data(datanameall)];
	}
	
	
	function getId(){
		var result=makeID();
		if(_dataogj[result]){
			return arguments.callee();
		}else{
			return result;
		}
	}
	
	
	
	function makeID() {
	    var S4 = function () {
	        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
	    };
	    return (S4() + S4() + "-" + S4());
	}
})(jQuery)