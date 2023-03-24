package com.penghaisoft.framework.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作日志————操作类型枚举集合
 * @author 秦超
 * 2017-09-13 17:39:41
 */
public enum OperationTypeEnum {

	OTHERS("0", "其他"),
	USER("1", "用户管理"),
	MENU("2", "菜单管理"),
	PERMISSION("3", "权限管理"),
	LOG("4", "日志管理"),
	REPORT("5", "报表管理");
	
	//操作类型码
	private String code;
	//操作类型
	private String operationType;
	
	//枚举查询Map
	private static final Map<String, OperationTypeEnum> lookup = new HashMap<>();
	
	//枚举查询Map初始化
	static {
		for(OperationTypeEnum e : OperationTypeEnum.values()){
			lookup.put(e.code, e);
		}
	}
    
	OperationTypeEnum( String code , String operationType ){
        this.code = code ;
        this.operationType = operationType;
    } 
	
	//根据错误码获取枚举值
	public static OperationTypeEnum find(String code){
		
		OperationTypeEnum value = lookup.get(code);
		
        if(value == null){
            return OTHERS;
        }
        return value;
    }
	
	public String getOperationType(){
		return operationType;
	}
}
