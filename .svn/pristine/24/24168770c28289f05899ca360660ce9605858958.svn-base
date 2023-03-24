//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.penghaisoft.framework.basicdatamanagement.model.business.IBaseDictItemService;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsAddressRealRelaMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsGoodsMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsAddressRealRela;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicDeailMapper;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicMapper;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopicDeail;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsSendLedDataService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsTaskExecutionLogMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import onbon.bx06.Bx6GEnv;
import onbon.bx06.Bx6GException;
import onbon.bx06.Bx6GScreenClient;
import onbon.bx06.area.TextCaptionBxArea;
import onbon.bx06.area.page.TextBxPage;
import onbon.bx06.file.ProgramBxFile;
import onbon.bx06.series.Bx6E;
import onbon.bx06.utils.DisplayStyleFactory;
import onbon.bx06.utils.DisplayStyleFactory.DisplayStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sendLedDataService")
public class WmsSendLedDataServiceImpl extends BaseService implements IWmsSendLedDataService {
    private static final Logger log = LoggerFactory.getLogger(WmsSendLedDataServiceImpl.class);
    @Resource
    private WmsTaskExecutionLogMapper wmsTaskExecutionLogMapper;
    @Resource
    private WmsPalletMapper wmsPalletMapper;
    @Resource
    private WmsOrderOutStereoscopicMapper wmsOrderOutStereoscopicMapper;
    @Resource
    private WmsOrderOutStereoscopicDeailMapper wmsOrderOutStereoscopicDeailMapper;
    @Resource
    private WmsGoodsMapper wmsGoodsMapper;
    @Resource
    private WmsAddressRealRelaMapper wmsAddressRealRelaMapper;
    @Autowired
    private IBaseDictItemService baseDictItemService;
    private String ledIp;
    private String ledPort;

    public WmsSendLedDataServiceImpl() {
    }

