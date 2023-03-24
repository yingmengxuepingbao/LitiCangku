//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business.impl;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsGoodsService;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsGoodsMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;

import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import org.springframework.stereotype.Service;

@Service("wmsGoodsService")
public class WmsGoodsServiceImpl extends BaseService implements IWmsGoodsService {
    @Resource
    private WmsGoodsMapper wmsGoodsMapper;

    public WmsGoodsServiceImpl() {
    }

    public Resp create(WmsGoods wmsGoods) {
        WmsGoods checkGoods = new WmsGoods();
        String goodsCode = wmsGoods.getGoodsCode().toUpperCase();
        checkGoods.setGoodsCode(goodsCode);
        List<WmsGoods> checkList = this.wmsGoodsMapper.queryByAny(checkGoods);
        if (checkList.size() > 0) {
            return ((WmsGoods)checkList.get(0)).getActiveFlag().equals("0") ? new Resp("1", "该商品编码已存在,请联系管理员解锁") : new Resp("1", "该商品编码已存在");
        } else {
            wmsGoods.setGoodsCode(goodsCode);
            this.wmsGoodsMapper.create(wmsGoods);
            return this.success();
        }
    }

    public Resp delete(WmsGoods wmsGoods) {
        this.wmsGoodsMapper.delete(wmsGoods);
        return this.success();
    }

    public Pager<WmsGoods> findListByCondition(int page, int rows, WmsGoods condition) {
        condition.setActiveFlag("1");
        Pager<WmsGoods> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsGoodsMapper.queryCount(condition);
        List<WmsGoods> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsGoodsMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsGoods findById(String id) {
        return (WmsGoods)this.wmsGoodsMapper.queryById(id);
    }

    public Resp update(WmsGoods wmsGoods) {
        this.wmsGoodsMapper.updateBySelect(wmsGoods);
        return this.success();
    }

    public List<BaseDictItem> getGoodsCodeAll() {
        WmsGoods wmsGoods = new WmsGoods();
        wmsGoods.setActiveFlag("1");
        List<WmsGoods> records = this.wmsGoodsMapper.queryByAny(wmsGoods);
        List<BaseDictItem> list = new ArrayList();
        Iterator var4 = records.iterator();

        while(var4.hasNext()) {
            WmsGoods goods = (WmsGoods)var4.next();
            BaseDictItem item = new BaseDictItem();
            item.setDicItemCode(goods.getGoodsCode());
            item.setDicItemName(goods.getGoodsName());
            list.add(item);
        }

        return list;
    }

    public List<WmsGoods> getGoodsCodeAllBatchNo() {
        WmsGoods wmsGoods = new WmsGoods();
        return this.wmsGoodsMapper.getGoodsCodeAllBatchNo(wmsGoods);
    }
    /**
     *功能描述: -获取基础商品列表
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    public List<WmsGoods> getHBGoodsCodeAllBatchNo() {
        WmsGoods wmsGoods = new WmsGoods();
        return this.wmsGoodsMapper.getHBGoodsCodeAllBatchNo(wmsGoods);
    }
    /**
     *功能描述: -根据[商品类型] 商品编码分组，排重，获取商品编码，商品名称，批次号
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    public List<WmsGoods> getAllBatchNoByType(String goodType) {
        WmsGoods wmsGoods = new WmsGoods();
        wmsGoods.setGoodsType(goodType);
        return this.wmsGoodsMapper.getAllBatchNoByType(wmsGoods);
    }


    public WmsGoods queryByCode(String goodsCode) {
        return this.wmsGoodsMapper.queryByCode(goodsCode);
    }
    /**
     *功能描述:  - 根据商品编码，获取批次号
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods>
     */
    public List<WmsGoods> getHBBatchNo(WmsGoods wmsgoods) {
        return this.wmsGoodsMapper.getHBBatchNo(wmsgoods);
    }
    /**
     *功能描述: 根据商品编码查询数据
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods>
     */
    @Override
    public List<WmsGoods> queryBatch(List<WmsGoods> dataList) {
        return this.wmsGoodsMapper.queryBatch(dataList);
    }
    /**
     *功能描述: 商品表批量上传-根据商品编码查询数据
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods>
     */
    @Override
    public Resp batchInsertLocation(List<WmsGoods> insertList) {
        //批量插入，因为sqlserver参数限制2100个。
        if(insertList!=null &&insertList.size()>0){
            if(insertList.size()<60) {
                this.wmsGoodsMapper.queryBatch(insertList);
            }else {
                // 每次插入数据库的数据量
                int preInsertDataCount = 60;
                // 可遍历的插入数据库的次数
                int insertSqlCount = 0;
                // 总数据条数
                int totalDataCount=insertList.size();
                if(totalDataCount % preInsertDataCount==0){
                    insertSqlCount=totalDataCount/preInsertDataCount;
                }else {
                    insertSqlCount=totalDataCount/preInsertDataCount+1;
                }
                for (int i = 0; i < insertSqlCount; i++) {
                    int startNumber = i*preInsertDataCount;
                    int endUnmber=(i+1)*preInsertDataCount;
                    if(endUnmber>totalDataCount){
                        endUnmber=totalDataCount;
                    }
                    List<WmsGoods> subListOK = insertList.subList(startNumber,endUnmber);
                    this.wmsGoodsMapper.batchInsert(subListOK);
                }
            }
        }
        return this.success();
    }
}
