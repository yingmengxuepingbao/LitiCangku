//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsOutTask;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheck;
import java.util.List;

public interface IWmsOrderCheckService {
    Resp create(WmsOrderCheck wmsOrderCheck);

    Resp updateBusiness(List<WmsOutTask> wmsOutTaskList, String unDoPalletCodes, String orderNo, String loginName);

    Resp deleteByCheckId(WmsOrderCheck wmsOrderCheck);

    Resp deleteByOrderNo(WmsOrderCheck wmsOrderCheck);

    Pager<WmsOrderCheck> findListByCondition(int page, int rows, WmsOrderCheck condition);

    WmsOrderCheck findById(String id);

    Resp update(WmsOrderCheck wmsOrderCheck);

    List<WmsOrderCheck> queryByAny(WmsOrderCheck condition);
}
