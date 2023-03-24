package com.penghaisoft.wcs.jobmanagement.model.business.impl;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.jobmanagement.model.business.IJobManagementService;
import com.penghaisoft.wcs.jobmanagement.model.dao.WcsJobExecutionLogMapper;
import com.penghaisoft.wcs.jobmanagement.model.dao.WcsJobExecutionSummaryMapper;
import com.penghaisoft.wcs.jobmanagement.model.entity.WcsJobExecutionLog;
import com.penghaisoft.wcs.jobmanagement.model.entity.WcsJobExecutionSummary;
import com.penghaisoft.wcs.jobmanagement.model.entity.WcsJobExecutionSummaryKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class JobManagementServiceImpl implements IJobManagementService {

    @Autowired
    private WcsJobExecutionLogMapper jobExecutionLogMapper;

    @Autowired
    private WcsJobExecutionSummaryMapper jobExecutionSummaryMapper;

    /**
     * 定时任务执行时进行日志记录
     * 这里故意不加事务是为了速度，哪怕记录错误了无所谓
     *
     * @param jobName    任务名=类全路径
     * @param taskIds    可能为null
     * @param jobStatus  任务状态1执行2完成3异常
     * @param createTime 开始时间
     * @param endTime    结束时间
     * @param msg        信息
     * @param interval   执行频率 秒
     */
    @Override
    public void addExecutionAndSummaryLog(String jobName, List<Long> taskIds, String jobStatus, Date createTime, Date endTime, String msg, Integer interval) {
        Date now = new Date();
        Long elapsedTimeLong = endTime.getTime() - createTime.getTime();
        int elapsedTime = elapsedTimeLong.intValue();
//        1 写入任务执行日志表wcs_job_execution_log
        WcsJobExecutionLog jobExecutionLog = new WcsJobExecutionLog();
        jobExecutionLog.setJobName(jobName);
        jobExecutionLog.setGmtCreate(createTime);
        jobExecutionLog.setGmtEnd(endTime);
        jobExecutionLog.setElapsedTime(elapsedTime);
        if (null != taskIds && taskIds.size() > 0) {
            jobExecutionLog.setUserDefined1(taskIds.toString());
        }
        jobExecutionLog.setJobStatus(jobStatus);
        jobExecutionLogMapper.insertSelective(jobExecutionLog);
//        2 定时作业执行汇总表 wcs_job_execution_summary,更新或者插入
        int summaryHour = calSummaryHour(createTime);
        WcsJobExecutionSummaryKey wcsJobExecutionSummaryKey = new WcsJobExecutionSummaryKey();
        wcsJobExecutionSummaryKey.setJobName(jobName);
        wcsJobExecutionSummaryKey.setSummaryHour(summaryHour);
        WcsJobExecutionSummary wcsJobExecutionSummary = jobExecutionSummaryMapper.selectByPrimaryKey(wcsJobExecutionSummaryKey);
        if (null == wcsJobExecutionSummary) {
//            新增记录
            wcsJobExecutionSummary = new WcsJobExecutionSummary();
            wcsJobExecutionSummary.setJobName(jobName);
            wcsJobExecutionSummary.setSummaryHour(summaryHour);
            wcsJobExecutionSummary.setJobInterval(interval);
            wcsJobExecutionSummary.setTotalCount(1);
            if ("3".equals(jobStatus)) {
                wcsJobExecutionSummary.setErrorCount(1);
            } else {
                wcsJobExecutionSummary.setErrorCount(0);
            }
            wcsJobExecutionSummary.setTotalElapsedTime(elapsedTime);
            wcsJobExecutionSummary.setMinElapsedTime(elapsedTime);
            wcsJobExecutionSummary.setMaxElapsedTime(elapsedTime);
            wcsJobExecutionSummary.setGmtCreate(now);
            wcsJobExecutionSummary.setGmtModified(now);
            jobExecutionSummaryMapper.insertSelective(wcsJobExecutionSummary);
        } else {
//            更新记录，
//            根据本作业的开始时间以及作业名称（类名），累加total_count+1，
//            如果是执行错误error_count+1，total_elapsed_time=total_elapsed_time+当前任务消耗时间
            WcsJobExecutionSummary summaryUpdate = new WcsJobExecutionSummary();
            summaryUpdate.setJobName(jobName);
            summaryUpdate.setSummaryHour(summaryHour);
            summaryUpdate.setGmtModified(now);
            summaryUpdate.setTotalElapsedTime(elapsedTime);
            if ("3".equals(jobStatus)) {
                summaryUpdate.setErrorCount(1);
            }
            Integer minElapsedTime = wcsJobExecutionSummary.getMinElapsedTime();
            if (null != minElapsedTime && minElapsedTime > elapsedTime) {
                summaryUpdate.setMinElapsedTime(elapsedTime);
            }
            Integer maxElapsedTime = wcsJobExecutionSummary.getMaxElapsedTime();
            if (null != maxElapsedTime && maxElapsedTime < elapsedTime) {
                summaryUpdate.setMaxElapsedTime(elapsedTime);
            }
            jobExecutionSummaryMapper.updateSummaryLog(summaryUpdate);
        }
    }


    /**
     * 格式为年月日时，
     * 最后两位是当天的小时，从00到23，00代表0点到1点的数据（用任务开始时间统计）
     * 根据任务开始时间计算统计时间
     *
     * @param createTime
     * @return
     */
    private int calSummaryHour(Date createTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHH");
        String ss = sdf.format(createTime);
        return Integer.parseInt(ss);
    }

    /**
     * 清理JobExecution一星期前数据
     * jzh
     *
     * @return
     */
    @Override
    @Transactional
    public Resp deleteJobExecutionWeekAgo() {
        Resp resp = new Resp();
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DATE, -7);
//            一星期前时间点
        Date last = c.getTime();
        WcsJobExecutionLog jobExecutionLog = new WcsJobExecutionLog();
        jobExecutionLog.setGmtCreateMax(last);
        int deleteNum = 0;
        try {
            deleteNum = jobExecutionLogMapper.deleteJobExecutionWeekAgo(jobExecutionLog);
        } catch (Exception e) {
            resp.setCode("1");
            resp.setMsg("删除JobExecution异常");
            return resp;
        }
        resp.setCode("1");
        resp.setMsg("删除JobExecution成功" + deleteNum);
        return resp;
    }

    /**
     * 查询列表
     *
     * @param pager
     * @param baseResource
     * @return
     */
    @Override
    public Pager<WcsJobExecutionLog> findListByCondition(int page, int rows, WcsJobExecutionLog condition) {
        Pager<WcsJobExecutionLog> pager = new Pager<>();
        pager.setPage(page);
        pager.setRows(rows);
//        condition.preQuery();
        long size = jobExecutionLogMapper.queryCount(condition);
        List<WcsJobExecutionLog> records = new ArrayList<WcsJobExecutionLog>();
        if (size > 0) {
            records = jobExecutionLogMapper.queryList(pager, condition);
            if (records != null && !records.isEmpty()) {
                for (WcsJobExecutionLog tmp : records) {
                    if (tmp.getJobStatus() != null && "1".equals(tmp.getJobStatus())) {
                        tmp.setJobStatus("执行");
                    } else if (tmp.getJobStatus() != null && "2".equals(tmp.getJobStatus())) {
                        tmp.setJobStatus("完成");
                    } else if (tmp.getJobStatus() != null && "3".equals(tmp.getJobStatus())) {
                        tmp.setJobStatus("异常");
                    }
                }
            }
        }
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

}
