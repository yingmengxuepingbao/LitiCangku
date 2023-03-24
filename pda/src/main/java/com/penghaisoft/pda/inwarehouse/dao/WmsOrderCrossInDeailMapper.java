package com.penghaisoft.pda.inwarehouse.dao;

import com.penghaisoft.pda.inwarehouse.model.WmsOrderCrossInDeail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WmsOrderCrossInDeailMapper {

    List<WmsOrderCrossInDeail> selectByOrderNo(String orderNo);

    int updateAmountByOrderNoAndGoods(WmsOrderCrossInDeail record);
}