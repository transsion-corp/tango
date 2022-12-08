package com.transsion.framework.tango.core.engine;

import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.data.meta.DataMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/17
 * @Version 1.0
 **/
public class DefaultWritePipeline implements WritePipeline {

    private final List<WriteProcessor> processors = new ArrayList<>(4);

    @Override
    public void addProcessor(WriteProcessor writeProcessor) {
        processors.add(writeProcessor);
    }

    @Override
    public Data process(DataMeta meta, Data source) {
        Data temp = source;
        for (WriteProcessor p : processors) {
            if (temp != null && p.shouldProcess(meta, source)) {
                temp = p.process(meta, temp);
            }
        }
        return temp;
    }

    @Override
    public WritePipeline copy() {
        DefaultWritePipeline pipeline = new DefaultWritePipeline();
        for (WriteProcessor p : processors) {
            pipeline.addProcessor(p.copy());
        }
        return pipeline;
    }
}
