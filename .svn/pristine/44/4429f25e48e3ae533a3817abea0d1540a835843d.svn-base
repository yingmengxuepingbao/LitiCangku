//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business.impl;

import com.penghaisoft.wms.basicmanagement.model.business.IDifferentBusinessService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DifferentBusinessFactory {
    @Autowired
    private final Map<String, IDifferentBusinessService> strategyMap = new ConcurrentHashMap();

    public DifferentBusinessFactory() {
    }

    public IDifferentBusinessService getIDifferentBusinessService(String serviceName) {
        return (IDifferentBusinessService)this.strategyMap.get(serviceName);
    }
}
