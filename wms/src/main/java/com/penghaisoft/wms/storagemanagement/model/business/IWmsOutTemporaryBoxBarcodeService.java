//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOutTemporaryBoxBarcode;
import java.util.List;

public interface IWmsOutTemporaryBoxBarcodeService {
    Resp create(WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode);

    Resp delete(WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode);

    Pager<WmsOutTemporaryBoxBarcode> findListByCondition(int page, int rows, WmsOutTemporaryBoxBarcode condition);

    WmsOutTemporaryBoxBarcode findById(String id);

    Resp update(WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode);

    List<WmsOutTemporaryBoxBarcode> queryByAny(WmsOutTemporaryBoxBarcode condition);
}
