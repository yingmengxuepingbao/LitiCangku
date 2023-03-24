package com.penghaisoft.pda.storage.dao;

import com.penghaisoft.pda.storage.model.WmsInTemporary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WmsInTemporaryMapper {

    int insertSelective(WmsInTemporary record);

    List<WmsInTemporary> selectByGoods(String goodsCode);

    int updateAmountByGoods(WmsInTemporary record);
}