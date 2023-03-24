package com.penghaisoft.pda.storage.service;

import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.storage.model.WmsOutTemporary;

import java.util.List;

/**
 * 暂存区库存服务
 */
public interface TemporaryAreaStorageService {

    /**
     *  变化入库暂存区商品数量
     * @param goodsCode
     * @param amount
     * @param account
     */
    void addTemporaryInStorage(String goodsCode, Integer amount, String account);

    /**
     * 托盘商品绑定，这是在收货暂存区绑定的
     * @param palletCode
     * @param goodsCode
     * @param amount
     * @param batchNo
     * @param account
     * @param boxCodes
     */
    void bindPalletGoods(String palletCode, String goodsCode, Integer amount, String batchNo, String account, List<String> boxCodes,String user3);


    /**
     * 根据商品号查询出库缓存区信息
     * @param goodsCode
     * @return
     */
    WmsOutTemporary getTemporaryOutInfo(String goodsCode);


    /**
     * 发货暂存区发货
     * @param account
     * @param orderNo
     * @param goodsCode
     * @param amount
     * @param boxCodes
     * @return
     */
    Resp temporaryOutSend(String account, String orderNo, String goodsCode, Integer amount, List<String> boxCodes);


    /**
     * 能否扣减入库暂存区库存（看库存够不够）
     * @param goodsCode
     * @param amount
     * @return
     */
    boolean canMinusTemporaryInStorage(String goodsCode, Integer amount);
}
