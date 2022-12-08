package com.transsion.framework.tango.log.data;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.core.data.Data;

import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public class LogData implements Data {

    private final String biz;
    private final String scenario;
    private final Identifier id;
    private Map<String, Object> properties;

    public LogData(String biz, String scenario) {
        this.biz = biz;
        this.scenario = scenario;
        this.id = new Identifier(biz, scenario);
    }

    public String getBiz() {
        return biz;
    }

    public String getScenario() {
        return scenario;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public Identifier getId() {
        return id;
    }
}
