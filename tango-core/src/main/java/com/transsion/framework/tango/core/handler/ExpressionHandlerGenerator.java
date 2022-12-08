package com.transsion.framework.tango.core.handler;

import com.transsion.framework.tango.common.ClassRegistry;
import com.transsion.framework.tango.common.exception.UnimplementedException;
import com.transsion.framework.tango.core.expression.Expression;
import com.transsion.framework.tango.core.expression.RateExpression;

/**
 * @Author mengqi.lv
 * @Date 2022/9/5
 * @Version 1.0
 **/
public abstract class ExpressionHandlerGenerator<T extends Expression> {

    private static final ClassRegistry<Expression, ExpressionHandlerGenerator> registry =
            new ClassRegistry<>(Expression.class);

    static {
        registerExpressionHandler(RateExpression.class, RateExpressionHandlerGenerator.class);
    }

    private static void registerExpressionHandler(Class<? extends Expression> v, Class<? extends ExpressionHandlerGenerator> h) {
        registry.register(v, h);
    }

    public static ExpressionHandlerGenerator newHandler(Expression exp) {
        Class<? extends ExpressionHandlerGenerator> g = registry.get(exp.getClass());
        if (g == null) {
            return null;
        }

        try {
            ExpressionHandlerGenerator generator = g.newInstance();
            generator.setExpression(exp);

            return generator;
        } catch (Exception e) {
            throw new UnimplementedException("failed to generate expression handler: " + exp.getClass());
        }
    }

    private T expression;

    public abstract ExpressionHandler gen();

    public T getExpression() {
        return expression;
    }

    public void setExpression(T expression) {
        this.expression = expression;
    }
}
