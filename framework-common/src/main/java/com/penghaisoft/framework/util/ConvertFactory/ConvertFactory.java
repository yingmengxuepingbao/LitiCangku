package com.penghaisoft.framework.util.ConvertFactory;

/** 
* 格式转化工厂类
* @author 刘立华
* @date 2017-08-31 
*/  
public class ConvertFactory {
	
	private ConvertFactory(){}
	/**
	 * 构建JSON转换对象
	 * @return JsonConvert 对象
	 */
	public static Convert buildJSON(){
		return new JsonConvert();
	}

	/**
	 * 构建XML转换对象
	 * @return JsonConvert 对象
	 */
	public static Convert buildXML(){
		return new XMLConvert();
	}	
	
} 
