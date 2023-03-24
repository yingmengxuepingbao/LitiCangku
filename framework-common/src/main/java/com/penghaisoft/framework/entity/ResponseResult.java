package com.penghaisoft.framework.entity;

/**
* request请求返回
* @author 于翔
* @date 2017-09-01 
*/ 
public class ResponseResult{
	private String code;
	private String message;
	private Object data;

	public ResponseResult(String code, String message, Object data){
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
