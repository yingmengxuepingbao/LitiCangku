package com.penghaisoft.wcs.log.model.dao;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.log.model.entity.WcsFourwaycarReportLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcsFourwaycarReportLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WcsFourwaycarReportLog record);

    int insertSelective(WcsFourwaycarReportLog record);

    WcsFourwaycarReportLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WcsFourwaycarReportLog record);

    int updateByPrimaryKey(WcsFourwaycarReportLog record);

    List<WcsFourwaycarReportLog> queryList(@Param("page") Pager<WcsFourwaycarReportLog> pager,@Param("entity") WcsFourwaycarReportLog condition);

    long queryCount(@Param("entity") WcsFourwaycarReportLog condition);
}