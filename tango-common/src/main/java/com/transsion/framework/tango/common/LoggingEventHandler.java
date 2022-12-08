package com.transsion.framework.tango.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mengqi.lv
 * @Date 2022/10/21
 * @Version 1.0
 **/
public class LoggingEventHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoggingEventHandler.class);

    @Override
    public void handleEvent(Event event) {
        logger.info(event.toString());
    }

    @Override
    public void handleThrowable(String message, Throwable th) {
        logger.error(message, th);
    }
}
