package com.penghaisoft.wcs.operation.service;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.operation.model.WcsBindingInfo;

/**
 *
* @Description 绑定服务
* @Date 2020/7/2 13:25
* @param
* @return
**/
public interface BindingService {

    /**
     * 写入绑定数据表
     * @param palletCode
     * @param batchNo
     * @param amount
     * @param goodsCode
     * @param channelId 通道id 一共5个
     * @param operator 操作人
     */
    WcsBindingInfo bindPalletParam(String palletCode, String batchNo, Short amount, String goodsCode, int channelId, String operator);

    WcsBindingInfo bindPallet(WcsBindingInfo bindInfo);

    Pager<WcsBindingInfo> findBindingInfo(int page, int rows, WcsBindingInfo wcsBindingInfo);

    /**
    * @Description 如果没绑定托盘就绑定
    * @Date 2020/7/6 16:04
    * @param bindInfo
    * @return boolean 是否做了绑定
    **/
    boolean bindPalletIfNot(WcsBindingInfo bindInfo);
}
