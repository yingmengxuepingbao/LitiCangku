package com.penghaisoft.pda.storage.dao;

import com.penghaisoft.pda.storage.model.WmsStoragePlane;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WmsStoragePlaneMapper {
    int deleteByPrimaryKey(String storagePlaneId);

    int insert(WmsStoragePlane record);

    int insertSelective(WmsStoragePlane record);

    WmsStoragePlane selectByPrimaryKey(String storagePlaneId);

    int updateByPrimaryKeySelective(WmsStoragePlane record);

    int updateByPrimaryKey(WmsStoragePlane record);

    List<WmsStoragePlane> queryByAny(WmsStoragePlane storagePlane);
}