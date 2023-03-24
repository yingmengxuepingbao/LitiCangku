package com.penghaisoft.framework.util;

/**
 * 数据查询页码转换工具
 * @author 秦超
 * 2017-08-29 10:17:28
 */
public class PageNumberTransfermation {

	public static final char UNDERLINE='_';

	public static final String DATA_LIST = "dataList";
	public static final String PAGE_NO = "pageNo";
	public static final String PAGE_SIZE = "pageSize";
	public static final String TOTAL_COUNT = "totalCount";

	private PageNumberTransfermation(){}
	/**
	 * 数据当前页码转换为sql语句查询起始位置
	 * @param pageNumber 当前页码
	 * @param numberOnePage 每页条数
	 * @return sql查询起始位置
	 * @author 秦超
	 * 2017-08-29 10:19:15
	 */
	public static int pageNumberToSelectStartNumber(int pageNumber, int numberOnePage){
		return (pageNumber-1)*numberOnePage;
	}
	
	/**
	 * 数据当前页码转换为list分页参数结束位置
	 * @author 徐超
	 * @Date 2017年9月7日 上午9:22:13
	 * @param pageNumber
	 * @param numberOnePage
	 * @return
	 */
	public static int pageNumberToSelectEndNumber(int pageNumber, int numberOnePage){
		return pageNumber*numberOnePage;
	}


	public static String camelToUnderline(String param){
		if (param==null||"".equals(param.trim())){
			return "";
		}
		int len=param.length();
		StringBuilder sb=new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c=param.charAt(i);
			if (Character.isUpperCase(c)){
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	/*
	 * @Author 张旭
	 * @Description  下划线转驼峰
	 * @Date 9:12 2018/9/21
	 * @Param [param]
	 * @return java.lang.String
	 **/
	public static String underlineToCamel(String param){
		if (param==null||"".equals(param.trim())){
			return "";
		}
		int len=param.length();
		StringBuilder sb=new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c=param.charAt(i);
			if (c==UNDERLINE){
				if (++i<len){
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}


}
