package com.transsion.framework.tango.log;

import com.google.gson.Gson;
import com.transsion.framework.tango.common.config.StatefulConfigBuilder;
import com.transsion.framework.tango.log.data.LogMeta;

import java.util.List;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/10/22
 * @Version 1.0
 **/
public class LogMetaConfigBuilder implements StatefulConfigBuilder<LogMeta> {

    private final Gson gson = new Gson();

    @Override
    public LogMeta build(String key, String configValue) {
        return LogMeta.fromJson(gson.fromJson(configValue, Map.class));
    }

    @Override
    public List<LogMeta> buildList(String key, String configValue) {
        return null;
    }
}
