package com.transsion.framework.tango.core.expression;

/**
 * @Author mengqi.lv
 * @Date 2022/8/16
 * @Version 1.0
 **/
public abstract class MinExpression implements AggregateExpression {
    @Override
    public ExpressionType getType() {
        return ExpressionType.MIN;
    }
}