    public Resp sendLEDOut(long taskId, String palletCode, String status, Integer boxAmount) {
        String orderNo = "";
        String outAddress = "";
        String carNo = "";
        String taskType = "";
        if (boxAmount == null) {
            boxAmount = 0;
        }

        String goodCode = "";
        String goodName = "";
        WmsTaskExecutionLog task = new WmsTaskExecutionLog();
        task.setTaskId(taskId);
        List<WmsTaskExecutionLog> list = this.wmsTaskExecutionLogMapper.queryByAny(task);
        if (list != null && !list.isEmpty()) {
            log.info("LED显示，orderNo={},carNo = {}", orderNo, carNo);
            orderNo = ((WmsTaskExecutionLog)list.get(0)).getOrderNo();
            outAddress = ((WmsTaskExecutionLog)list.get(0)).getOutAddress();
            taskType = ((WmsTaskExecutionLog)list.get(0)).getTaskType();
            goodCode = ((WmsTaskExecutionLog)list.get(0)).getGoodsCode();
            if (goodCode != null && !"".equals(goodCode)) {
                WmsGoods wmsGoods = this.wmsGoodsMapper.queryByCode(goodCode);
                if (wmsGoods != null && wmsGoods.getGoodsName() != null) {
                    goodName = wmsGoods.getGoodsName();
                }
            }

            List addressList;
            if (orderNo != null && !"".equals(orderNo)) {
                WmsOrderOutStereoscopic searchOb = new WmsOrderOutStereoscopic();
                searchOb.setOrderNo(orderNo);
                searchOb.setActiveFlag("1");
                addressList = this.wmsOrderOutStereoscopicMapper.queryByAny(searchOb);
                if (addressList != null && !addressList.isEmpty()) {
                    carNo = ((WmsOrderOutStereoscopic)addressList.get(0)).getUserDefined1();
                }
            }

            if (outAddress != null && !"".equals(outAddress)) {
                WmsAddressRealRela t = new WmsAddressRealRela();
                t.setAddressCode(outAddress);
                addressList = this.wmsAddressRealRelaMapper.queryByAny(t);
                if (addressList != null && !addressList.isEmpty()) {
                    this.ledIp = ((WmsAddressRealRela)addressList.get(0)).getUserDefined1();
                    this.ledPort = ((WmsAddressRealRela)addressList.get(0)).getUserDefined2();
                }
            }

            if (null != this.ledIp && !"".equals(this.ledIp) && null != this.ledPort && !"".equals(this.ledPort)) {
                WmsPallet wmsPallet = new WmsPallet();
                wmsPallet.setPalletCode(palletCode);
                wmsPallet.setActiveFlag("1");
                List<WmsPallet> wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
                if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                    WmsPallet pallet = (WmsPallet)wmsPalletList.get(0);

                    try {
                        DisplayStyle[] styles = (DisplayStyle[])DisplayStyleFactory.getStyles().toArray(new DisplayStyle[0]);
                        Bx6GEnv.initial();
                        Bx6GScreenClient screen = new Bx6GScreenClient("MyScreen", new Bx6E());
                        screen.connect(this.ledIp, Integer.parseInt(this.ledPort));
                        ProgramBxFile pf = new ProgramBxFile("P000", screen.getProfile());
                        TextCaptionBxArea area = new TextCaptionBxArea(0, 0, 256, 128, screen.getProfile());
                        TextBxPage page = new TextBxPage();
                        if (taskType != null && String.valueOf(TaskType.HAND_OUT.getTaskType()).equals(taskType)) {
                            page.newLine("人工出库");
                            page.newLine("-------------------------------");
                            page.newLine("                        ");
                            page.newLine("托盘号:" + palletCode);
                            page.newLine("商品名称:" + goodName);
                            page.newLine("商品数量:" + boxAmount);
                        } else {
                            page.newLine("直发出库");
                            page.newLine("-------------------------------");
                            page.newLine("出库单号:" + orderNo);
                            page.newLine("车牌号:" + carNo);
                            page.newLine("托盘号:" + palletCode);
                            page.newLine("商品名称:" + goodName);
                            if (orderNo != null && !"".equals(orderNo)) {
                                Integer requirePalletAmount = 0;
                                Integer outPalletAmount = 0;
                                Integer requireBoxAmount = 0;
                                Integer outBoxAmount = 0;
                                WmsTaskExecutionLog searchOb = new WmsTaskExecutionLog();
                                searchOb.setOrderNo(orderNo);
                                List<WmsTaskExecutionLog> taskList = this.wmsTaskExecutionLogMapper.queryByAny(searchOb);
                                if (taskList != null && !taskList.isEmpty()) {
                                    Iterator var28 = taskList.iterator();

                                    while(var28.hasNext()) {
                                        WmsTaskExecutionLog tmp = (WmsTaskExecutionLog)var28.next();
                                        if (tmp.getTaskStatus() != null && "1".equals(tmp.getTaskStatus())) {
                                            requirePalletAmount = requirePalletAmount + 1;
                                        }
                                    }
                                }

                                WmsOrderOutStereoscopicDeail detail = new WmsOrderOutStereoscopicDeail();
                                detail.setOrderNo(orderNo);
                                detail.setGoodsCode(pallet.getGoodsCode());
                                detail.setBatchNo(pallet.getBatchNo());
                                List<WmsOrderOutStereoscopicDeail> detailList = this.wmsOrderOutStereoscopicDeailMapper.queryByAnyList(detail);
                                if (detailList != null && !detailList.isEmpty()) {
                                    Iterator var30 = detailList.iterator();

                                    while(var30.hasNext()) {
                                        WmsOrderOutStereoscopicDeail tmp = (WmsOrderOutStereoscopicDeail)var30.next();
                                        if (tmp.getPlanAmount() != null) {
                                            requireBoxAmount = requireBoxAmount + tmp.getPlanAmount();
                                        }

                                        if (tmp.getRealPalletAmount() != null) {
                                            outPalletAmount = outPalletAmount + tmp.getRealPalletAmount();
                                        }

                                        if (tmp.getRealAmount() != null) {
                                            outBoxAmount = outBoxAmount + tmp.getRealAmount();
                                        }
                                    }
                                }

                                if (status != null && "8".equals(status)) {
                                    List<BaseDictItem> dictItems = this.baseDictItemService.getDictTypeByCode("num_progress");
                                    if (!dictItems.isEmpty()) {
                                        String dict = ((BaseDictItem)dictItems.get(0)).getDicItemCode();
                                        if (dict != null && "1".equals(dict)) {
                                            page.newLine("商品数量:" + boxAmount + " ( " + outBoxAmount + " / " + requireBoxAmount + " ) ");
                                        } else {
                                            page.newLine("商品数量:" + boxAmount);
                                        }
                                    } else {
                                        page.newLine("商品数量:" + boxAmount);
                                    }

                                    page.newLine("出库进度:" + outPalletAmount + " / " + requirePalletAmount + " 托");
                                } else {
                                    page.newLine("商品数量:" + boxAmount);
                                }
                            }
                        }

                        if (status != null && "9".equals(status)) {
                            page.setFont(new Font("宋体", 0, 15));
                            page.setForeground(Color.RED);
                        } else {
                            page.setFont(new Font("宋体", 0, 14));
                        }

                        page.setDisplayStyle(styles[2]);
                        area.addPage(page);
                        pf.addArea(area);
                        screen.writeProgram(pf);
                        screen.disconnect();
                    } catch (Exception var32) {
                        var32.printStackTrace();
                        log.error("发送LED地址（" + this.ledIp + "）、端口（" + this.ledPort + "）发生异常！" + var32.getMessage());
                        return this.fail("发送LED地址（" + this.ledIp + "）、端口（" + this.ledPort + "）发生异常！" + var32.getMessage());
                    }

                    return this.success();
                } else {
                    log.error("LED-托盘号（" + palletCode + "）查询不到信息！");
                    return this.fail("托盘号（" + palletCode + "）查询不到信息！");
                }
            } else {
                log.error("【wms_address_real_rela】没有配置LED地址、端口信息！");
                return this.fail("【wms_address_real_rela】没有配置LED地址、端口信息！");
            }
        } else {
            log.info("LED显示----wms_task_execution_log根据taskId查询不到记录！");
            return this.fail("wms_task_execution_log根据taskId查询不到记录！");
        }
    }

    public static void testLed(String ledIp, String orderNo) {
        String ledPort = "5005";
        WmsPallet pallet = new WmsPallet();
        pallet.setPalletCode("AA1234");
        pallet.setGoodsCode("P110101002");
        pallet.setAmount(99);
        String goodsName = "150ml巧媳妇味极鲜1*60";
        String carNo = "鲁B 6Z123";
        DisplayStyle[] styles = (DisplayStyle[])DisplayStyleFactory.getStyles().toArray(new DisplayStyle[0]);

        try {
            Bx6GEnv.initial();
        } catch (Exception var14) {
            var14.printStackTrace();
        }

        Bx6GScreenClient screen = new Bx6GScreenClient("MyScreen", new Bx6E());

        try {
            screen.connect(ledIp, Integer.parseInt(ledPort));
        } catch (Exception var13) {
            var13.printStackTrace();
        }

        ProgramBxFile pf = new ProgramBxFile("P000", screen.getProfile());
        TextCaptionBxArea area = new TextCaptionBxArea(0, 0, 256, 128, screen.getProfile());
        TextBxPage page = new TextBxPage();
        if (orderNo != null && !"".equals(orderNo)) {
            page.newLine("直发出库");
            page.newLine("-------------------------------");
            page.newLine("出库单号:" + orderNo);
            page.newLine("车牌号:" + carNo);
            page.newLine("托盘号:" + pallet.getPalletCode());
            page.newLine("商品名称:" + goodsName);
            page.newLine("商品数量:" + pallet.getAmount());
        } else {
            page.newLine("人工出库");
            page.newLine("-------------------------------");
            page.newLine("                        ");
            page.newLine("托盘号:" + pallet.getPalletCode());
            page.newLine("商品名称:" + goodsName);
            page.newLine("商品数量:" + pallet.getAmount());
        }

        page.setFont(new Font("宋体", 0, 15));
        page.setDisplayStyle(styles[2]);
        area.addPage(page);
        pf.addArea(area);

        try {
            screen.writeProgram(pf);
        } catch (Bx6GException var12) {
            var12.printStackTrace();
        }

        screen.disconnect();
    }

    public static void main(String[] args) {
        String ledIp = "10.1.1.242";
        String orderNo = "999999009";
        testLed(ledIp, orderNo);
    }
}
