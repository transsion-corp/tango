package com.transsion.framework.tango.core.engine;

import com.transsion.framework.tango.common.Threads;
import com.transsion.framework.tango.core.dag.DAGParser;
import com.transsion.framework.tango.core.dag.DefaultDAGParser;
import com.transsion.framework.tango.core.dag.DefaultNodeGenerator;
import com.transsion.framework.tango.core.data.meta.DataMetaRegistry;
import com.transsion.framework.tango.core.handler.ExpressionHandlerGenerator;
import com.transsion.framework.tango.core.storage.Storage;
import com.transsion.framework.tango.core.storage.StorageAdapter;

import java.util.concurrent.ExecutorService;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public class DefaultDumperEngine implements DumperEngine {

    private final Storage storage;
    private final StorageAdapter adapter;
    private final DataMetaRegistry metaRegistry;
    private final ExecutorService executor;
    private final DAGParser DAGParser;
    private final ReadPipeline readPipeline;

    public DefaultDumperEngine(Storage storage, StorageAdapter adapter, DataMetaRegistry registry) {
        this.metaRegistry = registry;
        this.storage = storage;
        this.adapter = adapter;
        this.executor = Threads.forPool().getFixedThreadPool("tango-dumper-engine", Runtime.getRuntime().availableProcessors());
        this.DAGParser = new DefaultDAGParser(new DefaultNodeGenerator(storage, adapter, metaRegistry,
                exp -> ExpressionHandlerGenerator.newHandler(exp).gen(), executor));
        this.readPipeline = new DefaultReadPipeline();
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public StorageAdapter getStorageAdapter() {
        return adapter;
    }

    @Override
    public DAGParser getParser() {
        return DAGParser;
    }

    @Override
    public ExecutorService getExecutor() {
        return executor;
    }

    @Override
    public ReadPipeline getReadPipeline() {
        return readPipeline;
    }

    public void addProcessor(ReadProcessor processor) {
        readPipeline.addProcessor(processor);
    }
}
