package com.transsion.framework.tango.log.adapter;

import com.clickhouse.client.*;
import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.property.NamedType;
import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.log.data.LogMeta;
import com.transsion.framework.tango.log.data.LogMetaWrapper;
import com.transsion.framework.tango.log.data.BatchLogData;
import com.transsion.framework.tango.log.data.DataUtils;
import com.transsion.framework.tango.log.data.LogData;
import com.transsion.framework.tango.storage.clickhouse.ClickhouseInsert;
import com.transsion.framework.tango.storage.clickhouse.ClickhouseStorageAdapter;
import com.transsion.framework.tango.storage.clickhouse.view.ClickhouseQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author mengqi.lv
 * @Date 2022/10/21
 * @Version 1.0
 **/
public class LogClickhouseAdapter implements ClickhouseStorageAdapter<LogMeta, LogData, BatchLogData> {

    private static final Map<ClickHouseDataType, NamedType> TYPE_MAP = new HashMap<ClickHouseDataType, NamedType>(){
        {
            put(ClickHouseDataType.Map, NamedType.MAP);
            put(ClickHouseDataType.Bool, NamedType.BOOLEAN);
            put(ClickHouseDataType.String, NamedType.STRING);
            put(ClickHouseDataType.Int64, NamedType.LONG);
            put(ClickHouseDataType.Int32, NamedType.INT);
        }
    };
    private final Map<Identifier, LogMetaWrapper> wrapperCache;

    public LogClickhouseAdapter() {
        wrapperCache = new ConcurrentHashMap<>();
    }

    @Override
    public ClickhouseInsert newInsert(LogMeta meta, LogData from) {
        Identifier id = meta.getId();
        LogMetaWrapper wrapper = wrapperCache.computeIfAbsent(id, k -> new LogMetaWrapper(meta));
        ClickhouseInsert insert = new ClickhouseInsert();
        insert.setColumns(wrapper.getColumns());
        insert.setTable(wrapper.getTable());
        insert.setRows(Arrays.asList(DataUtils.getRow(wrapper, from)));
        return insert;
    }

    @Override
    public BatchLogData convertFrom(ClickhouseQuery query, ClickHouseResponse from) {
        BatchLogData logData = new BatchLogData(query.getDb(), query.getTable());
        List<Object[]> data = new ArrayList<>();
        List<ClickHouseColumn> columns = from.getColumns();
        List<Property> properties = new ArrayList<>();
        columns.forEach(c -> {
            // convert to property
            Property p = new Property(c.getColumnName(), TYPE_MAP.get(c.getDataType()));
            properties.add(p);
        });

        from.stream().forEach(r -> {
            Object[] objects = new Object[columns.size()];
            for (int i = 0; i < columns.size(); i++) {
                ClickHouseValue value = r.getValue(i);
                objects[i] = value.asObject();
            }
            data.add(objects);
        });
        logData.setFields(properties);
        logData.setData(data);
        from.close();

        return logData;
    }
}
