package com.transsion.framework.tango.core.data;

/**
 * @Author mengqi.lv
 * @Date 2022/9/7
 * @Version 1.0
 **/
public class Point<T> extends CommonData {

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
