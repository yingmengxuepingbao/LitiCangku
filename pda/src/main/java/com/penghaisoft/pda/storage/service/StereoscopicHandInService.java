package com.penghaisoft.pda.storage.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author
 */
public interface StereoscopicHandInService {
    /**
     * 下发速锐入库任务
     * @param map
     * @return
     */
    public JSONObject startTask(JSONObject map);

    /**
     * 下发WMS出库任务
     * @param map
     * @return
     */
    public JSONObject outTask(JSONObject map);

    /**
     * 下发速锐更改库口测试
     */
    public JSONObject changeWarehouse(JSONObject map);
}
