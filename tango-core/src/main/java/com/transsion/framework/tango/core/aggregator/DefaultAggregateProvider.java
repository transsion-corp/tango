package com.transsion.framework.tango.core.aggregator;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.core.data.meta.DataMeta;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author mengqi.lv
 * @Date 2022/9/7
 * @Version 1.0
 **/
public class DefaultAggregateProvider implements AggregateProvider {

    private final ConcurrentHashMap<Identifier, Aggregator> cache = new ConcurrentHashMap<>();

    private final AggregatorGenerator generator;

    public DefaultAggregateProvider(AggregatorGenerator generator) {
        this.generator = generator;
    }

    @Override
    public Aggregator getAggregator(DataMeta meta) {
//        if (meta.getAggregation() == null) {
//            return null;
//        }
//
//        return cache.computeIfAbsent(meta.getId(), k -> generator.newAggregator(meta.getAggregation()));
        return null;
    }
}
