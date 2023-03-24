package com.penghaisoft.framework.basicdatamanagement.model.dao;


import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.util.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Mapper</p>
 *  
 *  @author 
 *  @createDate 
 **/
public interface BaseDictItemMapper extends BaseMapper<BaseDictItem> {
	public Integer batchInsert(List<BaseDictItem> list);
	
	public Integer batchDelete(@Param("entity") BaseDictItem t);

    int deleteBaseDictItem(@Param("entity") BaseDictItem baseDictItem);

    int deleteDictItemByCode(String dicTypeCode);
}