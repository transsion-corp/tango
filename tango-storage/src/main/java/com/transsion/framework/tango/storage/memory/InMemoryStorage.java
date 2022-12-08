package com.transsion.framework.tango.storage.memory;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.Utility;
import com.transsion.framework.tango.core.data.meta.DataMeta;
import com.transsion.framework.tango.core.storage.Storage;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Only for test.
 *
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public class InMemoryStorage implements Storage<SimpleInsert, SimpleQuery, Stream, DataMeta> {
    private final Map<Identifier, Collection<Map<String, Object>>> innerStore = new ConcurrentHashMap<>();

    @Override
    public void newStore(DataMeta meta) {

    }

    @Override
    public void dropStore(DataMeta meta) {

    }

    @Override
    public void insert(SimpleInsert insert) {
        innerStore.computeIfAbsent(insert.getId(), k -> new ArrayBlockingQueue<>(10000)).add(insert.getData());
    }

    @Override
    public Stream<Map<String, Object>> query(SimpleQuery query) {
        Collection<Map<String, Object>> collection = innerStore.get(query.getDataId());
        if (Utility.isEmpty(collection)) {
            return Stream.empty();
        }
        return collection.stream();
    }
}
