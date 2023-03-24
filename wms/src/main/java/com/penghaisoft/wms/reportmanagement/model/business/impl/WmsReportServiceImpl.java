//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.reportmanagement.model.business.impl;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsLocationStereoscopicMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.logmanagement.model.dao.WmsSendLogMapper;
import com.penghaisoft.wms.reportmanagement.model.business.IWmsReportService;
import com.penghaisoft.wms.reportmanagement.model.dto.InOutSummaryDto;
import com.penghaisoft.wms.reportmanagement.model.dto.ReportShowData;
import com.penghaisoft.wms.reportmanagement.model.dto.StorageStatisticCount;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOutTemporaryMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsTaskExecutionLogMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import java.text.SimpleDateFormat;
import java.util.*;

import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("iWmsReportService")
public class WmsReportServiceImpl implements IWmsReportService {
    private static final Logger log = LoggerFactory.getLogger(WmsReportServiceImpl.class);
    @Autowired
    private WmsPalletMapper wmsPalletMapper;
    @Autowired
    private WmsOutTemporaryMapper wmsOutTemporaryMapper;
    @Autowired
    private WmsSendLogMapper wmsSendLogMapper;
    @Resource
    private WmsLocationStereoscopicMapper wmsLocationStereoscopicMapper;
    @Resource
    private WmsTaskExecutionLogMapper wmsTaskExecutionLogMapper;

    public WmsReportServiceImpl() {
    }

    public StorageStatisticCount queryStorageStatisticCount() {
        StorageStatisticCount storageStatisticCount = new StorageStatisticCount();
        List<WmsLocationStereoscopic> locGroups = this.wmsLocationStereoscopicMapper.groupByUseStatus((WmsLocationStereoscopic)null);
        int isUsedLocCount = 0;
        int notUsedLocCount = 0;
        Iterator var5 = locGroups.iterator();

        while(var5.hasNext()) {
            WmsLocationStereoscopic tmp = (WmsLocationStereoscopic)var5.next();
            int count = Integer.parseInt(tmp.getUserDefined1());
            if ("0".equals(tmp.getUseStatus())) {
                notUsedLocCount = count;
            } else {
                isUsedLocCount += count;
            }
        }

        storageStatisticCount.setIsUsedLocCount(isUsedLocCount);
        storageStatisticCount.setNotUsedLocCount(notUsedLocCount);
        String minBatchNo = this.wmsPalletMapper.queryMinBatchNo();
        if (!"".equals(minBatchNo) && null != minBatchNo) {
            WmsPallet cond = new WmsPallet();
            cond.setBatchNo(minBatchNo);
            List<WmsPallet> wmsPallets = this.wmsPalletMapper.queryListStereoscopicHz((Pager)null, cond);
            storageStatisticCount.setLongGoodsInfo(wmsPallets);
        }

        return storageStatisticCount;
    }

    public ReportShowData queryLatelySendData(int days) {
        ReportShowData reportShowData = new ReportShowData();
        List<InOutSummaryDto> inData1 = this.wmsTaskExecutionLogMapper.queryTaskDays("10", days);
        List<InOutSummaryDto> inData2 = this.wmsTaskExecutionLogMapper.queryTaskDays("50", days);
        List<InOutSummaryDto> inData =new ArrayList<>();
        if(inData1!=null && inData1.size()>0){
            for (InOutSummaryDto i:inData1){
                inData.add(i);
            }

        }
        if(inData2!=null && inData2.size()>0){
            for (InOutSummaryDto i:inData2){
                inData.add(i);
            }

        }
        Map<String, Integer> inMap = new HashMap();
        Iterator var5 = inData.iterator();

        while(var5.hasNext()) {
            InOutSummaryDto in = (InOutSummaryDto)var5.next();
            inMap.put(in.getDay(), in.getCount());
        }

        List<InOutSummaryDto> outData = this.wmsTaskExecutionLogMapper.queryTaskDays("20", days);
        Map<String, Integer> outMap = new HashMap();
        Iterator var7 = outData.iterator();

        while(var7.hasNext()) {
            InOutSummaryDto out = (InOutSummaryDto)var7.next();
            outMap.put(out.getDay(), out.getCount());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date = Calendar.getInstance();
        date.add(5, -7);
        String[] name = new String[7];
        reportShowData.setName(name);
        Integer[] inSevenDays = new Integer[7];
        Integer[] outSevenDays = new Integer[7];

        for(int i = 0; i < 7; ++i) {
            String batchNo = sdf.format(date.getTime());
            date.add(5, 1);
            name[i] = batchNo;
            if (inMap.containsKey(batchNo)) {
                inSevenDays[i] = (Integer)inMap.get(batchNo);
            } else {
                inSevenDays[i] = 0;
            }

            if (outMap.containsKey(batchNo)) {
                outSevenDays[i] = (Integer)outMap.get(batchNo);
            } else {
                outSevenDays[i] = 0;
            }
        }

        reportShowData.setInValue(inSevenDays);
        reportShowData.setOutValue(outSevenDays);
        return reportShowData;
    }


    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar date = Calendar.getInstance();
        date.add(5, -7);

        for(int i = 0; i < 7; ++i) {
            String createMax = sdf.format(date.getTime());
            System.out.println(createMax);
            date.add(5, 1);
        }

    }
    //========现场修改，将收发货改成出入库==================
    /**
     *功能描述:
     * @params
     * @return com.penghaisoft.wms.reportmanagement.model.dto.ReportShowData
     */
    @Override
   public  ReportShowData getChuRuSendData(){
       Integer[] inSevenDays = new Integer[7];
       Integer[] outSevenDays = new Integer[7];
       WmsTaskExecutionLog wmsTaskExecutionLog =new WmsTaskExecutionLog();

       ReportShowData reportShowData = new ReportShowData();
       List list =new ArrayList();
       list.add("10");
       list.add("50");
       wmsTaskExecutionLog.setTaskTypeList(list);
       List<InOutSummaryDto> inData = this.wmsTaskExecutionLogMapper.groupByUseStatus(wmsTaskExecutionLog);
       Map<String, Integer> inMap = new HashMap();
       Iterator var5 = inData.iterator();

       while(var5.hasNext()) {
           InOutSummaryDto in = (InOutSummaryDto)var5.next();
           inMap.put(in.getDay(), in.getCount());
       }
       list=new ArrayList();
       list.add("20");
       wmsTaskExecutionLog.setTaskTypeList(list);
       List<InOutSummaryDto> outData = this.wmsTaskExecutionLogMapper.groupByUseStatus(wmsTaskExecutionLog);

       Map<String, Integer> outMap = new HashMap();
       Iterator var7 = outData.iterator();

       while(var7.hasNext()) {
           InOutSummaryDto out = (InOutSummaryDto)var7.next();
           outMap.put(out.getDay(), out.getCount());
       }

       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Calendar date = Calendar.getInstance();
       date.add(5, -7);
       String[] name = new String[7];
       reportShowData.setName(name);

       for(int i = 0; i < 7; ++i) {
           String time = sdf.format(date.getTime());
           date.add(5, 1);
           name[i] = time;
           if (inMap.containsKey(time)) {
               inSevenDays[i] = (Integer)inMap.get(time);
           } else {
               inSevenDays[i] = 0;
           }

           if (outMap.containsKey(time)) {
               outSevenDays[i] = (Integer)outMap.get(time);
           } else {
               outSevenDays[i] = 0;
           }
       }
        reportShowData.setInValue(inSevenDays);
        reportShowData.setOutValue(outSevenDays);
        return reportShowData;
    }
}
