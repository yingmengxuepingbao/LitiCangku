package com.penghaisoft.wms;

//import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class WmsApplicationTests {

    //@Test
    void contextLoads() {
    }


    public static void main(String[] args) {
        String str = "1,2,3,4,5,6,7";
        List list = new ArrayList(Arrays.asList(str.split(",")));

        String code =  "020301";
        //库位编码截取最后一位.
        String number = code.substring(code.length()-1);
        System.out.println("库位编码截取最后一位:"+number);
        // list是否包含此数字
        for (int i=0;i<list.size();i++){
            System.out.print(list.get(i) +" ");
        }

        if(list.contains(number)){
            for(int i=0 ; i<list.size() ;i++){
                if(number.equals(list.get(i))){
                    list.remove(i);
                }
            }
        }
        for (int i=0;i<list.size();i++){
            System.out.print(list.get(i)+"-");
        }
    }
}
