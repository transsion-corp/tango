package com.transsion.framework.tango.storage.clickhouse.view.condition;

import com.transsion.framework.tango.common.ClassRegistry;
import com.transsion.framework.tango.common.exception.UnimplementedException;
import com.transsion.framework.tango.core.condition.Condition;
import com.transsion.framework.tango.core.condition.EqualsCondition;

/**
 * @Author mengqi.lv
 * @Date 2022/8/31
 * @Version 1.0
 **/
public abstract class ConditionGenerator<C extends Condition> {
    private static final ClassRegistry<Condition, ConditionGenerator> registry =
            new ClassRegistry<>(Condition.class);

    static {
        registerConditionGenerator(EqualsCondition.StringEqualsCondition.class, EqualsConditionGenerator.StringEqualsConditionGenerator.class);
    }

    private static void registerConditionGenerator(Class<? extends Condition> v, Class<? extends ConditionGenerator> g) {
        registry.register(v, g);
    }

    public static String genCondition(Condition c) {
        Class<? extends ConditionGenerator> g = registry.get(c.getClass());
        if (g == null) {
            return null;
        }

        try {
            ConditionGenerator generator = g.newInstance();
            generator.setCondition(c);

            return generator.gen();
        } catch (Exception e) {
            throw new UnimplementedException("failed to generate condition: " + c.getClass());
        }
    }

    protected abstract String gen();

    private C condition;

    public C getCondition() {
        return condition;
    }

    public void setCondition(C condition) {
        this.condition = condition;
    }
}
