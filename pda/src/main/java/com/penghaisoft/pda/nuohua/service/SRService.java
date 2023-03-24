package com.penghaisoft.pda.nuohua.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author hym
 * @create 2022/10/9 11:34
 */
public interface SRService {

    /**
     * 四向车任务下发操作
     * @param map
     * @return
     */
    public JSONObject sendTask(JSONObject map);
}
