//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business.impl;

import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsAddressRealRelaService;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsAddressRealRelaMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsAddressTransformMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsAddressRealRela;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsAddressTransform;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("wmsAddressRealRelaService")
public class WmsAddressRealRelaServiceImpl extends BaseService implements IWmsAddressRealRelaService {
    @Resource
    private WmsAddressRealRelaMapper wmsAddressRealRelaMapper;
    @Resource
    private WmsAddressTransformMapper wmsAddressTransformMapper;

    public WmsAddressRealRelaServiceImpl() {
    }

    @Transactional
    public Resp create(WmsAddressRealRela wmsAddressRealRela) {
        String addressType = wmsAddressRealRela.getAddressType();
        List<String> onlyList = new ArrayList();
        String[] addressTypeList = addressType.split(",");
        if (addressTypeList != null) {
            String[] var5 = addressTypeList;
            int var6 = addressTypeList.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String insertTmp = var5[var7];
                if (String.valueOf(TaskType.STRAIGHT_OUT.getTaskType()).equals(insertTmp) || String.valueOf(TaskType.SORT_OUT.getTaskType()).equals(insertTmp) || String.valueOf(TaskType.HAND_OUT.getTaskType()).equals(insertTmp)) {
                    onlyList.add(insertTmp);
                }

                if (onlyList.size() > 1) {
                    return this.fail("地址类型不能同时是分拣出库、直发出库、人工出库");
                }
            }
        }

        Integer id = wmsAddressRealRela.getId() == null ? 0 : wmsAddressRealRela.getId();
        WmsAddressRealRela yzOb;
        List yzList;
        if (id != 0) {
            yzOb = new WmsAddressRealRela();
            yzOb.setAddressCode(wmsAddressRealRela.getAddressCode());
            yzOb.setIdNotEqual(id);
            yzOb.setActiveFlag("1");
            yzList = this.wmsAddressRealRelaMapper.queryByAny(yzOb);
            if (yzList != null && !yzList.isEmpty()) {
                return this.fail("当前地址编码已经存在");
            }

            yzOb = new WmsAddressRealRela();
            yzOb.setRealAddress(wmsAddressRealRela.getRealAddress());
            yzOb.setIdNotEqual(id);
            yzOb.setActiveFlag("1");
            yzList = this.wmsAddressRealRelaMapper.queryByAny(yzOb);
            if (yzList != null && !yzList.isEmpty()) {
                return this.fail("当前实际物理地址已经存在");
            }

            wmsAddressRealRela.setLastModifiedBy(wmsAddressRealRela.getCreateBy());
            wmsAddressRealRela.setGmtModified(wmsAddressRealRela.getGmtCreate());
            wmsAddressRealRela.setCreateBy((String)null);
            wmsAddressRealRela.setGmtCreate((Date)null);
            this.wmsAddressRealRelaMapper.updateBySelect(wmsAddressRealRela);
            WmsAddressTransform deleteOb = new WmsAddressTransform();
            deleteOb.setRelaId(id);
            this.wmsAddressTransformMapper.deleteByRelaId(deleteOb);
            List<WmsAddressTransform> insertList = new ArrayList();
            if (addressTypeList != null) {
                String[] var10 = addressTypeList;
                int var11 = addressTypeList.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    String insertTmp = var10[var12];
                    WmsAddressTransform insetOb = new WmsAddressTransform();
                    insetOb.setAddressType(insertTmp);
                    insetOb.setRelaId(id);
                    insertList.add(insetOb);
                }
            }

