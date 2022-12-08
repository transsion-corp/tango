package com.transsion.framework.tango.metrics.data;

import com.transsion.framework.tango.core.data.Point;
import com.transsion.framework.tango.metrics.data.examplar.Examplar;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/23
 * @Version 1.0
 **/
public class MetricPoint extends Point<Number> {
    private long timestamp;
    private Number value;
    private List<Examplar> examplars;

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public void setValue(Number value) {
        this.value = value;
    }

    public void setExamplars(List<Examplar> examplars) {
        this.examplars = examplars;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public Number getValue() {
        return value;
    }

    public List<Examplar> getExamplars() {
        return examplars;
    }
}
