package com.penghaisoft.wcs.basicmanagement.model.business;


import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsPath;

/**
 * <p>
 * 业务接口类
 * </p>
 * 
 * @author
 * @createDate 
 **/
public interface IWcsPathService{
	
	public Resp create(WcsPath wcsPath);

	public Resp delete(WcsPath wcsPath);
	
	public Pager<WcsPath> findListByCondition(int page, int rows, WcsPath condition);
	
	public WcsPath findById(String id);
	
	public Resp update(WcsPath wcsPath);

	/**
	 * 当前起止位置是否合法
	 * 当前起止只有一条记录才是合法的
	 * @param fromAddress
	 * @param toAddress
	 * @return
	 */
	public Resp isPathLegal(Integer fromAddress,Integer toAddress);
}
