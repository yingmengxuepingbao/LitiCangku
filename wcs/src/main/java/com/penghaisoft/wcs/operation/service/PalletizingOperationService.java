package com.penghaisoft.wcs.operation.service;

import com.penghaisoft.wcs.operation.model.EarlyInLocOccupyInfo;

/**
 * 绑定码垛机服务类
 * zhangx
 */
public interface PalletizingOperationService {

    /**
     * 设置码垛机接受完成
     * @param status
     */
    void setPalletReceiveFinish(Short status);

    /**
    * @Description 当agv取完货物以后，将相应的码垛线请求完成位置1
    * @Date 2020/7/6 10:11
    * @param lineId
    * @return void
    **/
    boolean setReceiveFinish(Integer lineId);

    /**
    * @Description 前期入库功能用的
    * @Date 2020/7/17 14:50
    * @param target
    * @return void
    **/
    void setInTask(String target);


    EarlyInLocOccupyInfo getEarlyInLocOccupyInfo();
}
