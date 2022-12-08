package com.transsion.framework.tango.common.property;

/**
 * @Author mengqi.lv
 * @Date 2022/2/28
 * @Version 1.0
 **/
public class EnumValue {

    private String name;
    private int value;

    public EnumValue(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
