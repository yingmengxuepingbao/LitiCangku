package com.penghaisoft.wcs.basicmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsErrorCodeService;
import com.penghaisoft.wcs.basicmanagement.model.dao.WcsErrorCodeMapper;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsErrorCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 业务逻辑实现类
 * </p>
 *
 * @author
 * @createDate
 **/
@Service("wcsErrorCodeService")
public class WcsErrorCodeServiceImpl extends BaseService implements IWcsErrorCodeService {
    @Resource
    private WcsErrorCodeMapper wcsErrorCodeMapper;

    /**
     * 新增记录
     *
     * @param baseResource
     * @return
     */
    @Override
    public Resp create(WcsErrorCode wcsErrorCode) {
        if (wcsErrorCode.getErrId() != null && !"".equals(wcsErrorCode.getErrId())) {
            wcsErrorCodeMapper.updateBySelect(wcsErrorCode);
        } else {
            wcsErrorCodeMapper.create(wcsErrorCode);
        }

        return success();
    }

    /**
     * 删除记录（逻辑删除）
     *
     * @param baseResource
     * @return
     */
    @Override
    public Resp delete(WcsErrorCode wcsErrorCode) {
        wcsErrorCodeMapper.delete(wcsErrorCode);
        return success();
    }

    /**
     * 查询列表
     *
     * @param pager
     * @param baseResource
     * @return
     */
    @Override
    public Pager<WcsErrorCode> findListByCondition(int page, int rows, WcsErrorCode condition) {
        Pager<WcsErrorCode> pager = new Pager<>();
        pager.setPage(page);
        pager.setRows(rows);
//        condition.preQuery();
        long size = wcsErrorCodeMapper.queryCount(condition);
        List<WcsErrorCode> records = new ArrayList<WcsErrorCode>();
        if (size > 0) {
            records = wcsErrorCodeMapper.queryList(pager, condition);
        }
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

    /**
     * 查询单条
     *
     * @param id
     * @return
     */
    @Override
    public WcsErrorCode findById(String id) {
        return wcsErrorCodeMapper.queryById(id);
    }

    /**
     * 修改记录
     *
     * @param baseResource
     * @return
     */
    @Override
    public Resp update(WcsErrorCode wcsErrorCode) {
        wcsErrorCodeMapper.updateBySelect(wcsErrorCode);
        return success();
    }

    @Override
    public List<WcsErrorCode> queryByAny(WcsErrorCode condition) {
        return wcsErrorCodeMapper.queryByAny(condition);
    }
}
