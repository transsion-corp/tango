package com.transsion.framework.tango.storage.clickhouse;

import com.transsion.framework.tango.common.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author mengqi.lv
 * @Date 2022/1/18
 * @Version 1.0
 **/
public class ClickhouseCluster implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(ClickhouseCluster.class);

    private final AtomicInteger writeRobinIndex = new AtomicInteger(0);
    private final AtomicInteger readRobinIndex = new AtomicInteger(1);

    private final EventHandler eventHandler;
    private final List<ClickhouseNode> nodes;

    public ClickhouseCluster(List<ClickhouseEndpoint> nodes, EventHandler eventHandler) {
        this.nodes = createClients(nodes);
        this.eventHandler = eventHandler;
    }

    private List<ClickhouseNode> createClients(List<ClickhouseEndpoint> nodes) {
        List<ClickhouseNode> list = new ArrayList<>(nodes.size());
        for (ClickhouseEndpoint endpoint : nodes) {
            list.add(new ClickhouseNode(endpoint, eventHandler));
        }
        return list;
    }

    public ClickhouseNode getWriteNode() {
        List<ClickhouseNode> cur = nodes;
        return cur.get(writeRobinIndex.incrementAndGet() % cur.size());
    }

    public ClickhouseNode getReadNode() {
        List<ClickhouseNode> cur = nodes;
        return cur.get(readRobinIndex.incrementAndGet() % nodes.size());
    }

    @Override
    public void close() {
        nodes.forEach(r -> r.close());
    }
}
