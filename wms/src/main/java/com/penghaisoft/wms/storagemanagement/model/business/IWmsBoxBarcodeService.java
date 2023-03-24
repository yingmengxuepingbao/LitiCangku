//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsBoxBarcode;
import java.util.List;

public interface IWmsBoxBarcodeService {
    Resp create(WmsBoxBarcode wmsBoxBarcode);

    Resp delete(WmsBoxBarcode wmsBoxBarcode);

    Pager<WmsBoxBarcode> findListByCondition(int page, int rows, WmsBoxBarcode condition);

    WmsBoxBarcode findById(String id);

    Resp update(WmsBoxBarcode wmsBoxBarcode);

    List<WmsBoxBarcode> queryByAny(WmsBoxBarcode condition);
}
