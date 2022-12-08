package com.transsion.framework.tango.core.aggregator;

import com.transsion.framework.tango.core.aggregation.Aggregation;

/**
 * @Author mengqi.lv
 * @Date 2022/9/7
 * @Version 1.0
 **/
public interface AggregatorGenerator {
    Aggregator newAggregator(Aggregation aggregation);
}
