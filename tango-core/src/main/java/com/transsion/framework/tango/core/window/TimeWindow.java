package com.transsion.framework.tango.core.window;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public class TimeWindow {

    private int length;
    private WindowUnit unit;
    private long millisLength;

    public TimeWindow(WindowUnit unit, int length) {
        this.length = length;
        this.unit = unit;
        this.millisLength = this.unit.getTimeMills() * length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public WindowUnit getUnit() {
        return unit;
    }

    public void setUnit(WindowUnit unit) {
        this.unit = unit;
    }

    public boolean isValid() {
        return length > 0;
    }

    public long getMillisLength() {
        return millisLength;
    }
}
