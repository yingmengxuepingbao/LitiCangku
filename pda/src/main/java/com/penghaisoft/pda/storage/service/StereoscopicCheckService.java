package com.penghaisoft.pda.storage.service;


import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.storage.model.WmsOrderCheckPallet;

public interface StereoscopicCheckService {
    Resp checkLadingBill(String orderNo, String areaCode, String account);

    Resp checkScanPallet(String orderNo, String palletCode, String account);

    public Resp confirmPallet(String account, WmsOrderCheckPallet param);
}
