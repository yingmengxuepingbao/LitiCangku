package com.penghaisoft.wcs.common.service.impl;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.wcs.common.service.IWcsCommonService;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("wcsCommonService")
public class WcsCommonServiceImpl implements IWcsCommonService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 获取越库的地址信息
     *
     * @return
     */
    @Override
    public int[] getCrossAddress() {
//        todo 要等线体地址确定
        int[] crossArr = new int[6];
//        越库时候的堆垛机取货货位信息-入库线
        crossArr[0] = 88;//编码
        crossArr[1] = 88;//X
        crossArr[2] = 88;//Z
//        越库时候的堆垛机放货货位信息
        crossArr[3] = 99;//编码
        crossArr[4] = 99;//X
        crossArr[5] = 99;//Z
        return crossArr;
    }


    /**
     * 根据库位获取入库缓冲区线体地址
     * 一个入库口可能对应多个库位
     * 但是一个库位理论上是只有一个入库口的
     *
     * @param locationId
     * @return
     */
    @Override
    public Integer getInBufferAddressByLocation(int locationId) {
        return 1001;
    }


    /**
     * 下给硬件的任务号，不超过10位
     * 获取任务号 月(不到10补9)日+5位序列号
     * eg 931300001 代表3月13号单子
     *
     * @return
     */
    @Override
    public int[] getTaskNo(int size) {
        if (size > 0) {
            int[] taskNos = new int[size];
            //        月日,如果是10月之前0替换为9
            String datePrefix = sdfYM.format(new Date());
            char[] dateCharArr = datePrefix.toCharArray();
            if (dateCharArr[0] == '0') {
                dateCharArr[0] = '9';
            }
            datePrefix = String.valueOf(dateCharArr);
            int dataPreFixInt = Integer.parseInt(datePrefix);
//            月日+类型
//            redis k
            String key = WCS_TASK_NO_SEQ_PREFIX + datePrefix;
//            一次性增加size个数
            Long maxVal = stringRedisTemplate.boundValueOps(key).increment(size);
            int j = 0;
            for (int i = maxVal.intValue() - size + 1; i <= maxVal; i++) {
//                流水号为5位数
                int val = dataPreFixInt * 100000 + i;
                taskNos[j] = val;
                j++;
            }
            return taskNos;
        } else {
            return null;
        }
    }

    /**
     * 获取任务号 年月日+任务类型+5位序列号
     * 使用基本类型，效率最高
     *
     * @param taskType 任务类型
     * @param size     个数>0
     * @return
     */
    @Override
    public long[] getTaskIds(Constant.TaskType taskType, int size) {
        if (size > 0) {
            SimpleDateFormat sdfShort = new SimpleDateFormat("yyMMdd");
            long[] taskIds = new long[size];
//        年月日
            String datePrefix = sdfShort.format(new Date());
//            年月日+类型
            long taskPrefix = Integer.parseInt(datePrefix) * 100 + taskType.getTaskType();
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
        } else {
            return null;
        }

    }

    /**
     * @param taskId
     * @return java.lang.String
     * zhangx
     * @Description 根据taskId获取任务类型
     * @Date 2020/7/28 10:13
     **/
    @Override
    public String getTaskTypeByTaskId(Long taskId) {
        String type = taskId.toString().substring(6, 8);
        return type;
    }

    /**
     * @param target
     * @return boolean
     * @Description 判断当前地址是否合法的出库口
     * @Date 2020/7/31 10:23
     **/
    @Override
    public boolean isOutLine(int target) {
        if (2001==target||2002==target||2003==target||3001==target){
            return true;
        }
        return false;
    }

    @Override
    public List<String> getSeqLen4(String seqName, int size) {
        List<String> seqList = new ArrayList<>();
        if (size > 0) {
            String datePrefix = sdf.format(new Date());
            String key = WCS_TASK_NO_SEQ_PREFIX + datePrefix + seqName;
            Long maxVal = stringRedisTemplate.boundValueOps(key).increment(size);
            for (int i = maxVal.intValue() - size + 1; i <= maxVal; i++) {
                String tmp = String.valueOf(i);
                while (tmp.length() < 4) {
                    tmp = "0" + tmp;
                }
                seqList.add(tmp);
            }
//            removeLastKey(seqName);
        }
        return seqList;
    }

}
