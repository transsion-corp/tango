package com.transsion.framework.tango.metrics.sdk;

import com.transsion.framework.tango.core.aggregation.AggregateType;
import com.transsion.framework.tango.core.condition.Condition;
import com.transsion.framework.tango.core.expression.ExpressionType;
import com.transsion.framework.tango.metrics.data.MetricData;

import java.util.Map;

/**
 * Wrap of MetricsEngine to provide simple interfaces to users.
 * @Author mengqi.lv
 * @Date 2022/8/11
 * @Version 1.0
 **/
public interface MetricsSdk {

    void registerMetric(String group, String name, AggregateType type, String... tags);

    void addMetric(String group, String name, Map<String, String> properties, Number value);

    MetricData queryMetric(String group, String name, ExpressionType type, Condition condition,
                           long startTime, long endTime, String... groupbys);
}
