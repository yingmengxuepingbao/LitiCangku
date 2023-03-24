package com.penghaisoft.wms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.wms.util.ConstantUtil;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description
 * @Author zhangxin
 * @Date
 **/
public class TestMain {
    public static void main(String[] args) {
       /* //成功 且无货，下发入库任务
        JSONObject ruku_jsonObject =new JSONObject();
        Map<String,Object> map =new HashMap<>();
        //组号
        ruku_jsonObject.put("groupId", ConstantUtil.UUID_GroupId());
        //下发时间
        ruku_jsonObject.put("msgTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //优先级
        ruku_jsonObject.put("priorityCode", "");
        //仓库编码
        ruku_jsonObject.put("warehouse", "L-NH01");
        //任务单号
        map.put("taskId", "2222");
        //任务类型
        map.put("taskType", "0");
        //任务起点
        map.put("startNode", "2001");
        //任务终点
        map.put("endNode", "2222");
        //托盘编号
        map.put("barCode","11111");
        map.put("order", 1);
        //tasks
        JSONArray jsonArray = new JSONArray();
        jsonArray.add( new JSONObject(map));
        ruku_jsonObject.put("tasks", jsonArray);

        System.out.println(ruku_jsonObject.toString());*/

        List zhanyongList =new ArrayList();
        zhanyongList.add("1");
        zhanyongList.add("2");
        zhanyongList.add("3");
        zhanyongList.add("1");
        zhanyongList.add("2");
        zhanyongList.add("2");
        zhanyongList.add("2");
        for(int i=0; i<zhanyongList.size();i++ ){
            for(int j=0;j<zhanyongList.size();j++){
                if(i!=j && zhanyongList.get(i) == zhanyongList.get(j)) {
                    zhanyongList.remove(zhanyongList.get(j));
                }
            }
        }
        System.out.println(zhanyongList.toString());
        System.out.println(zhanyongList.toString());
    }
}
