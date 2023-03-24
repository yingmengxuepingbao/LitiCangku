package com.penghaisoft.pda.storage.dao;

import com.penghaisoft.pda.storage.model.WmsBoxBarcode;
import com.penghaisoft.pda.storage.model.WmsBoxBarcodeKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WmsBoxBarcodeMapper {
    int deleteByPrimaryKey(WmsBoxBarcodeKey key);

    int deleteByPalletCode(WmsBoxBarcodeKey key);

    int deleteByBoxBarcodeBatch(@Param("boxBarcodeList") List<String> boxBarcodeList);

    int insertSelective(WmsBoxBarcode record);

    WmsBoxBarcode selectByPrimaryKey(WmsBoxBarcodeKey key);

    List<WmsBoxBarcode> selectByPalletCode(WmsBoxBarcodeKey key);

    Integer batchInsert(List<WmsBoxBarcode> list);

    List<WmsBoxBarcodeKey> queryByCode(String palletCode);

    int changePalletCode(@Param("fromPalletCode") String fromPalletCode, @Param("toPalletCode") String toPalletCode);
}