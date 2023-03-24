package com.penghaisoft.pda.temporary.controller;


import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.outwarehouse.service.WmsTaskExecutionLogService;
import com.penghaisoft.pda.storage.dao.WmsPalletMapper;
import com.penghaisoft.pda.storage.model.WmsPallet;
import com.penghaisoft.pda.storage.service.CommonStorageService;
import com.penghaisoft.pda.storage.service.TemporaryAreaStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品解绑功能
 * @author hym
 */
@Slf4j
@RestController
@RequestMapping("goodsUnbind")
public class GoodsUnbindController {


    @Autowired
    private CommonStorageService commonStorageService;

    @Autowired
    private TemporaryAreaStorageService temporaryAreaStorageService;

    @Autowired
    private WmsTaskExecutionLogService wmsTaskExecutionLogService;

    @Autowired
    private WmsPalletMapper wmsPalletMapper;

    @PostMapping("/unbind/goods")
    public JSONObject GoodsUnbind(@RequestBody JSONObject palletCode){
        log.info("测试解绑接口："+palletCode);
        JSONObject result = null;
        WmsPallet pallet = commonStorageService.queryPalletInfoByCode(palletCode.getString("palletCode"));
        if(pallet==null){
            result = CommonUtil.genErrorResult("托盘不存在！");
        }
        else{
            if(pallet.getLocationCode()!=null){
                result=CommonUtil.genErrorResult("货位信息已绑定，无法解绑！");
            }
            else{
                if(pallet.getGoodsCode()==null){
                    result=CommonUtil.genErrorResult("托盘未绑定货物，无效解绑！");
                }
                else{
                    wmsPalletMapper.palletUnbind(palletCode.getString("palletCode"));
                    result=CommonUtil.genErrorResult("货物解绑成功！");
                }
            }
        }
        return result;
    }
}
