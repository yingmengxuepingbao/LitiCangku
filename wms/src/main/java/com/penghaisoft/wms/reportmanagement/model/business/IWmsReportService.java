//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.reportmanagement.model.business;

import com.penghaisoft.wms.reportmanagement.model.dto.ReportShowData;
import com.penghaisoft.wms.reportmanagement.model.dto.StorageStatisticCount;

public interface IWmsReportService {
    StorageStatisticCount queryStorageStatisticCount();

    ReportShowData queryLatelySendData(int days);
    //========现场修改，将收发货改成出入库==================
    /**
     *功能描述:
     * @params
     * @return com.penghaisoft.wms.reportmanagement.model.dto.ReportShowData
     */
    ReportShowData getChuRuSendData();
}