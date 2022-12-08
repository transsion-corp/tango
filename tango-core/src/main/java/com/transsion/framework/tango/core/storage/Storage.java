package com.transsion.framework.tango.core.storage;

import com.transsion.framework.tango.core.data.meta.DataMeta;

import java.util.stream.Stream;

/**
 * @Author mengqi.lv
 * @Date 2022/8/17
 * @Version 1.0
 **/
public interface Storage<InsertT extends Insert, QueryT extends Query, QueryDataT, M extends DataMeta> {

    void newStore(M meta);

    void dropStore(M meta);

    void insert(InsertT insert);

    QueryDataT query(QueryT query);
}
