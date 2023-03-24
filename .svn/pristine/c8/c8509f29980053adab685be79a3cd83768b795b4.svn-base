package com.penghaisoft.wcs.monitormanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsManualOperationLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Mapper</p>
 *
 * @author
 * @createDate
 **/
public interface WcsManualOperationLogMapper extends BaseMapper<WcsManualOperationLog> {
    public Integer batchInsert(List<WcsManualOperationLog> list);

    public Integer batchDelete(@Param("entity") WcsManualOperationLog t);

    public Long queryByAnyCount(@Param("entity") WcsManualOperationLog t);
}