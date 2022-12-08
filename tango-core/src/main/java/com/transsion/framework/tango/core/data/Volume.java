package com.transsion.framework.tango.core.data;

import com.transsion.framework.tango.common.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/8/22
 * @Version 1.0
 **/
public class Volume extends CommonData {
    private List<Detail> volume;

    public Volume(Identifier id, List<Detail> volume) {
        super(id, Collections.emptyMap());
        this.volume = volume;
    }

    public List<Detail> getVolume() {
        return volume;
    }

    public void setVolume(List<Detail> volume) {
        this.volume = volume;
    }

    public static class Detail {

        private Map<String, Object> properties;

        public Detail(Map<String, Object> properties) {
            this.properties = properties;
        }

        public Map<String, Object> getProperties() {
            return properties;
        }

        public void setProperties(Map<String, Object> properties) {
            this.properties = properties;
        }
    }
}
