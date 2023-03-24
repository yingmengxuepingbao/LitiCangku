//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCrossProduct;
import java.util.List;

public interface IWmsOrderCrossProductService {
    Resp create(WmsOrderCrossProduct wmsOrderCrossProduct);

    Resp delete(WmsOrderCrossProduct wmsOrderCrossProduct);

    Pager<WmsOrderCrossProduct> findListByCondition(int page, int rows, WmsOrderCrossProduct condition);

    WmsOrderCrossProduct findById(String id);

    Resp update(WmsOrderCrossProduct wmsOrderCrossProduct);

    List<WmsOrderCrossProduct> queryByAny(WmsOrderCrossProduct condition);

    Resp start(WmsOrderCrossProduct wmsOrderCrossProduct);
}
