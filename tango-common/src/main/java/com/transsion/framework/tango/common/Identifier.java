package com.transsion.framework.tango.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @Author mengqi.lv
 * @Date 2022/1/13
 * @Version 1.0
 **/
public class Identifier {
    private static final Logger logger = LoggerFactory.getLogger(Identifier.class);

    public static final String SEPERATOR = "@";

    private final String group;
    private final String name;

    public Identifier(String group, String name) {
        if (group == null || name == null) {
            throw new IllegalArgumentException("null keys to create the identifier");
        }

        this.group = group;
        this.name = name;
    }

    public String getUnfoldedName() {
        return String.join(SEPERATOR, this.group, this.name);
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getUnfoldedName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)  {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Identifier that = (Identifier) o;
        return Objects.equals(group, that.group) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, name);
    }
}
