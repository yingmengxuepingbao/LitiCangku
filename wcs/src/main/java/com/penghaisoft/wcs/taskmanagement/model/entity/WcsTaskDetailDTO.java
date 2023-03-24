package com.penghaisoft.wcs.taskmanagement.model.entity;

import java.util.List;
import java.util.Date;
import lombok.Data;


/**
 * 
 * @author
 * 
 */
@Data
public class WcsTaskDetailDTO {

	private Long taskId;//  任务id 年月日+task_type+5位序列号

	private Integer taskNo;//  任务编号

	private String taskForm;//  任务类型 1 堆垛机 2 传输线

	private String actionType;//  动作（堆垛机） 1 取货 2 放货 4 移动

	private Integer locationId;//  货位（堆垛机）

	private Integer pathId;//  路径（传输线）

	private Date gmtStart;//  启动时间

	private Date gmtEnd;//  任务结束时间

	private String taskStatus;//四向车任务状态

	private String taskAgvStatus;//AGV任务状态
	
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

