package com.penghaisoft.wms.nuohua.service;

import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsMoveStereoscopic;

import java.util.LinkedList;

/**
 * @Description  出库/移库 业务
 * @Author zhangxin
 * @Date 2022-08-11
 **/
public interface WmsNHService {

    /**
     *功能描述: 出库/移库相关业务
     * 同层移库
     * 非同层移库
     * @author zhangxin
     * @date 2022/8/11
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    ResponseResult getResponseResultCK(Resp resp, DifferentBusinessNHService differentBusinessNHService, StringBuffer sb, String orderNo, LinkedList wmsOutTaskList);
    /**
     *功能描述: 移库相关业务
     * 同层移库
     * 非同层移库
     * @author zhangxin
     * @date 2022/8/11
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
     ResponseResult getResponseResultYK(WmsMoveStereoscopic wmsMoveStereoscopic, DifferentBusinessNHService differentBusinessNHService, WmsMoveStereoscopic wmsMoveStereoscopicNew, long taskId);
}
