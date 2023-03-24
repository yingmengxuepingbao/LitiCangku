package com.penghaisoft.pda.storage.dao;

import com.penghaisoft.pda.storage.model.WmsOutTemporary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WmsOutTemporaryMapper {

    int insertSelective(WmsOutTemporary record);

    List<WmsOutTemporary> selectByGoodsCode(String goodsCode);

    int addAmountByGoodsCode(WmsOutTemporary record);
}