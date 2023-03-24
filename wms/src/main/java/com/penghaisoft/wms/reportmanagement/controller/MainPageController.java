//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.reportmanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.wms.reportmanagement.model.business.IWmsReportService;
import com.penghaisoft.wms.reportmanagement.model.dto.ReportShowData;
import com.penghaisoft.wms.reportmanagement.model.dto.StorageStatisticCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/main"})
public class MainPageController {
    private static final Logger log = LoggerFactory.getLogger(MainPageController.class);
    @Autowired
    private IWmsReportService wmsReportService;

    public MainPageController() {
    }
    /**
     *功能描述: 面板统计数量
     * @params
     * @return com.penghaisoft.wms.reportmanagement.model.dto.StorageStatisticCount
     */
    @GetMapping({"storage/statistic/count"})
    public StorageStatisticCount getStorageStatisticCount() {
        StorageStatisticCount storageStatisticCount = this.wmsReportService.queryStorageStatisticCount();
        return storageStatisticCount;
    }

    @GetMapping({"storage/lately"})
    public JSONObject getLatelySendData() {
        JSONObject result = new JSONObject();
        ReportShowData sendData = this.wmsReportService.queryLatelySendData(7);
        result.put("sendData", sendData);
        return result;
    }
    //========现场修改，将收发货改成出入库==================
    @GetMapping({"storage/getChuRuSendData"})
    public JSONObject getChuRuSendData() {
        JSONObject result = new JSONObject();
        ReportShowData sendData = this.wmsReportService.getChuRuSendData();
        result.put("sendData", sendData);
        return result;
    }
}
