//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business.impl;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.business.IDifferentBusinessService;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsAddressRealRelaMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsGoodsMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsLocationStereoscopicMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsQXFLocationStereoscopicMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsAddressRealRela;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsMoveTask;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsOutTask;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import com.penghaisoft.wms.expose.FloorNumGenerate;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicDeailMapper;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicMapper;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopicDeail;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsMoveStereoscopicMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOrderCheckPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsTaskExecutionLogMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsMoveStereoscopic;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheck;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheckPallet;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("differentBusinessQXFService")
public class DifferentBusinessQXFServiceImpl extends BaseService implements IDifferentBusinessService {
    private static final Logger log = LoggerFactory.getLogger(DifferentBusinessQXFServiceImpl.class);
    @Resource
    private WmsGoodsMapper wmsGoodsMapper;
    @Resource
    private WmsLocationStereoscopicMapper wmsLocationStereoscopicMapper;
    @Resource
    private WmsQXFLocationStereoscopicMapper wmsQXFLocationStereoscopicMapper;
    @Resource
    private WmsAddressRealRelaMapper wmsAddressRealRelaMapper;
    @Autowired
    private IWmsCommonService wmsCommonService;
    @Resource
    private WmsOrderOutStereoscopicMapper wmsOrderOutStereoscopicMapper;
    @Resource
    private WmsMoveStereoscopicMapper wmsMoveStereoscopicMapper;
    @Resource
    private WmsPalletMapper wmsPalletMapper;
    @Resource
    private WmsOrderCheckPalletMapper wmsOrderCheckPalletMapper;
    @Resource
    private WmsOrderOutStereoscopicDeailMapper wmsOrderOutStereoscopicDeailMapper;
    @Resource
    private WmsTaskExecutionLogMapper wmsTaskExecutionLogMapper;

    public DifferentBusinessQXFServiceImpl() {
    }

    public Resp queryRecommendLocationCodeOld(String goodsCode, String batchNo, String areaCode) {
        Resp resp = new Resp();
        String locationCode = "";
        Boolean bvFlag = false;
        WmsGoods wmsGoods = this.wmsGoodsMapper.queryByCode(goodsCode);
        if (wmsGoods != null && wmsGoods.getGoodsType() != null && "0".equals(wmsGoods.getGoodsType())) {
            bvFlag = true;
        }

        if ("pallet".equals(goodsCode)) {
            bvFlag = true;
        }

        log.info("是否包材或虚拟托盘={}", bvFlag);
        Integer floorNumber = 1;
        WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
        searchOb.setGoodsCode(goodsCode);
        searchOb.setBatchNo(batchNo);
        searchOb.setFloorNumber(floorNumber);
        List<WmsLocationStereoscopic> listRight = this.wmsQXFLocationStereoscopicMapper.getRightRecommendLocation(searchOb);
        if (listRight != null && !listRight.isEmpty()) {
            locationCode = ((WmsLocationStereoscopic)listRight.get(0)).getLocationCode();
        }

        List listRight1;
        if ("".equals(locationCode)) {
            listRight1 = this.wmsQXFLocationStereoscopicMapper.getLeftRecommendLocation(searchOb);
            if (listRight1 != null && !listRight1.isEmpty()) {
                locationCode = ((WmsLocationStereoscopic)listRight1.get(0)).getLocationCode();
            }
        }

        if ("".equals(locationCode)) {
            listRight1 = this.wmsQXFLocationStereoscopicMapper.getEmptyRecommendLocation();
            if (listRight1 != null && !listRight1.isEmpty() && !"4".equals(((WmsLocationStereoscopic)listRight1.get(0)).getShelvesNumber())) {
                int size = listRight1.size();
                locationCode = ((WmsLocationStereoscopic)listRight1.get(size / 2)).getLocationCode();
            }
        }

        searchOb = new WmsLocationStereoscopic();
        searchOb.setFloorNumber(floorNumber);
        if ("".equals(locationCode)) {
            listRight1 = this.wmsQXFLocationStereoscopicMapper.getMixLeftRecommendLocation(searchOb);
            if (listRight1 != null && !listRight1.isEmpty()) {
                locationCode = ((WmsLocationStereoscopic)listRight1.get(0)).getLocationCode();
            }
        }

        if ("".equals(locationCode)) {
            listRight1 = this.wmsQXFLocationStereoscopicMapper.getMixRightRecommendLocation(searchOb);
            if (listRight1 != null && !listRight1.isEmpty()) {
                locationCode = ((WmsLocationStereoscopic)listRight1.get(0)).getLocationCode();
            }
        }

        if ("".equals(locationCode)) {
            resp.setCode(RESULT.FAILED.code);
            resp.setMsg("没有找到合适的库位！");
            return resp;
        } else {
            WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
            updateOb.setLocationCode(locationCode);
            updateOb.setUseStatus("1");
            updateOb.setLastModifiedBy("wcs_query_in_location");
            updateOb.setGmtModified(new Date());
            this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
            resp.setCode(RESULT.SUCCESS.code);
            resp.setData(locationCode);
            return resp;
        }
    }

    public Resp queryRecommendLocationCode(String goodsCode, String batchNo, String areaCode) {
        Resp resp = new Resp();
        String locationCode = "";
        Boolean bvFlag = false;
        WmsGoods wmsGoods = this.wmsGoodsMapper.queryByCode(goodsCode);
        if (wmsGoods != null && wmsGoods.getGoodsType() != null && ("0".equals(wmsGoods.getGoodsType()) || "3".equals(wmsGoods.getGoodsType()))) {
            bvFlag = true;
        }

        log.info("是否包材或虚拟托盘={}", bvFlag);
        long t1 = System.currentTimeMillis();
        List<WmsLocationStereoscopic> allLocationList = this.wmsQXFLocationStereoscopicMapper.getAllLocationInfo();
        long t2 = System.currentTimeMillis();
        log.info("##################推荐库位算法SQL查询耗时：" + (t2 - t1) + "ms！#########################");
        long t3 = System.currentTimeMillis();
        List<WmsLocationStereoscopic> virtualPalletRangeList = new ArrayList();
        List<WmsLocationStereoscopic> normalGoodsRangeList = new ArrayList();
        List<WmsLocationStereoscopic> normalGoodsBeiHuoRangeList = new ArrayList();
        Iterator var18 = allLocationList.iterator();

        while(true) {
            while(var18.hasNext()) {
                WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var18.next();
                if (1 == tmp.getFloorNumber() && 3 == tmp.getShelvesNumber() && 5 >= tmp.getLayerNumber()) {
                    virtualPalletRangeList.add(tmp);
                } else if (tmp.getShelvesNumber() == 4) {
                    normalGoodsBeiHuoRangeList.add(tmp);
                } else {
                    normalGoodsRangeList.add(tmp);
                }
            }

            log.info("######静态变量楼层：5#######");
            if (bvFlag) {
                locationCode = this.getReVirtualPalletLocation(virtualPalletRangeList);
            }

            if ("".equals(locationCode)) {
                List<Integer> list = FloorNumGenerate.getRandomFloorList();
                List<WmsTaskExecutionLog> runTaskList = this.wmsTaskExecutionLogMapper.queryRunInWhTask();
                if (runTaskList != null && !runTaskList.isEmpty()) {
                    List<Integer> listBig2 = new LinkedList();
                    List<Integer> listLess2 = new LinkedList();
                    Iterator var22 = ((List)list).iterator();

                    while(true) {
                        if (!var22.hasNext()) {
                            list = new LinkedList();
                            ((List)list).addAll(listLess2);
                            ((List)list).addAll(listBig2);
                            break;
                        }

                        Integer tmp = (Integer)var22.next();
                        int num = 0;
                        Iterator var25 = runTaskList.iterator();

                        while(var25.hasNext()) {
                            WmsTaskExecutionLog taskTmp = (WmsTaskExecutionLog)var25.next();
                            if (taskTmp.getLocationCode() != null && taskTmp.getLocationCode().startsWith(Integer.toString(tmp))) {
                                ++num;
                                if (num >= 2) {
                                    break;
                                }
                            }
                        }

                        if (num >= 2) {
                            listBig2.add(tmp);
                        } else {
                            listLess2.add(tmp);
                        }
                    }
                }

                Iterator var30 = ((List)list).iterator();

                Integer tmp;
                while(var30.hasNext()) {
                    tmp = (Integer)var30.next();
                    log.info("#################遍历" + tmp + "楼！#########################");
                    locationCode = this.getRecommendLocation(normalGoodsRangeList, goodsCode, batchNo, tmp);
                    if (!"".equals(locationCode)) {
                        log.info("#################推荐库位" + locationCode + "#########################");
                        break;
                    }

                    log.info("#################没有可推荐库位#########################");
                }

                if ("".equals(locationCode)) {
                    var30 = ((List)list).iterator();

                    while(var30.hasNext()) {
                        tmp = (Integer)var30.next();
                        locationCode = this.getRecommendLocation(normalGoodsBeiHuoRangeList, goodsCode, batchNo, tmp);
                        if (!"".equals(locationCode)) {
                            break;
                        }
                    }
                }
            }

            long t4 = System.currentTimeMillis();
            log.info("##################内存库位遍历查找耗时：" + (t4 - t3) + "ms！#########################");
            if ("".equals(locationCode)) {
                resp.setCode(RESULT.FAILED.code);
                resp.setMsg("没有找到合适的库位！");
                return resp;
            }

            long t5 = System.currentTimeMillis();
            WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
            updateOb.setLocationCode(locationCode);
            updateOb.setUseStatus("1");
            updateOb.setLastModifiedBy("wcs_query_in_location");
            updateOb.setGmtModified(new Date());
            this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
            long t6 = System.currentTimeMillis();
            log.info("##################更新库位状态耗时：" + (t6 - t5) + "ms！#########################");
            resp.setCode(RESULT.SUCCESS.code);
            resp.setData(locationCode);
            return resp;
        }
    }

    public Resp querySortingRecommendLocationCode(String goodsCode, String batchNo, String areaCode) {
        Resp resp = new Resp();
        String locationCode = "";
        long t1 = System.currentTimeMillis();
        List<WmsLocationStereoscopic> allLocationList = this.wmsQXFLocationStereoscopicMapper.getAllLocationInfo();
        long t2 = System.currentTimeMillis();
        log.info("##################推荐库位算法SQL查询耗时：" + (t2 - t1) + "ms！#########################");
        long t3 = System.currentTimeMillis();
        List<WmsLocationStereoscopic> virtualPalletRangeList = new ArrayList();
        List<WmsLocationStereoscopic> normalGoodsRangeList = new ArrayList();
        List<WmsLocationStereoscopic> normalGoodsBeiHuoRangeList = new ArrayList();
        Iterator var16 = allLocationList.iterator();

        while(true) {
            while(var16.hasNext()) {
                WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var16.next();
                if (1 == tmp.getFloorNumber() && 3 == tmp.getShelvesNumber() && 5 >= tmp.getLayerNumber()) {
                    virtualPalletRangeList.add(tmp);
                } else if (tmp.getShelvesNumber() == 4) {
                    normalGoodsBeiHuoRangeList.add(tmp);
                } else {
                    normalGoodsRangeList.add(tmp);
                }
            }

            Integer floorNumber = 1;
            locationCode = this.getRecommendLocation(normalGoodsRangeList, goodsCode, batchNo, floorNumber);
            if ("".equals(locationCode)) {
                locationCode = this.getRecommendLocation(normalGoodsBeiHuoRangeList, goodsCode, batchNo, floorNumber);
            }

            long t4 = System.currentTimeMillis();
            log.info("##################内存库位遍历查找耗时：" + (t4 - t3) + "ms！#########################");
            if ("".equals(locationCode)) {
                resp.setCode(RESULT.FAILED.code);
                resp.setMsg("没有找到合适的库位！");
                return resp;
            }

            long t5 = System.currentTimeMillis();
            WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
            updateOb.setLocationCode(locationCode);
            updateOb.setUseStatus("1");
            updateOb.setLastModifiedBy("wcs_query_in_location");
            updateOb.setGmtModified(new Date());
            this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
            long t6 = System.currentTimeMillis();
            log.info("##################更新库位状态耗时：" + (t6 - t5) + "ms！#########################");
            resp.setCode(RESULT.SUCCESS.code);
            resp.setData(locationCode);
            return resp;
        }
    }

    public String getReVirtualPalletLocation(List<WmsLocationStereoscopic> locationList) {
        String recommendLocation = "";
        List<List<WmsLocationStereoscopic>> allList = new ArrayList();
        List<List<WmsLocationStereoscopic>> notContainLockList = new ArrayList();
        List<List<WmsLocationStereoscopic>> emptyList = new ArrayList();
        List<List<WmsLocationStereoscopic>> containLockList = new ArrayList();
        Integer tmpFloorNum = 0;
        Integer tmpShelvelsNum = 0;
        Integer tmpLayerNum = 0;
        List<WmsLocationStereoscopic> tranList = new ArrayList();
        Iterator var11 = locationList.iterator();

        while(var11.hasNext()) {
            WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var11.next();
            if (tmpFloorNum != tmp.getFloorNumber() || tmpShelvelsNum != tmp.getShelvesNumber() || tmpLayerNum != tmp.getLayerNumber()) {
                tranList = new ArrayList();
                tmpFloorNum = tmp.getFloorNumber();
                tmpShelvelsNum = tmp.getShelvesNumber();
                tmpLayerNum = tmp.getLayerNumber();
            }

            tranList.add(tmp);
            if (!allList.contains(tranList)) {
                allList.add(tranList);
            }
        }

        var11 = allList.iterator();

        List tmpList;
        while(var11.hasNext()) {
            tmpList = (List)var11.next();
            Boolean containLockFlag = false;
            Boolean flag_3 = false;
            Iterator var15 = tmpList.iterator();

            while(true) {
                while(var15.hasNext()) {
                    WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var15.next();
                    if (tmp.getUseStatus() != null && ("1".equals(tmp.getUseStatus()) || "2".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus()))) {
                        containLockFlag = true;
                    } else if (tmp.getUseStatus() != null && "3".equals(tmp.getUseStatus())) {
                        flag_3 = true;
                    }
                }

                if (containLockFlag) {
                    containLockList.add(tmpList);
                } else if (flag_3) {
                    notContainLockList.add(tmpList);
                } else {
                    emptyList.add(tmpList);
                }
                break;
            }
        }

        var11 = notContainLockList.iterator();

