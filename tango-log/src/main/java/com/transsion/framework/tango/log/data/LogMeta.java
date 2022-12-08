package com.transsion.framework.tango.log.data;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.common.Utility;
import com.transsion.framework.tango.common.config.StatefulConfig;
import com.transsion.framework.tango.common.property.NamedType;
import com.transsion.framework.tango.common.property.Property;
import com.transsion.framework.tango.common.property.PropertyEnum;
import com.transsion.framework.tango.common.property.PropertyUtils;
import com.transsion.framework.tango.core.data.meta.DataMeta;

import java.util.*;

/**
 * @Author mengqi.lv
 * @Date 2022/10/21
 * @Version 1.0
 **/
public class LogMeta implements DataMeta, StatefulConfig {
    private static final Map<String, NamedType> FIX_FIELDS = new HashMap<String, NamedType>() {
        {
            put("timestamp", NamedType.LONG);
        }
    };

    private Identifier id;
    private String biz;
    private String scenario;
    private long ttlSeconds = 3600 * 24 * 3;
    private List<Property> properties = Collections.synchronizedList(new ArrayList<>());
    private List<Property> orderBy;
    private List<PropertyEnum> propertyEnums;

    public LogMeta(String biz, String scenario) {
        this.id = new Identifier(biz, scenario);
        this.biz = biz;
        this.scenario = scenario;
    }

    public String getScenario() {
        return scenario;
    }

    public String getBiz() {
        return biz;
    }

    public List<PropertyEnum> getPropertyEnums() {
        return propertyEnums;
    }

    public long getTtlSeconds() {
        return ttlSeconds;
    }

    public void setTtlSeconds(long ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<Property> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(List<Property> orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public long ttl() {
        return ttlSeconds;
    }

    public static LogMeta fromJson(Object obj) {
        Map<Object, Object> map = (Map<Object, Object>) obj;
        String biz = (String) map.get("biz");
        String scenario = (String) map.get("scenario");
        LogMeta meta = new LogMeta(biz, scenario);

        meta.properties = new ArrayList<>();

        List<Object> propertiesObj = (List<Object>)map.get("properties");
        Identifier id = meta.getId();
        if (propertiesObj != null) {
            for(Object o : propertiesObj) {
                Property property = Property.fromJson(o, id);
                meta.properties.add(property);
            }
        }

        if (map.containsKey("ttl")) {
            meta.ttlSeconds = ((Number) map.get("ttl")).longValue();
        }

        List<Object> enumObjects = (List<Object>) map.get("enums");
        meta.propertyEnums = new ArrayList<>();
        if (!Utility.isEmpty(enumObjects)) {
            for (Object o : enumObjects) {
                meta.propertyEnums.add(PropertyEnum.fromJson(o, meta.properties));
            }
        }

        List<String> orderBy = (List<String>) map.get("order_by");
        meta.orderBy = new ArrayList<>();
        if (!Utility.isEmpty(orderBy)) {
            for (String o : orderBy) {
                meta.orderBy.add(PropertyUtils.findPropertyByName(meta.properties, o));
            }
        }

        ensureFixedFields(meta, id);

        return meta;
    }

    private static void ensureFixedFields(LogMeta meta, Identifier id) {
        for (Map.Entry<String, NamedType> schema : FIX_FIELDS.entrySet()) {
            Property p = PropertyUtils.findPropertyByName(meta.getProperties(), schema.getKey());
            if (p == null) {
                meta.getProperties().add(Property.buildProperty(id, schema.getKey(), schema.getValue()));
            }
        }
    }
}
