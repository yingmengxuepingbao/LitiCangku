package com.penghaisoft.pda.storage.service.impl;

import com.penghaisoft.pda.basic.model.BaseDictItem;
import com.penghaisoft.pda.basic.service.IBaseDictItemService;
import com.penghaisoft.pda.common.Constant;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.storage.dao.WmsGoodsMapper;
import com.penghaisoft.pda.storage.dao.WmsLocationStereoscopicMapper;
import com.penghaisoft.pda.storage.dao.WmsPalletMapper;
import com.penghaisoft.pda.storage.model.WmsGoods;
import com.penghaisoft.pda.storage.model.WmsLocationStereoscopic;
import com.penghaisoft.pda.storage.model.WmsPallet;
import com.penghaisoft.pda.storage.service.WmsLocationStereoscopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * <p>
 * 业务逻辑实现类
 * </p>
 *
 * @author
 * @createDate
 **/
@Slf4j
@Service("wmsLocationStereoscopicService")
@ConditionalOnProperty(prefix = "wcs.config", name = "applyfactory", havingValue = "DEFAULT")
public class WmsLocationStereoscopicServiceImpl implements WmsLocationStereoscopicService {
    @Resource
    private WmsLocationStereoscopicMapper wmsLocationStereoscopicMapper;

    @Autowired
    private WmsPalletMapper wmsPalletMapper;

    @Resource
    private WmsGoodsMapper wmsGoodsMapper;

    @Override
    public List<WmsPallet> queryWmsPallet(String palletCode) {
        return wmsPalletMapper.selectByCode(palletCode);
    }

    @Autowired
    private IBaseDictItemService baseDictItemService;

    /**
     * @return
     * @Description 异常口入库，如果托盘被锁定的库位上的托盘就是自己可以直接入到该库位
     * @Author luot
     * @Date 2020/3/12 11:20
     * @Param
     **/
    @Override
    @Transactional
    public String queryRecommendLocationCodeCheck(String palletCode) {
        String locationCode = "";

        WmsPallet pallet = new WmsPallet();
        List<WmsPallet> pallets = wmsPalletMapper.selectByCode(palletCode);
        if (pallets != null && !pallets.isEmpty()) {
            pallet = pallets.get(0);
            if (pallet.getLockBy() != null && !"".equals(pallet.getLockBy())) {
                WmsLocationStereoscopic tmp = wmsLocationStereoscopicMapper.selectByLocationCode(pallet.getLocationCode());
                if (tmp != null && palletCode.equals(tmp.getPalletCode())) {
                    locationCode = pallet.getLocationCode();

                    WmsLocationStereoscopic record = new WmsLocationStereoscopic();
                    record.setLocationCode(locationCode);
//                    使用状态 0可用1入库中2出库中3占用4异常
                    record.setUseStatus("1");
//                    之前的状态记录住，防止回滚
                    record.setUserDefined1(tmp.getUseStatus());
                    record.setLastModifiedBy("pda_query_in_location");
                    record.setGmtModified(new Date());
                    wmsLocationStereoscopicMapper.updateByLocationCode(record);
                }
            }
        }

        return locationCode;
    }

