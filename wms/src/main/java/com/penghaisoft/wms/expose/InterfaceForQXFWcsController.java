//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.expose;

import com.alibaba.fastjson.JSON;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.constant.Constant.RecommendLocationEnum;
import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.business.IDifferentBusinessService;
import com.penghaisoft.wms.basicmanagement.model.business.impl.DifferentBusinessFactory;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsOutTask;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsMoveStereoscopicService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOrderCrossProductDeailService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsPalletService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsSendLedDataService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsTaskExecutionLogService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/expose"})
public class InterfaceForQXFWcsController {
    private static final Logger log = LoggerFactory.getLogger(InterfaceForQXFWcsController.class);
    @Autowired
    private IWmsTaskExecutionLogService wmsTaskExecutionLogService;
    @Autowired
    private IWmsPalletService wmsPalletService;
    @Autowired
    private DifferentBusinessFactory differentBusinessFactory;
    @Autowired
    private IWmsOrderCrossProductDeailService wmsOrderCrossProductDeailService;
    @Autowired
    private IWmsMoveStereoscopicService wmsMoveStereoscopicService;
    @Autowired
    private IWmsCommonService wmsCommonService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IWmsSendLedDataService sendLedDataService;
    @Value("${applyfactory}")
    private String applyfactory;

    public InterfaceForQXFWcsController() {
    }

    @PostMapping({"/goodsbind"})
    public ResponseResult goodsBind(@RequestBody WmsPallet wmsPallet) {
        new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, (Object)null);
        Resp result = this.wmsPalletService.palletizerBind(wmsPallet);
        String code = result.getCode();
        ResponseResult responseResult;
        if (code.equals(RESULT.SUCCESS.code)) {
            Map<String, String> map = new HashMap();
            String taskId = result.getMsg();
            map.put("taskId", taskId);
            responseResult = new ResponseResult(code, (String)null, map);
        } else {
            responseResult = new ResponseResult(code, result.getMsg(), (Object)null);
        }

