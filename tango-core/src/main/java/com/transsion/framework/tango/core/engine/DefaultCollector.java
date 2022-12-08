package com.transsion.framework.tango.core.engine;

import com.transsion.framework.tango.core.aggregator.AggregateProvider;
import com.transsion.framework.tango.core.aggregator.AggregatorGenerator;
import com.transsion.framework.tango.core.aggregator.DefaultAggregateProvider;
import com.transsion.framework.tango.core.storage.Storage;
import com.transsion.framework.tango.core.storage.StorageAdapter;

/**
 * @Author mengqi.lv
 * @Date 2022/8/17
 * @Version 1.0
 **/
public class DefaultCollector implements DataCollector {
    private final Storage storage;
    private final StorageAdapter adapter;
    private final WritePipeline pipeline;
    private final AggregateProvider aggregateProvider;

    public DefaultCollector(Storage storage, StorageAdapter adapter, WritePipeline pipeline, AggregatorGenerator generator) {
        this(storage, adapter, pipeline, new DefaultAggregateProvider(generator));
    }
    public DefaultCollector(Storage storage, StorageAdapter adapter, WritePipeline pipeline, AggregateProvider aggregateProvider) {
        this.storage = storage;
        this.adapter = adapter;
        this.pipeline = pipeline;
        this.aggregateProvider = aggregateProvider;
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
    public WritePipeline getPipeline() {
        return pipeline;
    }

    @Override
    public DataCollector copy() {
        return new DefaultCollector(storage, adapter, pipeline.copy(), aggregateProvider);
    }
}
