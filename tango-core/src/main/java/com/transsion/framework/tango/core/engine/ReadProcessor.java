package com.transsion.framework.tango.core.engine;

import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.view.View;

/**
 * @Author mengqi.lv
 * @Date 2022/9/5
 * @Version 1.0
 **/
public interface ReadProcessor {

    Data process(View view, Data data);

    boolean shouldProcess(View view, Data data);

    ReadProcessor copy();
}
