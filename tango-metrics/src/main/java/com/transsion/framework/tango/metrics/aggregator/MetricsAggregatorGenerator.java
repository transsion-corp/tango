package com.transsion.framework.tango.metrics.aggregator;

import com.transsion.framework.tango.core.aggregation.AggregateType;
import com.transsion.framework.tango.core.aggregation.Aggregation;
import com.transsion.framework.tango.core.aggregator.Aggregator;
import com.transsion.framework.tango.core.aggregator.AggregatorGenerator;

/**
 * @Author mengqi.lv
 * @Date 2022/9/15
 * @Version 1.0
 **/
public class MetricsAggregatorGenerator implements AggregatorGenerator {
    @Override
    public Aggregator newAggregator(Aggregation aggregation) {
        if (aggregation.getAggregateType().equals(AggregateType.LONG_SUM)) {
            LongSumAggregator aggregator = new LongSumAggregator();
            aggregator.setDimensions(aggregation.getDimensions());
            aggregator.setTimeWindow(aggregation.getTimeWindow());
            return aggregator;
        }
        return null;
    }
}
