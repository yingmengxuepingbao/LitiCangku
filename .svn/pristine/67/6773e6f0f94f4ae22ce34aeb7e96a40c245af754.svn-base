package com.penghaisoft.pda.storage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.basic.model.PalletInDto;
import com.penghaisoft.pda.common.Constant;
import com.penghaisoft.pda.common.IWmsCommonService;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.outwarehouse.model.WmsTaskExecutionLog;
import com.penghaisoft.pda.outwarehouse.service.WmsTaskExecutionLogService;
import com.penghaisoft.pda.storage.dao.*;
import com.penghaisoft.pda.storage.model.*;
import com.penghaisoft.pda.storage.service.StereoscopicCheckService;
import com.penghaisoft.pda.storage.service.WmsLocationStereoscopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

/**
 * @Description 立库盘点【除了提单、扫描托盘后的确认（需要调wcs），剩余的全部调平库的盘点接口】
 * @ClassName StereoscopicCheckServiceImpl
 * @Author luot
 * @Date 2020/3/5 22:48
 **/
@Slf4j
@Service("stereoscopicCheckService")
public class StereoscopicCheckServiceImpl implements StereoscopicCheckService {

    @Autowired
    private WmsOrderCheckPalletMapper wmsOrderCheckPalletMapper;

    @Autowired
    private WmsOrderCheckMapper wmsOrderCheckMapper;

    @Autowired
    private WmsGoodsMapper goodsMapper;

    @Autowired
    private WmsPalletMapper palletMapper;

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

    @Autowired
    private WmsPalletMapper wmsPalletMapper;

    @Autowired
    private WmsOrderCheckPalletBoxBarcodeMapper wmsOrderCheckPalletBoxBarcodeMapper;

    /**
     * @return
     * @Description 盘点提单
     * @Author luot
     * @Date 2020/3/5 14:42
     * @Param
     **/
    @Override
    public Resp checkLadingBill(String orderNo, String areaCode, String account) {
        Resp resp = new Resp();

        List<WmsOrderCheck> checkList = wmsOrderCheckMapper.selectByOrderNo(orderNo);
        if (checkList == null || checkList.isEmpty()) {
            resp.setCode("0");
            resp.setMessage("查无数据！");
            return resp;
        } else {
            WmsOrderCheck tmp = checkList.get(0);
//            库区类型，平面库0，立体库1，2暂存区
            if (tmp.getAreaType() != null && !"1".equals(tmp.getAreaType())) {
                resp.setCode("0");
                resp.setMessage("当前盘点单非立库盘点单！");
                return resp;
            }

//            订单状态1创建 2盘点中 3完成4异常
            if (tmp.getOrderStatus() != null && "1".equals(tmp.getOrderStatus())) {
                resp.setCode("0");
                resp.setMessage("当前盘点单未启动立库出库！");
                return resp;
            }

            if (!tmp.getAreaCode().equals(areaCode)) {
                resp.setCode("0");
                resp.setMessage("当前盘点对应库区非当前用户所属库区！");
                return resp;
            }

//            订单状态1创建 2盘点中 3完成4异常
            if (tmp.getOrderStatus() != null && "3".equals(tmp.getOrderStatus())) {
                resp.setCode("0");
                resp.setMessage("当前盘点单已完成！");
                return resp;
            }
        }

        //汇总查询，需盘点信息
        WmsOrderCheckPallet orderCheckPallet = new WmsOrderCheckPallet();
        orderCheckPallet.setOrderNo(orderNo);
        orderCheckPallet.setActiveFlag("1");
        List<WmsOrderCheckPallet> list = wmsOrderCheckPalletMapper.collectGroupBYGoods(orderCheckPallet);
        if (list == null || list.isEmpty()) {
            resp.setCode("0");
            resp.setMessage("查无数据！");
            return resp;
        } else {
            WmsOrderCheckPallet tmp = list.get(0);
//            uni-collapse 展开用 ture展开
            tmp.setOpen(true);
        }
        resp.setCode("1");
        resp.setData(list);
        return resp;
    }

