package com.transsion.framework.tango.common.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/10/21
 * @Version 1.0
 **/
public class StringConfigBuilder implements StatefulConfigBuilder<StringConfig> {

    @Override
    public StringConfig build(String key, String configValue) {
        return new StringConfig(key, configValue);
    }

    @Override
    public List<StringConfig> buildList(String key, String configValue) {
        return Arrays.asList(new StringConfig(key, configValue));
    }
}
