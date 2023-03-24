package com.penghaisoft.wcs.basicmanagement.model.entity;

import java.util.List;
import java.util.Date;
import lombok.Data;


/**
 * 
 * @author
 * 
 */
@Data
public class WcsAddress {
	
	private List<String> addressIdList;
	
	private Integer addressId;//  addressId
	private String areaCode;//  所属库区
	private Integer x;//  x坐标
	private Integer y;//  y坐标
	private Integer z;//  z坐标
	private String description;//  描述
	private Integer deviceId;//  设备id
	private String deviceName;//  设备名称
	private String deviceType;//  1码垛机 2堆垛机 3RGV 4AGV 5四向穿梭车 6线体
	private String userDefined1;//  用户自定义1
	private String userDefined2;//  用户自定义2
	private String userDefined3;//  用户自定义3
	private String userDefined4;//  用户自定义4
	private String userDefined5;//  用户自定义5
	
	private String orderBy;
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

