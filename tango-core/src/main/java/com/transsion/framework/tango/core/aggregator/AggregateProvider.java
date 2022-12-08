package com.transsion.framework.tango.core.aggregator;

import com.transsion.framework.tango.core.data.meta.DataMeta;

/**
 * @Author mengqi.lv
 * @Date 2022/9/7
 * @Version 1.0
 **/
public interface AggregateProvider {
    Aggregator getAggregator(DataMeta meta);
}
