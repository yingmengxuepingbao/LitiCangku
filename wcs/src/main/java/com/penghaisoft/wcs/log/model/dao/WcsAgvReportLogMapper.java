package com.penghaisoft.wcs.log.model.dao;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.log.model.entity.WcsAgvReportLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcsAgvReportLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WcsAgvReportLog record);

    int insertSelective(WcsAgvReportLog record);

    WcsAgvReportLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WcsAgvReportLog record);

    int updateByPrimaryKey(WcsAgvReportLog record);

    List<WcsAgvReportLog> queryList(@Param("page") Pager<WcsAgvReportLog> pager,@Param("entity") WcsAgvReportLog condition);

    long queryCount(@Param("entity") WcsAgvReportLog condition);
}