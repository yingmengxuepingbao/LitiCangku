package com.penghaisoft.pda.outwarehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.basic.model.PalletInDto;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Constant;
import com.penghaisoft.pda.common.IWmsCommonService;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.outwarehouse.dao.WmsOrderOutStereoscopicDeailMapper;
import com.penghaisoft.pda.outwarehouse.dao.WmsOrderOutStereoscopicMapper;
import com.penghaisoft.pda.outwarehouse.dao.WmsTaskExecutionLogMapper;
import com.penghaisoft.pda.outwarehouse.model.WmsOrderOutStereoscopic;
import com.penghaisoft.pda.outwarehouse.model.WmsOrderOutStereoscopicDeail;
import com.penghaisoft.pda.outwarehouse.model.WmsTaskExecutionLog;
import com.penghaisoft.pda.outwarehouse.service.WmsOrderOutStereoscopicService;
import com.penghaisoft.pda.outwarehouse.service.WmsTaskExecutionLogService;
import com.penghaisoft.pda.storage.dao.*;
import com.penghaisoft.pda.storage.model.*;
import com.penghaisoft.pda.storage.service.WmsLocationStereoscopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description 立库出库--分拣
 * @ClassName WmsOrderOutStereoscopicServiceImpl
 * @Author luot
 * @Date 2020/2/24 16:41
 **/
@Slf4j
@Service("baoboWmsOrderOutStereoscopicService")
@ConditionalOnProperty(prefix = "wcs.config",name = "applyfactory",havingValue = "BAOBO")
public class BaoboWmsOrderOutStereoscopicServiceImpl implements WmsOrderOutStereoscopicService {
    @Resource
    private WmsOrderOutStereoscopicMapper wmsOrderOutStereoscopicMapper;

    @Resource
    private WmsOrderOutStereoscopicDeailMapper wmsOrderOutStereoscopicDeailMapper;

    @Autowired
    private WmsPalletMapper wmsPalletMapper;

    @Autowired
    private WmsBoxBarcodeMapper wmsBoxBarcodeMapper;

    @Resource
    private WmsOutTemporaryMapper wmsOutTemporaryMapper;

    @Resource
    private WmsOutTemporaryBoxBarcodeMapper wmsOutTemporaryBoxBarcodeMapper;

    @Resource
    private WmsGoodsMapper wmsGoodsMapper;

    @Resource
    private WmsTaskExecutionLogMapper wmsTaskExecutionLogMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private WmsLocationStereoscopicService wmsLocationStereoscopicService;

    @Autowired
    private IWmsCommonService wmsCommonService;

    @Autowired
    private WmsTaskExecutionLogService wmsTaskExecutionLogService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${notice.other-sys-addr.pallet-in}")
    private String noticePalletInAddr;

