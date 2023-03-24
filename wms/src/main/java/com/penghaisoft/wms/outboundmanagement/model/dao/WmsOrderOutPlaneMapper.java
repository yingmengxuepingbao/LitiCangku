//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutPlane;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsOrderOutPlaneMapper extends BaseMapper<WmsOrderOutPlane> {
    Integer batchInsert(List<WmsOrderOutPlane> list);

    Integer batchDelete(@Param("entity") WmsOrderOutPlane t);

    Integer deleteMain(List<String> orderList);
}
