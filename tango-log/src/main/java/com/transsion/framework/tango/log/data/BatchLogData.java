package com.transsion.framework.tango.log.data;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.data.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/10/21
 * @Version 1.0
 **/
public class BatchLogData implements Data {
    private String biz;
    private String scenario;
    private Identifier id;
    private List<Property> fields;
    private List<Object[]> data;
    private Map<String, Object> properties;

    public BatchLogData(String biz, String scenario) {
        this.biz = biz;
        this.scenario = scenario;
        this.id = new Identifier(biz, scenario);
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public Object getPropertyValue(String propertyName) {
        return Data.super.getPropertyValue(propertyName);
    }

    @Override
    public Object getPropertyValue(String propertyName, String defaultValue) {
        return Data.super.getPropertyValue(propertyName, defaultValue);
    }

    @Override
    public Identifier getId() {
        return null;
    }

    public List<Property> getFields() {
        return fields;
    }

    public void setFields(List<Property> fields) {
        this.fields = fields;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public List<Object[]> getData() {
        return data;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }
}
