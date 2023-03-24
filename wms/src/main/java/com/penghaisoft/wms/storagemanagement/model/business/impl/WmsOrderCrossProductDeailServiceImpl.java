//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsGoodsMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOrderCrossProductDeailService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOrderCrossProductDeailMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOrderCrossProductMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsTaskExecutionLogMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCrossProduct;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCrossProductDeail;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("wmsOrderCrossProductDeailService")
public class WmsOrderCrossProductDeailServiceImpl extends BaseService implements IWmsOrderCrossProductDeailService {
    @Resource
    private WmsOrderCrossProductDeailMapper wmsOrderCrossProductDeailMapper;
    @Resource
    private WmsOrderCrossProductMapper wmsOrderCrossProductMapper;
    @Resource
    private WmsPalletMapper wmsPalletMapper;
    @Resource
    private WmsTaskExecutionLogMapper wmsTaskExecutionLogMapper;
    @Resource
    private WmsGoodsMapper wmsGoodsMapper;

    public WmsOrderCrossProductDeailServiceImpl() {
    }

    public Resp create(WmsOrderCrossProductDeail wmsOrderCrossProductDeail) {
        Resp resp = new Resp();
        WmsOrderCrossProduct wmsOrderCrossProduct = new WmsOrderCrossProduct();
        wmsOrderCrossProduct.setOrderNo(wmsOrderCrossProductDeail.getOrderNo());
        wmsOrderCrossProduct.setActiveFlag("1");
        List<WmsOrderCrossProduct> checkMain = this.wmsOrderCrossProductMapper.queryByAny(wmsOrderCrossProduct);
        if (!"1".equals(((WmsOrderCrossProduct)checkMain.get(0)).getOrderStatus())) {
            resp.setCode("1");
            resp.setMsg("主表状态不为创建，不可新增明细");
            return resp;
        } else {
            WmsOrderCrossProductDeail checkDetail = new WmsOrderCrossProductDeail();
            checkDetail.setOrderNo(wmsOrderCrossProductDeail.getOrderNo());
            checkDetail.setGoodsCode(wmsOrderCrossProductDeail.getGoodsCode());
            checkDetail.setActiveFlag("1");
            List<WmsOrderCrossProductDeail> list = this.wmsOrderCrossProductDeailMapper.queryByAny(checkDetail);
            if (list.size() > 0) {
                resp.setCode("1");
                resp.setMsg("商品编码已存在");
                return resp;
            } else {
                String goodsName = "";
                WmsGoods wmsGoods = new WmsGoods();
                wmsGoods.setGoodsCode(wmsOrderCrossProductDeail.getGoodsCode());
                List<WmsGoods> goods = this.wmsGoodsMapper.queryByAny(wmsGoods);
                if (goods.size() > 0) {
                    goodsName = ((WmsGoods)goods.get(0)).getGoodsName();
                }

                wmsOrderCrossProductDeail.setGoodsName(goodsName);
                wmsOrderCrossProductDeail.setRealAmount(0);
                this.wmsOrderCrossProductDeailMapper.create(wmsOrderCrossProductDeail);
                return this.success();
            }
        }
    }

    public Resp delete(WmsOrderCrossProductDeail wmsOrderCrossProductDeail) {
        Resp resp = new Resp();
        WmsOrderCrossProductDeail orderCrossProductDeail = new WmsOrderCrossProductDeail();
        orderCrossProductDeail.setOrderCrossProductDetailId(wmsOrderCrossProductDeail.getOrderCrossProductDetailId());
        orderCrossProductDeail.setActiveFlag("1");
        List<WmsOrderCrossProductDeail> list = this.wmsOrderCrossProductDeailMapper.queryByAny(orderCrossProductDeail);
        if (list.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("查询该行项目失败");
            return resp;
        } else {
            String orderNo = ((WmsOrderCrossProductDeail)list.get(0)).getOrderNo();
            WmsOrderCrossProduct orderCrossProduct = new WmsOrderCrossProduct();
            orderCrossProduct.setOrderNo(orderNo);
            orderCrossProduct.setActiveFlag("1");
            List<WmsOrderCrossProduct> checkMain = this.wmsOrderCrossProductMapper.queryByAny(orderCrossProduct);
            if (!"1".equals(((WmsOrderCrossProduct)checkMain.get(0)).getOrderStatus())) {
                resp.setCode("1");
                resp.setMsg("主表状态不为创建，不可删除明细");
                return resp;
            } else {
                this.wmsOrderCrossProductDeailMapper.deleteReally(wmsOrderCrossProductDeail);
                return this.success();
            }
        }
    }

