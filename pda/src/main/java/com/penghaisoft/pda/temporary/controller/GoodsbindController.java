package com.penghaisoft.pda.temporary.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.outwarehouse.service.WmsTaskExecutionLogService;
import com.penghaisoft.pda.storage.model.WmsGoods;
import com.penghaisoft.pda.storage.model.WmsPallet;
import com.penghaisoft.pda.storage.service.CommonStorageService;
import com.penghaisoft.pda.storage.service.TemporaryAreaStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品托盘绑定
 * @Description GoodsbindController
 * @Auther zhangxu
 * @Date 2020/2/19 14:33
 **/
@Slf4j
@RestController
@RequestMapping("goodsbind")
public class GoodsbindController {

    @Autowired
    private CommonStorageService commonStorageService;

    @Autowired
    private TemporaryAreaStorageService temporaryAreaStorageService;

    @Autowired
    private WmsTaskExecutionLogService wmsTaskExecutionLogService;
    /**
     * 根据商品编码查询信息
     * @param param
     * @return
     */
    @PostMapping("query/goods")
    public JSONObject queryGoods(@RequestBody JSONObject param){
        String goodsCode = param.getString("goodsCode");

        JSONObject result = null;
        WmsGoods goodInfo = commonStorageService.queryGoodInfoByCode(goodsCode);
        if (null==goodInfo){
            result = CommonUtil.genErrorResult("商品不存在！");
        }else {
            result = CommonUtil.genSucessResultWithData(goodInfo);
        }
        return result;
    }

    /**
     * 查询托盘信息
     * @param palletCode
     * @return
     */
    @GetMapping("query/pallet/{palletCode}")
    public JSONObject queryPallet(@PathVariable("palletCode") String palletCode){
        JSONObject result = null;
        WmsPallet pallet = commonStorageService.queryPalletInfoByCode(palletCode);
        if (null==pallet){
            result = CommonUtil.genErrorResult("托盘不存在！");
        }else {
            if (commonStorageService.isEmptyPallet(pallet)){
//              对于巧媳妇来说要找到他最近的出库记录中的商品号wms_task_execution_log，任务类型20/26
                WmsGoods wmsGoods = wmsTaskExecutionLogService.queryLastOutGoodsByPallet(palletCode);
                if (null!=wmsGoods){
                    result = CommonUtil.genSucessResultWithData(wmsGoods);
                }else {
                    result = CommonUtil.genSucessResult();
                }
            }else {
                result = CommonUtil.genErrorResult("托盘未解绑，不能绑定！");
            }
        }
        return result;
    }


    /**
     * 商品绑定
     * @param account
     * @param param
     * @return
     */
    @PostMapping("pallet/goods/bind")
    public JSONObject doBind(@RequestHeader("account") String account,@RequestBody JSONObject param){
        String palletCode = param.getString("palletNo");
        JSONObject result = null;
        WmsPallet pallet = commonStorageService.queryPalletInfoByCode(palletCode);
//        托盘校验
        if (null==pallet){
            result = CommonUtil.genErrorResult("托盘不存在！");
            return result;
        }

//        已绑定校验
        if (!commonStorageService.isEmptyPallet(pallet)){

            result = CommonUtil.genErrorResult("托盘未解绑，不能绑定！");
            return result;
        }

        String goodsCode = param.getString("goodsCode");
        Integer amount = param.getInteger("amount");
        String batchNo = param.getString("batchNo");
//      扣减入库暂存区库存时不能扣减成负数的校验
//        boolean flag = temporaryAreaStorageService.canMinusTemporaryInStorage(goodsCode,amount);
//        if (!flag){
//            result = CommonUtil.genErrorResult("收货数量不足！");
//            return result;
//        }

        List<String> boxCodes = param.getJSONArray("boxCodes").toJavaList(String.class);
        if (boxCodes.size()>0){
            amount = boxCodes.size();
        }
        //user3未审核
        temporaryAreaStorageService.bindPalletGoods(palletCode,goodsCode,amount,batchNo,account,boxCodes,"1");
        result = CommonUtil.genSucessResult();

        return result;
    }

}
