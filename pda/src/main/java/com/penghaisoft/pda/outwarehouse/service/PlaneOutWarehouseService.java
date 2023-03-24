package com.penghaisoft.pda.outwarehouse.service;


import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.outwarehouse.model.WmsOrderOutPlaneDeailDTO;

import java.util.List;

public interface PlaneOutWarehouseService {

 Resp lading(String orderNo, String account, String areaCode);

    Resp scanPalletNo(String palletNo, String account, String areaCode, String goodsCode, String batchNo, String locationCode);

    Resp scanBarcode(String palletCode, String boxBarcode);

    Resp confirmOrderOut(String orderNo, List<WmsOrderOutPlaneDeailDTO> wmsOrderInfos, String account);
}
