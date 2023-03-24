//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business;

import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;

public interface IWmsHandleWcsReportService {
    Resp handlePalletHandOutTask(WmsTaskExecutionLog condition);

    Resp handleSortHandOutTask(WmsTaskExecutionLog wmsTaskExecutionLog);

    Resp handleAutoSelectTask(WmsTaskExecutionLog wmsTaskExecutionLog);

    void lockPallet(WmsTaskExecutionLog wmsTaskExecutionLog);

    Resp sendLEDData(WmsTaskExecutionLog wmsTaskExecutionLog, String ledIp, String ledPort);

    Resp sendLEDDataWelcome(String ledIp, String ledPort);
}
