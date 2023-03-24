package com.penghaisoft.wcs.basicmanagement.model.business.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsLocationRealService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import org.springframework.stereotype.Service;
import com.penghaisoft.wcs.basicmanagement.model.dao.WcsLocationRealMapper;

/**
 * <p>
 * 业务逻辑实现类
 * </p>
 *
 * @author
 * @createDate
 **/
@Service
public class WcsLocationRealServiceImpl extends BaseService implements IWcsLocationRealService {
	@Resource
	private WcsLocationRealMapper wcsLocationRealMapper;

	/**
	 * 新增记录
	 * @return
	 */
	@Override
	public Resp create(WcsLocationReal wcsLocationReal){
		//编码重复校验 todo
		Resp resp = new Resp();
		WcsLocationReal check = new WcsLocationReal();
		check.setShelfId(wcsLocationReal.getLocationId());
		List<WcsLocationReal> list = wcsLocationRealMapper.queryByAny(check);
		if (list.size()>0){
			resp.setCode("1");
			resp.setMsg("物理库位ID重复");
			return resp;
		}
		wcsLocationRealMapper.insertBySelect(wcsLocationReal);
		return success();
	}

	/**
	 * 删除记录
	 * @return
	 */
	@Override
	public Resp delete(WcsLocationReal wcsLocationReal){
		wcsLocationRealMapper.delete(wcsLocationReal);
		return success();
	}

	/**
	 * 查询列表
	 * @return
	 */
	@Override
	public Pager<WcsLocationReal> findListByCondition(int page, int rows, WcsLocationReal condition){
		Pager<WcsLocationReal> pager = new Pager<>();
		pager.setPage(page);
		pager.setRows(rows);
		List<WcsLocationReal> records = wcsLocationRealMapper.queryList(pager,condition);
		long size = wcsLocationRealMapper.queryCount(condition);
		pager.setRecords(records);
		pager.setTotalCount(size);
		return pager;
	}

	/**
	 * 查询单条
	 * @return
	 */
	@Override
	public WcsLocationReal findById(Integer id){
		return wcsLocationRealMapper.queryById(id);
	}

	/**
	 * 修改记录
	 * @return
	 */
	@Override
	public Resp update(WcsLocationReal wcsLocationReal){
		wcsLocationRealMapper.updateBySelect(wcsLocationReal);
		return success();
	}

	@Override
	public List<WcsLocationReal> findByIds(List<Integer> locationIds) {
		List<WcsLocationReal> locationReals = new ArrayList<>();
		if (locationIds.size()==1){
			WcsLocationReal locationReal = wcsLocationRealMapper.queryById(locationIds.get(0));
			if (null!=locationReal){
				locationReals.add(locationReal);
			}
		}else {
			locationReals = wcsLocationRealMapper.queryByIds(locationIds);
		}
		return locationReals;
	}

}
