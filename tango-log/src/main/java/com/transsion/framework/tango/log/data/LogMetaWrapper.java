package com.transsion.framework.tango.log.data;

/**
 * @Author mengqi.lv
 * @Date 2022/10/22
 * @Version 1.0
 **/
public class LogMetaWrapper {

    private final LogMeta meta;
    private final String[] columns;
    private final String table;

    public LogMetaWrapper(LogMeta meta) {
        this.meta = meta;
        this.columns = meta.getProperties().stream().map(p -> p.getName()).toArray(String[]::new);
        StringBuilder sb = new StringBuilder();
        sb.append(meta.getBiz()).append(".`").append(meta.getScenario()).append("_local`");
        this.table = sb.toString();
    }

    public LogMeta getMeta() {
        return meta;
    }

    public String[] getColumns() {
        return columns;
    }

    public String getTable() {
        return table;
    }
}
