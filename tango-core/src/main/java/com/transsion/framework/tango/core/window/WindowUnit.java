package com.transsion.framework.tango.core.window;

/**
 * @Author mengqi.lv
 * @Date 2021/11/14
 * @Version 1.0
 **/
public enum WindowUnit {
    millis(1L),
    SECOND(1000L),
    min(60 * 1000L),
    hour(60 * 60 * 1000L),
    day(24 * 60 * 60 * 1000L),
    month(30 * 24 * 60 * 60 * 1000L);

    WindowUnit(Long timeMills) {
        this.timeMills = timeMills;
    }

    private Long timeMills;

    public Long getTimeMills() {
        return timeMills;
    }
}
