package com.penghaisoft.wms.nuohua.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.expose.WcsTransOb;
import com.penghaisoft.wms.nuohua.service.*;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsSendLedDataService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import com.penghaisoft.wms.util.BarCodeUtils;
import com.penghaisoft.wms.util.LedUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description 项目 - 业务请求入口
 * @Author zhangxin
 * @Date 2022-07-13
 **/
@Slf4j
@RestController
@RequestMapping({"/nuohua"})
public class InterfaceForNHController {
    @Autowired
    private InterfaceForNHService interfaceForNHService;
    @Autowired
    private WmsNHAgvService wmsNHAgvService;
    @Autowired
    private IWmsSendLedDataService sendLedDataService;
    //接口调用记录日志接口
    @Autowired
    private WcsInterfaceCallLogService wcsInterfaceCallLogService;
    /*
     *功能描述: 校验传入的json参数
     */
    @Autowired
    private CheckJsonObjictService checkJsonObjictService;
    /*
     *功能描述: LED-ip
     * @params
     * @return
     */
    @Value("${LED.LED-IP}")
    private String ledIP;

    /**
     *功能描述: 项目- WMS生成推荐货位（库位推荐）
     * @author zhangxin
     * @date 2022/7/13
     * @params WcsTransOb
     * 【 taskId：任务号 】
     * 【 taskType：任务类型 】
     * 【 palletCode：托盘码 】
     * 【 inAddress：入库口地址 】
     * 【 materialCode	物料编码 】
     * 【 bacth	批次号 】
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"productin/location/automatic"})
    public ResponseResult queryLocationCode(@RequestBody WcsTransOb wcsTransOb) {
        //创建时间
        Date gmtCreate=new Date();
        //结束时间
        Date gmtEnd;
        //异常信息
        String errorMsg = wcsTransOb.toString();
        //状态
        String status="1";
        log.info("WMS生成推荐货位（库位推荐）:"+wcsTransOb);
        ResponseResult res = interfaceForNHService.queryLocationCode_HB(wcsTransOb);
        log.info("WMS生成推荐货位（库位推荐）-结束");
        if(Constant.RESULT.FAILED.code.equals(res.getCode())){
            log.info("WMS生成推荐货位（库位推荐）-失败！"+res.getMessage());
            errorMsg = res.getMessage();
            status = "2";
        }
        gmtEnd = new Date();
        Integer elapsedTime = Math.toIntExact((gmtEnd.getTime() - gmtCreate.getTime()));
        wcsInterfaceCallLogService.addWcsInterfaceCallLog(this.getClass().getName()+".queryLocationCode",status,gmtCreate,gmtEnd,errorMsg,elapsedTime);
        return res;
    }
    /**  任务状态接收 入库，出库，移库
     @params
     * areaCode    是	String	当前立库库区编码，一个WMS可能会对应多个立库，要能区分出来
     * taskId	是	Long  任务编号
     * taskType	是	String	 31出库移库
     * palletCode	是	String	托盘号码
     * status	是	String	3完成4异常
     * msg	否	String	如果异常要记录信息
     * @return com.penghaisoft.framework.entity.ResponseResult
     */

