package com.transsion.framework.tango.common;

import java.lang.annotation.*;

/**
 * @Author mengqi.lv
 * @Date 2022/4/9
 * @Version 1.0
 **/
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface NotThreadSafe {
}
