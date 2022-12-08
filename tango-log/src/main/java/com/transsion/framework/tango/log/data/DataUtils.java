package com.transsion.framework.tango.log.data;

import com.transsion.framework.tango.common.Utility;
import com.transsion.framework.tango.common.property.NamedType;
import com.transsion.framework.tango.common.property.Property;

import java.util.List;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/4/9
 * @Version 1.0
 **/
public class DataUtils {

    public static String getRow(LogMetaWrapper wrapper, LogData data) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (Property p : wrapper.getMeta().getProperties()) {
            if (first) {
                sb.append("(");
                first = false;
            } else {
                sb.append(",");
            }
            Object value = data.getProperties().get(p.getName());
            if (p.getType().equals(NamedType.STRING) || p.getType().equals(NamedType.ENUM)) {
                sb.append("'");
                sb.append(value == null ? "" : value);
                sb.append("'");
            } else if (p.getType().equals(NamedType.BOOLEAN)) {
                sb.append((Boolean) value ? 1 : 0);
            } else if (p.getType().equals(NamedType.MAP)) {
                appendMapData(sb, (Map<String, Object>) value);
            } else if (p.getType().equals(NamedType.LIST)) {
                appendListData(sb, (List<Object>) value, p.getSubType());
            } else {
                sb.append(value == null ? 0 : value);
            }
        }
        sb.append(")");

        return sb.toString();
    }

    private static void appendMapData(StringBuilder sb, Map<String, Object> map) {
        if (Utility.isEmpty(map)) {
            sb.append("{}");
            return;
        }
        sb.append("{");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append("'").append(entry.getKey()).append("':'").append(entry.getValue()).append("', ");
        }
        sb.append("}");
    }

    private static void appendListData(StringBuilder sb, List<Object> list, NamedType subType) {
        if (Utility.isEmpty(list)) {
            sb.append("[]");
            return;
        }
        sb.append("[");
        boolean first = true;
        for (Object value : list) {
            if (!first) {
                sb.append(",");
            } else {
                first = false;
            }
            sb.append(getValue(value, subType));
        }
        sb.append("]");
    }

    private static Object getValue(Object value, NamedType subType) {
        if (NamedType.STRING.equals(subType) || NamedType.ENUM.equals(subType)) {
            value = value == null ? "" : String.valueOf(value);
            return "'" + value + "'";
        }
        if (NamedType.LONG.equals(subType)) {
            value = value == null ? 0 : value;
            return ((Number) value).longValue();
        }
        if (NamedType.DOUBLE.equals(subType)) {
            value = value == null ? 0 : value;
            return ((Number) value).doubleValue();
        }
        if (NamedType.INT.equals(subType)) {
            value = value == null ? 0 : value;
            return ((Number) value).intValue();
        }
        if (NamedType.BOOLEAN.equals(subType)) {
            value = value == null ? false : value;
            return ((Boolean) value) ? 1 : 0;
        }
        return "";
    }
}