        int i;
        WmsLocationStereoscopic tmp;
        WmsLocationStereoscopic after;
        while(var11.hasNext()) {
            tmpList = (List)var11.next();

            for(i = 0; i < tmpList.size() - 1; ++i) {
                tmp = (WmsLocationStereoscopic)tmpList.get(i);
                if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                    break;
                }

                after = (WmsLocationStereoscopic)tmpList.get(i + 1);
                if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                    recommendLocation = tmp.getLocationCode();
                    break;
                }
            }

            if (!"".equals(recommendLocation)) {
                break;
            }

            Collections.reverse(tmpList);

            for(i = 0; i < tmpList.size() - 1; ++i) {
                tmp = (WmsLocationStereoscopic)tmpList.get(i);
                if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                    break;
                }

                after = (WmsLocationStereoscopic)tmpList.get(i + 1);
                if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                    recommendLocation = tmp.getLocationCode();
                    break;
                }
            }

            if (!"".equals(recommendLocation)) {
                break;
            }
        }

        if ("".equals(recommendLocation) && !emptyList.isEmpty()) {
            recommendLocation = ((WmsLocationStereoscopic)((List)emptyList.get(0)).get(((List)emptyList.get(0)).size() / 2)).getLocationCode();
        }

        if ("".equals(recommendLocation)) {
            var11 = containLockList.iterator();

            while(var11.hasNext()) {
                tmpList = (List)var11.next();

                for(i = 0; i < tmpList.size() - 1; ++i) {
                    tmp = (WmsLocationStereoscopic)tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }

                    after = (WmsLocationStereoscopic)tmpList.get(i + 1);
                    if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                        recommendLocation = tmp.getLocationCode();
                        break;
                    }
                }

                if (!"".equals(recommendLocation)) {
                    break;
                }

                Collections.reverse(tmpList);

                for(i = 0; i < tmpList.size() - 1; ++i) {
                    tmp = (WmsLocationStereoscopic)tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }

                    after = (WmsLocationStereoscopic)tmpList.get(i + 1);
                    if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                        recommendLocation = tmp.getLocationCode();
                        break;
                    }
                }

                if (!"".equals(recommendLocation)) {
                    break;
                }
            }
        }

        return recommendLocation;
    }

    public String getRecommendLocation(List<WmsLocationStereoscopic> locationList, String goodsCode, String batchNo, Integer floorNum) {
        String recommendLocation = "";
        List<List<WmsLocationStereoscopic>> allList = new ArrayList();
        List<List<WmsLocationStereoscopic>> notContainLockList = new ArrayList();
        List<List<WmsLocationStereoscopic>> emptyList = new ArrayList();
        List<List<WmsLocationStereoscopic>> containLockList = new ArrayList();
        List<WmsLocationStereoscopic> floorLocationList = new ArrayList();
        Iterator var11 = locationList.iterator();

        while(var11.hasNext()) {
            WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var11.next();
            if (floorNum == tmp.getFloorNumber()) {
                floorLocationList.add(tmp);
            }
        }

        Integer tmpShelvelsNum = 0;
        Integer tmpLayerNum = 0;
        List<WmsLocationStereoscopic> tranList = new ArrayList();
        Iterator var14 = floorLocationList.iterator();

        while(var14.hasNext()) {
            WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var14.next();
            if (tmpShelvelsNum != tmp.getShelvesNumber() || tmpLayerNum != tmp.getLayerNumber()) {
                tranList = new ArrayList();
                tmpShelvelsNum = tmp.getShelvesNumber();
                tmpLayerNum = tmp.getLayerNumber();
            }

            tranList.add(tmp);
            if (!allList.contains(tranList)) {
                allList.add(tranList);
            }
        }

        var14 = allList.iterator();

        while(var14.hasNext()) {
            List<WmsLocationStereoscopic> tmpList = (List)var14.next();
            Boolean containLockFlag = false;
            Boolean flag_3 = false;
            HashSet<String> goodsCodeSet = new HashSet();
            Iterator var19 = tmpList.iterator();

            while(var19.hasNext()) {
                WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var19.next();
                if (tmp.getUseStatus() != null && ("1".equals(tmp.getUseStatus()) || "2".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus()))) {
                    containLockFlag = true;
                } else if (tmp.getUseStatus() != null && "3".equals(tmp.getUseStatus())) {
                    flag_3 = true;
                }

                if (tmp.getGoodsCode() != null && !"".equals(tmp.getGoodsCode())) {
                    goodsCodeSet.add(tmp.getGoodsCode());
                }
            }

            if (goodsCodeSet.size() <= 2) {
                if (containLockFlag) {
                    containLockList.add(tmpList);
                } else if (flag_3) {
                    notContainLockList.add(tmpList);
                } else {
                    emptyList.add(tmpList);
                }
            }
        }

        recommendLocation = this.getLocation(notContainLockList, goodsCode, batchNo);
        if ("".equals(recommendLocation) && !emptyList.isEmpty()) {
            Integer shelvelsNum = ((WmsLocationStereoscopic)((List)emptyList.get(0)).get(0)).getShelvesNumber();
            if (shelvelsNum == 4) {
                recommendLocation = ((WmsLocationStereoscopic)((List)emptyList.get(0)).get(((List)emptyList.get(0)).size() - 1)).getLocationCode();
            } else if (shelvelsNum == 20) {
                recommendLocation = ((WmsLocationStereoscopic)((List)emptyList.get(0)).get(((List)emptyList.get(0)).size() - 1)).getLocationCode();
            } else if (shelvelsNum == 21) {
                recommendLocation = ((WmsLocationStereoscopic)((List)emptyList.get(0)).get(0)).getLocationCode();
            } else if (shelvelsNum == 1) {
                recommendLocation = ((WmsLocationStereoscopic)((List)emptyList.get(0)).get(0)).getLocationCode();
            } else {
                recommendLocation = ((WmsLocationStereoscopic)((List)emptyList.get(0)).get(((List)emptyList.get(0)).size() / 2)).getLocationCode();
            }
        }

        if ("".equals(recommendLocation)) {
            recommendLocation = this.getLocation(containLockList, goodsCode, batchNo);
        }

        return recommendLocation;
    }

    public String getLocation(List<List<WmsLocationStereoscopic>> list, String goodsCode, String batchNo) {
        String recommendLocation = "";
        Iterator var5 = list.iterator();

        List tmpList;
        Integer shelvelsNum;
        WmsLocationStereoscopic tmp;
        while(var5.hasNext()) {
            tmpList = (List)var5.next();
            shelvelsNum = ((WmsLocationStereoscopic)tmpList.get(0)).getShelvesNumber();
            int i;
//            WmsLocationStereoscopic tmp;
            if (shelvelsNum != 1 && shelvelsNum != 21) {
                if (shelvelsNum != 4 && shelvelsNum != 20) {
                    for(i = 0; i < tmpList.size() - 1; ++i) {
                        tmp = (WmsLocationStereoscopic)tmpList.get(i);
                        if (tmp.getLayerNumber() <= 3 && shelvelsNum == 2 || tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                            break;
                        }

                        tmp = (WmsLocationStereoscopic)tmpList.get(i + 1);
                        if (tmp.getUseStatus() != null && "3".equals(tmp.getUseStatus())) {
                            if ("pallet".equals(goodsCode)) {
                                if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode())) {
                                    recommendLocation = tmp.getLocationCode();
                                    break;
                                }
                            } else if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode()) && tmp.getBatchNo() != null && batchNo.equals(tmp.getBatchNo())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        }
                    }

                    if (!"".equals(recommendLocation)) {
                        break;
                    }

                    Collections.reverse(tmpList);

                    for(i = 0; i < tmpList.size() - 1; ++i) {
                        tmp = (WmsLocationStereoscopic)tmpList.get(i);
                        if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                            break;
                        }

                        tmp = (WmsLocationStereoscopic)tmpList.get(i + 1);
                        if (tmp.getUseStatus() != null && "3".equals(tmp.getUseStatus())) {
                            if ("pallet".equals(goodsCode)) {
                                if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode())) {
                                    recommendLocation = tmp.getLocationCode();
                                    break;
                                }
                            } else if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode()) && tmp.getBatchNo() != null && batchNo.equals(tmp.getBatchNo())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        }
                    }

                    if (!"".equals(recommendLocation)) {
                        break;
                    }
                } else {
                    for(i = 0; i < tmpList.size() - 1; ++i) {
                        tmp = (WmsLocationStereoscopic)tmpList.get(i);
                        if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                            break;
                        }

                        tmp = (WmsLocationStereoscopic)tmpList.get(i + 1);
                        if (tmp.getUseStatus() != null && "3".equals(tmp.getUseStatus())) {
                            if ("pallet".equals(goodsCode)) {
                                if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode())) {
                                    recommendLocation = tmp.getLocationCode();
                                    break;
                                }
                            } else if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode()) && tmp.getBatchNo() != null && batchNo.equals(tmp.getBatchNo())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        }
                    }

                    if (!"".equals(recommendLocation)) {
                        break;
                    }
                }
            } else {
                Collections.reverse(tmpList);

                for(i = 0; i < tmpList.size() - 1; ++i) {
                    tmp = (WmsLocationStereoscopic)tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }

                    tmp = (WmsLocationStereoscopic)tmpList.get(i + 1);
                    if (tmp.getUseStatus() != null && "3".equals(tmp.getUseStatus())) {
                        if ("pallet".equals(goodsCode)) {
                            if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        } else if (tmp.getGoodsCode() != null && goodsCode.equals(tmp.getGoodsCode()) && tmp.getBatchNo() != null && batchNo.equals(tmp.getBatchNo())) {
                            recommendLocation = tmp.getLocationCode();
                            break;
                        }
                    }
                }

                if (!"".equals(recommendLocation)) {
                    break;
                }
            }
        }

        if ("".equals(recommendLocation)) {
            var5 = list.iterator();

            do {
                HashSet goodsCodeSet;
                do {
                    do {
                        if (!var5.hasNext()) {
                            return recommendLocation;
                        }

                        tmpList = (List)var5.next();
                        shelvelsNum = ((WmsLocationStereoscopic)tmpList.get(0)).getShelvesNumber();
                    } while(shelvelsNum != 3);

                    goodsCodeSet = new HashSet();
                    Iterator var12 = tmpList.iterator();

                    while(var12.hasNext()) {
                        tmp = (WmsLocationStereoscopic)var12.next();
                        if (tmp.getLayerNumber() <= 3 && shelvelsNum == 2) {
                            break;
                        }

                        if (tmp.getGoodsCode() != null && !"".equals(tmp.getGoodsCode())) {
                            goodsCodeSet.add(tmp.getGoodsCode());
                        }
                    }
                } while(goodsCodeSet.size() != 1);

                WmsLocationStereoscopic after;
                int i;
                for(i = 0; i < tmpList.size() - 1; ++i) {
                    tmp = (WmsLocationStereoscopic)tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }

                    after = (WmsLocationStereoscopic)tmpList.get(i + 1);
                    if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                        recommendLocation = tmp.getLocationCode();
                        break;
                    }
                }

                if (!"".equals(recommendLocation)) {
                    break;
                }

                Collections.reverse(tmpList);

                for(i = 0; i < tmpList.size() - 1; ++i) {
                    tmp = (WmsLocationStereoscopic)tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }

                    after = (WmsLocationStereoscopic)tmpList.get(i + 1);
                    if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                        recommendLocation = tmp.getLocationCode();
                        break;
                    }
                }
            } while("".equals(recommendLocation));
        }

        return recommendLocation;
    }

    public Resp revertLocationStatus0(String locationCode) {
        WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
        updateOb.setLocationCode(locationCode);
        updateOb.setUseStatus("0");
        updateOb.setLastModifiedBy("wsc_query_in_location_revert");
        updateOb.setGmtModified(new Date());
        this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
        return this.success();
    }

    public Resp queryOutRecommendLocationCode(String goodsCode, String batchNo, Integer planAmount, String targetAddress, String loginName) {
        Resp resp = new Resp();
        resp.setCode(RESULT.SUCCESS.code);
        TaskType taskType = TaskType.STRAIGHT_OUT;
        Integer findAmount = 0;
        List<WmsOutTask> wmsOutTaskList = new LinkedList();
        WmsLocationStereoscopic returnData = new WmsLocationStereoscopic();
        returnData.setWmsOutTaskList(wmsOutTaskList);
        resp.setData(returnData);
        WmsLocationStereoscopic seachOb = new WmsLocationStereoscopic();
        seachOb.setGoodsCode(goodsCode);
        seachOb.setBatchNo(batchNo);
        Long useableNum;
        if ("2002".equals(targetAddress)) {
            seachOb.setFloorNumber(1);
            useableNum = this.wmsQXFLocationStereoscopicMapper.queryUseableGoodsAmount(seachOb);
            if (useableNum < (long)planAmount) {
                resp.setCode(RESULT.FAILED.code);
                resp.setMsg("一楼商品(" + goodsCode + "-" + batchNo + ")库存数量不足！");
                return resp;
            }

            List<Integer> floorNumList = new ArrayList();
            floorNumList.add(1);
            Map<Integer, LinkedList<WmsLocationStereoscopic>> mapList = this.getOutMapList(goodsCode, batchNo, floorNumList);
            LinkedList<WmsLocationStereoscopic> out1List = (LinkedList)mapList.get(1);
            if (out1List != null && !out1List.isEmpty()) {
                Iterator var16 = out1List.iterator();

                while(var16.hasNext()) {
                    WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var16.next();
                    if (tmp.getAmount() != null && tmp.getAmount() > 0) {
                        findAmount = findAmount + tmp.getAmount();
                        WmsOutTask wmsOutTask = new WmsOutTask();
                        wmsOutTask.setTargetAddress("2002");
                        wmsOutTask.setOperator(loginName);
                        wmsOutTask.setTaskType(String.valueOf(taskType.getTaskType()));
                        wmsOutTask.setFromLocation(tmp.getLocationCode());
                        wmsOutTask.setPalletCode(tmp.getPalletCode());
                        wmsOutTask.setGoodsCode(goodsCode);
                        wmsOutTask.setBatchNo(batchNo);
                        wmsOutTask.setAmount(tmp.getAmount());
                        wmsOutTaskList.add(wmsOutTask);
                        if (findAmount >= planAmount) {
                            break;
                        }
                    }
                }
            }
        } else {
            useableNum = this.wmsQXFLocationStereoscopicMapper.queryUseableGoodsAmount(seachOb);
            if (useableNum < (long)planAmount) {
                resp.setCode(RESULT.FAILED.code);
                resp.setMsg("一至四楼商品(" + goodsCode + "-" + batchNo + ")库存数量不足！");
                return resp;
            }

            long t1 = System.currentTimeMillis();
            List<Integer> floorNumList = new ArrayList();
            floorNumList.add(1);
            floorNumList.add(2);
            floorNumList.add(3);
            floorNumList.add(4);
            Map<Integer, LinkedList<WmsLocationStereoscopic>> mapList = this.getOutMapList(goodsCode, batchNo, floorNumList);
            LinkedList<WmsLocationStereoscopic> out1List = (LinkedList)mapList.get(1);
            LinkedList<WmsLocationStereoscopic> out2List = (LinkedList)mapList.get(2);
            LinkedList<WmsLocationStereoscopic> out3List = (LinkedList)mapList.get(3);
            LinkedList<WmsLocationStereoscopic> out4List = (LinkedList)mapList.get(4);
            long t2 = System.currentTimeMillis();
            log.info("##################JAVA算法NEWWWWWWWWWWWWWWWWWWWWWWWW耗时" + (t2 - t1) + "ms！#########################");

            while(out1List != null && !out1List.isEmpty() || out2List != null && !out2List.isEmpty() || out3List != null && !out3List.isEmpty() || out4List != null && !out4List.isEmpty()) {
                int i;
                WmsLocationStereoscopic tmp;
                WmsOutTask wmsOutTask;
                if (out1List != null && !out1List.isEmpty()) {
                    if ((double)out1List.size() >= 2.0D) {
                        for(i = 0; (double)i < 2.0D; ++i) {
                            tmp = (WmsLocationStereoscopic)out1List.pop();
                            if (tmp.getAmount() != null && tmp.getAmount() > 0) {
                                findAmount = findAmount + tmp.getAmount();
                                wmsOutTask = new WmsOutTask();
                                wmsOutTask.setTargetAddress("2002");
                                wmsOutTask.setOperator(loginName);
                                wmsOutTask.setTaskType(String.valueOf(taskType.getTaskType()));
                                wmsOutTask.setFromLocation(tmp.getLocationCode());
                                wmsOutTask.setPalletCode(tmp.getPalletCode());
                                wmsOutTask.setGoodsCode(goodsCode);
                                wmsOutTask.setBatchNo(batchNo);
                                wmsOutTask.setAmount(tmp.getAmount());
                                wmsOutTaskList.add(wmsOutTask);
                                if (findAmount >= planAmount) {
                                    break;
                                }
                            }
                        }
                    } else {
                        for(i = 0; i < out1List.size(); ++i) {
                            tmp = (WmsLocationStereoscopic)out1List.pop();
                            if (tmp.getAmount() != null && tmp.getAmount() > 0) {
                                findAmount = findAmount + tmp.getAmount();
                                wmsOutTask = new WmsOutTask();
                                wmsOutTask.setTargetAddress("2002");
                                wmsOutTask.setOperator(loginName);
                                wmsOutTask.setTaskType(String.valueOf(taskType.getTaskType()));
                                wmsOutTask.setFromLocation(tmp.getLocationCode());
                                wmsOutTask.setPalletCode(tmp.getPalletCode());
                                wmsOutTask.setGoodsCode(goodsCode);
                                wmsOutTask.setBatchNo(batchNo);
                                wmsOutTask.setAmount(tmp.getAmount());
                                wmsOutTaskList.add(wmsOutTask);
                                if (findAmount >= planAmount) {
                                    break;
                                }
                            }
                        }
                    }
                }

                if (findAmount >= planAmount) {
                    break;
                }

                if (out2List != null && !out2List.isEmpty()) {
                    if ((double)out2List.size() >= 2.0D) {
                        for(i = 0; (double)i < 2.0D; ++i) {
                            tmp = (WmsLocationStereoscopic)out2List.pop();
                            if (tmp.getAmount() != null && tmp.getAmount() > 0) {
                                findAmount = findAmount + tmp.getAmount();
                                wmsOutTask = new WmsOutTask();
                                wmsOutTask.setTargetAddress(targetAddress);
                                wmsOutTask.setOperator(loginName);
                                wmsOutTask.setTaskType(String.valueOf(taskType.getTaskType()));
                                wmsOutTask.setFromLocation(tmp.getLocationCode());
                                wmsOutTask.setPalletCode(tmp.getPalletCode());
                                wmsOutTask.setGoodsCode(goodsCode);
                                wmsOutTask.setBatchNo(batchNo);
                                wmsOutTask.setAmount(tmp.getAmount());
                                wmsOutTaskList.add(wmsOutTask);
                                if (findAmount >= planAmount) {
                                    break;
                                }
                            }
                        }
                    } else {
                        for(i = 0; i < out2List.size(); ++i) {
                            tmp = (WmsLocationStereoscopic)out2List.pop();
                            if (tmp.getAmount() != null && tmp.getAmount() > 0) {
                                findAmount = findAmount + tmp.getAmount();
                                wmsOutTask = new WmsOutTask();
                                wmsOutTask.setTargetAddress(targetAddress);
                                wmsOutTask.setOperator(loginName);
                                wmsOutTask.setTaskType(String.valueOf(taskType.getTaskType()));
                                wmsOutTask.setFromLocation(tmp.getLocationCode());
                                wmsOutTask.setPalletCode(tmp.getPalletCode());
                                wmsOutTask.setGoodsCode(goodsCode);
                                wmsOutTask.setBatchNo(batchNo);
                                wmsOutTask.setAmount(tmp.getAmount());
                                wmsOutTaskList.add(wmsOutTask);
                                if (findAmount >= planAmount) {
                                    break;
                                }
                            }
                        }
                    }
                }

                if (findAmount >= planAmount) {
                    break;
                }

                if (out3List != null && !out3List.isEmpty()) {
                    if ((double)out3List.size() >= 1.0D) {
                        for(i = 0; (double)i < 1.0D; ++i) {
                            tmp = (WmsLocationStereoscopic)out3List.pop();
                            if (tmp.getAmount() != null && tmp.getAmount() > 0) {
                                findAmount = findAmount + tmp.getAmount();
                                wmsOutTask = new WmsOutTask();
                                wmsOutTask.setTargetAddress(targetAddress);
                                wmsOutTask.setOperator(loginName);
                                wmsOutTask.setTaskType(String.valueOf(taskType.getTaskType()));
                                wmsOutTask.setFromLocation(tmp.getLocationCode());
                                wmsOutTask.setPalletCode(tmp.getPalletCode());
                                wmsOutTask.setGoodsCode(goodsCode);
                                wmsOutTask.setBatchNo(batchNo);
                                wmsOutTask.setAmount(tmp.getAmount());
                                wmsOutTaskList.add(wmsOutTask);
                                if (findAmount >= planAmount) {
                                    break;
                                }
                            }
                        }
                    } else {
                        for(i = 0; i < out3List.size(); ++i) {
                            tmp = (WmsLocationStereoscopic)out3List.pop();
                            if (tmp.getAmount() != null && tmp.getAmount() > 0) {
                                findAmount = findAmount + tmp.getAmount();
                                wmsOutTask = new WmsOutTask();
                                wmsOutTask.setTargetAddress(targetAddress);
                                wmsOutTask.setOperator(loginName);
                                wmsOutTask.setTaskType(String.valueOf(taskType.getTaskType()));
                                wmsOutTask.setFromLocation(tmp.getLocationCode());
                                wmsOutTask.setPalletCode(tmp.getPalletCode());
                                wmsOutTask.setGoodsCode(goodsCode);
                                wmsOutTask.setBatchNo(batchNo);
                                wmsOutTask.setAmount(tmp.getAmount());
                                wmsOutTaskList.add(wmsOutTask);
                                if (findAmount >= planAmount) {
                                    break;
                                }
                            }
                        }
                    }
                }

                if (findAmount >= planAmount) {
                    break;
                }

                if (out4List != null && !out4List.isEmpty()) {
                    if ((double)out4List.size() >= 1.0D) {
                        for(i = 0; (double)i < 1.0D; ++i) {
                            tmp = (WmsLocationStereoscopic)out4List.pop();
                            if (tmp.getAmount() != null && tmp.getAmount() > 0) {
                                findAmount = findAmount + tmp.getAmount();
                                wmsOutTask = new WmsOutTask();
                                wmsOutTask.setTargetAddress(targetAddress);
                                wmsOutTask.setOperator(loginName);
                                wmsOutTask.setTaskType(String.valueOf(taskType.getTaskType()));
                                wmsOutTask.setFromLocation(tmp.getLocationCode());
                                wmsOutTask.setPalletCode(tmp.getPalletCode());
                                wmsOutTask.setGoodsCode(goodsCode);
                                wmsOutTask.setBatchNo(batchNo);
                                wmsOutTask.setAmount(tmp.getAmount());
                                wmsOutTaskList.add(wmsOutTask);
                                if (findAmount >= planAmount) {
                                    break;
                                }
                            }
                        }
                    } else {
                        for(i = 0; i < out4List.size(); ++i) {
                            tmp = (WmsLocationStereoscopic)out4List.pop();
                            if (tmp.getAmount() != null && tmp.getAmount() > 0) {
                                findAmount = findAmount + tmp.getAmount();
                                wmsOutTask = new WmsOutTask();
                                wmsOutTask.setTargetAddress(targetAddress);
                                wmsOutTask.setOperator(loginName);
                                wmsOutTask.setTaskType(String.valueOf(taskType.getTaskType()));
                                wmsOutTask.setFromLocation(tmp.getLocationCode());
                                wmsOutTask.setPalletCode(tmp.getPalletCode());
                                wmsOutTask.setGoodsCode(goodsCode);
                                wmsOutTask.setBatchNo(batchNo);
                                wmsOutTask.setAmount(tmp.getAmount());
                                wmsOutTaskList.add(wmsOutTask);
                                if (findAmount >= planAmount) {
                                    break;
                                }
                            }
                        }
                    }
                }

                if (findAmount >= planAmount) {
                    break;
                }
            }
        }

        if (findAmount < planAmount) {
            resp.setCode(RESULT.FAILED.code);
            resp.setMsg("当前时间点，立库中商品(" + goodsCode + "-" + batchNo + ")可出库的数量不足！请稍后再试！");
            return resp;
        } else {
            List<String> outlockList = new ArrayList();

            WmsOutTask tmp;
            for(Iterator iter = wmsOutTaskList.iterator(); iter.hasNext(); tmp.setTaskId(this.wmsCommonService.getTaskIds(taskType, 1)[0])) {
                tmp = (WmsOutTask)iter.next();
                if (tmp.getFromLocation() != null) {
                    outlockList.add(tmp.getFromLocation());
                }
            }

            WmsLocationStereoscopic updateLocation = new WmsLocationStereoscopic();
            updateLocation.setLastModifiedBy(loginName);
            updateLocation.setGmtModified(new Date());
            if (outlockList != null && !outlockList.isEmpty()) {
                updateLocation.setLocationCodeList(outlockList);
                updateLocation.setUseStatus("2");
                this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateLocation);
            }

            return resp;
        }
    }

    @Transactional
    public Resp queryVirtualPalletOut(String goodsCode, String batchNo, Integer planAmount, String targetAddress, String loginName) {
        Resp resp = new Resp();
        resp.setCode(RESULT.SUCCESS.code);
        TaskType taskType = TaskType.VIRTUAL_PALLET_OUT;
        Integer findAmount = 0;
        List<WmsOutTask> wmsOutTaskList = new LinkedList();
        WmsLocationStereoscopic returnData = new WmsLocationStereoscopic();
        returnData.setWmsOutTaskList(wmsOutTaskList);
        resp.setData(returnData);
        List<Integer> floorNumList = new ArrayList();
        floorNumList.add(1);
        floorNumList.add(2);
        floorNumList.add(3);
        floorNumList.add(4);
        WmsLocationStereoscopic seachOb = new WmsLocationStereoscopic();
        seachOb.setGoodsCode(goodsCode);
        seachOb.setBatchNo(batchNo);
        seachOb.setFloorNumberList(floorNumList);
        Long useableNum = this.wmsQXFLocationStereoscopicMapper.queryUseablePalletAmount(seachOb);
        if (useableNum < (long)planAmount) {
            resp.setCode(RESULT.FAILED.code);
            resp.setMsg("虚拟托盘(" + goodsCode + ")库存数量不足！");
            return resp;
        } else {
            Map<Integer, LinkedList<WmsLocationStereoscopic>> mapList = this.getVirtualPalletOutMapList(goodsCode, batchNo, floorNumList);
            LinkedList<WmsLocationStereoscopic> out1List = (LinkedList)mapList.get(1);
            LinkedList<WmsLocationStereoscopic> out2List = (LinkedList)mapList.get(2);
            LinkedList<WmsLocationStereoscopic> out3List = (LinkedList)mapList.get(3);
            LinkedList out4List = (LinkedList)mapList.get(4);

            while(out1List != null && !out1List.isEmpty() || out2List != null && !out2List.isEmpty() || out3List != null && !out3List.isEmpty() || out4List != null && !out4List.isEmpty()) {
                Iterator var19;
                WmsLocationStereoscopic tmp;
                WmsOutTask wmsOutTask;
                if (out4List != null && !out4List.isEmpty()) {
                    var19 = out4List.iterator();

                    while(var19.hasNext()) {
                        tmp = (WmsLocationStereoscopic)var19.next();
                        if (tmp.getAmount() != null && tmp.getAmount() > 0) {
                            findAmount = findAmount + 1;
                            wmsOutTask = new WmsOutTask();
                            wmsOutTask.setTargetAddress(targetAddress);
                            wmsOutTask.setOperator(loginName);
                            wmsOutTask.setTaskType(String.valueOf(taskType.getTaskType()));
                            wmsOutTask.setFromLocation(tmp.getLocationCode());
                            wmsOutTask.setPalletCode(tmp.getPalletCode());
                            wmsOutTask.setGoodsCode(goodsCode);
                            wmsOutTask.setBatchNo(batchNo);
                            wmsOutTaskList.add(wmsOutTask);
                            if (findAmount >= planAmount) {
                                break;
                            }
                        }
                    }
                }

                if (findAmount >= planAmount) {
                    break;
                }

                if (out3List != null && !out3List.isEmpty()) {
                    var19 = out3List.iterator();

                    while(var19.hasNext()) {
                        tmp = (WmsLocationStereoscopic)var19.next();
                        if (tmp.getAmount() != null && tmp.getAmount() > 0) {
                            findAmount = findAmount + 1;
                            wmsOutTask = new WmsOutTask();
                            wmsOutTask.setTargetAddress(targetAddress);
                            wmsOutTask.setOperator(loginName);
                            wmsOutTask.setTaskType(String.valueOf(taskType.getTaskType()));
                            wmsOutTask.setFromLocation(tmp.getLocationCode());
                            wmsOutTask.setPalletCode(tmp.getPalletCode());
                            wmsOutTask.setGoodsCode(goodsCode);
                            wmsOutTask.setBatchNo(batchNo);
                            wmsOutTaskList.add(wmsOutTask);
                            if (findAmount >= planAmount) {
                                break;
                            }
                        }
                    }
                }

                if (findAmount >= planAmount) {
                    break;
                }

                if (out2List != null && !out2List.isEmpty()) {
                    var19 = out2List.iterator();

                    while(var19.hasNext()) {
                        tmp = (WmsLocationStereoscopic)var19.next();
                        if (tmp.getAmount() != null && tmp.getAmount() > 0) {
                            findAmount = findAmount + 1;
                            wmsOutTask = new WmsOutTask();
                            wmsOutTask.setTargetAddress(targetAddress);
                            wmsOutTask.setOperator(loginName);
                            wmsOutTask.setTaskType(String.valueOf(taskType.getTaskType()));
                            wmsOutTask.setFromLocation(tmp.getLocationCode());
                            wmsOutTask.setPalletCode(tmp.getPalletCode());
                            wmsOutTask.setGoodsCode(goodsCode);
                            wmsOutTask.setBatchNo(batchNo);
                            wmsOutTaskList.add(wmsOutTask);
                            if (findAmount >= planAmount) {
                                break;
                            }
                        }
                    }
                }

                if (findAmount >= planAmount) {
                    break;
                }

                if (out1List != null && !out1List.isEmpty()) {
                    var19 = out1List.iterator();

                    while(var19.hasNext()) {
                        tmp = (WmsLocationStereoscopic)var19.next();
                        if (tmp.getAmount() != null && tmp.getAmount() > 0) {
                            findAmount = findAmount + 1;
                            wmsOutTask = new WmsOutTask();
                            wmsOutTask.setTargetAddress(targetAddress);
                            wmsOutTask.setOperator(loginName);
                            wmsOutTask.setTaskType(String.valueOf(taskType.getTaskType()));
                            wmsOutTask.setFromLocation(tmp.getLocationCode());
                            wmsOutTask.setPalletCode(tmp.getPalletCode());
                            wmsOutTask.setGoodsCode(goodsCode);
                            wmsOutTask.setBatchNo(batchNo);
                            wmsOutTaskList.add(wmsOutTask);
                            if (findAmount >= planAmount) {
                                break;
                            }
                        }
                    }
                }

                if (findAmount >= planAmount) {
                    break;
                }
            }

            if (findAmount < planAmount) {
                resp.setCode(RESULT.FAILED.code);
                resp.setMsg("当前时间点，立库中虚拟托盘(" + goodsCode + ")可出库的数量不足！请稍后再试！");
                return resp;
            } else {
                List<String> outlockList = new ArrayList();
                List<WmsPallet> wmsPalletList = new ArrayList();
                List<WmsTaskExecutionLog> wmsTaskExecutionLogList = new ArrayList();
                Date now = new Date();
                String orderNo = null;
                if (wmsOutTaskList != null && !wmsOutTaskList.isEmpty()) {
                    Iterator iter = wmsOutTaskList.iterator();

                    while(iter.hasNext()) {
                        WmsOutTask tmp = (WmsOutTask)iter.next();
                        if (tmp.getFromLocation() != null) {
                            outlockList.add(tmp.getFromLocation());
                        }

                        tmp.setTaskId(this.wmsCommonService.getTaskIds(taskType, 1)[0]);
                        WmsPallet wmsPallet = new WmsPallet();
                        wmsPallet.setPalletCode(tmp.getPalletCode());
                        wmsPallet.setLockBy(String.valueOf(tmp.getTaskId()));
                        wmsPallet.setUserDefined1((String)orderNo);
                        if (tmp.getChannelLocation() != null && !"".equals(tmp.getChannelLocation())) {
                            wmsPallet.setChannelLocation(tmp.getChannelLocation());
                        }

                        wmsPallet.setLastModifiedBy(loginName);
                        wmsPallet.setGmtModified(now);
                        wmsPalletList.add(wmsPallet);
                        WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
                        wmsTaskExecutionLog.setTaskId(tmp.getTaskId());
                        wmsTaskExecutionLog.setOrderNo((String)orderNo);
                        wmsTaskExecutionLog.setTaskType(tmp.getTaskType());
                        wmsTaskExecutionLog.setTaskStatus("1");
                        wmsTaskExecutionLog.setOutAddress(tmp.getTargetAddress());
                        wmsTaskExecutionLog.setPalletCode(tmp.getPalletCode());
                        wmsTaskExecutionLog.setGoodsCode(tmp.getGoodsCode());
                        wmsTaskExecutionLog.setBatchNo(tmp.getBatchNo());
                        wmsTaskExecutionLog.setLocationCode(tmp.getFromLocation());
                        wmsTaskExecutionLog.setCreateBy(loginName);
                        wmsTaskExecutionLog.setGmtCreate(now);
                        wmsTaskExecutionLog.setActiveFlag("1");
                        wmsTaskExecutionLogList.add(wmsTaskExecutionLog);
                    }
                }

                WmsLocationStereoscopic updateLocation = new WmsLocationStereoscopic();
                updateLocation.setLastModifiedBy(loginName);
                updateLocation.setGmtModified(new Date());
                if (outlockList != null && !outlockList.isEmpty()) {
                    updateLocation.setLocationCodeList(outlockList);
                    updateLocation.setUseStatus("2");
                    this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateLocation);
                }

                this.wmsPalletMapper.updateByBatch(wmsPalletList);
                this.wmsTaskExecutionLogMapper.batchInsert(wmsTaskExecutionLogList);
                return resp;
            }
        }
    }

    public LinkedList<WmsLocationStereoscopic> getOutList(String goodsCode, String batchNo, Integer floorNum) {
        LinkedList<WmsLocationStereoscopic> outList = new LinkedList();
        WmsLocationStereoscopic seachOb = new WmsLocationStereoscopic();
        seachOb.setGoodsCode(goodsCode);
        seachOb.setBatchNo(batchNo);
        LinkedList<LinkedList<WmsLocationStereoscopic>> floor1ListAll = new LinkedList();
        LinkedList<LinkedList<WmsLocationStereoscopic>> floor1ListBH = new LinkedList();
        LinkedList<LinkedList<WmsLocationStereoscopic>> floor1ListRight = new LinkedList();
        List<String> right = new ArrayList();
        LinkedList<LinkedList<WmsLocationStereoscopic>> floor1ListLeft = new LinkedList();
        long t1 = System.currentTimeMillis();
        List<Integer> middleShelvesNumberList = new ArrayList();
        middleShelvesNumberList.add(1);
        middleShelvesNumberList.add(2);
        middleShelvesNumberList.add(3);
        seachOb.setShelvesNumberList(middleShelvesNumberList);
        List<Integer> floorNumList = new ArrayList();
        floorNumList.add(floorNum);
        seachOb.setFloorNumberList(floorNumList);
        List<WmsLocationStereoscopic> list = this.wmsQXFLocationStereoscopicMapper.getOutRihtSimpleLocation(seachOb);
        Integer tmpShelvelsNum;
        if (list != null && !list.isEmpty()) {
           // Integer tmpShelvelsNum = 0;
          //  tmpShelvelsNum = 0;

            tmpShelvelsNum = 0;
            LinkedList<WmsLocationStereoscopic> locationCodeList = new LinkedList();
            Boolean findFlag = false;
            Iterator var20 = list.iterator();

            label203:
            while(true) {
                while(true) {
                    WmsLocationStereoscopic tmp;
                    do {
                        while(true) {
                            do {
                                do {
                                    do {
                                        if (!var20.hasNext()) {
                                            break label203;
                                        }

                                        tmp = (WmsLocationStereoscopic)var20.next();
                                        if (tmpShelvelsNum != tmp.getShelvesNumber() || tmpShelvelsNum != tmp.getLayerNumber()) {
                                            locationCodeList = new LinkedList();
                                            tmpShelvelsNum = tmp.getShelvesNumber();
                                            tmpShelvelsNum = tmp.getLayerNumber();
                                            findFlag = true;
                                        }
                                    } while(!findFlag);
                                } while("0".equals(tmp.getUseStatus()));
                            } while("2".equals(tmp.getUseStatus()));

                            if (!"1".equals(tmp.getUseStatus()) && !"4".equals(tmp.getUseStatus())) {
                                break;
                            }

                            findFlag = false;
                        }
                    } while(!"3".equals(tmp.getUseStatus()));

                    if (goodsCode.equals(tmp.getGoodsCode()) && batchNo.equals(tmp.getBatchNo())) {
                        locationCodeList.add(tmp);
                        if (!floor1ListRight.contains(locationCodeList)) {
                            floor1ListRight.add(locationCodeList);
                        }

                        right.add(tmp.getLocationCode());
                    } else {
                        findFlag = false;
                    }
                }
            }
        }

        middleShelvesNumberList = new ArrayList();
        middleShelvesNumberList.add(2);
        middleShelvesNumberList.add(3);
        seachOb.setShelvesNumberList(middleShelvesNumberList);
        seachOb.setFloorNumber(floorNum);
        List<WmsLocationStereoscopic> listLeft = this.wmsQXFLocationStereoscopicMapper.getOutLeftSimpleLocation(seachOb);
        //Integer tmpShelvelsNum;


        if (listLeft != null && !listLeft.isEmpty()) {
            tmpShelvelsNum = 0;
            tmpShelvelsNum = 0;
            LinkedList<WmsLocationStereoscopic> locationCodeList = new LinkedList();
            Boolean findFlag = false;
            Iterator var30 = listLeft.iterator();

            label162:
            while(true) {
                while(true) {
                    WmsLocationStereoscopic tmp;
                    do {
                        while(true) {
                            do {
                                do {
                                    do {
                                        if (!var30.hasNext()) {
                                            break label162;
                                        }

                                        tmp = (WmsLocationStereoscopic)var30.next();
                                        if (tmpShelvelsNum != tmp.getShelvesNumber() || tmpShelvelsNum != tmp.getLayerNumber()) {
                                            locationCodeList = new LinkedList();
                                            tmpShelvelsNum = tmp.getShelvesNumber();
                                            tmpShelvelsNum = tmp.getLayerNumber();
                                            findFlag = true;
                                        }
                                    } while(!findFlag);
                                } while("0".equals(tmp.getUseStatus()));
                            } while("2".equals(tmp.getUseStatus()));

                            if (!"1".equals(tmp.getUseStatus()) && !"4".equals(tmp.getUseStatus())) {
                                break;
                            }

                            findFlag = false;
                        }
                    } while(!"3".equals(tmp.getUseStatus()));

                    if (goodsCode.equals(tmp.getGoodsCode()) && batchNo.equals(tmp.getBatchNo())) {
                        if (!right.contains(tmp.getLocationCode())) {
                            locationCodeList.add(tmp);
                            if (!floor1ListLeft.contains(locationCodeList)) {
                                floor1ListLeft.add(locationCodeList);
                            }
                        } else {
                            findFlag = false;
                        }
                    } else {
                        findFlag = false;
                    }
                }
            }
        }

        middleShelvesNumberList = new ArrayList();
        middleShelvesNumberList.add(4);
        seachOb.setShelvesNumberList(middleShelvesNumberList);
        seachOb.setFloorNumber(floorNum);
        List<WmsLocationStereoscopic> listBH = this.wmsQXFLocationStereoscopicMapper.getOutLeftSimpleLocation(seachOb);
        if (listBH != null && !listBH.isEmpty()) {
            tmpShelvelsNum = 0;
            Integer tmpLayerNum = 0;
            LinkedList<WmsLocationStereoscopic> locationCodeList = new LinkedList();
            Boolean findFlag = false;
            Iterator var34 = listBH.iterator();

            label121:
            while(true) {
                while(true) {
                    WmsLocationStereoscopic tmp;
                    do {
                        while(true) {
                            do {
                                do {
                                    do {
                                        if (!var34.hasNext()) {
                                            break label121;
                                        }

                                        tmp = (WmsLocationStereoscopic)var34.next();
                                        if (tmpShelvelsNum != tmp.getShelvesNumber() || tmpLayerNum != tmp.getLayerNumber()) {
                                            locationCodeList = new LinkedList();
                                            tmpShelvelsNum = tmp.getShelvesNumber();
                                            tmpLayerNum = tmp.getLayerNumber();
                                            findFlag = true;
                                        }
                                    } while(!findFlag);
                                } while("0".equals(tmp.getUseStatus()));
                            } while("2".equals(tmp.getUseStatus()));

                            if (!"1".equals(tmp.getUseStatus()) && !"4".equals(tmp.getUseStatus())) {
                                break;
                            }

                            findFlag = false;
                        }
                    } while(!"3".equals(tmp.getUseStatus()));

                    if (goodsCode.equals(tmp.getGoodsCode()) && batchNo.equals(tmp.getBatchNo())) {
                        locationCodeList.add(tmp);
                        if (!floor1ListBH.contains(locationCodeList)) {
                            floor1ListBH.add(locationCodeList);
                        }
                    } else {
                        findFlag = false;
                    }
                }
            }
        }

        this.bubbleSortAsc(floor1ListBH);
        this.bubbleSortAsc(floor1ListRight);
        this.bubbleSortAsc(floor1ListLeft);
        long t2 = System.currentTimeMillis();
        floor1ListAll.addAll(floor1ListBH);
        floor1ListAll.addAll(floor1ListRight);
        floor1ListAll.addAll(floor1ListLeft);
        t2 = System.currentTimeMillis();
        if (floor1ListAll != null && !floor1ListAll.isEmpty()) {
            outList = this.transferLocation(floor1ListAll);
        }

        t2 = System.currentTimeMillis();
        return outList;
    }

    public Map<Integer, LinkedList<WmsLocationStereoscopic>> getOutMapList(String goodsCode, String batchNo, List<Integer> floorNumList) {
        Map<Integer, LinkedList<WmsLocationStereoscopic>> outMapList = new HashMap();
        List<String> right = new ArrayList();
        Map<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>> allMapList = new HashMap<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>>() {
            {
                this.put(1, new LinkedList());
                this.put(2, new LinkedList());
                this.put(3, new LinkedList());
                this.put(4, new LinkedList());
            }
        };
        Map<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>> leftMapList = new HashMap<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>>() {
            {
                this.put(1, new LinkedList());
                this.put(2, new LinkedList());
                this.put(3, new LinkedList());
                this.put(4, new LinkedList());
            }
        };
        Map<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>> centerLeftMapList = new HashMap<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>>() {
            {
                this.put(1, new LinkedList());
                this.put(2, new LinkedList());
                this.put(3, new LinkedList());
                this.put(4, new LinkedList());
            }
        };
        Map<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>> centerRightMapList = new HashMap<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>>() {
            {
                this.put(1, new LinkedList());
                this.put(2, new LinkedList());
                this.put(3, new LinkedList());
                this.put(4, new LinkedList());
            }
        };
        Map<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>> bhMapList = new HashMap<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>>() {
            {
                this.put(1, new LinkedList());
                this.put(2, new LinkedList());
                this.put(3, new LinkedList());
                this.put(4, new LinkedList());
            }
        };
        LinkedList<LinkedList<WmsLocationStereoscopic>> leftList = new LinkedList();
        LinkedList<LinkedList<WmsLocationStereoscopic>> centerLeftList = new LinkedList();
        LinkedList<LinkedList<WmsLocationStereoscopic>> centerRightList = new LinkedList();
        LinkedList<LinkedList<WmsLocationStereoscopic>> bhList = new LinkedList();
        WmsLocationStereoscopic seachOb = new WmsLocationStereoscopic();
        seachOb.setGoodsCode(goodsCode);
        seachOb.setBatchNo(batchNo);
        seachOb.setFloorNumberList(floorNumList);
        List<WmsLocationStereoscopic> list = this.wmsQXFLocationStereoscopicMapper.getOutRihtSimpleLocation(seachOb);
        Integer i;
        LinkedList queryList;
        if (list != null && !list.isEmpty()) {
            Integer tmpFloorNum = 0;
            i = 0;
            Integer tmpLayerNum = 0;
            queryList = new LinkedList();
            LinkedList<WmsLocationStereoscopic> locationCodeListReverse = new LinkedList();
            Boolean findFlag = false;
            Iterator var23 = list.iterator();

            label309:
            while(true) {
                while(true) {
                    while(true) {
                        if (!var23.hasNext()) {
                            break label309;
                        }

                        WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var23.next();
                        if (tmpFloorNum != tmp.getFloorNumber() || i != tmp.getShelvesNumber() || tmpLayerNum != tmp.getLayerNumber()) {
                            queryList = new LinkedList();
                            locationCodeListReverse = new LinkedList();
                            tmpFloorNum = tmp.getFloorNumber();
                            i = tmp.getShelvesNumber();
                            tmpLayerNum = tmp.getLayerNumber();
                        }

                        queryList.add(tmp);
                        locationCodeListReverse.addFirst(tmp);
                        if (1 != tmp.getShelvesNumber() && 21 != tmp.getShelvesNumber()) {
                            if (3 == tmp.getShelvesNumber()) {
                                if (!centerLeftList.contains(locationCodeListReverse)) {
                                    centerLeftList.add(locationCodeListReverse);
                                }

                                if (!centerRightList.contains(queryList)) {
                                    centerRightList.add(queryList);
                                }
                            } else if ((4 == tmp.getShelvesNumber() || 20 == tmp.getShelvesNumber()) && !bhList.contains(locationCodeListReverse)) {
                                bhList.add(locationCodeListReverse);
                            }
                        } else if (!leftList.contains(queryList)) {
                            leftList.add(queryList);
                        }
                    }
                }
            }
        }

        Iterator var25;
        LinkedList tmpList;
        LinkedList outList;
        Iterator var28;
        WmsLocationStereoscopic tmp;
        if (bhList != null && !bhList.isEmpty()) {
            var25 = bhList.iterator();

            while(var25.hasNext()) {
                tmpList = (LinkedList)var25.next();
                outList = (LinkedList)bhMapList.get(((WmsLocationStereoscopic)tmpList.get(0)).getFloorNumber());
                queryList = new LinkedList();
                var28 = tmpList.iterator();

                while(var28.hasNext()) {
                    tmp = (WmsLocationStereoscopic)var28.next();
                    if (!"0".equals(tmp.getUseStatus())) {
                        if ("2".equals(tmp.getUseStatus())) {
                            if (!queryList.isEmpty()) {
                                break;
                            }
                        } else {
                            if ("1".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus())) {
                                break;
                            }

                            if ("3".equals(tmp.getUseStatus())) {
                                if (!goodsCode.equals(tmp.getGoodsCode()) || !batchNo.equals(tmp.getBatchNo())) {
                                    break;
                                }

                                queryList.add(tmp);
                            }
                        }
                    }
                }

                if (!queryList.isEmpty()) {
                    outList.add(queryList);
                }
            }
        }

        if (centerRightList != null && !centerRightList.isEmpty()) {
            var25 = centerRightList.iterator();

            while(var25.hasNext()) {
                tmpList = (LinkedList)var25.next();
                outList = (LinkedList)centerRightMapList.get(((WmsLocationStereoscopic)tmpList.get(0)).getFloorNumber());
                queryList = new LinkedList();
                var28 = tmpList.iterator();

                while(var28.hasNext()) {
                    tmp = (WmsLocationStereoscopic)var28.next();
                    if (!"0".equals(tmp.getUseStatus())) {
                        if ("2".equals(tmp.getUseStatus())) {
                            if (!queryList.isEmpty()) {
                                break;
                            }
                        } else {
                            if ("1".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus())) {
                                break;
                            }

                            if ("3".equals(tmp.getUseStatus())) {
                                if (!goodsCode.equals(tmp.getGoodsCode()) || !batchNo.equals(tmp.getBatchNo())) {
                                    break;
                                }

                                queryList.add(tmp);
                                right.add(tmp.getLocationCode());
                            }
                        }
                    }
                }

                if (!queryList.isEmpty()) {
                    outList.add(queryList);
                }
            }
        }

        if (centerLeftList != null && !centerLeftList.isEmpty()) {
            var25 = centerLeftList.iterator();

            while(var25.hasNext()) {
                tmpList = (LinkedList)var25.next();
                outList = (LinkedList)centerLeftMapList.get(((WmsLocationStereoscopic)tmpList.get(0)).getFloorNumber());
                queryList = new LinkedList();
                var28 = tmpList.iterator();

                while(var28.hasNext()) {
                    tmp = (WmsLocationStereoscopic)var28.next();
                    if (!"0".equals(tmp.getUseStatus())) {
                        if ("2".equals(tmp.getUseStatus())) {
                            if (!queryList.isEmpty()) {
                                break;
                            }
                        } else {
                            if ("1".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus())) {
                                break;
                            }

                            if ("3".equals(tmp.getUseStatus())) {
                                if (!goodsCode.equals(tmp.getGoodsCode()) || !batchNo.equals(tmp.getBatchNo()) || right.contains(tmp.getLocationCode())) {
                                    break;
                                }

                                queryList.add(tmp);
                            }
                        }
                    }
                }

                if (!queryList.isEmpty()) {
                    outList.add(queryList);
                }
            }
        }

        if (leftList != null && !leftList.isEmpty()) {
            var25 = leftList.iterator();

            while(var25.hasNext()) {
                tmpList = (LinkedList)var25.next();
                outList = (LinkedList)leftMapList.get(((WmsLocationStereoscopic)tmpList.get(0)).getFloorNumber());
                queryList = new LinkedList();
                var28 = tmpList.iterator();

                while(var28.hasNext()) {
                    tmp = (WmsLocationStereoscopic)var28.next();
                    if (!"0".equals(tmp.getUseStatus())) {
                        if ("2".equals(tmp.getUseStatus())) {
                            if (!queryList.isEmpty()) {
                                break;
                            }
                        } else {
                            if ("1".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus())) {
                                break;
                            }

                            if ("3".equals(tmp.getUseStatus())) {
                                if (!goodsCode.equals(tmp.getGoodsCode()) || !batchNo.equals(tmp.getBatchNo())) {
                                    break;
                                }

                                queryList.add(tmp);
                            }
                        }
                    }
                }

                if (!queryList.isEmpty()) {
                    outList.add(queryList);
                }
            }
        }

        var25 = bhMapList.keySet().iterator();

        while(var25.hasNext()) {
            i = (Integer)var25.next();
            this.bubbleSortAsc((List)bhMapList.get(i));
        }

        var25 = centerRightMapList.keySet().iterator();

        while(var25.hasNext()) {
            i = (Integer)var25.next();
            this.bubbleSortAsc((List)centerRightMapList.get(i));
        }

        var25 = centerLeftMapList.keySet().iterator();

        while(var25.hasNext()) {
            i = (Integer)var25.next();
            this.bubbleSortAsc((List)centerLeftMapList.get(i));
        }

        var25 = leftMapList.keySet().iterator();

        while(var25.hasNext()) {
            i = (Integer)var25.next();
            this.bubbleSortAsc((List)leftMapList.get(i));
        }

        var25 = bhMapList.keySet().iterator();

        while(var25.hasNext()) {
            i = (Integer)var25.next();
            ((LinkedList)allMapList.get(i)).addAll((Collection)centerRightMapList.get(i));
            ((LinkedList)allMapList.get(i)).addAll((Collection)centerLeftMapList.get(i));
            ((LinkedList)allMapList.get(i)).addAll((Collection)leftMapList.get(i));
        }

        var25 = floorNumList.iterator();

        while(var25.hasNext()) {
            i = (Integer)var25.next();
            outList = new LinkedList();
            outList.addAll(this.transferLocation((LinkedList)bhMapList.get(i)));
            outList.addAll(this.transferLocation((LinkedList)allMapList.get(i)));
            outMapList.put(i, outList);
        }

        return outMapList;
    }

    public Map<Integer, LinkedList<WmsLocationStereoscopic>> getVirtualPalletOutMapList(String goodsCode, String batchNo, List<Integer> floorNumList) {
        Map<Integer, LinkedList<WmsLocationStereoscopic>> outMapList = new HashMap();
        List<String> right = new ArrayList();
        Map<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>> allMapList = new HashMap<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>>() {
            {
                this.put(1, new LinkedList());
                this.put(2, new LinkedList());
                this.put(3, new LinkedList());
                this.put(4, new LinkedList());
            }
        };
        Map<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>> leftMapList = new HashMap<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>>() {
            {
                this.put(1, new LinkedList());
                this.put(2, new LinkedList());
                this.put(3, new LinkedList());
                this.put(4, new LinkedList());
            }
        };
        Map<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>> centerVirtualLeftMapList = new HashMap<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>>() {
            {
                this.put(1, new LinkedList());
                this.put(2, new LinkedList());
                this.put(3, new LinkedList());
                this.put(4, new LinkedList());
            }
        };
        Map<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>> centerVirtualRightMapList = new HashMap<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>>() {
            {
                this.put(1, new LinkedList());
                this.put(2, new LinkedList());
                this.put(3, new LinkedList());
                this.put(4, new LinkedList());
            }
        };
        Map<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>> centerLeftMapList = new HashMap<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>>() {
            {
                this.put(1, new LinkedList());
                this.put(2, new LinkedList());
                this.put(3, new LinkedList());
                this.put(4, new LinkedList());
            }
        };
        Map<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>> centerRightMapList = new HashMap<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>>() {
            {
                this.put(1, new LinkedList());
                this.put(2, new LinkedList());
                this.put(3, new LinkedList());
                this.put(4, new LinkedList());
            }
        };
        Map<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>> bhMapList = new HashMap<Integer, LinkedList<LinkedList<WmsLocationStereoscopic>>>() {
            {
                this.put(1, new LinkedList());
                this.put(2, new LinkedList());
                this.put(3, new LinkedList());
                this.put(4, new LinkedList());
            }
        };
        LinkedList<LinkedList<WmsLocationStereoscopic>> leftList = new LinkedList();
        LinkedList<LinkedList<WmsLocationStereoscopic>> centerVirtualLeftList = new LinkedList();
        LinkedList<LinkedList<WmsLocationStereoscopic>> centerVirtualRightList = new LinkedList();
        LinkedList<LinkedList<WmsLocationStereoscopic>> centerGoodsLeftList = new LinkedList();
        LinkedList<LinkedList<WmsLocationStereoscopic>> centerGoodsRightList = new LinkedList();
        LinkedList<LinkedList<WmsLocationStereoscopic>> bhList = new LinkedList();
        WmsLocationStereoscopic seachOb = new WmsLocationStereoscopic();
        seachOb.setGoodsCode(goodsCode);
        seachOb.setBatchNo(batchNo);
        seachOb.setFloorNumberList(floorNumList);
        List<WmsLocationStereoscopic> list = this.wmsQXFLocationStereoscopicMapper.getOutRihtSimpleLocation(seachOb);
        Integer i;
        LinkedList queryList;
        if (list != null && !list.isEmpty()) {
            Integer tmpFloorNum = 0;
            i = 0;
            Integer tmpLayerNum = 0;
            queryList = new LinkedList();
            LinkedList<WmsLocationStereoscopic> locationCodeListReverse = new LinkedList();
            Boolean findFlag = false;
            Iterator var27 = list.iterator();

            label402:
            while(true) {
                while(true) {
                    while(true) {
                        if (!var27.hasNext()) {
                            break label402;
                        }

                        WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var27.next();
                        if (tmpFloorNum != tmp.getFloorNumber() || i != tmp.getShelvesNumber() || tmpLayerNum != tmp.getLayerNumber()) {
                            queryList = new LinkedList();
                            locationCodeListReverse = new LinkedList();
                            tmpFloorNum = tmp.getFloorNumber();
                            i = tmp.getShelvesNumber();
                            tmpLayerNum = tmp.getLayerNumber();
                        }

                        queryList.add(tmp);
                        locationCodeListReverse.addFirst(tmp);
                        if (1 != tmp.getShelvesNumber() && 21 != tmp.getShelvesNumber()) {
                            if (3 == tmp.getShelvesNumber()) {
                                if (5 >= tmp.getLayerNumber()) {
                                    if (!centerVirtualLeftList.contains(locationCodeListReverse)) {
                                        centerVirtualLeftList.addFirst(locationCodeListReverse);
                                    }

                                    if (!centerVirtualRightList.contains(queryList)) {
                                        centerVirtualRightList.addFirst(queryList);
                                    }
                                } else {
                                    if (!centerGoodsLeftList.contains(locationCodeListReverse)) {
                                        centerGoodsLeftList.add(locationCodeListReverse);
                                    }

                                    if (!centerGoodsRightList.contains(queryList)) {
                                        centerGoodsRightList.add(queryList);
                                    }
                                }
                            } else if ((4 == tmp.getShelvesNumber() || 20 == tmp.getShelvesNumber()) && !bhList.contains(locationCodeListReverse)) {
                                bhList.add(locationCodeListReverse);
                            }
                        } else if (!leftList.contains(queryList)) {
                            leftList.add(queryList);
                        }
                    }
                }
            }
        }

        Iterator var29;
        LinkedList tmpList;
        LinkedList outList;
        Iterator var32;
        WmsLocationStereoscopic tmp;
        if (bhList != null && !bhList.isEmpty()) {
            var29 = bhList.iterator();

            while(var29.hasNext()) {
                tmpList = (LinkedList)var29.next();
                outList = (LinkedList)bhMapList.get(((WmsLocationStereoscopic)tmpList.get(0)).getFloorNumber());
                queryList = new LinkedList();
                var32 = tmpList.iterator();

                while(var32.hasNext()) {
                    tmp = (WmsLocationStereoscopic)var32.next();
                    if (!"0".equals(tmp.getUseStatus())) {
                        if ("2".equals(tmp.getUseStatus())) {
                            if (!queryList.isEmpty()) {
                                break;
                            }
                        } else {
                            if ("1".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus())) {
                                break;
                            }

                            if ("3".equals(tmp.getUseStatus())) {
                                if (!goodsCode.equals(tmp.getGoodsCode())) {
                                    break;
                                }

                                queryList.add(tmp);
                            }
                        }
                    }
                }

                if (!queryList.isEmpty()) {
                    outList.add(queryList);
                }
            }
        }

        if (centerVirtualRightList != null && !centerVirtualRightList.isEmpty()) {
            var29 = centerVirtualRightList.iterator();

            while(var29.hasNext()) {
                tmpList = (LinkedList)var29.next();
                outList = (LinkedList)centerVirtualRightMapList.get(((WmsLocationStereoscopic)tmpList.get(0)).getFloorNumber());
                queryList = new LinkedList();
                var32 = tmpList.iterator();

                while(var32.hasNext()) {
                    tmp = (WmsLocationStereoscopic)var32.next();
                    if (!"0".equals(tmp.getUseStatus())) {
                        if ("2".equals(tmp.getUseStatus())) {
                            if (!queryList.isEmpty()) {
                                break;
                            }
                        } else {
                            if ("1".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus())) {
                                break;
                            }

                            if ("3".equals(tmp.getUseStatus())) {
                                if (!goodsCode.equals(tmp.getGoodsCode())) {
                                    break;
                                }

                                queryList.add(tmp);
                                right.add(tmp.getLocationCode());
                            }
                        }
                    }
                }

                if (!queryList.isEmpty()) {
                    outList.add(queryList);
                }
            }
        }

        if (centerVirtualLeftList != null && !centerVirtualLeftList.isEmpty()) {
            var29 = centerVirtualLeftList.iterator();

            while(var29.hasNext()) {
                tmpList = (LinkedList)var29.next();
                outList = (LinkedList)centerVirtualLeftMapList.get(((WmsLocationStereoscopic)tmpList.get(0)).getFloorNumber());
                queryList = new LinkedList();
                var32 = tmpList.iterator();

                while(var32.hasNext()) {
                    tmp = (WmsLocationStereoscopic)var32.next();
                    if (!"0".equals(tmp.getUseStatus())) {
                        if ("2".equals(tmp.getUseStatus())) {
                            if (!queryList.isEmpty()) {
                                break;
                            }
                        } else {
                            if ("1".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus())) {
                                break;
                            }

                            if ("3".equals(tmp.getUseStatus())) {
                                if (!goodsCode.equals(tmp.getGoodsCode()) || right.contains(tmp.getLocationCode())) {
                                    break;
                                }

                                queryList.add(tmp);
                            }
                        }
                    }
                }

                if (!queryList.isEmpty()) {
                    outList.add(queryList);
                }
            }
        }

        if (centerGoodsRightList != null && !centerGoodsRightList.isEmpty()) {
            var29 = centerGoodsRightList.iterator();

            while(var29.hasNext()) {
                tmpList = (LinkedList)var29.next();
                outList = (LinkedList)centerRightMapList.get(((WmsLocationStereoscopic)tmpList.get(0)).getFloorNumber());
                queryList = new LinkedList();
                var32 = tmpList.iterator();

                while(var32.hasNext()) {
                    tmp = (WmsLocationStereoscopic)var32.next();
                    if (!"0".equals(tmp.getUseStatus())) {
                        if ("2".equals(tmp.getUseStatus())) {
                            if (!queryList.isEmpty()) {
                                break;
                            }
                        } else {
                            if ("1".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus())) {
                                break;
                            }

                            if ("3".equals(tmp.getUseStatus())) {
                                if (!goodsCode.equals(tmp.getGoodsCode())) {
                                    break;
                                }

                                queryList.add(tmp);
                                right.add(tmp.getLocationCode());
                            }
                        }
                    }
                }

                if (!queryList.isEmpty()) {
                    outList.add(queryList);
                }
            }
        }

        if (centerGoodsLeftList != null && !centerGoodsLeftList.isEmpty()) {
            var29 = centerGoodsLeftList.iterator();

            while(var29.hasNext()) {
                tmpList = (LinkedList)var29.next();
                outList = (LinkedList)centerLeftMapList.get(((WmsLocationStereoscopic)tmpList.get(0)).getFloorNumber());
                queryList = new LinkedList();
                var32 = tmpList.iterator();

                while(var32.hasNext()) {
                    tmp = (WmsLocationStereoscopic)var32.next();
                    if (!"0".equals(tmp.getUseStatus())) {
                        if ("2".equals(tmp.getUseStatus())) {
                            if (!queryList.isEmpty()) {
                                break;
                            }
                        } else {
                            if ("1".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus())) {
                                break;
                            }

                            if ("3".equals(tmp.getUseStatus())) {
                                if (!goodsCode.equals(tmp.getGoodsCode()) || right.contains(tmp.getLocationCode())) {
                                    break;
                                }

                                queryList.add(tmp);
                            }
                        }
                    }
                }

                if (!queryList.isEmpty()) {
                    outList.add(queryList);
                }
            }
        }

        if (leftList != null && !leftList.isEmpty()) {
            var29 = leftList.iterator();

            while(var29.hasNext()) {
                tmpList = (LinkedList)var29.next();
                outList = (LinkedList)leftMapList.get(((WmsLocationStereoscopic)tmpList.get(0)).getFloorNumber());
                queryList = new LinkedList();
                var32 = tmpList.iterator();

                while(var32.hasNext()) {
                    tmp = (WmsLocationStereoscopic)var32.next();
                    if (!"0".equals(tmp.getUseStatus())) {
                        if ("2".equals(tmp.getUseStatus())) {
                            if (!queryList.isEmpty()) {
                                break;
                            }
                        } else {
                            if ("1".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus())) {
                                break;
                            }

                            if ("3".equals(tmp.getUseStatus())) {
                                if (!goodsCode.equals(tmp.getGoodsCode())) {
                                    break;
                                }

                                queryList.add(tmp);
                            }
                        }
                    }
                }

                if (!queryList.isEmpty()) {
                    outList.add(queryList);
                }
            }
        }

        var29 = bhMapList.keySet().iterator();

        while(var29.hasNext()) {
            i = (Integer)var29.next();
            this.bubbleSortAsc((List)bhMapList.get(i));
        }

        var29 = centerRightMapList.keySet().iterator();

        while(var29.hasNext()) {
            i = (Integer)var29.next();
            this.bubbleSortAsc((List)centerRightMapList.get(i));
        }

        var29 = centerLeftMapList.keySet().iterator();

        while(var29.hasNext()) {
            i = (Integer)var29.next();
            this.bubbleSortAsc((List)centerLeftMapList.get(i));
        }

        var29 = leftMapList.keySet().iterator();

        while(var29.hasNext()) {
            i = (Integer)var29.next();
            this.bubbleSortAsc((List)leftMapList.get(i));
        }

        var29 = bhMapList.keySet().iterator();

        while(var29.hasNext()) {
            i = (Integer)var29.next();
            ((LinkedList)allMapList.get(i)).addAll((Collection)centerRightMapList.get(i));
            ((LinkedList)allMapList.get(i)).addAll((Collection)centerLeftMapList.get(i));
            ((LinkedList)allMapList.get(i)).addAll((Collection)leftMapList.get(i));
        }

        var29 = floorNumList.iterator();

        while(var29.hasNext()) {
            i = (Integer)var29.next();
            outList = new LinkedList();
            outList.addAll(this.transferLocation((LinkedList)bhMapList.get(i)));
            outList.addAll(this.transferLocation((LinkedList)allMapList.get(i)));
            outList.addAll(this.transferLocation((LinkedList)centerVirtualRightMapList.get(i)));
            outList.addAll(this.transferLocation((LinkedList)centerVirtualLeftMapList.get(i)));
            outMapList.put(i, outList);
        }

        return outMapList;
    }

    public void bubbleSortAsc(List<LinkedList<WmsLocationStereoscopic>> list) {
        if (list != null && !list.isEmpty()) {
            for(int i = 0; i < list.size() - 1; ++i) {
                for(int j = 0; j < list.size() - 1 - i; ++j) {
                    if (((LinkedList)list.get(j)).size() > ((LinkedList)list.get(j + 1)).size()) {
                        LinkedList<WmsLocationStereoscopic> a = (LinkedList)list.get(j + 1);
                        list.set(j + 1, list.get(j));
                        list.set(j, a);
                    }
                }
            }
        }

    }

    public LinkedList<WmsLocationStereoscopic> transferLocation(LinkedList<LinkedList<WmsLocationStereoscopic>> list) {
        LinkedList<WmsLocationStereoscopic> rtnList = new LinkedList();
        if (list != null && !list.isEmpty()) {
            if (list.size() == 1) {
                rtnList = (LinkedList)list.get(0);
                return rtnList;
            } else {
                Boolean readFirstRow = true;
                LinkedList<WmsLocationStereoscopic> first = (LinkedList)list.pop();
                LinkedList second = (LinkedList)list.pop();

                while(!first.isEmpty() || !second.isEmpty()) {
                    if (readFirstRow) {
                        readFirstRow = false;
                        if (!first.isEmpty()) {
                            rtnList.add(first.pop());
                            if (first.isEmpty() && !list.isEmpty()) {
                                first = (LinkedList)list.pop();
                            }
                        }
                    } else {
                        readFirstRow = true;
                        if (!second.isEmpty()) {
                            rtnList.add((WmsLocationStereoscopic)second.pop());
                            readFirstRow = true;
                            if (second.isEmpty() && !list.isEmpty()) {
                                second = (LinkedList)list.pop();
                            }
                        }
                    }
                }

                return rtnList;
            }
        } else {
            return rtnList;
        }
    }

    public Resp revertOut(List<WmsMoveTask> wmsMoveTaskList, List<WmsOutTask> wmsOutTaskList, String orderNo) {
        Resp resp = new Resp();
        resp.setCode(RESULT.SUCCESS.code);
        List<String> outlockList = new ArrayList();
        if (wmsOutTaskList != null && !wmsOutTaskList.isEmpty()) {
            Iterator iter = wmsOutTaskList.iterator();

            while(iter.hasNext()) {
                WmsOutTask tmp = (WmsOutTask)iter.next();
                if (tmp.getFromLocation() != null) {
                    outlockList.add(tmp.getFromLocation());
                }
            }
        }

        WmsLocationStereoscopic updateLocation = new WmsLocationStereoscopic();
        updateLocation.setLastModifiedBy("query_out_location_revert");
        updateLocation.setGmtModified(new Date());
        if (outlockList != null && !outlockList.isEmpty()) {
            updateLocation.setLocationCodeList(outlockList);
            updateLocation.setUseStatus("3");
            this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateLocation);
        }

        if (orderNo != null && !"".equals(orderNo)) {
            WmsOrderOutStereoscopic wmsOrderOutStereoscopic = new WmsOrderOutStereoscopic();
            wmsOrderOutStereoscopic.setOrderStatus("5");
            wmsOrderOutStereoscopic.setOrderNo(orderNo);
            this.wmsOrderOutStereoscopicMapper.updateByOrderNo(wmsOrderOutStereoscopic);
        }

        return this.success();
    }

    @Transactional
    public Resp palletOutLocationCheck(String locationCode, String loginName, String targetAddress) {
        Date now = new Date();
        Resp resp = new Resp();
        List<WmsOutTask> wmsOutTaskList = new LinkedList();
        WmsPallet moveWmsPallet = new WmsPallet();
        WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
        searchOb.setLocationCode(locationCode);
        List<WmsLocationStereoscopic> list = this.wmsQXFLocationStereoscopicMapper.getTotalRowLocation(searchOb);
        if (list != null && !list.isEmpty()) {
            Boolean leftUnLocked = true;
            Boolean rightUnLocked = true;
            Integer shelvesNumber = ((WmsLocationStereoscopic)list.get(0)).getShelvesNumber();
            Integer layerNumber = ((WmsLocationStereoscopic)list.get(0)).getLayerNumber();
            Iterator var14 = list.iterator();

            WmsLocationStereoscopic tmp;
            while(var14.hasNext()) {
                tmp = (WmsLocationStereoscopic)var14.next();
                if (locationCode.equals(tmp.getLocationCode())) {
                    if (tmp.getUseStatus() != null && !"3".equals(tmp.getUseStatus())) {
                        return this.fail("当前托盘库位非占用状态，不能启动托盘出库！");
                    }

                    WmsPallet wmsPallet = new WmsPallet();
                    wmsPallet.setPalletCode(tmp.getPalletCode());
                    List<WmsPallet> palletList = this.wmsPalletMapper.queryByAny(wmsPallet);
                    if (palletList != null && !palletList.isEmpty()) {
                        WmsPallet ptmp = (WmsPallet)palletList.get(0);
                        if (ptmp.getLockBy() != null && !"".equals(ptmp.getLockBy())) {
                            return this.fail("当前托盘被锁定，不能启动托盘出库！");
                        }

                        moveWmsPallet = ptmp;
                        break;
                    }

                    return this.fail("当前托盘信息有误，不能启动托盘出库！");
                }
            }

            var14 = list.iterator();

            while(var14.hasNext()) {
                tmp = (WmsLocationStereoscopic)var14.next();
                if (locationCode.equals(tmp.getLocationCode())) {
                    break;
                }

                if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                    leftUnLocked = false;
                    break;
                }
            }

            Collections.reverse(list);
            var14 = list.iterator();

            while(var14.hasNext()) {
                tmp = (WmsLocationStereoscopic)var14.next();
                if (locationCode.equals(tmp.getLocationCode())) {
                    break;
                }

                if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                    rightUnLocked = false;
                    break;
                }
            }

            if (shelvesNumber != 1 && shelvesNumber != 21) {
                if (shelvesNumber != 4 && shelvesNumber != 20) {
                    if (!rightUnLocked && !leftUnLocked) {
                        return this.fail("当前托盘库位的出库通道不通畅，不能启动托盘出库！");
                    }
                } else if (!leftUnLocked) {
                    return this.fail("当前托盘库位的出库通道不通畅，不能启动托盘出库！");
                }
            } else if (!rightUnLocked) {
                return this.fail("当前托盘库位的出库通道不通畅，不能启动托盘出库！");
            }

            WmsLocationStereoscopic updateOb1 = new WmsLocationStereoscopic();
            updateOb1.setLocationCode(locationCode);
            updateOb1.setUseStatus("2");
            updateOb1.setLastModifiedBy(loginName);
            updateOb1.setGmtModified(now);
            this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb1);
            WmsOutTask wmsOutTask = new WmsOutTask();
            wmsOutTask.setTaskId(this.wmsCommonService.getTaskIds(TaskType.HAND_OUT, 1)[0]);
            wmsOutTask.setTargetAddress(targetAddress);
            wmsOutTask.setOperator(loginName);
            wmsOutTask.setTaskType(String.valueOf(TaskType.HAND_OUT.getTaskType()));
            wmsOutTask.setFromLocation(locationCode);
            wmsOutTask.setPalletCode(moveWmsPallet.getPalletCode());
            wmsOutTask.setGoodsCode(moveWmsPallet.getGoodsCode());
            wmsOutTask.setGoodsName(moveWmsPallet.getGoodsName());
            wmsOutTask.setBatchNo(moveWmsPallet.getBatchNo());
            wmsOutTask.setAmount(moveWmsPallet.getAmount());
            wmsOutTaskList.add(wmsOutTask);
            resp.setData(wmsOutTaskList);
            return resp;
        } else {
            return this.fail("当前托盘库位信息有误，不能启动托盘出库！");
        }
    }

    @Transactional
    public Resp startPalletOut(List<WmsOutTask> wmsOutTaskList, String loginName) {
        Date now = new Date();
        String orderNo = "";
        if (wmsOutTaskList != null && !wmsOutTaskList.isEmpty()) {
            WmsOutTask tmp = (WmsOutTask)wmsOutTaskList.get(0);
            if (!String.valueOf(TaskType.VIRTUAL_PALLET_OUT).equals(tmp.getTaskType())) {
                orderNo = this.wmsCommonService.getOrderNoByType("PSO");
                WmsOrderOutStereoscopic wmsOrderOutStereoscopic = new WmsOrderOutStereoscopic();
                wmsOrderOutStereoscopic.setOrderNo(orderNo);
                wmsOrderOutStereoscopic.setOutAddress(tmp.getTargetAddress());
                wmsOrderOutStereoscopic.setOrderType(String.valueOf(TaskType.HAND_OUT.getTaskType()));
                wmsOrderOutStereoscopic.setOrderOutId(CommonUtils.getUUID());
                wmsOrderOutStereoscopic.setCreateBy(loginName);
                wmsOrderOutStereoscopic.setGmtCreate(now);
                wmsOrderOutStereoscopic.setActiveFlag("1");
                wmsOrderOutStereoscopic.setOrderStatus("2");
                this.wmsOrderOutStereoscopicMapper.create(wmsOrderOutStereoscopic);
                WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail = new WmsOrderOutStereoscopicDeail();
                wmsOrderOutStereoscopicDeail.setOrderOutDetailId(CommonUtils.getUUID());
                wmsOrderOutStereoscopicDeail.setOrderNo(orderNo);
                wmsOrderOutStereoscopicDeail.setGoodsCode(tmp.getGoodsCode());
                wmsOrderOutStereoscopicDeail.setGoodsName(tmp.getGoodsName());
                wmsOrderOutStereoscopicDeail.setBatchNo(tmp.getBatchNo());
                wmsOrderOutStereoscopicDeail.setPlanAmount(tmp.getAmount());
                wmsOrderOutStereoscopicDeail.setCreateBy(loginName);
                wmsOrderOutStereoscopicDeail.setGmtCreate(now);
                wmsOrderOutStereoscopicDeail.setActiveFlag("1");
                this.wmsOrderOutStereoscopicDeailMapper.create(wmsOrderOutStereoscopicDeail);
            }

            WmsPallet t = new WmsPallet();
            t.setPalletCode(tmp.getPalletCode());
            t.setLockBy(String.valueOf(tmp.getTaskId()));
            t.setLastModifiedBy(loginName);
            t.setGmtModified(now);
            t.setUserDefined1(orderNo);
            this.wmsPalletMapper.updateByPalletCode(t);
            WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
            wmsTaskExecutionLog.setTaskId(tmp.getTaskId());
            wmsTaskExecutionLog.setOrderNo(orderNo);
            wmsTaskExecutionLog.setTaskType(tmp.getTaskType());
            wmsTaskExecutionLog.setPalletCode(tmp.getPalletCode());
            wmsTaskExecutionLog.setTaskStatus("1");
            wmsTaskExecutionLog.setOutAddress(tmp.getTargetAddress());
            wmsTaskExecutionLog.setLocationCode(tmp.getFromLocation());
            wmsTaskExecutionLog.setGoodsCode(tmp.getGoodsCode());
            wmsTaskExecutionLog.setBatchNo(tmp.getBatchNo());
            wmsTaskExecutionLog.setCreateBy(loginName);
            wmsTaskExecutionLog.setGmtCreate(now);
            wmsTaskExecutionLog.setActiveFlag("1");
            wmsTaskExecutionLog.setUserDefined1(Integer.toString(tmp.getAmount()));
            this.wmsTaskExecutionLogMapper.create(wmsTaskExecutionLog);
        }

        return this.success();
    }

    @Transactional
    public Resp lockCheckLocationCode(WmsOrderCheck wmsOrderCheck) {
        Resp resp = new Resp();
        resp.setCode(RESULT.SUCCESS.code);
        List<WmsOutTask> wmsOutTaskList = new LinkedList();
        StringBuffer sb = new StringBuffer("");
        WmsOrderCheckPallet checkPallet = new WmsOrderCheckPallet();
        checkPallet.setOrderNo(wmsOrderCheck.getOrderNo());
        List<WmsOrderCheckPallet> locationStatusList = this.wmsOrderCheckPalletMapper.queryLocationStatus(checkPallet);
        if (locationStatusList != null && !locationStatusList.isEmpty()) {
            Iterator var7 = locationStatusList.iterator();

            while(var7.hasNext()) {
                WmsOrderCheckPallet tmp = (WmsOrderCheckPallet)var7.next();
                if (tmp.getUseStatus() != null && ("1".equals(tmp.getUseStatus()) || "2".equals(tmp.getUseStatus()))) {
                    return this.fail("当前盘点托盘库位及途径库位中包含锁定状态的库位");
                }

                if (tmp.getUseStatus() != null && "4".equals(tmp.getUseStatus())) {
                    return this.fail("当前盘点托盘库位及途径库位中包含异常状态的库位");
                }
            }
        }

        String targetAddress = "1";
        WmsAddressRealRela address = new WmsAddressRealRela();
        address.setAddressCode(wmsOrderCheck.getOutAddress());
        List<WmsAddressRealRela> addressList = this.wmsAddressRealRelaMapper.queryByAddressCode(address);
        if (addressList != null && !addressList.isEmpty()) {
            WmsAddressRealRela tmp = (WmsAddressRealRela)addressList.get(0);
            targetAddress = String.valueOf(tmp.getRealAddress());
        }

        List<WmsOrderCheckPallet> list = this.wmsOrderCheckPalletMapper.queryOutList(checkPallet);
        WmsOutTask tmp;
        if (list != null && !list.isEmpty()) {
            Iterator var11 = list.iterator();

            while(var11.hasNext()) {
                WmsOrderCheckPallet tmp1 = (WmsOrderCheckPallet)var11.next();
                //WmsOrderCheckPallet tmp = (WmsOrderCheckPallet)var11.next();
                tmp = new WmsOutTask();
                tmp.setTaskId(this.wmsCommonService.getTaskIds(TaskType.CHECK_OUT, 1)[0]);
                tmp.setTargetAddress(targetAddress);
                tmp.setOperator(wmsOrderCheck.getCreateBy());
                tmp.setTaskType(String.valueOf(TaskType.CHECK_OUT.getTaskType()));
                tmp.setPalletCode(tmp.getPalletCode());
                tmp.setFromLocation(tmp1.getLocationCode());
                tmp.setGoodsCode(tmp.getGoodsCode());
                tmp.setGoodsName(tmp.getGoodsName());
                tmp.setBatchNo(tmp.getBatchNo());
                wmsOutTaskList.add(tmp);
            }
        }

        List<String> outlockList = new ArrayList();
        Iterator iter = wmsOutTaskList.iterator();

        while(iter.hasNext()) {
            tmp = (WmsOutTask)iter.next();
            if (tmp.getFromLocation() != null) {
                outlockList.add(tmp.getFromLocation());
            }
        }

        WmsLocationStereoscopic updateLocation = new WmsLocationStereoscopic();
        updateLocation.setLastModifiedBy(wmsOrderCheck.getCreateBy());
        updateLocation.setGmtModified(new Date());
        if (outlockList != null && !outlockList.isEmpty()) {
            updateLocation.setLocationCodeList(outlockList);
            updateLocation.setUseStatus("2");
            this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateLocation);
        }

        resp.setData(wmsOutTaskList);
        resp.setMsg(sb.toString());
        return resp;
    }

    @Transactional
    public Resp createOrEditYk(WmsMoveStereoscopic wmsMoveStereoscopic) {
        Date now = new Date();
        String moveNo = wmsMoveStereoscopic.getMoveNo();
        WmsPallet moveWmsPallet = new WmsPallet();
        String moveGoodsCode = "";
        String fromFloor = wmsMoveStereoscopic.getFromLocationCode().substring(0, 1);
        String toFloor = wmsMoveStereoscopic.getToLocationCode().substring(0, 1);
        if (!fromFloor.equals(toFloor)) {
            return this.fail("非同层库位间不能移库！");
        } else {
            WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
            searchOb.setLocationCode(wmsMoveStereoscopic.getFromLocationCode());
            List<WmsLocationStereoscopic> list = this.wmsQXFLocationStereoscopicMapper.getTotalRowLocation(searchOb);
            if (list != null && !list.isEmpty()) {
                Boolean leftUnLocked = true;
                Boolean rightUnLocked = true;
                Integer shelvesNumber = ((WmsLocationStereoscopic)list.get(0)).getShelvesNumber();
                Integer layerNumber = ((WmsLocationStereoscopic)list.get(0)).getLayerNumber();
                Iterator var14 = list.iterator();

                while(true) {
                    WmsLocationStereoscopic tmp;
                    if (var14.hasNext()) {
                        tmp = (WmsLocationStereoscopic)var14.next();
                        if (!wmsMoveStereoscopic.getFromLocationCode().equals(tmp.getLocationCode())) {
                            continue;
                        }

                        if (tmp.getUseStatus() != null && !"3".equals(tmp.getUseStatus())) {
                            return this.fail("源库位非占用状态，不能进行移库！");
                        }

                        WmsPallet wmsPallet = new WmsPallet();
                        wmsPallet.setPalletCode(tmp.getPalletCode());
                        List<WmsPallet> palletList = this.wmsPalletMapper.queryByAny(wmsPallet);
                        if (palletList == null || palletList.isEmpty()) {
                            return this.fail("源库位上的托盘信息有误，不能进行移库！");
                        }

                        WmsPallet ptmp = (WmsPallet)palletList.get(0);
                        if (ptmp.getLockBy() != null && !"".equals(ptmp.getLockBy())) {
                            return this.fail("源库位上的托盘被锁定，不能进行移库！");
                        }

                        moveWmsPallet = ptmp;
                        moveGoodsCode = tmp.getGoodsCode();
                    }

                    var14 = list.iterator();

                    while(var14.hasNext()) {
                        tmp = (WmsLocationStereoscopic)var14.next();
                        if (wmsMoveStereoscopic.getFromLocationCode().equals(tmp.getLocationCode())) {
                            break;
                        }

                        if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                            leftUnLocked = false;
                            break;
                        }
                    }

                    Collections.reverse(list);
                    var14 = list.iterator();

                    while(var14.hasNext()) {
                        tmp = (WmsLocationStereoscopic)var14.next();
                        if (wmsMoveStereoscopic.getFromLocationCode().equals(tmp.getLocationCode())) {
                            break;
                        }

                        if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                            rightUnLocked = false;
                            break;
                        }
                    }

                    if (shelvesNumber != 1 && shelvesNumber != 21) {
                        if (shelvesNumber != 4 && shelvesNumber != 20) {
                            if (!rightUnLocked && !leftUnLocked) {
                                return this.fail("源库位的移库通道不通畅，不能进行移库！");
                            }
                        } else if (!leftUnLocked) {
                            return this.fail("源库位的移库通道不通畅，不能进行移库！");
                        }
                    } else if (!rightUnLocked) {
                        return this.fail("源库位的移库通道不通畅，不能进行移库！");
                    }

                    WmsLocationStereoscopic searchOb2 = new WmsLocationStereoscopic();
                    searchOb2.setLocationCode(wmsMoveStereoscopic.getToLocationCode());
                    List<WmsLocationStereoscopic> list2 = this.wmsQXFLocationStereoscopicMapper.getTotalRowLocation(searchOb2);
                    if (list2 != null && !list2.isEmpty()) {
//                        Boolean leftUnLocked = true;
//                        Boolean rightUnLocked = true;
//                        Integer shelvesNumber = ((WmsLocationStereoscopic)list2.get(0)).getShelvesNumber();
//                        Integer layerNumber = ((WmsLocationStereoscopic)list2.get(0)).getLayerNumber();


                        leftUnLocked = true;
                        rightUnLocked = true;
                        shelvesNumber = ((WmsLocationStereoscopic)list2.get(0)).getShelvesNumber();
                        layerNumber = ((WmsLocationStereoscopic)list2.get(0)).getLayerNumber();

                        Iterator var28 = list2.iterator();

                       // WmsLocationStereoscopic tmp;
                        do {
                            do {
                                if (!var28.hasNext()) {
                                    String beforeGoodsCode = null;
                                    String afterGoodsCode = null;
                                    HashSet<String> goodsSet = new HashSet();

                                   // WmsLocationStereoscopic tmp;
                                    for(int i = 0; i < list2.size(); ++i) {
                                        tmp = (WmsLocationStereoscopic)list2.get(i);
                                        if (tmp.getGoodsCode() != null && !"".equals(tmp.getGoodsCode())) {
                                            goodsSet.add(tmp.getGoodsCode());
                                        }

                                        if (wmsMoveStereoscopic.getToLocationCode().equals(tmp.getLocationCode())) {
                                            if (i - 1 >= 0) {
                                                beforeGoodsCode = ((WmsLocationStereoscopic)list2.get(i - 1)).getGoodsCode();
                                            }

                                            if (list2.size() > i + 1) {
                                                afterGoodsCode = ((WmsLocationStereoscopic)list2.get(i + 1)).getGoodsCode();
                                            }
                                        }
                                    }

                                    if (goodsSet.size() > 2) {
                                        return this.fail("目的库位所在巷道混放商品数量大于2，不能进行移库！");
                                    }

                                    if (goodsSet.size() == 2) {
                                        if (!goodsSet.contains(moveGoodsCode)) {
                                            return this.fail("目的库位所在巷道混放商品数量等于2，且不包含当前移动商品，不能进行移库！");
                                        }

                                        if (!moveGoodsCode.equals(beforeGoodsCode) && !moveGoodsCode.equals(afterGoodsCode)) {
                                            return this.fail("目的库位所在巷道混放商品数量等于2，且包含当前移动商品，此时待移动商品必须同类挨着排放！");
                                        }
                                    } else if (goodsSet.size() == 1) {
                                        if (goodsSet.contains(moveGoodsCode)) {
                                            if (!moveGoodsCode.equals(beforeGoodsCode) && !moveGoodsCode.equals(afterGoodsCode)) {
                                                return this.fail("目的库位所在巷道只存放着当前移动商品，此时待移动商品必须同类挨着排放！");
                                            }
                                        } else if ((beforeGoodsCode == null || "".equals(beforeGoodsCode)) && (afterGoodsCode == null || "".equals(afterGoodsCode))) {
                                            return this.fail("目的库位所在巷道只存一种非当前移动商品，此时待移动商品必须与其挨着排放，不能存在间隔！");
                                        }
                                    }

                                    Iterator var33 = list2.iterator();

                                    while(var33.hasNext()) {
                                        tmp = (WmsLocationStereoscopic)var33.next();
                                        if (wmsMoveStereoscopic.getToLocationCode().equals(tmp.getLocationCode())) {
                                            if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                                                return this.fail("目的库位非可用状态，不能启动移库！");
                                            }

                                            if (tmp.getPalletCode() != null && !"".equals(tmp.getPalletCode())) {
                                                return this.fail("目的库位非可用状态，不能启动移库！");
                                            }
                                            break;
                                        }
                                    }

                                    var33 = list2.iterator();

                                    while(var33.hasNext()) {
                                        tmp = (WmsLocationStereoscopic)var33.next();
                                        if (wmsMoveStereoscopic.getToLocationCode().equals(tmp.getLocationCode())) {
                                            break;
                                        }

                                        if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                                            leftUnLocked = false;
                                            break;
                                        }
                                    }

                                    Collections.reverse(list2);
                                    var33 = list2.iterator();

                                    while(var33.hasNext()) {
                                        tmp = (WmsLocationStereoscopic)var33.next();
                                        if (wmsMoveStereoscopic.getToLocationCode().equals(tmp.getLocationCode())) {
                                            break;
                                        }

                                        if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                                            rightUnLocked = false;
                                            break;
                                        }
                                    }

                                    if (shelvesNumber != 1 && shelvesNumber != 21) {
                                        if (shelvesNumber != 4 && shelvesNumber != 20) {
                                            if (!rightUnLocked && !leftUnLocked) {
                                                return this.fail("目的库位的移库通道不通畅，不能进行移库！");
                                            }
                                        } else if (!leftUnLocked) {
                                            return this.fail("目的库位的移库通道不通畅，不能进行移库！");
                                        }
                                    } else if (!rightUnLocked) {
                                        return this.fail("目的库位的移库通道不通畅，不能进行移库！");
                                    }

                                    if (wmsMoveStereoscopic.getMoveNo() != null && !"".equals(wmsMoveStereoscopic.getMoveNo())) {
                                        WmsMoveStereoscopic updateOb = new WmsMoveStereoscopic();
                                        updateOb.setMoveNo(wmsMoveStereoscopic.getMoveNo());
                                        updateOb.setLastModifiedBy(wmsMoveStereoscopic.getCreateBy());
                                        updateOb.setGmtModified(now);
                                        this.wmsMoveStereoscopicMapper.delete(updateOb);
                                    } else {
                                        moveNo = this.wmsCommonService.getOrderNoByType("MS");
                                        wmsMoveStereoscopic.setMoveNo(moveNo);
                                    }

                                    wmsMoveStereoscopic.setMoveId(CommonUtils.getUUID());
                                    wmsMoveStereoscopic.setMoveStatus("1");
                                    wmsMoveStereoscopic.setAreaCode(moveWmsPallet.getAreaCode());
                                    wmsMoveStereoscopic.setPalletCode(moveWmsPallet.getPalletCode());
                                    wmsMoveStereoscopic.setGoodsCode(moveWmsPallet.getGoodsCode());
                                    wmsMoveStereoscopic.setGoodsName(moveWmsPallet.getGoodsName());
                                    wmsMoveStereoscopic.setBatchNo(moveWmsPallet.getBatchNo());
                                    wmsMoveStereoscopic.setAmount(moveWmsPallet.getAmount());
                                    wmsMoveStereoscopic.setGmtCreate(now);
                                    wmsMoveStereoscopic.setActiveFlag("1");
                                    this.wmsMoveStereoscopicMapper.create(wmsMoveStereoscopic);
                                    return this.success();
                                }

                                tmp = (WmsLocationStereoscopic)var28.next();
                            } while(tmp.getUseStatus() == null);
                        } while(!"1".equals(tmp.getUseStatus()) && !"2".equals(tmp.getUseStatus()) && !"4".equals(tmp.getUseStatus()));

                        return this.fail("目的库位所在巷道存在锁定、异常库位，不能进行移库！");
                    }

                    return this.fail("目的库位信息有误，不能启动移库！");
                }
            } else {
                return this.fail("源库位信息有误，不能启动移库！");
            }
        }
    }

    @Transactional
    public Resp locationLockYk(WmsMoveStereoscopic condition) {
        Date now = new Date();
        Resp resp = new Resp();
        String moveGoodsCode = "";
        WmsMoveStereoscopic wmsMoveStereoscopic = new WmsMoveStereoscopic();
        WmsMoveStereoscopic conditionSearch = new WmsMoveStereoscopic();
        conditionSearch.setMoveId(condition.getMoveId());
        conditionSearch.setActiveFlag("1");
        List<WmsMoveStereoscopic> wmsMoveStereoscopicList = this.wmsMoveStereoscopicMapper.queryByAny(conditionSearch);
        if (wmsMoveStereoscopicList != null && !wmsMoveStereoscopicList.isEmpty()) {
            wmsMoveStereoscopic = (WmsMoveStereoscopic)wmsMoveStereoscopicList.get(0);
        }

        String fromFloor = wmsMoveStereoscopic.getFromLocationCode().substring(0, 1);
        String toFloor = wmsMoveStereoscopic.getToLocationCode().substring(0, 1);
        if (!fromFloor.equals(toFloor)) {
            return this.fail("非同层库位间不能移库！");
        } else {
            WmsPallet moveWmsPallet = null;
            WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
            searchOb.setLocationCode(wmsMoveStereoscopic.getFromLocationCode());
            List<WmsLocationStereoscopic> list = this.wmsQXFLocationStereoscopicMapper.getTotalRowLocation(searchOb);
            if (list != null && !list.isEmpty()) {
                Boolean leftUnLocked = true;
                Boolean rightUnLocked = true;
                Integer shelvesNumber = ((WmsLocationStereoscopic)list.get(0)).getShelvesNumber();
                Integer layerNumber = ((WmsLocationStereoscopic)list.get(0)).getLayerNumber();
                Iterator var17 = list.iterator();

                WmsLocationStereoscopic tmp;
                while(var17.hasNext()) {
                    tmp = (WmsLocationStereoscopic)var17.next();
                    if (wmsMoveStereoscopic.getFromLocationCode().equals(tmp.getLocationCode())) {
                        if (tmp.getUseStatus() != null && !"3".equals(tmp.getUseStatus())) {
                            return this.fail("源库位非占用状态，不能启动移库！");
                        }

                        if (tmp.getLockBy() != null && !"".equals(tmp.getLockBy())) {
                            return this.fail("源库位上的托盘被锁定，不能启动移库！");
                        }

                        if (tmp.getGoodsCode() != null && !"".equals(tmp.getGoodsCode())) {
                            moveGoodsCode = tmp.getGoodsCode();
                            break;
                        }

                        return this.fail("源库位上的托盘信息有误，不能启动移库！");
                    }
                }

                var17 = list.iterator();

                while(var17.hasNext()) {
                    tmp = (WmsLocationStereoscopic)var17.next();
                    if (wmsMoveStereoscopic.getFromLocationCode().equals(tmp.getLocationCode())) {
                        break;
                    }

                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        leftUnLocked = false;
                        break;
                    }
                }

                Collections.reverse(list);
                var17 = list.iterator();

                while(var17.hasNext()) {
                    tmp = (WmsLocationStereoscopic)var17.next();
                    if (wmsMoveStereoscopic.getFromLocationCode().equals(tmp.getLocationCode())) {
                        break;
                    }

                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        rightUnLocked = false;
                        break;
                    }
                }

                if (shelvesNumber != 1 && shelvesNumber != 21) {
                    if (shelvesNumber != 4 && shelvesNumber != 20) {
                        if (!rightUnLocked && !leftUnLocked) {
                            return this.fail("源库位的移库通道不通畅，不能启动移库！");
                        }
                    } else if (!leftUnLocked) {
                        return this.fail("源库位的移库通道不通畅，不能启动移库！");
                    }
                } else if (!rightUnLocked) {
                    return this.fail("源库位的移库通道不通畅，不能启动移库！");
                }

                WmsLocationStereoscopic searchOb2 = new WmsLocationStereoscopic();
                searchOb2.setLocationCode(wmsMoveStereoscopic.getToLocationCode());
                List<WmsLocationStereoscopic> list2 = this.wmsQXFLocationStereoscopicMapper.getTotalRowLocation(searchOb2);
                if (list2 != null && !list2.isEmpty()) {
//                    Boolean leftUnLocked = true;
//                    Boolean rightUnLocked = true;
//                    Integer shelvesNumber = ((WmsLocationStereoscopic)list2.get(0)).getShelvesNumber();
//                    Integer layerNumber = ((WmsLocationStereoscopic)list2.get(0)).getLayerNumber();

                    leftUnLocked = true;
                    rightUnLocked = true;
                    shelvesNumber = ((WmsLocationStereoscopic)list2.get(0)).getShelvesNumber();
                    layerNumber = ((WmsLocationStereoscopic)list2.get(0)).getLayerNumber();
                    String beforeGoodsCode = null;
                    String afterGoodsCode = null;
                    HashSet<String> goodsSet = new HashSet();

                    //WmsLocationStereoscopic tmp;
                    for(int i = 0; i < list2.size(); ++i) {
                        tmp = (WmsLocationStereoscopic)list2.get(i);
                        if (tmp.getGoodsCode() != null && !"".equals(tmp.getGoodsCode())) {
                            goodsSet.add(tmp.getGoodsCode());
                        }

                        if (wmsMoveStereoscopic.getToLocationCode().equals(tmp.getLocationCode())) {
                            if (i - 1 >= 0) {
                                beforeGoodsCode = ((WmsLocationStereoscopic)list2.get(i - 1)).getGoodsCode();
                            }

                            if (list2.size() > i + 1) {
                                afterGoodsCode = ((WmsLocationStereoscopic)list2.get(i + 1)).getGoodsCode();
                            }
                        }
                    }

                    if (goodsSet.size() > 2) {
                        return this.fail("目的库位所在巷道混放商品数量大于2，不能启动移库！");
                    } else {
                        if (goodsSet.size() == 2) {
                            if (!goodsSet.contains(moveGoodsCode)) {
                                return this.fail("目的库位所在巷道混放商品数量等于2，且不包含当前移动商品，不能启动移库！");
                            }

                            if (!moveGoodsCode.equals(beforeGoodsCode) && !moveGoodsCode.equals(afterGoodsCode)) {
                                return this.fail("目的库位所在巷道混放商品数量等于2，且包含当前移动商品，此时待移动商品必须同类挨着排放！");
                            }
                        } else if (goodsSet.size() == 1) {
                            if (goodsSet.contains(moveGoodsCode)) {
                                if (!moveGoodsCode.equals(beforeGoodsCode) && !moveGoodsCode.equals(afterGoodsCode)) {
                                    return this.fail("目的库位所在巷道只存放着当前移动商品，此时待移动商品必须同类挨着排放！");
                                }
                            } else if ((beforeGoodsCode == null || "".equals(beforeGoodsCode)) && (afterGoodsCode == null || "".equals(afterGoodsCode))) {
                                return this.fail("目的库位所在巷道只存一种非当前移动商品，此时待移动商品必须与其挨着排放，不能存在间隔！");
                            }
                        }

                        Iterator var33 = list2.iterator();

                        while(var33.hasNext()) {
                            tmp = (WmsLocationStereoscopic)var33.next();
                            if (wmsMoveStereoscopic.getToLocationCode().equals(tmp.getLocationCode())) {
                                if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                                    return this.fail("目的库位非可用状态，不能启动移库！");
                                }

                                if (tmp.getPalletCode() != null && !"".equals(tmp.getPalletCode())) {
                                    return this.fail("目的库位非可用状态，不能启动移库！");
                                }
                                break;
                            }
                        }

                        var33 = list2.iterator();

                        while(var33.hasNext()) {
                            tmp = (WmsLocationStereoscopic)var33.next();
                            if (wmsMoveStereoscopic.getToLocationCode().equals(tmp.getLocationCode())) {
                                break;
                            }

                            if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                                leftUnLocked = false;
                                break;
                            }
                        }

                        Collections.reverse(list2);
                        var33 = list2.iterator();

                        while(var33.hasNext()) {
                            tmp = (WmsLocationStereoscopic)var33.next();
                            if (wmsMoveStereoscopic.getToLocationCode().equals(tmp.getLocationCode())) {
                                break;
                            }

                            if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                                rightUnLocked = false;
                                break;
                            }
                        }

                        if (shelvesNumber != 1 && shelvesNumber != 21) {
                            if (shelvesNumber != 4 && shelvesNumber != 20) {
                                if (!rightUnLocked && !leftUnLocked) {
                                    return this.fail("目的库位的移库通道不通畅，不能启动移库！");
                                }
                            } else if (!leftUnLocked) {
                                return this.fail("目的库位的移库通道不通畅，不能启动移库！");
                            }
                        } else if (!rightUnLocked) {
                            return this.fail("目的库位的移库通道不通畅，不能启动移库！");
                        }

                        List<String> outLocationList = new ArrayList();
                        outLocationList.add(wmsMoveStereoscopic.getFromLocationCode());
                        List<String> inLocationList = new ArrayList();
                        inLocationList.add(wmsMoveStereoscopic.getToLocationCode());
                        WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
                        updateOb.setLastModifiedBy("wcs-move");
                        updateOb.setGmtModified(now);
                        updateOb.setUseStatus("2");
                        updateOb.setLocationCodeList(outLocationList);
                        this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateOb);
                        updateOb.setUseStatus("1");
                        updateOb.setLocationCodeList(inLocationList);
                        this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateOb);
                        resp.setData(wmsMoveStereoscopic);
                        resp.setCode(RESULT.SUCCESS.code);
                        return resp;
                    }
                } else {
                    return this.fail("目的库位信息有误，不能启动移库！");
                }
            } else {
                return this.fail("源库位信息有误，不能启动移库！");
            }
        }
    }

    @Transactional
    public Resp reverseYk(WmsMoveStereoscopic wmsMoveStereoscopic) {
        List<String> outLocationList = new ArrayList();
        List<String> inLocationList = new ArrayList();
        outLocationList.add(wmsMoveStereoscopic.getFromLocationCode());
        inLocationList.add(wmsMoveStereoscopic.getToLocationCode());
        WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
        updateOb.setLastModifiedBy(wmsMoveStereoscopic.getCreateBy());
        updateOb.setGmtModified(new Date());
        updateOb.setUseStatus("3");
        updateOb.setLocationCodeList(outLocationList);
        this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateOb);
        updateOb.setUseStatus("0");
        updateOb.setLocationCodeList(inLocationList);
        this.wmsLocationStereoscopicMapper.updateByLocationCodeBatch(updateOb);
        return this.success();
    }

    private static enum StackerShelvesNum {
        STAVKER_1("1", new ArrayList<Integer>() {
            {
                this.add(1);
                this.add(2);
            }
        });

        private final String stackerNum;
        private final List<Integer> shelvesNum;

        private StackerShelvesNum(String stackerNum, List<Integer> shelvesNum) {
            this.stackerNum = stackerNum;
            this.shelvesNum = shelvesNum;
        }

        public static Boolean isSameStacker(Integer shelvesNum1, Integer shelvesNum2) {
            return true;
        }
    }
}
