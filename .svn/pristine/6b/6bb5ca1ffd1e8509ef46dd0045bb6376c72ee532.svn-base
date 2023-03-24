package com.penghaisoft.wcs.monitormanagement.model.business.impl;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.monitormanagement.model.business.IWcsErrorLogService;
import com.penghaisoft.wcs.monitormanagement.model.dao.WcsErrorLogMapper;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;
import com.penghaisoft.wcs.util.JDBCUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description WcsErrorLogServiceImpl
 * @Auther zhangxu
 * @Date 2020/3/18 14:09
 **/
@Service
public class WcsErrorLogServiceImpl implements IWcsErrorLogService {

    @Autowired
    private WcsErrorLogMapper wcsErrorLogMapper;


    @Override
    public void addLog(WcsErrorLog errorLog) {
//        只存储不超过256个字符的提示信息
        String msg = errorLog.getDescription();
        if (msg.length() >= 256) {
            msg = msg.substring(0, 255);
            errorLog.setDescription(msg);
        }
        wcsErrorLogMapper.insertSelective(errorLog);
    }


    /**
     * 查询列表
     *
     * @param page
     * @param rows
     * @return
     */
    @Override
    public Pager<WcsErrorLog> findListByCondition(int page, int rows, WcsErrorLog condition) {
        Pager<WcsErrorLog> pager = new Pager<>();
        pager.setPage(page);
        pager.setRows(rows);
        long size = wcsErrorLogMapper.queryCount(condition);
        List<WcsErrorLog> records = new ArrayList<WcsErrorLog>();
        if (size > 0) {
            records = wcsErrorLogMapper.queryList(pager, condition);
        }
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

    /**
     * 查询错误日志（一月前）
     * @return
     */
    @Override
    public List<WcsErrorLog> queryMothAgoError() {

        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MONTH, -1);
//            一月前时间点
        Date lastyear = c.getTime();
        WcsErrorLog errorLog = new WcsErrorLog();
        errorLog.setGmtCreateMax(lastyear);
        List<WcsErrorLog> list = wcsErrorLogMapper.queryByGmtCreateMax(errorLog);
        return list;
    }

    /**
     * 转移错误日志
     * @param list
     * @param idList
     * @return
     */
    @Override
    public Resp dealErrorLog(List<WcsErrorLog> list, List<Integer> idList) {
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
            String sql = "insert into wcs_error_log (device_id,device_type,err_type,err_code," +
                    "level,fault_source,instruction,description,gmt_create) values(?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);

            for(int i = 0; i < list.size(); i++){
                //device_id
                if (list.get(i).getDeviceId() == null){
                    ps.setNull(1, Types.INTEGER);
                }else {
                    ps.setInt(1,list.get(i).getDeviceId());
                }
                //device_type
                ps.setString(2,list.get(i).getDeviceType());
                //err_type
                ps.setString(3,list.get(i).getErrType());
                //err_code
                if (list.get(i).getErrCode() == null){
                    ps.setNull(4, Types.INTEGER);
                }else {
                    ps.setInt(4,list.get(i).getErrCode());
                }
                //level
                if (list.get(i).getLevel() == null){
                    ps.setNull(5, Types.INTEGER);
                }else {
                    ps.setInt(5,list.get(i).getLevel());
                }
                //fault_source
                ps.setString(6,list.get(i).getFaultSource());
                //instruction
                ps.setString(7,list.get(i).getInstruction());
                //description
                ps.setString(8,list.get(i).getDescription());
                //gmt_create
                if (list.get(i).getGmtCreate() == null){
                    ps.setNull(9, java.sql.Types.DATE);
                }else {
                    ps.setTimestamp(9,new Timestamp(list.get(i).getGmtCreate().getTime()));
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
                dealNum = list.size();
            }else {
                dealNum = dealNum+(list.size()-dealNum);
            }
            conn.close();
            System.out.println("wcs_error_log插入his条数"+dealNum);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setCode("0");
            resp.setMsg("wcs_error_log批量插入异常");
            return resp;
        } finally {
            JDBCUtil.close(conn,ps,rs);
        }
        //开始批量删除原数据库
        //根据taskIdList
        int delectNum = wcsErrorLogMapper.deleteErrorLogByIdList(idList);
        System.out.println("wcs_error_log删除原库数量"+delectNum);
        if (delectNum != dealNum){
            resp.setCode("0");
            resp.setMsg("wcs_error_log批量插入"+dealNum+"批量删除"+delectNum);
            return resp;
        }

        resp.setCode("1");
        resp.setMsg("wcs_error_log转移成功");
        return resp;
    }


