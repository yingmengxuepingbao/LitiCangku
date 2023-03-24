//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsMoveStereoscopic;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import java.util.List;

public interface IWmsMoveStereoscopicService {
    Resp create(WmsMoveStereoscopic wmsMoveStereoscopic);

    Resp delete(WmsMoveStereoscopic wmsMoveStereoscopic);

    Pager<WmsMoveStereoscopic> findListByCondition(int page, int rows, WmsMoveStereoscopic condition);

    WmsMoveStereoscopic findById(String id);

    Resp update(WmsMoveStereoscopic wmsMoveStereoscopic);

    List<WmsMoveStereoscopic> queryByAny(WmsMoveStereoscopic condition);

    Resp reportNormalYkTask(WmsTaskExecutionLog condition);

    Resp startYkTask(WmsMoveStereoscopic wmsMoveStereoscopic);
    /**
     *功能描述: 移库状态修改
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    Resp kuNei_YiKu(WmsTaskExecutionLog wmsTaskExecutionLog);
    //===========================现场添加接口=======================================
    /**
     *功能描述:
     * @author 原材料创建移库任务：确保只占两个巷道，
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    Resp createYkTaskList(WmsMoveStereoscopic wmsMoveStereoscopic);

}
