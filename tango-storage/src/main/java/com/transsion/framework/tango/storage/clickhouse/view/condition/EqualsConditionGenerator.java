package com.transsion.framework.tango.storage.clickhouse.view.condition;

import com.transsion.framework.tango.core.condition.EqualsCondition;

/**
 * @Author mengqi.lv
 * @Date 2022/9/5
 * @Version 1.0
 **/
public abstract class EqualsConditionGenerator<C extends EqualsCondition> extends ConditionGenerator<C> {

    public static class StringEqualsConditionGenerator extends EqualsConditionGenerator<EqualsCondition.StringEqualsCondition> {

        @Override
        protected String gen() {
            StringBuilder sb = new StringBuilder();
            sb.append("(").append(getCondition().getProperty().getName()).append(" equals '")
                    .append(getCondition().getParam()).append("')");
            return sb.toString();
        }
    }
}