    /**
     * @return
     * @Description 根据商品编码、批次 获取推荐库位， 并将库位状态锁定 改成use_status=1 入库中
     * 【库位属性先不考虑，若加 库位表推荐的时候加个location_attr 筛选；商品种类也先不考虑，若加，可以高于某个重量，将放到层数layer_number大于几的库位上】
     * 立库库位表按 列号升序、层号升序、货架号升序 排序【这样就是从入库口这边横向取完，纵向深入再横向取】
     * @Author luot
     * @Date 2020/2/16 12:44
     * @Param
     **/
    @Override
    @Transactional
    public Resp queryRecommendLocationCode(String palletCode) {
        Resp resp = new Resp();
        String locationCode = "";

//        为保证不把相同库位推荐给不同的任务，库位表先锁住【可能存在多条生产线同时下线相同型号的条码，并且同时扫码】
//        WmsLocationStereoscopic lockOb = new WmsLocationStereoscopic();
//        lockOb.setLocationCode("virtualLocked");
//        wmsLocationStereoscopicMapper.updateByLocationCode(lockOb);

        String goodsCode = "";
        String batchNo = "";
        WmsPallet pallet = new WmsPallet();
        List<WmsPallet> pallets = wmsPalletMapper.selectByCode(palletCode);
        if (pallets == null || pallets.isEmpty()) {
            resp.setCode("0");
            resp.setMessage("查询不到该托盘");
            return resp;
        } else {
            pallet = pallets.get(0);
            goodsCode = pallet.getGoodsCode();
            batchNo = pallet.getBatchNo();
            if (pallet.getLockBy() != null && !"".equals(pallet.getLockBy())) {
                resp.setCode("0");
                resp.setMessage("该托盘被锁定");
                return resp;
            }
        }

//        包材、虚拟托盘标志
        Boolean bvFlag = false;
        List<WmsGoods> wmsGoodList = wmsGoodsMapper.selectByGoodsCode(pallet.getGoodsCode());
        if (wmsGoodList != null && !wmsGoodList.isEmpty()) {
            WmsGoods tmp = wmsGoodList.get(0);
//            1成品0包材3托盘
            if (tmp.getGoodsType() != null && ("0".equals(tmp.getGoodsType()) || "3".equals(tmp.getGoodsType()))) {
                bvFlag = true;
            }
        }

        log.info("是否包材或虚拟托盘={}", bvFlag);

        long t1 = System.currentTimeMillis();
        List<WmsLocationStereoscopic> allLocationList = wmsLocationStereoscopicMapper.getAllLocationInfo();
        long t2 = System.currentTimeMillis();
        log.info("##################推荐库位算法SQL查询耗时：" + (t2 - t1) + "ms！#########################");

        long t3 = System.currentTimeMillis();
        List<WmsLocationStereoscopic> virtualPalletRangeList = new ArrayList<WmsLocationStereoscopic>();
        List<WmsLocationStereoscopic> normalGoodsRangeList = new ArrayList<WmsLocationStereoscopic>();
        List<WmsLocationStereoscopic> normalGoodsBeiHuoRangeList = new ArrayList<WmsLocationStereoscopic>();

        Integer vpLayerNum = 5;
        List<BaseDictItem> dictItems = baseDictItemService.getDictTypeByCode("virtual_pallet_layer_num");
        if (!dictItems.isEmpty()){
            BaseDictItem dicTmp = dictItems.get(0);
            if(dicTmp.getDicItemCode() != null && !"".equals(dicTmp.getDicItemCode())){
                vpLayerNum = Integer.parseInt(dicTmp.getDicItemCode());
            }
        }

        log.info("######虚拟托盘在一楼3区的" + vpLayerNum + "层及" + vpLayerNum + "层以下#######");

        for (WmsLocationStereoscopic tmp : allLocationList) {
//            虚拟托盘的范围
            if (1 == tmp.getFloorNumber() && 3 == tmp.getShelvesNumber() && vpLayerNum >= tmp.getLayerNumber()) {
                virtualPalletRangeList.add(tmp);
            } else {
                if (tmp.getShelvesNumber() == 4) {
                    normalGoodsBeiHuoRangeList.add(tmp);
                } else {
                    normalGoodsRangeList.add(tmp);
                }
            }
        }

        if (bvFlag) {
            locationCode = getReVirtualPalletLocation(virtualPalletRangeList);
        }

        if ("".equals(locationCode)) {
//            确定楼层
            Integer floorNumber = 1;

            List<Integer> list = new ArrayList<Integer>();
            list.add(floorNumber);

            for (Integer tmp : list) {
                locationCode = getRecommendLocation(normalGoodsRangeList, goodsCode, batchNo, tmp);
                if (!"".equals(locationCode)) {
                    break;
                }
            }
            if ("".equals(locationCode)) {
                for (Integer tmp : list) {
                    locationCode = getRecommendLocation(normalGoodsBeiHuoRangeList, goodsCode, batchNo, tmp);
                    if (!"".equals(locationCode)) {
                        break;
                    }
                }
            }
        }

        long t4 = System.currentTimeMillis();

        if ("".equals(locationCode)) {
            resp.setCode("0");
            resp.setMessage("没有找到合适的库位！");
            return resp;
        }

        log.info("##################推荐库位是：" + locationCode + "#########################");
        log.info("##################内存库位遍历查找耗时：" + (t4 - t3) + "ms！#########################");

        WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
        updateOb.setLocationCode(locationCode);
//        使用状态 0可用1入库中2出库中3占用4异常，针对立库，只要是出入库开始但未完成都属于使用中
        updateOb.setUseStatus("1");
        updateOb.setLastModifiedBy("pda_query_in_location");
        updateOb.setGmtModified(new Date());
        wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);

