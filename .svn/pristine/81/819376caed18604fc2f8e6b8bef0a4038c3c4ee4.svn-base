//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.inboundmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsGoodsMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods;
import com.penghaisoft.wms.inboundmanagement.model.business.IWmsOrderCrossInDeailService;
import com.penghaisoft.wms.inboundmanagement.model.dao.WmsOrderCrossInDeailMapper;
import com.penghaisoft.wms.inboundmanagement.model.dao.WmsOrderCrossInMapper;
import com.penghaisoft.wms.inboundmanagement.model.entity.WmsOrderCrossIn;
import com.penghaisoft.wms.inboundmanagement.model.entity.WmsOrderCrossInDeail;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsOrderCrossInDeailService")
public class WmsOrderCrossInDeailServiceImpl extends BaseService implements IWmsOrderCrossInDeailService {
    @Resource
    private WmsOrderCrossInDeailMapper wmsOrderCrossInDeailMapper;
    @Resource
    private WmsOrderCrossInMapper wmsOrderCrossInMapper;
    @Resource
    private WmsGoodsMapper wmsGoodsMapper;

    public WmsOrderCrossInDeailServiceImpl() {
    }

    public Resp create(WmsOrderCrossInDeail wmsOrderCrossInDeail) {
        Resp resp = new Resp();
        WmsOrderCrossIn wmsOrderCrossIn = new WmsOrderCrossIn();
        wmsOrderCrossIn.setOrderNo(wmsOrderCrossInDeail.getOrderNo());
        wmsOrderCrossIn.setActiveFlag("1");
        List<WmsOrderCrossIn> checkMain = this.wmsOrderCrossInMapper.queryByAny(wmsOrderCrossIn);
        if (!"1".equals(((WmsOrderCrossIn)checkMain.get(0)).getOrderStatus())) {
            resp.setCode("1");
            resp.setMsg("主表状态不为创建，不可新增明细");
            return resp;
        } else {
            WmsOrderCrossInDeail checkDetail = new WmsOrderCrossInDeail();
            checkDetail.setOrderNo(wmsOrderCrossInDeail.getOrderNo());
            checkDetail.setGoodsCode(wmsOrderCrossInDeail.getGoodsCode());
            checkDetail.setActiveFlag("1");
            List<WmsOrderCrossInDeail> list = this.wmsOrderCrossInDeailMapper.queryByAny(checkDetail);
            if (list.size() > 0) {
                resp.setCode("1");
                resp.setMsg("商品编码已存在");
                return resp;
            } else {
                String goodsName = "";
                WmsGoods wmsGoods = new WmsGoods();
                wmsGoods.setGoodsCode(wmsOrderCrossInDeail.getGoodsCode());
                List<WmsGoods> goods = this.wmsGoodsMapper.queryByAny(wmsGoods);
                if (goods.size() > 0) {
                    goodsName = ((WmsGoods)goods.get(0)).getGoodsName();
                }

                wmsOrderCrossInDeail.setGoodsName(goodsName);
                wmsOrderCrossInDeail.setRealAmount(0);
                this.wmsOrderCrossInDeailMapper.create(wmsOrderCrossInDeail);
                return this.success();
            }
        }
    }

    public Resp delete(WmsOrderCrossInDeail wmsOrderCrossInDeail) {
        Resp resp = new Resp();
        WmsOrderCrossInDeail orderCrossInDeail = new WmsOrderCrossInDeail();
        orderCrossInDeail.setOrderCrossInDetailId(wmsOrderCrossInDeail.getOrderCrossInDetailId());
        orderCrossInDeail.setActiveFlag("1");
        List<WmsOrderCrossInDeail> list = this.wmsOrderCrossInDeailMapper.queryByAny(orderCrossInDeail);
        if (list.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("查询该行项目失败");
            return resp;
        } else {
            String orderNo = ((WmsOrderCrossInDeail)list.get(0)).getOrderNo();
            WmsOrderCrossIn orderCrossIn = new WmsOrderCrossIn();
            orderCrossIn.setOrderNo(orderNo);
            orderCrossIn.setActiveFlag("1");
            List<WmsOrderCrossIn> checkMain = this.wmsOrderCrossInMapper.queryByAny(orderCrossIn);
            if (!"1".equals(((WmsOrderCrossIn)checkMain.get(0)).getOrderStatus())) {
                resp.setCode("1");
                resp.setMsg("主表状态不为创建，不可删除明细");
                return resp;
            } else {
                this.wmsOrderCrossInDeailMapper.delete(wmsOrderCrossInDeail);
                return this.success();
            }
        }
    }

