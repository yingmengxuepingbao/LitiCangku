//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsHBLocationStereoscopicMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsLocationStereoscopicMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import com.penghaisoft.wms.logmanagement.model.dao.WmsMoveLogMapper;
import com.penghaisoft.wms.logmanagement.model.entity.WmsMoveLog;
import com.penghaisoft.wms.nuohua.service.DifferentBusinessNHFactory;
import com.penghaisoft.wms.nuohua.service.DifferentBusinessNHService;
import com.penghaisoft.wms.nuohua.service.SLWCSService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsMoveStereoscopicService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsMoveStereoscopicMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsTaskExecutionLogMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsMoveStereoscopic;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;

import com.penghaisoft.wms.util.ConstantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("wmsMoveStereoscopicService")
public class WmsMoveStereoscopicServiceImpl extends BaseService implements IWmsMoveStereoscopicService {
    @Resource
    private WmsMoveStereoscopicMapper wmsMoveStereoscopicMapper;
    @Resource
    private WmsLocationStereoscopicMapper wmsLocationStereoscopicMapper;
    @Autowired
    private IWmsCommonService wmsCommonService;
    @Autowired
    private SLWCSService sLWCSService;
    @Autowired
    private IWmsMoveStereoscopicService wmsMoveStereoscopicService;
    @Resource
    private WmsHBLocationStereoscopicMapper wmsHBLocationStereoscopicMapper;
    @Resource
    private WmsPalletMapper wmsPalletMapper;
    @Resource
    private WmsMoveLogMapper wmsMoveLogMapper;
    @Resource
    private WmsTaskExecutionLogMapper wmsTaskExecutionLogMapper;
    @Autowired
    public DifferentBusinessNHFactory differentBusinessNHFactory;
    @Value("${applyfactory}")
    private String applyfactory;

    public WmsMoveStereoscopicServiceImpl() {
    }

    public Resp create(WmsMoveStereoscopic wmsMoveStereoscopic) {
        this.wmsMoveStereoscopicMapper.create(wmsMoveStereoscopic);
        return this.success();
    }

    public Resp delete(WmsMoveStereoscopic wmsMoveStereoscopic) {
        this.wmsMoveStereoscopicMapper.delete(wmsMoveStereoscopic);
        return this.success();
    }

    public Pager<WmsMoveStereoscopic> findListByCondition(int page, int rows, WmsMoveStereoscopic condition) {
        Pager<WmsMoveStereoscopic> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsMoveStereoscopicMapper.queryCount(condition);
        List<WmsMoveStereoscopic> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsMoveStereoscopicMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsMoveStereoscopic findById(String id) {
        return (WmsMoveStereoscopic)this.wmsMoveStereoscopicMapper.queryById(id);
    }

    public Resp update(WmsMoveStereoscopic wmsMoveStereoscopic) {
        this.wmsMoveStereoscopicMapper.updateBySelect(wmsMoveStereoscopic);
        return this.success();
    }

    public List<WmsMoveStereoscopic> queryByAny(WmsMoveStereoscopic condition) {
        return this.wmsMoveStereoscopicMapper.queryByAny(condition);
    }

    @Transactional
    public Resp reportNormalYkTask(WmsTaskExecutionLog condition) {
        Date now = new Date();
        WmsMoveStereoscopic serachOb = new WmsMoveStereoscopic();
        serachOb.setMoveNo(condition.getOrderNo());
        serachOb.setActiveFlag("1");
        List<WmsMoveStereoscopic> wmsMoveStereoscopicList = this.wmsMoveStereoscopicMapper.queryByAny(serachOb);
        if (wmsMoveStereoscopicList != null && !wmsMoveStereoscopicList.isEmpty()) {
            WmsMoveStereoscopic tmp = (WmsMoveStereoscopic)wmsMoveStereoscopicList.get(0);
            List<String> locationCodeList = new ArrayList();
            if (tmp.getFromLocationCode() != null && !"".equals(tmp.getFromLocationCode())) {
                locationCodeList.add(tmp.getFromLocationCode());
            }

            if (tmp.getOutChannelLocationCode() != null && !"".equals(tmp.getOutChannelLocationCode())) {
                locationCodeList.add(tmp.getOutChannelLocationCode());
            }

            if (tmp.getInChannelLocationCode() != null && !"".equals(tmp.getInChannelLocationCode())) {
                locationCodeList.add(tmp.getInChannelLocationCode());
            }

            if ("3".equals(condition.getTaskStatus())) {
                WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
                updateOb.setLocationCodeList(locationCodeList);
                updateOb.setUseStatus("0");
                updateOb.setLastModifiedBy("wsc");
                updateOb.setGmtModified(now);
                this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateOb);
                if (tmp.getFromLocationCode() != null && !"".equals(tmp.getFromLocationCode())) {
                    updateOb = new WmsLocationStereoscopic();
                    updateOb.setLocationCode(tmp.getFromLocationCode());
                    updateOb.setPalletCodeNull("null");
                    updateOb.setLastModifiedBy("wsc");
                    updateOb.setGmtModified(now);
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
                }

                if (tmp.getToLocationCode() != null && !"".equals(tmp.getToLocationCode())) {
                    updateOb = new WmsLocationStereoscopic();
                    updateOb.setLocationCode(tmp.getToLocationCode());
                    updateOb.setUseStatus("3");
                    updateOb.setPalletCode(tmp.getPalletCode());
                    updateOb.setLastModifiedBy("wsc");
                    updateOb.setGmtModified(now);
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
                }

                WmsPallet wmsPalletOb = new WmsPallet();
                wmsPalletOb.setPalletCode(tmp.getPalletCode());
                wmsPalletOb.setLockByNull("null");
                wmsPalletOb.setChannelLocationNull("null");
                wmsPalletOb.setLocationCode(tmp.getToLocationCode());
                wmsPalletOb.setLastModifiedBy("wsc");
                wmsPalletOb.setGmtModified(now);
                this.wmsPalletMapper.updateByPalletCode(wmsPalletOb);
                WmsMoveLog wmsMoveLog = new WmsMoveLog();
                wmsMoveLog.setMoveLogId(CommonUtils.getUUID());
                wmsMoveLog.setPalletCode(tmp.getPalletCode());
                wmsMoveLog.setAreaCode(tmp.getAreaCode());
                wmsMoveLog.setFromLocationCode(tmp.getFromLocationCode());
                wmsMoveLog.setToLocationCode(tmp.getToLocationCode());
                wmsMoveLog.setMoveResult("1");
                wmsMoveLog.setAmount(tmp.getAmount());
                wmsMoveLog.setBatchNo(tmp.getBatchNo());
                wmsMoveLog.setGoodsCode(tmp.getGoodsCode());
                wmsMoveLog.setCreateBy("wcs");
                wmsMoveLog.setGmtCreate(now);
                wmsMoveLog.setActiveFlag("1");
                this.wmsMoveLogMapper.create(wmsMoveLog);
                WmsMoveStereoscopic wmsMoveStereoscopicUpdate = new WmsMoveStereoscopic();
                wmsMoveStereoscopicUpdate.setMoveId(tmp.getMoveId());
                wmsMoveStereoscopicUpdate.setMoveStatus("3");
                wmsMoveStereoscopicUpdate.setLastModifiedBy("wcs");
                wmsMoveStereoscopicUpdate.setGmtModified(now);
                this.wmsMoveStereoscopicMapper.updateBySelect(wmsMoveStereoscopicUpdate);
                this.wmsTaskExecutionLogMapper.updateByTaskId(condition);
            } else if ("4".equals(condition.getTaskStatus())) {
                WmsMoveStereoscopic wmsMoveStereoscopicUpdate = new WmsMoveStereoscopic();
                wmsMoveStereoscopicUpdate.setMoveId(tmp.getMoveId());
                wmsMoveStereoscopicUpdate.setMoveStatus("4");
                wmsMoveStereoscopicUpdate.setLastModifiedBy("wcs");
                wmsMoveStereoscopicUpdate.setGmtModified(now);
                this.wmsMoveStereoscopicMapper.updateBySelect(wmsMoveStereoscopicUpdate);
                if (tmp.getToLocationCode() != null && !"".equals(tmp.getToLocationCode())) {
                    locationCodeList.add(tmp.getToLocationCode());
                }

                WmsMoveLog wmsMoveLog = new WmsMoveLog();
                wmsMoveLog.setMoveLogId(CommonUtils.getUUID());
                wmsMoveLog.setPalletCode(tmp.getPalletCode());
                wmsMoveLog.setAmount(tmp.getAmount());
                wmsMoveLog.setBatchNo(tmp.getBatchNo());
                wmsMoveLog.setAreaCode(tmp.getAreaCode());
                wmsMoveLog.setFromLocationCode(tmp.getFromLocationCode());
                wmsMoveLog.setToLocationCode(tmp.getToLocationCode());
                wmsMoveLog.setMoveResult("0");
                wmsMoveLog.setErrorMsg(condition.getMsg());
                wmsMoveLog.setCreateBy("wcs");
                wmsMoveLog.setGmtCreate(now);
                wmsMoveLog.setActiveFlag("1");
                this.wmsMoveLogMapper.create(wmsMoveLog);
                WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
                updateOb.setLocationCodeList(locationCodeList);
                updateOb.setUseStatus("4");
                updateOb.setLastModifiedBy("wsc");
                updateOb.setGmtModified(now);
                this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateOb);
                this.wmsTaskExecutionLogMapper.updateByTaskId(condition);
            }
        }

