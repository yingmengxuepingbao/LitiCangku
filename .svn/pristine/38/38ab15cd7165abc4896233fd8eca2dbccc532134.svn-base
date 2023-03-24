package com.penghaisoft.pda.storage.dao;

import com.penghaisoft.pda.storage.model.WmsOrderCheckPallet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WmsOrderCheckPalletMapper {
    int deleteByPrimaryKey(Integer checkPalletId);

    int insert(WmsOrderCheckPallet record);

    int insertSelective(WmsOrderCheckPallet record);

    WmsOrderCheckPallet selectByPrimaryKey(Integer checkPalletId);

    int updateByPrimaryKeySelective(WmsOrderCheckPallet record);

    int updateByPrimaryKey(WmsOrderCheckPallet record);

    List<WmsOrderCheckPallet> collectByOrderNo(WmsOrderCheckPallet orderCheckPallet);

    List<WmsOrderCheckPallet> queryByAny(WmsOrderCheckPallet orderCheckPallet);

    List<WmsOrderCheckPallet> collectGroupBYGoods(WmsOrderCheckPallet orderCheckPallet);
}