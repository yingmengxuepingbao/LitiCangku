package com.penghaisoft.wcs.log.model.business.impl;

import com.alibaba.fastjson.JSON;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.expose.dto.FourWayCarTaskApply;
import com.penghaisoft.wcs.expose.dto.FourWayCarTaskReport;
import com.penghaisoft.wcs.log.model.business.IWcsFourwaycarReportLogService;
import com.penghaisoft.wcs.log.model.dao.WcsFourwaycarReportLogMapper;
import com.penghaisoft.wcs.log.model.entity.WcsFourwaycarReportLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName WcsFourwaycarReportLogServiceImpl
 * @Description 四向车上报日志服务
 * @Author zhangx
 * @Date 2020/7/29 10:56
 **/
@Service
public class WcsFourwaycarReportLogServiceImpl implements IWcsFourwaycarReportLogService {

    @Autowired
    private WcsFourwaycarReportLogMapper reportLogMapper;

    /**
     * 增加任务申请日志
     *
     * @param taskApply
     * @param returnStatus
     * @param returnInfo
     */
    @Override
    public void addTaskApplyReportLog(FourWayCarTaskApply taskApply, String returnStatus, String returnInfo) {
        WcsFourwaycarReportLog reportLog = new WcsFourwaycarReportLog();
        reportLog.setReportType("taskApply");
        reportLog.setInParam(JSON.toJSONString(taskApply));
        reportLog.setReturnStatus(returnStatus);
        if (null!=returnInfo&&!"".equals(returnInfo)){
            reportLog.setReturnInfo(returnInfo);
        }
        reportLog.setRespTime(new Date());
        reportLogMapper.insertSelective(reportLog);
    }

    /**
     * 增加任务申请日志
     *
     * @param taskReport
     * @param returnStatus
     * @param returnInfo
     */
    @Override
    public void addTaskReportLog(FourWayCarTaskReport taskReport, String returnStatus, String returnInfo) {
        WcsFourwaycarReportLog reportLog = new WcsFourwaycarReportLog();
        reportLog.setReportType("taskReport");
        reportLog.setTaskId(taskReport.getTaskId());
        reportLog.setInParam(JSON.toJSONString(taskReport));
        reportLog.setReturnStatus(returnStatus);
        if (null!=returnInfo&&!"".equals(returnInfo)){
            reportLog.setReturnInfo(returnInfo);
        }
        reportLog.setRespTime(new Date());
        reportLogMapper.insertSelective(reportLog);
    }
    /**
     * @Description: 四向车上报任务日志查询
     * @Author: jzh
     * @Date: 2020/7/29
     */ 
    @Override
    public Pager<WcsFourwaycarReportLog> findFourwaycarReportLog(int page, int rows, WcsFourwaycarReportLog condition) {
        Pager<WcsFourwaycarReportLog> pager = new Pager<>();
        pager.setPage(page);
        pager.setRows(rows);
        List<WcsFourwaycarReportLog> records = reportLogMapper.queryList(pager,condition);
        long size = reportLogMapper.queryCount(condition);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }
}
