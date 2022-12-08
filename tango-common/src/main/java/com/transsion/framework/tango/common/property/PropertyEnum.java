package com.transsion.framework.tango.common.property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/2/28
 * @Version 1.0
 **/
public class PropertyEnum {
    private Property property;
    private List<EnumValue> enumValues;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public List<EnumValue> getEnumValues() {
        return enumValues;
    }

    public void setEnumValues(List<EnumValue> enumValues) {
        this.enumValues = enumValues;
    }

    public static PropertyEnum fromJson(Object obj, List<Property> properties) {
        PropertyEnum propertyEnum = new PropertyEnum();
        Map<String, Object> json = (Map<String, Object>) obj;
        Property property = PropertyUtils.findPropertyByName(properties, (String) json.get("name"));
        propertyEnum.setProperty(property);
        Map<String, Number> values = (Map) json.get("values");
        propertyEnum.setEnumValues(new ArrayList<>(values.size()));
        for (Map.Entry<String, Number> valueEntry : values.entrySet()) {
            propertyEnum.getEnumValues().add(new EnumValue(valueEntry.getKey(), valueEntry.getValue().intValue()));
        }
        return propertyEnum;
    }
}
