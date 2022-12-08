package com.transsion.framework.tango.core.handler;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.core.data.Data;

/**
 * @Author mengqi.lv
 * @Date 2022/9/5
 * @Version 1.0
 **/
public interface ExpressionHandler<T extends Data, R extends Data> {

    R handle();

    void setSource(Identifier viewId, T source);
}
