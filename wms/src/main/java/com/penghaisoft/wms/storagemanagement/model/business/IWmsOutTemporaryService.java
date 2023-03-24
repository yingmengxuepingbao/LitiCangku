//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOutTemporary;
import java.util.List;

public interface IWmsOutTemporaryService {
    Resp create(WmsOutTemporary wmsOutTemporary);

    Resp delete(WmsOutTemporary wmsOutTemporary);

    Pager<WmsOutTemporary> findListByCondition(int page, int rows, WmsOutTemporary condition);

    WmsOutTemporary findById(String id);

    Resp update(WmsOutTemporary wmsOutTemporary);

    List<WmsOutTemporary> findExportListByCondition(WmsOutTemporary outTemporary);
}
