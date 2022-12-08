package com.transsion.framework.tango.core.engine;

import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.data.meta.DataMeta;
import com.transsion.framework.tango.core.storage.Insert;
import com.transsion.framework.tango.core.storage.Storage;
import com.transsion.framework.tango.core.storage.StorageAdapter;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface DataCollector {

    default void collect(DataMeta meta, Data data) {
        Data output = getPipeline().process(meta, data);
        if (output != null) {
            Insert insert = getStorageAdapter().newInsert(meta, output);
            getStorage().insert(insert);
        }
    }
    default void collect(DataMeta meta, List<Data> list) {
        list.forEach(d -> {
            collect(meta, d);
        });
    }

    Storage getStorage();

    StorageAdapter getStorageAdapter();

    WritePipeline getPipeline();

    DataCollector copy();
}
