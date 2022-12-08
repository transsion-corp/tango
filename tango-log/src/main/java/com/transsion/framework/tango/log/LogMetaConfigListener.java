package com.transsion.framework.tango.log;

import com.transsion.framework.tango.common.config.StatefulConfigListener;
import com.transsion.framework.tango.core.data.meta.DataMetaRegistry;
import com.transsion.framework.tango.log.data.LogMeta;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/10/22
 * @Version 1.0
 **/
public class LogMetaConfigListener implements StatefulConfigListener<LogMeta> {

    private final DataMetaRegistry<LogMeta> registry;

    public LogMetaConfigListener(DataMetaRegistry<LogMeta> registry) {
        this.registry = registry;
    }

    @Override
    public void onInitial(List<LogMeta> configs) {
        configs.forEach(m -> this.registry.register(m));
    }

    @Override
    public void onChange(LogMeta oldConfig, LogMeta newConfig) {
        this.registry.register(newConfig);
    }

    @Override
    public void onRemove(LogMeta config) {
        this.registry.deregister(config);
    }

    @Override
    public void onInsert(LogMeta config) {
        this.registry.register(config);
    }
}
