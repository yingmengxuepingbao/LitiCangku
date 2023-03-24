package com.penghaisoft.framework.authorization.datasource.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.authorization.datasource.IRealm;
import com.penghaisoft.framework.permissionmanagement.model.dao.ResourcesMapper;
import com.penghaisoft.framework.permissionmanagement.model.entity.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class Realm implements IRealm {
    @Autowired
    private ResourcesMapper resourcesMapper;


    @Override
    public Map<String, List<Resources>> getAuthorizationInfoByUserId(String userId) {
//        按照类别区分的资源
        Map<String, List<Resources>> result = new HashMap<>();
//         所有的用户资源
        List<Resources> allRes = resourcesMapper.selectUserResources(userId);

        if (allRes!=null && allRes.size()>0){

            for (Resources res:allRes) {
                String tmpResType = res.getResType();
                if (result.containsKey(tmpResType)){
                    result.get(tmpResType).add(res);
                }else {
                    List<Resources> newList = new ArrayList<>();
                    newList.add(res);
                    result.put(tmpResType,newList);
                }
            }

        }
        return result;
    }

    /**
     * @param resTypeSet = null时获取所有，不为null 根据类型过滤
     * @return
     */
    @Override
    public List<Resources> getAuthorizationInfoByType(Set<String> resTypeSet) {
//        指定类型的资源
        List<Resources> targetList = new ArrayList<>();
        //         所有的用户资源
        List<Resources> allRes = resourcesMapper.selectAllResources();
        if (resTypeSet==null){
            return allRes;
        }else {
            for (Resources res:allRes) {
                String resType = res.getResType();
                if (resTypeSet.contains(resType)){
                    targetList.add(res);
                }
            }
        }
        return targetList;
    }
}
