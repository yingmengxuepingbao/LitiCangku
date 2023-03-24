/*
 *<input type="text" class='mes-checkbox' id="csgKeyaccountFlag" name="csgKeyaccountFlag" checkedvalue='1' uncheckedvalue='0'>
 * 用于checkbox 给checkbox赋值 定义class之后自动触发
 * @created 2018/5/23 cyj
 */

(function($){
	function _parseCheckBox1(){
		var checkedValue=$(this).attr("checkedvalue");
		var unCheckedValue=$(this).attr("uncheckedvalue");
		var value=$(this).val();
		if(!value||value==""){
			value="";
		}
		var $input=$(this);
		var id=$(this).attr("id");
		var name=$(this).attr("name");
		var $checkBox=$("<input type='checkBox'></input>");
		
		$checkBox.insertBefore(this);
		//绑定事件
		$checkBox.click(function(){
			if($(this).prop('readOnly')){
				return false;
			}
			if($(this).prop('checked')){
				$input.val(checkedValue);
			}else{
				$input.val(unCheckedValue);
			}
			$input.click();
			
		});
		if(value==checkedValue){
			$checkBox.prop('checked',true);
		}else{
			$checkBox.prop('checked',false);
			value=unCheckedValue;
		}
		//绑定计时器
		$(this).everyTime('500ms',function(){
			if($input.prop("disabled")||$input.prop("readOnly")){
				$checkBox.prop("disabled",true);
				$checkBox.prop("readonly",true);
			}else{
				$checkBox.prop("disabled",false);
				$checkBox.prop("readonly",false);
			}
			//每秒中执行以下勾选操作
			if($input.val()==checkedValue&&!$checkBox.prop('checked')){
				$checkBox.prop('checked',true);
				return
			}
			if($input.val()==unCheckedValue&&$checkBox.prop('checked')){
				$checkBox.prop('checked',false);
				return
			}
			if($input.val()!=unCheckedValue&&$input.val()!=checkedValue){
				$input.val(unCheckedValue);
				$checkBox.prop('checked',false);
				return
			}
		});
	}
	function _initparse(){
		//自动读取有 mes-checkbox class的checkbox  自动生成绑定的input
		$('.mes-checkbox').each(function(){
			_parseCheckBox1.call(this);
		});
	}
	$(function(){
		_initparse();
	});
	$.fn.mescheckbox=function(){
		_parseCheckBox1.call(this);
		return this;
	}
})(jQuery)
