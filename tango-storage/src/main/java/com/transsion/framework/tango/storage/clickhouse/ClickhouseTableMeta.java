package com.transsion.framework.tango.storage.clickhouse;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.common.property.PropertyEnum;
import com.transsion.framework.tango.core.data.meta.DataMeta;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/10/24
 * @Version 1.0
 **/
public class ClickhouseTableMeta implements DataMeta {

    private final String db;
    private final String table;
    private Identifier id;
    private int ttlSeconds = -1;
    private String storageCluster = "default";
    private String storagePolicy = "default";

    private List<Property> properties;
    private List<Property> orderBy;
    private List<PropertyEnum> propertyEnums;

    public ClickhouseTableMeta(String db, String table) {
        this.db = db;
        this.table = table;
        this.id = new Identifier(db, table);
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public long ttl() {
        return ttlSeconds;
    }

    public String getDb() {
        return db;
    }

    public void setTtlSeconds(int ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }

    public void setStorageCluster(String storageCluster) {
        this.storageCluster = storageCluster;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<Property> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(List<Property> orderBy) {
        this.orderBy = orderBy;
    }

    public List<PropertyEnum> getPropertyEnums() {
        return propertyEnums;
    }

    public void setPropertyEnums(List<PropertyEnum> propertyEnums) {
        this.propertyEnums = propertyEnums;
    }

    public String getTable() {
        return table;
    }

    public String getStorageCluster() {
        return storageCluster;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public String getStoragePolicy() {
        return storagePolicy;
    }

    public void setStoragePolicy(String storagePolicy) {
        this.storagePolicy = storagePolicy;
    }
}
