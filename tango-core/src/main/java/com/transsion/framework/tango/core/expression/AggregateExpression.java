package com.transsion.framework.tango.core.expression;

import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.window.TimeWindow;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/9/1
 * @Version 1.0
 **/
public interface AggregateExpression extends Expression {

    TimeWindow getTimeWindow();

    List<Property> getDimensions();

    Property getSourceProperty();

    Property getValueProperty();
}
