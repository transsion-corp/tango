package com.transsion.framework.tango.core.data.translate;

import com.transsion.framework.tango.core.data.Data;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface Translator<R, T extends Data> {

    T translate(R raw);
}
