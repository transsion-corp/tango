package com.transsion.framework.tango.core.engine;

import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.data.meta.DataMeta;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface WriteProcessor {

    Data process(DataMeta meta, Data data);

    boolean shouldProcess(DataMeta meta, Data data);

    WriteProcessor copy();
}
