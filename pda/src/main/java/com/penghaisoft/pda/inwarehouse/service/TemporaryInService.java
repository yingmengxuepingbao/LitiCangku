package com.penghaisoft.pda.inwarehouse.service;

import com.penghaisoft.pda.inwarehouse.model.WmsOrderCrossInDeail;
import com.penghaisoft.pda.inwarehouse.model.WmsOrderIn;

import java.util.List;

public interface TemporaryInService {

    /**
     * 提单 收货单 提取状态为1或者2的数据
     * @param orderNo
     * @return
     */
    List<WmsOrderIn> getStartOrderInList(String orderNo, String account);

    /**
     * 当前收货单订单状态应该为2
     * @param orderNo
     * @return
     */
    boolean checkSubmitOrderInStatus(String orderNo);

    /**
     * 提交收货单订单信息
     * @param orderNo
     * @param account
     * @param orderIns
     * @return
     */
    boolean submitOrderIn(String orderNo, String account, List<WmsOrderIn> orderIns);


    /**
     * 提单 提取收货越库状态为1或者2的数据
     * @param orderNo
     * @return
     */
    List<WmsOrderCrossInDeail> getStartOrderCrossDetailList(String orderNo, String account);

    /**
     * 当前收货越库订单状态应该为2
     * @param orderNo
     * @return
     */
    boolean checkSubmitOrderCrossStatus(String orderNo);

    /**
     * 提交收货越库订单信息
     * @param orderNo
     * @param account
     * @param orderCrossDetailList
     * @return
     */
    boolean submitOrderCross(String orderNo, String account, List<WmsOrderCrossInDeail> orderCrossDetailList);
}
