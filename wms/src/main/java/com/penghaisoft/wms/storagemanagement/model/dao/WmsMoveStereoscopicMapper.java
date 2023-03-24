//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsMoveStereoscopic;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsMoveStereoscopicMapper extends BaseMapper<WmsMoveStereoscopic> {
    Integer batchInsert(List<WmsMoveStereoscopic> list);

    Integer batchDelete(@Param("entity") WmsMoveStereoscopic t);

    Long queryByAnyCount(@Param("entity") WmsMoveStereoscopic t);
    /**
     *功能描述:  逻辑删除,根据 移库单号 设置active_flag ='0'
     * @params
     * @return java.lang.Integer
     */
    public Integer updateByMoveNo(@Param("entity") WmsMoveStereoscopic t);

}
