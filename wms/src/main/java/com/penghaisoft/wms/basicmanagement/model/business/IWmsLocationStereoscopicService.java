//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import java.util.List;
import java.util.Map;

public interface IWmsLocationStereoscopicService {
    Resp create(WmsLocationStereoscopic wmsLocationStereoscopic);

    Resp delete(WmsLocationStereoscopic wmsLocationStereoscopic);

    Pager<WmsLocationStereoscopic> findListByCondition(int page, int rows, WmsLocationStereoscopic condition);

    WmsLocationStereoscopic findById(String id);

    List<WmsLocationStereoscopic> queryByAny(WmsLocationStereoscopic condition);

    WmsLocationStereoscopic getUseAbleAmount(WmsLocationStereoscopic condition);
    /**
     *功能描述: 获取商品的可用重量 项目
     * @params
     * @return com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic
     */
    WmsLocationStereoscopic getHBUseAbleAmount(WmsLocationStereoscopic condition);

    Resp update(WmsLocationStereoscopic wmsLocationStereoscopic);

    List<BaseDictItem> getlocationCodeAll();

    Resp updateByLocationCode(WmsLocationStereoscopic wmsLocationStereoscopic);

    List<WmsLocationStereoscopic> queryBatch(List<WmsLocationStereoscopic> insertList);

    Resp batchInsertLocation(List<WmsLocationStereoscopic> insertList);

    List<WmsLocationStereoscopic> queryNotEmptyLocationList();

    List<WmsLocationStereoscopic> getAllLocationList();

    WmsLocationStereoscopic findByLocationCode(String locationCode);

    Map<String, String> groupByUseStatus(WmsLocationStereoscopic cond);

    Resp forbiddenLocation(String ids);

    Resp openFlagLocation(String ids);
}
