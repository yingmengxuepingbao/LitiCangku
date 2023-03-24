package com.penghaisoft.wcs.basicmanagement.model.dao;


import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Mapper</p>
 *  
 *  @author 
 *  @createDate 
 **/
public interface WcsAddressMapper extends BaseMapper<WcsAddress> {
	public Integer batchInsert(List<WcsAddress> list);
	
	public Integer batchDelete(@Param("entity") WcsAddress t);
	
	public Integer insertBySelect(WcsAddress t);

	List<WcsAddress> findAgvAddress(@Param("entity") WcsAddress wcsAddress);
}