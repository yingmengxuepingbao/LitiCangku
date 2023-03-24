package com.penghaisoft.wcs.task.clear;


import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.jobmanagement.model.business.IJobManagementService;
import com.penghaisoft.wcs.monitormanagement.model.business.IWcsErrorLogService;
import com.penghaisoft.wcs.operation.model.WcsBindingInfo;
import com.penghaisoft.wcs.operation.service.CallWmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 绑定信息迁移his数据库
 * report_wms=1
 * jzh
 */
//@Component
public class MoveBindingInfoHisDataTask {


    @Autowired
    IWcsErrorLogService wcsErrorLogService;

    @Autowired
    CallWmsService callWmsServiceImpl;

    @Autowired
    private IJobManagementService jobManagementService;

    // 绑定信息清理定时任务
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void scheduledBindingInfoJob() {
        Date startTime = new Date();

        //获取绑定信息
        List<WcsBindingInfo> list = callWmsServiceImpl.queryMoveBindingInfo();
        if(list.size()>0){

            List<Integer> idList = new ArrayList<>();
            for (WcsBindingInfo info : list){
                idList.add(info.getId());
            }

            Resp resp = callWmsServiceImpl.dealBindingInfo(list,idList);

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
