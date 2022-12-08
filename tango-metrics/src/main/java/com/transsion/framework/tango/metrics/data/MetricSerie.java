package com.transsion.framework.tango.metrics.data;

import com.transsion.framework.tango.core.data.CommonData;
import com.transsion.framework.tango.metrics.data.examplar.Examplar;

import java.util.List;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/8/16
 * @Version 1.0
 **/
public class MetricSerie extends CommonData {

    private List<SeriePoint> points;

    public Map<String, Object> getTags() {
        return getProperties();
    }

    public List<SeriePoint> getPoints() {
        return points;
    }

    public void setPoints(List<SeriePoint> points) {
        this.points = points;
    }

    public static class SeriePoint {
        private long timestamp;
        private Number value;
        private List<Examplar> examplars;

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public Number getValue() {
            return value;
        }

        public void setValue(Number value) {
            this.value = value;
        }

        public List<Examplar> getExamplars() {
            return examplars;
        }

        public void setExamplars(List<Examplar> examplars) {
            this.examplars = examplars;
        }
    }
}
