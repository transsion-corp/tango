package com.transsion.framework.tango.metrics.data;

import com.transsion.framework.tango.core.data.CommonData;

import java.util.List;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/9/15
 * @Version 1.0
 **/
public class MetricAggregateValue extends CommonData {

    private long timestamp;
    private List<DimensionData> data;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<DimensionData> getData() {
        return data;
    }

    public void setData(List<DimensionData> data) {
        this.data = data;
    }

    public static class DimensionData {
        private Map<String, Object> properties;
        private Number value;

        public Map<String, Object> getProperties() {
            return properties;
        }

        public void setProperties(Map<String, Object> properties) {
            this.properties = properties;
        }

        public Number getValue() {
            return value;
        }

        public void setValue(Number value) {
            this.value = value;
        }
    }
}
