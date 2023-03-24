package com.penghaisoft.wms.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Description 常量类
 **/
@Component
public class ConstantUtil {
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
    //随机生成11位的AGV任务
    public static String UUID_AGV() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars_PLC[x % 10]);
        }
        return shortBuffer.toString();
    }
    //随机生成5位的uuid
    public static String UUID_PLC() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i <= 4; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            if(i == 0 && chars_PLC[x % 10] == "0"){
                System.out.println("chars_PLC[x % 10] ="+chars_PLC[x % 10]);
                shortBuffer.append("9");
            }else {
                shortBuffer.append(chars_PLC[x % 10]);
            }

        }
        return shortBuffer.toString();
    }
    //随机生成5位的uuid -第一位数不为0
    public static String UUID_PLC_two() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i <= 4; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            if(i == 0 && chars_PLC[x % 10] == "0"){
                System.out.println("chars_PLC[x % 10] ="+chars_PLC[x % 10]);
                shortBuffer.append("9");
            }else {
                shortBuffer.append(chars_PLC[x % 10]);
            }

        }
        return shortBuffer.toString();
    }
    //随机生成6位的uuid用于WCS生成组号
    public static String UUID_GroupId() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 6; i++) {
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
    public static String[] chars_PLC = new String[] { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9"};
}
