package com.penghaisoft.wcs.basicmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsErrorCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Mapper</p>
 *
 * @author
 * @createDate
 **/
public interface WcsErrorCodeMapper extends BaseMapper<WcsErrorCode> {
    public Integer batchInsert(List<WcsErrorCode> list);

    public Integer batchDelete(@Param("entity") WcsErrorCode t);

    public Long queryByAnyCount(@Param("entity") WcsErrorCode t);
}