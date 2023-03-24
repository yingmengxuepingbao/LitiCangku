//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCrossProductDeail;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import java.util.List;

public interface IWmsOrderCrossProductDeailService {
    Resp create(WmsOrderCrossProductDeail wmsOrderCrossProductDeail);

    Resp delete(WmsOrderCrossProductDeail wmsOrderCrossProductDeail);

    Pager<WmsOrderCrossProductDeail> findListByCondition(int page, int rows, WmsOrderCrossProductDeail condition);

    WmsOrderCrossProductDeail findById(String id);

    Resp update(WmsOrderCrossProductDeail wmsOrderCrossProductDeail);

    List<WmsOrderCrossProductDeail> queryByAny(WmsOrderCrossProductDeail condition);

    List<WmsOrderCrossProductDeail> queryCrossOpenList(WmsOrderCrossProductDeail condition);

    Resp dealCrossOpen(WmsOrderCrossProductDeail wmsOrderCrossProductDeail, WmsTaskExecutionLog wmsTaskExecutionLog);

    List<WmsOrderCrossProductDeail> queryByOrderNo(String orderNo);
}
