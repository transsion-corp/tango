package com.transsion.framework.tango.metrics.aggregator;

import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.aggregator.Aggregator;
import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.window.TimeWindow;
import com.transsion.framework.tango.metrics.data.MetricPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author mengqi.lv
 * @Date 2022/9/15
 * @Version 1.0
 **/
public abstract class MetricsAggregator<T extends MetricPoint, R extends Data> implements Aggregator<T, R> {

    private List<Property> dimensions = new ArrayList<>();
    private TimeWindow timeWindow;
    private long millisInterval;

    private AtomicLong lastFlushTime = new AtomicLong(System.currentTimeMillis());

    public TimeWindow getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(TimeWindow timeWindow) {
        this.timeWindow = timeWindow;
    }

    public List<Property> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<Property> dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public R aggregate(T input) {
        doAggregate(input);
        long flushTime = lastFlushTime.get();
        long duration = input.getTimestamp() - flushTime;
        if (duration >= timeWindow.getMillisLength()) {
            // compareAndSet
            boolean success = lastFlushTime.compareAndSet(flushTime, input.getTimestamp());
            if (success) {
                return dumpAndReset();
            }
        }
        return null;
    }

    protected abstract void doAggregate(T input);

    protected abstract R dumpAndReset();
}
