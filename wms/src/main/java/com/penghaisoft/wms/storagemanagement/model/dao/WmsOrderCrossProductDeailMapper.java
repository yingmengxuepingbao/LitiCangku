//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCrossProductDeail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsOrderCrossProductDeailMapper extends BaseMapper<WmsOrderCrossProductDeail> {
    Integer batchInsert(List<WmsOrderCrossProductDeail> list);

    Integer batchDelete(@Param("entity") WmsOrderCrossProductDeail t);

    Long queryByAnyCount(@Param("entity") WmsOrderCrossProductDeail t);

    Integer updateByKey(@Param("entity") WmsOrderCrossProductDeail t);

    List<WmsOrderCrossProductDeail> queryCrossOpenList(@Param("entity") WmsOrderCrossProductDeail t);

    void deleteReally(@Param("entity") WmsOrderCrossProductDeail wmsOrderCrossProductDeail);
}
