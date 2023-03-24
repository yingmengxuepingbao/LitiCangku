//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOrderCrossProductService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOrderCrossProductDeailMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOrderCrossProductMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCrossProduct;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCrossProductDeail;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsOrderCrossProductService")
public class WmsOrderCrossProductServiceImpl extends BaseService implements IWmsOrderCrossProductService {
    @Resource
    private WmsOrderCrossProductMapper wmsOrderCrossProductMapper;
    @Resource
    private WmsOrderCrossProductDeailMapper wmsOrderCrossProductDeailMapper;

    public WmsOrderCrossProductServiceImpl() {
    }

    public Resp create(WmsOrderCrossProduct wmsOrderCrossProduct) {
        this.wmsOrderCrossProductMapper.create(wmsOrderCrossProduct);
        return this.success();
    }

    public Resp delete(WmsOrderCrossProduct wmsOrderCrossProduct) {
        Resp resp = new Resp();
        WmsOrderCrossProduct orderCrossProduct = new WmsOrderCrossProduct();
        orderCrossProduct.setOrderCrossProductId(wmsOrderCrossProduct.getOrderCrossProductId());
        orderCrossProduct.setActiveFlag("1");
        List<WmsOrderCrossProduct> mainList = this.wmsOrderCrossProductMapper.queryByAny(orderCrossProduct);
        if (mainList.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("查询不到该越库单");
            return resp;
        } else {
            if (((WmsOrderCrossProduct)mainList.get(0)).getOrderStatus() != null) {
                String orderStatus = ((WmsOrderCrossProduct)mainList.get(0)).getOrderStatus();
                if (!"1".equals(orderStatus)) {
                    resp.setCode("1");
                    resp.setMsg("该越库单状态不为创建状态");
                    return resp;
                }
            }

            this.wmsOrderCrossProductMapper.deleteReally(wmsOrderCrossProduct);
            return this.success();
        }
    }

    public Pager<WmsOrderCrossProduct> findListByCondition(int page, int rows, WmsOrderCrossProduct condition) {
        condition.setActiveFlag("1");
        Pager<WmsOrderCrossProduct> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsOrderCrossProductMapper.queryCount(condition);
        List<WmsOrderCrossProduct> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsOrderCrossProductMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsOrderCrossProduct findById(String id) {
        return (WmsOrderCrossProduct)this.wmsOrderCrossProductMapper.queryById(id);
    }

    public Resp update(WmsOrderCrossProduct wmsOrderCrossProduct) {
        this.wmsOrderCrossProductMapper.updateBySelect(wmsOrderCrossProduct);
        return this.success();
    }

    public List<WmsOrderCrossProduct> queryByAny(WmsOrderCrossProduct condition) {
        return this.wmsOrderCrossProductMapper.queryByAny(condition);
    }

    public Resp start(WmsOrderCrossProduct wmsOrderCrossProduct) {
        Resp resp = new Resp();
        WmsOrderCrossProduct orderCrossProduct = new WmsOrderCrossProduct();
        orderCrossProduct.setOrderNo(wmsOrderCrossProduct.getOrderNo());
        orderCrossProduct.setActiveFlag("1");
        List<WmsOrderCrossProduct> mainList = this.wmsOrderCrossProductMapper.queryByAny(orderCrossProduct);
        if (mainList.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("查询不到该越库单");
            return resp;
        } else if (((WmsOrderCrossProduct)mainList.get(0)).getOrderStatus() != null) {
            String orderStatus = ((WmsOrderCrossProduct)mainList.get(0)).getOrderStatus();
            if (!"1".equals(orderStatus)) {
                resp.setCode("1");
                resp.setMsg("该越库单状态不为创建状态");
                return resp;
            } else {
                WmsOrderCrossProductDeail checkDetail = new WmsOrderCrossProductDeail();
                checkDetail.setOrderNo(wmsOrderCrossProduct.getOrderNo());
                checkDetail.setActiveFlag("1");
                List<WmsOrderCrossProductDeail> listDetail = this.wmsOrderCrossProductDeailMapper.queryByAny(checkDetail);
                if (listDetail.size() <= 0) {
                    resp.setCode("1");
                    resp.setMsg("该越库单无明细信息");
                    return resp;
                } else {
                    wmsOrderCrossProduct.setOrderStatus("2");
                    this.wmsOrderCrossProductMapper.updateBySelect(wmsOrderCrossProduct);
                    return this.success();
                }
            }
        } else {
            resp.setCode("1");
            resp.setMsg("越库单状态异常");
            return resp;
        }
    }
}
