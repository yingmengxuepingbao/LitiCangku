package com.penghaisoft.pda.storage.dao;

import com.penghaisoft.pda.storage.model.WmsGoods;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WmsGoodsMapper {

    int insertSelective(WmsGoods record);

    List<WmsGoods> selectByGoodsCode(String goodsCode);

    int updateByPrimaryKeySelective(WmsGoods record);

}