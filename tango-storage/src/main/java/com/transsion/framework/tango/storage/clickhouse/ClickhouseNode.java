package com.transsion.framework.tango.storage.clickhouse;

import com.clickhouse.client.*;
import com.clickhouse.client.config.ClickHouseClientOption;
import com.clickhouse.client.http.config.ClickHouseHttpOption;
import com.transsion.framework.tango.common.EventHandler;
import com.transsion.framework.tango.common.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Author mengqi.lv
 * @Date 2022/8/29
 * @Version 1.0
 **/
public class ClickhouseNode implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(ClickhouseNode.class);

    private final ClickHouseClient client;
    private final ClickHouseNode node;
    private final ClickhouseEndpoint endpoint;
    private final EventHandler eventHandler;

    public ClickhouseNode(ClickhouseEndpoint endpoint, EventHandler eventHandler) {
        this.client = ClickHouseClient.builder().config(new ClickHouseConfig()).build();
        this.node = buildNode(endpoint);
        this.endpoint = endpoint;
        this.eventHandler = eventHandler;
    }

    private ClickHouseNode buildNode(ClickhouseEndpoint endpoint) {
        ClickHouseNode.Builder builder = ClickHouseNode.builder()
                .host(endpoint.getHost())
                .port(ClickHouseProtocol.valueOf(endpoint.getProtocol()), endpoint.getPort());
        if (!Utility.isEmpty(endpoint.getDatabase())) {
            builder.database(endpoint.getDatabase());
        }
        if (endpoint.getCredentials() != null) {
            builder.credentials(ClickHouseCredentials.fromUserAndPassword(
                    endpoint.getCredentials().getUser(), endpoint.getCredentials().getPassword()));
        }
        return builder.build();
    }

    @Override
    public void close() {
        this.client.close();
    }

    public ClickHouseResponse query(String sql) {
        ClickHouseRequest request = client.connect(node)
                .set("max_threads", endpoint.getMaxThreads())
//                .set("max_rows_to_read", 10000000)
                .set("read_overflow_mode", "throw")
                .set("send_progress_in_http_headers", 1)
                .set("max_memory_usage", endpoint.getMaxMemoryUsage())
                .option(ClickHouseHttpOption.KEEP_ALIVE, false)
                .format(ClickHouseFormat.RowBinaryWithNamesAndTypes)
                .query(sql);
        ClickHouseResponse response = null;
        try {
            response = (ClickHouseResponse) request.execute().get();
            if (response == null) {
                return null;
            }

            return response;
        } catch (Exception e) {
            logger.error("query sql[" + sql + "] error!", e);
            throw new RuntimeException("Query Sql Error!");
        }
    }

    public void insert(String sql) {
        client.connect(this.node)
                .set("max_insert_threads", this.endpoint.getMaxInsertThreads())
                .set("send_progress_in_http_headers", 1)
                .option(ClickHouseClientOption.ASYNC, false)
                .option(ClickHouseHttpOption.KEEP_ALIVE, false)
                .query(sql).execute().handle((response, throwable) -> {
                    ClickHouseResponse clickHouseResponse = null;
                    if (throwable != null) {
                        if (eventHandler != null) {
                            eventHandler.handleThrowable(sql, throwable);
                        }
                    }
                    if (clickHouseResponse != null) {
                        clickHouseResponse.close();
                    }

                    // TODO
//                    eventHandler.handleEvent(new SuccessEvent());
                    return "OK";
                });
    }
}
