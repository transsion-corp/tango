package com.transsion.framework.tango.metrics.aggregator;

import java.util.Map;
import java.util.Objects;

/**
 * @Author mengqi.lv
 * @Date 2022/9/15
 * @Version 1.0
 **/
public class IdentifierTags {
    private Map<String, Object> properties;
    private String identifier;

    public IdentifierTags(String identifier, Map<String, Object> properties) {
        this.properties = properties;
        this.identifier = identifier;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdentifierTags that = (IdentifierTags) o;
        return identifier.equals(that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