        log.info("绑定托盘传入信息" + JSON.toJSONString(wmsPallet));
        return responseResult;
    }

    @PostMapping({"/pallet/out/ready"})
    public ResponseResult taskToComplete(@RequestBody WcsTransOb wcsTransOb) {
        Resp resp = this.wmsTaskExecutionLogService.taskToComplete(wcsTransOb.getTaskId(), wcsTransOb.getPalletCode());
        ResponseResult responseResult;
        if (RESULT.SUCCESS.code.equals(resp.getCode())) {
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, (Object)null);
            return responseResult;
        } else {
            responseResult = new ResponseResult(RESULT.FAILED.code, resp.getMsg(), (Object)null);
            return responseResult;
        }
    }

    @PostMapping({"/show/led"})
    public ResponseResult showLed(@RequestBody WcsTransOb wcsTransOb) {
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, (Object)null);
        return responseResult;
    }

    @PostMapping({"productin/location/automatic"})
    public ResponseResult queryLocationCode(@RequestBody WcsTransOb wcsTransOb) {
        long taskId = 0L;
        taskId = wcsTransOb.getTaskId();
        ResponseResult responseResult;
        if (wcsTransOb.getTaskId() == 0L) {
            responseResult = new ResponseResult(RESULT.FAILED.code, "TaskId为空！", (Object)null);
            return responseResult;
        } else if (wcsTransOb.getTaskType() != null && !"".equals(wcsTransOb.getTaskType())) {
            if (wcsTransOb.getPalletCode() != null && !"".equals(wcsTransOb.getPalletCode())) {
                if (wcsTransOb.getInAddress() != null && !"".equals(wcsTransOb.getInAddress())) {
                    Date now = new Date();
                    String isCross = "0";
                    String locationCode = "";
                    Integer amount = 0;
                    WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
                    wmsTaskExecutionLog.setAreaCode(wcsTransOb.getAreaCode());
                    wmsTaskExecutionLog.setTaskType(wcsTransOb.getTaskType());
                    wmsTaskExecutionLog.setPalletCode(wcsTransOb.getPalletCode());
                    wmsTaskExecutionLog.setInAddress(wcsTransOb.getInAddress());
                    wmsTaskExecutionLog.setCreateBy("wcs");
                    wmsTaskExecutionLog.setGmtCreate(now);
                    wmsTaskExecutionLog.setActiveFlag("1");
                    WmsPallet wmsPalletOb = new WmsPallet();
                    wmsPalletOb.setPalletCode(wcsTransOb.getPalletCode());
                    wmsPalletOb.setActiveFlag("1");
                    List<WmsPallet> wmsPalletList = this.wmsPalletService.queryByAny(wmsPalletOb);
                    if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                        WmsPallet wmsPallet = (WmsPallet)wmsPalletList.get(0);
                        if (wmsPallet.getLockBy() != null && !"".equals(wmsPallet.getLockBy())) {
                            if (Long.valueOf(wmsPallet.getLockBy()) == taskId) {
                                Map<String, Object> rtnMap = new HashMap();
                                rtnMap.put("taskId", wmsPallet.getLockBy());
                                rtnMap.put("isCross", "0");
                                rtnMap.put("locationCode", wmsPallet.getLocationCode());
                                responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, rtnMap);
                                return responseResult;
                            } else {
                                responseResult = new ResponseResult(RESULT.FAILED.code, "该托盘码被锁定！", (Object)null);
                                return responseResult;
                            }
                        } else {
                            wmsTaskExecutionLog.setGoodsCode(wmsPallet.getGoodsCode());
                            wmsTaskExecutionLog.setBatchNo(wmsPallet.getBatchNo());
                            wmsTaskExecutionLog.setUserDefined1(Integer.toString(wmsPallet.getAmount()));
                            amount = wmsPallet.getAmount();
                            if ("0".equals(isCross)) {
                                long t1 = System.currentTimeMillis();
                                IDifferentBusinessService differentBusinessService = this.differentBusinessFactory.getIDifferentBusinessService(RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
                                Resp resp = differentBusinessService.queryRecommendLocationCode(wmsTaskExecutionLog.getGoodsCode(), wmsTaskExecutionLog.getBatchNo(), wcsTransOb.getAreaCode());
                                long t2 = System.currentTimeMillis();
                                log.info("##################推荐库位算法整体耗时：" + (t2 - t1) + "ms！#########################");

                                try {
                                    if (resp.getCode() != null && RESULT.FAILED.code.equals(resp.getCode())) {
                                        responseResult = new ResponseResult(RESULT.FAILED.code, resp.getMsg(), (Object)null);
                                        return responseResult;
                                    }

                                    locationCode = resp.getData().toString();
                                    log.info("##################推荐库位是：" + locationCode + "#########################");
                                    wmsTaskExecutionLog.setTaskStatus("1");
                                    wmsTaskExecutionLog.setTaskId(taskId);
                                    wmsTaskExecutionLog.setLocationCode(locationCode);
                                    this.wmsTaskExecutionLogService.inStereoscopicTaskCreate(wmsTaskExecutionLog);
                                } catch (Exception var20) {
                                    log.info("获取推荐库位成功，插入入库任务、更新托盘状态失败");
                                    differentBusinessService.revertLocationStatus0(locationCode);
                                    responseResult = new ResponseResult(RESULT.FAILED.code, "获取推荐库位失败", (Object)null);
                                    return responseResult;
                                }

                                long t3 = System.currentTimeMillis();
                                log.info("##################获取推荐库位成功，插入入库任务、更新托盘状态失耗时：" + (t3 - t2) + "ms！#########################");
                            }

                            Map<String, Object> rtnMap = new HashMap();
                            rtnMap.put("taskId", taskId);
                            rtnMap.put("locationCode", locationCode);
                            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, rtnMap);
                            return responseResult;
                        }
                    } else {
                        responseResult = new ResponseResult(RESULT.FAILED.code, RESULT.PALLET_CODE_ERROR.message, (Object)null);
                        return responseResult;
                    }
                } else {
                    responseResult = new ResponseResult(RESULT.FAILED.code, "入库口地址为空！", (Object)null);
                    return responseResult;
                }
            } else {
                responseResult = new ResponseResult(RESULT.FAILED.code, "托盘码为空！", (Object)null);
                return responseResult;
            }
        } else {
            responseResult = new ResponseResult(RESULT.FAILED.code, "任务类型为空！", (Object)null);
            return responseResult;
        }
    }

    @PostMapping({"virtualpalletin/location/automatic"})
    public ResponseResult queryLocationCodeVP(@RequestBody WcsTransOb wcsTransOb) {
        long taskId = 0L;
        ResponseResult responseResult;
        if (wcsTransOb.getPalletCode() != null && !"".equals(wcsTransOb.getPalletCode())) {
            if (wcsTransOb.getAmount() != null && !"".equals(wcsTransOb.getAmount())) {
                if (wcsTransOb.getInAddress() != null && !"".equals(wcsTransOb.getInAddress())) {
                    Date now = new Date();
                    String isCross = "0";
                    String locationCode = "";
                    String palletAmount = "";
                    WmsPallet wmsPalletOb = new WmsPallet();
                    wmsPalletOb.setPalletCode(wcsTransOb.getPalletCode());
                    wmsPalletOb.setActiveFlag("1");
                    List<WmsPallet> wmsPalletList = this.wmsPalletService.queryByAny(wmsPalletOb);
                    if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                        WmsPallet wmsPallet = (WmsPallet)wmsPalletList.get(0);
                        if (wmsPallet.getLockBy() != null && !"".equals(wmsPallet.getLockBy())) {
                            Map<String, Object> rtnMap = new HashMap();
                            rtnMap.put("taskId", wmsPallet.getLockBy());
                            rtnMap.put("isCross", "0");
                            rtnMap.put("locationCode", wmsPallet.getLocationCode());
                            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, rtnMap);
                            return responseResult;
                        } else if (wmsPallet.getLocationCode() != null && !"".equals(wmsPallet.getLocationCode())) {
                            responseResult = new ResponseResult(RESULT.FAILED.code, "该虚拟托盘(" + wcsTransOb.getPalletCode() + ")已经入库！", (Object)null);
                            return responseResult;
                        } else {
                            responseResult = new ResponseResult(RESULT.FAILED.code, "该虚拟托盘(" + wcsTransOb.getPalletCode() + ")已经存在，但有问题，需要排查！", (Object)null);
                            return responseResult;
                        }
                    } else {
                        if ("0".equals(isCross)) {
                            long t1 = System.currentTimeMillis();
                            IDifferentBusinessService differentBusinessService = this.differentBusinessFactory.getIDifferentBusinessService(RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
                            Resp resp = differentBusinessService.queryRecommendLocationCode("pallet", (String)null, wcsTransOb.getAreaCode());
                            long t2 = System.currentTimeMillis();
                            log.info("##################推荐库位算法整体耗时：" + (t2 - t1) + "ms！#########################");

                            try {
                                if (resp.getCode() != null && RESULT.FAILED.code.equals(resp.getCode())) {
                                    responseResult = new ResponseResult(RESULT.FAILED.code, resp.getMsg(), (Object)null);
                                    return responseResult;
                                }

                                locationCode = resp.getData().toString();
                                WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
                                wmsTaskExecutionLog.setAreaCode(wcsTransOb.getAreaCode());
                                wmsTaskExecutionLog.setTaskType(Long.toString(TaskType.VIRTUAL_PALLET_IN.getTaskType()));
                                wmsTaskExecutionLog.setPalletCode(wcsTransOb.getPalletCode());
                                wmsTaskExecutionLog.setInAddress(wcsTransOb.getInAddress());
                                wmsTaskExecutionLog.setCreateBy("wcs");
                                wmsTaskExecutionLog.setGmtCreate(now);
                                wmsTaskExecutionLog.setActiveFlag("1");
                                wmsTaskExecutionLog.setGoodsCode("pallet");
                                wmsTaskExecutionLog.setAmount(wcsTransOb.getAmount());
                                wmsTaskExecutionLog.setTaskStatus("1");
                                taskId = this.wmsCommonService.getTaskIds(TaskType.VIRTUAL_PALLET_IN, 1)[0];
                                wmsTaskExecutionLog.setTaskId(taskId);
                                wmsTaskExecutionLog.setLocationCode(locationCode);
                                wmsTaskExecutionLog.setUserDefined1(Integer.toString(wcsTransOb.getAmount()));
                                this.wmsTaskExecutionLogService.inStereoscopicTaskCreateVP(wmsTaskExecutionLog);
                            } catch (Exception var19) {
                                log.info("获取推荐库位成功，插入入库任务、更新托盘状态失败");
                                differentBusinessService.revertLocationStatus0(locationCode);
                                responseResult = new ResponseResult(RESULT.FAILED.code, "获取推荐库位失败", (Object)null);
                                return responseResult;
                            }

                            long t3 = System.currentTimeMillis();
                            log.info("##################获取推荐库位成功，插入入库任务、更新托盘状态失耗时：" + (t3 - t2) + "ms！#########################");
                        }

                        Map<String, Object> rtnMap = new HashMap();
                        rtnMap.put("taskId", taskId);
                        rtnMap.put("locationCode", locationCode);
                        responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, rtnMap);
                        return responseResult;
                    }
                } else {
                    responseResult = new ResponseResult(RESULT.FAILED.code, "入库口地址为空！", (Object)null);
                    return responseResult;
                }
            } else {
                responseResult = new ResponseResult(RESULT.FAILED.code, "数量为空！", (Object)null);
                return responseResult;
            }
        } else {
            responseResult = new ResponseResult(RESULT.FAILED.code, "托盘码为空！", (Object)null);
            return responseResult;
        }
    }

    @PostMapping({"sorting/location/automatic"})
    public ResponseResult querySortingLocationCode(@RequestBody WcsTransOb wcsTransOb) {
        long taskId = 0L;
        taskId = wcsTransOb.getTaskId();
        ResponseResult responseResult;
        if (wcsTransOb.getTaskId() == 0L) {
            responseResult = new ResponseResult(RESULT.FAILED.code, "TaskId为空！", (Object)null);
            return responseResult;
        } else if (wcsTransOb.getTaskType() != null && !"".equals(wcsTransOb.getTaskType())) {
            if (wcsTransOb.getPalletCode() != null && !"".equals(wcsTransOb.getPalletCode())) {
                if (wcsTransOb.getInAddress() != null && !"".equals(wcsTransOb.getInAddress())) {
                    Date now = new Date();
                    String isCross = "0";
                    String locationCode = "";
                    Integer amount = 0;
                    WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
                    wmsTaskExecutionLog.setAreaCode(wcsTransOb.getAreaCode());
                    wmsTaskExecutionLog.setTaskType(wcsTransOb.getTaskType());
                    wmsTaskExecutionLog.setPalletCode(wcsTransOb.getPalletCode());
                    wmsTaskExecutionLog.setInAddress(wcsTransOb.getInAddress());
                    wmsTaskExecutionLog.setCreateBy("wcs");
                    wmsTaskExecutionLog.setGmtCreate(now);
                    wmsTaskExecutionLog.setActiveFlag("1");
                    WmsPallet wmsPalletOb = new WmsPallet();
                    wmsPalletOb.setPalletCode(wcsTransOb.getPalletCode());
                    wmsPalletOb.setActiveFlag("1");
                    List<WmsPallet> wmsPalletList = this.wmsPalletService.queryByAny(wmsPalletOb);
                    if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                        WmsPallet wmsPallet = (WmsPallet)wmsPalletList.get(0);
                        if (wmsPallet.getLockBy() != null && !"".equals(wmsPallet.getLockBy())) {
                            if (Long.valueOf(wmsPallet.getLockBy()) == taskId) {
                                Map<String, Object> rtnMap = new HashMap();
                                rtnMap.put("taskId", wmsPallet.getLockBy());
                                rtnMap.put("isCross", "0");
                                rtnMap.put("locationCode", wmsPallet.getLocationCode());
                                responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, rtnMap);
                                return responseResult;
                            } else {
                                responseResult = new ResponseResult(RESULT.FAILED.code, "该托盘码被锁定！", (Object)null);
                                return responseResult;
                            }
                        } else {
                            wmsTaskExecutionLog.setGoodsCode(wmsPallet.getGoodsCode());
                            wmsTaskExecutionLog.setBatchNo(wmsPallet.getBatchNo());
                            amount = wmsPallet.getAmount();
                            if ("0".equals(isCross)) {
                                long t1 = System.currentTimeMillis();
                                IDifferentBusinessService differentBusinessService = this.differentBusinessFactory.getIDifferentBusinessService(RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
                                Resp resp = differentBusinessService.querySortingRecommendLocationCode(wmsTaskExecutionLog.getGoodsCode(), wmsTaskExecutionLog.getBatchNo(), wcsTransOb.getAreaCode());
                                long t2 = System.currentTimeMillis();
                                log.info("##################推荐库位算法整体耗时：" + (t2 - t1) + "ms！#########################");

                                try {
                                    if (resp.getCode() != null && RESULT.FAILED.code.equals(resp.getCode())) {
                                        responseResult = new ResponseResult(RESULT.FAILED.code, resp.getMsg(), (Object)null);
                                        return responseResult;
                                    }

                                    locationCode = resp.getData().toString();
                                    wmsTaskExecutionLog.setTaskStatus("1");
                                    wmsTaskExecutionLog.setTaskId(taskId);
                                    wmsTaskExecutionLog.setLocationCode(locationCode);
                                    wmsTaskExecutionLog.setUserDefined1(Integer.toString(amount));
                                    this.wmsTaskExecutionLogService.inStereoscopicTaskCreate(wmsTaskExecutionLog);
                                } catch (Exception var20) {
                                    log.info("获取推荐库位成功，插入入库任务、更新托盘状态失败");
                                    differentBusinessService.revertLocationStatus0(locationCode);
                                    responseResult = new ResponseResult(RESULT.FAILED.code, "获取推荐库位失败", (Object)null);
                                    return responseResult;
                                }

                                long t3 = System.currentTimeMillis();
                                log.info("##################获取推荐库位成功，插入入库任务、更新托盘状态失耗时：" + (t3 - t2) + "ms！#########################");
                            }

                            Map<String, Object> rtnMap = new HashMap();
                            rtnMap.put("taskId", taskId);
                            rtnMap.put("locationCode", locationCode);
                            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, rtnMap);
                            return responseResult;
                        }
                    } else {
                        responseResult = new ResponseResult(RESULT.PALLET_CODE_ERROR.code, RESULT.PALLET_CODE_ERROR.message, (Object)null);
                        return responseResult;
                    }
                } else {
                    responseResult = new ResponseResult(RESULT.FAILED.code, "入库口地址为空！", (Object)null);
                    return responseResult;
                }
            } else {
                responseResult = new ResponseResult(RESULT.FAILED.code, "托盘码为空！", (Object)null);
                return responseResult;
            }
        } else {
            responseResult = new ResponseResult(RESULT.FAILED.code, "任务类型为空！", (Object)null);
            return responseResult;
        }
    }

    @PostMapping({"/report/inwarehouse/task"})
    public ResponseResult reportInStereoscopicTask(@RequestBody WcsTransOb wcsTransOb) {
        Resp checkResp = this.wmsTaskExecutionLogService.checkWcsParamCommon(wcsTransOb);
        if (RESULT.FAILED.code.equals(checkResp.getCode())) {
            ResponseResult responseResult = new ResponseResult(checkResp.getCode(), checkResp.getMsg(), (Object)null);
            return responseResult;
        } else {
            WmsTaskExecutionLog wmsTaskExecutionLog = (WmsTaskExecutionLog)checkResp.getData();
            List<Long> allowType = new ArrayList();
            allowType.add(TaskType.PRODUCT_IN.getTaskType());
            allowType.add(TaskType.SORT_IN.getTaskType());
            allowType.add(TaskType.CHECK_IN.getTaskType());
            allowType.add(TaskType.HAND_IN.getTaskType());
            allowType.add(TaskType.PACKING_IN.getTaskType());
            allowType.add(TaskType.VIRTUAL_PALLET_IN.getTaskType());
            if (!allowType.contains(Long.valueOf(wmsTaskExecutionLog.getTaskType()))) {
                ResponseResult responseResult = new ResponseResult(RESULT.FAILED.code, "接口【WMS接收WCS上报入库任务状态】不支持【" + TaskType.getTaskTypeDesc(Long.valueOf(wmsTaskExecutionLog.getTaskType())) + "】任务！", (Object)null);
                return responseResult;
            } else {
                String key = "wms:interface:pallet:" + wcsTransOb.getPalletCode();
                ResponseResult responseResult;
                if (this.stringRedisTemplate.hasKey(key)) {
                    responseResult = new ResponseResult(RESULT.FAILED.code, "当前托盘码正在处理中！", (Object)null);
                    return responseResult;
                } else {
                    this.stringRedisTemplate.opsForValue().set(key, "1", 1L, TimeUnit.MINUTES);
                    if (wcsTransOb.getTaskStatus() == null || !"3".equals(wcsTransOb.getTaskStatus()) && !"4".equals(wcsTransOb.getTaskStatus())) {
                        this.wmsTaskExecutionLogService.create(wmsTaskExecutionLog);
                    } else {
                        Resp resp = this.wmsTaskExecutionLogService.reportInStereoscopicTask(wmsTaskExecutionLog);
                        if (RESULT.FAILED.code.equals(resp.getCode())) {
                            this.stringRedisTemplate.delete(key);
                            responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
                            return responseResult;
                        }
                    }

                    this.stringRedisTemplate.delete(key);
                    responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, (Object)null);
                    return responseResult;
                }
            }
        }
    }

    @PostMapping({"/report/move/pallet"})
    public ResponseResult reportNormalYkTask(@RequestBody WcsTransOb wcsTransOb) {
        Resp checkResp = this.wmsTaskExecutionLogService.checkWcsParamCommon(wcsTransOb);
        if (RESULT.FAILED.code.equals(checkResp.getCode())) {
            ResponseResult responseResult = new ResponseResult(checkResp.getCode(), checkResp.getMsg(), (Object)null);
            return responseResult;
        } else {
            WmsTaskExecutionLog wmsTaskExecutionLog = (WmsTaskExecutionLog)checkResp.getData();
            List<Long> allowType = new ArrayList();
            allowType.add(TaskType.NORMAL_MOVE.getTaskType());
            if (!allowType.contains(Long.valueOf(wmsTaskExecutionLog.getTaskType()))) {
                ResponseResult responseResult = new ResponseResult(RESULT.FAILED.code, "接口【WMS接收WCS接收普通移库任务状态】不支持【" + TaskType.getTaskTypeDesc(Long.valueOf(wmsTaskExecutionLog.getTaskType())) + "】任务！", (Object)null);
                return responseResult;
            } else {
                String key = "wms:interface:pallet:" + wcsTransOb.getPalletCode();
                if (this.stringRedisTemplate.hasKey(key)) {
                    ResponseResult responseResult = new ResponseResult(RESULT.FAILED.code, "当前托盘码正在处理中！", (Object)null);
                    return responseResult;
                } else {
                    this.stringRedisTemplate.opsForValue().set(key, "1", 1L, TimeUnit.MINUTES);
                    Resp resp = this.wmsMoveStereoscopicService.reportNormalYkTask(wmsTaskExecutionLog);
                    ResponseResult responseResult;
                    if (RESULT.FAILED.code.equals(resp.getCode())) {
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
                        return responseResult;
                    } else {
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, (Object)null);
                        return responseResult;
                    }
                }
            }
        }
    }

    @PostMapping({"/report/outwarehouse/straight"})
    public ResponseResult reportOutStraightTask(@RequestBody WcsTransOb wcsTransOb) {
        Resp checkResp = this.wmsTaskExecutionLogService.checkWcsParamCommon(wcsTransOb);
        if (RESULT.FAILED.code.equals(checkResp.getCode())) {
            ResponseResult responseResult = new ResponseResult(checkResp.getCode(), checkResp.getMsg(), (Object)null);
            return responseResult;
        } else {
            WmsTaskExecutionLog wmsTaskExecutionLog = (WmsTaskExecutionLog)checkResp.getData();
            List<Long> allowType = new ArrayList();
            allowType.add(TaskType.STRAIGHT_OUT.getTaskType());
            allowType.add(TaskType.HAND_OUT.getTaskType());
            if (!allowType.contains(Long.valueOf(wmsTaskExecutionLog.getTaskType()))) {
                ResponseResult responseResult = new ResponseResult(RESULT.FAILED.code, "接口【WMS接收WCS出库任务状态-直发口】不支持【" + TaskType.getTaskTypeDesc(Long.valueOf(wmsTaskExecutionLog.getTaskType())) + "】任务！", (Object)null);
                return responseResult;
            } else {
                String key = "wms:interface:pallet:" + wcsTransOb.getPalletCode();
                if (this.stringRedisTemplate.hasKey(key)) {
                    ResponseResult responseResult = new ResponseResult(RESULT.FAILED.code, "当前托盘码正在处理中！", (Object)null);
                    return responseResult;
                } else {
                    this.stringRedisTemplate.opsForValue().set(key, "1", 1L, TimeUnit.MINUTES);
                    WmsPallet wmsPalletOb = new WmsPallet();
                    wmsPalletOb.setPalletCode(wcsTransOb.getPalletCode());
                    wmsPalletOb.setActiveFlag("1");
                    List<WmsPallet> wmsPalletList = this.wmsPalletService.queryByAny(wmsPalletOb);
                    Integer boxAmount = 0;
                    if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                        boxAmount = ((WmsPallet)wmsPalletList.get(0)).getAmount();
                    }

                    Resp resp = this.wmsTaskExecutionLogService.reportOutStraightTask(wmsTaskExecutionLog);
                    ResponseResult responseResult;
                    if (RESULT.FAILED.code.equals(resp.getCode())) {
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
                        return responseResult;
                    } else {
                        log.info("------------------上报完成时调LED显示开始----------------------");
                        this.sendLedDataService.sendLEDOut(wcsTransOb.getTaskId(), wcsTransOb.getPalletCode(), "8", boxAmount);
                        log.info("------------------上报完成时调LED显示结束----------------------");
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, (Object)null);
                        return responseResult;
                    }
                }
            }
        }
    }

    @PostMapping({"/report/outwarehouse/check"})
    public ResponseResult reportOutCheckTask(@RequestBody WcsTransOb wcsTransOb) {
        Resp checkResp = this.wmsTaskExecutionLogService.checkWcsParamCommon(wcsTransOb);
        if (RESULT.FAILED.code.equals(checkResp.getCode())) {
            ResponseResult responseResult = new ResponseResult(checkResp.getCode(), checkResp.getMsg(), (Object)null);
            return responseResult;
        } else {
            WmsTaskExecutionLog wmsTaskExecutionLog = (WmsTaskExecutionLog)checkResp.getData();
            List<Long> allowType = new ArrayList();
            allowType.add(TaskType.CHECK_OUT.getTaskType());
            if (!allowType.contains(Long.valueOf(wmsTaskExecutionLog.getTaskType()))) {
                ResponseResult responseResult = new ResponseResult(RESULT.FAILED.code, "接口【WMS接收WCS出库任务状态-盘点口】不支持【" + TaskType.getTaskTypeDesc(Long.valueOf(wmsTaskExecutionLog.getTaskType())) + "】任务！", (Object)null);
                return responseResult;
            } else {
                String key = "wms:interface:pallet:" + wcsTransOb.getPalletCode();
                if (this.stringRedisTemplate.hasKey(key)) {
                    ResponseResult responseResult = new ResponseResult(RESULT.FAILED.code, "当前托盘码正在处理中！", (Object)null);
                    return responseResult;
                } else {
                    this.stringRedisTemplate.opsForValue().set(key, "1", 1L, TimeUnit.MINUTES);
                    Resp resp = this.wmsTaskExecutionLogService.reportOutCheckTask(wmsTaskExecutionLog);
                    ResponseResult responseResult;
                    if (RESULT.FAILED.code.equals(resp.getCode())) {
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
                        return responseResult;
                    } else {
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, (Object)null);
                        return responseResult;
                    }
                }
            }
        }
    }

    @PostMapping({"/report/outwarehouse/virtualpallet"})
    public ResponseResult reportVirtualpalletTask(@RequestBody WcsTransOb wcsTransOb) {
        Resp checkResp = this.wmsTaskExecutionLogService.checkWcsParamCommon(wcsTransOb);
        if (RESULT.FAILED.code.equals(checkResp.getCode())) {
            ResponseResult responseResult = new ResponseResult(checkResp.getCode(), checkResp.getMsg(), (Object)null);
            return responseResult;
        } else {
            WmsTaskExecutionLog wmsTaskExecutionLog = (WmsTaskExecutionLog)checkResp.getData();
            List<Long> allowType = new ArrayList();
            allowType.add(TaskType.VIRTUAL_PALLET_OUT.getTaskType());
            if (!allowType.contains(Long.valueOf(wmsTaskExecutionLog.getTaskType()))) {
                ResponseResult responseResult = new ResponseResult(RESULT.FAILED.code, "接口【WMS接收WCS越库出库任务状态】不支持【" + TaskType.getTaskTypeDesc(Long.valueOf(wmsTaskExecutionLog.getTaskType())) + "】任务！", (Object)null);
                return responseResult;
            } else {
                String key = "wms:interface:pallet:" + wcsTransOb.getPalletCode();
                if (this.stringRedisTemplate.hasKey(key)) {
                    ResponseResult responseResult = new ResponseResult(RESULT.FAILED.code, "当前托盘码正在处理中！", (Object)null);
                    return responseResult;
                } else {
                    this.stringRedisTemplate.opsForValue().set(key, "1", 1L, TimeUnit.MINUTES);
                    Resp resp = this.wmsTaskExecutionLogService.reportVirtualpalletTask(wmsTaskExecutionLog);
                    ResponseResult responseResult;
                    if (RESULT.FAILED.code.equals(resp.getCode())) {
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
                        return responseResult;
                    } else {
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, (Object)null);
                        return responseResult;
                    }
                }
            }
        }
    }

    @PostMapping({"/start/outwarehouse/virtualpallet"})
    public ResponseResult callVirtualpalletTask() {
        IDifferentBusinessService differentBusinessService = this.differentBusinessFactory.getIDifferentBusinessService(RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
        List<WmsOutTask> wmsOutTaskList = new LinkedList();
        Integer planAmount = 1;
        Resp resp = differentBusinessService.queryVirtualPalletOut("pallet", (String)null, planAmount, "3001", "wcs");
        if (resp != null && RESULT.FAILED.code.equals(resp.getCode())) {
            return new ResponseResult(RESULT.FAILED.code, "查找虚拟托盘失败：" + resp.getMsg(), (Object)null);
        } else {
            WmsLocationStereoscopic returnData = (WmsLocationStereoscopic)resp.getData();
            wmsOutTaskList.addAll(returnData.getWmsOutTaskList());
            return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsOutTaskList);
        }
    }
}
