//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business.impl;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsLocationStereoscopicService;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsAddressRealRelaMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsLocationStereoscopicMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicDeailMapper;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsTaskExecutionLogMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wmsLocationStereoscopicService")
public class WmsLocationStereoscopicServiceImpl extends BaseService implements IWmsLocationStereoscopicService {
    @Resource
    private WmsLocationStereoscopicMapper wmsLocationStereoscopicMapper;
    @Resource
    private WmsPalletMapper wmsPalletMapper;
    @Resource
    private WmsTaskExecutionLogMapper wmsTaskExecutionLogMapper;
    @Resource
    private WmsAddressRealRelaMapper wmsAddressRealRelaMapper;
    @Resource
    private WmsOrderOutStereoscopicMapper wmsOrderOutStereoscopicMapper;
    @Resource
    private WmsOrderOutStereoscopicDeailMapper wmsOrderOutStereoscopicDeailMapper;
    @Autowired
    private IWmsCommonService wmsCommonService;

    public WmsLocationStereoscopicServiceImpl() {
    }

    public Resp create(WmsLocationStereoscopic wmsLocationStereoscopic) {
        this.wmsLocationStereoscopicMapper.create(wmsLocationStereoscopic);
        return this.success();
    }

    public Resp delete(WmsLocationStereoscopic wmsLocationStereoscopic) {
        this.wmsLocationStereoscopicMapper.delete(wmsLocationStereoscopic);
        return this.success();
    }

    public Pager<WmsLocationStereoscopic> findListByCondition(int page, int rows, WmsLocationStereoscopic condition) {
        Pager<WmsLocationStereoscopic> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsLocationStereoscopicMapper.queryCount(condition);
        List<WmsLocationStereoscopic> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsLocationStereoscopicMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsLocationStereoscopic findById(String id) {
        return (WmsLocationStereoscopic)this.wmsLocationStereoscopicMapper.queryById(id);
    }

    public List<WmsLocationStereoscopic> queryByAny(WmsLocationStereoscopic condition) {
        return this.wmsLocationStereoscopicMapper.queryByAny(condition);
    }

    public WmsLocationStereoscopic getUseAbleAmount(WmsLocationStereoscopic condition) {
        return this.wmsLocationStereoscopicMapper.getUseAbleAmount(condition);
    }
    /**
     *功能描述: 获取商品的可用重量 项目
     * @params
     * @return com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic
     */
    public WmsLocationStereoscopic getHBUseAbleAmount(WmsLocationStereoscopic condition) {
        return this.wmsLocationStereoscopicMapper.getHBUseAbleAmount(condition);
    }

    public Resp update(WmsLocationStereoscopic wmsLocationStereoscopic) {
        this.wmsLocationStereoscopicMapper.updateBySelect(wmsLocationStereoscopic);
        return this.success();
    }

    public List<BaseDictItem> getlocationCodeAll() {
        WmsLocationStereoscopic locationStereoscopic = new WmsLocationStereoscopic();
        List<WmsLocationStereoscopic> records = this.wmsLocationStereoscopicMapper.queryByAny(locationStereoscopic);
        List<BaseDictItem> list = new ArrayList();
        Iterator var4 = records.iterator();

        while(var4.hasNext()) {
            WmsLocationStereoscopic location = (WmsLocationStereoscopic)var4.next();
            BaseDictItem item = new BaseDictItem();
            item.setDicItemCode(location.getLocationCode());
            item.setDicItemName(location.getLocationDesc());
            list.add(item);
        }

        return list;
    }

    public Resp updateByLocationCode(WmsLocationStereoscopic wmsLocationStereoscopic) {
        this.wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopic);
        return this.success();
    }

