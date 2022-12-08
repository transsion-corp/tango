package com.transsion.framework.tango.core.storage;

import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.data.meta.DataMeta;

import java.util.stream.Stream;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface StorageAdapter<Meta extends DataMeta, StoreF extends Data, InsertT extends Insert,
        QueryT extends Query, QueryDataT, ResultT extends Data> {

    InsertT newInsert(Meta meta, StoreF from);

    QueryBuilder<QueryT> queryBuilder();

    ResultT convertFrom(QueryT query, QueryDataT queryData);
}