            this.wmsAddressTransformMapper.batchInsert(insertList);
        } else {
            yzOb = new WmsAddressRealRela();
            yzOb.setAddressCode(wmsAddressRealRela.getAddressCode());
            yzOb.setActiveFlag("1");
            yzList = this.wmsAddressRealRelaMapper.queryByAny(yzOb);
            if (yzList != null && !yzList.isEmpty()) {
                return this.fail("当前地址编码已经存在");
            }

            yzOb = new WmsAddressRealRela();
            yzOb.setRealAddress(wmsAddressRealRela.getRealAddress());
            yzOb.setActiveFlag("1");
            yzList = this.wmsAddressRealRelaMapper.queryByAny(yzOb);
            if (yzList != null && !yzList.isEmpty()) {
                return this.fail("当前实际物理地址已经存在");
            }

            this.wmsAddressRealRelaMapper.create(wmsAddressRealRela);
            WmsAddressRealRela tmp = new WmsAddressRealRela();
            tmp.setAddressCode(wmsAddressRealRela.getAddressCode());
            tmp.setActiveFlag("1");
            List<WmsAddressRealRela> list1 = this.wmsAddressRealRelaMapper.queryByAny(tmp);
            if (list1 != null && !list1.isEmpty()) {
                WmsAddressRealRela ob = (WmsAddressRealRela)list1.get(0);
                List<WmsAddressTransform> insertList = new ArrayList();
                if (addressTypeList != null) {
                    String[] var25 = addressTypeList;
                    int var26 = addressTypeList.length;

                    for(int var27 = 0; var27 < var26; ++var27) {
                        String insertTmp = var25[var27];
                        WmsAddressTransform insetOb = new WmsAddressTransform();
                        insetOb.setAddressType(insertTmp);
                        insetOb.setRelaId(ob.getId());
                        insertList.add(insetOb);
                    }
                }

                this.wmsAddressTransformMapper.batchInsert(insertList);
            }
        }

        return this.success();
    }

    @Transactional
    public Resp delete(WmsAddressRealRela wmsAddressRealRela) {
        this.wmsAddressRealRelaMapper.deleteByKey(wmsAddressRealRela);
        WmsAddressTransform tmp = new WmsAddressTransform();
        tmp.setRelaId(wmsAddressRealRela.getId());
        this.wmsAddressTransformMapper.deleteByRelaId(tmp);
        return this.success();
    }

    public Pager<WmsAddressRealRela> findListByCondition(int page, int rows, WmsAddressRealRela condition) {
        Pager<WmsAddressRealRela> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsAddressRealRelaMapper.queryCount(condition);
        List<WmsAddressRealRela> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsAddressRealRelaMapper.queryList(pager, condition);
            if (records != null && !((List)records).isEmpty()) {
                Iterator var8 = ((List)records).iterator();

                label34:
                while(true) {
                    WmsAddressRealRela tmp;
                    do {
                        do {
                            if (!var8.hasNext()) {
                                break label34;
                            }

                            tmp = (WmsAddressRealRela)var8.next();
                        } while(tmp.getAddressType() == null);
                    } while("".equals(tmp.getAddressType()));

                    String[] addressTypeList = tmp.getAddressType().split(",");
                    List<String> transforList = new ArrayList();
                    String[] var12 = addressTypeList;
                    int var13 = addressTypeList.length;

                    for(int var14 = 0; var14 < var13; ++var14) {
                        String addressType = var12[var14];
                        transforList.add(TaskType.getTaskTypeDesc(Long.valueOf(addressType)));
                    }

                    tmp.setAddressType(String.join(",", transforList));
                }
            }
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsAddressRealRela findById(String id) {
        return (WmsAddressRealRela)this.wmsAddressRealRelaMapper.queryById(id);
    }

    public Resp update(WmsAddressRealRela wmsAddressRealRela) {
        this.wmsAddressRealRelaMapper.updateBySelect(wmsAddressRealRela);
        return this.success();
    }

    public List<WmsAddressRealRela> queryByAny(WmsAddressRealRela condition) {
        return this.wmsAddressRealRelaMapper.queryByAny(condition);
    }

    public List<WmsAddressRealRela> queryOutAddress(WmsAddressRealRela condition) {
        return this.wmsAddressRealRelaMapper.queryOutAddress(condition);
    }
    /**
     *功能描述:  查询 出库口（只有一个出货口）
     * @author zhangxin
     * @date 2022/8/1
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsAddressRealRela>
     */
    public List<WmsAddressRealRela> queryHBOutAddress(WmsAddressRealRela condition) {
        return this.wmsAddressRealRelaMapper.queryHBOutAddress(condition);
    }
}
