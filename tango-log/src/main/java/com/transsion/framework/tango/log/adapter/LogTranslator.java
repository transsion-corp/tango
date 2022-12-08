package com.transsion.framework.tango.log.adapter;

import com.google.gson.Gson;
import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.core.data.meta.DataMeta;
import com.transsion.framework.tango.core.data.meta.DataMetaRegistry;
import com.transsion.framework.tango.core.data.translate.Translator;
import com.transsion.framework.tango.log.data.LogData;
import com.transsion.framework.tango.log.data.LogMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public class LogTranslator implements Translator<String, LogData> {

    private final Gson gson = new Gson();

    private final DataMetaRegistry metaRegistry;

    public LogTranslator(DataMetaRegistry metaRegistry) {
        this.metaRegistry = metaRegistry;
    }

    @Override
    public LogData translate(String raw) {
        Map<String, Object> logDataJson = gson.fromJson(raw, Map.class);
        return toLogData(logDataJson);
    }

    private LogData toLogData(Map<String, Object> logDataJson) {
        Map<String, Object> resourceMap = (Map<String, Object>) logDataJson.get("resource");

        LogData logData = new LogData((String) resourceMap.get("biz"), (String) resourceMap.get("scenario"));

        DataMeta meta = metaRegistry.getDataMeta(logData.getId());
        if (meta == null || !(meta instanceof LogMeta)) {
            return null;
        }

        resourceMap.remove("biz");
        resourceMap.remove("scenario");

        Map<String, Object> attributesMap = (Map<String, Object>) logDataJson.get("attributes");

        Object spanMap = logDataJson.get("span_context");
        if (spanMap == null) {
            spanMap = logDataJson.get("spanContext");
        }

        Map<String, Object> properties = new HashMap<>();
        properties.putAll(resourceMap);
        if (spanMap != null) {
            properties.putAll((Map<String, Object>) spanMap);
        }
        properties.put("body", logDataJson.get("body"));
        properties.put("level", logDataJson.get("level"));

        List<Property> metaProperties = ((LogMeta) meta).getProperties();
        for (Property property : metaProperties) {
            if (attributesMap.containsKey(property.getName())) {
                properties.put(property.getName(), attributesMap.remove(property.getName()));
            }
        }

        properties.put("attributes", attributesMap);
        properties.put("timestamp", ((Number) attributesMap.remove("timestamp")).longValue());

        logData.setProperties(properties);
        return logData;
    }
}
