package com.penghaisoft.framework.basicdatamanagement.model.business;

import com.penghaisoft.framework.basicdatamanagement.model.dao.BaseUnitMapper;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseUnit;
import com.penghaisoft.framework.util.PageNumberTransfermation;
import com.penghaisoft.framework.util.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UnitBusiness implements IUnitBusiness {

    @Autowired
    private BaseUnitMapper baseUnitMapper;


    /**
     * 获取单位列表
     * jzh
     * 2020-02-05
     */
    @Override
    public List<BaseUnit> getBaseUnitList(int currentPage, int numberOnePage, String account, String unitId, String unitCode, String unitZhName, String unitEnName, String unitChange, String unitType) {
        //返回值
        List<BaseUnit> baseUnitList = null;

        //初始化查询起始条数为-1：表示获取单位列表
        int startNumber = -1;
        if (currentPage != -1) {
            //获取sql查询起始条数：按照起始条数查询
            startNumber = PageNumberTransfermation.pageNumberToSelectStartNumber(currentPage,numberOnePage);
        }

        BaseUnit baseUnit = new BaseUnit();
        baseUnit.setStartNumber(startNumber);
        baseUnit.setUnitCode(unitCode);
        baseUnit.setUnitZhName(unitZhName);
        baseUnit.setUnitEnName(unitEnName);
        //返回符合条件的所有单位列表，此时没有分页
        baseUnitList = baseUnitMapper.queryBaseUnitList(baseUnit);

        //按照查询的条数返回
        if(numberOnePage*currentPage>baseUnitList.size()){
            baseUnitList = baseUnitList.subList(startNumber,baseUnitList.size());
        }else {
            baseUnitList = baseUnitList.subList(startNumber,numberOnePage*currentPage);
        }

        return baseUnitList;
    }
    /**
     * 获取单位总数
     * jzh
     * 2020-02-05
     */
    @Override
    public Integer getBaseUnitTotalCount(String account, String unitId, String unitCode, String unitZhName, String unitEnName, String unitChange, String unitType) {
        BaseUnit baseUnit = new BaseUnit();
        baseUnit.setUnitCode(unitCode);
        baseUnit.setUnitZhName(unitZhName);
        baseUnit.setUnitEnName(unitEnName);
        return baseUnitMapper.getBaseUnitTotalCount(baseUnit);
    }
    /**
     * 新增单位
     * jzh
     * 2020-02-05
     * @return
     */
    @Override
    public Resp addBaseUnit(String unitId, String unitCode, String unitZhName, String unitEnName, String unitChange, String unitType, String loginName) {
        Resp resp = new Resp();
        BaseUnit baseUnit = new BaseUnit();
        baseUnit.setUnitCode(unitCode);
        List<BaseUnit> baseUnitList = baseUnitMapper.queryByAny(baseUnit);
        if(baseUnitList.size()>0){
            resp.setCode("1");
            resp.setMsg("单位重复");
            return resp;
        }
        baseUnit.setUnitZhName(unitZhName);
        baseUnit.setUnitEnName(unitEnName);
        baseUnit.setUnitChange(unitChange);
        baseUnit.setUnitType(unitType);
        baseUnit.setCreateBy(loginName);
        baseUnit.setGmtCreate(new Date());
        baseUnit.setActiveFlag("1");
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        baseUnit.setUnitId(uuid);
        int createUnitNum  =  baseUnitMapper.create(baseUnit);
        if(createUnitNum<=0){
            resp.setCode("1");
            resp.setMsg("新增失败");
            return resp;
        }
        resp.setCode("0");
        resp.setMsg("新增成功");
        return resp;
    }
    /**
     * 删除单位
     * jzh
     * 2020-02-05
     * @return
     */
    @Override
    public Resp deleteBaseUnit(String unitId) {
        Resp resp = new Resp();
        if (unitId == null || unitId.equals("")) {
            resp.setCode("1");
            resp.setMsg("输入参数错误");
            return resp;
        } else {
            BaseUnit baseUnit = new BaseUnit();
            baseUnit.setUnitId(unitId);
            List<BaseUnit> baseUnitList = baseUnitMapper.queryByAny(baseUnit);
            if(baseUnitList.size()==0){
                resp.setCode("1");
                resp.setMsg("输入参数错误");
                return resp;
            }else {
                int deleteUnit = baseUnitMapper.deleteUnit(baseUnit);
                if(deleteUnit<=0){
                    resp.setCode("1");
                    resp.setMsg("删除失败");
                    return resp;
                }
            }
        }
        resp.setCode("0");
        resp.setMsg("删除成功");
        return resp;
    }
    /**
     * 根据id获取单位
     * jzh
     * 2020-02-05
     */
    @Override
    public BaseUnit getBaseUnitById(String unitId) {
        BaseUnit baseUnit = new BaseUnit();
        baseUnit.setUnitId(unitId);
        List<BaseUnit> baseUnitList = baseUnitMapper.queryByAny(baseUnit);
        return baseUnitList.get(0);
    }
    /**
     * 修改单位
     * jzh
     * 2020-02-05
     * @return
     */
    @Override
    public Resp updateBaseUnit(BaseUnit baseUnit) {
        Resp resp = new Resp();
        if (baseUnit.getUnitId() == null || "".equals(baseUnit.getUnitId()) || baseUnit.getUnitCode() == null || baseUnit.getUnitCode() == "") {
            resp.setCode("1");
            resp.setMsg("输入参数错误");
            return resp;
        } else {
            BaseUnit baseUnitId = new BaseUnit();
            baseUnitId.setUnitId(baseUnit.getUnitId());
            List<BaseUnit> baseUnitList = baseUnitMapper.queryByAny(baseUnitId);
            if (baseUnitList.size() == 0) {//查询不到单位
                resp.setCode("1");
                resp.setMsg("输入参数错误");
                return resp;
            } else {
                //更新单位
                int updateNum = baseUnitMapper.updateBySelect(baseUnit);
                if(updateNum<=0) {
                    resp.setCode("1");
                    resp.setMsg("更新失败");
                    return resp;
                }
            }
        }
        resp.setCode("0");
        resp.setMsg("更新成功");
        return resp;
    }

}
