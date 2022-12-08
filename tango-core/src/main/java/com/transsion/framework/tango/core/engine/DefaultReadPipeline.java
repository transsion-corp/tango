package com.transsion.framework.tango.core.engine;

import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/22
 * @Version 1.0
 **/
public class DefaultReadPipeline implements ReadPipeline {
    private final List<ReadProcessor> processors = new ArrayList<>(4);

    @Override
    public void addProcessor(ReadProcessor processor) {
        processors.add(processor);
    }

    @Override
    public Data dump(View view, Data source) {
        Data data = source;
        for (ReadProcessor p : processors) {
            data = p.process(view, source);
            if (data == null || Data.Empty.equals(data)) {
                return Data.Empty;
            }
        }
        return data;
    }
}
