package com.penghaisoft.pda.stereoscopic.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.common.*;
import com.penghaisoft.pda.outwarehouse.service.WmsTaskExecutionLogService;
import com.penghaisoft.pda.storage.model.WmsOrderCheckPallet;
import com.penghaisoft.pda.storage.service.PlaneStorageService;
import com.penghaisoft.pda.storage.service.StereoscopicCheckService;
import com.penghaisoft.pda.storage.service.WmsLocationStereoscopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description 立库盘点【除了提单、扫描托盘后的确认（需要调wcs），剩余的全部调平库的盘点接口】
 * @ClassName StereoscopicCheckController
 * @Author luot
 * @Date 2020/3/6 1:09
 **/
@Slf4j
@RestController
@RequestMapping("stereoscopicCheck")
public class StereoscopicCheckController {
    @Autowired
    private StereoscopicCheckService stereoscopicCheckService;

    @Autowired
    private PlaneStorageService planeStorageService;

    @Autowired
    private WmsLocationStereoscopicService wmsLocationStereoscopicService;

    @Autowired
    private WmsTaskExecutionLogService wmsTaskExecutionLogService;

    @Autowired
    private IWmsCommonService wmsCommonService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private KanbanUtil kanbanUtil;

    /**
     * @return
     * @Description 盘点提单
     * @Author luot
     * @Date 2020/3/5 14:41
     * @Param
     **/
    @PostMapping("ladingbill")
    public JSONObject ladingBill(@RequestHeader("account") String account, @RequestHeader("areaCode") String areaCode, @RequestBody JSONObject param) {
        JSONObject result = null;
        String orderNo = param.getString("orderNo");

        if (orderNo == null || "".equals(orderNo)) {
            result = CommonUtil.genErrorResult("请输入单号");
            return result;
        }

        //提单
        Resp resp = stereoscopicCheckService.checkLadingBill(orderNo, areaCode, account);
        if (resp.getCode() == "0") {
            result = CommonUtil.genErrorResult(resp.getMessage());
            return result;
        }
        result = CommonUtil.genSucessResultWithData(resp.getData());
        return result;
    }

    /**
     * 扫描托盘
     *
     * @param account
     * @param param
     * @return
     */
    @PostMapping("checkscanpallet")
    public JSONObject checkScanPallet(@RequestHeader("account") String account, @RequestBody JSONObject param) {
        JSONObject result = null;
        String orderNo = param.getString("orderNo");
        String palletCode = param.getString("palletCode");

        if (orderNo == null || "".equals(orderNo)) {
            result = CommonUtil.genErrorResult("无盘点单号");
            return result;
        }
        if (palletCode == null || "".equals(palletCode)) {
            result = CommonUtil.genErrorResult("请扫描托盘");
            return result;
        }

        //扫描托盘
        Resp resp = stereoscopicCheckService.checkScanPallet(orderNo, palletCode, account);
        if (resp.getCode() == "0") {
            result = CommonUtil.genErrorResult(resp.getMessage());
            return result;
        }
        result = CommonUtil.genSucessResultWithData(resp.getData());
        return result;
    }

