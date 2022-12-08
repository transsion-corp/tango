package com.transsion.framework.tango.core.engine;

import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.data.meta.DataMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public class DefaultCollectorBatch implements CollectorBatch {

    private final List<DataCollector> collectors;

    public DefaultCollectorBatch(DataCollector collector, int shard) {
        collectors = new ArrayList<>(shard);
        collectors.add(collector);
        if (shard > 1) {
            for (int i = 1; i < shard; i++) {
                collectors.add(collector.copy());
            }
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void collect(DataMeta meta, Data data) {
        // pick one
        DataCollector collector = collectors.get(0);
        collector.collect(meta, data);
    }

    @Override
    public void collect(DataMeta meta, List<Data> list) {

    }

    @Override
    public List<DataCollector> getCollectors() {
        return null;
    }
}
