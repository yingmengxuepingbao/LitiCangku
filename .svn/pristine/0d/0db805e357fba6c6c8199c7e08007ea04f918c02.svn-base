package com.penghaisoft.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * FastJsonProperties 属性文件配置
 */
@Data
@ConfigurationProperties(prefix = "spring.fastjson")
public class FastJsonProperties {

  /**
   * 字符集
   */
  private String charset = "UTF-8";

  /**
   * 时间格式
   */
  private String dateFormat = "yyyy-MM-dd HH:mm:ss";

  /**
   * 是否输出值为null的字段
   */
  private Boolean writeMapNullValue = true;

  /**
   * List字段如果为null,输出为[],而非null
   */
  private Boolean writeNullListAsEmpty = false;

  /**
   * 字符类型字段如果为null,输出为”“,而非null
   */
  private Boolean writeNullStringAsEmpty = true;

  /**
   * 数值字段如果为null,输出为0,而非null
   */
  private Boolean writeNullNumberAsZero = true;

  /**
   * Boolean字段如果为null,输出为false,而非null
   */
  private Boolean writeNullBooleanAsFalse = false;

  /**
   * 结果是否格式化
   */
  private Boolean prettyFormat = false;

}
