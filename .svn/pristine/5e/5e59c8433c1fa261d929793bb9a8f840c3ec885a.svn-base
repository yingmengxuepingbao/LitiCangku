package com.penghaisoft.pda.storage.dao;

import com.penghaisoft.pda.storage.model.WmsOrderCheck;

import java.util.List;

public interface WmsOrderCheckMapper {
    int deleteByPrimaryKey(String checkId);

    int insert(WmsOrderCheck record);

    int insertSelective(WmsOrderCheck record);

    WmsOrderCheck selectByPrimaryKey(String checkId);

    int updateByPrimaryKeySelective(WmsOrderCheck record);

    int updateByPrimaryKey(WmsOrderCheck record);

    List<WmsOrderCheck> selectByOrderNo(String orderNo);
}