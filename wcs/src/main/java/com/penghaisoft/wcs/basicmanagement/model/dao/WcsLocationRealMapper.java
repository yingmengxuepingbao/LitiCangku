package com.penghaisoft.wcs.basicmanagement.model.dao;

import java.util.List;
import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>Mapper</p>
 *
 *  @author
 *  @createDate
 **/
@Repository
public interface WcsLocationRealMapper extends BaseMapper<WcsLocationReal> {
	public Integer batchInsert(List<WcsLocationReal> list);

	public Integer batchDelete(@Param("entity") WcsLocationReal t);

	public Integer insertBySelect(WcsLocationReal t);

    List<WcsLocationReal> queryByIds(@Param("locationIds")List<Integer> locationIds);
}