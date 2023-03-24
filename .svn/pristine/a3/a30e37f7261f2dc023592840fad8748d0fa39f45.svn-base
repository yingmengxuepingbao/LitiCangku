package com.penghaisoft.pda.temporary.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.inwarehouse.model.WmsOrderCrossInDeail;
import com.penghaisoft.pda.inwarehouse.service.TemporaryInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收货越库
 * @Description OrderCrossController
 * @Auther zhangxu
 * @Date 2020/2/20 15:03
 **/
@Slf4j
@RestController
@RequestMapping("ordercross")
public class OrderCrossController {

    @Autowired
    private TemporaryInService temporaryInService;

    /**
     * 查询收货越库单
     * @param orderNo
     * @return
     */
    @GetMapping("query/{orderNo}")
    public JSONObject queryOrder(@PathVariable("orderNo") String orderNo, @RequestHeader("account") String account){
        JSONObject result = null;
        List<WmsOrderCrossInDeail> orderCrossInDeailList = temporaryInService.getStartOrderCrossDetailList(orderNo,account);
        if (null==orderCrossInDeailList || orderCrossInDeailList.size()==0){
            result = CommonUtil.genErrorResult("收货越库单不存在或已完成！");
        }else {
            result = CommonUtil.genSucessResultWithData(orderCrossInDeailList);
        }
        return result;
    }

    /**
     * 订单确认收货
     * @param orderNo
     * @param account
     * @return
     */
    @PostMapping("confirm/{orderNo}")
    public JSONObject confirmOrder(@PathVariable("orderNo") String orderNo, @RequestHeader("account") String account,@RequestBody JSONObject orderInfos){

        JSONObject result = null;
        boolean checkStatusFlag = temporaryInService.checkSubmitOrderCrossStatus(orderNo);
        if (checkStatusFlag){
            JSONArray orderInfoArray = orderInfos.getJSONArray("orderInfos");
            List<WmsOrderCrossInDeail> wmsOrderCrossInDeails = orderInfoArray.toJavaList(WmsOrderCrossInDeail.class);
            boolean flag = temporaryInService.submitOrderCross(orderNo,account,wmsOrderCrossInDeails);
            if (flag){
                result = CommonUtil.genSucessResult();
            }else {
                result = CommonUtil.genErrorResult("收货暂存区库存不足！");
            }
        }else {

            result = CommonUtil.genErrorResult("订单已完成!");
        }
        return result;
    }
}
