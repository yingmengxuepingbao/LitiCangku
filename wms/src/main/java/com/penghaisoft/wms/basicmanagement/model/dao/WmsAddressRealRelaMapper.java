//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsAddressRealRela;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsAddressRealRelaMapper extends BaseMapper<WmsAddressRealRela> {
    Integer deleteByKey(@Param("entity") WmsAddressRealRela t);

    Integer batchInsert(List<WmsAddressRealRela> list);

    Integer batchDelete(@Param("entity") WmsAddressRealRela t);

    Long queryByAnyCount(@Param("entity") WmsAddressRealRela t);

    List<WmsAddressRealRela> queryOutAddress(@Param("entity") WmsAddressRealRela t);

    List<WmsAddressRealRela> queryByAddressCode(@Param("entity") WmsAddressRealRela t);

    /**
     *功能描述:  查询出库口
     * @author zhangxin
     * @date 2022/8/1
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsAddressRealRela>
     */
    List<WmsAddressRealRela> queryHBOutAddress(@Param("entity") WmsAddressRealRela t);
}
