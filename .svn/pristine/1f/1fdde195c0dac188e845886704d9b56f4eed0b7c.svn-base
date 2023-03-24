//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsStoragePlane;
import java.util.List;

public interface IWmsStoragePlaneService {
    Resp create(WmsStoragePlane wmsStoragePlane);

    Resp delete(WmsStoragePlane wmsStoragePlane);

    Pager<WmsStoragePlane> findListByCondition(int page, int rows, WmsStoragePlane condition);

    WmsStoragePlane findById(String id);

    Resp update(WmsStoragePlane wmsStoragePlane);

    List<BaseDictItem> getAvailableGoods();

    List<BaseDictItem> findBatchNo(String goodsCode);

    List<WmsStoragePlane> findAvaAmount(WmsStoragePlane wmsStoragePlane);

    List<BaseDictItem> findLocation(WmsStoragePlane wmsStoragePlane);

    List<WmsStoragePlane> findAllLocation(WmsStoragePlane wmsStoragePlane);

    List<WmsStoragePlane> findExportListByCondition(WmsStoragePlane wmsStoragePlane);
}
