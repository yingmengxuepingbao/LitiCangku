package com.penghaisoft.wms.nuohua.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsHBLocationStereoscopicMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsLocationStereoscopicMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsWarehouseAreaMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsWarehouseArea;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import com.penghaisoft.wms.expose.WcsTransOb;
import com.penghaisoft.wms.nuohua.service.*;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsMoveStereoscopicService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsPalletService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsSendLedDataService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsTaskExecutionLogService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsTaskExecutionLogMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsMoveStereoscopic;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import com.penghaisoft.wms.util.ConstantUtil;
import com.penghaisoft.wms.util.LedUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author zhangxin
 * @Date 2022-07-14
 **/
@Slf4j
@Service("interfaceForHBService")
public class InterfaceForNHServiceImpl implements InterfaceForNHService {

    @Resource
    private WmsPalletMapper wmsPalletMapper;
    @Resource
    private WmsTaskExecutionLogMapper wmsTaskExecutionLogMapper;
    @Resource
    private WmsLocationStereoscopicMapper wmsLocationStereoscopicMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IWmsMoveStereoscopicService wmsMoveStereoscopicService;
    @Autowired
    private IWmsPalletService wmsPalletService;
    @Autowired
    private IWmsTaskExecutionLogService wmsTaskExecutionLogService;
    @Autowired
    private IWmsSendLedDataService sendLedDataService;
    @Autowired
    private DifferentBusinessNHFactory differentBusinessNHFactory;
    @Autowired
    private SLWCSService sLWCSService;
    @Autowired
    private WmsNHAgvService wmsNHAgvService;
    @Autowired
    private IWmsCommonService wmsCommonService;
    @Resource
    private WmsWarehouseAreaMapper warehouseAreaMapper;
    @Resource
    private WmsHBLocationStereoscopicMapper wmsHBLocationStereoscopicMapper;
    @Value("${applyfactory}")
    private String applyfactory;
    @Value("${LED.LED-IP}")
    private String ledIP;

    private static int count = 0;

