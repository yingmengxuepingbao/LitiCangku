package com.penghaisoft.pda.outwarehouse.dao;


import com.penghaisoft.pda.outwarehouse.model.WmsOrderOutPlaneDeail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WmsOrderOutPlaneDeailMapper {
    int deleteByPrimaryKey(String orderOutDetailId);

    int insert(WmsOrderOutPlaneDeail record);

    int insertSelective(WmsOrderOutPlaneDeail record);

    WmsOrderOutPlaneDeail selectByPrimaryKey(String orderOutDetailId);

    int updateByPrimaryKeySelective(WmsOrderOutPlaneDeail record);

    int updateByPrimaryKey(WmsOrderOutPlaneDeail record);

    List<WmsOrderOutPlaneDeail> querByAny(WmsOrderOutPlaneDeail orderOutPlaneDeail);
}