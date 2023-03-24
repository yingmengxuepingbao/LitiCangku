package com.penghaisoft.framework.util;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Title: CommonUtils.java
 * @Description: 存放一些公共方法
 * @author zxcq0
 * @date 2017/2/28 14:21
 * @version V1.0
 */

public class CommonUtils {

	public static final SimpleDateFormat SDF_YMD = new SimpleDateFormat("yyyyMMdd");
	
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",  
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
            "W", "X", "Y", "Z" };

	/**
	 * 获取当前时间-年月日形式
	 * @return
	 */
	public static String getCurrentYmdDate(){
		Date date = new Date();
		String sdfDate = SDF_YMD.format(date);
		return sdfDate;
	}
	
	/** 
	* @Title: generateShortUuid 
	* @Description: 短8位UUID
	* @author zhangxu 
	* @2017年9月21日:2017年9月21日:上午10:27:02
	* @param @return    
	* @return String
	* @version V0.1    
	* @throws 
	*/
	public static String generateShortUuid() {  
	    StringBuffer shortBuffer = new StringBuffer();  
	    String uuid = UUID.randomUUID().toString().replace("-", "");  
	    for (int i = 0; i < 8; i++) {  
	        String str = uuid.substring(i * 4, i * 4 + 4);  
	        int x = Integer.parseInt(str, 16);  
	        shortBuffer.append(chars[x % 0x3E]);  
	    }  
	    return shortBuffer.toString();  
	  
	} 
    /**
     * @Description: 获取一个UUID，去掉-
     * @author zxcq0
     * @date 2017/2/28 14:23
     * @param
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
    /**
     * @Description: 获取一组uuid
     * @author zxcq0
     * @date 2017/2/28 14:25
     * @param
     * @return
     */
    public static List<String> getUUIDs(int size){
        List<String> uuids = new ArrayList<String>();
        if (size>0){
            for (int i = 0; i < size; i++) {
                uuids.add(getUUID());
            }
        }
        return uuids;
    }

}