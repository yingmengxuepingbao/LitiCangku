package com.penghaisoft.wcs.basicmanagement.model.business;


import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;

import java.util.List;

/**
 * <p>
 * 业务接口类
 * </p>
 * 
 * @author
 * @createDate 
 **/
public interface IWcsLocationRealService{
	
	public Resp create(WcsLocationReal wcsLocationReal);

	public Resp delete(WcsLocationReal wcsLocationReal);
	
	public Pager<WcsLocationReal> findListByCondition(int page, int rows, WcsLocationReal condition);
	
	public WcsLocationReal findById(Integer id);
	
	public Resp update(WcsLocationReal wcsLocationReal);

    List<WcsLocationReal> findByIds(List<Integer> locationIds);
}
