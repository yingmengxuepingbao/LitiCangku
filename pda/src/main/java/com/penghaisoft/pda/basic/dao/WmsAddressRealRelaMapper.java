package com.penghaisoft.pda.basic.dao;

import com.penghaisoft.pda.basic.model.WmsAddressRealRela;

import java.util.List;

public interface WmsAddressRealRelaMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(WmsAddressRealRela record);

    WmsAddressRealRela selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WmsAddressRealRela record);

    public List<WmsAddressRealRela> queryOutAddress(WmsAddressRealRela t);

    public List<WmsAddressRealRela> queryByAddressCode(WmsAddressRealRela t);
}