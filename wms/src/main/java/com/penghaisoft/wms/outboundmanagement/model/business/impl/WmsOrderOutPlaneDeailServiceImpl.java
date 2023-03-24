//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.model.business.impl;

import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsGoodsMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import com.penghaisoft.wms.outboundmanagement.model.business.IWmsOrderOutPlaneDeailService;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutPlaneDeailMapper;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutPlaneMapper;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutPlane;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutPlaneDeail;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsStoragePlaneMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsStoragePlane;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("wmsOrderOutPlaneDeailService")
public class WmsOrderOutPlaneDeailServiceImpl extends BaseService implements IWmsOrderOutPlaneDeailService {
    @Resource
    private WmsOrderOutPlaneDeailMapper wmsOrderOutPlaneDeailMapper;
    @Resource
    private WmsStoragePlaneMapper wmsStoragePlaneMapper;
    @Resource
    private WmsOrderOutPlaneMapper wmsOrderOutPlaneMapper;
    @Autowired
    private IWmsCommonService wmsCommonService;
    @Autowired
    private IUserBusiness userBusiness;
    @Resource
    private WmsGoodsMapper wmsGoodsMapper;

    public WmsOrderOutPlaneDeailServiceImpl() {
    }

    public Resp create(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail) {
        Resp resp = new Resp();
        WmsOrderOutPlane wmsOrderOutPlane = new WmsOrderOutPlane();
        wmsOrderOutPlane.setOrderNo(wmsOrderOutPlaneDeail.getOrderNo());
        wmsOrderOutPlane.setActiveFlag("1");
        List<WmsOrderOutPlane> checkMain = this.wmsOrderOutPlaneMapper.queryByAny(wmsOrderOutPlane);
        if (!"1".equals(((WmsOrderOutPlane)checkMain.get(0)).getOrderStatus())) {
            resp.setCode("1");
            resp.setMsg("主表状态不为创建，不可新增明细");
            return resp;
        } else {
            WmsOrderOutPlaneDeail checkDetail = new WmsOrderOutPlaneDeail();
            checkDetail.setOrderNo(wmsOrderOutPlaneDeail.getOrderNo());
            checkDetail.setGoodsCode(wmsOrderOutPlaneDeail.getGoodsCode());
            checkDetail.setBatchNo(wmsOrderOutPlaneDeail.getBatchNo());
            checkDetail.setLocationCode(wmsOrderOutPlaneDeail.getLocationCode());
            checkDetail.setActiveFlag("1");
            List<WmsOrderOutPlaneDeail> list = this.wmsOrderOutPlaneDeailMapper.queryByAny(checkDetail);
            if (list.size() > 0) {
                resp.setCode("1");
                resp.setMsg("商品编码，批次号，库位已存在");
                return resp;
            } else {
                String goodsName = "";
                WmsGoods wmsGoods = new WmsGoods();
                wmsGoods.setGoodsCode(wmsOrderOutPlaneDeail.getGoodsCode());
                List<WmsGoods> goods = this.wmsGoodsMapper.queryByAny(wmsGoods);
                if (goods.size() > 0) {
                    goodsName = ((WmsGoods)goods.get(0)).getGoodsName();
                }

                wmsOrderOutPlaneDeail.setGoodsName(goodsName);
                this.wmsOrderOutPlaneDeailMapper.create(wmsOrderOutPlaneDeail);
                return this.success();
            }
        }
    }

