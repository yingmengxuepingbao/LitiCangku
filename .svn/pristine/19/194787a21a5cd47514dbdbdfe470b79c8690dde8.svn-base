//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceContext {
    private static final Logger log = LoggerFactory.getLogger(DataSourceContext.class);
    private static final ThreadLocal<String> contextHolder = new ThreadLocal();

    public DataSourceContext() {
    }

    public void setDataSource(String dsBeanName) {
        contextHolder.set(dsBeanName);
    }

    public String getDataSource() {
        return (String)contextHolder.get();
    }

    public void clearDataSource() {
        log.info("释放数据源：");
        contextHolder.remove();
    }
}
