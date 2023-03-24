package com.penghaisoft.wcs.operation.model.dao;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.operation.model.WcsBindingInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcsBindingInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WcsBindingInfo record);

    int insertSelective(WcsBindingInfo record);

    WcsBindingInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WcsBindingInfo record);

    int updateByPrimaryKey(WcsBindingInfo record);

    List<WcsBindingInfo> selectByCond(WcsBindingInfo record);

    List<WcsBindingInfo> queryMoveBindingInfo(WcsBindingInfo bindingInfo);

    int deleteBindInfoByIdList(@Param("idList") List<Integer> idList);

    public Long queryCount(@Param("entity") WcsBindingInfo bindingInfo);

    public List<WcsBindingInfo> queryList(@Param("page") Pager<WcsBindingInfo> page, @Param("entity") WcsBindingInfo bindingInfo);


    List<WcsBindingInfo> selectByPalletCode(@Param("palletCode") String palletCode);

    int deleteAll();
}