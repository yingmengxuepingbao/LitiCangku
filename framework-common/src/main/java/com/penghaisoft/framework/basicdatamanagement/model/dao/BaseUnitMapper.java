package com.penghaisoft.framework.basicdatamanagement.model.dao;


import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseUnit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Mapper</p>
 *  
 *  @author 
 *  @createDate 
 **/
@Repository
public interface BaseUnitMapper{

	List<BaseUnit> queryBaseUnitList(@Param("entity") BaseUnit baseUnit);

	Integer getBaseUnitTotalCount(@Param("entity") BaseUnit baseUnit);

    List<BaseUnit> queryByAny(@Param("entity") BaseUnit baseUnit);

	int create(BaseUnit baseUnit);

	int deleteUnit(@Param("entity") BaseUnit baseUnit);

	int updateBySelect(@Param("entity") BaseUnit baseUnit);
}