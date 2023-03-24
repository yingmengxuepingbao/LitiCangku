package com.penghaisoft.framework.util;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * service方法的返回对象
 */
@Data
public class Resp implements Serializable{
	
	public Resp(){
		
	}
	
	public Resp(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public Resp(String code, String msg, List<Object> objList){
		this.code = code;
		this.msg = msg;
		this.objList = objList;
	}

    private String code;

    private String msg;
    
    private List<Object> objList = new ArrayList<Object>();
    
    private Object data;
}
