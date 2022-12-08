package com.transsion.framework.tango.storage.clickhouse;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.core.storage.Insert;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/26
 * @Version 1.0
 **/
public class ClickhouseInsert implements Insert {
    private String table;
    private List<String> rows;
    private String[] columns;

    private boolean flush = false;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<String> getRows() {
        return rows;
    }

    public void setRows(List<String> rows) {
        this.rows = rows;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public boolean isFlush() {
        return flush;
    }

    public void setFlush(boolean flush) {
        this.flush = flush;
    }
}
