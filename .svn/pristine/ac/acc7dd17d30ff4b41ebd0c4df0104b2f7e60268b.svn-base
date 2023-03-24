package com.penghaisoft.pda.storage.dao;

import com.penghaisoft.pda.storage.model.WmsLocationStereoscopic;

import java.util.List;

public interface WmsLocationStereoscopicMapper {
    int deleteByPrimaryKey(String locationId);

    int insertSelective(WmsLocationStereoscopic record);

    WmsLocationStereoscopic selectByPrimaryKey(String locationId);

    WmsLocationStereoscopic selectByLocationCode(String locationCode);

    int updateByPrimaryKeySelective(WmsLocationStereoscopic record);

    Integer updateByLocationCode(WmsLocationStereoscopic record);

    List<WmsLocationStereoscopic> getAllLocationInfo();
}