package com.transsion.framework.tango.metrics.sdk;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.Utility;
import com.transsion.framework.tango.common.config.StatefulConfigManager;
import com.transsion.framework.tango.common.property.NamedType;
import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.aggregation.AggregateType;
import com.transsion.framework.tango.core.condition.Condition;
import com.transsion.framework.tango.core.expression.ExpressionType;
import com.transsion.framework.tango.core.storage.Storage;
import com.transsion.framework.tango.core.storage.StorageAdapter;
import com.transsion.framework.tango.core.view.DefaultView;
import com.transsion.framework.tango.metrics.data.MetricData;
import com.transsion.framework.tango.metrics.data.MetricPoint;
import com.transsion.framework.tango.metrics.data.adapter.MetricInMemoryAdapter;
import com.transsion.framework.tango.metrics.data.meta.MetricMeta;
import com.transsion.framework.tango.metrics.server.MetricsEngine;
import com.transsion.framework.tango.metrics.storage.MetricClickhouseStorageAdapter;
import com.transsion.framework.tango.storage.clickhouse.ClickhouseStorage;

import java.util.Arrays;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/9/16
 * @Version 1.0
 **/
public class DefaultMetricSdk implements MetricsSdk {
    private final MetricsEngine engine;

    public DefaultMetricSdk(StatefulConfigManager configManager, Storage storage) {
        StorageAdapter adapter;
        if (storage instanceof ClickhouseStorage) {
            adapter = new MetricClickhouseStorageAdapter();
        } else {
            adapter = new MetricInMemoryAdapter();
        }
        this.engine = new MetricsEngine(configManager, storage, adapter);
    }

    @Override
    public void registerMetric(String group, String name, AggregateType type, String... tags) {
        MetricMeta.MetricMetaBuilder builder = MetricMeta.MetricMetaBuilder.builder().aggregationType(type).id(group, name);
        for (String t : tags) {
            builder.dimension(new Property(t, NamedType.STRING));
        }
        this.engine.register(builder.build());
    }

    // TODO change examplar to SpanContext
    @Override
    public void addMetric(String group, String name, Map<String, String> properties, Number value) {
        MetricPoint point = new MetricPoint();
        point.setTimestamp(System.currentTimeMillis());
        point.setId(new Identifier(group, name));
        point.setValue(value);
        this.engine.collectMetrics(Arrays.asList(point));
    }

    // TODO expression parser
    @Override
    public MetricData queryMetric(String group, String name, ExpressionType type, Condition condition, long startTime, long endTime, String... groupbys) {
        DefaultView.DefaultViewBuilder builder = DefaultView.DefaultViewBuilder.builder();
        builder.viewId(new Identifier("view", Utility.nextRandom(10000) + ""))
                .dataId(new Identifier(group, name))
                .startTime(startTime).endTime(endTime).build();
//                .expression();
        return this.engine.queryMetrics(builder.build());
    }
}
