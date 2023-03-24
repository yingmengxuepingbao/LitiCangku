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
import com.penghaisoft.wms.inboundmanagement.model.business.IWmsOrderInService;
import com.penghaisoft.wms.inboundmanagement.model.dao.WmsOrderInMapper;
import com.penghaisoft.wms.inboundmanagement.model.entity.WmsOrderIn;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("wmsOrderInService")
public class WmsOrderInServiceImpl extends BaseService implements IWmsOrderInService {
    @Resource
    private WmsOrderInMapper wmsOrderInMapper;
    @Resource
    private WmsGoodsMapper wmsGoodsMapper;

    public WmsOrderInServiceImpl() {
    }

    public Resp create(WmsOrderIn wmsOrderIn) {
        this.wmsOrderInMapper.create(wmsOrderIn);
        return this.success();
    }

    public Resp delete(WmsOrderIn wmsOrderIn) {
        this.wmsOrderInMapper.delete(wmsOrderIn);
        return this.success();
    }

    public Pager<WmsOrderIn> findListByCondition(int page, int rows, WmsOrderIn condition) {
        Pager<WmsOrderIn> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        List<WmsOrderIn> records = this.wmsOrderInMapper.queryList(pager, condition);
        long size = this.wmsOrderInMapper.queryCount(condition);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsOrderIn findById(String id) {
        return (WmsOrderIn)this.wmsOrderInMapper.queryById(id);
    }

    public Resp update(WmsOrderIn wmsOrderIn) {
        Resp resp = new Resp();
        WmsOrderIn orderIn = new WmsOrderIn();
        orderIn.setOrderInId(wmsOrderIn.getOrderInId());
        List<WmsOrderIn> list = this.wmsOrderInMapper.queryByAny(orderIn);
        if (list.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("查询该行项目失败");
            return resp;
        } else {
            String orderStatus = ((WmsOrderIn)list.get(0)).getOrderStatus();
            if (!orderStatus.equals("1")) {
                resp.setCode("1");
                resp.setMsg("单据状态不为创建不可修改");
                return resp;
            } else {
                String goodsCode = ((WmsOrderIn)list.get(0)).getGoodsCode();
                if (goodsCode.equals(wmsOrderIn.getGoodsCode())) {
                    this.wmsOrderInMapper.updateBySelect(wmsOrderIn);
                } else {
                    WmsOrderIn orderInDiff = new WmsOrderIn();
                    orderInDiff.setOrderNo(wmsOrderIn.getOrderNo());
                    List<WmsOrderIn> orderInList = this.wmsOrderInMapper.queryByAny(orderInDiff);
                    Iterator var9 = orderInList.iterator();

                    while(var9.hasNext()) {
                        WmsOrderIn order = (WmsOrderIn)var9.next();
                        if (wmsOrderIn.getGoodsCode().equals(order.getGoodsCode())) {
                            resp.setCode("1");
                            resp.setMsg("要修改的商品在订单中重复");
                            return resp;
                        }
                    }

                    String goodsName = "";
                    WmsGoods wmsGoods = new WmsGoods();
                    wmsGoods.setGoodsCode(wmsOrderIn.getGoodsCode());
                    List<WmsGoods> goods = this.wmsGoodsMapper.queryByAny(wmsGoods);
                    if (goods.size() > 0) {
                        goodsName = ((WmsGoods)goods.get(0)).getGoodsName();
                    }

                    wmsOrderIn.setGoodsName(goodsName);
                    this.wmsOrderInMapper.updateBySelect(wmsOrderIn);
                }

                return this.success();
            }
        }
    }

    @Transactional
    public Resp creatOrderIn(List<WmsOrderIn> orderInList) {
        Resp resp = new Resp();
        int createNum = this.wmsOrderInMapper.batchInsert(orderInList);
        if (createNum != orderInList.size()) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            resp.setCode("1");
            resp.setMsg("插入错误");
            return resp;
        } else {
            return this.success();
        }
    }
}