    public List<WmsLocationStereoscopic> queryBatch(List<WmsLocationStereoscopic> dataList) {
        return this.wmsLocationStereoscopicMapper.queryBatch(dataList);
    }
    /**
     *功能描述: 库位批量上传
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods>
     */
    public Resp batchInsertLocation(List<WmsLocationStereoscopic> insertList) {
        //批量插入，因为sqlserver参数限制2100个。
        if(insertList!=null &&insertList.size()>0){
            if(insertList.size()<80) {
                this.wmsLocationStereoscopicMapper.batchInsert(insertList);
            }else {
                // 每次插入数据库的数据量
                int preInsertDataCount = 80;
                // 可遍历的插入数据库的次数
                int insertSqlCount = 0;
                // 总数据条数
                int totalDataCount=insertList.size();
                if(totalDataCount % preInsertDataCount==0){
                    insertSqlCount=totalDataCount/preInsertDataCount;
                }else
                {
                    insertSqlCount=totalDataCount/preInsertDataCount+1;
                }
                for (int i = 0; i < insertSqlCount; i++) {
                    int startNumber = i*preInsertDataCount;
                    int endUnmber=(i+1)*preInsertDataCount;
                    if(endUnmber>totalDataCount){
                        endUnmber=totalDataCount;
                    }
                    List<WmsLocationStereoscopic> subListOK = insertList.subList(startNumber,endUnmber);
                    this.wmsLocationStereoscopicMapper.batchInsert(subListOK);
                }
            }
        }
        return this.success();
    }

    public List<WmsLocationStereoscopic> queryNotEmptyLocationList() {
        return this.wmsLocationStereoscopicMapper.queryNotEmptyLocationList();
    }

    public List<WmsLocationStereoscopic> getAllLocationList() {
        WmsLocationStereoscopic cond = new WmsLocationStereoscopic();
        List<WmsLocationStereoscopic> resultList = this.wmsLocationStereoscopicMapper.queryByAny(cond);
        return resultList;
    }

    public WmsLocationStereoscopic findByLocationCode(String locationCode) {
        WmsLocationStereoscopic result = null;
        WmsLocationStereoscopic cond = new WmsLocationStereoscopic();
        cond.setActiveFlag("1");
        cond.setLocationCode(locationCode);
        List<WmsLocationStereoscopic> resultList = this.wmsLocationStereoscopicMapper.queryByAny(cond);
        if (null != resultList && resultList.size() == 1) {
            result = (WmsLocationStereoscopic)resultList.get(0);
        }

        return result;
    }

    public Map<String, String> groupByUseStatus(WmsLocationStereoscopic cond) {
        List<WmsLocationStereoscopic> stereoscopics = this.wmsLocationStereoscopicMapper.groupByUseStatus(cond);
        Map<String, String> result = new HashMap();
        Iterator var4 = stereoscopics.iterator();

        while(var4.hasNext()) {
            WmsLocationStereoscopic s = (WmsLocationStereoscopic)var4.next();
            result.put(s.getUseStatus(), s.getUserDefined1());
        }

        String[] statusArr = new String[]{"0", "1", "2", "3", "4"};

        for(int i = 0; i < statusArr.length; ++i) {
            String status = statusArr[i];
            if (!result.containsKey(status)) {
                result.put(status, "0");
            }
        }

        return result;
    }

    public Resp forbiddenLocation(String ids) {
        new Resp();
        List<String> list = new ArrayList();
        String[] idValue = ids.split(",");
        String[] var5 = idValue;
        int var6 = idValue.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String id = var5[var7];
            if (id != null && !"".equals(id)) {
                list.add(id);
            }
        }

        String flag = "0";
        Integer updateNum = this.wmsLocationStereoscopicMapper.upActiveFlag(flag, list);
        if (updateNum <= 0) {
            return this.fail("禁用失败");
        } else {
            return this.success();
        }
    }

    public Resp openFlagLocation(String ids) {
        new Resp();
        List<String> list = new ArrayList();
        String[] idValue = ids.split(",");
        String[] var5 = idValue;
        int var6 = idValue.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String id = var5[var7];
            if (id != null && !"".equals(id)) {
                list.add(id);
            }
        }

        String flag = "1";
        Integer updateNum = this.wmsLocationStereoscopicMapper.upActiveFlag(flag, list);
        if (updateNum <= 0) {
            return this.fail("解用失败");
        } else {
            return this.success();
        }
    }
}
