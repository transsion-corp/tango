package com.transsion.framework.tango.common.exception;

import com.transsion.framework.tango.common.Code;

/**
 * @Author mengqi.lv
 * @Date 2021/11/17
 * @Version 1.0
 **/
public class ConfigException extends TangoException {
    public ConfigException(Throwable cause) {
        super(Code.INVALID_CONFIG, cause);
    }

    public ConfigException(String message) {
        super(Code.INVALID_CONFIG, message);
    }

    public ConfigException(String message, Throwable cause) {
        super(Code.INVALID_CONFIG, message, cause);
    }
}
