//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiRouteDataSource extends AbstractRoutingDataSource {
    private static final Logger log = LoggerFactory.getLogger(MultiRouteDataSource.class);
    @Autowired
    DataSourceContext dataSourceContext;

    public MultiRouteDataSource() {
    }

    protected Object determineCurrentLookupKey() {
        String ds = this.dataSourceContext.getDataSource();
        return ds;
    }
}
