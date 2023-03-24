//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.nuohua.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.basicdatamanagement.model.business.IBaseDictItemService;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsOutTask;
import com.penghaisoft.wms.nuohua.service.DifferentBusinessNHFactory;
import com.penghaisoft.wms.nuohua.service.DifferentBusinessNHService;
import com.penghaisoft.wms.nuohua.service.WcsForNHService;
import com.penghaisoft.wms.outboundmanagement.model.business.IWmsOrderOutStereoscopicDeailService;
import com.penghaisoft.wms.outboundmanagement.model.business.IWmsOrderOutStereoscopicService;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopicDeail;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsTaskExecutionLogService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
/**
 *功能描述:  - 出库
 * @author zhangxin
 * @date 2022/7/21
 * @params
 * @return
 */
@RestController
@RequestMapping({"/wmsNHOrderOutStereoscopic"})
public class WmsNHOrderOutStereoscopicController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsNHOrderOutStereoscopicController.class);
    @Autowired
    private IWmsOrderOutStereoscopicService wmsOrderOutStereoscopicService;
    @Autowired
    private IWmsOrderOutStereoscopicDeailService wmsOrderOutStereoscopicDeailService;
    @Autowired
    private DifferentBusinessNHFactory differentBusinessNHFactory;
    @Autowired
    private IWmsTaskExecutionLogService wmsTaskExecutionLogService;
    @Value("${applyfactory}")
    private String applyfactory;

    @Autowired
    private IBaseDictItemService baseDictItemService;
    @Autowired
    private IUserBusiness userBusiness;
    //调WCS接口
    @Autowired
    private WcsForNHService wcsForNHService;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${notice.other-sys-addr.pallet-out}")
    private String noticePalletOutAddr;


    public WmsNHOrderOutStereoscopicController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        wmsOrderOutStereoscopic.setActiveFlag("1");
        wmsOrderOutStereoscopic.setOrderOutId(CommonUtils.getUUID());
        wmsOrderOutStereoscopic.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsOrderOutStereoscopic.setGmtCreate(new Date());
        Resp resp = this.wmsOrderOutStereoscopicService.create(wmsOrderOutStereoscopic);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        wmsOrderOutStereoscopic.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsOrderOutStereoscopic.setGmtModified(new Date());
        Resp resp = this.wmsOrderOutStereoscopicService.delete(wmsOrderOutStereoscopic);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        wmsOrderOutStereoscopic.setActiveFlag("1");
        if (wmsOrderOutStereoscopic.getOrderBy() == null || "".equals(wmsOrderOutStereoscopic.getOrderBy())) {
            wmsOrderOutStereoscopic.setOrderBy("gmt_create desc");
        }

        Pager<WmsOrderOutStereoscopic> pager = this.wmsOrderOutStereoscopicService.findListByCondition(page, rows, wmsOrderOutStereoscopic);
        List<BaseDictItem> baseDictItemList = this.baseDictItemService.getDictTypeByCode("order_out_type");
        if (pager.getRecords() != null && !pager.getRecords().isEmpty() && baseDictItemList != null && !baseDictItemList.isEmpty()) {
            Iterator var6 = pager.getRecords().iterator();

            while(var6.hasNext()) {
                WmsOrderOutStereoscopic tmp = (WmsOrderOutStereoscopic)var6.next();
                Iterator var8 = baseDictItemList.iterator();

                while(var8.hasNext()) {
                    BaseDictItem baseDictItem = (BaseDictItem)var8.next();
                    if (null != tmp.getOrderType() && tmp.getOrderType().equals(baseDictItem.getDicItemCode())) {
                        tmp.setOrderType(baseDictItem.getDicItemName());
                    }
                }
                if (null != tmp.getOrderType() && tmp.getOrderType().equals("26")) {
                    tmp.setOrderType("人工出库");
                }
            }
        }
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, pager);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsOrderOutStereoscopic wmsOrderOutStereoscopic = this.wmsOrderOutStereoscopicService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsOrderOutStereoscopic);
    }

    @PostMapping({"u"})
    public ResponseResult update(@RequestBody WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        Resp resp = this.wmsOrderOutStereoscopicService.createAndEditDeal(wmsOrderOutStereoscopic, this.userBusiness.getCurrentUser().getNickname());
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), resp);
        return result;
    }

    /**
     *功能描述: -启动出库
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"batch/start"})
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
//                                String str="{ \"ret\": true, \"msg\": \"操作成功\"}";
//                                jSONObject = JSONObject.parseObject(str);

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

                        /*HttpHeaders headers = new HttpHeaders();
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
                        }*/

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
    }
    /**
     *功能描述: 合并出库
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"batch/merge"})
    public ResponseResult batchMerge(@RequestBody WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        List<String> outAddressSet = new ArrayList();
        List<WmsOrderOutStereoscopic> list = this.wmsOrderOutStereoscopicService.queryByAny(wmsOrderOutStereoscopic);
        ResponseResult result;
        if (list != null && !list.isEmpty()) {
            Iterator var5 = list.iterator();

            while(var5.hasNext()) {
                WmsOrderOutStereoscopic tmp = (WmsOrderOutStereoscopic)var5.next();
                if (tmp.getOrderStatus() != null && !"1".equals(tmp.getOrderStatus())) {
                    result = new ResponseResult(RESULT.FAILED.code, "出库单包含状态非创建的记录！", (Object)null);
                    return result;
                }

                if (tmp.getOutAddress() == null || "".equals(tmp.getOutAddress())) {
                    result = new ResponseResult(RESULT.FAILED.code, "出库单中出库口数据异常！", (Object)null);
                    return result;
                }

                if (!outAddressSet.contains(tmp.getOutAddress())) {
                    outAddressSet.add(tmp.getOutAddress());
                }
            }
        }

        if (outAddressSet.size() > 1) {
            result = new ResponseResult(RESULT.FAILED.code, "出库单中包含不同的出库口记录，不能合并！", (Object)null);
            return result;
        } else {
            Resp resp = this.wmsOrderOutStereoscopicService.batchMerge(wmsOrderOutStereoscopic, (String)outAddressSet.get(0), this.userBusiness.getCurrentUser().getNickname());
            result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
            return result;
        }
    }

    /**
     *功能描述:  - 出库任务停止
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"disableEdgeBox"})
    public ResponseResult disableEdgeBox(@RequestBody WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        if(wmsOrderOutStereoscopic!=null &&wmsOrderOutStereoscopic.getOrderNo()!=null) {
            //根据订单查看任务，是否已经过账，已经过账，不允许停用。
            WmsTaskExecutionLog taskExecutionLog =new WmsTaskExecutionLog();
            taskExecutionLog.setOrderNo(wmsOrderOutStereoscopic.getOrderNo());
            boolean flag =false;
            List<WmsTaskExecutionLog> taskExecutionLogList = wmsTaskExecutionLogService.queryByAny(taskExecutionLog);
            for (WmsTaskExecutionLog wmsTaskExecutionLog:taskExecutionLogList){
                //创建状态，但已过账 0 已过账  1未过账
                if("1".equals(wmsTaskExecutionLog.getTaskStatus()) && "0".equals(wmsTaskExecutionLog.getUserDefined3())){
                    flag = true;
                    break;
                }
            }
            if(flag == true){
                return new ResponseResult("1", "出库任务创建状态,但已过账，不允许停止！请先将任务暂缓过账！", null);
            }

            WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail = new WmsOrderOutStereoscopicDeail();
            wmsOrderOutStereoscopicDeail.setOrderNo(wmsOrderOutStereoscopic.getOrderNo());
            //根据出库单，查询出库明细
            log.info("根据出库单，查询出库明细==========开始");
            List<WmsOrderOutStereoscopicDeail>  WmsOrderOutStereoscopicDeailList=this.wmsOrderOutStereoscopicDeailService.queryByAny(wmsOrderOutStereoscopicDeail);
            log.info("根据出库单，查询出库明细==========结束");
            if(WmsOrderOutStereoscopicDeailList!=null &&WmsOrderOutStereoscopicDeailList.size()>0) {
               wmsOrderOutStereoscopic.setWmsOrderOutStereoscopicDeailList(WmsOrderOutStereoscopicDeailList);
               //停止
                log.info("根据出库单，出库明细，停止==========开始");
               Resp resp = this.wmsOrderOutStereoscopicService.createAndEditDeal(wmsOrderOutStereoscopic, this.userBusiness.getCurrentUser().getNickname());
                log.info("根据出库单，出库明细，停止==========结束");
               ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), resp);
               return result;
           }else{
               ResponseResult result = new ResponseResult("1", "出库任务停止 - 立库出库单明细无数据！！", null);
               return result;
           }
        }else{
            ResponseResult result = new ResponseResult("1", "出库任务停止 - 请求数据异常！", null);
            return result;
        }
    }

    /**
     *功能描述: 任务明细 -生成查看
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @PostMapping({"taskStartList"})
    public ResponseResult taskStartList(@RequestBody WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        User currentUser = this.userBusiness.getCurrentUser();
        String loginName = currentUser.getNickname();
        String loginNameWithAccount = currentUser.getNickname() + "(" + currentUser.getAccount() + ")";
        //接受前台传来的 单据编号 列表
        List<String> orderNoList = wmsOrderOutStereoscopic.getOrderNoList();
        if(orderNoList!=null &&orderNoList.size()>0){
            for(int i=0; i<orderNoList.size();i++){
                WmsTaskExecutionLog wmsTaskExecutionLog =new WmsTaskExecutionLog();
                wmsTaskExecutionLog.setOrderNo(orderNoList.get(i));
                wmsTaskExecutionLog.setActiveFlag("1");
                wmsTaskExecutionLog.setTaskType(wmsOrderOutStereoscopic.getOrderType());
                //根据单据，查看任务是否已经生成
                List<WmsTaskExecutionLog> logList = wmsTaskExecutionLogService.queryByAny(wmsTaskExecutionLog);
                //存在任务
                if(logList!=null &&logList.size()>0){
                    continue;
                }
                //不存在任务，创建任务
                else{
                    //只检查数量，不检查单据状态
                    Resp resp = this.wmsOrderOutStereoscopicService.checkAmount(wmsOrderOutStereoscopic);
                    if (RESULT.FAILED.code.equals(resp.getCode())) {
                        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
                        return result;
                    } else {
                        DifferentBusinessNHService differentBusinessNHService = this.differentBusinessNHFactory.getDifferentBusinessNHService(Constant.RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
                        LinkedList wmsOutTaskList =new LinkedList();
                        String targetAddress;

                        targetAddress = "";
                        WmsOrderOutStereoscopicDeail searchDetailOb = new WmsOrderOutStereoscopicDeail();
                        searchDetailOb.setOrderNo(orderNoList.get(i));
                        searchDetailOb.setActiveFlag("1");
                        //标识成品/原材料
                        searchDetailOb.setUserDefined3(wmsOrderOutStereoscopic.getUserDefined2());
                        //立库出库单表,出库到暂存区
                        List detailList = this.wmsOrderOutStereoscopicDeailService.queryByAny(searchDetailOb);

                        targetAddress = ((WmsOrderOutStereoscopicDeail) detailList.get(0)).getOutAddress();
                        Iterator var16 = detailList.iterator();

                        while (var16.hasNext()) {
                            WmsOrderOutStereoscopicDeail tmp = (WmsOrderOutStereoscopicDeail) var16.next();
                            //出库单，批量启动
                            resp = differentBusinessNHService.queryOutRecommendLocationCode(tmp, targetAddress, loginNameWithAccount);
                            if (resp != null && RESULT.FAILED.code.equals(resp.getCode())) {
                                differentBusinessNHService.revertOut((List) null, wmsOutTaskList, orderNoList.get(i));
                                return new ResponseResult(RESULT.FAILED.code, resp.getMsg(), (Object) null);
                            }

                            WmsLocationStereoscopic returnData = (WmsLocationStereoscopic) resp.getData();
                            wmsOutTaskList.addAll(returnData.getWmsOutTaskList());

                            if (returnData.getWmsOutTaskList() != null && !returnData.getWmsOutTaskList().isEmpty()) {
                                WmsOrderOutStereoscopicDeail updateOb = new WmsOrderOutStereoscopicDeail();
                                updateOb.setOrderOutDetailId(tmp.getOrderOutDetailId());
                                updateOb.setActiveFlag("1");
                                updateOb.setPlanPalletAmount(returnData.getWmsOutTaskList().size());
                                updateOb.setUserDefined3(wmsOrderOutStereoscopic.getUserDefined2());
                                //立库出库单明细表
                                this.wmsOrderOutStereoscopicDeailService.update(updateOb);
                            }
                        }
                        if (wmsOutTaskList != null && !wmsOutTaskList.isEmpty()) {
                            //立库出库单，改为2：启动。托盘表lock_by为订单号，执行任务表，新增数据。
                            resp  = this.wmsOrderOutStereoscopicService.startOrderNo((List) null, wmsOutTaskList, orderNoList.get(i), loginName);
                            ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
                            return result;
                        }

                    }
                }
            }
            return new ResponseResult(RESULT.SUCCESS.code, "任务明细已创建！", null);
        }else{
            return new ResponseResult(RESULT.FAILED.code, "立库出库单据为空！！", null);
        }
    }
}
