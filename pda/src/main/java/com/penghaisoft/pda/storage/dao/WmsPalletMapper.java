package com.penghaisoft.pda.storage.dao;

import com.penghaisoft.pda.storage.model.WmsPallet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WmsPalletMapper {
//    @Transactional(propagation= Propagation.REQUIRES_NEW)
    int insertSelective(WmsPallet record);

    List<WmsPallet> selectByCode(String palletCode);

    int updateByCode(WmsPallet record);

    Integer deleteByPrimaryKey(String palletId);

    int palletCleanByCode(String fromPalletCode);

    int palletUnbind(String fromPalletCode);
}