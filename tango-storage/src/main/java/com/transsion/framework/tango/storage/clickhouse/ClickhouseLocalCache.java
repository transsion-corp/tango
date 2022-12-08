package com.transsion.framework.tango.storage.clickhouse;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/29
 * @Version 1.0
 **/
public class ClickhouseLocalCache {
    private int maxBatch = 10 * 10000;
    private long maxInterval = 10000;

    private long lastSendTimestamp = System.currentTimeMillis();
    private List<String> rows = new ArrayList<>();

    public List<String> getRows() {
        return rows;
    }

    public List<String> getNextBatch() {
        return getNextBatch(false);
    }

    public ClickhouseLocalCache() {
    }

    public ClickhouseLocalCache(int maxBatch, long maxIntervalInSec) {
        this.maxBatch = maxBatch;
        this.maxInterval = maxIntervalInSec;
    }

    /**
     * True for test.
     * @param force
     * @return
     */
    public List<String> getNextBatch(boolean force) {
        if (force) {
            return rows;
        }
        if (rows.size() > maxBatch) {
            return rows;
        }
        if (System.currentTimeMillis() - lastSendTimestamp > maxInterval) {
            return rows;
        }
        return null;
    }
}
