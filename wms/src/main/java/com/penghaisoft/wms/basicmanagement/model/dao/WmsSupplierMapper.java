//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsSupplier;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsSupplierMapper extends BaseMapper<WmsSupplier> {
    Integer batchInsert(List<WmsSupplier> list);

    Integer batchDelete(@Param("entity") WmsSupplier t);
}
