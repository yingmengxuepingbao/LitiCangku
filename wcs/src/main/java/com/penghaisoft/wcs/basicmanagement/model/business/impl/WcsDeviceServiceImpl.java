package com.penghaisoft.wcs.basicmanagement.model.business.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsDeviceService;
import com.penghaisoft.wcs.basicmanagement.model.dao.WcsDeviceMapper;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsDevice;
import org.springframework.stereotype.Service;



/**
 * <p>
 * 业务逻辑实现类
 * </p>
 * 
 * @author
 * @createDate 
 **/
@Service("wcsDeviceService")
public class WcsDeviceServiceImpl extends BaseService implements IWcsDeviceService {
		@Resource
		private WcsDeviceMapper wcsDeviceMapper;
		
	  /**
	   * 新增记录
	   * @return
	   */
		@Override
		public Resp create(WcsDevice wcsDevice){
			//编码重复校验 todo
			Resp resp = new Resp();
			WcsDevice device = new WcsDevice();
			device.setDeviceId(wcsDevice.getDeviceId());
			List<WcsDevice> list = wcsDeviceMapper.queryByAny(device);
			if (list.size()>0){
				resp.setCode("1");
				resp.setMsg("设备ID重复");
				return resp;
			}

			wcsDeviceMapper.insertBySelect(wcsDevice);
			return success();
		}

	  /**
	   * 删除记录
	   * @return
	   */
		@Override
		public Resp delete(WcsDevice wcsDevice){
			wcsDeviceMapper.delete(wcsDevice);
			return success();
		}
		
	  /**
	   * 查询列表
	   * @return
	   */
		@Override
		public Pager<WcsDevice> findListByCondition(int page, int rows,WcsDevice condition){
		    Pager<WcsDevice> pager = new Pager<>();
		    pager.setPage(page);
		    pager.setRows(rows);
			List<WcsDevice> records = wcsDeviceMapper.queryList(pager,condition);
			long size = wcsDeviceMapper.queryCount(condition);
			pager.setRecords(records);
			pager.setTotalCount(size);
			return pager;
		}

	  /**
	   * 查询单条
	   * @return
	   */
		@Override
		public WcsDevice findById(String id){
			return wcsDeviceMapper.queryById(id);
		}
		
	  /**
	   * 修改记录
	   * @return
	   */
		@Override
		public Resp update(WcsDevice wcsDevice){
			wcsDeviceMapper.updateBySelect(wcsDevice);
			return success();
		}
		
		/**
		 * 根据类型查找设备	
		 *
		 * @param deviceType
		 * @return
		 */
		@Override
		public List<WcsDevice> queryByDeviceType(String deviceType) {
			return wcsDeviceMapper.selectByDeviceType(deviceType);
		}

		@Override
		public List<BaseDictItem> findDeviceBy(WcsDevice wcsDevice) {
			List<WcsDevice> records = wcsDeviceMapper.queryByAny(wcsDevice);
			List<BaseDictItem> list = new ArrayList<>();
			for (WcsDevice device : records) {
				BaseDictItem item = new BaseDictItem();
				item.setDicItemCode(device.getDeviceId().toString());
				item.setDicItemName(device.getDeviceName());
				list.add(item);
			}
			return list;
		}
		/**
		 * @Description: 地址管理 设备查询下拉框（设备id，名称，类型）
		 * @Author: jzh
		 * @Date: 2020/7/6
		 */ 
		@Override
		public List<BaseDictItem> findDeviceType(WcsDevice wcsDevice) {
			List<WcsDevice> records = wcsDeviceMapper.queryByAny(wcsDevice);
			List<BaseDictItem> list = new ArrayList<>();
			for (WcsDevice device : records) {
				BaseDictItem item = new BaseDictItem();
				item.setDicItemCode(device.getDeviceId().toString());
				item.setDicItemName(device.getDeviceType());
				//获取设备名称，暂用userDefined1
				item.setUserDefined1(device.getDeviceName());
				list.add(item);
			}
			return list;
		}
}
