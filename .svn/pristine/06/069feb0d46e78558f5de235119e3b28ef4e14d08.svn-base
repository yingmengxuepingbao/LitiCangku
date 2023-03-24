package com.penghaisoft.wms.nuohua.service;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.util.Resp;

/**
 * @Description 检查接口传入的json串
 * @Author zhangxin
 * @Date 2022
 **/
public interface CheckJsonObjictService {


    Resp outboundTask(JSONObject jsonObject);

    Resp outwarehouse(JSONObject jsonObject);
    //--------------现场新增接口-------------------
    /**
     *功能描述: 校验 agv 出入库单据下发 接口
     * @author zhangxin
     * @date 2022/10/4
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    Resp checkDocumentDistribution(JSONObject jsonObject);
    /**
     *功能描述: agv 安全驶离 状态报告
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    Resp checkAGVDriveAwaySafely(JSONObject jsonObject);
}
