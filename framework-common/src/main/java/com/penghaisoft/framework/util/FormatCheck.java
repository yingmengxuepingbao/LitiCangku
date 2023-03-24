package com.penghaisoft.framework.util;

/**
 * 格式校验工具类
 * @author 徐超
 * @Date 2017年9月6日 下午2:50:52
 */
public class FormatCheck {

	private FormatCheck(){}
	/**
	 * 检查字符串是否由字母组成
	 * @author 徐超
	 * @Date 2017年9月6日 下午2:53:24
	 * @param toBeCheckedString
	 * @return
	 */
	public static boolean checkStringIsMadeUpOfLetter(String toBeCheckedString){
		
		return  toBeCheckedString.matches("[a-zA-Z]+");
	}

	/**
	 *
	 * @date: 2018年3月16日 上午11:20:41
	 * @author: cyj
	 * @Title: NullToStr
	 * @Description: null转化为空字符串
	 * @param
	 * @return String    返回类型
	 */
	public static final String NullToStr(Object obj) {

		if (obj == null) {
			return "";
		} else if ((obj.toString()).equals("null")) {
			return "";
		} else if ((obj.toString()).equals("")) {
			return "";
		} else {
			return obj.toString();
		}

	}

}
