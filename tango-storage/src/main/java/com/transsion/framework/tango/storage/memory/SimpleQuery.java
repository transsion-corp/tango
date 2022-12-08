package com.transsion.framework.tango.storage.memory;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.storage.Query;

import java.util.Collections;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/29
 * @Version 1.0
 **/
public class SimpleQuery implements Query {
    private Identifier queryId;
    private Identifier dataId;
    private List<Property> properties;

    public SimpleQuery(Identifier queryId, Identifier dataId) {
        this.queryId = queryId;
        this.dataId = dataId;
    }

    @Override
    public Identifier getViewId() {
        return queryId;
    }

    public Identifier getQueryId() {
        return queryId;
    }

    public void setQueryId(Identifier queryId) {
        this.queryId = queryId;
    }

    public Identifier getDataId() {
        return dataId;
    }

    public void setDataId(Identifier dataId) {
        this.dataId = dataId;
    }

    @Override
    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
