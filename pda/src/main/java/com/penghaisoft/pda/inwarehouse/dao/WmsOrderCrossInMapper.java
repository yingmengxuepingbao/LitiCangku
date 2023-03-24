package com.penghaisoft.pda.inwarehouse.dao;

import com.penghaisoft.pda.inwarehouse.model.WmsOrderCrossIn;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WmsOrderCrossInMapper {

    List<WmsOrderCrossIn> selectByOrderNoAndStatus(@Param("orderNo") String orderNo, @Param("list") List<String> statusList);

    int updateStatusByOrderNo(WmsOrderCrossIn record);

    List<WmsOrderCrossIn> queryOrderCrossListByNo(@Param("orderNo") String orderNo);
}