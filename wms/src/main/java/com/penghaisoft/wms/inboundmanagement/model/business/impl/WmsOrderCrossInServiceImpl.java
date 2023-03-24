//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.inboundmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.inboundmanagement.model.business.IWmsOrderCrossInService;
import com.penghaisoft.wms.inboundmanagement.model.dao.WmsOrderCrossInDeailMapper;
import com.penghaisoft.wms.inboundmanagement.model.dao.WmsOrderCrossInMapper;
import com.penghaisoft.wms.inboundmanagement.model.entity.WmsOrderCrossIn;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsOrderCrossInService")
public class WmsOrderCrossInServiceImpl extends BaseService implements IWmsOrderCrossInService {
    @Resource
    private WmsOrderCrossInMapper wmsOrderCrossInMapper;
    @Resource
    private WmsOrderCrossInDeailMapper wmsOrderCrossInDeailMapper;

    public WmsOrderCrossInServiceImpl() {
    }

    public Resp create(WmsOrderCrossIn wmsOrderCrossIn) {
        this.wmsOrderCrossInMapper.create(wmsOrderCrossIn);
        return this.success();
    }

    public Resp delete(WmsOrderCrossIn wmsOrderCrossIn) {
        Resp resp = new Resp();
        List<WmsOrderCrossIn> list = this.wmsOrderCrossInMapper.queryByAny(wmsOrderCrossIn);
        if (list.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("查询不到出库单");
            return resp;
        } else {
            String orderStatus = ((WmsOrderCrossIn)list.get(0)).getOrderStatus();
            if (!"1".equals(orderStatus)) {
                resp.setCode("1");
                resp.setMsg("单据不为创建状态不可删除");
                return resp;
            } else {
                String orderNo = ((WmsOrderCrossIn)list.get(0)).getOrderNo();
                this.wmsOrderCrossInDeailMapper.deleteByOrderNO(orderNo);
                this.wmsOrderCrossInMapper.delete(wmsOrderCrossIn);
                return this.success();
            }
        }
    }

    public Pager<WmsOrderCrossIn> findListByCondition(int page, int rows, WmsOrderCrossIn condition) {
        Pager<WmsOrderCrossIn> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        List<WmsOrderCrossIn> records = this.wmsOrderCrossInMapper.queryList(pager, condition);
        long size = this.wmsOrderCrossInMapper.queryCount(condition);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsOrderCrossIn findById(String id) {
        return (WmsOrderCrossIn)this.wmsOrderCrossInMapper.queryById(id);
    }

    public Resp update(WmsOrderCrossIn wmsOrderCrossIn) {
        this.wmsOrderCrossInMapper.updateBySelect(wmsOrderCrossIn);
        return this.success();
    }
}
