package com.transsion.framework.tango.common.property;

import com.transsion.framework.tango.common.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author mengqi.lv
 * @Date 2022/1/13
 * @Version 1.0
 **/
public class Property<P extends Property> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Property.class);

    private Identifier identifier;
    private String name;
    private NamedType type;
    private NamedType subType;

    public Property(Identifier identifier, String name, NamedType type, NamedType subType) {
        try {
            this.identifier = identifier;
            this.name = name;
            this.type = type;
            this.subType = subType;
        } catch (Exception e) {
            LOGGER.error(String.format("name: %s", name), e);
            throw new RuntimeException(e);
        }
    }

    public Property(String name, NamedType type){
        try {
            this.identifier = null;
            this.name = name;
            this.type = type;
            this.subType = null;
        } catch (Exception e) {
            LOGGER.error(String.format("name: %s", name), e);
            throw new RuntimeException(e);
        }
    }

    public static Property buildProperty(Identifier id, String name, NamedType type) {
        switch (type) {
            case LONG:
                return buildLongProperty(id, name);
            case STRING:
                return buildStringProperty(id, name);
            case BOOLEAN:
                return buildBooleanProperty(id, name);
            default:
                throw new RuntimeException("Not supported type: " + type);
        }
    }

    public P deepCopy() {
        try {
            P result = (P)this.getClass().newInstance();
            result.setType(type);
            result.setName(name);
            result.setIdentifier(identifier);
            result.setSubType(subType);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("can't instantiate the clone object", e);
        }
    }

    public String getName() {
        return name;
    }

    public NamedType getType() {
        return type;
    }

    public NamedType getSubType() {
        return subType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(NamedType type) {
        this.type = type;
    }

    public void setSubType(NamedType type) {
        this.subType = type;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Property property = (Property) o;

        if (identifier != null ? !identifier.equals(property.identifier) : property.identifier != null) {
            return false;
        }
        if (!name.equals(property.name)) {
            return false;
        }
        if (type != property.type) {
            return false;
        }
        if (subType != property.subType) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = identifier != null ? identifier.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (subType == null ? "".hashCode() : subType.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Property{" +
                "identifier=" + identifier +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", subtype=" + subType +
                '}';
    }

    public static Property buildListProperty(Identifier identifier, String name) {
        return new Property(identifier, name, NamedType.LIST, null);
    }

    public static Property buildLongProperty(Identifier identifier, String name) {
        return new Property(identifier, name, NamedType.LONG, null);
    }

    public static Property buildLongProperty(String name) {
        return new Property(null, name, NamedType.LONG, null);
    }

    public static Property buildDoubleProperty(Identifier identifier, String name) {
        return new Property(identifier, name, NamedType.DOUBLE, null);
    }

    public static Property buildDoubleProperty(String name) {
        return new Property(null, name, NamedType.DOUBLE, null);
    }

    public static Property buildStringProperty(Identifier identifier, String name) {
        return new Property(identifier, name, NamedType.STRING, null);
    }

    public static Property buildStringProperty(String name) {
        return new Property(null, name, NamedType.STRING, null);
    }

    public static Property buildDatetimeProperty(String name) {
        return new Property(null, name, NamedType.DATETIME, null);
    }

    public static Property buildBooleanProperty(Identifier identifier, String name) {
        return new Property(identifier, name, NamedType.BOOLEAN, null);
    }

    public static Property buildBooleanProperty(String name) {
        return new Property(null, name, NamedType.BOOLEAN, null);
    }

    public static Property fromJson(Object object, Identifier id) {
        Map<Object, Object> mObject = (Map<Object, Object>)(object);
        String name = (String)mObject.get("name");
        String type = (String) mObject.get("type");
        String subType = (String) mObject.getOrDefault("subtype", "");
        return new Property(id, name, NamedType.from_json_object(type), subType.isEmpty() ? null : NamedType.from_json_object(subType));
    }
}
