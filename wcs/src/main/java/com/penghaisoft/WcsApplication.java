package com.penghaisoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.unit.DataSize;
import javax.servlet.MultipartConfigElement;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.penghaisoft.framework.*.model.dao","com.penghaisoft.wcs.*.model.dao"})
@EnableScheduling
public class WcsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WcsApplication.class, args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement(
            @Value("${framework.multipart.maxFileSize}")String maxFileSize,
            @Value("${framework.multipart.maxFileSize}") String maxRequestSize) {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse(maxFileSize));
        factory.setMaxRequestSize(DataSize.parse(maxRequestSize));
        return factory.createMultipartConfig();
    }
}
