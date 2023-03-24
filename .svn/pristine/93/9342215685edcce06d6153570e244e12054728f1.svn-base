package com.penghaisoft.framework.sparepartManagement.model.business.impl;

import com.alibaba.fastjson.JSON;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.sparepartManagement.model.business.ISparePartBasicBusiness;
import com.penghaisoft.framework.sparepartManagement.model.dao.ISparePartBasicDAO;

import com.penghaisoft.framework.sparepartManagement.model.entity.SparePart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 备件基础信息
 * @author zhangwei
 * @date 2019/4/3 上午 10:08
 */
@Service
public class SparePartBasicBusinessImpl implements ISparePartBasicBusiness {
    private static final Logger logger = LoggerFactory.getLogger(SparePartBasicBusinessImpl.class);

    @Autowired
    private ISparePartBasicDAO sparePartBasicDAO;


    @Override
    public Map<String, Object> getAllSparePart(String queryContent,String status,String orderBy, Integer currentPage, Integer numberOnePage) {
        if(currentPage == null){
            currentPage = 1;
        }
        if(numberOnePage == null){
            numberOnePage = Constant.ConfigInfo.NUMBER_ONE_PAGE;
        }
        SparePart sparePart = new SparePart();
        sparePart.setQueryContent(queryContent);
        //当前登陆用户userid
//        sparePart.setCreateUserId(UserInfoUtil.getCurrentUserInfo().getId());
//        sparePart.setOrderBy(orderBy);
        sparePart.setStatus(status);
//        PageHelper.startPage(currentPage,numberOnePage);
        List<SparePart> spareParts = sparePartBasicDAO.getAllSparePart(sparePart);
//        PageInfo<SparePart> partPageInfo = new PageInfo<>(spareParts);
        Map<String,Object> result = new HashMap<>(4);
        result.put("totalNumber",spareParts.size());
        result.put("spareParts",spareParts);
        return result;
    }

}

