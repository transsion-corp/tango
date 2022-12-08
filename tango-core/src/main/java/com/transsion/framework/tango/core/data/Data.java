package com.transsion.framework.tango.core.data;

import com.transsion.framework.tango.common.Identifier;

import java.util.Collections;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface Data {

    Data Empty = new CommonData(new Identifier("", ""), Collections.EMPTY_MAP);

    Map<String, Object> getProperties();

    default Object getPropertyValue(String propertyName) {
        return getProperties().get(propertyName);
    }

    default Object getPropertyValue(String propertyName, String defaultValue) {
        return getProperties().getOrDefault(propertyName, defaultValue);
    }

    Identifier getId();

    default String getTopic() {
        return "default";
    }
}
