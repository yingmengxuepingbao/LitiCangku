package com.penghaisoft.wcs.basicmanagement.controller;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.wcs.operation.constant.DeviceConstant;
import com.penghaisoft.wcs.operation.model.Palletizer;
import com.penghaisoft.wcs.operation.model.PalletizerInfo;
import com.penghaisoft.wcs.operation.service.PalletizingOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping(value = "/palletizerstatus")
@Slf4j
public class PalletizerStatusController {

    @Autowired
    private PalletizingOperationService palletizingOperationService;

    /**
     * 根据码垛机号获取码垛机状态
     * jzh
     * @param palletizingNum
     * @return
     */
    @PostMapping("/getpalletizer")
    public ResponseResult getPalletizerStatusByNum(Integer palletizingNum) {
        Palletizer palletizer = DeviceConstant.PALLETIZER;

        Map<Short, PalletizerInfo> palletizerInfoMap = palletizer.getPalletizerInfoMap();

        Short shortNum = palletizingNum.shortValue();
        PalletizerInfo palletizerInfo = palletizerInfoMap.get(shortNum);

        return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, palletizerInfo);
    }


    @PostMapping("/handReceive")
    public ResponseResult handReceive(Integer palletizingNum) {
        Boolean flag =palletizingOperationService.setReceiveFinish(palletizingNum);
        if (flag == true){
            return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, null);
        }else {
            return new ResponseResult(Constant.RESULT.FAILED.code,Constant.RESULT.FAILED.message,null);
        }
    }
}
