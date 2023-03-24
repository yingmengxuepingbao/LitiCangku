package com.penghaisoft.wcs.taskmanagement.model.business.impl;

import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.taskmanagement.model.business.IWcsTaskService;
import com.penghaisoft.wcs.taskmanagement.model.dao.*;
import com.penghaisoft.wcs.taskmanagement.model.entity.*;
import com.penghaisoft.wcs.util.JDBCUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName WcsTaskServiceImpl
 * @Description 将完成的wcs任务回传给wms
 * @Author luo_0
 * @Date 2020/3/23 15:04
 **/
@Slf4j
@Service("wcsTaskService")
public class WcsTaskServiceImpl implements IWcsTaskService {

    @Autowired
    private WcsTaskMapper wcsTaskMapper;

    @Autowired
    private IUserBusiness userBusiness;

    @Autowired
    private WcsAgvTaskMapper wcsAgvTaskMapper;

    @Autowired
    private WcsFourwaycarTaskMapper wcsFourwaycarTaskMapper;

    /**
    * @Description 将task_status 3完成4异常5取消的
     * report_wms 0未上传 3上传失败 查出来
    * @Date  10:26
    * @param
    * @return
    **/
    @Override
    public List<WcsTask> selectCompleteTask() {
//        `task_status` '任务状态1创建2执行中【3完成4异常5取消】',
//        `report_wms`  '上报wms结果标志  1成功上传【0未上传】2业务异常【3上传失败】',
        return wcsTaskMapper.selectCompleteTask();
    }

    @Override
    public int updateByPrimaryKeySelective(WcsTask record) {
        return wcsTaskMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 查询一个月前的任务
     * @return
     */
    @Override
    public List<WcsTask> selectMothAgoTask() {

        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MONTH, -1);
//            一月前时间点
        Date lastyear = c.getTime();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        WcsTask wcsTask = new WcsTask();
        wcsTask.setGmtEndMax(lastyear);
        List<WcsTask> list = wcsTaskMapper.queryByGmtEndMax(wcsTask);


        return list;
    }



