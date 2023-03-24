//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCrossProduct;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsOrderCrossProductMapper extends BaseMapper<WmsOrderCrossProduct> {
    Integer batchInsert(List<WmsOrderCrossProduct> list);

    Integer batchDelete(@Param("entity") WmsOrderCrossProduct t);

    Long queryByAnyCount(@Param("entity") WmsOrderCrossProduct t);

    Integer updateByOrderNo(@Param("entity") WmsOrderCrossProduct t);

    void deleteReally(@Param("entity") WmsOrderCrossProduct wmsOrderCrossProduct);
}
