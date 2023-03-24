//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.nuohua.service;

import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsMoveTask;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsOutTask;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopicDeail;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsMoveStereoscopic;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheck;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;

import java.util.List;

public interface DifferentBusinessNHService {

    Resp queryRecommendLocationCode(String goodsCode, String batchNo, String areaCode);
    /**
     *功能描述: 获取推荐库位
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    Resp queryRecommendLocationCodeHB(WmsTaskExecutionLog wmsTaskExecutionLog, String areaCode);

    Resp querySortingRecommendLocationCode(String goodsCode, String batchNo, String areaCode);

    Resp revertLocationStatus0(String locationCode);

    Resp queryOutRecommendLocationCode(WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail, String targetAddress, String loginName);

     Resp revertOut(List<WmsMoveTask> wmsMoveTaskList, List<WmsOutTask> wmsOutTaskList, String orderNo);

    Resp palletOutLocationCheck(String locationCode, String loginName, String targetAddress);

    Resp startPalletOut(List<WmsOutTask> wmsOutTaskList, String loginName);

    Resp lockCheckLocationCode(WmsOrderCheck wmsOrderCheck);

    Resp createOrEditYk(WmsMoveStereoscopic wmsMoveStereoscopic);

    Resp locationLockYk(WmsMoveStereoscopic wmsMoveStereoscopic);

    Resp reverseYk(WmsMoveStereoscopic wmsMoveStereoscopic);
    /**
     *功能描述: 检查立库出库单，状态为2：启动
     * 任务执行日志，状态是否存在 2：执行中 的数据
     * @author zhangxin
     * @date 2022/8/8
     * @params
     * @return boolean
     */
    WmsLocationStereoscopic checkOutAndTask();
    /**
     *功能描述:
     * 检查并调取wcs
     * @author zhangxin
     * @date 2022/8/8
     * @params
     * @return boolean
     */
    void  checkAndSend();
    /**
     *功能描述:
     * 盘点出库
     * @params
     * @return boolean
     */
    void  panDianAndSend();
    /**
     * 功能描述:
     * 根据指定的托盘号，检查 库位
     *
     * @return boolean
     * @author zhangxin
     * @date 2022/8/8
     * @params
     */
    WmsLocationStereoscopic checkByPalletCode(String palletCode);

    //======================现场编写接口=============================
    /**
     *功能描述: 定时任务
     * 入库优先，
     * 查询是否有出库任务正在执行，将创建状态为1的，修改成7，暂停下发出库任务。
     * 优先下发入库任务。
     * 如果没有入库任务，再启动出库任务，一次最多下发三个出库任务。（只有三个小车）
     * @params
     * @return
     */
    void checkReceiptIssue();

    String selectlocationCodeByfloor(String goodsCode, String batchNo, String locationCode, List<WmsLocationStereoscopic> tongpi_location, List list);
    /**
     *功能描述: PDA手动入库，有出库任务，出库任务完成后，给用户一个LED提示。
     * @params
     * @return void
     */
    void checkShouDongRuKu();
}