        resp.setCode("1");
        resp.setData(locationCode);
        return resp;
    }

    /**
     * @return
     * @Description 立体库入库时，推荐库位状态回滚成初始状态0可用
     * @Author luot
     * @Date 2020/2/20 19:36
     * @Param
     **/
    @Override
    public Resp revertLocationStatus0(String locationCode) {
        Resp resp = new Resp();
        WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
        updateOb.setLocationCode(locationCode);
//        使用状态 0可用1入库中2出库中3占用4异常，针对立库，只要是出入库开始但未完成都属于使用中
        updateOb.setUseStatus("0");
        updateOb.setLastModifiedBy("pda_query_in_location_revert");
        updateOb.setGmtModified(new Date());
        wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);

        resp.setCode("0");
        return resp;
    }

    @Override
    @Transactional
    public Resp revertLocationStatus0New(String locationCode, String palletId) {
        Resp resp = new Resp();
        WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
        updateOb.setLocationCode(locationCode);
//        使用状态 0可用1入库中2出库中3占用4异常，针对立库，只要是出入库开始但未完成都属于使用中
        updateOb.setUseStatus("0");
        updateOb.setLastModifiedBy("pda_query_in_location_revert");
        updateOb.setGmtModified(new Date());
        wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);

        wmsPalletMapper.deleteByPrimaryKey(palletId);

        resp.setCode("0");
        return resp;
    }

    /**
     * @return
     * @Description 推荐虚拟托盘库位
     * @Author luot
     * @Date 2020/7/24 13:27
     * @Param
     **/
    public String getReVirtualPalletLocation(List<WmsLocationStereoscopic> locationList) {
        String recommendLocation = "";

        List<List<WmsLocationStereoscopic>> allList = new ArrayList<List<WmsLocationStereoscopic>>();

        List<List<WmsLocationStereoscopic>> notContainLockList = new ArrayList<List<WmsLocationStereoscopic>>();
        List<List<WmsLocationStereoscopic>> emptyList = new ArrayList<List<WmsLocationStereoscopic>>();
        List<List<WmsLocationStereoscopic>> containLockList = new ArrayList<List<WmsLocationStereoscopic>>();

        Integer tmpFloorNum = 0;//  楼层
        Integer tmpShelvelsNum = 0;//  分区号
        Integer tmpLayerNum = 0;//  行数
        List<WmsLocationStereoscopic> tranList = new ArrayList<WmsLocationStereoscopic>();
        for (WmsLocationStereoscopic tmp : locationList) {
            if (tmpFloorNum != tmp.getFloorNumber() || tmpShelvelsNum != tmp.getShelvesNumber() || tmpLayerNum != tmp.getLayerNumber()) {
                tranList = new ArrayList<WmsLocationStereoscopic>();
                tmpFloorNum = tmp.getFloorNumber();
                tmpShelvelsNum = tmp.getShelvesNumber();
                tmpLayerNum = tmp.getLayerNumber();
            }

            tranList.add(tmp);
            if (!allList.contains(tranList)) {
                allList.add(tranList);
            }
        }

        for (List<WmsLocationStereoscopic> tmpList : allList) {
            Boolean containLockFlag = false;
            Boolean flag_3 = false;
            for (WmsLocationStereoscopic tmp : tmpList) {
//                使用状态 0可用1入库中2出库中3占用4异常
                if (tmp.getUseStatus() != null && ("1".equals(tmp.getUseStatus()) || "2".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus()))) {
                    containLockFlag = true;
                } else if (tmp.getUseStatus() != null && "3".equals(tmp.getUseStatus())) {
                    flag_3 = true;
                }
            }
            if (containLockFlag) {
                containLockList.add(tmpList);
            } else {
                if (flag_3) {
                    notContainLockList.add(tmpList);
                } else {
                    emptyList.add(tmpList);
                }
            }
        }

