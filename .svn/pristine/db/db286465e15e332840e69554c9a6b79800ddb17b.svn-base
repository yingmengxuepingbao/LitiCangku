package com.penghaisoft.pda.common;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Description CommonUtil
 * @Auther zhangxu
 * @Date 2020/1/16 17:37
 **/
public class CommonUtil {
    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

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

    public static JSONObject genResultFromResp(Resp resp){
        JSONObject result = new JSONObject();
        result.put("code",Integer.parseInt(resp.getCode()));
        result.put("msg",resp.getMessage()==null?"":resp.getMessage());
        result.put("data",resp.getData()==null?"":resp.getData());
        return result;
    }

    public static JSONObject genSucessResultWithData(Object data){
        JSONObject result = genSucessResult();
        result.put("data",data);
        return result;
    }

    public static JSONObject genSucessResult(){
        JSONObject result = new JSONObject();
        result.put("code",1);
        return result;
    }
    public static JSONObject genSucessResult(String msg){
        JSONObject result = genSucessResult();
        result.put("msg",msg);
        return result;
    }
    public static JSONObject genErrorResult(Integer errorCode,String errorMsg){
        JSONObject result = new JSONObject();
        result.put("code",errorCode);
        result.put("msg",errorMsg);
        return result;
    }
    public static JSONObject genErrorResult(String errorMsg){
        return genErrorResult(0,errorMsg);
    }
    public static JSONObject genErrorResult(){
        return genErrorResult(0,"");
    }
}