    /**
     * @return
     * @Description 扫描托盘码,如果是自动分拣类型的 提示不能扫描
     * @Author zhangx
     * @Date 2020/6/8 16:40
     * @Param
     **/
    @Override
    public Resp scanPalletCode(String palletCode) {
        Resp resp = new Resp();
        WmsOrderOutStereoscopicDeail rtnob = new WmsOrderOutStereoscopicDeail();

        List<WmsPallet> pallets = wmsPalletMapper.selectByCode(palletCode);
        if (pallets == null || pallets.isEmpty()) {
            resp.setCode("0");
            resp.setMessage("查询不到该托盘");
            return resp;
        } else {
            WmsPallet pallet = pallets.get(0);
            String lockBy = pallet.getLockBy();
            if (lockBy == null || "".equals(lockBy)) {
//                这里应该是已经提交了，解绑了
                resp.setCode("0");
                resp.setMessage("该托盘锁定信息有误！");
                return resp;
            }
            Long taskId = Long.parseLong(lockBy);
            List<WmsTaskExecutionLog> taskExecutionLogs = wmsTaskExecutionLogMapper.selectByTaskId(taskId);
//            实际应该是两条记录，一条状态1，一条状态3
            if (taskExecutionLogs.size()>0){
                WmsTaskExecutionLog wmsTaskExecutionLog = taskExecutionLogs.get(0);
                String taskType = wmsTaskExecutionLog.getTaskType();
//                自动分拣类型的，在线体任务调度那里进行处理
                if (Long.parseLong(taskType)==Constant.TaskType.STRAIGHT_OUT.getTaskType()){
                    resp.setCode("0");
                    resp.setMessage("自动分拣任务无须扫码!");
                    return resp;
                }
            }else {
                resp.setCode("0");
                resp.setMessage("任务执行信息有误!");
                return resp;
            }


//            是否有箱码 1 有 0 没有
            String hasBoxCode = "0";

            WmsBoxBarcode wmsBoxBarcode = new WmsBoxBarcode();
            wmsBoxBarcode.setPalletCode(pallet.getPalletCode());
            List<WmsBoxBarcode> wmsBoxBarcodeList = wmsBoxBarcodeMapper.selectByPalletCode(wmsBoxBarcode);
            if (wmsBoxBarcodeList != null && !wmsBoxBarcodeList.isEmpty()) {
                hasBoxCode = "1";
            }
//          todo 这里是为了什么？按单出库？？
//            String orderNo = lockBy.replace("sorting-", "");
            String orderNo = pallet.getUserDefined1();

            WmsOrderOutStereoscopicDeail searchOb = new WmsOrderOutStereoscopicDeail();
            searchOb.setOrderNo(orderNo);
            searchOb.setGoodsCode(pallet.getGoodsCode());
            searchOb.setBatchNo(pallet.getBatchNo());
            List<WmsOrderOutStereoscopicDeail> deailList = wmsOrderOutStereoscopicDeailMapper.queryByAny(searchOb);
//            这里想整托出库，如何判断？？
            if(deailList != null && !deailList.isEmpty()){
                rtnob = deailList.get(0);
//                计划数量
                Integer planAmount = rtnob.getPlanAmount() == null ? 0 : rtnob.getPlanAmount();
//                已捡数量
                Integer realAmount = rtnob.getRealAmount() == null ? 0 : rtnob.getRealAmount();
//                当前托盘数量
                Integer amount = pallet.getAmount() == null ? 0 : pallet.getAmount();

//                1 整托分拣 0 拆托
                String isSortingAll = "1";
//                如果当前托盘数量amount<planAmount-realAmount，代表这个托盘需要整托分拣，计算得到isSortingAll
                if(amount > planAmount-realAmount){
                    isSortingAll = "0";
                    rtnob.setPlanSortingAmount(planAmount-realAmount);
                }else {
                    rtnob.setPlanSortingAmount(amount);
                }

                rtnob.setAmount(amount);
                rtnob.setIsSortingAll(isSortingAll);
                rtnob.setHasBoxCode(hasBoxCode);
                rtnob.setPalletCode(palletCode);
            }else{
//                这里是托盘出库？
                rtnob.setOrderNo(orderNo);
                rtnob.setGoodsCode(pallet.getGoodsCode());
                rtnob.setGoodsName(pallet.getGoodsName());
                rtnob.setBatchNo(pallet.getBatchNo());
                rtnob.setAmount(pallet.getAmount());
                rtnob.setPlanSortingAmount(pallet.getAmount());
//                1 整托分拣 0 拆托【托盘出库的分拣全部是拆托】
                rtnob.setIsSortingAll("0");
                rtnob.setHasBoxCode(hasBoxCode);
                rtnob.setPalletCode(palletCode);
            }
        }

        resp.setCode("1");
        resp.setData(rtnob);
//        这里放入信息是为了给电子看板提示
        stringRedisTemplate.opsForValue().set("WMS:HANDSORT:INFO", JSON.toJSONString(rtnob));
        return resp;
    }

