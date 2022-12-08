package com.transsion.framework.tango.core.engine;

import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.data.meta.DataMeta;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface WritePipeline {

    void addProcessor(WriteProcessor processor);

    Data process(DataMeta meta, Data source);

    WritePipeline copy();
}
