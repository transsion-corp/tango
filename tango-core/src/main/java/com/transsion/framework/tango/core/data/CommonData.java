package com.transsion.framework.tango.core.data;

import com.transsion.framework.tango.common.Identifier;

import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public class CommonData implements Data {

    protected Map<String, Object> properties;
    protected Identifier id;

    public CommonData() {
    }

    public CommonData(Identifier id, Map<String, Object> properties) {
        this.properties = properties;
        this.id = id;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public Identifier getId() {
        return id;
    }
}