    /**
     * 扫描托盘号
     * @param orderNo
     * @param palletCode
     * @param account
     * @return
     */
    @Override
    public Resp checkScanPallet(String orderNo, String palletCode, String account) {
        Resp resp =new Resp();

        WmsOrderCheckPallet orderCheckPallet = new WmsOrderCheckPallet();
        orderCheckPallet.setOrderNo(orderNo);
        orderCheckPallet.setPalletCode(palletCode);
        orderCheckPallet.setActiveFlag("1");
        List<WmsOrderCheckPallet> list = wmsOrderCheckPalletMapper.queryByAny(orderCheckPallet);
        if (list.size()<=0){
            //去托盘表继续校验是否未绑定
            List<WmsPallet> pallets = palletMapper.selectByCode(palletCode);
            if (pallets.size()<=0){
                resp.setCode("0");
                resp.setMessage("查询不到此托盘，请绑定");
                return resp;
            }else{
                WmsPallet tmp = pallets.get(0);
                if(tmp.getLockBy() != null && !"".equals(tmp.getLockBy())){
                    resp.setCode("0");
                    resp.setMessage("该托盘码被锁定，不能盘点");
                    return resp;
                }
            }
            resp.setCode("0");
            resp.setMessage("此托盘不属于盘点范围");
            return resp;
        }
        String ifCheck = list.get(0).getUserDefined1();
        if (ifCheck.equals("1")){
            resp.setCode("0");
            resp.setMessage("该托盘已盘点");
            return resp;
        }
        List<WmsGoods> wmsGoodsList = goodsMapper.selectByGoodsCode(list.get(0).getGoodsCode());
        String hasBoxCode = "";
        if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
            hasBoxCode = wmsGoodsList.get(0).getHasBoxCode();
        }else{
//            是否有箱码 1有0 无【商品查不到,就默认没有箱码】
            hasBoxCode = "0";
        }
        list.get(0).setHasBoxCode(hasBoxCode);
        resp.setCode("1");
        resp.setData(list.get(0));
        return resp;
    }

    /**
     * 托盘确认
     *
     * @param account
     * @param param
     * @return
     */
    @Override
    @Transactional
    public Resp confirmPallet(String account, WmsOrderCheckPallet param) {
        Resp resp = new Resp();
        Date now = new Date();
        String orderNo = param.getOrderNo();
        String palletCode = param.getPalletCode();
        WmsOrderCheckPallet rtnOb = new WmsOrderCheckPallet();
        //查询是否已经盘点过
        WmsOrderCheckPallet orderCheckPallet = new WmsOrderCheckPallet();
        orderCheckPallet.setOrderNo(orderNo);
        orderCheckPallet.setPalletCode(palletCode);
        orderCheckPallet.setActiveFlag("1");
        List<WmsOrderCheckPallet> list = wmsOrderCheckPalletMapper.queryByAny(orderCheckPallet);
        if(list != null && !list.isEmpty()){
            String ifCheck = list.get(0).getUserDefined1();
            if (ifCheck.equals("1")){
                resp.setCode("0");
                resp.setMessage("该托盘已盘点");
                return resp;
            }
            rtnOb = list.get(0);
        }

        //查看是否有无箱码,根据托盘号到wms_order_check_pallet_box_barcode
        WmsOrderCheckPalletBoxBarcode palletBoxBarcode = new WmsOrderCheckPalletBoxBarcode();
        palletBoxBarcode.setOrderNo(orderNo);
        palletBoxBarcode.setPalletCode(palletCode);
        palletBoxBarcode.setActiveFlag("1");
        List<WmsOrderCheckPalletBoxBarcode> listBarcode = wmsOrderCheckPalletBoxBarcodeMapper.queryByAny(palletBoxBarcode);
        if (listBarcode.size()>0){
            //有箱码信息需要比对
            List<String> barcodeList = param.getBarcodeList();
            for(String barcode : barcodeList){

                WmsOrderCheckPalletBoxBarcode boxBarcode = new WmsOrderCheckPalletBoxBarcode();
                boxBarcode.setOrderNo(orderNo);
                boxBarcode.setPalletCode(palletCode);
                boxBarcode.setActiveFlag("1");
                boxBarcode.setBoxBarcode(barcode);
                List<WmsOrderCheckPalletBoxBarcode> boxBarcodeList = wmsOrderCheckPalletBoxBarcodeMapper.queryByAny(boxBarcode);
                //存在修改，不存在新增
                if (boxBarcodeList.size()>0){
                    //如果有箱码barcodes，根据托盘号到wms_order_check_pallet_box_barcode填充real_box_barcode，
                    WmsOrderCheckPalletBoxBarcode updateBarcode = new WmsOrderCheckPalletBoxBarcode();
                    updateBarcode.setCheckPalletBoxBarcodeId(boxBarcodeList.get(0).getCheckPalletBoxBarcodeId());
                    updateBarcode.setRealBoxBarcode(barcode);
                    //差异状态0无差异
                    updateBarcode.setDiffFlag("0");
                    updateBarcode.setLastModifiedBy(account);
                    updateBarcode.setGmtModified(now);
                    int changeNum = wmsOrderCheckPalletBoxBarcodeMapper.updateByPrimaryKeySelective(updateBarcode);
                }else {
                    // 这时候要插入一条数据，box_barcode=空，real_box_barcode=实际箱码，diff_flag=1;
                    WmsOrderCheckPalletBoxBarcode createBarcode = new WmsOrderCheckPalletBoxBarcode();
//                    createBarcode.setCheckPalletBoxBarcodeId(CommonUtil.getUUID());
                    createBarcode.setOrderNo(orderNo);
                    createBarcode.setDiffFlag("1");
                    createBarcode.setPalletCode(palletCode);
                    createBarcode.setRealBoxBarcode(barcode);
                    createBarcode.setCreateBy(account);
                    createBarcode.setGmtCreate(now);
                    createBarcode.setActiveFlag("1");
                    int createNum = wmsOrderCheckPalletBoxBarcodeMapper.insertSelective(createBarcode);
                }
            }
        }
        //如果少了某个箱码，原数据的real_box_barcode应该为空,diff_flag插入时候=1，所以这条数据也是差异数据。


        //更改盘点托盘实际数量wms_order_check_pallet
        WmsOrderCheckPallet checkPallet = new WmsOrderCheckPallet();
        checkPallet.setCheckPalletId(list.get(0).getCheckPalletId());
        checkPallet.setRealAmount(param.getRealAmount());
        checkPallet.setUserDefined1("1");
        //校验托盘数量
        if (list.get(0).getAmount()==param.getRealAmount()){
            //校验箱码是否一样
            WmsOrderCheckPalletBoxBarcode checkBoxBarcode = new WmsOrderCheckPalletBoxBarcode();
            checkBoxBarcode.setOrderNo(orderNo);
            checkBoxBarcode.setPalletCode(palletCode);
            checkBoxBarcode.setActiveFlag("1");
            checkBoxBarcode.setDiffFlag("1");
            List<WmsOrderCheckPalletBoxBarcode> checkBarcodeList = wmsOrderCheckPalletBoxBarcodeMapper.queryByAny(checkBoxBarcode);
            if(checkBarcodeList.size()>0){
                //存在差异条码
                checkPallet.setDiffFlag("1");
            }else {
                checkPallet.setDiffFlag("0");
            }
        }else {
            checkPallet.setDiffFlag("1");
        }
        checkPallet.setLastModifiedBy(account);
        checkPallet.setGmtModified(now);
        int upMain = wmsOrderCheckPalletMapper.updateByPrimaryKeySelective(checkPallet);

//        盘点托盘码确认完成，将托盘直接生成入库任务，入库
        Resp recommondResp = wmsLocationStereoscopicService.queryRecommendLocationCode(palletCode);
        if ("0".equals(recommondResp.getCode())) {
            throw new RuntimeException("分拣回库时推荐库位失败！");
        }
        String targetLocation = (String) recommondResp.getData();

        List<WmsPallet> pallets = wmsPalletMapper.selectByCode(palletCode);
        if (pallets == null || pallets.isEmpty()) {
            resp.setCode("0");
            resp.setMessage("查询不到该托盘");
            return resp;
        }
        WmsPallet pallet = pallets.get(0);

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

        resp.setCode("1");
        resp.setMessage("该托盘盘点完成");
        resp.setData(rtnOb);
        return resp;
    }
}
