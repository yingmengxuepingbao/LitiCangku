//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheck;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsOrderCheckMapper extends BaseMapper<WmsOrderCheck> {
    Integer batchInsert(List<WmsOrderCheck> list);

    Integer batchDelete(@Param("entity") WmsOrderCheck t);

    Integer deleteByOrderNo(@Param("entity") WmsOrderCheck t);

    Long queryByAnyCount(@Param("entity") WmsOrderCheck t);

    Integer updateByOrderNo(@Param("entity") WmsOrderCheck t);

    Long queryCount1(@Param("entity") WmsOrderCheck t);
    /**
     *功能描述: 解决SQL server中，字段无效，因为该列没有包含在聚合函数或 GROUP BY 子句中，的问题
     * @params
     * @return java.lang.Long
     */
    Long queryCount_sqlserver(@Param("entity") WmsOrderCheck t);

    List<WmsOrderCheck> queryList1(@Param("page") Pager<WmsOrderCheck> page, @Param("entity") WmsOrderCheck t);
    /**
     *功能描述: 解决SQL server中，字段无效，因为该列没有包含在聚合函数或 GROUP BY 子句中，的问题
     * @params
     * @return java.lang.Long
     */
    List<WmsOrderCheck> queryListToSqlServer(@Param("page") Pager<WmsOrderCheck> page, @Param("entity") WmsOrderCheck t);
}
