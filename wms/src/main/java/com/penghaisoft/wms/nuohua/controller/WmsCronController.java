package com.penghaisoft.wms.nuohua.controller;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.wms.nuohua.service.DifferentBusinessNHFactory;
import com.penghaisoft.wms.nuohua.service.DifferentBusinessNHService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import lombok.extern.slf4j.Slf4j;
import onbon.bx06.Bx6GEnv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Description
 * 定时任务 - 默认加载/调用执行
 * @Author zhangxin
 * @Date 2022-08-09
 **/
@Slf4j
@Component
public class WmsCronController {
    @Autowired
    public DifferentBusinessNHFactory differentBusinessNHFactory;
    /**
     * 出库任务轮询-下发 调 wcs
     * 默认加载
     * 检查立库出库单，状态为2：启动
     * 任务执行日志，状态是否存在 2：执行中 的数据
     * 并下发
     * 每1分钟执行一次
     */
    //@PostConstruct
    //@Scheduled(cron = "0 0/1 * * * * ")
    public void  checkAndSend()  {
        try {
            String  applyfactory = "HB";
            log.info("默认加载======检查立库出库单及任务执行日志的状态");
            DifferentBusinessNHService differentBusinessHBService = this.differentBusinessNHFactory.getDifferentBusinessNHService(Constant.RecommendLocationEnum.getServiceNameByCode(applyfactory));
            //检查并下发任务
            log.info("检查立库出库单及任务执行日志并下发出库任务-----开始");
            differentBusinessHBService.checkAndSend();
            log.info("检查立库出库单及任务执行日志并下发出库任务-----结束");
        }catch (Exception e){
            log.info("检查立库出库单及任务执行日志并下发出库任务-----异常 "+e);
        }
    }

    /**
     * 出库任务轮询-下发 调 wcs
     * 默认加载
     * 检查立库出库单，状态为2：启动
     * 任务执行日志，状态是否存在 2：执行中 的数据
     * 并下发
     * 每5分钟执行一次
     */
    //@PostConstruct
    //@Scheduled(cron = "0 0/5 * * * * ")
    public void  panDianAndSend()  {
        try {
            String  applyfactory = "HB";
            log.info("默认加载======检查立库出库单及任务执行日志的状态");
            DifferentBusinessNHService differentBusinessHBService = this.differentBusinessNHFactory.getDifferentBusinessNHService(Constant.RecommendLocationEnum.getServiceNameByCode(applyfactory));
            //检查并下发任务
            log.info("线下盘点 - 检查立库盘点单及任务执行日志并下发出库任务-----开始");
            differentBusinessHBService.panDianAndSend();
            log.info("线下盘点 - 检查立库盘点单及任务执行日志并下发出库任务-----开始");
        }catch (Exception e){
            log.info("线下盘点 - 检查立库盘点单及任务执行日志并下发出库任务-----异常 "+e);
        }
    }

    /**
     * 默认启动LED服务
     */
    @PostConstruct
    public void LEDServer() {
        try {
            log.info("=========LED服务启动==========");
            Bx6GEnv.initial(1000);
            log.info("=========LED服务启动完成==========");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("=========LED服务启动异常==========");
        }

    }
//==================================现场修改接口=================================
    /**
     * 入库优先，先查询是否有入库任务，有入库任务执行入库任务，
     * 没有入库任务，再查询出库任务，有出库任务，执行出库任务
     * 每1分钟执行一次
     */
    @PostConstruct
    @Scheduled(cron = "0 0/1 * * * * ")
    public void  checkReceiptIssue()  {
        try {
            String  applyfactory = "NH";
            log.info("默认加载======检查立库出库单及任务执行日志的状态");
            DifferentBusinessNHService differentBusinessHBService = this.differentBusinessNHFactory.getDifferentBusinessNHService(Constant.RecommendLocationEnum.getServiceNameByCode(applyfactory));
            //检查并下发任务
            log.info("检查立库出库单及任务执行日志并下发出库任务-----开始");
            differentBusinessHBService.checkReceiptIssue();
            log.info("检查立库出库单及任务执行日志并下发出库任务-----结束");
        }catch (Exception e){
            log.info("检查立库出库单及任务执行日志并下发出库任务-----异常 "+e);
        }
    }
    /**
     *功能描述: PDA手动入库，有出库任务，出库任务完成后，给用户一个LED提示。
     * @params
     * @return void
     */
    //@PostConstruct
    //@Scheduled(cron = "0 0/2 * * * * ")
    public void  checkShouDongRuKu()  {
        try {
            String  applyfactory = "NH";
            log.info("默认加载======PDA手动入库，有出库任务，出库任务完成后，给用户一个LED提示");
            DifferentBusinessNHService differentBusinessHBService = this.differentBusinessNHFactory.getDifferentBusinessNHService(Constant.RecommendLocationEnum.getServiceNameByCode(applyfactory));
            //检查并下发任务
            log.info("检查立库出库单及任务执行日志并下发出库任务-----开始");
            differentBusinessHBService.checkReceiptIssue();
            log.info("检查立库出库单及任务执行日志并下发出库任务-----结束");
        }catch (Exception e){
            log.info("检查立库出库单及任务执行日志并下发出库任务-----异常 "+e);
        }
    }
}
