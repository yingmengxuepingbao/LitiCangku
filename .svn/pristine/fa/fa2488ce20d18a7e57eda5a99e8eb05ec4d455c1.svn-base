package com.penghaisoft.framework.basicdatamanagement.model.business;


import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;

import java.util.List;

/**
 * <p>
 * 业务接口类
 * </p>
 * 
 * @author
 * @createDate 
 **/
public interface IBaseDictItemService{
	
	public Resp create(BaseDictItem baseDictItem);

	public Resp delete(BaseDictItem baseDictItem);
	
	public Pager<BaseDictItem> findListByCondition(int page, int rows, BaseDictItem condition);
	
	public BaseDictItem findById(String id);
	
	public Resp update(BaseDictItem baseDictItem);

    Resp deleteDictItem(String dictItemId);

    List<BaseDictItem> getDictTypeByCode(String code);
}
