package com.penghaisoft.framework.basicdatamanagement.model.dao;


import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictType;
import com.penghaisoft.framework.util.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Mapper</p>
 *  
 *  @author 
 *  @createDate 
 **/
public interface BaseDictTypeMapper extends BaseMapper<BaseDictType> {
	public Integer batchInsert(List<BaseDictType> list);
	
	public Integer batchDelete(@Param("entity") BaseDictType t);

    int deleteBaseDictType(@Param("entity") BaseDictType baseDictType);
}