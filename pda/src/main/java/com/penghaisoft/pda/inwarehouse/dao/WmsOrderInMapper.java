package com.penghaisoft.pda.inwarehouse.dao;


import com.penghaisoft.pda.inwarehouse.model.WmsOrderIn;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Mapper</p>
 *  
 *  @author 
 *  @createDate 
 **/
@Repository
public interface WmsOrderInMapper{

    List<WmsOrderIn> queryOrderInListByNoAndStatus(@Param("orderNo") String orderNo, @Param("list") List<String> statusList);

    int updateOrderStatus(@Param("orderNo") String orderNo, @Param("orderStatus") String orderStatus, @Param("account") String account);

    List<WmsOrderIn> queryOrderInListByNo(@Param("orderNo") String orderNo);

    int updateOrderAmount(@Param("entity") WmsOrderIn wmsOrderIn);
}