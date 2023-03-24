//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutPlaneDeail;
import java.util.List;

public interface IWmsOrderOutPlaneDeailService {
    Resp create(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail);

    Resp delete(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail);

    Pager<WmsOrderOutPlaneDeail> findListByCondition(int page, int rows, WmsOrderOutPlaneDeail condition);

    WmsOrderOutPlaneDeail findById(String id);

    Resp update(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail);

    List<WmsOrderOutPlaneDeail> queryByOrderNo(String orderNo);

    Resp distribution(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail);

    Resp combineOrder(String ids);

    Resp cancel(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail);
}
