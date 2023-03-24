package com.penghaisoft.wcs.task.clear;


import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.jobmanagement.model.business.IJobManagementService;
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
 * 错误日志迁移his数据库
 * 4 wcs_error_log：根据gmt_create迁移一个月之前的数据
 * jzh
 */
//@Component
public class MoveErrorLogHisDataTask {


    @Autowired
    IWcsErrorLogService wcsErrorLogService;
    @Autowired
    private IJobManagementService jobManagementService;

    // 错误日志清理定时任务
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void scheduledErrorLogJob() {
        Date startTime = new Date();

        //获取一月前错误日志
        List<WcsErrorLog> list = wcsErrorLogService.queryMothAgoError();
        if(list.size()>0){

            List<Integer> idList = new ArrayList<>();
            for (WcsErrorLog errorLog : list){
                idList.add(errorLog.getId());
            }

            Resp resp = wcsErrorLogService.dealErrorLog(list,idList);

            if (resp.getCode().equals("0")){
                Date endTime = new Date();
                jobManagementService.addExecutionAndSummaryLog(this.getClass().getName(),null,"3",startTime,endTime,
                        resp.getMsg(),86400);
                return;
            }
            //处理结束
            Date endTime = new Date();
            jobManagementService.addExecutionAndSummaryLog(this.getClass().getName(),null,"2",startTime,endTime,
                    "处理成功",86400);
        }else {
            Date endTime = new Date();
            jobManagementService.addExecutionAndSummaryLog(this.getClass().getName(),null,"2",startTime,endTime,
                    "无处理数据",86400);
        }


    }

}
