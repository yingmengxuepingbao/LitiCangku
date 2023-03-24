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
public class WcsDevice {
	
	private List<String> deviceIdList;
	
	private Integer deviceId;//  deviceId
	private String areaCode;//  所属库区
	private String deviceName;//  设备名称
	private String deviceDesc;//  设备描述
	private String deviceType;//  设备类型 1码垛机 2堆垛机 3RGV 4AGV 5四向穿梭车 6线体
	private String deviceStatus;//  设备状态 1正常 0异常
	private String communicationProtocol;//  通信协议 0 modbus-tcp 1 OPC
	private String ip;//  IP
	private Integer port;//  端口
	private String userDefined1;//  用户自定义1
	private String userDefined2;//  用户自定义2
	private String userDefined3;//  用户自定义3
	private String userDefined4;//  用户自定义4
	private String userDefined5;//  用户自定义5
	
	
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

