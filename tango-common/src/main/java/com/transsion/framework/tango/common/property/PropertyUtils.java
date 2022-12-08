package com.transsion.framework.tango.common.property;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/1/19
 * @Version 1.0
 **/
public class PropertyUtils {

    public static Property findPropertyByName(List<Property> source, String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        for (Property p : source) {
            if (name.equals(p.getName())) {
                return p;
            }
        }

        return null;
    }

    public static PropertyEnum findPropertyEnumByName(List<PropertyEnum> source, String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        for (PropertyEnum p : source) {
            if (name.equals(p.getProperty().getName())) {
                return p;
            }
        }

        return null;
    }
}
