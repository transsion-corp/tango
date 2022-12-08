package com.transsion.framework.tango.core.condition;

import com.transsion.framework.tango.common.property.Property;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/11
 * @Version 1.0
 **/
public class CompoundCondition extends Condition<List<Condition>> {
    @Override
    public Property getProperty() {
        return null;
    }

    @Override
    public List<Condition> getParam() {
        return null;
    }
}
