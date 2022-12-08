package com.transsion.framework.tango.source;

import com.transsion.framework.tango.core.data.Data;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface Poller<T extends Data> {

    String getName();

    List<T> poll(int max);
}
