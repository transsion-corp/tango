package com.transsion.framework.tango.core.data.meta;

import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.condition.Condition;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface ContextDataMeta extends DataMeta {

    Condition getFilterCondition();

    List<Property> getProperties();
}
