package com.penghaisoft.wms.nuohua.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.wms.nuohua.service.WmsNHAgvService;
import com.penghaisoft.wms.util.PostResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * AGV-接口实现
 */
@Slf4j
@Service("wmsNHAgvService")
public class WmsNHAgvServiceImpl implements WmsNHAgvService {
    //AGV接口地址

    @Value(value = "${AGV.AGV-url}")
    private String AGV_URL;
    //private String AGV_URL="http://192.168.137.1:3345/Api";

    @Autowired
    public PostResponseUtil postResponseUtil;

    @Override
    public JSONObject noVerifyBucketMove(JSONObject map) {
        //任务接收接口-路径http://192.168.137.1:3345/Api/Project/Beijing/Fourwaycar/NoVerifyBucketMove
        String url = AGV_URL + "/Project/Beijing/Fourwaycar/NoVerifyBucketMove";
        String data = postResponseUtil.postJson(url, map);
        System.out.println("返回数据：" + data);
        JSONObject jSONObject = JSONObject.parseObject(data);
        return jSONObject;
    }
    /**
     *功能描述: 入库异常时，将货物送至交接位后上报异常
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject fourwaycarError(String str){
        log.info("==========调入库异常-上报异常: 任务接收接口==============");
        String ret_str= postResponseUtil.postString(AGV_URL+"/Project/Beijing/Novartis/FourwaycarError", str);
        System.out.println("调入库异常-上报异常: 任务接收接口=====返回的数据："+ret_str);
        JSONObject jsonObject =JSONObject.parseObject(ret_str);
        return jsonObject;
    }
    //-----------------现场添加接口--------------
    /**
     *功能描述: 调agv-执行下发
     * 当用户操作PDA将出入库单据推送至WMS中，WMS对任务进行管理，优先下发入库单据，暂停后续的出库单据，该接口由WMS主动下发控制执行任务
     *
     * billNo	String	True	单据号，唯一。下发给小车执行
     * startPoint	String	True	起始编号
     * endPoint	String	True	目标编号
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject executeIssue(JSONObject map){
        log.info("==========调agv-执行下发接口==============");
        String url = AGV_URL + "/Project/Beijing/Fourwaycar/NoVerifyBucketMove";
        log.info("调agv-执行下发:url="+url);
        String data = postResponseUtil.postJson(url, map);
        System.out.println("返回数据：" + data);
        JSONObject jSONObject = JSONObject.parseObject(data);
        return jSONObject;
    }
    /**
     *功能描述: 入库异常调agv取货
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject carError(JSONObject map){
        log.info("==========入库异常调agv取货==============");
        String url = AGV_URL + "/Project/Beijing/Fourwaycar/AddExcept";
        log.info("入库异常调agv取货:url="+url);
        String data = postResponseUtil.postJson(url, map);
        System.out.println("入库异常调agv取货 - 返回数据：" + data);
        JSONObject jSONObject = JSONObject.parseObject(data);
        return jSONObject;
    }
}
