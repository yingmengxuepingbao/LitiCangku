package com.penghaisoft.framework.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * 异常聚合类
 * @author 于翔
 * @date 2017-08-31
 *
 */
public class AggregateException extends Exception {
	
	//聚合所有类型的异常对象
	private final List<Exception> innerExceptions;
	
	//构造函数
	public AggregateException() {
		this.innerExceptions = new ArrayList<>();
	}

	public List<Exception> getInnerExceptions() {
		return innerExceptions;
	}

	//添加一个异常对象
    public void addException(Exception exception) {
    	if(exception != null) {
    	    this.innerExceptions.add(exception);
    	}
    }
    
    //获取聚合的异常对象的数量
    public int count() {
    	return this.innerExceptions.size();
    }
}
