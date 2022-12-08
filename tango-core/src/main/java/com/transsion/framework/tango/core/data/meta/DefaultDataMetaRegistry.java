package com.transsion.framework.tango.core.data.meta;

import com.transsion.framework.tango.common.Identifier;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public class DefaultDataMetaRegistry implements DataMetaRegistry {

    private final ConcurrentHashMap<Identifier, DataMeta> metaMap = new ConcurrentHashMap<>();

    @Override
    public void register(DataMeta meta) {
        metaMap.put(meta.getId(), meta);
    }

    @Override
    public void deregister(DataMeta meta) {
        metaMap.remove(meta.getId());
    }

    @Override
    public DataMeta getDataMeta(Identifier id) {
        return metaMap.get(id);
    }

    @Override
    public boolean hasMeta(Identifier dataId) {
        return metaMap.containsKey(dataId);
    }
}
