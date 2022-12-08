package com.transsion.framework.tango.common.config;

import com.transsion.framework.tango.common.Utility;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author mengqi.lv
 * @Date 2021/11/11
 * @Version 1.0
 **/
public class CommonConfig {

    private static final CommonConfig INSTANCE = new CommonConfig();
    private final Map<String, String> internalMap;

    private CommonConfig() {
        internalMap = new ConcurrentHashMap<>();
    }

    public static CommonConfig getInstance() {
        return INSTANCE;
    }

    public String[] getStringArray(String key) {
        String all = internalMap.get(key);
        if (Utility.isEmptyStr(all)) {
            return null;
        }
        return all.split(",");
    }

    public final void add(String key, String value) {
        internalMap.put(key, value);
    }

    public final String get(String key) {
        return internalMap.get(key);
    }

    public final String get(String key, String defaultValue) {
        return internalMap.getOrDefault(key, defaultValue);
    }
}
