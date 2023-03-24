package com.penghaisoft.framework.authorization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.penghaisoft.framework.authorization.constant.SecurityManagerConstant;
import com.penghaisoft.framework.authorization.datasource.IRealm;
import com.penghaisoft.framework.distributedsession.IHttpSession;
import com.penghaisoft.framework.exception.ParameterNullException;
import com.penghaisoft.framework.permissionmanagement.model.entity.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * 权限管理服务
 */
@Service
public class SecurityManager {

    @Autowired
    private IHttpSession httpSession;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IRealm realm;

    @Value("${spring.application.name}")
    private String sysName;

    /**
     * 登录时放入用户的权限信息
     * @param userId
     */
    public String login(String userId) {

        //如果传入userId为空则打印错误并返回
        if(userId == null || userId.length() == 0){
            new ParameterNullException(SecurityManagerConstant.ErrorMessage.PARAMETER_NULL_ERROR_MESSAGE + ",userId is null").printStackTrace();
            return null;
        }
        //将AuthorizationInfo对象存入session
        String token = httpSession.createSessionGetToken(userId);
        Map<String, List<Resources>> authorizationInfo = realm.getAuthorizationInfoByUserId(userId);
        String authorizationInfoString = JSON.toJSONString(authorizationInfo, SerializerFeature.DisableCircularReferenceDetect);
//        放入权限信息
        httpSession.setString(token,SecurityManagerConstant.AUTHORIZATIONINFO,authorizationInfoString);
//        放入用户的菜单
        if (authorizationInfo.containsKey("0")){
            List<Resources> resourceMenu = authorizationInfo.get("0");
            if (authorizationInfo.containsKey("3")){
                resourceMenu.addAll(authorizationInfo.get("3"));
            }
            httpSession.setString(token,SecurityManagerConstant.AUTHORIZATIONINFO_MENU,getUserMenuList(resourceMenu));
        }
        return token;
    }

    /**
     * 更新缓存中系统的资源信息
     */
    public void updateSysAuthorizationInfo() {
        List<Resources> allRes = realm.getAuthorizationInfoByType(null);
//        根据系统区分权限信息在缓存中的位置
        redisTemplate.opsForValue().set(SecurityManagerConstant.AUTHORIZATIONINFO_ALL + ":" + sysName, JSON.toJSONString(allRes));
    }

    /**
     * 登出
     */
    public void logout() {
        httpSession.abort();
    }

    /**
     * 获取用户的菜单列表
     * @return
     */
    public String getUserMenuList(){
        return httpSession.getString(SecurityManagerConstant.AUTHORIZATIONINFO_MENU);
    }

    /**
     * 组装用户菜单
     * @param allMenus
     * @return
     */
    private String getUserMenuList(List<Resources> allMenus){
//        父菜单
        List<Resources> parentMenus = new ArrayList<>();
//        刨除父菜单其他的菜单
        List<Resources> leftRes = new ArrayList<>();
        for (int i = 0; i < allMenus.size(); i++) {
            Resources menu = allMenus.get(i);
            if (menu.getParentId().equals(0)){
                parentMenus.add(menu);
            }else{
                leftRes.add(menu);
            }
        }
        Collections.sort(parentMenus);
        for (Resources parent:parentMenus) {
            List<Resources> childMenus = new ArrayList<>();
            for (Resources leftItem:leftRes) {
                if (leftItem.getParentId().equals(parent.getId())){
                    childMenus.add(leftItem);
                }
            }
            Collections.sort(childMenus);
            parent.setSubMenu(childMenus);
        }
        String result = JSON.toJSONString(parentMenus);
        return result;
    }


}
