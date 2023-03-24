package com.penghaisoft.pda.temporary.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.storage.model.WmsGoods;
import com.penghaisoft.pda.storage.model.WmsOutTemporary;
import com.penghaisoft.pda.storage.service.CommonStorageService;
import com.penghaisoft.pda.storage.service.TemporaryAreaStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 装车复核
 * @Description CarConfirmController
 * @Auther zhangxu
 * @Date 2020/2/21 10:26
 **/
@Slf4j
@RestController
@RequestMapping("carconfirm")
public class CarConfirmController {

    @Autowired
    private CommonStorageService commonStorageService;

    @Autowired
    private TemporaryAreaStorageService temporaryAreaStorageService;

    /**
     * 根据商品编码查询信息
     * @param goodsCode
     * @return
     */
    @GetMapping("query/goods/{goodsCode}")
    public JSONObject queryGoods(@PathVariable("goodsCode") String goodsCode){

        JSONObject result = null;

        WmsGoods goodInfo = commonStorageService.queryGoodInfoByCode(goodsCode);
        if (null==goodInfo){

            result = CommonUtil.genErrorResult("商品数据不存在！");

        }else {
//            根据这个商品查询出库暂存区数量
            WmsOutTemporary wmsOutTemporary = temporaryAreaStorageService.getTemporaryOutInfo(goodsCode);

            if (null==wmsOutTemporary){

                result = CommonUtil.genErrorResult("发货区无此商品！");

            }else {
//                组装返回数据
                JSONObject temporaryOutGoodsInfo = new JSONObject();
                temporaryOutGoodsInfo.put("goodsName",wmsOutTemporary.getGoodsName());
                temporaryOutGoodsInfo.put("goodsCode",goodsCode);
                temporaryOutGoodsInfo.put("hasBoxCode",Integer.parseInt(goodInfo.getHasBoxCode()));
                temporaryOutGoodsInfo.put("totalAmount",wmsOutTemporary.getAmount());
                result = CommonUtil.genSucessResultWithData(temporaryOutGoodsInfo);

            }
        }
        return result;
    }

    /**
     * 提交，扣减库存，记录日志
     * @param account
     * @param param
     * @return
     */
    @PostMapping("submit")
    public JSONObject doConfirm(@RequestHeader("account") String account,@RequestBody JSONObject param){

        String orderNo = param.getString("orderNo");
        String goodsCode = param.getString("goodsCode");
//        如果有条码页面计算好了
        Integer amount = param.getInteger("amount");
        List<String> boxCodes = param.getJSONArray("boxCodes").toJavaList(String.class);

        Resp resp = temporaryAreaStorageService.temporaryOutSend(account,orderNo,goodsCode,amount,boxCodes);
        JSONObject result = CommonUtil.genResultFromResp(resp);

        return result;
    }
}
