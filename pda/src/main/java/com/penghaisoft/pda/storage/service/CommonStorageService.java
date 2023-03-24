package com.penghaisoft.pda.storage.service;

import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.storage.model.WmsGoods;
import com.penghaisoft.pda.storage.model.WmsPallet;

/**
 * 商品，托盘
 * @Description CommonStorageService
 * @Auther zhangxu
 * @Date 2020/2/19 14:37
 **/
public interface CommonStorageService {

    /**
     * 根据编码查询商品信息
     * @param goodsCode
     * @return
     */
    WmsGoods queryGoodInfoByCode(String goodsCode);

    /**
     * 查询托盘信息
     * @param palletCode
     * @return
     */
    WmsPallet queryPalletInfoByCode(String palletCode);

    /**
     * 是否是空托盘
     * @param pallet
     * @return
     */
    boolean isEmptyPallet(WmsPallet pallet);

    Resp handVirtualPalletInSubmit(String palletLocation, Integer palletAmount, String account);
}
