package com.penghaisoft.wcs.basicmanagement.model.dao;


import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>Mapper</p>
 *  
 *  @author 
 *  @createDate 
 **/
public interface WcsDeviceMapper extends BaseMapper<WcsDevice> {
	public Integer batchInsert(List<WcsDevice> list);
	
	public Integer batchDelete(@Param("entity") WcsDevice t);
	
	public Integer insertBySelect(WcsDevice t);
	
	/**
     * 根据类型取设备列表
     * @param deviceType
     * @return
     */
    List<WcsDevice> selectByDeviceType(@Param("deviceType") String deviceType);
}