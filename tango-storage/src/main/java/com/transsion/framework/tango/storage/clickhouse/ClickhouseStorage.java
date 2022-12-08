package com.transsion.framework.tango.storage.clickhouse;

import com.clickhouse.client.ClickHouseResponse;
import com.transsion.framework.tango.common.EventHandler;
import com.transsion.framework.tango.common.NotThreadSafe;
import com.transsion.framework.tango.common.Utility;
import com.transsion.framework.tango.core.storage.Storage;
import com.transsion.framework.tango.storage.clickhouse.exception.ClickhouseExecuteException;
import com.transsion.framework.tango.storage.clickhouse.sql.TableSqlGen;
import com.transsion.framework.tango.storage.clickhouse.view.ClickhouseQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Author mengqi.lv
 * @Date 2022/8/26
 * @Version 1.0
 **/
@NotThreadSafe
public class ClickhouseStorage implements Storage<ClickhouseInsert, ClickhouseQuery, ClickHouseResponse, ClickhouseTableMeta> {
    //10w rows
    private int maxBatch;
    // more than 10s write once
    private long flushMillis;

    private final Map<String, ClickhouseLocalCache> localCache = new HashMap<>();
    private final Map<String, String[]> localColumns = new HashMap<>();

    private final ClickhouseCluster clickhouseCluster;
    private final EventHandler eventHandler;

    public ClickhouseStorage(List<ClickhouseEndpoint> nodes, EventHandler eventHandler) {
        this(nodes, eventHandler, 10 * 10000, 1000 * 10);
    }

    public ClickhouseStorage(List<ClickhouseEndpoint> nodes, EventHandler eventHandler, int flushSeconds, int maxBatch) {
        this.clickhouseCluster = new ClickhouseCluster(nodes, eventHandler);
        this.eventHandler = eventHandler;
        this.maxBatch = maxBatch;
        this.flushMillis = 1000L * flushSeconds;
    }

    @Override
    public void newStore(ClickhouseTableMeta meta) {
        List<String> sqls = TableSqlGen.genSqls(meta);
        for (String s : sqls) {
            ClickHouseResponse response = null;
            try {
                response = this.clickhouseCluster.getWriteNode().query(s);
                if (response.getColumns().size() <= 0) {
                    throw new ClickhouseExecuteException("error happens when execute: " + s);

                }
            } catch (Exception e) {
                throw new ClickhouseExecuteException("error happens when execute: " + s);
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        }
    }

    @Override
    public void dropStore(ClickhouseTableMeta meta) {

    }

    @Override
    public void insert(ClickhouseInsert insert) {
        String[] oldColumns = localColumns.get(insert.getTable());

        if (!insert.getColumns().equals(oldColumns)) {
            if (oldColumns != null) {
                // TODO flush
            }
            localColumns.put(insert.getTable(), insert.getColumns());
        }

        ClickhouseLocalCache cache = localCache.computeIfAbsent(insert.getTable(), k -> new ClickhouseLocalCache(maxBatch, flushMillis));
        cache.getRows().addAll(insert.getRows());

        List<String> batch = cache.getNextBatch(insert.isFlush());
        if (!Utility.isEmpty(batch)) {
            clickhouseCluster.getWriteNode().insert(buildSQL(insert.getTable(), insert.getColumns(), batch));
        }
    }

    @Override
    public ClickHouseResponse query(ClickhouseQuery query) {
        return this.clickhouseCluster.getReadNode().query(query.getSql());
    }

    protected String buildSQL(String table, String[] columns, List<String> rows) {
        StringBuilder builder = new StringBuilder();
        builder.append("insert into table ").append(table).append("(");
        boolean first = true;
        for (String col : columns) {
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }
            builder.append("`").append(col).append("`");
        }
        builder.append(") values ");
        builder.append(String.join(", ", rows));
        builder.append(";");

        return builder.toString();
    }
}
