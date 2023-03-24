package com.penghaisoft.framework.util;

import com.penghaisoft.framework.constant.Constant.ConfigInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormat {
	
	private DateTimeFormat(){}
	/**
	 * 获取当前时间
	 * @return 格式：yyyy-MM-dd hh:mm:ss
	 * @author 秦超
	 * 2017-09-01 09:15:33
	 */
	public static String getCurrentDateFormat(){
		//获取当前时间
				Date nowDate = new Date();
				DateFormat format=new SimpleDateFormat(ConfigInfo.DATE_FORMAT);
				
				return format.format(nowDate);
	}
}
