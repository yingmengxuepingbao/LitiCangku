package com.penghaisoft.wcs.log.model.business;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.expose.dto.AgvCallback;
import com.penghaisoft.wcs.log.model.entity.WcsAgvReportLog;

/**
* @Description agv上报日志服务
* @Date 2020/7/25 9:14
 * zhangx
* @param
* @return
**/
public interface IWcsAgvReportLogService {

    /**
    * @Description 添加日志
    * @Date 2020/7/25 9:19
    * @param agvCallback
    * @return void
    **/
    void addReportLog(AgvCallback agvCallback);
    /**
     * @Description: AGV回调日志查询
     * @Author: jzh
     * @Date: 2020/7/27
     */
    Pager<WcsAgvReportLog> findAgvReportLog(int page, int rows, WcsAgvReportLog wcsAgvReportLog);
}
