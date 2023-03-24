package com.penghaisoft.wcs.task.clear;


import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 将
 * @Description MoveHisDataTask
 * @Auther zhangxu
 * @Date 2020/3/3 16:32
 **/
//@Component
public class MoveHisDataTask {
    // do test
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void scheduled() {
        //测试jdbc连接
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<WcsTask> list = new ArrayList<>();
        try {

            conn = getJdbcConWcsHis();
//            String strSql = "";
//            strSql = "select * from kt_sbu.dbo.View_ForCPKWX2 where createdate >= dateadd(hh,?,getdate())  ";
//            pstmt = conn.prepareStatement(strSql);
//            pstmt.setInt(1, 1);  // Compliant; PreparedStatements escape their inputs.
//            rs = pstmt.executeQuery();

            String sql="select * from wcs_task";
			Statement statement = conn.createStatement();
			rs = statement.executeQuery(sql);
            while (rs.next()) {
                //id
                int id = rs.getInt("id");
                //任务id
                long taskId = rs.getLong("task_id");
                //依赖的任务id
                long relyTaskId = rs.getLong("rely_task_id");
                //任务类型
                String taskType = rs.getString("task_type");
                //任务状态1创建2执行中3完成4异常5取消
                String taskStatus = rs.getString("task_status");
                //托盘编码
                String palletCode = rs.getString("pallet_code");
                WcsTask wcsTask = new WcsTask();
                wcsTask.setId(id);
                wcsTask.setTaskId(taskId);
                wcsTask.setRelyTaskId(relyTaskId);
                wcsTask.setTaskType(taskType);
                wcsTask.setTaskStatus(taskStatus);
                wcsTask.setPalletCode(palletCode);
                list.add(wcsTask);
            }
            System.out.println(list);
            if (rs != null) {
                rs.close();
            }
            if(pstmt != null){
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
//            log.info("-------1007------Exception:"+e.getMessage());
//            jobWmsTaskService.deleteById(task);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if(pstmt != null){
                    pstmt.close();
                }
                if(conn != null){
                    conn.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private Connection getJdbcConWcsHis() throws Exception{

        String url = "jdbc:mysql://47.105.177.120:35000/bj_wcs_his?characterEncoding=utf8&serverTimezone=GMT%2B8";
        String name = "com.mysql.cj.jdbc.Driver"; // "com.mysql.jdbc.Driver";
        String user = "admin";
        String password = "Admin@2020";
        Connection conn = null;

        Class.forName(name);    // 指定连接类型
        conn = DriverManager.getConnection(url, user, password);// 获取连接
        return conn;
    }
}
