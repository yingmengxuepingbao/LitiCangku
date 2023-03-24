package com.penghaisoft.wcs.log.model.business.impl;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.expose.dto.AgvCallback;
import com.penghaisoft.wcs.log.model.business.IWcsAgvReportLogService;
import com.penghaisoft.wcs.log.model.dao.WcsAgvReportLogMapper;
import com.penghaisoft.wcs.log.model.entity.WcsAgvReportLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * @ClassName WcsAgvReportLogServiceImpl
 * @Description agv上报服务类
 * @Author zhangx
 * @Date 2020/7/25 9:16
 **/
@Service
public class WcsAgvReportLogServiceImpl implements IWcsAgvReportLogService {

    @Autowired
    private WcsAgvReportLogMapper agvReportLogMapper;
    /**
     * @param agvCallback
     * @return void
     * @Description 添加日志
     * @Date 2020/7/25 9:19
     **/
    @Override
    public void addReportLog(AgvCallback agvCallback) {
/*        {
            "cooX": "60058.0",
                "cooY": "65270.0",
                "currentPositionCode": "H2",
                "data": "null",
                "mapCode": "XY",
                "mapDataCode": "060058XY065270",
                "method": "end",
                "podCode": "100003",
                "reqCode": "1737424510D15XS",
                "reqTime": "2020-07-22 09:30:03",
                "robotCode": "268",
                "taskCode": "972200007",
                "wbCode": "m5"
        }*/
        String currentPositionCode = agvCallback.getCurrentPositionCode();
        String method = agvCallback.getMethod();
        Integer taskCode = Integer.parseInt(agvCallback.getTaskCode());
        String robotCode = agvCallback.getRobotCode();
        String wbCode = agvCallback.getWbCode();
        String reqCode = agvCallback.getReqCode();

        WcsAgvReportLog agvReportLog = new WcsAgvReportLog();
        agvReportLog.setReqCode(reqCode);
        agvReportLog.setTaskCode(taskCode);
        agvReportLog.setCurrentPositionCode(currentPositionCode);
        agvReportLog.setWbCode(wbCode);
        agvReportLog.setRobotCode(robotCode);
        agvReportLog.setMethod(method);
        agvReportLog.setReceiveTime(new Date());

        agvReportLogMapper.insertSelective(agvReportLog);

    }
    /**
     * @Description: AGV回调日志查询
     * @Author: jzh
     * @Date: 2020/7/27
     */ 
    @Override
    public Pager<WcsAgvReportLog> findAgvReportLog(int page, int rows, WcsAgvReportLog condition) {
        Pager<WcsAgvReportLog> pager = new Pager<>();
        pager.setPage(page);
        pager.setRows(rows);
        List<WcsAgvReportLog> records = agvReportLogMapper.queryList(pager,condition);
        long size = agvReportLogMapper.queryCount(condition);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }
}
