//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsPlatform;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsPlatformMapper extends BaseMapper<WmsPlatform> {
    Integer batchInsert(List<WmsPlatform> list);

    Integer batchDelete(@Param("entity") WmsPlatform t);
}
