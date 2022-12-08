package com.transsion.framework.tango.core.engine;

import com.transsion.framework.tango.core.dag.DAGParser;
import com.transsion.framework.tango.core.dag.QueryDAG;
import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.storage.Storage;
import com.transsion.framework.tango.core.storage.StorageAdapter;
import com.transsion.framework.tango.core.view.View;

import java.util.concurrent.ExecutorService;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface DumperEngine {

    Storage getStorage();

    StorageAdapter getStorageAdapter();

    DAGParser getParser();

    ExecutorService getExecutor();

    ReadPipeline getReadPipeline();

    default Data dump(View view) {
        QueryDAG dag = getParser().parseDAG(view);
        return getReadPipeline().dump(view, dag.getData());
    }
}