        return this.success();
    }

    /**
     *功能描述:  开始移库任务
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    @Transactional
    public Resp startYkTask(WmsMoveStereoscopic wmsMoveStereoscopic) {
        Date now = new Date();
        Resp resp = new Resp();
        WmsPallet t = new WmsPallet();
        t.setPalletCode(wmsMoveStereoscopic.getPalletCode());
        t.setLockBy(wmsMoveStereoscopic.getMoveNo());
        t.setLastModifiedBy(wmsMoveStereoscopic.getCreateBy());
        t.setGmtModified(now);
        this.wmsPalletMapper.updateByPalletCode(t);
        WmsMoveStereoscopic wmsMoveStereoscopicUpdatOb = new WmsMoveStereoscopic();
        if(wmsMoveStereoscopic.getMoveId()!=null &&!wmsMoveStereoscopic.getMoveId().isEmpty()) {
            wmsMoveStereoscopicUpdatOb.setMoveId(wmsMoveStereoscopic.getMoveId());
        }
        wmsMoveStereoscopicUpdatOb.setMoveId(String.valueOf(wmsMoveStereoscopic.getTaskId()));
        wmsMoveStereoscopicUpdatOb.setMoveStatus("2");
        wmsMoveStereoscopicUpdatOb.setLastModifiedBy(wmsMoveStereoscopic.getCreateBy());
        wmsMoveStereoscopicUpdatOb.setGmtModified(now);
        wmsMoveStereoscopicUpdatOb.setActiveFlag("1");
        this.wmsMoveStereoscopicMapper.updateBySelect(wmsMoveStereoscopicUpdatOb);
        WmsMoveLog wmsMoveLog = new WmsMoveLog();
        wmsMoveLog.setMoveLogId(CommonUtils.getUUID());
        wmsMoveLog.setPalletCode(wmsMoveStereoscopic.getPalletCode());
        wmsMoveLog.setAreaCode(wmsMoveStereoscopic.getAreaCode());
        wmsMoveLog.setFromLocationCode(wmsMoveStereoscopic.getFromLocationCode());
        wmsMoveLog.setToLocationCode(wmsMoveStereoscopic.getToLocationCode());
        wmsMoveLog.setMoveResult("0");
        wmsMoveLog.setCreateBy(wmsMoveStereoscopic.getCreateBy());
        wmsMoveLog.setGmtCreate(now);
        wmsMoveLog.setActiveFlag("1");
        wmsMoveLog.setGoodsCode(wmsMoveStereoscopic.getGoodsCode());
        wmsMoveLog.setBatchNo(wmsMoveStereoscopic.getBatchNo());
        wmsMoveLog.setAmount(wmsMoveStereoscopic.getAmount());
        this.wmsMoveLogMapper.create(wmsMoveLog);
        WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
        wmsTaskExecutionLog.setAreaCode(wmsMoveStereoscopic.getAreaCode());
        wmsTaskExecutionLog.setTaskId(Long.valueOf(wmsMoveStereoscopic.getMoveId()));
        wmsTaskExecutionLog.setOrderNo(wmsMoveStereoscopic.getMoveNo());
        wmsTaskExecutionLog.setTaskType(String.valueOf(TaskType.NORMAL_MOVE.getTaskType()));
        wmsTaskExecutionLog.setPalletCode(wmsMoveStereoscopic.getPalletCode());
        wmsTaskExecutionLog.setTaskStatus("2");
        wmsTaskExecutionLog.setInAddress(wmsMoveStereoscopic.getToLocationCode());
        wmsTaskExecutionLog.setOutAddress(wmsMoveStereoscopic.getFromLocationCode());
        wmsTaskExecutionLog.setLocationCode(wmsMoveStereoscopic.getToLocationCode());
        wmsTaskExecutionLog.setGoodsCode(wmsMoveStereoscopic.getGoodsCode());
        wmsTaskExecutionLog.setBatchNo(wmsMoveStereoscopic.getBatchNo());
        wmsTaskExecutionLog.setCreateBy(wmsMoveStereoscopic.getCreateBy());
        if(wmsMoveStereoscopic.getUserDefined3()!=null &&!wmsMoveStereoscopic.getUserDefined3().isEmpty()){
            wmsTaskExecutionLog.setUserDefined3(wmsMoveStereoscopic.getUserDefined3());
        }
        if(wmsMoveStereoscopic.getUserDefined1()!=null &&!wmsMoveStereoscopic.getUserDefined1().isEmpty()){
            wmsTaskExecutionLog.setUserDefined1(wmsMoveStereoscopic.getUserDefined1());
        }
        wmsTaskExecutionLog.setWarehouseCode("NH_WAREHOUSE");
        wmsTaskExecutionLog.setAreaCode("L-NH01");
        wmsTaskExecutionLog.setGmtCreate(now);
        wmsTaskExecutionLog.setActiveFlag("1");
        this.wmsTaskExecutionLogMapper.create(wmsTaskExecutionLog);
        //单个移库-修改 入库 未过账 移库的库位
        //将托盘，物料，批次，未过账，的任务 更改库位
        WmsTaskExecutionLog wmsTaskExecutionLog1 =new WmsTaskExecutionLog();
        wmsTaskExecutionLog1.setPalletCode(wmsMoveStereoscopic.getPalletCode());
        wmsTaskExecutionLog1.setGoodsCode(wmsMoveStereoscopic.getGoodsCode());
        wmsTaskExecutionLog1.setBatchNo(wmsMoveStereoscopic.getBatchNo());
        wmsTaskExecutionLog1.setTaskStatus("3");
        wmsTaskExecutionLog1.setUserDefined3("1");
        List idList =new ArrayList();
        idList.add("10");
        idList.add("50");
        wmsTaskExecutionLog1.setIdList(idList);
        List<WmsTaskExecutionLog> list = wmsTaskExecutionLogMapper.selReceipt(wmsTaskExecutionLog1);
        if(list!=null && !list.isEmpty()) {
            for(WmsTaskExecutionLog wmsTaskExecutionLog2:list) {
                wmsTaskExecutionLog2.setLocationCode(wmsMoveStereoscopic.getToLocationCode());
                wmsTaskExecutionLogMapper.updateByTaskId(wmsTaskExecutionLog2);
            }
        }

        resp.setCode(RESULT.SUCCESS.code);
        return resp;
    }



    //===========================现场添加接口，库内移库任务完成状态==========================
    /**
     *功能描述: 修改库内移库状态
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    @Override
    public Resp kuNei_YiKu(WmsTaskExecutionLog wmsTaskExecutionLog) {
        //根据TaskID查询数据
        WmsTaskExecutionLog wmsTaskExecutionLog1 =wmsTaskExecutionLogMapper.queryByTask(wmsTaskExecutionLog);

        Date now = new Date();
        List<String> locationCodeList = new ArrayList();
        WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
        if ("3".equals(wmsTaskExecutionLog.getTaskStatus())) {
            String zhuangtai = "";
            if (wmsTaskExecutionLog.getOutAddress() != null && wmsTaskExecutionLog.getOutAddress() != "") {
                //将源库位状态清空
                locationCodeList.add(wmsTaskExecutionLog.getOutAddress());

                updateOb.setLocationCodeList(locationCodeList);
                updateOb.setUseStatus("0");
                updateOb.setPalletCodeNull("null");
                updateOb.setLastModifiedBy("wsc");
                updateOb.setGmtModified(new Date());
                this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateOb);
            }
            if (wmsTaskExecutionLog.getInAddress() != null && wmsTaskExecutionLog.getInAddress() != "") {
                updateOb = new WmsLocationStereoscopic();
                updateOb.setLocationCode(wmsTaskExecutionLog.getInAddress());
                updateOb.setUseStatus("5");
                updateOb.setPalletCode(wmsTaskExecutionLog1.getPalletCode());
                updateOb.setLastModifiedBy("wsc");
                updateOb.setGmtModified(now);
                this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
            }

            WmsPallet wmsPalletOb = new WmsPallet();
            wmsPalletOb.setPalletCode(wmsTaskExecutionLog1.getPalletCode());
            wmsPalletOb.setLockByNull("null");
            wmsPalletOb.setChannelLocationNull("null");
            wmsPalletOb.setLocationCode(wmsTaskExecutionLog.getInAddress());
            wmsPalletOb.setLastModifiedBy("wsc");
            wmsPalletOb.setGmtModified(now);
            this.wmsPalletMapper.updateByPalletCode(wmsPalletOb);
            WmsMoveLog wmsMoveLog = new WmsMoveLog();
            wmsMoveLog.setMoveLogId(CommonUtils.getUUID());
            wmsMoveLog.setPalletCode(wmsTaskExecutionLog1.getPalletCode());
            wmsMoveLog.setAreaCode(wmsTaskExecutionLog.getAreaCode());
            wmsMoveLog.setFromLocationCode(wmsTaskExecutionLog.getOutAddress());
            wmsMoveLog.setToLocationCode(wmsTaskExecutionLog.getInAddress());
            wmsMoveLog.setMoveResult("1");
           // wmsMoveLog.setAmount(Integer.parseInt(wmsTaskExecutionLog.getUserDefined1()));
            wmsMoveLog.setBatchNo(wmsTaskExecutionLog1.getBatchNo());
            wmsMoveLog.setGoodsCode(wmsTaskExecutionLog1.getGoodsCode());
            wmsMoveLog.setCreateBy("wcs");
            wmsMoveLog.setGmtCreate(now);
            wmsMoveLog.setActiveFlag("1");
            this.wmsMoveLogMapper.create(wmsMoveLog);
            WmsMoveStereoscopic wmsMoveStereoscopicUpdate = new WmsMoveStereoscopic();
            wmsMoveStereoscopicUpdate.setMoveId(String.valueOf(wmsTaskExecutionLog.getTaskId()));
            wmsMoveStereoscopicUpdate.setMoveStatus("3");
            wmsMoveStereoscopicUpdate.setLastModifiedBy("wcs");
            wmsMoveStereoscopicUpdate.setGmtModified(now);
            this.wmsMoveStereoscopicMapper.updateBySelect(wmsMoveStereoscopicUpdate);
            wmsTaskExecutionLog.setActiveFlag("0");
            this.wmsTaskExecutionLogMapper.updateByTaskId(wmsTaskExecutionLog);
        } else if ("4".equals(wmsTaskExecutionLog.getTaskStatus())) {
            WmsMoveStereoscopic wmsMoveStereoscopicUpdate = new WmsMoveStereoscopic();
            wmsMoveStereoscopicUpdate.setMoveId(String.valueOf(wmsTaskExecutionLog.getTaskId()));
            wmsMoveStereoscopicUpdate.setMoveStatus("4");
            wmsMoveStereoscopicUpdate.setLastModifiedBy("wcs");
            wmsMoveStereoscopicUpdate.setGmtModified(now);
            this.wmsMoveStereoscopicMapper.updateBySelect(wmsMoveStereoscopicUpdate);
            if (wmsTaskExecutionLog.getInAddress() != null && !"".equals(wmsTaskExecutionLog.getInAddress())) {
                locationCodeList.add(wmsTaskExecutionLog.getInAddress());
            }

            WmsMoveLog wmsMoveLog = new WmsMoveLog();
            wmsMoveLog.setMoveLogId(CommonUtils.getUUID());
            wmsMoveLog.setPalletCode(wmsTaskExecutionLog1.getPalletCode());
            wmsMoveLog.setAmount(Integer.parseInt(wmsTaskExecutionLog1.getUserDefined1()));
            wmsMoveLog.setBatchNo(wmsTaskExecutionLog.getBatchNo());
            wmsMoveLog.setAreaCode(wmsTaskExecutionLog.getAreaCode());
            wmsMoveLog.setFromLocationCode(wmsTaskExecutionLog.getOutAddress());
            wmsMoveLog.setToLocationCode(wmsTaskExecutionLog.getInAddress());
            wmsMoveLog.setMoveResult("0");
            wmsMoveLog.setErrorMsg(wmsTaskExecutionLog.getMsg());
            wmsMoveLog.setCreateBy("wcs");
            wmsMoveLog.setGmtCreate(now);
            wmsMoveLog.setActiveFlag("1");
            this.wmsMoveLogMapper.create(wmsMoveLog);
            WmsLocationStereoscopic updateOb1 = new WmsLocationStereoscopic();
            updateOb1.setLocationCodeList(locationCodeList);
            updateOb1.setUseStatus("4");
            updateOb1.setLastModifiedBy("wsc");
            updateOb1.setGmtModified(now);
            this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateOb1);
            this.wmsTaskExecutionLogMapper.updateByTaskId(wmsTaskExecutionLog);
        }
        return this.success();
    }
    //===========================现场添加接口=======================================
    /**
     *功能描述:
     * @author 创建批量移库任务：确保最多只占两个巷道，
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    @Override
    public Resp createYkTaskList(WmsMoveStereoscopic wmsMoveStereoscopic){
        Resp resp = new Resp();
        System.out.println("原材料-自动理库：移库开始");
        if(wmsMoveStereoscopic.getGoodsCode()==null || "".equals(wmsMoveStereoscopic.getGoodsCode())){
            resp.setCode(RESULT.FAILED.code);
            resp.setMsg("物料码为空！");
            return resp;
        }
        if(wmsMoveStereoscopic.getBatchNo()==null || "".equals(wmsMoveStereoscopic.getBatchNo())){
            resp.setCode(RESULT.FAILED.code);
            resp.setMsg("批次号为空！");
            return resp;
        }
        Integer xiangdao =0;
        Integer flag =0;
        Integer number = 0;
        WmsLocationStereoscopic wmsLocationStereoscopic =new WmsLocationStereoscopic();
        wmsLocationStereoscopic.setGoodsCode(wmsMoveStereoscopic.getGoodsCode());
        wmsLocationStereoscopic.setBatchNo(wmsMoveStereoscopic.getBatchNo());
        //根据物料码/批次号，查询此入库未过账的数据在那几条巷道 占用道
        List<Integer> zhandaoList =wmsHBLocationStereoscopicMapper.getZhanyongDao(wmsLocationStereoscopic);
        if(zhandaoList!=null &&!zhandaoList.isEmpty()){
            xiangdao = zhandaoList.size();
            flag = zhandaoList.size();
            //如果巷道大于1条，将其余巷道自动理库
            if(zhandaoList.size()>1){

                    //根据物料码/批次号，查询此入库未过账的数据在那几条巷道 占用道的货位使用情况 有多到少
                    List<WmsLocationStereoscopic> shiYongList = wmsHBLocationStereoscopicMapper.getZhanyongKuWei(wmsLocationStereoscopic);
                    //将查询到的信息，作为源，进行移库
                    List<Integer> shelvesNumberList = new ArrayList<>();

                    //目标巷道的A空货位是否多于最小巷道C的货物。则B为源   //否则B为目标
                    if (shiYongList != null && shiYongList.get(0).getZhanYongNumber() > shiYongList.get(shiYongList.size() - 1).getZhanYongNumber()) {
                        for (int i = 0; i < zhandaoList.size() - 1; i++) {
                            shelvesNumberList.add(zhandaoList.get(i));
                        }
                    } else {
                        for (int i = shiYongList.size() - 1; i < shiYongList.size(); i++) {
                            shelvesNumberList.add(shiYongList.get(i).getShelvesNumber());
                        }
                    }
                    wmsLocationStereoscopic.setShelvesNumberList(shelvesNumberList);
                    //入库未过账状态
                    wmsLocationStereoscopic.setUseStatus("5");
                    //根据巷道查询 占位货物信息-源
                    List<WmsLocationStereoscopic> yuanList = wmsHBLocationStereoscopicMapper.getLocationCodeByShelvesNumber(wmsLocationStereoscopic);
                    if (yuanList != null && yuanList.size() > 0) {
                        //根据巷道，查询空货位信息
                        List<Integer> daoxuList = new ArrayList();
                        HashSet h1 = new HashSet(zhandaoList);
                        HashSet h2 = new HashSet(shelvesNumberList);
                        h1.removeAll(h2);
                        daoxuList.addAll(h1);
                          /* List daoxuList = zhandaoList;
                           Collections.reverse(daoxuList);
                           int num = daoxuList.size() - shelvesNumberList.size();*/
                        //清空list
                        shelvesNumberList = new ArrayList<>();
                        for (int i = 0; i < daoxuList.size(); i++) {
                            shelvesNumberList.add(Integer.parseInt(daoxuList.get(i).toString()));
                        }
                        //未占用状态
                        wmsLocationStereoscopic.setUseStatus("0");
                        wmsLocationStereoscopic.setShelvesNumberList(shelvesNumberList);
                        wmsLocationStereoscopic.setGoodsCode("");
                        wmsLocationStereoscopic.setBatchNo("");
                        //先往巷道数量多的放 -目标
                        List<WmsLocationStereoscopic> mubiaoList = wmsHBLocationStereoscopicMapper.getLocationCodeByShelvesNumber(wmsLocationStereoscopic);
                        if (mubiaoList != null && !mubiaoList.isEmpty()) {
                            if (yuanList.size() > mubiaoList.size()) {
                                //要移动的货物，比空货位多
                                number = mubiaoList.size();
                            } else {
                                number = yuanList.size();
                            }
                            //TODO 调 自动盘库 方法
                            resp = zidong(number, yuanList, mubiaoList, wmsMoveStereoscopic);
                            return resp;
                        } else {
                              List<WmsLocationStereoscopic> duoXiangDaoList = wmsHBLocationStereoscopicMapper.getZhanyongKuWei(wmsLocationStereoscopic);
                              if(duoXiangDaoList!=null && duoXiangDaoList.size() > 2){
                                   List<Integer> paichuList = new ArrayList();
                                    for (int i = 1; i < duoXiangDaoList.size(); i++) {
                                        paichuList.add(duoXiangDaoList.get(i).getShelvesNumber());
                                    }
                                    shelvesNumberList = new ArrayList<>();
                                    shelvesNumberList.add(paichuList.get(paichuList.size()-1));
                                    wmsLocationStereoscopic.setShelvesNumberList(shelvesNumberList);
                                    //入库未过账状态
                                    wmsLocationStereoscopic.setUseStatus("5");
                                    //根据巷道查询 占位货物信息-源
                                    List<WmsLocationStereoscopic> yuanList1 = wmsHBLocationStereoscopicMapper.getLocationCodeByShelvesNumber(wmsLocationStereoscopic);
                                    if (yuanList1 != null && yuanList1.size() > 0) {
                                        //根据巷道，查询空货位信息
                                        List<Integer> daoxuList1 = new ArrayList();
                                        HashSet hh1 = new HashSet(paichuList);
                                        HashSet hh2 = new HashSet(shelvesNumberList);
                                        hh1.removeAll(hh2);
                                        daoxuList1.addAll(hh1);
                                        //清空list
                                        shelvesNumberList = new ArrayList<>();
                                        for (int i = 0; i < daoxuList1.size(); i++) {
                                            shelvesNumberList.add(Integer.parseInt(daoxuList1.get(i).toString()));
                                        }
                                        //未占用状态
                                        wmsLocationStereoscopic.setUseStatus("0");
                                        wmsLocationStereoscopic.setShelvesNumberList(shelvesNumberList);
                                        wmsLocationStereoscopic.setGoodsCode("");
                                        wmsLocationStereoscopic.setBatchNo("");
                                        //先往巷道数量多的放 -目标
                                        List<WmsLocationStereoscopic> mubiaoList1 = wmsHBLocationStereoscopicMapper.getLocationCodeByShelvesNumber(wmsLocationStereoscopic);
                                        if (mubiaoList1 != null && !mubiaoList1.isEmpty()) {
                                            if (yuanList1.size() > mubiaoList1.size()) {
                                                //要移动的货物，比空货位多
                                                number = mubiaoList1.size();
                                            } else {
                                                number = yuanList1.size();
                                            }
                                            //TODO 调 自动盘库 方法
                                            resp = zidong(number, yuanList1, mubiaoList1, wmsMoveStereoscopic);
                                            return resp;
                                        }else{
                                            log.info("再次 -目标库位-未找到！");
                                            resp.setCode(RESULT.FAILED.code);
                                            resp.setMsg("再次 -目标库位-未找到！");
                                            return resp;
                                        }
                                    }else {
                                        log.info("再次-源目标库位-为空");
                                        resp.setCode(RESULT.FAILED.code);
                                        resp.setMsg("再次-源目标库位-为空");
                                        return resp;
                                    }
                              } else {
                                log.info("目标库位-未找到！");
                                resp.setCode(RESULT.FAILED.code);
                                resp.setMsg("目标库位-未找到！");
                                return resp;
                             }
                        }
                    } else {
                        log.info("源目标库位-为空");
                        resp.setCode(RESULT.FAILED.code);
                        resp.setMsg("源目标库位-为空");
                        return resp;
                    }
            }else{
                log.info("原材料所占巷道小于2巷道，无需移库！");
                resp.setCode(RESULT.FAILED.code);
                resp.setMsg("原材料所占巷道小于2巷道，无需移库！");
                return resp;
            }
        }else{
            log.info("未查到物料号："+wmsMoveStereoscopic.getGoodsCode()+"批次号："+wmsLocationStereoscopic.getBatchNo()+"相关数据！");
            resp.setCode(RESULT.FAILED.code);
            resp.setMsg("未查到物料号："+wmsMoveStereoscopic.getGoodsCode()+"批次号："+wmsLocationStereoscopic.getBatchNo()+"相关数据！");
            return resp;
        }
    }

    /**
     *功能描述:  下发 自动移库任务
     * @params
     * @return
     */
    private Resp zidong(Integer number,List<WmsLocationStereoscopic>  yuanList ,List<WmsLocationStereoscopic> mubiaoList ,WmsMoveStereoscopic wmsMoveStereoscopic){
        Resp resp = new Resp();

        List<WmsMoveStereoscopic> wmsMoveStereoscopicList = new ArrayList();
        List<WmsPallet> wmsPalletList = new ArrayList();
        List<WmsLocationStereoscopic> wmsLocationStereoscopicFromList = new ArrayList();
        List<WmsLocationStereoscopic> wmsLocationStereoscopicToList = new ArrayList();
        List<WmsTaskExecutionLog> wmsTaskExecutionLogList = new ArrayList();

        JSONObject jsonObject = new JSONObject();

        //组号
        jsonObject.put("groupId", ConstantUtil.UUID_GroupId());
        //下发时间
        jsonObject.put("msgTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //优先级
        jsonObject.put("priorityCode", "");
        //仓库编码
        jsonObject.put("warehouse", "L-NH01");
        ArrayList swiperList = new ArrayList();

        for (int j = 0; j < number; j++) {
            //源货位
            String fromLocationCode = yuanList.get(j).getLocationCode();
            //目标货位
            String toLocationCode = mubiaoList.get(j).getLocationCode();

          /* //大号处，小号位置是否有货位阻挡 / 小号处，大号位置是否有货位阻挡
           List LocationPasslist = this.wmsHBLocationStereoscopicMapper.getLocationPass(yuanList.get(j));
           if (LocationPasslist != null && LocationPasslist.size() > 0) {
               log.info("源库位的移库通道不通畅，不能进行移库！");
               return this.fail("源库位的移库通道不通畅，不能进行移库！");
           }
           //大号处，小号位置是否有货位阻挡 / 小号处，大号位置是否有货位阻挡
           List LocationPasslist2 = this.wmsHBLocationStereoscopicMapper.getLocationPass(mubiaoList.get(j));
           if (LocationPasslist2 != null && LocationPasslist2.size() > 0) {
               log.info("目的库位的移库通道不通畅，不能进行移库！");
               return this.fail("目的库位的移库通道不通畅，不能进行移库！");
           }*/

            //移库批量下发 json 拼接
            Map map = new HashMap<>();
            long taskId = this.wmsCommonService.getTaskIds(Constant.TaskType.NORMAL_MOVE, 1)[0];
            //任务单号
            map.put("taskId", taskId);
            //任务类型
            map.put("taskType", "2");
            //任务起点
            map.put("startNode", fromLocationCode);
            //任务终点
            map.put("endNode", toLocationCode);
            //托盘编号
            map.put("barCode", yuanList.get(j).getPalletCode());
            map.put("order", j);
            swiperList.add(map);

            //拼接移库实体类
            WmsMoveStereoscopic wmsMoveStereoscopic1 = new WmsMoveStereoscopic();
            wmsMoveStereoscopic1.setTaskId(taskId);
            wmsMoveStereoscopic1.setGoodsCode(yuanList.get(j).getGoodsCode());
            wmsMoveStereoscopic1.setGoodsName(wmsMoveStereoscopic.getGoodsName());
            wmsMoveStereoscopic1.setPalletCode(yuanList.get(j).getPalletCode());
            wmsMoveStereoscopic1.setFromLocationCode(fromLocationCode);
            wmsMoveStereoscopic1.setToLocationCode(toLocationCode);
            wmsMoveStereoscopic1.setAmount(yuanList.get(j).getAmount());
            wmsMoveStereoscopic1.setBatchNo(yuanList.get(j).getBatchNo());
            wmsMoveStereoscopic1.setCreateBy("WMS_move");
            wmsMoveStereoscopic1.setUserDefined3("1");
            wmsMoveStereoscopic1.setUserDefined1(String.valueOf(yuanList.get(j).getAmount()));
            wmsMoveStereoscopic1.setGmtCreate(new Date());
            wmsMoveStereoscopicList.add(wmsMoveStereoscopic1);

            WmsPallet wmsPallet = new WmsPallet();
            wmsPallet.setLockBy(String.valueOf(taskId));
            wmsPallet.setPalletCode(yuanList.get(j).getPalletCode());
            wmsPalletList.add(wmsPallet);
            //根据托盘码锁定托盘
            //wmsPalletMapper.updateByPalletCode(wmsPallet);
            WmsLocationStereoscopic wmsLocationStereoscopic1 = new WmsLocationStereoscopic();
            wmsLocationStereoscopic1.setLocationCode(fromLocationCode);
            //状态：2 出库中
            wmsLocationStereoscopic1.setUseStatus("2");
            wmsLocationStereoscopic1.setLastModifiedBy("WMS");
            wmsLocationStereoscopic1.setGmtModified(new Date());
            wmsLocationStereoscopicFromList.add(wmsLocationStereoscopic1);
            //this.wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopic1);

            WmsLocationStereoscopic wmsLocationStereoscopic2 = new WmsLocationStereoscopic();
            wmsLocationStereoscopic2.setLocationCode(toLocationCode);
            //状态：1 入库中
            wmsLocationStereoscopic2.setUseStatus("1");
            wmsLocationStereoscopic2.setLastModifiedBy("WMS");
            wmsLocationStereoscopic2.setGmtModified(new Date());
            wmsLocationStereoscopicToList.add(wmsLocationStereoscopic2);
            // this.wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopic2);
            //将托盘，物料，批次，未过账，的任务 更改库位
            WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
            wmsTaskExecutionLog.setPalletCode(yuanList.get(j).getPalletCode());
            wmsTaskExecutionLog.setGoodsCode(yuanList.get(j).getGoodsCode());
            wmsTaskExecutionLog.setBatchNo(yuanList.get(j).getBatchNo());
            wmsTaskExecutionLog.setTaskStatus("3");
            wmsTaskExecutionLog.setUserDefined3("1");
            List idList = new ArrayList();
            idList.add("10");
            idList.add("50");
            wmsTaskExecutionLog.setIdList(idList);
            List<WmsTaskExecutionLog> list = wmsTaskExecutionLogMapper.selReceipt(wmsTaskExecutionLog);
            if (list != null && !list.isEmpty()) {
                list.get(0).setLocationCode(toLocationCode);
                wmsTaskExecutionLogList.add(list.get(0));
            }
        }
        //tasks
        jsonObject.put("tasks", JSONArray.parseArray(JSONObject.toJSONString(swiperList)));

        DifferentBusinessNHService differentBusinessHBService = this.differentBusinessNHFactory.getDifferentBusinessNHService(Constant.RecommendLocationEnum.getServiceNameByCode(applyfactory));

        try {
            //调WCS出库任务
            log.info("--------移库，调用wcs的任务接收接口 : -------" + jsonObject);
            JSONObject returnJsonObject = sLWCSService.taskReceive(jsonObject);
            //模拟返回
//           JSONObject returnJsonObject = new JSONObject();
//             returnJsonObject.put("returnStatus",0);
            //returnJsonObject.put("returnStatus",1);
            log.info("---------------移库，调用wcs的任务接收接口 -结束: " + returnJsonObject.toString());
            //接收成功
            if (returnJsonObject.getInteger("returnStatus") == 0) {
                log.info("移库，调用wcs的任务接收接口-成功返回！");
                if (wmsMoveStereoscopicList != null && wmsMoveStereoscopicList.size() > 0) {
                    for (WmsMoveStereoscopic wmsMoveStereoscopicNew : wmsMoveStereoscopicList) {
                        this.creatYklogList(wmsMoveStereoscopicNew);
                    }
                }
                if (wmsPalletList != null && wmsPalletList.size() > 0) {
                    for (WmsPallet wmsPallet : wmsPalletList) {
                        //根据托盘码锁定托盘
                        wmsPalletMapper.updateByPalletCode(wmsPallet);
                    }
                }
                if (wmsLocationStereoscopicFromList != null && wmsLocationStereoscopicFromList.size() > 0) {
                    for (WmsLocationStereoscopic wmsLocationStereoscopicOne : wmsLocationStereoscopicFromList) {
                        wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopicOne);
                    }
                }
                if (wmsLocationStereoscopicToList != null && wmsLocationStereoscopicToList.size() > 0) {
                    for (WmsLocationStereoscopic wmsLocationStereoscopicOne : wmsLocationStereoscopicToList) {
                        wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopicOne);
                    }
                }
                if (wmsTaskExecutionLogList != null && wmsTaskExecutionLogList.size() > 0) {
                    for (WmsTaskExecutionLog wmsTaskExecutionLog : wmsTaskExecutionLogList) {
                        wmsTaskExecutionLogMapper.updateByTaskId(wmsTaskExecutionLog);
                    }
                }
                resp.setCode(RESULT.SUCCESS.code);
                resp.setMsg("移库任务已下发！");
                return resp;
            } else {
                log.info("同层 出库-移库， 调wcs接口失败！");
                if (wmsMoveStereoscopicList != null && wmsMoveStereoscopicList.size() > 0) {
                    for (WmsMoveStereoscopic wmsMoveStereoscopicNew : wmsMoveStereoscopicList) {
                        differentBusinessHBService.reverseYk(wmsMoveStereoscopicNew);
                    }
                }
                resp.setCode(RESULT.FAILED.code);
                resp.setMsg("移库任务:请求失败！" + returnJsonObject.getString("returnInfo"));
                return resp;
            }
        } catch (Exception e) {
            log.info("同层 出库-移库， 调wcs接口异常！" + e.toString());
            if (wmsMoveStereoscopicList != null && wmsMoveStereoscopicList.size() > 0) {
                for (WmsMoveStereoscopic wmsMoveStereoscopicNew : wmsMoveStereoscopicList) {
                    differentBusinessHBService.reverseYk(wmsMoveStereoscopicNew);
                }
            }
            resp.setCode(RESULT.FAILED.code);
            resp.setMsg("移库任务:请求失败！" + e.toString());
            return resp;
        }
    }

    /**
     *功能描述: 原材料批量移库日志添加
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    private  Resp creatYklogList(WmsMoveStereoscopic wmsMoveStereoscopic) {
        String moveNo =this.wmsCommonService.getOrderNoByType("MS");
        Date now = new Date();
        Resp resp = new Resp();
        if (wmsMoveStereoscopic.getMoveNo() != null && !"".equals(wmsMoveStereoscopic.getMoveNo())) {
            WmsMoveStereoscopic updateOb = new WmsMoveStereoscopic();
            updateOb.setMoveNo(wmsMoveStereoscopic.getMoveNo());
            updateOb.setLastModifiedBy(wmsMoveStereoscopic.getCreateBy());
            updateOb.setGmtModified(now);
            this.wmsMoveStereoscopicMapper.updateByMoveNo(updateOb);
        }else {
            WmsMoveStereoscopic wmsMoveStereoscopicUpdatOb = new WmsMoveStereoscopic();
            if (wmsMoveStereoscopic.getMoveId() != null && !wmsMoveStereoscopic.getMoveId().isEmpty()) {
                wmsMoveStereoscopicUpdatOb.setMoveId(wmsMoveStereoscopic.getMoveId());
            } else {
                wmsMoveStereoscopicUpdatOb.setMoveId(String.valueOf(wmsMoveStereoscopic.getTaskId()));
            }
            wmsMoveStereoscopicUpdatOb.setMoveNo(moveNo);
            wmsMoveStereoscopicUpdatOb.setMoveStatus("2");
            wmsMoveStereoscopicUpdatOb.setLastModifiedBy(wmsMoveStereoscopic.getCreateBy());
            wmsMoveStereoscopicUpdatOb.setGmtModified(now);
            wmsMoveStereoscopicUpdatOb.setActiveFlag("1");
            wmsMoveStereoscopicUpdatOb.setUserDefined1("1");
            wmsMoveStereoscopicUpdatOb.setUserDefined3("1");
            wmsMoveStereoscopicUpdatOb.setAreaCode("L-NH01");
            wmsMoveStereoscopicUpdatOb.setGoodsCode(wmsMoveStereoscopic.getGoodsCode());
            wmsMoveStereoscopicUpdatOb.setBatchNo(wmsMoveStereoscopic.getBatchNo());
            wmsMoveStereoscopicUpdatOb.setGoodsName(wmsMoveStereoscopic.getGoodsName());
            wmsMoveStereoscopicUpdatOb.setPalletCode(wmsMoveStereoscopic.getPalletCode());
            wmsMoveStereoscopicUpdatOb.setFromLocationCode(wmsMoveStereoscopic.getFromLocationCode());
            wmsMoveStereoscopicUpdatOb.setToLocationCode(wmsMoveStereoscopic.getToLocationCode());
            wmsMoveStereoscopicUpdatOb.setAmount(wmsMoveStereoscopic.getAmount());
            wmsMoveStereoscopicUpdatOb.setGmtCreate(now);
            //备注
            wmsMoveStereoscopic.setUserDefined1(wmsMoveStereoscopic.getUserDefined1());
            this.wmsMoveStereoscopicMapper.create(wmsMoveStereoscopicUpdatOb);
        }
        WmsMoveLog wmsMoveLog = new WmsMoveLog();
        wmsMoveLog.setMoveLogId(CommonUtils.getUUID());
        wmsMoveLog.setPalletCode(wmsMoveStereoscopic.getPalletCode());
        wmsMoveLog.setAreaCode(wmsMoveStereoscopic.getAreaCode());
        wmsMoveLog.setFromLocationCode(wmsMoveStereoscopic.getFromLocationCode());
        wmsMoveLog.setToLocationCode(wmsMoveStereoscopic.getToLocationCode());
        wmsMoveLog.setMoveResult("0");
        wmsMoveLog.setCreateBy(wmsMoveStereoscopic.getCreateBy());
        wmsMoveLog.setGmtCreate(now);
        wmsMoveLog.setActiveFlag("1");
        wmsMoveLog.setAreaCode("L-NH01");
        wmsMoveLog.setWarehouseCode("NH_WAREHOUSE");
        if(wmsMoveStereoscopic.getUserDefined3()!=null &&!wmsMoveStereoscopic.getUserDefined3().isEmpty()){
            wmsMoveLog.setUserDefined3(wmsMoveStereoscopic.getUserDefined3());
        }
        if(wmsMoveStereoscopic.getUserDefined1()!=null &&!wmsMoveStereoscopic.getUserDefined1().isEmpty()){
            wmsMoveLog.setUserDefined1(wmsMoveStereoscopic.getUserDefined1());
        }
        wmsMoveLog.setGoodsCode(wmsMoveStereoscopic.getGoodsCode());
        wmsMoveLog.setBatchNo(wmsMoveStereoscopic.getBatchNo());
        wmsMoveLog.setAmount(wmsMoveStereoscopic.getAmount());
        this.wmsMoveLogMapper.create(wmsMoveLog);
        WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
        wmsTaskExecutionLog.setAreaCode(wmsMoveStereoscopic.getAreaCode());
        wmsTaskExecutionLog.setTaskId(wmsMoveStereoscopic.getTaskId());
        if (wmsMoveStereoscopic.getMoveNo() != null && !"".equals(wmsMoveStereoscopic.getMoveNo())) {
            wmsTaskExecutionLog.setOrderNo(wmsMoveStereoscopic.getMoveNo());
        }else {
            wmsTaskExecutionLog.setOrderNo(moveNo);
        }
        wmsTaskExecutionLog.setTaskType(String.valueOf(TaskType.NORMAL_MOVE.getTaskType()));
        wmsTaskExecutionLog.setPalletCode(wmsMoveStereoscopic.getPalletCode());
        wmsTaskExecutionLog.setTaskStatus("2");
        wmsTaskExecutionLog.setInAddress(wmsMoveStereoscopic.getToLocationCode());
        wmsTaskExecutionLog.setOutAddress(wmsMoveStereoscopic.getFromLocationCode());
        wmsTaskExecutionLog.setLocationCode(wmsMoveStereoscopic.getToLocationCode());
        wmsTaskExecutionLog.setGoodsCode(wmsMoveStereoscopic.getGoodsCode());
        wmsTaskExecutionLog.setBatchNo(wmsMoveStereoscopic.getBatchNo());
        wmsTaskExecutionLog.setCreateBy(wmsMoveStereoscopic.getCreateBy());
        if(wmsMoveStereoscopic.getUserDefined3()!=null &&!wmsMoveStereoscopic.getUserDefined3().isEmpty()){
            wmsTaskExecutionLog.setUserDefined3(wmsMoveStereoscopic.getUserDefined3());
        }
        if(wmsMoveStereoscopic.getUserDefined1()!=null &&!wmsMoveStereoscopic.getUserDefined1().isEmpty()){
            wmsTaskExecutionLog.setUserDefined1(wmsMoveStereoscopic.getUserDefined1());
        }
        wmsTaskExecutionLog.setWarehouseCode("NH_WAREHOUSE");
        wmsTaskExecutionLog.setAreaCode("L-NH01");
        wmsTaskExecutionLog.setGmtCreate(now);
        wmsTaskExecutionLog.setActiveFlag("1");
        this.wmsTaskExecutionLogMapper.create(wmsTaskExecutionLog);
        resp.setCode(RESULT.SUCCESS.code);
        return resp;
    }
}
