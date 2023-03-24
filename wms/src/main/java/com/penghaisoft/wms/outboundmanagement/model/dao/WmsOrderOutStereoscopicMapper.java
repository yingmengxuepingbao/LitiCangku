//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsOrderOutStereoscopicMapper extends BaseMapper<WmsOrderOutStereoscopic> {
    Integer batchInsert(List<WmsOrderOutStereoscopic> list);

    Integer batchDelete(@Param("entity") WmsOrderOutStereoscopic t);

    Long queryByAnyCount(@Param("entity") WmsOrderOutStereoscopic t);

    Integer updateByOrderNo(@Param("entity") WmsOrderOutStereoscopic t);

    Integer updateBySelectBatch(@Param("entity") WmsOrderOutStereoscopic t);
    /**
     *功能描述: 现场添加：根据订单号查询数据
     * @params
     * @return com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic
     */
    WmsOrderOutStereoscopic queryByOrderNo(@Param("entity") WmsOrderOutStereoscopic condition);
}
