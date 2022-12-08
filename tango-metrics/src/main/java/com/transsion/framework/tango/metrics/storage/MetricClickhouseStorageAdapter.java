package com.transsion.framework.tango.metrics.storage;

import com.clickhouse.client.ClickHouseResponse;
import com.transsion.framework.tango.metrics.data.MetricData;
import com.transsion.framework.tango.metrics.data.MetricPoint;
import com.transsion.framework.tango.metrics.data.meta.MetricMeta;
import com.transsion.framework.tango.storage.clickhouse.ClickhouseInsert;
import com.transsion.framework.tango.storage.clickhouse.ClickhouseStorageAdapter;
import com.transsion.framework.tango.storage.clickhouse.view.ClickhouseQuery;

import java.util.stream.Stream;

/**
 * @Author mengqi.lv
 * @Date 2022/9/16
 * @Version 1.0
 **/
public class MetricClickhouseStorageAdapter implements ClickhouseStorageAdapter<MetricMeta, MetricPoint, MetricData> {
    @Override
    public ClickhouseInsert newInsert(MetricMeta meta, MetricPoint from) {
        return null;
    }

    @Override
    public MetricData convertFrom(ClickhouseQuery query, ClickHouseResponse from) {
        return null;
    }
}
