//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsWarehouseArea;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsWarehouseAreaMapper extends BaseMapper<WmsWarehouseArea> {
    Integer batchInsert(List<WmsWarehouseArea> list);

    Integer batchDelete(@Param("entity") WmsWarehouseArea t);
}
