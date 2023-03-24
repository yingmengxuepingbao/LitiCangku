//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsAddressTransform;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsAddressTransformMapper extends BaseMapper<WmsAddressTransform> {
    Integer deleteByRelaId(@Param("entity") WmsAddressTransform t);

    Integer batchInsert(List<WmsAddressTransform> list);

    Integer batchDelete(@Param("entity") WmsAddressTransform t);

    Long queryByAnyCount(@Param("entity") WmsAddressTransform t);
}
