package com.transsion.framework.tango.core.engine;

import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.view.View;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface ReadPipeline {

    void addProcessor(ReadProcessor processor);

    Data dump(View view, Data source);
}
