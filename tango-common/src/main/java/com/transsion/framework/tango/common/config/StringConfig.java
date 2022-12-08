package com.transsion.framework.tango.common.config;

/**
 * @Author mengqi.lv
 * @Date 2021/5/12
 * @Version 1.0
 **/
public class StringConfig implements StatefulConfig {
    private String key;
    private String value;

    public StringConfig() {
    }

    public StringConfig(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
