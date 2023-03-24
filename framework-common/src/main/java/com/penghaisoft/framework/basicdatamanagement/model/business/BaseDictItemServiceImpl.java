package com.penghaisoft.framework.basicdatamanagement.model.business;

import java.util.List;

import javax.annotation.Resource;

import com.penghaisoft.framework.basicdatamanagement.model.dao.BaseDictItemMapper;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictType;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 业务逻辑实现类
 * </p>
 * 
 * @author
 * @createDate 
 **/
@Service("baseDictItemService")
public class BaseDictItemServiceImpl extends BaseService implements IBaseDictItemService{
		@Resource
		private BaseDictItemMapper baseDictItemMapper;
		
	  /**
	   * 新增记录
	   *
	   * @return
	   */
		@Override
		public Resp create(BaseDictItem baseDictItem){

			Resp resp = new Resp();
			BaseDictItem dictItem = new BaseDictItem();
			dictItem.setDicTypeCode(baseDictItem.getDicTypeCode());
			dictItem.setDicItemCode(baseDictItem.getDicItemCode());
			List<BaseDictItem> list = baseDictItemMapper.queryByAny(dictItem);
			if (list.size()>0){
				resp.setCode("1");
				resp.setMsg("该字典编码已存在");
				return resp;
			}
			baseDictItemMapper.create(baseDictItem);
			return success();
		}

	  /**
	   * 删除记录（逻辑删除）
	   *
	   * @return
	   */
		@Override
		public Resp delete(BaseDictItem baseDictItem){
			baseDictItemMapper.delete(baseDictItem);
			return success();
		}
		
	  /**
	   * 查询列表
	   *
	   *
	   * @return
	   */
		@Override
		public Pager<BaseDictItem> findListByCondition(int page, int rows,BaseDictItem condition){
		    Pager<BaseDictItem> pager = new Pager<>();
		    pager.setPage(page);
		    pager.setRows(rows);
			List<BaseDictItem> records = baseDictItemMapper.queryList(pager,condition);
			long size = baseDictItemMapper.queryCount(condition);
			pager.setRecords(records);
			pager.setTotalCount(size);
			return pager;
		}

	  /**
	   * 查询单条
	   * @param id
	   * @return
	   */
		@Override
		public BaseDictItem findById(String id){
			return baseDictItemMapper.queryById(id);
		}
		
	  /**
	   * 修改记录
	   *
	   * @return
	   */
		@Override
		public Resp update(BaseDictItem baseDictItem){
			baseDictItemMapper.updateBySelect(baseDictItem);
			return success();
		}

		@Override
		public Resp deleteDictItem(String dictItemId) {
			Resp resp = new Resp();
			if (dictItemId == null || dictItemId.equals("")) {
				resp.setCode("1");
				resp.setMsg("输入参数错误");
				return resp;
			} else {
				BaseDictItem baseDictItem = new BaseDictItem();
				baseDictItem.setDictItemId(dictItemId);
				List<BaseDictItem> baseDictItemList = baseDictItemMapper.queryByAny(baseDictItem);
				if(baseDictItemList.size()==0){
					resp.setCode("1");
					resp.setMsg("查询不到数据");
					return resp;
				}else {

					int deleteBaseDictItem = baseDictItemMapper.deleteBaseDictItem(baseDictItem);
					if(deleteBaseDictItem<=0){
						resp.setCode("1");
						resp.setMsg("删除失败");
						return resp;
					}
				}
			}
			resp.setCode("0");
			resp.setMsg("删除成功");
			return resp;
		}

		@Override
		public List<BaseDictItem> getDictTypeByCode(String code) {
			BaseDictItem baseDictItem = new BaseDictItem();
			baseDictItem.setDicTypeCode(code);
			List<BaseDictItem> list = baseDictItemMapper.queryByAny(baseDictItem);
			return list;
		}

}
