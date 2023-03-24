package com.penghaisoft.framework.authorization.datasource;


import com.penghaisoft.framework.permissionmanagement.model.entity.Resources;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRealm {

    public Map<String, List<Resources>> getAuthorizationInfoByUserId(String userId);

    public List<Resources> getAuthorizationInfoByType(Set<String> resTypeSet);
}
