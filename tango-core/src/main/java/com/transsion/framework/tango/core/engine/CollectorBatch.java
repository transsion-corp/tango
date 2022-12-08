package com.transsion.framework.tango.core.engine;

import com.transsion.framework.tango.common.Utility;
import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.data.meta.DataMeta;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface CollectorBatch {

    void start();

    void stop();

    default void collect(DataMeta meta, Data data) {
        if (Utility.isEmpty(getCollectors())) {
            return;
        }
        getCollectors().get(0).collect(meta, data);
    }

    default void collect(DataMeta meta, List<Data> list) {
        if (Utility.isEmpty(getCollectors())) {
            return;
        }
        getCollectors().get(0).collect(meta, list);
    }

    List<DataCollector> getCollectors();
}