    /**
     * @return
     * @Description 托盘确认【扫描托盘确认】
     * @Author luot
     * @Date 2020/3/5 23:16
     * @Param
     **/
    @PostMapping("confirmpallet")
    public JSONObject confirmPallet(@RequestHeader("account") String account, @RequestHeader("areaCode") String areaCode, @RequestBody WmsOrderCheckPallet param) {
        JSONObject result = null;
        String orderNo = param.getOrderNo();
        String palletCode = param.getPalletCode();
        Integer realAmount = param.getRealAmount();
        if (orderNo == null || "".equals(orderNo)) {
            result = CommonUtil.genErrorResult("无盘点单号");
            return result;
        }
        if (realAmount == null) {
            result = CommonUtil.genErrorResult("无托盘实际数量");
            return result;
        }
        if (palletCode == null || "".equals(palletCode)) {
            result = CommonUtil.genErrorResult("请扫描托盘");
            return result;
        }

        String key = Constant.ConfigInfo.INTERFACE_FOR_WCS_REDIS + palletCode;
        if (stringRedisTemplate.hasKey(key)) {
            result = CommonUtil.genErrorResult("当前托盘码正在处理中");
            return result;
        } else {
            stringRedisTemplate.opsForValue().set(key, "1", Constant.ConfigInfo.INTERFACE_FOR_WCS_REDIS_LOST_MINUTES, TimeUnit.MINUTES);//写入对象，并设置失效时间
        }

        //确认托盘
        Resp resp = new Resp();
        try{
            resp = stereoscopicCheckService.confirmPallet(account, param);
            if (resp.getCode() == "0") {

                stringRedisTemplate.delete(key);//删除对象
                result = CommonUtil.genErrorResult(resp.getMessage());
                return result;
            }
        }catch (Exception e){
            stringRedisTemplate.delete(key);//删除对象
            result = CommonUtil.genErrorResult(e.getMessage());
            return result;
        }


//        WmsOrderCheckPallet rtnOb = (WmsOrderCheckPallet) resp.getData();
//
//        String targetLocation = "";
//        resp = wmsLocationStereoscopicService.queryCheckRecommendLocationCode(palletCode);
//        if ("0".equals(resp.getCode())) {
//            stringRedisTemplate.delete(key);//删除对象
//            result = CommonUtil.genErrorResult("盘点确认成功，返库失败：" + resp.getMessage());
//            return result;
//        } else {
//            targetLocation = (String) resp.getData();
//        }
//
//        Date now = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
////        long taskId = GenerateTaskId.getTaskId(Constant.TaskType.CHECK_IN);
//        long taskId = wmsCommonService.getTaskIds(Constant.TaskType.CHECK_IN, 1)[0];
//        WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
//        wmsTaskExecutionLog.setAreaCode(areaCode);
////            任务类型 1 生产入库 2 分拣入库 3 移库 4 出库 5越库 6托盘入库
//        wmsTaskExecutionLog.setTaskType(String.valueOf(Constant.TaskType.CHECK_IN.getValue()));
//        wmsTaskExecutionLog.setPalletCode(palletCode);
////            入库口地址
//        wmsTaskExecutionLog.setInAddress("盘点口返回入库");
////            任务状态1创建2执行3完成4异常5取消6创建失败
//        wmsTaskExecutionLog.setTaskStatus("1");
//        wmsTaskExecutionLog.setGoodsCode(rtnOb.getGoodsCode());
//        wmsTaskExecutionLog.setBatchNo(rtnOb.getBatchNo());
//        wmsTaskExecutionLog.setCreateBy(account);
//        wmsTaskExecutionLog.setGmtCreate(now);
//        wmsTaskExecutionLog.setActiveFlag("1");
//        wmsTaskExecutionLog.setTaskId(taskId);
//        wmsTaskExecutionLog.setOrderNo(orderNo);
//        wmsTaskExecutionLog.setLocationCode(targetLocation);
//
//        try {
////                TODO 调用wcs接收入库指令【任务id、托盘号、入库口地址、目标库位】
////                CallWcs(taskId, palletCode, fromAddress, targetLocation);
//            //                创建入立体库的指令任务、更新托盘状态
//            wmsTaskExecutionLogService.inStereoscopicTaskCreate(wmsTaskExecutionLog);
//        } catch (Exception e) {
////                库位状态回滚成初始状态0可用
//            wmsLocationStereoscopicService.revertLocationStatus0(targetLocation);
//            result = CommonUtil.genErrorResult("盘点确认成功，发送入库任务失败！");
//            stringRedisTemplate.delete(key);//删除对象
//            return result;
//        }

        stringRedisTemplate.delete(key);//删除对象
        result = CommonUtil.genSucessResultWithData(resp.getData());
        return result;
    }
}
