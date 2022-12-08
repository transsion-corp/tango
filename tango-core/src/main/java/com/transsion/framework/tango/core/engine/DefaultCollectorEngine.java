package com.transsion.framework.tango.core.engine;

import com.transsion.framework.tango.common.Utility;
import com.transsion.framework.tango.core.aggregator.AggregateProvider;
import com.transsion.framework.tango.core.aggregator.AggregatorGenerator;
import com.transsion.framework.tango.core.aggregator.DefaultAggregateProvider;
import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.data.meta.DataMeta;
import com.transsion.framework.tango.core.data.meta.DataMetaRegistry;
import com.transsion.framework.tango.core.storage.Storage;
import com.transsion.framework.tango.core.storage.StorageAdapter;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public class DefaultCollectorEngine implements CollectorEngine {

    private final ConcurrentHashMap<String, List<CollectorBatch>> groupedCollectBatch;
    private final Storage storage;
    private final StorageAdapter adapter;
    private final DataMetaRegistry registry;
    private final AggregateProvider provider;

    public DefaultCollectorEngine(Storage storage, StorageAdapter adapter, DataMetaRegistry registry, AggregateProvider provider) {
        this.groupedCollectBatch = new ConcurrentHashMap<>();
        // add default pipeline
        this.storage = storage;
        this.adapter = adapter;
        this.registry = registry;
        this.provider = provider;
        addCollector(new DefaultWritePipeline(), 1, "default");
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public StorageAdapter getStorageAdapter() {
        return adapter;
    }

    @Override
    public void addData(Data data) {
        if (data == null) {
            return;
        }
        List<CollectorBatch> batches = groupedCollectBatch.get(data.getTopic());
        if (Utility.isEmpty(batches)) {
            return;
        }
        DataMeta meta = registry.getDataMeta(data.getId());
        if (meta == null) {
            return;
        }
        batches.forEach(b -> b.collect(meta, data));
    }

    @Override
    public void addCollector(WritePipeline pipeline, int shard, String... topics) {
        DataCollector collector = new DefaultCollector(storage, adapter, pipeline, provider);
        CollectorBatch batch = new DefaultCollectorBatch(collector, shard);
        if (topics == null || topics.length <= 0) {
            groupedCollectBatch.computeIfAbsent("default", k -> new CopyOnWriteArrayList<>()).add(batch);
        }
        for (String topic : topics) {
            groupedCollectBatch.computeIfAbsent(topic, k -> new CopyOnWriteArrayList<>()).add(batch);
        }
    }
}
