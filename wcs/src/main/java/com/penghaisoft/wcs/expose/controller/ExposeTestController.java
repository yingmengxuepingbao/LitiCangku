package com.penghaisoft.wcs.expose.controller;

import com.penghaisoft.wcs.basicmanagement.model.business.IWcsLocationRealService;
import com.penghaisoft.wcs.common.service.IWcsCommonService;
import com.penghaisoft.wcs.operation.model.AgvPositionItem;
import com.penghaisoft.wcs.operation.model.AgvTask;
import com.penghaisoft.wcs.operation.service.AgvOperationService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskDispatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Description ExposeTestController
 * @Auther zhangxu
 * @Date 2020/3/23 16:05
 **/
@RestController
@RequestMapping(value = "/expose/test")
@Slf4j
public class ExposeTestController {
    @Autowired
    private ITaskDispatchService taskDispatchService;

    @Autowired
    private IWcsLocationRealService locationRealService;

    @Autowired
    private IWcsCommonService wcsCommonService;


    @Autowired
    private AgvOperationService agvOperationService;




    @GetMapping("testagv")
    public String testAgv(){
        AgvTask agvTask = new AgvTask();
        agvTask.setReqCode(UUID.randomUUID().toString());
        agvTask.setTaskTyp("QXF");
//        根据规则生成位置编号数据
        List<AgvPositionItem> agvPositionItemList = new ArrayList<>();
        AgvPositionItem from = new AgvPositionItem();
        from.setPositionCode("B");
        from.setType("00");
        agvPositionItemList.add(from);
        AgvPositionItem to = new AgvPositionItem();
        to.setPositionCode("H");
        to.setType("00");
        agvPositionItemList.add(to);

        agvTask.setPositionCodePath(agvPositionItemList);
        agvOperationService.sendTask(agvTask);
        return "1";
    }

    public static void main(String[] args) {
        String reqCode = String.valueOf(System.currentTimeMillis());
        System.out.println(reqCode);
        System.out.println(reqCode.length());
    }

}
