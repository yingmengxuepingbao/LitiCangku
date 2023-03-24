package com.penghaisoft.wcs.basicmanagement.model.business;


import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsAddress;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsPath;

import java.util.List;

/**
 * <p>
 * 业务接口类
 * </p>
 * 
 * @author
 * @createDate 
 **/
public interface IWcsAddressService{
	
	public Resp create(WcsAddress wcsAddress);

	public Resp delete(WcsAddress wcsAddress);
	
	public Pager<WcsAddress> findListByCondition(int page, int rows, WcsAddress condition);
	
	public WcsAddress findById(String id);
	
	public Resp update(WcsAddress wcsAddress);

    List<BaseDictItem> findAddress(WcsAddress wcsAddress);


	/**
	 * 根据路径id 找到 地址信息
	 * @param pathId
	 * @return
	 */
	List<WcsAddress> findByPathId(Integer pathId);


	/**
	 * 根据pathId找到结束地址信息
	 * @param pathId
	 * @return
	 */
    WcsAddress findEndAddressByPathId(Integer pathId);

    List<BaseDictItem> findAgvAddress(WcsAddress wcsAddress);
}
