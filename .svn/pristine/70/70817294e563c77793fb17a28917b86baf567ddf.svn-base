package com.penghaisoft.wcs.taskmanagement.model.entity;

import java.util.List;
import java.util.Date;
import lombok.Data;


/**
 * @Description: AGV手动控制管理
 * @Author: jzh
 * @Date: 2020/7/16
 */ 
@Data
public class WcsManualAgvTask {
	
	private List<String> manualAgvTaskIdList;
	
	private Integer manualAgvTaskId;//  manualAgvTaskId
	private String taskNo;//  任务号
	private Integer agvId;//  agv id 一般用不到
	private String agvTaskType;//  任务类型 QXF1
	private String actionType;//  任务类型 1 入库 2 移动
	private String taskStatus;//  任务状态1创建；2下发；3到达目的地；21下发失败；31到达失败
	private String palletCode;//  托盘编码
	private Integer fromAddressId;//  起始地址id
	private String fromAddressName;//  起始地址名称
	private Integer toAddressId;//  目的地址id
	private String toAddressName;//  目的地址名称
	private Integer priority;//  优先级,越大级别越高
	private String errorMsg;//  错误信息
	private String userDefined1;//  用户自定义1
	private String userDefined2;//  用户自定义2
	private String userDefined3;//  用户自定义3
	private String userDefined4;//  用户自定义4
	private String userDefined5;//  用户自定义5
	private Date gmtStart;//  任务开始执行时间
	private Date gmtEnd;//  任务结束时间
	
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

