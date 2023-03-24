package com.penghaisoft.wcs.monitormanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;

import java.util.List;

public interface IWcsErrorLogService {

    void addLog(WcsErrorLog errorLog);


    Pager<WcsErrorLog> findListByCondition(int page, int rows, WcsErrorLog condition);

    List<WcsErrorLog> queryMothAgoError();

    Resp dealErrorLog(List<WcsErrorLog> list, List<Integer> idList);


    /**
     * 增加agv 错误日志
     * @param agvId
     * @param errType
     * @param errCode
     * @param level
     * @param faultSource
     * @param instruction
     * @param description
     */
    void addAgvLog(int agvId, String errType, Short errCode, Short level, String faultSource, String instruction, String description);

    /**
     * 增加码垛机的日志
     * @param palletizingId
     * @param errType
     * @param errCode
     * @param level
     * @param faultSource
     * @param instruction
     * @param description
     */
    void addPalletizingLog(int palletizingId, String errType, Short errCode, Short level, String faultSource, String instruction, String description);


    /**
     * 四向车错误日志
     * @param errType
     * @param errCode
     * @param level
     * @param faultSource
     * @param instruction
     * @param description
     */
    void addFourwaycarLog(String errType, Short errCode, Short level, String faultSource, String instruction, String description);

}