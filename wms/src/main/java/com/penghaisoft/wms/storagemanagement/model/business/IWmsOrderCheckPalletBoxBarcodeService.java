//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheckPalletBoxBarcode;
import java.util.List;

public interface IWmsOrderCheckPalletBoxBarcodeService {
    Resp create(WmsOrderCheckPalletBoxBarcode wmsOrderCheckPalletBoxBarcode);

    Resp delete(WmsOrderCheckPalletBoxBarcode wmsOrderCheckPalletBoxBarcode);

    Pager<WmsOrderCheckPalletBoxBarcode> findListByCondition(int page, int rows, WmsOrderCheckPalletBoxBarcode condition);

    WmsOrderCheckPalletBoxBarcode findById(String id);

    Resp update(WmsOrderCheckPalletBoxBarcode wmsOrderCheckPalletBoxBarcode);

    List<WmsOrderCheckPalletBoxBarcode> queryByAny(WmsOrderCheckPalletBoxBarcode condition);
}
