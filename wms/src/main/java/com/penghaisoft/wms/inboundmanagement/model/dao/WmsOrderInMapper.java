//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.inboundmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.inboundmanagement.model.entity.WmsOrderIn;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsOrderInMapper extends BaseMapper<WmsOrderIn> {
    Integer batchInsert(List<WmsOrderIn> list);

    Integer batchDelete(@Param("entity") WmsOrderIn t);
}
