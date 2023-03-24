package com.penghaisoft.wcs.monitormanagement.model.dao;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcsErrorLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WcsErrorLog record);

    int insertSelective(WcsErrorLog record);

    WcsErrorLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WcsErrorLog record);

    int updateByPrimaryKey(WcsErrorLog record);

    Long queryCount(WcsErrorLog record);

    List<WcsErrorLog> queryList(@Param("page") Pager<WcsErrorLog> page, @Param("entity") WcsErrorLog record);

    void insertList(@Param("list") List<WcsErrorLog> errorLogList);

    List<WcsErrorLog> queryByGmtCreateMax(WcsErrorLog errorLog);

    int deleteErrorLogByIdList(@Param("idList") List<Integer> idList);
}