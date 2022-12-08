package com.transsion.framework.tango.common;

/**
 * @Author mengqi.lv
 * @Date 2022/8/29
 * @Version 1.0
 **/
public interface EventHandler {

    void handleEvent(Event event);

    void handleThrowable(String message, Throwable th);
}
