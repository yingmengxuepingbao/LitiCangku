//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.inboundmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.inboundmanagement.model.entity.WmsOrderCrossIn;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsOrderCrossInMapper extends BaseMapper<WmsOrderCrossIn> {
    Integer batchInsert(List<WmsOrderCrossIn> list);

    Integer batchDelete(@Param("entity") WmsOrderCrossIn t);
}
