package com.penghaisoft.pda.basic.dao;

import com.penghaisoft.pda.basic.model.WmsPlaneLocation;

import java.util.List;

public interface WmsPlaneLocationMapper {
    int deleteByPrimaryKey(Integer locationId);

    int insert(WmsPlaneLocation record);

    int insertSelective(WmsPlaneLocation record);

    WmsPlaneLocation selectByPrimaryKey(Integer locationId);

    int updateByPrimaryKeySelective(WmsPlaneLocation record);

    int updateByPrimaryKey(WmsPlaneLocation record);

    List<WmsPlaneLocation> selectByCode(String locationCode);
}