    /**
     * 增加agv 错误日志
     *
     * @param agvId
     * @param errType
     * @param errCode
     * @param level
     * @param faultSource
     * @param instruction
     * @param description
     */
    @Override
    public void addAgvLog(int agvId, String errType, Short errCode, Short level, String faultSource, String instruction, String description) {
        //记录错误日志
        WcsErrorLog errorLog = new WcsErrorLog();
        errorLog.setDeviceId((short)agvId);
        errorLog.setDeviceType("4");
//          `err_type` char(2) DEFAULT NULL COMMENT '故障类型1光电异常 2移动异常 3连接异常 4上位机指令异常 5伺服电机异常\r\n21 服务端异常 22 网络异常',
        errorLog.setErrType(errType);
        errorLog.setErrCode(errCode);
        errorLog.setLevel(level);
        errorLog.setFaultSource(faultSource);
        errorLog.setInstruction(instruction);
        errorLog.setGmtCreate(new Date());
        if (description.length() >= 256) {
            description = description.substring(0, 255);
            errorLog.setDescription(description);
        }else {
            errorLog.setDescription(description);
        }
        wcsErrorLogMapper.insertSelective(errorLog);
    }

    /**
     * 增加码垛机的日志
     * zhangx
     * @param palletizingId
     * @param errType
     * @param errCode
     * @param level
     * @param faultSource
     * @param instruction
     * @param description
     */
    @Override
    public void addPalletizingLog(int palletizingId, String errType, Short errCode, Short level, String faultSource, String instruction, String description) {
        //记录错误日志
        WcsErrorLog errorLog = new WcsErrorLog();
        errorLog.setDeviceId((short)palletizingId);
        errorLog.setDeviceType("1");
//          `err_type` char(2) DEFAULT NULL COMMENT '故障类型1光电异常 2移动异常 3连接异常 4上位机指令异常 5伺服电机异常\r\n21 服务端异常 22 网络异常',
        errorLog.setErrType(errType);
        errorLog.setErrCode(errCode);
        errorLog.setLevel(level);
        errorLog.setFaultSource(faultSource);
        errorLog.setInstruction(instruction);
        errorLog.setGmtCreate(new Date());
        if (description.length() >= 256) {
            description = description.substring(0, 255);
            errorLog.setDescription(description);
        }else {
            errorLog.setDescription(description);
        }
        wcsErrorLogMapper.insertSelective(errorLog);
    }

    /**
     * 四向车错误日志
     *
     * @param errType
     * @param errCode
     * @param level
     * @param faultSource
     * @param instruction
     * @param description
     */
    @Override
    public void addFourwaycarLog(String errType, Short errCode, Short level, String faultSource, String instruction, String description) {
        //记录错误日志
        WcsErrorLog errorLog = new WcsErrorLog();
        errorLog.setDeviceId((short)5);
        errorLog.setDeviceType("5");
//          `err_type` char(2) DEFAULT NULL COMMENT '故障类型1光电异常 2移动异常 3连接异常 4上位机指令异常 5伺服电机异常\r\n21 服务端异常 22 网络异常',
        errorLog.setErrType(errType);
        errorLog.setErrCode(errCode);
        errorLog.setLevel(level);
        errorLog.setFaultSource(faultSource);
        errorLog.setInstruction(instruction);
        errorLog.setGmtCreate(new Date());
        if (description.length() >= 256) {
            description = description.substring(0, 255);
            errorLog.setDescription(description);
        }else {
            errorLog.setDescription(description);
        }
        wcsErrorLogMapper.insertSelective(errorLog);
    }
}
