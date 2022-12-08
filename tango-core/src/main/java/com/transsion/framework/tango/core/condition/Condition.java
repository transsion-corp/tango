package com.transsion.framework.tango.core.condition;

import com.transsion.framework.tango.common.property.Property;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public abstract class Condition<T> {
    private Property property;

    public void setProperty(Property property) {
        this.property = property;
    }

    public Property getProperty() {
        return property;
    }

    public abstract T getParam();
}
