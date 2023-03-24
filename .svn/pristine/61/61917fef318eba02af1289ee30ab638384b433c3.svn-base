package com.penghaisoft.wcs.task.clear;


import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.jobmanagement.model.business.IJobManagementService;
import com.penghaisoft.wcs.jobmanagement.model.business.IWcsJobExecutionSummaryService;
import com.penghaisoft.wcs.jobmanagement.model.entity.WcsJobExecutionSummary;
import com.penghaisoft.wcs.monitormanagement.model.business.IWcsErrorLogService;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 定时作业执行日志 清理
 * 定时作业执行汇总 转移
 * jzh
 */
//@Component
public class MoveJobExecutionHisDataTask {


    @Autowired
    private IJobManagementService jobManagementService;
    @Autowired
    private IWcsJobExecutionSummaryService wcsJobExecutionSummaryService;


    /**
     * 定时作业执行日志 清理
     * 定时作业执行汇总  转移
     */
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void scheduledJobExecutionJob() {
        Date startTime = new Date();
        //定时作业执行日志 清理(一个星期之前)
        Resp resp = jobManagementService.deleteJobExecutionWeekAgo();
        if (resp.getCode().equals("1")){
            //转移 定时作业执行汇总
            //查询
            List<WcsJobExecutionSummary> listExecutionSummary = wcsJobExecutionSummaryService.selectMothAgoExecutionSummary();
            //转移
            Resp executionResp = wcsJobExecutionSummaryService.dealExecutionSummary(listExecutionSummary);
            if (executionResp.getCode().equals("0")){
                Date endTime = new Date();
                jobManagementService.addExecutionAndSummaryLog(this.getClass().getName(),null,"3",startTime,endTime,
                        resp.getMsg(),86400);
                return;
            }

        }else {
            Date endTime = new Date();
            jobManagementService.addExecutionAndSummaryLog(this.getClass().getName(),null,"3",startTime,endTime,
                    resp.getMsg(),86400);
            return;
        }
        //处理结束
        Date endTime = new Date();
        jobManagementService.addExecutionAndSummaryLog(this.getClass().getName(),null,"2",startTime,endTime,
                "处理成功",86400);

    }

}
