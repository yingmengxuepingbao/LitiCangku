//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.outboundmanagement.model.business.IWmsOrderOutPlaneService;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutPlaneDeailMapper;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutPlaneMapper;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutPlane;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsOrderOutPlaneService")
public class WmsOrderOutPlaneServiceImpl extends BaseService implements IWmsOrderOutPlaneService {
    @Resource
    private WmsOrderOutPlaneMapper wmsOrderOutPlaneMapper;
    @Resource
    private WmsOrderOutPlaneDeailMapper wmsOrderOutPlaneDeailMapper;

    public WmsOrderOutPlaneServiceImpl() {
    }

    public Resp create(WmsOrderOutPlane wmsOrderOutPlane) {
        this.wmsOrderOutPlaneMapper.create(wmsOrderOutPlane);
        return this.success();
    }

    public Resp delete(WmsOrderOutPlane wmsOrderOutPlane) {
        Resp resp = new Resp();
        List<WmsOrderOutPlane> list = this.wmsOrderOutPlaneMapper.queryByAny(wmsOrderOutPlane);
        if (list.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("查询不到出库单");
            return resp;
        } else {
            String orderStatus = ((WmsOrderOutPlane)list.get(0)).getOrderStatus();
            if (!"1".equals(orderStatus)) {
                resp.setCode("1");
                resp.setMsg("单据不为创建状态不可删除");
                return resp;
            } else {
                String orderNo = ((WmsOrderOutPlane)list.get(0)).getOrderNo();
                this.wmsOrderOutPlaneDeailMapper.deleteByOrderNO(orderNo);
                this.wmsOrderOutPlaneMapper.delete(wmsOrderOutPlane);
                return this.success();
            }
        }
    }

    public Pager<WmsOrderOutPlane> findListByCondition(int page, int rows, WmsOrderOutPlane condition) {
        Pager<WmsOrderOutPlane> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        List<WmsOrderOutPlane> records = this.wmsOrderOutPlaneMapper.queryList(pager, condition);
        long size = this.wmsOrderOutPlaneMapper.queryCount(condition);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsOrderOutPlane findById(String id) {
        return (WmsOrderOutPlane)this.wmsOrderOutPlaneMapper.queryById(id);
    }

    public Resp update(WmsOrderOutPlane wmsOrderOutPlane) {
        this.wmsOrderOutPlaneMapper.updateBySelect(wmsOrderOutPlane);
        return this.success();
    }
}
