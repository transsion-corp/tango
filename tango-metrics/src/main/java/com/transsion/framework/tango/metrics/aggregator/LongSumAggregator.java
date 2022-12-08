package com.transsion.framework.tango.metrics.aggregator;

import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.metrics.data.MetricAggregateValue;
import com.transsion.framework.tango.metrics.data.MetricPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author mengqi.lv
 * @Date 2022/9/15
 * @Version 1.0
 **/
public class LongSumAggregator extends MetricsAggregator<MetricPoint, MetricAggregateValue> {

    private volatile ConcurrentHashMap<IdentifierTags, LongAdder> dimensionSum = new ConcurrentHashMap<>();

    @Override
    protected void doAggregate(MetricPoint input) {
        StringBuilder sb = new StringBuilder();
        for (Property p : getDimensions()) {
            sb.append(input.getPropertyValue(p.getName(), "")).append("@");
        }
        String s = sb.toString();
        dimensionSum.computeIfAbsent(new IdentifierTags(s, input.getProperties()), k -> new LongAdder()).add(input.getValue().longValue());
    }

    @Override
    protected MetricAggregateValue dumpAndReset() {
        ConcurrentHashMap<IdentifierTags, LongAdder> old = dimensionSum;
        dimensionSum = new ConcurrentHashMap<>();

        MetricAggregateValue points = new MetricAggregateValue();
        points.setTimestamp(System.currentTimeMillis());
        List<MetricAggregateValue.DimensionData> dataList = new ArrayList<>(old.size());
        for (Map.Entry<IdentifierTags, LongAdder> entry : old.entrySet()) {
            MetricAggregateValue.DimensionData data = new MetricAggregateValue.DimensionData();
            data.setProperties(entry.getKey().getProperties());
            data.setValue(entry.getValue().longValue());
            dataList.add(data);
        }
        points.setData(dataList);
        return points;
    }
}
