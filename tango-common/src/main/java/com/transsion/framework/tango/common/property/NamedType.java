package com.transsion.framework.tango.common.property;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/1/13
 * @Version 1.0
 **/
public enum NamedType {
    @SerializedName("enum")
    ENUM("enum", String.class),
    @SerializedName("int")
    INT("int", Integer.class),
    @SerializedName("long")
    LONG("long", Long.class),
    @SerializedName("datetime")
    DATETIME("datetime", Long.class),
    @SerializedName("double")
    DOUBLE("double", Double.class),
    @SerializedName("boolean")
    BOOLEAN("boolean", Boolean.class),
    @SerializedName("string")
    STRING("string", String.class),
    @SerializedName("map")
    MAP("map", Map.class),
    @SerializedName("object")
    OBJECT("object", Object.class),
    @SerializedName("list")
    LIST("list", List.class);

    private static Map<String, Class<?>> codeToTypeMap = new HashMap<>();
    private static Map<Class<?>, String> typeToCodeMap = new HashMap<>();

    static {
        // this is executed after the static members are initialized.
        for (NamedType t : NamedType.values()) {
            codeToTypeMap.put(t.code, t.classType);
            typeToCodeMap.put(t.classType, t.code);
        }
    }

    private final String code;
    private final Class<?> classType;

    private NamedType(String code, Class<?> classType) {
        this.code = code;
        this.classType = classType;
    }

    public String getCode() {
        return code;
    }

    public Class<?> getClassType() {
        return classType;
    }

    @Override
    public String toString() {
        return code;
    }

    public static String getCodeFromType(Class<?> type) {
        String result = typeToCodeMap.get(type);
        if (result == null) {
            throw new IllegalStateException("no type found for Class:" + type);
        }

        return result;
    }

    public static Class<?> getTypeFromCode(String code) {
        Class<?> result = codeToTypeMap.get(code);
        if (result == null) {
            throw new IllegalStateException("no type found for code:" + code);
        }

        return result;
    }

    public static NamedType fromCode(String code) {
        for (NamedType nt : NamedType.values()) {
            if (nt.code.equals(code)) {
                return nt;
            }
        }

        return null;
    }

    public static NamedType fromType(Class<?> type) {
        if (type == null) {
            throw new IllegalArgumentException("null type");
        }

        for (NamedType nt : NamedType.values()) {
            if (nt.getClassType().equals(type)) {
                return nt;
            }
        }

        return null;
    }

    public Object to_json_object() {
        return this.code;
    }

    public static NamedType from_json_object(Object object) {
        return fromCode((String) object);
    }
}
