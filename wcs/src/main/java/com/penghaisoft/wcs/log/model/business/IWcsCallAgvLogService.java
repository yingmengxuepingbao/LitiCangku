package com.penghaisoft.wcs.log.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.log.model.entity.WcsCallAgvLog;
import com.penghaisoft.wcs.operation.model.AgvTask;

import java.util.Date;

/**
* @Description 调用AGV时候的日志记录
* @Date 2020/7/25 9:40
 * zhangx
**/
public interface IWcsCallAgvLogService {
   /**
   * @Description 申请任务单的日志
   * @Date 2020/7/25 9:54
   * @param agvTask, sendTime, respTime, sendFlag, respData
   * @return void
   **/
    void addGenTaskLog(AgvTask agvTask, Date sendTime,Date respTime,String sendFlag,String respData);

    /**
    * @Description 继续任务的日志
    * @Date 2020/7/25 9:54
    * @param agvTask, sendTime, respTime, sendFlag, respData
    * @return void
    **/
    void addContinueTaskLog(AgvTask agvTask,Date sendTime,Date respTime,String sendFlag,String respData);
    /**
     * @Description: 下发AGV指令查询
     * @Author: jzh
     * @Date: 2020/7/27
     */ 
    Pager<WcsCallAgvLog> findCallAgvLog(int page, int rows, WcsCallAgvLog wcsCallAgvLog);
    /**
     * @Description: AGV指令重发
     * @Author: jzh
     * @Date: 2020/7/28
     */ 
    Resp callAgvTask(WcsCallAgvLog wcsCallAgvLog, String loginName);
}
