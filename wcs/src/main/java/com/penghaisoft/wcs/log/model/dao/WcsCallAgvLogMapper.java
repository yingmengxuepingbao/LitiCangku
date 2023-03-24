package com.penghaisoft.wcs.log.model.dao;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.log.model.entity.WcsCallAgvLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcsCallAgvLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WcsCallAgvLog record);

    int insertSelective(WcsCallAgvLog record);

    WcsCallAgvLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WcsCallAgvLog record);

    int updateByPrimaryKey(WcsCallAgvLog record);

    List<WcsCallAgvLog> queryList(@Param("page") Pager<WcsCallAgvLog> pager,@Param("entity") WcsCallAgvLog condition);

    long queryCount(@Param("entity") WcsCallAgvLog condition);
}