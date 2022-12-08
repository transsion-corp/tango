package com.transsion.framework.tango.core.expression;

import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.window.TimeWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/16
 * @Version 1.0
 **/
public abstract class SumExpression<T extends Number> implements AggregateExpression {
    private List<Property> dimensions;
    private TimeWindow timeWindow;
    private Property sourceProperty;
    private Property valueProperty;

    public void setDimensions(List<Property> dimensions) {
        this.dimensions = dimensions;
    }

    public void setTimeWindow(TimeWindow timeWindow) {
        this.timeWindow = timeWindow;
    }

    public void setSourceProperty(Property sourceProperty) {
        this.sourceProperty = sourceProperty;
    }

    public void setValueProperty(Property valueProperty) {
        this.valueProperty = valueProperty;
    }

    @Override
    public TimeWindow getTimeWindow() {
        return timeWindow;
    }

    @Override
    public List<Property> getDimensions() {
        return dimensions;
    }

    @Override
    public Property getSourceProperty() {
        return sourceProperty;
    }

    @Override
    public Property getValueProperty() {
        return valueProperty;
    }

    @Override
    public List<Property> getProperties() {
        List<Property> properties = new ArrayList<>();
        if (dimensions != null) {
            properties.addAll(dimensions);
        }
        if (valueProperty != null) {
            properties.add(valueProperty);
        }
        return properties;
    }

    @Override
    public ExpressionType getType() {
        return ExpressionType.SUM;
    }

    public static class LongSumExpression extends SumExpression<Long> {

    }
}
