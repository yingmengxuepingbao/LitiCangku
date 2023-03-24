package com.penghaisoft.framework.basicdatamanagement.controller;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.basicdatamanagement.model.business.IUnitBusiness;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseUnit;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.framework.util.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 计量单位
 * @author 井振瀚
 * @date 2020-02-04
 */
@Slf4j
@Controller
@RequestMapping(value = "/unitManagement")
public class UnitManagementController {

    @Autowired
    private IUnitBusiness unitBusiness;
    @Autowired
    private IUserBusiness userBusiness;

    /**
     * 获取计量单位列表
     * @return JSON字符串
     * code：返回码
     * message：返回消息
     * data：成功返回计量单位列表，失败返回null
     */
    @RequestMapping(value = "/getBaseUnitList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getBaseUnitList(int currentPage, int numberOnePage,String account, String unitId, String unitCode, String unitZhName, String unitEnName,
                                          String unitChange, String unitType) {
        //响应结果对象
        ResponseResult responseResult = null;

        //获取单位列表
        List<BaseUnit> baseUnitList = unitBusiness.getBaseUnitList(currentPage,numberOnePage,account,unitId,unitCode,unitZhName,unitEnName,unitChange,unitType);
        if (baseUnitList == null) {
            responseResult = new ResponseResult(RESULT.FAILED.code, "查询数据错误", null);
        } else {
            //获取总单位数
            Integer baseUnitCount = unitBusiness.getBaseUnitTotalCount(account,unitId,unitCode,unitZhName,unitEnName,unitChange,unitType);
            //将单位列、总单位数转化为Map
            Map<String, Object> unitMap = new HashMap<>();
            unitMap.put("userList", baseUnitList);
            unitMap.put("userTotalCount", baseUnitCount);
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, unitMap);
        }
        return responseResult;
    }
    /**
     * 添加计量单位
     * @return JSON字符串
     * code：返回码
     * message：返回消息
     * data：null
     */
    @RequestMapping(value = "/addBaseUnit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult addBaseUnit(String unitId, String unitCode, String unitZhName, String unitEnName, String unitChange, String unitType) {
        //响应结果对象
        ResponseResult responseResult = null;
        //获取登录名，插入用
        User user = userBusiness.getCurrentUser();
        String loginName = user.getNickname();
        Resp resp = unitBusiness.addBaseUnit(unitId,unitCode,unitZhName,unitEnName,unitChange,unitType,loginName);
        if (resp.getCode().equals("0")) {
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, null);
        } else {
            responseResult = new ResponseResult(RESULT.FAILED.code,resp.getMsg() , null);
        }
        return responseResult;
    }

    /**
     * 删除单位
     * @return JSON字符串
     * code：返回码
     * message：返回消息
     * data：null
     */
    @RequestMapping(value = "/deleteBaseUnit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult deleteBaseUnit(String unitId) {
        //响应结果对象
        ResponseResult responseResult = null;
        Resp resp = unitBusiness.deleteBaseUnit(unitId);

        responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), null);
        return responseResult;
    }
    /**
     * 根据id查询单位
     */
    @RequestMapping(value = "/getBaseUnitById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getBaseUnitById(String unitId) {
        //响应结果对象
        ResponseResult responseResult = null;
        //获取单位列表
        BaseUnit baseUnit = unitBusiness.getBaseUnitById(unitId);
        if (baseUnit != null) {
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, baseUnit);
        } else {
            responseResult = new ResponseResult(RESULT.FAILED.code,"查询失败", null);
        }
        return responseResult;
    }

    /**
     * 根据id修改单位
     */
    @RequestMapping(value = "/updateBaseUnit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateBaseUnit(BaseUnit baseUnit) {
        //响应结果对象
        ResponseResult responseResult = null;
        //获取登录名，插入用
        User user = userBusiness.getCurrentUser();
        String loginName = user.getNickname();
        baseUnit.setLastModifiedBy(loginName);
        baseUnit.setGmtModified(new Date());
        Resp resp  = unitBusiness.updateBaseUnit(baseUnit);

        responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), null);
        return responseResult;
    }
}