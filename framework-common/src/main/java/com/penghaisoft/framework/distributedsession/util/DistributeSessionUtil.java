package com.penghaisoft.framework.distributedsession.util;


import java.text.SimpleDateFormat;
import java.util.Random;

public class DistributeSessionUtil {

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SessionConstant.DATE_FORMAT_STR);

    /**
     * 获取随机的sessionId
     * @param sessionLength
     * @return
     */
    public static String getRandomSessionId(int sessionLength){
        Random random = new Random();
        StringBuilder randomStr = new StringBuilder();

        // 根据sessionLength生成相应长度的随机字符串
        for(int i = 0; i < sessionLength; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";

            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                randomStr.append((char)(random.nextInt(26) + temp));
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                randomStr.append(String.valueOf(random.nextInt(10)));
            }
        }

        return randomStr.toString();
    }
}
