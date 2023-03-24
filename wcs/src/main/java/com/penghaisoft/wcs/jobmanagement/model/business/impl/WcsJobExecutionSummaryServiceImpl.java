package com.penghaisoft.wcs.jobmanagement.model.business.impl;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.jobmanagement.model.business.IWcsJobExecutionSummaryService;
import com.penghaisoft.wcs.jobmanagement.model.dao.WcsJobExecutionSummaryMapper;
import com.penghaisoft.wcs.jobmanagement.model.entity.WcsJobExecutionLog;
import com.penghaisoft.wcs.jobmanagement.model.entity.WcsJobExecutionSummary;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;
import com.penghaisoft.wcs.util.JDBCUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("wcsJobExecutionSummaryService")
public class WcsJobExecutionSummaryServiceImpl implements IWcsJobExecutionSummaryService {

    @Autowired
    private WcsJobExecutionSummaryMapper jobExecutionSummaryMapper;
    /**
     * 查询列表
     *
     * @param pager
     * @param baseResource
     * @return
     */
    @Override
    public Pager<WcsJobExecutionSummary> findListByCondition(int page, int rows, WcsJobExecutionSummary condition) {
        Pager<WcsJobExecutionSummary> pager = new Pager<>();
        pager.setPage(page);
        pager.setRows(rows);
//        condition.preQuery();
        long size = jobExecutionSummaryMapper.queryCount(condition);
        List<WcsJobExecutionSummary> records = new ArrayList<WcsJobExecutionSummary>();
        if (size > 0) {
            records = jobExecutionSummaryMapper.queryList(pager, condition);
            if(records != null && !records.isEmpty()){
                for(WcsJobExecutionSummary tmp : records){
                    tmp.setAvgElapsedTime(tmp.getTotalElapsedTime() / tmp.getTotalCount());
                }
            }
        }
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

    /**
     * 查询一月前数据
     * @return
     */
    @Override
    public List<WcsJobExecutionSummary> selectMothAgoExecutionSummary() {
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MONTH, -1);
//            一月前时间点
        Date last = c.getTime();
        WcsJobExecutionSummary jobExecutionSummary = new WcsJobExecutionSummary();
        jobExecutionSummary.setGmtCreateMax(last);
        List<WcsJobExecutionSummary> list = jobExecutionSummaryMapper.queryGmtCreateMax(jobExecutionSummary);
        return list;
    }

    /**
     * 转移 汇总日志
     * @param listExecutionSummary
     * @return
     */
    @Override
    public Resp dealExecutionSummary(List<WcsJobExecutionSummary> listExecutionSummary) {
        Resp resp = new Resp();
        //开始批量插入his数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Statement statement = null;
        long dealNum = 0;
        try {
            conn = JDBCUtil.getConn();
            conn.setAutoCommit(false);////关闭自动提交
            String sql = "insert into wcs_job_execution_summary (job_name,summary_hour,job_interval,total_count," +
                    "error_count,total_elapsed_time,min_elapsed_time,max_elapsed_time,gmt_create,gmt_modified) values(?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);

            for(int i = 0; i < listExecutionSummary.size(); i++){
                //job_name
                ps.setString(1,listExecutionSummary.get(i).getJobName());

                //summary_hour
                if (listExecutionSummary.get(i).getSummaryHour() == null){
                    ps.setNull(2, Types.INTEGER);
                }else {
                    ps.setInt(2,listExecutionSummary.get(i).getSummaryHour());
                }
                //job_interval
                if (listExecutionSummary.get(i).getJobInterval() == null){
                    ps.setNull(3, Types.INTEGER);
                }else {
                    ps.setInt(3,listExecutionSummary.get(i).getJobInterval());
                }
                //total_count
                if (listExecutionSummary.get(i).getTotalCount() == null){
                    ps.setNull(4, Types.INTEGER);
                }else {
                    ps.setInt(4,listExecutionSummary.get(i).getTotalCount());
                }
                //error_count
                if (listExecutionSummary.get(i).getErrorCount() == null){
                    ps.setNull(5, Types.INTEGER);
                }else {
                    ps.setInt(5,listExecutionSummary.get(i).getErrorCount());
                }
                //total_elapsed_time
                if (listExecutionSummary.get(i).getTotalElapsedTime() == null){
                    ps.setNull(6, Types.INTEGER);
                }else {
                    ps.setInt(6,listExecutionSummary.get(i).getTotalElapsedTime());
                }
                //min_elapsed_time
                if (listExecutionSummary.get(i).getMinElapsedTime() == null){
                    ps.setNull(7, Types.INTEGER);
                }else {
                    ps.setInt(7,listExecutionSummary.get(i).getMinElapsedTime());
                }
                //max_elapsed_time
                if (listExecutionSummary.get(i).getMaxElapsedTime() == null){
                    ps.setNull(8, Types.INTEGER);
                }else {
                    ps.setInt(8,listExecutionSummary.get(i).getMaxElapsedTime());
                }
                //gmt_create
                if (listExecutionSummary.get(i).getGmtCreate() == null){
                    ps.setNull(9, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(9,new Timestamp(listExecutionSummary.get(i).getGmtCreate().getTime()));
                }
                //gmt_modified
                if (listExecutionSummary.get(i).getGmtModified() == null){
                    ps.setNull(10, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(10,new Timestamp(listExecutionSummary.get(i).getGmtModified().getTime()));
                }

                ps.addBatch();
                if (i % 10000 == 0 && i != 0){
                    ps.executeBatch();
                    conn.commit();
                    dealNum=dealNum+10000;
                    ps.clearBatch();
                }
            }
            ps.executeBatch();
            conn.commit();
            if (dealNum == 0){
                dealNum = listExecutionSummary.size();
            }else {
                dealNum = dealNum+(listExecutionSummary.size()-dealNum);
            }
            conn.close();
            System.out.println("wcs_job_execution_summary插入his条数"+dealNum);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setCode("0");
            resp.setMsg("wcs_job_execution_summary批量插入异常");
            return resp;
        } finally {
            JDBCUtil.close(conn,ps,rs);
        }
        //开始批量删除原数据库
        //根据时间
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MONTH, -1);
//            一月前时间点
        Date last = c.getTime();
        WcsJobExecutionSummary jobExecutionSummary = new WcsJobExecutionSummary();
        jobExecutionSummary.setGmtCreateMax(last);
        int delectNum = jobExecutionSummaryMapper.deleteExecutionSummary(jobExecutionSummary);

        System.out.println("wcs_job_execution_summary删除原库数量"+delectNum);
        if (delectNum != dealNum){
            resp.setCode("0");
            resp.setMsg("wcs_job_execution_summary批量插入"+dealNum+"批量删除"+delectNum);
            return resp;
        }

        resp.setCode("1");
        resp.setMsg("wcs_error_log转移成功");
        return resp;
    }
}
