//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.inboundmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.inboundmanagement.model.entity.WmsOrderCrossInDeail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsOrderCrossInDeailMapper extends BaseMapper<WmsOrderCrossInDeail> {
    Integer batchInsert(List<WmsOrderCrossInDeail> list);

    Integer batchDelete(@Param("entity") WmsOrderCrossInDeail t);

    int deleteByOrderNO(String orderNo);
}
