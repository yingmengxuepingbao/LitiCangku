package com.penghaisoft.framework.basicdatamanagement.model.business;


import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictType;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;

/**
 * <p>
 * 业务接口类
 * </p>
 * 
 * @author
 * @createDate 
 **/
public interface IBaseDictTypeService{
	
	public Resp create(BaseDictType baseDictType);

	public Resp delete(BaseDictType baseDictType);
	
	public Pager<BaseDictType> findListByCondition(int page, int rows, BaseDictType condition);
	
	public BaseDictType findById(String id);
	
	public Resp update(BaseDictType baseDictType);

	Resp deleteDictType(String dictTypeId);
}
