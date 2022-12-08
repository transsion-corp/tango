package com.transsion.framework.tango.common.exception;

import com.transsion.framework.tango.common.Code;

/**
 * @Author mengqi.lv
 * @Date 2022/7/25
 * @Version 1.0
 **/
public class TangoException extends RuntimeException {

    protected final Code code;

    public TangoException(Code code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public TangoException(Code code, String message) {
        super(message);
        this.code = code;
    }

    public TangoException(Code code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public Code getErrorCode() {
        return code;
    }
}
