//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WmsPalletMapper extends BaseMapper<WmsPallet> {
    Integer batchInsert(List<WmsPallet> list);

    Integer batchDelete(@Param("entity") WmsPallet t);

    Integer deleteByPalletCode(@Param("entity") WmsPallet t);

    Long queryCountStereoscopic(@Param("entity") WmsPallet t);

    List<WmsPallet> queryListStereoscopic(@Param("page") Pager<WmsPallet> page, @Param("entity") WmsPallet t);

    Long queryCountStereoscopicHz(@Param("entity") WmsPallet t);

    List<WmsPallet> queryListStereoscopicHz(@Param("page") Pager<WmsPallet> page, @Param("entity") WmsPallet t);

    Integer updateByPalletCode(@Param("entity") WmsPallet t);

    Integer updateByBatch(List<WmsPallet> list);

    int cleanPallet(WmsPallet wmsPallet);

    Integer queryUnShelfCount();

    Integer queryCountPlane();

    String queryMinBatchNo();
}
