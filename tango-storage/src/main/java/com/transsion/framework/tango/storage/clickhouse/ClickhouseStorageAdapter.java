package com.transsion.framework.tango.storage.clickhouse;

import com.clickhouse.client.ClickHouseResponse;
import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.data.meta.DataMeta;
import com.transsion.framework.tango.core.storage.QueryBuilder;
import com.transsion.framework.tango.core.storage.StorageAdapter;
import com.transsion.framework.tango.storage.clickhouse.view.ClickhouseQuery;
import com.transsion.framework.tango.storage.clickhouse.view.ClickhouseQueryBuilder;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface ClickhouseStorageAdapter<Meta extends DataMeta, StoreF extends Data, ResultT extends Data> extends
        StorageAdapter<Meta, StoreF, ClickhouseInsert, ClickhouseQuery, ClickHouseResponse, ResultT> {

    @Override
    default QueryBuilder<ClickhouseQuery> queryBuilder() {
        return new ClickhouseQueryBuilder();
    }
}
