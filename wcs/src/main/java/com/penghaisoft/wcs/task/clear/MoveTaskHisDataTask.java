package com.penghaisoft.wcs.task.clear;


import com.alibaba.druid.sql.visitor.functions.If;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.jobmanagement.model.business.IJobManagementService;
import com.penghaisoft.wcs.taskmanagement.model.business.IWcsTaskService;
import com.penghaisoft.wcs.taskmanagement.model.entity.*;
import com.penghaisoft.wcs.util.JDBCUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 移动任务历史数据
 * 1 wcs_task：找到gmt_end是一个月之前进行删除；记录这一堆数据的task_id（就是已经结束的任务），后面会用。
 * 2 wcs_stacker_task：根据上一步task_id找本表的task_id数据，迁移到历史库
 * 3 wcs_conveyor_task：根据第一步task_id找本表的task_id数据，迁移到历史库
 * jzh
 */
//@Component
public class MoveTaskHisDataTask {

    @Autowired
    private IWcsTaskService wcsTaskService;
    @Autowired
    private IJobManagementService jobManagementService;

    // 任务清理定时任务
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void scheduledTaskJob() {
        Date startTime = new Date();
//        1 wcs_task：找到gmt_end是一个月之前进行删除；记录这一堆数据的task_id（就是已经结束的任务），后面会用。
        //查询task (一月前)
        List<WcsTask> listTask = wcsTaskService.selectMothAgoTask();
        //查看是否存在需要移动的数据(不存在空跑)
        if (listTask.size()>0){
            List<Long> taskIdList = new ArrayList<>();
            List<Integer> idList = new ArrayList<>();
            for (WcsTask task : listTask){
                idList.add(task.getId());
                taskIdList.add(task.getTaskId());
            }

            //批量转移task数据
            Resp resultTask = wcsTaskService.dealTask( listTask , idList );
            if (resultTask.getCode().equals("0")){
                Date endTime = new Date();
                jobManagementService.addExecutionAndSummaryLog(this.getClass().getName(),null,"3",startTime,endTime,
                        resultTask.getMsg(),86400);
                return;
            }
            //2 wcs_fourwaycar_task：根据上一步task_id找本表的task_id数据，迁移到历史库
            //查询wcs_fourwaycar_task需转移数据
            List<WcsFourwaycarTask> fourWayCarTaskList = wcsTaskService.selectFourwaycarTaskById(taskIdList);

            if(fourWayCarTaskList.size()>0){
                //根据taskIdList批量转入wcs_fourwaycar_task
                Resp resultFourWayCarTask = wcsTaskService.dealFourWayCarTask(fourWayCarTaskList , taskIdList);

                if (resultFourWayCarTask.getCode().equals("0")){
                    Date endTime = new Date();
                    jobManagementService.addExecutionAndSummaryLog(this.getClass().getName(),null,"3",startTime,endTime,
                            resultFourWayCarTask.getMsg(),86400);
                    return;
                }
            }





//            3 wcs_agv_task：根据第一步task_id找本表的task_id数据，迁移到历史库
            //查询wcs_agv_task需转移数据
            List<WcsAgvTask> listAgvTask = wcsTaskService.selectAgvTaskById(taskIdList);
            if (listAgvTask.size()>0){
                //根据taskIdList批量转入wcs_agv_task
                Resp resultAgvTask = wcsTaskService.dealAgvTask(listAgvTask , taskIdList);
                if (resultAgvTask.getCode().equals("0")){
                    Date endTime = new Date();
                    jobManagementService.addExecutionAndSummaryLog(this.getClass().getName(),null,"3",startTime,endTime,
                            resultAgvTask.getMsg(),86400);
                    return;
                }
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
