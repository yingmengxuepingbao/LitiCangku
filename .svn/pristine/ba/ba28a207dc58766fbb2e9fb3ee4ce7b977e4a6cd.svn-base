package com.penghaisoft.pda.basic.service.impl;

import com.penghaisoft.pda.basic.dao.BaseDictItemMapper;
import com.penghaisoft.pda.basic.dao.BasePdaVersionMapper;
import com.penghaisoft.pda.basic.dao.WmsWarehouseAreaMapper;
import com.penghaisoft.pda.basic.model.BaseDictItem;
import com.penghaisoft.pda.basic.model.BasePdaVersion;
import com.penghaisoft.pda.basic.model.WmsWarehouseArea;
import com.penghaisoft.pda.basic.service.BasicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 基础数据服务 仓库 库区等
 * @Auther zhangxu
 * @Date 2020/2/17 15:21
 **/
@Service
public class BasicDataServiceImpl implements BasicDataService {


    @Autowired
    private WmsWarehouseAreaMapper wmsWarehouseAreaMapper;

    @Autowired
    private BaseDictItemMapper baseDictItemMapper;

    @Autowired
    private BasePdaVersionMapper basePdaVersionMapper;

    @Override
    public List<String> getRootAreaCodeByWarehouse() {
        List<WmsWarehouseArea> areaList = wmsWarehouseAreaMapper.selectRootAreaCodeByWarehouse();
        List<String> areas = null;
        if (null!=areaList && areaList.size() > 0){
            areas = new ArrayList<>();
            for (WmsWarehouseArea area:areaList) {
                areas.add(area.getAreaCode());
            }
        }
        return areas;
    }

    @Override
    public List<String> getdicType(String dicTypeCode) {

        List<String> dicTypes = new ArrayList<>();
        List<BaseDictItem> list = baseDictItemMapper.selectByDicTypeCode(dicTypeCode);
        if (list.size()>0){
            for (BaseDictItem baseDictItem : list){
                dicTypes.add(baseDictItem.getDicItemCode());
            }
        }

        return dicTypes;
    }

    /**
      * @Description 获取PDA版本信息
      * @Author luot
      * @Date 2020/8/6 17:13
      * @Param
      * @return
      **/
    @Override
    public List<BasePdaVersion> getPdaVersionInfo() {
        return basePdaVersionMapper.selectAll();
    }
}
