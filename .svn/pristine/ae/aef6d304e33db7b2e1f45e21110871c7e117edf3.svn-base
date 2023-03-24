package com.penghaisoft.pda.plane.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.storage.model.WmsOrderCheckPallet;
import com.penghaisoft.pda.storage.service.CommonStorageService;
import com.penghaisoft.pda.storage.service.PlaneStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 平库盘点任务
 * @Auther jzh
 * @Date 2020/3/3 19:33
 **/
@Slf4j
@RestController
@RequestMapping("planecheck")
public class PlaneCheckController {

    @Autowired
    private CommonStorageService commonStorageService;
    @Autowired
    private PlaneStorageService planeStorageService;


    /**
     * 盘点提单
     * @param account
     * @param param
     * @return
     */
    @PostMapping("ladingbill")
    public JSONObject ladingBill(@RequestHeader("account") String account,@RequestBody JSONObject param){
        JSONObject result = null;
        String orderNo = param.getString("orderNo");

        if(orderNo==null||"".equals(orderNo)){
            result = CommonUtil.genErrorResult("请输入单号");
            return result;
        }

        //提单
        Resp resp = planeStorageService.checkLadingBill(orderNo,account);
        if(resp.getCode()=="0"){
            result = CommonUtil.genErrorResult(resp.getMessage());
            return result;
        }
        result = CommonUtil.genSucessResultWithData(resp.getData());
        return result;
    }

    /**
     * 扫描托盘
     * @param account
     * @param param
     * @return
     */
    @PostMapping("checkscanpallet")
    public JSONObject checkScanPallet(@RequestHeader("account") String account,@RequestBody JSONObject param){
        JSONObject result = null;
        String orderNo = param.getString("orderNo");
        String palletCode = param.getString("palletCode");

        if(orderNo==null||"".equals(orderNo)){
            result = CommonUtil.genErrorResult("无盘点单号");
            return result;
        }
        if(palletCode==null||"".equals(palletCode)){
            result = CommonUtil.genErrorResult("请扫描托盘");
            return result;
        }

        //扫描托盘
        Resp resp = planeStorageService.checkScanPallet(orderNo,palletCode,account);
        if(resp.getCode()=="0"){
            result = CommonUtil.genErrorResult(resp.getMessage());
            return result;
        }
        result = CommonUtil.genSucessResultWithData(resp.getData());
        return result;
    }

    /**
     * 托盘校验
     * @param account
     * @param param
     * @return
     */
    @PostMapping("checkpallet")
    public JSONObject checkPallet(@RequestHeader("account") String account,@RequestBody WmsOrderCheckPallet param){
        JSONObject result = null;
        String orderNo = param.getOrderNo();
        String palletCode = param.getPalletCode();
        Integer realAmount = param.getRealAmount();
        if(orderNo==null||"".equals(orderNo)){
            result = CommonUtil.genErrorResult("无盘点单号");
            return result;
        }
        if(realAmount==null){
            result = CommonUtil.genErrorResult("无托盘实际数量");
            return result;
        }
        if(palletCode==null||"".equals(palletCode)){
            result = CommonUtil.genErrorResult("请扫描托盘");
            return result;
        }
        //确认托盘
        Resp resp = planeStorageService.checkPallet(account,param);
        if(resp.getCode()=="0"){
            result = CommonUtil.genErrorResult(resp.getMessage());
            return result;
        }
        result = CommonUtil.genSucessResultWithData(resp.getData());
        return result;
    }
    /**
     * 托盘确认
     * @param account
     * @param param
     * @return
     */
    @PostMapping("confirmpallet")
    public JSONObject confirmPallet(@RequestHeader("account") String account,@RequestBody WmsOrderCheckPallet param){
        JSONObject result = null;
        String orderNo = param.getOrderNo();
        String palletCode = param.getPalletCode();
        Integer realAmount = param.getRealAmount();
        if(orderNo==null||"".equals(orderNo)){
            result = CommonUtil.genErrorResult("无盘点单号");
            return result;
        }
        if(realAmount==null){
            result = CommonUtil.genErrorResult("无托盘实际数量");
            return result;
        }
        if(palletCode==null||"".equals(palletCode)){
            result = CommonUtil.genErrorResult("请扫描托盘");
            return result;
        }
        //确认托盘
        Resp resp = planeStorageService.confirmPallet(account,param);
        if(resp.getCode()=="0"){
            result = CommonUtil.genErrorResult(resp.getMessage());
            return result;
        }
        result = CommonUtil.genSucessResultWithData(resp.getData());
        return result;
    }

    /**
     * 盘点确认校验
     * @param account
     * @param param
     * @return
     */
    @PostMapping("order/submit")
    public JSONObject orderSubmit(@RequestHeader("account") String account,@RequestBody JSONObject param){
        JSONObject result = null;
        String orderNo = param.getString("orderNo");

        if(orderNo==null||"".equals(orderNo)){
            result = CommonUtil.genErrorResult("无盘点单号");
            return result;
        }
        //盘点确认校验
        Resp resp = planeStorageService.orderSubmit(account,orderNo);
        if(resp.getCode()=="0"){
            result = CommonUtil.genErrorResult(resp.getMessage());
            return result;
        }
        result = CommonUtil.genSucessResultWithData(resp.getData());
        return result;
    }

    /**
     * 盘点最终确认
     * @param account
     * @param param
     * @return
     */
    @PostMapping("order/finalsubmit")
    public JSONObject orderFinalSubmit(@RequestHeader("account") String account,@RequestBody JSONObject param){
        JSONObject result = null;
        String orderNo = param.getString("orderNo");

        if(orderNo==null||"".equals(orderNo)){
            result = CommonUtil.genErrorResult("无盘点单号");
            return result;
        }
        //盘点确认校验
        Resp resp = planeStorageService.orderFinalSubmit(account,orderNo);
        if(resp.getCode()=="0"){
            result = CommonUtil.genErrorResult(resp.getMessage());
            return result;
        }
        result = CommonUtil.genSucessResultWithData(resp.getData());
        return result;
    }

}
