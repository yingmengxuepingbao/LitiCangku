package com.penghaisoft.wcs.basicmanagement.model.business;


import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsDevice;

import java.util.List;

/**
 * <p>
 * 业务接口类
 * </p>
 * 
 * @author
 * @createDate 
 **/
public interface IWcsDeviceService{
	
	public Resp create(WcsDevice wcsDevice);

	public Resp delete(WcsDevice wcsDevice);
	
	public Pager<WcsDevice> findListByCondition(int page, int rows, WcsDevice condition);
	
	public WcsDevice findById(String id);
	
	public Resp update(WcsDevice wcsDevice);
	
	/**
     * 根据类型查找数据
     * @param deviceType
     * @return
     */
    List<WcsDevice> queryByDeviceType(String deviceType);

    List<BaseDictItem> findDeviceBy(WcsDevice wcsDevice);

    List<BaseDictItem> findDeviceType(WcsDevice wcsDevice);
}
