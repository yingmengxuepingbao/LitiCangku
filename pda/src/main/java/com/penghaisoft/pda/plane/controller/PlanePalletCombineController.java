package com.penghaisoft.pda.plane.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.storage.service.CommonStorageService;
import com.penghaisoft.pda.storage.service.PlaneStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 平库托盘合并任务
 * @Auther jzh
 * @Date 2020/2/20 19:33
 **/
@Slf4j
@RestController
@RequestMapping("palletcombine")
public class PlanePalletCombineController {

    @Autowired
    private CommonStorageService commonStorageService;
    @Autowired
    private PlaneStorageService planeStorageService;


    /**
     * 扫描托盘
     * jzh
     * @param account
     * @param param
     * @return
     */
    @PostMapping("scanpallet")
    public JSONObject scanPalletNo(@RequestHeader("account") String account,@RequestBody JSONObject param){
        JSONObject result = null;
        //获取单号
        String palletCode = param.getString("palletCode");
        String areaCode = param.getString("areaCode");
        String locationCode = param.getString("locationCode");
        if(areaCode==null||"".equals(areaCode)){
            result = CommonUtil.genErrorResult("请选择库区");
            return result;
        }
        if(palletCode==null||"".equals(palletCode)){
            result = CommonUtil.genErrorResult("输入目标托盘号");
            return result;
        }

        if(locationCode==null||"".equals(locationCode)){
            result = CommonUtil.genErrorResult("输入目标库位");
            return result;
        }
        //判断出库单状态 （获取出库明细）
        Resp resp = planeStorageService.scanPallet(palletCode,account,areaCode,locationCode);
        if ("0".equals(resp.getCode())){
            result = CommonUtil.genErrorResult(resp.getMessage());
        }else {
            result = CommonUtil.genSucessResultWithData(resp.getData());
        }
        return result;
    }


    /**
     * 扫描待合并托盘校验
     * @param account
     * @param param
     * @return
     */
    @PostMapping("scanfrompalletcode")
    public JSONObject scanFromPalletCode(@RequestHeader("account") String account,@RequestBody JSONObject param){
        JSONObject result = null;
        //获取
        String goodsCode = param.getString("goodsCode");
        String batchNo = param.getString("batchNo");
        String fromPalletCode = param.getString("fromPalletCode");
        String areaCode = param.getString("areaCode");

        if(goodsCode==null||"".equals(goodsCode)){
            result = CommonUtil.genErrorResult("无目标托盘信息");
            return result;
        }
        if(batchNo==null||"".equals(batchNo)){
            result = CommonUtil.genErrorResult("无目标托盘信息");
            return result;
        }
        if(fromPalletCode==null||"".equals(fromPalletCode)){
            result = CommonUtil.genErrorResult("请输入待合并托盘");
            return result;
        }

        //判断出库单状态 （获取出库明细）
        Resp resp = planeStorageService.scanFromPalletCode(goodsCode,batchNo,account,areaCode,fromPalletCode);
        if ("0".equals(resp.getCode())){
            result = CommonUtil.genErrorResult(resp.getMessage());
        }else {
            result = CommonUtil.genSucessResultWithData(resp.getData());
        }
        return result;
    }

    /**
     * 合并托盘确认
     * jzh
     * @param toPalletCodeText
     * @param account
     * @param palletList
     * @return
     */
    @PostMapping("submit/{toPalletCodeText}")
    public JSONObject combineSubmit(@PathVariable("toPalletCodeText") String toPalletCodeText,@RequestHeader("account") String account,@RequestBody List<String> palletList){
        JSONObject result = null;

//        JSONArray palletArray = param.getJSONArray("palletList");
//        List<String> palletLists = palletArray.toJavaList(String.class);
//        JSONArray array = JSON.parseArray(param.toJSONString());
//        List<String> list = JSON.parseObject(param.toString(), List.class);
        if(toPalletCodeText==null||"".equals(toPalletCodeText)){
           result = CommonUtil.genErrorResult("无目标托盘信息");
           return result;
        }
        if (palletList.size()<=0){
            result = CommonUtil.genErrorResult("无待合并托盘");
            return result;
        }

        Resp resp = planeStorageService.combineSubmit(toPalletCodeText,palletList,account);
        if ("0".equals(resp.getCode())){
            result = CommonUtil.genErrorResult(resp.getMessage());
        }else {
            result = CommonUtil.genSucessResultWithData(resp.getData());
        }
        return result;
    }


}