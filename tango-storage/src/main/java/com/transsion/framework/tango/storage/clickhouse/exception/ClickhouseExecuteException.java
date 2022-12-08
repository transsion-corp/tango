package com.transsion.framework.tango.storage.clickhouse.exception;

import com.transsion.framework.tango.common.Code;
import com.transsion.framework.tango.common.exception.TangoException;

/**
 * @Author mengqi.lv
 * @Date 2022/10/24
 * @Version 1.0
 **/
public class ClickhouseExecuteException extends TangoException {
    public ClickhouseExecuteException(Throwable cause) {
        super(Code.STORAGE_ERROR, cause);
    }

    public ClickhouseExecuteException(String message) {
        super(Code.STORAGE_ERROR, message);
    }

    public ClickhouseExecuteException(String message, Throwable cause) {
        super(Code.STORAGE_ERROR, message, cause);
    }
}
