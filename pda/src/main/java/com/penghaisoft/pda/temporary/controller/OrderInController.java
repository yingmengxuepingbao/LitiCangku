package com.penghaisoft.pda.temporary.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.inwarehouse.model.WmsOrderIn;
import com.penghaisoft.pda.inwarehouse.service.TemporaryInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收货服务接口
 * 订单状态1创建2入库中3完成
 * @Description OrderInController
 * @Auther zhangxu
 * @Date 2020/2/18 10:15
 **/
@Slf4j
@RestController
@RequestMapping("orderin")
public class OrderInController {

    @Autowired
    private TemporaryInService temporaryInService;

    /**
     * 查询收货单
     * @param orderNo
     * @return
     */
    @GetMapping("query/{orderNo}")
    public JSONObject queryOrder(@PathVariable("orderNo") String orderNo, @RequestHeader("account") String account){
        JSONObject result = null;
        List<WmsOrderIn> orderInList = temporaryInService.getStartOrderInList(orderNo,account);
        if (null==orderInList || orderInList.size()==0){
            result = CommonUtil.genErrorResult("收货单不存在或已完成！");
        }else {
            result = CommonUtil.genSucessResultWithData(orderInList);
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
        boolean checkStatusFlag = temporaryInService.checkSubmitOrderInStatus(orderNo);
        if (checkStatusFlag){
            JSONArray orderInfoArray = orderInfos.getJSONArray("orderInfos");
            List<WmsOrderIn> wmsOrderIns = orderInfoArray.toJavaList(WmsOrderIn.class);
            temporaryInService.submitOrderIn(orderNo,account,wmsOrderIns);
            result = CommonUtil.genSucessResult();
        }else {

            result = CommonUtil.genErrorResult("订单已完成!");
        }
        return result;
    }

}