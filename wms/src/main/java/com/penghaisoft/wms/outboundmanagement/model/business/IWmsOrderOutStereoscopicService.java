//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsMoveTask;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsOutTask;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;

import java.util.List;

public interface IWmsOrderOutStereoscopicService {
    Resp create(WmsOrderOutStereoscopic wmsOrderOutStereoscopic);

    Resp delete(WmsOrderOutStereoscopic wmsOrderOutStereoscopic);

    Pager<WmsOrderOutStereoscopic> findListByCondition(int page, int rows, WmsOrderOutStereoscopic condition);

    WmsOrderOutStereoscopic findById(String id);

    Resp update(WmsOrderOutStereoscopic wmsOrderOutStereoscopic);

    Resp batchUpdate(WmsOrderOutStereoscopic wmsOrderOutStereoscopic);

    Resp batchMerge(WmsOrderOutStereoscopic wmsOrderOutStereoscopic, String outAddress, String currentUser);

    List<WmsOrderOutStereoscopic> queryByAny(WmsOrderOutStereoscopic condition);

    Resp createAndEditDeal(WmsOrderOutStereoscopic wmsOrderOutStereoscopic, String currentUser);

    Resp checkStatusAmount(WmsOrderOutStereoscopic condition);

    Resp start(WmsOrderOutStereoscopic wmsOrderOutStereoscopic);

    Resp startOrderNo(List<WmsMoveTask> wmsMoveTaskList, List<WmsOutTask> wmsOutTaskList, String orderNo, String loginName);
    /**
     *功能描述: 校验数量
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    Resp checkAmount(WmsOrderOutStereoscopic condition);
    /**
     *功能描述: 现场添加：根据订单号查询数据
     * @params
     * @return com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic
     */
    WmsOrderOutStereoscopic queryByOrderNo(WmsOrderOutStereoscopic condition);
}
