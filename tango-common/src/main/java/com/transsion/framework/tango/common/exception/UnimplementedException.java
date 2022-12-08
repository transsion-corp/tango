package com.transsion.framework.tango.common.exception;

import com.transsion.framework.tango.common.Code;

/**
 * @Author mengqi.lv
 * @Date 2021/11/20
 * @Version 1.0
 **/
public class UnimplementedException extends TangoException {
    public UnimplementedException(Throwable cause) {
        super(Code.NOT_SUPPORT, cause);
    }

    public UnimplementedException(String message) {
        super(Code.NOT_SUPPORT, message);
    }

    public UnimplementedException(String message, Throwable cause) {
        super(Code.NOT_SUPPORT, message, cause);
    }
}
