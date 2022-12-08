package com.transsion.framework.tango.core.aggregation;

import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.window.TimeWindow;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/10
 * @Version 1.0
 **/
public interface Aggregation {

    List<Property> getDimensions();

    TimeWindow getTimeWindow();

    AggregateType getAggregateType();

    Property getValueProperty();
}
