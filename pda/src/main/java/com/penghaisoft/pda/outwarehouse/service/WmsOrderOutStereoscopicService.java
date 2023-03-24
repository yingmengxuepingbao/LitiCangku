package com.penghaisoft.pda.outwarehouse.service;

import com.penghaisoft.pda.common.Resp;

import java.util.List;

/**
 * <p>
 * 业务接口类
 * </p>
 *
 * @author
 * @createDate
 **/
public interface WmsOrderOutStereoscopicService {
    Resp scanPalletCode(String palletCode);

    Resp allsubmit(String palletCode, String account);

    Resp splitsubmit(String palletCode, Integer realAmount, String isPalletBack, List<String> boxBarcodeList, String account);

    Resp scanBoxBarcode(String palletCode, String boxBarcode);


    /**
     * 校验箱码是否在托盘内
     * @param palletCode
     * @param boxBarcodeList
     * @return
     */
    boolean checkBarcodesBelongPallet(String palletCode, List<String> boxBarcodeList);
}
