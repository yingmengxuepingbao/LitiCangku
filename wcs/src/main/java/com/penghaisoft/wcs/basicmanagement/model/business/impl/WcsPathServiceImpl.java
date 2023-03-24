package com.penghaisoft.wcs.basicmanagement.model.business.impl;

import java.util.List;

import javax.annotation.Resource;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsPathService;
import com.penghaisoft.wcs.basicmanagement.model.dao.WcsPathMapper;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsPath;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 业务逻辑实现类
 * </p>
 * 
 * @author
 * @createDate 
 **/
@Service
public class WcsPathServiceImpl extends BaseService implements IWcsPathService {
		@Resource
		private WcsPathMapper wcsPathMapper;
		
	  /**
	   * 新增记录
	   * @return
	   */
		@Override
		public Resp create(WcsPath wcsPath){
			//编码重复校验 todo
			Resp resp = new Resp();
			WcsPath check = new WcsPath();
			check.setPathId(wcsPath.getPathId());
			List<WcsPath> list = wcsPathMapper.queryByAny(check);
			if (list.size()>0){
				resp.setCode("1");
				resp.setMsg("路径ID重复");
				return resp;
			}
			wcsPathMapper.insertBySelect(wcsPath);
			return success();
		}

	  /**
	   * 删除记录
	   * @return
	   */
		@Override
		public Resp delete(WcsPath wcsPath){
			wcsPathMapper.delete(wcsPath);
			return success();
		}
		
	  /**
	   * 查询列表
	   * @return
	   */
		@Override
		public Pager<WcsPath> findListByCondition(int page, int rows, WcsPath condition){
		    Pager<WcsPath> pager = new Pager<>();
		    pager.setPage(page);
		    pager.setRows(rows);
			List<WcsPath> records = wcsPathMapper.queryList(pager,condition);
			long size = wcsPathMapper.queryCount(condition);
			pager.setRecords(records);
			pager.setTotalCount(size);
			return pager;
		}

	  /**
	   * 查询单条
	   * @return
	   */
		@Override
		public WcsPath findById(String id){
			return wcsPathMapper.queryById(id);
		}
		
	  /**
	   * 修改记录
	   * @return
	   */
		@Override
		public Resp update(WcsPath wcsPath){
			wcsPathMapper.updateBySelect(wcsPath);
			return success();
		}

    /**
     * 当前起止位置是否合法
     * 当前起止只有一条记录才是合法的
     * @param fromAddress
     * @param toAddress
     * @return
     */
    @Override
    public Resp isPathLegal(Integer fromAddress, Integer toAddress) {
        Resp result = new Resp();
        result.setCode("0");
        WcsPath wcsPath = new WcsPath();
        wcsPath.setActiveFlag("1");
        wcsPath.setFromAddressId(fromAddress);
        wcsPath.setToAddressId(toAddress);
        List<WcsPath> pathList = wcsPathMapper.queryByAny(wcsPath);
//        理论上只可能有一条记录
        if (null == pathList){
            result.setMsg("路径不合法，从"+ fromAddress+"到"+toAddress+"的路径不存在！");
        }else if (pathList.size()==1){
            result.setCode("1");
            result.setData(pathList.get(0));
        }else {
            result.setMsg("路径不合法，从"+ fromAddress+"到"+toAddress+"存在路径数量="+pathList.size());
        }
        return result;
    }

}
