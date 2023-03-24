//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.nuohua.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DifferentBusinessNHFactory {
    @Autowired
    private final Map<String, DifferentBusinessNHService> strategyMap = new ConcurrentHashMap();

    public DifferentBusinessNHFactory() {
    }

    public DifferentBusinessNHService getDifferentBusinessNHService(String serviceName) {
        return (DifferentBusinessNHService)this.strategyMap.get(serviceName);
    }
}
