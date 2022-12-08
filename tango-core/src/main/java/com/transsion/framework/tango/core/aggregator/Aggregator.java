package com.transsion.framework.tango.core.aggregator;

import com.transsion.framework.tango.core.data.Data;

/**
 * @Author mengqi.lv
 * @Date 2022/9/7
 * @Version 1.0
 **/
public interface Aggregator<T extends Data, R extends Data> {
    R aggregate(T input);
}
