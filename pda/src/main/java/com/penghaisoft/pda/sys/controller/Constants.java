package com.penghaisoft.pda.sys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 诺华项目-常量类
 */
@Slf4j
public class Constants {
    //保存错误日志
    //扫码枪服务器1IP
    public static String SCANNING_GUN_HOST_1="172.16.31.165";
    //扫码枪服务器1端口
    public static  int SCANNING_GUN_PORT_1 = 9091;
    //扫码枪服务器1IP(2号扫码枪)
    public static String SCANNING_GUN_HOST_1_2="172.16.31.169";
    //扫码枪服务器1端口(2号扫码枪)
    public static  int SCANNING_GUN_PORT_1_2 = 9095;
    //扫码枪服务器1设备编码
    public static  String STACKERID_1="14";

    //扫码枪服务器2IP
    public static String SCANNING_GUN_HOST_2="172.16.31.166";
    //扫码枪服务器2端口
    public static  int SCANNING_GUN_PORT_2 = 9092;
    //扫码枪服务器2IP(2号扫码枪)
    public static String SCANNING_GUN_HOST_2_2="172.16.31.170";
    //扫码枪服务器2端口(2号扫码枪)
    public static  int SCANNING_GUN_PORT_2_2 = 9096;
    //扫码枪服务器2设备编码
    public static  String STACKERID_2="28";
    //扫码枪未获取到数据
    public static String SCANNING_GUN_NoRead="NoRead";

    //plc服务器地址IP
    public static String PLC_IP="172.16.31.110";
    //plc服务器端口
    public static  Integer PLC_PORT = 502;
    //PLC区块编号
    public static  Integer PLC_BLOCKNUMBER = 3;
    //PLC数据值长度
    public static  Integer PLC_VALUELENGTH = 1;
    //14_PLC偏移量1
    public static  Integer PLC_OFFSET_1 = 28;
    //28_PLC偏移量2
    public static  Integer PLC_OFFSET_2 = 56;
    //14#位置结果（外形）偏移量
    public static  Integer PLC_OFFSET_14=72;
    //28#位置结果（外形）偏移量
    public static  Integer PLC_OFFSET_28=74;

    //访问WMS地址：
    public static String WL_SERVLET_PATH = "http://172.22.0.61:18080/datahub/WL_WCS/FluxWmsJsonApi/";

    //生成随机数（流水单号）
    public static String  UUid(){
        //最大支持1-9个集群机器部署
        int machineId = 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        //有可能是负数
        if(hashCodeV < 0) {
            hashCodeV = - hashCodeV;
        }
        //System.out.println(machineId+String.format("%012d", hashCodeV));
        /* 0 代表前面补充0
         * 4 代表长度为4
         * d 代表参数为正数型
        */
        return machineId+String.format("%012d", hashCodeV);
    }
    //随机生成32位的uuid
    public static String  UUID32(){
       UUID uuid= UUID.randomUUID();
       String str =uuid.toString();
       String uuidStr=str.replace("-","");
        return uuidStr;
    }
    //随机生成8位的uuid
    public static String UUID8() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }
    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };


    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        String string = null;
        try {
            string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json字符串转成map。
     * @param str
     * @return
     */
    public static Map<String,String> strToMap(String  str) {
        Map map = null;
        try {
            map = MAPPER.readValue(str,Map.class);
            return map;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param clazz 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 流读取http请求内容
     * @param request
     * @return
     */
    public static String getHttpRequest(HttpServletRequest request) {
        String body = "";
        try {
            ServletInputStream inputStream = request.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            while (true) {
                String info = br.readLine();
                if (info == null) {
                    break;
                }
                if (body == null || "".equals(body)) {
                    body = info;
                } else {
                    body += info;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }


    public static String date(){
        Date currentTime=new Date();
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(currentTime.getTime() - 3 * 24 * 60 * 60 * 1000));
    }

    public static String date_log(){
        //设置日期格式
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df2.format(new Date());
    }
}
