//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.model.business.impl;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsLocationStereoscopicMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsMoveTask;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsOutTask;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import com.penghaisoft.wms.outboundmanagement.model.business.IWmsOrderOutStereoscopicService;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicDeailMapper;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicMapper;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopicDeail;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsTaskExecutionLogMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("wmsOrderOutStereoscopicService")
public class WmsOrderOutStereoscopicServiceImpl extends BaseService implements IWmsOrderOutStereoscopicService {
    @Resource
    private WmsOrderOutStereoscopicMapper wmsOrderOutStereoscopicMapper;
    @Resource
    private WmsOrderOutStereoscopicDeailMapper wmsOrderOutStereoscopicDeailMapper;
    @Resource
    private WmsLocationStereoscopicMapper wmsLocationStereoscopicMapper;
    @Resource
    private WmsPalletMapper wmsPalletMapper;
    @Resource
    private WmsTaskExecutionLogMapper wmsTaskExecutionLogMapper;
    @Autowired
    private IWmsCommonService wmsCommonService;



    public WmsOrderOutStereoscopicServiceImpl() {
    }

    public Resp create(WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        this.wmsOrderOutStereoscopicMapper.create(wmsOrderOutStereoscopic);
        return this.success();
    }

    public Resp delete(WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        wmsOrderOutStereoscopic.setActiveFlag("0");
        this.wmsOrderOutStereoscopicMapper.updateByOrderNo(wmsOrderOutStereoscopic);

        WmsOrderOutStereoscopicDeail updateOb = new WmsOrderOutStereoscopicDeail();
        updateOb.setOrderNo(wmsOrderOutStereoscopic.getOrderNo());
        updateOb.setActiveFlag("0");
        updateOb.setLastModifiedBy(wmsOrderOutStereoscopic.getLastModifiedBy());
        updateOb.setGmtModified(wmsOrderOutStereoscopic.getGmtModified());
        this.wmsOrderOutStereoscopicDeailMapper.updateByOrderNo(updateOb);
        //现场修改 1、删除任务，修改地图状态
        WmsTaskExecutionLog taskLog =new WmsTaskExecutionLog();
        taskLog.setOrderNo(wmsOrderOutStereoscopic.getOrderNo());
        List<WmsTaskExecutionLog> taskLogList = this.wmsTaskExecutionLogMapper.queryByAny(taskLog);
        if(taskLogList!=null && taskLogList.size()>0){
            for(WmsTaskExecutionLog wmsTaskExecutionLog : taskLogList) {
                //如果删除的状态为1，将状态复原。
                if("1".equals(wmsTaskExecutionLog.getTaskStatus())) {
                    WmsLocationStereoscopic wmsLocationStereoscopic = new WmsLocationStereoscopic();
                    wmsLocationStereoscopic.setLocationCode(wmsTaskExecutionLog.getLocationCode());
                    //过账 - 出库任务删除，将库位状态修改为占用
                    wmsLocationStereoscopic.setUseStatus("3");
                    wmsLocationStereoscopic.setGmtModified(new Date());
                    wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopic);

                    wmsTaskExecutionLog.setLastModifiedBy(wmsOrderOutStereoscopic.getLastModifiedBy());
                    wmsTaskExecutionLog.setGmtModified(new Date());
                    //将active_flag设置为0 逻辑删除
                    wmsTaskExecutionLogMapper.delete(wmsTaskExecutionLog);
                }

            }
        }
        return this.success();
    }

    public Pager<WmsOrderOutStereoscopic> findListByCondition(int page, int rows, WmsOrderOutStereoscopic condition) {
        Pager<WmsOrderOutStereoscopic> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsOrderOutStereoscopicMapper.queryCount(condition);
        List<WmsOrderOutStereoscopic> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsOrderOutStereoscopicMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsOrderOutStereoscopic findById(String id) {
        return (WmsOrderOutStereoscopic)this.wmsOrderOutStereoscopicMapper.queryById(id);
    }

    public Resp update(WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        this.wmsOrderOutStereoscopicMapper.updateBySelect(wmsOrderOutStereoscopic);
        return this.success();
    }

    public Resp batchUpdate(WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        this.wmsOrderOutStereoscopicMapper.updateBySelectBatch(wmsOrderOutStereoscopic);
        return this.success();
    }

    @Transactional
    public Resp batchMerge(WmsOrderOutStereoscopic wmsOrderOutStereoscopic, String outAddress, String currentUser) {
        Date now = new Date();
        String orderNo = this.wmsCommonService.getOrderNoByType("SOC");
        List<String> orderNoList = new ArrayList();
        WmsOrderOutStereoscopic createOb = new WmsOrderOutStereoscopic();
        createOb.setOrderOutId(CommonUtils.getUUID());
        createOb.setOrderNo(orderNo);
        createOb.setOrderType("2");
        createOb.setOrderStatus("1");
        createOb.setOutAddress(outAddress);
        createOb.setCreateBy(currentUser);
        createOb.setGmtCreate(now);
        createOb.setActiveFlag("1");
        this.wmsOrderOutStereoscopicMapper.create(createOb);
        WmsOrderOutStereoscopic searchOb = new WmsOrderOutStereoscopic();
        searchOb.setOrderOutIdList(wmsOrderOutStereoscopic.getOrderOutIdList());
        searchOb.setActiveFlag("1");
        List<WmsOrderOutStereoscopic> list = this.wmsOrderOutStereoscopicMapper.queryByAny(searchOb);
        if (list != null && !list.isEmpty()) {
            Iterator var10 = list.iterator();

            while(var10.hasNext()) {
                WmsOrderOutStereoscopic tmp = (WmsOrderOutStereoscopic)var10.next();
                orderNoList.add(tmp.getOrderNo());
            }
        }

        WmsOrderOutStereoscopicDeail searchDetailOb = new WmsOrderOutStereoscopicDeail();
        searchDetailOb.setOrderNoList(orderNoList);
        List<WmsOrderOutStereoscopicDeail> insertDetailList = this.wmsOrderOutStereoscopicDeailMapper.queryMergeList(searchDetailOb);
        Iterator var12 = insertDetailList.iterator();

        WmsOrderOutStereoscopicDeail updateDetailOb;
        while(var12.hasNext()) {
            updateDetailOb = (WmsOrderOutStereoscopicDeail)var12.next();
            updateDetailOb.setOrderOutDetailId(CommonUtils.getUUID());
            updateDetailOb.setOrderNo(orderNo);
            updateDetailOb.setCreateBy(currentUser);
            updateDetailOb.setGmtCreate(now);
            updateDetailOb.setActiveFlag("1");
        }

        this.wmsOrderOutStereoscopicDeailMapper.batchInsert(insertDetailList);
        WmsOrderOutStereoscopic updateOb = new WmsOrderOutStereoscopic();
        updateOb.setOrderOutIdList(wmsOrderOutStereoscopic.getOrderOutIdList());
        updateOb.setActiveFlag("0");
        updateOb.setLastModifiedBy(currentUser);
        updateOb.setGmtModified(now);
        this.wmsOrderOutStereoscopicMapper.updateBySelectBatch(updateOb);
        updateDetailOb = new WmsOrderOutStereoscopicDeail();
        updateDetailOb.setOrderNoList(orderNoList);
        updateDetailOb.setActiveFlag("0");
        updateDetailOb.setLastModifiedBy(currentUser);
        updateDetailOb.setGmtModified(now);
        this.wmsOrderOutStereoscopicDeailMapper.updateByOrderNoBatch(updateDetailOb);
        return this.success();
    }

    public List<WmsOrderOutStereoscopic> queryByAny(WmsOrderOutStereoscopic condition) {
        return this.wmsOrderOutStereoscopicMapper.queryByAny(condition);
    }
/**
 *功能描述: 根据出库单，出库明细，停止出库任务
 * @params
 * @return com.penghaisoft.framework.util.Resp
 */
    @Transactional
    public Resp createAndEditDeal(WmsOrderOutStereoscopic wmsOrderOutStereoscopic, String currentUser) {
        Date now = new Date();
        List<WmsOrderOutStereoscopicDeail> list = wmsOrderOutStereoscopic.getWmsOrderOutStereoscopicDeailList();
        Iterator var6;
        WmsOrderOutStereoscopicDeail tmp;
        if (wmsOrderOutStereoscopic.getOrderNo() != null && !"".equals(wmsOrderOutStereoscopic.getOrderNo())) {
            wmsOrderOutStereoscopic.setLastModifiedBy(currentUser);
            wmsOrderOutStereoscopic.setGmtModified(now);
            this.wmsOrderOutStereoscopicMapper.updateByOrderNo(wmsOrderOutStereoscopic);
            WmsOrderOutStereoscopicDeail updateOb = new WmsOrderOutStereoscopicDeail();
            updateOb.setOrderNo(wmsOrderOutStereoscopic.getOrderNo());
            updateOb.setActiveFlag("0");
            updateOb.setLastModifiedBy(currentUser);
            updateOb.setGmtModified(now);
            //区别原材料还是成品
            updateOb.setUserDefined3(wmsOrderOutStereoscopic.getUserDefined2());
            this.wmsOrderOutStereoscopicDeailMapper.updateByOrderNo(updateOb);
            if (list != null && !list.isEmpty()) {
                var6 = list.iterator();

                while(var6.hasNext()) {
                    tmp = (WmsOrderOutStereoscopicDeail)var6.next();
                    tmp.setOrderOutDetailId(CommonUtils.getUUID());
                    tmp.setOrderNo(wmsOrderOutStereoscopic.getOrderNo());
                    tmp.setCreateBy(currentUser);
                    tmp.setGmtCreate(now);
                    tmp.setLastModifiedBy((String)null);
                    tmp.setGmtModified((Date)null);
                    tmp.setActiveFlag("1");
                }
            }

            this.wmsOrderOutStereoscopicDeailMapper.batchInsert(list);
        } else {
            String orderNo;
            if(wmsOrderOutStereoscopic.getSapOrderNo()!=null &&!"".equals(wmsOrderOutStereoscopic.getSapOrderNo())){
                orderNo=wmsOrderOutStereoscopic.getSapOrderNo();
            }else{
                orderNo = this.wmsCommonService.getOrderNoByType("SO");
            }
            wmsOrderOutStereoscopic.setOrderNo(orderNo);
            wmsOrderOutStereoscopic.setOrderOutId(CommonUtils.getUUID());
            wmsOrderOutStereoscopic.setCreateBy(currentUser);
            wmsOrderOutStereoscopic.setGmtCreate(now);
            wmsOrderOutStereoscopic.setActiveFlag("1");
            wmsOrderOutStereoscopic.setOrderStatus("1");
            this.wmsOrderOutStereoscopicMapper.create(wmsOrderOutStereoscopic);
            if (list != null && !list.isEmpty()) {
                var6 = list.iterator();

                while(var6.hasNext()) {
                    tmp = (WmsOrderOutStereoscopicDeail)var6.next();
                    tmp.setOrderOutDetailId(CommonUtils.getUUID());
                    tmp.setOrderNo(orderNo);
                    tmp.setCreateBy(currentUser);
                    tmp.setUserDefined3(wmsOrderOutStereoscopic.getUserDefined2());
                    tmp.setGmtCreate(now);
                    tmp.setLastModifiedBy((String)null);
                    tmp.setGmtModified((Date)null);
                    tmp.setActiveFlag("1");
                }
            }

            this.wmsOrderOutStereoscopicDeailMapper.batchInsert(list);
        }

        return this.success();
    }

    public Resp checkStatusAmount(WmsOrderOutStereoscopic condition) {
        Resp resp = new Resp();
        List<String> orderNoList = condition.getOrderNoList();
        //查询，立库出库单表,出库到暂存区
        List<WmsOrderOutStereoscopic> list = this.wmsOrderOutStereoscopicMapper.queryByAny(condition);
        //判断出库单状态
        if (list != null && !list.isEmpty()) {
            Iterator var5 = list.iterator();

            while(var5.hasNext()) {
                WmsOrderOutStereoscopic tmp = (WmsOrderOutStereoscopic)var5.next();
                //订单状态：1创建2启动3完成4异常5启动失败
                if (tmp.getOrderStatus() != null && "2".equals(tmp.getOrderStatus())) {
                    resp.setCode(RESULT.FAILED.code);
                    resp.setMsg("该出库单包含已经启动的记录！");
                    return resp;
                }

                if (tmp.getOrderStatus() != null && "3".equals(tmp.getOrderStatus())) {
                    resp.setCode(RESULT.FAILED.code);
                    resp.setMsg("该出库单包含已经完成的记录！");
                    return resp;
                }
            }
        }

        WmsOrderOutStereoscopicDeail searchDetailOb = new WmsOrderOutStereoscopicDeail();
        searchDetailOb.setOrderNoList(orderNoList);
        //根据出库单单据编号 查询 立库出库单明细表
        List<WmsOrderOutStereoscopicDeail> detailList = this.wmsOrderOutStereoscopicDeailMapper.queryMergeList(searchDetailOb);
        if (detailList != null && !detailList.isEmpty()) {
            Iterator var7 = detailList.iterator();

            while(var7.hasNext()) {
                WmsOrderOutStereoscopicDeail tmp = (WmsOrderOutStereoscopicDeail)var7.next();
                Integer planAmount = tmp.getPlanAmount() == null ? 0 : tmp.getPlanAmount();
                Integer useAbleAmount = 0;
                WmsLocationStereoscopic location = new WmsLocationStereoscopic();
                location.setGoodsCode(tmp.getGoodsCode());
                location.setBatchNo(tmp.getBatchNo());
                //查询商品对应的可用数量/重量
                WmsLocationStereoscopic wmsLocationStereoscopic = this.wmsLocationStereoscopicMapper.getHBUseAbleAmount(location);
                if (wmsLocationStereoscopic != null && wmsLocationStereoscopic.getUseAbleAmount() != null) {
                    useAbleAmount = wmsLocationStereoscopic.getUseAbleAmount();
                }

                if (useAbleAmount < planAmount) {
                    resp.setCode(RESULT.FAILED.code);
                    resp.setMsg("整个仓库中的商品（" + tmp.getGoodsCode() + "-" + tmp.getBatchNo() + "）可用数量不足！");
                    return resp;
                }

                WmsLocationStereoscopic seachOb = new WmsLocationStereoscopic();
                seachOb.setGoodsCode(tmp.getGoodsCode());
                seachOb.setBatchNo(tmp.getBatchNo());
            }
        }

        return this.success();
    }
    /**
     *功能描述: 校验数量
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    public Resp checkAmount(WmsOrderOutStereoscopic condition) {
        Resp resp = new Resp();
        List<String> orderNoList = condition.getOrderNoList();
        //出库类型
        String userDefined2=condition.getUserDefined2();
        WmsOrderOutStereoscopicDeail searchDetailOb = new WmsOrderOutStereoscopicDeail();
        searchDetailOb.setOrderNoList(orderNoList);
        searchDetailOb.setUserDefined2(userDefined2);
        //根据出库单单据编号 查询 立库出库单明细表
        List<WmsOrderOutStereoscopicDeail> detailList = this.wmsOrderOutStereoscopicDeailMapper.queryMergeList(searchDetailOb);
        if (detailList != null && !detailList.isEmpty()) {
            Iterator var7 = detailList.iterator();

            while(var7.hasNext()) {
                WmsOrderOutStereoscopicDeail tmp = (WmsOrderOutStereoscopicDeail)var7.next();
                Integer planAmount = tmp.getPlanAmount() == null ? 0 : tmp.getPlanAmount();
                Integer useAbleAmount = 0;
                WmsLocationStereoscopic location = new WmsLocationStereoscopic();
                location.setGoodsCode(tmp.getGoodsCode());
                location.setBatchNo(tmp.getBatchNo());
                //查询商品对应的可用数量/重量
                WmsLocationStereoscopic wmsLocationStereoscopic = this.wmsLocationStereoscopicMapper.getHBUseAbleAmount(location);
                if (wmsLocationStereoscopic != null && wmsLocationStereoscopic.getUseAbleAmount() != null) {
                    useAbleAmount = wmsLocationStereoscopic.getUseAbleAmount();
                }

                if (useAbleAmount < planAmount) {
                    resp.setCode(RESULT.FAILED.code);
                    resp.setMsg("整个仓库中的商品（" + tmp.getGoodsCode() + "-" + tmp.getBatchNo() + "）可用数量不足！");
                    return resp;
                }

                WmsLocationStereoscopic seachOb = new WmsLocationStereoscopic();
                seachOb.setGoodsCode(tmp.getGoodsCode());
                seachOb.setBatchNo(tmp.getBatchNo());
            }
        }

        return this.success();
    }

    public Resp start(WmsOrderOutStereoscopic wmsOrderOutStereoscopic) {
        this.wmsOrderOutStereoscopicMapper.updateBySelectBatch(wmsOrderOutStereoscopic);
        return this.success();
    }

    /**
     *功能描述: 根据单据订单号，移库/出库，启动任务。
     * 立库枯萎表，使用状态 ：2
     * 托盘表lock_by为订单号，
     * 执行任务表，新增一条数据。
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    @Transactional
    public Resp startOrderNo(List<WmsMoveTask> wmsMoveTaskList, List<WmsOutTask> wmsOutTaskList, String orderNo, String loginName) {
        Date now = new Date();
        WmsOrderOutStereoscopic wmsOrderOutStereoscopic = new WmsOrderOutStereoscopic();
        wmsOrderOutStereoscopic.setOrderStatus("2");
        wmsOrderOutStereoscopic.setOrderNo(orderNo);
        //任务启动时间
        wmsOrderOutStereoscopic.setGmtRun(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        wmsOrderOutStereoscopic.setLastModifiedBy(loginName);
        wmsOrderOutStereoscopic.setGmtModified(now);
        //出库状态修改为 2：启动
        this.wmsOrderOutStereoscopicMapper.updateByOrderNo(wmsOrderOutStereoscopic);
        List<WmsPallet> wmsPalletList = new ArrayList();
        List<WmsTaskExecutionLog> wmsTaskExecutionLogList = new ArrayList();
        Iterator iter;
        WmsPallet wmsPallet;
        WmsTaskExecutionLog wmsTaskExecutionLog;
        //移库
        if (wmsMoveTaskList != null && !wmsMoveTaskList.isEmpty()) {
            iter = wmsMoveTaskList.iterator();

            while(iter.hasNext()) {
                WmsMoveTask tmp = (WmsMoveTask)iter.next();
                wmsPallet = new WmsPallet();
                wmsPallet.setPalletCode(tmp.getPalletCode());
                //TODO zx 托盘被锁，出不来库
                //wmsPallet.setLockBy(orderNo);
                if (tmp.getChannelLocation() != null && !"".equals(tmp.getChannelLocation())) {
                    wmsPallet.setChannelLocation(tmp.getChannelLocation());
                }

                wmsPallet.setLastModifiedBy(loginName);
                wmsPallet.setGmtModified(now);
                wmsPalletList.add(wmsPallet);
                wmsTaskExecutionLog = new WmsTaskExecutionLog();
                wmsTaskExecutionLog.setTaskId(tmp.getTaskId());
                wmsTaskExecutionLog.setOrderNo(orderNo);
                wmsTaskExecutionLog.setTaskType(String.valueOf(TaskType.OUT_MOVE.getTaskType()));
                wmsTaskExecutionLog.setTaskStatus("1");
                wmsTaskExecutionLog.setPalletCode(tmp.getPalletCode());
                wmsTaskExecutionLog.setGoodsCode(tmp.getGoodsCode());
                wmsTaskExecutionLog.setBatchNo(tmp.getBatchNo());
                wmsTaskExecutionLog.setOutAddress(tmp.getFromLocation());
                wmsTaskExecutionLog.setLocationCode(tmp.getTargetLocation());
                wmsTaskExecutionLog.setCreateBy(loginName);
                wmsTaskExecutionLog.setGmtCreate(now);
                wmsTaskExecutionLog.setAreaCode("L-NH01");
                wmsTaskExecutionLog.setActiveFlag("1");
                wmsTaskExecutionLog.setUserDefined2(tmp.getUserDefined2());
                wmsTaskExecutionLogList.add(wmsTaskExecutionLog);
            }
        }
        //出库
        if (wmsOutTaskList != null && !wmsOutTaskList.isEmpty()) {
            iter = wmsOutTaskList.iterator();

            while(iter.hasNext()) {
                WmsOutTask tmp = (WmsOutTask)iter.next();
                wmsPallet = new WmsPallet();
                wmsPallet.setPalletCode(tmp.getPalletCode());
                //TODO zx 托盘被锁，出不来库
                //wmsPallet.setLockBy(String.valueOf(tmp.getTaskId()));
                wmsPallet.setUserDefined1(orderNo);
                if (tmp.getChannelLocation() != null && !"".equals(tmp.getChannelLocation())) {
                    wmsPallet.setChannelLocation(tmp.getChannelLocation());
                }

                wmsPallet.setLastModifiedBy(loginName);
                wmsPallet.setGmtModified(now);
                wmsPalletList.add(wmsPallet);
                wmsTaskExecutionLog = new WmsTaskExecutionLog();
                wmsTaskExecutionLog.setTaskId(tmp.getTaskId());
                wmsTaskExecutionLog.setOrderNo(orderNo);
                wmsTaskExecutionLog.setTaskType(tmp.getTaskType());
                wmsTaskExecutionLog.setTaskStatus("1");
                wmsTaskExecutionLog.setOutAddress(tmp.getTargetAddress());
                wmsTaskExecutionLog.setPalletCode(tmp.getPalletCode());
                wmsTaskExecutionLog.setGoodsCode(tmp.getGoodsCode());
                wmsTaskExecutionLog.setBatchNo(tmp.getBatchNo());
                wmsTaskExecutionLog.setLocationCode(tmp.getFromLocation());
                wmsTaskExecutionLog.setCreateBy(loginName);
                wmsTaskExecutionLog.setGmtCreate(now);
                wmsTaskExecutionLog.setAreaCode("L-NH01");
                wmsTaskExecutionLog.setActiveFlag("1");
                wmsTaskExecutionLog.setUserDefined1(Integer.toString(tmp.getAmount()));
                wmsTaskExecutionLog.setUserDefined2(tmp.getUserDefined2());
                //出库任务默认1：未审核
                wmsTaskExecutionLog.setUserDefined3("1");
                wmsTaskExecutionLogList.add(wmsTaskExecutionLog);
            }
        }

        this.wmsPalletMapper.updateByBatch(wmsPalletList);
        this.wmsTaskExecutionLogMapper.batchInsert(wmsTaskExecutionLogList);
        return this.success();
    }
    /**
     *功能描述: 现场添加：根据订单号查询数据
     * @params
     * @return com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic
     */
    @Override
    public WmsOrderOutStereoscopic queryByOrderNo(WmsOrderOutStereoscopic condition){
        return wmsOrderOutStereoscopicMapper.queryByOrderNo(condition);
    }
}
