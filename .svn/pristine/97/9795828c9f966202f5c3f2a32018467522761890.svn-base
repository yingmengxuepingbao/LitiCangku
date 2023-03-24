package com.penghaisoft.pda.outwarehouse.dao;

import com.penghaisoft.pda.outwarehouse.model.WmsOrderOutStereoscopicDeail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WmsOrderOutStereoscopicDeailMapper {
    int deleteByPrimaryKey(String orderOutDetailId);

    int insertSelective(WmsOrderOutStereoscopicDeail record);

    WmsOrderOutStereoscopicDeail selectByPrimaryKey(String orderOutDetailId);

    int updateByPrimaryKeySelective(WmsOrderOutStereoscopicDeail record);

    List<WmsOrderOutStereoscopicDeail> queryByAny(@Param("entity") WmsOrderOutStereoscopicDeail t);
}