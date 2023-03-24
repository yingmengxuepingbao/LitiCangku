package com.penghaisoft.pda.storage.service;


import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.storage.model.WmsOrderCheckPallet;
import com.penghaisoft.pda.storage.model.WmsPallet;

import java.util.List;

public interface PlaneStorageService {

    Resp submitInbound(String palletCode, String locationCode, String areaCode, String goodsCode, String batchNo, Integer amount, String account);

    Resp submitMove(String palletCode, String fromLocationCode, String toLocationCode, String areaCode, String account, WmsPallet pallet);

    Resp scanPallet(String palletCode, String account, String areaCode, String locationCode);

    Resp scanFromPalletCode(String goodsCode, String batchNo, String account, String areaCode, String fromPalletCode);

    Resp combineSubmit(String toPalletCodeText, List<String> palletList, String account);

    Resp checkLadingBill(String orderNo, String account);

    Resp checkScanPallet(String orderNo, String palletCode, String account);

    Resp confirmPallet(String account, WmsOrderCheckPallet param);

    Resp orderSubmit(String account, String orderNo);

    Resp orderFinalSubmit(String account, String orderNo);

    Resp checkPallet(String account, WmsOrderCheckPallet param);

    Resp checkPlaneLocation(String locationCode);
}