    public Pager<WmsOrderCrossInDeail> findListByCondition(int page, int rows, WmsOrderCrossInDeail condition) {
        Pager<WmsOrderCrossInDeail> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        List<WmsOrderCrossInDeail> records = this.wmsOrderCrossInDeailMapper.queryList(pager, condition);
        long size = this.wmsOrderCrossInDeailMapper.queryCount(condition);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsOrderCrossInDeail findById(String id) {
        return (WmsOrderCrossInDeail)this.wmsOrderCrossInDeailMapper.queryById(id);
    }

    public Resp update(WmsOrderCrossInDeail wmsOrderCrossInDeail) {
        Resp resp = new Resp();
        WmsOrderCrossInDeail orderCrossInDeail = new WmsOrderCrossInDeail();
        orderCrossInDeail.setOrderCrossInDetailId(wmsOrderCrossInDeail.getOrderCrossInDetailId());
        orderCrossInDeail.setActiveFlag("1");
        List<WmsOrderCrossInDeail> list = this.wmsOrderCrossInDeailMapper.queryByAny(orderCrossInDeail);
        if (list.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("查询该行项目失败");
            return resp;
        } else {
            String orderNo = ((WmsOrderCrossInDeail)list.get(0)).getOrderNo();
            WmsOrderCrossIn orderCrossIn = new WmsOrderCrossIn();
            orderCrossIn.setOrderNo(orderNo);
            orderCrossIn.setActiveFlag("1");
            List<WmsOrderCrossIn> checkMain = this.wmsOrderCrossInMapper.queryByAny(orderCrossIn);
            if (!"1".equals(((WmsOrderCrossIn)checkMain.get(0)).getOrderStatus())) {
                resp.setCode("1");
                resp.setMsg("主表状态不为创建，不可修改明细");
                return resp;
            } else {
                String goodsCode = ((WmsOrderCrossInDeail)list.get(0)).getGoodsCode();
                if (goodsCode.equals(wmsOrderCrossInDeail.getGoodsCode())) {
                    this.wmsOrderCrossInDeailMapper.updateBySelect(wmsOrderCrossInDeail);
                } else {
                    WmsOrderCrossInDeail detail = new WmsOrderCrossInDeail();
                    detail.setOrderNo(orderNo);
                    detail.setActiveFlag("1");
                    List<WmsOrderCrossInDeail> listDetail = this.wmsOrderCrossInDeailMapper.queryByAny(detail);
                    Iterator var11 = listDetail.iterator();

                    while(var11.hasNext()) {
                        WmsOrderCrossInDeail planeDeail = (WmsOrderCrossInDeail)var11.next();
                        if (wmsOrderCrossInDeail.getGoodsCode().equals(planeDeail.getGoodsCode())) {
                            resp.setCode("1");
                            resp.setMsg("要修改的商品在订单中重复");
                            return resp;
                        }
                    }

                    String goodsName = "";
                    WmsGoods wmsGoods = new WmsGoods();
                    wmsGoods.setGoodsCode(wmsOrderCrossInDeail.getGoodsCode());
                    List<WmsGoods> goods = this.wmsGoodsMapper.queryByAny(wmsGoods);
                    if (goods.size() > 0) {
                        goodsName = ((WmsGoods)goods.get(0)).getGoodsName();
                    }

                    wmsOrderCrossInDeail.setGoodsName(goodsName);
                    this.wmsOrderCrossInDeailMapper.updateBySelect(wmsOrderCrossInDeail);
                }

                return this.success();
            }
        }
    }

    public List<WmsOrderCrossInDeail> queryByOrderNo(String orderNo) {
        WmsOrderCrossInDeail orderCrossInDeail = new WmsOrderCrossInDeail();
        orderCrossInDeail.setOrderNo(orderNo);
        orderCrossInDeail.setActiveFlag("1");
        List<WmsOrderCrossInDeail> list = this.wmsOrderCrossInDeailMapper.queryByAny(orderCrossInDeail);
        return list;
    }
}
