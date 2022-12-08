package com.transsion.framework.tango.metrics.data;

/**
 * @Author mengqi.lv
 * @Date 2022/8/16
 * @Version 1.0
 **/
public enum DefaultUnit implements Unit {
    COUNT("c"),
    MILLISECONDS("ms");

    private final String label;

    DefaultUnit(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
