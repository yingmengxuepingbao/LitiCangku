package com.penghaisoft.framework.annotation;

import java.lang.annotation.*;

/**
 * 操作日志切面注解
 * @author 秦超
 * 2017-09-13 19:01:45
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented
public @interface OperationLogAspect {
	//操作名
	String operationName()  default "";
	//操作类型
	String operationType()  default "";
}
