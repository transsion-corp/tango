package com.transsion.framework.tango.storage.memory;

import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.data.meta.DataMeta;
import com.transsion.framework.tango.core.storage.QueryBuilder;
import com.transsion.framework.tango.core.storage.StorageAdapter;

import java.util.stream.Stream;

/**
 * @Author mengqi.lv
 * @Date 2022/8/23
 * @Version 1.0
 **/
public interface InMemoryAdapter<Meta extends DataMeta, StoreF extends Data, ResultT extends Data> extends
        StorageAdapter<Meta, StoreF, SimpleInsert, SimpleQuery, Stream, ResultT> {

    @Override
    default QueryBuilder<SimpleQuery> queryBuilder() {
        return new InMemoryQueryBuilder();
    }
}
