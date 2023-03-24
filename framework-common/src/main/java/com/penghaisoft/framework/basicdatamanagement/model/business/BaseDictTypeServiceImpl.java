package com.penghaisoft.framework.basicdatamanagement.model.business;

import java.util.List;

import javax.annotation.Resource;

import com.penghaisoft.framework.basicdatamanagement.model.dao.BaseDictItemMapper;
import com.penghaisoft.framework.basicdatamanagement.model.dao.BaseDictTypeMapper;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictType;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseUnit;
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
@Service("baseDictTypeService")
public class BaseDictTypeServiceImpl extends BaseService implements IBaseDictTypeService{
		@Resource
		private BaseDictTypeMapper baseDictTypeMapper;
		@Resource
		private BaseDictItemMapper baseDictItemMapper;
		
	  /**
	   * 新增记录
	   * @return
	   */
		@Override
		public Resp create(BaseDictType baseDictType){
			Resp resp = new Resp();
			BaseDictType dictType = new BaseDictType();
			dictType.setDicTypeCode(baseDictType.getDicTypeCode());
			List<BaseDictType> list = baseDictTypeMapper.queryByAny(dictType);
			if (list.size()>0){
				resp.setCode("1");
				resp.setMsg("该字典编码已存在");
				return resp;
			}
			baseDictTypeMapper.create(baseDictType);
			return success();
		}

	  /**
	   * 删除记录（逻辑删除）
	   * @return
	   */
		@Override
		public Resp delete(BaseDictType baseDictType){
			baseDictTypeMapper.delete(baseDictType);
			return success();
		}
		
	  /**
	   * 查询列表
	   * @return
	   */
		@Override
		public Pager<BaseDictType> findListByCondition(int page, int rows,BaseDictType condition){
		    Pager<BaseDictType> pager = new Pager<>();
		    pager.setPage(page);
		    pager.setRows(rows);
			List<BaseDictType> records = baseDictTypeMapper.queryList(pager,condition);
			long size = baseDictTypeMapper.queryCount(condition);
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
		public BaseDictType findById(String id){
			return baseDictTypeMapper.queryById(id);
		}
		
	  /**
	   * 修改记录
	   * @return
	   */
		@Override
		public Resp update(BaseDictType baseDictType){
			baseDictTypeMapper.updateBySelect(baseDictType);
			return success();
		}

		@Override
		public Resp deleteDictType(String dictTypeId) {
			Resp resp = new Resp();
			if (dictTypeId == null || dictTypeId.equals("")) {
				resp.setCode("1");
				resp.setMsg("输入参数错误");
				return resp;
			} else {
				BaseDictType baseDictType = new BaseDictType();
				baseDictType.setDictTypeId(dictTypeId);
				List<BaseDictType> baseDictTypeList = baseDictTypeMapper.queryByAny(baseDictType);
				if(baseDictTypeList.size()==0){
					resp.setCode("1");
					resp.setMsg("查询不到数据");
					return resp;
				}else {

					int deleteBaseDictType = baseDictTypeMapper.deleteBaseDictType(baseDictType);
					if(deleteBaseDictType<=0){
						resp.setCode("1");
						resp.setMsg("删除失败");
						return resp;
					}
					//删除明细
					// TODO: 2020/3/5
					int deleteDetail = baseDictItemMapper.deleteDictItemByCode(baseDictTypeList.get(0).getDicTypeCode());
				}
			}
			resp.setCode("0");
			resp.setMsg("删除成功");
			return resp;
		}

}
