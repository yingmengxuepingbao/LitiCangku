//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.common.service.impl;

import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service("wmsCommonService")
public class WmsCommonServiceImpl implements IWmsCommonService {
    private static final Logger log = LoggerFactory.getLogger(WmsCommonServiceImpl.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat sdfShort = new SimpleDateFormat("yyMMdd");
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public WmsCommonServiceImpl() {
    }

    public long[] getTaskIds(TaskType taskType, int size) {
        if (size <= 0) {
            return null;
        } else {
            long[] taskIds = new long[size];
            String datePrefix = sdfShort.format(new Date());
            long taskPrefix = (long)(Integer.parseInt(datePrefix) * 100) + taskType.getTaskType();
            String key = "wms:seq:task:" + datePrefix;
            Long maxVal = this.stringRedisTemplate.boundValueOps(key).increment((long)size);
            int j = 0;

            for(int i = maxVal.intValue() - size + 1; (long)i <= maxVal; ++i) {
                long val = taskPrefix * 100000L + (long)i;
                taskIds[j] = val;
                ++j;
            }

            return taskIds;
        }
    }

    public String getOrderNoByType(String orderType) {
        String orderNo = null;
        if (null != orderType) {
            orderType = orderType.toUpperCase();
            String datePrefix = sdf.format(new Date());
            String key = "wms:seq:" + orderType + ":" + datePrefix;
            Long maxVal = this.stringRedisTemplate.boundValueOps(key).increment();

            String no;
            for(no = String.valueOf(maxVal); no.length() < 3; no = "0" + no) {
                ;
            }

            orderNo = orderType + datePrefix + no;
        }

        return orderNo;
    }
}
