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
 * 平库入库任务
 * @Auther jzh
 * @Date 2020/2/20 19:33
 **/
@Slf4j
@RestController
@RequestMapping("planein")
public class PlaneInController {

    @Autowired
    private CommonStorageService commonStorageService;
    @Autowired
    private PlaneStorageService planeStorageService;


    /**
     * 入库提交
     * @param account
     * @param param
     * @return
     */
    @PostMapping("submitinboud")
    public JSONObject submitInbound(@RequestHeader("account") String account,@RequestBody JSONObject param){
        JSONObject result = null;
        String palletCode = param.getString("palletNo");
        String locationCode = param.getString("locationCode");
        String areaCode = param.getString("areaCode");
        //todo （库位校验）
        Resp respCheck = planeStorageService.checkPlaneLocation(locationCode);
        if (respCheck.getCode().equals("0")){
            //不存在此库位
            result = CommonUtil.genErrorResult(respCheck.getMessage());
            return result;
        }
        //校验托盘
        WmsPallet pallet = commonStorageService.queryPalletInfoByCode(palletCode);
        if (null==pallet){
                    result = CommonUtil.genErrorResult("托盘不存在！");
                    return result;
        }else {
            if (commonStorageService.isEmptyPallet(pallet)) {
                result = CommonUtil.genErrorResult("托盘没有绑定商品。");
                return result;
            }
        }
        String palletLocation = pallet.getLocationCode();
        if(palletLocation!=null&&!"".equals(palletLocation)){
            result = CommonUtil.genErrorResult("当前托盘已经入库");
            return result;
        }

        String goodsCode = pallet.getGoodsCode();
        String batchNo = pallet.getBatchNo();
        Integer amount = pallet.getAmount();
        //增加库存，修改库区，库位
        Resp resp = planeStorageService.submitInbound( palletCode, locationCode, areaCode ,goodsCode, batchNo ,amount ,account);
        if(resp.getCode()=="1"){
            result = CommonUtil.genErrorResult(resp.getMessage());
            return result;
        }
        result = CommonUtil.genSucessResult("入库成功");
        return result;
    }

}