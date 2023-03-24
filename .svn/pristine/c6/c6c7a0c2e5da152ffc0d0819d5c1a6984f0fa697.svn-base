package com.penghaisoft.framework.expose;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WMS与WCS相互暴露的接口暴露的接口
 * @Description ExposeController
 * @Auther zhangxu
 * @Date 2019/12/18 14:05
 **/
@RestController
@RequestMapping("expose/inner")
public class ExposeInnerController {

    @Autowired
    private IUserBusiness userBusiness;

    @PostMapping("/addUser")
    public ResponseResult addUser(String account, String nickname, String email, String phone, String officePhone, String entryDate, String description){
        //响应结果对象
        ResponseResult responseResult = null;

        int result = userBusiness.addUser(account, nickname, email, phone, officePhone, entryDate, description, "", "");

        if (result > 0) {
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, null);
        } else {
            responseResult = new ResponseResult(userBusiness.getLastErrorCode(), userBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }
}