    /**
      * @Description 整托分拣
      * @Author luot
      * @Date 2020/2/24 17:40
      * @Param 
      * @return 
      **/
    @Transactional
    @Override
    public Resp allsubmit(String palletCode, String account){
        Resp resp = new Resp();
        Date now = new Date();

        List<WmsPallet> pallets = wmsPalletMapper.selectByCode(palletCode);
        if (pallets == null || pallets.isEmpty()) {
            resp.setCode("0");
            resp.setMessage("查询不到该托盘");
            return resp;
        } else {
            WmsPallet pallet = pallets.get(0);
            String lockBy = pallet.getLockBy();
            if (lockBy == null || "".equals(lockBy)) {
                resp.setCode("0");
                resp.setMessage("该托盘锁定信息有误！");
                return resp;
            }

//            托盘解绑
            WmsPallet updateOb = new WmsPallet();
            updateOb.setPalletCode(pallet.getPalletCode());
            updateOb.setLockByNull("null");
            updateOb.setLocationCodeNull("null");
//                    途径库位可能存在，清掉
            updateOb.setChannelLocationNull("null");
            updateOb.setGoodsCodeNull("null");
            updateOb.setBatchNoNull("null");
            updateOb.setAmountNull("null");
//            清空订单号
            updateOb.setUserDefined1("");
            updateOb.setLastModifiedBy(account);
            updateOb.setGmtModified(now);
            wmsPalletMapper.updateByCode(updateOb);

//            String orderNo = lockBy.replace("sorting-", "");
            String orderNo = pallet.getUserDefined1();

            if(!orderNo.contains("PalletOutVirtualOrderNo")){
                WmsOrderOutStereoscopicDeail searchOb = new WmsOrderOutStereoscopicDeail();
                searchOb.setOrderNo(orderNo);
                searchOb.setGoodsCode(pallet.getGoodsCode());
                searchOb.setBatchNo(pallet.getBatchNo());
                List<WmsOrderOutStereoscopicDeail> deailList = wmsOrderOutStereoscopicDeailMapper.queryByAny(searchOb);
                if(deailList != null && !deailList.isEmpty()){
                    WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail = deailList.get(0);
                    WmsOrderOutStereoscopicDeail detailOb = new WmsOrderOutStereoscopicDeail();
                    detailOb.setOrderOutDetailId(wmsOrderOutStereoscopicDeail.getOrderOutDetailId());
                    detailOb.setRealAmountAdd(pallet.getAmount());
                    detailOb.setLastModifiedBy(account);
                    detailOb.setGmtModified(now);
//                  将立库出库明细表当前商品+批次的实际数量增加
                    wmsOrderOutStereoscopicDeailMapper.updateByPrimaryKeySelective(detailOb);
                }

                WmsOrderOutStereoscopicDeail completeOb = new WmsOrderOutStereoscopicDeail();
                completeOb.setOrderNo(orderNo);
                completeOb.setPlanAmountMoreRealAmount("PlanAmountMoreRealAmount");
                completeOb.setActiveFlag("1");
                List<WmsOrderOutStereoscopicDeail> completeList= wmsOrderOutStereoscopicDeailMapper.queryByAny(completeOb);
//                  发货完成，计划数量>实际数量的记录不存在
                if(completeList == null || completeList.isEmpty()){
                    WmsOrderOutStereoscopic record = new WmsOrderOutStereoscopic();
                    record.setOrderNo(orderNo);
//                      订单状态1创建2启动3完成4异常
                    record.setOrderStatus("3");
                    record.setLastModifiedBy(account);
                    record.setGmtModified(now);
                    wmsOrderOutStereoscopicMapper.updateByOrderNo(record);
                }
            }

            List<WmsGoods> wmsGoodsList = wmsGoodsMapper.selectByGoodsCode(pallet.getGoodsCode());

            List<WmsOutTemporary> wmsOutTemporaryList = wmsOutTemporaryMapper.selectByGoodsCode(pallet.getGoodsCode());
            if (wmsOutTemporaryList != null && !wmsOutTemporaryList.isEmpty()) {
//                增加出库暂存区库存
                WmsOutTemporary updateOutTemporary = new WmsOutTemporary();
                updateOutTemporary.setGoodsCode(pallet.getGoodsCode());
                updateOutTemporary.setAmount(pallet.getAmount());
                updateOutTemporary.setLastModifiedBy(account);
                updateOutTemporary.setGmtModified(now);
                wmsOutTemporaryMapper.addAmountByGoodsCode(updateOutTemporary);
            } else {
//                如果出库暂存区记录不存在就创建一条记录
                WmsOutTemporary wmsOutTemporary = new WmsOutTemporary();
                if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
                    wmsOutTemporary.setGoodsName(wmsGoodsList.get(0).getGoodsName());
                }
                wmsOutTemporary.setId(CommonUtil.getUUID());
                wmsOutTemporary.setGoodsCode(pallet.getGoodsCode());
                wmsOutTemporary.setAmount(pallet.getAmount());
                wmsOutTemporary.setCreateBy(account);
                wmsOutTemporary.setGmtCreate(now);
                wmsOutTemporaryMapper.insertSelective(wmsOutTemporary);
            }

            WmsBoxBarcode wmsBoxBarcode = new WmsBoxBarcode();
            wmsBoxBarcode.setPalletCode(pallet.getPalletCode());
            List<WmsBoxBarcode> wmsBoxBarcodeList = wmsBoxBarcodeMapper.selectByPalletCode(wmsBoxBarcode);
//            将托盘的箱码从wmsBoxBarcode迁移到wmsOutTemporaryBoxBarcode
            if (wmsBoxBarcodeList != null && !wmsBoxBarcodeList.isEmpty()) {
                wmsBoxBarcodeMapper.deleteByPalletCode(wmsBoxBarcode);
                List<WmsOutTemporaryBoxBarcode> wmsOutTemporaryBoxBarcodeList = new ArrayList<WmsOutTemporaryBoxBarcode>();
                for (WmsBoxBarcode tmp : wmsBoxBarcodeList) {
                    WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode = new WmsOutTemporaryBoxBarcode();
                    wmsOutTemporaryBoxBarcode.setId(CommonUtil.getUUID());
                    wmsOutTemporaryBoxBarcode.setOrderNo(orderNo);
                    wmsOutTemporaryBoxBarcode.setGoodsCode(pallet.getGoodsCode());
                    if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
                        wmsOutTemporaryBoxBarcode.setGoodsName(wmsGoodsList.get(0).getGoodsName());
                    }
                    wmsOutTemporaryBoxBarcode.setBatchNo(pallet.getBatchNo());
                    wmsOutTemporaryBoxBarcode.setBoxBarcode(tmp.getBoxBarcode());
                    wmsOutTemporaryBoxBarcode.setCreateBy(account);
                    wmsOutTemporaryBoxBarcode.setGmtCreate(now);
                    wmsOutTemporaryBoxBarcodeList.add(wmsOutTemporaryBoxBarcode);
                }
                wmsOutTemporaryBoxBarcodeMapper.batchInsert(wmsOutTemporaryBoxBarcodeList);
            }
        }

        resp.setCode("1");
        resp.setData(null);
        return resp;
    }

    /**
      * @Description 拆托分拣，将任务下发给wcs
      * @Author luot
      * @Date 2020/2/24 17:40
      * @Param
      * @return
      **/
    @Transactional
    @Override
    public Resp splitsubmit(String palletCode, Integer realAmount, String isPalletBack, List<String> boxBarcodeList, String account){
        Resp resp = new Resp();
        Date now = new Date();

        List<WmsPallet> pallets = wmsPalletMapper.selectByCode(palletCode);
        if (pallets == null || pallets.isEmpty()) {
            resp.setCode("0");
            resp.setMessage("查询不到该托盘");
            return resp;
        }
        WmsPallet pallet = pallets.get(0);
        String lockBy = pallet.getLockBy();
        if (lockBy == null || "".equals(lockBy)) {
            resp.setCode("0");
            resp.setMessage("该托盘锁定信息有误！");
            return resp;
        }
//          托盘表修改
        WmsPallet updateOb = new WmsPallet();
        updateOb.setPalletCode(pallet.getPalletCode());
        updateOb.setLockByNull("null");
        updateOb.setLocationCodeNull("null");
//                    途径库位可能存在，清掉
        updateOb.setChannelLocationNull("null");
//            托盘是否返库【0：返库 1：入暂存区】
        if(isPalletBack != null && "0".equals(isPalletBack)){
            updateOb.setAmountDel(realAmount);
        }else{
            updateOb.setGoodsCodeNull("null");
            updateOb.setBatchNoNull("null");
            updateOb.setAmountNull("null");
        }
        updateOb.setLastModifiedBy(account);
        updateOb.setGmtModified(now);
        wmsPalletMapper.updateByCode(updateOb);

//            String orderNo = lockBy.replace("sorting-", "");
        String orderNo = pallet.getUserDefined1();

        if(!orderNo.contains("PalletOutVirtualOrderNo")){
//                发货明细表和发货主表修改
            WmsOrderOutStereoscopicDeail searchOb = new WmsOrderOutStereoscopicDeail();
            searchOb.setOrderNo(orderNo);
            searchOb.setGoodsCode(pallet.getGoodsCode());
            searchOb.setBatchNo(pallet.getBatchNo());
            List<WmsOrderOutStereoscopicDeail> deailList = wmsOrderOutStereoscopicDeailMapper.queryByAny(searchOb);
            if(deailList != null && !deailList.isEmpty()){
                WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail = deailList.get(0);
                WmsOrderOutStereoscopicDeail detailOb = new WmsOrderOutStereoscopicDeail();
                detailOb.setOrderOutDetailId(wmsOrderOutStereoscopicDeail.getOrderOutDetailId());
                detailOb.setRealAmountAdd(realAmount);
                detailOb.setLastModifiedBy(account);
                detailOb.setGmtModified(now);
//                      将立库出库明细表当前商品+批次的实际数量增加
                wmsOrderOutStereoscopicDeailMapper.updateByPrimaryKeySelective(detailOb);
            }

            WmsOrderOutStereoscopicDeail completeOb = new WmsOrderOutStereoscopicDeail();
            completeOb.setOrderNo(orderNo);
            completeOb.setPlanAmountMoreRealAmount("PlanAmountMoreRealAmount");
            completeOb.setActiveFlag("1");
            List<WmsOrderOutStereoscopicDeail> completeList= wmsOrderOutStereoscopicDeailMapper.queryByAny(completeOb);
//                  发货完成
            if(completeList == null || completeList.isEmpty()){
                WmsOrderOutStereoscopic record = new WmsOrderOutStereoscopic();
                record.setOrderNo(orderNo);
//                      订单状态1创建2启动3完成4异常
                record.setOrderStatus("3");
                record.setLastModifiedBy(account);
                record.setGmtModified(now);
                wmsOrderOutStereoscopicMapper.updateByOrderNo(record);
            }
        }

        List<WmsGoods> wmsGoodsList = wmsGoodsMapper.selectByGoodsCode(pallet.getGoodsCode());
//            增加出库暂存区库存
        List<WmsOutTemporary> wmsOutTemporaryList = wmsOutTemporaryMapper.selectByGoodsCode(pallet.getGoodsCode());
        if (wmsOutTemporaryList != null && !wmsOutTemporaryList.isEmpty()) {
            WmsOutTemporary updateOutTemporary = new WmsOutTemporary();
            updateOutTemporary.setGoodsCode(pallet.getGoodsCode());
//                托盘是否返库【0：返库 1：入暂存区】
            if(isPalletBack != null && "0".equals(isPalletBack)){
                updateOutTemporary.setAmount(realAmount);
            }else{
                updateOutTemporary.setAmount(pallet.getAmount());
            }
            updateOutTemporary.setLastModifiedBy(account);
            updateOutTemporary.setGmtModified(now);
            wmsOutTemporaryMapper.addAmountByGoodsCode(updateOutTemporary);
        } else {
            WmsOutTemporary wmsOutTemporary = new WmsOutTemporary();
            if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
                wmsOutTemporary.setGoodsName(wmsGoodsList.get(0).getGoodsName());
            }
            wmsOutTemporary.setId(CommonUtil.getUUID());
            wmsOutTemporary.setGoodsCode(pallet.getGoodsCode());
//                托盘是否返库【0：返库 1：入暂存区】
            if(isPalletBack != null && "0".equals(isPalletBack)){
                wmsOutTemporary.setAmount(realAmount);
            }else{
                wmsOutTemporary.setAmount(pallet.getAmount());
            }
            wmsOutTemporary.setCreateBy(account);
            wmsOutTemporary.setGmtCreate(now);
            wmsOutTemporaryMapper.insertSelective(wmsOutTemporary);
        }

