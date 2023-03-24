package com.penghaisoft.pda.outwarehouse.dao;

import com.penghaisoft.pda.outwarehouse.model.WmsOrderOutPlane;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WmsOrderOutPlaneMapper {
    int deleteByPrimaryKey(String orderOutId);

    int insert(WmsOrderOutPlane record);

    int insertSelective(WmsOrderOutPlane record);

    WmsOrderOutPlane selectByPrimaryKey(String orderOutId);

    int updateByPrimaryKeySelective(WmsOrderOutPlane record);

    int updateByPrimaryKey(WmsOrderOutPlane record);

    List<WmsOrderOutPlane> queryByAny(WmsOrderOutPlane orderOutPlane);
}