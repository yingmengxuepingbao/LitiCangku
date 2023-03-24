package com.penghaisoft.wcs.taskmanagement.model.business;


import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsManualAgvTask;

/**
 * @Description: AGV手动控制管理
 * @Author: jzh
 * @Date: 2020/7/16
 */
public interface IWcsManualAgvTaskService{
	
	public Resp create(WcsManualAgvTask wcsManualAgvTask);

	public Resp delete(WcsManualAgvTask wcsManualAgvTask);
	
	public Pager<WcsManualAgvTask> findListByCondition(int page, int rows, WcsManualAgvTask condition);
	
	public WcsManualAgvTask findById(String id);
	
	public Resp update(WcsManualAgvTask wcsManualAgvTask);

    Resp startTask(WcsManualAgvTask wcsManualAgvTask, String loginName);
}