    /**
     *功能描述: 入库推荐-生成推荐库位（一个）
     * @params WcsTransOb
     * 【 taskId：任务号 】
     * 【 taskType：任务类型 】
     * 【 palletCode：托盘码 】
     * 【 inAddress：入库口地址 】
     * 【 materialCode	物料编码 】
     * 【 bacth	批次号 】
     * @return ResponseResult
     */
    @Override
    public ResponseResult queryLocationCode_HB(WcsTransOb wcsTransOb){
        long taskId = 0L;
        taskId = wcsTransOb.getTaskId();
        ResponseResult responseResult;
        //任务号
        if (wcsTransOb.getTaskId() == 0L) {
            responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "任务号:TaskId为空！", wcsTransOb);
            return responseResult;
        } //任务类型
        else if (wcsTransOb.getTaskType() != null && !"".equals(wcsTransOb.getTaskType())) {
            //托盘码
            if (wcsTransOb.getPalletCode() != null && !"".equals(wcsTransOb.getPalletCode())) {
                //入库口地址
                if (wcsTransOb.getInAddress() != null && !"".equals(wcsTransOb.getInAddress())) {
                    Date now = new Date();
                    String isCross = "0";
                    String locationCode = "";
                    //托盘信息-实体类
                    WmsPallet wmsPalletOb = new WmsPallet();
                    //托盘编码
                    wmsPalletOb.setPalletCode(wcsTransOb.getPalletCode());
                    //激活标记 1是 0否
                    wmsPalletOb.setActiveFlag("1");
                    //根据托盘编码、激活标记 1是 0否 查询数据。
                    List<WmsPallet> wmsPalletList = wmsPalletMapper.queryByAny(wmsPalletOb);
                    //判断激活的托盘码是否存在。
                    if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                        WmsPallet wmsPallet = (WmsPallet)wmsPalletList.get(0);
                        //TODO 判断托盘是否绑定，未绑定，绑定数据
                        if((wmsPallet.getGoodsCode()==null || wmsPallet.getGoodsCode().isEmpty()) ){
                            wmsPallet.setGoodsCode(wcsTransOb.getMaterialCode());
                            wmsPallet.setBatchNo(wcsTransOb.getBatch());
                            wmsPallet.setAmount(Integer.parseInt(wcsTransOb.getWeight()));
                            //更新托盘绑定商品数据
                            wmsPalletMapper.updateBySelect(wmsPallet);
                        }else{
                            if(!wmsPallet.getGoodsCode().equals(wcsTransOb.getMaterialCode())){
                                responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "该托盘码绑定的商品不一致！", (Object)null);
                                return responseResult;
                            }
                            if(!wmsPallet.getBatchNo().equals(wcsTransOb.getBatch())){
                                responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "该托盘码绑定商品的批次号不一致！", (Object)null);
                                return responseResult;
                            }
                            if(wmsPallet.getAmount()!=Integer.parseInt(wcsTransOb.getWeight())){
                                responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "该托盘码绑定商品的数量不一致！", (Object)null);
                                return responseResult;
                            }
                        }
                        //任务是否被锁定
                        if (wmsPallet.getLockBy() != null && !"".equals(wmsPallet.getLockBy())) {
                            //任务锁定，是否是当前任务。是当前任务，则已经存在库位信息，直接返回。
                            if (Long.valueOf(wmsPallet.getLockBy()) == taskId) {
                                Map<String, Object> rtnMap = new HashMap();
                                rtnMap.put("taskId", wmsPallet.getLockBy());
                                rtnMap.put("isCross", "0");
                                rtnMap.put("locationCode", wmsPallet.getLocationCode());
                                responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, rtnMap);
                                return responseResult;
                            } else {
                                responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "该托盘码被锁定！", (Object)null);
                                return responseResult;
                            }
                        } else {//任务未被锁定
                            //执行日志-实体类
                            WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
                            wmsTaskExecutionLog.setAreaCode(wcsTransOb.getAreaCode());
                            wmsTaskExecutionLog.setTaskType(wcsTransOb.getTaskType());
                            wmsTaskExecutionLog.setPalletCode(wcsTransOb.getPalletCode());
                            wmsTaskExecutionLog.setInAddress(wcsTransOb.getInAddress());
                            wmsTaskExecutionLog.setCreateBy("wcs");
                            wmsTaskExecutionLog.setGmtCreate(now);
                            wmsTaskExecutionLog.setActiveFlag("1");
                            wmsTaskExecutionLog.setGoodsCode(wmsPallet.getGoodsCode());
                            wmsTaskExecutionLog.setBatchNo(wmsPallet.getBatchNo());
                            wmsTaskExecutionLog.setUserDefined1(wmsPallet.getAmount().toString());
                            wmsTaskExecutionLog.setTaskId(taskId);
                            //是否审核 1未审核，0审核
                            wmsTaskExecutionLog.setUserDefined3("1");
                            if ("0".equals(isCross)) {
                                long t1 = System.currentTimeMillis();
                                log.info("##################推荐库位算法#############################");
                                DifferentBusinessNHService differentBusinessNHService = this.differentBusinessNHFactory.getDifferentBusinessNHService(Constant.RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
                                //查询推荐货位
                                // 参数：商品编码、批次号、所属库区
                                Resp resp = differentBusinessNHService.queryRecommendLocationCodeHB(wmsTaskExecutionLog, wcsTransOb.getAreaCode());
                                long t2 = System.currentTimeMillis();
                                log.info("##################推荐库位算法整体耗时：" + (t2 - t1) + "ms！#########################");
                                try {
                                    if (resp.getCode() != null && Constant.RESULT.FAILED.code.equals(resp.getCode())) {
                                        responseResult = new ResponseResult(Constant.RESULT.FAILED.code, resp.getMsg(), (Object)null);
                                        return responseResult;
                                    }
                                    locationCode = resp.getData().toString();
                                    log.info("##################推荐库位是：" + locationCode + "#########################");
                                    wmsTaskExecutionLog.setTaskStatus("1");
                                    wmsTaskExecutionLog.setTaskId(taskId);
                                    //库位编码，目标库位
                                    wmsTaskExecutionLog.setLocationCode(locationCode);
                                    //任务执行日志表创建信息，修改立库库位信息表，修改托盘信息表
                                    this.wmsTaskExecutionLogService.inStereoscopicTaskCreate(wmsTaskExecutionLog);

                                } catch (Exception var20) {
                                    log.info("获取推荐库位成功，插入入库任务、更新托盘状态失败");
                                    //修改立库信息表，库位可用
                                    differentBusinessNHService.revertLocationStatus0(locationCode);
                                    long t3 = System.currentTimeMillis();
                                    log.info("##################获取推荐库位成功，插入入库任务、更新托盘状态失耗时：" + (t3 - t2) + "ms！#########################");
                                    responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "获取推荐库位失败", (Object)null);
                                    return responseResult;
                                }
                            }

                            Map<String, Object> rtnMap = new HashMap();
                            rtnMap.put("taskId", taskId);
                            rtnMap.put("locationCode", locationCode);
                            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, rtnMap);
                            return responseResult;
                        }
                    } else {
                        //激活的托盘条码不存在
                        responseResult = new ResponseResult(Constant.RESULT.FAILED.code, Constant.RESULT.PALLET_CODE_ERROR.message, (Object)null);
                        return responseResult;
                        //托盘码不存在，重新绑定。
                    }
                } else {
                    responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "入库口地址为空！", (Object)null);
                    return responseResult;
                }
            } else {
                responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "托盘码为空！", (Object)null);
                return responseResult;
            }
        } else {
            responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "任务类型为空！", (Object)null);
            return responseResult;
        }
    }
    /**
     *功能描述: 生产入库 - 接收入库任务状态
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @Override
    public ResponseResult reportInStereoscopicTask_HB(@RequestBody WcsTransOb wcsTransOb) {
        Resp checkResp = this.wmsTaskExecutionLogService.checkWcsParamCommon(wcsTransOb);
        ResponseResult responseResult;
        if (Constant.RESULT.FAILED.code.equals(checkResp.getCode())) {
             responseResult = new ResponseResult(checkResp.getCode(), checkResp.getMsg(), (Object)null);
            return responseResult;
        } else {
            WmsTaskExecutionLog wmsTaskExecutionLog = (WmsTaskExecutionLog)checkResp.getData();
        if(wcsTransOb!=null && wcsTransOb.getTaskId()!=0L) {

            wmsTaskExecutionLog.setTaskId(wcsTransOb.getTaskId());
            //因为速锐不区分成品，原材料，故加此查询
            //-----查询开始
            WmsTaskExecutionLog wmsTask = wmsTaskExecutionLogService.queryByTask(wmsTaskExecutionLog);
            wmsTaskExecutionLog.setTaskType(wmsTask.getTaskType());
            //----查询结束
            List<Long> allowType = new ArrayList();
            allowType.add(Constant.TaskType.NH_PRODUCT_IN.getTaskType());
            allowType.add(Constant.TaskType.NH_PRODUCT_IN_YCL.getTaskType());
            allowType.add(Constant.TaskType.SORT_IN.getTaskType());
            allowType.add(Constant.TaskType.CHECK_IN.getTaskType());
            allowType.add(Constant.TaskType.HAND_IN.getTaskType());
            allowType.add(Constant.TaskType.PACKING_IN.getTaskType());
            allowType.add(Constant.TaskType.VIRTUAL_PALLET_IN.getTaskType());

            if (!allowType.contains(Long.valueOf(wmsTaskExecutionLog.getTaskType()))) {
                responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "接口【WMS接收WCS上报入库任务状态】不支持【" + Constant.TaskType.getTaskTypeDesc(Long.valueOf(wmsTaskExecutionLog.getTaskType())) + "】任务！", (Object) null);
            } else {
                //保存的key名
                String key = "wms:interface:pallet:" + wcsTransOb.getPalletCode();
                if (this.stringRedisTemplate.hasKey(key)) { //缓存中存在，从缓存中获取并转换为自己需要的对象
                    responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "当前托盘码正在处理中！", (Object) null);
                } else {//缓存中不存在，先从数据库中获取，然后保存到缓存中
                    //保存到缓存中，并设置过期时间
                    this.stringRedisTemplate.opsForValue().set(key, "1", 1L, TimeUnit.MINUTES);
                    //任务状态1创建2执行3完成4异常5重新推荐货位6创建失败
                    if (wcsTransOb.getTaskStatus() == null || !"2".equals(wcsTransOb.getTaskStatus()) && !"3".equals(wcsTransOb.getTaskStatus()) && !"4".equals(wcsTransOb.getTaskStatus()) &&!"5".equals(wcsTransOb.getTaskStatus()) ) {
                        this.wmsTaskExecutionLogService.create(wmsTaskExecutionLog);
                    } else {
                        log.info("--接收入库任务状态,更改状态!");
                        Resp resp = this.wmsTaskExecutionLogService.reportInStereoscopicTask(wmsTaskExecutionLog);
                        if (Constant.RESULT.FAILED.code.equals(resp.getCode())) {
                            this.stringRedisTemplate.delete(key);
                            responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), (Object) null);
                        }
                    }

                    this.stringRedisTemplate.delete(key);
                    responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, (Object) null);

                }
            }
        }else {
            responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "传入数据-task为空", (Object) null);
        }
            if ("3".equals(wcsTransOb.getTaskStatus()) || "4".equals(wcsTransOb.getTaskStatus())) {
                log.info("------------------上报完成时调LED显示----------------------");

                WmsTaskExecutionLog wmsTaskExecutionLogNew = wmsTaskExecutionLogService.queryByTask(wmsTaskExecutionLog);
                //调LED屏幕显示
                try {
                    log.info("入库任务结束，状态为3，调LED！");
                    new LedUtils().SendProgramIn(ledIP, wmsTaskExecutionLogNew.getBatchNo(), wmsTaskExecutionLogNew.getGoodsCode(), wmsTaskExecutionLogNew.getPalletCode(), wmsTaskExecutionLogNew.getTaskId().toString(), "正常", "出库 - LED显示内容:");
                } catch (Exception e) {
                    log.info("LED-入库调显示屏异常" + e);
                }
                log.info("------------------上报完成时调LED显示结束----------------------");
            }else if("5".equals(wcsTransOb.getTaskStatus())){
                log.info("上报状态5，重新推荐货位:");
                if(wcsTransOb.getPalletCode()!=null &&!"".equals(wcsTransOb.getPalletCode())){
                    WmsPallet wmsPallet =new WmsPallet();
                    wmsPallet.setPalletCode(wcsTransOb.getPalletCode());
                    List<WmsPallet> wmsPalletList = wmsPalletMapper.queryByAny(wmsPallet);
                    String locationCode = wmsPalletList.get(0).getLocationCode();
                    String locationCodeNew="";
                    String goodsCode = wmsPalletList.get(0).getGoodsCode();
                    String batchNo = wmsPalletList.get(0).getBatchNo();
                    log.info("原推荐的库位："+locationCode);


                    WmsLocationStereoscopic wmsLocationStereoscopic =new WmsLocationStereoscopic();
                    wmsLocationStereoscopic.setLocationCode(locationCode);
                    WmsLocationStereoscopic information = wmsHBLocationStereoscopicMapper.seleInformationByLocation(wmsLocationStereoscopic);
                    List list =new ArrayList();
                    list.add(1);
                    list.add(2);
                    list.add(3);
                    list.add(4);
                    wmsLocationStereoscopic.setShelvesNumber(information.getShelvesNumber());
                    wmsLocationStereoscopic.setGoodsCode(goodsCode);
                    wmsLocationStereoscopic.setBatchNo(batchNo);
                    wmsLocationStereoscopic.setFloorNumberList(list);
                    //wcs 路径规划失败了，将上次推荐的货位的通道排除，查询同批次的可用货位
                    List<WmsLocationStereoscopic> tongpi_Location= wmsHBLocationStereoscopicMapper.selectLocationCodeByShelvesNumber(wmsLocationStereoscopic);
                    DifferentBusinessNHService differentBusinessNHService = this.differentBusinessNHFactory.getDifferentBusinessNHService(Constant.RecommendLocationEnum.getServiceNameByCode(this.applyfactory));

                    if(tongpi_Location!=null && tongpi_Location.size()>0){
                        log.info("将上次推荐的货位的通道排除，同批次的可用货位：");
                        //判断是否存在同批次，巷道有空位置的，
                        locationCodeNew = differentBusinessNHService.selectlocationCodeByfloor(goodsCode, batchNo, locationCode, tongpi_Location, list);
                        log.info("将上次推荐的货位的通道排除，判断是否存在同批次，巷道有空位置的："+locationCodeNew.toString());
                    }
                    if(locationCodeNew==null || "".equals(locationCodeNew)){
                        //查询空巷道信息
                        List<WmsLocationStereoscopic> kongxiangdaoList =  wmsHBLocationStereoscopicMapper.selectKongXiangDao(wmsLocationStereoscopic);
                        if (kongxiangdaoList != null && kongxiangdaoList.size() > 0) {
                            locationCodeNew = differentBusinessNHService.selectlocationCodeByfloor(goodsCode, batchNo, locationCode, kongxiangdaoList, list);
                            //空巷道，找库位
                            log.info("空巷道，找库位:"+locationCodeNew);
                        }
                    }
                    if(locationCodeNew!=null && !locationCodeNew.isEmpty()){
                        JSONObject jSONObject =new JSONObject();
                        //任务id
                        jSONObject.put("taskId",wcsTransOb.getTaskId());
                        //终点位置
                        jSONObject.put("endNode",locationCodeNew);
                        jSONObject.put("msgTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

                        log.info("调wcs的目的地更换接口-开始！");
                        JSONObject returnJSONObject = sLWCSService.taskChange(jSONObject);
                        log.info("调wcs的目的地更换接口-结束！");
                        if(returnJSONObject.getInteger("returnStatus")!=0){
                            responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "调wcs的目的地更换接口失败！", (Object) null);
                        }else {
                            log.info("将原来库位恢复可用状态！");
                            //更新表数据
                            WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
                            updateOb.setLocationCode(locationCode);
                            updateOb.setUseStatus("0");
                            updateOb.setPalletCodeNull("null");
                            updateOb.setLastModifiedBy("wcs_query_in_location");
                            updateOb.setGmtModified(new Date());
                            this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
                            log.info("更新-新推荐货位写入表中！");
                            //更新表数据
                            updateOb.setLocationCode(locationCode);
                            updateOb.setUseStatus("1");
                            updateOb.setPalletCodeNull("");
                            updateOb.setPalletCode(wcsTransOb.getPalletCode());
                            updateOb.setLastModifiedBy("wcs_query_in_location");
                            updateOb.setGmtModified(new Date());
                            this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);

                            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, (Object) null);
                        }
                    }else{
                        log.info("重新推荐货位-未找到合适的货位");
                        responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "重新推荐货位-未找到合适的货位", (Object) null);

                    }
                }
            }
            return responseResult;
        }
    }

    /**
     *功能描述: 普通移库
     * @params
     * areaCode    是	String	当前立库库区编码，一个WMS可能会对应多个立库，要能区分出来
     * taskId	是	String	任务号，
     * taskType	是	String	30 普通移库
     * palletCode	是	String	托盘号码
     * status	是	String	3完成4异常
     * msg	否	String	如果异常要记录信息
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @Override
    public ResponseResult reportNormalYkTask_HB(@RequestBody WcsTransOb wcsTransOb) {
       /* Resp checkResp = this.wmsTaskExecutionLogService.checkWcsParamCommon_YK(wcsTransOb);
        if (Constant.RESULT.FAILED.code.equals(checkResp.getCode())) {
            ResponseResult responseResult = new ResponseResult(checkResp.getCode(), checkResp.getMsg(), (Object)null);
            return responseResult;
        } else {
            WmsTaskExecutionLog wmsTaskExecutionLog = (WmsTaskExecutionLog)checkResp.getData();*/

            //因为速锐不区分成品，原材料，故加此查询
            //-----查询开始
        WmsTaskExecutionLog wmsTaskExecutionLog=new WmsTaskExecutionLog();
        wmsTaskExecutionLog.setTaskId(wcsTransOb.getTaskId());
        wmsTaskExecutionLog.setTaskStatus(wcsTransOb.getTaskStatus());
        WmsTaskExecutionLog wmsTask =wmsTaskExecutionLogMapper.queryByTask(wmsTaskExecutionLog);
        if(wmsTask!=null) {
            wmsTaskExecutionLog.setTaskType(wmsTask.getTaskType());
            wmsTaskExecutionLog.setUserDefined3(wmsTask.getUserDefined3());
            wmsTaskExecutionLog.setOutAddress(wmsTask.getOutAddress());
            wmsTaskExecutionLog.setInAddress(wmsTask.getInAddress());
        }else{
            log.info("wms未查询到移库任务！");
            return new ResponseResult(Constant.RESULT.FAILED.code, "wms未查询到移库任务：【" + Long.valueOf(wmsTaskExecutionLog.getTaskType()) + "】任务！", (Object)null);
        }
            //----查询结束
            List<Long> allowType = new ArrayList();
            allowType.add(Constant.TaskType.NORMAL_MOVE.getTaskType());
            ResponseResult responseResult ;
            if (!allowType.contains(Long.valueOf(wmsTaskExecutionLog.getTaskType()))) {
                responseResult= new ResponseResult(Constant.RESULT.FAILED.code, "接口【WMS接收WCS接收普通移库任务状态】不支持【" + Constant.TaskType.getTaskTypeDesc(Long.valueOf(wmsTaskExecutionLog.getTaskType())) + "】任务！", (Object)null);
                return responseResult;
            } else {
                String key = "wms:interface:pallet:" + wcsTransOb.getPalletCode();
                if (this.stringRedisTemplate.hasKey(key)) {
                   responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "当前托盘码正在处理中！", (Object)null);
                } else {
                    this.stringRedisTemplate.opsForValue().set(key, "1", 1L, TimeUnit.MINUTES);
                    log.info("修改状态-开始");
                    Resp resp = wmsMoveStereoscopicService.kuNei_YiKu(wmsTaskExecutionLog);
                    log.info("修改状态-结束");
                    if (Constant.RESULT.FAILED.code.equals(resp.getCode())) {
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
                    } else {
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, (Object)null);
                    }
                }
            }
            if("3".equals(wcsTransOb.getTaskStatus()) ||"4".equals(wcsTransOb.getTaskStatus())) {
                log.info("------------------上报完成时调LED显示----------------------");
                WmsTaskExecutionLog wmsTaskExecutionLogNew = wmsTaskExecutionLogService.queryByTask(wmsTaskExecutionLog);
                //调LED屏幕显示
                try {
                    log.info("出库结束，状态为3，调LED！");
                    new LedUtils().SendProgramIn(ledIP, wmsTaskExecutionLogNew.getBatchNo(), wmsTaskExecutionLogNew.getGoodsCode(), wmsTaskExecutionLogNew.getPalletCode(), wmsTaskExecutionLogNew.getTaskId().toString(), "移库完成", "出库 - LED显示内容:");
                } catch (Exception e) {
                    log.info("LED-入库调显示屏异常" + e);
                }
                log.info("------------------上报完成时调LED显示结束----------------------");
            }
            return responseResult;

    }
    /**
     *功能描述: 出库（库内）移库
     * @params
     * areaCode    是	String	当前立库库区编码，一个WMS可能会对应多个立库，要能区分出来
     * orderNo	是	String	订单号，
     * taskType	是	String	 31出库移库
     * palletCode	是	String	托盘号码
     * status	是	String	3完成4异常
     * msg	否	String	如果异常要记录信息
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @Override
    public ResponseResult reportOutwarehouseYkTask_HB(WcsTransOb wcsTransOb){
        Resp checkResp = this.wmsTaskExecutionLogService.checkWcsParamCommon(wcsTransOb);
        if (Constant.RESULT.FAILED.code.equals(checkResp.getCode())) {
            ResponseResult responseResult = new ResponseResult(checkResp.getCode(), checkResp.getMsg(), (Object)null);
            return responseResult;
        } else {
            WmsTaskExecutionLog wmsTaskExecutionLog = (WmsTaskExecutionLog)checkResp.getData();
            //因为速锐不区分成品，原材料，故加此查询
            //-----查询开始
            WmsTaskExecutionLog wmsTask = wmsTaskExecutionLogService.queryByTask(wmsTaskExecutionLog);
            wmsTaskExecutionLog.setTaskType(wmsTask.getTaskType());
            //----查询结束
            List<Long> allowType = new ArrayList();
            allowType.add(Constant.TaskType.NORMAL_MOVE.getTaskType());
            if (!allowType.contains(Long.valueOf(wmsTaskExecutionLog.getTaskType()))) {
                ResponseResult responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "接口【WMS接收WCS接收普通移库任务状态】不支持【" + Constant.TaskType.getTaskTypeDesc(Long.valueOf(wmsTaskExecutionLog.getTaskType())) + "】任务！", (Object)null);
                return responseResult;
            } else {
                String key = "wms:interface:pallet:" + wcsTransOb.getPalletCode();
                if (this.stringRedisTemplate.hasKey(key)) {
                    ResponseResult responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "当前托盘码正在处理中！", (Object)null);
                    return responseResult;
                } else {
                    this.stringRedisTemplate.opsForValue().set(key, "1", 1L, TimeUnit.MINUTES);
                    Resp resp = this.wmsMoveStereoscopicService.reportNormalYkTask(wmsTaskExecutionLog);
                    ResponseResult responseResult;
                    if (Constant.RESULT.FAILED.code.equals(resp.getCode())) {
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
                        return responseResult;
                    } else {
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, (Object)null);
                        return responseResult;
                    }
                }
            }
        }
    }
    /**
     *功能描述: 直发出库 -接受出库任务状态  /盘点出库
     * areaCode	当前立库库区编码	String	是
     * taskId	任务号	Integer	是
     * taskType	20 直发出库	String	是
     * palletCode	托盘号码	String	是
     * taskStatus	2执行3完成4异常	String	是
     * msg	信息	String	否
     *
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @Override
    public ResponseResult reportOutStraightTask_HB(@RequestBody WcsTransOb wcsTransOb) {
        //先查看，此任务是否已经完成
        if(wcsTransOb!=null &&wcsTransOb.getTaskId()!=0L){
            WmsTaskExecutionLog  taskExecutionLog = new WmsTaskExecutionLog();
            taskExecutionLog.setTaskId(wcsTransOb.getTaskId());
            //taskExecutionLog.setTaskType(wcsTransOb.getTaskType());
            //查询任务表，查看任务是否已经完成
            List<WmsTaskExecutionLog> wmsTaskExecutionLogList = wmsTaskExecutionLogMapper.queryByAny(taskExecutionLog);
            if(wmsTaskExecutionLogList!=null &&wmsTaskExecutionLogList.size()>0){
                for(WmsTaskExecutionLog  wmsTaskExecutionLog1 :wmsTaskExecutionLogList){
                    //根据任务号 判断状态，是否存在 已完成状态   任务状态1创建2执行3完成4异常5取消6创建失败
                    if("3".equals(wmsTaskExecutionLog1.getTaskStatus())){
                        ResponseResult responseResult = new ResponseResult("1", "此任务:"+wmsTaskExecutionLog1.getTaskId()+"，状态已完成！", (Object)null);
                        return responseResult;
                    }
                }
            }
        }else{
            ResponseResult responseResult = new ResponseResult("1", "信息不全，订单号为空！", (Object)null);
            return responseResult;
        }

        //检查传入的字段是否匹配wmsTaskExecutionLog
        Resp checkResp = this.wmsTaskExecutionLogService.checkWcsParamCommon(wcsTransOb);
        if (Constant.RESULT.FAILED.code.equals(checkResp.getCode())) {
            ResponseResult responseResult = new ResponseResult(checkResp.getCode(), checkResp.getMsg(), (Object)null);
            return responseResult;
        } else {
            WmsTaskExecutionLog wmsTaskExecutionLog = (WmsTaskExecutionLog)checkResp.getData();
            //因为速锐不区分成品，原材料，故加此查询
            //-----查询开始
            WmsTaskExecutionLog wmsTask = wmsTaskExecutionLogService.queryByTask(wmsTaskExecutionLog);
            wmsTaskExecutionLog.setTaskType(wmsTask.getTaskType());
            //----查询结束
            List<Long> allowType = new ArrayList();
            //直发出库
            allowType.add(Constant.TaskType.STRAIGHT_OUT.getTaskType());
            //人工出库
            allowType.add(Constant.TaskType.HAND_OUT.getTaskType());
            //盘点出库
            allowType.add(Constant.TaskType.CHECK_OUT.getTaskType());
            if (!allowType.contains(Long.valueOf(wmsTaskExecutionLog.getTaskType()))) {
                log.info("出库任务类型 ：不是直发出库20，不是人工出库26，也不是盘点出库22");
                ResponseResult responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "接口【WMS接收WCS出库任务状态-直发口】不支持【" + Constant.TaskType.getTaskTypeDesc(Long.valueOf(wmsTaskExecutionLog.getTaskType())) + "】任务！", (Object)null);
                return responseResult;
            } else {
                String key = "wms:interface:pallet:" + wcsTransOb.getPalletCode();
                if (this.stringRedisTemplate.hasKey(key)) {
                    ResponseResult responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "当前托盘码正在处理中！", (Object)null);
                    return responseResult;
                } else {
                    /* this.stringRedisTemplate.opsForValue().set(key, "1", 1L, TimeUnit.MINUTES);
                    WmsPallet wmsPalletOb = new WmsPallet();
                    wmsPalletOb.setPalletCode(wcsTransOb.getPalletCode());
                    wmsPalletOb.setActiveFlag("1");
                    List<WmsPallet> wmsPalletList = this.wmsPalletService.queryByAny(wmsPalletOb);
                    Integer boxAmount = 0;
                    if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                        boxAmount = ((WmsPallet)wmsPalletList.get(0)).getAmount();
                    }*/
                    //wcs任务上报，更新任务表。
                    Resp resp = this.wmsTaskExecutionLogService.reportUpOutStraightTask(wmsTaskExecutionLog);
                    ResponseResult responseResult;
                    if (Constant.RESULT.FAILED.code.equals(resp.getCode())) {
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
                        return responseResult;
                    } else {
                        //agv手持 原料出库，下发agv，
                        if ("20".equals(wmsTaskExecutionLog.getTaskType()) &&"3".equals(wcsTransOb.getTaskStatus()) && String.valueOf(Constant.TaskType.NH_PRODUCT_IN_YCL.getTaskType()).equals(wmsTaskExecutionLog.getUserDefined2()) &&wmsTaskExecutionLog.getUserDefined5() != null && !"".equals(wmsTaskExecutionLog.getUserDefined5())) {
                            //TODO 下发AGV
                            JSONObject agvJsonObject = new JSONObject();
                            agvJsonObject.put("taskId", wmsTaskExecutionLog.getTaskId());
                            agvJsonObject.put("startPoint", Constant.StereoscopicInfo.FLOOR_ONE_OUT_TO_ADDRESS);
                            agvJsonObject.put("endPoint", wmsTaskExecutionLog.getUserDefined5());
                            try {
                                log.info("原材料出库，下发AGV -开始");
                                JSONObject returnAgvJSONObject = wmsNHAgvService.executeIssue(agvJsonObject);
                                //模拟返回  调取agv
//                                JSONObject returnAgvJSONObject = new JSONObject();
//                                returnAgvJSONObject.put("isSuccessful","true");
                                log.info("原材料出库，下发AGV -结束");
                                if ("true".equals(returnAgvJSONObject.getString("isSuccessful"))) {
                                    responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, (Object) null);
                                } else {

                                    responseResult = new ResponseResult(Constant.RESULT.FAILED.code, returnAgvJSONObject.getString("errorMessage"), (Object) null);
                                }
                            }catch (Exception e){
                                log.info("下发agv任务异常！");
                                WmsTaskExecutionLog wmsTaskExecutionLogNew = wmsTaskExecutionLogService.queryByTask(wmsTaskExecutionLog);
                                //调LED屏幕显示
                                try {
                                    log.info("出库结束，状态为3，调LED！");
                                    new LedUtils().SendProgramIn(ledIP, wmsTaskExecutionLogNew.getBatchNo(), wmsTaskExecutionLogNew.getGoodsCode(), wmsTaskExecutionLogNew.getPalletCode(), wmsTaskExecutionLogNew.getTaskId().toString(), "下发agv任务异常！", "出库 - LED显示内容:");
                                } catch (Exception e1) {
                                    log.info("LED-入库调显示屏异常" + e1);
                                }
                                responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "下发agv任务异常！", (Object) null);
                                return responseResult;
                            }
                        }
                        // 状态完成，成品出库，下发agv
                        else if ("20".equals(wmsTaskExecutionLog.getTaskType()) && "3".equals(wcsTransOb.getTaskStatus()) && String.valueOf(Constant.TaskType.NH_PRODUCT_IN.getTaskType()).equals(wmsTaskExecutionLog.getUserDefined2()) && wmsTaskExecutionLog.getUserDefined5() != null && !"".equals(wmsTaskExecutionLog.getUserDefined5())) {
                            //TODO 下发AGV
                            JSONObject agvJsonObject = new JSONObject();
                            agvJsonObject.put("taskId", wmsTaskExecutionLog.getTaskId());
                            agvJsonObject.put("startPoint", Constant.StereoscopicInfo.FLOOR_ONE_OUT_TO_ADDRESS);
                            int n=(int)(Math.random()+0.5);
                            if(n == 0) {
                                agvJsonObject.put("endPoint", Constant.StereoscopicInfo.FLOOR_ONE_OUT_TO_AGV_1);
                            }else {
                                agvJsonObject.put("endPoint", Constant.StereoscopicInfo.FLOOR_ONE_OUT_TO_AGV_2);
                            }
                            try {
                                log.info("成品出库，下发AGV -开始:"+agvJsonObject );
                                JSONObject returnAgvJSONObject = wmsNHAgvService.executeIssue(agvJsonObject);
                                //模拟返回  调取agv
//                                JSONObject returnAgvJSONObject = new JSONObject();
//                                returnAgvJSONObject.put("isSuccessful","true");
                                log.info("成品出库，下发AGV -结束");
                                if ("true".equals(returnAgvJSONObject.getString("isSuccessful"))) {
                                    responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, (Object) null);
                                } else {
                                    responseResult = new ResponseResult(Constant.RESULT.FAILED.code, returnAgvJSONObject.getString("errorMessage"), (Object) null);
                                }
                            }catch (Exception e){
                                log.info("成品出库- 下发agv任务异常！");
                                WmsTaskExecutionLog wmsTaskExecutionLogNew = wmsTaskExecutionLogService.queryByTask(wmsTaskExecutionLog);
                                //调LED屏幕显示
                                try {
                                    log.info("成品出库 - 出库结束，状态为3，调LED！");
                                    new LedUtils().SendProgramIn(ledIP, wmsTaskExecutionLogNew.getBatchNo(), wmsTaskExecutionLogNew.getGoodsCode(), wmsTaskExecutionLogNew.getPalletCode(), wmsTaskExecutionLogNew.getTaskId().toString(), "下发agv任务异常！", "出库 - LED显示内容:");
                                } catch (Exception e1) {
                                    log.info("LED-入库调显示屏异常" + e1);
                                }
                                responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "下发agv任务异常！", (Object) null);
                                return responseResult;
                            }
                        }
                        else {
                            this.stringRedisTemplate.delete(key);
                            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, (Object) null);
                        }
                        if ("3".equals(wcsTransOb.getTaskStatus()) ||"4".equals(wcsTransOb.getTaskStatus())) {
                            log.info("------------------上报完成时调LED显示----------------------");

                            WmsTaskExecutionLog wmsTaskExecutionLogNew = wmsTaskExecutionLogService.queryByTask(wmsTaskExecutionLog);
                            //调LED屏幕显示
                            try {
                                String err="";
                                log.info("出库,状态： "+wcsTransOb.getTaskStatus()+"，调LED！");
                                if("3".equals(wcsTransOb.getTaskStatus())){
                                    err="正常";
                                }else{
                                    err=wcsTransOb.getMsg();
                                }

                                new LedUtils().SendProgramIn(ledIP, wmsTaskExecutionLogNew.getBatchNo(), wmsTaskExecutionLogNew.getGoodsCode(), wmsTaskExecutionLogNew.getPalletCode(), wmsTaskExecutionLogNew.getTaskId().toString(), err, "出库 - LED显示内容:");
                            } catch (Exception e) {
                                log.info("LED-入库调显示屏异常" + e);
                            }
                            log.info("------------------上报完成时调LED显示结束----------------------");
                        }
                        return responseResult;
                    }
                }
            }
        }
    }
    /**
     *功能描述: 接收出库任务状态-盘点口
     * @params
     * areaCode    是	String	当前立库库区编码，一个WMS可能会对应多个立库，要能区分出来
     * orderNo	是	String	盘点单号
     * taskType	是	String	4 出库
     * palletCode	是	String	托盘号码
     * status	是	String	3完成4异常
     * msg	否	String	如果异常要记录信息
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @Override
    public ResponseResult reportOutCheckTask_HB(@RequestBody WcsTransOb wcsTransOb) {
        Resp checkResp = this.wmsTaskExecutionLogService.checkWcsParamCommon(wcsTransOb);
        if (Constant.RESULT.FAILED.code.equals(checkResp.getCode())) {
            ResponseResult responseResult = new ResponseResult(checkResp.getCode(), checkResp.getMsg(), (Object)null);
            return responseResult;
        } else {
            WmsTaskExecutionLog wmsTaskExecutionLog = (WmsTaskExecutionLog)checkResp.getData();
            //因为速锐不区分成品，原材料，故加此查询
            //-----查询开始
            WmsTaskExecutionLog wmsTask = wmsTaskExecutionLogService.queryByTask(wmsTaskExecutionLog);
            wmsTaskExecutionLog.setTaskType(wmsTask.getTaskType());
            //----查询结束
            List<Long> allowType = new ArrayList();
            allowType.add(Constant.TaskType.CHECK_OUT.getTaskType());
            if (!allowType.contains(Long.valueOf(wmsTaskExecutionLog.getTaskType()))) {
                ResponseResult responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "接口【WMS接收WCS出库任务状态-盘点口】不支持【" + Constant.TaskType.getTaskTypeDesc(Long.valueOf(wmsTaskExecutionLog.getTaskType())) + "】任务！", (Object)null);
                return responseResult;
            } else {
                String key = "wms:interface:pallet:" + wcsTransOb.getPalletCode();
                if (this.stringRedisTemplate.hasKey(key)) {
                    ResponseResult responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "当前托盘码正在处理中！", (Object)null);
                    return responseResult;
                } else {
                    this.stringRedisTemplate.opsForValue().set(key, "1", 1L, TimeUnit.MINUTES);
                    Resp resp = this.wmsTaskExecutionLogService.reportOutCheckTask(wmsTaskExecutionLog);
                    ResponseResult responseResult;
                    if (Constant.RESULT.FAILED.code.equals(resp.getCode())) {
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
                        return responseResult;
                    } else {
                        this.stringRedisTemplate.delete(key);
                        responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, (Object)null);
                        return responseResult;
                    }
                }
            }
        }
    }
    /**
     *功能描述: 确认对账 - 修改任务的状态
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @Override
    public ResponseResult taskStatusUpdate(@RequestBody WmsTaskExecutionLog wmsTaskExecutionLog){
        log.info("修改任务的状态:"+wmsTaskExecutionLog.getUserDefined3());
        ResponseResult responseResult;
        //根据任务号查询任务数据
        WmsTaskExecutionLog wmsTaskExecutionLog1 = wmsTaskExecutionLogMapper.queryByTask(wmsTaskExecutionLog);
        //入库（成品原材料）状态 非3完成，不可更改  出库状态1可更改
        if( ("10".equals(wmsTaskExecutionLog1.getTaskType())  || "50".equals(wmsTaskExecutionLog1.getTaskType())  ) && !"3".equals(wmsTaskExecutionLog1.getTaskStatus())){
           return new ResponseResult(Constant.RESULT.FAILED.code, "入库（成品/原材料）状态 非3完成，状态不可更改！", (Object) null);
        }
        if( "20".equals(wmsTaskExecutionLog1.getTaskType()) && !"1".equals(wmsTaskExecutionLog1.getTaskStatus())){
            return new ResponseResult(Constant.RESULT.FAILED.code, "出库状态，非创建，状态不可更改！", (Object) null);
        }
        WmsLocationStereoscopic wms =new WmsLocationStereoscopic();
        wms.setLocationCode(wmsTaskExecutionLog1.getLocationCode());
        //查询数据库-地图，状态
        List<WmsLocationStereoscopic> wmsList = wmsLocationStereoscopicMapper.queryByAny(wms);
        //地图状态非0时，可更改。
        if(wmsList!=null && wmsList.size()>0 &&!"0".equals(wmsList.get(0).getUseStatus())) {

            if("0".equals(wmsTaskExecutionLog.getUserDefined3())){
            wmsTaskExecutionLog.setErrorMsg("已过账");
            }else if("1".equals(wmsTaskExecutionLog.getUserDefined3())){
                wmsTaskExecutionLog.setErrorMsg("暂缓过账");
            }
            wmsTaskExecutionLogMapper.updateByTaskId(wmsTaskExecutionLog);
            WmsLocationStereoscopic wmsLocationStereoscopic =new WmsLocationStereoscopic();
            if("0".equals(wmsTaskExecutionLog.getUserDefined3())){
                wmsLocationStereoscopic.setUseStatus("3");
            }else if("1".equals(wmsTaskExecutionLog.getUserDefined3())){
                wmsLocationStereoscopic.setUseStatus("6");
            }
            wmsLocationStereoscopic.setLocationCode(wmsTaskExecutionLog1.getLocationCode());
            wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopic);
            log.info("修改任务的状态:"+wmsTaskExecutionLog.getUserDefined3()+"完成！");
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, (Object) null);
        }else{
            responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "此库位无货，不可过账", (Object) null);
        }
        return responseResult;
    }
    /**
     *功能描述: 入库确认对账 - 修改任务的状态，并且修改库位的状态。
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @Override
    public ResponseResult stereoscopicStatusUpdate(WmsTaskExecutionLog wmsTaskExecutionLog) {
        log.info("入库确认对账 -  修改任务的状态- 开始" +wmsTaskExecutionLog.getUserDefined3());
        ResponseResult responseResult;
        //根据任务号查询任务数据
        WmsTaskExecutionLog wmsTaskExecutionLog1 = wmsTaskExecutionLogMapper.queryByTask(wmsTaskExecutionLog);
        //入库（成品原材料）状态 非3完成，不可更改  出库状态1可更改
        if( ("10".equals(wmsTaskExecutionLog1.getTaskType())  || "50".equals(wmsTaskExecutionLog1.getTaskType())  ) && !"3".equals(wmsTaskExecutionLog1.getTaskStatus())){
            return new ResponseResult(Constant.RESULT.FAILED.code, "入库（成品/原材料）状态 非3完成，状态不可更改！", (Object) null);
        }
        if( "20".equals(wmsTaskExecutionLog1.getTaskType()) && !"1".equals(wmsTaskExecutionLog1.getTaskStatus())){
            return new ResponseResult(Constant.RESULT.FAILED.code, "出库状态，非创建，状态不可更改！", (Object) null);
        }
        WmsLocationStereoscopic wms =new WmsLocationStereoscopic();
        wms.setLocationCode(wmsTaskExecutionLog1.getLocationCode());
        //查询数据库-地图，状态
        List<WmsLocationStereoscopic> wmsList = wmsLocationStereoscopicMapper.queryByAny(wms);
        //地图状态非0时，可更改。
        if(wmsList!=null && wmsList.size()>0 &&!"0".equals(wmsList.get(0).getUseStatus())) {
            WmsPallet wmsPallet = new WmsPallet();
            wmsPallet.setPalletCode(wmsTaskExecutionLog1.getPalletCode());
            wmsPallet.setUserDefined3(wmsTaskExecutionLog.getUserDefined3());
            //托盘表校验审核
            wmsPalletMapper.updateByPalletCode(wmsPallet);
            //任务表校验审核
            if ("0".equals(wmsTaskExecutionLog.getUserDefined3())) {
                wmsTaskExecutionLog.setErrorMsg("已过账");
            } else if ("1".equals(wmsTaskExecutionLog.getUserDefined3())) {
                wmsTaskExecutionLog.setErrorMsg("暂缓过账");
            }
            wmsTaskExecutionLogMapper.updateByTaskId(wmsTaskExecutionLog);

            WmsLocationStereoscopic wmsLocationStereoscopic = new WmsLocationStereoscopic();
            if ("0".equals(wmsTaskExecutionLog.getUserDefined3())) {
                wmsLocationStereoscopic.setUseStatus("3");
            } else if ("1".equals(wmsTaskExecutionLog.getUserDefined3())) {
                wmsLocationStereoscopic.setUseStatus("5");
            }
            wmsLocationStereoscopic.setLocationCode(wmsTaskExecutionLog1.getLocationCode());
            wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopic);

            log.info("入库确认对账 -  修改任务的状态 - 结束");

             responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, (Object) null);
        }else{
            responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "此库位无货，不可过账", (Object) null);
        }
        return responseResult;
    }
    /**
     *功能描述: AGV /PDA 请求出库 /盘库  -业务处理
     *
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
    @Override
    public JSONObject requestDelivery(@RequestBody JSONObject jsonObject){
        log.info("请求出库,转化后的json串:"+jsonObject);
        log.info("AGV请求出库 - 请求时间:"+jsonObject.getString("applyTime") +" /PDA请求出库 - 请求时间: "+jsonObject.getString("reportTime"));
        String outboundID ="";
        WmsTaskExecutionLog wmsTaskExecutionLog =new WmsTaskExecutionLog();
        if("22".equals(jsonObject.getString("outboundType"))) {
            wmsTaskExecutionLog.setTaskType("22");
        }else{
            //类型：出库
            wmsTaskExecutionLog.setTaskType("20");
            //成品还是原材料
            wmsTaskExecutionLog.setUserDefined2(jsonObject.getString("outboundType"));
        }
        //物料编码
        String goodsCode = jsonObject.getString("materialsCode");
        wmsTaskExecutionLog.setGoodsCode(goodsCode);
        //批次号
        wmsTaskExecutionLog.setBatchNo(jsonObject.getString("batchCode"));
        //审核通过：0 未审核：1
        wmsTaskExecutionLog.setUserDefined3("0");
        wmsTaskExecutionLog.setTaskStatus("1");
        JSONObject returnJsonObject =new JSONObject();
        //根据物料号，批次号，审核通过 查询数据。
        List<WmsTaskExecutionLog> wmsTaskExecutionLogList = wmsTaskExecutionLogMapper.queryByAny(wmsTaskExecutionLog);
        if (wmsTaskExecutionLogList != null && wmsTaskExecutionLogList.size() > 0) {
            //将查到信息 进行排序，
           /* List mixList = new ArrayList();
            List maxList = new ArrayList();
            for (WmsTaskExecutionLog w : wmsTaskExecutionLogList) {
                int number = Integer.parseInt(w.getLocationCode().substring(0, 2));
                if (number < 15) {
                    mixList.add(w);
                } else {
                    maxList.add(w);
                }
            }
            ListIterator<Integer> li = maxList.listIterator();
            // 将游标定位到集合的结尾
            while (li.hasNext()) {
                li.next();
            }
            // 迭代器遍历hasPrevious()方法用于反向遍历的时候判断是否还有下一个元素
            while (li.hasPrevious()) {
                mixList.add(li.previous());
            }
            wmsTaskExecutionLogList = new ArrayList<>();
            wmsTaskExecutionLogList.addAll(mixList);*/

            //将要出库的数据
            List<WmsTaskExecutionLog> list = new ArrayList<>();
            int number = 0;
            for (int i = 0; i < wmsTaskExecutionLogList.size(); i++) {
                if (wmsTaskExecutionLogList.get(i).getUserDefined1() != null) {
                    number = number + Integer.parseInt(wmsTaskExecutionLogList.get(i).getUserDefined1());
                }
                list.add(wmsTaskExecutionLogList.get(i));
                if (number >= Integer.parseInt(jsonObject.getString("Weight"))) {
                    break;
                }
            }
            if (number < Integer.parseInt(jsonObject.getString("Weight"))) {
                return getReturnJsonObject("1", "可出的数量，小于请求的数量！");
            }
            List<WmsTaskExecutionLog> logList =new ArrayList<>();
            //判断是否可出货
            for (int i = 0; i < list.size(); i++) {
                WmsLocationStereoscopic wmsLocationStereoscopic = new WmsLocationStereoscopic();
                wmsLocationStereoscopic.setGoodsCode(list.get(i).getGoodsCode());
                wmsLocationStereoscopic.setBatchNo(list.get(i).getBatchNo());
                List floorNumberList = new ArrayList();
                int num = Integer.parseInt(list.get(i).getLocationCode().substring(list.get(i).getLocationCode().length() - 2, list.get(i).getLocationCode().length()));
                floorNumberList.add(num);
                wmsLocationStereoscopic.setFloorNumberList(floorNumberList);
                //库位的行
                Integer hang = Integer.parseInt(list.get(i).getLocationCode().substring(0, 2));
                //库位的列
                Integer lie = Integer.parseInt(list.get(i).getLocationCode().substring(2, 4));
                wmsLocationStereoscopic.setColumnNumber(lie);
                //查询此列是否被锁，此列如果被锁, (再以15为界限，判断)
                List<WmsLocationStereoscopic> logkList = this.wmsHBLocationStereoscopicMapper.getLocationorlockBy(wmsLocationStereoscopic);
                if (logkList != null && logkList.size() > 0) {
                    for (int n = 0; n < logkList.size(); n++) {
                        if (logkList.get(n).getColumnNumber() < 15 && hang < logkList.get(n).getColumnNumber()) {
                            log.info("此库位：" + list.get(i).getLocationCode() + "小于15的向道，前面有库位被锁：被锁库位：" + logkList.get(n).getLocationCode());
                            if (!list.get(i).getLocationCode().equals(logkList.get(n).getColumnNumber())) {
                                list.remove(i);
                            }
                        }
                        if (logkList.get(n).getColumnNumber() > 15 && hang > logkList.get(n).getColumnNumber()) {
                            log.info("此库位：" + list.get(i).getLocationCode() + "大于15的向道，前面有库位被锁：被锁库位：" + logkList.get(n).getLocationCode());
                            if (!list.get(i).getLocationCode().equals(logkList.get(n).getColumnNumber())) {
                                list.remove(i);
                            }
                        }
                    }
                }
                    //根据托盘号，查找库位。
                    WmsLocationStereoscopic wmsLocationStereoscopic1 =new WmsLocationStereoscopic();
                    wmsLocationStereoscopic1.setPalletCode(list.get(i).getPalletCode());
                    List<WmsLocationStereoscopic> wmsLocationStereoscopicList = this.wmsLocationStereoscopicMapper.queryByAny(wmsLocationStereoscopic1);
                    //一个托盘只能有一个货位。
                    if(wmsLocationStereoscopicList!=null &&wmsLocationStereoscopicList.size()>0) {
                        //判断此巷道是否在短巷道内，如果在短巷道
                       int  shelvesNumber = wmsLocationStereoscopicList.get(0).getShelvesNumber();
                       List<Integer> shelvesNumberList = Arrays.asList(1,3,13,15,25,27,37,39);
                       //如果是短巷道，查询短巷道阻隔sql语句
                       if(shelvesNumberList.contains(shelvesNumber)){
                           List<WmsLocationStereoscopic> LocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPassDuanDao(wmsLocationStereoscopicList.get(0));
                           if (LocationStereoscopicList != null && LocationStereoscopicList.size() > 0) {
                               log.info("短巷道货位：" + list.get(i).getLocationCode() + "有阻隔，不能出库");
                               continue;
                           }else{
                               logList.add(list.get(i));
                           }
                       } else {
                           //判断向道是否被阻隔,有数据即被阻隔
                           List<WmsLocationStereoscopic> LocationStereoscopicList = this.wmsHBLocationStereoscopicMapper.getLocationPass(wmsLocationStereoscopicList.get(0));
                           if (LocationStereoscopicList != null && LocationStereoscopicList.size() > 0) {
                               //log.info("巷道货位：" + list.get(i).getLocationCode() + "有阻隔，不能出库");
                               continue;
                           }else{
                               logList.add(list.get(i));
                           }
                       }
                    }
                }
                //如果有入库任务，将出库暂缓出库
                //判断list中是否还有数据，有则下发wcs，出库
            if (logList != null && logList.size() > 0) {
                    //判断是否有任务正在执行。如果有任务正在执行，排队，否则直接出库
                    WmsTaskExecutionLog wmsTaskExecutionLog1 =new WmsTaskExecutionLog();
                    List rukuStatus =new ArrayList();
                    rukuStatus.add("2");
                    rukuStatus.add("4");
                    wmsTaskExecutionLog1.setTaskStatusList(rukuStatus);
                    List statuslist =new ArrayList();
                    statuslist.add("10");
                    statuslist.add("50");
                    wmsTaskExecutionLog1.setIdList(statuslist);
                    List<WmsTaskExecutionLog> receiptList = wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog1);
                    //agv下发的任务
                    if(jsonObject.getString("applyTime")!=null) {
                        outboundID = jsonObject.getString("sapOrderNo");
                    }
                    //PDA下发的任务
                    if(jsonObject.getString("reportTime")!=null){
                        outboundID = jsonObject.getString("outboundID");
                    }
                    //如果为空，没有任务，则出库任务下发
                    if(receiptList==null ||receiptList.isEmpty()) {
                        //更新任务，是否呼叫agv
                        if(list!=null && list.size()>0) {
                            for (int n=0 ;n<list.size();n++) {
                                WmsTaskExecutionLog wmsTaskExecutionLogUP = list.get(n);
                                //将传入的testID存入UserDefined4
                                wmsTaskExecutionLogUP.setUserDefined4(outboundID);
                                if (jsonObject.getString("endLocation") != null && !jsonObject.getString("endLocation").isEmpty()) {
                                    wmsTaskExecutionLogUP.setUserDefined5(jsonObject.getString("endLocation"));
                                }
                                //PDA下出库任务，是否呼叫agv 1：呼叫 0：不呼叫
                                if(jsonObject.getString("agvCall")!=null &&!"".equals(jsonObject.getString("agvCall"))){
                                    if("1".equals(jsonObject.getString("agvCall"))){
                                        wmsTaskExecutionLogUP.setUserDefined5("PDA_AGV_ture");
                                    }
                                }
                                //7等待出库，8暂存出库
                                wmsTaskExecutionLogUP.setTaskStatus("7");
                                //存在入库任务，出库任务 暂存
                                wmsTaskExecutionLogService.updateByTaskId(wmsTaskExecutionLogUP);
                            }
                        }
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("groupId", outboundID);
                        jsonObject1.put("msgTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        jsonObject1.put("priorityCode", "");
                        jsonObject1.put("warehouse", "L-NH01");
                        List list2 = new ArrayList();
                        //拼接调速锐的【任务下发】接口
                        for (int j = 0; j < logList.size(); j++) {
                            if(j==0){
                                Map map = new HashMap();
                                map.put("taskId", list.get(j).getTaskId().toString());
                                //任务类型，1：出库
                                map.put("taskType", 1);
                                //任务起点 库位
                                map.put("startNode", logList.get(j).getLocationCode());
                                //任务终点 //TODO 待确认终点
                                map.put("endNode", Constant.StereoscopicInfo.FLOOR_ONE_OUT_TO_ADDRESS);
                                //托盘码
                                map.put("barCode", logList.get(j).getPalletCode());
                                map.put("order", j);
                                list2.add(map);
                                jsonObject1.put("tasks", list2);
                                try {
                                    //调WCS出库任务
                                    log.info("---------------出库，调用wcs的任务接收接口 : " + jsonObject1.toString());
                                    returnJsonObject = sLWCSService.taskReceive(jsonObject1);
                                    // 模拟返回
//                                        returnJsonObject.put("returnStatus",0);
                                    log.info("---------------出库，调用wcs的任务接收接口 -结束: " + returnJsonObject.toString());
                                } catch (Exception e) {
                                    return getReturnJsonObject("1", "出库任务:" + outboundID + "请求wcs异常！");
                                }
                            }
                        }

                        //接收成功
                        if (returnJsonObject.getInteger("returnStatus") == 0) {
                            WmsPallet wmsPallet = new WmsPallet();
                            wmsPallet.setLockBy(logList.get(0).getTaskId().toString());
                            wmsPallet.setPalletCode(logList.get(0).getPalletCode());
                            //根据托盘码锁定托盘
                            wmsPalletMapper.updateByPalletCode(wmsPallet);
                            WmsLocationStereoscopic wmsLocationStereoscopic1 = new WmsLocationStereoscopic();
                            wmsLocationStereoscopic1.setLocationCode(logList.get(0).getLocationCode());
                            wmsLocationStereoscopic1.setUseStatus("2");
                            wmsLocationStereoscopic1.setLastModifiedBy("wms");
                            wmsLocationStereoscopic1.setGmtModified(new Date());
                            this.wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopic1);
                            WmsTaskExecutionLog wmsTaskExecutionLogUP = logList.get(0);
                            wmsTaskExecutionLogUP.setTaskStatus("2");
                            //存在入库任务，出库任务 暂存
                            wmsTaskExecutionLogService.updateByTaskId(wmsTaskExecutionLogUP);
                            return getReturnJsonObject("0", "出库任务:" + outboundID + "请求成功！");
                        } else {
                            return getReturnJsonObject("1", "出库任务:" + outboundID + "请求失败！" + returnJsonObject.getString("returnInfo"));
                        }

                    }else{
                        log.info("存在入库任务！将出库 任务缓存！");
                        for (int j = 0; j < list.size(); j++) {
                            WmsTaskExecutionLog wmsTaskExecutionLogUP = list.get(j);
                            //将传入的testID存入UserDefined4
                            wmsTaskExecutionLogUP.setUserDefined4(outboundID);
                            if (jsonObject.getString("endLocation") != null && !jsonObject.getString("endLocation").isEmpty()) {
                                wmsTaskExecutionLogUP.setUserDefined5(jsonObject.getString("endLocation"));
                            }
                            //PDA下出库任务，是否呼叫agv 1：呼叫 0：不呼叫
                            if(jsonObject.getString("agvCall")!=null &&!"".equals(jsonObject.getString("agvCall"))){
                                if("1".equals(jsonObject.getString("agvCall"))){
                                    wmsTaskExecutionLogUP.setUserDefined5("PDA_AGV_ture");
                                }
                            }
                            //7等待出库，8暂存出库
                            wmsTaskExecutionLogUP.setTaskStatus("8");
                            //存在入库任务，出库任务 暂存
                            wmsTaskExecutionLogService.updateByTaskId(wmsTaskExecutionLogUP);
                        }
                    return getReturnJsonObject("0", "存在入库任务！将出库 任务缓存");
                }
                //}
            } else {
                return getReturnJsonObject("1", "物料编码：" + goodsCode + "批次号:" + jsonObject.getString("batchCode") + "向道被锁，无法正常出库！");
            }
        } else {
            return getReturnJsonObject("1", " 未查到 物料编码：" + goodsCode + "批次号:" + jsonObject.getString("batchCode") + "存在审核通过的数据！");
        }
    }

    /**
     *功能描述: 请求-返回的数据
     * @params code 状态码  0成功，1失败
     * @param  msg 信息
     * @return com.alibaba.fastjson.JSONObject
     */
    private JSONObject getReturnJsonObject(String code,String msg) {
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("tresStat",code);
        jsonObject.put("resOutbound",msg);
        jsonObject.put("resTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        jsonObject.put("userDefined1","");
        jsonObject.put("userDefined2","");
        jsonObject.put("userDefined3","");
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
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    public ResponseResult requestWarehousing(WcsTransOb wcsTransOb){
        long taskId = 0L;
        taskId = wcsTransOb.getTaskId();
        ResponseResult responseResult;

        WmsTaskExecutionLog wmsTaskExecutionLog1 =new WmsTaskExecutionLog();
        wmsTaskExecutionLog1.setTaskStatus("2");
        List statuslist =new ArrayList();
        statuslist.add("20");
        wmsTaskExecutionLog1.setIdList(statuslist);
        log.info("查询是否存在出库任务，正在出库，");
        List<WmsTaskExecutionLog> receiptList = wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog1);

        //任务号
        if (wcsTransOb.getTaskId() == 0L) {
            //调led屏显示异常 ip地址 批次号 物料编码 母托盘码 任务id 问题表述 标题
            new LedUtils().SendProgramIn(ledIP, "", "", "","", "任务号:TaskId为空！", "LED显示内容:");
            responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "任务号:TaskId为空！", (Object)null);
            return responseResult;
        }else {
            //根据任务号，查询任务信息
            WmsTaskExecutionLog taskLog = new WmsTaskExecutionLog();
            taskLog.setTaskId(wcsTransOb.getTaskId());
            WmsTaskExecutionLog wmsTaskExecutionLog = wmsTaskExecutionLogService.queryByTask(taskLog);
            //查询是否有正在出库任务
            if(receiptList!=null && receiptList.size()>0){
                log.info("存在出库任务，正在出库，");
                wmsTaskExecutionLog1.setTaskStatus("7");
                List<WmsTaskExecutionLog>  chukuTest = wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog1);
                if(chukuTest!=null &&chukuTest.size()>0){
                    for (WmsTaskExecutionLog w :chukuTest) {
                        w.setTaskStatus("8");
                        //存在入库任务，出库任务 暂存
                        wmsTaskExecutionLogService.updateByTaskId(w);
                    }
                }
                //调LED屏幕显示
                try {
                    log.info("入库任务结束，状态为3，调LED！");
                    new LedUtils().SendProgramIn(ledIP, wmsTaskExecutionLog.getBatchNo(), wmsTaskExecutionLog.getGoodsCode(), wmsTaskExecutionLog.getPalletCode(), wmsTaskExecutionLog.getTaskId().toString(), "存在正在出库任务，请稍后再试！任务号："+wcsTransOb.getTaskId(), "入库 - LED显示内容:");
                } catch (Exception e) {
                    log.info("LED-入库调显示屏异常" + e);
                }
                log.info("------------------上报完成时调LED显示结束----------------------");
                responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "存在正在出库任务，请稍后再试！任务号："+wcsTransOb.getTaskId(), (Object)null);
                return responseResult;
            }


            if (wmsTaskExecutionLog != null) {
                String goodsCode = "", batchNo = "";
                //托盘不为空
                if (wcsTransOb.getPalletCode() != null && !wcsTransOb.getPalletCode().isEmpty()) {
                    //托盘信息-实体类
                    WmsPallet wmsPalletOb = new WmsPallet();
                    //托盘编码
                    wmsPalletOb.setPalletCode(wcsTransOb.getPalletCode());
                    //激活标记 1是 0否
                    wmsPalletOb.setActiveFlag("1");
                    //根据托盘编码、激活标记 1是 0否 查询数据。
                    List<WmsPallet> wmsPalletList = wmsPalletMapper.queryByAny(wmsPalletOb);

                    //判断成品
                    if (String.valueOf(Constant.TaskType.NH_PRODUCT_IN.getTaskType()).equals(wmsTaskExecutionLog.getTaskType())) {
                        if (!wcsTransOb.getPalletCode().equals(wmsTaskExecutionLog.getPalletCode())) {
                            //传入的托盘码 与agv请求的数据托盘码不一致
                            log.info("wcs传入的托盘码与agv请求的数据托盘码不一致！");
                            //调led屏显示异常 ip地址 批次号 物料编码 母托盘码 任务id 问题表述 标题
                            new LedUtils().SendProgramIn(ledIP, "", "", wcsTransOb.getPalletCode(),String.valueOf(wcsTransOb.getTaskId()), "托盘码不一致！", "LED显示内容:");

                            responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "wcs传入的托盘码与agv请求的数据托盘码不一致！", (Object) null);
                            return responseResult;
                        } else {
                            goodsCode = wmsTaskExecutionLog.getGoodsCode();
                            batchNo = wmsTaskExecutionLog.getBatchNo();
                        }
                    }//判断是否是原材料
                    else if (String.valueOf(Constant.TaskType.NH_PRODUCT_IN_YCL.getTaskType()).equals(wmsTaskExecutionLog.getTaskType())) {
                        //先判断商品是否已经绑定
                        if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                            WmsPallet wmsPallet = (WmsPallet) wmsPalletList.get(0);
                            //判断托盘是否物料码绑定，
                            if ((wmsPallet.getGoodsCode() != null && !wmsPallet.getGoodsCode().isEmpty())) {
                                log.info("托盘已与物料码绑定!");
                                //判断是否已经存在库位了
                                if(wmsPallet.getLocationCode()!=null && !wmsPallet.getLocationCode().isEmpty()){
                                    log.info("原材料：Fwcs传入的托盘已经在库内了！");
                                    //调led屏显示异常 ip地址 批次号 物料编码 母托盘码 任务id 问题表述 标题
                                    new LedUtils().SendProgramIn(ledIP, "", "", wcsTransOb.getPalletCode(),String.valueOf(wcsTransOb.getTaskId()), "托盘码已存在！", "LED显示内容:");

                                    responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "wcs传入的托盘已经在库内了！", (Object) null);
                                    return responseResult;
                                }else{
                                    //托盘已经绑定了物料号，未分配货位。
                                    log.info("托盘已经绑定了物料号，未分配货位。");
                                    goodsCode=wmsPallet.getGoodsCode();
                                    batchNo=wmsPallet.getBatchNo();
                                }
                            }else{
                                log.info("托盘未与物料码绑定!");
                                //托盘未绑定物料，解析wcs传入的物料号
                                //解析物料码  899030 SFUD3 000000000001
                                if (wcsTransOb.getMaterialCode() == null || wcsTransOb.getMaterialCode().isEmpty() || "0".equals(wcsTransOb.getMaterialCode())) {
                                    //物料码没扫到
                                    log.info("原材料：托盘未与物料码绑定!");
                                    responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "wcs传入的物料码异常,托盘未与物料码绑定!", (Object) null);
                                    return responseResult;
                                } else {
                                    log.info("传入的物料码：" + wcsTransOb.getMaterialCode());
                                    String[] arr = wcsTransOb.getMaterialCode().split("\\s+");
                                    goodsCode = arr[0];
                                    log.info("解析的物料码：" + goodsCode);
                                    batchNo = arr[1];
                                    log.info("解析的批次号：" + batchNo);
                                }
                            }
                        }


                    }
                    Date now = new Date();
                    String isCross = "0";
                    String locationCode = "";
                    //判断激活的托盘码是否存在。
                    if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                        WmsPallet wmsPallet = (WmsPallet) wmsPalletList.get(0);
                        //TODO 判断托盘是否绑定，未绑定，绑定数据
                        if ((wmsPallet.getGoodsCode() == null || wmsPallet.getGoodsCode().isEmpty())) {
                            wmsPallet.setGoodsCode(goodsCode);
                            wmsPallet.setBatchNo(batchNo);
                            wmsPallet.setAmount(Integer.parseInt(wmsTaskExecutionLog.getUserDefined1()));
                            wmsPallet.setUserDefined3("1");
                            log.info("更新托盘绑定商品数据");
                            wmsPalletMapper.updateBySelect(wmsPallet);
                        } else {
                            if (!wmsPallet.getGoodsCode().equals(goodsCode)) {
                                //调led屏显示异常 ip地址 批次号 物料编码 母托盘码 任务id 问题表述 标题
                                new LedUtils().SendProgramIn(ledIP, batchNo, goodsCode, wcsTransOb.getPalletCode(),String.valueOf(wcsTransOb.getTaskId()), "绑定的商品不一致！", "LED显示内容:");

                                responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "该托盘码绑定的商品不一致！", (Object) null);
                                return responseResult;
                            }
                            if (!wmsPallet.getBatchNo().equals(batchNo)) {
                                //调led屏显示异常 ip地址 批次号 物料编码 母托盘码 任务id 问题表述 标题
                                new LedUtils().SendProgramIn(ledIP, batchNo, goodsCode, wcsTransOb.getPalletCode(),String.valueOf(wcsTransOb.getTaskId()), "绑定的批次不一致！", "LED显示内容:");

                                responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "该托盘码绑定商品的批次号不一致！", (Object) null);
                                return responseResult;
                            }
                            if (wmsPallet.getAmount() != Integer.parseInt(wmsTaskExecutionLog.getUserDefined1())) {
                                //调led屏显示异常 ip地址 批次号 物料编码 母托盘码 任务id 问题表述 标题
                                new LedUtils().SendProgramIn(ledIP, batchNo, goodsCode, wcsTransOb.getPalletCode(),String.valueOf(wcsTransOb.getTaskId()), "绑定的数量不一致！", "LED显示内容:");

                                responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "该托盘码绑定商品的数量不一致！", (Object) null);
                                return responseResult;
                            }
                        }
                        //托盘是否被锁定
                        if (wmsPallet.getLockBy() != null && !"".equals(wmsPallet.getLockBy())) {
                            //任务锁定，是否是当前任务。是当前任务，则已经存在库位信息，直接返回。
                            if (Long.valueOf(wmsPallet.getLockBy()) == taskId) {
                                log.info("当前任务,已经存在库位信息");
                                Map<String, Object> rtnMap = new HashMap();
                                rtnMap.put("taskId", wmsPallet.getLockBy());
                                rtnMap.put("isCross", "0");
                                rtnMap.put("locationCode", wmsPallet.getLocationCode());
                                responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, rtnMap);
                                return responseResult;
                            } else {
                                log.info("该托盘码被锁定！");
                                //调led屏显示异常 ip地址 批次号 物料编码 母托盘码 任务id 问题表述 标题
                                new LedUtils().SendProgramIn(ledIP, batchNo, goodsCode, wcsTransOb.getPalletCode(),String.valueOf(wcsTransOb.getTaskId()), "该托盘码被锁定！", "LED显示内容:");

                                responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "该托盘码被锁定！", (Object) null);
                                return responseResult;
                            }
                        } else {//任务未被锁定
                            log.info("任务未被锁定:生成推荐货位！");
                            if ("0".equals(isCross)) {
                                long t1 = System.currentTimeMillis();
                                log.info("##################推荐库位算法#############################");
                                DifferentBusinessNHService differentBusinessNHService = this.differentBusinessNHFactory.getDifferentBusinessNHService(Constant.RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
                                wmsTaskExecutionLog.setPalletCode(wcsTransOb.getPalletCode());
                                wmsTaskExecutionLog.setGoodsCode(goodsCode);
                                wmsTaskExecutionLog.setBatchNo(batchNo);
                                // 参数：商品编码、批次号、所属库区
                                log.info("推荐库位方法：differentBusinessNHService.queryRecommendLocationCodeHB");
                                //查询推荐货位
                                Resp resp = differentBusinessNHService.queryRecommendLocationCodeHB(wmsTaskExecutionLog, wcsTransOb.getAreaCode());
                                long t2 = System.currentTimeMillis();
                                log.info("##################推荐库位算法整体耗时：" + (t2 - t1) + "ms！#########################");
                                try {
                                    if (resp.getCode() != null && Constant.RESULT.FAILED.code.equals(resp.getCode())) {
                                        responseResult = new ResponseResult(Constant.RESULT.FAILED.code, resp.getMsg(), (Object) null);
                                        return responseResult;
                                    }
                                    locationCode = resp.getData().toString();
                                    log.info("resp.getData().toString()######推荐库位是：" + locationCode + "#########################");

                                    //库位编码，目标库位
                                    wmsTaskExecutionLog.setLocationCode(locationCode);
                                    wmsTaskExecutionLog.setGoodsCode(goodsCode);
                                    wmsTaskExecutionLog.setBatchNo(batchNo);
                                    wmsTaskExecutionLog.setPalletCode(wcsTransOb.getPalletCode());
                                    //任务执行日志表创建信息，修改立库库位信息表，修改托盘信息表
                                    this.wmsTaskExecutionLogService.inStereoscopicTaskCreate(wmsTaskExecutionLog);
                                } catch (Exception var20) {
                                    log.info("获取推荐库位成功，插入入库任务、更新托盘状态失败");
                                    //修改立库信息表，库位可用
                                    differentBusinessNHService.revertLocationStatus0(locationCode);
                                    long t3 = System.currentTimeMillis();
                                    log.info("##################获取推荐库位成功，插入入库任务、更新托盘状态失耗时：" + (t3 - t2) + "ms！#########################");
                                    responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "获取推荐库位失败", (Object) null);
                                    return responseResult;
                                }
                            }
                            //调led屏显示异常 ip地址 批次号 物料编码 母托盘码 任务id 问题表述 标题
                            //new LedUtils().SendProgramIn(ledIP, batchNo, goodsCode, wcsTransOb.getPalletCode(),String.valueOf(wcsTransOb.getTaskId()), "", "LED显示内容:");

                            Map<String, Object> rtnMap = new HashMap();
                            rtnMap.put("taskId", taskId);
                            rtnMap.put("locationCode", locationCode);
                            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, rtnMap);
                            return responseResult;
                        }
                    } else {
                        //激活的托盘条码不存在
                        responseResult = new ResponseResult(Constant.RESULT.FAILED.code, Constant.RESULT.PALLET_CODE_ERROR.message, (Object) null);
                        return responseResult;
                        //托盘码不存在，重新绑定。
                    }

                } else {
                    log.info("wcs传入的托盘码为空");
                    //传入的托盘码为空
                    responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "wcs传入的托盘码为空", (Object) null);
                    return responseResult;
                }
            } else {
                log.info("wms中，不存在任务号："+wcsTransOb.getTaskId());
                //不存在任务号
                responseResult = new ResponseResult(Constant.RESULT.FAILED.code, "wms中，不存在任务号", (Object) null);
                return responseResult;
            }

        }
    }

    /**
     *功能描述: 出入库单据下发 -（AGV）用户在操作手持，出入库单据，下发到冷库四项车WMS中
     * 入库：原材料（扫码获取托盘/批次号）/成品（直接获取信息） 生成入库任务。
     *     判断是否有正在执行的出库任务，  有完成后下发/没有直接下发。
     * 出库： wcs上报出库任务完成，调agv执行下发
     *-------------------------------------------------------------------------
     * billNo	String	True	单据号，唯一。下发给小车执行
     * billType	Int	True	单据类型（1：入库，2：出库）
     * productType	Int	True	产品类别（1：原料，2：成品）
     * productCode	String	True	产品编号
     * batchNo	String	False	批次号
     * （入库：原料扫码获取，成品传入值）
     * （出库：原料传入值）
     * quantity	Int	False	数量
     * （入库：原料默认1，成品传入值）
     * （出库：原料默认1）
     * palletNo	String	Fasle	拍号
     * （入库：原料扫码获取，成品传入值）
     * （出库：原料不传入）
     * sapOrderNo	String	False	sap订单号
     * （入库：原料默认0001，成品传入值）
     * （出库：原料不传入）
     * stationCode	String	True	站点编号（入库传入起始编号，出库传入目标编号。站点可以统一定义一下）
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public ResponseResult documentDistribution(@RequestBody JSONObject jsonObject){
        Resp resp =new Resp();
        if(jsonObject!=null && jsonObject.getString("billType")!=null) {
            //入库
            if ("1".equals(jsonObject.getString("billType"))) {
                //生成入库任务
                WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
                //任务号
                wmsTaskExecutionLog.setTaskId(Long.valueOf(ConstantUtil.UUID_AGV()));
                //产品编号
                wmsTaskExecutionLog.setGoodsCode(jsonObject.getString("productCode"));
                //库口
                wmsTaskExecutionLog.setInAddress(String.valueOf(Constant.StereoscopicInfo.FLOOR_ONE_OUT_TO_ADDRESS));
                //站点
                wmsTaskExecutionLog.setUserDefined5(jsonObject.getString("stationCode"));
                //任务状态:创建
                wmsTaskExecutionLog.setTaskStatus("1");
                //sap订单号
                wmsTaskExecutionLog.setOrderNo(jsonObject.getString("sapOrderNo"));
                //数量
                wmsTaskExecutionLog.setUserDefined1(jsonObject.get("quantity").toString());
                wmsTaskExecutionLog.setWarehouseCode("NH_WAREHOUSE");
                wmsTaskExecutionLog.setAreaCode("L-NH01");
                //产品类别
                if ("1".equals(jsonObject.getString("productType"))) {
                    //原材料
                    wmsTaskExecutionLog.setTaskType(String.valueOf(Constant.TaskType.NH_PRODUCT_IN_YCL.getTaskType()));
                } else if ("2".equals(jsonObject.getString("productType"))) {
                    //成品
                    wmsTaskExecutionLog.setTaskType(String.valueOf(Constant.TaskType.PRODUCT_IN.getTaskType()));
                    //批次号
                    wmsTaskExecutionLog.setBatchNo(jsonObject.getString("batchNo"));
                    //托盘号
                    wmsTaskExecutionLog.setPalletCode(jsonObject.getString("palletNo"));

                    WmsPallet wmsPallet = new WmsPallet();
                    wmsPallet.setPalletCode(jsonObject.getString("palletNo"));
                    wmsPallet.setBatchNo(jsonObject.getString("batchNo"));
                    wmsPallet.setGoodsCode(jsonObject.getString("productCode"));
                    wmsPallet.setAmount(jsonObject.getInteger("quantity"));
                    wmsPallet.setAreaCode("L-NH01");
                    //托盘-商品绑定
                    log.info("托盘-商品绑定-开始！");
                    resp = palletCommodityBinding(wmsPallet);
                    log.info("托盘-商品绑定-结束！");
                    if (!"0".equals(resp.getCode())) {
                        log.info("托盘商品绑定异常：" + resp.getMsg());
                        return new ResponseResult(Constant.RESULT.FAILED.code, resp.getMsg(), null);
                    }
                }
                WmsTaskExecutionLog wmsTaskExecutionLogOld =new WmsTaskExecutionLog();
                wmsTaskExecutionLogOld.setPalletCode(wmsTaskExecutionLog.getPalletCode());
                wmsTaskExecutionLogOld.setGoodsCode(wmsTaskExecutionLog.getGoodsCode());
                wmsTaskExecutionLogOld.setBatchNo(wmsTaskExecutionLog.getBatchNo());
                wmsTaskExecutionLogOld.setOrderNo(wmsTaskExecutionLog.getOrderNo());

                wmsTaskExecutionLogOld.setUserDefined5(wmsTaskExecutionLog.getUserDefined5());
                List list =new ArrayList();
                list.add("1");
                if(wmsTaskExecutionLog.getPalletCode()!=null && !"".equals(wmsTaskExecutionLog.getPalletCode())){
                    list.add("2");
                }
                wmsTaskExecutionLogOld.setTaskStatusList(list);
                list=new ArrayList();
                list.add(wmsTaskExecutionLog.getTaskType());
                wmsTaskExecutionLogOld.setTaskTypeList(list);
                List<WmsTaskExecutionLog> oldList =wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLogOld);
                if(oldList==null || oldList.size()==0) {
                    //生成入库任务
                    log.info("AGV-生成入库任务-开始！");
                    wmsTaskExecutionLog.setCreateBy("AGV");
                    wmsTaskExecutionLog.setGmtCreate(new Date());
                    wmsTaskExecutionLog.setUserDefined3("1");
                    wmsTaskExecutionLog.setActiveFlag("1");
                    wmsTaskExecutionLogService.create(wmsTaskExecutionLog);
                    log.info("AGV-生成入库任务-结束！");
                }else{
                    log.info("托盘号：" + jsonObject.getString("palletNo") +"物料编码：" + jsonObject.getString("productCode") + "批次号:" + jsonObject.getString("batchNo") + "任务已存在！");
                    return new ResponseResult("1", "托盘号：" + jsonObject.getString("palletNo") +"物料编码：" + jsonObject.getString("productCode") + "批次号:" + jsonObject.getString("batchNo") + "任务已存在！", (Object) null);
                }
            }
            //出库
            else if ("2".equals(jsonObject.getString("billType"))) {

                //根据单据 等相关信息，查询任务
                WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
                //sap订单号
                wmsTaskExecutionLog.setOrderNo(jsonObject.getString("sapOrderNo"));
                //产品编号
                wmsTaskExecutionLog.setGoodsCode(jsonObject.getString("productCode"));
                //任务状态:创建
                wmsTaskExecutionLog.setTaskStatus("1");
                //批次号
                wmsTaskExecutionLog.setBatchNo(jsonObject.getString("batchNo"));
                //产品类别
                if ("1".equals(jsonObject.getString("productType"))) {
                    //原材料
                    wmsTaskExecutionLog.setUserDefined2(String.valueOf(Constant.TaskType.NH_PRODUCT_IN_YCL.getTaskType()));
                } else if ("2".equals(jsonObject.getString("productType"))) {
                    //成品
                    wmsTaskExecutionLog.setUserDefined2(String.valueOf(Constant.TaskType.PRODUCT_IN.getTaskType()));
                }
                //审核通过：0 未审核：1
                wmsTaskExecutionLog.setUserDefined3("0");
                //终点位置
                wmsTaskExecutionLog.setUserDefined5(jsonObject.getString("stationCode"));
                //根据物料号，批次号，审核通过 终点 查询数据。
                List<WmsTaskExecutionLog> wmsTaskExecutionLogList1 = wmsTaskExecutionLogMapper.queryByAny(wmsTaskExecutionLog);
                if (wmsTaskExecutionLogList1 != null && wmsTaskExecutionLogList1.size() > 0) {
                    log.info("订单号："+jsonObject.getString("sapOrderNo")+"物料编码：" + jsonObject.getString("productCode") + "批次号:" + jsonObject.getString("batchNo") + "任务已下发！");
                    return new ResponseResult("1", "物料编码：" + jsonObject.getString("productCode") + "批次号:" + jsonObject.getString("batchNo") + "任务已下发！", (Object) null);
                }else{
                    //终点位置
                    wmsTaskExecutionLog.setUserDefined5("");
                    //根据物料号，批次号，审核通过 查询数据。
                    List<WmsTaskExecutionLog> wmsTaskExecutionLogList = wmsTaskExecutionLogMapper.queryByAny(wmsTaskExecutionLog);
                    if(wmsTaskExecutionLogList != null && wmsTaskExecutionLogList.size() > 0) {
                        //将查到信息 进行排序，
                        List mixList = new ArrayList();
                        List maxList = new ArrayList();
                        for (WmsTaskExecutionLog w : wmsTaskExecutionLogList) {
                            int number = Integer.parseInt(w.getLocationCode().substring(0, 2));
                            if (number < 15) {
                                mixList.add(w);
                            } else {
                                maxList.add(w);
                            }
                        }
                        ListIterator<Integer> li = mixList.listIterator();
                        // 将游标定位到集合的结尾
                        while (li.hasNext()) {
                            li.next();
                        }
                        // 迭代器遍历hasPrevious()方法用于反向遍历的时候判断是否还有下一个元素
                        while (li.hasPrevious()) {
                            maxList.add(li.previous());
                        }
                        wmsTaskExecutionLogList = new ArrayList<>();
                        wmsTaskExecutionLogList.addAll(maxList);

                        //将要出库的数据
                        List<WmsTaskExecutionLog> list = new ArrayList<>();
                        int number = 0;
                        int shuliang=0;
                        for (int i = 0; i < wmsTaskExecutionLogList.size(); i++) {
                            if (wmsTaskExecutionLogList.get(i).getUserDefined1() != null) {
                                number = number + Integer.parseInt(wmsTaskExecutionLogList.get(i).getUserDefined1());
                            }
                            list.add(wmsTaskExecutionLogList.get(i));
                            if(jsonObject.getString("Weight")!=null){
                                shuliang=Integer.parseInt(jsonObject.getString("Weight"));
                                if (number >= shuliang) {
                                    break;
                                }
                            }else {
                                if(jsonObject.getInteger("quantity")!=0){
                                    shuliang=jsonObject.getInteger("quantity");
                                    if (number >= shuliang) {
                                        break;
                                    }
                                }
                            }
                        }
                        if (number < shuliang) {
                            return new ResponseResult("1", "可出的数量，小于请求的数量！",(Object) null);
                        }
                        //判断是否可出货
                        for (int i = 0; i < list.size(); i++) {
                            WmsLocationStereoscopic wmsLocationStereoscopic = new WmsLocationStereoscopic();
                            wmsLocationStereoscopic.setGoodsCode(list.get(i).getGoodsCode());
                            wmsLocationStereoscopic.setBatchNo(list.get(i).getBatchNo());
                            List floorNumberList = new ArrayList();
                            int num = Integer.parseInt(list.get(i).getLocationCode().substring(list.get(i).getLocationCode().length() - 2, list.get(i).getLocationCode().length()));
                            floorNumberList.add(num);
                            wmsLocationStereoscopic.setFloorNumberList(floorNumberList);
                            //库位的行
                            Integer hang = Integer.parseInt(list.get(i).getLocationCode().substring(0, 2));
                            //库位的列
                            Integer lie = Integer.parseInt(list.get(i).getLocationCode().substring(2, 4));
                            wmsLocationStereoscopic.setColumnNumber(lie);
                            //查询此列是否被锁，此列如果被锁, (再以15为界限，判断)
                            List<WmsLocationStereoscopic> logkList = this.wmsHBLocationStereoscopicMapper.getLocationorlockBy(wmsLocationStereoscopic);
                            if (logkList != null && logkList.size() > 0) {
                                for (int n = 0; n < logkList.size(); n++) {
                                    if (logkList.get(n).getColumnNumber() < 15 && hang < logkList.get(n).getColumnNumber()) {
                                        log.info("此库位：" + list.get(i).getLocationCode() + "小于15的向道，前面有库位被锁：被锁库位：" + logkList.get(n).getLocationCode());
                                        if (!list.get(i).getLocationCode().equals(logkList.get(n).getColumnNumber())) {
                                            list.remove(i);
                                        }
                                    }
                                    if (logkList.get(n).getColumnNumber() > 15 && hang > logkList.get(n).getColumnNumber()) {
                                        log.info("此库位：" + list.get(i).getLocationCode() + "大于15的向道，前面有库位被锁：被锁库位：" + logkList.get(n).getLocationCode());
                                        if (!list.get(i).getLocationCode().equals(logkList.get(n).getColumnNumber())) {
                                            list.remove(i);
                                        }
                                    }
                                }
                            }
                            //判断list中是否还有数据，有则下发wcs，出库
                            if (list != null && list.size() > 0) {

                                //判断是否有任务正在执行。如果有任务正在执行，排队，否则直接出库
                                WmsTaskExecutionLog wmsTaskExecutionLog1 =new WmsTaskExecutionLog();
                                wmsTaskExecutionLog1.setTaskStatus("2");
                                List statuslist =new ArrayList();
                                statuslist.add("10");
                                statuslist.add("50");
                                statuslist.add("20");
                                wmsTaskExecutionLog1.setIdList(statuslist);
                                List<WmsTaskExecutionLog> receiptList = wmsTaskExecutionLogService.selReceipt(wmsTaskExecutionLog1);
                                //如果为空，没有任务，则出库任务下发
                                if(receiptList==null ||receiptList.isEmpty()) {
                                   /* //TODO 业务上是否已经判断了 向道问题？
                                    WmsLocationStereoscopic  location =new WmsLocationStereoscopic();
                                    location.setFloorNumber(num);//第三位
                                    if(hang>15) {
                                        location.setLayerNumber(hang);//第一位
                                    }else{
                                        location.setLayerNumber(hang+1);//第一位
                                    }
                                    location.setColumnNumber(lie);//第二位
                                    //判断是否需要移库
                                    List<WmsLocationStereoscopic> yiKuList =wmsHBLocationStereoscopicMapper.selHuoWu(location);
                                    if(yiKuList!=null &&yiKuList.size()>0){
                                        //需要移库
                                        YiKu(yiKuList);
                                    }else {*/
                                        //出库任务号 - 组号
                                        String outboundID = jsonObject.getString("sapOrderNo");


                                        JSONObject jsonObject1 = new JSONObject();

                                        jsonObject1.put("groupId", outboundID);
                                        jsonObject1.put("msgTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                                        jsonObject1.put("priorityCode", "");
                                        jsonObject1.put("warehouse", "L-NH01");
                                        List list1 = new ArrayList();
                                        //拼接调速锐的【任务下发】接口
                                        for (int j = 0; j < list.size(); j++) {
                                            WmsTaskExecutionLog wmsTaskExecutionLogUP = list.get(i);
                                            if (jsonObject.getString("stationCode") != null && !jsonObject.getString("stationCode").isEmpty()) {
                                                wmsTaskExecutionLogUP.setUserDefined5(jsonObject.getString("stationCode"));
                                            }
                                            if (j < 3) {
                                                Map map = new HashMap();
                                                map.put("taskId", list.get(i).getTaskId().toString());
                                                //任务类型，1：出库
                                                map.put("taskType", 1);
                                                //任务起点 库位
                                                map.put("startNode", list.get(i).getLocationCode());
                                                //任务终点 //TODO 待确认终点
                                                map.put("endNode", Constant.StereoscopicInfo.FLOOR_ONE_OUT_TO_ADDRESS);
                                                //托盘码
                                                map.put("barCode", list.get(i).getPalletCode());
                                                map.put("order", i);
                                                list1.add(map);
                                                //状态
                                                wmsTaskExecutionLogUP.setTaskStatus("2");

                                                WmsPallet wmsPallet =new WmsPallet();
                                                wmsPallet.setLockBy(list.get(i).getTaskId().toString());
                                                wmsPallet.setPalletCode(list.get(j).getPalletCode());
                                                //根据托盘码锁定托盘
                                                wmsPalletMapper.updateByPalletCode(wmsPallet);
                                                WmsLocationStereoscopic wmsLocationStereoscopic1 =new WmsLocationStereoscopic();
                                                wmsLocationStereoscopic1.setLocationCode(list.get(i).getLocationCode());
                                                wmsLocationStereoscopic1.setUseStatus("2");
                                                wmsLocationStereoscopic1.setLastModifiedBy("wms");
                                                wmsLocationStereoscopic1.setGmtModified(new Date());
                                                this.wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopic1);
                                            } else {
                                                //状态
                                                wmsTaskExecutionLogUP.setTaskStatus("7");
                                            }
                                            //更新数据,将传入的数据保存一下，为的是，任务完成后可追溯
                                            wmsTaskExecutionLogService.updateByTaskId(wmsTaskExecutionLogUP);
                                        }
                                        jsonObject1.put("tasks", list1);
                                        try {
                                            //调WCS出库任务
                                            log.info("---------------出库，调用wcs的任务接收接口 : " + jsonObject1.toString());
                                            JSONObject returnJsonObject = sLWCSService.taskReceive(jsonObject1);
                                            //模拟返回
//                                            String str="{ \"returnStatus\":\"0\", \"ret\": true, \"msg\": \"操作成功\"}";
//                                            JSONObject returnJsonObject = JSONObject.parseObject(str);
                                            log.info("---------------出库，调用wcs的任务接收接口 -结束: " + returnJsonObject.toString());
                                            //接收成功
                                            if (returnJsonObject.getInteger("returnStatus") == 0) {
                                                return new ResponseResult("0", "出库任务:" + outboundID + "请求成功！", (Object) null);
                                            } else {
                                                return new ResponseResult("1", "出库任务:" + outboundID + "请求失败！" + returnJsonObject.getString("returnInfo"), (Object) null);
                                            }
                                            //return getReturnJsonObject("0", "出库任务:" + outboundID + "请求成功！");
                                        } catch (Exception e) {
                                            log.info("出库任务:订单号【" + outboundID + "】请求wcs异常！");
                                            return new ResponseResult("1", "出库任务:订单号【" + outboundID + "】请求wcs异常！", (Object) null);
                                        }
                                   // }
                                }else {
                                    //如果存在正在执行的任务，将出库信息排队。
                                    for (int j = 0; j < list.size(); j++) {
                                        WmsTaskExecutionLog wmsTaskExecutionLogUP = list.get(i);
                                        wmsTaskExecutionLogUP.setTaskStatus("7");
                                        if (jsonObject.getString("stationCode") != null && !jsonObject.getString("stationCode").isEmpty()) {
                                            wmsTaskExecutionLogUP.setUserDefined5(jsonObject.getString("stationCode"));
                                        }
                                        //更新数据,将任务划分到，待出库状态。
                                        wmsTaskExecutionLogService.updateByTaskId(wmsTaskExecutionLogUP);
                                    }
                                }
                            } else {
                                log.info("物料编码：" + jsonObject.getString("productCode") + "批次号:" + jsonObject.getString("batchNo") + "向道被锁，无法正常出库！");
                                return new ResponseResult("1", "物料编码：" + jsonObject.getString("productCode") + "批次号:" + jsonObject.getString("batchNo") + "向道被锁，无法正常出库！", (Object) null);
                            }
                        }
                    } else {
                            log.info("订单号："+jsonObject.getString("sapOrderNo")+"物料编码：" + jsonObject.getString("productCode") + "批次号:" + jsonObject.getString("batchNo") + "状态：  审核通过，未查到数据！");
                            return new ResponseResult("1", "订单号："+jsonObject.getString("sapOrderNo")+"物料编码：" + jsonObject.getString("productCode") + "批次号:" + jsonObject.getString("batchNo") + "状态：审核通过，未查到数据！", (Object) null);
                        }
                }
            }
        }
        return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, (Object)null);
    }

    /**
     *功能描述: 托盘商品绑定
     * 托盘存在，校验是否已绑定其他商品。
     * 托盘不存在，生成并绑定托盘商品。
     * UserDefined3入库托盘是否审核 0审核 1未审核
     * @params
     * @return
     */
    private Resp palletCommodityBinding( WmsPallet wmsPallet){
        Resp resp = new Resp();
        if(wmsPallet!=null && !wmsPallet.getPalletCode().isEmpty()) {
            String palletCode = wmsPallet.getPalletCode();
            String batchNo = wmsPallet.getBatchNo();
            String goodsCode = wmsPallet.getGoodsCode();
            Integer amount = wmsPallet.getAmount();
            String areaCode = wmsPallet.getAreaCode();
            WmsPallet pallet = new WmsPallet();
            pallet.setPalletCode(palletCode);

            List<WmsPallet> pallets = wmsPalletMapper.queryByAny(pallet);
            //long taskId = 0L;
            String whCode;
            if (pallets.size() > 0) {
                WmsPallet tmp = (WmsPallet) pallets.get(0);
                String tmpGoodsCode = tmp.getGoodsCode() == null ? "" : tmp.getGoodsCode();
                whCode = tmp.getBatchNo() == null ? "" : tmp.getBatchNo();
                if (!"".equals(tmpGoodsCode)) {
                    if (!tmpGoodsCode.equals(goodsCode) || !whCode.equals(batchNo)) {
                        resp.setCode("2");
                        resp.setMsg("该托盘已绑定其他商品");
                        return resp;
                    }
                    //taskId = tmp.getTaskId();
                }

                /*if (taskId == 0L) {
                    taskId = this.wmsCommonService.getTaskIds(Constant.TaskType.PRODUCT_IN, 1)[0];
                }*/

                pallet.setPalletId(((WmsPallet) pallets.get(0)).getPalletId());
                pallet.setGoodsCode(goodsCode);
                pallet.setBatchNo(batchNo);
                pallet.setAmount(amount);
                pallet.setAreaCode(areaCode);
                pallet.setLastModifiedBy("wcs");
                pallet.setGmtModified(new Date());
                //入库托盘是否审核 0审核 1未审核
                pallet.setUserDefined3("1");
                // String whCode = "";
                WmsWarehouseArea warehouseArea = new WmsWarehouseArea();
                warehouseArea.setAreaCode(areaCode);
                List<WmsWarehouseArea> areaList = this.warehouseAreaMapper.queryByAny(warehouseArea);
                if (areaList.size() > 0) {
                    whCode = ((WmsWarehouseArea) areaList.get(0)).getWarehouseCode();
                    pallet.setWarehouseCode(whCode);
                }

                this.wmsPalletMapper.updateBySelect(pallet);
            } else {
                pallet.setPalletId(CommonUtils.getUUID());
                pallet.setGoodsCode(goodsCode);
                pallet.setBatchNo(batchNo);
                pallet.setAmount(amount);
                pallet.setAreaCode(areaCode);
                pallet.setCreateBy("wcs");
                pallet.setGmtCreate(new Date());
                pallet.setActiveFlag("1");
                //入库托盘是否审核 0审核 1未审核
                pallet.setUserDefined3("1");
                /*if (taskId == 0L) {
                    taskId = this.wmsCommonService.getTaskIds(Constant.TaskType.PRODUCT_IN, 1)[0];
                }*/

                WmsWarehouseArea warehouseArea = new WmsWarehouseArea();
                warehouseArea.setAreaCode(areaCode);
                List<WmsWarehouseArea> areaList = this.warehouseAreaMapper.queryByAny(warehouseArea);
                if (areaList.size() > 0) {
                    whCode = ((WmsWarehouseArea) areaList.get(0)).getWarehouseCode();
                    pallet.setWarehouseCode(whCode);
                }

                this.wmsPalletMapper.create(pallet);
            }
        }else{
            log.info("托盘码为空！");
            resp.setCode("2");
            resp.setMsg("托盘码为空！");
            return resp;
        }
        resp.setCode("0");
        return resp;
    }
    /**
     *功能描述: wcs调wms异常请求接口
     * 根据任务号，查询，入库，是原材料还是成品
     * 原材料手动pda入库
     * 成品呼叫agv
     * @params
     * @return
     */
    @Override
    public ResponseResult abnormal(@RequestBody JSONObject jsonObject){
        if(jsonObject!=null && (jsonObject.getLong("taskId")!=0L &&!"".equals(jsonObject.getString("taskId")))){
            String palletNo="";

            //根据taskid 查询任务表
            WmsTaskExecutionLog wmsTaskExecutionLog =new WmsTaskExecutionLog();
            wmsTaskExecutionLog.setTaskId(Long.valueOf(jsonObject.getString("taskId")));
            WmsTaskExecutionLog wmsTaskExecutionLogNew = wmsTaskExecutionLogService.queryByTask(wmsTaskExecutionLog);
            if(wmsTaskExecutionLogNew!=null) {
                if (jsonObject.getString("palletNo") != null && !"".equals(jsonObject.getString("palletNo"))) {
                    palletNo = jsonObject.getString("palletNo");
                } else {
                    palletNo = wmsTaskExecutionLogNew.getPalletCode();
                }

                WmsPallet wmsPallet = new WmsPallet();
                wmsPallet.setPalletCode(palletNo);
                List wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
                if(wmsPalletList!=null && wmsPalletList.size()>0) {
                    WmsPallet pallet =(WmsPallet)wmsPalletList.get(0);
                    WmsPallet updateOb = new WmsPallet();
                    updateOb.setPalletId(pallet.getPalletId());
                    updateOb.setLockByNull("null");
                    updateOb.setLocationCodeNull("null");
                    updateOb.setChannelLocationNull("null");
                    updateOb.setGoodsCodeNull("null");
                    updateOb.setBatchNoNull("null");
                    updateOb.setAmountNull("null");
                    updateOb.setLastModifiedBy("wcs");
                    updateOb.setUserDefined3Null("null");
                    updateOb.setGmtModified(new Date());
                    this.wmsPalletMapper.updateBySelect(updateOb);
                }
                //如果不是异常呼叫agv失败，则LED显示，即正常的异常，
                if(jsonObject.getString("agvErr")==null ||"".equals(jsonObject.getString("agvErr"))) {
                    //调LED屏幕显示
                    try {
                        new LedUtils().SendProgramIn(ledIP, wmsTaskExecutionLogNew.getBatchNo(), wmsTaskExecutionLogNew.getGoodsCode(), palletNo, wmsTaskExecutionLogNew.getTaskId().toString(), "扫码失败或外形检测异常！", "入库异常 - LED显示内容:");
                    } catch (Exception e) {
                        log.info("LED-入库调显示屏异常" + e);
                    }
                }
                //AGV 成品入库异常，呼叫AGV
                if (String.valueOf(Constant.TaskType.NH_PRODUCT_IN.getTaskType()).equals(wmsTaskExecutionLogNew.getTaskType()) &&(wmsTaskExecutionLogNew.getUserDefined5()!=null &&!"".equals(wmsTaskExecutionLogNew.getUserDefined5() ))) { //
                    //如果是成品呼叫agv
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("taskId", wmsTaskExecutionLogNew.getTaskId());
                    //产品类别（1：原料，2：成品）
                    jsonObject1.put("productType", 2);
                    jsonObject1.put("startPoint", Constant.StereoscopicInfo.FLOOR_ONE_OUT_TO_ADDRESS);
                    try {
                        log.info("成品入库异常，呼叫AGV : 调agv 执行下发-开始！");
                        //TODO 调agv 执行下发
                        JSONObject jsonObject2 = wmsNHAgvService.carError(jsonObject1);
                        //模拟返回
//                        JSONObject jsonObject2 =new JSONObject();
//                        jsonObject2.put("isSuccessful","true");
                        log.info("成品入库异常，呼叫AGV : 调agv 执行下发--结束！");

                        if ("true".equals(jsonObject2.getString("isSuccessful")) ) {
                            log.info("成品入库异常:调度agv将货物取回-完成！");
                            return new ResponseResult("0", "调度agv将货物取回！", (Object) null);
                        } else {
                            log.info("成品入库异常:调度agv将货物取回-失败！");
                            wmsTaskExecutionLogNew.setTaskStatus("9");
                            wmsTaskExecutionLogNew.setErrorMsg("成品入库异常-呼叫agv失败！");
                            wmsTaskExecutionLogService.updateByTaskId(wmsTaskExecutionLogNew);
                            return new ResponseResult("1", "调度agv将货物取回-失败！", (Object) null);
                        }
                    }catch (Exception e){
                            log.info("成品入库异常:调度agv将货物取回-异常！");
                            wmsTaskExecutionLogNew.setTaskStatus("9");
                            wmsTaskExecutionLogNew.setErrorMsg("成品入库异常-呼叫agv异常！");
                            wmsTaskExecutionLogService.updateByTaskId(wmsTaskExecutionLogNew);

                            return new ResponseResult("1", "调度agv将货物取回-异常！", (Object) null);
                    }
                }
                //AGV 原材料入库异常，呼叫AGV
                if (String.valueOf(Constant.TaskType.NH_PRODUCT_IN_YCL.getTaskType()).equals(wmsTaskExecutionLogNew.getTaskType()) &&(wmsTaskExecutionLogNew.getUserDefined5()!=null &&!"".equals(wmsTaskExecutionLogNew.getUserDefined5() )) ) {//
                    //如果是成品呼叫agv
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("taskId", wmsTaskExecutionLogNew.getTaskId());
                    //产品类别（1：原料，2：成品）
                    jsonObject1.put("productType", 1);
                    jsonObject1.put("startPoint", Constant.StereoscopicInfo.FLOOR_ONE_OUT_TO_ADDRESS);
                    try {
                        log.info("原材料入库异常，呼叫AGV : 调agv 执行下发-开始！");
                        //TODO 调agv 执行下发
                        JSONObject jsonObject2 = wmsNHAgvService.carError(jsonObject1);
                        //模拟返回
//                        JSONObject jsonObject2 =new JSONObject();
//                        jsonObject2.put("isSuccessful","true");
                        log.info("原材料入库异常，呼叫AGV : 调agv 执行下发--结束！");
                        if ("true".equals(jsonObject2.getString("isSuccessful")) ) {
                            log.info("原材料入库异常：调度agv将货物取回-完成！");
                            return new ResponseResult("0", "调度agv将货物取回！", (Object) null);
                        } else {
                            log.info("原材料入库异常：调度agv将货物取回-失败！");
                            wmsTaskExecutionLogNew.setTaskStatus("9");
                            wmsTaskExecutionLogNew.setErrorMsg("原材料入库异常-呼叫agv异常！");
                            wmsTaskExecutionLogService.updateByTaskId(wmsTaskExecutionLogNew);
                            return new ResponseResult("1", "原材料入库异常：调度agv将货物取回-失败！", (Object) null);
                        }
                    }catch (Exception e){
                        log.info("原材料入库异常：调度agv将货物取回-异常！");
                        wmsTaskExecutionLogNew.setTaskStatus("9");
                        wmsTaskExecutionLogNew.setErrorMsg("原材料入库异常-呼叫agv异常！");
                        wmsTaskExecutionLogService.updateByTaskId(wmsTaskExecutionLogNew);
                        return new ResponseResult("1", "原材料入库异常：调度agv将货物取回-异常！", (Object) null);
                    }
                }
                //PDA下发的入库 异常 需要人工取走
                if(wmsTaskExecutionLogNew.getUserDefined5()==null || "".equals(wmsTaskExecutionLogNew.getUserDefined5())){
                    //任务下发成功，将任务修改为正在执行中。
                    wmsTaskExecutionLogNew.setTaskStatus("5");
                    wmsTaskExecutionLogNew.setErrorMsg("异常退出-人工处理，任务取消-结束！");
                    wmsTaskExecutionLogService.updateByTaskId(wmsTaskExecutionLogNew);
                }
            }else{
                log.info("任务表中不存在任务ID"+jsonObject.getLong("taskId"));
                try {
                    new LedUtils().SendProgramIn(ledIP, wmsTaskExecutionLogNew.getBatchNo(), wmsTaskExecutionLogNew.getGoodsCode(), palletNo, wmsTaskExecutionLogNew.getTaskId().toString(), "任务表中不存在任务ID", "入库异常 - LED显示内容:");
                } catch (Exception e) {
                    log.info("LED-入库调显示屏异常" + e);
                }
                return new ResponseResult("1","任务表中不存在任务ID"+jsonObject.getLong("taskId"),(Object) null);
            }
        }else{
            return new ResponseResult("1","wcs任务id未传入！",(Object) null);
        }
        return new ResponseResult("0","收到！",(Object) null);
    }

    /**
     *功能描述:  AGV 异常取货 安全驶离 - 上报  wms / wms更改任务状态。
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @Override
    public ResponseResult agvDriveAwaySafely(JSONObject jsonObject) {
        if(jsonObject!=null && (jsonObject.getLong("taskId")!=0L &&!"".equals(jsonObject.getString("taskId")))){
           WmsTaskExecutionLog wmsTaskExecutionLog =new WmsTaskExecutionLog();
           wmsTaskExecutionLog.setTaskId(jsonObject.getLong("taskId"));
           wmsTaskExecutionLog.setActiveFlag("1");
           List<WmsTaskExecutionLog>  taskList = wmsTaskExecutionLogMapper.queryByAny(wmsTaskExecutionLog);
           if(taskList!=null && taskList.size()>0){
               wmsTaskExecutionLog.setTaskStatus("5");
               wmsTaskExecutionLog.setErrorMsg("AGV安全驶离-任务取消");
               wmsTaskExecutionLogMapper.updateByTaskId(wmsTaskExecutionLog);
               log.info("AGV安全驶离-任务取消-状态修改成功！");
           }
        }else{
            return new ResponseResult("1","AGV安全驶离任务id未传入！",(Object) null);
        }
        return new ResponseResult("0","收到AGV安全驶离状态！",(Object) null);
    }


    /**
     *功能描述: 移库任务
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    private ResponseResult YiKu(List<WmsLocationStereoscopic> locationStereoscopicList) {
        //目标货位
        List huowei_mubiao =new ArrayList();
        Integer number = 0;
        List list =new ArrayList();
        for(WmsLocationStereoscopic wmsLocationStereoscopicList :locationStereoscopicList){
        WmsLocationStereoscopic wmsLocationStereoscopic =new WmsLocationStereoscopic();
        wmsLocationStereoscopic.setShelvesNumber(wmsLocationStereoscopicList.getShelvesNumber());
        wmsLocationStereoscopic.setGoodsCode(wmsLocationStereoscopicList.getGoodsCode());
        wmsLocationStereoscopic.setBatchNo(wmsLocationStereoscopicList.getBatchNo());
        list.add(wmsLocationStereoscopicList.getFloorNumber());
        wmsLocationStereoscopic.setFloorNumberList(list);
        //同一批次的商品，
        List<WmsLocationStereoscopic> tongpi_Location= wmsHBLocationStereoscopicMapper.getLocationCodeByUseStatus(wmsLocationStereoscopic);
        if(tongpi_Location!=null && tongpi_Location.size()>0) {
           k: for (WmsLocationStereoscopic tongpi :tongpi_Location) {
                if(wmsLocationStereoscopic.getShelvesNumber()!=tongpi.getShelvesNumber()){
                    if(tongpi.getLayerNumber()>15){
                        //查询同巷道最里面的数据
                        List<WmsLocationStereoscopic> l = this.wmsHBLocationStereoscopicMapper.getxiangdaoMax(tongpi);
                        for(WmsLocationStereoscopic ll :l){
                            huowei_mubiao.add(ll.getLocationCode());
                            number++;
                            if (number >= locationStereoscopicList.size()) {
                                break k;
                            }
                        }
                    }else {
                        huowei_mubiao.add(tongpi.getLocationCode());
                        number++;
                        if (number >= locationStereoscopicList.size()) {
                            break k;
                        }
                    }
                }
            }
        }else if(number<locationStereoscopicList.size()){
            //查询空巷道信息
            List<WmsLocationStereoscopic> kongxiangdaoList =  wmsHBLocationStereoscopicMapper.getKongXiangDao(wmsLocationStereoscopic);
            if(kongxiangdaoList!=null &&kongxiangdaoList.size()>0){
                if (kongxiangdaoList.get(0).getLayerNumber() > 15) {
                    log.info("移库，列大于15，查找此行，最里面的库位！");
                    //当y大于15的时候，从数字大的排序
                    List lisnew = this.wmsHBLocationStereoscopicMapper.getLocationInfoByFloorMax(wmsLocationStereoscopic);
                    if (lisnew != null && lisnew.size() > 0) {
                        for (int i=0 ;i<lisnew.size();i++) {
                            String locationCode = ((WmsLocationStereoscopic) lisnew.get(i)).getLocationCode();
                            huowei_mubiao.add(locationCode);
                            number++;
                            if(number<=locationStereoscopicList.size()) {
                                break;
                            }
                        }
                    }
                } else {
                    List lisnew =  wmsHBLocationStereoscopicMapper.getLocationInfoByFloor(wmsLocationStereoscopic);
                    //推荐的库位小于15 查询此行，最小的库位
                    if (lisnew != null && lisnew.size() > 0) {
                        log.info("推荐的库位，列小于15，查找此行，最里面的库位！");
                        for (int i=0 ;i<lisnew.size();i++) {
                            String locationCode = ((WmsLocationStereoscopic) lisnew.get(i)).getLocationCode();
                            huowei_mubiao.add(locationCode);
                            number++;
                            if(number<=locationStereoscopicList.size()) {
                                break;
                            }
                        }
                    }
                }
            }else{
                //查询全货位
                WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
                searchOb.setGoodsCode(wmsLocationStereoscopic.getGoodsCode());
                searchOb.setBatchNo(wmsLocationStereoscopic.getBatchNo());
                //的4个缓存位
                List<String> locationCodeList = new ArrayList<>();
                locationCodeList.add(Constant.StereoscopicInfo.YIKU_WEI_1);
                locationCodeList.add(Constant.StereoscopicInfo.YIKU_WEI_2);
                locationCodeList.add(Constant.StereoscopicInfo.YIKU_WEI_3);
                //获取每一层的固定的3个不可分配的库位编码
                searchOb.setLocationCodeList(locationCodeList);
                //-查询所有可用货位，3个缓存位除外
                List<WmsLocationStereoscopic> listRight = this.wmsHBLocationStereoscopicMapper.getLocationInfoBylocationCodeList(searchOb);
                if(listRight!=null &&listRight.size()>0) {
                    for (int i=0 ;i<listRight.size();i++) {
                        String locationCode = listRight.get(i).getLocationCode();
                        huowei_mubiao.add(locationCode);
                        number++;
                        if(number>=locationStereoscopicList.size()) {
                            break;
                        }
                    }
                }
            }
        }
            List<WmsMoveStereoscopic> WmsMoveStereoscopicList =new ArrayList();
            WmsMoveStereoscopic wmsMoveStereoscopic = new WmsMoveStereoscopic();

            if(huowei_mubiao!=null && huowei_mubiao.size()>0){
                JSONObject jsonObject = new JSONObject();

                //组号
                jsonObject.put("groupId", ConstantUtil.UUID_GroupId());
                //下发时间
                jsonObject.put("msgTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                //优先级
                jsonObject.put("priorityCode", "");
                //仓库编码
                jsonObject.put("warehouse", "L-NH01");
                ArrayList swiperList = new ArrayList();
                for(int i =0;i<huowei_mubiao.size();i++) {
                    Map map = new HashMap<>();
                    long taskId = this.wmsCommonService.getTaskIds(Constant.TaskType.NORMAL_MOVE, 1)[0];
                    //任务单号
                    map.put("taskId", taskId);
                    //任务类型
                    map.put("taskType", "2");
                    //任务起点
                    map.put("startNode", locationStereoscopicList.get(i).getLocationCode());
                    //任务终点
                    map.put("endNode", huowei_mubiao.get(i));
                    //托盘编号
                    map.put("barCode", locationStereoscopicList.get(i).getPalletCode());
                    map.put("order", 1);
                    swiperList.add(map);
                    //拼接移库实体类
                    wmsMoveStereoscopic.setTaskId(taskId);
                    wmsMoveStereoscopic.setGoodsCode(locationStereoscopicList.get(i).getGoodsCode());
                    wmsMoveStereoscopic.setPalletCode(locationStereoscopicList.get(i).getPalletCode());
                    wmsMoveStereoscopic.setFromLocationCode(locationStereoscopicList.get(i).getLocationCode());
                    wmsMoveStereoscopic.setToLocationCode(huowei_mubiao.get(i).toString());
                    wmsMoveStereoscopic.setAmount(locationStereoscopicList.get(i).getAmount());
                    wmsMoveStereoscopic.setBatchNo(locationStereoscopicList.get(i).getBatchNo());
                    wmsMoveStereoscopic.setCreateBy("wcs_move");
                    wmsMoveStereoscopic.setUserDefined1(locationStereoscopicList.get(i).getUserDefined1());
                    wmsMoveStereoscopic.setUserDefined2(locationStereoscopicList.get(i).getUserDefined2());
                    wmsMoveStereoscopic.setUserDefined3(locationStereoscopicList.get(i).getUserDefined3());
                    wmsMoveStereoscopic.setUserDefined4(locationStereoscopicList.get(i).getUserDefined4());
                    wmsMoveStereoscopic.setUserDefined5(locationStereoscopicList.get(i).getUserDefined5());
                    WmsMoveStereoscopicList.add(wmsMoveStereoscopic);
                }
                //tasks
                jsonObject.put("tasks", JSONArray.parseArray(JSONObject.toJSONString(swiperList)));

                DifferentBusinessNHService differentBusinessNHService = this.differentBusinessNHFactory.getDifferentBusinessNHService(Constant.RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
                try {
                    //调WCS出库任务
                    log.info("--------移库，调用wcs的任务接收接口 : -------" + jsonObject);
                    JSONObject returnJsonObject = sLWCSService.taskReceive(jsonObject);
                    //模拟返回
//                    JSONObject returnJsonObject = new JSONObject();
//                    returnJsonObject.put("returnStatus",0);
                    //returnJsonObject.put("returnStatus",1);
                    log.info("---------------移库，调用wcs的任务接收接口 -结束: " + returnJsonObject.toString());
                    //接收成功
                    if (returnJsonObject.getInteger("returnStatus") == 0) {
                        log.info("移库，调用wcs的任务接收接口-成功返回！");
                        if(WmsMoveStereoscopicList!=null &&WmsMoveStereoscopicList.size()>0) {
                            for (WmsMoveStereoscopic wmsMoveStereoscopicNew:WmsMoveStereoscopicList) {
                                this.wmsMoveStereoscopicService.startYkTask(wmsMoveStereoscopicNew);
                            }
                        }
                        log.info("修改当前");
                        return new ResponseResult(Constant.RESULT.SUCCESS.code, "移库任务:请求成功！", (Object) null);
                    } else {
                        log.info("同层 出库-移库， 调wcs接口失败！");
                        if(WmsMoveStereoscopicList!=null &&WmsMoveStereoscopicList.size()>0) {
                            for (WmsMoveStereoscopic wmsMoveStereoscopicNew:WmsMoveStereoscopicList) {
                                differentBusinessNHService.reverseYk(wmsMoveStereoscopicNew);
                            }
                        }

                        return new ResponseResult(Constant.RESULT.FAILED.code, "移库任务:请求失败！" + returnJsonObject.getString("returnInfo"), (Object) null);
                    }
                } catch (Exception e) {
                    log.info("同层 出库-移库， 调wcs接口异常！");
                    if(WmsMoveStereoscopicList!=null &&WmsMoveStereoscopicList.size()>0) {
                        for (WmsMoveStereoscopic wmsMoveStereoscopicNew:WmsMoveStereoscopicList) {
                            differentBusinessNHService.reverseYk(wmsMoveStereoscopicNew);
                        }
                    }
                    return new ResponseResult(Constant.RESULT.FAILED.code, "移库任务:请求失败！" + e.toString(), (Object) null);
                }
            }
        }
            return new ResponseResult(Constant.RESULT.SUCCESS.code, "移库成功！", (Object) null);
        }

}