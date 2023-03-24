package com.penghaisoft.wcs.basicmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsAgvTaskPlaneService;
import com.penghaisoft.wcs.taskmanagement.model.dao.WcsAgvTaskPlaneMapper;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTaskPlane;
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
@Service
public class WcsAgvTaskPlaneServiceImpl extends BaseService implements IWcsAgvTaskPlaneService {
    @Resource
    private WcsAgvTaskPlaneMapper wcsAgvTaskPlaneMapper;

    /**
     * @return
     * @Description m67agv任务
     * @Author luot
     * @Date 2020/8/7 17:29
     * @Param
     **/
    @Override
    public Pager<WcsAgvTaskPlane> findBindingInfo(int page, int rows, WcsAgvTaskPlane wcsAgvTaskPlane) {
        Pager<WcsAgvTaskPlane> pager = new Pager<>();
        pager.setPage(page);
        pager.setRows(rows);
        long size = wcsAgvTaskPlaneMapper.queryCount(wcsAgvTaskPlane);
        List<WcsAgvTaskPlane> records = new ArrayList<WcsAgvTaskPlane>();
        if (size > 0) {
            records = wcsAgvTaskPlaneMapper.queryList(pager, wcsAgvTaskPlane);
        }
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }
}
