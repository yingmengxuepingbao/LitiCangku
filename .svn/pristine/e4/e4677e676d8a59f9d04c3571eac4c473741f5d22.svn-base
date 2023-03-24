package com.penghaisoft.wcs.log.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.expose.dto.FourWayCarTaskApply;
import com.penghaisoft.wcs.expose.dto.FourWayCarTaskReport;
import com.penghaisoft.wcs.log.model.entity.WcsFourwaycarReportLog;

/**
* @Description 四向车上报日志
* @Date 2020/7/29 10:55
 * zhangx
**/
public interface IWcsFourwaycarReportLogService {

    /**
     * 增加任务申请日志
     * @param taskApply
     * @param returnStatus
     * @param returnInfo
     */
    void addTaskApplyReportLog(FourWayCarTaskApply taskApply,String returnStatus,String returnInfo);


    /**
     * 增加任务申请日志
     * @param taskReport
     * @param returnStatus
     * @param returnInfo
     */
    void addTaskReportLog(FourWayCarTaskReport taskReport,String returnStatus,String returnInfo);
    /**
     * @Description: 四向车上报任务日志查询
     * @Author: jzh
     * @Date: 2020/7/29
     */ 
    Pager<WcsFourwaycarReportLog> findFourwaycarReportLog(int page, int rows, WcsFourwaycarReportLog wcsFourwaycarReportLog);
}