    /**
     * task转移到his数据库
     * @param listTask
     * @param taskIdList
     * @return
     */
    @Override
    public Resp dealTask(List<WcsTask> listTask, List<Integer> taskIdList) {
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
            String sql = "insert into wcs_task (task_id,rely_task_id,task_type,task_status,pallet_code," +
                    "from_address,to_address,report_wms,priority,error_msg,user_defined1,gmt_start,create_by," +
                    "gmt_create,last_modified_by,gmt_modified,gmt_end,end_by) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);

            for(int i = 0; i < listTask.size(); i++){
                ps.setLong(1, listTask.get(i).getTaskId());
                if (listTask.get(i).getRelyTaskId() == null){
                    ps.setNull(2, Types.BIGINT);
                }else {
                    ps.setLong(2, listTask.get(i).getRelyTaskId());
                }
                ps.setString(3,listTask.get(i).getTaskType());
                ps.setString(4,listTask.get(i).getTaskStatus());
                ps.setString(5,listTask.get(i).getPalletCode());

                if (listTask.get(i).getFromAddress() == null){
                    ps.setNull(6, Types.INTEGER);
                }else {
                    ps.setInt(6,listTask.get(i).getFromAddress());
                }
                if (listTask.get(i).getToAddress() == null){
                    ps.setNull(7, Types.INTEGER);
                }else {
                    ps.setInt(7,listTask.get(i).getToAddress());
                }

                ps.setString(8,listTask.get(i).getReportWms());
                if (listTask.get(i).getPriority() == null){
                    ps.setNull(9, Types.INTEGER);
                }else {
                    ps.setInt(9,listTask.get(i).getPriority());
                }
                ps.setString(10,listTask.get(i).getErrorMsg());
                ps.setString(11,listTask.get(i).getUserDefined1());
                if (listTask.get(i).getGmtStart() == null){
                    ps.setNull(12, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(12,new Timestamp(listTask.get(i).getGmtStart().getTime()));
                }
                ps.setString(13,listTask.get(i).getCreateBy());
                if (listTask.get(i).getGmtCreate() == null){
                    ps.setNull(14, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(14,new Timestamp(listTask.get(i).getGmtCreate().getTime()));
                }
                ps.setString(15,listTask.get(i).getLastModifiedBy());
                if (listTask.get(i).getGmtModified() == null){
                    ps.setNull(16, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(16,new Timestamp(listTask.get(i).getGmtModified().getTime()));
                }
                if (listTask.get(i).getGmtEnd() == null){
                    ps.setNull(17, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(17,new Timestamp(listTask.get(i).getGmtEnd().getTime()));
                }
                ps.setString(18,listTask.get(i).getEndBy());

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
                dealNum = listTask.size();
            }else {
                dealNum = dealNum+(listTask.size()-dealNum);
            }

            conn.close();
            System.out.println("插入his条数"+dealNum);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setCode("0");
            resp.setMsg("task批量插入异常");
            return resp;
        } finally {
            JDBCUtil.close(conn,ps,rs);
        }
        //开始批量删除原数据库
        //根据taskIdList
        int delectNum = wcsTaskMapper.deleteTaskByIdList(taskIdList);
        System.out.println("删除原库数量"+delectNum);
        if (delectNum != dealNum){
            resp.setCode("0");
            resp.setMsg("task批量插入"+dealNum+"批量删除"+delectNum);
            return resp;
        }

        resp.setCode("1");
        resp.setMsg("task转移成功");
        return resp;
    }



    /**
     * 基础页面查询
     * @param page
     * @param rows
     * @param condition
     * @return
     */
    @Override
    public Pager<WcsTask> findListByCondition(int page, int rows, WcsTask condition) {
        Pager<WcsTask> pager = new Pager<>();
        pager.setPage(page);
        pager.setRows(rows);
        List<WcsTask> records = wcsTaskMapper.queryList(pager,condition);
        long size = wcsTaskMapper.queryCount(condition);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

    /**
     * @Description: 修改为 根据task id 查询 AGV 和四向穿梭车 任务明细
     * @Author: jzh
     * @Date: 2020/7/8
     */
    @Override
    public List<WcsTaskDetailDTO> queryDetailByTaskId(String taskId) {
        List<WcsTaskDetailDTO> resultList = new ArrayList<>();
        List<WcsAgvTask> wcsAgvTaskList  = wcsAgvTaskMapper.queryAnyByKey(Long.valueOf(taskId));
        for (WcsAgvTask wcsAgvTask : wcsAgvTaskList) {
            WcsTaskDetailDTO taskDetail = new WcsTaskDetailDTO();
            taskDetail.setTaskId(wcsAgvTask.getTaskId());
            taskDetail.setTaskNo(wcsAgvTask.getTaskNo());
            taskDetail.setTaskForm("AGV");
            taskDetail.setActionType(wcsAgvTask.getActionType());
            taskDetail.setPathId(wcsAgvTask.getPathId());
            taskDetail.setGmtStart(wcsAgvTask.getGmtStart());
            taskDetail.setGmtEnd(wcsAgvTask.getGmtEnd());
            //分开显示AGV任务状态TaskAgvStatus
            taskDetail.setTaskAgvStatus(wcsAgvTask.getTaskStatus());
            taskDetail.setGmtCreate(wcsAgvTask.getGmtCreate());
            resultList.add(taskDetail);
        }


        List<WcsFourwaycarTask> fourWayCarTaskList = wcsFourwaycarTaskMapper.queryAnyByKey(Long.valueOf(taskId));
        for (WcsFourwaycarTask wcsFourwaycarTask : fourWayCarTaskList) {
            WcsTaskDetailDTO taskDetail = new WcsTaskDetailDTO();
            taskDetail.setTaskId(wcsFourwaycarTask.getTaskId());
            taskDetail.setTaskNo(wcsFourwaycarTask.getTaskNo());
            taskDetail.setTaskForm("四向穿梭车");
            taskDetail.setActionType(wcsFourwaycarTask.getActionType());
            taskDetail.setLocationId(wcsFourwaycarTask.getLocationId());
            taskDetail.setGmtStart(wcsFourwaycarTask.getGmtStart());
            taskDetail.setGmtEnd(wcsFourwaycarTask.getGmtEnd());
            taskDetail.setTaskStatus(wcsFourwaycarTask.getTaskStatus());
            taskDetail.setGmtCreate(wcsFourwaycarTask.getGmtCreate());
            resultList.add(taskDetail);
        }
        return resultList;
    }

    /**
     * 根据taskId修改任务状态(线体,堆垛机)
     * @param wcsTaskDetailDTO
     * @return
     */
    @Override
    public Resp changeTaskStatus(WcsTaskDetailDTO wcsTaskDetailDTO) {
        User currentUser = userBusiness.getCurrentUser();
        String loginNameWithAccount = currentUser.getNickname() + "(" + currentUser.getAccount() + ")";
        Date now = new Date();
        String taskFrom = wcsTaskDetailDTO.getTaskForm();
        Long taskId = wcsTaskDetailDTO.getTaskId();
        String newStatus = wcsTaskDetailDTO.getTaskStatus();
        log.info("更改任务{}中的{}状态为{}",taskId,taskFrom,newStatus);
        if ("AGV".equals(taskFrom)){
            List<WcsAgvTask> agvTaskList = wcsAgvTaskMapper.selectByTaskId(taskId);
            if (agvTaskList.size() == 0){
                return new Resp("1","查询不到AGV任务");
            }
            if (newStatus.equals(agvTaskList.get(0).getTaskStatus())){
                return new Resp("1","状态相同不需要更新");
            }
            //修改任务状态
            Integer agvTaskId = agvTaskList.get(0).getAgvTaskId();
            WcsAgvTask updAgvTask = new WcsAgvTask();
            updAgvTask.setAgvTaskId(agvTaskId);
            updAgvTask.setTaskStatus(newStatus);
            updAgvTask.setLastModifiedBy(loginNameWithAccount);
            updAgvTask.setGmtModified(now);
            wcsAgvTaskMapper.updateByPrimaryKeySelective(updAgvTask);
        }else if ("四向穿梭车".equals(taskFrom)){
            List<WcsFourwaycarTask> fourwaycarTasks = wcsFourwaycarTaskMapper.selectByTaskId(taskId);
            if (fourwaycarTasks.size()==0){
                return new Resp("1","查询不到四向车任务");
            }
            if (newStatus.equals(fourwaycarTasks.get(0).getTaskStatus())){
                return new Resp("1","状态相同不需要更新");
            }
            //修改任务状态
            Integer fourwaycarTaskId = fourwaycarTasks.get(0).getFourwaycarTaskId();
            WcsFourwaycarTask updFourwaycarTask = new WcsFourwaycarTask();
            updFourwaycarTask.setFourwaycarTaskId(fourwaycarTaskId);
            updFourwaycarTask.setTaskStatus(wcsTaskDetailDTO.getTaskStatus());
            updFourwaycarTask.setLastModifiedBy(loginNameWithAccount);
            updFourwaycarTask.setGmtModified(now);
            wcsFourwaycarTaskMapper.updateByPrimaryKeySelective(updFourwaycarTask);
        }else {
            return new Resp("1","任务类型异常");
        }
        return new Resp("0","任务状态更新成功");
    }

    /**
     * 修改主任务状态
     * @param wcsTask
     * @return
     */
    @Override
    public Resp changeMain(WcsTask wcsTask) {
        User currentUser = userBusiness.getCurrentUser();
        String loginNameWithAccount = currentUser.getNickname() + "(" + currentUser.getAccount() + ")";
        Date now = new Date();

        String status = wcsTask.getTaskStatus();
        WcsTask wcsTaskMain = wcsTaskMapper.selectByPrimaryKey(wcsTask.getId());
//        Long taskId = wcsTaskMain.getTaskId();

        if (status.equals(wcsTaskMain.getTaskStatus())){
            return new Resp("1","状态相同不需要更新");
        }

//        主任务 1创建2执行中3完成4异常5取消
//        agv任务 1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消
//        四向车任务 1创建 2 申请任务 3 执行任务（下发）4 执行 5取货完成 6 任务完成
        log.info("主任务更改为{}状态",status);
        WcsTask updWcsTask = new WcsTask();
        updWcsTask.setId(wcsTask.getId());
        updWcsTask.setTaskStatus(status);
        updWcsTask.setGmtModified(now);
        updWcsTask.setLastModifiedBy(loginNameWithAccount);
        wcsTaskMapper.updateByPrimaryKeySelective(updWcsTask);


        return new Resp("0","任务状态更新成功");
    }

    /**
     * 修改上传wms状态
     * @param wcsTask
     * @return
     */
    @Override
    public Resp changeReportWms(WcsTask wcsTask) {

        User currentUser = userBusiness.getCurrentUser();
        String loginNameWithAccount = currentUser.getNickname() + "(" + currentUser.getAccount() + ")";
        Date now = new Date();

        String reportWms = wcsTask.getReportWms();
        WcsTask wcsTaskMain = wcsTaskMapper.selectByPrimaryKey(wcsTask.getId());
        Long taskId = wcsTaskMain.getTaskId();

        if (reportWms.equals(wcsTaskMain.getReportWms())){
            return new Resp("1","状态相同不需要更新");
        }

        String nowReportWms = wcsTaskMain.getReportWms();
        if ("1".equals(nowReportWms)){
            if ("0".equals(reportWms)){
                //执行修改
                wcsTask.setLastModifiedBy(loginNameWithAccount);
                wcsTask.setGmtModified(now);
                Integer change = wcsTaskMapper.updateByPrimaryKeySelective(wcsTask);
            }else {
                return new Resp("1","当前任务为上传wms成功，只可修改为未上传");
            }
        }
        if ("0".equals(nowReportWms)){
            if ("1".equals(reportWms)){
                //执行修改
                wcsTask.setLastModifiedBy(loginNameWithAccount);
                wcsTask.setGmtModified(now);
                Integer change = wcsTaskMapper.updateByPrimaryKeySelective(wcsTask);
            }else {
                return new Resp("1","当前任务为未上传，只可修改为上传成功");
            }
        }
        if ("2".equals(nowReportWms)){
            if ("0".equals(reportWms)||"3".equals(reportWms)){
                //执行修改
                wcsTask.setLastModifiedBy(loginNameWithAccount);
                wcsTask.setGmtModified(now);
                Integer change = wcsTaskMapper.updateByPrimaryKeySelective(wcsTask);
            }else {
                return new Resp("1","当前任务为业务异常，只可修改为未上传或上传失败");
            }
        }
        if ("3".equals(nowReportWms)){
            if ("1".equals(reportWms)||"2".equals(reportWms)){
                //执行修改
                wcsTask.setLastModifiedBy(loginNameWithAccount);
                wcsTask.setGmtModified(now);
                Integer change = wcsTaskMapper.updateByPrimaryKeySelective(wcsTask);
            }else {
                return new Resp("1","当前任务为上传失败，只可修改为成功上传或业务异常");
            }
        }


        return new Resp("0","任务状态更新成功");
    }
    /**
     * @Description: 根据task id list 查询需要处理的四向车历史数据
     * @Author: jzh
     * @Date: 2020/7/9
     */ 
    @Override
    public List<WcsFourwaycarTask> selectFourwaycarTaskById(List<Long> taskIdList) {
        List<WcsFourwaycarTask> list = wcsFourwaycarTaskMapper.selectFourwaycarTaskById(taskIdList);
        return list;
    }
    /**
     * @Description: 四向车历史数据转移处理
     * @Author: jzh
     * @Date: 2020/7/9
     */
    @Override
    public Resp dealFourWayCarTask(List<WcsFourwaycarTask> fourWayCarTaskList, List<Long> taskIdList) {
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
            String sql = "insert into wcs_fourwaycar_task (task_no,fourwaycar_id,task_id,rely_fourwaycar_task_id," +
                    "fourwaycar_task_type,action_type,task_status,pallet_code,location_id,location_x,location_y," +
                    "location_z,location_direction,priority,error_msg,remark,create_by,gmt_create,gmt_start," +
                    "last_modified_by,gmt_modified,gmt_end) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);

            for(int i = 0; i < fourWayCarTaskList.size(); i++){
                //task_no
                ps.setInt(1,fourWayCarTaskList.get(i).getTaskNo());
                //fourwaycar_id
                if (fourWayCarTaskList.get(i).getFourwaycarId() == null){
                    ps.setNull(2, Types.INTEGER);
                }else {
                    ps.setInt(2,fourWayCarTaskList.get(i).getFourwaycarId());
                }
                //task_id
                if (fourWayCarTaskList.get(i).getTaskId() == null){
                    ps.setNull(3, Types.BIGINT);
                }else {
                    ps.setLong(3, fourWayCarTaskList.get(i).getTaskId());
                }
                //rely_fourwaycar_task_id
                if (fourWayCarTaskList.get(i).getRelyFourwaycarTaskId() == null){
                    ps.setNull(4, Types.INTEGER);
                }else {
                    ps.setInt(4,fourWayCarTaskList.get(i).getRelyFourwaycarTaskId());
                }
                //fourwaycar_task_type
                ps.setString(5,fourWayCarTaskList.get(i).getFourwaycarTaskType());
                //action_type
                ps.setString(6,fourWayCarTaskList.get(i).getActionType());
                //task_status
                ps.setString(7,fourWayCarTaskList.get(i).getTaskStatus());
                //pallet_code
                ps.setString(8,fourWayCarTaskList.get(i).getPalletCode());
                //location_id
                if (fourWayCarTaskList.get(i).getLocationId() == null){
                    ps.setNull(9, Types.INTEGER);
                }else {
                    ps.setInt(9,fourWayCarTaskList.get(i).getLocationId());
                }
                //location_x
                if (fourWayCarTaskList.get(i).getLocationX() == null){
                    ps.setNull(10, Types.INTEGER);
                }else {
                    ps.setInt(10,fourWayCarTaskList.get(i).getLocationX());
                }
                //location_y
                if (fourWayCarTaskList.get(i).getLocationY() == null){
                    ps.setNull(11, Types.INTEGER);
                }else {
                    ps.setInt(11,fourWayCarTaskList.get(i).getLocationY());
                }
                //location_z
                if (fourWayCarTaskList.get(i).getLocationZ() == null){
                    ps.setNull(12, Types.INTEGER);
                }else {
                    ps.setInt(12,fourWayCarTaskList.get(i).getLocationZ());
                }
                //location_direction
                ps.setString(13,fourWayCarTaskList.get(i).getLocationDirection());
                //priority
                if (fourWayCarTaskList.get(i).getPriority() == null){
                    ps.setNull(14, Types.INTEGER);
                }else {
                    ps.setInt(14,fourWayCarTaskList.get(i).getPriority());
                }
                //error_msg
                ps.setString(15,fourWayCarTaskList.get(i).getErrorMsg());
                //remark
                ps.setString(16,fourWayCarTaskList.get(i).getRemark());
                //create_by
                ps.setString(17,fourWayCarTaskList.get(i).getCreateBy());
                //gmt_create
                if (fourWayCarTaskList.get(i).getGmtCreate() == null){
                    ps.setNull(18, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(18,new Timestamp(fourWayCarTaskList.get(i).getGmtCreate().getTime()));
                }
                //gmt_start
                if (fourWayCarTaskList.get(i).getGmtStart() == null){
                    ps.setNull(19, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(19,new Timestamp(fourWayCarTaskList.get(i).getGmtStart().getTime()));
                }
                //gmt_modified
                ps.setString(20,fourWayCarTaskList.get(i).getLastModifiedBy());
                if (fourWayCarTaskList.get(i).getGmtModified() == null){
                    ps.setNull(21, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(21,new Timestamp(fourWayCarTaskList.get(i).getGmtModified().getTime()));
                }
                //gmt_end
                if (fourWayCarTaskList.get(i).getGmtEnd() == null){
                    ps.setNull(22, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(22,new Timestamp(fourWayCarTaskList.get(i).getGmtEnd().getTime()));
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
                dealNum = fourWayCarTaskList.size();
            }else {
                dealNum = dealNum+(fourWayCarTaskList.size()-dealNum);
            }
            conn.close();
            System.out.println("wcs_fourwaycar_task插入his条数"+dealNum);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setCode("0");
            resp.setMsg("wcs_fourwaycar_task批量插入异常");
            return resp;
        } finally {
            JDBCUtil.close(conn,ps,rs);
        }
        //开始批量删除原数据库
        //根据taskIdList
        int delectNum = wcsFourwaycarTaskMapper.deleteFourwaycarTaskByIdList(taskIdList);
        System.out.println("wcs_fourwaycar_task删除原库数量"+delectNum);
        if (delectNum != dealNum){
            resp.setCode("0");
            resp.setMsg("wcs_fourwaycar_task批量插入"+dealNum+"批量删除"+delectNum);
            return resp;
        }

        resp.setCode("1");
        resp.setMsg("wcs_fourwaycar_task转移成功");
        return resp;
    }
    /**
     * @Description: 根据task id list 查询需要处理的AGV历史数据
     * @Author: jzh
     * @Date: 2020/7/9
     */ 
    @Override
    public List<WcsAgvTask> selectAgvTaskById(List<Long> taskIdList) {
        List<WcsAgvTask> list = wcsAgvTaskMapper.selectAgvTaskById(taskIdList);
        return list;
    }
    /**
     * @Description: AGV历史数据转移处理
     * @Author: jzh
     * @Date: 2020/7/9
     */ 
    @Override
    public Resp dealAgvTask(List<WcsAgvTask> listAgvTask, List<Long> taskIdList) {
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
            String sql = "insert into wcs_agv_task (task_no,task_id,task_status,pallet_code," +
                    "path_id,error_msg,remark,create_by,gmt_create,gmt_start," +
                    "last_modified_by,gmt_modified,gmt_end,agv_task_type,action_type) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);

            for(int i = 0; i < listAgvTask.size(); i++){
                //task_no
                ps.setInt(1,listAgvTask.get(i).getTaskNo());
                //task_id
                if (listAgvTask.get(i).getTaskId() == null){
                    ps.setNull(2, Types.BIGINT);
                }else {
                    ps.setLong(2, listAgvTask.get(i).getTaskId());
                }
                //task_status
                ps.setString(3,listAgvTask.get(i).getTaskStatus());
                //pallet_code
                ps.setString(4,listAgvTask.get(i).getPalletCode());
                //path_id
                if (listAgvTask.get(i).getPathId() == null){
                    ps.setNull(5, Types.INTEGER);
                }else {
                    ps.setInt(5,listAgvTask.get(i).getPathId());
                }

                //error_msg
                ps.setString(6,listAgvTask.get(i).getErrorMsg());
                //remark
                ps.setString(7,listAgvTask.get(i).getRemark());
                //create_by
                ps.setString(8,listAgvTask.get(i).getCreateBy());
                //gmt_create
                if (listAgvTask.get(i).getGmtCreate() == null){
                    ps.setNull(9, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(9,new Timestamp(listAgvTask.get(i).getGmtCreate().getTime()));
                }
                //gmt_start
                if (listAgvTask.get(i).getGmtStart() == null){
                    ps.setNull(10, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(10,new Timestamp(listAgvTask.get(i).getGmtStart().getTime()));
                }
                //last_modified_by
                ps.setString(11,listAgvTask.get(i).getLastModifiedBy());
                //gmt_modified
                if (listAgvTask.get(i).getGmtModified() == null){
                    ps.setNull(12, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(12,new Timestamp(listAgvTask.get(i).getGmtModified().getTime()));
                }
                //gmt_end
                if (listAgvTask.get(i).getGmtEnd() == null){
                    ps.setNull(13, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(13,new Timestamp(listAgvTask.get(i).getGmtEnd().getTime()));
                }
                //agv_task_type
                ps.setString(14,listAgvTask.get(i).getAgvTaskType());
                //action_type
                ps.setString(15,listAgvTask.get(i).getActionType());
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
                dealNum = listAgvTask.size();
            }else {
                dealNum = dealNum+(listAgvTask.size()-dealNum);
            }
            conn.close();
            System.out.println("wcs_agv_task插入his条数"+dealNum);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setCode("0");
            resp.setMsg("wcs_agv_task批量插入异常");
            return resp;
        } finally {
            JDBCUtil.close(conn,ps,rs);
        }
        //开始批量删除原数据库
        //根据taskIdList
        int delectNum = wcsAgvTaskMapper.deleteAgvTaskByIdList(taskIdList);
        System.out.println("wcs_agv_task删除原库数量"+delectNum);
        if (delectNum != dealNum){
            resp.setCode("0");
            resp.setMsg("wcs_agv_task批量插入"+dealNum+"批量删除"+delectNum);
            return resp;
        }

        resp.setCode("1");
        resp.setMsg("wcs_agv_task转移成功");
        return resp;
    }

    /**
     * 删除无效任务：长时间没完成的
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Resp delUselessTask(Integer id) {
        Resp resp = new Resp();
//        失败
        resp.setCode("1");
        WcsTask wcsTask = wcsTaskMapper.selectByPrimaryKey(id);
        if (null == wcsTask){
            resp.setMsg("任务不存在");
            return resp;
        }
        String taskStatus = wcsTask.getTaskStatus();
        if ("3".equals(taskStatus)){
            resp.setMsg("任务已完成");
            return resp;
        }
        Date createTime = wcsTask.getGmtCreate();
//        超过2小时才能删除
        Long diff = System.currentTimeMillis()-createTime.getTime();
        if (diff < 6 * 3600 * 1000){
            resp.setMsg("时间小于6小时！");
            return resp;
        }

        Long taskId = wcsTask.getTaskId();
        wcsTaskMapper.deleteByPrimaryKey(id);
        List<WcsAgvTask> agvTasks = wcsAgvTaskMapper.selectByTaskId(taskId);
        if (agvTasks.size() > 0){
            WcsAgvTask agvTask = agvTasks.get(0);
            wcsAgvTaskMapper.deleteByPrimaryKey(agvTask.getAgvTaskId());
        }
        List<WcsFourwaycarTask> fourwaycarTasks = wcsFourwaycarTaskMapper.selectByTaskId(taskId);
        if (fourwaycarTasks.size() > 0){
            WcsFourwaycarTask fourwaycarTask = fourwaycarTasks.get(0);
            wcsFourwaycarTaskMapper.deleteByPrimaryKey(fourwaycarTask.getFourwaycarTaskId());
        }
        resp.setCode("0");
        return resp;
    }

}
