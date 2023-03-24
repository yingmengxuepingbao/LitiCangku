//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.logmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.logmanagement.model.entity.WmsCombineLog;

public interface IWmsCombineLogService {
    Resp create(WmsCombineLog wmsCombineLog);

    Pager<WmsCombineLog> findListByCondition(int page, int rows, WmsCombineLog condition);

    WmsCombineLog findById(String id);
}
