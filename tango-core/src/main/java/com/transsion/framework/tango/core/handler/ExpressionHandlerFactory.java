package com.transsion.framework.tango.core.handler;

import com.transsion.framework.tango.core.expression.Expression;

/**
 * @Author mengqi.lv
 * @Date 2022/9/5
 * @Version 1.0
 **/
public interface ExpressionHandlerFactory {

    ExpressionHandler createHandler(Expression exp);
}
