package com.transsion.framework.tango.core.engine;

import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.storage.Storage;
import com.transsion.framework.tango.core.storage.StorageAdapter;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface CollectorEngine {

    Storage getStorage();

    StorageAdapter getStorageAdapter();

    void addData(Data data);

    void addCollector(WritePipeline pipeline, int shard, String... groups);
}