//            托盘是否返库【0：返库 1：入暂存区】
        if(isPalletBack != null && "0".equals(isPalletBack)){
            if(boxBarcodeList != null && !boxBarcodeList.isEmpty()){
                wmsBoxBarcodeMapper.deleteByBoxBarcodeBatch(boxBarcodeList);

                List<WmsOutTemporaryBoxBarcode> wmsOutTemporaryBoxBarcodeList = new ArrayList<WmsOutTemporaryBoxBarcode>();
                for (String boxBarcode : boxBarcodeList) {
                    WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode = new WmsOutTemporaryBoxBarcode();
                    wmsOutTemporaryBoxBarcode.setId(CommonUtil.getUUID());
                    wmsOutTemporaryBoxBarcode.setOrderNo(orderNo);
                    wmsOutTemporaryBoxBarcode.setGoodsCode(pallet.getGoodsCode());
                    if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
                        wmsOutTemporaryBoxBarcode.setGoodsName(wmsGoodsList.get(0).getGoodsName());
                    }
                    wmsOutTemporaryBoxBarcode.setBatchNo(pallet.getBatchNo());
                    wmsOutTemporaryBoxBarcode.setBoxBarcode(boxBarcode);
                    wmsOutTemporaryBoxBarcode.setCreateBy(account);
                    wmsOutTemporaryBoxBarcode.setGmtCreate(now);
                    wmsOutTemporaryBoxBarcodeList.add(wmsOutTemporaryBoxBarcode);
                }
                wmsOutTemporaryBoxBarcodeMapper.batchInsert(wmsOutTemporaryBoxBarcodeList);
            }
        }else{
            WmsBoxBarcode wmsBoxBarcode = new WmsBoxBarcode();
            wmsBoxBarcode.setPalletCode(pallet.getPalletCode());
            List<WmsBoxBarcode> wmsBoxBarcodeList = wmsBoxBarcodeMapper.selectByPalletCode(wmsBoxBarcode);
            if (wmsBoxBarcodeList != null && !wmsBoxBarcodeList.isEmpty()) {
                wmsBoxBarcodeMapper.deleteByPalletCode(wmsBoxBarcode);
                List<WmsOutTemporaryBoxBarcode> wmsOutTemporaryBoxBarcodeList = new ArrayList<WmsOutTemporaryBoxBarcode>();
                for (WmsBoxBarcode tmp : wmsBoxBarcodeList) {
                    WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode = new WmsOutTemporaryBoxBarcode();
                    wmsOutTemporaryBoxBarcode.setId(CommonUtil.getUUID());
                    wmsOutTemporaryBoxBarcode.setOrderNo(orderNo);
                    wmsOutTemporaryBoxBarcode.setGoodsCode(pallet.getGoodsCode());
                    if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
                        wmsOutTemporaryBoxBarcode.setGoodsName(wmsGoodsList.get(0).getGoodsName());
                    }
                    wmsOutTemporaryBoxBarcode.setBatchNo(pallet.getBatchNo());
                    wmsOutTemporaryBoxBarcode.setBoxBarcode(tmp.getBoxBarcode());
                    wmsOutTemporaryBoxBarcode.setCreateBy(account);
                    wmsOutTemporaryBoxBarcode.setGmtCreate(now);
                    wmsOutTemporaryBoxBarcodeList.add(wmsOutTemporaryBoxBarcode);
                }
                wmsOutTemporaryBoxBarcodeMapper.batchInsert(wmsOutTemporaryBoxBarcodeList);
            }
        }

