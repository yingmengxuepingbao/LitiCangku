//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business;

import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsMoveTask;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsOutTask;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsMoveStereoscopic;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheck;
import java.util.List;

public interface IDifferentBusinessService {
    Resp queryRecommendLocationCode(String goodsCode, String batchNo, String areaCode);

    Resp querySortingRecommendLocationCode(String goodsCode, String batchNo, String areaCode);

    Resp revertLocationStatus0(String locationCode);

    Resp queryOutRecommendLocationCode(String goodsCode, String batchNo, Integer planAmount, String targetAddress, String loginName);

    Resp queryVirtualPalletOut(String goodsCode, String batchNo, Integer planAmount, String targetAddress, String loginName);

    Resp revertOut(List<WmsMoveTask> wmsMoveTaskList, List<WmsOutTask> wmsOutTaskList, String orderNo);

    Resp palletOutLocationCheck(String locationCode, String loginName, String targetAddress);

    Resp startPalletOut(List<WmsOutTask> wmsOutTaskList, String loginName);

    Resp lockCheckLocationCode(WmsOrderCheck wmsOrderCheck);

    Resp createOrEditYk(WmsMoveStereoscopic wmsMoveStereoscopic);

    Resp locationLockYk(WmsMoveStereoscopic wmsMoveStereoscopic);

    Resp reverseYk(WmsMoveStereoscopic wmsMoveStereoscopic);
}
