package com.transsion.framework.tango.storage.clickhouse.view;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.storage.Query;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/26
 * @Version 1.0
 **/
public class ClickhouseQuery implements Query {

    private String db;
    private String table;
    private String sql;
    private Identifier viewId;
    private List<Property> properties;

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getSql() {
        return sql;
    }

    public void setViewId(Identifier viewId) {
        this.viewId = viewId;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public Identifier getViewId() {
        return viewId;
    }

    @Override
    public List<Property> getProperties() {
        return properties;
    }
}
