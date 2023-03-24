package com.penghaisoft.framework.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 *  alibaba fastjson HttpMessageConverters类型转换对象配置 by jun.zhao
 *  只有pom依赖了fastjson & spring-web的模块才会自动配置
 */
@Slf4j
@Configuration
@ConditionalOnClass({com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter.class,
    HttpMessageConverters.class})
@EnableConfigurationProperties(FastJsonProperties.class)
public class    FastJsonConfiguration {

  /**
   *  FastJsonProperties 属性文件配置
   */
  @Autowired
  private FastJsonProperties fastJsonProperties;

  /**
   * 配置FastJsonHttpMessageConverter
   * @return HttpMessageConverters
   */
  @Bean
  @Primary
  public HttpMessageConverters fastJsonHttpMessageConverters() {

    FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
    fastConverter.setDefaultCharset(Charset.forName(fastJsonProperties.getCharset()));
    //设置fastjson
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setCharset(Charset.forName(fastJsonProperties.getCharset()));
    fastJsonConfig.setDateFormat(fastJsonProperties.getDateFormat());
    List<SerializerFeature> serializerFeatures = new ArrayList<>();
    if (fastJsonProperties.getPrettyFormat()) {
      serializerFeatures.add(SerializerFeature.PrettyFormat);
    }
    if (fastJsonProperties.getWriteMapNullValue()) {
      serializerFeatures.add(SerializerFeature.WriteMapNullValue);
    }
    if (fastJsonProperties.getWriteNullListAsEmpty()) {
      serializerFeatures.add(SerializerFeature.WriteNullListAsEmpty);
    }
    if (fastJsonProperties.getWriteNullStringAsEmpty()) {
      serializerFeatures.add(SerializerFeature.WriteNullStringAsEmpty);
    }
    if (fastJsonProperties.getWriteNullNumberAsZero()) {
      serializerFeatures.add(SerializerFeature.WriteNullNumberAsZero);
    }
    if (fastJsonProperties.getWriteNullBooleanAsFalse()) {
      serializerFeatures.add(SerializerFeature.WriteNullBooleanAsFalse);
    }
    fastJsonConfig.setSerializerFeatures(serializerFeatures.toArray(new SerializerFeature[serializerFeatures.size()]));
    fastConverter.setFastJsonConfig(fastJsonConfig);

    //ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
    //byteArrayHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));

    //List<HttpMessageConverter<?>> converters = new ArrayList<>();
    //converters.add(fastConverter);
    //converters.add(byteArrayHttpMessageConverter);
    log.info("FastJsonHttpMessageConverter4 设置完毕");
    return new HttpMessageConverters(fastConverter);
  }

}
