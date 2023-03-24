package com.penghaisoft.wcs.basicmanagement.model.dao;

import java.util.List;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsPath;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>Mapper</p>
 *  
 *  @author 
 *  @createDate 
 **/
@Repository
public interface WcsPathMapper extends BaseMapper<WcsPath> {
	public Integer batchInsert(List<WcsPath> list);
	
	public Integer batchDelete(@Param("entity") WcsPath t);
	
	public Integer insertBySelect(WcsPath t);
}