    public Resp delete(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail) {
        Resp resp = new Resp();
        WmsOrderOutPlaneDeail orderOutPlaneDeail = new WmsOrderOutPlaneDeail();
        orderOutPlaneDeail.setOrderOutDetailId(wmsOrderOutPlaneDeail.getOrderOutDetailId());
        orderOutPlaneDeail.setActiveFlag("1");
        List<WmsOrderOutPlaneDeail> list = this.wmsOrderOutPlaneDeailMapper.queryByAny(orderOutPlaneDeail);
        if (list.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("查询该行项目失败");
            return resp;
        } else {
            String orderNo = ((WmsOrderOutPlaneDeail)list.get(0)).getOrderNo();
            WmsOrderOutPlane wmsOrderOutPlane = new WmsOrderOutPlane();
            wmsOrderOutPlane.setOrderNo(orderNo);
            wmsOrderOutPlane.setActiveFlag("1");
            List<WmsOrderOutPlane> checkMain = this.wmsOrderOutPlaneMapper.queryByAny(wmsOrderOutPlane);
            if (!"1".equals(((WmsOrderOutPlane)checkMain.get(0)).getOrderStatus())) {
                resp.setCode("1");
                resp.setMsg("主表状态不为创建，不可修改明细");
                return resp;
            } else {
                this.wmsOrderOutPlaneDeailMapper.delete(wmsOrderOutPlaneDeail);
                return this.success();
            }
        }
    }

