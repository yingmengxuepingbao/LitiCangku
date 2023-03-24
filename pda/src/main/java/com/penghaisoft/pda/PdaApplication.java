package com.penghaisoft.pda;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.penghaisoft.pda.*.dao"})
//@EnableScheduling
public class PdaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdaApplication.class, args);
    }

}
