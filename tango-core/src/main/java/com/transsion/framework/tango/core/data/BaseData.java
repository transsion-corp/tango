package com.transsion.framework.tango.core.data;

import com.transsion.framework.tango.common.Identifier;

import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/9/6
 * @Version 1.0
 **/
public abstract class BaseData implements Data {

    private final Identifier id;
    private final Map<String, Object> properties;

    public BaseData(Identifier id, Map<String, Object> properties) {
        this.id = id;
        this.properties = properties;
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
