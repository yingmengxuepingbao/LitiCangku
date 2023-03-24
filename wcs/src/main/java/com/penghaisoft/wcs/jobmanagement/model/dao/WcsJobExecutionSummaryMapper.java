package com.penghaisoft.wcs.jobmanagement.model.dao;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.jobmanagement.model.entity.WcsJobExecutionSummary;
import com.penghaisoft.wcs.jobmanagement.model.entity.WcsJobExecutionSummaryKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcsJobExecutionSummaryMapper {
    int deleteByPrimaryKey(WcsJobExecutionSummaryKey key);

    int insert(WcsJobExecutionSummary record);

    int insertSelective(WcsJobExecutionSummary record);

    WcsJobExecutionSummary selectByPrimaryKey(WcsJobExecutionSummaryKey key);

    int updateByPrimaryKeySelective(WcsJobExecutionSummary record);

    int updateByPrimaryKey(WcsJobExecutionSummary record);

    int updateSummaryLog(WcsJobExecutionSummary summaryUpdate);

    Long queryCount(@Param("entity") WcsJobExecutionSummary record);

    List<WcsJobExecutionSummary> queryList(@Param("page") Pager<WcsJobExecutionSummary> page, @Param("entity") WcsJobExecutionSummary record);

    List<WcsJobExecutionSummary> queryGmtCreateMax(WcsJobExecutionSummary jobExecutionSummary);

    int deleteExecutionSummary(WcsJobExecutionSummary jobExecutionSummary);
}