package com.transsion.framework.tango.metrics.data.meta;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.property.NamedType;
import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.aggregation.AggregateType;
import com.transsion.framework.tango.core.aggregation.Aggregation;
import com.transsion.framework.tango.core.aggregation.DefaultAggregation;
import com.transsion.framework.tango.core.condition.Condition;
import com.transsion.framework.tango.core.data.meta.ContextDataMeta;
import com.transsion.framework.tango.core.window.TimeWindow;
import com.transsion.framework.tango.metrics.data.Unit;
import com.transsion.framework.tango.metrics.data.examplar.ExamplarType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/10
 * @Version 1.0
 **/
public class MetricMeta implements ContextDataMeta {
    private Identifier id;
    private Unit unit;
    private Aggregation aggregation;
    private Condition condition;
    private List<Property> dimensions;
    private long ttl;
    private ExamplarType examplarType = ExamplarType.WINDOW_FIX;

    public Unit getUnit() {
        return unit;
    }

    public Aggregation getAggregation() {
        return aggregation;
    }

    @Override
    public Condition getFilterCondition() {
        return condition;
    }

    @Override
    public List<Property> getProperties() {
        return dimensions;
    }

    @Override
    public long ttl() {
        return 0;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    public static final class MetricMetaBuilder {
        private Unit unit;
        private AggregateType aggregateType;
        private TimeWindow window;
        private Condition condition;
        private List<Property> dimensions;
        private long ttl;
        private Identifier id;

        public static MetricMetaBuilder builder() {
            return new MetricMetaBuilder();
        }

        public MetricMetaBuilder unit(Unit unit) {
            this.unit = unit;
            return this;
        }

        public MetricMetaBuilder id(String group, String name) {
            this.id = new Identifier(group, name);
            return this;
        }

        public MetricMetaBuilder dimension(Property dimension) {
            if (this.dimensions == null) {
                this.dimensions = new ArrayList<>(4);
            }
            this.dimensions.add(dimension);
            return this;
        }

        public MetricMetaBuilder aggregationType(AggregateType type) {
            this.aggregateType = type;
            return this;
        }

        public MetricMetaBuilder window(TimeWindow window) {
            this.window = window;
            return this;
        }

        public MetricMetaBuilder condition(Condition condition) {
            this.condition = condition;
            return this;
        }

        public MetricMetaBuilder dimensions(List<Property> dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        public MetricMetaBuilder ttl(long ttl) {
            this.ttl = ttl;
            return this;
        }

        public MetricMeta build() {
            MetricMeta metricMeta = new MetricMeta();
            metricMeta.aggregation = DefaultAggregation.DefaultAggregationBuilder.builder()
                    .dimensions(dimensions)
                    .dataWindow(window)
                    .type(aggregateType)
                    .build();
            metricMeta.unit = this.unit;
            metricMeta.dimensions = this.dimensions;
            metricMeta.condition = this.condition;
            metricMeta.id = this.id;
            metricMeta.ttl = this.ttl;
            return metricMeta;
        }
    }
}
