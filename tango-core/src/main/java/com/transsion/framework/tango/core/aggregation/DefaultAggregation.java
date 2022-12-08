package com.transsion.framework.tango.core.aggregation;

import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.window.TimeWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/16
 * @Version 1.0
 **/
public class DefaultAggregation implements Aggregation {
    private List<Property> dimensions;
    private TimeWindow timeWindow;
    private AggregateType type;
    private Property valueProperty;

    @Override
    public List<Property> getDimensions() {
        return dimensions;
    }

    @Override
    public TimeWindow getTimeWindow() {
        return timeWindow;
    }

    @Override
    public AggregateType getAggregateType() {
        return type;
    }

    @Override
    public Property getValueProperty() {
        return valueProperty;
    }


    public static final class DefaultAggregationBuilder {
        private List<Property> dimensions;
        private TimeWindow timeWindow;
        private AggregateType type;

        private DefaultAggregationBuilder() {
        }

        public static DefaultAggregationBuilder builder() {
            return new DefaultAggregationBuilder();
        }

        public DefaultAggregationBuilder dimensions(List<Property> dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        public DefaultAggregationBuilder dimension(Property dimension) {
            if (this.dimensions == null) {
                this.dimensions = new ArrayList<>(4);
            }
            this.dimensions.add(dimension);
            return this;
        }

        public DefaultAggregationBuilder dataWindow(TimeWindow timeWindow) {
            this.timeWindow = timeWindow;
            return this;
        }

        public DefaultAggregationBuilder type(AggregateType type) {
            this.type = type;
            return this;
        }

        public DefaultAggregation build() {
            DefaultAggregation defaultAggregation = new DefaultAggregation();
            defaultAggregation.type = this.type;
            defaultAggregation.valueProperty = new Property("value", type.valueType);
            defaultAggregation.timeWindow = this.timeWindow;
            defaultAggregation.dimensions = this.dimensions;
            return defaultAggregation;
        }
    }
}
