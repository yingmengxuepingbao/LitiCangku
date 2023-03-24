package com.penghaisoft.wcs.jobmanagement.model.dao;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.jobmanagement.model.entity.WcsJobExecutionLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcsJobExecutionLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WcsJobExecutionLog record);

    int insertSelective(WcsJobExecutionLog record);

    WcsJobExecutionLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WcsJobExecutionLog record);

    int updateByPrimaryKey(WcsJobExecutionLog record);

    int deleteJobExecutionWeekAgo(WcsJobExecutionLog jobExecutionLog);

    Long queryCount(@Param("entity") WcsJobExecutionLog record);

    List<WcsJobExecutionLog> queryList(@Param("page") Pager<WcsJobExecutionLog> page, @Param("entity") WcsJobExecutionLog record);
}