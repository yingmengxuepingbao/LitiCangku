package com.penghaisoft.framework.sparepartManagement.contrller;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.sparepartManagement.model.business.ISparePartBasicBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 备件管理基础
 * @author zhangwei
 * @date 2019/4/3 上午 10:05
 */

@RestController
@RequestMapping("/sparePartManagement/basic/")
public class SparePartBasicController {
    @Autowired
    private ISparePartBasicBusiness sparePartBasicBusiness;

    @RequestMapping(value = "getAllSparePart",method = RequestMethod.GET)
    public ResponseResult getAllSparePart(String queryContent ,String status, String orderBy, Integer currentPage , Integer numberOnePage){
        ResponseResult responseResult = null;
        try {
            Map<String,Object> data = sparePartBasicBusiness.getAllSparePart(queryContent,status,orderBy,currentPage,numberOnePage);
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code,Constant.RESULT.SUCCESS.message,data);
        } catch (Exception e) {
            responseResult = new ResponseResult(Constant.RESULT.FAILED.code,Constant.RESULT.FAILED.message,null);
        }
        return  responseResult;
    }

}
