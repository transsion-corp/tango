package com.transsion.framework.tango.metrics.data.adapter;

import java.util.Map;
import java.util.Objects;

/**
 * @Author mengqi.lv
 * @Date 2022/8/23
 * @Version 1.0
 **/
public class CompareSerieKey {

    private String serie;
    private Map<String, Object> tags;

    public CompareSerieKey(String serie, Map<String, Object> tags) {
        this.serie = serie;
        this.tags = tags;
    }

    public Map<String, Object> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompareSerieKey that = (CompareSerieKey) o;
        return serie.equals(that.serie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serie);
    }
}
