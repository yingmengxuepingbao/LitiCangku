package com.penghaisoft.wcs.operation.model;

import lombok.Data;

/**
 * @ClassName TempInLocInfo
 * @Description 前期入库的入库口占位信息
 * @Author zhangx
 * @Date 2020/8/6 14:05
 **/
@Data
public class EarlyInLocOccupyInfo {

    private Boolean h1AllowPallet;

    private Boolean h2AllowPallet;

    private Boolean h1HasJob;

    private Boolean h2HasJob;
}
