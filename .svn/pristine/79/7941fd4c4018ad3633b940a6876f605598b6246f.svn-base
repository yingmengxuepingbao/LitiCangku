package com.penghaisoft.wms.nuohua.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.constant.Constant.RecommendLocationEnum;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsOutTask;
import com.penghaisoft.wms.nuohua.service.DifferentBusinessNHFactory;
import com.penghaisoft.wms.nuohua.service.DifferentBusinessNHService;
import com.penghaisoft.wms.nuohua.service.SLWCSService;
import com.penghaisoft.wms.nuohua.service.WcsForNHService;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopicDeail;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOrderCheckPalletBoxBarcodeService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOrderCheckPalletService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOrderCheckService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheck;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheckPallet;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheckPalletBoxBarcode;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import com.penghaisoft.wms.util.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *功能描述:  盘点
 * @author zhangxin
 * @date 2022/8/9
 * @params
 * @return
 */
@RestController
@RequestMapping({"/wmsHBOrderCheck"})
public class WmsNHOrderCheckController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsNHOrderCheckController.class);
    @Autowired
    private IWmsOrderCheckService wmsOrderCheckService;
    @Autowired
    private IWmsOrderCheckPalletService wmsOrderCheckPalletService;
    @Autowired
    private IWmsOrderCheckPalletBoxBarcodeService wmsOrderCheckPalletBoxBarcodeService;
    @Autowired
    private DifferentBusinessNHFactory differentBusinessNHFactory;
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${notice.other-sys-addr.pallet-out}")
    private String noticePalletOutAddr;
    @Value("${applyfactory}")
    private String applyfactory;
    //调WCS接口
    @Autowired
    private SLWCSService sLWCSService;

    public WmsNHOrderCheckController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsOrderCheck wmsOrderCheck) {
        wmsOrderCheck.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        Resp resp = this.wmsOrderCheckService.create(wmsOrderCheck);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, "创建成功！", resp);
        return responseResult;
    }

    @PostMapping({"start/check"})
    public ResponseResult startCheck(WmsOrderCheck wmsOrderCheck) {
        WmsOrderCheck ob = new WmsOrderCheck();
        ob.setOrderNo(wmsOrderCheck.getOrderNo());
        ob.setActiveFlag("1");
        List<WmsOrderCheck> list = this.wmsOrderCheckService.queryByAny(ob);
        if (list != null && !list.isEmpty()) {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                WmsOrderCheck tmp = (WmsOrderCheck)var4.next();
                if (tmp.getAreaType() != null && !"1".equals(tmp.getAreaType())) {
                    return new ResponseResult(RESULT.FAILED.code, "当前盘点单非立库盘点，不能启动！", (Object)null);
                }

                if (tmp.getOrderStatus() != null && !"1".equals(tmp.getOrderStatus())) {
                    return new ResponseResult(RESULT.FAILED.code, "当前盘点单包含状态非创建的记录，不能启动！", (Object)null);
                }
            }
        }

        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, (Object)null);
    }
    /**
     *功能描述: 盘点出库-启动
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"start"})
    public ResponseResult start(WmsOrderCheck wmsOrderCheck) {
        User currentUser = this.userBusiness.getCurrentUser();
        String loginName = currentUser.getNickname();
        String loginNameWithAccount = currentUser.getNickname() + "(" + currentUser.getAccount() + ")";
        wmsOrderCheck.setCreateBy(loginNameWithAccount);
        DifferentBusinessNHService differentBusinessNHService = this.differentBusinessNHFactory.getDifferentBusinessNHService(RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
        Resp resp = differentBusinessNHService.lockCheckLocationCode(wmsOrderCheck);
        if (resp != null && RESULT.FAILED.code.equals(resp.getCode())) {
            return new ResponseResult(RESULT.FAILED.code, "立库盘点启动失败：" + resp.getMsg(), resp);
        } else {
            List<WmsOutTask> wmsOutTaskList = (List)resp.getData();
            if (wmsOutTaskList != null && !wmsOutTaskList.isEmpty()) {
                try {
                    //出库任务下发，
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("groupId", ConstantUtil.UUID_GroupId());
                    jsonObject.put("msgTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    jsonObject.put("priorityCode","");
                    jsonObject.put("warehouse","L-NH01");
                    List list =new ArrayList();
                    for(int i=0;i<wmsOutTaskList.size();i++ ) {
                        Map map =new HashMap();
                        WmsOutTask wmsOutTask = (WmsOutTask) wmsOutTaskList.get(i);
                        //检查此库位是否可出
                        //根据指定的托盘号，检查
                        WmsLocationStereoscopic wmsLocationStereoscopic = differentBusinessNHService.checkByPalletCode(wmsOutTask.getPalletCode());
                        if(wmsLocationStereoscopic != null ) {
                            map.put("taskId", wmsOutTask.getTaskId());
                            map.put("taskType", 1);
                            map.put("barCode", wmsOutTask.getPalletCode());
                            map.put("startNode", wmsOutTask.getFromLocation());
                            map.put("endNode", Constant.StereoscopicInfo.FLOOR_ONE_OUT_TO_ADDRESS);
                            map.put("order", i);
                        }
                        list.add(map);
                        //如果是 线上盘点，发出库指令一条。
                        if("1".equals(wmsOrderCheck.getUserDefined2())){
                            break;
                        }
                    }
                    jsonObject.put("tasks",list);
                    //盘点：调WCS出库任务
                    log.info("---------------盘点，调用wcs的任务接收接口 : " + jsonObject);
                    JSONObject returnJsonObject = sLWCSService.taskReceive(jsonObject);
                    //模拟返回
//                    String str = "{ \"ret\": true, \"msg\": \"操作成功\"}";
//                    JSONObject returnJsonObject = JSONObject.parseObject(str);
                    log.info("---------------盘点，调用wcs的任务接收接口 -结束: " + returnJsonObject.toString());

                /*try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<List<WmsOutTask>> request = new HttpEntity(wmsOutTaskList, headers);
                    ResponseEntity<JSONObject> wcsResp = this.restTemplate.postForEntity(this.noticePalletOutAddr, request, JSONObject.class, new Object[0]);
                    if (wcsResp.getStatusCodeValue() != 200) {
                        log.error("调wcs接口失败！");
                        differentBusinessHBService.revertOut((List)null, wmsOutTaskList, (String)null);
                        return new ResponseResult(RESULT.FAILED.code, "锁定盘点库位成功，调用WCS出库接口失败！", (Object)null);
                    }

                    JSONObject noticeResult = (JSONObject)wcsResp.getBody();
                    if (!noticeResult.getString("code").equals("1")) {
                        log.error("调wcs接口失败：" + noticeResult.getString("message"));
                        differentBusinessHBService.revertOut((List)null, wmsOutTaskList, (String)null);
                        return new ResponseResult(RESULT.FAILED.code, "锁定盘点库位成功，调用WCS出库接口失败:" + noticeResult.getString("message"), (Object)null);
                    }*/
                    if(returnJsonObject!=null ) {
                        //如果调用成功
                        if(Boolean.valueOf(returnJsonObject.get("ret").toString())) {
                            log.info("盘点任务 -调wcs接口成功！");
                            this.wmsOrderCheckService.updateBusiness(wmsOutTaskList, resp.getMsg(), wmsOrderCheck.getOrderNo(), loginName);
                        }else{
                            differentBusinessNHService.revertOut((List)null, wmsOutTaskList, (String)null);
                            return new ResponseResult(RESULT.FAILED.code, "锁定盘点库位成功，调用WCS出库接口失败！", (Object)null);
                        }
                    }else{
                        differentBusinessNHService.revertOut((List)null, wmsOutTaskList, (String)null);
                        return new ResponseResult(RESULT.FAILED.code, "锁定盘点库位成功，调用WCS出库接口失败！", (Object)null);
                    }
                } catch (Exception var12) {
                    differentBusinessNHService.revertOut((List)null, wmsOutTaskList, (String)null);
                    return new ResponseResult(RESULT.FAILED.code, "锁定盘点库位成功，调用WCS出库接口失败！", (Object)null);
                }

                return new ResponseResult(RESULT.SUCCESS.code, "立库盘点启动成功！", resp);
            } else {
                return new ResponseResult(RESULT.FAILED.code, "立库盘点启动失败： 没有待盘点的托盘需要出库！", resp);
            }
        }
    }

    @PostMapping({"deleteByCheckId"})
    public ResponseResult deleteByCheckId(WmsOrderCheck wmsOrderCheck) {
        Resp resp = this.wmsOrderCheckService.deleteByCheckId(wmsOrderCheck);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, resp);
        return responseResult;
    }

    @PostMapping({"deleteByOrderNo"})
    public ResponseResult deleteByOrderNo(WmsOrderCheck wmsOrderCheck) {
        Resp resp = this.wmsOrderCheckService.deleteByOrderNo(wmsOrderCheck);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, resp);
        return responseResult;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsOrderCheck wmsOrderCheck) {
        wmsOrderCheck.setActiveFlag("1");
        Pager<WmsOrderCheck> result = this.wmsOrderCheckService.findListByCondition(page, rows, wmsOrderCheck);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, result);
        return responseResult;
    }

    @PostMapping({"pallet/list"})
    public ResponseResult palletList(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsOrderCheckPallet wmsOrderCheckPallet) {
        wmsOrderCheckPallet.setActiveFlag("1");
        Pager<WmsOrderCheckPallet> result = this.wmsOrderCheckPalletService.findListByCondition(page, rows, wmsOrderCheckPallet);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, result);
        return responseResult;
    }

    @PostMapping({"pallet/boxBarcode/list"})
    public ResponseResult palletBoxBarcodeList(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsOrderCheckPalletBoxBarcode wmsOrderCheckPalletBoxBarcode) {
        wmsOrderCheckPalletBoxBarcode.setActiveFlag("1");
        Pager<WmsOrderCheckPalletBoxBarcode> result = this.wmsOrderCheckPalletBoxBarcodeService.findListByCondition(page, rows, wmsOrderCheckPalletBoxBarcode);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, result);
        return responseResult;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsOrderCheck wmsOrderCheck = this.wmsOrderCheckService.findById(id);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsOrderCheck);
        return responseResult;
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsOrderCheck wmsOrderCheck) {
        Resp resp = this.wmsOrderCheckService.update(wmsOrderCheck);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, resp);
        return responseResult;
    }

    //=========================现场修改：诺华盘库是出入库流程=======================================
    /**
     *功能描述: -启动出库
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    /*@PostMapping({"batch/start"})
    public ResponseResult batchStart(@RequestBody WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        User currentUser = this.userBusiness.getCurrentUser();
        String loginName = currentUser.getNickname();
        String loginNameWithAccount = currentUser.getNickname() + "(" + currentUser.getAccount() + ")";
        //检查数量
        Resp resp = this.wmsOrderOutStereoscopicService.checkStatusAmount(wmsOrderOutStereoscopic);
        if (RESULT.FAILED.code.equals(resp.getCode())) {
            ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
            return result;
        } else {
            DifferentBusinessNHService differentBusinessNHService = this.differentBusinessNHFactory.getDifferentBusinessNHService(Constant.RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
            //接受前台传来的 单据编号 列表
            List<String> orderNoList = wmsOrderOutStereoscopic.getOrderNoList();
            StringBuffer sb = new StringBuffer("");
            Iterator var10 = orderNoList.iterator();

            while(true) {
                String orderNo;
                LinkedList wmsOutTaskList;
                String targetAddress;
                List detailList;
                do {
                    do {
                        if (!var10.hasNext()) {
                            return new ResponseResult(RESULT.SUCCESS.code, sb.toString(), null);
                        }
                        orderNo = (String)var10.next();
                        wmsOutTaskList = new LinkedList();
                        targetAddress = "";
                        WmsOrderOutStereoscopicDeail searchDetailOb = new WmsOrderOutStereoscopicDeail();
                        searchDetailOb.setOrderNo(orderNo);
                        searchDetailOb.setActiveFlag("1");
                        //立库出库单表,出库到暂存区
                        detailList = this.wmsOrderOutStereoscopicDeailService.queryByAny(searchDetailOb);
                    } while(detailList == null);
                } while(detailList.isEmpty());

                targetAddress = ((WmsOrderOutStereoscopicDeail)detailList.get(0)).getOutAddress();
                Iterator var16 = detailList.iterator();

                while(var16.hasNext()) {
                    WmsOrderOutStereoscopicDeail tmp = (WmsOrderOutStereoscopicDeail)var16.next();
                    //出库单，批量启动
                    resp = differentBusinessNHService.queryOutRecommendLocationCode(tmp, targetAddress, loginNameWithAccount);
                    if (resp != null && RESULT.FAILED.code.equals(resp.getCode())) {
                        differentBusinessNHService.revertOut((List)null, wmsOutTaskList, orderNo);
                        sb.append("出库单(" + orderNo + ")启动失败：" + resp.getMsg() + ";");
                        return new ResponseResult(RESULT.FAILED.code, sb.toString(), (Object)null);
                    }

                    WmsLocationStereoscopic returnData = (WmsLocationStereoscopic)resp.getData();
                    wmsOutTaskList.addAll(returnData.getWmsOutTaskList());
                    if (returnData.getWmsOutTaskList() != null && !returnData.getWmsOutTaskList().isEmpty()) {
                        WmsOrderOutStereoscopicDeail updateOb = new WmsOrderOutStereoscopicDeail();
                        updateOb.setOrderOutDetailId(tmp.getOrderOutDetailId());
                        updateOb.setActiveFlag("1");
                        updateOb.setPlanPalletAmount(returnData.getWmsOutTaskList().size());
                        //立库出库单明细表
                        this.wmsOrderOutStereoscopicDeailService.update(updateOb);
                    }
                }
                if (wmsOutTaskList != null && !wmsOutTaskList.isEmpty()) {
                    //立库出库单，改为2：启动。托盘表lock_by为订单号，执行任务表，新增数据。
                    this.wmsOrderOutStereoscopicService.startOrderNo((List) null, wmsOutTaskList, orderNo, loginName);
                    //调用wcs接口
                    try {
                        JSONObject jSONObject =new JSONObject();
                        for(int i=0;i<wmsOutTaskList.size();i++ ) {
                            WmsOutTask wmsOutTask = (WmsOutTask) wmsOutTaskList.get(i);
                            //检查此库位是否可出
                            //根据指定的托盘号，检查数据
                            WmsLocationStereoscopic wmsLocationStereoscopic = differentBusinessNHService.checkByPalletCode(wmsOutTask.getPalletCode());
                            if(wmsLocationStereoscopic != null ) {
                                //出库任务下发，
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("taskId", wmsOutTask.getTaskId());
                                jsonObject.put("containerId", wmsOutTask.getPalletCode());
                                jsonObject.put("floor", wmsLocationStereoscopic.getFloorNumber());
                                jsonObject.put("taskType", "2");
                                jsonObject.put("priority", "2001");
                                jsonObject.put("locIdFrom", wmsLocationStereoscopic.getLocationCode());
                                jsonObject.put("locIdTo", "90");
                                jsonObject.put("rgvId", "");
                                //TODO 下发出库任务，调wcs接口，接收出库指令
                                jSONObject = wcsForNHService.taskReceive(jsonObject.toString());

                                // 模拟返回
                                *//*String str="{ \"ret\": true, \"msg\": \"操作成功\"}";
                                jSONObject = JSONObject.parseObject(str);*//*

                                if(jSONObject!=null ) {
                                    //如果调用成功
                                    if(Boolean.valueOf(jSONObject.get("ret").toString())) {
                                        log.info("出库任务 - 调wcs接口成功！");
                                        WmsTaskExecutionLog wmsTaskExecutionLog =new WmsTaskExecutionLog();
                                        wmsTaskExecutionLog.setTaskId(wmsOutTask.getTaskId());
                                        wmsTaskExecutionLog.setTaskStatus("2");
                                        // 如果调用成功，根据任务单号，修改任务表状态 2：执行中
                                        wmsTaskExecutionLogService.updateByTaskId(wmsTaskExecutionLog);
                                        log.info("出库单(" + orderNo + ")启动成功;");
                                        sb.append("出库单(" + orderNo + ")启动成功;");

                                    }else{
                                        differentBusinessNHService.revertOut((List)null, wmsOutTaskList, orderNo);
                                        log.info("\"出库单(\" + orderNo + \")启动失败，获取推荐出库库位成功，调用WCS出库接口失败;\"");
                                        sb.append("出库单(" + orderNo + ")启动失败，获取推荐出库库位成功，调用WCS出库接口失败;");
                                        return new ResponseResult(RESULT.FAILED.code, sb.toString(), (Object)null);
                                    }
                                }else{
                                    differentBusinessNHService.revertOut((List)null, wmsOutTaskList, orderNo);
                                    log.info("\"出库单(\" + orderNo + \")启动失败，获取推荐出库库位成功，调用WCS出库接口失败;\"");
                                    sb.append("出库单(" + orderNo + ")启动失败，获取推荐出库库位成功，调用WCS出库接口失败;");
                                    return new ResponseResult(RESULT.FAILED.code, sb.toString(), (Object)null);
                                }
                                //跳出循环
                                break;
                            }
                        }

                        *//*HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        HttpEntity<List<WmsOutTask>> request = new HttpEntity(wmsOutTaskList.got(0), headers);
                        ResponseEntity<JSONObject> wcsResp = this.restTemplate.postForEntity(this.noticePalletOutAddr, request, JSONObject.class, new Object[0]);
                        if (wcsResp.getStatusCodeValue() != 200) {
                            log.error("调wcs接口失败！");
                            differentBusinessHBService.revertOut((List)null, wmsOutTaskList, orderNo);
                            sb.append("出库单号(" + orderNo + ")启动失败，获取推荐库位成功，调用WCS出库接口失败;");
                            return new ResponseResult(RESULT.FAILED.code, sb.toString(), (Object)null);
                        }

                        JSONObject noticeResult = (JSONObject)wcsResp.getBody();
                        if (!noticeResult.getString("code").equals("1")) {
                            log.error("调wcs接口失败：" + noticeResult.getString("message"));
                            differentBusinessHBService.revertOut((List)null, wmsOutTaskList, orderNo);
                            sb.append("出库单号(" + orderNo + ")启动失败，获取推荐库位成功，调用WCS出库接口失败;");
                            return new ResponseResult(RESULT.FAILED.code, sb.toString(), (Object)null);
                        }*//*

                    } catch (Exception var20) {
                        var20.printStackTrace();
                        differentBusinessNHService.revertOut((List)null, wmsOutTaskList, orderNo);
                        log.info("出库单(" + orderNo + ")启动失败，获取推荐出库库位成功，调用WCS出库接口失败;");
                        sb.append("出库单(" + orderNo + ")启动失败，获取推荐出库库位成功，调用WCS出库接口失败;");
                        return new ResponseResult(RESULT.FAILED.code, sb.toString(), (Object)null);
                    }
                    return new ResponseResult(RESULT.SUCCESS.code, "立库出库启动成功！", resp);
                } else {
                    return new ResponseResult(RESULT.FAILED.code, "立库出库启动失败： 没有待出库的托盘需要出库！", resp);
                }
            }
        }
    }*/
}
