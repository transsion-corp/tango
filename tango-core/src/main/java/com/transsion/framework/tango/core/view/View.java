package com.transsion.framework.tango.core.view;

import com.transsion.framework.tango.common.Identifier;
import com.transsion.framework.tango.core.condition.Condition;
import com.transsion.framework.tango.core.expression.Expression;

import java.util.List;

/**
 * @Author mengqi.lv
 * @Date 2022/8/1
 * @Version 1.0
 **/
public interface View {

    List<View> getParents();

    Identifier getViewId();

    /**
     * May split into different views.
     * @return
     */
    Identifier getDataId();

    long getStartTime();

    long getEndTime();

    Condition getCondition();

    Expression getExpression();
}
