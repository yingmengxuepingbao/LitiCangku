package com.penghaisoft.wms.nuohua.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.nuohua.service.CheckJsonObjictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description 检查接口传入的json串
 * @Author zhangxin
 * @Date 2022
 **/
@Slf4j
@Service("checkJsonObjictService")
public class CheckJsonObjictServiceImpl extends BaseService implements CheckJsonObjictService {

    /**
     *功能描述: 请求出库 - 接口
     * 领料出库叫料
     * 产成品发货
     * applyTime	请求时间	String
     * outboundID	出库任务号	String
     * outboundType	出库任务类型	String
     * endLocation	终点位置	String
     * batchCode	批次号	String
     * materialsCode	物料编码	String
     * Weight	数量	String
     * userDefined1	自定义1	String
     * userDefined2	自定义2	String
     * userDefined3	自定义3	String
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    @Override
    public Resp outboundTask(JSONObject jsonObject){
        if(jsonObject != null && !"".equals(jsonObject)){
            if(jsonObject.getString("outboundID")==null ||"".equals(jsonObject.getString("outboundID"))){
                return this.fail("请求的参数-出库任务号 为空！");
            }
            if(jsonObject.getString("outboundType")==null ||"".equals(jsonObject.getString("outboundType"))){
                return this.fail("请求的参数-出库任务类型 为空！");
            }else{
                if("0".equals(jsonObject.getString("outboundType"))){
                    log.info("请求的参数-出库任务类型：原材料："+jsonObject.getString("outboundType") );
                    jsonObject.put("outboundType","50");
                }
                if("1".equals(jsonObject.getString("outboundType"))){
                    log.info("请求的参数-出库任务类型：产成品："+jsonObject.getString("outboundType") );
                    jsonObject.put("outboundType","10");
                }
            }
            if(jsonObject.getString("batchCode")==null ||"".equals(jsonObject.getString("batchCode"))){
                return this.fail("请求的参数-批次号 为空！");
            }
            if(jsonObject.getString("materialsCode")==null ||"".equals(jsonObject.getString("materialsCode"))){
                return this.fail("请求的参数-物料编码 为空！");
            }
            if(jsonObject.getString("Weight")==null ||"".equals(jsonObject.getString("Weight"))){
                return this.fail("请求的参数-数量 为空！");
            }
        }else{
            return this.fail("请求的参数为空！");
        }
        Resp resp = this.success();
        resp.setData(jsonObject);
        return resp;
    }

    /**
     *功能描述: 接收出库任务状态
     * areaCode	当前立库库区编码	String	是
     * taskId	任务号	Integer	是
     * taskType	20 直发出库	String	是
     * palletCode	托盘号码	String	是
     * taskStatus	2执行3完成4异常	String	是
     * msg	信息	String	否
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    @Override
    public Resp outwarehouse(JSONObject jsonObject){
        log.info("接收出库任务状态-校验数据格式："+jsonObject);
        if(jsonObject != null && !"".equals(jsonObject)){
            if(jsonObject.getString("areaCode")==null ||"".equals(jsonObject.getString("areaCode"))){
                return this.fail("接收出库任务状态 - 请求的参数-当前立库库区编码 为空！");
            }
            if(jsonObject.get("taskId")==null || jsonObject.getInteger("taskId")==0){
                return this.fail("接收出库任务状态 -请求的参数-出库任务号 为空！");
            }
            if(jsonObject.getString("taskType")==null ||"".equals(jsonObject.getString("taskType"))){
                return this.fail("接收出库任务状态 -请求的参数-任务类型 为空！");
            }
            if(jsonObject.getString("palletCode")==null ||"".equals(jsonObject.getString("palletCode"))){
                return this.fail("接收出库任务状态 -请求的参数-托盘号码 为空！");
            }
            if(jsonObject.getString("taskStatus")==null ||"".equals(jsonObject.getString("taskStatus"))){
                return this.fail("接收出库任务状态 -请求的参数-任务状态 为空！");
            }
        }else{
            return this.fail("请求的参数为空！");
        }
        Resp resp = this.success();
        resp.setData(jsonObject);
        return resp;
    }

    //--------------现场新增接口-------------------
    /**
     *功能描述: 校验 agv 出入库单据下发 接口
     *
     * billNo	String	True	单据号，唯一。下发给小车执行
     * billType	 Int	True	单据类型（1：入库，2：出库）
     * productType	Int	True	产品类别（1：原料，2：成品）
     * productCode	String	True	产品编号
     * stationCode	String	True	站点编号
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    @Override
    public Resp checkDocumentDistribution(JSONObject jsonObject){
        log.info("接收agv-出入库单据下发-校验数据格式："+jsonObject);
        if(jsonObject != null && !"".equals(jsonObject)){
            /*if(jsonObject.getString("billNo")==null ||"".equals(jsonObject.getString("billNo"))){
                return this.fail("接收agv-出入库单据下发 - 请求的参数 : 单据号 为空！");
            }*/
            if(jsonObject.get("billType")==null || jsonObject.getInteger("billType")==0){
                return this.fail("接收agv-出入库单据下发 -请求的参数 : 单据类型（1：入库，2：出库） 为空！");
            }
            if(jsonObject.getString("productType")==null ||"".equals(jsonObject.getString("productType"))){
                return this.fail("接收agv-出入库单据下发 -请求的参数 : 产品类别（1：原料，2：成品） 为空！");
            }
            if("1".equals(jsonObject.get("billType"))  &&"2".equals(jsonObject.getString("productType"))){
                if(jsonObject.getString("palletNo")==null ||"".equals(jsonObject.getString("palletNo"))){
                    return this.fail("接收agv-出入库单据下发 -请求的参数 : 成品入库- 托盘码为空！");
                }
            }
            if(jsonObject.getString("productCode")==null ||"".equals(jsonObject.getString("productCode"))){
                return this.fail("接收agv-出入库单据下发 -请求的参数 : 产品编号 为空！");
            }
            if(jsonObject.getString("stationCode")==null ||"".equals(jsonObject.getString("stationCode"))){
                return this.fail("接收agv-出入库单据下发 -请求的参数 : 站点编号 为空！");
            }
        }else{
            return this.fail("接收agv-出入库单据下发 - 请求的参数为空！");
        }
        Resp resp = this.success();
        resp.setData(jsonObject);
        return resp;
    }
    @Override
    public Resp checkAGVDriveAwaySafely(JSONObject jsonObject){
        log.info("接收：agv-安全驶离-校验数据格式："+jsonObject);
        if(jsonObject != null && !"".equals(jsonObject)){
            if(jsonObject.get("taskId")==null || jsonObject.getLong("taskId")==0L){
                return this.fail("接收：agv-安全驶离 -请求的参数-任务号 为空！");
            }
            if(jsonObject.get("action")==null || "".equals(jsonObject.getLong("action"))){
                return this.fail("接收：agv-安全驶离 -请求的参数-动作 为空！");
            }else if(!"2".equals(jsonObject.get("action"))){
                return this.fail("接收：agv-安全驶离 -请求的参数-动作 非 告知分离！");
            }
            if(jsonObject.get("agvCode")==null || jsonObject.getLong("agvCode")==0L){
                return this.fail("接收：agv-安全驶离 -请求的参数-动作 为空！");
            }
        }else{
            return this.fail("接收agv-出入库单据下发 - 请求的参数为空！");
        }

        Resp resp = this.success();
        resp.setData(jsonObject);
        return resp;
    }
}
