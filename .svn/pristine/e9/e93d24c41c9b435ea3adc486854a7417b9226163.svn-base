package com.penghaisoft.wcs.jobmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.jobmanagement.model.entity.WcsJobExecutionLog;

import java.util.Date;
import java.util.List;

public interface IJobManagementService {

    /**
     * 定时任务执行时进行日志记录
     *
     * @param jobName    任务名=类全路径
     * @param taskId     可能为null
     * @param jobStatus  任务状态1执行2完成3异常
     * @param createTime 开始时间
     * @param endTime    结束时间
     * @param msg        信息
     * @param interval   执行频率 秒
     */
    void addExecutionAndSummaryLog(String jobName, List<Long> taskId, String jobStatus,
                                   Date createTime, Date endTime, String msg, Integer interval);

    Resp deleteJobExecutionWeekAgo();

    public Pager<WcsJobExecutionLog> findListByCondition(int page, int rows, WcsJobExecutionLog condition);
}