    public Pager<WmsOrderOutPlaneDeail> findListByCondition(int page, int rows, WmsOrderOutPlaneDeail condition) {
        Pager<WmsOrderOutPlaneDeail> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        List<WmsOrderOutPlaneDeail> records = this.wmsOrderOutPlaneDeailMapper.queryList(pager, condition);
        long size = this.wmsOrderOutPlaneDeailMapper.queryCount(condition);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsOrderOutPlaneDeail findById(String id) {
        return (WmsOrderOutPlaneDeail)this.wmsOrderOutPlaneDeailMapper.queryById(id);
    }

    public Resp update(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail) {
        Resp resp = new Resp();
        WmsOrderOutPlaneDeail orderOutPlaneDeail = new WmsOrderOutPlaneDeail();
        orderOutPlaneDeail.setOrderOutDetailId(wmsOrderOutPlaneDeail.getOrderOutDetailId());
        orderOutPlaneDeail.setActiveFlag("1");
        List<WmsOrderOutPlaneDeail> list = this.wmsOrderOutPlaneDeailMapper.queryByAny(orderOutPlaneDeail);
        if (list.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("查询该行项目失败");
            return resp;
        } else {
            String orderNo = ((WmsOrderOutPlaneDeail)list.get(0)).getOrderNo();
            WmsOrderOutPlane wmsOrderOutPlane = new WmsOrderOutPlane();
            wmsOrderOutPlane.setOrderNo(orderNo);
            wmsOrderOutPlane.setActiveFlag("1");
            List<WmsOrderOutPlane> checkMain = this.wmsOrderOutPlaneMapper.queryByAny(wmsOrderOutPlane);
            if (!"1".equals(((WmsOrderOutPlane)checkMain.get(0)).getOrderStatus())) {
                resp.setCode("1");
                resp.setMsg("主表状态不为创建，不可修改明细");
                return resp;
            } else {
                String goodsCode = ((WmsOrderOutPlaneDeail)list.get(0)).getGoodsCode();
                String batchNo = ((WmsOrderOutPlaneDeail)list.get(0)).getBatchNo();
                String locationCode = ((WmsOrderOutPlaneDeail)list.get(0)).getLocationCode();
                if (goodsCode.equals(wmsOrderOutPlaneDeail.getGoodsCode()) && batchNo.equals(wmsOrderOutPlaneDeail.getBatchNo()) && locationCode.equals(wmsOrderOutPlaneDeail.getLocationCode())) {
                    this.wmsOrderOutPlaneDeailMapper.updateBySelect(wmsOrderOutPlaneDeail);
                } else {
                    WmsOrderOutPlaneDeail detail = new WmsOrderOutPlaneDeail();
                    detail.setOrderNo(orderNo);
                    detail.setActiveFlag("1");
                    List<WmsOrderOutPlaneDeail> listDetail = this.wmsOrderOutPlaneDeailMapper.queryByAny(detail);
                    Iterator var13 = listDetail.iterator();

                    while(var13.hasNext()) {
                        WmsOrderOutPlaneDeail planeDeail = (WmsOrderOutPlaneDeail)var13.next();
                        if (wmsOrderOutPlaneDeail.getGoodsCode().equals(planeDeail.getGoodsCode()) && wmsOrderOutPlaneDeail.getBatchNo().equals(planeDeail.getBatchNo()) && wmsOrderOutPlaneDeail.getLocationCode().equals(planeDeail.getLocationCode())) {
                            resp.setCode("1");
                            resp.setMsg("要修改的商品批次库位在订单中重复");
                            return resp;
                        }
                    }

                    String goodsName = "";
                    WmsGoods wmsGoods = new WmsGoods();
                    wmsGoods.setGoodsCode(wmsOrderOutPlaneDeail.getGoodsCode());
                    List<WmsGoods> goods = this.wmsGoodsMapper.queryByAny(wmsGoods);
                    if (goods.size() > 0) {
                        goodsName = ((WmsGoods)goods.get(0)).getGoodsName();
                    }

                    wmsOrderOutPlaneDeail.setGoodsName(goodsName);
                    this.wmsOrderOutPlaneDeailMapper.updateBySelect(wmsOrderOutPlaneDeail);
                }

                return this.success();
            }
        }
    }

    public List<WmsOrderOutPlaneDeail> queryByOrderNo(String orderNo) {
        WmsOrderOutPlaneDeail orderOutPlaneDeail = new WmsOrderOutPlaneDeail();
        orderOutPlaneDeail.setOrderNo(orderNo);
        orderOutPlaneDeail.setActiveFlag("1");
        List<WmsOrderOutPlaneDeail> list = this.wmsOrderOutPlaneDeailMapper.queryByAny(orderOutPlaneDeail);
        return list;
    }

    @Transactional
    public Resp distribution(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail) {
        Resp resp = new Resp();
        WmsOrderOutPlane orderOutPlaneMain = new WmsOrderOutPlane();
        orderOutPlaneMain.setOrderNo(wmsOrderOutPlaneDeail.getOrderNo());
        orderOutPlaneMain.setActiveFlag("1");
        List<WmsOrderOutPlane> mainList = this.wmsOrderOutPlaneMapper.queryByAny(orderOutPlaneMain);
        if (mainList.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("查询不到该出库单");
            return resp;
        } else if (((WmsOrderOutPlane)mainList.get(0)).getOrderStatus() != null) {
            String orderStatus = ((WmsOrderOutPlane)mainList.get(0)).getOrderStatus();
            if (!"1".equals(orderStatus)) {
                resp.setCode("1");
                resp.setMsg("该出库单状态不为创建不能分配库存");
                return resp;
            } else {
                WmsOrderOutPlaneDeail orderOutPlane = new WmsOrderOutPlaneDeail();
                orderOutPlane.setOrderNo(wmsOrderOutPlaneDeail.getOrderNo());
                orderOutPlane.setActiveFlag("1");
                List<WmsOrderOutPlaneDeail> list = this.wmsOrderOutPlaneDeailMapper.queryByAny(orderOutPlane);
                if (list.size() <= 0) {
                    resp.setCode("1");
                    resp.setMsg("出库单不存在明细");
                    return resp;
                } else {
                    boolean checkFlag = true;
                    Iterator var8 = list.iterator();

                    WmsOrderOutPlaneDeail orderOutPlaneDeail;
                    Integer planAmount;
                    Integer availableAmount;
                    do {
                        if (!var8.hasNext()) {
                            var8 = list.iterator();

                            while(var8.hasNext()) {
                                orderOutPlaneDeail = (WmsOrderOutPlaneDeail)var8.next();
                                WmsStoragePlane storageDetail = new WmsStoragePlane();
                                storageDetail.setGoodsCode(orderOutPlaneDeail.getGoodsCode());
                                storageDetail.setBatchNo(orderOutPlaneDeail.getBatchNo());
                                storageDetail.setLocationCode(orderOutPlaneDeail.getLocationCode());
                                storageDetail.setActiveFlag("1");
                                List<WmsStoragePlane> records = this.wmsStoragePlaneMapper.queryByAny(storageDetail);
                                if (((WmsStoragePlane)records.get(0)).getAvailableAmount() < orderOutPlaneDeail.getPlanAmount()) {
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                    resp.setCode("1");
                                    resp.setMsg("可用数量不足");
                                    return resp;
                                }

                                int avaAmout = ((WmsStoragePlane)records.get(0)).getAvailableAmount() - orderOutPlaneDeail.getPlanAmount();
                                int lockAmout = ((WmsStoragePlane)records.get(0)).getLockAmount() + orderOutPlaneDeail.getPlanAmount();
                                storageDetail.setAvailableAmount(avaAmout);
                                storageDetail.setLockAmount(lockAmout);
                                storageDetail.setStoragePlaneId(((WmsStoragePlane)records.get(0)).getStoragePlaneId());
                                Integer update = this.wmsStoragePlaneMapper.updateBySelect(storageDetail);
                                if (update <= 0) {
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                }
                            }

                            orderOutPlaneMain.setOrderOutId(wmsOrderOutPlaneDeail.getOrderOutId());
                            orderOutPlaneMain.setOrderStatus("2");
                            Integer changeNum = this.wmsOrderOutPlaneMapper.updateBySelect(orderOutPlaneMain);
                            if (changeNum <= 0) {
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            }

                            return this.success();
                        }

                        orderOutPlaneDeail = (WmsOrderOutPlaneDeail)var8.next();
                        planAmount = orderOutPlaneDeail.getPlanAmount();
                        WmsStoragePlane storagePlane = new WmsStoragePlane();
                        storagePlane.setGoodsCode(orderOutPlaneDeail.getGoodsCode());
                        storagePlane.setBatchNo(orderOutPlaneDeail.getBatchNo());
                        storagePlane.setLocationCode(orderOutPlaneDeail.getLocationCode());
                        storagePlane.setActiveFlag("1");
                        List<WmsStoragePlane> records = this.wmsStoragePlaneMapper.queryByAny(storagePlane);
                        if (records.size() <= 0) {
                            resp.setCode("1");
                            resp.setMsg("商品" + orderOutPlaneDeail.getGoodsCode() + "批次" + orderOutPlaneDeail.getBatchNo() + "库位" + orderOutPlaneDeail.getLocationCode() + "查询不到库存");
                            return resp;
                        }

                        availableAmount = ((WmsStoragePlane)records.get(0)).getAvailableAmount();
                    } while(availableAmount >= planAmount);

                    resp.setCode("1");
                    resp.setMsg("商品" + orderOutPlaneDeail.getGoodsCode() + "批次" + orderOutPlaneDeail.getBatchNo() + "库位" + orderOutPlaneDeail.getLocationCode() + "可用数量不足");
                    return resp;
                }
            }
        } else {
            resp.setCode("1");
            resp.setMsg("出库单状态异常");
            return resp;
        }
    }

    public Resp cancel(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail) {
        Resp resp = new Resp();
        WmsOrderOutPlane orderOutPlaneMain = new WmsOrderOutPlane();
        orderOutPlaneMain.setOrderNo(wmsOrderOutPlaneDeail.getOrderNo());
        orderOutPlaneMain.setActiveFlag("1");
        List<WmsOrderOutPlane> mainList = this.wmsOrderOutPlaneMapper.queryByAny(orderOutPlaneMain);
        if (mainList.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("查询不到该出库单");
            return resp;
        } else if (((WmsOrderOutPlane)mainList.get(0)).getOrderStatus() != null) {
            String orderStatus = ((WmsOrderOutPlane)mainList.get(0)).getOrderStatus();
            if (!"2".equals(orderStatus)) {
                resp.setCode("1");
                resp.setMsg("该出库单状态不为分配不能取消");
                return resp;
            } else {
                WmsOrderOutPlaneDeail orderOutPlane = new WmsOrderOutPlaneDeail();
                orderOutPlane.setOrderNo(wmsOrderOutPlaneDeail.getOrderNo());
                orderOutPlane.setActiveFlag("1");
                List<WmsOrderOutPlaneDeail> list = this.wmsOrderOutPlaneDeailMapper.queryByAny(orderOutPlane);
                if (list.size() <= 0) {
                    resp.setCode("1");
                    resp.setMsg("出库单不存在明细");
                    return resp;
                } else {
                    Iterator var7 = list.iterator();

                    WmsOrderOutPlaneDeail orderOutPlaneDeail;
                    Integer planAmount;
                    Integer lockAmount;
                    do {
                        if (!var7.hasNext()) {
                            var7 = list.iterator();

                            while(var7.hasNext()) {
                                orderOutPlaneDeail = (WmsOrderOutPlaneDeail)var7.next();
                                WmsStoragePlane storageDetail = new WmsStoragePlane();
                                storageDetail.setGoodsCode(orderOutPlaneDeail.getGoodsCode());
                                storageDetail.setBatchNo(orderOutPlaneDeail.getBatchNo());
                                storageDetail.setLocationCode(orderOutPlaneDeail.getLocationCode());
                                storageDetail.setActiveFlag("1");
                                List<WmsStoragePlane> records = this.wmsStoragePlaneMapper.queryByAny(storageDetail);
                                if (((WmsStoragePlane)records.get(0)).getLockAmount() < orderOutPlaneDeail.getPlanAmount()) {
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                    resp.setCode("1");
                                    resp.setMsg("冻结数量不足");
                                    return resp;
                                }

                                int avaAmout = ((WmsStoragePlane)records.get(0)).getAvailableAmount() + orderOutPlaneDeail.getPlanAmount();
                                int lockAmout = ((WmsStoragePlane)records.get(0)).getLockAmount() - orderOutPlaneDeail.getPlanAmount();
                                storageDetail.setAvailableAmount(avaAmout);
                                storageDetail.setLockAmount(lockAmout);
                                storageDetail.setStoragePlaneId(((WmsStoragePlane)records.get(0)).getStoragePlaneId());
                                Integer update = this.wmsStoragePlaneMapper.updateBySelect(storageDetail);
                                if (update <= 0) {
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                }
                            }

                            orderOutPlaneMain.setOrderOutId(wmsOrderOutPlaneDeail.getOrderOutId());
                            orderOutPlaneMain.setOrderStatus("1");
                            orderOutPlaneMain.setCancelFlag("1");
                            Integer changeNum = this.wmsOrderOutPlaneMapper.updateBySelect(orderOutPlaneMain);
                            if (changeNum <= 0) {
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            }

                            return this.success();
                        }

                        orderOutPlaneDeail = (WmsOrderOutPlaneDeail)var7.next();
                        planAmount = orderOutPlaneDeail.getPlanAmount();
                        WmsStoragePlane storagePlane = new WmsStoragePlane();
                        storagePlane.setGoodsCode(orderOutPlaneDeail.getGoodsCode());
                        storagePlane.setBatchNo(orderOutPlaneDeail.getBatchNo());
                        storagePlane.setLocationCode(orderOutPlaneDeail.getLocationCode());
                        storagePlane.setActiveFlag("1");
                        List<WmsStoragePlane> records = this.wmsStoragePlaneMapper.queryByAny(storagePlane);
                        if (records.size() <= 0) {
                            resp.setCode("1");
                            resp.setMsg("商品" + orderOutPlaneDeail.getGoodsCode() + "批次" + orderOutPlaneDeail.getBatchNo() + "库位" + orderOutPlaneDeail.getLocationCode() + "查询不到库存");
                            return resp;
                        }

                        lockAmount = ((WmsStoragePlane)records.get(0)).getLockAmount();
                    } while(lockAmount >= planAmount);

                    resp.setCode("1");
                    resp.setMsg("商品" + orderOutPlaneDeail.getGoodsCode() + "批次" + orderOutPlaneDeail.getBatchNo() + "库位" + orderOutPlaneDeail.getLocationCode() + "冻结数量不足");
                    return resp;
                }
            }
        } else {
            resp.setCode("1");
            resp.setMsg("出库单状态异常");
            return resp;
        }
    }

    @Transactional
    public Resp combineOrder(String ids) {
        Resp resp = new Resp();
        List<String> list = new ArrayList();
        String[] idValue = ids.split(",");
        String[] var5 = idValue;
        int var6 = idValue.length;

        String loginName;
        for(int var7 = 0; var7 < var6; ++var7) {
            loginName = var5[var7];
            if (loginName != null && !"".equals(loginName)) {
                list.add(loginName);
            }
        }

        if (list.size() < 2) {
            resp.setCode("1");
            resp.setMsg("请选择两条以上出库单");
            return resp;
        } else {
            List<WmsOrderOutPlane> planes = new ArrayList();
            List<String> orderList = new ArrayList();
            String orderType = "";
            Iterator var22 = list.iterator();

            while(var22.hasNext()) {
                String id = (String)var22.next();
                WmsOrderOutPlane orderOutPlaneMain = new WmsOrderOutPlane();
                orderOutPlaneMain.setOrderOutId(id);
                orderOutPlaneMain.setActiveFlag("1");
                List<WmsOrderOutPlane> mainList = this.wmsOrderOutPlaneMapper.queryByAny(orderOutPlaneMain);
                if (mainList.size() <= 0) {
                    resp.setCode("1");
                    resp.setMsg("查询不到出库单信息");
                    return resp;
                }

                String orderStatus = ((WmsOrderOutPlane)mainList.get(0)).getOrderStatus();
                if (!"2".equals(orderStatus)) {
                    resp.setCode("1");
                    resp.setMsg("出库单" + ((WmsOrderOutPlane)mainList.get(0)).getOrderNo() + "单据状态不为分配");
                    return resp;
                }

                if (orderType == "") {
                    orderType = ((WmsOrderOutPlane)mainList.get(0)).getOrderType();
                }

                if (!orderType.equals(((WmsOrderOutPlane)mainList.get(0)).getOrderType())) {
                    resp.setCode("1");
                    resp.setMsg("出库单据类型不统一");
                    return resp;
                }

                planes.add(mainList.get(0));
                orderList.add(((WmsOrderOutPlane)mainList.get(0)).getOrderNo());
            }

            loginName = this.userBusiness.getCurrentUser().getNickname();
            WmsOrderOutPlane orderOutPlane = new WmsOrderOutPlane();
            String uuid = CommonUtils.getUUID();
            orderOutPlane.setOrderOutId(uuid);
            String orderNo = this.wmsCommonService.getOrderNoByType("POC");
            orderOutPlane.setOrderNo(orderNo);
            orderOutPlane.setOrderType(orderType);
            orderOutPlane.setOrderStatus("2");
            orderOutPlane.setCancelFlag("0");
            orderOutPlane.setActiveFlag("1");
            orderOutPlane.setCreateBy(loginName);
            Date now = new Date();
            orderOutPlane.setGmtCreate(now);
            this.wmsOrderOutPlaneMapper.create(orderOutPlane);
            List<WmsOrderOutPlaneDeail> selectList = this.wmsOrderOutPlaneDeailMapper.selectCombine(planes);
            String account = this.userBusiness.getCurrentUser().getNickname();
            Iterator var16 = selectList.iterator();

            while(var16.hasNext()) {
                WmsOrderOutPlaneDeail detail = (WmsOrderOutPlaneDeail)var16.next();
                detail.setOrderOutDetailId(CommonUtils.getUUID());
                detail.setOrderOutId(uuid);
                detail.setOrderNo(orderNo);
                detail.setCreateBy(loginName);
                detail.setGmtCreate(now);
                detail.setCreateBy(account);
            }

            this.wmsOrderOutPlaneDeailMapper.batchInsert(selectList);
            this.wmsOrderOutPlaneDeailMapper.deleteDetail(orderList);
            this.wmsOrderOutPlaneMapper.deleteMain(orderList);
            return this.success();
        }
    }
}