//        1.先从不包含锁定状态的巷道推荐入库库位
        for (List<WmsLocationStereoscopic> tmpList : notContainLockList) {
            for (int i = 0; i < tmpList.size() - 1; i++) {
                WmsLocationStereoscopic tmp = tmpList.get(i);
                if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                    break;
                }
                WmsLocationStereoscopic after = tmpList.get(i + 1);
                if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                    recommendLocation = tmp.getLocationCode();
                    break;
                }
            }
            if (!"".equals(recommendLocation)) {
                break;
            }
            Collections.reverse(tmpList);
            for (int i = 0; i < tmpList.size() - 1; i++) {
                WmsLocationStereoscopic tmp = tmpList.get(i);
                if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                    break;
                }
                WmsLocationStereoscopic after = tmpList.get(i + 1);
                if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                    recommendLocation = tmp.getLocationCode();
                    break;
                }
            }
            if (!"".equals(recommendLocation)) {
                break;
            }
        }

//        2.从全部为空的巷道推荐入库库位
        if ("".equals(recommendLocation)) {
            if (!emptyList.isEmpty()) {
                recommendLocation = emptyList.get(0).get(emptyList.get(0).size() / 2).getLocationCode();
            }
        }
//        3.从包含锁定状态的巷道推荐入库库位
        if ("".equals(recommendLocation)) {
            for (List<WmsLocationStereoscopic> tmpList : containLockList) {
                for (int i = 0; i < tmpList.size() - 1; i++) {
                    WmsLocationStereoscopic tmp = tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }
                    WmsLocationStereoscopic after = tmpList.get(i + 1);
                    if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                        recommendLocation = tmp.getLocationCode();
                        break;
                    }
                }
                if (!"".equals(recommendLocation)) {
                    break;
                }
                Collections.reverse(tmpList);
                for (int i = 0; i < tmpList.size() - 1; i++) {
                    WmsLocationStereoscopic tmp = tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }
                    WmsLocationStereoscopic after = tmpList.get(i + 1);
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

    /**
     * @return
     * @Description 托盘入库 从某一楼层 推荐库位
     * @Author luot
     * @Date 2020/7/24 16:45
     * @Param
     **/
    public String getRecommendLocation(List<WmsLocationStereoscopic> locationList, String goodsCode, String batchNo, Integer floorNum) {
        String recommendLocation = "";

        List<List<WmsLocationStereoscopic>> allList = new ArrayList<List<WmsLocationStereoscopic>>();

        List<List<WmsLocationStereoscopic>> notContainLockList = new ArrayList<List<WmsLocationStereoscopic>>();
        List<List<WmsLocationStereoscopic>> emptyList = new ArrayList<List<WmsLocationStereoscopic>>();
        List<List<WmsLocationStereoscopic>> containLockList = new ArrayList<List<WmsLocationStereoscopic>>();

        List<WmsLocationStereoscopic> floorLocationList = new ArrayList<WmsLocationStereoscopic>();
        for (WmsLocationStereoscopic tmp : locationList) {
            if (floorNum == tmp.getFloorNumber()) {
                floorLocationList.add(tmp);
            }
        }

        Integer tmpShelvelsNum = 0;//  分区号
        Integer tmpLayerNum = 0;//  行数
        List<WmsLocationStereoscopic> tranList = new ArrayList<WmsLocationStereoscopic>();
        for (WmsLocationStereoscopic tmp : floorLocationList) {
            if (tmpShelvelsNum != tmp.getShelvesNumber() || tmpLayerNum != tmp.getLayerNumber()) {
                tranList = new ArrayList<WmsLocationStereoscopic>();
                tmpShelvelsNum = tmp.getShelvesNumber();
                tmpLayerNum = tmp.getLayerNumber();
            }

            tranList.add(tmp);
            if (!allList.contains(tranList)) {
                allList.add(tranList);
            }
        }

        for (List<WmsLocationStereoscopic> tmpList : allList) {
            Boolean containLockFlag = false;
            Boolean flag_3 = false;
            HashSet<String> goodsCodeSet = new HashSet<String>();
            for (WmsLocationStereoscopic tmp : tmpList) {
//                使用状态 0可用1入库中2出库中3占用4异常
                if (tmp.getUseStatus() != null && ("1".equals(tmp.getUseStatus()) || "2".equals(tmp.getUseStatus()) || "4".equals(tmp.getUseStatus()))) {
                    containLockFlag = true;
                } else if (tmp.getUseStatus() != null && "3".equals(tmp.getUseStatus())) {
                    flag_3 = true;
                }

                if (tmp.getGoodsCode() != null && !"".equals(tmp.getGoodsCode())) {
                    String tmpGoodsCode = tmp.getGoodsCode();
                    if (tmp.getBatchNo() != null && !"".equals(tmp.getBatchNo())) {
                        tmpGoodsCode = tmpGoodsCode + tmp.getBatchNo();
                    }
                    goodsCodeSet.add(tmpGoodsCode);
                }
            }
            if (goodsCodeSet.size() > 2) {
                continue;
            }
            if (containLockFlag) {
                containLockList.add(tmpList);
            } else {
                if (flag_3) {
                    notContainLockList.add(tmpList);
                } else {
                    emptyList.add(tmpList);
                }
            }
        }

//        1.先从不包含锁定状态的巷道推荐入库库位
        recommendLocation = getLocation(notContainLockList, goodsCode, batchNo);

//        2.从全部为空的巷道推荐入库库位
        if ("".equals(recommendLocation)) {
            if (!emptyList.isEmpty()) {
                Integer shelvelsNum = emptyList.get(0).get(0).getShelvesNumber();
                if (shelvelsNum == 4) {
//                    备货区只能放最里面
                    recommendLocation = emptyList.get(0).get(emptyList.get(0).size() - 1).getLocationCode();
                } else if (shelvelsNum == 20) {
                    recommendLocation = emptyList.get(0).get(emptyList.get(0).size() - 1).getLocationCode();
                } else if (shelvelsNum == 21) {
                    recommendLocation = emptyList.get(0).get(0).getLocationCode();
                } else if (shelvelsNum == 1) {
                    recommendLocation = emptyList.get(0).get(0).getLocationCode();
                } else {
                    recommendLocation = emptyList.get(0).get(emptyList.get(0).size() / 2).getLocationCode();
                }
            }
        }
//        3.从包含锁定状态的巷道推荐入库库位
        if ("".equals(recommendLocation)) {
            recommendLocation = getLocation(containLockList, goodsCode, batchNo);
        }

        return recommendLocation;
    }

    /**
     * @return
     * @Description 从指定范围的库位找推荐库位
     * @Author luot
     * @Date 2020/7/24 17:21
     * @Param
     **/
    public String getLocation(List<List<WmsLocationStereoscopic>> list, String goodsCode, String batchNo) {
        String recommendLocation = "";
        for (List<WmsLocationStereoscopic> tmpList : list) {
            Integer shelvelsNum = tmpList.get(0).getShelvesNumber();
            if (shelvelsNum == 1 || shelvelsNum == 21) {
                Collections.reverse(tmpList);
                for (int i = 0; i < tmpList.size() - 1; i++) {
                    WmsLocationStereoscopic tmp = tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }
                    WmsLocationStereoscopic after = tmpList.get(i + 1);
                    if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                        if (Constant.StereoscopicInfo.VIRTUAL_PALLET_GOODS_CODE.equals(goodsCode)) {
                            if (after.getGoodsCode() != null && goodsCode.equals(after.getGoodsCode())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        } else {
                            if (after.getGoodsCode() != null && goodsCode.equals(after.getGoodsCode()) && after.getBatchNo() != null && batchNo.equals(after.getBatchNo())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        }
                    }
                }
                if (!"".equals(recommendLocation)) {
                    break;
                }
            } else if (shelvelsNum == 4 || shelvelsNum == 20) {
                for (int i = 0; i < tmpList.size() - 1; i++) {
                    WmsLocationStereoscopic tmp = tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }
                    WmsLocationStereoscopic after = tmpList.get(i + 1);
                    if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                        if (Constant.StereoscopicInfo.VIRTUAL_PALLET_GOODS_CODE.equals(goodsCode)) {
                            if (after.getGoodsCode() != null && goodsCode.equals(after.getGoodsCode())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        } else {
                            if (after.getGoodsCode() != null && goodsCode.equals(after.getGoodsCode()) && after.getBatchNo() != null && batchNo.equals(after.getBatchNo())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        }
                    }
                }
                if (!"".equals(recommendLocation)) {
                    break;
                }
            } else {
                for (int i = 0; i < tmpList.size() - 1; i++) {
                    WmsLocationStereoscopic tmp = tmpList.get(i);
                    if (tmp.getLayerNumber() <= 3 && shelvelsNum == 2) {
                        break;
                    }
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }
                    WmsLocationStereoscopic after = tmpList.get(i + 1);
                    if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                        if (Constant.StereoscopicInfo.VIRTUAL_PALLET_GOODS_CODE.equals(goodsCode)) {
                            if (after.getGoodsCode() != null && goodsCode.equals(after.getGoodsCode())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        } else {
                            if (after.getGoodsCode() != null && goodsCode.equals(after.getGoodsCode()) && after.getBatchNo() != null && batchNo.equals(after.getBatchNo())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        }
                    }
                }
                if (!"".equals(recommendLocation)) {
                    break;
                }
                Collections.reverse(tmpList);
                for (int i = 0; i < tmpList.size() - 1; i++) {
                    WmsLocationStereoscopic tmp = tmpList.get(i);
                    if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                        break;
                    }
                    WmsLocationStereoscopic after = tmpList.get(i + 1);
                    if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                        if (Constant.StereoscopicInfo.VIRTUAL_PALLET_GOODS_CODE.equals(goodsCode)) {
                            if (after.getGoodsCode() != null && goodsCode.equals(after.getGoodsCode())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        } else {
                            if (after.getGoodsCode() != null && goodsCode.equals(after.getGoodsCode()) && after.getBatchNo() != null && batchNo.equals(after.getBatchNo())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        }
                    }
                }
                if (!"".equals(recommendLocation)) {
                    break;
                }
            }
        }

//        放到非当前商品库位的旁边
        if ("".equals(recommendLocation)) {
            for (List<WmsLocationStereoscopic> tmpList : list) {
                Integer shelvelsNum = tmpList.get(0).getShelvesNumber();
//                if (shelvelsNum == 2 || shelvelsNum == 3) {
                if (shelvelsNum == 3) {
                    HashSet<String> goodsCodeSet = new HashSet<String>();
                    for (WmsLocationStereoscopic tmp : tmpList) {
                        if (tmp.getLayerNumber() <= 3 && shelvelsNum == 2) {
                            break;
                        }

                        if (tmp.getGoodsCode() != null && !"".equals(tmp.getGoodsCode())) {
                            String tmpGoodsCode = tmp.getGoodsCode();
                            if (tmp.getBatchNo() != null && !"".equals(tmp.getBatchNo())) {
                                tmpGoodsCode = tmpGoodsCode + tmp.getBatchNo();
                            }
                            goodsCodeSet.add(tmpGoodsCode);
                        }
                    }

                    if (goodsCodeSet.size() == 1) {
                        for (int i = 0; i < tmpList.size() - 1; i++) {
                            WmsLocationStereoscopic tmp = tmpList.get(i);
                            if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                                break;
                            }
                            WmsLocationStereoscopic after = tmpList.get(i + 1);
                            if (after.getUseStatus() != null && "3".equals(after.getUseStatus())) {
                                recommendLocation = tmp.getLocationCode();
                                break;
                            }
                        }
                        if (!"".equals(recommendLocation)) {
                            break;
                        }
                        Collections.reverse(tmpList);
                        for (int i = 0; i < tmpList.size() - 1; i++) {
                            WmsLocationStereoscopic tmp = tmpList.get(i);
                            if (tmp.getUseStatus() != null && !"0".equals(tmp.getUseStatus())) {
                                break;
                            }
                            WmsLocationStereoscopic after = tmpList.get(i + 1);
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
            }
        }

        return recommendLocation;
    }
}
