//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutPlane;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutPlaneDeail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsOrderOutPlaneDeailMapper extends BaseMapper<WmsOrderOutPlaneDeail> {
    Integer batchInsert(List<WmsOrderOutPlaneDeail> list);

    Integer batchDelete(@Param("entity") WmsOrderOutPlaneDeail t);

    List<WmsOrderOutPlaneDeail> selectCombine(List<WmsOrderOutPlane> planes);

    Integer deleteDetail(List<String> orderList);

    int deleteByOrderNO(String orderNo);
}
