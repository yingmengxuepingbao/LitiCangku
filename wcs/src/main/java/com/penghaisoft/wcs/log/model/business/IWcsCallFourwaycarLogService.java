package com.penghaisoft.wcs.log.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.log.model.entity.WcsCallFourwaycarLog;
import com.penghaisoft.wcs.operation.model.FourWayCarTask;

import java.util.Date;
import java.util.Map;

/**
* @Description 调用四向车的日志记录
* @Date 2020/7/29 18:15
 * zhangx
**/
public interface IWcsCallFourwaycarLogService {

    /**
     * 下发四向车指令日志
     * @param fourWayCarTask
     * @param reqFlag 1成功 2业务异常 3服务端异常 4客户端异常
     * @param respData 返回数据
     * @param sendTime 发送时间
     */
    void addTaskReceiveLog(FourWayCarTask fourWayCarTask, String reqFlag, String respData, Date sendTime);


    /**
     * 调用四向车时候的简单接口
     * @param url
     * @param param
     * @param reqFlag
     * @param respData
     * @param sendTime
     */
    void addSimpleLog(String url, Map<String,Object> param, String reqFlag, String respData, Date sendTime);
    /**
     * @Description: 调用四向车日志查询
     * @Author: jzh
     * @Date: 2020/7/30
     */ 
    Pager<WcsCallFourwaycarLog> findCallFourwaycarLog(int page, int rows, WcsCallFourwaycarLog wcsCallFourwaycarLog);
    /**
     * @Description: 四向车日志重发
     * @Author: jzh
     * @Date: 2020/7/30
     */ 
    Resp resend(WcsCallFourwaycarLog wcsCallFourwaycarLog, String loginName);
    /**
     * @Description: 修改重发日志
     * @Author: jzh
     * @Date: 2020/7/30
     */ 
    void updateTaskReceiveLog(String reqFlag, Integer callFourwaycarLogId, String message);
}

