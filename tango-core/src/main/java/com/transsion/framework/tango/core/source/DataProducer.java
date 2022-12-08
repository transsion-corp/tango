package com.transsion.framework.tango.core.source;

import com.transsion.framework.tango.core.engine.CollectorEngine;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface DataProducer {

    void start();

    void stop();

    String getName();

    void setCollectorEngine(CollectorEngine engine);
}
