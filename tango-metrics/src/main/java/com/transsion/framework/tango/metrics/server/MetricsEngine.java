package com.transsion.framework.tango.metrics.server;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.config.StatefulConfigManager;
import com.transsion.framework.tango.core.aggregator.AggregateProvider;
import com.transsion.framework.tango.core.aggregator.DefaultAggregateProvider;
import com.transsion.framework.tango.core.data.meta.DataMetaRegistry;
import com.transsion.framework.tango.core.data.meta.DefaultDataMetaRegistry;
import com.transsion.framework.tango.core.engine.*;
import com.transsion.framework.tango.core.storage.Storage;
import com.transsion.framework.tango.core.storage.StorageAdapter;
import com.transsion.framework.tango.core.view.View;
import com.transsion.framework.tango.metrics.aggregator.MetricsAggregatorGenerator;
import com.transsion.framework.tango.metrics.data.MetricData;
import com.transsion.framework.tango.metrics.data.MetricPoint;
import com.transsion.framework.tango.metrics.data.meta.MetricMeta;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/16
 * @Version 1.0
 **/
public class MetricsEngine {

    private final CollectorEngine collectorEngine;
    private final DumperEngine dumperEngine;

    private final StatefulConfigManager configManager;
    private final DataMetaRegistry<MetricMeta> metaRegistry;
    private final AggregateProvider aggregateProvider;

    public MetricsEngine(StatefulConfigManager configManager, Storage storage, StorageAdapter adapter) {
        this.metaRegistry = new DefaultDataMetaRegistry();
        this.aggregateProvider = new DefaultAggregateProvider(new MetricsAggregatorGenerator());
        this.collectorEngine = new DefaultCollectorEngine(storage, adapter, metaRegistry, aggregateProvider);
        this.dumperEngine = new DefaultDumperEngine(storage, adapter, metaRegistry);
        this.configManager = configManager;
        initCollectorBatch();
    }

    private void initCollectorBatch() {
        DefaultWritePipeline pipeline = new DefaultWritePipeline();
        this.collectorEngine.addCollector(pipeline, 1);
    }

    public void collectMetrics(List<MetricPoint> list) {
        for (MetricPoint data : list) {
            collectorEngine.addData(data);
        }
    }

    public MetricData queryMetrics(View view) {
        return (MetricData) dumperEngine.dump(view);
    }

    public void register(MetricMeta meta) {
        this.metaRegistry.register(meta);
    }

    public MetricMeta findMeta(Identifier id) {
        return metaRegistry.getDataMeta(id);
    }

    public void start() {

    }

    public void stop() {

    }
}
