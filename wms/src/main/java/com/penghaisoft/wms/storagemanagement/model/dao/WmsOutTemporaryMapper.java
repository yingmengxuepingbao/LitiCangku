//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOutTemporary;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WmsOutTemporaryMapper extends BaseMapper<WmsOutTemporary> {
    Integer batchInsert(List<WmsOutTemporary> list);

    Integer batchDelete(@Param("entity") WmsOutTemporary t);

    Long queryByAnyCount(@Param("entity") WmsOutTemporary t);

    Integer updateByGoodsCode(@Param("entity") WmsOutTemporary t);

    Integer querySumGoodsCount();
}
