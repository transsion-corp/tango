package com.transsion.framework.tango.common.exception;

import com.transsion.framework.tango.common.Code;

/**
 * @Author mengqi.lv
 * @Date 2021/11/17
 * @Version 1.0
 **/
public class InitializeException extends TangoException {
    public InitializeException(Throwable cause) {
        super(Code.INITIAL_ERROR, cause);
    }

    public InitializeException(String message) {
        super(Code.INITIAL_ERROR, message);
    }

    public InitializeException(String message, Throwable cause) {
        super(Code.INITIAL_ERROR, message, cause);
    }
}
