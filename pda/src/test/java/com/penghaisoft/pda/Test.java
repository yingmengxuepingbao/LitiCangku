package com.penghaisoft.pda;

/**
 * @Description
 * @Author zhangxin
 * @Date
 **/
public class Test {
    public static void main(String[] args) {
        String srt ="016761279768158110SFUD3172405003050";
        //768158
        String materialsCode=srt.substring(9,15);
        //SFUD3
        String batchNo=srt.substring(18,23);
        System.out.println(materialsCode);
        System.out.println(batchNo);
    }
}
