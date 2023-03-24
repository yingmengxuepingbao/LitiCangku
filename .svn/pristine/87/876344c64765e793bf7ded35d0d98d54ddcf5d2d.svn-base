package com.penghaisoft.wcs.taskmanagement.model.dao;


import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsManualAgvTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: AGV手动控制管理
 * @Author: jzh
 * @Date: 2020/7/16
 */ 
public interface WcsManualAgvTaskMapper extends BaseMapper<WcsManualAgvTask> {
	public Integer batchInsert(List<WcsManualAgvTask> list);
	
	public Integer batchDelete(@Param("entity") WcsManualAgvTask t);
	
	public Integer insertBySelect(WcsManualAgvTask t);
}