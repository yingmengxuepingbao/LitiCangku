package com.penghaisoft.wms.nuohua.model.business.impl;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.nuohua.model.business.WmsInOutTacticsService;
import com.penghaisoft.wms.nuohua.model.dao.WmsInOutTacticsMapper;
import com.penghaisoft.wms.nuohua.model.entity.WmsInOutTactics;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Date 2022-08-22
 **/
@Service("wmsInOutTacticsService")
public class WmsInOutTacticsServiceImpl  implements WmsInOutTacticsService {
    @Resource
    private WmsInOutTacticsMapper wmsInOutTacticsMapper;
    @Override
    public int insert(WmsInOutTactics wmsInOutTactics) {
        return wmsInOutTacticsMapper.insert(wmsInOutTactics);
    }

    @Override
    public Integer delete(WmsInOutTactics wmsInOutTactics) {
        return wmsInOutTacticsMapper.delete(wmsInOutTactics);
    }

    @Override
    public Pager<WmsInOutTactics> findListByCondition(int page, int rows, WmsInOutTactics wmsInOutTactics) {
        Pager<WmsInOutTactics> pager = new Pager<>();
        pager.setPage(page);
        pager.setRows(rows);
        List<WmsInOutTactics> records = wmsInOutTacticsMapper.queryList(pager,wmsInOutTactics);
        long size = wmsInOutTacticsMapper.queryCount(wmsInOutTactics);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

    @Override
    public WmsInOutTactics findById(String id) {
        return wmsInOutTacticsMapper.queryById(id);
    }

    @Override
    public Integer update(WmsInOutTactics wmsInOutTactics) {
        return wmsInOutTacticsMapper.update(wmsInOutTactics);
    }
}
