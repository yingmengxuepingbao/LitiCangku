//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft;

import javax.servlet.MultipartConfigElement;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.unit.DataSize;

@SpringBootApplication
@MapperScan(
        basePackages = {"com.penghaisoft.framework.*.model.dao", "com.penghaisoft.wms.*.model.dao"}
)
@EnableScheduling
public class WmsApplication {
    public WmsApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(WmsApplication.class, args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement(@Value("${framework.multipart.maxFileSize}") String maxFileSize,
                                                         @Value("${framework.multipart.maxFileSize}") String maxRequestSize) {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse(maxFileSize));
        factory.setMaxRequestSize(DataSize.parse(maxRequestSize));
        return factory.createMultipartConfig();
    }
}
