//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsStoragePlane;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsStoragePlaneMapper extends BaseMapper<WmsStoragePlane> {
    Integer batchInsert(List<WmsStoragePlane> list);

    Integer batchDelete(@Param("entity") WmsStoragePlane t);

    Long queryByAnyCount(@Param("entity") WmsStoragePlane t);

    List<WmsStoragePlane> getAvailableGoods(@Param("entity") WmsStoragePlane storagePlane);

    List<WmsStoragePlane> findBatchNo(@Param("entity") WmsStoragePlane storagePlane);

    List<WmsStoragePlane> findLocation(@Param("entity") WmsStoragePlane wmsStoragePlane);

    List<WmsStoragePlane> findAllLocation(@Param("entity") WmsStoragePlane wmsStoragePlane);
}