    @PostMapping({"/report/allTask"})
    public ResponseResult allTask(@RequestBody WcsTransOb wcsTransOb) {
        //创建时间
        Date gmtCreate=new Date();
        //结束时间
        Date gmtEnd;
        //异常信息
        String errorMsg = wcsTransOb.toString();
        //状态
        String status="1";
        log.info("任务状态:"+wcsTransOb);
        ResponseResult res =new ResponseResult("1","异常！","");
        if(wcsTransOb!=null ){
            //出库
            if("20".equals(wcsTransOb.getTaskType())){
                log.info("直发出库-接收直发出库任务状态:"+wcsTransOb);
                //直发出库
                res= interfaceForNHService.reportOutStraightTask_HB(wcsTransOb);
            }
            //盘点出库
            else if("22".equals(wcsTransOb.getTaskType())){
                log.info("盘点出库-接收盘点出库任务状态:"+wcsTransOb);
                //盘点出库
                res= interfaceForNHService.reportOutStraightTask_HB(wcsTransOb);
            }
            //移库
            else if("30".equals(wcsTransOb.getTaskType())){
                log.info("普通移库-接收普通移库任务状态:"+wcsTransOb);
                res = interfaceForNHService.reportNormalYkTask_HB(wcsTransOb);
            }
            //入库
            else{
                log.info("入库-接收入库任务状态:"+wcsTransOb);
                 res = interfaceForNHService.reportInStereoscopicTask_HB(wcsTransOb);
            }
            //失败记录状态
            if(Constant.RESULT.FAILED.code.equals(res.getCode())){
                log.info("接收任务状态-失败！"+res.getMessage());
                errorMsg = res.getMessage();
                status = "2";
            }
        }
        gmtEnd = new Date();
        Integer elapsedTime = Math.toIntExact((gmtEnd.getTime() - gmtCreate.getTime()));
        wcsInterfaceCallLogService.addWcsInterfaceCallLog(this.getClass().getName()+".allTask",status,gmtCreate,gmtEnd,errorMsg,elapsedTime);
        return res;
    }
    /**
     *功能描述: 生产入库 - 接收入库任务状态
     *
     * @params areaCode    是	String	当前立库库区编码，一个WMS可能会对应多个立库，要能区分出来
     * @params taskId	是	String	任务编号
     * @params taskType	是	String	1 生产入库 2 分拣入库 6托盘入库
     * @params palletCode	是	String	托盘号码
     * @params taskStatus	是	String	2执行3完成4异常
     * @params msg	否	String	如果异常要记录信息
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"/report/inwarehouse/task"})
    public ResponseResult reportInStereoscopicTask(@RequestBody WcsTransOb wcsTransOb) {
        //创建时间
        Date gmtCreate=new Date();
        //结束时间
        Date gmtEnd;
        //异常信息
        String errorMsg = wcsTransOb.toString();
        //状态
        String status="1";
        log.info("生产入库 - 接收入库任务状态:"+wcsTransOb);
        ResponseResult res = interfaceForNHService.reportInStereoscopicTask_HB(wcsTransOb);
        log.info("生产入库 - 接收入库任务状态-结束");
        if(Constant.RESULT.FAILED.code.equals(res.getCode())){
            errorMsg = res.getMessage();
            status = "2";
        }
        gmtEnd = new Date();
        Integer elapsedTime = Math.toIntExact((gmtEnd.getTime() - gmtCreate.getTime()));
        wcsInterfaceCallLogService.addWcsInterfaceCallLog(this.getClass().getName()+".reportInStereoscopicTask",status,gmtCreate,gmtEnd,errorMsg,elapsedTime);
        return res;
    }
    /**
     *功能描述: 普通移库-接收普通移库任务状态
     * @params
     * areaCode    是	String	当前立库库区编码，一个WMS可能会对应多个立库，要能区分出来
     * taskId	是	String	任务号，
     * taskType	是	String	30 普通移库
     * palletCode	是	String	托盘号码
     * status	是	String	3完成4异常
     * msg	否	String	如果异常要记录信息
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */

    @PostMapping({"/report/move/pallet"})
    public ResponseResult reportNormalYkTask(@RequestBody WcsTransOb wcsTransOb) {
        //创建时间
        Date gmtCreate=new Date();
        //结束时间
        Date gmtEnd;
        //异常信息
        String errorMsg = wcsTransOb.toString();
        //状态
        String status="1";
        log.info("普通移库-接收普通移库任务状态:"+wcsTransOb);
        ResponseResult res = interfaceForNHService.reportNormalYkTask_HB(wcsTransOb);
        if(Constant.RESULT.FAILED.code.equals(res.getCode())){
            errorMsg = res.getMessage();
            status = "2";
        }
        gmtEnd = new Date();
        Integer elapsedTime = Math.toIntExact((gmtEnd.getTime() - gmtCreate.getTime()));
        wcsInterfaceCallLogService.addWcsInterfaceCallLog(this.getClass().getName()+".reportNormalYkTask",status,gmtCreate,gmtEnd,errorMsg,elapsedTime);
        return res;
    }
    /**
     *功能描述: 出库时（库内）移库-接收出库时（库内）移库任务状态
     * 出库单发生的移库情况，完成后才能出库
     * @params
     * areaCode    是	String	当前立库库区编码，一个WMS可能会对应多个立库，要能区分出来
     * orderNo	是	String	订单号，
     * taskType	是	String	 31出库移库
     * palletCode	是	String	托盘号码
     * status	是	String	3完成4异常
     * msg	否	String	如果异常要记录信息
     * @return com.penghaisoft.framework.entity.ResponseResult
     */

