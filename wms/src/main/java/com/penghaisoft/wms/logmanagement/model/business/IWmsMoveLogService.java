//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.logmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.logmanagement.model.entity.WmsMoveLog;

public interface IWmsMoveLogService {
    Resp create(WmsMoveLog wmsMoveLog);

    Pager<WmsMoveLog> findListByCondition(int page, int rows, WmsMoveLog condition);

    WmsMoveLog findById(String id);
}
