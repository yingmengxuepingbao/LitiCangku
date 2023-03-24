package com.penghaisoft.pda.plane.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.storage.model.WmsPallet;
import com.penghaisoft.pda.storage.service.CommonStorageService;
import com.penghaisoft.pda.storage.service.PlaneStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 平库移库任务
 * @Auther jzh
 * @Date 2020/2/20 19:33
 **/
@Slf4j
@RestController
@RequestMapping("planemove")
public class PlaneMoveController {

    @Autowired
    private CommonStorageService commonStorageService;
    @Autowired
    private PlaneStorageService planeStorageService;


    /**
     * 移库提交
     * @param account
     * @param param
     * @return
     */
    @PostMapping("submitmove")
    public JSONObject submitMove(@RequestHeader("account") String account,@RequestBody JSONObject param){
        JSONObject result = null;
        String palletCode = param.getString("palletCode");
        String fromLocationCode = param.getString("fromLocationCode");
        String toLocationCode = param.getString("toLocationCode");
        String areaCode = param.getString("areaCode");

        if(areaCode==null||"".equals(areaCode)){
            result = CommonUtil.genErrorResult("请选择库区");
            return result;
        }
        if(palletCode==null||"".equals(palletCode)){
            result = CommonUtil.genErrorResult("输入托盘号");
            return result;
        }
        if(fromLocationCode==null||"".equals(fromLocationCode)){
            result = CommonUtil.genErrorResult("输入原库位");
            return result;
        }
        if(toLocationCode==null||"".equals(toLocationCode)){
            result = CommonUtil.genErrorResult("输入目的库位");
            return result;
        }

        //库位暂时不用校验
        //todo （库位校验）
        Resp respCheck = planeStorageService.checkPlaneLocation(toLocationCode);
        if (respCheck.getCode().equals("0")){
            //不存在此库位
            result = CommonUtil.genErrorResult("目的库位不存在");
            return result;
        }
        //校验托盘
        WmsPallet pallet = commonStorageService.queryPalletInfoByCode(palletCode);
        if (null==pallet){
            result = CommonUtil.genErrorResult("托盘不存在！");
            return result;
        }
        //库位判断
        String palletLocation = pallet.getLocationCode();
        if(!palletLocation.equals(fromLocationCode)){
            result = CommonUtil.genErrorResult("当前托盘库位为"+palletLocation);
            return result;
        }
        //库区判断
        String palletAreaCode = pallet.getAreaCode();
        if (!palletAreaCode.equals(areaCode)){
            result = CommonUtil.genErrorResult("该托盘不符合当前库区！");
            return result;
        }
        //任务锁定
        String lockBy = pallet.getLockBy();
        if(!(lockBy==""||lockBy==null)){
            result = CommonUtil.genErrorResult("该托盘已被锁定！");
            return result;
        }

        //移库
        Resp resp = planeStorageService.submitMove(palletCode,fromLocationCode,toLocationCode,areaCode,account,pallet);
        if(resp.getCode()=="0"){
            result = CommonUtil.genErrorResult(resp.getMessage());
            return result;
        }
        result = CommonUtil.genSucessResult("移库成功");
        return result;
    }

}
