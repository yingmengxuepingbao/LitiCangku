package com.penghaisoft.pda.storage.dao;

import com.penghaisoft.pda.storage.model.WmsOrderCheckPalletBoxBarcode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WmsOrderCheckPalletBoxBarcodeMapper {
    int deleteByPrimaryKey(Integer checkPalletBoxBarcodeId);

    int insert(WmsOrderCheckPalletBoxBarcode record);

    int insertSelective(WmsOrderCheckPalletBoxBarcode record);

    WmsOrderCheckPalletBoxBarcode selectByPrimaryKey(Integer checkPalletBoxBarcodeId);

    int updateByPrimaryKeySelective(WmsOrderCheckPalletBoxBarcode record);

    int updateByPrimaryKey(WmsOrderCheckPalletBoxBarcode record);

    List<WmsOrderCheckPalletBoxBarcode> queryByAny(WmsOrderCheckPalletBoxBarcode palletBoxBarcode);
}