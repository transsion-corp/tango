package com.transsion.framework.tango.storage.memory;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.core.storage.Insert;

import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/8/29
 * @Version 1.0
 **/
public class SimpleInsert implements Insert {
    private final Map<String, Object> data;
    private final Identifier dataId;

    public SimpleInsert(Identifier dataId, Map<String, Object> data) {
        this.data = data;
        this.dataId = dataId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Identifier getId() {
        return dataId;
    }
}
