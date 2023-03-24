package com.penghaisoft.wcs.task.connect;

import com.penghaisoft.wcs.operation.constant.DeviceConstant;
import com.penghaisoft.wcs.operation.model.PalletizerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;


@Slf4j
//@Component
public class TestConnectTask {
    @PostConstruct
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void scheduled() {

        for (short i=1;i <=5;i++){

            PalletizerInfo palletizerInfo = new PalletizerInfo();
            palletizerInfo.setGoodsCode("goodsCode");
            palletizerInfo.setPalletCode("PalletCode");
            palletizerInfo.setBatchNo("BatchNo");
            palletizerInfo.setAmount(i);
            palletizerInfo.setReqTrans((short)1);
            palletizerInfo.setRecFinish((short)1);
            DeviceConstant.PALLETIZER.getPalletizerInfoMap().put(i,palletizerInfo);
        }


    }
}