//        返库，要调用wcs
        if (null != isPalletBack && "0".equals(isPalletBack)){

            Resp recommondResp = wmsLocationStereoscopicService.queryRecommendLocationCode(palletCode);
            if ("0".equals(recommondResp.getCode())) {
                throw new RuntimeException("分拣回库时推荐库位失败！");
            }
            String targetLocation = (String) recommondResp.getData();
            long taskId = wmsCommonService.getTaskIds(Constant.TaskType.HAND_IN, 1)[0];
            WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
            wmsTaskExecutionLog.setAreaCode("L-BJ01");
//            任务类型 1 生产入库 2 分拣入库 3 移库 4 出库 5越库 6托盘入库
            wmsTaskExecutionLog.setTaskType(String.valueOf(Constant.TaskType.HAND_IN.getTaskType()));
            wmsTaskExecutionLog.setPalletCode(palletCode);
//            入库口地址
            wmsTaskExecutionLog.setInAddress("1");
//            任务状态1创建2执行3完成4异常5取消6创建失败
            wmsTaskExecutionLog.setTaskStatus("1");
            wmsTaskExecutionLog.setGoodsCode(pallet.getGoodsCode());
            wmsTaskExecutionLog.setBatchNo(pallet.getBatchNo());
            wmsTaskExecutionLog.setCreateBy(account);
            wmsTaskExecutionLog.setGmtCreate(now);
            wmsTaskExecutionLog.setActiveFlag("1");
            wmsTaskExecutionLog.setTaskId(taskId);
//        wmsTaskExecutionLog.setOrderNo(orderNo);
            wmsTaskExecutionLog.setLocationCode(targetLocation);

            try {
                PalletInDto palletInDto = new PalletInDto();
                palletInDto.setTaskId(taskId);
                palletInDto.setTaskType(String.valueOf(Constant.TaskType.HAND_IN.getTaskType()));
//            目标库位
                palletInDto.setTargetLocation(Integer.valueOf(targetLocation));
//            入口path
                palletInDto.setFromAddress(1);
                palletInDto.setPalletCode(palletCode);
                palletInDto.setOperator(account);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<PalletInDto> request = new HttpEntity<PalletInDto>(palletInDto, headers);

//                          调用wcs接收出库指令接口
                ResponseEntity<JSONObject> wcsResp = restTemplate.postForEntity(noticePalletInAddr, request, JSONObject.class);
                if (wcsResp.getStatusCodeValue()!=200){
                    log.error("调wcs接口失败！");
//                库位状态回滚成初始状态0可用
//                    wmsLocationStereoscopicService.revertLocationStatus0(targetLocation);
                    throw new RuntimeException("启动失败，获取推荐库位成功，调用WCS入库接口失败;");
//                    stringRedisTemplate.delete(key);//删除对象
                }else {
                    JSONObject noticeResult = wcsResp.getBody();
//                              状态码：1成功 0 本次下达失败
                    if (noticeResult.getString("code").equals("1")){
                        log.info("调wcs接口成功！");
                    }else {
                        log.error("调wcs接口失败：" + noticeResult.getString("message"));
//                    库位状态回滚成初始状态0可用
//                        wmsLocationStereoscopicService.revertLocationStatus0(targetLocation);
                        throw new RuntimeException("启动失败，获取推荐库位成功，调用WCS入库接口失败;");
//                        stringRedisTemplate.delete(key);//删除对象
//                        return result;
                    }
                }

//            创建入立体库的指令任务、更新托盘状态
                wmsTaskExecutionLogService.inStereoscopicTaskCreate(wmsTaskExecutionLog);
            } catch (Exception e) {
                e.printStackTrace();
//            库位状态回滚成初始状态0可用
//                wmsLocationStereoscopicService.revertLocationStatus0(targetLocation);
                throw new RuntimeException("启动失败，获取推荐库位成功，调用WCS入库接口失败;");
//                stringRedisTemplate.delete(key);//删除对象
//                return result;
            }
        }
        resp.setCode("1");
        resp.setData(null);
        return resp;
    }

    /**
      * @Description 扫描箱码
      * @Author luot
      * @Date 2020/2/24 17:30
      * @Param
      * @return
      **/
    @Override
    public Resp scanBoxBarcode(String palletCode, String boxBarcode) {
        Resp resp = new Resp();
        WmsBoxBarcodeKey boxBarcodeKey= new WmsBoxBarcodeKey();
        boxBarcodeKey.setBoxBarcode(boxBarcode);
        boxBarcodeKey.setPalletCode(palletCode);
        WmsBoxBarcodeKey key = wmsBoxBarcodeMapper.selectByPrimaryKey(boxBarcodeKey);
        if(key == null ){
            resp.setCode("0");
            resp.setMessage("查询不到该箱码");
            return resp;
        }
        resp.setCode("1");
        resp.setData(key);
        return resp;
    }

    /**
     * 校验箱码是否在托盘内
     *
     * @param palletCode
     * @param boxBarcodeList
     * @return
     */
    @Override
    public boolean checkBarcodesBelongPallet(String palletCode, List<String> boxBarcodeList) {
        WmsBoxBarcode wmsBoxBarcode = new WmsBoxBarcode();
        wmsBoxBarcode.setPalletCode(palletCode);
        List<WmsBoxBarcode> wmsBoxBarcodeList = wmsBoxBarcodeMapper.selectByPalletCode(wmsBoxBarcode);
        Set<String> realCodes = new HashSet<>();
        for (WmsBoxBarcode realBarcode : wmsBoxBarcodeList) {
            realCodes.add(realBarcode.getBoxBarcode());
        }
        for (String inputBarcode:boxBarcodeList) {
            if (!realCodes.contains(inputBarcode)){
                return false;
            }
        }
        return true;
    }

}
