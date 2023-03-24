package com.penghaisoft.pda.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("wmsCommonService")
public class WmsCommonServiceImpl implements IWmsCommonService {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat sdfShort = new SimpleDateFormat("yyMMdd");

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取任务号 年月日+任务类型+5位序列号
     * 使用基本类型，效率最高
     * @param taskType 任务类型
     * @param size 个数>0
     * @return
     */
    @Override
    public long[] getTaskIds(Constant.TaskType taskType, int size) {
        if (size > 0){

            long[] taskIds = new long[size];
//        年月日
            String datePrefix = sdfShort.format(new Date());
//            年月日+类型
            long taskPrefix = Integer.parseInt(datePrefix)*100 + taskType.getTaskType();
//            redis k
            String key = Constant.ConfigInfo.SEQ_PREFIX_TASK + datePrefix;
//            一次性增加size个数
            Long maxVal = stringRedisTemplate.boundValueOps(key).increment(size);
            int j = 0;
            for (int i = maxVal.intValue() - size + 1; i <= maxVal; i++) {
//                流水号为5位数
                long val = taskPrefix * 100000 + i;
                taskIds[j] = val;
                j++;
            }
            return taskIds;
        }else{
            return null;
        }

    }

    /**
     * 根据订单类型生成流水号
     * 前缀+年月日+序列号
     * 在定时任务中统一清除
     * @param orderType
     * @return
     */
    @Override
    public String getOrderNoByType(String orderType) {
        String orderNo = null;
        if (null != orderType) {
            orderType = orderType.toUpperCase();
            String datePrefix = sdf.format(new Date());
            String key = Constant.ConfigInfo.SEQ_PREFIX + orderType + ":" + datePrefix;
            Long maxVal = stringRedisTemplate.boundValueOps(key).increment();
            String no = String.valueOf(maxVal);
//            默认三位长度，不够再加
            while (no.length() < 3) {
                no = "0" + no;
            }
            orderNo = orderType + datePrefix + no;
        }
        return orderNo;
    }

    /**
     * @Description
     * @Author  得到4位的流水号
     * @Date 2020/2/14 16:13
     * @Param
     * @return
     **/
    @Override
    public List<String> getSeqLen4(String seqName, int size) {
        List<String> seqList = new ArrayList<>();
        if (size > 0) {
            String datePrefix = sdf.format(new Date());
            String key = Constant.ConfigInfo.SEQ_PREFIX + datePrefix + seqName;
            Long maxVal = stringRedisTemplate.boundValueOps(key).increment(size);
            for (int i = maxVal.intValue() - size + 1; i <= maxVal; i++) {
                String tmp = String.valueOf(i);
                while (tmp.length() < 4) {
                    tmp = "0" + tmp;
                }
                seqList.add(tmp);
            }
            removeLastKey(seqName);
        }
        return seqList;
    }
    /**
     * @param @param seqName
     * @return void
     * @throws
     * @Title: removeLastKey
     * @Description: 移除前几天的key
     * @author zhangxu
     * @2017年9月28日:2017年9月28日:下午2:08:10
     * @version V0.1
     */
    private void removeLastKey(String seqName) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String datePrefix = "";
        String key = "";
        List<String> keys = new ArrayList<>();
//		fix 如果昨天没产生相关序列，则不会删除
        for (int i = 0; i < 5; i++) {
            cal.add(Calendar.DATE, -1);
            datePrefix = sdf.format(cal.getTime());
            key = Constant.ConfigInfo.SEQ_PREFIX + datePrefix + seqName;
            keys.add(key);
        }
        stringRedisTemplate.delete(keys);
    }

}
