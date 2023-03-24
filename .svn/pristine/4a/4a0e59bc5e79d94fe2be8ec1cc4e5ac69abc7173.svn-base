package com.penghaisoft.wcs.basicmanagement.model.business.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsAddressService;
import com.penghaisoft.wcs.basicmanagement.model.dao.WcsAddressMapper;
import com.penghaisoft.wcs.basicmanagement.model.dao.WcsPathMapper;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsAddress;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsDevice;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;



/**
 * <p>
 * 业务逻辑实现类
 * </p>
 *
 * @author
 * @createDate
 **/
@Service("wcsAddressService")
public class WcsAddressServiceImpl extends BaseService implements IWcsAddressService {

	@Resource
	private WcsAddressMapper wcsAddressMapper;

	@Resource
	private WcsPathMapper wcsPathMapper;


	/**
	 * 新增记录
	 * @return
	 */
	@Override
	public Resp create(WcsAddress wcsAddress){
		//编码重复校验 todo
		Resp resp = new Resp();
		WcsAddress check = new WcsAddress();
		check.setAddressId(wcsAddress.getAddressId());
		List<WcsAddress> list = wcsAddressMapper.queryByAny(check);
		if (list.size()>0){
			resp.setCode("1");
			resp.setMsg("地址ID重复");
			return resp;
		}
		wcsAddressMapper.insertBySelect(wcsAddress);
		return success();
	}

	/**
	 * 删除记录
	 * @return
	 */
	@Override
	public Resp delete(WcsAddress wcsAddress){
		wcsAddressMapper.delete(wcsAddress);
		return success();
	}

	/**
	 * 查询列表
	 * @return
	 */
	@Override
	public Pager<WcsAddress> findListByCondition(int page, int rows,WcsAddress condition){
		Pager<WcsAddress> pager = new Pager<>();
		pager.setPage(page);
		pager.setRows(rows);
		List<WcsAddress> records = wcsAddressMapper.queryList(pager,condition);
		long size = wcsAddressMapper.queryCount(condition);
		pager.setRecords(records);
		pager.setTotalCount(size);
		return pager;
	}

	/**
	 * 查询单条
	 * @return
	 */
	@Override
	public WcsAddress findById(String id){
		return wcsAddressMapper.queryById(id);
	}

	/**
	 * 修改记录
	 * @return
	 */
	@Override
	public Resp update(WcsAddress wcsAddress){
		wcsAddressMapper.updateBySelect(wcsAddress);
		return success();
	}

	@Override
	public List<BaseDictItem> findAddress(WcsAddress wcsAddress) {
		List<WcsAddress> records = wcsAddressMapper.queryByAny(wcsAddress);
		List<BaseDictItem> list = new ArrayList<>();
		for (WcsAddress address : records) {
			BaseDictItem item = new BaseDictItem();
			item.setDicItemCode(address.getAddressId().toString());
			item.setDicItemName(address.getDescription());
			list.add(item);
		}
		return list;
	}

	/**
	 * 根据路径id 找到 地址信息
	 *
	 * @param pathId
	 * @return
	 */
	@Override
	public List<WcsAddress> findByPathId(Integer pathId) {
		List<WcsAddress> addressList = new ArrayList<>();
		WcsPath wcsPath = wcsPathMapper.queryById(pathId.toString());

		Integer fromId = wcsPath.getFromAddressId();
		WcsAddress address = wcsAddressMapper.queryById(fromId.toString());
		addressList.add(address);
//		当有途径路径的时候要写入
		String channelAddressStr = wcsPath.getChannelAddress();
		if (StringUtils.isNotBlank(channelAddressStr)){
			address = wcsAddressMapper.queryById(channelAddressStr);
			addressList.add(address);
		}

		Integer toId = wcsPath.getToAddressId();
		address = wcsAddressMapper.queryById(toId.toString());
		addressList.add(address);

		return addressList;
	}

	/**
	 * 根据pathId找到结束地址信息
	 *
	 * @param pathId
	 * @return
	 */
	@Override
	public WcsAddress findEndAddressByPathId(Integer pathId) {
		WcsPath wcsPath = wcsPathMapper.queryById(pathId.toString());
		Integer toId = wcsPath.getToAddressId();
		WcsAddress address = wcsAddressMapper.queryById(toId.toString());
		return address;
	}
	/**
	 * @Description: 手动AGV任务下发，查询agv地址下拉框
	 * @Author: jzh
	 * @Date: 2020/7/16
	 */ 
	@Override
	public List<BaseDictItem> findAgvAddress(WcsAddress wcsAddress) {
		List<WcsAddress> records = wcsAddressMapper.findAgvAddress(wcsAddress);
		List<BaseDictItem> list = new ArrayList<>();
		for (WcsAddress address : records) {
			BaseDictItem item = new BaseDictItem();
			item.setDicItemCode(address.getAddressId().toString());
			item.setDicItemName(address.getDescription());
			list.add(item);
		}
		return list;
	}

}
