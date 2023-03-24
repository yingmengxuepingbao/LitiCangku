package com.penghaisoft.wcs.operation.service.impl;

import com.penghaisoft.wcs.operation.model.WcsBindingInfoPlane;
import com.penghaisoft.wcs.operation.model.dao.WcsBindingInfoPlaneMapper;
import com.penghaisoft.wcs.operation.service.BindingPlaneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName BindingPlaneServiceImpl
 * @Description
 * @Author luo_0
 * @Date 2020/7/29 16:44
 **/
@Slf4j
@Service
public class BindingPlaneServiceImpl implements BindingPlaneService {
    @Autowired
    private WcsBindingInfoPlaneMapper wcsBindingInfoPlaneMapper;

    @Override
    public List<WcsBindingInfoPlane> queryAll(){
        return wcsBindingInfoPlaneMapper.queryAll();
    }

    @Override
    public int insertSelective(WcsBindingInfoPlane record){
        return wcsBindingInfoPlaneMapper.insertSelective(record);
    }
}
