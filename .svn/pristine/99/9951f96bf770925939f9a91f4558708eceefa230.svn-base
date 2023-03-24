package com.penghaisoft.pda.stereoscopic.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.storage.service.CommonStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName StereoscopicVirtualPalletInController
 * @Description 虚拟托盘入库
 * @Author zhangx
 * @Date 2020/8/4 15:31
 **/
@RestController
@RequestMapping("stereoscopic/virtual/pallet")
public class StereoscopicVirtualPalletInController {

    @Autowired
    private CommonStorageService commonStorageService;

    /**
     * 托盘手工入库
     * jzh
     *
     * @param account
     * @param param
     * @return
     */
    @PostMapping("in/submit")
    public JSONObject handInSubmit(@RequestHeader("account") String account, @RequestBody JSONObject param) {
        JSONObject result = null;

//        应该是固定的1002
        String palletLocation = param.getString("palletLocation");

        Integer palletAmount = param.getInteger("palletAmount");

        if (palletLocation == null || "".equals(palletLocation)) {
            result = CommonUtil.genErrorResult("输入位置");
            return result;
        }
        if (palletAmount == null || palletAmount == 0) {
            result = CommonUtil.genErrorResult("无托盘数量");
            return result;
        }

        Resp resp = commonStorageService.handVirtualPalletInSubmit(palletLocation, palletAmount, account);
        if ("0".equals(resp.getCode())) {
            result = CommonUtil.genErrorResult(resp.getMessage());
        } else {
            result = CommonUtil.genSucessResultWithData(resp.getData());
        }
        return result;
    }
}
