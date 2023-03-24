//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsLocationStereoscopicMapper extends BaseMapper<WmsLocationStereoscopic> {
    Integer batchInsert(List<WmsLocationStereoscopic> list);

    Integer batchDelete(@Param("entity") WmsLocationStereoscopic t);

    Integer updateByLocationCode(@Param("entity") WmsLocationStereoscopic t);

    Integer updateByLocationCodeBatch(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> queryShallowLocationCode(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> queryDeepLocationCode(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> queryShallowLocationCodeMix(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> queryDeepLocationCodeMix(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> queryStackerRunTaskNum(@Param("entity") WmsLocationStereoscopic t);

    WmsLocationStereoscopic getUseAbleAmount(@Param("entity") WmsLocationStereoscopic t);
    /**
     *功能描述: 获取商品的可用重量
     * @params
     * @return com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic
     */
    WmsLocationStereoscopic getHBUseAbleAmount(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> queryLocationCode1(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> queryLocationCode2(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> queryLocationCode3(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> queryLocationCode4(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> queryLocationCode5(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> queryLocationCode6(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> queryShallowMix(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> queryDeepMix(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> queryBatch(@Param("dataList") List<WmsLocationStereoscopic> dataList);

    List<WmsLocationStereoscopic> queryNotEmptyLocationList();

    List<WmsLocationStereoscopic> groupByUseStatus(@Param("entity") WmsLocationStereoscopic cond);

    Integer upActiveFlag(@Param("flag") String flag, @Param("list") List<String> list);
}
