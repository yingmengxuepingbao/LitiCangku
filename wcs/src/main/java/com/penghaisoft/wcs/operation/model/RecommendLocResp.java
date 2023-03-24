package com.penghaisoft.wcs.operation.model;

import lombok.Data;

/**
 * @ClassName RecommondLocResp
 * @Description 请求wms推荐库位的返回,入库时候不会移库
 * @Author zhangx
 * @Date 2020/7/2 15:18
 **/
@Data
public class RecommendLocResp {

//    库位编码
    private Integer locationCode;
//     任务号
    private Long taskId;
//    1 越库0 非越库 默认0
    private String isCross;

}
