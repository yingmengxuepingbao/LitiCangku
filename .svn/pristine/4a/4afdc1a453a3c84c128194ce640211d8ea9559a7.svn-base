package com.penghaisoft.pda.outwarehouse.service;

import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.outwarehouse.model.WmsTaskExecutionLog;
import com.penghaisoft.pda.storage.model.WmsGoods;

import java.util.List;

/**
 * <p>
 * 业务接口类
 * </p>
 *
 * @author
 * @createDate
 **/
public interface WmsTaskExecutionLogService {

    void updateByTaskId(WmsTaskExecutionLog wmsTaskExecutionLog);

    WmsTaskExecutionLog selectByTaskId(String id);

    public Resp inStereoscopicTaskCreate(WmsTaskExecutionLog condition);

    List<WmsTaskExecutionLog> selectByStatus(String status,String type);
    /**
     * 根据托盘号，找到最近的本托盘出库商品
     * @param palletCode
     * @return
     */
    WmsGoods queryLastOutGoodsByPallet(String palletCode);
//=================现场添加=============================
    /**
     *功能描述:
     * @params  
     * @return java.util.List<com.penghaisoft.pda.outwarehouse.model.WmsTaskExecutionLog>
     */
    List<WmsTaskExecutionLog> selectTaskByPallet(WmsTaskExecutionLog condition);
}
