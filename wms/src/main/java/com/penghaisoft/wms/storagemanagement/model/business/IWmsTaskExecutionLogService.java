//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.expose.WcsTransOb;
import com.penghaisoft.wms.storagemanagement.model.entity.TurnoverDTO;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import java.util.List;

public interface IWmsTaskExecutionLogService {
    Resp create(WmsTaskExecutionLog wmsTaskExecutionLog);

    Resp delete(WmsTaskExecutionLog wmsTaskExecutionLog);

    Resp deleteWuli(WmsTaskExecutionLog wmsTaskExecutionLog);

    Pager<WmsTaskExecutionLog> findListByCondition(int page, int rows, WmsTaskExecutionLog condition);

    WmsTaskExecutionLog findById(String id);

    Resp update(WmsTaskExecutionLog wmsTaskExecutionLog);

    Resp updateByTaskId(WmsTaskExecutionLog wmsTaskExecutionLog);

    List<WmsTaskExecutionLog> queryByAny(WmsTaskExecutionLog condition);
    /**
     *功能描述: 根据任务号查询数据
     * @date 2022/9/17
     * @params
     * @return com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog
     */
    WmsTaskExecutionLog queryByTask(WmsTaskExecutionLog condition);

    Resp inStereoscopicTaskCreate(WmsTaskExecutionLog condition);

    Resp inStereoscopicTaskCreateVP(WmsTaskExecutionLog condition);

    Resp reportInStereoscopicTask(WmsTaskExecutionLog condition);

    Resp reportOutYkTask(WmsTaskExecutionLog condition);

    Resp reportOutStraightTask(WmsTaskExecutionLog condition);
    /**
     *功能描述: wcs任务上报，更新任务表。
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    Resp reportUpOutStraightTask(WmsTaskExecutionLog condition);

    Resp reportOutSortingTask(WmsTaskExecutionLog condition);

    Resp reportOutCheckTask(WmsTaskExecutionLog condition);

    Resp reportOutCrossTask(WmsTaskExecutionLog condition);

    Resp reportVirtualpalletTask(WmsTaskExecutionLog condition);

    Resp checkWcsParamCommon(WcsTransOb wcsTransOb);
    /**
     *功能描述: 移库 - wcs入参校验
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    Resp checkWcsParamCommon_YK(WcsTransOb wcsTransOb);

    Pager<WmsTaskExecutionLog> countInAndOut(int page, int rows, WmsTaskExecutionLog wmsTaskExecutionLog);

    Resp taskToComplete(long taskId, String palletCode);
//========================现场添加接口====================
    /**
     *功能描述: 查询是否存在入库任务，入库优先 （原材料：50 成品：10）
     * @params
     * @return java.util.List<com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog>
     */
    List<WmsTaskExecutionLog> selReceipt(WmsTaskExecutionLog wmsTaskExecutionLog);
    /**
     *功能描述: 进出周转汇总
     * @params
     * @return java.util.List<com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog>
     */
    List<TurnoverDTO> shaiXuancountInAndOut(WmsTaskExecutionLog wmsTaskExecutionLog);
}