    public Pager<WmsOrderCrossProductDeail> findListByCondition(int page, int rows, WmsOrderCrossProductDeail condition) {
        Pager<WmsOrderCrossProductDeail> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsOrderCrossProductDeailMapper.queryCount(condition);
        List<WmsOrderCrossProductDeail> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsOrderCrossProductDeailMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsOrderCrossProductDeail findById(String id) {
        return (WmsOrderCrossProductDeail)this.wmsOrderCrossProductDeailMapper.queryById(id);
    }

    public Resp update(WmsOrderCrossProductDeail wmsOrderCrossProductDeail) {
        Resp resp = new Resp();
        WmsOrderCrossProductDeail orderCrossProductDeail = new WmsOrderCrossProductDeail();
        orderCrossProductDeail.setOrderCrossProductDetailId(wmsOrderCrossProductDeail.getOrderCrossProductDetailId());
        orderCrossProductDeail.setActiveFlag("1");
        List<WmsOrderCrossProductDeail> list = this.wmsOrderCrossProductDeailMapper.queryByAny(orderCrossProductDeail);
        if (list.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("查询该行项目失败");
            return resp;
        } else {
            String orderNo = ((WmsOrderCrossProductDeail)list.get(0)).getOrderNo();
            WmsOrderCrossProduct orderCrossProduct = new WmsOrderCrossProduct();
            orderCrossProduct.setOrderNo(orderNo);
            orderCrossProduct.setActiveFlag("1");
            List<WmsOrderCrossProduct> checkMain = this.wmsOrderCrossProductMapper.queryByAny(orderCrossProduct);
            if (!"1".equals(((WmsOrderCrossProduct)checkMain.get(0)).getOrderStatus())) {
                resp.setCode("1");
                resp.setMsg("主表状态不为创建，不可修改明细");
                return resp;
            } else {
                String goodsCode = ((WmsOrderCrossProductDeail)list.get(0)).getGoodsCode();
                if (goodsCode.equals(wmsOrderCrossProductDeail.getGoodsCode())) {
                    this.wmsOrderCrossProductDeailMapper.updateBySelect(wmsOrderCrossProductDeail);
                } else {
                    WmsOrderCrossProductDeail detail = new WmsOrderCrossProductDeail();
                    detail.setOrderNo(orderNo);
                    detail.setActiveFlag("1");
                    List<WmsOrderCrossProductDeail> listDetail = this.wmsOrderCrossProductDeailMapper.queryByAny(detail);
                    Iterator var11 = listDetail.iterator();

                    while(var11.hasNext()) {
                        WmsOrderCrossProductDeail planeDeail = (WmsOrderCrossProductDeail)var11.next();
                        if (wmsOrderCrossProductDeail.getGoodsCode().equals(planeDeail.getGoodsCode())) {
                            resp.setCode("1");
                            resp.setMsg("要修改的商品在订单中重复");
                            return resp;
                        }
                    }

                    String goodsName = "";
                    WmsGoods wmsGoods = new WmsGoods();
                    wmsGoods.setGoodsCode(wmsOrderCrossProductDeail.getGoodsCode());
                    List<WmsGoods> goods = this.wmsGoodsMapper.queryByAny(wmsGoods);
                    if (goods.size() > 0) {
                        goodsName = ((WmsGoods)goods.get(0)).getGoodsName();
                    }

                    wmsOrderCrossProductDeail.setGoodsName(goodsName);
                    this.wmsOrderCrossProductDeailMapper.updateBySelect(wmsOrderCrossProductDeail);
                }

                return this.success();
            }
        }
    }

    public List<WmsOrderCrossProductDeail> queryByAny(WmsOrderCrossProductDeail condition) {
        return this.wmsOrderCrossProductDeailMapper.queryByAny(condition);
    }

    public List<WmsOrderCrossProductDeail> queryCrossOpenList(WmsOrderCrossProductDeail condition) {
        return this.wmsOrderCrossProductDeailMapper.queryCrossOpenList(condition);
    }

    @Transactional
    public Resp dealCrossOpen(WmsOrderCrossProductDeail wmsOrderCrossProductDeail, WmsTaskExecutionLog wmsTaskExecutionLog) {
        WmsOrderCrossProductDeail detailUpob = new WmsOrderCrossProductDeail();
        detailUpob.setOrderCrossProductDetailId(wmsOrderCrossProductDeail.getOrderCrossProductDetailId());
        detailUpob.setRealAmountAdd(wmsOrderCrossProductDeail.getRealAmountAdd());
        detailUpob.setLastModifiedBy("wcs");
        detailUpob.setGmtModified(wmsTaskExecutionLog.getGmtCreate());
        Integer num = this.wmsOrderCrossProductDeailMapper.updateByKey(detailUpob);
        if (num <= 0) {
            return this.fail("数量超发！");
        } else {
            WmsOrderCrossProductDeail searchOb = new WmsOrderCrossProductDeail();
            searchOb.setOrderCrossProductId(wmsOrderCrossProductDeail.getOrderCrossProductId());
            searchOb.setPlanAmountBigRealAmount("PlanAmountBigRealAmount");
            searchOb.setActiveFlag("1");
            List<WmsOrderCrossProductDeail> list = this.wmsOrderCrossProductDeailMapper.queryByAny(searchOb);
            if (list == null || list.isEmpty()) {
                WmsOrderCrossProduct wmsOrderCrossProduct = new WmsOrderCrossProduct();
                wmsOrderCrossProduct.setOrderCrossProductId(wmsOrderCrossProductDeail.getOrderCrossProductId());
                wmsOrderCrossProduct.setOrderStatus("3");
                wmsOrderCrossProduct.setLastModifiedBy("wcs");
                wmsOrderCrossProduct.setGmtModified(wmsTaskExecutionLog.getGmtCreate());
                this.wmsOrderCrossProductMapper.updateBySelect(wmsOrderCrossProduct);
            }

            WmsPallet wmsPalletOb = new WmsPallet();
            wmsPalletOb.setPalletCode(wmsTaskExecutionLog.getPalletCode());
            wmsPalletOb.setLockBy(String.valueOf(wmsTaskExecutionLog.getTaskId()));
            wmsPalletOb.setLocationCode(wmsTaskExecutionLog.getLocationCode());
            wmsPalletOb.setLastModifiedBy("wsc");
            wmsPalletOb.setGmtModified(wmsTaskExecutionLog.getGmtCreate());
            this.wmsPalletMapper.updateByPalletCode(wmsPalletOb);
            wmsTaskExecutionLog.setOutAddress(wmsTaskExecutionLog.getLocationCode());
            wmsTaskExecutionLog.setTaskType(String.valueOf(TaskType.PRODUCT_CROSS.getTaskType()));
            this.wmsTaskExecutionLogMapper.create(wmsTaskExecutionLog);
            return this.success();
        }
    }

    public List<WmsOrderCrossProductDeail> queryByOrderNo(String orderNo) {
        WmsOrderCrossProductDeail orderCrossProductDeail = new WmsOrderCrossProductDeail();
        orderCrossProductDeail.setOrderNo(orderNo);
        orderCrossProductDeail.setActiveFlag("1");
        List<WmsOrderCrossProductDeail> list = this.wmsOrderCrossProductDeailMapper.queryByAny(orderCrossProductDeail);
        return list;
    }
}
