//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOutTemporaryBoxBarcode;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsOutTemporaryBoxBarcodeMapper extends BaseMapper<WmsOutTemporaryBoxBarcode> {
    Integer batchInsert(List<WmsOutTemporaryBoxBarcode> list);

    Integer batchDelete(@Param("entity") WmsOutTemporaryBoxBarcode t);

    Long queryByAnyCount(@Param("entity") WmsOutTemporaryBoxBarcode t);
}