    @PostMapping({"/report/outwarehouse/move/pallet"})
    public ResponseResult reportOutwarehouseYkTask(@RequestBody WcsTransOb wcsTransOb) {
        //创建时间
        Date gmtCreate=new Date();
        //结束时间
        Date gmtEnd;
        //异常信息
        String errorMsg = wcsTransOb.toString();
        //状态
        String status="1";
        log.info("出库时（库内）移库-接收出库时（库内）移库任务状态:"+wcsTransOb);
        ResponseResult res = interfaceForNHService.reportOutwarehouseYkTask_HB(wcsTransOb);
        if(Constant.RESULT.FAILED.code.equals(res.getCode())){
            errorMsg = res.getMessage();
            status = "2";
        }
        gmtEnd = new Date();
        Integer elapsedTime = Math.toIntExact((gmtEnd.getTime() - gmtCreate.getTime()));
        wcsInterfaceCallLogService.addWcsInterfaceCallLog(this.getClass().getName()+".reportOutwarehouseYkTask",status,gmtCreate,gmtEnd,errorMsg,elapsedTime);
        return res;
    }
    /**
     *功能描述: 直发出库-接收出库任务状态
     * @params
     * {
     *     "areaCode": "L-NH01" //当前立库库区编码
     *     ,
     *     "orderNo": "1111113" //订单号
     *     ,
     *     "taskType": "20" //10 生产入库 11 分拣入库 13盘点入库 14托盘入库 15 异常口入库
     *                     //20 直发出库 21分拣出库 22盘点出库 23人工出库 24 异常出库 25托盘出库
     *                     //30普通移库 31出库移库 40生产越库
     *     ,
     *     "palletCode": "AA0019" //托盘号码
     *     ,
     *     "taskStatus": "3" //2执行3完成4异常
     *     ,
     *     "msg":"任务完成"
     * }
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"/report/outwarehouse/straight"})
    public ResponseResult reportOutStraightTask(@RequestBody JSONObject map) {
        //创建时间
        Date gmtCreate=new Date();
        //结束时间
        Date gmtEnd;
        //异常信息
        String errorMsg = map.toString();
        //状态
        String status="1";
        log.info("收出库任务状态-校验请求数据："+map.toString());
        ResponseResult res;
        Resp resp = checkJsonObjictService.outwarehouse(map);
        if (Constant.RESULT.FAILED.code.equals(resp.getCode())) {
            log.info("校验请求数据- 传入数据异常："+resp.getMsg());
            res = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
            errorMsg = resp.getMsg();
            status = "2";
        }else {
            WcsTransOb wcsTransOb =new WcsTransOb();
            wcsTransOb.setAreaCode(map.getString("areaCode"));
            wcsTransOb.setTaskId(map.getLong("taskId"));
            wcsTransOb.setTaskType(map.getString("taskType"));
            wcsTransOb.setPalletCode(map.getString("palletCode"));
            wcsTransOb.setTaskStatus(map.getString("taskStatus"));
            wcsTransOb.setMsg(map.getString("msg"));
            //直发出库
            res= interfaceForNHService.reportOutStraightTask_HB(wcsTransOb);
            if (Constant.RESULT.FAILED.code.equals(res.getCode())) {
                errorMsg = res.getMessage();
                status = "2";
            }
        }
        //插入日志
        getCallLog(gmtCreate, errorMsg, status,".reportOutStraightTask");

        return res;
    }


    /**
     *功能描述: 接收出库任务状态-盘点口
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"/report/outwarehouse/check"})
    public ResponseResult reportOutCheckTask(@RequestBody WcsTransOb wcsTransOb) {
        //创建时间
        Date gmtCreate=new Date();
        //结束时间
        Date gmtEnd;
        //异常信息
        String errorMsg = wcsTransOb.toString();
        //状态
        String status="1";
        log.info("接收出库任务状态-盘点口:");
        ResponseResult res = interfaceForNHService.reportOutCheckTask_HB(wcsTransOb);
        if(Constant.RESULT.FAILED.code.equals(res.getCode())){
            errorMsg = res.getMessage();
            status = "2";
        }
        //插入日志
        getCallLog(gmtCreate, errorMsg, status,".reportOutCheckTask");
        return res;
    }
    /**
     *功能描述:  1.(WCS->WMS) LED更新显示内容时，WMS接收 WCS接口。WMS通过WCS传递的接口信息来更换LED显示内容。
     * {
     *    "palletCode" : "无",
     *    "batch" : "PC20220820",
     *    "materialCode" : "WL12345678",
     *    "weight" : "1",
     *    "msg" : "未读到托盘码",
     *    "field1" : "",
     *    "field1" : "",
     *    "field1" : "",
     *    "field1" : "",
     * }
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"/httpService/LEDUpdate"})
    public JSONObject LEDUpdate(JSONObject jsonObject) {
        //创建时间
        Date gmtCreate=new Date();
        //结束时间
        Date gmtEnd;
        //异常信息
        String errorMsg = jsonObject.getString("msg");
        //状态
        String status="1";
        log.info("LED显示内容:");
        /**
         * 异常模板调用
         * @param IP ip地址
         * @param batch 批次号
         * @param code 物料编码
         * @param name 物料名称
         * @param count 出库数量
         * @param splitCount 拆托数量
         * @throws Exception
         */
        new LedUtils().sendOut(ledIP,jsonObject.getString("batch"),"1001","test2",100,100);
        //TODO sendLedDataService.sendLEDOut(jsonObject.getLong("materialCode"), jsonObject.getString("batch"), "8", 0);
        gmtEnd = new Date();
        Integer elapsedTime = Math.toIntExact((gmtEnd.getTime() - gmtCreate.getTime()));
        wcsInterfaceCallLogService.addWcsInterfaceCallLog(this.getClass().getName()+".LEDUpdate",status,gmtCreate,gmtEnd,errorMsg,elapsedTime);
        return getJsonObject("000","接收成功");
    }
    /**
     *功能描述: (WCS->WMS) 当入库发生异常时，WCS通知WMS告知入库异常，
     * 根据任务号，查询，入库，是原材料还是成品
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"/httpService/warehousing/abnormal"})
    public JSONObject abnormal(@RequestBody JSONObject jsonObject) {
        //创建时间
        Date gmtCreate=new Date();
        //结束时间
        Date gmtEnd;
        //异常信息
        String errorMsg = jsonObject.toString();
        //状态
        String status="1";
        log.info("入库异常 - LED显示内容:开始！"+errorMsg);
        ResponseResult res =  interfaceForNHService.abnormal(jsonObject);
        log.info("入库异常 - LED显示内容:结束！");
        if(Constant.RESULT.FAILED.code.equals(res.getCode())){
            errorMsg = res.getMessage();
            status = "2";
        }
        gmtEnd = new Date();
        Integer elapsedTime = Math.toIntExact((gmtEnd.getTime() - gmtCreate.getTime()));
        wcsInterfaceCallLogService.addWcsInterfaceCallLog(this.getClass().getName()+".abnormal",status,gmtCreate,gmtEnd,errorMsg,elapsedTime);
        return getJsonObject("0","接收成功");
    }
    /**
     *功能描述: 条形二维码
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @GetMapping("/get128yards")
    public ResponseResult get128yards(HttpServletResponse response, String str) throws IOException {
        //直接返回二维码
        //BarCodeUtils.creat128yards(response ,str);
        //返回数组
        byte[] bytes = BarCodeUtils.generateBarCode128(str, 10.00, 0.3, true, false);
        ResponseResult responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, bytes);
        return responseResult;
    }
    /**
     *功能描述: 出库确认对账 - 修改任务的状态
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @RequestMapping({"/report/taskStatusUpdate"})
    public ResponseResult taskStatusUpdate(WmsTaskExecutionLog wmsTaskExecutionLog) {
        log.info("是否对账 - 修改任务的状态:");
        return interfaceForNHService.taskStatusUpdate(wmsTaskExecutionLog);
    }

    /**
     *功能描述: 入库确认对账 - 修改任务的状态，并且修改库位的状态。
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @RequestMapping({"/report/stereoscopicStatusUpdate"})
    public ResponseResult stereoscopicStatusUpdate(WmsTaskExecutionLog wmsTaskExecutionLog) {
        log.info("是否对账 - 修改任务的状态:");
        return interfaceForNHService.stereoscopicStatusUpdate(wmsTaskExecutionLog);
    }

    /**
     *功能描述: 4)入库异常时，将货物送至交接位后上报异常 - 报废
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    @PostMapping("/httpService/FourwaycarError")
    public JSONObject fourwaycarError(@RequestBody JSONObject map){
        //创建时间
        Date gmtCreate=new Date();
        //结束时间
        Date gmtEnd;
        //异常信息
        String errorMsg = map.toString();
        //状态
        String status="1";
        log.info("=======入库异常时，将货物送至交接位后上报异常-接口："+map);
        JSONObject jsonObject= wmsNHAgvService.fourwaycarError(map.toString());
        log.info("=======入库异常时，将货物送至交接位后上报异常-接口返回："+jsonObject);
        gmtEnd = new Date();
        Integer elapsedTime = Math.toIntExact((gmtEnd.getTime() - gmtCreate.getTime()));
        wcsInterfaceCallLogService.addWcsInterfaceCallLog(this.getClass().getName()+".fourwaycarError",status,gmtCreate,gmtEnd,errorMsg,elapsedTime);
        return jsonObject;
    }
    /**
     *功能描述: PDA 出库请求
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
     * @return com.alibaba.fastjson.JSONObject
     */

    @PostMapping("/httpService/outboundTask")
    public JSONObject outboundTask(@RequestBody JSONObject map){
        //创建时间
        Date gmtCreate=new Date();
        //结束时间
        Date gmtEnd;
        //异常信息
        String errorMsg = map.toString();
        //状态
        String status="1";
        JSONObject jsonObject=new JSONObject() ;
        //校验传过来的jsonobject数据
        log.info("=======调出库请求-接口："+map);
        Resp resp = checkJsonObjictService.outboundTask(map);
        if (Constant.RESULT.FAILED.code.equals(resp.getCode())) {
            log.info("校验请求数据- 传入数据异常："+resp.getMsg());
            jsonObject.put("tresStat","1");
            jsonObject.put("resOutbound",resp.getMsg());
            jsonObject.put("resTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            jsonObject.put("userDefined1","");
            jsonObject.put("userDefined2","");
            jsonObject.put("userDefined3","");
            errorMsg = resp.getMsg();
            status = "2";
        }
        try{
            log.info("=======AGV /PDA 请求出库 /盘库 :开始");
            //请求出库，业务处理
            jsonObject = interfaceForNHService.requestDelivery(JSONObject.parseObject(resp.getData().toString()));
            log.info("=======AGV /PDA 请求出库 /盘库 -接口返回：" + jsonObject);
            errorMsg= jsonObject.getString("resOutbound");
        }catch (Exception e){
            log.info("调出库请求-异常 "+e.getMessage());
            errorMsg =e.getMessage();
            status = "2";
        }
        gmtEnd = new Date();
        Integer elapsedTime = Math.toIntExact((gmtEnd.getTime() - gmtCreate.getTime()));
        wcsInterfaceCallLogService.addWcsInterfaceCallLog(this.getClass().getName()+".outboundTask",status,gmtCreate,gmtEnd,errorMsg,elapsedTime);
        return jsonObject;
    }
    /**
     *功能描述: 记录调此方法的日志
     * @params
     * @return void
     */
    private void getCallLog(Date gmtCreate, String errorMsg, String status,String str) {
        Date gmtEnd = new Date();
        Integer elapsedTime = Math.toIntExact((gmtEnd.getTime() - gmtCreate.getTime()));
        wcsInterfaceCallLogService.addWcsInterfaceCallLog(this.getClass().getName()+str,status,gmtCreate,gmtEnd,errorMsg,elapsedTime);
    }
    /**
     *功能描述: 返回状态
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    private JSONObject getJsonObject(String cood,String msg) {
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("rtCode",cood);
        jsonObject.put("rtDescr",msg);
        return jsonObject;
    }
    //===========================================现场接口修改====================================
    /**
     * 功能描述: wcs 入库请求 只给个TaskID 任务号，托盘号，物料号
     * agv先请求 wms
     * wms 调agv下任务
     * wcs请求入库，根据任务ID 查询相关数据
     * 如果是原材料，接受托盘号，解析物料号（因为物料号里是一长串数据）
     * 如果是成品，校验托盘号即可
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"productin/requestWarehousing"})
    public ResponseResult requestWarehousing(@RequestBody WcsTransOb wcsTransOb) throws Exception {
        //创建时间
        Date gmtCreate=new Date();
        //结束时间
        Date gmtEnd;
        //异常信息
        String errorMsg = wcsTransOb.toString();
        //状态
        String status="1";
        log.info("wcs入库请求-生成库位推荐-给wcs下入库任务:"+wcsTransOb);
        ResponseResult res = interfaceForNHService.requestWarehousing(wcsTransOb);
        log.info("wcs入库请求-生成库位推荐-给wcs下入库任务:-结束");
        if(Constant.RESULT.FAILED.code.equals(res.getCode())){
            log.info("wcs入库请求-生成库位推荐-给wcs下入库任务:-失败！"+res.getMessage());
            errorMsg = res.getMessage();
            status = "2";
        }
        gmtEnd = new Date();
        Integer elapsedTime = Math.toIntExact((gmtEnd.getTime() - gmtCreate.getTime()));
        wcsInterfaceCallLogService.addWcsInterfaceCallLog(this.getClass().getName()+".queryLocationCode",status,gmtCreate,gmtEnd,errorMsg,elapsedTime);
        return res;
    }
    /**
     *功能描述: （AGV）用户在操作手持，产生出入库单据，下发到冷库四项车WMS中
     * @author zhangxin
     * @date 2022/10/4
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    @PostMapping("/httpService/documentDistribution")
    public JSONObject documentDistribution(@RequestBody JSONObject map){
        ResponseResult res;
        //创建时间
        Date gmtCreate = new Date();
        //结束时间
        Date gmtEnd;
        //异常信息
        String errorMsg = map.toString();
        //状态
        String status = "1";
        //校验传参
        Resp resp =checkJsonObjictService.checkDocumentDistribution(map);

        if (Constant.RESULT.FAILED.code.equals(resp.getCode())) {
            log.info("校验请求数据- 传入数据异常："+resp.getMsg());
            res = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
            errorMsg = resp.getMsg();
            status = "2";
        }else {
            log.info("（AGV）出入库单据下发:" + map);
            res= interfaceForNHService.documentDistribution(map);
            log.info("（AGV）出入库单据下发-结束:");
            if (Constant.RESULT.FAILED.code.equals(res.getCode())) {
                errorMsg = res.getMessage();
                status = "2";
            }
            gmtEnd = new Date();
            Integer elapsedTime = Math.toIntExact((gmtEnd.getTime() - gmtCreate.getTime()));
            wcsInterfaceCallLogService.addWcsInterfaceCallLog(this.getClass().getName() + ".documentDistribution", status, gmtCreate, gmtEnd, errorMsg, elapsedTime);
        }
        //将数据转换，返回
        return retJsonObject(res.getCode(),res.getMessage());
    }

    /**
     *功能描述: AGV 异常取货 安全驶离 - 上报  wms
     * wms更改任务状态。
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    @PostMapping("/httpService/AGVDriveAwaySafely")
    public JSONObject AGVDriveAwaySafely(@RequestBody JSONObject map){
        ResponseResult res;
        //创建时间
        Date gmtCreate = new Date();
        //结束时间
        Date gmtEnd;
        //异常信息
        String errorMsg = map.toString();
        //状态
        String status = "1";
        //校验传参
        Resp resp =checkJsonObjictService.checkAGVDriveAwaySafely(map);

        if (Constant.RESULT.FAILED.code.equals(resp.getCode())) {
            log.info("校验请求数据- 传入数据异常："+resp.getMsg());
            res = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
            errorMsg = resp.getMsg();
            status = "2";
        }else {
            log.info("AGV 异常取货 安全驶离:开始" + map);
            res= interfaceForNHService.agvDriveAwaySafely(map);
            log.info("AGV 异常取货 安全驶离-结束:");
            if (Constant.RESULT.FAILED.code.equals(res.getCode())) {
                errorMsg = res.getMessage();
                status = "2";
            }
            gmtEnd = new Date();
            Integer elapsedTime = Math.toIntExact((gmtEnd.getTime() - gmtCreate.getTime()));
            wcsInterfaceCallLogService.addWcsInterfaceCallLog(this.getClass().getName() + ".AGVDriveAwaySafely", status, gmtCreate, gmtEnd, errorMsg, elapsedTime);
        }
        //将数据转换，返回
        return retJsonObject(res.getCode(),res.getMessage());
    }

    /**
     *功能描述: agv请求，返回状态
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    private JSONObject retJsonObject(String cood,String msg) {
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("resStat",cood);
        jsonObject.put("resInbound",msg);
        return jsonObject;
    }


}
