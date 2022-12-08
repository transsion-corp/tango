package com.transsion.framework.tango.metrics.data;

import com.transsion.framework.tango.core.data.CommonData;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/23
 * @Version 1.0
 **/
public class MetricData extends CommonData {

    private List<MetricSerie> series;

    public List<MetricSerie> getSeries() {
        return series;
    }

    public void setSeries(List<MetricSerie> series) {
        this.series = series;
    }
}
