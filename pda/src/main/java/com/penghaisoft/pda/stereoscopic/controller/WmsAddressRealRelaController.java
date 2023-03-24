package com.penghaisoft.pda.stereoscopic.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.basic.model.WmsAddressRealRela;
import com.penghaisoft.pda.basic.service.IWmsAddressRealRelaService;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/wmsAddressRealRela")
@Slf4j
public class WmsAddressRealRelaController {

    @Autowired
    private IWmsAddressRealRelaService wmsAddressRealRelaService;

    /**
     * @return
     * @Description 获取立库人工入库口
     * @Author luot
     * @Date 2020/3/18 11:35
     * @Param
     **/
    @GetMapping("getHandInAddress")
    public JSONObject getHandInAddress() {
        List<String> addressTypeList = new ArrayList<String>();
        addressTypeList.add(String.valueOf(Constant.TaskType.HAND_IN.getTaskType()));
        WmsAddressRealRela wmsAddressRealRela = new WmsAddressRealRela();
        wmsAddressRealRela.setAddressTypeList(addressTypeList);
        List<WmsAddressRealRela> list = wmsAddressRealRelaService.queryOutAddress(wmsAddressRealRela);

        JSONObject result = null;
        if (list == null || list.isEmpty()) {
            result = CommonUtil.genErrorResult("人工入库口获取失败");
        } else {
            List<Map> mapList = new ArrayList<Map>();
            for(WmsAddressRealRela tmp : list){
                Map<String, String> map = new HashMap<String, String>();
                map.put("item", tmp.getAddressCode());
                map.put("value", tmp.getAddressName());
                mapList.add(map);
            }
            result = CommonUtil.genSucessResultWithData(mapList);
        }
        log.info("++++++++++++::   "+result);
        return result;
    }

    /**
     * @return
     * @Description 诺华获取出入库库口编码
     * @Author luot
     * @Date 2020/3/18 11:35
     * @Param
     **/
    @GetMapping("getHandInAddress2")
    public JSONObject getHandInAddress2() {

        List<Map> mapList = new ArrayList<Map>();
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> map2 = new HashMap<String, String>();
        map.put("item", "0");
        map.put("value", "入库模式");
        mapList.add(map);
        map2.put("item", "1");
        map2.put("value", "出库模式");
        mapList.add(map2);
        JSONObject result = null;
        result = CommonUtil.genSucessResultWithData(mapList);
        return result;
    }
}