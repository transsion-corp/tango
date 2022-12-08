package com.transsion.framework.tango.common;

/**
 * @Author mengqi.lv
 * @Date 2020/12/8
 * @Version 1.0
 */
public enum Code {

    OK("APM.0", "OK"),
    PARAM_VALIDATED_FAIL("Tango.4400", "Param validated fail"),

    NOT_SUPPORT("Tango.4404", "Not support."),

    INTERNAL_ERROR("Tango.5500", "Internal error."),
    INVALID_CONFIG("Tango.5590", "Initial error."),
    INITIAL_ERROR("Tango.5599", "Initial error."),

    STORAGE_ERROR("Tango.6000", "Storage execute error.");

    private String code;
    private String message;

    Code(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
