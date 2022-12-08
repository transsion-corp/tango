package com.transsion.framework.tango.storage.clickhouse.view.expression;

import com.transsion.framework.tango.common.ClassRegistry;
import com.transsion.framework.tango.common.exception.UnimplementedException;
import com.transsion.framework.tango.core.expression.Expression;
import com.transsion.framework.tango.core.expression.RawExpression;

/**
 * @Author mengqi.lv
 * @Date 2022/8/31
 * @Version 1.0
 **/
public abstract class ExpressionGenerator<T extends Expression> {
    private static final ClassRegistry<Expression, ExpressionGenerator> registry =
            new ClassRegistry<>(Expression.class);

    static {
        registerExpressionGenerator(RawExpression.class, RawExpressionGenerator.class);
    }
    private static void registerExpressionGenerator(Class<? extends Expression> v, Class<? extends ExpressionGenerator> g) {
        registry.register(v, g);
    }

    private T expression;

    public T getExpression() {
        return expression;
    }

    public void setExpression(T expression) {
        this.expression = expression;
    }

    public abstract String genSelect();

    public abstract String genEndClause();

    public static ExpressionGenerator newGenerator(Expression exp) {
        Class<? extends ExpressionGenerator> g = registry.get(exp.getClass());
        if (g == null) {
            return null;
        }

        try {
            ExpressionGenerator generator = g.newInstance();
            generator.setExpression(exp);

            return generator;
        } catch (Exception e) {
            throw new UnimplementedException("failed to generate expression: " + exp.getClass());
        }
    }
}
