package com.transsion.framework.tango.metrics.data.adapter;

import com.transsion.framework.tango.common.Utility;
import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.metrics.data.*;
import com.transsion.framework.tango.metrics.data.meta.MetricMeta;
import com.transsion.framework.tango.storage.memory.InMemoryAdapter;
import com.transsion.framework.tango.storage.memory.SimpleInsert;
import com.transsion.framework.tango.storage.memory.SimpleQuery;

import java.util.*;
import java.util.stream.Stream;

/**
 * @Author mengqi.lv
 * @Date 2022/8/23
 * @Version 1.0
 **/
public class MetricInMemoryAdapter implements InMemoryAdapter<MetricMeta, MetricAggregateValue, MetricData> {
    @Override
    public SimpleInsert newInsert(MetricMeta meta, MetricAggregateValue from) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("timestamp", from.getTimestamp());
        properties.put("value", from.getData());
        return new SimpleInsert(meta.getId(), properties);
    }

    @Override
    public MetricData convertFrom(SimpleQuery query, Stream from) {
        Map<CompareSerieKey, List<MetricSerie.SeriePoint>> tagMap = new HashMap<>();
        for (Object obj : from.toArray()) {
            Map d = (Map<String, Object>) obj;
            List<MetricAggregateValue.DimensionData> dimensionDataList = (List<MetricAggregateValue.DimensionData>) d.get("value");
            for (MetricAggregateValue.DimensionData dd : dimensionDataList) {
                CompareSerieKey serieKey = getSerieKey(query.getProperties(), dd.getProperties());
                MetricSerie.SeriePoint point = new MetricSerie.SeriePoint();
                point.setTimestamp((Long) d.get("timestamp"));
                point.setValue(dd.getValue());
                tagMap.computeIfAbsent(serieKey, k -> new ArrayList<>()).add(point);
            }
        }
        from.close();
        MetricData metricData = new MetricData();
        metricData.setId(query.getViewId());
        metricData.setSeries(toSeries(tagMap));
        return metricData;
    }

    private List<MetricSerie> toSeries(Map<CompareSerieKey, List<MetricSerie.SeriePoint>> tagMap) {
        List<MetricSerie> series = new ArrayList<>(tagMap.size());
        for (Map.Entry<CompareSerieKey, List<MetricSerie.SeriePoint>> entry : tagMap.entrySet()) {
            MetricSerie serie = new MetricSerie();
            serie.setProperties(entry.getKey().getTags());
            serie.setPoints(entry.getValue());
            series.add(serie);
        }
        return series;
    }

    private CompareSerieKey getSerieKey(List<Property> properties, Map<String, Object> tags) {
        if (Utility.isEmpty(tags)) {
            return new CompareSerieKey("", tags);
        }
        StringBuilder sb = new StringBuilder();
        for (Property p : properties) {
            Object o = tags.get(p.getName());
            sb.append(o == null? ",":o.toString());
        }
        return new CompareSerieKey(sb.toString(), tags);
    }
}
