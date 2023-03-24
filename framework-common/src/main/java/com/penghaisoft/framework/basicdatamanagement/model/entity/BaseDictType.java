package com.penghaisoft.framework.basicdatamanagement.model.entity;

import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * 
 * @author
 * 
 */
@Data
public class BaseDictType   {
	
	private List<String> dictTypeList;
	//特殊校验是否为主表
	private String parentId;
	//级别
	private String level;

	private String dictTypeId;//  唯一标识
	private String dicTypeName;//  类型标志
	private String dicTypeCode;//  类型编码
	private String userDefined1;//  用户自定义1
	private String userDefined2;//  用户自定义2
	private String userDefined3;//  用户自定义3
	private String userDefined4;//  用户自定义4
	private String userDefined5;//  用户自定义5
	private String sysParam;//  1代表系统参数，如果是系统参数不允许修改删除
	private int startNumber; //查询起始位置
	private int numberOnePage; //每页数据条数
	private String createBy;
	private String remark;
	private Date gmtCreate;
	private Date gmtCreateMin;
	private Date gmtCreateMax;
	private String lastModifiedBy;// 最后更新人
	private Date gmtModified;
	private Date gmtModifiedMax;
	private Date gmtModifiedMin;
	private String activeFlag;
}

