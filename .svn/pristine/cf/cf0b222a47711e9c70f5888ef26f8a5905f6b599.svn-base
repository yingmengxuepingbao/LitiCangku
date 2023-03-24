package com.penghaisoft.wcs.log.model.dao;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.log.model.entity.WcsCallFourwaycarLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcsCallFourwaycarLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WcsCallFourwaycarLog record);

    int insertSelective(WcsCallFourwaycarLog record);

    WcsCallFourwaycarLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WcsCallFourwaycarLog record);

    int updateByPrimaryKey(WcsCallFourwaycarLog record);

    List<WcsCallFourwaycarLog> queryList(@Param("page") Pager<WcsCallFourwaycarLog> pager,@Param("entity") WcsCallFourwaycarLog condition);

    long queryCount(@Param("entity") WcsCallFourwaycarLog condition);
}